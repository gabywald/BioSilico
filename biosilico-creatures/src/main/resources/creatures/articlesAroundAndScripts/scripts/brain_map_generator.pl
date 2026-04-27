#!/usr/bin/perl
use strict;
use warnings;
use SVG;
use Getopt::Long;

=head1 NAME
brain_map_generator.pl - Generate a brain map with lobes and instincts from Creatures genome files.

=head1 SYNOPSIS
perl brain_map_generator.pl --input <genome_file.gen> [--output <output.svg>]

=head1 DESCRIPTION
This script parses a Creatures genome file and generates an SVG brain map showing:
- Brain lobes (colored rectangles)
- Instinct cells (marked with circles)
- Instinct actions (legend)

=cut

# --- Reference Data ---
my %LOBE_NAMES = (
    0 => "Perception",
    1 => "Drive",
    2 => "Stimulus Source",
    3 => "Verb",
    4 => "Noun",
    5 => "General Sense",
    6 => "Decision",
    7 => "Attention",
    8 => "Concept",
    9 => "Regulator",
);

my %ACTION_NAMES = (
    0 => "Quiescent",
    1 => "Push (Activate 1)",
    2 => "Pull (Activate 2)",
    3 => "Stop (Deactivate)",
    4 => "Come (Approach)",
    5 => "Run (Retreat)",
    6 => "Get",
    7 => "Drop",
    8 => "Think/Say",
    9 => "Sleep/Rest",
    10 => "Left",
    11 => "Right",
);

my @COLORS = (
    '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFBE0B', '#FB5607',
    '#8338EC', '#3A86FF', '#FF006E', '#A5DD9B', '#FF9E9E'
);

# --- Parse Genome File ---
sub parse_genome_file {
    my ($filename) = @_;
    open(my $fh, "<:raw", $filename) or die "Cannot open $filename: $!";
    binmode($fh);
    my $content = do { local $/; <$fh> };
    close($fh);

    my @genes;
    my $offset = 0;

    while ($offset < length($content)) {
        my $header = substr($content, $offset, 9);
        last unless length($header) >= 8;

        my ($gene_marker, $type, $subtype, $number, $unknown, $switch_on) =
            unpack("A4 C C C C C", substr($header, 0, 8));

        my $sex_mut = (length($header) >= 9) ? unpack("C", substr($header, 8, 1)) : 0;
        my $data_length = (length($content) > $offset + 9) ? unpack("C", substr($content, $offset + 9, 1)) : 0;
        $data_length //= 0;

        $offset += 1;
        next unless $gene_marker eq "gene";

        my $sex_dep = ($sex_mut & 0x10) ? "Female" : (($sex_mut & 0x08) ? "Male" : "None");
        my @mutability;
        push @mutability, "Mutable" if $sex_mut & 0x01;
        push @mutability, "Duplicable" if $sex_mut & 0x02;
        push @mutability, "Deletable" if $sex_mut & 0x04;
        my $mutability = @mutability ? join(",", @mutability) : "None";

        my $data = [];
        if ($data_length > 0 && $offset + 10 + $data_length <= length($content)) {
            $data = [unpack("C*", substr($content, $offset + 10, $data_length))];
        }

        my $gene = {
            type     => $type,
            subtype  => $subtype,
            number   => $number,
            switch_on => $switch_on,
            sex_dep  => $sex_dep,
            mutability => $mutability,
            data     => $data,
        };

        # Parse data based on type/subtype
        if ($type == 0x00 && $subtype == 0x00) {
            $gene->{parsed_data} = parse_lobe($data);
        }
        elsif ($type == 0x02 && $subtype == 0x05) {
            $gene->{parsed_data} = parse_instinct($data);
        }

        push @genes, $gene;
        $offset += 10 + $data_length;
    }

    return \@genes;
}

sub parse_lobe {
    my ($data) = @_;
    return unless @$data >= 5;
    return {
        type => "lobe",
        x => $data->[0],
        y => $data->[1],
        width => $data->[2],
        height => $data->[3],
        perception_link => $data->[4] ? "Yes" : "No",
    };
}

sub parse_instinct {
    my ($data) = @_;
    return unless @$data >= 9;
    my @conditions;
    for my $i (0..2) {
        push @conditions, {
            lobe => $LOBE_NAMES{$data->[$i * 2]} // sprintf("Lobe%02X", $data->[$i * 2]),
            cell => $data->[$i * 2 + 1],
        };
    }
    return {
        type => "instinct",
        conditions => \@conditions,
        action => $ACTION_NAMES{$data->[6]} // sprintf("Action%02X", $data->[6]),
        reward_punish => $data->[7] == 0 ? "Reward" : "Punish",
        amount => $data->[8],
    };
}

# --- Extract Lobes and Instincts ---
sub extract_lobes {
    my ($genes) = @_;
    my @lobes;
    for my $gene (@$genes) {
        if ($gene->{type} == 0x00 && $gene->{subtype} == 0x00 && exists $gene->{parsed_data}{x}) {
            push @lobes, {
                number => $gene->{number},
                name => $LOBE_NAMES{$gene->{number}} // sprintf("Lobe%02X", $gene->{number}),
                %{$gene->{parsed_data}},
            };
        }
    }
    return \@lobes;
}

sub extract_instincts {
    my ($genes) = @_;
    my @instincts;
    for my $gene (@$genes) {
        if ($gene->{type} == 0x02 && $gene->{subtype} == 0x05 && exists $gene->{parsed_data}{conditions}) {
            push @instincts, $gene->{parsed_data};
        }
    }
    return \@instincts;
}

# --- Generate SVG ---
sub generate_svg {
    my ($lobes, $instincts, $output_file) = @_;
    my $svg = SVG->new(width => 800, height => 600, viewBox => "0 0 64 48");

    # Background
    $svg->rectangle(x => 0, y => 0, width => 64, height => 48, style => { fill => 'white' });

    # Draw lobes
    for my $i (0..$#$lobes) {
        my $lobe = $lobes->[$i];
        my $color = $COLORS[$i % @COLORS];

        $svg->rectangle(
            x      => $lobe->{x},
            y      => $lobe->{y},
            width  => $lobe->{width},
            height => $lobe->{height},
            style  => {
                fill           => $color,
                stroke         => 'black',
                'stroke-width' => 0.1,
                opacity        => 0.7,
            },
            id => "lobe-$lobe->{number}",
        );

        $svg->text(
            x          => $lobe->{x} + $lobe->{width} / 2,
            y          => $lobe->{y} + $lobe->{height} / 2 + 2,
            'font-size' => 0.5,
            'text-anchor' => 'middle',
            'dominant-baseline' => 'middle',
            '-cdata'   => $lobe->{name},
        );
    }

    # Draw instinct cells
    for my $instinct (@$instincts) {
        for my $condition (@{$instinct->{conditions}}) {
            my $lobe_name = $condition->{lobe};
            my $cell = $condition->{cell};

            # Find the lobe by name
            print "'".$_->{name}."' :: '".$lobe_name."'\n";
            my $lobe = first { $_->{name} eq $lobe_name } @$lobes;
            if ($lobe) {
                $svg->circle(
                    cx => $lobe->{x} + $lobe->{width} / 2,
                    cy => $lobe->{y} + $lobe->{height} / 2,
                    r  => 0.5,
                    style => { fill => 'black' },
                );

                $svg->text(
                    x          => $lobe->{x} + $lobe->{width} / 2,
                    y          => $lobe->{y} + $lobe->{height} / 2 - 1,
                    'font-size' => 0.3,
                    'text-anchor' => 'middle',
                    '-cdata'   => $cell,
                );
            }
        }
    }

    # Add legend for instincts
    my $y = 10;
    for my $instinct (@$instincts) {
        $svg->text(
            x          => 50,
            y          => $y,
            'font-size' => 0.4,
            '-cdata'   => "$instinct->{action} ($instinct->{reward_punish}: $instinct->{amount})",
        );
        $y += 2;
    }

    open(my $fh, ">", $output_file) or die "Cannot open $output_file: $!";
    print $fh $svg->xmlify;
    close($fh);
}

# Helper function
sub first (&@) {
    my ($code, @array) = @_;
    for my $item (@array) {
        return $item if $code->($item);
    }
    return undef;
}

# --- Main ---
my $input_file;
my $output_file = "brain_map_pl.svg";

GetOptions(
    "input=s"  => \$input_file,
    "output=s" => \$output_file,
);

die "Usage: $0 --input <genome_file.gen> [--output <output.svg>]" unless $input_file;

my $genes = parse_genome_file($input_file);
my $lobes = extract_lobes($genes);
my $instincts = extract_instincts($genes);

generate_svg($lobes, $instincts, $output_file);
print "Brain map generated: $output_file\n";

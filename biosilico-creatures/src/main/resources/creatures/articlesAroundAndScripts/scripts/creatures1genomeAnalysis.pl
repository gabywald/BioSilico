#!/usr/bin/perl
use strict;
use warnings;
use JSON::PP;

=head1 NAME
creatures1genomeAnalysis.pl - Parse and analyze Creatures genome files.

=head1 SYNOPSIS
perl creatures1genomeAnalysis.pl [filename.gen]

=head1 DESCRIPTION
This script parses Creatures genome files (`.gen`) and extracts gene information.
It supports all gene types and subtypes, including Brain Lobes, Biochemistry Reactions, and Creature Instincts.
The output includes human-readable descriptions and JSON exports.

=head1 GENE STRUCTURE
- Header: 8 bytes (gene marker, type, subtype, etc.)
- Data: Variable length, parsed based on gene type/subtype.

=head1 GENE TYPES AND SUBTYPES
=over 4
=item * Brain Genes (Type 0x00)
=over 8
=item - Lobe (Subtype 0x00): Defines brain lobes with position and size.
=back
=item * Biochemistry Genes (Type 0x01)
=over 8
=item - Receptor (Subtype 0x00): Detects chemical concentrations.
=item - Emitter (Subtype 0x01): Emits chemicals.
=item - Reaction (Subtype 0x02): Chemical reactions.
=item - Half-Lives (Subtype 0x03): Chemical decay rates.
=item - Initial Concentration (Subtype 0x04): Initial chemical levels.
=back
=item * Creature Genes (Type 0x02)
=over 8
=item - Stimulus (Subtype 0x00): Stimulus responses.
=item - Genus (Subtype 0x01): Species and parent information.
=item - Appearance (Subtype 0x02): Visual appearance.
=item - Instinct (Subtype 0x05): Innate behaviors.
=back

=cut

# --- Reference Data Sources ---
# Source: creatures1BrainMapCells_GenesHeader.txt
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
    9 => "Regulator",  # Creatures 2+
);

# Source: creaturesDevelopmentRessources.pdf, p.8-12
my %CHEM_NAMES = (
    0x00 => "Pain",
    0x01 => "Need for Pleasure",
    0x02 => "Hunger",
    0x03 => "Coldness",
    0x04 => "Hotness",
    0x0A => "Fear",
    0x0B => "Boredom",
    0x1E => "Reward",
    0x1F => "Punishment",
    # Additional chemicals can be added here from the documentation.
);

# Source: creatures1BrainMapCells_GenesHeader.txt, p.21
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

# --- Gene Class ---
package Gene;
use Data::Dumper;

=head2 Gene->new(%args)
Creates a new Gene object.

=cut
sub new {
    my ($class, %args) = @_;
    my $self = {
        # Required fields
        type        => defined $args{type}        ? $args{type}        : 0,
        subtype     => defined $args{subtype}     ? $args{subtype}     : 0,
        number      => defined $args{number}      ? $args{number}      : 0,
        switch_on   => defined $args{switch_on}   ? $args{switch_on}   : 0,
        # Optional fields
        sex_dep     => defined $args{sex_dep}     ? $args{sex_dep}     : "None",
        mutability  => defined $args{mutability}  ? $args{mutability}  : "None",
        data        => defined $args{data}        ? $args{data}        : [],
        parsed_data => defined $args{parsed_data} ? $args{parsed_data} : {},
    };
    bless $self, $class;
    return $self;
}

=head2 parse_reaction()
Parses Reaction genes (Biochemistry type).

=cut
sub parse_reaction {
    my ($self) = @_;
    return unless defined $self->{type} && $self->{type} == 0x01 &&
                  defined $self->{subtype} && $self->{subtype} == 0x02;
    return unless ref($self->{data}) eq 'ARRAY' && @{$self->{data}} >= 4;

    my @data = @{$self->{data}};
    my @reactants = ();
    my @products = ();
    for my $i (0..3) {
        last if $i + 1 >= @data;
        my $chem = $data[$i + 1] || 0;
        my $proportion = $data[$i] || 0;
        my $container = ($i < 2) ? \@reactants : \@products;
        push @$container, {
            chem => $chem,
            proportion => $proportion,
            chem_name => $CHEM_NAMES{$chem} || sprintf("Chem%02X", $chem),
        };
    }

    my $rate = (@data >= 9 && defined $data[8]) ? $data[8] : 1;
    $self->{parsed_data} = {
        reactants => \@reactants,
        products => \@products,
        rate => $rate,
    };
}

=head2 parse_instinct()
Parses Instinct genes (Creature type).

=cut
sub parse_instinct {
    my ($self) = @_;
    return unless defined $self->{type} && $self->{type} == 0x02 &&
                  defined $self->{subtype} && $self->{subtype} == 0x05;
    return unless ref($self->{data}) eq 'ARRAY' && @{$self->{data}} >= 9;

    my @data = @{$self->{data}};
    my ($lobe1, $cell1, $lobe2, $cell2, $lobe3, $cell3, $action, $reward_punish, $amount) = @data[0..8];
    $self->{parsed_data} = {
        conditions => [
            { lobe => $LOBE_NAMES{$lobe1} || sprintf("Lobe%02X", $lobe1 || 0), cell => $cell1 || 0 },
            { lobe => $LOBE_NAMES{$lobe2} || sprintf("Lobe%02X", $lobe2 || 0), cell => $cell2 || 0 },
            { lobe => $LOBE_NAMES{$lobe3} || sprintf("Lobe%02X", $lobe3 || 0), cell => $cell3 || 0 },
        ],
        action => $ACTION_NAMES{$action} || sprintf("Action%02X", $action || 0),
        reward_punish => (defined $reward_punish && $reward_punish == 0) ? "Reward" : "Punish",
        amount => $amount || 0,
    };
}

=head2 parse_lobe()
Parses Brain Lobe genes.

=cut
sub parse_lobe {
    my ($self) = @_;
    return unless defined $self->{type} && $self->{type} == 0x00 &&
                  defined $self->{subtype} && $self->{subtype} == 0x00;
    return unless ref($self->{data}) eq 'ARRAY' && @{$self->{data}} >= 5;

    my ($x, $y, $width, $height, $perception_link) = @{$self->{data}}[0..4];
    $self->{parsed_data} = {
        position => { x => $x || 0, y => $y || 0 },
        size => { width => $width || 0, height => $height || 0 },
        perception_link => defined $perception_link ? ($perception_link ? "Yes" : "No") : "No",
        neurons => ($width || 0) * ($height || 0),
    };
}

=head2 to_string()
Returns a human-readable string representation of the gene.

=cut
sub to_string {
    my ($self) = @_;

    my $str = sprintf(
        "Gene %03d: Type=0x%02X (%s), Subtype=0x%02X (%s), SwitchOn=%d (%s), SexDep=%s, Mutability=%s",
        $self->{number} || 0,
        $self->{type} || 0, Gene::_get_type_name($self->{type}),
        $self->{subtype} || 0, Gene::_get_subtype_name($self->{type}, $self->{subtype}),
        $self->{switch_on} || 0, Gene::_get_switch_on_name($self->{switch_on}),
        $self->{sex_dep} || "None",
        $self->{mutability} || "None",
    );

    # Add details for Reaction genes
    if (defined $self->{type} && $self->{type} == 0x01 &&
        defined $self->{subtype} && $self->{subtype} == 0x02 &&
        ref($self->{parsed_data}) eq 'HASH' && exists $self->{parsed_data}{reactants}) {
        my $reaction = $self->{parsed_data};
        $str .= "\n  Reaction: " .
            join(" + ", map { ($_->{proportion} || 0) . " " . ($_->{chem_name} || "Unknown") } @{$reaction->{reactants}}) . " → " .
            join(" + ", map { ($_->{proportion} || 0) . " " . ($_->{chem_name} || "Unknown") } @{$reaction->{products}}) .
            " (Rate: " . ($reaction->{rate} || 1) . ")";
    }
    # Add details for Instinct genes
    elsif (defined $self->{type} && $self->{type} == 0x02 &&
           defined $self->{subtype} && $self->{subtype} == 0x05 &&
           ref($self->{parsed_data}) eq 'HASH' && exists $self->{parsed_data}{conditions}) {
        my $instinct = $self->{parsed_data};
        $str .= "\n  Instinct: IF " .
            join(" AND ", map { ($_->{lobe} || "Unknown") . "[" . ($_->{cell} || 0) . "]" } @{$instinct->{conditions}}) .
            " THEN " . ($instinct->{action} || "Unknown") .
            " (" . ($instinct->{reward_punish} || "Unknown") . ": " . ($instinct->{amount} || 0) . ")";
    }
    # Add details for Brain Lobe genes
    elsif (defined $self->{type} && $self->{type} == 0x00 &&
           defined $self->{subtype} && $self->{subtype} == 0x00 &&
           ref($self->{parsed_data}) eq 'HASH' && exists $self->{parsed_data}{position}) {
        my $lobe = $self->{parsed_data};
        $str .= "\n  Lobe: " . ($LOBE_NAMES{$self->{number}} || sprintf("Lobe%02X", $self->{number} || 0)) .
            " at (" . ($lobe->{position}{x} || 0) . "," . ($lobe->{position}{y} || 0) . "), " .
            "Size: " . ($lobe->{size}{width} || 0) . "x" . ($lobe->{size}{height} || 0) .
            ", Neurons: " . ($lobe->{neurons} || 0) .
            ", Perception Link: " . ($lobe->{perception_link} || "No");
    }

    return $str;
}

# Helper functions
sub _get_type_name {
    my ($type) = @_;
    my %TYPE_NAMES = (
        0x00 => "Brain",
        0x01 => "Biochemistry",
        0x02 => "Creature",
    );
    return $TYPE_NAMES{$type} || "Unknown";
}

sub _get_subtype_name {
    my ($type, $subtype) = @_;
    my %SUBTYPE_NAMES = (
        0x00 => { 0x00 => "Lobe" },
        0x01 => {
            0x00 => "Receptor",
            0x01 => "Emitter",
            0x02 => "Reaction",
            0x03 => "Half-Lives",
            0x04 => "Initial Concentration",
        },
        0x02 => {
            0x00 => "Stimulus",
            0x01 => "Genus",
            0x02 => "Appearance",
            0x03 => "Pose",
            0x04 => "Gait",
            0x05 => "Instinct",
            0x06 => "Pigment",
            0x07 => "Pigment Bleed",
        },
    );
    return $SUBTYPE_NAMES{$type}{$subtype} || "Unknown";
}

sub _get_switch_on_name {
    my ($stage) = @_;
    my @STAGE_NAMES = qw(Embryo Child Youth Adolescent Adult Senior Old);
    return $STAGE_NAMES[$stage] || "Unknown";
}

package main;

=head1 FUNCTIONS

=head2 parse_genome_file($filename)
Parses a .gen file and returns a list of Gene objects.

=cut
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
        last unless length($header) >= 8;  # 8 bytes minimum for header

        my ($gene_marker, $type, $subtype, $number, $unknown, $switch_on) =
            unpack("A4 C C C C C", substr($header, 0, 8));

        my $sex_mut = (length($header) >= 9) ? unpack("C", substr($header, 8, 1)) : 0;
        my $data_length = (length($content) > $offset + 9) ? unpack("C", substr($content, $offset + 9, 1)) : 0;
        $data_length //= 0;

        $offset += 1;  # Skip invalid marker
        next unless $gene_marker eq "gene";

        # Parse sex dependency and mutability
        my $sex_dep = ($sex_mut & 0x10) ? "Female" : (($sex_mut & 0x08) ? "Male" : "None");
        my @mutability;
        push @mutability, "Mutable" if $sex_mut & 0x01;
        push @mutability, "Duplicable" if $sex_mut & 0x02;
        push @mutability, "Deletable" if $sex_mut & 0x04;
        my $mutability = @mutability ? join(",", @mutability) : "None";

        # Parse gene data
        my $data = [];
        if ($data_length > 0 && $offset + 10 + $data_length <= length($content)) {
            $data = [unpack("C*", substr($content, $offset + 10, $data_length))];
        }

        my $gene = Gene->new(
            type        => $type,
            subtype     => $subtype,
            number      => $number,
            switch_on   => $switch_on,
            sex_dep     => $sex_dep,
            mutability  => $mutability,
            data        => $data,
        );

        eval { $gene->parse_reaction() };
        eval { $gene->parse_instinct() };
        eval { $gene->parse_lobe() };

        push @genes, $gene;
        $offset += 10 + $data_length;
    }

    return \@genes;
}

=head2 export_to_json($genes, $filename)
Exports gene data to a JSON file.

=cut
sub export_to_json {
    my ($genes, $filename) = @_;
    my @json_genes;
    for my $gene (@$genes) {
        my $json_gene = {
            type        => sprintf("0x%02X", $gene->{type} || 0),
            type_name   => Gene::_get_type_name($gene->{type}),
            subtype     => sprintf("0x%02X", $gene->{subtype} || 0),
            subtype_name => Gene::_get_subtype_name($gene->{type}, $gene->{subtype}),
            number      => $gene->{number} || 0,
            switch_on   => $gene->{switch_on} || 0,
            switch_on_name => Gene::_get_switch_on_name($gene->{switch_on}),
            sex_dep     => $gene->{sex_dep} || "None",
            mutability  => $gene->{mutability} || "None",
            data        => $gene->{data},
        };
        $json_gene->{parsed_data} = $gene->{parsed_data} if ref($gene->{parsed_data}) eq 'HASH';
        push @json_genes, $json_gene;
    }
    open(my $json_fh, ">", $filename) or die "Cannot open $filename: $!";
    print $json_fh JSON::PP->new->pretty->encode(\@json_genes);
    close($json_fh);
}

=head2 display_genes($genes)
Displays gene information in a human-readable format.

=cut
sub display_genes {
    my ($genes) = @_;
    for my $gene (@$genes) {
        print $gene->to_string(), "\n";
    }
}

# --- Main Program ---
if (@ARGV) {
    my $filename = $ARGV[0];
    my $genes = parse_genome_file($filename);
    display_genes($genes);
    my $json_file = "$filename-byPL.json";
    export_to_json($genes, $json_file) if $filename =~ /\.gen$/;
    print "Exported to $json_file\n";
}

=head1 EXAMPLES

=head2 Example: Parsing a Genome File
perl creatures1genomeAnalysis.pl dad1.gen

=head2 Example Output
Gene 001: Type=0x02 (Creature), Subtype=0x01 (Genus), SwitchOn=0 (Embryo), SexDep=None, Mutability=None
Exported to dad1.gen.json

=cut

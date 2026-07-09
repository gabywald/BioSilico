#!/usr/bin/perl
use strict;
use warnings;
use Getopt::Long;
use JSON::PP;
use Data::Dumper;

=head1 NAME
compare_genomes.pl - Compare two Creatures genome files.

=head1 SYNOPSIS
perl compare_genomes.pl --file1 <file1.gen> --file2 <file2.gen> --output <output.txt>

=head1 DESCRIPTION
This script compares two Creatures genome files and identifies added, removed, and modified genes.

=cut

# --- Gene Class ---
package Gene;
sub new {
    my ($class, %args) = @_;
    my $self = {
        type        => $args{type}        // 0,
        subtype     => $args{subtype}     // 0,
        number      => $args{number}      // 0,
        switch_on   => $args{switch_on}   // 0,
        sex_dep     => $args{sex_dep}     // "None",
        mutability  => $args{mutability}  // "None",
        data        => $args{data}        // [],
        parsed_data => $args{parsed_data} // {},
    };
    bless $self, $class;
    return $self;
}

sub to_string {
    my ($self) = @_;
    return sprintf(
        "Gene %03d: Type=0x%02X, Subtype=0x%02X, SwitchOn=%d, SexDep=%s, Mutability=%s",
        $self->{number},
        $self->{type},
        $self->{subtype},
        $self->{switch_on},
        $self->{sex_dep},
        $self->{mutability},
    );
}

sub equals {
    my ($self, $other) = @_;
    return 0 unless $self->{type} == $other->{type};
    return 0 unless $self->{subtype} == $other->{subtype};
    return 0 unless $self->{number} == $other->{number};
    return 0 unless $self->{switch_on} == $other->{switch_on};
    return 0 unless $self->{sex_dep} eq $other->{sex_dep};
    return 0 unless $self->{mutability} eq $other->{mutability};
    return 0 unless join(",", @{$self->{data}}) eq join(",", @{$other->{data}});
    return 1;
}

package main;

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

        my $gene = Gene->new(
            type        => $type,
            subtype     => $subtype,
            number      => $number,
            switch_on   => $switch_on,
            sex_dep     => $sex_dep,
            mutability  => $mutability,
            data        => $data,
        );

        push @genes, $gene;
        $offset += 10 + $data_length;
    }

    return \@genes;
}

# --- Compare Genomes ---
sub compare_genomes {
    my ($genes1, $genes2) = @_;

    my %genes1_hash = map { $_->{number} => $_ } @$genes1;
    my %genes2_hash = map { $_->{number} => $_ } @$genes2;

    my @added = grep { !$genes1_hash{$_->{number}} } @$genes2;
    my @removed = grep { !$genes2_hash{$_->{number}} } @$genes1;
    my @common = grep { $genes1_hash{$_->{number}} && $genes2_hash{$_->{number}} } @$genes1;

    my @modified;
    for my $gene (@common) {
        my $gene1 = $genes1_hash{$gene->{number}};
        my $gene2 = $genes2_hash{$gene->{number}};
        push @modified, { old => $gene1, new => $gene2 } unless $gene1->equals($gene2);
    }

    return {
        added    => \@added,
        removed  => \@removed,
        modified => \@modified,
        unchanged => scalar(@common) - scalar(@modified),
    };
}

# --- Generate Report ---
sub generate_report {
    my ($comparison, $file1, $file2, $genes1, $genes2, $output) = @_;

    open(my $fh, ">", $output) or die "Cannot open $output: $!";

    print $fh "=== Genome Comparison Report ===\n";
    print $fh "File 1: $file1\n";
    print $fh "File 2: $file2\n\n";

    print $fh "=== Added Genes (", scalar(@{$comparison->{added}}), ") ===\n";
    for my $gene (@{$comparison->{added}}) {
        print $fh $gene->to_string(), "\n";
    }

    print $fh "\n=== Removed Genes (", scalar(@{$comparison->{removed}}), ") ===\n";
    for my $gene (@{$comparison->{removed}}) {
        print $fh $gene->to_string(), "\n";
    }

    print $fh "\n=== Modified Genes (", scalar(@{$comparison->{modified}}), ") ===\n";
    for my $mod (@{$comparison->{modified}}) {
        print $fh "Gene ", $mod->{old}->{number}, ":\n";
        print $fh "  Old: ", $mod->{old}->to_string(), "\n";
        print $fh "  New: ", $mod->{new}->to_string(), "\n";
    }

    print $fh "\n=== Summary ===\n";
    print $fh "Total genes in File 1: ", scalar(@{$genes1}), "\n";
    print $fh "Total genes in File 2: ", scalar(@{$genes2}), "\n";
    print $fh "Added: ", scalar(@{$comparison->{added}}), "\n";
    print $fh "Removed: ", scalar(@{$comparison->{removed}}), "\n";
    print $fh "Modified: ", scalar(@{$comparison->{modified}}), "\n";
    print $fh "Unchanged: ", $comparison->{unchanged}, "\n";

    close($fh);
}

# --- Main Program ---
my $file1;
my $file2;
my $output = "comparison_report.txt";

GetOptions(
    "file1=s"  => \$file1,
    "file2=s"  => \$file2,
    "output=s" => \$output,
);

die "Usage: $0 --file1 <file1.gen> --file2 <file2.gen> [--output <output.txt>]" unless $file1 && $file2;

my $genes1 = parse_genome_file($file1);
my $genes2 = parse_genome_file($file2);

# print "Genes1:".$genes1."\n";
# print "Genes2:".$genes2."\n";

my $comparison = compare_genomes($genes1, $genes2);
generate_report($comparison, $file1, $file2, $genes1, $genes2, $output);

print "Comparison report generated: $output\n";

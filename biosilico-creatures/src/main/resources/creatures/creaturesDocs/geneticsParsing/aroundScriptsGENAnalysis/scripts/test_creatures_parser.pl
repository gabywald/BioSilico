#!/usr/bin/perl
use strict;
use warnings;
use Test::More;
use File::Basename;

require_ok('./creatures1genomeAnalysis.pl');

my $test_dir = "test_genomes_scripts";
my @valid_files = ("valid_genome1.gen", "valid_genome2.gen", "minimal_genome.gen");
my @corrupted_files = ("corrupted_header.gen", "corrupted_data.gen");
my $empty_file = "empty.gen";

sub test_valid_genomes {
    for my $filename (@valid_files) {
        my $file_path = "$test_dir/$filename";
        my $genes = parse_genome_file($file_path);
        is(ref($genes), 'ARRAY', "$filename: returns an array");
        ok(@$genes > 0, "$filename: has genes");
        for my $gene (@$genes) {
            ok(defined $gene->{type}, "$filename: gene has type");
            ok(defined $gene->{subtype}, "$filename: gene has subtype");
        }
    }
}

sub test_empty_file {
    my $file_path = "$test_dir/$empty_file";
    my $genes = parse_genome_file($file_path);
    is(scalar(@$genes), 0, "$empty_file: no genes parsed");
}

sub test_corrupted_files {
    for my $filename (@corrupted_files) {
        my $file_path = "$test_dir/$filename";
        my $genes = parse_genome_file($file_path);
        is(ref($genes), 'ARRAY', "$filename: returns an array (may be empty)");
        diag(sprintf("%s: parsed %d genes (corrupted)", $filename, scalar(@$genes)));
    }
}

test_valid_genomes();
test_empty_file();
test_corrupted_files();

done_testing();

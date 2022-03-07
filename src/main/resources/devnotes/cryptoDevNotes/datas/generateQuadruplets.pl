#!/usr/bin/perl -w

use strict;

my $code = $ARGV[0];

while (!$code) {
	my %tmpHash = (
		"1"	=> "acgt", 
		"2"	=> "ubvp", 
		"3"	=> "ACGT", 
		"4"	=> "UBVP", 
		"5"	=> "xyzt", 
	);
	print " CHOICE : \n";
	print "==========\n";
	foreach my $key (sort keys %tmpHash) 
		{ print " ".$key.": ".$tmpHash{$key}."  \n"; }
	
	my $data = <STDIN>;
	chomp($data);
	$code = $tmpHash{$data};
	
	## print "\t'".$data."' : '".$code."'\n";
}

my $count = 0;
open(OUTPUT,">"."quad".$code.".txt");
for (my $i = 0 ; $i < length($code) ; $i++) {
	for (my $j = 0 ; $j < length($code) ; $j++) {
		for (my $k = 0 ; $k < length($code) ; $k++) {
			for (my $l = 0 ; $l < length($code) ; $l++) {
				my $uplet = substr($code,$i,1);
				$uplet .= substr($code,$j,1);
				$uplet .= substr($code,$k,1);
				$uplet .= substr($code,$l,1);
				print OUTPUT "\t'".$uplet."'\t'".$count++."'\n";
			}
		}
	}
}
close (OUTPUT);


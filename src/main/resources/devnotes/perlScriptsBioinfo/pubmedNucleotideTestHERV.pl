#!/usr/bin/perl -w

use strict;

## http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=nucleotide&retstart=0&retmax=100&retmode=text&rettype=full&term=herv
system("./pubmedQuerying.pl -d nucleotide -t 1 -a 0 -m 100 -r txt -s herv -y full -f outputTESTS.txt");

my @content = ();
open (INPUT, "<outputTESTS.txt");
while (my $line = <INPUT>) {
	if ($line =~ /<Id>(.*)<\/Id>/) 
		{ push (@content,$1); }
}
close INPUT;

my $i = 0;
foreach my $iden (@content) {
	## https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=nucleotide&id=225543584&retmode=text&rettype=gbwithparts
	print "\t".$iden."\n";
	print "\t\t".system("./pubmedQuerying.pl -d nucleotide -t 0 -i ".$iden." -r text -y gbwithparts -f results".$iden.".txt")."\n"; ## gbwithparts|fasta
	$i++;
	if ($i >= 1) { exit(0); }
}
#!/usr/bin/perl -w

use strict;

## http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&retstart=0&retmax=100&retmode=html&rettype=abstract&term=herv&usehistory=n&cmd=display
system("./pubmedQuerying.pl -d pubmed -t 1 -a 0 -m 100 -r txt -s herv -y full -f outputTESTS.txt");

my @content = ();
open (INPUT, "<outputTESTS.txt");
while (my $line = <INPUT>) {
	if ($line =~ /<Id>(.*)<\/Id>/) 
		{ push (@content,$1); }
}
close INPUT;

foreach my $iden (@content) {
	## https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=18324973&retmode=text&rettype=abstract
	print "\t".$iden."\n";
	print "\t\t".system("./pubmedQuerying.pl -d pubmed -t 0 -i ".$iden." -r text -y abstract -f results".$iden.".txt")."\n";
}
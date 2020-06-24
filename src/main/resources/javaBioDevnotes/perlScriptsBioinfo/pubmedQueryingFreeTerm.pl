#!/usr/bin/perl -w

use strict;

## http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&retstart=0&retmax=100&retmode=html&rettype=abstract&term=herv&usehistory=n&cmd=display
system("./pubmedQuerying.pl -d pubmed -t 1 -a 0 -m 100 -r txt -y full -f outputTESTS.txt");

my @content = ();
open (INPUT, "<outputTESTS.txt");
while (my $line = <INPUT>) {
	if ($line =~ /<Id>(.*)<\/Id>/) 
		{ push (@content,$1); }
}
close INPUT;

foreach my $iden (@content) {
	## https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=18324973&retmode=text&rettype=abstract|citation|medline|xml
	print "\t".$iden."\n";
	print "\t\t".system("./pubmedQuerying.pl -d pubmed -t 0 -i ".$iden." -r text -y citation -f results".$iden.".txt")."\n";
	
	exit(0);
}

## http://rubydoc.info/gems/bio/1.4.1/Bio/NCBI/REST/EFetch/Methods

## https://eutils.ncbi.nlm.nih.gov/entrez/query/static/efetch_help.html

## http://doc.bioperl.org/releases/bioperl-1.5.2/Bio/DB/EUtilities/efetch.html

# uilist (all databases),
# abstract, citation, medline (not omim),
# full (journals and omim)
   # Literature databases have the below return types:
# native (full record, all databases),
# fasta, seqid, acc (nucleotide or protein),
# gb, gbc, gbwithparts (nucleotide only),
# est (dbEST only),
# gss (dbGSS only),
# gp, gpc (protein only),
# chr, flt, rsr, brief, docset (dbSNP only)
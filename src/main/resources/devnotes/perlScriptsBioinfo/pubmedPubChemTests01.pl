#!/usr/bin/perl -w

use strict;

my @chemicalsList = ();

open (CHEMICALS, "<"."chemicals2search.txt");
while (my $line = <CHEMICALS>) 
	{ chomp($line);push(@chemicalsList, $line); }
close CHEMICALS;

if (-d "results") 
	{ rmdir("results"); }
mkdir("results",0777);

## <DbName>pcassay</DbName> 
## <DbName>pccompound</DbName>
## <DbName>pcsubstance</DbName>
foreach my $chemical (@chemicalsList) {
	my $tmp = lc($chemical);
	$tmp =~ tr/éèîï/eeii/;
	$tmp =~ s/ee/e/g;
	$tmp =~ s/ //g;
	print "\t'".$chemical."'\t'".lc($chemical)."'\t'".$tmp."'\n";
	if ($tmp =~ / /) { $tmp = "\"".$tmp."\""; }
	system("./pubmedQuerying.pl -d pccompound -t 1 -a 0 -m 100 -r txt -s ".$tmp." -y sdf -f results/output".lc($tmp).".txt");
}

## exit(0); ## http://www.bioperl.org/wiki/HOWTO:EUtilities_Cookbook

## ==>> 'http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=38409&disopt=SaveSDF'

my @files		= glob("results/output*");
my @content		= ();

foreach my $file (@files) { 
	print "\t'".$file."'\n";
	my $name = $file;
	$name =~ s/^results\/output(.*?)\.txt$/$1/;
	push(@content, $name);
	my $temp = "";
	open (INPUT, "<".$file);
	while (my $line = <INPUT>) {
		if ($line =~ /<Id>(.*)<\/Id>/) 
			{ $temp .= $1.", "; }
	}
	close INPUT;
	## print "\t".length($temp)."\n"; ## my $length = length($temp);
	$temp =~ s/^(.*), $/$1/; ## $temp = substr($temp, 0, ((length($temp) > 0)?(length($temp)-2):0));
	push(@content, $temp);
}

print "\n\n";

open (OUTPUT, ">"."results01.txt");

foreach my $item (@content) { 
	if ($item =~ /[a-z]+/) { print "\t".$item." : \n";print OUTPUT "\t".$item." : \n"; }
	else { print $item."\n\n";print OUTPUT $item."\n\n"; }
}

close OUTPUT;

exit(0);




my $i = 0;
foreach my $iden (@content) {
	## https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pccompound&id=38409&retmode=html&rettype=abstract
	## ftp://ftp.ncbi.nlm.nih.gov/pubchem/Compound/CURRENT-Full/SDF/
	print "\t".$iden."\n";
	print "\t\t".system("./pubmedQuerying.pl -d pccompound -t 0 -i ".$iden." -r text -y sdf -f results".$iden.".txt")."\n";
	$i++;
	if ($i >= 1) { exit(0); }
}
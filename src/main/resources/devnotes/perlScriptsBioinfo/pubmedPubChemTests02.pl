#!/usr/bin/perl -w

use strict;

my @chemicalsList = ();

open (CHEMICALS, "<"."chemicals2searchFollowing.txt");
while (my $line = <CHEMICALS>) 
	{ chomp($line);push(@chemicalsList, $line); }
close CHEMICALS;

if (-d "results02") 
	{ rmdir("results02"); }
mkdir("results02", 0777);

## <DbName>pcassay</DbName> 
## <DbName>pccompound</DbName>
## <DbName>pcsubstance</DbName>
my %currentHash	= ();
my $currentKey	= undef;
foreach my $chemical (@chemicalsList) {
	if ( ($chemical !~ /^\t/) && ($chemical ne "") ) {
		my $tmp = lc($chemical);
		$tmp =~ tr/éèëîï/eeeii/;
		$tmp =~ s/ee/e/g;
		$tmp =~ s/[ -]/_/g;
		print "\t'".$chemical."'\t'".lc($chemical)."'\t'".$tmp."'\n";
		## ## if ($tmp =~ / /) { $tmp = "\"".$tmp."\""; }
		my $fileName = "results02/output".lc($tmp).".txt";
		print "\t*****'".$tmp."'*****\n";
		system("./pubmedQuerying.pl -d pccompound -t 1 -a 0 -m 100 -r txt -s ".$tmp." -y sdf -f ".$fileName);
		
		my $temp = "";
		open (INPUT, "<".$fileName);
		while (my $line = <INPUT>) {
			if ($line =~ /<Id>(.*)<\/Id>/) 
				{ $temp .= $1.", "; }
		}
		close INPUT;
		$temp =~ s/^(.*?), (.*)$/$1/; ## beware : get only first id !!
		if ($temp ne "") { $currentHash{$currentKey} .= $temp.", "; }
		
		print "\t'".$currentKey."'\t'".$temp."'\t'".$currentHash{$currentKey}."'\n";## getc();
		
	} elsif ($chemical ne "") {
		if (defined $currentKey) {
			open (OUTPUT, ">>"."results02tmp.txt"); ## append
			print "\t'".$currentKey."'\t'".$currentHash{$currentKey}."'\n";
			print OUTPUT "\t'".$currentKey."'\t'".$currentHash{$currentKey}."'\n";
			close OUTPUT;
		} else {
			open (OUTPUT, ">"."results02tmp.txt"); ## clear
			print OUTPUT "";
			close OUTPUT;
		}
		$currentKey = $chemical;
		$currentKey =~ s/^\t(.*)$/$1/;
		$currentHash{$currentKey} = "";
	} else { print "\tNEXT GROUP!! \n"; }
}

open (OUTPUT, ">"."results02.txt");
foreach my $key (sort keys %currentHash) {
	print "\t'".$key."'\t'".$currentHash{$key}."'\n";
	print OUTPUT "\t'".$key."'\t'".$currentHash{$key}."'\n";
}
close OUTPUT;

exit(0);
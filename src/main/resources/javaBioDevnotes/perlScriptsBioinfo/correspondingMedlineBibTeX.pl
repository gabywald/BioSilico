#!/usr/bin/perl -w

use strict;

my @lines = ();
	open (CORRESPOND, "<"."correspondancesModelineBibTeX.csv");
	while (my $line = <CORRESPOND>) 
		{ chomp($line);push(@lines, $line); }
	close CORRESPOND;
	
my %hash2build = ();
	foreach my $line (@lines) {
		if ($line =~ /^(.*?);(.*?);(.*?);(.*?);;(.*?)$/ ) {
			## print "\t1-'".$1."'\t2-'".$2."'\t3-'".$3."'\t4-'".$4."'\t5-'".$5."'\n";
			my $medlineDenomin	= $1;
			my $medlineCode		= $3;
			my $bibTeXcode		= $4;
			if ( ($medlineDenomin =~ /#/) && ($bibTeXcode ne "") ) {
				$hash2build{$medlineCode} = $bibTeXcode;
				## print "\t'".$medlineCode."' => '".$bibTeXcode."'\n";
			}
		}
	}
	
open (OUTPUT, ">"."correspondingHash.txt");
	print OUTPUT "my %hashCorresponding = (\n";
	foreach my $key (sort keys %hash2build) {
		print OUTPUT "\t'".$key."'\t=>\t'".$hash2build{$key}."', \n";
	}
	print OUTPUT ");\n";
close OUTPUT;
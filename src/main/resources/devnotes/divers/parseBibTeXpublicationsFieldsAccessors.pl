#!/usr/bin/perl -w

use strict;

my @content		= ();
my $exitTest	= 0;
open (INPUT, "<"."publicationsTypesBibTeX.txt");
while (my $line = <INPUT>) {
	if ($line =~ /=/) { $exitTest = 1; }
	if ( ($exitTest == 1) 
			&& ( ($line !~ /^[0-9]+/) 
				&& ($line =~ /[a-z]+/) ) ) {
		chomp($line);
		## print "\t'".$line."'\n";
		push(@content, $line);
	}
}
close INPUT;

open (OUTPUT, ">"."bibTeXparsingFields.txt");
print OUTPUT "";
close OUTPUT;

my @booleansForFields = ();

foreach my $line (@content) {
	print "\t'".$line."'\n";
	if ($line =~ /^\s+(.*?):(.*)$/)	{
		my $accessToData = $1;
		
		open (OUTPUT, ">>"."bibTeXparsingFields.txt");
		print OUTPUT "\tpublic String get".ucfirst($accessToData)
						."() { return this.content.get(\"".$accessToData."\"); }\n";
		close OUTPUT;
	}
}

open (OUTPUT, ">>"."bibTeXparsingFields.txt");
print OUTPUT "\n\n";
close OUTPUT;

foreach my $line (@content) {
	print "\t'".$line."'\n";
	if ($line =~ /^\s+(.*?):(.*)$/)	{
		my $accessToData = $1;
		
		open (OUTPUT, ">>"."bibTeXparsingFields.txt");
		print OUTPUT "\tpublic void set".ucfirst($accessToData)
						."(String ".$accessToData.")\n\t\t{ this.content.put(\""
							.$accessToData."\", ".$accessToData."); } \n";
		close OUTPUT;
	}
}


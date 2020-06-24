#!/usr/bin/perl -w

use strict;

my @data = (
		"Comment on	(CON)	CON	", 
		"Comment in	(CIN)	CIN	", 
		"Erratum in	(EIN)	EIN	", 
		"Erratum for	(EFR)	EFR	", 
		"Corrected and Republished in	(CRI)	CRI	", 
		"Corrected and Republished from	(CRF)	CRF	", 
		"Partial retraction in	(PRIN)	PRIN	", 
		"Partial retraction of	(PROF)	PROF	", 
		"Republished in	(RPI)	RPI	", 
		"Republished from	(RPF)	RPF	", 
		"Retraction in	(RIN)	RIN	", 
		"Retraction of	(ROF)	ROF	", 
		"Update in	(UIN)	UIN	", 
		"Update of	(UOF)	UOF	", 
		"Summary for patients in	(SPIN)	SPIN	", 
		"Original report in	(ORI)	ORI	"
);

my $commentsGetters = "";
my $commentsSetters = "";

foreach my $elt (@data) {
	if ($elt =~ /^([A-Za-z ]+)\t(.*?)\t(.*?)\t$/) {
		my $name	= $1;
		my $first	= $2;
		my $second	= $3;
		
		while ($name =~ / (.)/) {
			my $rep = uc($1);
			$name =~ s/ (.)/$rep/;
		}
		
		$commentsGetters .= "\tpublic String get".$name."()\t\t{ return this.content.get(\"".$second."\"); }\n";
		
		$commentsSetters .= "\tpublic String set".$name."(String ".lc($second).") \n".
				"\t\t{ return this.content.put(\"".$second."\", ".lc($second)."); }\n";
	}
}

open (OUTPUT, ">"."commentsAccessors.txt");

print OUTPUT $commentsGetters."\n\n".$commentsSetters;

close OUTPUT;


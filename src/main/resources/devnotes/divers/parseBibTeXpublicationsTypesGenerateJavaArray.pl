#!/usr/bin/perl -w

use strict;

my @content		= ();
my $exitTest	= 0;
open (INPUT, "<"."publicationsTypesBibTeX.txt");
while (my $line = <INPUT>) {
	if ($line =~ /=/) { $exitTest = 1; }
	if ( ($exitTest == 0) 
		&& ( ($line !~ /^[0-9]+/) 
			&& ($line =~ /[a-z]+/) ) ) {
		chomp($line);
		## print "\t'".$line."'\n";
		push(@content, $line);
	}
}
close INPUT;

open (OUTPUT, ">"."bibTeXparsingArray.txt");
print OUTPUT "";
close OUTPUT;

my @booleansForFields = ();

my $name		= undef;
my $descr		= undef;
my $mandatory	= undef;
my $optionnal	= undef;

foreach my $line (@content) {
	print "\t'".$line."'\n";
	if ($line =~ /^([a-z]+)/)	{
		if (defined $name) {
			my @booleans	= ();
			my $fields = "";
			if ($mandatory ne "none") { 
				$fields = $mandatory.", ";
				my @tabOfFields = split(", ", $mandatory);
				foreach my $elt (@tabOfFields) 
					{ push (@booleans, "true"); }
			}
			
			$fields .= $optionnal;
			my @tabOfFields = split(", ", $optionnal);
			foreach my $elt (@tabOfFields) 
				{ push (@booleans, "false"); }
				
			push (@booleansForFields, \@booleans);
			
			open (OUTPUT, ">>"."bibTeXparsingArray.txt");
			print OUTPUT "\t\t{ \"".$name."\", \"".$descr."\", \n\t\t\t\"".$fields."\" }, \n";
			close OUTPUT;
		}
		$name = $1;
	}
	elsif ($line =~ /^    Required fields: (.*)$/) 
		{ $mandatory = $1; }
	elsif ($line =~ /^    Optional fields: (.*)$/) 
		{ $optionnal = $1; }
	else {
		$descr = $line;
		$descr =~ s/^\s+|\s+$//g; ## remove leading / trailing whitespaces...
	}
}


open (OUTPUT, ">>"."bibTeXparsingArray.txt");
print OUTPUT "\n\n";
foreach my $booleans (@booleansForFields) {
	print OUTPUT "\t\t{ ";
	my $content = "";
	my @currentboleans = @{$booleans};
	foreach my $bool (@currentboleans) {
		$content .= $bool.", ";
	}
	print OUTPUT substr($content, 0, length($content)-2)." }, \n";
}
close OUTPUT;


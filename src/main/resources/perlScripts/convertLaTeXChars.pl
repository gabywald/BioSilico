#!/usr/bin/perl -w

use strict;

my $file = $ARGV[0];
if ($ARGV[0] eq '-R') { $file = $ARGV[1]; }

my @lines = ();
open (FILE,"<".$file);
while (my $line = <FILE>) { push (@lines,$line); }
close (FILE);

open (FILE,">".$file);
if ($ARGV[0] eq '-R') { 
	foreach my $line (@lines) {
		$line =~ s/\{\\^e\}/ê/g;
		$line =~ s/\{\\'e\}/é/g;
		$line =~ s/\{\\`e\}/è/g;
		$line =~ s/\{\\`a\}/à/g;
		$line =~ s/\{\\^a\}/â/g;
		$line =~ s/\{\\^u\}/û/g;
		$line =~ s/\{\\`u\}/ù/g;
		$line =~ s/\{\\^o\}/ô/g;
		$line =~ s/\{\\^i\}/î/g;
		$line =~ s/\\c\{c\}/ç/g;
		$line =~ s/{\\"a\}/ä/g;
		$line =~ s/{\\"e\}/ë/g;
		$line =~ s/{\\"i\}/ï/g;
		$line =~ s/{\\"o\}/ö/g;
		$line =~ s/{\\"u\}/ü/g;
		
		$line =~ s/\{\\^E\}/Ê/g;
		$line =~ s/\{\\'E\}/É/g;
		$line =~ s/\{\\`E\}/È/g;
		$line =~ s/\{\\`A\}/À/g;
		$line =~ s/\{\\^A\}/Â/g;
		$line =~ s/\{\\^U\}/Û/g;
		$line =~ s/\{\\`U\}/Ù/g;
		$line =~ s/\{\\^O\}/Ô/g;
		$line =~ s/\\c\{C\}/Ç/g;
		print FILE $line;
	}
} else {
	foreach my $line (@lines) {
		$line =~ s/ê/\{\\^e\}/g;
		$line =~ s/é/\{\\'e\}/g;
		$line =~ s/è/\{\\`e\}/g;
		$line =~ s/à/\{\\`a\}/g;
		$line =~ s/â/\{\\^a\}/g;
		$line =~ s/û/\{\\^u\}/g;
		$line =~ s/ù/\{\\`u\}/g;
		$line =~ s/ô/\{\\^o\}/g;
		$line =~ s/î/\{\\^i\}/g;
		$line =~ s/ç/\\c\{c\}/g;
		$line =~ s/ä/{\\"a\}/g;
		$line =~ s/ë/{\\"e\}/g;
		$line =~ s/ï/{\\"i\}/g;
		$line =~ s/ö/{\\"o\}/g;
		$line =~ s/ü/{\\"u\}/g;
		
		$line =~ s/Ê/\{\\^E\}/g;
		$line =~ s/É/\{\\'E\}/g;
		$line =~ s/È/\{\\`E\}/g;
		$line =~ s/À/\{\\`A\}/g;
		$line =~ s/Â/\{\\^A\}/g;
		$line =~ s/Û/\{\\^U\}/g;
		$line =~ s/Ù/\{\\`U\}/g;
		$line =~ s/Ô/\{\\^O\}/g;
		$line =~ s/Ç/\\c\{C\}/g;
		print FILE $line;
	}
}
close (FILE);

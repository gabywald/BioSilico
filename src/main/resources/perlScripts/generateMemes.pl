#!/usr/bin/perl -w

use strict;

use Switch;

## $| = 1;

system('clear'); ## http://fatiha.canalblog.com/archives/2007/11/26/7023781.html
print getMeme();


sub getMeme {
	my @consonnes	= getConsonnes();
	my @voyelles	= getVoyelles();
	my @separators	= getSeparators();
	my $count = int(rand(42*42));
	my $output = "";
	for (my $i = 0 ; $i < $count ; $i++) {
		my $word = (int(rand(2)) == 0)
						?$voyelles[int(rand(@voyelles))].$consonnes[int(rand(@consonnes))]
						:$consonnes[int(rand(@consonnes))].$voyelles[int(rand(@voyelles))];
		$output .= $word.$separators[int(rand(@separators))];
	}
	
	
	return $output;
}

sub getConsonnes {
	my @consonnes = ('b','c','d','f','g',
					 'h','j','k','l','m',
					 'n','p','q','r','s',
					 't','v','w','x','z');
	return @consonnes;
}

sub getVoyelles {
	my @voyelles = ('a','e','i','o','u','y');
	return @voyelles;
}

sub getSeparators {
	my @separators = (' ','-',' ; ','. ',
					  ', ','? ',': ','! ',
					  '_','+','*','/','\\');
	return @separators;
}
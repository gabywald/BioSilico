#!/usr/bin/perl -w

use strict;

my $alphabet	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
my $numbers		= "0123456789";
my $listOfBases	= "acgt";
my $listOfAAs	= "ACDEFGHIKLMNPQRSTVWY";
	

my $directory	= "geneticsRepository";

if (-d $directory) {
	print "\t[".$directory."] exists !\n";
	chdir($directory);
	my @files = glob("*");
	print "\t\t[".@files."] files to remove...\n";
	my $i = 0;
	foreach my $elt (@files) { 
		print "\t\tunlinking [".$directory."/".$elt."]...\n";
		unlink($elt); 
		$i++;
		if ($i%10 == 0) { print "\t\t(".$i.") removed files...\n"; }
	}
	chdir ("..");
	print "\tdeleting [".$directory."]...\n";
	rmdir($directory);
	print "\n\n";
}

mkdir($directory);

chdir($directory);

my $numberOfFilesToCreate = int(rand(1000000)); ## !!
print "\t\t will create ".$numberOfFilesToCreate." files !!\n";
for (my $i = 0 ; $i < $numberOfFilesToCreate ; $i++) {
	my $fileName = substr($alphabet, rand(length($alphabet)), 1);
	for (my $j = 0 ; $j < 10 ; $j++) 
		{ $fileName .= substr($numbers, rand(length($numbers)), 1); }
	$fileName .= "-".substr($numbers, rand(length($numbers)), 1).".fasta";
	
	print "\t\tcreating file [".$fileName."]\n";
	
	open (OUTPUTFILE, ">".$fileName);
	
	print OUTPUTFILE "> unknown sequence -- ".$fileName." -- \n";
	
	my $baseOrAA	= int(rand(5000));
	my $whatToUse	= ($baseOrAA%5 == 0)?$listOfAAs:$listOfBases;
	
	my $numberOfLines = int(rand(5000000)); ## !!
	print "\t\t\t will have ".$numberOfLines." lines !!\n";
	for (my $j = 0 ; $j < $numberOfLines ; $j++) {
		my $newLine = "";
		for (my $k = 0 ; $k < 80 ; $k++) 
			{ $newLine .= substr($whatToUse, rand(length($whatToUse)), 1); }
		print OUTPUTFILE $newLine."\n";
		if ( ($j > 0) && ($j%10000 == 0) ) { print "\t\t[".$j."] lines created !\n"; }
	}
	
	close OUTPUTFILE;
}



chdir("..");
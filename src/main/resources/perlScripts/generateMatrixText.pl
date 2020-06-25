#!/usr/bin/perl -w

use strict;

use Switch;

## $| = 1;

system('clear'); ## http://fatiha.canalblog.com/archives/2007/11/26/7023781.html
print getMatrixAssertion();


sub getMatrixAssertion {
	my $randomAssert = int(rand(10));
	my $assertion = "...";
	my @assertionsList= ("...");
	push (@assertionsList,"Toc, toc, toc...");
	push (@assertionsList,"Follow the white rabbit !");
	push (@assertionsList,"You got a rendezvous with Trinity...");
	push (@assertionsList,"Hello Neo, how are you today ?");
	push (@assertionsList,"The Matrix has you. ");
	push (@assertionsList,"Red pill or blue pill ?");
	push (@assertionsList,"Hack the planet !");
	push (@assertionsList,"Ready to bounce ?");
	push (@assertionsList,"What is the real world ?");
	push (@assertionsList,"What is the Matrix ?");
	push (@assertionsList,"Tomorrow will be a welcome party to Zion. ");
	push (@assertionsList,"Ready to go through the Rabbit Hole ?");
	push (@assertionsList,getOctets());
	push (@assertionsList,getOctet());
	push (@assertionsList,"Meet Morpheus...");
	## push (@assertionsList,"Hi ".$ENV{USER}." on ".$ENV{SHELL}." ! ");
	push (@assertionsList,"Cypher is a traitor !");
	push (@assertionsList,"Pretty Switch ;-p !");
	push (@assertionsList,"Niobe on the rescue !");
	push (@assertionsList,"Calling the Wizard of Oz, over the Rainbow... wait !");
	push (@assertionsList,"Do NOT be in disturbance with the Merovingian, just get in trouble with his wife...");
	
	$assertion = $assertionsList[int(rand(@assertionsList))];
	
	## my $num = rand(@assertionsList);
	## my $tabs = "";
	## my $randomTabs = int(rand($num));
	## for (my $i = 0 ; $i < $randomTabs ; $i++){ $tabs .= "\t"; }
	## my $pastMemories = "";
	## my $numBlankLines = int(rand(42));
	## for (my $i = 0 ; $i < $numBlankLines ; $i++) {
		## my $nextTabs = "\n";
		## my $randomTabs = int(rand($num));
		## for (my $i = 0 ; $i < $randomTabs ; $i++) { $nextTabs .= "\t"; }
		## $pastMemories .= $nextTabs.".\n";
	## }	
	return $assertion; ## $tabs.$assertion.$pastMemories;
}

sub getOctet {
	my $octet = "";
	for (my $i = 0 ; $i < 8 ; $i++) { $octet .= int(rand(2)); }
	return $octet;
}

sub getOctets {
	my $octets = "";
	my $num = int(rand(42));
	for (my $i = 0 ; $i < $num ; $i++) { $octets .= getOctet(); }
	return $octets;
}
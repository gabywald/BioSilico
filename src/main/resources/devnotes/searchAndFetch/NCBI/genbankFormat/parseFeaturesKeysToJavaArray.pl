#!/usr/bin/perl -w

use strict;

use FeatureKey qw($separator);

my $featuresKeysDefinitions	= "featuresKeysDefinitions.txt";
my $outputFileJava			= "featuresKeyJavaInstanciation.txt";


#my %currentFeatureHash		= (
#	'Feature Key'			=> undef, ## one line
#	'Parent Key'			=> undef, ## one line
#	'Definition'			=> undef, ## flag = 1
#	'Organism scope'		=> undef, ## one line
#	'Molecule scope'		=> undef, ## one line
#	'References'			=> undef, ## flag = 2
#	'Comment'				=> undef, ## flag = 3
#	'Mandatory qualifiers'	=> (),    ## flag = 4
#	'Optional qualifiers'	=> (),    ## flag = 5
#	
#);

my $feature 	= undef;
my $flag		= 0;
## my $separator	= $FeatureKey::separator;

open (OUTPUT, ">".$outputFileJava);
print OUTPUT "";
close OUTPUT;


#	public static FeatureDefinition getFromFactory(String nameOfFD) {
#		if (nameOfFD.equals("")) 
#			{ return FeatureDefinition.get10signalFD(); }
#		return null;
#	}
my $getFromFactoryTXT = "\tpublic static FeatureDefinition getFromFactory(String nameOfFD) {\n";

open (FEATURESKEYS, "<".$featuresKeysDefinitions);

while(my $line = <FEATURESKEYS>) {
	chomp($line);
	if ( ($line eq "") || ($line =~ /^$/) ) { $flag = 0; }
	if ($flag != 0) {
		if ( ($flag == 1) && ($line =~ /^\s+(.*)$/) ) 
			{ $feature->addToDefinition($1); }
		if ( ($flag == 2) && ($line =~ /^\s+(.*)$/) ) 
			{ $feature->addToReferences($1); }
		if ( ($flag == 3) && ($line =~ /^\s+(.*)$/) ) 
			{ $feature->addToComment($1); }
		if ( ($flag == 4) && ($line =~ /^\s+\/(.*?)=(.*)$/) ) 
			{ $feature->addMandatoryQualifier($1.$separator.$2); }
		if ( ($flag == 5) && ($line =~ /^\s+\/(.*?)=(.*)$/) ) 
			{ $feature->addOptionnalQualifier($1.$separator.$2); }
	}
	elsif ($line =~ /^Feature Key\s+(.*?)$/)					{
		if (defined $feature) { 
			print $feature->toString()."\n*****\n";
			## print $feature->toJavaStaticMultiTon()."\n=====\n";
			open (OUTPUT, ">>".$outputFileJava);
			print OUTPUT $feature->toJavaStaticMultiTon()."\n";
			close OUTPUT;
			
			$getFromFactoryTXT.= "\t\tif (nameOfFD.equals(\"".$feature->getFeatureKey()."\"))\n"
								."\t\t\t{ return FeatureDefinition.get"
								.FeatureKey::_stringMethodJava($feature->getFeatureKey())
								."FD(); }\n";
			
			## getc();
		}
		my $keyName = $1;
		$feature = FeatureKey->new($keyName);
		## $feature->setFeatureKey($keyName);
	}
	elsif ($line =~ /^Parent Key\s+(.*)$/)			
		{ $feature->setParentKey($1); }
	elsif ($line =~ /^Definition\s+(.*)$/)						{
		$feature->setDefinition($1);
		$flag = 1;
	}
	elsif ($line =~ /^Organism scope\s+(.*)$/)			
		{ $feature->setOrganismScope($1); }
	elsif ($line =~ /^Molecule scope\s+(.*)$/)			
		{ $feature->setMoleculeScope($1); }
	elsif ($line =~ /^References\s+(.*)$/)						{
		$feature->setReferences($1);
		$flag = 2;
	}
	elsif ($line =~ /^Comment\s+(.*)$/)						{
		$feature->setComment($1);
		$flag = 3;
	}
	elsif ($line =~ /^Mandatory qualifiers\s+\/(.*?)=(.*)$/)	{
		$feature->addMandatoryQualifier($1.$separator.$2);
		$flag = 4;
	}
	elsif ($line =~ /^Optional qualifiers\s+\/(.*?)=(.*)$/)	{
		$feature->addOptionnalQualifier($1.$separator.$2);
		$flag = 5;
	}
	## else { print "!!!!! '".$line."' !!!!! ".(defined $line)."\n";getc(); }
}

close FEATURESKEYS;

$getFromFactoryTXT .= "\t\treturn null;\n\t}\n";

			open (OUTPUT, ">>".$outputFileJava);
			print OUTPUT $getFromFactoryTXT;
			close OUTPUT;

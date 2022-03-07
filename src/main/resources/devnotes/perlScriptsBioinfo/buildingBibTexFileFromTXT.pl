#!/usr/bin/perl -w

use strict;
use Cwd;

my $baseDir		= cwd();
my $directory	= $ARGV[0];
chdir($directory);
my @files		= glob("*");
chdir($baseDir);

foreach my $entryFile (@files) {
	if ($entryFile !~ /results[0-9]+.txt/) { next; }
	## print $directory.$entryFile."\n";getc();
	
	open (INPUTFILE, "<".$directory.$entryFile);
	my @lines = ();
	while (my $line = <INPUTFILE>) 
		{ chomp($line);push(@lines, $line); }
	close INPUTFILE;
	
	## print "\t".@lines." lines !\n";
	
	my $journal	= undef;
	my $year	= undef;
	my $month	= undef;
	my $volume	= undef;
	my $number	= undef;
	my $pages	= undef;
	my $authors	= undef;
	
	my $cumulAuthors = undef;
	
	for (my $i = 0 ; $i < @lines ; $i++) {
		my $currentLine = $lines[$i];
		## print "\t".$i." : '".$currentLine."'";getc();
		
		## ## '1: Bioorg Med Chem. 2006 May 1;14(9):3153-9. Epub 2006 Jan 19. '
		## ## '1: Curr Drug Metab. 2010 May;11(4):379-406. '
		## ## '1: Bioorg Med Chem. 2008 Nov 15;16(22):9684-93. Epub 2008 Oct 5. '
		if ($currentLine =~ /^1: (.*?)\. ([0-9]{4}) (([A-Z][a-z]{2}\-?)+)( [0-9]+)?;([0-9]+)(\([0-9]+\))?:([0-9\-]*)\..*?$/) {
			
			print "\t::\t1-".$1."\t2-".$2."\t3-".$3."\t4-".$4.((defined $5)?"\t5-".$5:"")."\t6-".$6."\t7-".$7."\t8-".$8."\n";
			
			$journal	= $1;
			$year		= $2;
			$month		= $3;
			$volume		= $6;
			
			if (defined $8) {
				$number		= $7;
				$pages		= $8;
				$number		=~ s/\((.*)\)/$1/;
			} else { $pages	= $7; }
		}
			
		if ( ($currentLine =~ /^([A-Z][a-z]+ [A-Z]+, )*([A-Z][a-z]+ [A-Z]+.)$/) && (!defined $authors) ) {
			## print "\t##".$currentLine."##";getc();
			$authors = "";
			my @tmpAuthors = split(", ", $currentLine);
			foreach my $author (@tmpAuthors) {
				my @theOne = split(" ", $author);
				$authors .= $theOne[0].", ".$theOne[1];
				## print "\t".$author."\t".$tmpAuthors[$#tmpAuthors]."\n";
				if ($author ne $tmpAuthors[$#tmpAuthors])
					{ $authors .= " and "; }
				else { $authors = substr($authors, 0 , length($authors)-1 ); } 
			}
		} elsif ( ($currentLine =~ /^([A-Z][a-z]+ [A-Z]+, )*$/) && (!defined $authors) ) {
			$cumulAuthors = "";
			
			print "\t\"".$currentLine."\"\n";
			
			do {
				$cumulAuthors	.= $currentLine;
				$i++;
				$currentLine	= $lines[$i];
			} while ($currentLine ne "");
			## RE DO IT !!
			if ( ($cumulAuthors =~ /^([A-Z][a-z]+ [A-Z]+, )*([A-Z][a-z]+ [A-Z]+.)$/) && (!defined $authors) ) {
				## print "\t##".$currentLine."##";getc();
				$authors = "";
				my @tmpAuthors = split(", ", $cumulAuthors);
				foreach my $author (@tmpAuthors) {
					my @theOne = split(" ", $author);
					$authors .= $theOne[0].", ".$theOne[1];
					## print "\t".$author."\t".$tmpAuthors[$#tmpAuthors]."\n";
					if ($author ne $tmpAuthors[$#tmpAuthors])
						{ $authors .= " and "; }
					else { $authors = substr($authors, 0 , length($authors)-1 ); } 
				}
			} 
		}
	}
	
	if ( (!defined $journal) || (!defined $authors)) { print "===>> ".$entryFile." <====\n"; }
	else { print "\t'".$journal."'\t'".$year."'\t'".$month."'\t'".$volume."'\t'".$number."'\t'".$pages."'\t'".$authors."'\n"; }
	print "\n\n";
}

# http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=16426850&retmode=text&rettype=abstract
# 
# 
# 1: Bioorg Med Chem. 2006 May 1;14(9):3153-9. Epub 2006 Jan 19. 
# 
# Prediction of hERG potassium channel affinity by the CODESSA approach.
# 
# Coi A, Massarelli I, Murgia L, Saraceno M, Calderone V, Bianucci AM.
# 
# Dipartimento di Scienze Farmaceutiche, Universita di Pisa, Via Bonanno 6, 56126
# Pisa, Italy.
# 
# The problem of predicting torsadogenic cardiotoxicity of drugs is afforded in
# this work. QSAR studies on a series of molecules, acting as hERG K+ channel
# blockers, were carried out for this purpose by using the CODESSA program.
# Molecules belonging to the analyzed dataset are characterized by different
# therapeutic targets and by high molecular diversity. The predictive power of the
# obtained models was estimated by means of rigorous validation criteria implying
# the use of highly diagnostic statistical parameters on the test set, other than
# the training set. Validation results obtained for a blind set, disjoined from
# the whole dataset initially considered, confirmed the predictive potency of the
# models proposed here, so suggesting that they are worth to be considered as a
# valuable tool for practical applications in predicting the blockade of hERG K+
# channels.
# 
# Publication Types:
    # Research Support, Non-U.S. Gov't
# 
# PMID: 16426850 [PubMed - indexed for MEDLINE]
# 
# 
	# http://www.ncbi.nlm.nih.gov/pubmed/16426850

# @ARTICLE{DS2008,
  # author = {Chekmarev, DS and Kholodovych, V and Balakin, KV and  Ivanenkov,
	# Y and Ekins, S and Welsh, WJ},
  # title = {Shape signatures: new descriptors for predicting cardiotoxicity in
	# silico.},
  # journal = {Chem Res Toxicol},
  # year = {2008},
  # volume = {21},
  # pages = {1304-4314},
  # number = {6},
  # month = {June},
  # abstract = {Shape Signatures is a new computational tool that is being evaluated
	# for applications in computational toxicology and drug discovery.
	# The method employs a customized ray-tracing algorithm to explore
	# the volume enclosed by the surface of a molecule and then uses the
	# output to construct compact histograms (i.e., signatures) that encode
	# for molecular shape and polarity. In the present study, we extend
	# the application of the Shape Signatures methodology to the domain
	# of computational models for cardiotoxicity. The Shape Signatures
	# method is used to generate molecular descriptors that are then utilized
	# with widely used classification techniques such as k nearest neighbors
	# ( k-NN), support vector machines (SVM), and Kohonen self-organizing
	# maps (SOM). The performances of these approaches were assessed by
	# applying them to a data set of compounds with varying affinity toward
	# the 5-HT(2B) receptor as well as a set of human ether-a-go-go-related
	# gene (hERG) potassium channel inhibitors. Our classification models
	# for 5-HT(2B) represented the first attempt at global computational
	# models for this receptor and exhibited average accuracies in the
	# range of 73-83%. This level of performance is comparable to using
	# commercially available molecular descriptors. The overall accuracy
	# of the hERG Shape Signatures-SVM models was 69-73%, in line with
	# other computational models published to date. Our data indicate that
	# Shape Signatures descriptors can be used with SVM and Kohonen SOM
	# and perform better in classification problems related to the analysis
	# of highly clustered and heterogeneous property spaces. Such models
	# may have utility for predicting the potential for cardiotoxicity
	# in drug discovery mediated by the 5-HT(2B) receptor and hERG.},
  # owner = {ChandesrisG},
  # timestamp = {2011.06.21},
  # url = {http://www.ncbi.nlm.nih.gov/pubmed/18461975}
# }

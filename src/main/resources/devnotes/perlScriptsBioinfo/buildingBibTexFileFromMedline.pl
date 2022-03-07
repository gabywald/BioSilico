#!/usr/bin/perl -w

use strict;
use Cwd;

my $baseDir		= cwd();
my $directory	= $ARGV[0];
chdir($directory);
my @files		= glob("*");
chdir($baseDir);

	my $outputFile = $directory;
	$outputFile =~ s/\///g;
	$outputFile .= ".bib";
	open (OUTPUT, ">".$outputFile);
	print OUTPUT "% This file was created with a script.\n";
	print OUTPUT "% Encoding: UTF8\n\n";
	close OUTPUT;

	my %hashCorresponding = (
		## 'AB'	=>	'abstract', 
		## 'AU'	=>	'author', 
		'BTI'	=>	'booktitle', 
		'DEP'	=>	'eprint', 
		'ED FED'	=>	'editor', 
		'EN'	=>	'edition', 
		'GN'	=>	'note', 
		'IP'	=>	'number', 
		'JT'	=>	'journal', 
		'PG'	=>	'pages', 
		'PMID'	=>	'id', 
		## 'PT'	=>	'howpublished', 
		'TA'	=>	'journal', 
		## 'TI'	=>	'title', 
		'VI'	=>	'volume', 
		## 'MH'	=>	'keywords',
	);
	
	my %hashMonthes = (
		'01'	=> 'January',
		'02'	=> 'February',
		'03'	=> 'March',
		'04'	=> 'April',
		'05'	=> 'May',
		'06'	=> 'June',
		'07'	=> 'July',
		'08'	=> 'August',
		'09'	=> 'September',
		'10'	=> 'October',
		'11'	=> 'November',
		'12'	=> 'December',
	);

foreach my $entryFile (@files) {
	if ($entryFile !~ /results[0-9]+.txt/) { next; }
	## print $directory.$entryFile."\n";getc();
	
	my @lines = ();
	open (INPUTFILE, "<".$directory.$entryFile);
	while (my $line = <INPUTFILE>) 
		{ chomp($line);push(@lines, $line); }
	close INPUTFILE;
	
	## print "\t".@lines." lines !\n";

	my %hashContent = (
		'address'		=> undef,
		'abstract'		=> undef,
		'annote'		=> undef,
		'author'		=> undef,
		'booktitle'		=> undef,
		'chapter'		=> undef,
		'crossref'		=> undef,
		'edition'		=> undef,
		'editor'		=> undef,
		'eprint'		=> undef,
		'howpublished'	=> undef,
		'institution'	=> undef,
		'journal'		=> undef,
		'key'			=> undef,
		'month'			=> undef,
		'note'			=> undef,
		'number'		=> undef,
		'organization'	=> undef,
		'pages'			=> undef,
		'publisher'		=> undef,
		'school'		=> undef,
		'series'		=> undef,
		'title'			=> undef,
		'type'			=> undef,
		'url'			=> undef,
		'volume'		=> undef,
		'year'			=> undef,
		'keywords'		=> undef,
	);

	my $abstractFlag	= 0;
	my $titleFlag		= 0;
	
	my $cumulAuthors	= undef;
	
	for (my $i = 0 ; $i < @lines ; $i++) {
		my $currentLine		= $lines[$i];
		## print "\t".$i." : '".$currentLine."'";getc();
		
		foreach my $key (sort keys %hashCorresponding) {
			my $toDetect = $key;
			if ($toDetect =~ / /) { 
				$toDetect =~ s/ /|/;
				$toDetect = "(".$toDetect.")";
			}
			## print "\t'".$key."'\t'".$toDetect."'\n";
			if ($currentLine =~ /^$toDetect\s*\- (.*)$/) {
				my $content = $1;
				$hashContent{$hashCorresponding{$key}} = $content;
				## if (!defined $content) 
				## 	{ print "\t!! '".$toDetect."' empty !!\n"; } 
				## else { print "\t\t'".$content."'\n"; }
			}
		}
		
		if ($currentLine =~ /^MH  \- (.*)$/) {
			if (!defined $hashContent{keywords}) 
				{ $hashContent{keywords} = ""; }
			$hashContent{keywords}	.= $1.", ";
		}
		
		if ($currentLine =~ /^PT  \- (.*)$/) {
			if (!defined $hashContent{howpublished}) 
				{ $hashContent{howpublished} = ""; }
			$hashContent{howpublished}	.= $1." -- ";
		}
		
		if ($currentLine =~ /^AU  \- (.*)$/) {
			my $author = $1;
			$author =~ s/(\w.+) ([A-Z]+)/$1, $2/;
			if (!defined $hashContent{author}) 
				{ $hashContent{author} = ""; }
			$hashContent{author}	.= $author." and ";
		}
		
		if ($currentLine =~ /^DA  \- (.*)$/) {
			my $date			= $1;
			$hashContent{year}	= $date;
			$hashContent{month}	= $date;
			$hashContent{year}	=~ s/([0-9]{4})([0-9]{2})([0-9]{2})/$1/;
			$hashContent{month}	=~ s/([0-9]{4})([0-9]{2})([0-9]{2})/$hashMonthes{$2}/;
		}
		
		if ($currentLine =~ /^AB  \- (.*)$/) {
			$abstractFlag			= 1;
			$hashContent{abstract}	= $1;
		} 
		elsif ( ($currentLine =~ /^      (.*)$/) 
				&& ($abstractFlag == 1) ) 
			{ $hashContent{abstract} .= " ".$1; }
		else { $abstractFlag		= 0; }
		
		if ($currentLine =~ /^TI  \- (.*)$/) {
			$titleFlag			= 1;
			$hashContent{title}	= $1;
		} 
		elsif ( ($currentLine =~ /^      (.*)$/) 
				&& ($titleFlag == 1) ) 
			{ $hashContent{title} .= " ".$1; }
		else { $titleFlag		= 0; }
		
	} ## END for (my $i		= 0 ; $i < @lines ; $i++)
	
	print "\n\n";
	
	if (defined $hashContent{keywords}) { 
		## print "\t keywords !! ".(length($hashContent{keywords})-2)."\n";
	 	$hashContent{keywords} = substr($hashContent{keywords}, 0, (length($hashContent{keywords})-2) ); 
	}
	if (defined $hashContent{howpublished}) 
		{ $hashContent{howpublished} = 
			substr($hashContent{howpublished}, 
				0, (length($hashContent{howpublished})-4) );  }
	if (defined $hashContent{author}) 
		{ $hashContent{author} = 
			substr($hashContent{author}, 
				0, (length($hashContent{author})-5) );  }
	## print "\n\n";
	
	foreach my $key (sort keys %hashContent) { 
		if (defined $hashContent{$key}) 
			{ print "\t'".$key."'\t=>\t'".$hashContent{$key}."'\n"; }
	}
	print "\n\n"; ## getc();
	
	my $outputFile = $directory;
	$outputFile =~ s/\///g;
	$outputFile .= ".bib";
	open (OUTPUT, ">>".$outputFile);
	print OUTPUT "\@ARTICLE{".$hashContent{id}.", \n";
	print OUTPUT "\tauthor\t= {".$hashContent{author}."}, \n";
	print OUTPUT "\ttitle\t= {".$hashContent{title}."}, \n";
	print OUTPUT "\tjournal\t= {".$hashContent{journal}."}, \n";
	print OUTPUT "\tyear\t= {".$hashContent{year}."}, \n";
	print OUTPUT "\tmonth\t= {".$hashContent{month}."}, \n";
	print OUTPUT "\tvolume\t= {".$hashContent{volume}."}, \n";
	print OUTPUT "\tnumber\t= {".$hashContent{number}."}, \n";
	print OUTPUT "\tpages\t= {".$hashContent{pages}."}, \n";
	print OUTPUT "\tkeywords\t= {".$hashContent{keywords}."}, \n";
	print OUTPUT "\tabstract\t= {".$hashContent{abstract}."}, \n";
	print OUTPUT "\turl\t= {http://www.ncbi.nlm.nih.gov/pubmed/".$hashContent{id}."}\n";
	print OUTPUT "}\n\n";
	close OUTPUT;
	
  # author		= {Chekmarev, DS and Kholodovych, V and Balakin, KV and  Ivanenkov,
	# Y and Ekins, S and Welsh, WJ},
  # title		= {Shape signatures: new descriptors for predicting cardiotoxicity in
	# silico.},
  # journal		= {Chem Res Toxicol},
  # year		= {2008},
  # volume		= {21},
  # pages		= {1304-4314},
  # number		= {6},
  # month		= {June},
  # abstract		= {Shape Signatures is a new computational tool that is being evaluated
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
  # owner		= {ChandesrisG},
  # timestamp		= {2011.06.21},
  # url		= {http://www.ncbi.nlm.nih.gov/pubmed/18461975}
# }
}


    # address : L'adresse de l'éditeur
    # abstract : Résumé de l'article
    # annote : Une annotation
    # author : Nom(s) puis prénom(s) des auteurs séparés par and. Exemple : author		= {DO, John and DUPOND, Marc}
    # booktitle : Le titre du livre
    # chapter : Le numéro de chapitre
    # crossref : La clé d'une référence croisée
    # edition : L'édition du livre
    # editor : Le nom de l'éditeur
    # eprint : La spécification d'un publication électronique.
    # howpublished : Comment il a été publié, si ce n'est pas avec une méthode standard
    # institution : L'institution qui été impliqué dans la publication (pas forcément l'éditeur)
    # journal : Le journal ou le magazine dans lequel le travail a été publié
    # key : Un champ caché utilisé pour spécifier ou remplacer l'ordre alphabétique des entrées (quand "author et "editor" ne sont pas présents).
    # month : Le mois de la création ou de la publication
    # note : Informations diverses.
    # number : Le numéro du journal ou du magazine.
    # organization : Le sponsor d'une conférence
    # pages : Le nombre de pages, séparés par des virgules.
    # publisher : Le nom de la maison d'édition
    # school : L'école dans laquelle la thèse a été écrite
    # series : La série de livres dans laquelle la livre a été publié
    # title : Le titre du document
    # type : Le type
    # url : L'adresse URL
    # volume : Le volume, dans le cas où il y a plusieurs volumes
    # year : L'année de publication (ou de création s'il n'a pas été publié)
	
# article
# Un article, provenant d'un journal ou d'un magazine
# Champs requis : author, title, journal, year
# Champs optionnels : volume, number, pages, month, note, key

# book
# Un livre
# Champs requis : author/editor, title, publisher, year
# Champs optionnels : volume, series, address, edition, month, note, key, pages

# booklet
# Un document imprimé, mais sans maison d'édition ou d'institution sponsor.
# Champs requis : title
# Champs optionnels : author, howpublished, address, month, year, note, key

# conference
# Identique à inproceedings ; inclus pour la compatibilité avec Scribe.
# Champs requis : author, title, booktitle, year
# Champs optionnels : editor, pages, organization, publisher, address, month, note, key

# inbook
# Une partie d'un livre, souvent sans nom. Peut être un chapitre et/ou un ensemble de pages.
# Champs requis : author/editor, title, chapter/pages, publisher, year
# Champs optionnels : volume, series, address, edition, month, note, key

# incollection
# Une partie d'un livre qui possède son propre titre
# Champs requis : author, title, booktitle, year
# Champs optionnels : editor, pages, organization, publisher, address, month, note, key

# inproceedings
# Un article d'une conférence.
# Champs requis : author, title, booktitle, year
# Champs optionnels : editor, pages, organization, publisher, address, month, note, key

# manual
# Documentation technique
# Champs requis : title
# Champs optionnels : author, organization, address, edition, month, year, note, key

# mastersthesis
# Une thèse de Master
# Champs requis : author, title, school, year
# Champs optionnels : address, month, note, key

# misc
# Pour les documents qui ne correspondent à aucune des catégories ci-dessus.
# Champs requis : aucun
# Champs optionnels : author, title, howpublished, month, year, note, key

# phdthesis
# Une thèse de doctorat
# Champs requis : author, title, school, year
# Champs optionnels : address, month, note, key

# proceedings
# Les débats d'une conférence.
# Champs requis : title, year
# Champs optionnels : editor, publisher, organization, address, month, note, key

# techreport
# Un rapport technique, publié par une école ou un autre institution, numéroté par série.
# Champs requis : author, title, institution, year
# Champs optionnels : type, number, address, month, note, key

# unpublished
# Un document qui possède un auteur et un titre, mais qui n'a pas été formellement publié.
# Champs requis : author, title, note
# Champs optionnels : month, year, key


## http://www.nlm.nih.gov/bsd/mms/medlineelements.html

# Abstract							(AB)
# Copyright Information				(CI)
# Affiliation						(AD)
# Investigator Affiliation			(IRAD)
# Article Identifier				(AID)
# Author							(AU)
# Full Author						(FAU)
# Book Title						(BTI)
# Collection Title					(CTI)
# Comments/Corrections									## nothing ? 
# Corporate Author					(CN)
# Create Date						(CRDT)
# Date Completed					(DCOM)
# Date Created						(DA)
# Date Last Revised					(LR)
# Date of Electronic Publication	(DEP)
# Date of Publication				(DP)
# Edition							(EN)
# Editor and Full Editor Name		(ED) (FED)
# Entrez Date						(EDAT)
# Gene Symbol						(GS)
# General Note						(GN)
# Grant Number						(GR)
# Investigator Name and Full Investigator Name			(IR) (FIR)
# ISBN								(ISBN)
# ISSN								(IS)
# Issue								(IP)
# Journal Title Abbreviation		(TA)
# Journal Title						(JT)
# Language							(LA)
# Location Identifier				(LID)
# Manuscript Identifier				(MID)
# MeSH Date							(MHDA)
# MeSH Terms						(MH)
# NLM Unique ID						(JID)
# Number of References				(RF)
# Other Abstract					(OAB)
# Other Copyright Information		(OCI)
# Other ID							(OID)
# Other Term						(OT)
# Other Term Owner					(OTO)
# Owner								(OWN)
# Pagination						(PG)
# Personal Name as Subject			(PS)
# Full Personal Name as Subject		(FPS)
# Place of Publication				(PL)
# Publication History Status		(PHST)
# Publication Status				(PST)
# Publication Type					(PT)
# Publishing Model					(PUBM)
# PubMed Central Identifier			(PMC)
# PubMed Central Release			(PMCR)
# PubMed Unique Identifier			(PMID)
# Registry Number/EC Number			(RN)
# Substance Name					(NM)
# Secondary Source ID				(SI)
# Source							(SO)
# Space Flight Mission				(SFM)
# Status							(STAT)
# Subset							(SB)
# Title								(TI)
# Transliterated Title				(TT)
# Volume							(VI)
# Volume Title						(VTI)


# PMID- 2131836
# OWN - NLM
# STAT- MEDLINE
# DA  - 19911230
# DCOM- 19911230
# LR  - 20061115
# IS  - 0893-228X (Print)
# IS  - 0893-228X (Linking)
# VI  - 3
# IP  - 3
# DP  - 1990 May-Jun
# TI  - Prediction of initial reduction potentials of compounds related to
      # anthracyclines and implications for estimating cardiotoxicity.
# PG  - 244-7
# AB  - Eighteen anthracyclines representing several different structural classes
      # were found in the literature to have their respective redox potentials
      # measured under near-identical conditions. The initial reductions were
      # determined in terms of half-wave potentials (HWPs). The electronic
      # structures of these 18 anthracyclines were optimized, and the resulting
      # electronic descriptor measures were regressed against the HWPs. A very
      # significant linear correlation (QSAR) was found between HWP and the lowest
      # unoccupied molecular orbital (LUMO) energy by using both the CNDO/2 and
      # MNDO one-electron molecular orbital methods. These findings suggest that
      # it may be possible to estimate the HWP of an anthracycline in advance of
      # its synthesis. Insofar as the redox potential, as represented by HWP, is
      # an indicator of the relative degree of cardiotoxicity of an anthracycline,
      # it may also be possible to rank relative cardiotoxicity for chemical
      # classes of anthracycline analogues in terms of the identified QSAR.
# AD  - Department of Medicinal Chemistry and Pharmacognosy, University of
      # Illinois, Chicago 60680.
# FAU - Kawakami, Y
# AU  - Kawakami Y
# FAU - Hopfinger, A J
# AU  - Hopfinger AJ
# LA  - eng
# PT  - Journal Article
# PT  - Research Support, Non-U.S. Gov't
# PL  - UNITED STATES
# TA  - Chem Res Toxicol
# JT  - Chemical research in toxicology
# JID - 8807448
# RN  - 0 (Antibiotics, Antineoplastic)
# RN  - 0 (Antineoplastic Agents)
# SB  - IM
# MH  - Antibiotics, Antineoplastic/chemistry/*toxicity
# MH  - Antineoplastic Agents/chemistry/toxicity
# MH  - Electrochemistry
# MH  - Heart/*drug effects
# MH  - Oxidation-Reduction
# MH  - Structure-Activity Relationship
# EDAT- 1990/05/01
# MHDA- 1990/05/01 00:01
# CRDT- 1990/05/01 00:00
# PST - ppublish
# SO  - Chem Res Toxicol. 1990 May-Jun;3(3):244-7.

# @ARTICLE{DS2008,
  # author		= {Chekmarev, DS and Kholodovych, V and Balakin, KV and  Ivanenkov,
	# Y and Ekins, S and Welsh, WJ},
  # title		= {Shape signatures: new descriptors for predicting cardiotoxicity in
	# silico.},
  # journal		= {Chem Res Toxicol},
  # year		= {2008},
  # volume		= {21},
  # pages		= {1304-4314},
  # number		= {6},
  # month		= {June},
  # abstract		= {Shape Signatures is a new computational tool that is being evaluated
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
  # owner		= {ChandesrisG},
  # timestamp		= {2011.06.21},
  # url		= {http://www.ncbi.nlm.nih.gov/pubmed/18461975}
# }

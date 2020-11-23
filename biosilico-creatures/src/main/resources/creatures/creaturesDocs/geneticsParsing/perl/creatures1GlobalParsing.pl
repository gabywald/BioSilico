#!/usr/bin/perl -w

use strict;

use Switch;
use Data::Dumper;
use Cwd;

## To read *.exp Creatures 1 files
## ## ## "./creatures1GlobalParsing.pl ../../genetics/C1unregs/unreg6.exp"
## ## ## "./creatures1GlobalParsing.pl ../../genetics/C1grendels/grendel02.exp"

my $fileInput = $ARGV[0];

if (!$fileInput) { die "Pas d'argument fichier en entrée !"; }

## ## ## test et récupération contenu
open (FILE,"<".$fileInput) or die "Fichier inexistant. ";
binmode(FILE);
my $contenu = "";
while (my $line = <FILE>) {
	## print "\t".$line;
	## chomp($line); ## PAS d'organisation en ligne !!
	$contenu .= $line;
}

close FILE;


## ## ## ATTENTION : le fait que les fichiers contiennent du binaire peut 
## ## ## causer des soucis à cause ASCII (passage à la ligne notemment)
## ## "sed 's/\r\r/\r/g' genetics/malun.exp > malun.exp" ou similaire 
## ## $content =~ s/\r\r/\r/g; ## pour le double ASCII 13 (carriage return)
## ## peut résoudre pas mal de soucis
## ## ## ## Format de fichier probablement lié à l'outil des 
## ## ## ## MFC / OLE Microsoft et la sérialisation des objets...
## ## ## ## COM : Component Object Model / MFC : Microsoft Fondation Class Library

## diff -y parsed/unreg2_exp/CBiochemistry.txt parsed/unreg3_exp/CBiochemistry.txt > parsed/diffUR2_UR3.txt

my $hash = createStructure($contenu);
## ## export treated data
exportTreatedData($fileInput,$hash);


## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 

sub createStructure {
	my $content		= shift;
	my %hashStruct	= (
		'Creature'		=> undef,
		'CGallery'		=> undef,
		'Body'			=> undef,
		'Limb'			=> undef,
		'CBrain'		=> undef,
		'CBiochemistry'	=> undef,
		'CGenome'		=> undef,
	);
	my $state		= 0;
	#	state	2	1 0 8 0 		'Creature'
	#	state	2	1 0 8 0 		'CGallery'
	#	state	2	1 0 4 0 		'Body'
	#	state	2	1 0 4 0 		'Limb'
	#	state	2	1 0 6 0 		'CBrain'
	#	state	2	1 0 13 0 		'CBiochemistry'
	#	state	2	1 0 7 0 		'CGenome'
	
	my $lastPosition	= 0;
	my $previousRecon	= undef;
	
	my $available = "";
	my $availView = "";
	
	for (my $i = 0 ; $i < length($content) ; $i++ ) {
		my @array = getCharAndNum($content, $i);
		$available .= $array[0];
	#	if ($array[0] =~ /[a-zA-Z0-9 ]/) { $availView .= $array[0]; }
	#	else { $availView .= " ".$array[1]." "; }
		$availView .= " ".$array[1]." ";
		if ($array[1] == 255) { $state++; }
		else { 
			## if not the 'FF FF 01' separator [main] !! (beware 'FF FF FF 01' exists !!)
			my $indicator = $i-3;
			
			## ## only one case in this switch !!
			switch($state) {
				case 2 { 
					my $limit	= $i+4;
					my $flags	= 0;
					my $ident	= "";
					my $recon	= "";
					
					## ## concatenate while data is not 0 twice
					## ## compute text identification
					## ## compute text recognition
					while ($flags < 2) {
						@array = getCharAndNum($content, $i);
						$ident .= $array[1]." ";
						$i++;
						if ($array[1] == 0) { $flags++; }
					}
					
					## ## 
					## ## compute text recognition
					do {
						@array = getCharAndNum($content, $i);
						if ($array[0] =~ /[a-zA-Z]/)
							{ $recon .= $array[0]; }
						$i++;
					} while ($array[0] =~ /[a-zA-Z]/); ## ($array[1] != 0);
					$i -= 2;
					
					## ## recognition is good
					if (length($recon) > 0) {
						if ($state != 0) { print "\tstate\t".$state."\t"; }
						print $ident."\t'".$recon."'\n\n";
						
						my %currentRepository = (
							'ident'		=> $ident,
							'recon'		=> $recon,
							## 'contentLength'	=> ($indicator-$lastPosition),
							## 'content'	=> substr($content,$lastPosition,$indicator-$lastPosition),
							## 'content'	=> $available,
						);
						$hashStruct{$recon} = \%currentRepository;
						
						if ($previousRecon) {
							## ## length = $indicator-$lastPosition
							print "\t".$lastPosition."\t".$indicator."\tlength of '"
									.$previousRecon."' is ".($indicator-$lastPosition)."\n\n";
							$hashStruct{$previousRecon}->{contentLength} = 
								$indicator-$lastPosition;
							$hashStruct{$previousRecon}->{content} = 
								availableGlobalTreatment($availView,$available);
								## substr($content,$lastPosition,$indicator-$lastPosition);
							$available = "";$availView = "";
						}
						$lastPosition	= $i;
						$previousRecon	= $recon;
						
						## last but not least !!
						if ($previousRecon =~ /CGenome/) {
							print "\t".$lastPosition."\t".(length($content)-1)."\tlength of '"
									.$previousRecon."' is ".((length($content)-1)-$lastPosition)."\n";
							$hashStruct{$previousRecon}->{contentLength} = 
										(length($content)-1)-$lastPosition;
							for ( ; $i < length($content) ; $i++ ) {
								my @array = getCharAndNum($content, $i);
								$available .= $array[0];
								if ($array[0] =~ /[a-zA-Z0-9 ]/) { $availView .= $array[0]; }
								else { $availView .= " ".$array[1]." "; }
							}
							
							$hashStruct{$previousRecon}->{content} = 
								availableGlobalTreatment($availView,$available);
							$available = "";$availView = "";
						}
						
	
					}
				} ## end case 2
				## ## else { if ($state != 0) { print "\tstate\t".$state." -- unknown\n"; } }
			} ## END SWITCH
			$state = 0;
		}
	}
	
	return \%hashStruct;
}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 

sub exportTreatedData {
	my $file		= shift;
	my $structure	= shift;
	my %structure	= %{$structure};

	my $dirName = $file;
	$dirName =~ s/\./_/g;
	$dirName =~ s/(.*)\/(.*)/$2/g;
	chomp($dirName);
	chdir("parsed");
	if (! -d $dirName) {
		mkdir($dirName) 
			or die "impossible créer répertoire ".$dirName."\n";
	} else { "\t'".$dirName."'existe !\n"; }
	chdir($dirName);
	chdir("../..");
	
	my $finalDirRecord = "parsed/".$dirName."/";
	
	#print cwd()."\n\n";
	
#	open OUTPUT,">".$finalDirRecord."exportAnalysis.txt";
#	print OUTPUT Dumper(\%structure);
#	close OUTPUT;
	
	print "=====================================================\n";
	
	foreach my $key (keys %structure) {
		my $subcontent = $structure{$key}->{content};
		open OUT,">".$finalDirRecord.$key.".txt";
		## print OUT translateBin2NumsLetters($subcontent)."\n\n";
		## ## print translateBin2NumsLetters($subcontent)."\n";
		my $analysis = undef;
		switch ($key) {
			case "Creature" {
				print "\tCREATURE IN\n";
				$analysis = _CREanalysis($subcontent);
			}
			case "Body" {
				print "\tBODY IN\n";
				$analysis =  _BODanalysis($subcontent);
			}
			case "CBiochemistry" {
				print "\tBIOCHEMISTRY IN\n";
				$analysis = _BIOanalysis($subcontent);
			}
			case "CGallery" {
				print "\tGALLERY IN\n";
				$analysis = _GALanalysis($subcontent);
			}
			case "Limb" {
				print "\tLIMB IN\n";
				$analysis = _LIManalysis($subcontent);
			}
			case "CGenome" {
				print "\tGENOME IN\n";
				## ## more elaborate analysis
				$analysis = _GENanalysis($subcontent);
			}
			case "CBrain" {
				print "\tBRAIN IN\n";
				$analysis = _BRAanalysis($subcontent);
			}
		} ## END switch
		$structure{$key}->{content} = "\n".$analysis;
		print OUT $analysis;
		close OUT;
		## die "\t".$key."\n";
	}

	open OUTPUT,">".$finalDirRecord."exportAnalysis.txt";
	foreach my $key (sort keys %structure) {
		my $alignTabsKey = "\t";
		if (length($key) < 12)	{ $alignTabsKey .= "\t"; }
		if (length($key) < 8)	{ $alignTabsKey .= "\t"; }
		if (length($key) < 4)	{ $alignTabsKey .= "\t"; }
		
		my $len = $structure{$key}->{contentLength};
		my $alignTabsLen = "\t";
		if (length($len) < 4)	{ $alignTabsLen .= "\t"; }
		
		print OUTPUT "\t".$key.
				$alignTabsKey.$structure{$key}->{ident}.
				"\t".$structure{$key}->{contentLength}.
				$alignTabsLen.$structure{$key}->{recon}."\n";
	}
	print OUTPUT "\n";
	print OUTPUT Dumper(\%structure);
	close OUTPUT;

}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Generic subfunctions Part : 

sub sumValues {
	my $content	= shift;
	my $result	= 0;
	$content =~ s/\n//g;
	my @liste	= split(" +",$content);
	foreach my $elt (@liste) { $result += $elt; }
	return $result;
}

## ## gives (binary char,ASCII number)
sub getCharAndNum {
	my $content		= shift;
	my $position	= shift;
	my @array = (substr($content, $position, 1));
	push(@array,ord($array[0]));
	return @array;
}

sub availableGlobalTreatment {
	my $view	= shift;
	my $binary	= shift;
	availableViewTreatment($view,$binary);
	return availableTreatment($binary);
}

sub availableViewTreatment {
	my $view	= shift;
	my $binary	= shift;
	$view =~ s/^ 255  255  1 //g;
	$view =~ s/ 255  255  1 $//g;
	## if (length($binary) < 1000) 
	## 	{ print "----------\n".$view."\n----------\n"; }
	## getc();
	return $view;
}

sub availableTreatment {
	my $view = shift;
	my $toRemove = chr(255).chr(255).chr(1);
	$view =~ s/^$toRemove//g;
	$view =~ s/$toRemove$//g;
	print "length : ".length($view)."\n----------\n"; ## getc();
	## print "----------\n".length($view)."\n----------\n";
	## getc();
	return $view;
}


sub translateReadableBin2NumsLetters {
	my $subcontent	= shift;
	my $i			= shift;
	my $dist		= shift;
	my $output = translateBin2NumsLetters(substr($subcontent,$i,$dist));
	return ($i+$dist,"\t".makePartsReadable($output)."\n");
}

sub translateReadableBin2Nums {
	my $subcontent	= shift;
	my $i			= shift;
	my $dist		= shift;
	my $output = translateBin2Nums(substr($subcontent,$i,$dist));
	return ($i+$dist,"\t".makePartsReadable($output)."\n");
}

sub translateReadableBin {
	my $subcontent	= shift;
	my $i			= shift;
	my $dist		= shift;
	my $output = translateBin(substr($subcontent,$i,$dist));
	return ($i+$dist,"\t".makePartsReadable($output)."\n");
}

## ## translate if extended alphanumeric (except space and DEL)
sub translateBin {
	my $content = shift;
	my $result	= "";
	my $prev	= 0;
	for (my $i = 0 ; $i < length($content) ; $i++ ) {
		my @array = getCharAndNum($content, $i);
		if ( ($array[1] >= 33) && ($array[1] <= 126) ) { 
			$result .= " ".$array[0]." "; 
			## ## (($prev==1)?"":" ").$array[0]." ";
			$prev = 1;
		}
		else { $result .= " ".$array[1]." ";$prev = 0; }
	}
	return $result;
}

## ## translate to ASCII numbers 
sub translateBin2Nums {
	my $content = shift;
	my $result	= "";
	for (my $i = 0 ; $i < length($content) ; $i++ ) {
		my @array = getCharAndNum($content, $i);
		$result .= $array[1]." ";
	}
	return $result;
}

## ## translate to ASCII numbers, and if alpha chars, gives letter (include space)
sub translateBin2NumsLetters {
	my $content = shift;
	my $result	= "";
	for (my $i = 0 ; $i < length($content) ; $i++ ) {
		my @array = getCharAndNum($content, $i);
		if ($array[0] =~ /[a-zA-Z0-9 ]/) { $result .= $array[0]; }
		else { $result .= " ".$array[1]." "; }
	}
	return $result;
}

sub readUntilZeroOrNotZero {
	my $content = shift;
	my $not		= shift; ## 0 or 1
	my $flag	= 0;
	my $i		= 0;
	
	my $part	= "";
	
	while ( ($i < length($content)) && ($flag == 0) ) {
		my @array = getCharAndNum($content, $i);
		if ($not == 0) {
			## Stop when '0'
			if ($array[1] == 0) { $flag = 1; }
			else { $part .= $array[0]; }
		} else {
			## Stop when NOT '0'
			if ($array[1] != 0) { $flag = 1; }
			else { $part .= $array[0]; }
		}

		$i++;
	};
	## ## print "\ti : ".$i."\tlength : ".length($content)."\n";
	my $subcontent = (length($content) != 0)?
			substr($content,$i-1,(length($content)-$i)):"";
#	print translateBin2NumsLetters($part)."\t//\t";
#	print translateBin2NumsLetters($subcontent)."\n";
#	print "\n";
	my @parts = ($part,$subcontent);
	return @parts;
}

sub readUntilPlain {
	my $content = shift;
	my $flag	= 0;
	my $i		= 0;
	
	my $part	= "";
	
	while ( ($i < length($content)) && ($flag == 0) ) {
		my @array = getCharAndNum($content, $i);
		## Stop when '255'
		if ($array[1] == 255) { $flag = 1; }
		else { $part .= $array[0]; }
		$i++;
	}
	my $subcontent = substr($content,$i-1,(length($content)-$i+1));
#	print translateBin2NumsLetters($part)."\t//\t";
#	print translateBin2NumsLetters($subcontent)."\n";
#	print "\n";
	my @parts = ($part,$subcontent);
	return @parts;
}

sub readAllByFour {
	my $content = shift;
	return readAllBy($content,4);
}

sub readAllBy {
	my $content = shift;
	my $dist	= shift;
	my @liste	= ();
	for (my $i = 0 ; $i < length($content) ; $i += $dist) 
		{ push (@liste,substr($content,$i,$dist)); }
	return @liste;
}


## ## 'tabulate' when submitted is a (numbers serie) separated by spaces
sub makeNumsReadable {
	my $series = shift;
	## my $output = translateBin2Nums(substr($subcontent,$i,$dist)); 
	$series =~ s/ /   /g;
	$series =~ s/([0-9]{2})   /$1  /g;
	$series =~ s/([0-9]{3})  /$1 /g;
	return $series;
}

## ## 'tabulate' when submitted is a (chars serie) separated by spaces
sub makePartsReadable {
	my $series = shift;
	## my $output = translateBin2Nums(substr($subcontent,$i,$dist)); 
	$series =~ s/ /   /g;
	$series =~ s/([^ ]{2})   /$1  /g;
	$series =~ s/([^ ]{3})  /$1 /g;
	return $series;
}


## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Creature analysis Part : 

sub _CREanalysis {
	my $creature	= shift;
	my $result		= "";
	## ## NOTE :: différence male / femelle repérée ici !!
	## TODO préciser...
	my @results = readAllByFour($creature);
	foreach my $elt (@results) {
		my $output = translateBin2Nums($elt);
		$result .= "\t".makePartsReadable($output)."\n";
	}
	
	return $result;
}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Biochemistry analysis Part : 

sub _BIOanalysis {
	my $biochemistry 	= shift;
	my $result			= "";
	my $output			= "";
	
	## ## 2 first bytes
	my $i		= 0;
	my $dist	= 1;
		($i,$output) = translateReadableBin2Nums($biochemistry,$i,$dist);
		$result .= $output;
	$i += $dist;
	my $reactionsTypes = sumValues($output)+1;
	## ## 3 groups of 4 bytes : number of default reactions + number of enlarged
	my @reactionsNumbers = ();
	$dist = 4;
	for (my $j = 0 ; $j < $reactionsTypes ; $j++) {
		($i,$output) = translateReadableBin2Nums($biochemistry,$i,4);
		$result .= $output;
		push (@reactionsNumbers,sumValues($output));
		## $result .= "\t".makeNumsReadable($output)."\n";
	}
	## 1 byte left
	$output = translateBin2Nums(substr($biochemistry,$i,1));
	$result .= "\t".makePartsReadable($output)."\n";
	
	## ## list of chemicals + half-lives
	$result .= "Chemicals and Half-lives\n";
	$dist = 2;
	for (my $j = 0 ; $j < 256 ; $j++) {
		$output = translateBin2Nums(substr($biochemistry,$i,$dist));
		$result .= "\t-".$j."-".(($j<100)?" ".(($j<10)?" ":""):"")."\t"
					.makePartsReadable($output)."\t"
					._BIOgetChemicalName($j)."\n";
		$i += $dist;
	}
	$result .= "\n";
	
	## http://meliweb.net/creatures/formulae.htm
	## http://meliweb.net/creatures/chemlist.htm
	## ## list of basic reactions ??
	## ## list of enlarged reactions ??
	## ## list of main standard reactions
	my $totalCount = 0;
	for (my $j = 0 ; $j < @reactionsNumbers ; $j++) {
		if ($j != $#reactionsNumbers) { 
			$result .= "?? Reactions '".$j."' (".$reactionsNumbers[$j].")\n";
			$dist = 8;
		} else { 
			$result .= "\nStandards reactions...\n";
			$dist = 9;
		}
		
		for (my $k = 0 ; $k < $reactionsNumbers[$j] ; $k++) {
			$output = translateBin2Nums(substr($biochemistry,$i,$dist));
			$result .= "\t".makePartsReadable($output)
							.(($j == $#reactionsNumbers)?
								"\t"._BIOconvertStandardReaction($output):
								"\t"._BIOconvertDefaultReaction($output))
							."\n";
			## ## print _BIOconvertReaction($output)."\n";
			$i += $dist;
		}
		$result		.= "\n";
		$totalCount += $reactionsNumbers[$j];
	}
	
	$result .= "\t".$totalCount." 'reactions'\t -- ".(256-$totalCount)." slots free\n\n";
	
	## ## following is unknown...
	$result .= "unknown zone 01\n";
	my @valuesSum = ();
		($i,$output) = translateReadableBin2Nums($biochemistry,$i,1);
		$result .= $output;
		my $unknown = int($output);
		for (my $j = 0 ; $j < 4 ; $j++) {
			($i,$output) = translateReadableBin2Nums($biochemistry,$i,4);
			$result .= $output;
			push (@valuesSum,sumValues($output));
		}
	$result .= "\n";
#	my $diffCount = $valuesSum[3] - $totalCount;
#	$dist = 9;
#	for (my $j = 0 ; $j < $valuesSum[3]+($valuesSum[0])-$totalCount ; $j++) { ## $diffCount+($valuesSum[0])
#		($i,$output) = translateReadableBin2Nums($biochemistry,$i,$dist);
#		$result .= $output;
#	}

	my $threshold	= 32;
	my $start		= $i;
	my $temps		= "";
	do {
		($i,$output) = translateReadableBin2Nums($biochemistry,$i,1);
		$output =~ s/\n//g;
		$result .= $output;
		if ( (int($output) != 0) && (int($output) < $threshold) )
			{ $temps .= "\tat pos '".$i."' is '".int($output)."'\n"; }
	} while (int($output) < $threshold);
	$i--;
	my $end = $i;
	$result .= "\n".$temps."\n\tlength is '".($end-$start+1)."'\n";
	$result .= "\tstart at pos. '".$start."' and end at pos. '".$end."'\n";
	
	## bizarre...
	$result .= "\n\n'bizarre' zone (324 bytes [9*36])\n";
	$dist = 9;
	for (my $j = 0 ; $j < 36 ; $j++) {
		($i,$output) = translateReadableBin2Nums($biochemistry,$i,$dist);
		$result .= $output;
	}
#	($i,$output) = translateReadableBin2Nums($biochemistry,$i,8);
#	$result .= $output;
	
	## stable zone
	$result .= "\n\nstable ?? zone\n";
	$dist = 8;
	for (my $j = 0 ; $j < 32 ; $j++) {
		($i,$output) = translateReadableBin($biochemistry,$i,$dist);
		$output =~ s/^\t(.) +(.) +(.) +(.) +/\t$1$2$3$4/g;
		$result .= $output;
	}
	$result .= "\n\n\n";
	
	## ## "informations complémentaires"
	## Ici un byte / octet qui indique ensuite le nombre de caractère à lire, 
	## ## puis recommence...
	$output = translateBin2Nums(substr($biochemistry,$i,1));
	$i++;
	$dist	= int($output);
	while ( ($dist > 4) && ($i < length($biochemistry)) ) {
		$output = translateBin(substr($biochemistry,$i,$dist));
		$output =~ s/ +//g;
		if ($dist >= 10) { $output =~ s/32/ /g; }
		$result .= "\t(".$dist.")\t'".makePartsReadable($output)."'\n";
		$i += $dist;
		if ($i < length($biochemistry)) {
			$output = translateBin2Nums(substr($biochemistry,$i,1));
			$dist	= int($output);
			$i++;
		}
	}
	$i--;
	
		if ($i < length($biochemistry)) {
			($i,$output) = translateReadableBin2Nums($biochemistry,$i,4);
			$result .= $output;
		}
	
	return $result;
}

sub _BIOconvertStandardReaction {
	my $reaction	= shift;
	my $result		= "";
	my @liste		= split(" ",$reaction);
	
	$result .= $liste[0]."\t*\t"._BIOgetChemicalName($liste[01])
				."\t+\t".$liste[2]."\t*\t"._BIOgetChemicalName($liste[3])
				."\t=>\t".$liste[4]."\t*\t"._BIOgetChemicalName($liste[5])
				."\t+\t".$liste[6]."\t*\t"._BIOgetChemicalName($liste[7]);
	$result .= "\tSpeed :\t".$liste[8];
	return $result;
}

sub _BIOconvertDefaultReaction {
	my $reaction	= shift;
	my $result		= "";
	my @liste		= split(" ",$reaction);
	
	$result .= $liste[4]."\t*\t"._BIOgetChemicalName($liste[0])
				."\t+\t".$liste[5]."\t*\t"._BIOgetChemicalName($liste[1])
				."\t=>\t".$liste[6]."\t*\t"._BIOgetChemicalName($liste[2])
				."\t+\t".$liste[7]."\t*\t"._BIOgetChemicalName($liste[3]);
	if (defined $liste[8]) { $result .= "\tSpeed :\t".$liste[8]; }
	else { $result .= "\tSpeed :\t8\t??"; }
#	for (my $i = 0 ; $i <= $#liste ; $i++) {
#		if ($i%2 != 0) {
#			$result .= _BIOgetChemicalName($liste[$i])."\t";
#		} else { $result .= $liste[$i]."\t"; }
#	}
	return $result;
}


our %chemicals = ();
sub _BIOinitChemicalList {
	open (CHEMICALS,"<"."../../geNorNics/chemicalsC1.csv")
		or die "Cannot open '"."../../geNorNics/chemicalsC1.csv"."'\n";
	my $group = undef;
	while (my $line = <CHEMICALS>) {
		## ## print "\t".$line."\n";
		if ($line =~ /;(.*?);(.*?);(.*?);(.*?);(.*?);(.*?)/) {
			## print "\t1-".$1."\n\t2-".$2."\n\t3-".$3
			## 		."\n\t4-".$4."\n\t5-".$5."\n\t6-".$6;
			my $numHex = $1;
			my $numDec = $2;
			my $classe = $3;
			my $bnames = $4;
			my $halHex = $5;
			my $halDec = $6;
			if ($classe ne "") { 
				$group = $classe;
				$group =~ s/"(.*) "/$1/;
				## ## print "\t".$classe."\n";
			}
			$bnames =~ s/"(.*) "/$1/;
			
			if ($numDec =~ /[0-9]*/) {
				if ($numDec =~ /^"([0-9]{1,3})\-([0-9]{1,3}) "$/) {
					my $start = $1;my $stop = $2;
					for (my $i = $start ; $i <= $stop ; $i++) 
						{ $chemicals{$i} = [ $bnames , $group ]; }
				} else 
					{ $chemicals{$numDec} = [ $bnames , $group ]; }
			}
		}
	}
	close CHEMICALS;
	## print Dumper(\%chemicals);die;
#	foreach my $key (sort keys %chemicals) 
#		{ print "\t".$key."\t".$chemicals{$key}->[0]
#				."\t".$chemicals{$key}->[1]."\n"; }
#	die;
}

sub _BIOgetChemicalName {
	my $num = shift;
	if (!$chemicals{$num}) { _BIOinitChemicalList(); }
	return "'".$chemicals{$num}->[0]."'";
}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Limb analysis Part : 

	## ## ## ## NOTES : 
	## ## On trouve les poses dans cette section (15 caractères)
	## ## on trouve les mots utilisés / connus

sub _LIManalysis {
	my $limb	= shift;
	my $result	= "";
	
	my $i		= 0;
	my $dist	= 0;
	my $output	= "";
#	my $dist	= 13;
#	my $output	= translateBin2Nums(substr($limb,$i,$dist));
#	$result .= "\t".makePartsReadable($output)."\n";
#	$i += $dist;
	
		($i,$output) = translateReadableBin2Nums($limb,$i,2);
		$result .= $output;
		
		for (my $j = 0 ; $j < 4 ; $j++) {
			($i,$output) = translateReadableBin2Nums($limb,$i,4);
			$result .= $output;
		}
		
		($i,$output) = translateReadableBin2Nums($limb,$i,7);
		$result .= $output;
	
		($i,$output) = translateReadableBin2Nums($limb,$i,40);
		$result .= $output;
	
	for (my $k = 0 ; $k < 2 ; $k++) {
		($i,$output) = translateReadableBin2Nums($limb,$i,2); ## 4 ??
		$result .= $output;
		
		for (my $j = 0 ; $j < 3 ; $j++) {
			($i,$output) = translateReadableBin2Nums($limb,$i,67);
			$result .= $output;
		}
	}
	
	for (my $k = 0 ; $k < 2 ; $k++) {
		($i,$output) = translateReadableBin2Nums($limb,$i,2); ## 4 ??
		$result .= $output;
		
		for (my $j = 0 ; $j < 2 ; $j++) {
			($i,$output) = translateReadableBin2Nums($limb,$i,67);
			$result .= $output;
		}
	}
	
	for (my $j = 0 ; $j < 4 ; $j++) {
		($i,$output) = translateReadableBin2Nums($limb,$i,4);
		$result .= $output;
	}
	
		($i,$output) = translateReadableBin2Nums($limb,$i,2);
		$result .= $output;	
	
		($i,$output) = translateReadableBin($limb,$i,16); ## !! bin
		$output =~ s/ +//g;
		$result .= $output;
		
		($i,$output) = translateReadableBin2Nums($limb,$i,3);
		$result .= $output;
	
	## Ici un byte / octet qui indique ensuite le nombre de caractère à lire, 
	## ## puis recommence...
	$output = translateBin2Nums(substr($limb,$i,1));
	$i++;
	$dist	= int($output);
	while ($dist > 4) {
		$output = translateBin(substr($limb,$i,$dist));
		$output =~ s/ +//g;
		$result .= "\t(".$dist.")\t".makePartsReadable($output)."\n";
		$i += $dist;
		$output = translateBin2Nums(substr($limb,$i,1));
		$i++;
		$dist	= int($output);
	}
	
	## structure légèrement différente (2*lecture + "valeur 0 0 0")...
	my $secondPass = 0;
	while ($dist =~ /^[2-68]$/){
		$output = translateBin(substr($limb,$i,$dist));
		$output =~ s/ +//g;
		$result .= "\t(".$dist.")\t".makePartsReadable($output)."\n";
		$i += $dist;
		if ($secondPass == 0) { $secondPass = 1; }
		else {
			$dist = 4;
			$output = translateBin2Nums(substr($limb,$i,$dist));
			$result .= "\t=>\t".makePartsReadable($output)."\n";
			$i += $dist;
			$secondPass = 0;
		}
		$output = translateBin2Nums(substr($limb,$i,1));
		$i++;
		$dist	= int($output);
	}
	$i--;
	
#	$dist = 16;
#	for (my $j = 0 ; $j < 20 ; $j++) {
#		$output = translateBin2Nums(substr($limb,$i,$dist));
#		$result .= "\t".makePartsReadable($output)."\n";
#		$i += $dist;
#	}
	
	$dist = 16;
	for (my $j = 0 ; $j < 20 ; $j++) {
		$output = translateBin2Nums(substr($limb,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	}
	
	$dist = 3;
		$output = translateBin2Nums(substr($limb,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	
	$dist = 12;
	for (my $j = 0 ; $j < 7 ; $j++) {
		$output = translateBin2Nums(substr($limb,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	}
	
	$dist = 10;
		$output = translateBin2Nums(substr($limb,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	
	$dist = 12;
	for (my $j = 0 ; $j < 27 ; $j++) {
		$output = translateBin2Nums(substr($limb,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	}
	
	$dist = 8;
	my @results = readAllBy(substr($limb,$i),$dist);
	foreach my $elt (@results) {
		$output = translateBin2Nums($elt);
		$result .= "\t".makePartsReadable($output)."\n";
	}
	
	return $result;
}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Gallery analysis Part : 

## 4 (iden) ; 8 ; 137*15 ; 3*4 (iden)(idenP)(idenM)

sub _GALanalysis {
	my $gallery = shift; 
	my $result	= "";
	## ## NOTE : indique code + codes des deux parents
	## ## ## ## NOTE : suppression premiers caractères
	my $i		= 0;
	my $dist	= 4;
	my $output = translateBin2Nums(substr($gallery,$i,$dist));
	$result .= "\t".makePartsReadable($output)."\n";

	my $count = sumValues($output);
	
	$i += $dist;$dist = 4;
	$result .= "\t".translateBin2NumsLetters(
					substr($gallery,$i,$dist))."\n";
	$i += $dist;
	
	$output = translateBin2Nums(substr($gallery,$i,4));
	$result .= "\t".makePartsReadable($output)."\n";
	$i += $dist;
	
	$output = translateBin2Nums(substr($gallery,$i,4));
	$result .= "\t".makePartsReadable($output)."\n";
	$i += $dist;
	
	$result .= "\n";
	my $limit = length($gallery)-(3*4+6);
	
	$dist = 15;
	for (my $j = 0 ; $j < $count ; $j++) {
		$output = translateBin2Nums(substr($gallery,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	}
	$result .= "\n";
	for (my $j = 0 ; $j < 2 ; $j++) {
		$output = translateBin2Nums(substr($gallery,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		$i += $dist;
	}
	
		$result .= "\n\n";
		while ($i < length($gallery)) {
			($i,$output) = translateReadableBin2Nums($gallery,$i,12);
			$result .= $output;
		}
	
#	$dist = 2;
#	$output = translateBin2Nums(substr($gallery,$i,$dist));
#	$result .= "\t".makePartsReadable($output)."\n";
#	$i += $dist;

	$result .= "\n";
	$i = length($gallery)-(3*4);
	$dist = 4;
	for (my $j = 0 ; $j < 3 ; $j++) {
		$result .= "\t".translateBin2NumsLetters(
						substr($gallery,$i,$dist))."\n";
		$i += $dist;
	}

	return $result;
}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Body analysis Part : 

sub _BODanalysis {
	my $body	= shift;
	my $result	= "";
	my $output	= "";
	## 2 bytes
	my $i = 0;
		($i,$output) = translateReadableBin2Nums($body,$i,2);
		$result .= $output;
	my $count = sumValues($output)+1;
	for (my $j = 0 ; $j < $count ; $j++) {
		($i,$output) = translateReadableBin2Nums($body,$i,4);
		$result .= $output;
	}
	## 3 bytes
		($i,$output) = translateReadableBin2Nums($body,$i,3);
		$result .= $output;
		
		
	$result .= "\n";
	for (my $j = 0 ; $j < 5 ; $j++) {
		($i,$output) = translateReadableBin2Nums($body,$i,4);
		$result .= $output;
	}
	$result .= "\n";
	for (my $j = 0 ; $j < 20 ; $j++) {
		($i,$output) = translateReadableBin2Nums($body,$i,4);
		$result .= $output;
	}
	
	
	my $dist = 8;
	my @results = readAllBy(substr($body,$i),$dist);
	foreach my $elt (@results) {
		my $output = translateBin2Nums($elt);
		$result .= "\t".makePartsReadable($output)."\n";
	}
	
	return $result;
}

## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Genetics analysis Part : 

## $subcontent =~ s/gend//g;
## my @liste = split("gene",$subcontent);
## foreach my $gene (@liste) 
## 	{ print OUT "\t".translateBin2Nums($gene)."\n"; }

sub _GENanalysis {
	my $genome = shift;
	
	my %geneHash = ();
	## ## Gene analysis
	my $counterOfGenes = 0;
	## ord($char) pour obtenir valeur ASCII caractère
	## chr($valu) dans l'autre sens (obtenir caractère ASCII)
	my $dist	= 14;my $i = 0;my $output = "";
	($i,$output) = translateReadableBin2Nums($genome,$i,$dist);
	my $result .= $output;
	
	my $temp	= "\t";
	for (my $i = 0 ; $i < length($genome) ; $i++ ) {
		my $char	= substr($genome, $i, 1);
		my $charNum	= ord($char);
		
		if ( ( ($charNum != 100) && ($charNum != 101) )
				&& ( ($charNum != 103) && ($charNum != 110) ) )
			{ $temp .= $charNum." "; }
		else { $temp .= $char; } ## { $temp .= "'".$char."'\t"; }
		if ($temp =~ /gene(.*)gen/) {
			my $tmp = $1;
			$tmp =~ s/g/103 /g;
			$tmp =~ s/e/101 /g;
			$tmp =~ s/n/110 /g;
			$tmp =~ s/d/100 /g;
#			$tmp =~ s/58/:/g;
#			$tmp =~ s/59/;/g;
#			$tmp =~ s/60/</g;
#			$tmp =~ s/61/>/g;
#			$tmp =~ s/62/=/g;
			## print "\t".Encode::encode_utf8($tmp)."\n";
			$tmp  = makeNumsReadable($tmp);
			%geneHash = %{_GENaddGene2hash($tmp,\%geneHash)};
			$temp = "\tgen";
			$counterOfGenes++;
		}
	}
		
	print "\t\t".$counterOfGenes." gènes comptés\n";
	return $result."\n"._GENshowAllGroups(\%geneHash);
}

sub _GENaddGene2hash {
	my $gene		= shift;
	my $geneHash	= shift;
	my %geneHash	= %{$geneHash};
	if ($gene =~ /^([0-9]) +([0-9]) +(.*)$/) {
		my $id1 = $1;
		my $id2 = $2;
		my $key = $id1."-".$id2;
		if ($geneHash{$key}) {
			my @liste = @{$geneHash{$key}};
			push (@liste,$gene);
			$geneHash{$key} = \@liste;
		} else {
			my @liste = ($gene);
			$geneHash{$key} = \@liste;
		}
	}
	return \%geneHash
}

sub _GENshowAllGroups {
	my $geneHash	= shift;
	my %geneHash	= %{$geneHash};
	my $result		= "";
	## ## print "\tDUMPER\n\t".Dumper(\%geneHash);
	my $globalCount = 0;
	foreach my $key (sort(keys %geneHash)) {
		my @liste = @{$geneHash{$key}};
		
		$result .= "\t"._GENgetGeneTitle($key)."\t".@liste." genes ; ";
		$globalCount += @liste+0;
		my @localAnalysis = split(" ",$liste[0]);
		$result .= "Length is\t".(@localAnalysis+1)."\n";
		foreach my $elt (@liste) 
			{ $result .=  "\t\t".$elt."\n"; }
	}
	$result .= "\tTotal\t".$globalCount." genes\n";
	return $result;
}

sub _GENgetGeneTitle {
	my $key = shift;
	my $title = ""; 
	switch ($key) {
		case"0-0" {	## 9 genes ; Length is	120
			$title = "Brain Gene -- Lobe"; }
		case"1-0" {	## 43 genes ; Length is	15
			$title = "Biochemistry Gene -- Receptor"; }
		case"1-1" {	## 27 genes ; Length is	15
			$title = "Biochemistry Gene -- Emitter"; }
		case"1-2" {	## 58 genes ; Length is	16
			$title = "Biochemistry Gene -- Reaction"; }
		case"1-3" {	## 1 genes ; Length is	263
			$title = "Biochemistry Gene -- Half-Lives"; }
		case"1-4" {	## 8 genes ; Length is	9
			$title = "Biochemistry Gene -- Initial Concentration"; }
		case"2-0" {	## 26 genes ; Length is	20
			$title = "Creature Gene -- Stimulus"; }
		case"2-1" {	## 1 genes ; Length is	16
			$title = "Creature Gene -- Genus"; }
		case"2-2" {	## 4 genes ; Length is	9
			$title = "Creature Gene -- Appearance"; }
		case"2-3" {	## 104 genes ; Length is	23
			$title = "Creature Gene -- Pose"; }
		case"2-4" {	## 8 genes ; Length is	17
			$title = "Creature Gene -- Gait"; }
		case"2-5" {	## 19 genes ; Length is	16
			$title = "Creature Gene -- Instinct"; }
		case"2-6" {	## 12 genes ; Length is	9
			$title = "Creature Gene -- pigment"; }
	}
	return "'".$title."'";
}


## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## 
## ## Brain analysis Part : 

sub _BRAanalysis {
	my $subcontent	= shift;
	my $result		= "";
	
	## ## http://double.co.nz/creatures/creatures2/expbrain.htm
	my $output	= "";
	my $i		= 0;
	## ## ""neuf premières lignes"" : 9 lobes (standard), length = 164
	my $dist = 4;
		($i,$output) = translateReadableBin2Nums($subcontent,$i,$dist);
		$result .= $output;
	
	my @counts = split(" +",$output);
	my $lobeCount = $counts[0];
	
	$result .= "=> ".$lobeCount." lobes\n";
	
	my $count	= 0;
	my @lobes 	= ();
	$dist		= 164;
	for ( ; ($i < length($subcontent)) && ($count < $lobeCount) ; $i += $dist) { 
		$output = translateBin2Nums(substr($subcontent,$i,$dist));
		$result .= "\t".makePartsReadable($output)."\n";
		push (@lobes,$output);
		$count++;
	}
#	## ## 6 bytes
#	$dist = 6;
#	$output = translateBin2Nums(substr($subcontent,$i,$dist));
#	$result .= "\t".makePartsReadable($output)."\n";
#	$i += $dist;

	## ## neurones : length = 16
	$result .= "\n\t=> neurones\n";
	my $lobeNumber = 0;
	foreach my $lobe (@lobes) {
		## ## $result .= "'".$lobe."'\n";
		my @composition = split(" +",$lobe);
		
#		if ($lobeNumber == 6) {
#			my $bolo = 0;
#			foreach my $elt (@composition) {
#				print "\t\t\t".$bolo."\t\t".$elt."\n";
#				$bolo++;
#			}
#		}
		
		my $lobePOSX = $composition[0];		## Pos X
		my $lobePOSY = $composition[4];		## Pos Y
		my $lobeHEIG = $composition[8];		## Height
		my $lobeWIDT = $composition[12];	## Width
		my $lobePERC = $composition[16];	## Perception Lobe Link
		my $lobeTHRE = $composition[20];	## Nominal Threshold
		my $lobeLEAK = $composition[26];	## Leakage rate
		my $lobeREST = $composition[27];	## Rest State
		my $lobeGAIN = $composition[28];	## input gain lo-hi
		my $lobeSR01 = $composition[29];	## state rule
		my $lobeSR02 = $composition[30];	## state rule
		my $lobeSR03 = $composition[31];	## state rule
		my $lobeSR04 = $composition[32];	## state rule
		my $lobeSR05 = $composition[33];	## state rule
		my $lobeSR06 = $composition[34];	## state rule
		my $lobeSR07 = $composition[35];	## state rule
		my $lobeSR08 = $composition[36];	## state rule
		my $lobeWTAA = $composition[39];	## winner takes all (WTA)
		
		## ## D0 Growth
		my $lobeD0SOUR = $composition[40];	## Source Lobe
		my $lobeD0MIND = $composition[44];	## Min. Dendritis
		my $lobeD0MAXD = $composition[45];	## Max. dendritis
		my $lobeD0SPRE = $composition[46];	## Spread
		my $lobeD0FANO = $composition[47];	## Fanout
		my $lobeD0MINL = $composition[48];	## Min. LTW
		my $lobeD0MAXL = $composition[49];	## Max. LTW
		my $lobeD0MINS = $composition[50];	## Min. Strength
		my $lobeD0MAXS = $composition[51];	## Max. Strength
		my $lobeD0MIGR = $composition[52];	## Migration Rule
		## ## D0 Dynamics
		
		## ## Following items of lobe concern  : 
		## ## ## ## ## D0 dynamics
		## ## ## a1 - relax susceptibility
		## ## ## a2 - relax STW
		## ## ## a3 - LTW gain rate
		## ## ## ge - gain every...
		## ## ## gr - gain rule				[*8]
		## ## ## le - lose every...
		## ## ## lr - lose rule				[*8]
		## ## ## ss - susceptibility		[*8]
		## ## ## rr - reinforcement			[*8]
		
		## ## D1 Growth
		my $lobeD1SOUR = $composition[98];	## Source Lobe
		my $lobeD1MIND = $composition[102];	## Min. Dendritis
		my $lobeD1MAXD = $composition[103];	## Max. dendritis
		my $lobeD1SPRE = $composition[104];	## Spread
		my $lobeD1FANO = $composition[105];	## Fanout
		my $lobeD1MINL = $composition[106];	## Min. LTW
		my $lobeD1MAXL = $composition[107];	## Max. LTW
		my $lobeD1MINS = $composition[108];	## Min. Strength
		my $lobeD1MAXS = $composition[109];	## Max. Strength
		my $lobeD1MIGR = $composition[110];	## Migration Rule
		## ## D1 Dynamics
		
		## ## Following items of lobe concern  : 
		## ## ## ## ## D1 dynamics
		## ## ## a1 - relax susceptibility
		## ## ## a2 - relax STW
		## ## ## a3 - LTW gain rate
		## ## ## ge - gain every...
		## ## ## gr - gain rule				[*8]
		## ## ## le - lose every...
		## ## ## lr - lose rule				[*8]
		## ## ## ss - susceptibility		[*8]
		## ## ## rr - reinforcement			[*8]
		
		## ## END of lobe
		my $lobeLENG = $composition[156];	## Length of lobe ??
		my $lobeUN01 = $composition[157];	## unknown 01
		my $lobeUN02 = $composition[160];	## number of cells ??				## unknown 02
		my $lobeUN03 = $composition[161];	## number of dendritis ??	## Length modifier | checker ?
		
#		my $lobeLength = $lobeLENG;
#		if ( ($lobeUN03 != 0) && ($lobeUN03 != $lobeLENG) ) 
#			{ $lobeLength *= $lobeUN03; }
			
		my $aboutLobeInfo = "\t".$lobeHEIG."\t".$lobeWIDT."\t(".$lobeHEIG*$lobeWIDT.")"
				.(($lobeWTAA == 1)?"\tWTA":"\t---")."\n";
				
		$aboutLobeInfo .= "\t\tDendritis 0 : \tSource : ".$lobeD0SOUR."\t(".$lobeD0MIND."-".$lobeD0MAXD
					.")\tSpread : ".$lobeD0SPRE."\tFanout : ".$lobeD0FANO
					."\tLTW : (".$lobeD0MINL."-".$lobeD0MAXL.")\tStrength : (".$lobeD0MINS."-".$lobeD0MAXS.")\n";
					
		$aboutLobeInfo .= "\t\tDendritis 1 : \tSource : ".$lobeD1SOUR."\t(".$lobeD1MIND."-".$lobeD1MAXD
					.")\tSpread : ".$lobeD1SPRE."\tFanout : ".$lobeD1FANO
					."\tLTW : (".$lobeD1MINL."-".$lobeD1MAXL.")\tStrength : (".$lobeD1MINS."-".$lobeD1MAXS.")\n";	
				
		print "\tLOBE ".$lobeNumber."\t::".$aboutLobeInfo."\n";
		$result .= "\tLOBE ".$lobeNumber."\t::".$aboutLobeInfo."\n";
		my $lobeLength = $lobeHEIG*$lobeWIDT;
		
#		if ( ($lobeUN02 > 0) && ($lobeLength > $lobeUN02) )
#			{ $lobeLength = $lobeUN02*(($lobeUN03 != 0)?$lobeUN03:1); }
		
		for (my $j = 0 ; $j < $lobeLength ; $j++) {
			my $neuroneDist		= 16;
			## 1 : relative pos X of neurone
			## 2 : relative pos Y of neurone
			## 3 : OUTPUT of neurone
			## 4 : STATE of neurone
			my $dendriteDist	= 10;
			## ## Xpos
			## ## Ypos
			## ## LTW
			## ## Strength
			## ## Type
	
			my @dendrites0 = ();
			my @dendrites1 = ();
			$dist = 11; ## 11
			$output = translateBin2Nums(substr($subcontent,$i,$dist));
			$result .= "\t\t".makePartsReadable($output); ## ."\n";
			$i += $dist;
			
			my @detection0	= split(" ",$output);
			my $counterD00	= $detection0[6];
			
			if ($counterD00 > 0) {
				$result .= "\n";
				$dist = $dendriteDist; ## && (@dendrites0 <= $lobeD0MAXD)
				for (my $l = 0 ; ($l < $counterD00)  ; $l++) { ## $lobeD0MIND
					($i,$output) = translateReadableBin2Nums($subcontent,$i,$dist);
					## $result .= $output;
					$result .= "\t\t(0)".$output;
					my @dendSplit = split(" +",$output);
					push(@dendrites0,"\t\t".$output);
				}
			}

			$dist = 5; ## 5
			$output = translateBin2Nums(substr($subcontent,$i,$dist));
			$result .= (($counterD00 > 0)?"\t\t":"").makePartsReadable($output).""; ## ."\n";
			$i += $dist;
			
			my @detection1	= split(" ",$output);
			my $counterD01	= $detection1[0]; ## [1]
			
			if ($counterD01 > 0) {
				$result .= "\n"; 
				$dist = $dendriteDist; ## && (@dendrites1 <= $lobeD1MAXD)
				for (my $l = 0 ; ($l < $counterD01)  ; $l++) { ## $lobeD1MIND
					($i,$output) = translateReadableBin2Nums($subcontent,$i,$dist);
					## $result .= $output;
					$result .= "\t\t(1)".$output;
					my @dendSplit = split(" +",$output);
					push(@dendrites1,"\t\t".$output);
				}
				$result .= "\n";
			}
			
			$result .= "\n".((($counterD00 != 0)&&($counterD01 == 0))?"\n":"");
			## if ($lobeNumber >= 7) { last; }
		} 
			
		$result .= "\n\n";
		$lobeNumber++;
	}
	
	## ?? ?? ?? ?? 
	$dist = 10;
	for ( ; $i < length($subcontent) ; ) { 
		($i,$output) = translateReadableBin2Nums($subcontent,$i,$dist);
		$result .= $output;
	}
	
	## ## patterns "255 255 255 1" et "128 128"
	
	return $result;
}

## ## Parsing Lobe's ??
## ## Lobes definitions for Creatures 1
#			Lobe ID		Gene #		X			Y				Width		Height		Neurons		Denomination		Lobe ID
#			0			01			4	(04)	13	(0D)		7	(07)	16	(10)	112			Perception			0
#			1			02			34	(22)	30	(1E)		8	(08)	2	(02)	16			Drive				1
#			2			03			15	(0F)	24	(18)		8	(08)	5	(05)	40			Source				2
#			3			04			37	(25)	24	(18)		8	(08)	2	(02)	16			Verb				3
#			4			08			21	(15)	3	(03)		20	(14)	2	(02)	40			Noun				4
#			5			09			32	(20)	34	(22)		8	(08)	4	(04)	32			General	Sense		5
#			6			05			53	(35)	15	(0F)		1	(01)	16	(10)	16			Decision			6
#			7			06			44	(2C)	30	(1E)		5	(05)	8	(08)	40			Attention			7
#			8			07			12	(0C)	6	(06)		40	(28)	16	(10)	640			Concept				8
## ## Lobes definitions for Creatures 2
#			Lobe ID		Gene #		X			Y				Width		Height		Neurons		Denomination		Lobe ID
#			0			01			1	(01)	13	(0D)		7	(07)	16	(10)	112			Perception			0
#			1			02			34	(22)	36	(24)		6	(06)	3	(03)	18			Drive				1
#			2			03			15	(0F)	32	(20)		8	(08)	5	(05)	40			Source				2
#			3			04			37	(25)	32	(20)		8	(08)	2	(02)	16			Verb				3
#			4			08			21	(15)	1	(01)		20	(14)	2	(02)	40			Noun				4
#			5			09			32	(20)	40	(28)		8	(08)	4	(04)	32			General	Sense		5
#			6			05			62	(3E)	15	(0F)		1	(01)	16	(10)	16			Decision			6
#			7			06			44	(2C)	36	(24)		5	(05)	8	(08)	40			Attention			7
#			8			07			11	(0B)	5	(05)		40	(28)	16	(10)	640			Concept				8
#			9			0A			4	(04)	40	(28)		16	(10)	1	(01)	16			Regulator			9

## ## Les State Variable Rules pour Creatures 1 (par groupe de 8)
my %hashSVRC1 = ();
sub _BRAstateVariableRulesC1 {
	my $codeNum = shift;
	if (!$hashSVRC1{$codeNum}) {
		$hashSVRC1{0} = ['00','00','<end>'];
		$hashSVRC1{1} = ['01','01','0'];
		$hashSVRC1{2} = ['02','02','1'];
		$hashSVRC1{3} = ['03','03','64'];
		$hashSVRC1{4} = ['04','04','255'];
		$hashSVRC1{5} = ['05','05','chem 0'];
		$hashSVRC1{6} = ['06','06','chem 1'];
		$hashSVRC1{7} = ['07','07','chem 2'];
		$hashSVRC1{8} = ['08','08','chem 3'];
		$hashSVRC1{9} = ['09','09','state'];
		$hashSVRC1{10} = ['10','0A','output'];
		$hashSVRC1{11} = ['11','0B','thres'];
		$hashSVRC1{12} = ['12','0C','type0'];		## sum of type 0 dendrites : value = source cell * ( stw/255)
		$hashSVRC1{13} = ['13','0D','type1'];		## sum of type 1 dendrites : value = source cell * ( stw/255)
		$hashSVRC1{14} = ['14','0E','anded 0'];
		$hashSVRC1{15} = ['15','0F','anded 1'];
		$hashSVRC1{16} = ['16','10','input'];
		$hashSVRC1{17} = ['17','11','conduct'];
		$hashSVRC1{18} = ['18','12','Suscept'];
		$hashSVRC1{19} = ['19','13','STW'];			## stw = ltw + (susceptibility/255) * reinforcement
		$hashSVRC1{20} = ['20','14','LTW'];
		$hashSVRC1{21} = ['21','15','Strength'];
		$hashSVRC1{22} = ['22','16','TRUE'];
		$hashSVRC1{23} = ['23','17','PLUS'];
		$hashSVRC1{24} = ['24','18','MINUS'];
		$hashSVRC1{25} = ['25','19','TIMES'];
		$hashSVRC1{26} = ['26','1A','INCR'];
		$hashSVRC1{27} = ['27','1B','DECR'];
		$hashSVRC1{28} = ['28','1C','<unused>'];
		$hashSVRC1{29} = ['29','1D','<unused>'];
		$hashSVRC1{30} = ['30','1E','<ERROR>'];
	}
	return $hashSVRC1{$codeNum};
}

## ## Les State Variable Rules pour Creatures 2 (par groupe de 12)
my %hashSVRC2 = ();
sub _BRAstateVariableRulesC2 {
	my $codeNum = shift;
	if (!$hashSVRC2{$codeNum}) {
		$hashSVRC2{0} = ['00','00','<end>'];
		$hashSVRC2{1} = ['01','01','0'];
		$hashSVRC2{2} = ['02','02','1'];
		$hashSVRC2{3} = ['03','03','32'];
		$hashSVRC2{4} = ['04','04','64'];
		$hashSVRC2{5} = ['05','05','128'];
		$hashSVRC2{6} = ['06','06','255'];
		$hashSVRC2{7} = ['07','07','chem 0'];
		$hashSVRC2{8} = ['08','08','chem 1'];
		$hashSVRC2{9} = ['09','09','chem 2'];
		$hashSVRC2{10} = ['10','0A','chem 3'];
		$hashSVRC2{11} = ['11','0B','chem 4'];
		$hashSVRC2{12} = ['12','0C','chem 5'];
		$hashSVRC2{13} = ['13','0D','state'];
		$hashSVRC2{14} = ['14','0E','output'];
		$hashSVRC2{15} = ['15','0F','thres'];
		$hashSVRC2{16} = ['16','10','type0'];
		$hashSVRC2{17} = ['17','11','type1'];
		$hashSVRC2{18} = ['18','12','anded 0'];
		$hashSVRC2{19} = ['19','13','anded 1'];
		$hashSVRC2{20} = ['20','14','input'];
		$hashSVRC2{21} = ['21','15','conduct'];
		$hashSVRC2{22} = ['22','16','Suscept'];
		$hashSVRC2{23} = ['23','17','STW'];
		$hashSVRC2{24} = ['24','18','LTW'];
		$hashSVRC2{25} = ['25','19','Strength'];
		$hashSVRC2{26} = ['26','1A','TRUE'];
		$hashSVRC2{27} = ['27','1B','FALSE'];
		$hashSVRC2{28} = ['28','1C','PLUS'];
		$hashSVRC2{29} = ['29','1D','MINUS'];
		$hashSVRC2{30} = ['30','1E','TIMES'];
		$hashSVRC2{31} = ['31','1F','INCR'];
		$hashSVRC2{32} = ['32','20','DECR'];
		$hashSVRC2{33} = ['33','21','rnd const'];
		$hashSVRC2{34} = ['34','22','leak in'];
		$hashSVRC2{35} = ['35','23','leak out'];
		$hashSVRC2{36} = ['36','24','curr src leak in'];
		$hashSVRC2{37} = ['37','25','multiply'];
		$hashSVRC2{38} = ['38','26','average'];
		$hashSVRC2{39} = ['39','27','move twrds'];
		$hashSVRC2{40} = ['40','28','random'];
		$hashSVRC2{41} = ['41','29','<unused>'];
		$hashSVRC2{42} = ['42','30','<ERROR>'];
	}
	return $hashSVRC2{$codeNum};
}
## ## ## NOTE : pour Creatures 3 : véritable machine à registre...

			

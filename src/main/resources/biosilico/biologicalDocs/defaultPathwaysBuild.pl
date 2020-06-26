#!/usr/bin/perl -w

use strict;

use Data::Dumper;


my $fileChemicals = "../data/ChemicalList.txt";
my %chemicals = ();
my $i = 0;
open (CHEMICALS,"<".$fileChemicals);
while (my $line = <CHEMICALS>) {
	my @content = split("\t",$line);
	if (!$chemicals{$content[0]})
		{ $chemicals{$content[0]} = $i++; }
}
close CHEMICALS;

## print Dumper(\%chemicals);die;

$i = 0;

my $fileInput = "defaultPathwaysNotes.txt";
my @formulaeS = ();

my $Acoef = undef;my $Achem = undef;
my $Bcoef = undef;my $Bchem = undef;
my $Ccoef = undef;my $Cchem = undef;
my $Dcoef = undef;my $Dchem = undef;

open (INPUT,"<".$fileInput);
open (OUTPUT,">defaultPathwaysGeneDescription.txt");
while (my $line = <INPUT>) {
	chomp($line);
	if ($line =~ /\t(.*?)=>(.*)$/) {
		my $begin = $1;
		my $endin = $2;
		## print "\t".$begin."\\\\".$endin."\n";
		
		$begin =~ /^(.*?) \+? ?(.*?)? ?$/;
		my $AA = $1;
		my $BB = ($2)?$2:undef;
		$AA =~ /^([0-9]+\*)?([a-zA-Z0-9\-\+]*?)$/;
		## print "\t1-'".(($1)?$1:"")."'\t2-'".(($2)?$2:"")."'\n";
		if (defined $2) { $Acoef = $1;$Achem = $2; }
		else { $Acoef = '1';$Achem = $1; }
		if (!$Acoef) { $Acoef = 1; }
		else { $Acoef =~ s/\*//g; }
		
		if (defined $BB) {
			$BB =~ /^([0-9]+\*)?([a-zA-Z0-9\-\+]*?)$/;
			## print "\t1-'".(($1)?$1:"")."'\t2-'".(($2)?$2:"")."'\n";
			if (defined $2) { $Bcoef = $1;$Bchem = $2; }
			else { $Bcoef = '1';$Bchem = $1; }
			if (!$Bcoef) { $Bcoef = 1; }
			else { $Bcoef =~ s/\*//g; }
		} else { $Bcoef = '0';$Bchem = '<NONE00>'; }
		
		
		## ## ' (.*?)'
		## ## ' (.*?) \+ (.*?)'
		## ## ' (.*?) \+ (.*?)\t\[\*([0-9])\]'
		## ## ' (.*?) \+ (.*?)\t\-\-(.*)'
		## ## ' (.*?) \+ (.*?)\t\[(\*|\+)([0-9])\]\t\-\-(.*)'
		
		$endin  =~ /^ (.*?)( \+ (.*?))?\t?(\[(\*|\+)[0-9]+\])?\t?(\-\-.*?)?$/;
		my $CC = (($1)?$1:$2);
		my $DD = (($1 && $2)?$3:undef);
		my $count	= $4;
		my $comment	= $5;
		## ## if ($4) { print "\t'".$count."'\n";getc(); }
		$CC =~ /^([0-9]+\*)?([a-zA-Z0-9\-\+]*?)$/;
		## print "\t1-'".(($1)?$1:"")."'\t2-'".(($2)?$2:"")."'\n";
		if (defined $2) { $Ccoef = $1;$Cchem = $2; }
		else { $Ccoef = '1';$Cchem = $1; }
		if (!$Ccoef) { $Ccoef = 1; }
		else { $Ccoef =~ s/\*//g; }
		if (defined $DD) {
			$DD =~ /^([0-9]+\*)?([a-zA-Z0-9\-\+]*?)$/;
			## print "\t1-'".(($1)?$1:"")."'\t2-'".(($2)?$2:"")."'\n";
			if (defined $2) { $Dcoef = $1;$Dchem = $2; }
			else { $Dcoef = '1';$Dchem = $1; }
			if (!$Dcoef) { $Dcoef = 1; }
			else { $Dcoef =~ s/\*//g; }
		} else { $Dcoef = '0';$Dchem = '<NONE00>'; }
		
		my $KMVM = 1;
		if ( (defined $count) && ($count =~ /(\*|\+)([0-9]+)/) ) { 
			if ($1 eq '*') { $KMVM *= int($2); }
			else { $KMVM += int($2); }
			## print "\t'".$KMVM."'\t'".$1."'\n";getc();
		}
		
#		print $line."\n";
		my $result = "\t".$i++."\t";
		$result .= $Acoef." * '".$Achem."' + ";
		$result .= $Bcoef." * '".$Bchem."' = ";
		$result .= $Ccoef." * '".$Cchem."' + ";
		$result .= $Dcoef." * '".$Dchem."'   (".$KMVM.")\n";
		
		print OUTPUT $result;
		
#		if ( ( ($Achem =~ /[0-9]/) || ($Bchem =~ /[0-9]/) ) 
#				|| ( ($Cchem =~ /[0-9]/) || ($Dchem =~ /[0-9]/) ) ) 
#			{ print "NUMBER !!";getc(); }
		
		if (defined $chemicals{$Achem})
			{ $Achem = $chemicals{$Achem}; }
		else { print "\tA -> '".$Achem."'\n";getc(); }
		if (defined $chemicals{$Bchem})
			{ $Bchem = $chemicals{$Bchem}; }
		else { print "\tB -> '".$Bchem."'\n";getc(); }
		if (defined $chemicals{$Cchem})
			{ $Cchem = $chemicals{$Cchem}; }
		else { print "\tC -> '".$Cchem."'\n";getc(); }
		if (defined $chemicals{$Dchem})
			{ $Dchem = $chemicals{$Dchem}; }
		else { print "\tD -> '".$Dchem."'\n";getc(); }
		$Acoef = int($Acoef);
		$Bcoef = int($Bcoef);
		$Ccoef = int($Ccoef);
		$Dcoef = int($Dcoef);
		
		my @formulae = ($Achem,$Acoef,$Bchem,$Bcoef,
						$Cchem,$Ccoef,$Dchem,$Dcoef,
						$KMVM,(($comment)?$comment:"nocomment"));
		push(@formulaeS,\@formulae);
		
#		print $formulae[0]." * ".$formulae[1]." + ";
#		print $formulae[2]." * ".$formulae[3]." = ";
#		print $formulae[4]." * ".$formulae[5]." + ";
#		print $formulae[6]." * ".$formulae[7]."   (".$formulae[8].")\n";
#		print "\n\n";
		## print Dumper(\@formulae);
		## getc(); ## die;
	}
}
close OUTPUT;
close INPUT;

## print Dumper(\@formulaeS);die;

my $fileOutput = "defaultPathwaysGeneCode.txt";
open (OUTPUT,">".$fileOutput);
my @moreResults = ();
for (my $j = 0 ; $j < @formulaeS ; $j++) {
	my $num = (($j < 100)?(($j < 10)?"00":"0"):"").$j;
	my @BRgene = @{$formulaeS[$j]};
	my $geneName = "BR".$num."";
	my $result = "BiochemicalReaction ".$geneName." = new BiochemicalReaction(\n";
	$result .= "\t\t\tfalse,true, false, true, 0, 9, 5, 25,\n";
	$result .= "\t\t\t";
	$result .= $BRgene[0].", ".$BRgene[1].", ".$BRgene[2].", ".$BRgene[3].", ";
	$result .= $BRgene[4].", ".$BRgene[5].", ".$BRgene[6].", ".$BRgene[7]."";
	$result .= ", ".$BRgene[8].");\n";
	$result .= $geneName.".setName(\"".$geneName."\");\n";
	push (@moreResults,"liste.addToChamps(\"".$geneName."\t2\t\"+".$geneName.".toString());");
	print OUTPUT $result;
}

## print OUTPUT "\n\ntry {\n";
foreach my $item (@moreResults) {
	print OUTPUT "\t".$item."\n";
}
## print OUTPUT "} catch (DataException e) { e.printStackTrace(); }";

close OUTPUT;


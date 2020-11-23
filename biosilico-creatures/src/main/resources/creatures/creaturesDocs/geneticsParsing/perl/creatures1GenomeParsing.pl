#!/usr/bin/perl -w

use strict;

use lib '.';
use GeneEnumsGroup;

my $fileInputPath = $ARGV[0];
if (!$fileInputPath) { die "No FilePath in argument !"; }

## To read *.gen Creatures 1 files
## ## ## "./creatures1GlobalParsing.pl ../../genetics/0WKW.gen"
## ## ## "./creatures1GlobalParsing.pl ../../genetics/8ARU.gen"

## https://www.perl.com/article/how-to-parse-binary-data-with-perl/
## https://www.tutorialspoint.com/perl/perl_unpack.htm
## https://www.tutorialspoint.com/perl/perl_pack.htm
## for beginners in OOP in Perl : https://www.tutorialspoint.com/perl/perl_oo_perl.htm
## see also : https://www.perltutorial.org/perl-oop/

sub readBinaryGenes {
	my $fileReadingInput = shift;
	my $data = "";
	my $loopindex = 1;
	while ( $loopindex ) {
		my $bytes = undef;
		my $success = read $fileReadingInput, $bytes, 1;
		## print $bytes . " // " . unpack("a*", $data)." // ".pack("a4", "gene")."\n";
		$data .= $bytes;
		## exit(1);
		if ( ($data =~ /(.*?)gen[ed]$/ ) || ( ! $success) ) {
			## print "gene detected !";
			$loopindex = 0;
		}
		## getc();
	}
	return $data;
}

open my $fileReadingInput, "<", $fileInputPath or die "File does not exist !";
binmode $fileReadingInput ;

while( my $gene = readBinaryGenes( $fileReadingInput ) ) {
	if ($gene eq "gene") { next; }
	if ($gene eq "") { next; }
	$gene =~ s/gen[ed]$//;
	print $gene."\n";
	my @toTreat = unpack("(C*) a4", $gene);
	
	## TODO treat Gene DATA !!
	
	foreach my $elt (@toTreat) {
		print "\t".$elt."\n";
	}
}

close $fileReadingInput;

my $tsg = GeneEnumsGroup->getEnumsTSG();
print $tsg."\n";
print $tsg->getName()."\n";
my @TSGcontents = @$tsg->getContents();
print @TSGcontents."\n";
print qq(@TSGcontents)."\n";


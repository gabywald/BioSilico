package CreaturesGene;

use strict;

use Switch;

use GeneEnumsGroup;

my %_dictionnaryGeneST = ();

sub new {
	my $class	= shift;
	$class		= ref($class) || $class;

	my $self	= {};

    $self->{type}		= shift;
    $self->{header}		= shift;
    $self->{attempted}	= shift; ## attempted length
    $self->{contents}	= ();
    $self->{haserror}	= undef;
    
	bless($self, $class);
	return $self;
}

sub addContents {
	my $self = shift;
	while(@_) { push (@{$self->{contents}}, shift); }
}

sub getAttempted {
	my $self = shift;
	return $self->{attempted};
}

sub toString {
	my $self		= shift;
	my $toReturn	= "";
	
	## example => "Genus : [2, 1, 1, 0, 0, 0] => ['1ATH', '8DSH']"
	
	$toReturn		.= $self->{type}." : [";
	my @header		= @{$self->{header}};
	$toReturn		.= join(", ", @header)."] => [";
	my @content		= @{$self->{contents}};
	$toReturn		.= join(", ", @content)."]";
	if (defined $self->{haserror}) {
		$toReturn	.= "\thas errors (".$self->{haserror}.")";
	}
	
	return $toReturn."\n";
}

sub autocheck {
	my $self = shift;
	
	
	if ( ! defined $self->{attempted}) 
		{ print "attempted NOT defined !"; } else {
		
		if ( ! defined $self->{contents}) { $self->{contents} = (); }
		
		while (@{$self->{contents}} < $self->{attempted}) {
			$self->addContents( '0' );
			if (defined $self->{haserror}) { $self->{haserror}++; }
			else { $self->{haserror} = 1; }
		}
		
		if (@{$self->{contents}} > $self->{attempted}) {
			my @nextContents = @{$self->{contents}}[1..$self->{attempted}];
			$self->{contents} = \@nextContents;
		}
	}
}

sub treatGeneData {
	my @toTreat = @_;
	
	my @header = @toTreat[0..5];
	
	if ( ! defined $header[0]) { print "UNDEFINED KEY 0 !!\n";return undef; }
	if ( ! defined $header[1]) { print "UNDEFINED KEY 1 !!\n";return undef; }
	
	my $key = $header[0]."-".$header[1];
	
	my $geneST = &_GENgetGeneTitle( $key );
	
	my ($genegroup, $genetype) = split(" -- ", $geneST->getName() );
	
	if ( ! defined $genetype) 
		## { print "UNDEFINED $genegroup :: $genetype !!\n";getc();return undef; }
		{ return $genetype; } ## return undef
	
	my $newGene = CreaturesGene->new( $genetype, \@header, $geneST->getAttempted() );
	
	## switch
	switch ($key) {
		case "0-0" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "0-1" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "1-0" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "1-1" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "1-2" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "1-3" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "1-4" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-0" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-1" {
			my @dataOne = @toTreat[7..10];
			my @dataTwo = @toTreat[11..14];
			
			if ( grep { $_ eq '0'} @dataOne ) { @dataOne = (); }
			if ( grep { $_ eq '0'} @dataTwo ) { @dataTwo = (); }
			
			$newGene->addContents( converterBinaryToChar( \@dataOne ) );
			$newGene->addContents( converterBinaryToChar( \@dataTwo ) );
		}
		case "2-2" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-3" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-4" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-5" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-6" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "2-7" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
		case "3-0" { $newGene->addContents( @toTreat[6..$#toTreat] ); }
	}
	
	$newGene->autocheck();
	
	return $newGene;
}

sub _GENgetGeneTitle {
	my $key = shift;
	if ( defined $_dictionnaryGeneST{$key} ) {
		return $_dictionnaryGeneST{$key};
	}
	## %_dictionnaryGeneST = {};
	my $tsg = GeneEnumsGroup->getEnumsTSG();
	foreach my $elt ($tsg->getContents()) {
		## elt is GeneTypeSubtype
		my $localkey = $elt->{type}."-".$elt->{subtype};
		$_dictionnaryGeneST{$localkey} = $elt;
	}

	return $_dictionnaryGeneST{$key};
}

sub converterBinaryToChar {
	my $data = shift;
	my $toReturn = "";
	for(@{$data}) { $toReturn .= chr($_); }
	return "'".$toReturn."'";
}

1;

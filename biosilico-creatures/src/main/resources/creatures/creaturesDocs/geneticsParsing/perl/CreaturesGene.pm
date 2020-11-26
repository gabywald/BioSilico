package CreaturesGene;

use strict;

use Switch;

use Data::Dumper;

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

sub toString {
	my $self		= shift;
	my $toReturn	= "";
	
	## example => "Genus : [2, 1, 1, 0, 0, 0] => ['1ATH', '8DSH']"
	
	if ( ! defined $self->{type}) {
		print Dumper( $self );
	}
	
	$toReturn		.= $self->{type}." : [";
	my @header		= @{$self->{header}};
	$toReturn		.= join(", ", @header)."] => [";
	my @content		= @{$self->{contents}};
	$toReturn		.= join(", ", @content)."]";
	if (defined $self->{haserror}) {
		$toReturn		.= "\t".$self->{haserror};
	}
	
	return $toReturn."\n";
}

sub autocheck {
	my $self = shift;
	## TODO checking !!
}

sub treatGeneData {
	my @toTreat = @_;
	
	
	my @header = (); ## my @header = @toTreat[1..6];
	for (my $i = 0 ; $i < 6 ; $i++) {
		push(@header, shift(@toTreat) );
	}
	
	if ( ! defined $header[0]) { print "UNDEFINED KEY 0 !!\n";return undef; }
	if ( ! defined $header[1]) { print "UNDEFINED KEY 1 !!\n";return undef; }
	
	my $key = $header[0]."-".$header[1];
	
	my ($genegroup, $genetype) = split(" -- ", &_GENgetGeneTitle( $key ) );
	
	if ( ! defined $genetype) 
		## { print "UNDEFINED $genegroup :: $genetype !!\n";getc();return undef; }
		{ return $genetype; } ## return undef
	
	my $newGene = CreaturesGene->new( $genetype, \@header );
	
	## switch
	switch ($key) {
		case "0-0" { $newGene->addContents( @toTreat ); }
		case "0-1" { $newGene->addContents( @toTreat ); }
		case "1-0" { $newGene->addContents( @toTreat ); }
		case "1-1" { $newGene->addContents( @toTreat ); }
		case "1-2" { $newGene->addContents( @toTreat ); }
		case "1-3" { $newGene->addContents( @toTreat ); }
		case "1-4" { $newGene->addContents( @toTreat ); }
		case "2-0" { $newGene->addContents( @toTreat ); }
		case "2-1" {
			my @dataOne = @toTreat[1..4];
			my @dataTwo = @toTreat[5..8];
			
			## print join("-\t-", @dataOne), "*****\n";
			## print join("-\t-", @dataTwo), "*****\n";
			if ( grep { $_ eq '0'} @dataOne ) { @dataOne = (); }
			if ( grep { $_ eq '0'} @dataTwo ) { @dataTwo = (); }
			
			## TODO check if @dataTwo is empty or full of 0 !!
			$newGene->addContents( converterBinaryToChar( \@dataOne ) );
			$newGene->addContents( converterBinaryToChar( \@dataTwo ) );
		}
		case "2-2" { $newGene->addContents( @toTreat ); }
		case "2-3" { $newGene->addContents( @toTreat ); }
		case "2-4" { $newGene->addContents( @toTreat ); }
		case "2-5" { $newGene->addContents( @toTreat ); }
		case "2-6" { $newGene->addContents( @toTreat ); }
		case "2-7" { $newGene->addContents( @toTreat ); }
		case "3-0" { $newGene->addContents( @toTreat ); }
	}
	
	## TODO autocheck for genes (contents : number of values expected and values obtained => nb of errors !)
	
	return $newGene;
}

sub _GENgetGeneTitle {
	my $key = shift;
	my $title = "";
	## TODO load from configuration file !
	switch ($key) {
		case "0-0" { $title = "Brain Gene -- Brain lobe"; }
		case "0-1" { $title = "Brain Gene -- Brain organ"; }
		case "1-0" { $title = "Biochemistry Gene -- Receptor"; }
		case "1-1" { $title = "Biochemistry Gene -- Emitter"; }
		case "1-2" { $title = "Biochemistry Gene -- Chemical Reaction"; }
		case "1-3" { $title = "Biochemistry Gene -- Half Lives"; }
		case "1-4" { $title = "Biochemistry Gene -- Initial Concentration"; }
		case "2-0" { $title = "Creature Gene -- Stimulus"; }
		case "2-1" { $title = "Creature Gene -- Genus"; }
		case "2-2" { $title = "Creature Gene -- Appearance"; }
		case "2-3" { $title = "Creature Gene -- Pose"; }
		case "2-4" { $title = "Creature Gene -- Gait"; }
		case "2-5" { $title = "Creature Gene -- Instinct"; }
		case "2-6" { $title = "Creature Gene -- Pigment"; }
		case "2-7" { $title = "Creature Gene -- Pigment bleed"; }
		case "3-0" { $title = "Creature Gene -- Organ"; }
		else { return undef; }
		## else { $title = "UNKNOWN TYPE/SYBTYPE OF GENE ! -- UNKNOWN"; }
	}
	return $title;
}

sub converterBinaryToChar {
	my $data = shift;
	my $toReturn = "";
	for(@{$data}) { $toReturn .= chr($_); }
	return "'".$toReturn."'";
}

1;

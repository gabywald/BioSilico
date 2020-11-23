package CreaturesGene;

use strict;

use Switch;

sub new {
	my $class	= shift;
	$class		= ref($class) || $class;

	my $self	= {};

    $self->{type}		= shift;
    $self->{header}		= shift;
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
	
	$toReturn		.= $self->{type}." : [";
	my @header		= @{$self->{header}};
	foreach my $elt (@header) 
		{ $toReturn	.= "".$elt.", "; }
	$toReturn =~ s/, $//;
	$toReturn		.= "] => [";
	my @content		= @{$self->{contents}};
	foreach my $elt (@content) 
		{ $toReturn	.= "".$elt.", "; }
	$toReturn =~ s/, $//;
	$toReturn		.= "]";
	if (defined $self->{haserror}) {
		$toReturn		.= "\t".$self->{haserror};
	}
	
	return $toReturn."\n";
}

sub treatGeneData {
	my @toTreat = @_;
	
	my @header = ();
	for (my $i = 0 ; $i < 6 ; $i++) {
		push(@header, shift(@toTreat) );
	}
	print $header[0]."-".$header[1]."::";
	print "(".@toTreat.")\t";
	foreach my $elt (@toTreat) {
		if ( ($header[0] == '2') && ($header[1] == '1') ) 
			## 	&& ( ($elt >= 65) && ($elt <= 90) )  )
			{ print "\t".chr($elt).""; } ## ord(  )...
		else { print "\t[".$elt."]"; }
	}
	print "\n";
	
	## my @header = @toTreat[0, 1, 2, 3, 4, 5];
	my $key = $header[0]."-".$header[1];
	my $genetitle = &_GENgetGeneTitle( $key );
	my ($genegroup, $genetype) = split(" -- ", $genetitle);
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
			$newGene->addContents( converterBinaryToChar( @toTreat[0, 1, 2, 3] ) );
			$newGene->addContents( converterBinaryToChar( @toTreat[4, 5, 6, 7] ) );
		}
		case "2-2" { $newGene->addContents( @toTreat ); }
		case "2-3" { $newGene->addContents( @toTreat ); }
		case "2-4" { $newGene->addContents( @toTreat ); }
		case "2-5" { $newGene->addContents( @toTreat ); }
		case "2-6" { $newGene->addContents( @toTreat ); }
		case "2-7" { $newGene->addContents( @toTreat ); }
		case "3-0" { $newGene->addContents( @toTreat ); }
	}
	
	## TODO m/ autocheck for genes (contents : number of values expected and values obtained => nb of errors !)
	
	return $newGene;
}

sub _GENgetGeneTitle {
	my $key = shift;
	my $title = ""; 
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
	}
	return $title;
}

sub converterBinaryToChar {
	my $toReturn = "";
	while(@_) { $toReturn .= chr(shift); }
	return $toReturn;
}

1;


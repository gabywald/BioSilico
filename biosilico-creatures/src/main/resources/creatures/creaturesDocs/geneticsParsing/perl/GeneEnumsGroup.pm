package GeneEnumsGroup;

use strict;

my $_containerTSG = ();
my $_containerSVR = ();
my $_containerGBF = ();
my $_containerBoP = ();
my $_containerSOS = ();
my $_containerSpe = ();
my $_containerPiC = ();

sub new {
	my $class	= shift;
	$class		= ref($class) || $class;

	my $self	= {};

    $self->{name}		= shift;
    $self->{contents}	= ();

	bless($self, $class);
	return $self;
}

sub getName {
	my $self = shift;
	return $self->{name};
}

sub addContents {
	my $self = shift;
	while(@_) { push (@{$self->{contents}}, shift); }
	## print "\t".\@{$self->{contents}};
	## foreach my $elt (@{$self->{contents}}) { print "\t".$elt."\n"; }
}

sub getContents {
	my $self = shift;
	return ($self->{contents})?@{$self->{contents}}:();
}

sub getEnumsTSG {
	my $self = shift;
	
	if ( defined $_containerTSG ) 
		{ return $_containerTSG; }
	
	$_containerTSG = GeneEnumsGroup->new( "geneC1C2definitions" );
	
	$_containerTSG->addContents( &DataLoader::loadFileConfig( $_containerTSG->getName() ) );
	## foreach my $data ( &DataLoader::loadFileConfig( $_containerTSG->getName() ) ) 
	## 	{ $_containerTSG->addContents( $data ); }
		
	return $_containerTSG;
}

sub getEnumsDatas {
	my $self = shift;
	my $datakey = shift;
	
	my $return = GeneEnumsGroup->new( $datakey );
	$return->addContents( &DataLoader::loadDataConfig( $return->getName() ) );
		
	return $return;
}

sub getEnumsSVRules {
	my $self = shift;
	
	if ( defined $_containerSVR ) 
		{ return $_containerSVR; }
	
	$_containerSVR = GeneEnumsGroup->getEnumsDatas( "svrules" );
	return $_containerSVR;
}

sub getEnumsGeneBitFlags {
	my $self = shift;
	
	if ( defined $_containerGBF ) 
		{ return $_containerGBF; }
	
	$_containerGBF = GeneEnumsGroup->getEnumsDatas( "genebitflags" );
	return $_containerGBF;
}

sub getEnumsBodyParts {
	my $self = shift;
	
	if ( defined $_containerBoP ) 
		{ return $_containerBoP; }
	
	$_containerBoP = GeneEnumsGroup->getEnumsDatas( "bodyparts" );
	return $_containerBoP;
}

sub getEnumsSwitchOnStage {
	my $self = shift;
	
	if ( defined $_containerSOS ) 
		{ return $_containerSOS; }
	
	$_containerSOS = GeneEnumsGroup->getEnumsDatas( "switchonstage" );
	return $_containerSOS;
}

sub getEnumsSpecies {
	my $self = shift;
	
	if ( defined $_containerSpe ) 
		{ return $_containerSpe; }
	
	$_containerSpe = GeneEnumsGroup->getEnumsDatas( "species" );
	return $_containerSpe;
}

sub getEnumsPigmentColor {
	my $self = shift;
	
	if ( defined $_containerPiC ) 
		{ return $_containerPiC; }
	
	$_containerPiC = GeneEnumsGroup->getEnumsDatas( "pigmentcolor" );
	return $_containerPiC;
}

1;



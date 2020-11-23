package GeneEnumsGroup;

use strict;

my $_containerTSG = ();

sub new {
	my $class	= shift;
	$class		= ref($class) || $class;

	my $self	= {};

    $self->{name}		= shift;
    $self->{contents}	= ();

	bless($self, $class);
	return $self;
}

sub getName() {
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

sub getEnumsTSG() {
	my $self = shift;
	
	if ( defined $_containerTSG ) 
		{ return $_containerTSG; }
	
	$_containerTSG = GeneEnumsGroup->new( "geneC1C2definitions" );
	
	$_containerTSG->addContents( &DataLoader::loadFileConfig( $_containerTSG->getName() ) );
	## foreach my $data ( &DataLoader::loadFileConfig( $_containerTSG->getName() ) ) 
	## 	{ $_containerTSG->addContents( $data ); }
		
	return $_containerTSG;
}

1;
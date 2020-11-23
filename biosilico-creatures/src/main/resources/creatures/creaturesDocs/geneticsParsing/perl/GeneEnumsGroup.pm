package GeneEnumsGroup;

use strict;

my $_containerTSG = undef;

sub new {
	my $class	= shift;
	$class		= ref($class) || $class;

	my $self	= {};

    $self->{name}		= shift;
    $self->{contents}	= \();

	bless($self, $class);
	return $self;
}

sub getName() {
	my $self = shift;
	return $self->{name};
}

sub addContents {
	my $self			= shift;
	while(@_) { push (@{$self->{contents}},shift); }
}

sub getContents {
	my $self = shift;
	return ($self->{contents})?$self->{contents}:\();
}

sub getEnumsTSG() {
	my $class = shift;
	
	if ( defined $_containerTSG) {
		return $_containerTSG;
	}
	
	$_containerTSG = GeneEnumsGroup->new( "geneC1C2definitions" );
	
	## TODO putting data in !
	
	return $_containerTSG
}

1;
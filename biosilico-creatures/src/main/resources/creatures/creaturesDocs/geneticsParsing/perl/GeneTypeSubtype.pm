package GeneTypeSubtype;

use strict;


sub new {
	my $class	= shift;
	$class		= ref($class) || $class;

	my $self	= {};

    $self->{type}		= shift;
    $self->{subtype}	= shift;
    $self->{attempted}	= shift; ## attempted length
    $self->{name}		= shift;
    
	bless($self, $class);
	return $self;
}

sub toString {
	my $self		= shift;
	my $toReturn	= "";
	
	$toReturn		.= $self->{type}		."\t";
	$toReturn		.= $self->{subtype}		."\t";
	$toReturn		.= $self->{attempted}	."\t";
	$toReturn		.= $self->{name};
	
	return $toReturn."\n";
}

1;
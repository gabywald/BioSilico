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

sub getName {
	my $self = shift;
	return $self->{name};
}

sub getType {
	my $self = shift;
	return $self->{type};
}

sub getSubtype {
	my $self = shift;
	return $self->{subtype};
}

sub getAttempted {
	my $self = shift;
	return $self->{attempted};
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
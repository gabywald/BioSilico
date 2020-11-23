package CreaturesGene;

use strict;

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

sub toString {
	my $self		= shift;
	my $toReturn	= "";
	
	## Genus : [2, 1, 1, 0, 0, 0] => ['1ATH', '8DSH']
	
	$toReturn		.= $self->{type}." : ";
	$toReturn		.= $self->{header}." => ";
	## $toReturn	.= $self->{contents}."";
	$toReturn		.= "[";
	my @content		= @{$self->{contents}};
	foreach my $elt (@content) 
		{ $toReturn	.= "".$elt.", ";
	$toReturn =~ s/, $//;
	$toReturn		.= "]";
	if (defined $self->{haserror}) {
		$toReturn		.= "\t".$self->{haserror};
	}
	
	return $toReturn."\n";
}

1;
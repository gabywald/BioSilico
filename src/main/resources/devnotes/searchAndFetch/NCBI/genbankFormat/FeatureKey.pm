package FeatureKey;

use strict;

## see 'http://stein.cshl.org/genome_informatics/using_perl_modules/export.html' for usage of Exporter
use base 'Exporter';
our @EXPORT_OK = qw($separator);
our $separator = ":::::";

sub new {
	my $class		= shift;
	my @mandatories	= ();
	my @optionnales	= ();
	my $self		= {
		'Feature Key'			=> shift,	## one line
		'Parent Key'			=> undef,	## one line
		'Definition'			=> undef,	## flag = 1
		'Organism scope'		=> undef,	## one line
		'Molecule scope'		=> undef,	## one line
		'References'			=> undef,	## flag = 2
		'Comment'				=> undef,	## flag = 3
		
		'Mandatory qualifiers'	=> \@mandatories,	## flag = 4
		'Optional qualifiers'	=> \@optionnales,	## flag = 5
	};
	bless ($self, $class);
	return $self;
}

sub getFeatureKey {
	my $self					= shift;
	return $self->{'Feature Key'};
}

sub setFeatureKey {
	my $self					= shift;
	$self->{'Feature Key'}		= shift;
}

sub setParentKey {
	my $self					= shift;
	$self->{'Parent Key'}		= shift;
}

sub setDefinition {
	my $self					= shift;
	my $argo					= shift;
	$self->{'Definition'}		= $argo."\n";
}

sub addToDefinition {
	my $self					= shift;
	my $argo					= shift;
	if (!defined $self->{'Definition'})
		{ $self->{'Definition'} 	= "".$argo."\n"; }
	else { $self->{'Definition'}	.= $argo."\n"; }
}

sub setOrganismScope {
	my $self					= shift;
	$self->{'Organism scope'}	= shift;
}

sub setMoleculeScope {
	my $self					= shift;
	$self->{'Molecule scope'}	= shift;
}

sub setReferences {
	my $self					= shift;
	my $argo					= shift;
	$self->{'References'}		= $argo."\n";
}

sub addToReferences {
	my $self					= shift;
	my $argo					= shift;
	if (!defined $self->{'References'})
		{ $self->{'References'}		= "".$argo."\n"; }
	else { $self->{'References'}	.= $argo."\n"; }
}

sub setComment {
	my $self					= shift;
	my $argo					= shift;
	$self->{'Comment'}			= $argo."\n";
}

sub addToComment {
	my $self					= shift;
	my $argo					= shift;
	if (!defined $self->{'Comment'})
		{ $self->{'Comment'}	= "".$argo."\n"; }
	else { $self->{'Comment'}	.= $argo."\n"; }
}

sub addMandatoryQualifier {
	my $self				= shift;
	my $toAdd				= shift;
	my @content				= @{$self->{'Mandatory qualifiers'}};
	push(@content, $toAdd);
	$self->{'Mandatory qualifiers'}	= \@content;
}

sub addOptionnalQualifier {
	my $self				= shift;
	my $toAdd				= shift;
	my @content				= @{$self->{'Optional qualifiers'}};
	push(@content, $toAdd);
	$self->{'Optional qualifiers'}	= \@content;
}

sub toString {
	my $self				= shift;
	my $toReturn			= "";
	
	$toReturn .= "Feature : '".$self->{'Feature Key'}
					.((defined $self->{'Parent Key'})
						?$self->{'Parent Key'}:"")."'\n";
	if (defined $self->{'Organism scope'})
		{ $toReturn .= "\tOrganism : '".$self->{'Organism scope'}."'\n"; }
	if (defined $self->{'Molecule scope'})
		{ $toReturn .= "\tMolecule : '".$self->{'Molecule scope'}."'\n"; }
	
	#	print "\t'".$self->{'Optional qualifiers'}."'\n";
	#	print "\t'".@{$self->{'Optional qualifiers'}}."'\n";
	#	getc();
	
	my @mandatories			= @{$self->{'Mandatory qualifiers'}};
	if (@mandatories > 0) 
		{ $toReturn .= "\tMandatory qualifiers (".@mandatories.")\n"; }
	foreach my $mandat (@mandatories) { 
		my @cutMan	= split($separator, $mandat);
		$toReturn .= "\t\t"._completeWithSpaces($cutMan[0], 13)."\t".$cutMan[1]."\n"; 
	}
	
	my @optionnales			= @{$self->{'Optional qualifiers'}};
	if (@optionnales > 0) 
		{ $toReturn .= "\tOptional qualifiers (".@optionnales.")\n"; }
	foreach my $option (@optionnales) { 
		my @cutOpt	= split($separator, $option);
		$toReturn .= "\t\t"._completeWithSpaces($cutOpt[0], 13)."\t".$cutOpt[1]."\n"; 
	}
	
	return $toReturn;
}

sub toJavaStaticMultiTon {
	my $self				= shift;
	my $toReturn			= "";
	
	$toReturn .= "\tpublic static FeatureDefinition get"
					._stringMethodJava($self->{'Feature Key'})."FD() {\n";
	$toReturn .= "\t\tFeatureDefinition toReturn = new FeatureDefinition( \""
					.$self->{'Feature Key'}."\");\n";
	
	if (defined $self->{'Parent Key'}) 
		{ $toReturn .= "\t\ttoReturn.properties[1] = \""
					._stringEscapeJava($self->{'Parent Key'})
					."\"; /** Parent Key */\n"; }
	if (defined $self->{'Definition'}) 
		{ $toReturn .= "\t\ttoReturn.properties[2] = \""
					._stringEscapeJava($self->{'Definition'})
					."\"; /** Definition */\n"; }
	if (defined $self->{'Organism scope'}) 
		{ $toReturn .= "\t\ttoReturn.properties[3] = \""
					._stringEscapeJava($self->{'Organism scope'})
					."\"; /** Organism scope */\n"; }
	if (defined $self->{'Molecule scope'}) 
		{ $toReturn .= "\t\ttoReturn.properties[4] = \""
					._stringEscapeJava($self->{'Molecule scope'})
					."\"; /** Molecule scope */\n"; }
	if (defined $self->{'References'}) 
		{ $toReturn .= "\t\ttoReturn.properties[5] = \""
					._stringEscapeJava($self->{'References'})
					."\"; /** References */\n"; }
	if (defined $self->{'Comment'}) 
		{ $toReturn .= "\t\ttoReturn.properties[6] = \""
					._stringEscapeJava($self->{'Comment'})
					."\"; /** Comment */\n"; }
	
	my @mandatories			= @{$self->{'Mandatory qualifiers'}};
	if (@mandatories > 0) { 
		foreach my $mandat (@mandatories) {
			my @cutMan	= split($separator, $mandat);
			$toReturn .= "\t\ttoReturn.mandatory.put(\""
							._stringEscapeJava($cutMan[0])."\", \""
							._stringEscapeJava($cutMan[1])."\");\n";
		}
	}
	
	my @optionnales			= @{$self->{'Optional qualifiers'}};
	if (@optionnales > 0) { 
		foreach my $option (@optionnales) {
			my @cutOpt	= split($separator, $option);
			$toReturn .= "\t\ttoReturn.optionnal.put(\""
							._stringEscapeJava($cutOpt[0])."\", \""
							._stringEscapeJava($cutOpt[1])."\");\n";
		}
	}
	$toReturn .= "\t\treturn toReturn;\n";
	$toReturn .= "\t}\n\n";
	
	
	return $toReturn;
}

sub _stringMethodJava {
	my $string = shift;
	$string =~ s/['_\-]//g;
	return ucfirst($string);
}

sub _stringEscapeJava {
	my $string = shift;
	$string =~ s/"/\\\"/g;
	$string =~ s/\n/"\+\n\t\t\t"/g;
	$string =~ s/"\+\n\t\t\t"$//g;
	return $string;
}

sub _completeWithSpaces {
	my $string	= shift;
	my $numTo	= shift;
	for (my $i = length($string) ; $i < $numTo ; $i++) {
		$string .= " ";
	}
	return $string;
}

1;
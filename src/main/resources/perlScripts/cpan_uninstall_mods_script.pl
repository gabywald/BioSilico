#!/usr/bin/perl -w

use strict;

## ## L'objectif de ce script est de désinstaller proprement des modules CPAN (fichiers et documentation). 
## ## source : http://www.nuxwin.com/article-96-tutoriel-cpan-listage-et-desinstallation-des-modules

use ExtUtils::Packlist;
use ExtUtils::Installed;

$ARGV[0] or die "Usage: ".$0." Module::Name\n";

my $mod = $ARGV[0];

my $inst = ExtUtils::Installed->new();

foreach my $item (sort($inst->files($mod))) {
	print "suppression de ".$item."\n";
	unlink $item;
}

my $packfile = $inst->packlist($mod)->packlist_file();
print "suppression de ".$packfile."\n";
unlink $packfile;

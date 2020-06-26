#!/usr/bin/perl -w

use strict;

## ## L'objectif de ce script est d'effectuer une liste des modules CPAN installés. 
## ## source : http://www.nuxwin.com/article-96-tutoriel-cpan-listage-et-desinstallation-des-modules

use ExtUtils::Installed;
my $instmod = ExtUtils::Installed->new();
foreach my $module ($instmod->modules()) {
my $version = $instmod->version($module) || "Version inconnue";
       print $module." -- ".$version."\n";
}

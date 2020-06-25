#!/usr/bin/perl -w

use strict;




## the XML database
my $base_uniprot_xml = "uniprot_sprot.xml";

	my %table_organism = ();
	my %table_taxonoms = ();
	my $file_table_taxonoms = "table_taxonoms.txt";
		open (SAUVEGARDE,">".$file_table_taxonoms);
		print SAUVEGARDE "";
		close SAUVEGARDE;
	if ( -e "<".$file_table_taxonoms ) {
		open (SAUVEGARDE,"<".$file_table_taxonoms);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_taxonoms{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_organism = "table_organism.txt";
		open (SAUVEGARDE,">".$file_table_organism);
		print SAUVEGARDE "";
		close SAUVEGARDE;
	if ( -e "<".$file_table_organism ) {
		open (SAUVEGARDE,"<".$file_table_organism);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_organism{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $flag_organism			= 0;
	my $flag_organism_lineage	= 0;
		## for organism table
		my @list_organism = ();
		my %current_organism = (); ## key, names, lineage, dbrefer
		my @list_organism_names = ();
		my @list_organism_taxons = ();
		
		my $current_taxon_number = 0;


## reading file buffer
my $line = "";
open(XML_UNIPROT,"<".$base_uniprot_xml) or die "$base_uniprot_xml does not exist ! \n\n $!\n\n ";
while ($line = <XML_UNIPROT>) {
			#########################################################################################################
			#########################################################################################################
			## ##  organism detection
			if ($line =~ /<organism>/) {
				## print $line."\n";
				$flag_organism = 1;
			} elsif ($line =~ /<organism key="(.*)">/) {
				## print $line."\n";
				$flag_organism = 1;
				$current_organism{"key"} = $1;
			}
			if ($flag_organism == 1) {
				if ($line =~ /<name type="(.*)">(.*)<\/name>/) {
					## print "\t".$line."\n";
					my $name_type = $1;my $name = $2;
					## see in ORACLE table definition : "common"|"full"|"scientific"|"synonym"|"abbreviation" as [c ; f ; i ; y ; a] : [1-5]
					if ($name_type =~ /[a-z]+/) {
						if ($name_type =~ /^common$/) 		{ $name_type = "1"; }
						if ($name_type =~ /^full$/) 		{ $name_type = "2"; }
						if ($name_type =~ /^scientific$/) 	{ $name_type = "3"; }
						if ($name_type =~ /^synonym$/) 		{ $name_type = "4"; }
						if ($name_type =~ /^abbreviation$/) { $name_type = "5"; }
					}
					push (@list_organism_names,$name_type.":::::".$name);
				}
				if ($line =~ /<lineage>/) { $flag_organism_lineage = 1; }
				if ($flag_organism_lineage == 1) {
					if ($line =~ /<\/lineage>/) { $flag_organism_lineage = 0; }
					if ($line =~ /<taxon>(.*)<\/taxon>/) { 
						push (@list_organism_taxons, $1);
						## print "\t\t".$line."\t'".$1."'\n";
					}
				}
				if ($line =~ /<\/organism>/) {
					$flag_organism = 0;
					$current_organism{"names"} = \@list_organism_names;
					if ($#list_organism_taxons > 0) ## really more than one taxon (first taxon at position [0])
						{ $current_organism{"lineage"} = \@list_organism_taxons; }
					else { $current_organism{"lineage"} = \(); }
					push (@list_organism,[ %current_organism ]);
					
					_orgaTreatment(\%current_organism);
					## getc();
					
					@list_organism_names = ();
					@list_organism_taxons = ();
					%current_organism = ();
				}
			}
				
			
} ## while ($line = <XML_UNIPROT>)
close(XML_UNIPROT);


sub _orgaTreatment {
	my $item_orga = shift;
	## my @item_orga = @{$item_orga};
	my %item_orga = %$item_orga;
	
	# print $item_orga;
	# print "\t".%$item_orga;
	# print "\t".%{$item_orga}."\n";
	
	## my %item_orga = %$item_orga;## key, names, lineage, dbrefer
	# my $memo = ""; ## retrieving the hash of current organism
	# foreach my $valeur (sort keys %item_orga) {
		# print $valeur."*****\n";
		# if ($memo =~ /^$/) { $memo = $valeur; }
		# else { 
			# if ($memo =~ /^names$/)		{ $item_orga{"names"}	= $valeur;$memo = ""; }
			# if ($memo =~ /^lineage$/)	{ $item_orga{"lineage"}	= $valeur;$memo = ""; }
		# }
	# }
	
	## treatment for current organism
	## ## organism : lineage / taxon treament
	my $current_organism_taxon	= 0;
	my $previous_taxon_number	= -1;
	if (exists $item_orga{"lineage"}) { 
		# print "\tLINEAGE !!!\n";
		# print "\tsimple : '".$item_orga{"lineage"}."'"."\n";
		# print "\trecup  : '".@$item_orga{"lineage"}."'"."\n";
		# print "\t'".@{$item_orga{"lineage"}}."'"."\n";
		my @current_organism_taxons = @{$item_orga{"lineage"}};
		foreach my $taxon (@current_organism_taxons) {
			if (exists $table_taxonoms{$taxon}) {
				$current_organism_taxon = $table_taxonoms{$taxon}; 
				$previous_taxon_number	= $table_taxonoms{$taxon}; 
			} else {
				$current_organism_taxon = $current_taxon_number;
				$table_taxonoms{$taxon} = $current_taxon_number;
				
				my $toSaveLine = $taxon."\t".$current_taxon_number."\t".$previous_taxon_number."\n";
				open (SAUVEGARDE,">>".$file_table_taxonoms);
				print SAUVEGARDE $toSaveLine;
				print $toSaveLine;
				close SAUVEGARDE;
				
				$previous_taxon_number	= $current_taxon_number;
				$current_taxon_number++;
			}
		} ## end foreach $taxon (@current_organism_taxons)
	}					
	if (exists $item_orga{"names"}) {
		# print "\tNAME !!!\n";
		# print "\tsimple : '".$item_orga{"names"}."'"."\n";
		# print "\trecup  : '".@$item_orga{"names"}."'"."\n";
		# print "\t'".@{$item_orga{"names"}}."'"."\n";
		## organism : name treatment
		my @current_organism_names = @{$item_orga{"names"}};
		
		my @split_name = split(/:::::/,$current_organism_names[0]); ## organism type ::::: organism name
		## print "\t''".$current_organism_names[0]."''\n";
		my $current_organism_name_type	= $split_name[0];
		my $current_organism_name		= $split_name[1];
		
		foreach my $current_orga (@current_organism_names) {
			my @split_name = split(/:::::/,$current_orga); ## organism type ::::: organism name
			my $current_orga_name_type	= $split_name[0];
			my $current_orga_name		= $split_name[1];
			print "\t'".$current_orga_name_type."'\t'".$current_orga_name."'\n";
		}
		
		## print "\n";getc();

		# $current_organism_name =~ s/'/"/g;
		my $currentorgid = 0;
		foreach my $organism (sort keys %table_organism) # to get the highest index / id
				{ if ($table_organism{$organism} > $currentorgid) { $currentorgid = $table_organism{$organism}; } }
		if (!exists $table_organism{$current_organism_name}) {
			$currentorgid++;

			$table_organism{$current_organism_name} = $currentorgid;
			
			my $toSaveLine = $current_organism_name."\t".$currentorgid."\t".$current_organism_name_type."\t".$previous_taxon_number."==".$current_organism_taxon."\n";
			open (SAUVEGARDE,">>".$file_table_organism);
			print SAUVEGARDE $toSaveLine;
			print $toSaveLine;
			close SAUVEGARDE;
		}
	}
}

			########################################################################################################
			# INSERT organism : key, names, lineage, dbrefer
				# foreach my $item_orga (@list_organism) {
					# my @item_orga = @{$item_orga};
					# my %item_orga = ();## key, names, lineage, dbrefer
					# my $memo = ""; ## retrieving the hash of current organism
					# foreach my $valeur (@item_orga) {
						# if ($memo =~ /^$/) { $memo = $valeur; }
						# else { 
							# if ($memo =~ /^key$/) { $item_orga{"key"} = $valeur;$memo = ""; }
							# if ($memo =~ /^names$/) { $item_orga{"names"} = $valeur;$memo = ""; }
							# if ($memo =~ /^lineage$/) { $item_orga{"lineage"} = $valeur;$memo = ""; }
							# if ($memo =~ /^dbrefer$/) { $item_orga{"dbrefer"} = $valeur;$memo = ""; }
						# }
					# }
					# treatment for current organism
					# ## organism : lineage / taxon treament
					# my $current_organism_taxon = 0;
					# if (exists $item_orga{"lineage"}) { 
						# my @current_organism_taxons = @{$item_orga{"lineage"}};
						# foreach my $taxon (@current_organism_taxons) {
							# if (exists $table_taxonoms{$taxon}) 
								# { $current_organism_taxon = $table_taxonoms{$taxon}; }
							# else {
								
								# $current_organism_taxon = $current_taxon_number;
								# $table_taxonoms{$taxon} = $current_taxon_number;
								# open (SAUVEGARDE,">>".$file_table_taxonoms);
								# print SAUVEGARDE $taxon."\t".$current_taxon_number."\n";
								# print $taxon."\t".$current_taxon_number."\n";
								# close SAUVEGARDE;
								# $current_taxon_number++;
							# }
						# } ## end foreach $taxon (@current_organism_taxons)
					# }					
					# if (exists $item_orga{"names"}) {
						# organism : name treatment
						# my @current_organism_names = @{$item_orga{"names"}};
						# my $current_organism_key = $item_orga{"key"};
						# my @split_name = split(/:::::/,$current_organism_names[0]); ## organism type ::::: organism name
						# my $current_organism_name_type = $split_name[0];
						# my $current_organism_name = $split_name[1];
						# my $currentorgid = 0;
						# foreach my $organism (sort keys %table_organism) # to get the highest index / id
								# { if ($table_organism{$organism} > $currentorgid) { $currentorgid = $table_organism{$organism}; } }
						# if (!exists $table_organism{$current_organism_name}) {
							# $currentorgid++;
	
							# $table_organism{$current_organism_name} = $currentorgid;
							# open (SAUVEGARDE,">>".$file_table_organism);
							# print SAUVEGARDE $current_organism_name."\t".$currentorgid."\t".$current_organism_name_type."\n";
							# print $current_organism_name."\t".$currentorgid."\t".$current_organism_name_type."\n";
							# close SAUVEGARDE;
						# }

						
					# }
				# } ## END INSERT organism

































#!/usr/bin/perl -w

use strict;

use Cwd;

## ## ## DESCRIPTION OF THE SCRIPT
## ## this script's aim is to make a general treatment of an input uniprot XML database and convert it to insertions into an ORACLE database
## 1/ segmentation of initial XML UniprotKB database
## 2/ detection of generated parts
## 3/ insertion oracle generation on each part (SQL files to execute in ORACLE / SQLPLUS)
## ## redondancy solved basically for types already defined in UniprotKB XML / XSD definition ; UNIPROT_*_TYPE tables) : enumeration lists
## ## redondancy solved for author / editors names in citations [20090330] : dynamic hash storage
## ## redondancy solved for dbreferences property types [20090330] : dynamic hash storage and create enumeration list
## ## redondancy solved for taxonomy [20090330] : dynamic hash storage
## ## redondancy solved for organism [20090330] : dynamic hash storage
## ## redondancy solved for keywords [20090330] : dynamic hash storage and create enumeration list

## the XML database
my $base_uniprot_xml = "uniprot_sprot.xml";

	my $compteur = 1;## !! to count selected dbreferences selected
	my $selections = "selection_IPR_PDB_GO.txt";
	open (IPRPDBGOSELECTION,">".$selections);
	print IPRPDBGOSELECTION "";
	close(IPRPDBGOSELECTION);

## ## ## MAKING PARTITIONS OF THE XML DATABASE

## some detections
my $flag_part_done = 0;
my $flag_insert_done = 0;
my $directory = cwd(); ## "/";
opendir(DIR, $directory) || die "$directory: $!";
my @directory_list = readdir(DIR);
closedir(DIR);
foreach my $file (@directory_list) {
	if ($file =~ /uniprot_sprot_part[0-9]+.xml/) { $flag_part_done++; }
	if ($file =~ /insert_oracle_part[0-9]+.sql/) { $flag_insert_done++; }
}

if ($flag_part_done == 0) {
	my $partition_count = 0;
	my $line_count = 0;
	my $entry_count = 0;
	my $previous_entry_count = 0;
	my $diff_entry_count = 10000; ## number of entries for each partition

	my $flag_entry = 0;

	my @entry_temp = ();

	## opening the XML database
	open(XML_UNIPROT,"<".$base_uniprot_xml) or die "$base_uniprot_xml does not exist ! \n\n $!\n\n ";
	## reading file buffer
	my $line = "";

	while ($line = <XML_UNIPROT>) {
		$line_count++;
		## the output file parts
		# my $file_output_name = "uniprot_sprot_part".$partition_count.".xml";
		# open (SORTIE,">".$file_output_name);
		# print SORTIE "";
		# close SORTIE;

		if ($line =~ /<entry created="(.*)" dataset="(.*)" modified="(.*)" version="(.*)">/) {
			$flag_entry = 1;
			$entry_count++;
		}
		if ($flag_entry == 1) { push (@entry_temp,$line); }
		if ( ($flag_entry == 1) && ($line =~ /<\/entry>/) ) { $flag_entry = 0; }
		
		if ( ($flag_entry == 0) && (($entry_count%($diff_entry_count/10)) == 0) ) { print "\t".$entry_count."\n"; }
		
		if ( ( ($flag_entry == 0) && ($#entry_temp > -1) ) 
				&& ( ($entry_count - $previous_entry_count) >= $diff_entry_count) ) {
			my $file_output_name = "uniprot_sprot_part".$partition_count.".xml";
			print "\t".$file_output_name."\n";
			print "<!-- ".$file_output_name." -->\n";
			print "<!-- ".($entry_count-$previous_entry_count)." entries ; ".($#entry_temp+1)." lines -->\n";
			open (SORTIE,">>".$file_output_name);
			print SORTIE "<!-- ".$file_output_name." -->\n";
			print SORTIE "<!-- ".($entry_count-$previous_entry_count)." entries ; ".($#entry_temp+1)." lines -->\n";
			foreach my $elt (@entry_temp) { print SORTIE $elt; }
			close SORTIE;
			print "\tDONE\n";
			$previous_entry_count = $entry_count;
			@entry_temp = ();
			$partition_count++;
		}
	}
		## for the last entries
		if ( ($flag_entry == 0) && ($#entry_temp > -1) ) {
			my $file_output_name = "uniprot_sprot_part".$partition_count.".xml";
			print "\t".$file_output_name."\n";
			print "<!-- ".$file_output_name." -->\n";
			print "<!-- ".($entry_count-$previous_entry_count)." entries ; ".($#entry_temp+1)." lines -->\n";
			open (SORTIE,">>".$file_output_name);
			print SORTIE "<!-- ".$file_output_name." -->\n";
			print SORTIE "<!-- ".($entry_count-$previous_entry_count)." entries ; ".($#entry_temp+1)." lines -->\n";
			foreach my $elt (@entry_temp) { print SORTIE $elt; }
			close SORTIE;
			print "\tDONE\n";
			$previous_entry_count = $entry_count;
			@entry_temp = ();
			$partition_count++;
		}
	close (XML_UNIPROT);
	print "END of PARTITION\n";
} else { print "\t".$flag_part_done." partitions done\n"; }

##################################################################################
##################################################################################

## some "database select simulation table" to avoid redondancy and present in base (but not defined as types)
## ## should be replaced with dynamic database selections in each corresponding table if data already exists (in perl DBD:Oracle) or JDBC
## ## in case of dynamic request in databse : dynamic insertion should be already done instead of creating some SQL files
my %table_namelist = ();
my %table_genegene = ();
my %table_genelocs = ();
my %table_keywords = ();
my %table_organism = ();
my %table_taxonoms = ();
my %table_citation = ();
my %table_property = ();

	my $file_table_namelist = "table_namelist.txt";
	if ( -e "<".$file_table_namelist ) {
		open (SAUVEGARDE,"<".$file_table_namelist);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_namelist{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_genegene = "table_genegene.txt";
	if ( -e "<".$file_table_genegene ) {
		open (SAUVEGARDE,"<".$file_table_genegene);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_genegene{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_geneloc = "table_genelocs.txt";
	if ( -e "<".$file_table_geneloc ) {
		open (SAUVEGARDE,"<".$file_table_geneloc);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_genelocs{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_keyword = "table_keywords.txt";
	if ( -e "<".$file_table_keyword ) {
		open (SAUVEGARDE,"<".$file_table_keyword);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_keywords{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_organism = "table_organism.txt";
	if ( -e "<".$file_table_organism ) {
		open (SAUVEGARDE,"<".$file_table_organism);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_organism{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_taxonoms = "table_taxonoms.txt";
	if ( -e "<".$file_table_taxonoms ) {
		open (SAUVEGARDE,"<".$file_table_taxonoms);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_taxonoms{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_citation = "table_citation.txt"; ## useless for now
	if ( -e "<".$file_table_citation ) {
		open (SAUVEGARDE,"<".$file_table_citation);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_citation{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}
	my $file_table_property = "table_property.txt";
	if ( -e "<".$file_table_property ) {
		open (SAUVEGARDE,"<".$file_table_property);
		my $read = "";
		while ($read = <SAUVEGARDE>) {
			my @read_split = split(/\t/,$read);
			$table_property{$read_split[0]} = $read_split[1];
		}
		close SAUVEGARDE;
	}

## here to detect and launch insertion oracle generation repeatedly

##use Cwd;

if ($flag_insert_done == 0) {
	my $repertoire = cwd(); ## "/";
	opendir(DIR, $repertoire) || die "$repertoire: $!";
	my @fichiers = readdir(DIR);
	closedir(DIR);

	my @list_interest_item = ();

	# print "\tList current directory : \n";
	foreach my $item_in_dir (@fichiers) {
		# print "\t".$item_in_dir."\n";
		if ($item_in_dir =~ /uniprot_sprot_part(.*).xml/) { push (@list_interest_item,$item_in_dir); }
	}
	print "\n\n#####################################################\n\n";
	print "Selected sources files : \n";
	foreach my $item (@list_interest_item) { print "\t".$item."\n"; }
	print "\n\n#####################################################\n\n";
	# my $test = 1;
	# system "perl XMLtoORACLE_argument.pl ".$test;

	## this element is out of ORACLE inserts generator to avoid issue with primary key of taxons
	my $current_taxon_number = 0;

	print "launch generation of oracle insertions : \n";
	foreach my $item (@list_interest_item) {
		print "\t".$item."\n";
		## system "perl XMLtoORACLE_argument.pl ".$item;
	## }

		##################################################################################
		##################################################################################

			## ## aim of this script_part reading XML of UniprotKB and building ORACLE INSERTIONS

		## the XML database : take PARAMETER !! 'here is item
		my $base_uniprot_xml = "<".$item."";
			
			print "\t\t".$item."\n";
			my $item_num = $item;
			$item_num =~ s/uniprot_sprot_part(.*).xml/$1/;
			print "\t\t".$item_num."\n";

		## the output file for insertions
		my $sortiescript = "insert_ORACLE_part".$item_num.".sql";
		open (SORTIE,">".$sortiescript);
		print SORTIE "";
		close SORTIE;



		## special find tag to navigation in this script : 
		## ## "(!!|??)" for some special comments in code
		## ## "INSERT [entry|accessions|names|protein|gene|genelocation|keyword|evidence|dbreference|property|organism|references|features|sequence|comment]" to go directly to the part dedicaced to insertion generation
		## ## "[protein|gene|gene_location|keyword|dbreference|organism|reference|feature|evidence|sequence|comment] detection" for the event parsing on XML data
		## ## ## default is script begin really with "entry detection" and work on detected and stored data to make insertions when "</entry>" [end of entry]
		## ## flag variables got names upon their roles in detection (declared and instancied empty at beginning)
		## ## storage variables got names (declared and instancied empty at beginning) and commented in code : some majors informations given when data is stored in (* detection)

		## some indications on storage variables
		## ## some arrays contain hashes : a special treatment is done is that case to retrieve data 
		## ## ## and might be factorized (but it is useful for now to get in the codea "readable list of keys")
		## ## some arrays contains elements whioch can be splitted, elements are set in a certain order (may not be commented)

		## ## flags
		my $flag_count = 0; ## tests limit : remove it in whole script when real use !!
		my $maxi_count = 100000;

		my $flag_entry = 0;

		my $flag_protein = 0;
		my $flag_protein_nameType = "";
		my $flag_protein_domainorcomponent = "";

		my $flag_gene = 0;
		my $flag_gene_location = 0;
		my $flag_sequence = 0;
		my $flag_dbreference = 0;

		my $flag_organism = 0;
		my $flag_organism_lineage = 0;

		my $flag_reference = 0;
		my $flag_reference_citation = 0;
		my $flag_reference_List = "";
		my $flag_reference_source = 0;

		my $flag_feature = 0;
		my $flag_feature_location = 0;

		my $flag_comment = 0;
		my $flag_comment_subcellularlocation = 0;
		my $flag_comment_isoform = 0;
		my $flag_comment_interactant = 0;
		my $flag_comment_location = 0;
		my $flag_comment_conflict = 0;
		my $flag_comment_kinetics = 0;
		my $flag_comment_absorption = 0;


		## ## ## the following variables aim to store informations when reading the XML file, use to generate INSERTION
			## for entry table and more's
			my %current_entry = (); ## created (YYYY-MM-DD), modified (YYYY-MM-DD), version, dataset
			my @list_accession = ();
			my @list_name = ();
			
			## for protein table
			my @list_protein_names = ();
			my $current_proteinexistence = "";
			## for keyword table
			my @list_keywords = ();
			## for gene table
			my @list_genes = ();
			## for gene_location table
			my $current_genelocation_type = "";
			my @list_genelocations = ();
			## for feature table
			my @list_feature = ();
			my %current_feature = (); ## type, status?, id?, description?, evidence?, ref?, original?, variation?, begin, end?
			## for evidence table
			my @list_evidences = ();
			## for sequence table
			my %current_sequence = (); ## checksum, fragment?, length, mass, modified (YYYY-MM-DD), sequence, version, precursor?
			
			## for dbreference table
			my @list_dbreferences = ();
			my @list_dbproperty = ();
			
			## for organism table
			my @list_organism = ();
			my %current_organism = (); ## key, names, lineage, dbrefer
			my @list_organism_names = ();
			my @list_organism_taxons = ();
			my @list_organism_dbrefs = ();
			## my $current_taxon_number = 0;
			
			## for reference table
			my @list_reference = ();
			my %current_reference = (); ## key, citation_volume, citation_type, citation_name, citation_last, citation_first, citation_date (YYYY-MM-DD|YYYY-MM|YYYY), citation_db, citation_title, citation_dbrefer, citation_lists, scopes, sources
			my @list_reference_dbrefs = ();
			my @list_reference_Lists = ();
			my @list_reference_scopes = ();
			my @list_reference_sources = ();
			
			## for comment table
			my @list_comment = ();
			my @list_comment_events = ();
			my @list_comment_isoform_id = ();
			my @list_comment_isoform_name = ();
			my @list_comment_subcellularlocations = ();
			my @list_comment_location = ();
			my $tmp_comment_begin = 0;
			my $tmp_comment_end = 0;
			my $tmp_comment_begin_status = " ";
			my $tmp_comment_end_status = " ";
			my %current_comment_isoform = (); ## id, name, note, sequence_type, sequence_ref
			my @list_comment_kinetics_km = ();
			my @list_comment_kinetics_vmax = ();
			my @list_comment_isoform = ();
			my %current_comment = ();
			
		## opening the XML database
		open(XML_UNIPROT,$base_uniprot_xml);
		## reading file buffer
		my $line = "";
			
		while ( ( ($flag_entry == 0) || ($flag_count < $maxi_count) ) && ($line = <XML_UNIPROT>) ) {
			$flag_count++;## print $flag_count."\t".$line;
			## if ($flag_count <= 370) { print $flag_count."\t".$line; }
			if ($line =~ /'/) { $line =~ s/'/"/g; } ## $evidence =~ s/'/"/g;
			if ($line =~ /&gt;/) { $line =~ s/&gt;/>/g; }
			if ($line =~ /&lt;/) { $line =~ s/&lt;/</g; }
			if ($line =~ /&quote;/) { $line =~ s/&quote;/"/g; }
			if ($line =~ /&quot;/) { $line =~ s/&quot;/"/g; }
			if ($line =~ /&amp;/) { $line =~ s/&amp;/"amp"/g; }
			## cat insert_ORACLE_part* | grep "&" > esperluette.txt ## to detect if '&' is present is SQL files
			#########################################################################################################
			#########################################################################################################
			## entry detection
			if ($line =~ /<entry created="(.*)" dataset="(.*)" modified="(.*)" version="(.*)">/) {
				$flag_entry = 1;
				$current_entry{"version"} = $4;
				$current_entry{"created"} = $1;
				$current_entry{"modified"} = $3;
				$current_entry{"dataset"} = substr $2,0,1;
			}
			## when ending an entry, generate the insert assesment
			if ( ($flag_entry == 1) && ($line =~ /<\/entry>/) ) {
				$flag_entry = 0;
				
				#########################################################################################################
				## INSERT entry
				my $request = "INSERT INTO UNIPROT_ENTRY (ACCESSION,NAME,DATASET,CREATED,MODIFIED,VERSION)\n\tVALUES (";
				$request .= "\'".$list_accession[0]."\',\n\t\t\'".$list_name[0]."\',\n\t\t\'".$current_entry{"dataset"}."\',\n\t\t";
				$request .= "TO_DATE(\'".$current_entry{"created"}."\',\'YYYY-MM-DD\'),\n\t\tTO_DATE(\'".$current_entry{"modified"}."\',\'YYYY-MM-DD\'),\n\t\t";
				$request .= "".$current_entry{"version"}.");";
				open (SORTIE,">>".$sortiescript);
				print SORTIE $request."\n\n";
				close SORTIE;
				$request = "";
				
				#########################################################################################################
				## INSERT accessions
				my $length = @list_accession;
				if ($length > 0) {
					foreach my $access (@list_accession) {
						if ($list_accession[0] ne $access) {
							$request = "INSERT INTO UNIPROT_ENTRY_MOREACCES (ENTRY_ACCESSION,OTHER_ACC) \n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',\'".$access."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
						}
					}
				}
				
				#########################################################################################################
				## INSERT names
				$length = @list_name;
				if ($length > 0) {
					foreach my $name (@list_name) {
						if ($list_accession[0] ne $name) {
							$request = "INSERT INTO UNIPROT_ENTRY_MORENAMES (ENTRY_ACCESSION,OTHER_NAME) \n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',\'".$name."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
						}
					}
				}
				
				#########################################################################################################
				## INSERT protein
				my $control = 0;
				my $ref_prot = 0;
				foreach my $prot (@list_protein_names) {
					my $name_type_id = 0;
					my $evidence = "";
					if ($prot =~ /:::::/) {
						my @list_prot = split(/:::::/,$prot); ## type of protein
						$name_type_id = $list_prot[0];
						if ($list_prot[0] =~ /[1-3]/) { $ref_prot = $list_prot[1]; }
						else { $evidence = $list_prot[1]; }
					} else { $name_type_id = $prot; }
					
					if ($name_type_id =~ /[cd]\/\//) { ## component or domain naming
						my @name_split = split(/\/\//,$name_type_id);
						$name_type_id = $name_split[1];
						my $domainorcomponent = $name_split[0];
						# $evidence =~ s/'/"/g;
						$request = "INSERT INTO UNIPROT_PROTEIN_DOMORCOMP (ENTRY_ACCESSION,DOMAIN_OR_COMPONENT,NAME_TYPE_ID,EVIDENCE,PROTEIN_REF)\n\tVALUES (";
						$request .= "\'".$list_accession[0]."\',\'".$domainorcomponent."\',\'".$name_type_id."\',\'".$evidence."\',".$ref_prot.");";
					} else {
						my $table_name = "UNIPROT_PROTEIN";
						if ($control == 0) { ## the first item
							# $evidence =~ s/'/"/g;
							$request = "INSERT INTO UNIPROT_PROTEIN (ENTRY_ACCESSION,NAME_TYPE_ID,EVIDENCE,PROTEIN_REF,PROTEINEXISTENCE_ID)\n\tVALUES (";
							$request .= "\'".$list_accession[0]."\',\'".$name_type_id."\',\'".$evidence."\',".$ref_prot.",\'".$current_proteinexistence."\');";
							$control++;
						} else { ## the others
							# $evidence =~ s/'/"/g;
							$request = "INSERT INTO UNIPROT_MORE_EVIDENCE (ENTRY_ACCESSION,NAME_TYPE_ID,EVIDENCE,PROTEIN_REF)\n\tVALUES (";
							$request .= "\'".$list_accession[0]."\',\'".$name_type_id."\',\'".$evidence."\',".$ref_prot.");";
						} 

					}
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
				}
				
				#########################################################################################################
				## INSERT gene
				foreach my $gene (@list_genes) {
					my @item_gene = split(/:::::/,$gene); ## gene id ::::: gene name
					# %table_genegene
					my $currentgeneid = 0;
					foreach my $genegene (sort keys %table_genegene) # to get the highest index / id
						{ if ($table_genegene{$genegene} > $currentgeneid) { $currentgeneid = $table_genegene{$genegene}; } }
					if (!exists $table_genegene{$item_gene[1]}) { ## if gene does not exist in databse : insert it and remember it
						$currentgeneid++;
						$request = "INSERT INTO UNIPROT_GENE (GENE_NAME,GENE_TYPE_ID,EVIDENCE)\n\tVALUES(";
						# $item_gene[1] =~ s/'/"/g;
						$request .= "\'".$item_gene[1]."\',\'".$item_gene[0]."\',\'\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
						$table_genegene{$item_gene[1]} = $currentgeneid;
						open (SAUVEGARDE,">>".$file_table_genegene);
						print SAUVEGARDE $item_gene[1]."\t".$currentgeneid."\t".$item_gene[0]."\n";
						close SAUVEGARDE;
					}
					## in any case : relation between entry table and gene table
					$request = "INSERT INTO UNIPROT_ENTRY2GENE (ENTRY_ACCESSION,GENE_NAME,GENE_TYPE_ID)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',\'".$item_gene[1]."\',\'".$item_gene[0]."\');";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
				}
				
				#########################################################################################################
				## INSERT genelocation
				if ($#list_genelocations > -1) { ## at least a genelocation is specified
					foreach my $genelocation (@list_genelocations) {
						my @item_genelocation = split(/:::::/,$genelocation); ## genelocation name ::::: genelocation type
						if (!exists $table_genelocs{$item_genelocation[1]}) {
							$request = "INSERT INTO UNIPROT_GENELOCATION (NAME,TYPE_ID,STATUS_TYPE)\n\tVALUES(";
							$request .= "\'".$item_genelocation[0]."\',\'".$item_genelocation[1]."\',\'known\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
							$table_genelocs{$item_genelocation[1]} = $item_genelocation[0];
							open (SAUVEGARDE,">>".$file_table_geneloc);
							print SAUVEGARDE $item_genelocation[1]."\t".$item_genelocation[0]."\n";
							close SAUVEGARDE;
						}
						$request = "INSERT INTO UNIPROT_ENTRY2GENELOCATION (ENTRY_ACCESSION,GENE_NAME,GENELOCATION_TYPE)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',\'".$item_genelocation[0]."\',\'".$item_genelocation[1]."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
				} ## else { ; } ## no gene_location is specified
				## by default considered it as 'unknown' genelocation_type if not specified in genelocation table for a given gene name
				
				
				#########################################################################################################
				## INSERT keyword
				foreach my $keyword (@list_keywords) {
					my @item_keyword = split(/:::::/,$keyword); ## keyword id ::::: keyword description / content
					if (!exists $table_keywords{$item_keyword[0]}) {
						$request = "INSERT INTO UNIPROT_KEYWORDLIST (ID,KEYWORD)\n\tVALUES(";
						$request .= "\'".$item_keyword[0]."\',\'".$item_keyword[1]."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
						$table_keywords{$item_keyword[0]} = $item_keyword[1]; 
						open (SAUVEGARDE,">>".$file_table_keyword);
						print SAUVEGARDE $item_keyword[0]."\t".$item_keyword[1]."\n";
						close SAUVEGARDE;
					}
					$request = "INSERT INTO UNIPROT_ENTRY2KEYWORD (ENTRY_ACCESSION,KEYWORD_ID)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',\'".$item_keyword[0]."\');";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
				}
				
				#########################################################################################################
				## INSERT evidence
				foreach my $evidence (@list_evidences) {
					my @item_evidence = split(/:::::/,$evidence); ## evidence key ::::: evidence category ::::: evidence date ::::: evidence attribute
					$request = "INSERT INTO UNIPROT_EVIDENCE (ENTRY_ACCESSION,EVIDENCE_KEY,CATEGORY,TYPE_ID,EVIDENCE_DATE,ATTRIBUTE)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',\'".$item_evidence[0]."\',\'".$item_evidence[1]."\',\'".$item_evidence[2]."\',TO_DATE(\'".$item_evidence[3]."\',\'YYYY-MM-DD\'),\'".$item_evidence[4]."\');";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
				}
				
				
				#########################################################################################################
				## INSERT dbreferences + INSERT property (main dbReference in UniprotKB entry)
				## MODIFIED VERSION TO GET ONLY REFERENCES TO InterPro, PDB AND GO references
				foreach my $dbref (@list_dbreferences) {
					my @item_dbref = split(/:::::/,$dbref); ## dbreference type ::::: dbreference id ::::: dbreference key
					# if ($item_dbref[0] =~ /&gt;/) { $item_dbref[0] =~ s/&gt;/>/g; }
					# if ($item_dbref[0] =~ /&lt;/) { $item_dbref[0] =~ s/&lt;/</g; }
					# if ($item_dbref[1] =~ /&gt;/) { $item_dbref[1] =~ s/&gt;/>/g; }
					# if ($item_dbref[1] =~ /&lt;/) { $item_dbref[1] =~ s/&lt;/</g; }
					if ( ( ($item_dbref[0] =~ /InterPro/) || ($item_dbref[0] =~ /PDB/) )
							|| ($item_dbref[0] =~ /Go/) ) { ##  || ($item_dbref[0] =~ /PDBsum/) )
						## !! create insertion only if connected / related to InterPro || PDB database !!
						## $request = "INSERT INTO UNIPROT_DBREFERENCE (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES(";
						if ($item_dbref[0] =~ /InterPro/) { $request = "INSERT INTO UNIPROT_DBREF_IPR (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES("; }
						if ($item_dbref[0] =~ /PDB/) { $request = "INSERT INTO UNIPROT_DBREF_PDB (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES("; }
						if ($item_dbref[0] =~ /Go/) { $request = "INSERT INTO UNIPROT_DBREF_GO (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES("; }
						$request .= "\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].",\'\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						# $request = "";
						## $request = "INSERT INTO UNIPROT_ENTRY2DBREFERENCE (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES(";
						if ($item_dbref[0] =~ /InterPro/) { $request = "INSERT INTO UNIPROT_ENTRY2DBREF_IPR (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES("; }
						if ($item_dbref[0] =~ /PDB/) { $request = "INSERT INTO UNIPROT_ENTRY2DBREF_PDB (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES("; }
						if ($item_dbref[0] =~ /Go/) { $request = "INSERT INTO UNIPROT_ENTRY2DBREF_GO (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES("; }
						$request .= "\'".$list_accession[0]."\',\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].");";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
						
						open (IPRPDBGOSELECTION,">>".$selections);
						print IPRPDBGOSELECTION $item_dbref[0]."\t".$item_dbref[1]."\t".$list_accession[0]."\t".($compteur++)."\n";
						close(IPRPDBGOSELECTION);
					}
				}
				foreach my $dbpro (@list_dbproperty) {
					my @item_prop = split(/:::::/,$dbpro); ## property db_type ::::: property db_id ::::: property db_key ::::: property type ::::: property value
					# $item_prop[4] =~ s/'/"/g;
					if ( ( ($item_prop[0] =~ /InterPro/) || ($item_prop[0] =~ /PDB/) )
							|| ($item_prop[0] =~ /Go/) ) { ##  || ($item_prop[0] =~ /PDBsum/) )
						## !! create insertion only if connected / related to InterPro || PDB database !!
						my $currentpropertyid = 0;
						foreach my $property (sort keys %table_property) # to get the highest index / id
							{ if ($table_property{$property} > $currentpropertyid) { $currentpropertyid = $table_property{$property}; } }
						if (exists $table_property{$item_prop[3]}) { $currentpropertyid = $table_property{$item_prop[3]}; }
						else {
							$currentpropertyid++;
							$table_property{$item_prop[3]} = $currentpropertyid;
							open (SAUVEGARDE,">>".$file_table_property);
							print SAUVEGARDE $item_prop[3]."\t".$currentpropertyid."\n";
							close SAUVEGARDE;
							$request = "INSERT INTO UNIPROT_PROPERTY_TYPE (ID,TYPE_ID)\n\tVALUES(";
							$request .= "\'".$currentpropertyid."\',\'".$item_prop[3]."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
						}
						$request = "INSERT INTO UNIPROT_PROPERTY (DBREF_TYPE,DBREF_ID,DBREF_KEY,TYPE_ID,VALUE_CONTENT)\n\tVALUES(";
						$request .= "\'".$item_prop[0]."\',\'".$item_prop[1]."\',\'".$item_prop[2]."\',\'".$currentpropertyid."\',\'".$item_prop[4]."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
				}
				
				#########################################################################################################
				## INSERT dbreferences + INSERT property (main dbReference in UniptoyKB entry)
				## COMMENTED ORIGINAL VERSION
				# foreach my $dbref (@list_dbreferences) {
					# my @item_dbref = split(/:::::/,$dbref); ## dbreference type ::::: dbreference id ::::: dbreference key
					# if ($item_dbref[0] =~ /InterPro/) {
						## !! create insertion only if connected / related to InterPro database !!
						# $request = "INSERT INTO UNIPROT_DBREFERENCE (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES(";
						# $request .= "\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].",\'\');";
						# open (SORTIE,">>".$sortiescript);
						# print SORTIE $request."\n\n";
						# close SORTIE;
						# $request = "INSERT INTO UNIPROT_ENTRY2DBREFERENCE (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES(";
						# $request .= "\'".$list_accession[0]."\',\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].");";
						# open (SORTIE,">>".$sortiescript);
						# print SORTIE $request."\n\n";
						# close SORTIE;
						# $request = "";
					# }
				# }
				# foreach my $dbpro (@list_dbproperty) {
					# my @item_prop = split(/:::::/,$dbpro); ## property db_type ::::: property db_id ::::: property db_key ::::: property type ::::: property value
					# if ($item_prop[0] =~ /InterPro/) {
						## !! create insertion only if connected / related to InterPro database !!
						# my $currentpropertyid = 0;
						# foreach my $property (sort keys %table_property) # to get the highest index / id
							# { if ($table_property{$property} > $currentpropertyid) { $currentpropertyid = $table_property{$property}; } }
						# if (exists $table_property{$item_prop[3]}) { $currentpropertyid = $table_property{$item_prop[3]}; }
						# else {
							# $currentpropertyid++;
							# $table_property{$item_prop[3]} = $currentpropertyid;
							# open (SAUVEGARDE,">>table_property.txt");
							# print SAUVEGARDE $item_prop[3]."\t".$currentpropertyid."\n";
							# close SAUVEGARDE;
							# $request = "INSERT INTO UNIPROT_PROPERTY_TYPE (ID,TYPE_ID)\n\tVALUES(";
							# $request .= "\'".$currentpropertyid."\',\'".$item_prop[3]."\');";
							# open (SORTIE,">>".$sortiescript);
							# print SORTIE $request."\n\n";
							# close SORTIE;
							# $request = "";
						# }
						# $request = "INSERT INTO UNIPROT_PROPERTY (DBREF_TYPE,DBREF_ID,DBREF_KEY,TYPE_ID,VALUE_CONTENT)\n\tVALUES(";
						# $request .= "\'".$item_prop[0]."\',\'".$item_prop[1]."\',\'".$item_prop[2]."\',\'".$currentpropertyid."\',\'".$item_prop[4]."\');";
						# open (SORTIE,">>".$sortiescript);
						# print SORTIE $request."\n\n";
						# close SORTIE;
						# $request = "";
					# }
				# }
				
				#########################################################################################################
				## INSERT organism : key, names, lineage, dbrefer
				foreach my $item_orga (@list_organism) {
					my @item_orga = @{$item_orga};
					my %item_orga = ();## key, names, lineage, dbrefer
					my $memo = ""; ## retrieving the hash of current organism
					foreach my $valeur (@item_orga) {
						if ($memo =~ /^$/) { $memo = $valeur; }
						else { 
							if ($memo =~ /^key$/) { $item_orga{"key"} = $valeur;$memo = ""; }
							if ($memo =~ /^names$/) { $item_orga{"names"} = $valeur;$memo = ""; }
							if ($memo =~ /^lineage$/) { $item_orga{"lineage"} = $valeur;$memo = ""; }
							if ($memo =~ /^dbrefer$/) { $item_orga{"dbrefer"} = $valeur;$memo = ""; }
						}
					}
					## treatment for current organism
					## ## organism : lineage / taxon treament
					my $current_organism_taxon = 0;
					if (exists $item_orga{"lineage"}) { 
						# $request = "INSERT INTO UNIPROT_LINEAGE (ORGANISM_NAME,ORGANISM_NAME_TYPE,LAST_TAXON_ID)\n\tVALUES(";
						# $request .= "\'".$current_organism_name."\',\'".$current_organism_name_type."\',\'".$current_taxon_number."\');";
						# open (SORTIE,">>".$sortiescript);
						# print SORTIE $request."\n\n";
						# close SORTIE;
						# $request = "";
						my @current_organism_taxons = @{$item_orga{"lineage"}};
						foreach my $taxon (@current_organism_taxons) {
							if (exists $table_taxonoms{$taxon}) 
								{ $current_organism_taxon = $table_taxonoms{$taxon}; }
							else {
								$request = "INSERT INTO UNIPROT_TAXON (ID,NAME,PARENT_ID)\n\tVALUES(";
								$request .= "\'".$current_taxon_number."\',\'".$taxon."\',\'";
								if ($taxon eq $current_organism_taxons[0]) { $request .= "-1\');"; }
								else { $request .= $current_organism_taxon."\');"; }
								open (SORTIE,">>".$sortiescript);
								print SORTIE $request."\n\n";
								close SORTIE;
								$request = "";
								$current_organism_taxon = $current_taxon_number;
								$table_taxonoms{$taxon} = $current_taxon_number;
								open (SAUVEGARDE,">>".$file_table_taxonoms);
								print SAUVEGARDE $taxon."\t".$current_taxon_number."\n";
								close SAUVEGARDE;
								$current_taxon_number++;
							}
						} ## end foreach $taxon (@current_organism_taxons)
					}					
					if (exists $item_orga{"names"}) {
						## organism : name treatment
						my @current_organism_names = @{$item_orga{"names"}};
						my $current_organism_key = $item_orga{"key"};
						my @split_name = split(/:::::/,$current_organism_names[0]); ## organism type ::::: organism name
						my $current_organism_name_type = $split_name[0];
						my $current_organism_name = $split_name[1];
						# $current_organism_name =~ s/'/"/g;
						my $currentorgid = 0;
						foreach my $organism (sort keys %table_organism) # to get the highest index / id
								{ if ($table_organism{$organism} > $currentorgid) { $currentorgid = $table_organism{$organism}; } }
						if (!exists $table_organism{$current_organism_name}) {
							$currentorgid++;
							$request = "INSERT INTO UNIPROT_ORGANISM (NAME,NAME_TYPE,ORGANISM_KEY,LAST_TAXON_ID)\n\tVALUES(";
							$request .= "\'".$current_organism_name."\',\'".$current_organism_name_type."\',".$current_organism_key.",\'".$current_organism_taxon."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
							## ## secondary names for organism
							if ($#current_organism_names  > 0) { ## really more than one name (first name at position [0])
								foreach my $next_name (@current_organism_names) {
									@split_name = split(/:::::/,$next_name); ## organism type ::::: organism name
									if ($split_name[1] ne $current_organism_name) {
										# $current_organism_name =~ s/'/"/g;
										# $split_name[1] =~ s/'/"/g;
										$request = "INSERT INTO UNIPROT_ORGANISM_MORENAMES (PRIMARY_NAME,PRIMARY_TYPE,SECONDARY_NAME,SECONDARY_TYPE)\n\tVALUES(";
										$request .= "\'".$current_organism_name."\',\'".$current_organism_name_type."\',\'".$split_name[1]."\',\'".$split_name[0]."\');";
										open (SORTIE,">>".$sortiescript);
										print SORTIE $request."\n\n";
										close SORTIE;
										$request = "";
									}
								}
							}
							$table_organism{$current_organism_name} = $currentorgid;
							open (SAUVEGARDE,">>".$file_table_organism);
							print SAUVEGARDE $current_organism_name."\t".$currentorgid."\t".$current_organism_name_type."\n";
							close SAUVEGARDE;
						}
						$request = "INSERT INTO UNIPROT_ENTRY2ORGANISM (ENTRY_ACCESSION,NAME)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',\'".$current_organism_name."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";

						## ## organism : dbreferences treatment
						if (exists $item_orga{"dbrefer"}) {
							my @current_organism_dbrefer = @{$item_orga{"dbrefer"}};
							## this data putted in evidence of dbreference table to know where this dbreference comes from (here organism)
							my $insertion_organism = "organism:::::".$current_organism_name.":::::".$current_organism_name_type.":::::".$current_organism_key;
							foreach my $dbref (@current_organism_dbrefer) {
								my @item_dbref = split(/:::::/,$dbref); ## dbreference type ::::: dbreference id ::::: dbreference key
								# if ($item_dbref[0] =~ /&gt;/) { $item_dbref[0] =~ s/&gt;/>/g; }
								# if ($item_dbref[0] =~ /&lt;/) { $item_dbref[0] =~ s/&lt;/</g; }
								# if ($item_dbref[1] =~ /&gt;/) { $item_dbref[1] =~ s/&gt;/>/g; }
								# if ($item_dbref[1] =~ /&lt;/) { $item_dbref[1] =~ s/&lt;/</g; }
								if ($item_dbref[0] =~ /InterPro/) {
									$request = "INSERT INTO UNIPROT_DBREFERENCE (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES(";
									$request .= "\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].",\'".$insertion_organism."\');";
									open (SORTIE,">>".$sortiescript);
									print SORTIE $request."\n\n";
									close SORTIE;
									# $request = "";
									$request = "INSERT INTO UNIPROT_ENTRY2DBREFERENCE (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES(";
									$request .= "\'".$list_accession[0]."\',\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].");";
									open (SORTIE,">>".$sortiescript);
									print SORTIE $request."\n\n";
									close SORTIE;
									$request = "";
								}
							}
						}
					}
				} ## END INSERT organism
				
				#########################################################################################################
				## INSERT references : [..]
				## key, citation_volume, citation_type, citation_name, citation_last, citation_first, citation_date, 
				## citation_db, citation_title, citation_dbrefer, citation_lists, scopes, sources
				## citation_publisher, citation_city, citation_locator, citation_number, citation_country
				foreach my $item_reference (@list_reference) {
					my @item_reference = @{$item_reference};
					my %item_reference = ();## [..]
					my $memo = ""; ## retrieving the hash of current reference
					
					foreach my $valeur (@item_reference) {
						if ($memo =~ /^$/) { $memo = $valeur; }
						else {
							if ($memo =~ /^key$/) { $item_reference{"key"} = $valeur;$memo = ""; }	
							if ($memo =~ /^citation_type$/) { $item_reference{"citation_type"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_title$/) { $item_reference{"citation_title"} = $valeur;$memo = ""; }

							if ($memo =~ /^citation_volume$/) { $item_reference{"citation_volume"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_name$/) { $item_reference{"citation_name"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_last$/) { $item_reference{"citation_last"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_first$/) { $item_reference{"citation_first"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_date$/) { $item_reference{"citation_date"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_db$/) { $item_reference{"citation_db"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_dbrefer$/) { $item_reference{"citation_dbrefer"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_lists$/) { $item_reference{"citation_lists"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_publisher$/) { $item_reference{"citation_publisher"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_city$/) { $item_reference{"citation_city"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_locator$/) { $item_reference{"citation_locator"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_number$/) { $item_reference{"citation_number"} = $valeur;$memo = ""; }
							if ($memo =~ /^citation_country$/) { $item_reference{"citation_country"} = $valeur;$memo = ""; }
							if ($memo =~ /^scopes$/) { $item_reference{"scopes"} = $valeur;$memo = ""; }
							if ($memo =~ /^sources$/) { $item_reference{"sources"} = $valeur;$memo = ""; }
						}
					}
					
					## reference table : fusion with citation table on 20090327 (uniprotKB schema changes)
					my $current_reference_key = $item_reference{"key"};
					my $current_reference_scope = $item_reference{"scopes"}[0];
					# $request = "INSERT INTO UNIPROT_REFERENCE (ENTRY_ACCESSION,REFERENCE_KEY,EVIDENCE,SCOPE_CONTENT)\n\tVALUES(";
					# $request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'\',\'".$current_reference_scope."\');";
					# open (SORTIE,">>".$sortiescript);
					# print SORTIE $request."\n\n";
					# close SORTIE;
					# $request = "";
					## references : more scopes table
					my @list_current_reference_scope = @{$item_reference{"scopes"}};
					if ($#list_current_reference_scope > 0) { ## really more than one scope (first scope at position [0])
						foreach my $next_scope (@list_current_reference_scope) {
							if ($next_scope ne $current_reference_scope) {
								$request = "INSERT INTO UNIPROT_CITATION_MORESCOPE (ENTRY_ACCESSION,CITATION_KEY,SCOPE_CONTENT)\n\tVALUES(";
								$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$next_scope."\');";
								open (SORTIE,">>".$sortiescript);
								print SORTIE $request."\n\n";
								close SORTIE;
								$request = "";
							}
						}
					}
					## references : sourcedata table
					if (exists $item_reference{"sources"}) {
						my @current_reference_sources = @{$item_reference{"sources"}};
						foreach my $next_source (@current_reference_sources ) {
							my @split_name = split(/:::::/,$next_source); ## source data type ::::: source data content
							$request = "INSERT INTO UNIPROT_SOURCEDATA (ENTRY_ACCESSION,CITATION_KEY,TYPE_ID,CONTENT)\n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$split_name[0]."\',\'".$split_name[1]."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
						}
					}
					## references : lists table
					if (exists $item_reference{"citation_lists"}) {
						my @current_reference_lists = @{$item_reference{"citation_lists"}};
						foreach my $item_list (@current_reference_lists) {
							my @item_list = split(/:::::/,$item_list); ## name ::::: editor/author ::::: consortium/person
							my $currentnameid = 0;
							foreach my $name (sort keys %table_namelist) # to get the highest index / id
								{ if ($table_namelist{$name} > $currentnameid) { $currentnameid = $table_namelist{$name}; } }
							if (exists $table_namelist{$item_list[0]}) { $currentnameid = $table_namelist{$item_list[0]}; }
							else {
								$currentnameid++;
								$table_namelist{$item_list[0]} = $currentnameid;
								open (SAUVEGARDE,">>".$file_table_namelist);
								print SAUVEGARDE $item_list[0]."\t".$currentnameid."\n";
								close SAUVEGARDE;
								$request = "INSERT INTO UNIPROT_NAMELIST (ID,NAME,TYPE_OF,TYPE_DEF)\n\tVALUES(";
								$request .= "\'".$currentnameid."\',\'".$item_list[0]."\',\'".$item_list[1]."\',\'".$item_list[2]."\');";
								open (SORTIE,">>".$sortiescript);
								print SORTIE $request."\n\n";
								close SORTIE;
								$request = "";
							}
							# $item_list[0] =~ s/'/"/g;
							$request = "INSERT INTO UNIPROT_CITATION2NAME (ENTRY_ACCESSION,CITATION_KEY,NAME_ID)\n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$currentnameid."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
						}
					}
					## references : dbref table
					if (exists $item_reference{"citation_dbrefer"}) {
						my @current_reference_dbref = @{$item_reference{"citation_dbrefer"}};
						foreach my $dbref (@current_reference_dbref) {
							## this data putted in evidence of dbreference table to know where this dbreference comes from (here reference/citation)
							my $insertion_reference = "citation:::::".$item_reference{"citation_name"}.":::::".$item_reference{"citation_type"}.":::::".$current_reference_key;
							my @item_dbref = split(/:::::/,$dbref); ## dbreference type ::::: dbreference id ::::: dbreference key ;; here ':::::' separator because of some DOI uses '::'
							if ($item_dbref[0] =~ /InterPro/) {
								$request = "INSERT INTO UNIPROT_DBREFERENCE (DBREF_TYPE,DBREF_ID,DBREF_KEY,EVIDENCE)\n\tVALUES(";
								$request .= "\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].",\'".$insertion_reference."\');";
								open (SORTIE,">>".$sortiescript);
								print SORTIE $request."\n\n";
								close SORTIE;
								# $request = "";
								$request = "INSERT INTO UNIPROT_ENTRY2DBREFERENCE (ENTRY_ACCESSION,DBREF_TYPE,DBREF_ID,DBREF_KEY)\n\tVALUES(";
								$request .= "\'".$list_accession[0]."\',\'".$item_dbref[0]."\',\'".$item_dbref[1]."\',".$item_dbref[2].");";
								open (SORTIE,">>".$sortiescript);
								print SORTIE $request."\n\n";
								close SORTIE;
								$request = "";
							}
						}
					}
					
					## !! !! thinking about avoiding  redondancy in base (here about citations / publications) : how to detect it and remember ??
					$request = "INSERT INTO UNIPROT_ENTRY2CITATION (ENTRY_ACCESSION,CITATION_KEY)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',".$current_reference_key.");";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
					## references : citation table  : type differs == insertion differ !! ## 
					## ## reference citation type 1
					if ( $item_reference{"citation_type"} eq "1") { ## "book"
						# $item_reference{"citation_title"} =~ s/'/"/g;
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,SCOPE_CONTENT,TITLE,CITATION_TYPE_ID,CITATION_DATE"
							.",PUBLISHER,NAME,LAST_PAGE,FIRST_PAGE,CITY)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$current_reference_scope.",\'".$item_reference{"citation_type"}."\',\'".$item_reference{"citation_title"}
							."\',TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY\'),\'".$item_reference{"citation_publisher"}
							."\',\'".$item_reference{"citation_name"}."\',\'".$item_reference{"citation_last"}
							."\',\'".$item_reference{"citation_first"}."\',\'".$item_reference{"citation_city"}."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 2
					if ( $item_reference{"citation_type"} eq "2") { ## "journal article"
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE"
							.",CITATION_DATE,VOLUME,NAME,LAST_PAGE,FIRST_PAGE)\n\tVALUES(";
						if (!exists $item_reference{"citation_title"}) { $item_reference{"citation_title"} = "ERRATUM (scope)"; }
						## { print "reference_citation type 2, TITLE does not exists entry N°".$list_accession[0]."\n".$item_reference{"citation_date"}."\n".$item_reference{"citation_first"}."\n"; }
						## taking ERRATUM scope in case of entry accession P02708 in Uniprot KB !!
						# $item_reference{"citation_title"} =~ s/'/"/g;
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$item_reference{"citation_title"}
							."\',TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY\'),\'".$item_reference{"citation_volume"}
							."\',\'".$item_reference{"citation_name"}."\',\'".$item_reference{"citation_last"}
							."\',\'".$item_reference{"citation_first"}."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 3
					if ( $item_reference{"citation_type"} eq "3") { ## "online journal article"
						# $item_reference{"citation_title"} =~ s/'/"/g;
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE"
							.",NAME,CITATION_LOCATOR)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$item_reference{"citation_title"}
							."\',\'".$item_reference{"citation_name"}."\',\'".$item_reference{"citation_locator"}."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 4
					if ( $item_reference{"citation_type"} eq "4") { ## "patent"
						# $item_reference{"citation_title"} =~ s/'/"/g;
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE"
							.",CITATION_DATE,PATENT_NUMBER)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$item_reference{"citation_title"}
							."\',TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY-MM-DD\'),\'".$item_reference{"citation_number"}."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 5
					if ( $item_reference{"citation_type"} eq "5") { ## "submission" / database
						# $item_reference{"citation_title"} =~ s/'/"/g;
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE"
							.",CITATION_DATE,CITATION_DB_NAME)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$item_reference{"citation_title"}
							."\',TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY-MM\'),\'".$item_reference{"citation_bd"}."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 6
					if ( $item_reference{"citation_type"} eq "6") { ## "thesis"
						# $item_reference{"citation_title"} =~ s/'/"/g;
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE"
							.",CITATION_DATE,COUNTRY,CITY,INSTITUTE)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$item_reference{"citation_title"}
							."\',TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY\'),\'".$item_reference{"citation_country"}
							."\',\'".$item_reference{"citation_city"}."\',\'".$item_reference{"citation_institute"}."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 7
					if ( $item_reference{"citation_type"} eq "7") { ## "unpublished observations"
						my $citation_title = (exists $item_reference{"citation_title"})?$item_reference{"citation_title"}:"no title";
						# $citation_title =~ s/'/"/g;
						my $insert_date = "TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY\')";
						if ($item_reference{"citation_date"} =~ /[0-9]{4}-[0-9]{2}/) 
							{ $insert_date = "TO_DATE(\'".$item_reference{"citation_date"}."\',\'YYYY-MM\')";; }
						$request = "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE"
							.",CITATION_DATE)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$citation_title
							."\',TO_DATE(\'".$insert_date." );";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
					## ## reference citation type 8
					if ( $item_reference{"citation_type"} eq "8") { ## "unpublished RESULTS"
						my $citation_title = (exists $item_reference{"citation_title"})?$item_reference{"citation_title"}:"no title";
						# $citation_title =~ s/'/"/g;
						$request = "-- PLEASE TAKE ATTENTION TO THIS INSERTION : NOT DEFINE IN CONVERSION !!";
						$request .= "INSERT INTO UNIPROT_CITATION (ENTRY_ACCESSION,CITATION_KEY,CITATION_TYPE_ID,SCOPE_CONTENT,TITLE)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',".$current_reference_key.",\'".$item_reference{"citation_type"}."\',\'".$current_reference_scope."\',\'".$item_reference{"citation_title"}."\' );";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
					}
				} ## END INSERT reference
				
				#########################################################################################################
				## INSERT features : [..]
				## type, status?, id?, description?, evidence?, ref?, original?, variation?, begin, end?
				foreach my $item_feature (@list_feature) {
					my @item_feature = @{$item_feature};
					my %item_feature = ();## [..]
					my $memo = ""; ## retrieving the hash of current feature
					
					foreach my $valeur (@item_feature) {
						if ($memo =~ /^$/) { $memo = $valeur; }
						else {
							if ($memo =~ /^type$/)		{ $item_feature{"type"} = $valeur;$memo = ""; }	
							if ($memo =~ /^status$/)	{ $item_feature{"status"} = $valeur;$memo = ""; }
							if ($memo =~ /^id$/)		{ $item_feature{"id"} = $valeur;$memo = ""; }
							if ($memo =~ /^description$/) { $item_feature{"description"} = $valeur;$memo = ""; }
							if ($memo =~ /^evidence$/)	{ $item_feature{"evidence"} = $valeur;$memo = ""; }
							if ($memo =~ /^ref$/)		{ $item_feature{"ref"} = $valeur;$memo = ""; }
							if ($memo =~ /^original$/)	{ $item_feature{"original"} = $valeur;$memo = ""; }
							if ($memo =~ /^variation$/)	{ $item_feature{"variation"} = $valeur;$memo = ""; }
							if ($memo =~ /^begin$/)		{ $item_feature{"begin"} = $valeur;$memo = ""; }
							if ($memo =~ /^end$/)		{ $item_feature{"end"} = $valeur;$memo = ""; }
							if ($memo =~ /^begin_status$/)		{ $item_feature{"begin_status"} = $valeur;$memo = ""; }
							if ($memo =~ /^end_status$/)		{ $item_feature{"end_status"} = $valeur;$memo = ""; }
						}
					}
					
					my $feature_type = $item_feature{"type"};
					my $feature_status = 	(exists $item_feature{"status"})?$item_feature{"status"}:"";
					my $feature_id = 		(exists $item_feature{"id"})?$item_feature{"id"}:"";
					my $feature_description = (exists $item_feature{"description"})?$item_feature{"description"}:"";
					my $feature_evidence = 	(exists $item_feature{"evidence"})?$item_feature{"evidence"}:"";
					my $feature_ref = 		(exists $item_feature{"ref"})?$item_feature{"ref"}:"";
					my $feature_original = 	(exists $item_feature{"original"})?$item_feature{"original"}:"";
					my $feature_variation = (exists $item_feature{"variation"})?$item_feature{"variation"}:"";
					## begin could be "less than" or something else : do not appear here (in that case "end" exists), ex: entry#accession="P54613"
					my $feature_begin = 	(exists $item_feature{"begin"})?$item_feature{"begin"}:-1;
					my $feature_end = 		(exists $item_feature{"end"})?$item_feature{"end"}:-1;
					my $feature_begin_status = (exists $item_feature{"begin_status"})?$item_feature{"begin_status"}:"";
					my $feature_end_status = (exists $item_feature{"end_status"})?$item_feature{"end_status"}:"";
					# $feature_evidence =~ s/'/"/g;
					# $feature_description =~ s/'/"/g;
					$request = "INSERT INTO UNIPROT_FEATURE (ENTRY_ACCESSION,TYPE_ID,STATUS,ID,DESCRIPTION,EVIDENCE,FEATURE_REF,ORIGINAL,VARIATION,LOCATION_BEGIN,LOCATION_END,LOCATION_BEGIN_STATUS,LOCATION_END_STATUS)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',\'".$feature_type."\',\'"
								.$feature_status."\',\'".$feature_id."\',\'"
								.$feature_description."\',\'".$feature_evidence."\',\'"
								.$feature_ref."\',\'".$feature_original."\',\'"
								.$feature_variation."\',".$feature_begin.",".$feature_end.",\'".$feature_begin_status."\',\'".$feature_end_status."\');";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					# $request = "";
					$request = "INSERT INTO UNIPROT_ENTRY2FEATURE (ENTRY_ACCESSION,FEATURE_TYPE,FEATURE_BEGIN)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',\'".$feature_type."\',".$feature_begin.");";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
				}##end INSERT feature
				
				#########################################################################################################
				## INSERT sequence : [..]
				## checksum, fragment?, length, mass, modified (YYYY-MM-DD), sequence, version, precursor?
				# print "\n\n#### sequence current hash : \n";
				# print "\t".$list_accession[0]."\n";
				# foreach my $key (sort keys %current_sequence) {
					# print "\t".$key."\t::\t".$current_sequence{$key}."\n";
				# }
				my $sequence_length = $current_sequence{"length"};
				my $sequence_checksum = $current_sequence{"checksum"};
				my $sequence_mass = $current_sequence{"mass"};
				my $sequence_modified = $current_sequence{"modified"};
				my $sequence_version = $current_sequence{"version"};
				my $sequence_fragment = (exists $current_sequence{"fragment"})?$current_sequence{"fragment"}:"";
				my $sequence_precursor = (exists $current_sequence{"precursor"})?$current_sequence{"precursor"}:"f";
				if ($sequence_fragment =~ /^single$/) { $sequence_fragment = "s"; }
				if ($sequence_fragment =~ /^multiple$/) { $sequence_fragment = "m"; }
				if ($sequence_precursor =~ /^true$/) { $sequence_precursor = "t"; }
				if ($sequence_precursor =~ /^false$/) { $sequence_precursor = "f"; }
				# $count = length($longText);
				# @stringChunksArray = split(/.{60}/, $longText);
				my $test_length = length($current_sequence{"sequence"});
				## length to cut the sequence for the database max length (sequence_part[1-3] attributes)
				## please see comment into ORACLE TABLE definitions
				my $cutting_sequence = 4000; ## to cut the sequence to some 4000-length VARCHAR2
				my @sequence_sequence = split(/.{$cutting_sequence}/,$current_sequence{"sequence"});
				my $sequence_part1 = (exists $sequence_sequence[0])?$sequence_sequence[0]:"";
				my $sequence_part2 = (exists $sequence_sequence[1])?$sequence_sequence[1]:"";
				my $sequence_part3 = (exists $sequence_sequence[2])?$sequence_sequence[2]:"";
				
				if ($length >= 3000) { ## !! ?? tests for later : comment / erase these lines after...
					print "\t".$list_accession[0]." : ".$sequence_length." ?==? ".$test_length."\n";
					print "\t\t".$sequence_part1."\n";
					print "\t\t".$sequence_part2."\n";
					print "\t\t".$sequence_part3."\n########################\n";
				} ## end of tests
				
				$request = "INSERT INTO UNIPROT_SEQUENCE (ENTRY_ACCESSION,SEQUENCE_LENGTH,SEQUENCE_MASS,SEQUENCE_CHECKSUM,MODIFIED,VERSION,PRECURSOR,FRAGMENT,SEQUENCE_PART1,SEQUENCE_PART2,SEQUENCE_PART3)\n\tVALUES(";
				$request .= "\'".$list_accession[0]."\',".$sequence_length.",".$sequence_mass.",\'".$sequence_checksum."\',TO_DATE(\'"
							.$sequence_modified."\',\'YYYY-MM-DD\'),".$sequence_version.",\'".$sequence_fragment."\',\'".$sequence_precursor."\',\'"
							.$sequence_part1."\',\'".$sequence_part2."\',\'".$sequence_part3."\');";
				open (SORTIE,">>".$sortiescript);
				print SORTIE $request."\n\n";
				close SORTIE;
				$request = "";
				##end INSERT sequence
				
				#########################################################################################################
				## INSERT comment : [..]
				## type, name, link, events, evidence, evidence_status, isoform, location, subcellularLocation
				## conflict_ref, conflict_type, conflict_sequence_version, conflict_sequence_resource, conflict_sequence_id
				## interactant_experiments, interactant_id, interactant_intactid1, interactant_intactid2, interactant_label
				## phdependence, redoxpotential, temperaturedependence, maxabsorption, kinetics,
				## method, mass, error
				# my %hash_typecomment = ();
				my $comment_ref = 0; ## in order to have a number reference for comments for a same entry
				foreach my $item_comment (@list_comment) {
					$comment_ref++;
					my @item_comment = @{$item_comment};
					my %item_comment = ();## 
					my $memo = ""; ## retrieving the hash of current comment
					foreach my $valeur (@item_comment) {
						if ($memo =~ /^$/) { $memo = $valeur;
							# if (exists $hash_typecomment{$valeur}) { $hash_typecomment{$valeur}++; }
							# else { $hash_typecomment{$valeur} = 1; }
						} else { 
							if ($memo =~ /^type$/) 				{ $item_comment{"type"} = $valeur;$memo = ""; }
							if ($memo =~ /^evidence$/) 			{ $item_comment{"evidence"} = $valeur;$memo = ""; }
							if ($memo =~ /^evidence_status$/) 	{ $item_comment{"evidence_status"} = $valeur;$memo = ""; }
							if ($memo =~ /^name$/) 				{ $item_comment{"name"} = $valeur;$memo = ""; }
							if ($memo =~ /^link$/) 				{ $item_comment{"link"} = $valeur;$memo = ""; }
							
							if ($memo =~ /^method$/) 			{ $item_comment{"method"} = $valeur;$memo = ""; }
							if ($memo =~ /^mass$/) 				{ $item_comment{"mass"} = $valeur;$memo = ""; }
							if ($memo =~ /^error$/) 			{ $item_comment{"error"} = $valeur;$memo = ""; }
							
							if ($memo =~ /^events$/) 			{ $item_comment{"events"} = $valeur;$memo = ""; } ## @ : could of size 0
							if ($memo =~ /^isoform$/) 			{ $item_comment{"isoform"} = $valeur;$memo = ""; } ## % : id@, name@, note, sequence_type, sequence_ref
							
							if ($memo =~ /^location$/) 						{ $item_comment{"location"} = $valeur;$memo = ""; } ## @ : could of size 0
							if ($memo =~ /^subcellularLocations$/)			{ $item_comment{"subcellularLocations"} = $valeur;$memo = ""; } ## @ : could of size 0
							
							if ($memo =~ /^conflict_ref$/) 					{ $item_comment{"conflict_ref"} = $valeur;$memo = ""; }
							if ($memo =~ /^conflict_type$/) 				{ $item_comment{"conflict_type"} = $valeur;$memo = ""; }
							if ($memo =~ /^conflict_sequence_version$/) 	{ $item_comment{"conflict_sequence_version"} = $valeur;$memo = ""; }
							if ($memo =~ /^conflict_sequence_resource$/) 	{ $item_comment{"conflict_sequence_resource"} = $valeur;$memo = ""; }
							if ($memo =~ /^conflict_sequence_id$/) 			{ $item_comment{"conflict_sequence_id"} = $valeur;$memo = ""; }
							
							if ($memo =~ /^interactant_organismdiffer$/)	{ $item_comment{"interactant_organismdiffer"} = $valeur;$memo = ""; }
							if ($memo =~ /^interactant_experiments$/)		{ $item_comment{"interactant_experiments"} = $valeur;$memo = ""; }
							if ($memo =~ /^interactant_id$/) 				{ $item_comment{"interactant_id"} = $valeur;$memo = ""; }
							if ($memo =~ /^interactant_intactid1$/)			{ $item_comment{"interactant_intactid1"} = $valeur;$memo = ""; }
							if ($memo =~ /^interactant_intactid2$/)			{ $item_comment{"interactant_intactid2"} = $valeur;$memo = ""; }
							if ($memo =~ /^interactant_label$/) 			{ $item_comment{"interactant_label"} = $valeur;$memo = ""; }
							
							if ($memo =~ /^phdependence$/) 					{ $item_comment{"phdependence"} = $valeur;$memo = ""; }
							if ($memo =~ /^redoxpotential$/) 				{ $item_comment{"redoxpotential"} = $valeur;$memo = ""; }
							if ($memo =~ /^temperaturedependence$/) 		{ $item_comment{"temperaturedependence"} = $valeur;$memo = ""; }
							if ($memo =~ /^maxabsorption$/) 				{ $item_comment{"maxabsorption"} = $valeur;$memo = ""; }
							if ($memo =~ /^kinetics_km$/) 					{ $item_comment{"kinetics_km"} = $valeur;$memo = ""; } ## @ : could of size 0
							if ($memo =~ /^kinetics_vmax$/) 				{ $item_comment{"kinetics_vmax"} = $valeur;$memo = ""; } ## @ : could of size 0
						}
					}
					## in all cases where comment got a location (type of comment is specified
					if (exists $item_comment{"location"}) {
						my @locations = (exists $item_comment{"location"})?@{$item_comment{"location"}}:();
						foreach my $local (@locations) {
							my @local = split(/:::::/,$local); ## location (begin|position) ::::: location end ::::: location begin status ::::: location end status
							$request = "INSERT INTO UNIPROT_COMMENT2LOCATION (ENTRY_ACCESSION,COMMENT_TYPE,LOCATION_BEGIN,LOCATION_END,LOCATION_BEGIN_STATUS,LOCATION_END_STATUS) \n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',\'".$item_comment{"type"}."\',".$local[0].",".$local[1].",\'".$local[2]."\',\'".$local[3]."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
						}
					}
					## special cases for some types : 02|04|22|23|28|29|others
					my $evidence = (exists $item_comment{"evidence"})?$item_comment{"evidence"}:" ";
					my $evidence_status = (exists $item_comment{"evidence_status"})?$item_comment{"evidence_status"}:" ";
					# $evidence =~ s/'/"/g;
					# if ($evidence =~ /&gt;/) { $evidence =~ s/&gt;/>/g; }
					# if ($evidence =~ /&lt;/) { $evidence =~ s/&lt;/</g; }
					# if ($evidence =~ /&amp;/) { $evidence =~ s/&amp;/&/g; }
					# if ($name =~ /&gt;/) { $name =~ s/&gt;/>/g; }
					# if ($name =~ /&lt;/) { $name =~ s/&lt;/</g; }
					# if ($name =~ /&amp;/) { $name =~ s/&amp;/&/g; }
					# if ($link =~ /&gt;/) { $link =~ s/&gt;/>/g; }
					# if ($link =~ /&lt;/) { $link =~ s/&lt;/</g; }
					# if ($link =~ /&amp;/) { $link =~ s/&amp;/&/g; }
					$request = "INSERT INTO UNIPROT_ENTRY2COMMENT (ENTRY_ACCESSION,COMMENT_TYPE,COMMENT_REF)\n\tVALUES(";
					$request .= "\'".$list_accession[0]."\',\'".$item_comment{"type"}."\',\'".$comment_ref."\');";
					open (SORTIE,">>".$sortiescript);
					print SORTIE $request."\n\n";
					close SORTIE;
					$request = "";
					if ($item_comment{"type"} eq "02") { ## "alternative products" : event and isoform
						# my @comment_events = @{$item_comment{"events"}};
						my $event1 = $item_comment{"events"}[0]; ## $comment_events[0];
						my $event2 = (exists $item_comment{"events"}[1])?$item_comment{"events"}[1]:"";
						my $event3 = (exists $item_comment{"events"}[2])?$item_comment{"events"}[2]:"";
						my $event4 = (exists $item_comment{"events"}[3])?$item_comment{"events"}[3]:"";
						## print "\t".@{$item_comment{"events"}}."\tevents : ".$event1."\t".$event2."\t".$event3."\t".$event4."\n";
						$request = "INSERT INTO UNIPROT_COMMENT_ALTPRODS (ENTRY_ACCESSION,TYPE_ID,COMMENT_REF,EVIDENCE,EVIDENCE_STATUS,EVENT_TYPE1,EVENT_TYPE2,EVENT_TYPE3,EVENT_TYPE4)\n\tVALUES(";
						$request .= "\'".$list_accession[0]."\',\'02\',\'".$comment_ref."\',\'".$evidence."\',\'".$evidence_status."\',\'"
									.$event1."\',\'".$event2."\',\'".$event3."\',\'".$event4."\');";
						open (SORTIE,">>".$sortiescript);
						print SORTIE $request."\n\n";
						close SORTIE;
						$request = "";
						my @comment_isoforms = @{$item_comment{"isoform"}};
						foreach my $isoform (@comment_isoforms) {
							my %item_isoform = ();
							my @item_isoform = @{$isoform};
							my $memo = ""; ## retrieving the hash of current isoform
							foreach my $valeur (@item_isoform) {
								if ($memo =~ /^$/) { $memo = $valeur; } else {
									if ($memo =~ /^id$/) 			{ $item_isoform{"id"} = $valeur;$memo = ""; }
									if ($memo =~ /^name$/) 			{ $item_isoform{"name"} = $valeur;$memo = ""; }
									if ($memo =~ /^note$/) 			{ $item_isoform{"note"} = $valeur;$memo = ""; }
									if ($memo =~ /^sequence_type$/) { $item_isoform{"sequence_type"} = $valeur;$memo = ""; }
									if ($memo =~ /^sequence_ref$/)	{ $item_isoform{"sequence_ref"} = $valeur;$memo = ""; }
								}
							}					
							my @isoform_ids = @{$item_isoform{"id"}};
							my $isoform_id = $isoform_ids[0];
							my @isoform_names = @{$item_isoform{"name"}};
							my $name_content = $isoform_names[0];
							my $name_evidence = "";
							my $note_content = (exists $item_isoform{"note"})?$item_isoform{"note"}:"";
							my $note_evidence = "";
							my $sequence_type = $item_isoform{"sequence_type"};
							my $sequence_ref = (exists $item_isoform{"sequence_ref"})?$item_isoform{"sequence_ref"}:"";
							# if ($note_content =~ /&gt;/) { $note_content =~ s/&gt;/>/g; }
							# if ($note_content =~ /&lt;/) { $note_content =~ s/&lt;/</g; }
							# if ($note_content =~ /&amp;/) { $note_content =~ s/&amp;/&/g; }
							$request = "INSERT INTO UNIPROT_ISOFORM (ENTRY_ACCESSION,ID,NAME_CONTENT,NAME_EVIDENCE,NOTE_CONTENT,NOTE_EVIDENCE,SEQUENCE_TYPE,SEQUENCE_REF)\n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',\'".$isoform_id."\',\'".$name_content."\',\'"
										.$name_evidence."\',\'".$note_content."\',\'".$note_evidence."\',\'"
										.$sequence_type."\',\'".$sequence_ref."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
							if ($#{$item_isoform{"id"}} > 0) { 
								foreach my $id_more (@{$item_isoform{"id"}}) {
									if ($isoform_id ne $id_more) {
										$request = "INSERT INTO UNIPROT_ISOFORM_MOREID (ENTRY_ACCESSION,ID,ID_MORE) \n\tVALUES(";
										$request .= "\'".$list_accession[0]."\',\'".$isoform_id."\',\'".$id_more."\');";
										open (SORTIE,">>".$sortiescript);
										print SORTIE $request."\n\n";
										close SORTIE;
										$request = "";
									}
								}
							}
							if ($#{$item_isoform{"name"}} > 0) { 
								foreach my $name_more (@{$item_isoform{"name"}}) {
									if ($name_content ne $name_more) {
										$request = "INSERT INTO UNIPROT_ISOFORM_MORENAME (ENTRY_ACCESSION,ID,NAME_MORE) \n\tVALUES(";
										$request .= "\'".$list_accession[0]."\',\'".$isoform_id."\',\'".$name_more."\');";
										open (SORTIE,">>".$sortiescript);
										print SORTIE $request."\n\n";
										close SORTIE;
										$request = "";
									}
								}
							}
						}
					} else {
						if ($item_comment{"type"} eq "04") ## "bpc properties"
							{ 
							my $phdep = (exists $item_comment{"phdependence"})?$item_comment{"phdependence"}:"";
							my $redep = (exists $item_comment{"redoxpotential"})?$item_comment{"redoxpotential"}:"";
							my $tmdep = (exists $item_comment{"temperaturedependence"})?$item_comment{"temperaturedependence"}:"";
							my $maxab = (exists $item_comment{"maxabsorption"})?$item_comment{"maxabsorption"}:"";
							my $kinkm = (exists $item_comment{"kinetics_km"})?@{$item_comment{"kinetics_km"}}[0]:"";
							my $kinvmax = (exists $item_comment{"kinetics_vmax"})?@{$item_comment{"kinetics_vmax"}}[0]:"";
							$request = "INSERT INTO UNIPROT_COMMENT_BPC (ENTRY_ACCESSION,TYPE_ID,COMMENT_REF,EVIDENCE,EVIDENCE_STATUS,BPC_PHDEPENDANCE,BPC_REDOXPOTENTIAL,BPC_TEMPERATUREDEPENDANCE,BPC_ABSORPTION_MAX,BPC_KINETICS_KM,BPC_KINETICS_VMAX) \n\tVALUES(";
							$request .= "\'".$list_accession[0]."\',\'04\',\'".$comment_ref."\',\'".$evidence."\',\'".$evidence_status."\',\'".$phdep."\',\'".$redep."\',\'".$tmdep."\',\'".$maxab."\',\'".$kinkm."\',\'".$kinvmax."\');";
							open (SORTIE,">>".$sortiescript);
							print SORTIE $request."\n\n";
							close SORTIE;
							$request = "";
							if ($#{$item_comment{"kinetics_km"}} > 0) { 
								foreach my $more_km (@{$item_comment{"kinetics_km"}}) {
									if (@{$item_comment{"kinetics_km"}}[0] ne $more_km) {
										$request = "INSERT INTO UNIPROT_COMMENT_BPC_MOREKM (ENTRY_ACCESSION,MORE_KM) \n\tVALUES(";
										$request .= "\'".$list_accession[0]."\',\'".$more_km."\');";
										open (SORTIE,">>".$sortiescript);
										print SORTIE $request."\n\n";
										close SORTIE;
										$request = "";
									}
								}
							}
							if ($#{$item_comment{"kinetics_vmax"}} > 0) { 
								foreach my $more_vmax (@{$item_comment{"kinetics_vmax"}}) {
									if (@{$item_comment{"kinetics_vmax"}}[0] ne $more_vmax) {
										$request = "INSERT INTO UNIPROT_COMMENT_BPC_MOREVMAX (ENTRY_ACCESSION,MORE_VMAX) \n\tVALUES(";
										$request .= "\'".$list_accession[0]."\',\'".$more_vmax."\');";
										open (SORTIE,">>".$sortiescript);
										print SORTIE $request."\n\n";
										close SORTIE;
										$request = "";
									}
								}
							}
						} else {
							if ($item_comment{"type"} eq "22") ## "subcellular location" : evidence string table
								{ 
								my @list_subcell_location = @{$item_comment{"subcellularLocations"}};
								foreach my $item_subcell_location (@list_subcell_location) {
									my @item_subcell_loc = split(/:::::/,$item_subcell_location); ## name type id ::::: evidence ::::: evidence status
									# $item_subcell_loc[1] =~ s/'/"/g;
									$request = "INSERT INTO UNIPROT_MORE_EVIDENCE (ENTRY_ACCESSION,NAME_TYPE_ID,EVIDENCE,EVIDENCE_STATUS)\n\tVALUES (";
									$request .= "\'".$list_accession[0]."\',\'".$item_subcell_loc[0]."\',\'".$item_subcell_loc[1]."\',\'".$item_subcell_loc[2]."\' );";
									open (SORTIE,">>".$sortiescript);
									print SORTIE $request."\n\n";
									close SORTIE;
									$request = "";
								}
							} else {
								if ($item_comment{"type"} eq "23") ## "sequence caution" with location
									{ 
									my $conflict_type = $item_comment{"conflict_type"};
									my $conflict_ref = (exists $item_comment{"conflict_ref"})?$item_comment{"conflict_ref"}:"";
									my $conflict_sequence_version = (exists $item_comment{"conflict_sequence_version"})?$item_comment{"conflict_sequence_version"}:-1;
									if ($conflict_sequence_version eq "") { $conflict_sequence_version = -1; }
									my $conflict_sequence_resource = (exists $item_comment{"conflict_sequence_resource"})?$item_comment{"conflict_sequence_resource"}:"";
									my $conflict_sequence_id = (exists $item_comment{"conflict_sequence_id"})?$item_comment{"conflict_sequence_id"}:"";
									$request = "INSERT INTO UNIPROT_COMMENT_SEQU_CAUTION (ENTRY_ACCESSION,TYPE_ID,COMMENT_REF,EVIDENCE,EVIDENCE_STATUS,CONFLICT_TYPE,CONFLICT_REF,CONFLICT_SEQUENCE_RESOURCE,CONFLICT_SEQUENCE_ID,CONFLICT_SEQUENCE_VERSION) \n\tVALUES(";
									$request .= "\'".$list_accession[0]."\',\'23\',\'".$comment_ref."\',\'".$evidence."\',\'".$evidence_status."\',\'".$conflict_type."\',\'".$conflict_ref."\',\'".$conflict_sequence_resource."\',\'".$conflict_sequence_id."\',".$conflict_sequence_version.");";
									open (SORTIE,">>".$sortiescript);
									print SORTIE $request."\n\n";
									close SORTIE;
									$request = "";
								} else {
									if ($item_comment{"type"} eq "28") ## "mass spectrometry" with location
										{
										my $method = $item_comment{"method"};
										my $mass = $item_comment{"mass"};
										my $error = (exists $item_comment{"error"})?$item_comment{"error"}:0.0;
										$request = "INSERT INTO UNIPROT_COMMENT_MASS_SPECTRO (ENTRY_ACCESSION,TYPE_ID,COMMENT_REF,EVIDENCE,EVIDENCE_STATUS,MASS,METHOD,ERROR) \n\tVALUES(";
										$request .= "\'".$list_accession[0]."\',\'23\',\'".$comment_ref."\',\'".$evidence."\',\'".$evidence_status."\',".$mass.",\'".$method."\',".$error.");";
										open (SORTIE,">>".$sortiescript);
										print SORTIE $request."\n\n";
										close SORTIE;
										$request = "";
									} else {
										if ($item_comment{"type"} eq "29") ## "interaction"
											{
											my $organismdiffer = (exists $item_comment{"interactant_organismdiffer"})?$item_comment{"interactant_organismdiffer"}:"f";
											my $experiments = $item_comment{"interactant_experiments"};
											my $group_id = (exists $item_comment{"interactant_id"})?$item_comment{"interactant_id"}:"";
											my $group_label = (exists $item_comment{"interactant_label"})?$item_comment{"interactant_label"}:"";
											my $intactid1 = $item_comment{"interactant_intactid1"};
											my $intactid2 = $item_comment{"interactant_intactid2"};
											$request = "INSERT INTO UNIPROT_COMMENT_INTERACTION (ENTRY_ACCESSION,TYPE_ID,COMMENT_REF,EVIDENCE,EVIDENCE_STATUS,INTERACTANT_ORGANISMDIFFER,INTERACTANT_EXPERIMENTS,INTERACTANT_INTACTID1,INTERACTANT_INTACTID2,INTERACTANT_GROUP_ID,INTERACTANT_GROUP_LABEL) \n\tVALUES(";
											$request .= "\'".$list_accession[0]."\',\'29\',\'".$comment_ref."\',\'".$evidence."\',\'".$evidence_status."\',\'"
														.$organismdiffer."\',".$experiments.",\'".$intactid1."\',\'".$intactid2."\',\'".$group_id."\',\'".$group_label."\');";
											open (SORTIE,">>".$sortiescript);
											print SORTIE $request."\n\n";
											close SORTIE;
											$request = "";
										} else { ## general comment and / or link
											my $name = (exists $item_comment{"name"})?$item_comment{"name"}:" ";
											my $link = (exists $item_comment{"link"})?$item_comment{"link"}:" ";
											$request = "INSERT INTO UNIPROT_COMMENT_GENERIC (ENTRY_ACCESSION,TYPE_ID,COMMENT_REF,EVIDENCE,EVIDENCE_STATUS,NAME,LINK) \n\tVALUES(";
											$request .= "\'".$list_accession[0]."\',\'".$item_comment{"type"}."\',\'".$comment_ref."\',\'".$evidence."\',\'".$evidence_status."\',\'".$name."\',\'".$link."\' );";
											open (SORTIE,">>".$sortiescript);
											print SORTIE $request."\n\n";
											close SORTIE;
											$request = "";
										}
									}
								}
							}
						}
					}
					
				}## end INSERT comment
				
				$request = "COMMIT;\n";
				$request .= "-- end of insertions of entry ".$list_accession[0]." -- ";
				open (SORTIE,">>".$sortiescript);
				print SORTIE $request."\n\n";
				close SORTIE;
				$request = "";
				
				
				# foreach my $key (sort keys %hash_typecomment) 
					# { print "\t".$key."\t::\t".$hash_typecomment{$key}."\n"; }
				# print $flag_count."######################\n\n";
				
				## re-initialization
				@list_accession = ();
				@list_name = ();
				@list_protein_names = ();
				$current_proteinexistence = "";
				@list_keywords = ();
				@list_genes = ();
				@list_dbreferences = ();
				@list_dbproperty = ();
				@list_organism = ();
				@list_reference = ();
				@list_feature = ();
				%current_entry = ();
				@list_evidences = ();
				%current_sequence = ();
				@list_comment = ();
			}
			if ($line =~ /<accession>(.*)<\/accession>/) { push (@list_accession,$1); }
			if ($line =~ /<name>(.*)<\/name>/) { push (@list_name,$1); }
			
			#########################################################################################################
			#########################################################################################################
			## ## protein detection : see comment tables BDD oracle... ??
			if ($line =~ /<protein>/) { $flag_protein = 1; }
			if ($flag_protein == 1) {
				if ($line =~ /<\/protein>/) { $flag_protein = 0; }
				if ($line =~ /<domain>/) { $flag_protein_domainorcomponent = "d//"; }
				if ($line =~ /<\/domain>/) { $flag_protein_domainorcomponent = ""; }
				if ($line =~ /<component>/) { $flag_protein_domainorcomponent = "c//"; }
				if ($line =~ /<\/component>/) { $flag_protein_domainorcomponent = ""; }
				if ($line =~ /<recommendedName>/)
					{ push (@list_protein_names,$flag_protein_domainorcomponent."1"); }
				if ($line =~ /<recommendedName ref="(.*)">/)
					{ push (@list_protein_names,$flag_protein_domainorcomponent."1:::::".$1); }
				if ($line =~ /<alternativeName>/)
					{ push (@list_protein_names,$flag_protein_domainorcomponent."2"); }
				if ($line =~ /<alternativeName ref="(.*)">/)
					{ push (@list_protein_names,$flag_protein_domainorcomponent."2:::::".$1); }
				if ($line =~ /<submittedName>/)
					{ push (@list_protein_names,$flag_protein_domainorcomponent."3"); }
				if ($line =~ /<submittedName ref="(.*)">/) 
					{ push (@list_protein_names,$flag_protein_domainorcomponent."3:::::".$1); }
				if ($line =~ /<allergenName>(.*)<\/allergenName>/) { push (@list_protein_names,$flag_protein_domainorcomponent."4:::::".$1); }
				if ($line =~ /<biotechName>(.*)<\/biotechName>/) { push (@list_protein_names,$flag_protein_domainorcomponent."5:::::".$1); }
				if ($line =~ /<CdAntigenName>(.*)<\/CdAntigenName>/) { push (@list_protein_names,$flag_protein_domainorcomponent."6:::::".$1); }
				if ($line =~ /<innName>(.*)<\/innName>/) { push (@list_protein_names,$flag_protein_domainorcomponent."7:::::".$1); }
				if ($line =~ /<fullName>(.*)<\/fullName>/) { push (@list_protein_names,$flag_protein_domainorcomponent."8:::::".$1); }
				if ($line =~ /<shortName>(.*)<\/shortName>/) { push (@list_protein_names,$flag_protein_domainorcomponent."9:::::".$1); }
				if ($line =~ /<proteinExistence type="(.*)"\/>/) { $current_proteinexistence = $1; }
				if ($current_proteinexistence =~ /[a-z]+/) {
					if ($current_proteinexistence =~ /^Evidence at protein level$/)		{ $current_proteinexistence = "1"; }
					if ($current_proteinexistence =~ /^Evidence at transcript level$/)	{ $current_proteinexistence = "2"; }
					if ($current_proteinexistence =~ /^Inferred from homology$/)		{ $current_proteinexistence = "3"; }
					if ($current_proteinexistence =~ /^Predicted$/)						{ $current_proteinexistence = "4"; }
					if ($current_proteinexistence =~ /^Uncertain$/)						{ $current_proteinexistence = "5"; }
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## gene detection
			if ($line =~ /<gene>/) { $flag_gene = 1; }
			if ($flag_gene == 1) {
				if ($line =~ /<name type="(.*)">(.*)<\/name>/) {
					my $gene_name_type = $1;
					my $gene_name = $2;
					if ($gene_name_type =~ /^primary$/) 		{ $gene_name_type = "1"; }
					if ($gene_name_type =~ /^synonym$/) 		{ $gene_name_type = "2"; }
					if ($gene_name_type =~ /^ordered locus$/) 	{ $gene_name_type = "3"; }
					if ($gene_name_type =~ /^ORF$/) 			{ $gene_name_type = "4"; }
					if ($gene_name_type =~ /^scientific$/) 		{ $gene_name_type = "5"; }
					if ($gene_name_type =~ /^common$/) 			{ $gene_name_type = "6"; }
					push (@list_genes,$gene_name_type.":::::".$gene_name);
					if ($line =~ /<\/gene>/) { $flag_gene = 0; }
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## gene_location detection
			if ($line =~ /<geneLocation type="(.*)"\/>/) { 
				$current_genelocation_type = $1;
				push (@list_genelocations,":::::".$current_genelocation_type);
			} else { if ($line =~ /<geneLocation type="(.*)">/) 
				{ $flag_gene_location = 1;$current_genelocation_type = $1; } }
			if ($current_genelocation_type =~ /^apicoplast$/) 		{ $current_genelocation_type = "01"; }
			if ($current_genelocation_type =~ /^chloroplast$/) 		{ $current_genelocation_type = "02"; }
			if ($current_genelocation_type =~ /^chromatophore$/) 	{ $current_genelocation_type = "03"; }
			if ($current_genelocation_type =~ /^cyanelle$/) 		{ $current_genelocation_type = "04"; }
			if ($current_genelocation_type =~ /^hydrogenosome$/) 	{ $current_genelocation_type = "05"; }
			if ($current_genelocation_type =~ /^mitochondrion$/) 	{ $current_genelocation_type = "06"; }
			if ($current_genelocation_type =~ /^non-photosynthetic plastid$/) { $current_genelocation_type = "07"; }
			if ($current_genelocation_type =~ /^nucleomorph$/) 		{ $current_genelocation_type = "08"; }
			if ($current_genelocation_type =~ /^plasmid$/) 			{ $current_genelocation_type = "09"; }
			if ($current_genelocation_type =~ /^plastid$/) 			{ $current_genelocation_type = "10"; }
			if ($flag_gene_location == 1) {
				if ($line =~ /<name>(.*)<\/name>/) {
					my $current_genelocation_name = $1;
					push (@list_genelocations,$current_genelocation_name.":::::".$current_genelocation_type);
					if ($line =~ /<\/geneLocation>/) { $flag_gene_location = 0; }
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## keyword detection
			if ($line =~ /<keyword id="(.*)">(.*)<\/keyword>/) { push (@list_keywords,$1.":::::".$2); }
			
			#########################################################################################################
			#########################################################################################################
			## ## dbReference detection : as a main dbReference in UniprotKB entry
			if ($line =~ /<dbReference type="(.*)" key="(.*)" id="(.*)">/) { 
				$flag_dbreference = 1;
				push (@list_dbreferences,$1.":::::".$3.":::::".$2);
			}
			if ( ($flag_dbreference == 1) && ($line =~ /<property value="(.*)" type="(.*)"\/>/) ) 
				{ push (@list_dbproperty,$list_dbreferences[$#list_dbreferences].":::::".$2.":::::".$1); }
			if ( ($flag_dbreference == 1) && ($line =~ /<\/dbReference>/) ) { $flag_dbreference = 0; }
			
			#########################################################################################################
			#########################################################################################################
			## ##  organism detection
			if ($line =~ /<organism key="(.*)">/) {
				$flag_organism = 1;
				$current_organism{"key"} = $1;
			}
			if ($flag_organism == 1) {
				if ($line =~ /<name type="(.*)">(.*)<\/name>/) {
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
					if ($line =~ /<taxon>(.*)<\/taxon>/) { push (@list_organism_taxons,$1); }
				}
				if ($line =~ /<dbReference type="(.*)" key="(.*)" id="(.*)"\/>/)
					{ push (@list_organism_dbrefs,$1.":::::".$3.":::::".$2); }
				if ($line =~ /<\/organism>/) {
					$flag_organism = 0;
					## push (@list_organism_names,$list_organism_names[0]);
					## push (@list_organism_dbrefs,$list_organism_dbrefs[0]);
					## if ($#list_organism_names > 0)
					## 	{ $current_organism{"names"} = [ @list_organism_names ]; }
					## if ($#list_organism_dbrefs > 0)
					## 	{ $current_organism{"dbrefer"} = [ @list_organism_dbrefs ]; }
					$current_organism{"names"} = [ @list_organism_names ];
					if ($#list_organism_taxons > 0) ## really more than one taxon (first taxon at position [0])
						{ $current_organism{"lineage"} = [ @list_organism_taxons ]; }
					$current_organism{"dbrefer"} = [ @list_organism_dbrefs ];
					push (@list_organism,[ %current_organism ]);
					@list_organism_names = ();
					@list_organism_taxons = ();
					@list_organism_dbrefs = ();
					%current_organism = ();
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## reference detection : key, citation_volume, citation_type, citation_name, citation_last, citation_first, citation_date, citation_db, citation_title, citation_dbrefer, citation_lists, scopes, sources
			if ($line =~ /<reference key="(.*)">/) {
				$flag_reference = 1;
				$current_reference{"key"} = $1;
			}
			if ($flag_reference == 1) {
				## reference citation type is 1 : "book"
				if ($line =~ /<citation type="(.*)" publisher="(.*)" name="(.*)" last="(.*)" first="(.*)" date="(.*)" city="(.*)">/) 
					{
					$current_reference{"citation_type"} = $1; ## 1
					$current_reference{"citation_publisher"} = $2;
					$current_reference{"citation_name"} = $3;
					$current_reference{"citation_last"} = $4;
					$current_reference{"citation_first"} = $5;
					$current_reference{"citation_date"} = $6; ## YYYY
					$current_reference{"citation_city"} = $7;
					}
				## reference citation type is 2 : "journal article"
				if ($line =~ /<citation volume="(.*)" type="(.*)" name="(.*)" last="(.*)" first="(.*)" date="(.*)">/)
					{ 
					$flag_reference_citation = 1;
					$current_reference{"citation_volume"} = $1; ## 2
					$current_reference{"citation_type"} = $2;
					$current_reference{"citation_name"} = $3;
					$current_reference{"citation_last"} = $4;
					$current_reference{"citation_first"} = $5;
					$current_reference{"citation_date"} = $6; ## YYYY
					}
				## reference citation type is 3 : "online journal article"
				if ($line =~ /<citation type="(.*)" name="(.*)">/)
					{ 
					$flag_reference_citation = 1;
					$current_reference{"citation_type"} = $1; ## 3
					$current_reference{"citation_name"} = $2;
					}
				## reference citation type is 4 : "patent"
				if ($line =~ /<citation type="(.*)" number="(.*)" date="(.*)">/)
					{ 
					$flag_reference_citation = 1;
					$current_reference{"citation_type"} = $1; ## 4
					$current_reference{"citation_number"} = $2;
					$current_reference{"citation_date"} = $3; ## YYYY-MM-DD
					}
				## reference citation type is 5 : "submission" / database
				if ($line =~ /<citation type="(.*)" db="(.*)" date="(.*)">/)
					{ 
					$flag_reference_citation = 1;
					$current_reference{"citation_type"} = $1; ## 5
					$current_reference{"citation_db"} = $2;
					$current_reference{"citation_date"} = $3; ## YYYY-MM
					}
				## reference citation type is 6 : "thesis"
				if ($line =~ /<citation type="(.*)" institute="(.*)" date="(.*)" country="(.*)" city="(.*)">/)
					{ 
					$flag_reference_citation = 1;
					$current_reference{"citation_type"} = $1; ## 6
					$current_reference{"citation_institute"} = $2;
					$current_reference{"citation_date"} = $3; ## YYYY
					$current_reference{"citation_country"} = $4;
					$current_reference{"citation_city"} = $5;
					}
				## reference citation type is 7 : "unpublished observations"
				if ($line =~ /<citation type="(.*)" date="(.*)">/)
					{ 
					$flag_reference_citation = 1;
					$current_reference{"citation_type"} = $1; ## 7
					$current_reference{"citation_date"} = $2; ## YYYY-MM
					}
				if ( (exists $current_reference{"citation_type"}) && ($current_reference{"citation_type"} =~ /[a-z]+/) ) {
					if ($current_reference{"citation_type"} =~ /^book$/) 				{ $current_reference{"citation_type"} = "1"; }
					if ($current_reference{"citation_type"} =~ /^journal article$/) 	{ $current_reference{"citation_type"} = "2"; }
					if ($current_reference{"citation_type"} =~ /^online journal article$/) 	{ $current_reference{"citation_type"} = "3"; }
					if ($current_reference{"citation_type"} =~ /^patent$/) 			{ $current_reference{"citation_type"} = "4"; }
					if ($current_reference{"citation_type"} =~ /^submission$/) 		{ $current_reference{"citation_type"} = "5"; }
					if ($current_reference{"citation_type"} =~ /^thesis$/) 			{ $current_reference{"citation_type"} = "6"; }
					if ($current_reference{"citation_type"} =~ /^unpublished observations$/) 	{ $current_reference{"citation_type"} = "7"; }
					if ($current_reference{"citation_type"} =~ /^unpublished results$/)		{ $current_reference{"citation_type"} = "8"; }
				}
				if ($flag_reference_citation == 1) {
					if ($line =~ /<title>(.*)<\/title>/) { $current_reference{"citation_title"} = $1; }
					if ($line =~ /<dbReference type="(.*)" key="(.*)" id="(.*)"\/>/) 
						{ push (@list_reference_dbrefs,$1.":::::".$3.":::::".$2); }
					if ($line =~ /<authorList>/) { $flag_reference_List = "a"; }
					if ($line =~ /<editorList>/) { $flag_reference_List = "e"; }
					if ( ($line =~ /<\/authorList>/) || ($line =~ /<\/editorList>/) )
						{ $flag_reference_List = ""; }
					if ($flag_reference_List ne "") {
						if ($line =~ /<person name="(.*)"\/>/)
							{ push (@list_reference_Lists,$1.":::::".$flag_reference_List.":::::p"); }
						if ($line =~ /<consortium name="(.*)"\/>/)
							{ push (@list_reference_Lists,$1.":::::".$flag_reference_List.":::::c"); }
					}
					if ($line =~ /<locator>(.*)<\/locator>/) { $current_reference{"citation_locator"} = $1; }
					if ($line =~ /<\/citation>/) {
						$flag_reference_citation = 0;
						$current_reference{"citation_dbrefer"} = [ @list_reference_dbrefs ];
						$current_reference{"citation_lists"} = [ @list_reference_Lists ];
						@list_reference_dbrefs = ();
						@list_reference_Lists = ();
					}
				}
				if ($line =~ /<scope>(.*)<\/scope>/) { push (@list_reference_scopes,$1); }
				if ($line =~ /<source>/) { $flag_reference_source = 1; }
				if ($flag_reference_source == 1) {
					if ($line =~ /<\/source>/) { $flag_reference_source = 0; }
					## one source expected but sometimes in XML sources are more (see ORACLE table definition)
					if ($line =~ /<(.*)>(.*)<\/(.*)>/){
						if ($1 eq $3) {
							my $source_type = $1;my $source_content = $2;
							if ($source_type =~ /[a-z]+/) { 
								if ($source_type =~ /^species$/) 	{ $source_type = "1"; }
								if ($source_type =~ /^strain$/) 	{ $source_type = "2"; }
								if ($source_type =~ /^plasmid$/) 	{ $source_type = "3"; }
								if ($source_type =~ /^transposon$/) { $source_type = "4"; }
								if ($source_type =~ /^tissue$/)		{ $source_type = "5"; }
							}
							push (@list_reference_sources,$source_type.":::::".$source_content);
						}
					}
				}
				if ($line =~ /<\/reference>/) {
					$flag_reference = 0;
					$current_reference{"scopes"} = [ @list_reference_scopes ];
					$current_reference{"sources"} = [ @list_reference_sources ];
					@list_reference_scopes = ();
					@list_reference_sources = ();
					push (@list_reference,[ %current_reference ]);
					%current_reference = ();
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## feature detection : [..]
			## type, begin, end?, original?, variation?
			## "generic" feature detection of attributes
			if ($line =~ /<feature (.*)>/) {
				$flag_feature = 1;
				my $tmp_feature = $1;my $memo = "";
				my @list_tmp_feature = split(/" /,$tmp_feature); ## to cut and detect automatically the attributes (factorization ??)
				foreach my $elt_feature (@list_tmp_feature) {
					my @list_elt_feature = split(/="/,$elt_feature);
					foreach my $sub_elt_feature (@list_elt_feature) {
						$sub_elt_feature =~ s/"//;
						if ($memo =~ /^$/) { $memo = $sub_elt_feature; }
						else { $current_feature{$memo} = $sub_elt_feature;$memo = ""; }
					}
				}
			}
			## 38 different types of features, and attributes could not be "meta-identical" for one given type
			if ( (exists $current_feature{"type"}) && ($current_feature{"type"} =~ /[a-z]+/) ) {
				if ($current_feature{"type"} =~ /^active site$/) 			{ $current_feature{"type"} = "01"; }
				if ($current_feature{"type"} =~ /^binding site$/) 			{ $current_feature{"type"} = "02"; }
				if ($current_feature{"type"} =~ /^calcium-binding region$/) { $current_feature{"type"} = "03"; }
				if ($current_feature{"type"} =~ /^chain$/) 					{ $current_feature{"type"} = "04"; }
				if ($current_feature{"type"} =~ /^coiled-coil region$/) 	{ $current_feature{"type"} = "05"; }
				if ($current_feature{"type"} =~ /^compositionally biased region$/) 			{ $current_feature{"type"} = "06"; }
				if ($current_feature{"type"} =~ /^cross-link$/) 			{ $current_feature{"type"} = "07"; }
				if ($current_feature{"type"} =~ /^disulfide bond$/) 		{ $current_feature{"type"} = "08"; }
				if ($current_feature{"type"} =~ /^DNA-binding region$/)		{ $current_feature{"type"} = "09"; }
				if ($current_feature{"type"} =~ /^domain$/) 				{ $current_feature{"type"} = "10"; }
				if ($current_feature{"type"} =~ /^glycosylation site$/)		{ $current_feature{"type"} = "11"; }
				if ($current_feature{"type"} =~ /^helix$/) 					{ $current_feature{"type"} = "12"; }
				if ($current_feature{"type"} =~ /^initiator methionine$/) 	{ $current_feature{"type"} = "13"; }
				if ($current_feature{"type"} =~ /^lipid moiety-binding region$/) 			{ $current_feature{"type"} = "14"; }
				if ($current_feature{"type"} =~ /^metal ion-binding site$/) { $current_feature{"type"} = "15"; }
				if ($current_feature{"type"} =~ /^modified residue$/) 		{ $current_feature{"type"} = "16"; }
				if ($current_feature{"type"} =~ /^mutagenesis site$/) 		{ $current_feature{"type"} = "17"; }
				if ($current_feature{"type"} =~ /^non-consecutive residues$/) 				{ $current_feature{"type"} = "18"; }
				if ($current_feature{"type"} =~ /^non-terminal residue$/) 	{ $current_feature{"type"} = "19"; }
				if ($current_feature{"type"} =~ /^nucleotide phosphate-binding region$/) 	{ $current_feature{"type"} = "20"; }
				if ($current_feature{"type"} =~ /^peptide$/) 				{ $current_feature{"type"} = "21"; }
				if ($current_feature{"type"} =~ /^propeptide$/) 			{ $current_feature{"type"} = "22"; }
				if ($current_feature{"type"} =~ /^region of interest$/) 	{ $current_feature{"type"} = "23"; }
				if ($current_feature{"type"} =~ /^repeat$/) 				{ $current_feature{"type"} = "24"; }
				if ($current_feature{"type"} =~ /^non-standard amino acid$/) 				{ $current_feature{"type"} = "25"; }
				if ($current_feature{"type"} =~ /^sequence conflict$/) 		{ $current_feature{"type"} = "26"; }
				if ($current_feature{"type"} =~ /^sequence variant$/) 		{ $current_feature{"type"} = "27"; }
				if ($current_feature{"type"} =~ /^short sequence motif$/) 	{ $current_feature{"type"} = "28"; }
				if ($current_feature{"type"} =~ /^signal peptide$/) 		{ $current_feature{"type"} = "29"; }
				if ($current_feature{"type"} =~ /^site$/) 					{ $current_feature{"type"} = "30"; }
				if ($current_feature{"type"} =~ /^splice variant$/) 		{ $current_feature{"type"} = "31"; }
				if ($current_feature{"type"} =~ /^strand$/) 				{ $current_feature{"type"} = "32"; }
				if ($current_feature{"type"} =~ /^topological domain$/) 	{ $current_feature{"type"} = "33"; }
				if ($current_feature{"type"} =~ /^transit peptide$/) 		{ $current_feature{"type"} = "34"; }
				if ($current_feature{"type"} =~ /^transmembrane region$/) 	{ $current_feature{"type"} = "35"; }
				if ($current_feature{"type"} =~ /^turn$/) 					{ $current_feature{"type"} = "36"; }
				if ($current_feature{"type"} =~ /^unsure residue$/) 		{ $current_feature{"type"} = "37"; }
				if ($current_feature{"type"} =~ /^zinc finger region$/) 	{ $current_feature{"type"} = "38"; }
			}
			if ($flag_feature == 1) {
				## feature location detection
				if ($line =~ /<location>/) { $flag_feature_location = 1; }
				if ($flag_feature_location == 1) {
					if ( ($line =~ /<begin position="(.*)"\/>/) 
							|| ($line =~ /<position position="(.*)"\/>/) ) { 
						$current_feature{"begin"} = $1;
					}
					if ($line =~ /<end position="(.*)"\/>/) { $current_feature{"end"} = $1; }
					if ( ($line =~ /<begin status="(.*)" position="(.*)"\/>/) 
							|| ($line =~ /<position status="(.*)" position="(.*)"\/>/) ) {
						$current_feature{"begin_status"} = $1;
						$current_feature{"begin"} = $2;
					}
					if ($line =~ /<end status="(.*)" position="(.*)"\/>/) 
						{ $current_feature{"end_status"} = $1;$current_feature{"end"} = $2; }
					if ($line =~ /<\/location>/) { $flag_feature_location = 0; }
				}
				## feature original and variation detection
				if ($line =~ /<original>(.*)<\/original>/) { $current_feature{"original"} = $1; }
				if ($line =~ /<variation>(.*)<\/variation>/) { $current_feature{"variation"} = $1; }
				## feature end
				if ($line =~ /<\/feature>/) {
					$flag_feature = 0;
					## for generic detection of attributes of an XML item
					# print "\n\n".{%current_feature}."#### current feature hash : \n";
					# foreach my $key ( keys %current_feature) { ## sort keys %
						# print "\t".$key."\t::\t".$current_feature{$key}."\n";
					# }
					push (@list_feature,[ %current_feature ]);
					%current_feature = ();
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## evidence detection : 
			if ($line =~ /<evidence type="(.*)" key="(.*)" date="(.*)" category="(.*)" attribute="(.*)"\/>/) 
				{ push (@list_evidences,$2.":::::".$4.":::::".$1.":::::".$3.":::::".$5); }
				
			#########################################################################################################
			#########################################################################################################
			## ## sequence detection : 
			## "generic" sequence detection of attributes
			if ($line =~ /<sequence (.*)>(.*)<\/sequence>/) {
				$flag_sequence = 1;$flag_sequence = 0;
				my $tmp_sequence = $1;my $memo = "";
				$current_sequence{"sequence"} = $2;
				my @list_tmp_sequence = split(/" /,$tmp_sequence); ## to cut and detect automatically the attributes (factorization ??)
				foreach my $elt_sequence (@list_tmp_sequence) {
					my @list_elt_sequence = split(/="/,$elt_sequence);
					foreach my $sub_elt_sequence (@list_elt_sequence) {
						$sub_elt_sequence =~ s/"//;
						if ($memo =~ /^$/) { $memo = $sub_elt_sequence; }
						else { $current_sequence{$memo} = $sub_elt_sequence;$memo = ""; }
					}
				}
			}
			
			#########################################################################################################
			#########################################################################################################
			## ## comment detection : type, evidence?, evidence_status?, name ?, mass ?, error?
			## "generic" sequence detection of attributes
			if ($line =~ /<comment (.*)>/) { ## here type and evidence attributes
				$flag_comment = 1;
				my $tmp_comment = $1;my $memo = "";
				my @list_tmp_comment = split(/" /,$tmp_comment); ## to cut and detect automatically the attributes (factorization ??)
				foreach my $elt_comment (@list_tmp_comment) {
					my @list_elt_comment = split(/="/,$elt_comment);
					foreach my $sub_elt_comment (@list_elt_comment) {
						$sub_elt_comment =~ s/"//;
						if ($memo =~ /^$/) { $memo = $sub_elt_comment; }
						else { $current_comment{$memo} = $sub_elt_comment;$memo = ""; }
					}
				}
			}
			## comment types treatment : 29 differents types : for grouping in distinct tables see followings comments (##) in code
			if ( (exists $current_comment{"type"}) && ($current_comment{"type"} =~ /[a-zA-Z]+/) ) {
				if ($current_comment{"type"} =~ /^allergen$/) 				{ $current_comment{"type"} = "01"; }
				if ($current_comment{"type"} =~ /^alternative products$/) 	{ $current_comment{"type"} = "02"; }
				if ($current_comment{"type"} =~ /^biotechnology$/) 			{ $current_comment{"type"} = "03"; }
				if ($current_comment{"type"} =~ /^biophysicochemical properties$/) { $current_comment{"type"} = "04"; }
				if ($current_comment{"type"} =~ /^catalytic properties$/) 	{ $current_comment{"type"} = "05"; }
				if ($current_comment{"type"} =~ /^caution$/) 				{ $current_comment{"type"} = "06"; }
				if ($current_comment{"type"} =~ /^cofactor$/) 				{ $current_comment{"type"} = "07"; }
				if ($current_comment{"type"} =~ /^developmental stage$/) 	{ $current_comment{"type"} = "08"; }
				if ($current_comment{"type"} =~ /^disease$/) 				{ $current_comment{"type"} = "09"; }
				if ($current_comment{"type"} =~ /^domain$/) 				{ $current_comment{"type"} = "10"; }
				if ($current_comment{"type"} =~ /^disruption phenotype$/) 	{ $current_comment{"type"} = "11"; }
				if ($current_comment{"type"} =~ /^enzyme regulation$/) 		{ $current_comment{"type"} = "12"; }
				if ($current_comment{"type"} =~ /^function$/) 				{ $current_comment{"type"} = "13"; }
				if ($current_comment{"type"} =~ /^induction$/) 				{ $current_comment{"type"} = "14"; }
				if ($current_comment{"type"} =~ /^miscellaneous$/) 			{ $current_comment{"type"} = "15"; }
				if ($current_comment{"type"} =~ /^pathway$/) 				{ $current_comment{"type"} = "16"; }
				if ($current_comment{"type"} =~ /^pharmaceutical$/) 		{ $current_comment{"type"} = "17"; }
				if ($current_comment{"type"} =~ /^polymorphism$/) 			{ $current_comment{"type"} = "18"; }
				if ($current_comment{"type"} =~ /^PTM$/) 					{ $current_comment{"type"} = "19"; } ## posttranslational modification
				if ($current_comment{"type"} =~ /^RNA editing$/) 			{ $current_comment{"type"} = "20"; }
				if ($current_comment{"type"} =~ /^similarity$/) 			{ $current_comment{"type"} = "21"; }
				if ($current_comment{"type"} =~ /^subcellular location$/) 	{ $current_comment{"type"} = "22"; }
				if ($current_comment{"type"} =~ /^sequence caution$/) 		{ $current_comment{"type"} = "23"; }
				if ($current_comment{"type"} =~ /^subunit$/) 				{ $current_comment{"type"} = "24"; }
				if ($current_comment{"type"} =~ /^tissue specificity$/) 	{ $current_comment{"type"} = "25"; }
				if ($current_comment{"type"} =~ /^toxic dose$/) 			{ $current_comment{"type"} = "26"; }
				if ($current_comment{"type"} =~ /^online information$/) 	{ $current_comment{"type"} = "27"; }
				if ($current_comment{"type"} =~ /^mass spectrometry$/) 		{ $current_comment{"type"} = "28"; }
				if ($current_comment{"type"} =~ /^interaction$/) 			{ $current_comment{"type"} = "29"; }
				if ($current_comment{"type"} =~ /^catalytic activity$/) 	{ $current_comment{"type"} = "30"; }
			}	
			## comment types with txt (evidence / evidence_status)
			## ## cofactor, function, similarity, "catalytic activity" [+ev]?, similarity, 
			## ## "tissue specificity" [+ev], induction, PTM, "developmental stage" [+ev], 
			## ## pathway, "enzyme regulation", subunit, domain, induction, allergen, 
			## ## miscellaneous, disease, "disruption phenotype", biotechnologu, polymorphism
			## ## "mass spectrometry" [+location], "RNA editing" [+location]
			
			if ( ($flag_comment == 1) && ($line =~ /<text>(.*)<\/text>/) ) { 
				if (exists $current_comment{"evidence"}) 
					{ $current_comment{"evidence"} .= ":::::".$1; }
				else { $current_comment{"evidence"} = " :::::".$1; }
			}
			if ( ($flag_comment == 1) && ($line =~ /<text status="(.*)"\/>/) ) { 
				if (exists $current_comment{"evidence"}) 
					{ $current_comment{"evidence_status"} .= $1; }
				else { $current_comment{"evidence_status"} = $1; }
			}
			## comment location , concern types : 
			## ## "sequence caution", "mass spectrometry", "RNA editing"
			if ( ( ($flag_comment == 1) && ( ($flag_comment_subcellularlocation == 0) && ($flag_feature == 0) ) ) 
					&& ($line =~ /<location>/) ) 
				{ $flag_comment_location = 1; }
			if ($flag_comment_location == 1) {
				if ( ($line =~ /<begin position="(.*)"\/>/) 
						|| ($line =~ /<position position="(.*)"\/>/) ) { 
					$tmp_comment_begin = $1;
				}
				if ($line =~ /<end position="(.*)"\/>/) { $tmp_comment_end = $1; }
				if ( ($line =~ /<begin status="(.*)" position="(.*)"\/>/) 
						|| ($line =~ /<position status="(.*)" position="(.*)"\/>/) ) {
					$tmp_comment_begin_status = $1;
					$tmp_comment_begin = $2;
				}
				if ($line =~ /<end status="(.*)" position="(.*)"\/>/) 
					{ $tmp_comment_end_status = $1;$tmp_comment_end = $2; }
				if ($line =~ /<\/location/) { 
					$flag_comment_location = 0;
					my $location = $tmp_comment_begin.":::::".$tmp_comment_end.":::::".$tmp_comment_begin_status.":::::".$tmp_comment_end_status;
					push (@list_comment_location,$location);
					$tmp_comment_begin = 0;
					$tmp_comment_end = 0;
					$tmp_comment_begin_status = " ";
					$tmp_comment_end_status = " ";
				}
			}
			## comment "subcellular location" type : evidenceString
			if ( ($flag_comment == 1) && ($line =~ /<subcellularLocation>/) ) 
				{ $flag_comment_subcellularlocation = 1; }
			if ($flag_comment_subcellularlocation == 1) {
				if ($line =~ /<\/subcellularLocation>/) { $flag_comment_subcellularlocation = 0; }
				if ($line =~ /<location status="(.*)">(.*)<\/location>/) 
					{ push (@list_comment_subcellularlocations,"a:::::".$2.":::::".$1); }
				if ($line =~ /<location>(.*)<\/location>/) 
					{ push (@list_comment_subcellularlocations,"a:::::".$1."::::: "); }
				if ($line =~ /<topology status="(.*)">(.*)<\/topology>/) 
					{ push (@list_comment_subcellularlocations,"b:::::".$2.":::::".$1); }
				if ($line =~ /<topology>(.*)<\/topology>/) 
					{ push (@list_comment_subcellularlocations,"b:::::".$1."::::: "); }
				if ($line =~ /<orientation status="(.*)">(.*)<\/orientation>/) 
					{ push (@list_comment_subcellularlocations,"c:::::".$2.":::::".$1); }
				if ($line =~ /<orientation>(.*)<\/orientation>/) 
					{ push (@list_comment_subcellularlocations,"c:::::".$1."::::: "); }
			}
			## comment "alternative products" type
			if ( ($flag_comment == 1) && ($line =~ /<event type="(.*)"\/>/) ) {
				my $tmp_event_type = $1;
				## event types
				if ($tmp_event_type =~ /^alternative splicing$/) { $tmp_event_type = "1"; }
				if ($tmp_event_type =~ /^alternative initiation$/) { $tmp_event_type = "2"; }
				if ($tmp_event_type =~ /^alternative promoter$/) { $tmp_event_type = "3"; }
				if ($tmp_event_type =~ /^ribosomal frameshifting$/) { $tmp_event_type = "4"; }
				push (@list_comment_events,$tmp_event_type); ## (0) 1 to 4 possibles events
			}
			if ( ($flag_comment == 1) && ($line =~ /<isoform>/) ){ $flag_comment_isoform = 1; }
			if ($flag_comment_isoform == 1) {
				if ($line =~ /<id>(.*)<\/id>/) { push (@list_comment_isoform_id,$1); }
				if ($line =~ /<name>(.*)<\/name>/) { push (@list_comment_isoform_name,$1); }
				if ($line =~ /<sequence type="(.*)" ref="(.*)"\/>/) { 
					$current_comment_isoform{"sequence_type"} = $1; ## here type is often "described"
					$current_comment_isoform{"sequence_ref"} = $2;
				} 
				else { ## here type is often "not described"|"displayed"|"external"
					if ($line =~ /<sequence type="(.*)"\/>/) {
						$current_comment_isoform{"sequence_type"} = $1; 
						$current_comment_isoform{"sequence_ref"} = "";
					}
				}
				## isoform types
				if ( (exists $current_comment_isoform{"sequence_type"}) && ($current_comment_isoform{"sequence_type"} =~ /[a-z]+/) ) {
					if ($current_comment_isoform{"sequence_type"} =~ /^not described$/) { $current_comment_isoform{"sequence_type"} = "1"; }
					if ($current_comment_isoform{"sequence_type"} =~ /^described$/) { $current_comment_isoform{"sequence_type"} = "2"; }
					if ($current_comment_isoform{"sequence_type"} =~ /^displayed$/) { $current_comment_isoform{"sequence_type"} = "3"; }
					if ($current_comment_isoform{"sequence_type"} =~ /^external$/) { $current_comment_isoform{"sequence_type"} = "4"; }
				}
				if ($line =~ /<note>(.*)<\/note>/) { $current_comment_isoform{"note"} = $1; }
				if ($line =~ /<\/isoform>/) { 
					$flag_comment_isoform = 0;
					$current_comment_isoform{"id"} = [ @list_comment_isoform_id ];
					$current_comment_isoform{"name"} = [ @list_comment_isoform_name ];
					@list_comment_isoform_id = ();
					@list_comment_isoform_name = ();
					push (@list_comment_isoform, [ %current_comment_isoform ]);
					%current_comment_isoform = ();
				}
			} ## end comment "alternative products" treatment
			## comment "interaction" type
			if ( ($flag_comment == 1) && ($line =~ /<interactant intactId="(.*)"\/>/) )
				{ $current_comment{"interactant_intactid1"} = $1; }
			if ( ($flag_comment == 1) && ( ($line =~ /<interactant intactId="(.*)"\/>/) && (exists $current_comment{"interactant_intactid1"}) ) )
				{ $current_comment{"interactant_intactid2"} = $1; }
			if ( ($flag_comment == 1) && ($line =~ /<interactant intactId="(.*)">/) )
				{ $current_comment{"interactant_intactid2"} = $1;$flag_comment_interactant = 1; }
			if ( ($flag_comment == 1) && ($line =~ /<organismDiffer>(.*)<\/organismDiffer>/) )
				{ $current_comment{"interactant_organismdiffer"} = ($1 eq "true")?"t":"f"; }
			if ( ($flag_comment == 1) && ($line =~ /<experiments>(.*)<\/experiments>/) ) 
				{ $current_comment{"interactant_experiments"} =  $1; }
			if ($flag_comment_interactant == 1) {
				if ($line =~ /<\/interactant>/) { $flag_comment_interactant = 0; }
				if ($line =~ /<id>(.*)<\/id>/) { $current_comment{"interactant_id"} = $1 }
				if ($line =~ /<label>(.*)<\/label>/) { $current_comment{"interactant_label"} = $1 }
			}
			## comment "sequence caution" type
			if ( ($flag_comment == 1) && ($line =~ /<conflict type="(.*)">/) ) {
				$flag_comment_conflict = 1;
				$current_comment{"conflict_type"} = $1;
				$current_comment{"conflict_ref"} = "";
			}
			if ( ($flag_comment == 1) && ($line =~ /<conflict type="(.*)" ref="(.*)">/) ) {
				$flag_comment_conflict = 1;
				$current_comment{"conflict_type"} = $1;
				$current_comment{"conflict_ref"} = $2; ## never seen but can be encountered...
			}
			if ( (exists $current_comment{"conflict_type"}) && ($current_comment{"conflict_type"} =~ /[a-z]+/) ) {
				if ($current_comment{"conflict_type"} =~ /^frameshift$/) { $current_comment{"conflict_type"} = "1"; }
				if ($current_comment{"conflict_type"} =~ /^erroneous initiation$/) { $current_comment{"conflict_type"} = "2"; }
				if ($current_comment{"conflict_type"} =~ /^erroneous termination$/) { $current_comment{"conflict_type"} = "3"; }
				if ($current_comment{"conflict_type"} =~ /^erroneous gene model prediction$/) { $current_comment{"conflict_type"} = "4"; }
				if ($current_comment{"conflict_type"} =~ /^erroneous translation$/) { $current_comment{"conflict_type"} = "5"; }
				if ($current_comment{"conflict_type"} =~ /^miscellaneous discrepancy$/) { $current_comment{"conflict_type"} = "6"; }
			}
			if ($flag_comment_conflict == 1) {
				if ($line =~ /<\/conflict>/) { $flag_comment_conflict = 0; }
				if ($line =~ /<sequence version="(.*)" resource="(.*)" id="(.*)"\/>/) {
					$current_comment{"conflict_sequence_version"} = $1;
					$current_comment{"conflict_sequence_resource"} = $2;
					$current_comment{"conflict_sequence_id"} = $3;
				}
				if ($line =~ /<sequence resource="(.*)" id="(.*)"\/>/) {
					$current_comment{"conflict_sequence_version"} = "";
					$current_comment{"conflict_sequence_resource"} = $1;
					$current_comment{"conflict_sequence_id"} = $2;
				}
			}
			## comment "online information" type
			if ( ($flag_comment == 1) && ($line =~ /<link uri="(.*)"\/>/) )
				{ $current_comment{"link"} = $1; }
			## comment "biophysicochemical properties" type
			if ( ($flag_comment == 1) && ($line =~ /<phDependence>(.*)<\/phDependence>/) )
				{ $current_comment{"phdependence"} = $1; }
			if ( ($flag_comment == 1) && ($line =~ /<temperatureDependence>(.*)<\/temperatureDependence>/) )
				{ $current_comment{"temperaturedependence"} = $1; }
			if ( ($flag_comment == 1) && ($line =~ /<redoxPotential>(.*)<\/redoxPotential>/) )
				{ $current_comment{"redoxpotential"} = $1; }
			if ( ($flag_comment == 1) && ($line =~ /<kinetics>/) ) { $flag_comment_kinetics = 1; }
			if ($flag_comment_kinetics == 1) {
				if ($line =~ /<\/kinetics>/) { $flag_comment_kinetics = 0; }
				if ($line =~ /<KM>(.*)<\/KM>/) { push (@list_comment_kinetics_km,$1); }
				if ($line =~ /<Vmax>(.*)<\/Vmax>/) { push (@list_comment_kinetics_vmax,$1); }
			}
			if ( ($flag_comment == 1) && ($line =~ /<absorption>/) ) { $flag_comment_absorption = 1; }
			if ($flag_comment_absorption == 1) {
				if ($line =~ /<\/absorption>/) { $flag_comment_absorption = 0; }
				if ($line =~ /<max>(.*)<\/max>/) { $current_comment{"maxabsorption"} = $1; }
			}
			## ending comment
			if ($line =~ /<\/comment>/) { 
				$flag_comment = 0;
				if ($#list_comment_kinetics_km > -1) { $current_comment{"kinetics_km"} = [ @list_comment_kinetics_km ]; }
				if ($#list_comment_kinetics_vmax > -1) { $current_comment{"kinetics_vmax"} = [ @list_comment_kinetics_vmax ]; }
				if ($#list_comment_subcellularlocations > -1) { $current_comment{"subcellularLocations"} = [ @list_comment_subcellularlocations ]; }
				if ($#list_comment_events > -1) { $current_comment{"events"} = [ @list_comment_events ]; }
				if ($#list_comment_location > -1) { $current_comment{"location"} = [ @list_comment_location ]; }
				if ($#list_comment_isoform > -1) { $current_comment{"isoform"} = [ @list_comment_isoform ]; }
				push (@list_comment,[ %current_comment ]);
				@list_comment_kinetics_km = ();
				@list_comment_kinetics_vmax = ();
				@list_comment_isoform = ();
				@list_comment_subcellularlocations = ();
				@list_comment_events = ();
				@list_comment_location = ();
				%current_comment = ();
			}

		}

		close (XML_UNIPROT);

		system "rm ".$item; ## delete the part of database used
	} ## end of repeted launch of xml to oracle insertion generation

} else { print "\t".$flag_insert_done." inserts done\n"; }

##################################################################################
##################################################################################


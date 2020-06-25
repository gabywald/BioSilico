#!/usr/bin/perl -w

use strict;

my @dataNCBI = (
		## "pubmed",				
		"protein",				"nuccore",				"nucleotide",	
		"nucgss",				"nucest",		"structure",			"genome",		
		"genomeprj",			"bioproject",	"biosample",			"biosystems",	
		"blastdbinfo",			"books",		"cancerchromosomes",	"cdd",			
		"gap",					"dbvar",		"epigenomics",			"gene",			
		"gensat",				"gds",			"geo",					"geoprofiles",	
		"homologene",			"journals",		"mesh",					"ncbisearch",	
		"nlmcatalog",			"omia",			"omim",					"pmc",			
		"popset",				"probe",		"proteinclusters",		"pcassay",		
		"pccompound",			"pcsubstance",	"pubmedhealth",			"seqannot",		
		"snp",					"sra",			"taxonomy",				"toolkit",		
		"toolkitall",			"unigene",		"unists",				"gencoll"		
);

my @data = (
		"edam", 					"embl", 					"emblcds", 		"emblcon", 	
		"emblconexp", 				"emblsva", 					"ensemblgene", 	"ensemblgenomesgene", 
		"ensemblgenomestranscript", "ensembltranscript", 		"epo_prt", 		"genomereviews", 
		"genomereviewsgene", 		"genomereviewstranscript", 	"hgnc", 		"hgvbase", 
		"imgthla", 					"imgtligm", 				"interpro", 	"ipdkir", 
		"ipdmhc", 					"ipi", 						"iprmc", 		"iprmcuniparc", 
		"jpo_prt", 					"kipo_prt", 				"livelists", 	"medline", 
		"nrnl1", 					"nrnl2", 					"nrpl1", 		"nrpl2", 
		"pdb", 						"nucleotide", 				"refseqn", 		"protein", 
		"refseqp", 					"resid", 					"sgt", 			"taxonomy", 
		"tracearchive", 			"uniparc", 					"uniprotkb", 	"uniref100", 
		"uniref50", 				"uniref90", 				"unisave", 		"uspto_prt"
);

open (OUTPUT, ">"."toadd.txt");
foreach my $element (@data)  {
	print OUTPUT "\telse if (name.equals(\"".$element."\"))\n";
	print OUTPUT "\t\t{ toReturn = ".ucfirst($element)."Panel.getInstance(); }\n";
}
close (OUTPUT);

open (OUTPUT, ">"."toaddSecond.txt");
foreach my $element (@data)  {
	print OUTPUT "\telse if (database.equals(\"".$element."\"))\n";
	print OUTPUT "\t\t{ toReturn = ".ucfirst($element)."Element.create(iden); }\n";
}
close (OUTPUT);

open (ANOTHEROUTPUT, ">"."toMoreAdd.txt");
foreach my $element (@data)  {
	print ANOTHEROUTPUT "\t\@Override\n";
	print ANOTHEROUTPUT "\tpublic <D extends DatabaseElement> void setSelection(D deElt) \n";
	print ANOTHEROUTPUT "\t\t{ this.setSelection(deElt); }\n\n";	
	print ANOTHEROUTPUT "\tpublic void setSelection(".ucfirst($element)."Element elt) { /** TODO '".$element."' selection !! */; }\n";
}
close ANOTHEROUTPUT;


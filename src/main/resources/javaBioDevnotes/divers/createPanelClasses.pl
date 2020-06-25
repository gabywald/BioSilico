#!/usr/bin/perl -w

use strict;

my $main = "NCBI";
## ## NCBI DB list / set
my @list = ( ## "pubmed",
								"protein",		"nuccore",				"nucleotide",
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

## my $main = "EBI";
## ## EBI DB list / set
my @listEBI = (
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

foreach my $type (@list) {
	my $className	= ucfirst($type);
	my $database	= $type;

	
	open (OUTPUT, ">".$className."Panel.java");
	print OUTPUT "package gabywald.javabio.querying.".lc($main).".view.panels;\n\n";
	print OUTPUT "import java.util.ArrayList;\n";
	print OUTPUT "import gabywald.javabio.querying.".lc($main).".model.".$className."Element;\n";
	print OUTPUT "import gabywald.javabio.querying.DatabaseElement;\n";
	print OUTPUT "import gabywald.javabio.querying.view.DatabasePanel;\n\n";
	print OUTPUT "/**\n";
	print OUTPUT " * \n";
	print OUTPUT " * <br>For database '".$database."' into the ".$main.". \n";
	print OUTPUT " * <br><i>DPSingleton</i>\n";
	print OUTPUT " * \@author Gabriel Chandesris (2011)\n";
	print OUTPUT " * \@author SoBioS (2011)\n";
	print OUTPUT " */\n";
	print OUTPUT "public class ".$className."Panel extends DatabasePanel<".$className."Element> {\n";
	print OUTPUT "\tprivate static ".$className."Panel instance;\n\n";
	print OUTPUT "\tprivate ".$className."Panel() {\n";
	print OUTPUT "\t\tsuper(\"".$database."\", \"".$main."\");\n\t\t\n";
	print OUTPUT "\t}\n\n";
	print OUTPUT "\tpublic static ".$className."Panel getInstance() {\n";
	print OUTPUT "\t\tif (".$className."Panel.instance == null) \n";
	print OUTPUT "\t\t\t{ ".$className."Panel.instance = new ".$className."Panel(); }\n";
	print OUTPUT "\t\treturn ".$className."Panel.instance;\n";
	print OUTPUT "\t}\n\n";
	
	## print OUTPUT "\t\@Override\n";
	## print OUTPUT "\tpublic <D extends DatabaseElement> void setSelection(".$className."Element deElt) \n";
	## print OUTPUT "\t\t{ this.setSelection((".$className."Element)deElt); }\n\n";	
	## print OUTPUT "\tpublic void setSelection(".$className."Element elt) { /** TODO '".$database."' selection !! */; }\n";	
	
	print OUTPUT "\t\@Override\n";
	print OUTPUT "\tpublic void setSelection(".$className."Element elt) {\n";
	print OUTPUT "\t\tsuper.activateSelection(elt != null);\n";
	print OUTPUT "\t\tif (elt != null)\n"; 
	print OUTPUT "\t\t\t{ super.setCompleteDescription(elt.toString()); }\n";
	print OUTPUT "\t\telse { super.setCompleteDescription(\"\"); }\n";
	print OUTPUT "\t}\n\n";
	
	print OUTPUT "\t\@Override\n";
	print OUTPUT "\tprotected void addToRecords(String identifier) {\n";
	print OUTPUT "\t\tif (this.toRecordElts == null) \n";
	print OUTPUT "\t\t\t{ this.toRecordElts = new ArrayList<".$className."Element>(); }\n";
	if ($main eq "NCBI")	{ print OUTPUT "\t\tint ident\t\t= Integer.parseInt(identifier);\n"; }
	elsif ($main eq "EBI")	{ print OUTPUT "\t\t/** int ident\t= Integer.parseInt(identifier); */\n"; }
	print OUTPUT "\t\tboolean added\t= false;\n";
	print OUTPUT "\t\tfor (int i = 0 ; (i < this.elements.size()) && (!added) ; i++) {\n";
	if ($main eq "NCBI")	{ print OUTPUT "\t\t\tif ( ((DatabaseElement)this.elements.get(i)).getIdentifierInt() == ident) {\n"; } 
	elsif ($main eq "EBI")	{ print OUTPUT "\t\t\tif ( ((DatabaseElement)this.elements.get(i)).getIdentifierString().equals(identifier) ) {\n"; }
	
	
	print OUTPUT "\t\t\t\tthis.toRecordElts.add(this.elements.get(i));\n";
	print OUTPUT "\t\t\t\tadded = true;\n";
	print OUTPUT "\t\t\t}\n";
	print OUTPUT "\t\t}\n";
	print OUTPUT "\t}\n\n";
	print OUTPUT "\t\@Override\n";
	print OUTPUT "\tprotected void addToRecords(String[] identifiers) {\n";
	print OUTPUT "\t\tif (this.toRecordElts == null) \n";
	print OUTPUT "\t\t\t{ this.toRecordElts = new ArrayList<".$className."Element>(identifiers.length); }\n";
	print OUTPUT "\t\tfor (int i = 0 ; i < identifiers.length ; i++) \n";
	print OUTPUT "\t\t\t{ this.addToRecords(identifiers[i]); }\n";
	print OUTPUT "\t}\n\n";
	print OUTPUT "}\n";
	close OUTPUT;
	
	# open (OUTPUT, ">".$className."Element.java");
	# print OUTPUT "package gabywald.javabio.querying.".lc($main).".model;\n\n";
	# print OUTPUT "import gabywald.global.exceptions.MessageException;\n";
	# print OUTPUT "import gabywald.global.useragent.UserAgent;\n";
	# print OUTPUT "import gabywald.javabio.querying.DatabaseElement;\n";
	# print OUTPUT "import gabywald.javabio.querying.".lc($main).".QBuilder;\n\n";
	# print OUTPUT "public class ".$className."Element extends DatabaseElement {\n";
	# print OUTPUT "\t/** \n";
	# print OUTPUT "\t * TODO good properties of local (".$database.") format. \n";
	# print OUTPUT "\t * */\n";
	# print OUTPUT "\tprivate static String[] PROPERTIES = { \"EVERYTHING	# (EVRY)	# EVRY	# anything\" };\n\n";
	# print OUTPUT "\tprotected ".$className."Element(String iden) {\n";
	# print OUTPUT "\t\tsuper(iden, ".$className."Element.PROPERTIES);\n";
	# print OUTPUT "\t}\n\n";
	# print OUTPUT "\t/**\n";
	# print OUTPUT "\t * To parse and add a line in ".$className." / ".$database." format. \n";
	# print OUTPUT "\t * \@param line (String)\n";
	# print OUTPUT "\t */\n";
	# print OUTPUT "\tprivate void addAContentElt(String line)\n";
	# print OUTPUT "\t\t{ this.content.put(\"EVRY\", \n";
	# print OUTPUT "\t\t\t\t\t( (this.content.get(\"EVRY\") != null)?\n";
	# print OUTPUT "\t\t\t\t\t\tthis.content.get(\"EVRY\"):\"\") +line+ \"\\n\" ); }\n\n";
	# print OUTPUT "\tpublic String toString() \n";
	# print OUTPUT "\t\t{ return this.content.get(\"EVRY\"); }\n\n";
	# 
	# print OUTPUT "\tpublic static ".$className."Element create(String identifier) {\n";
	# print OUTPUT "\t\tQBuilder qbObtain".$className." = new QBuilder(0);\n";
	# print OUTPUT "\t\tqbObtain".$className.".setDatabase(\"".$database."\");\n";
	# print OUTPUT "\t\tqbObtain".$className.".setIdentifier(identifier);\n";
	# print OUTPUT "\t\tqbObtain".$className.".setModeOfReturn(\"default\");\n\n";
	# print OUTPUT "\t\tString query = null; /** new Query for article one by one. */\n";
	# print OUTPUT "\t\ttry { query = qbObtain".$className.".getBuildedQuery(); } \n";
	# print OUTPUT "\t\tcatch (MessageException e)	# { /** return articles */; }\n\n";
	# print OUTPUT "\t\tUserAgent ua\t\t\t= UserAgent.getUserAgent(query);\n";
	# print OUTPUT "\t\tString[] contentToParse\t= ua.getContentAsTab();\n\n";
	# if ($main eq "NCBI") {
	# 	# print OUTPUT "\t\tint currentID\t\t\t= Integer.parseInt(identifier);\n";
	# 	# print OUTPUT "\t\t".$className."Element elt\t\t\t= new ".$className."Element(currentID);\n";
	# } elsif ($main eq "EBI") { 
	# 	# print OUTPUT "\t\t/** int currentID\t\t= Integer.parseInt(identifier); */\n";
	# 	# print OUTPUT "\t\t".$className."Element elt\t= new ".$className."Element(identifier);\n";
	# }
	# print OUTPUT "\t\tfor (int j = 0 ; j < contentToParse.length ; j++) \n";
	# print OUTPUT "\t\t\t{ elt.addAContentElt(contentToParse[j]); }\n\n";
	# print OUTPUT "\t\treturn elt;\n";
	# print OUTPUT "\t}\n\n";
	# 
	# print OUTPUT "}\n";
	# close OUTPUT;
}
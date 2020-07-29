package gabywald.crypto.data;

import java.util.ArrayList;
import java.util.List;

import gabywald.crypto.model.EncodingNode;
import gabywald.crypto.model.EncodingNodeBuilder;
import gabywald.crypto.model.EncodingNodeException;
import gabywald.crypto.model.GeneticTranslator;
import gabywald.global.data.Fichier;
import gabywald.global.data.Utils;
import gabywald.global.data.samples.GenericDataFile;

/**
 * This abstract class is a repository for some informations (tables, arrays....). 
 * @author Gabriel Chandesris (2011)
 */
public abstract class BiologicalUtils extends Utils {

	/** IUPAC nomenclature : Nucleic Acids { one-letter-code, name / description }. */
	public static final String[][] IUPAC_NUCLEIC_ACID_CODES = {
		{ "A", "adenine" }, 
		{ "C", "cytosine" }, 
		{ "G", "guanine" }, 
		{ "T", "thymine" }, 
		{ "U", "uracil" }, 
		{ "R", "G A (purine)" }, 
		{ "Y", "T C U (pyrimidine)" }, 
		{ "K", "G T U (keto)" }, 
		{ "M", "A C (amino)" }, 
		{ "S", "G C" }, 
		{ "W", "A T U" }, 
		{ "B", "G T U C (not A)" }, 
		{ "D", "G A T U (not C)" }, 
		{ "H", "A C T U (not G)" }, 
		{ "V", "G C A (not T, not U)" }, 
		{ "N", "A G C T U (any)" }
	};
	
	/** IUPAC nomenclature : Amino Acids { one-letter-code, three-letters-code, name / description }. */
	public static final String[][] AMINO_ACID_CODES = {
		{ "A", "Ala", "Alanine" }, 
		{ "R", "Arg", "Arginine" }, 
		{ "N", "Asn", "Asparagine" }, 
		{ "D", "Asp", "Aspartic acid" }, 
		{ "C", "Cys", "Cysteine" }, 
		{ "Q", "Gln", "Glutamine" }, 
		{ "E", "Glu", "Glutamic acid" }, 
		{ "G", "Gly", "Glycine" }, 
		{ "H", "His", "Histidine" }, 
		{ "I", "Ile", "Isoleucine" }, 
		{ "L", "Leu", "Leucine" }, 
		{ "K", "Lys", "Lysine" }, 
		{ "M", "Met", "Methionine" }, 
		{ "F", "Phe", "Phenylalanine" }, 
		{ "P", "Pro", "Proline" }, 
		{ "S", "Ser", "Serine" }, 
		{ "T", "Thr", "Threonine" }, 
		{ "W", "Trp", "Tryptophan" }, 
		{ "Y", "Tyr", "Tyrosine" }, 
		{ "V", "Val", "Valine" }, 
		{ "B", "Asx", "Aspartic acid or Asparagine" }, 
		{ "Z", "Glx", "Glutamine or Glutamic acid" }, 
		{ "J", "Xle", "Leucine or Isoleucine" }, 
		{ "X", "Xaa", "Any amino acid; Unspecified or unknown amino acid" }, 
		{ "U", "Sec", "Selenocysteine" }, 
		{ "O", "Pyl", "Pyrrolysine" }, 
		/**
		 * ++ Ornithine
		 * ++ Citrulline
		 * ++ ...
		 */
	};
	
	/** 
	 * Table of possible features keys for GeneBank format (examples). 
	 * See <a href="https://www.ncbi.nlm.nih.gov/collab/FT/#7.2">https://www.ncbi.nlm.nih.gov/collab/FT/#7.2</a>
	 * */
	public static final String[][] GENBANK_FEATURES_KEYS = {
		{ "Eukaryotic gene",						"source;promoter;mRNA;CDS;exon;intron;polyA_signal" }, 
		{ "Bacterial operon",						"source;operon;-35_signal;-10_signal;CDS;mRNA" }, 
		{ "Artificial cloning vector (circular)",	"source;CDS" }, 
		{ "Repeat element",							"source;repeat_region" }, 
		{ "Immunoglobulin heavy chain",				"source;CDS;v_region" }, 
		{ "T-cell receptor",						"source;sig_peptide;CDS;mat_peptide;V_region;J_segment;C_region" }, 
		{ "Transfer RNA",							"-35_signal;-35_signal;tRNA" }
	};
	
	/** GeneBank divisions for sequences ; { three-letters-code, name / description }. */
	public static final String[][] GENEBANK_DIVISIONS = {
		{ "PRI", "primate sequences" }, 
		{ "ROD", "rodent sequences" }, 
		{ "MAM", "other mammalian sequences" }, 
		{ "VRT", "other vertebrate sequences" }, 
		{ "INV", "invertebrate sequences" }, 
		{ "PLN", "plant, fungal, and algal sequences" }, 
		{ "BCT", "bacterial sequences" }, 
		{ "VRL", "viral sequences" }, 
		{ "PHG", "bacteriophage sequences" }, 
		{ "SYN", "synthetic sequences" }, 
		{ "UNA", "unannotated sequences" }, 
		{ "EST", "EST sequences (expressed sequence tags)" }, 
		{ "PAT", "patent sequences" }, 
		{ "STS", "STS sequences (sequence tagged sites)" }, 
		{ "GSS", "GSS sequences (genome survey sequences)" }, 
		{ "HTG", "HTG sequences (high-throughput genomic sequences)" }, 
		{ "HTC", "unfinished high-throughput cDNA sequencing" }, 
		{ "ENV", "environmental sampling sequences" }, 
	};
	
	/** EMBL divisions for sequences ; { three-letters-code, name / description }. */
	public static final String[][] EMBL_DIVISIONS = {
		{ "PHG", "Bacteriophage" }, 
		{ "ENV", "Environmental Sample" }, 
		{ "FUN", "Fungal" }, 
		{ "HUM", "Human" }, 
		{ "INV", "Invertebrate" }, 
		{ "MAM", "Other Mammal" }, 
		{ "VRT", "Other Vertebrate" }, 
		{ "MUS", "Mus musculus" }, 
		{ "PLN", "Plant" }, 
		{ "PRO", "Prokaryote" }, 
		{ "ROD", "Other Rodent" }, 
		{ "SYN", "Synthetic" }, 
		{ "TGN", "Transgenic" }, 
		{ "UNC", "Unclassified" }, 
		{ "VRL", "Viral" }, 
	};
	
	/** EMBL entry classes ; { three-letters-code, name / description }. */
	public static final String[][] EMBL_CLASSES = {
		{ "CON", "Entry constructed from segment entry sequences; if unannotated, annotation may be drawn from segment entries" }, 
		{ "PAT", "Patent" }, 
		{ "EST", "Expressed Sequence Tag" }, 
		{ "GSS", "Genome Survey Sequence" }, 
		{ "HTC", "High Thoughput CDNA sequencing" }, 
		{ "HTG", "High Thoughput Genome sequencing" }, 
		{ "MGA", "Mass Genome Annotation" }, 
		{ "WGS", "Whole Genome Shotgun" }, 
		{ "TSA", "Transcriptome Shotgun Assembly" }, 
		{ "STS", "Sequence Tagged Site" }, 
		{ "STD", "Standard (all entries not classified as above)" }, 
	};
	
	/** EMBL data keys description ; { two-letters-code, name, counter description }. */
	public static final String[][] EMBL_DATA_KEYS = {
		{ "ID", "identification", 				"(begins each entry; 1 per entry)" }, 
		{ "AC", "accession number", 			"(>=1 per entry)" }, 
		{ "PR", "project identifier", 			"(0 or 1 per entry)" }, 
		{ "DT", "date", 						"(2 per entry)" }, 
		{ "DE", "description", 					"(>=1 per entry)" }, 
		{ "KW", "keyword", 						"(>=1 per entry)" }, 
		{ "OS", "organism species", 			"(>=1 per entry)" }, 
		{ "OC", "organism classification", 		"(>=1 per entry)" }, 
		{ "OG", "organelle", 					"(0 or 1 per entry)" }, 
		{ "RN", "reference number", 			"(>=1 per entry)" }, 
		{ "RC", "reference comment", 			"(>=0 per entry)" }, 
		{ "RP", "reference positions", 			"(>=1 per entry)" }, 
		{ "RX", "reference cross-reference", 	"(>=0 per entry)" }, 
		{ "RG", "reference group", 				"(>=0 per entry)" }, 
		{ "RA", "reference author(s)", 			"(>=0 per entry)" }, 
		{ "RT", "reference title", 				"(>=1 per entry)" }, 
		{ "RL", "reference location", 			"(>=1 per entry)" }, 
		{ "DR", "database cross-reference", 	"(>=0 per entry)" }, 
		{ "CC", "comments or notes", 			"(>=0 per entry)" }, 
		{ "AH", "assembly header", 				"(0 or 1 per entry)" }, 
		{ "AS", "assembly information", 		"(0 or >=1 per entry)" }, 
		{ "FH", "feature table header", 		"(2 per entry)" }, 
		{ "FT", "feature table data", 			"(>=2 per entry)" }, 
		{ "XX", "spacer line", 					"(many per entry)" }, 
		{ "SQ", "sequence header", 				"(1 per entry)" }, 
		{ "CO", "contig/construct line", 		"(0 or >=1 per entry)" }, 
		{ "  ", "bb (blanks) sequence data", 	"(>=1 per entry)" }
	};
	
	public static final String[][] MONTHES = {
		{ "Jan", "JAN", "January" }, 
		{ "Feb", "FEB", "February" }, 
		{ "Mar", "MAR", "March" }, 
		{ "Apr", "APR", "April" }, 
		{ "May", "MAY", "May" }, 
		{ "Jun", "JUN", "June" }, 
		{ "Jul", "JUL", "July" }, 
		{ "Aug", "AUG", "August" }, 
		{ "Sep", "SEP", "September" }, 
		{ "Oct", "OCT", "October" }, 
		{ "Nov", "NOV", "November" }, 
		{ "Dec", "DEC", "December" }
	};
	
	public static String getRandomDate() {
		String toReturn = new String("");
		
		int year	= 1870+Utils.randomValue(150)+Utils.randomValue(150);
		int month	= Utils.randomValue(BiologicalUtils.MONTHES.length);
		int day		= 30;
		if ( ( (month <= 6) && (month%2 == 0) ) 
				|| ( (month >= 7) && (month%2 != 0) ) ) 
			{ day = 31; }
		if (month == 1) 
			{ day = ( (year%4 == 0) && ( (year%100 != 0) 
						&& (year%400 == 0) ) )?29:28; }
		day = Utils.randomValue(day);
		
		toReturn = (day+1)+"-"+BiologicalUtils.MONTHES[month][1]+"-"+(year+1);
		
		return toReturn;
	}
	
	public static String generateIdentifier() {
		String toReturn = new String("");
		String alphabet = "AZERTYUIOPQSDFGHJKLMWXCVBN";
		String numerics = "0123456789";
		
		int numberOfChars = Utils.randomValue(5)+1;
		for (int i = 0 ; i < numberOfChars ; i++) 
			{ toReturn += alphabet.charAt(Utils.
					randomValue(alphabet.length())); }
		
		boolean underScore = (Utils.randomValue(100)%2 == 0);
		toReturn += (underScore)?"_":"";
		
		int numberOfNumbs = Utils.randomValue(5)+7;
		for (int i = 0 ; i < numberOfNumbs ; i++) 
		{ toReturn += numerics.charAt(Utils.
				randomValue(numerics.length())); }
		
		return toReturn;
	}
	
	public static String generateLocationOfSequence() {
		String toReturn = new String("");
		String numerics = "0123456789";
		
		toReturn += "LOC";
		
		for (int i = 0 ; i < 9 ; i++) 
		{ toReturn += numerics.charAt(Utils.
				randomValue(numerics.length())); }
		
		return toReturn;
	}
	
	public static GeneticTranslator getGenericCrypto(int file) {
		/** String fileName = (file == 0)?"genericCryptoASCIIJavaCPlus.txt":"genericCryptoAlphanumeric.txt"; */
		Fichier genericCryptoFile	= (file == 0)?GenericDataFile.getCryptoASCIIJavaCPlus()
												 :GenericDataFile.getCryptoAlphaNumeric();
		
		int sizeOfCodes					= genericCryptoFile.getNbLines()-1;
		
		char[] bases					= null;
		String[] codes					= new String[sizeOfCodes];
		
		List<Integer> starters			= new ArrayList<Integer>();
		List<Integer> stoppers			= new ArrayList<Integer>();
		
		String code	= genericCryptoFile.getLine(0);
		
		bases		= code.substring(11, code.length()-1).toCharArray();
		
		// System.out.println("\t'"+file+"'\t'"+genericCryptoFile.getName()+"'\t'"+code.substring(11, code.length()-1)+"'");
		
		int levels						= 3;
		switch (bases.length) {
		case(4): /** Four (4) bases like 'acgt' or 'ubvp' */
			if (sizeOfCodes <= 64)			{ sizeOfCodes =   64;levels = 3; }
			else if (sizeOfCodes <= 256)	{ sizeOfCodes =  256;levels = 4; }
			else if (sizeOfCodes <= 1024)	{ sizeOfCodes = 1024;levels = 5; }
			/** System.out.println("\t4 => '"+sizeOfCodes+"'\t'"+levels+"'"); */
			break;
		case(20): /** Twenty '"bases" like list of AA's... */
			if (sizeOfCodes <= 400)			{ sizeOfCodes =  400;levels = 2; }
			else if (sizeOfCodes <= 8000)	{ sizeOfCodes = 8000;levels = 3; }
			/** System.out.println("\t20 => '"+sizeOfCodes+"'\t'"+levels+"'"); */
			break;
		default: /** ?? */
			for (int i = 1 ; i < 6 ; i++) {
				double power = Math.pow(bases.length, i);
				if (sizeOfCodes <= power) {
					System.out.println("\t'"+sizeOfCodes+"'\t'"+power+"'\t'"+i+"'");
					sizeOfCodes	= (int)power;
					levels		= i;
					break; /** !! */
				}
			}
			/** System.out.println("\t"+bases.length+" => '"+sizeOfCodes+"'\t'"+levels+"'"); */
		}
		
		for (int i = 1 ; (i < codes.length+1) 
				&& (i < genericCryptoFile.getNbLines()) ; i++) {
			String line = (i < genericCryptoFile.getNbLines())?
							genericCryptoFile.getLine(i):"*****";
			int index = i-1;
			/** System.out.println("\t"+i+"\t'"+line+"'"); */
			if (line.matches("^(.*?)\t[M\\*]$")) {
				String[] table	= line.split("\t");
				codes[index]	= table[0];
				if (table[1].equals("M")) { starters.add(new Integer(index)); }
				if (table[1].equals("*")) { stoppers.add(new Integer(index)); }
			} else { codes[index] = line; }
			
			if (codes[index].equals("SP")) { codes[index] = " "; }
			if (codes[index].equals("HT")) { codes[index] = "\t"; }
			if (codes[index].equals("LF")) { codes[index] = "\n"; }
			if (codes[index].equals("CR")) { codes[index] = "\r"; }
		}
		
		EncodingNodeBuilder enb	= new EncodingNodeBuilder();
		EncodingNode root		= null;
		try {
			root = enb.maxLvls(levels).bases(bases).values(codes).build();
			// new EncodingNode(levels, bases, codes);
			
			/** DONE indicates start's and stop's */
			for (int i = 0 ; i < starters.size() ; i++) 
				{ root.getLeaves()[starters.get(i).intValue()].setStart(true); }
			for (int i = 0 ; i < stoppers.size() ; i++) 
				{ root.getLeaves()[stoppers.get(i).intValue()].setStop(true); }
			// System.out.println(EncodingNode.treeView(root));
			
		} catch (EncodingNodeException e) {
			e.printStackTrace();
			// TODO treat if problem here !
		}
		
		// TODO specific treatment if root is null !!	
		return new GeneticTranslator(root);
	}
}

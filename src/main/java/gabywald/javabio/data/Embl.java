package gabywald.javabio.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.javabio.data.composition.Author;
import gabywald.javabio.data.composition.ComposUtils;
import gabywald.javabio.data.composition.DataBaseCrossRef;
import gabywald.javabio.data.composition.Date;
import gabywald.javabio.data.composition.Feature;
import gabywald.javabio.data.composition.FeatureDefinition;
import gabywald.javabio.data.composition.Organism;
import gabywald.javabio.data.composition.Primary;
import gabywald.javabio.data.composition.Reference;
import gabywald.javabio.data.composition.Sequence;

/**
 * 
 * <br>See in <a href="http://www.ebi.ac.uk/2can/tutorials/formats.html">Help about sequence formats (EBI 2can)</a>
 * <br>See also <a href="http://www.ebi.ac.uk/embl/Documentation/User_manual/usrman.html">EMBL - User Manual</a>
 * @author Gabriel Chandesris (2011)
 */
public class Embl extends BiologicalFormat {
	// TODO Embl reading / writing
	/** 
	- The ID (IDentification line) line is always the first line of an entry. 
	- The XX line contains no data or comments. It is used instead of blank lines to avoid confusion with the sequence data lines.
	- The AC (Accession Number) line lists the accession numbers associated with this entry.
	- The DT (DaTe) line shows when an entry first appeared in the the database and when it was last updated.
	- The DE (DEscription) lines contain general descriptive information about the sequence stored.
	- The KW (KeyWord) lines provide information which can be used to generate cross-reference indexes of the sequence entries based on functional, structural, or other categories deemed important. The keywords chosen for each entry serve as a subject reference for the sequence, and will be expanded as work with the database continues. Often several KW lines are necessary for a single entry.
	- The OS (Organism Species) line specifies the preferred scientific name of the organism which was the source of the stored sequence.
	- The OC (Organism Classification) lines contain the taxonomic classification of the source organism.
	++ balise OG 'organelle' (associated to OS / OC )
	- The RN (Reference Number) line gives a unique number to each reference citation within an entry.
	- The RC (Reference Comment) line type is an optional line type which appears if the reference has a comment.
	- The RP (Reference Position) line type is an optional line type which appears if one or more contiguous base spans of the presented sequence can be attributed to the reference in question.
	- The RX (Reference Cross-reference) line type is an optional line type which contains a cross-reference to an external citation or abstract database.
	- The RA (Reference Author) lines list the authors of the paper (or other work) cited.
	- The RT (Reference Title) lines give the title of the paper (or other work).
	- The RL (Reference Location) line contains the conventional citation information for the reference.
	- The DR (Database Cross-Reference) line cross-references other databases which contain information related to the entry in which the DR line appears.
	- The CC lines are free text comments about the entry, and may be used to convey any sort of information thought to be useful.
	- The FH (Feature Header) lines are present only to improve readability of an entry when it is printed or displayed on a terminal screen. The lines contain no data and may be ignored by computer programs.
	- The FT (Feature Table) lines provide a mechanism for the annotation of the sequence data. Regions or sites in the sequence which are of interest are listed in the table.
A complete and definitive description of the feature table is given here [https://www.ebi.ac.uk/embl/Documentation/FT_definitions/feature_table.html].
	- The SQ (SeQuence header) line marks the beginning of the sequence data and gives a summary of its content.
	- The sequence data lines has lines of code starting with two blanks. The sequence is written 60 bases per line, in groups of 10 bases separated by a blank character, beginning in position 6 of the line. The direction listed is always 5' to 3'
	- The // (terminator) line also contains no data or comments. It designates the end of an entry. 
	 */
	
	/** Table of pair of (KEY, Pattern). */
	public static final String[][] EMBL_DATA_KEYS_PATTERNS = {
		{ "ID", "^ID   (.*?); (SV (.*?); )?((.*?); )?(DNA|RNA|mRNA|.*?); "
			+"((CON|PAT|EST|GSS|HTC|HTG|MGA|WGS|TSA|STS|STD); )?"
			+"(PHG|ENV|FUN|HUM|INV|MAM|VRT|MUS|PLN|PRO|ROD|SYN|TGN|UNC|VRL); "
			+"([0-9]+) BP.$" }, 
		{ "AC", "^AC   (([A-Z]+[0-9]+(\\-[A-Z]+[0-9]+)?; ?)+)$", 			"(>=1 per entry)" }, // ''
		{ "PR", "^PR   (.*?)$", 				"(0 or 1 per entry)" }, 
		{ "DT", "^DT   ([0-9]{2})\\-([A-Z]{3})\\-([0-9]{4}) \\(Rel\\. ([0-9]+), Created\\)$" }, /** 2 Date by Entry !! */
		{ "DT", "^DT   ([0-9]{2})\\-([A-Z]{3})\\-([0-9]{4}) \\(Rel\\. ([0-9]+), Last updated, Version ([0-4]+)\\)$", 			"(2 per entry)" }, /** 2 Date by Entry !! */
	/** 5*/	{ "DE", "^DE   (.*?)$", 			"(>=1 per entry)" }, 
		{ "KW", "^KW   (.*)\\.?$", 				"(>=1 per entry)" }, 
		{ "OS", "^OS   (.*?)$", 				"(>=1 per entry)" }, 
		{ "OC", "^OC   (.*?)$", 				"(>=1 per entry)" }, 
		{ "OG", "^OG   (.*?)$", 				"(0 or 1 per entry)" }, 
	/**10*/	{ "RN", "^RN   \\[([0-9]+)\\]$", 	"(>=1 per entry)" }, 
		{ "RC", "^RC   (.*?)$", 				"(>=0 per entry)" }, 
		{ "RP", "^RP   ([0-9]+)\\-([0-9]+)(.*?)$", 	"(>=1 per entry)" }, 
		{ "RX", "^RX   (.*?); (.*?)\\.$", 		"(>=0 per entry)" }, 
		{ "RG", "^RG   (.*?)$", 				"(>=0 per entry)" }, 
	/**15*/	{ "RA", "^RA   (.*?)$", 			"(>=0 per entry)" }, 
		{ "RT", "^RT   (.*?);?$", 				"(>=1 per entry)" }, 
		{ "RL", "^RL   (.*?)$", 				"(>=1 per entry)" }, 
		{ "DR", "^DR   (.*?); (.*?); (.*?).$", 	"(>=0 per entry)" }, 
		{ "CC", "^CC\\s*(.*?)$", 				"(>=0 per entry)" }, 
	/**20*/	{ "AH", "^AH   (.*?)$", 			"(0 or 1 per entry)" }, 
	/** 'AH   LOCAL_SPAN     PRIMARY_IDENTIFIER     PRIMARY_SPAN     COMP ' */
		{ "AS", "^AS   ([0-9]+)\\-([0-9]+)\\s+([A-Z]{2}[0-9]{6}.[0-9])\\s+([0-9]+)\\-([0-9]+)(\\s+(.*?)\\s*)?$", 				"(0 or >=1 per entry)" }, 
		{ "FH", "^FH(   (.*?))?$", 				"(2 per entry)" }, 
		{ "FT", "^FT   (.*?)\\s+(.*?)$", 		"(>=2 per entry)" }, 
		{ "SQ", "^SQ   Sequence ([0-9]+) BP; ([0-9]+) (A); ([0-9]+) (C); ([0-9]+) (G); ([0-9]+) (T); ([0-9]+) (other);$", 				"(1 per entry)" }, 
	/**25*/	{ "CO", "^CO   (.*?)$", 			"(0 or >=1 per entry)" }, 
		{ "  ", "^     (([acgt]+) ?)(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?\\s+([0-9]+)$", 	"(>=1 per entry)" }, 
		{ "XX", "^XX(.*?)$", 					"(many per entry)" }, 
		{ "//", "^//(.*?)$", 					"(>=1 per entry)" }
	};
	
	public static final Pattern[] EMBL_DATA_PATTERNS = {
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 0][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 1][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 2][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 3][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 4][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 5][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 6][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 7][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 8][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[ 9][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[10][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[11][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[12][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[13][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[14][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[15][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[16][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[17][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[18][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[19][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[20][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[21][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[22][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[23][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[24][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[25][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[26][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[27][1]),
		Pattern.compile(Embl.EMBL_DATA_KEYS_PATTERNS[28][1]),
	};
	
	/** Sequence Part recognition Pattern for EMBL Format. */
	public static final String SEQUENCE_PATTERN = 
		"^\\s{5}(([acgt]+) ?)(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?([acgt]+)?\\s+([0-9]+)$";
	
	/** Some date. */
	private Date created, lastRelease;
	/** Set of accessions... */
	private List<String> accessions;
	/** If part of any project. */ 
	private String project;
	/** Set of Database Cross-refs. */
	private List<DataBaseCrossRef> crossRefs;
	/** Set of data about construction. */
	private String construction; 
	
	
	/** Explicit call to "default" super-constructor. */
	public Embl() {
		super();
		this.created		= null;
		this.lastRelease	= null;
		this.accessions		= new ArrayList<String>();
		this.project		= null;
		this.crossRefs		= new ArrayList<DataBaseCrossRef>();
		this.construction	= null;
	}
	
	
	public String toString() {
		String toReturn = new String("");
		/** ID part 
		 * 'ID   SC10H5 standard; DNA; PRO; 4870 BP.'
		 * 'ID   X56734; SV 1; linear; mRNA; STD; PLN; 1859 BP.'
		 * */
		toReturn += "ID   "+this.locusData[0]+"; ";
		if (this.someDatas[2] != null) 
			{ toReturn += "SV "+this.someDatas[2]+"; "; }
		if (this.locusData[3] != null)
			{ toReturn += this.locusData[3]+"; "; }
		toReturn += this.locusData[2]+"; ";
		if (this.locusData[6] != null)
			{ toReturn += this.locusData[6]+"; "; }
		toReturn += this.locusData[4]+"; ";
		toReturn += this.locusData[1]+" BP.\n";
		toReturn += "XX\n";
		
		/** AC / Accession... XXX ?! multiline */
		toReturn += "AC   ";
		for (int i = 0 ; i < this.accessions.size() ; i++) 
			{ toReturn += ((i > 0)?" ":"")+this.accessions.get(i)+";"; }
		toReturn += "\nXX\n";
		
		/** PR / project... */
		if (this.project != null) 
			{ toReturn += "PR   "+this.project+"\nXX\n"; }
		
		/** DT && DT... */
		if ( (this.created != null) && (this.lastRelease != null) ) { 
			toReturn += "DT   "+this.created.toStringEmbl()+"\n";
			toReturn += "DT   "+this.lastRelease.toStringEmbl()+"\n";
			toReturn += "XX\n";
		}
		
		/** KW / keywords... */
		if (this.someDatas[3] != null) { 
			String[] partsOfKeywords = this.someDatas[3].split("\n");
			if (partsOfKeywords.length == 0) { toReturn += "KW   .\n"; }
			else {
				for (int  i = 0 ; i < partsOfKeywords.length ; i++) 
					{ toReturn += "KW   "+partsOfKeywords[i]+"\n"; }
			}
			toReturn += "XX\n";
		} /** '.' */
		
		/** OS ++ OC part... */
		toReturn += this.organism.toStringEMBL();
		toReturn += "XX\n";
		
		/** R* parts... */
		for (int i = 0 ; i < this.references.size() ; i++) 
			{ toReturn += this.references.get(i).toStringEMBL()+"XX\n"; }
		
		/** DR / crossRefs... */
		for (int i = 0 ; i < this.crossRefs.size() ; i++) 
			{ toReturn += this.crossRefs.get(i).toStringEMBL()+"\n"; }
		toReturn += "XX\n";
		
		/** AH... */
		if (this.primaries.size() > 0) {
			toReturn += "AH   LOCAL_SPAN     PRIMARY_IDENTIFIER     PRIMARY_SPAN     COMP\n";
			for (int i = 0 ; i < this.primaries.size() ; i++) 
				{ toReturn += this.primaries.get(i).toStringEMBL()+"\n"; }
			toReturn += "XX\n";
		}
		
		/** CO... (if CO, no sequence / feature...) XXX check it !! */
		if (this.construction != null) {
			String[] partsOfConstruction = this.construction.split("\n");
			if (partsOfConstruction.length > 0) {
				for (int  i = 0 ; i < partsOfConstruction.length ; i++) 
					{ toReturn += "CO   "+partsOfConstruction[i]+
							((i == partsOfConstruction.length-1)?";":"")+"\n"; }
				toReturn += "//\n";
			}
		} else {
			// "FH   Key             Location/Qualifiers"
			// "FH"
			if (this.features.size() > 0) {
				toReturn += "FH   Key             Location/Qualifiers\nFH\n";
				for (int i = 0 ; i < this.features.size() ; i++) 
					{ toReturn += this.features.get(i).toStringEMBL(); }
				toReturn += "XX\n";
			}
			
			boolean hasBaseCounts = false;
			for (int i = 0 ; (i < this.basesCounts.length) 
					&& (!hasBaseCounts) ; i++) 
				{ if (this.basesCounts[i] > 0) 
						{ hasBaseCounts = true; } }
			if (hasBaseCounts) {
				toReturn += "SQ   Sequence "+this.locusData[1]+" BP; ";
				for (int i = 0 ; i < this.basesCounts.length ; i++) { 
					String countAndName = this.basesCounts[i]+" "+this.basesNames[i]+"; ";
					toReturn += countAndName;
				}
				toReturn = toReturn.substring(0, toReturn.length()-1);
				toReturn += "\n";
				
				toReturn += this.origin.toStringEMBL();
			}
			
			
			toReturn += "//\n";
		}
		
		
		
		return toReturn;
	}
	
	public static List<Embl> fromString(String content) {
		String[] cont		= content.split("\n");
		List<Embl> toReturn	= new ArrayList<Embl>();
		Embl data			= new Embl();
		/** For multilines reading... */
		int marker				= -1;
		for (int i = 0 ; i < cont.length ; i++) {
			Matcher[] matchers 	= 
					new Matcher[Embl.EMBL_DATA_PATTERNS.length];
			int matchTo			= -1;
			for (int j = 0 ; (j < matchers.length) 
					&& (matchTo == -1) ; j++) { 
				matchers[j] = Embl.EMBL_DATA_PATTERNS[j]
				                  .matcher(cont[i]);
				if (matchers[j].matches())	{ matchTo = j; } 
			}
			if (matchTo != -1) { marker = -1; }
			switch(matchTo) {
			case(0): /** 'ID' */
				// BiologicalFormat.showDefault(matchers, matchTo);
				/** [IDENT, numberOfbp, typePrimary, typeSecondary, division, date] */
				data.locusData[0] = matchers[matchTo].group(1);
				data.locusData[1] = matchers[matchTo].group(10);
				data.locusData[2] = matchers[matchTo].group(6);
				// data.locusData[3] = null;
				data.locusData[4] = matchers[matchTo].group(9);
				// data.locusData[5] = null;
				if (matchers[matchTo].group(3) != null) 
					{ data.someDatas[2] = matchers[matchTo].group(3); }
				if (matchers[matchTo].group(5) != null) 
					{ data.locusData[3] = matchers[matchTo].group(5); }
				if (matchers[matchTo].group(8) != null) 
					{ data.locusData[6] = matchers[matchTo].group(8); }
				/** DONE ++ Class of sequence 'matchers[matchTo].group(8)' */
				break;
			case(1): /** 'AC' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				String accessionsToParse = matchers[matchTo].group(1);
				String[] accessionsTable = accessionsToParse.split("; ");
				for (int j = 0 ; j < accessionsTable.length ; j++) {
					String newAccess = accessionsTable[j];
					if (newAccess.lastIndexOf(';') != -1) 
						{ newAccess = newAccess.substring(0, 
										newAccess.lastIndexOf(';')); } 
					data.accessions.add(newAccess);
				}
				break;
			case(2): /** 'PR' */
				BiologicalFormat.showDefault(matchers, matchTo);
				data.project = matchers[matchTo].group(1);
				break;
			case(3): /** 'DT' */
				int dayCre		= Integer.parseInt(matchers[matchTo].group(1));
				String monthCre	= matchers[matchTo].group(2);
				int yearCre		= Integer.parseInt(matchers[matchTo].group(3));
				int releaseCre	= Integer.parseInt(matchers[matchTo].group(4));
				data.locusData[5] = dayCre+"-"+monthCre+"-"+yearCre; /** ?! */
				data.created	= new Date(dayCre, monthCre, yearCre, releaseCre);
				break;
			case(4): /** 'DT' */
				int dayLas		= Integer.parseInt(matchers[matchTo].group(1));
				String monthLas	= matchers[matchTo].group(2);
				int yearLas		= Integer.parseInt(matchers[matchTo].group(3));
				int releaseLas	= Integer.parseInt(matchers[matchTo].group(4));
				int versionLas	= Integer.parseInt(matchers[matchTo].group(5));
				data.locusData[5] = dayLas+"-"+monthLas+"-"+yearLas; /** ?! */
				data.lastRelease= new Date(dayLas, monthLas, yearLas, releaseLas, versionLas);
				break;
			case(5): /** 'DE' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				data.someDatas[0] = matchers[matchTo].group(1);
				break;
			case(6): /** 'KW' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.someDatas[3] == null) 
					{ data.someDatas[3] = matchers[matchTo].group(1)+"\n"; }
				else { data.someDatas[3] += matchers[matchTo].group(1)+"\n"; }
				break;
			case(7): /** 'OS' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				data.organism = new Organism(matchers[matchTo].group(1));
				break;
			case(8): /** 'OC' */ /** multiple lines */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				String taxonomy		= matchers[matchTo].group(1);
				String[] lineage	= 
					taxonomy.split(ComposUtils.TAXONOMY_SEPARATOR);
				for (int j = 0 ; j < lineage.length ; j++) {
					String lineageItem = lineage[j];
					if ( (lineageItem.endsWith(";")) 
							|| (lineageItem.endsWith(".")) ) 
						{ lineageItem = lineageItem.substring(0, 
										lineageItem.length()-1); }
					data.organism.addLineage(lineageItem);
				}
				break;
			case(9): /** 'OG' */
				BiologicalFormat.showDefault(matchers, matchTo);
				data.organism.setOrganelle(matchers[matchTo].group(1));
				break;
			case(10): /** 'RN' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				Reference newRef = new Reference
					(Integer.parseInt(matchers[matchTo].group(1)), 0, 0);
				data.references.add(newRef);
				break;
			case(11): /** 'RC' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.setRemark(matchers[matchTo].group(1));
				}
				break;
			case(12): /** 'RP' */
				// BiologicalFormat.showDefault(matchers, matchTo);
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					int start	= Integer.parseInt(matchers[matchTo].group(1));
					int stop	= Integer.parseInt(matchers[matchTo].group(2));
					tmpRef.setStartStop(start, stop);
				}
				break;
			case(13): /** 'RX' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.addCrossReference(matchers[matchTo].group(1), 
											 matchers[matchTo].group(2));
				}
				break;
			case(14): /** 'RG' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.addGroup(matchers[matchTo].group(1));
				}
				break;
			case(15): /** 'RA' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					List<Author> authors = Author.parseAuthors
								(matchers[matchTo].group(1), false);
					for (int j = 0 ; j < authors.size() ; j++) 
						{ tmpRef.addAuthor(authors.get(j)); }
				}
				break;
			case(16): /** 'RT' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.addToTitle(matchers[matchTo].group(1)+"\n");
				}
				break;
			case(17): /** 'RL' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.addLocation(matchers[matchTo].group(1));
				}
				break;
			case(18): /** 'DR' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				data.crossRefs.add(new DataBaseCrossRef(
						matchers[matchTo].group(1), 
						matchers[matchTo].group(2), 
						matchers[matchTo].group(3)));
				break;
			case(19): /** 'CC' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.someDatas[4] == null) 
					{ data.someDatas[4] = matchers[matchTo].group(1)+"\n"; }
				else { data.someDatas[4] += matchers[matchTo].group(1)+"\n"; }
				break;
			case(20): /** 'AH' */
				// BiologicalFormat.showDefault(matchers, matchTo);
				/** Nothing -- header of 'Third Party Annotation (TPA) and Transcriptome Shotgun Assembly (TSA)'. */
				break;
			case(21): /** 'AS' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (matchers[matchTo].groupCount() > 6) {
					data.primaries.add(new Primary(matchers[matchTo].group(1), 
												   matchers[matchTo].group(2), 
												   matchers[matchTo].group(3), 
												   matchers[matchTo].group(4), 
												   matchers[matchTo].group(5), 
												   matchers[matchTo].group(7)));
				} else {
					data.primaries.add(new Primary(matchers[matchTo].group(1), 
												   matchers[matchTo].group(2), 
												   matchers[matchTo].group(3), 
												   matchers[matchTo].group(4), 
												   matchers[matchTo].group(5)));
				}
				break;
			case(22): /** 'FH' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				/** !? 'nothing to do' ?! */
				break;
			case(23): /** 'FT' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				String featureKey		= matchers[matchTo].group(1);
				String featureContent	= matchers[matchTo].group(2);
				if (!featureKey.equals("")) {
					String currentFeatureKey = matchers[matchTo].group(1);
					String currentFeaturePos = matchers[matchTo].group(2);
					FeatureDefinition fd = FeatureDefinition
								.getFromFactory(currentFeatureKey);
					if (fd != null) {
						Feature toAddFeature = new Feature(fd, currentFeaturePos);
						data.features.add(toAddFeature);
					}
				} else if (data.references.size() > 0) {
					Pattern featContRecognition = Pattern.compile("^/(.*?)=(.*?)$");
					Matcher featContRecognMatch = featContRecognition.matcher(featureContent);
					Feature tmpFea = data.features.get(data.features.size()-1);
					if (featContRecognMatch.matches()) {
						if (!featContRecognMatch.group(1).equals("translation")) 
							{ tmpFea.addQualifier(featContRecognMatch.group(1), 
												  featContRecognMatch.group(2)); }
						else {
							/** translation... */
							String translationContent	= featContRecognMatch.group(2);
							String moreContent			= new String("");
							Pattern translationFollow	= Pattern.compile("^FT\\s+(.*?)$");
							Matcher tmpFollow			= translationFollow.matcher(cont[i]);
							// '                   '
							do {
								i++;
								tmpFollow		= translationFollow.matcher(cont[i]);
								if (!tmpFollow.matches()) 
									{ System.out.println("--"); }
								else {
									moreContent			= tmpFollow.group(1);
									translationContent	+= moreContent;
								}
							} while( (!moreContent.endsWith("\""))
											&& (tmpFollow.matches()) );
							tmpFea.addQualifier(featContRecognMatch.group(1), 
												translationContent);
						}

					}
				}/** END 'if (data.references.size() > 0)' */
				break;
			case(24): /** 'SQ' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				for (int j = 1 ; j < matchers[matchTo].groupCount() ; j++) {
					/** System.out.println("\t'"+j+" ("+(j%2)+"-"+(j/2)+") "
							+"'\t'"+matchers[matchTo].group(j+1)+"'"); */
					if (j%2 != 0) /** 'Base count' else 'Base name'. */
						{ data.basesCounts[(j-1)/2] = 
							Integer.parseInt(matchers[matchTo].group(j+1)); }
					else { data.basesNames[(j-1)/2] = matchers[matchTo].group(j+1); }
				}
				break;
			case(25): /** 'CO' */
				BiologicalFormat.showDefault(matchers, matchTo);
				/** TODO precise usage of COnstruction here !. */
				if (data.construction == null) 
					{ data.construction = matchers[matchTo].group(1)+"\n"; }
				else { data.construction += matchers[matchTo].group(1)+"\n"; }
				break;
			case(26): /** '  ' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				if (data.origin == null) { data.origin = new Sequence(); }
				int last = matchers[matchTo].groupCount();
				// String sequencePartLength	= matchers[matchTo].group(last);
				// String sequencePartSegmen	= matchers[matchTo].group(1);
				String whole = new String("");
				for (int j = 2 ; (j < last) 
						&& (matchers[matchTo].group(j) != null) ; j += 2) 
					{ whole += matchers[matchTo].group(j); }
				data.origin.addInSequence(whole);
				// System.out.println(whole);
				break;
			case(27): /** 'XX' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				/** Nothing happend here !! */
				break;
			case(28): /** '//' */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				toReturn.add(data);
				data = new Embl();
				break;
			default:System.out.println("\t'"+marker+"'\t'"+cont[i]+"'");
			}
			
			
		}
		
		
		return toReturn;
	}
	
}

package gabywald.javabio.data;

import gabywald.javabio.data.composition.Author;
import gabywald.javabio.data.composition.ComposUtils;
import gabywald.javabio.data.composition.Feature;
import gabywald.javabio.data.composition.FeatureDefinition;
import gabywald.javabio.data.composition.Organism;
import gabywald.javabio.data.composition.Primary;
import gabywald.javabio.data.composition.Reference;
import gabywald.javabio.data.composition.Sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * <br>For GenBank Flat File Format Definition : <a href="https://www.ncbi.nlm.nih.gov/Sitemap/samplerecord.html">https://www.ncbi.nlm.nih.gov/Sitemap/samplerecord.html</a>
 * <br>For GenBank Format Features Definition : <a href="https://www.ncbi.nlm.nih.gov/collab/FT/">https://www.ncbi.nlm.nih.gov/collab/FT/</a>
 * @author Gabriel Chandesris (2011)
 */
public class GenBank extends BiologicalFormat {

	/** Table of pair of (KEY, Pattern). */
	private static final String[][] GENBANK_DATA_KEYS_PATTERNS = {
		/** LOCUS : {locus name}\s+{sequence length}\s+{molecule type : DNA, RNA...}\s+{GenBank Division}\s+{Modification date} */
		{ "LOCUS",		"^LOCUS\\s+([A-Z_0-9]+)\\s+([0-9]+) bp\\s+(DNA|RNA|mRNA|.*?)\\s+(.*?)\\s+(PRI|ROD|MAM|VRT|INV|PLN|BCT|VRL|PHG|SYN|UNA|EST|PAT|STS|GSS|HTG|HTC|ENV)\\s+([0-9]+\\-[A-Z]{3}\\-[0-9]{4})$" },
		/** DEFINITION : multiples lines allowed */
		{ "DEFINITION",	"^DEFINITION\\s+(.*)$" }, 
		{ "ACCESSION",	"^ACCESSION\\s+(.*)$" },
		{ "VERSION",	"^VERSION\\s+(.*)$" },
		{ "KEYWORDS",	"^KEYWORDS\\s+(.*)\\.$" }, 
	/** 5*/	{ "SOURCE",		"^SOURCE\\s+(.*)$" }, 
		/** ORGANISM : one line is name (follows the keys, others lines are taxonomy / lineage. */
		{ "ORGANISM",	"^  ORGANISM\\s+(.*)$" }, 
		/** REEFRENCE : {numRef}\\s+(bases {start} to {end})} */
		{ "REFERENCE",	"^REFERENCE\\s+([0-9]+)\\s+\\(bases ([0-9]+) to ([0-9]+)\\)$" }, 
		{ "AUTHORS",	"^  AUTHORS\\s+(.*)$" }, 
		/** TITLE : multiples lines allowed */
		{ "TITLE",		"^  TITLE\\s+(.*)$" }, 
		/** JOURNAL : multiples lines allowed */
	/**10*/	{ "JOURNAL",	"^  JOURNAL\\s+(.*)$" }, 
		{ "REMARK", 	"^  REMARK\\s+(.*)$" }, 
		{ "PUBMED",		"^   PUBMED\\s+([0-9]+)$" }, 
		{ "FEATURES",	"^FEATURES\\s+Location/Qualifiers$" }, 
		{ "ORIGIN",		"^ORIGIN(.*)$" }, 
	/**15*/	{ "COMMENT", 	"^COMMENT\\s+(.*)$" }, 
		/** XXX What is PRIMARY ?? => Thirds-Party-Annotations */
		{ "PRIMARY",	"^PRIMARY\\s+REFSEQ_SPAN\\s+PRIMARY_IDENTIFIER\\s+PRIMARY_SPAN\\s+COMP$" }, 
		{ "PRIMARY_CONTENT",	"^\\s+([0-9]+)\\-([0-9]+)\\s+([A-Z]{2}[0-9]{6}.[0-9])\\s+([0-9]+)\\-([0-9]+)(\\s+(.*?)\\s*)?$" },
		{ "END_OF_GENFILE",		"^//$" },
		/** Originally "^BASE COUNT\\s+([0-9]+) (a)\\s+([0-9]+) (c)\\s+([0-9]+) (g)\\s+([0-9]+) (t)\\s+([0-9]+) (others)$" */
		{ "BASECOUNT", 			"^BASE COUNT\\s+([0-9]+) (a)\\s+([0-9]+) (c)\\s+([0-9]+) (g)\\s+([0-9]+) (t)\\s+([0-9]+) (others)$" }, 
		{ "MEDLINE", 			"^  MEDLINE\\s+([0-9]+)$" }, 
	};
	
	private static final Pattern[] GENBANK_DATA_PATTERNS = {
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 0][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 1][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 2][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 3][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 4][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 5][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 6][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 7][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 8][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[ 9][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[10][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[11][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[12][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[13][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[14][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[15][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[16][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[17][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[18][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[19][1]),
		Pattern.compile(GenBank.GENBANK_DATA_KEYS_PATTERNS[20][1]),
	};
	
	/** Sequence Part recognition Pattern for GeneBank Format. */
	public static final String SEQUENCE_PATTERN = 
		"^\\s+([0-9]+) (([acgt]+) ?)(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?(([acgt]+) ?)?([acgt]+)?$";
	
	/** Explicit call to "default" super-constructor. */
	public GenBank() { super(); }
	
	public String toString() {
		String toReturn = new String("");
		
		toReturn += ComposUtils.GENBANK_TAG_LOCUS/** +this.locusData[0] */;
		/** for (int i = 1 ; i < this.locusData.length ; i++) 
			{ toReturn += "\t"+this.locusData[i]+((i == 1)?" bp":""); } */
		toReturn += Sequence.completeDataWith(this.locusData[0]			, ' ', false,18);
		toReturn += Sequence.completeDataWith(this.locusData[1]+" bp"	, ' ', true, 10);
		toReturn += Sequence.completeDataWith(this.locusData[2]			, ' ', true, 8);
		toReturn += Sequence.completeDataWith(this.locusData[3]			, ' ', true, 10);
		toReturn += Sequence.completeDataWith(this.locusData[4]			, ' ', true, 6);
		toReturn += " "+this.locusData[5]; /** date */
		toReturn += "\n";
		
		toReturn += ComposUtils.GENBANK_TAG_DEFINITION	+this.someDatas[0]+"\n";
		toReturn += ComposUtils.GENBANK_TAG_ACCESSION	+this.someDatas[1]+"\n";
		toReturn += ComposUtils.GENBANK_TAG_VERSION		+this.someDatas[2]+"\n";
		toReturn += ComposUtils.GENBANK_TAG_KEYWORDS	+this.someDatas[3]+".\n";
		
		toReturn += this.organism.toStringGeneBank();
		
		for (int i = 0 ; i < this.references.size() ; i++) 
			{ toReturn += this.references.get(i).toStringGeneBank(); }
		
		if (this.someDatas[4] != null) { /** XXX mandatory comments... ?? */
			String[] partsOfComment = this.someDatas[4].split("\n");
			toReturn += ComposUtils.GENBANK_TAG_COMMENT+partsOfComment[0]+"\n";
			for (int  i = 1 ; i < partsOfComment.length ; i++) 
				{ toReturn += ComposUtils.BEGIN_EMPTY_LINE
								+partsOfComment[i]+"\n"; }
		}
		
		if (this.primaries.size() > 0) {
			toReturn += ComposUtils.GENBANK_PRIMARY_LINE+"\n";
			for (int i = 0 ; i < this.primaries.size() ; i++) 
				{ toReturn += this.primaries.get(i).toStringGeneBank()+"\n"; }
		}

		if (this.features.size() > 0) {
			toReturn += ComposUtils.GENBANK_FEATURES_LINE+"\n";
			for (int i = 0 ; i < this.features.size() ; i++) 
				{ toReturn += this.features.get(i).toStringGeneBank(); }
		}
		
		boolean hasBaseCounts = false;
		for (int i = 0 ; (i < this.basesCounts.length) 
				&& (!hasBaseCounts) ; i++) 
			{ if (this.basesCounts[i] > 0) 
					{ hasBaseCounts = true; } }
		if (hasBaseCounts) {
			toReturn += ComposUtils.GENBANK_TAG_BASECOUNT;
			for (int i = 0 ; i < this.basesCounts.length ; i++) { 
				String countAndName = Sequence.completeDataWith(this.basesCounts[i], 
										' ', true, 7)+" "+this.basesNames[i];
				toReturn += countAndName;
			}
			toReturn += "\n";
		}
		
		toReturn += ComposUtils.GENBANK_ORIGIN_LINE+"\n";
		toReturn += this.origin.toStringGenBank();
		
		toReturn += "//";
		
		return toReturn;
	}
	
	public static List<GenBank> fromString(String content) {
		String[] cont			= content.split("\n");
		List<GenBank> toReturn	= new ArrayList<GenBank>();
		GenBank data			= new GenBank();
		/** For multilines reading... */
		int marker				= -1;
		
		for (int i = 0 ; i < cont.length ; i++) {
			Matcher[] matchers 	= 
					new Matcher[GenBank.GENBANK_DATA_PATTERNS.length];
			int matchTo			= -1;
			for (int j = 0 ; (j < matchers.length) 
					&& (matchTo == -1) ; j++) { 
				matchers[j] = GenBank.GENBANK_DATA_PATTERNS[j]
				                  .matcher(cont[i]);
				if (matchers[j].matches())	{ matchTo = j; } 
			}
			if (matchTo != -1) { marker = -1; }
			switch(matchTo) {
			case(0): /** LOCUS */
				/** for (int j = 0 ; j < matchers[matchTo].groupCount() ; j++)  
					{ System.out.println("\t"+j+"\t'"+matchers[matchTo].group(j+1)+"'"); } */
				for (int j = 0 ; (j < data.locusData.length) 
									&& (j < matchers[matchTo].groupCount()) ; j++) 
					{ data.locusData[j] = matchers[matchTo].group(j+1); }
				break;
			case(1): /** DEFINITION */
				data.someDatas[0] = matchers[matchTo].group(1);
				break;
			case(2): /** ACCESSION */
				data.someDatas[1] = matchers[matchTo].group(1);
				break;
			case(3): /** VERSION */
				data.someDatas[2] = matchers[matchTo].group(1);
				break;
			case(4): /** KEYWORDS */
				data.someDatas[3] = matchers[matchTo].group(1);
				break;
			case(5): /** SOURCE */
				data.organism = new Organism(matchers[matchTo].group(1));
				break;
			case(6): /** ORGANISM */
				data.organism.setOrganism(matchers[matchTo].group(1));
				marker = 6; /** DONE adding taxonomy... */
				break;
			case(7): /** REFERENCE */
				int num = Integer.parseInt(matchers[matchTo].group(1));
				int sta = Integer.parseInt(matchers[matchTo].group(2));
				int end = Integer.parseInt(matchers[matchTo].group(3));
				Reference ref = new Reference(num, sta, end);
				data.references.add(ref);
				break;
			case(8): /** AUTHORS */
				/** DONE authors : multiples lines ++ parsing !! */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					List<Author> authors = Author.parseAuthors
								(matchers[matchTo].group(1), true);
					for (int j = 0 ; j < authors.size() ; j++) 
						{ tmpRef.addAuthor(authors.get(j)); }
					marker = 8;
				}
				break;
			case(9): /** TITLE */
				/** DONE title : multiples lines !! */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.setTitle(matchers[matchTo].group(1));
					marker = 9;
				}
				break;
			case(10): /** JOURNAL */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.setJournal(matchers[matchTo].group(1));
				}
				break;
			case(11): /** REMARK */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.setRemark(matchers[matchTo].group(1));
				}
				break;
			case(12): /** PUBMED */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.setPubmedID(matchers[matchTo].group(1));
				}
				break;
			case(13): /** FEATURES */
				marker = 13;
				break;
			case(14): /** ORIGIN */
				data.origin = new Sequence();
				marker = 14;
				break;
			case(15): /** COMMENT */
				if (data.someDatas[4] == null) 
					{ data.someDatas[4] = matchers[matchTo].group(1)+"\n"; }
				marker = 15;/** DONE multiple lines... */
				break;
			case(16): /** PRIMARY */
				/** marker = 16; */
				break;
			case(17): /** PRIMARY DATA */
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
			case(18): /** END GENBANK DATA */
				toReturn.add(data);
				data = new GenBank();
				break;
			case(19): /** BASE COUNT */
				/** BiologicalFormat.showDefault(matchers, matchTo); */
				for (int j = 0 ; j < matchers[matchTo].groupCount() ; j++) {
					/** System.out.println("\t'"+j+" ("+(j%2)+"-"+(j/2)+") "
							+"'\t'"+matchers[matchTo].group(j+1)+"'"); */
					if (j%2 == 0) /** 'Base count' else 'Base name'. */
						{ data.basesCounts[j/2] = 
							Integer.parseInt(matchers[matchTo].group(j+1)); }
					else { data.basesNames[j/2] = matchers[matchTo].group(j+1); }
				}
				break;
			case(20): /** MEDLINE */
				if (data.references.size() > 0) {
					Reference tmpRef = 
						data.references.get(data.references.size()-1);
					tmpRef.setMedline(matchers[matchTo].group(1));
				}
				break;
			default:
				Pattern genericBlank = Pattern.compile("^\\s+(.*?)$");
				Matcher matcherBlank = genericBlank.matcher(cont[i]);
				if (!matcherBlank.matches()) 
					{ System.out.println("--");marker = -1; }
				switch(marker) {
				case(6): /** ORGANISM : taxonomy */
					String taxonomy		= matcherBlank.group(1);
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
				case(8): /** AUTHORS : following... */
					if (data.references.size() > 0) {
						Reference tmpRef = 
							data.references.get(data.references.size()-1);
						List<Author> authors = Author.parseAuthors
									(matcherBlank.group(1), true);
						for (int j = 0 ; j < authors.size() ; j++) 
							{ tmpRef.addAuthor(authors.get(j)); }
					}
					break;
				case(9): /** TITLE: following... */
					if (data.references.size() > 0) {
						Reference tmpRef = 
							data.references.get(data.references.size()-1);
						tmpRef.addToTitle(matcherBlank.group(1));
					}
					break;
				case(13): /** FEATURES : content */
					Pattern featureRecognition = Pattern.compile("^\\s+(.*?)\\s+(.*?)$");
					Matcher featureRecognMatch = featureRecognition.matcher(cont[i]);
					
					/** Pattern featContRecognition = Pattern.compile("^/(.*?)=(.*?)$"); */
					/** Pattern featContRecognition = Pattern.compile("^/(.*?)=\"(.*?)\"$"); */
					Pattern featContRecognition = Pattern.compile("^/(.*?)=(.*?)$");
					Matcher featContRecognMatch	= featContRecognition.matcher(matcherBlank.group(1));
					
					if ( (featureRecognMatch.matches()) && (!featContRecognMatch.matches()) ) {
						String currentFeatureKey	= featureRecognMatch.group(1);
						String currentFeaturePos	= featureRecognMatch.group(2);
						FeatureDefinition fd		= FeatureDefinition
														.getFromFactory(currentFeatureKey);
						if (fd != null) {
							Feature toAddFeature	= new Feature(fd, currentFeaturePos);
							data.features.add(toAddFeature);
						}
					} else if (data.references.size() > 0) {
						Feature tmpFea = 
							data.features.get(data.features.size()-1);
						if (featContRecognMatch.matches()) {
							if (!featContRecognMatch.group(1).equals("translation")) 
								{ tmpFea.addQualifier(featContRecognMatch.group(1), 
													  featContRecognMatch.group(2)); }
							else {
								/** translation... */
								String translationContent	= featContRecognMatch.group(2);
								String moreContent			= new String("");
								do {
									i++;
									matcherBlank			= genericBlank.matcher(cont[i]);
									if (!matcherBlank.matches()) 
										{ System.out.println("--"); }
									else {
										moreContent			= matcherBlank.group(1);
										translationContent	+= moreContent;
									}
								} while( (!moreContent.endsWith("\""))
											&& (matcherBlank.matches()) );
								tmpFea.addQualifier(featContRecognMatch.group(1), 
													translationContent);
							}
							
						}
					} /** END 'if (data.references.size() > 0)' */
					break;
				case(14): /** ORIGIN : sequence */
					/** '     5341 ggaagcactg ctgtgtccgc tttcatgaac acagcctggg accagggcat attaaaggct' */
					Pattern sequenceRecognition = Pattern.compile(GenBank.SEQUENCE_PATTERN);
					Matcher sequenceRecognMatch = sequenceRecognition.matcher(cont[i]);
					if (sequenceRecognMatch.matches()) {
						/** for (int j = 0 ; j < sequenceRecognMatch.groupCount() ; j++) 
							{ System.out.print("\t"+j+"-'"+sequenceRecognMatch.group(j)+"'"); }
						System.out.println(); */
						String part01	= sequenceRecognMatch.group(3);
						String part02	= sequenceRecognMatch.group(5);
						String part03	= sequenceRecognMatch.group(7);
						String part04	= sequenceRecognMatch.group(9);
						String part05	= sequenceRecognMatch.group(11);
						String part06	= sequenceRecognMatch.group(12);
						
						String whole	= part01
										  +((part02 != null)?part02:"")
										  +((part03 != null)?part03:"")
										  +((part04 != null)?part04:"")
										  +((part05 != null)?part05:"")
										  +((part06 != null)?part06:"");
						
						/** System.out.println(whole); */
						data.origin.addInSequence(whole);
					}
					break;
				case(15): /** COMMENT: following... */
					data.someDatas[4] += matcherBlank.group(1)+"\n";
					break;
				// case(16): /** (PRIMARY) */
				// case(17): /** (PRIMARY DATA) */
				// 	System.out.println("\t*****\t'"+matchTo+"'\t'"+marker+"'");
				// 	break;
				default:System.out.println("\t'"+marker+"'\t'"+cont[i]+"'");
				}
			}
		}
		
		return toReturn;
	}

	public void addReference(Reference tmpRef) 
		{ this.references.add(tmpRef); }
	
}

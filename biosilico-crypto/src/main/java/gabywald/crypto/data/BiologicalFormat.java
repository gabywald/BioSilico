package gabywald.crypto.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import gabywald.crypto.data.composition.Feature;
import gabywald.crypto.data.composition.FeaturesListe;
import gabywald.crypto.data.composition.Organism;
import gabywald.crypto.data.composition.Primary;
import gabywald.crypto.data.composition.Reference;
import gabywald.crypto.data.composition.Sequence;


/**
 * Generic definition of Biological (File) Format. 
 * <br>See mostly NCBI WebSite Pages...
 * <br>Basics defines on <a href="https://www.genomatix.de/online_help/help/sequence_formats.html">https://www.genomatix.de/online_help/help/sequence_formats.html</a>. 
 * <br>More on <a href="https://www.ebi.ac.uk/2can/tutorials/formats.html">https://www.ebi.ac.uk/2can/tutorials/formats.html</a>
 * <br>See also <a href="http://en.wikipedia.org/wiki/Category:Biological_sequence_format">http://en.wikipedia.org/wiki/Category:Biological_sequence_format</a>. 
 * @author Gabriel Chandesris (2011)
 */
public abstract class BiologicalFormat {
	/***
	 * @see BiologicalFormat#basesCounts
	 * @see BiologicalFormat#basesNames
	 */
	public static final int BASES_COUNT_SIZE = 5;
	// private int id;
	protected List<Reference> references;
	protected FeaturesListe features;
	/** Third Party Annotations. */
	protected List<Primary> primaries;
	protected Organism organism;
	protected Sequence origin;
	/** Some useful attributes [definition, accession, version, keywords, comment]. */
	protected String[] someDatas;
	/** Some data about locus [IDENT, numberOfbp, typePrimary, typeSecondary, division, date, class]... 
	 * <br>typePrimary		= {DNA, RNA, mRNA...}
	 * <br>typeSecondary	= {linear, circular...}
	 * <br>division			= {group (+sequence class for GenBank)}
	 * <br>class			= {sequence class (Embl)}
	 *  */
	protected String[] locusData;
	/** Counts for '[a, c, g, t, others]' */
	protected int[] basesCounts;
	/** Names of bases counted (5). */
	protected String[] basesNames;
	
	/** Default Constructor. */
	protected BiologicalFormat() {
		// this.id				= ident;
		this.basesCounts	= new int[BiologicalFormat.BASES_COUNT_SIZE];
		this.basesNames		= new String[BiologicalFormat.BASES_COUNT_SIZE];
		for (int i = 0 ; i < BiologicalFormat.BASES_COUNT_SIZE ; i++) {
			this.basesCounts[i]	= 0;
			this.basesNames[i]	= null;
		}
		this.someDatas		= new String[5];
		for (int i = 0 ; i < this.someDatas.length ; i++)
			{ this.someDatas[i] = null; }
		this.locusData		= new String[7];
		for (int i = 0 ; i < this.locusData.length ; i++)
			{ this.locusData[i] = null; }
		this.organism		= null;
		this.origin			= null;
		this.references		= new ArrayList<Reference>();
		this.features		= new FeaturesListe();
		this.primaries		= new ArrayList<Primary>();
	}
	
	protected static void showDefault(Matcher[] matchers, int match) {
		System.out.println("BiologicalFormat.showDefault(..., ...)");
		System.out.println("\t"+match+"\t("
				+EmblFormat.EMBL_DATA_KEYS_PATTERNS[match][0]
				+")\t'"+matchers[match].groupCount()+"'");
		for (int i = 1 ; i <= matchers[match].groupCount() ; i++) 
			{ System.out.println("\t\t"+i+" -- '"+matchers[match].group(i)+"'"); }
	}
	
	public void addReference(Reference ref) 
		{ this.references.add(ref); }
	
	public void setReferences(List<Reference> refs) 
		{ this.references = refs; }
	
	public void addFeature(Feature feat) 
		{ this.features.add(feat); }
	
	public void setFeatures(FeaturesListe feats) 
		{ this.features = feats; }
	
	public void setPrimaries(List<Primary> prims) 
		{ this.primaries = prims; }
	
	public void setOrganism(Organism orga) 
		{ this.organism = orga; }
	
	public void setSequence(Sequence seq) 
		{ this.origin = seq; }
	
	public void setDefinition(String def)	{ this.someDatas[0] = def; }
	public void setAccession(String acc)	{ this.someDatas[1] = acc; }
	public void setVersion(String  ver)		{ this.someDatas[2] = ver; }
	public void setKeywords(String key)		{ this.someDatas[3] = key; }
	public void setComment(String com)		{ this.someDatas[4] = com; }
	
	public void setIdentification(String iden)	{ this.locusData[0] = iden; }
	public void setBasePairNumber(String bpnu)	{ this.locusData[1] = bpnu; }
	public void setPrimaryType(String prty)		{ this.locusData[2] = prty; }
	public void setSecondaryType(String sety)	{ this.locusData[3] = sety; }
	public void setDivision(String divi)		{ this.locusData[4] = divi; }
	public void setDate(String date)			{ this.locusData[5] = date; }
	public void setClass(String clas)			{ this.locusData[6] = clas; }
	
	public void setBasesCountsAndNames(int[] counts, String[] names) {
		if (counts.length <= names.length) {
			this.basesCounts	= counts;
			this.basesNames		= names;
		}
	}

	public List<Reference> getReferences()	{ return this.references; }
	public FeaturesListe getFeatures()		{ return this.features; }
	public List<Primary> getPrimaries()		{ return this.primaries; }
	public Organism getOrganism()			{ return this.organism; }
	public Sequence getOrigin()				{ return this.origin; }
	public String[] getSomeDatas()			{ return this.someDatas; }
	public String[] getLocusData()			{ return this.locusData; }
	public int[] getBasesCounts()			{ return this.basesCounts; }
	public String[] getBasesNames()			{ return this.basesNames; }

}

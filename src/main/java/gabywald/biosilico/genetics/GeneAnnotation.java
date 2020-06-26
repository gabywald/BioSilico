package gabywald.biosilico.genetics;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.structures.GeneAnnotationFeatureListe;
import gabywald.biosilico.utils.Sequence;
import gabywald.global.structures.StringCouple;
import gabywald.global.structures.StringListe;

public class GeneAnnotation extends Gene {
	/** 
	 * String properties annotations of the Gene. 
	 * <p></p>
	 * <ul>
	 * <li>0  : LOCUS</li>
	 * <li>1  : DEFINITION</li>
	 * <li>2  : ACCESSION</li>
	 * <li>3  : VERSION</li>
	 * <li>4  : KEYWORDS</li>
	 * <li>5  : SOURCE</li>
	 * <li>6  : ORGANISM</li>
	 * <li>7  : (lineage)</li>
	 * <li>8  : COMMENT</li>
	 * <li>9  : BASE COUNT</li>
	 * </ul>
	 */
	private StringListe properties;
	private static final int PROPERTIES_NUMBER = 10;
	/**
	 * <br>Convention is to attribute positions (x+i) in the list : 
	 * <p></p>
	 * <ul>
	 * <li>i=0 : the number (x) of the reference and the location concerned</li>
	 * <li>i=1 : AUTHORS</li>
	 * <li>i=2 : TITLE</li>
	 * <li>i=3 : JOURNAL</li>
	 * <li>i=4 : "MEDLINE"</li>
	 * <li>i=5 : "PUBMED"</li>
	 * </ul>
	 */
	private StringListe references;
	/** List of features of annotated gene. */
	private GeneAnnotationFeatureListe features;
	/** Current index to feature. */
	private int currentFeatureIndex = -1;
	/** Sequence description of the annotated gene. */
	private Sequence sequence;
	
	/**
	 * Default constructor of annotated gene instance. 
	 * <br>Properties as Gene are false or 0. 
	 * <br>Initialization of Liste's. 
	 */
	public GeneAnnotation() {
		super(false, false, false, false, 0, 0, 0, 0);
		this.properties = new StringListe();
		this.references = new StringListe();
		this.features = new GeneAnnotationFeatureListe();
		this.sequence = new Sequence();
		for (int i = 0 ; i <= GeneAnnotation.PROPERTIES_NUMBER ; i++) 
			{ this.properties.addString(""); }
	}
	
	/**
	 * To know if current instance of GeneAnnotation is valid or not. 
	 * @return (boolean)
	 */
	public boolean isValid() {
		if (this.references.length() == 0)			{ return false; }
		if (this.features.length() == 0)			{ return false; }
		if (this.sequence.getSequence().equals(""))	{ return false; }
		return true;
	}
	
	public void addProperty(int i, String content) 
		{ this.properties.setString(content, i); }
	
	public void addProperty(String type, String content) {
		if (type.equals("LOCUS")) 			{ this.addProperty(0, content); }
		else if (type.equals("DEFINITION"))	{ this.addProperty(1, content); } 
		else if (type.equals("ACCESSION"))	{ this.addProperty(2, content); }
		else if (type.equals("VERSION"))	{ this.addProperty(3, content); }
		else if (type.equals("KEYWORDS"))	{ this.addProperty(4, content); }
		else if (type.equals("SOURCE"))		{ this.addProperty(5, content); }
		else if (type.equals("ORGANISM"))	{ this.addProperty(6, content); }
		else if (type.equals("LINEAGE"))	{ this.addProperty(7, content); }
		else if (type.equals("COMMENT"))	{ this.addProperty(8, content); }
		else if (type.equals("BASE COUNT"))	{ this.addProperty(9, content); }
	}
	
	public void addReference(String numLocator, String authors,
							 String title, String journal,
							 String medlineID, String pubmedID) {
		this.references.addString(numLocator);
		this.references.addString(authors);
		this.references.addString(title);
		this.references.addString(journal);
		this.references.addString(medlineID);
		this.references.addString(pubmedID);
	}
	
	public void addFeature(String name, String part) {
		this.features.addGeneAnnotationFeature
			(new GeneAnnotationFeature(name,part));
		this.currentFeatureIndex++;
	}
	
	public void addFeatureData(String desc,String content) {
		if (this.currentFeatureIndex >= 0) {
			this.features.getGeneAnnotationFeature(this.currentFeatureIndex)
						.addStringCouple(new StringCouple(desc,content));
		}
	}

	public void setSequence(String name,String sequence) 
		{ this.sequence = new Sequence(name,sequence); }
	
	public Sequence getSequence()					{ return this.sequence; }
	public StringListe getProperties()				{ return this.properties; }
	public StringListe getReferences()				{ return this.references; }
	public GeneAnnotationFeatureListe getFeatures()	{ return this.features; }

	/** 
	 * String properties annotations of the Gene. 
	 * <p></p>
	 * <ul>
	 * <li>0  : LOCUS</li>
	 * <li>1  : DEFINITION</li>
	 * <li>2  : ACCESSION</li>
	 * <li>3  : VERSION</li>
	 * <li>4  : KEYWORDS</li>
	 * <li>5  : SOURCE</li>
	 * <li>6  : ORGANISM</li>
	 * <li>7  : (lineage)</li>
	 * <li>8  : COMMENT</li>
	 * <li>9  : BASE COUNT</li>
	 * </ul>
	 */
	public String getProperty(int prop) { 
		if ( (prop < 0) || (prop >= GeneAnnotation.PROPERTIES_NUMBER) ) 
			{ return null; }
		return this.properties.getString(prop);
	}
	public String getLocus()		{ return this.properties.getString(0); }
	public String getDefinition()	{ return this.properties.getString(1); }
	public String getAccession()	{ return this.properties.getString(2); }
	public String getVersion()		{ return this.properties.getString(3); }
	public String getKeywords()		{ return this.properties.getString(4); }
	public String getSource()		{ return this.properties.getString(5); }
	public String getOrganism()		{ return this.properties.getString(6); }
	public String getLineage() 		{ return this.properties.getString(7); }
	public String getComment()		{ return this.properties.getString(8); }
	public String getBaseCount()	{ return this.properties.getString(9); }
	
	public String toString() {
		String result = new String("");
		
		// result += "Properties : \n";
		for (int i = 0 ; i < GeneAnnotation.PROPERTIES_NUMBER ; i++) {
			result += (i == 0)?"LOCUS\t":"";
			result += (i == 1)?"DEFINITION\t":"";
			result += (i == 2)?"ACCESSION\t":"";
			result += (i == 3)?"VERSION\t":"";
			result += (i == 4)?"KEYWORDS\t":"";
			result += (i == 5)?"SOURCE\t":"";
			result += (i == 6)?"ORGANISM\t":"";
			result += (i == 7)?"":"";
			result += (i == 8)?"COMMENT\t":"";
			result += (i == 9)?"BASE COUNT\t":"";
			result += this.properties.getString(i)+"\n";
		}
		// result += this.properties.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		// result += "References : \n";
		int REFnumber = this.references.length()/6;
		for (int i = 0 ; i < REFnumber ; i++) {
			String ref = this.references.getString(i*6+0);
			String[] t = ref.split("\t");
			result += "REFERENCE\t"+t[0]+"\tbase "+t[1]+" to "+t[2]+""+"\n";
			result += "AUTHORS\t"+this.references.getString(i*6+1)+"\n";
			result += "TITLE\t"+this.references.getString(i*6+2)+"\n";
			result += "JOURNAL\t"+this.references.getString(i*6+3)+"\n";
			result += "MEDLINE\t"+this.references.getString(i*6+4)+"\n";
			result += "PUBMED\t"+this.references.getString(i*6+5)+"\n";
		}
		// result += this.references.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		result += "Features : \n";
		result += this.features.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		result += "ORIGIN\t\t";
		String sequence = this.sequence.getSequence();
		StringListe par = new StringListe();
		int cuts = sequence.length()/10;
		for (int i = 0 ; i < cuts ; i++) {
			int beginIndex = i*10,endIndex = i*10+10;
			par.addString(sequence.substring(beginIndex, endIndex));
		}
		int counter = 1;
		for (int i = 0 ; i < par.length() ; i++) {
			if (i%6 == 0) {
				result += "\n\t"+((counter<10000)?" "+((counter<1000)?" "
								+((counter<100)?" "+((counter<10)?" "
								:""):""):""):"")+counter+" ";
			}
			result += par.getString(i)+" ";
			counter += 10;
		}
		result += "\n";
		// result += this.sequence.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		result += "//\n";
		
		return result;
	}
	
	protected void exec(Organism orga) throws GeneException { ; }
	public String reverseTranslation(boolean end) { return new String(""); }
	
}

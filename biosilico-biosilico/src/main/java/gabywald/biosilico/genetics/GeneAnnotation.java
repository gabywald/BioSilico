package gabywald.biosilico.genetics;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.utils.Sequence;
import gabywald.global.structures.StringCouple;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 */
public class GeneAnnotation {
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
	private List<String> properties;
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
	private List<String> references;
	/** List of features of annotated gene. */
	private List<GeneAnnotationFeature> features;
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
		this.properties = new ArrayList<String>();
		this.references = new ArrayList<String>();
		this.features	= new ArrayList<GeneAnnotationFeature>();
		this.sequence	= new Sequence();
		for (int i = 0 ; i <= GeneAnnotation.PROPERTIES_NUMBER ; i++) 
			{ this.properties.add(""); }
	}
	
	/**
	 * To know if current instance of GeneAnnotation is valid or not. 
	 * @return (boolean)
	 */
	public boolean isValid() {
		if (this.references.size() == 0)			{ return false; }
		if (this.features.size() == 0)				{ return false; }
		if (this.sequence.getSequence().equals(""))	{ return false; }
		return true;
	}
	
	public void addProperty(int i, String content) 
		{ this.properties.set(i,content); }
	
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
		this.references.add(numLocator);
		this.references.add(authors);
		this.references.add(title);
		this.references.add(journal);
		this.references.add(medlineID);
		this.references.add(pubmedID);
	}
	
	public void addFeature(String name, String part) {
		this.features.add(new GeneAnnotationFeature(name,part));
		this.currentFeatureIndex++;
	}
	
	public void addFeatureData(String desc,String content) {
		if (this.currentFeatureIndex >= 0) {
			this.features.get(this.currentFeatureIndex).add(new StringCouple(desc,content));
		}
	}

	public void setSequence(String name,String sequence) 
		{ this.sequence = new Sequence(name,sequence); }
	
	public Sequence getSequence()						{ return this.sequence; }
	public List<String> getProperties()					{ return this.properties; }
	public List<String> getReferences()					{ return this.references; }
	public List<GeneAnnotationFeature> getFeatures()	{ return this.features; }

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
		return this.properties.get(prop);
	}
	public String getLocus()		{ return this.properties.get(0); }
	public String getDefinition()	{ return this.properties.get(1); }
	public String getAccession()	{ return this.properties.get(2); }
	public String getVersion()		{ return this.properties.get(3); }
	public String getKeywords()		{ return this.properties.get(4); }
	public String getSource()		{ return this.properties.get(5); }
	public String getOrganism()		{ return this.properties.get(6); }
	public String getLineage() 		{ return this.properties.get(7); }
	public String getComment()		{ return this.properties.get(8); }
	public String getBaseCount()	{ return this.properties.get(9); }
	
	@Override
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
			result += this.properties.get(i)+"\n";
		}
		// result += this.properties.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		// result += "References : \n";
		int REFnumber = this.references.size()/6;
		for (int i = 0 ; i < REFnumber ; i++) {
			String ref = this.references.get(i*6+0);
			String[] t = ref.split("\t");
			result += "REFERENCE\t"+t[0]+"\tbase "+t[1]+" to "+t[2]+""+"\n";
			result += "AUTHORS\t"+this.references.get(i*6+1)+"\n";
			result += "TITLE\t"+this.references.get(i*6+2)+"\n";
			result += "JOURNAL\t"+this.references.get(i*6+3)+"\n";
			result += "MEDLINE\t"+this.references.get(i*6+4)+"\n";
			result += "PUBMED\t"+this.references.get(i*6+5)+"\n";
		}
		// result += this.references.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		result += "Features : \n";
		result += this.features.toString();
//		result += "\n"+StringListe.repeat("*", 80)+"\n";
		result += "ORIGIN\t\t";
		String sequence = this.sequence.getSequence();
		List<String> par = new ArrayList<String>();
		int cuts = sequence.length()/10;
		for (int i = 0 ; i < cuts ; i++) {
			int beginIndex = i*10,endIndex = i*10+10;
			par.add(sequence.substring(beginIndex, endIndex));
		}
		int counter = 1;
		for (int i = 0 ; i < par.size() ; i++) {
			if (i%6 == 0) {
				result += "\n\t"+((counter<10000)?" "+((counter<1000)?" "
								+((counter<100)?" "+((counter<10)?" "
								:""):""):""):"")+counter+" ";
			}
			result += par.get(i)+" ";
			counter += 10;
		}
		result += "\n";
		// result += this.sequence.toString();
		// result += "\n"+StringListe.repeat("*", 80)+"\n";
		result += "//\n";
		
		return result;
	}
	
}

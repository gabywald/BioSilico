package gabywald.biosilico.genetics;

import gabywald.global.structures.StringCouple;
import gabywald.global.structures.StringCoupleListe;

/**
 * This class defines objects for GeneAnnotation : features (pre-defined comments). 
 * <br>First couple of String's represents name (type) and part of sequence/gene concerned. 
 * @author Gabriel Chandesris (2010)
 */
public class GeneAnnotationFeature extends StringCoupleListe {
	/** 
	 * Main constructor with given name and location. 
	 * @param name (String)
	 * @param part (String)
	 */
	public GeneAnnotationFeature(String name,String part) 
		{ this.addStringCouple(new StringCouple(name,part)); }
	
	public String getName() { return this.getStringCouple(0).getValueA(); }
	public String getPart() { return this.getStringCouple(0).getValueB(); }
	
}
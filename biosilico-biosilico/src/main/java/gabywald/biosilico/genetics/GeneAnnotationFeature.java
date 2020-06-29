package gabywald.biosilico.genetics;

import java.util.ArrayList;
import java.util.List;

import gabywald.global.structures.StringCouple;

/**
 * This class defines objects for GeneAnnotation : features (pre-defined comments). 
 * <br>First couple of String's represents name (type) and part of sequence/gene concerned. 
 * @author Gabriel Chandesris (2010, 2020)
 */
public class GeneAnnotationFeature {
	
	private List<StringCouple> list = new ArrayList<StringCouple>();
	
	/** 
	 * Main constructor with given name and location. 
	 * @param name (String)
	 * @param part (String)
	 */
	public GeneAnnotationFeature(String name, String part) 
		{ this.list.add(new StringCouple(name,part)); }
	
	public String getName() { return this.list.get(0).getValueA(); }
	public String getPart() { return this.list.get(0).getValueB(); }

	public void add(StringCouple stringCouple) {
		this.list.add(stringCouple);
	}
	
}
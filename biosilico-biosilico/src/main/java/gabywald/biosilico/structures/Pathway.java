package gabywald.biosilico.structures;

import gabywald.biosilico.genetics.Gene;

/**
 * This class describes a group of Gene united by a global aim (a Pathway). 
 * @author Gabriel Chandesris (2010, 2020)
 * @see GeneMoreListe
 */
public class Pathway {
	/** Name of the pathway. */
	private String name;
	/** The list of described genes. */
	private GeneMoreListe genesStock;
	
	/**
	 * Constructor with given name. 
	 * @param name (String)
	 */
	public Pathway(String name) { 
		this.name		= name;
		this.genesStock	= new GeneMoreListe();
	}
	
	/**
	 * To get the name of the pathway. 
	 * @return (String)
	 */
	public String getName() { return this.name; }
	
	public GeneMoreListe getGeneMoreListe() { 
		return this.genesStock; 
	} 
	
	/** Number of gene's in current pathway. */
	public int length() 
		{ return this.genesStock.length(); }
	
	public String getGeneName(int i) 
		{ return this.genesStock.getGeneName(i); }
	
	public Gene getGene(int i) 
		{ return this.genesStock.getGene(i); }
	
	/**
	 * To add a Gene with given type. 
	 * @param gene (Gene)
	 */
	public void addGene(Gene gene) {
		this.addGene(gene, -1);
	}
	
	/**
	 * To add a Gene with name and type. 
	 * @param gene (Gene)
	 * @param type (int)
	 * @see GeneMoreListe#addGene(Gene, int)
	 */
	public void addGene(Gene gene, int type) 
		{ this.genesStock.addGene(gene, type); }
	
}

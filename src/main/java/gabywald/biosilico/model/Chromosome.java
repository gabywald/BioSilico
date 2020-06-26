package gabywald.biosilico.model;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.structures.GeneListe;

/**
 * This class in order to instanciate chromosomes for organisms.<br>
 * An organism can have several chromosomes. 
 * @author Gabriel Chandesris (2009)
 */
public class Chromosome {
	/** The list of genes. */
	private GeneListe genes;

	/** Default constructor. */
	public Chromosome() { this.genes = new GeneListe(); }
	
	/** Constructor with a given table of Gene's. */
	public Chromosome(Gene[] genes) { this.genes = new GeneListe(genes); }
	
	/** Constructor with a given list of Gene's. */
	public Chromosome(GeneListe genes) { this.genes = genes; }
	
	public void addGene(Gene more) 
		{ this.genes.addGene(more); }
	
	/**
	 * This method execute gene by gene.
	 * @param orga (Organism) Current organism.  
	 */
	public void execution(Organism orga) {
		for (int i = 0 ; i < this.genes.length() ; i++) 
			{ this.genes.getGene(i).execution(orga); }
	}
	
	public int length() 		{ return this.genes.length(); }
	public int getGeneNumber()	{ return this.genes.length(); }
	public Gene getGene(int i) 
		{ return (i < this.genes.length())?((i < 0)?null
							:this.genes.getGene(i)):null; }
	
	
}

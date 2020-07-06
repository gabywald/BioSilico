package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.biosilico.genetics.Gene;

/**
 * This class in order to instanciate chromosomes for organisms.<br>
 * An organism can have several chromosomes. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Chromosome {
	/** The list of genes. */
	private List<Gene> genes;

	/** Default constructor. */
	public Chromosome() 
		{ this.genes = new ArrayList<Gene>(); }
	
	/** Constructor with a given table of Gene's. */
	public Chromosome(Gene[] genes) { 
		this();
		this.genes.addAll(Arrays.asList(genes));
	}
	
	/** Constructor with a given list of Gene's. */
	public Chromosome(List<Gene> genes) 
		{ this.genes = genes; }
	
	public void addGene(Gene more) 
		{ this.genes.add(more); }
	
	/**
	 * This method execute gene by gene.
	 * @param orga (Organism) Current organism.  
	 */
	public void execution(Organism orga) {
		this.genes.stream().forEach( g -> g.execution(orga) );
	}
	
	public int length() 		{ return this.genes.size(); }
	
	public int getGeneNumber()	{ return this.length(); }
	
	public Gene getGene(int i) 
		{ return (i < this.genes.size())?((i < 0)?null
							:this.genes.get(i)):null; }
	
}

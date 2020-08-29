package gabywald.biosilico.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import gabywald.biosilico.genetics.Gene;

/**
 * This class in order to instanciate chromosomes for organisms.<br>
 * An organism can have several chromosomes. 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Chromosome {
	/** Default name if not attributed. */
	public static final String DEFAULT_CHROMOSOME_NAME = "UnNamedChromosome";
	
	/** If apply. */
	private String name;
	/** The list of genes. */
	private List<Gene> genes;

	/** Default constructor. */
	public Chromosome() {
		this.name = Chromosome.DEFAULT_CHROMOSOME_NAME;
		this.genes = new ArrayList<Gene>();
	}
	
	/** Constructor with a given table of Gene's. */
	public Chromosome(Gene[] genes) { 
		this();
		this.genes.addAll(Arrays.asList(genes));
	}
	
	/** Constructor with a given List of Gene's. */
	public Chromosome(List<Gene> genes) { 
		this();
		this.genes.addAll(genes);
	}
	
	public void addGene(Gene more) { 
		this.genes.add(more);
	}
	
	/**
	 * This method execute gene by gene.
	 * @param orga (Organism) Current organism.  
	 */
	public void execution(Organism orga) {
		this.genes.stream().forEach( g -> g.execution(orga) );
	}
	
	public int length() 		{ return this.genes.size(); }
	
	// public int getGeneNumber()	{ return this.genes.size(); }
	
	public Gene getGene(int i) 
		{ return (i < this.genes.size()) ? ((i < 0) ? null : this.genes.get(i)) : null; }
	
	/** 
	 * Make a Stream from Collection of Genes. 
	 * @return Stream of Gene. 
	 */
	public Stream<Gene> streamGene() {
		return this.genes.stream();
	}

	public String getName()				{ return this.name; }

	public void setName(String name)	{ this.name = name; }
	
}

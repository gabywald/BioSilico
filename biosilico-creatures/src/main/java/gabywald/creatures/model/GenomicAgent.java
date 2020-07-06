package gabywald.creatures.model;

import gabywald.creatures.genetics.CreatureGene;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a specific kind of Agent, which have a genome, and possesses Organ's. 
 * @author Gabriel Chandesris (2013)
 * @see Organ
 */
public abstract class GenomicAgent extends Agent {
	private List<CreatureGene> genome;
	private List<Organ> organs;
	
	protected GenomicAgent() { 
		super();
		this.genome = new ArrayList<CreatureGene>();
		this.organs = new ArrayList<Organ>();
	}
	
	public GenomicAgent(int numberOfVariables, int numberOfGenes, int numberOfOrgans) { 
		super(numberOfVariables);
		this.genome = new ArrayList<CreatureGene>();
		this.organs = new ArrayList<Organ>();
	}
	
	public int lengthOfGenome() 
		{ return this.genome.size(); }
	
	public CreatureGene getGene(int i) {
		if ( (i < 0) || (i >= this.genome.size())) 
			{ return null; }
		return this.genome.get(i);
	}
	
	protected void addGene(CreatureGene cg)	{ this.genome.add(cg); }
	
	public int numberOfOrgans() 
		{ return this.organs.size(); }
	
	public Organ getOrgan(int i) {
		if ( (i < 0) || (i >= this.organs.size())) 
			{ return null; }
		return this.organs.get(i);
	}
	
	protected void addOrgan(Organ or)		{ this.organs.add(or); }
	
	/** Agent of type genomicAgent consumes their variables via half-life rules and 'execute' their genes. */
	public void run() {
		/** DONE internal use of some variable (consumption / half-life). */
		for (int i = 0 ; i < this.lengthOfVariables() ; i++) 
			{ this.getVariable(i).consumption(); }
		/** DONE [on each organ]. */
		for (int i = 0 ; i < this.numberOfOrgans() ; i++) 
			{ this.getOrgan(i).run(); }
		/** TODO execute the genome on organs / variables... */
	}
	
}

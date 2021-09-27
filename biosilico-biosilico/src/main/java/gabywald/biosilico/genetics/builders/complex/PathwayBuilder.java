package gabywald.biosilico.genetics.builders.complex;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.builders.Pair;

/**
 * Complex builder for Pathways. 
 * @author Gabriel Chandesris (2021)
 */
public class PathwayBuilder {
	
	// XXX NOTE 20210921 : aim of this class is to build several gene at the same time 
	// // // - BiochemicalReaction (for base metabolic pathway)
	// // // - TO CHECK EmitterReceptor (Input / Output) if needed in definition
	// // // - TO CHECK StimulusDecision (Input / Output) if needed in definition
	// TO BE DONE SEPARATELY : 
	// // // - InitialConcentration (if any need)
	// // // - Brain / BrainLobe / Instinct (if any need) : another specific complex builder for that purpose
	// // // - "Complete Genome Builder" by type of Organism (cf . gabywald.biosilico.model.enums.AgentType)
	
	// How It work (or should do), definition to review (!?)
	// // // - output : a list of Gene's instances (BR / ER? / SD?)
	// // // - input : a set of chemicals (or indexes) of starts, intermediates and destinations with ratio / coefficients
	// // // - ... 
	
	private List<Pair<Integer, Integer> > coefAndChemicals = new ArrayList<Pair<Integer, Integer> >();
	private List<BiochemicalReaction> outputGenes = new ArrayList<BiochemicalReaction>();
	
	public PathwayBuilder() {
		// Nothing here (see above initialization). 
	}
	
	public void add(int coef, int chemical) {
		this.coefAndChemicals.add(new Pair<Integer, Integer>(coef, chemical));
	}
	
	public PathwayBuilder addparams(int coef, int chemical) {
		this.add(coef, chemical);
		return this;
	}
	
	public List<BiochemicalReaction> build() {
		// NOTE 20210924 : 
		// // // - building serie(s) of BR Genes (specific algorithm to set !)
		// // // - (0, 0) to separate input chemicals from output chemicals of the pathway) ??
		// // // - (0, 0) to separate different steps of pathway !!
		// // // - different step to be indicated ?!
		// // // - need of EmitterReceptor here ? (no connection on brain ?!)
		// // // - need of StimulusDecision here ? (further actions ?!)
		return this.outputGenes;
	}
	
}

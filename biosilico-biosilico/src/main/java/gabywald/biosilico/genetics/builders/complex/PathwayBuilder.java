package gabywald.biosilico.genetics.builders.complex;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;

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
	
	private List<Tuple> coefAndChemicals = new ArrayList<Tuple>();
	private List<BiochemicalReaction> outputGenes = new ArrayList<BiochemicalReaction>();
	
	public PathwayBuilder() {
		// Nothing here (see above initialization). 
	}
	
	public void add(int coefA, int chemicalA, int coefB, int chemicalB, 
					int coefC, int chemicalC, int coefD, int chemicalD, 
					int kmvm) {
		this.coefAndChemicals.add(Tuple.build(	9, coefA, chemicalA, coefB, chemicalB, 
												coefC, chemicalC, coefD, chemicalD, kmvm));
	}
	
	public PathwayBuilder addparams(int coefA, int chemicalA, int coefB, int chemicalB, 
									int coefC, int chemicalC, int coefD, int chemicalD, 
									int kmvm) {
		this.add(coefA, chemicalA, coefB, chemicalB, 
				 coefC, chemicalC, coefD, chemicalD, 
				 kmvm);
		return this;
	}
	
	public List<BiochemicalReaction> build() {
		// NOTE 20210924 : 
		// // // - building serie(s) of BR Genes (specific algorithm to set !)
		// // // - different step to be indicated ?!
		// // // - need of EmitterReceptor here ? (no connection on brain ?!)
		// // // - need of StimulusDecision here ? (further actions ?!)
		
		BiochemicalReactionBuilder brb = new BiochemicalReactionBuilder();
		Assertions.assertNotNull( brb );
		for (Tuple t : this.coefAndChemicals) {
			BiochemicalReaction brGene = brb.achem( t.getElements()[0] ).acoef( t.getElements()[1] )
											.bchem( t.getElements()[2] ).bcoef( t.getElements()[3] )
											.cchem( t.getElements()[4] ).ccoef( t.getElements()[5] )
											.dchem( t.getElements()[6] ).dcoef( t.getElements()[7] )
											.kmvm(  t.getElements()[8] ).build();
			this.outputGenes.add( brGene );
		}
		
		return this.outputGenes;
	}
	
}

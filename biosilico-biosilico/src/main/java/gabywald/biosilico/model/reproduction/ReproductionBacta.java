package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.model.Organism;

/**
 * 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2020)
 */
public class ReproductionBacta implements IReproduction {
	
	private static ReproductionBacta instance = null;
	
	private ReproductionBacta() { ; }
	
	public static ReproductionBacta getInstance() {
		if (ReproductionBacta.instance == null) 
			{ ReproductionBacta.instance = new ReproductionBacta(); }
		return ReproductionBacta.instance;
	}
	
	@Override
	public void action(Organism... organisms) {
		if (organisms.length == 0) { return; }

		// ***** at least one
		
		// TODO reproduction ReproductionBacta
		
	}

}

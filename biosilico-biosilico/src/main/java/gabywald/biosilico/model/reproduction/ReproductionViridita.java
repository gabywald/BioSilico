package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.model.Organism;

/**
 * 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2020)
 */
public class ReproductionViridita implements IReproduction {

	private static ReproductionViridita instance = null;
	
	private ReproductionViridita() { ; }
	
	public static ReproductionViridita getInstance() {
		if (ReproductionViridita.instance == null) 
			{ ReproductionViridita.instance = new ReproductionViridita(); }
		return ReproductionViridita.instance;
	}
	
	@Override
	public void action(Organism... organisms) {
		if (organisms.length == 0) { return; }

		// ***** at least one
		// TODO reproduction ReproductionAnima
		
	}

}

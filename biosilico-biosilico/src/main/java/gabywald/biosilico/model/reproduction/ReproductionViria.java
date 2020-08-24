package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.model.Organism;

/**
 * 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2020)
 */
public class ReproductionViria implements IReproduction {

	private static ReproductionViria instance = null;
	
	private ReproductionViria() { ; }
	
	public static ReproductionViria getInstance() {
		if (ReproductionViria.instance == null) 
			{ ReproductionViria.instance = new ReproductionViria(); }
		return ReproductionViria.instance;
	}
	
	@Override
	public void action(Organism... organisms) {
		if (organisms.length == 0) { return; }

		// ***** at least two ?? (virion and infected organism ?!)
		// TODO reproduction ReproductionViria
		
	}

}

package gabywald.biosilico.model.reproduction;

import gabywald.biosilico.exceptions.ReproductionException;
import gabywald.biosilico.model.Organism;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

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

		// ***** at least one : using gamets from the first one given
		try {
			organisms[0].addAgent( ReproductionHelper.unaryReproduction( organisms[0] ) );
			ReproductionHelper.actualizeReproduction( organisms[0] );
		} catch (ReproductionException e) {
			// e.printStackTrace();
			// TODO ReproductionException treatment !! ignore ??
			Logger.printlnLog(LoggerLevel.LL_ERROR, e.getMessage());
		}
	}

}

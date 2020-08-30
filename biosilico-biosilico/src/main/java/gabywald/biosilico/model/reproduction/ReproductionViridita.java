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
		try {
			if (organisms.length == 1) {
				// ***** Put / drop it in current WorldCase When Laying Egg !
				organisms[0].addAgent( ReproductionHelper.unaryReproduction(organisms[0]) );
				
			} else if (organisms.length == 2) {
				// ***** Put / drop it in current WorldCase When Laying Egg !
				organisms[0].addAgent( ReproductionHelper.binaryReproduction(organisms[0], organisms[1]) );
				ReproductionHelper.actualizeReproduction( organisms[1] );
			} else {
				throw new ReproductionException( "" );
			}
			
			ReproductionHelper.actualizeReproduction( organisms[0] );

		} catch (ReproductionException e) {
			// e.printStackTrace();
			// TODO ReproductionException treatment !! ignore ??
			Logger.printlnLog(LoggerLevel.LL_ERROR, e.getMessage());
		}
		
	}

}

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
public class ReproductionAnima implements IReproduction {
	
	private static ReproductionAnima instance = null;
	
	private ReproductionAnima() { ; }
	
	public static ReproductionAnima getInstance() {
		if (ReproductionAnima.instance == null) 
			{ ReproductionAnima.instance = new ReproductionAnima(); }
		return ReproductionAnima.instance;
	}
	
	@Override
	public void action(Organism... organisms) {
		if (organisms.length == 0)	{ return; }
		if (organisms.length < 2)	{ return; }
		
		// ***** at least two : using gamets from both !
		
		try {
			organisms[0].addAgent( ReproductionHelper.binaryReproduction(organisms[0], organisms[1]) );
			ReproductionHelper.actualizeReproduction( organisms[0] );
			ReproductionHelper.actualizeReproduction( organisms[1] );
		} catch (ReproductionException e) {
			// e.printStackTrace();
			// TODO ReproductionException treatment !! ignore ??
			Logger.printlnLog(LoggerLevel.LL_ERROR, e.getMessage());
		}
	}

}

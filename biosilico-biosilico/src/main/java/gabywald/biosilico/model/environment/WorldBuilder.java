package gabywald.biosilico.model.environment;

import gabywald.biosilico.interfaces.IEnvironment;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class WorldBuilder {

	private WorldBuilder() { ; }
	
	public static IEnvironment getWorld2Ddefault() {
		return new World2D(World2D.MAX_HEIGHT, World2D.MAX_WIDTH);
	}
}

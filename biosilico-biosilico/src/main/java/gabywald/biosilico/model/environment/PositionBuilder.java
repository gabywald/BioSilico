package gabywald.biosilico.model.environment;

import gabywald.biosilico.interfaces.IPosition;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class PositionBuilder {

	private PositionBuilder() { ; }
	
	public static IPosition buildPosition(int posx, int posy) {
		return PositionBuilder.buildPosition(posx, posy, false);
	}
	
	public static IPosition buildPosition(int posx, int posy, boolean is3D) {
		return ( (is3D) ? new Position3D(posx, posy, 0) : new Position2D(posx, posy) );
	}
	
	public static IPosition buildPosition(int posx, int posy, int posz) {
		return new Position3D(posx, posy, posz);
	}
	
}

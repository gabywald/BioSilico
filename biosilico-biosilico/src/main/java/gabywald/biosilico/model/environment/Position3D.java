package gabywald.biosilico.model.environment;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IPosition;

/**
 * Position / Location of an item in a map (Neuron in Brain for example). 
 * @author Gabriel Chandesris (2020)
 */
public class Position3D implements IPosition {
	/** Height position in map. */ 
	private int posX;
	/** Width position in map. */
	private int posY;
	/** Depth position in map. */
	private int posZ;
	
	/** Best constructor for a position. */
	Position3D(int posx, int posy, int posz) {
		this.posX = posx;
		this.posY = posy;
		this.posZ = posz;
	}
	
	@Override
	public double euclidianDistanceFrom(IPosition pos) {
		// TODO euclidan distance for 3D positions !
		return Math.sqrt(
				Math.pow((this.posX - this.posY), 2) + 
				Math.pow(( pos.getPosY() -  pos.getPosY()), 2) );
	}
	
	/**
	 * To get a clone of the object (identical properties). 
	 * @return (Position)
	 */
	@Override
	public Position3D clone() 
		{ return new Position3D(this.posX, this.posY, this.posZ); }
	
	@Override
	public int getPosX()			{ return this.posX; }
	@Override
	public int getPosY()			{ return this.posY; }
	@Override
	public int getPosZ() 			{ return this.posZ; }
	
	@Override
	public void setPosX(int posX)	{ this.posX = posX; }
	@Override
	public void setPosY(int posY)	{ this.posY = posY; }
	@Override
	public void setPosZ(int posZ)	{ this.posZ = posZ; }
	
	@Override
	public boolean equals(Object toCompare) {
		if ( ! (toCompare instanceof Position3D) ) { return false; } 
		return this.equals((Position3D) toCompare);
	}
	
	@Override
	public boolean equals(IPosition toCompare) {
		if (this.posX != toCompare.getPosX())	{ return false; }
		if (this.posY != toCompare.getPosY())	{ return false; }
		if (this.posZ != toCompare.getPosZ())	{ return false; }
		return true;
	}
	
	@Override
	public String toString() {
		return "[" + Gene.convert0to99(this.posX) + ", " + Gene.convert0to99(this.posY) +", " + Gene.convert0to99(this.posZ) + "]";
	}

}

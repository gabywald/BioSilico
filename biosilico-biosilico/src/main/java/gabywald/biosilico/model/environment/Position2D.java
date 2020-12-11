package gabywald.biosilico.model.environment;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IPosition;

/**
 * Position / Location of an item in a map (Neuron in Brain for example). 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Position2D implements IPosition {
	/** Height position in map. */ 
	private int posX;
	/** Width position in map. */
	private int posY;
	
	/** Best constructor for a position. */
	Position2D(int posx, int posy) {
		this.posX = posx;
		this.posY = posy;
	}
	
	@Override
	public double euclidianDistanceFrom(IPosition pos) {
		return Math.sqrt(
				Math.pow((this.posX - this.posY), 2) + 
				Math.pow(( pos.getPosY() -  pos.getPosY()), 2) );
	}
	
	/**
	 * To get a clone of the object (identical properties). 
	 * @return (Position)
	 */
	@Override
	public Position2D clone() 
		{ return new Position2D(this.posX, this.posY); }
	
	@Override
	public int getPosX()			{ return this.posX; }
	@Override
	public int getPosY()			{ return this.posY; }
	@Override
	public int getPosZ() 			{ return 0; }
	
	@Override
	public void setPosX(int posX)	{ this.posX = posX; }
	@Override
	public void setPosY(int posY)	{ this.posY = posY; }
	@Override
	public void setPosZ(int posZ)	{ ; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)						{ return false; }
		if (obj.getClass() != this.getClass())	{ return false; }
		return this.equals((Position2D) obj);
	}
	
	@Override
	public boolean equals(IPosition toCompare) {
		if (this.posX != toCompare.getPosX())		{ return false; }
		if (this.posY != toCompare.getPosY())		{ return false; }
		if (this.getPosZ() != toCompare.getPosZ())	{ return false; }
		return true;
	}
	
	@Override
	public String toString() {
		return "[" + Gene.convert0to99(this.posX) + ", " + Gene.convert0to99(this.posY) + "]";
	}

}

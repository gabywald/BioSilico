package gabywald.biosilico.model;

/**
 * Position / Location of an item in a map (Neuron in Brain for example). 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class Position {
	/** Height position in map. */ 
	private int posX;
	/** Width position in map. */
	private int posY;
	
	/** Best constructor for a position. */
	public Position(int posx, int posy) {
		this.posX = posx;
		this.posY = posy;
	}
	
	/**
	 * Classical computing of euclidian distance between two positions. 
	 * <i>Between current position and a given position. </i>
	 * @param pos (Position) A given position. 
	 * @return Euclidian distance. 
	 */
	public double euclidianDistanceFrom(Position pos) {
		return Math.sqrt(
				Math.pow((this.posX - this.posY), 2) + 
				Math.pow(( pos.posY -  pos.posY), 2));
	}
	
	/**
	 * To get a clone of the object (identical properties). 
	 * @return (Position)
	 */
	public Position getCopy() 
		{ return new Position(this.posX, this.posY); }
	
	public int getPosX()			{ return this.posX; }
	public int getPosY()			{ return this.posY; }

	public void setPosX(int posX)	{ this.posX = posX; }
	public void setPosY(int posY)	{ this.posY = posY; }
	
	public boolean equals(Position toCompare) {
		if (this.posX != toCompare.posX) { return false; }
		if (this.posY != toCompare.posY) { return false; }
		return true;
	}
}

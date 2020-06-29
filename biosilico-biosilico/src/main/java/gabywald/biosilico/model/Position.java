package gabywald.biosilico.model;

/**
 * Position / Location of an item in a map (Neuron in Brain for example). 
 * @author Gabriel Chandesris (2009)
 */
public class Position {
	/** Height position in map. */ 
	private int pos_x;
	/** Width position in map. */
	private int pos_y;
	
	/** Best constructor for a position. */
	public Position(int posx,int posy){
		this.pos_x = posx;
		this.pos_y = posy;
	}
	
	/**
	 * Classical computing of euclidian distance between two positions. 
	 * <i>Between current position and a given position. </i>
	 * @param pos (Position) A given position. 
	 * @return Euclidian distance. 
	 */
	public double euclidianDistanceFrom(Position pos) {
		return Math.sqrt((this.pos_x-pos.getPosX())
								+(this.pos_y-pos.getPosY()));
	}
	
	/**
	 * To get a clone of the object (identical properties). 
	 * @return (Position)
	 */
	public Position getCopy() 
		{ return new Position(this.pos_x,this.pos_y); }
	
	public int getPosX() { return this.pos_x; }
	public int getPosY() { return this.pos_y; }

	public void setPosX(int pos_x) { this.pos_x = pos_x; }
	public void setPosY(int pos_y) { this.pos_y = pos_y; }
	
	public boolean equals(Position toCompare) {
		if (this.pos_x != toCompare.getPosX()) { return false; }
		if (this.pos_y != toCompare.getPosY()) { return false; }
		return true;
	}
}

package gabywald.biosilico.interfaces;

/**
 * Position / Location of an item in a map (Neuron in Brain for example). 
 * @author Gabriel Chandesris (2020)
 */
public interface IPosition extends Cloneable {

	public int getPosX();
	public int getPosY();
	public int getPosZ();
	
	public void setPosX(int posX);
	public void setPosY(int posY);
	public void setPosZ(int posZ);
	
	public boolean equals(IPosition toCompare);
	
	public IPosition clone();
	
	/**
	 * Classical computing of euclidian distance between two positions. 
	 * <i>Between current position and a given position. </i>
	 * @param pos (Position) A given position. 
	 * @return Euclidian distance. 
	 */
	public double euclidianDistanceFrom(IPosition position);
	
}

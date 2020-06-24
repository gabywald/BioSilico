package gabywald.creatures.model;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class Variable {
	private UnsignedByte value;
	private UnsignedByte halfl;
	
	public Variable(int halfLife) {
		this.value	= new UnsignedByte(0);
		this.halfl	= new UnsignedByte(halfLife);
	}
	
	public void setValue(int value)	{ this.value = new UnsignedByte(value); }
	
	public int getValue()		{ return this.value.getValue(); }
	public int getHalfLife()	{ return this.halfl.getValue(); }
	
	/** TODO law of Half-life ? */
	public void consumption() { ; }
}

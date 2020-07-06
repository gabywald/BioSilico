package gabywald.creatures.model;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public class UnsignedByte {
	private byte value;
	
	public UnsignedByte(int bValue) {
		if (bValue < 0)			{ this.value = Byte.MIN_VALUE; }	/** -128 */
		else if (bValue >= 255)	{ this.value = Byte.MAX_VALUE; }	/** +127 */
		else { this.value = (byte)(bValue + Byte.MIN_VALUE ); }
	}
	
	public int getValue() 
		{ return this.value - Byte.MIN_VALUE; }
	
	public short getShort() 
		{ return this.value; }
	
	public String toString() 
		{ return "" + this.getValue(); }
}

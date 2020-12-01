package gabywald.creatures.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2013, 2020)
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)						{ return false; }

		if (obj.getClass() != this.getClass())	{ return false; }
		
		final UnsignedByte other = (UnsignedByte) obj;
		if (other.value != this.value)			{ return false; }
		
		return true;
	}
	
	/* ***** ***** ***** ***** ***** */
	
	public static UnsignedByte[] headerCutterBytes(String header) {
		UnsignedByte[] headerBytes = new UnsignedByte[header.length()];
		String[] headerCutted = UnsignedByte.headerCutter(header);
		for (int i = 0 ; i < headerCutted.length ; i++) 
			{ headerBytes[i] = new UnsignedByte( headerCutted[i].charAt(0) ); }
		return headerBytes;
	}
	
	public static String[] headerCutter(String header) {
		return UnsignedByte.splitToNChar(header, 1);
	}
	
	/**
	 * Split text into n number of characters.
	 * @param text the text to be split.
	 * @param size the split size.
	 * @return an array of the split text.
	 */
	public static String[] splitToNChar(String text, int size) {
		List<String> parts = new ArrayList<String>();
		int length = text.length();
		for (int i = 0 ; i < length ; i += size) {
			parts.add(text.substring(i, Math.min(length, i + size)));
		}
		return parts.toArray(new String[0]);
	}
}

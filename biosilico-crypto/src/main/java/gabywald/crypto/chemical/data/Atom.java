package gabywald.crypto.chemical.data;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * This class defines Atom's representation. 
 * @author Gabriel Chandesris (2011)
 * @see <a href="https://secure.wikimedia.org/wikipedia/en/wiki/Chemical_table_file">Chemical Table File (Wikipedia EN)</a>
 * @see Molecule
 */
public class Atom {
	private double posX,posY,posZ;
	private char[] symbol;
	/** Extra Info (Here length of 12). */
	private int[] extraInfo;
	
	public Atom(char[] symb) {
		this(symb, 0, 0, 0);
	}
	
	public Atom(char[] symb, double xPos, double yPos, double zPos) {
		this.symbol		= symb;
		this.posX		= xPos;
		this.posY		= yPos;
		this.posZ		= zPos;
		this.extraInfo	= new int[12];
	}

	public double getPosX()					{ return this.posX; }
	public void setPosX(double posX)		{ this.posX = posX; }
	public double getPosY()					{ return this.posY; }
	public void setPosY(double posY)		{ this.posY = posY; }
	public double getPosZ()					{ return this.posZ; }
	public void setPosZ(double posZ)		{ this.posZ = posZ; }

	public String getSymbolAsString()		{ return new String(this.symbol); }
	public char[] getSymbol()				{ return this.symbol; }
	public void setSymbol(char[] symbol)	{ this.symbol = symbol; }

	public int[] getExtraInfo()					{ return this.extraInfo; }
	public void setExtraInfo(int[] extraInfo)	{ this.extraInfo = extraInfo; }
	public int getExtraInfo(int i)				{ 
		if ( (i >= 0) && (i < this.extraInfo.length) ) 
			{ return this.extraInfo[i]; }
		return 0; /** Default return value. */
	}
	public void setExtraInfo(int i, int val)	{ 
		if ( (i >= 0) && (i < this.extraInfo.length) ) 
			{ this.extraInfo[i] = val; }
	}
	
	public String toString() {
		String result = new String("");
		DecimalFormatSymbols dsf = new DecimalFormatSymbols();
		dsf.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(4);
		df.setDecimalFormatSymbols(dsf);
		String xPos = df.format(this.posX);
		String yPos = df.format(this.posY);
		String zPos = df.format(this.posZ);
		result += " "+((xPos.length() <= 8)?" "+((xPos.length() <= 7)?" "+((xPos.length() <= 6)?" ":""):""):"")+xPos;
		result += " "+((yPos.length() <= 8)?" "+((yPos.length() <= 7)?" "+((yPos.length() <= 6)?" ":""):""):"")+yPos;
		result += " "+((zPos.length() <= 8)?" "+((zPos.length() <= 7)?" "+((zPos.length() <= 6)?" ":""):""):"")+zPos;
		result += " "+(new String(this.symbol))+((this.symbol.length == 1)?" ":"");
		for (int i = 0 ; i < this.extraInfo.length ; i++) 
			{ result += ((this.extraInfo[i] < 100)?" "+
							((this.extraInfo[i] < 10)?" ":""):"")
								+this.extraInfo[i]; }
		return result;
	}
	
	public boolean equals(Atom toCompare) {
		if (this.posX != toCompare.getPosX()) { return false; }
		if (this.posY != toCompare.getPosY()) { return false; }
		if (this.posZ != toCompare.getPosZ()) { return false; }
		if (this.symbol.length != toCompare.getSymbol().length)
			{ return false; }
		for (int i = 0 ; i < this.symbol.length ; i++) 
			{ if (this.symbol[i] != toCompare.getSymbol()[i])
				{ return false; } }
		if (this.extraInfo.length != toCompare.getExtraInfo().length)
			{ return false; }
		for (int i = 0 ; i < this.extraInfo.length ; i++) 
			{ if (this.extraInfo[i] != toCompare.getExtraInfo(i))
				{ return false; } }
		return true;
	}
	
	public Atom clone() {
		char[] symbols = new char[this.symbol.length];
		for (int i = 0 ; i < this.symbol.length ; i++) 
			{ symbols[i] = this.symbol[i]; }
		Atom toReturn = new Atom(symbols, this.posX, this.posY, this.posZ);
		int[] infos		= new int[this.extraInfo.length];
		for (int i = 0 ; i < this.extraInfo.length ; i++) 
			{ infos[i] = this.extraInfo[i]; }
		toReturn.setExtraInfo(infos);
		return toReturn;
	}
	
}

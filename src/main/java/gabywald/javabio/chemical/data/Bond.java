package gabywald.javabio.chemical.data;

/**
 * This class defines Bond's representation. 
 * @author Gabriel Chandesris (2011)
 * @see <a href="https://secure.wikimedia.org/wikipedia/en/wiki/Chemical_table_file">Chemical Table File (Wikipedia EN)</a>
 * @see Molecule
 */
public class Bond {
	/** Informations about bonding (link between Atom's), which Atom's (Molecule context) and type. */
	private int firstAtom,secondAtom,type;
	/** Could include others (here length of 4). */
	private int[] extraInfo;
	
	/**
	 * Constructor with given main informations. 
	 * @param first (int) Index of first Atom's (Molecule context). 
	 * @param second (int) Index of second Atom's (Molecule context). 
	 * @param type (int) Type of bond (simple, double...). 
	 */
	public Bond(int first, int second, int type) {
		this.type		= type;
		this.firstAtom	= first;
		this.secondAtom	= second;
		this.extraInfo	= new int[4];
	}
	
	public int getType()						{ return this.type; }
	
	public int getFirstAtom()					{ return this.firstAtom; }
	public void setFirstAtom(int firstAtom)		{ this.firstAtom = firstAtom; }
	public int getSecondAtom()					{ return this.secondAtom; }
	public void setSecondAtom(int secondAtom)	{ this.secondAtom = secondAtom; }

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
		result += ((this.firstAtom < 100)?" "+((this.firstAtom < 10)?" ":""):"")+this.firstAtom;
		result += ((this.secondAtom < 100)?" "+((this.secondAtom < 10)?" ":""):"")+this.secondAtom;
		result += ((this.type < 100)?" "+((this.type < 10)?" ":""):"")+this.type;
		for (int i = 0 ; i < this.extraInfo.length ; i++) 
			{ result += ((this.extraInfo[i] < 100)?" "+
							((this.extraInfo[i] < 10)?" ":""):"")
								+this.extraInfo[i]; }
		return result;
	}
	
	public boolean equals(Bond toCompare) {
		if (this.firstAtom != toCompare.getFirstAtom())		{ return false; }
		if (this.secondAtom != toCompare.getSecondAtom())	{ return false; }
		if (this.type != toCompare.getType())				{ return false; }
		if (this.extraInfo.length != toCompare.getExtraInfo().length) 
			{ return false; }
		for (int i = 0 ; i < this.extraInfo.length ; i++) 
			{ if (this.extraInfo[i] != toCompare.getExtraInfo(i))
				{ return false; } }
		return true;
	}
	
	public Bond clone() {
		Bond toReturn	= new Bond(this.firstAtom, this.secondAtom, this.type);
		int[] infos		= new int[this.extraInfo.length];
		for (int i = 0 ; i < this.extraInfo.length ; i++) 
			{ infos[i] = this.extraInfo[i]; }
		toReturn.setExtraInfo(infos);
		return toReturn;
	}
}

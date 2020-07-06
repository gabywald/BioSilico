package gabywald.crypto.chemical.data;

import java.util.Hashtable;

/**
 * This class defines Molecules' representation. 
 * @author Gabriel Chandesris (2011)
 * @see <a href="https://secure.wikimedia.org/wikipedia/en/wiki/Chemical_table_file">Chemical Table File (Wikipedia EN)</a>
 * @see Atom
 * @see Bond
 * @see Molecule#hasError()
 */
public class Molecule {
	private String name,comment;
	/** 0 is number of atoms ; 1 is number of bonds ; +8 : total size of 11. */
	private int[] generalInformation; 
	/** Additionnal data provided by SDF : 
	 * MELTING.POINT ; DESCRIPTION ; ALTERNATE.NAMES ; 
	 * DATE ; CRC.NUMBER ; DENSITY ; BOILING.POINT ; 
	 * Unique_ID ; ClogP ; Vendor ; Molecular Weight
	 */
	private Hashtable<String,String> dataSDF;
	/** Set of Bond's inside this Molecule. */
	private Bond[] bonds;
	/** Set of Atom's inside this Molecule. */
	private Atom[] atoms;
	
	/**
	 * Constructor with given name, comment and general information table. 
	 * <br>Use {@linkplain Molecule#hasError()} after setting tables of Atom's and Bond's to check validity !!
	 * @param name (String)
	 * @param comment (String)
	 * @param general (int[]) size of 11. 
	 * @see Molecule#setAtoms(Atom[])
	 * @see Molecule#setBonds(Bond[])
	 */
	public Molecule(String name, String comment, int[] general) {
		this.name					= name;
		this.comment				= comment;
		this.generalInformation		= general;
		this.dataSDF				= new Hashtable<String,String>();
		this.atoms					= new Atom[this.generalInformation[0]];
		this.bonds					= new Bond[this.generalInformation[1]];
		
		if (!this.dataSDF.containsKey("visible")) 
			{ this.setVisible(true); }
	}
	
	public boolean isVisible() { 
		if (this.getDataValue("visible") == null) { this.setVisible(true); }
		// System.out.println("\t'"+this.name+"'\t'"+this.getDataValue("visible")+"'");
		return Boolean.parseBoolean(this.getDataValue("visible"));
	}
	
	public void setVisible(boolean b) 
		{ this.setDataValue("visible", b+""); }
	
	public String getName()				{ return this.name; }
	public void setName(String name)	{ this.name = name; }
	public String getComment()			{ return this.comment; }
	public void setComment(String cc)	{ this.comment = cc; }
	
	public Atom getAtom(int i)	{
		if ( (i >= 0) && (i < this.atoms.length) ) 
			{ return this.atoms[i]; }
		return null; /** Default return value. */
	}
	public void setAtom(int i,Atom at)	{
		if ( (i >= 0) && (i < this.atoms.length) ) 
			{ this.atoms[i] = at; }
	}
	public Atom[] getAtoms()			{ return this.atoms; }
	public void setAtoms(Atom[] atoms)	{ 
		this.atoms					= atoms;
		this.generalInformation[0]	= this.atoms.length;
	}
	
	public void addAtom(Atom atom) {
		int atomsLength				= this.generalInformation[0];
		Atom[] nextAtoms			= new Atom[atomsLength+1];
		for (int i = 0 ; i < atomsLength ; i++) 
			{ nextAtoms[i] = this.atoms[i]; }
		nextAtoms[atomsLength]		= atom;
		this.atoms					= nextAtoms;
		this.generalInformation[0]	= this.atoms.length;
	}
	
	public void remAtom(int i) {
		if ( (i >= 0) && (i < this.atoms.length) ) {
			Atom[] nextAtoms			= new Atom[this.atoms.length-1];
			for (int j = 0 ; j < i ; j++) 
				{ nextAtoms[j] = this.atoms[j]; }
			for (int j = i+1 ; j < this.atoms.length ; j++) 
				{ nextAtoms[j-1] = this.atoms[j]; }
			this.atoms					= nextAtoms;
			this.generalInformation[0]	= this.atoms.length;
		}
	}
	
	public Bond getBond(int i)	{
		if ( (i >= 0) && (i < this.bonds.length) ) 
			{ return this.bonds[i]; }
		return null; /** Default return value. */
	}
	public void setBond(int i, Bond bo)	{
		if ( (i >= 0) && (i < this.bonds.length) ) 
			{ this.bonds[i] = bo; }
	}
	public Bond[] getBonds()			{ return this.bonds; }
	public void setBonds(Bond[] bonds)	{ 
		this.bonds					= bonds;
		this.generalInformation[1]	= this.bonds.length;
	}
	
	public void addBond(Bond bond) {
		int bondsLength				= this.generalInformation[1];
		Bond[] nextBonds			= new Bond[bondsLength+1];
		for (int i = 0 ; i < bondsLength ; i++) 
			{ nextBonds[i] = this.bonds[i]; }
		nextBonds[bondsLength]		= bond;
		this.bonds					= nextBonds;
		this.generalInformation[1]	= this.bonds.length;
	}
	
	public void remBond(int i) {
		if ( (i >= 0) && (i < this.bonds.length) ) {
			Bond[] nextBonds			= new Bond[this.bonds.length-1];
			for (int j = 0 ; j < i ; j++) 
				{ nextBonds[j] = this.bonds[j]; }
			for (int j = i+1 ; j < this.bonds.length ; j++) 
				{ nextBonds[j-1] = this.bonds[j]; }
			this.bonds					= nextBonds;
			this.generalInformation[1]	= this.bonds.length;
		}
	}
	
//	public int[] getGeneralInfo()					{ return this.generalInformation; }
//	public void setGeneralInfo(int[] generalInfo)	{ this.generalInformation = generalInfo; }
//	public int getGeneralInfo(int i)				{ 
//		if ( (i >= 0) && (i < this.generalInformation.length) ) 
//			{ return this.generalInformation[i]; }
//		return 0; /** Default return value. */
//	}
//	public void setGeneralInfo(int i, int val)		{ 
//		if ( (i >= 0) && (i < this.generalInformation.length) ) 
//			{ this.generalInformation[i] = val; }
//	}

	public String[] getDataKeys() 
		{ return this.dataSDF.keySet().toArray
			(new String[this.dataSDF.keySet().size()]); }
	
	public String getDataValue(String key) 					{
		/** System.out.println("\t'"+key+"'\t'"+this.dataSDF.get(key)+"'"); */
		return this.dataSDF.get(key);
	}
	public void setDataValue(String key, String val)		{ this.dataSDF.put(key, val); }
	public void setDataValues(Hashtable<String, String> sdf){ this.dataSDF = sdf; }
	public void remDataValue(String key)					{ this.dataSDF.remove(key); }
	
	/**
	 * This method indicates if current instance has any problem (true and out System message if any). 
	 * @return (boolean) Is true if has errors, else false. 
	 */
	public boolean hasError() {
		for (int i = 0 ; i < this.atoms.length ; i++) {
			if (this.atoms[i] == null) {
				System.out.println("Atom "+i+" is null !!");
				return true;
			}
		}
		for (int i = 0 ; i < this.bonds.length ; i++) {
			if (this.bonds[i] == null) {
				System.out.println("Bond "+i+" is null !!");
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String result = new String("");
		result += this.name+"\n"+this.comment+"\n\n";
		for (int i = 0 ; i < this.generalInformation.length ; i++) 
			{ result += ((this.generalInformation[i] < 100)?" "+
							((this.generalInformation[i] < 10)?" ":""):"")
								+this.generalInformation[i]; }
		result += " V2000\n";
		for (int i = 0 ; i < this.atoms.length ; i++) 
			{ result += this.atoms[i].toString()+"\n"; }
		for (int i = 0 ; i < this.bonds.length ; i++) 
			{ result += this.bonds[i].toString()+"\n"; }
		result += "M  END\n";
		String[]keys = this.dataSDF.keySet().toArray(new String[0]);
		
		for (int i = 0 ; i < keys.length ; i++) {
			result += ">  <"+keys[i]+">\n";
			result += this.dataSDF.get(keys[i])+"\n\n";
		}
		result += "$$$$";
		return result+"\n";
	}
	
	public boolean equals(Molecule toCompare) {
		if (!this.name.equals(toCompare.getName()))			{ return false; }
		// System.out.println("Same name = '"+this.name+"'");
		if (!this.comment.equals(toCompare.getComment()))	{ return false; }
		// System.out.println("Same comment = '"+this.comment+"'");
		if (this.getAtoms().length != toCompare.getAtoms().length)
			{ return false; }
		// System.out.println("Same number of atoms = '"+this.getAtoms().length+"'");
		if (this.getBonds().length != toCompare.getBonds().length)
			{ return false; }
		// System.out.println("Same number of bonds = '"+this.getBonds().length+"'");
		for (int i = 0 ; i < this.atoms.length ; i++) 
			{ if (!this.atoms[i].equals(toCompare.getAtom(i)))
				{ return false; } }
		// System.out.println("Same atoms !!");
		for (int i = 0 ; i < this.bonds.length ; i++) 
			{ if (!this.bonds[i].equals(toCompare.getBond(i)))
				{ return false; } }
		// System.out.println("Same bonds !!");
		/** TODO comparison of annotations ?? */
		return true;
	}
	
	public Molecule clone() {
		/** Simple type(s) could be copied like that in Java...
		 * Not the same for object or arrays (references). */
		int[] general = new int[this.generalInformation.length];
		for (int i = 0 ; i < general.length ; i++) 
			{ general[i] = this.generalInformation[i]; }
		Molecule toReturn  = new Molecule(this.name.substring(0), 
										  this.comment.substring(0), 
										  general);
		for (int i = 0 ; i < this.atoms.length ; i++) 
			{ toReturn.setAtom(i, this.atoms[i].clone()); }
		for (int i = 0 ; i < this.bonds.length ; i++) 
			{ toReturn.setBond(i, this.bonds[i].clone()); }
		
		String[] keys = this.getDataKeys();
		for (int i = 0 ; i < keys.length ; i++) 
			{ toReturn.setDataValue(keys[i], 
					this.getDataValue(keys[i])); }
		
		return toReturn;
	}
}

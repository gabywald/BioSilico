package gabywald.creatures.genetics;

import java.util.ArrayList;
import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2013)
 */
public abstract class CreatureGene {
	/**
	 * Creatures 1 : 
		00 - Brain Genes	
				00 - Lobe
		01 - Biochemistry Genes	
		"	00 - Receptor
		"	01 - Emitter
		"	02 - Reaction
		"	03 - Half-Lives
		"	04 - Initial Concentration
		02 - Creature Genes	
		"	00 - Stimulus
		"	01 - Genus
		"	02 - Appearance
		"	03 - Pose
		"	04 - Gait
		"	05 - Instinct
		"	06 - Pigment
	 * Creatures 2 : 
		0	0	Brain lobe
		0	1	Brain organ
		1	0	Receptor
		1	1	Emitter
		1	2	Chemical reaction
		1	3	Half lives
		1	4	Initial concentration
		2	0	Stimulus
		2	1	Genus
		2	2	Appearance
		2	3	Pose
		2	4	Gait
		2	5	Instinct
		2	6	Pigment
		2	7	Pigment bleed
		3	0	Organ
	 */
	private UnsignedByte type, subtype;
	/** A number from 0 to 255 indicating the order within this gene type/subtype for this particular gene. */
	private UnsignedByte sequenceNumber;
	/** Is the gene a duplicate as a result of genetic mutation. A number from 0 to 255. */
	private UnsignedByte duplicateNumber;
	/** A number indicating the life stage when this gene becomes active.
			0 = Embryo
			1 = Child
			2 = Adolescent
			3 = Youth
			4 = Adult
			5 = Old
			6 = Senile */
	private UnsignedByte switchStage;
	/** A bitflag indicating particular characteristics of this gene. These bitflags may be combined in any manner to produce a number from 0 to 255.

     bit  description
     ===  ===========
     0    Mutable
     1    Duplicatable
     2    Deletable (Cut)
     3    Male
     4    Female
     5    Dormant (the gene has no effect if this is set)

	 Note that if both bits 3 and 4 are zero then the gender for the gene is 'both'. 

This byte contains three types of information:

    Sex dependence - whether the gene is active in females, in males, in both.
    Mutability - whether the gene can be duplicated, mutated, deleted, or some combination.
    Dormancy - whether the gene is active in the creature. 

Six of eight bits are used as follows:

    Bit # 7 6 5 4 3 2 1 0
     00 - 0 0 0 0 0 0 0 0
     01 - 0 0 0 0 0 0 0 1
     03 - 0 0 0 0 0 0 1 1
     07 - 0 0 0 0 0 1 1 1
     0F - 0 0 0 0 1 1 1 1
     11 - 0 0 0 1 0 0 0 1
     13 - 0 0 0 1 0 0 1 1
     17 - 0 0 0 1 0 1 1 1
     27 - 0 0 1 0 0 1 1 1
     2F - 0 0 1 0 1 1 1 1
     37 - 0 0 1 1 0 1 1 1 	

    Bit 0 - mutability
    Bit 1 - duplication
    Bit 2 - deletion
    Bit 3 - male-specific
    Bit 4 - female-specific
    Bit 5 - dormancy

In summary:

   01  =  Mutable
   03  =  Mutable  Duplicable
   07  =  Mutable  Duplicable  Deletable
   0F  =  Mutable  Duplicable  Deletable  Male
   11  =  Mutable                         Female
   13  =  Mutable  Duplicable             Female
   17  =  Mutable  Duplicable  Deletable  Female
   27  =  Mutable  Duplicable  Deletable          Dormant
   2F  =  Mutable  Duplicable  Deletable  Male    Dormant
   37  =  Mutable  Duplicable  Deletable  Female  Dormant

	 */
	private UnsignedByte flags;
	/** Chance of mutation. A value ranging from 0 to 255. */
	private UnsignedByte mutationRate;
	/** Generic data container for main Gene's. */
	protected List<UnsignedByte> data;
	/** For execution of this gene : indicates if already done or not. 
	 * @see CreatureGene#run() */
	protected boolean hasAlreadyRun;
	
	/**
	 * 
	 * @param type (UnsignedByte)
	 * @param subtype (UnsignedByte)
	 */
	protected CreatureGene(UnsignedByte type, UnsignedByte subtype)
		{ this.init(type, subtype, new ArrayList<UnsignedByte>(0)); }
	
	/**
	 * 
	 * @param type (int)
	 * @param subtype (int)
	 */
	protected CreatureGene(int type, int subtype)
		{ this.init(new UnsignedByte(type), new UnsignedByte(subtype), new ArrayList<UnsignedByte>(0)); }
	
	/**
	 * 
	 * @param type (int)
	 * @param subtype (int)
	 * @param datas (List&lt;UnsignedByte&gt;)
	 * @param expectedSize (int)
	 * @throws CreatureGeneException if size of <i>datas</i> is not correct. 
	 */
	protected CreatureGene(	int type, int subtype, 
							List<UnsignedByte> datas, int expectedSize) 
			throws CreatureGeneException {
		if (datas.size() != expectedSize) 
			{ throw new CreatureGeneException(type, subtype, "Bad number of datas e:" + expectedSize + " [" + datas.size() + "]"); }
		this.init(new UnsignedByte(type), new UnsignedByte(subtype), datas);
	}
	
	private void init(UnsignedByte type, UnsignedByte subtype, List<UnsignedByte> datas)
		{ this.type = type;this.subtype = subtype;this.data = datas;this.hasAlreadyRun = false; }
	
	/**
	 * Insert a String into data. 
	 * <br>Example : mother and father into GenusGene. 
	 * @param parent (String)
	 * @see CreatureGene#data
	 * @see GenusGene#GenusGene(int, String, String)
	 * @see CreatureGene#stringifyFromData(int, int)
	 */
	protected void addToData(String parent, int length) {
		for (int i = 0 ; i < length ; i++) {
			char tmpChar = 0;
			if (i < parent.length()) 
				{ tmpChar = parent.charAt(i); }
			this.data.add(new UnsignedByte((int)tmpChar));
		} /** END "for (int i = 0 ; i < 4 ; i++)" */
	}
	
	/**
	 * Transform a part of content of data into a String
	 * <br>Example : mother and father of GenusGene. 
	 * @param start (int)
	 * @param length (int)
	 * @return (String)
	 * @see CreatureGene#data
	 * @see GenusGene#getMotherID()
	 * @see GenusGene#getFatherID()
	 */
	protected String stringifyFromData(int start, int length) {
		String toReturn = new String("");
		for (int i = start ; i < (start+length) ; i++) {
			char tmpChar = 0;
			if (i < this.data.size()) 
				{ tmpChar = (char)this.data.get(i).getValue(); }
			toReturn += tmpChar;
		} /** END "for (int i = start ; i < (start+length) ; i++)" */
		return toReturn; 
	}
	
	/** TODO CreatureGene as Runnable (and need an Agent / GenomicAgent to execute on). */
	// public abstract void run();

	public UnsignedByte getType()		{ return this.type; }
	public UnsignedByte getSubtype()	{ return this.subtype; }
	
	public UnsignedByte getSequenceNumber()		{ return this.sequenceNumber; }
	public UnsignedByte getDuplicateNumber()	{ return this.duplicateNumber; }
	public UnsignedByte getSwitchStage()		{ return this.switchStage; }
	public UnsignedByte getMutationRate()		{ return this.mutationRate; }

	public void setSequenceNumber(UnsignedByte sequenceNumber)		
		{ this.sequenceNumber	= sequenceNumber; }
	public void setDuplicateNumber(UnsignedByte duplicateNumber)	
		{ this.duplicateNumber	= duplicateNumber; }
	public void setSwitchStage(UnsignedByte switchStage)			
		{ this.switchStage		= switchStage; }
	public void setMutationRate(UnsignedByte mutRate)		
		{ this.mutationRate		= mutRate; }
	public void setFlags(UnsignedByte flags)			
		{ this.flags			= flags; }
	
	public void setSequenceNumber(int sequenceNumber)		
		{ this.sequenceNumber	= new UnsignedByte(sequenceNumber); }
	public void setDuplicateNumber(int duplicateNumber)	
		{ this.duplicateNumber	= new UnsignedByte(duplicateNumber); }
	public void setSwitchStage(int switchStage)			
		{ this.switchStage		= new UnsignedByte(switchStage); }
	public void setMutationRate(int mutRate)		
		{ this.mutationRate		= new UnsignedByte(mutRate); }
	public void setFlags(int flags)		
		{ this.flags			= new UnsignedByte(flags); }
	
	public boolean isMutable()		
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(8) == '1'; }
	public boolean isDuplicable()	
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(7) == '1'; }
	public boolean isDeletable()	
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(6) == '1'; }
	public boolean isForMale()		
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(5) == '1'; }
	public boolean isForFemale()			
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(4) == '1'; }
	public boolean isForBoth()		
		{ return (! this.isForMale()) && (! this.isForFemale()); }
	public boolean isActive()		
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(3) != '1'; }
	public boolean isDormant()		
		{ return CreatureGene.binaryCompleted(this.flags.getValue(), 8, '0').charAt(3) == '1'; }
	
	public static String binaryCompleted(int value, int size, char ch) {
		String toReturn = Integer.toBinaryString(value);
		for (int i = toReturn.length() ; i < size ; i++) 
			{ toReturn = ch + toReturn; }
		return toReturn;
	}
		
	public String toString() {
		String toReturn = new String("");
		
		toReturn += this.type + "\t" + this.subtype + "\t" + this.sequenceNumber + "\t";
		toReturn += this.duplicateNumber + "\t" + this.switchStage + "\t" + this.mutationRate + "\t" + this.flags;
		
		return toReturn;
	}
}

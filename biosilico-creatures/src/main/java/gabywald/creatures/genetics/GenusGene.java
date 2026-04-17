package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

/**
 * This gene specifies the type of creature, and the monikers of its parents. 
 * @author Gabriel Chandesris (2013, 2026)
 */
public class GenusGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param type (UnsignedByte)
	 * @param mother (String)
	 * @param father (String)
	 */
	public GenusGene(UnsignedByte type, String mother, String father) {
		super(2, 1);
		this.data.add(type);
		this.addToData(mother, 4); // Expected length is 4
		this.addToData(father, 4); // Expected length is 4
	}
	
	public int getTypeSpecie()	{ return this.data.get(0).getValue(); }
	public String getMotherID()	{ return this.stringifyFromData(1, 4); }
	public String getFatherID()	{ return this.stringifyFromData(5, 4); }
}

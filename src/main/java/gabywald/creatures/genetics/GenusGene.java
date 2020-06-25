package gabywald.creatures.genetics;

import java.util.List;

import gabywald.creatures.model.UnsignedByte;

/**
 * This gene specifies the type of creature, and the monikers of its parents. 
 * @author Gabriel Chandesris (2013)
 */
public class GenusGene extends CreatureGene {
	/**
	 * Old variant of the constructor. 
	 * @param type (int)
	 * @param mother (String)
	 * @param father (String)
	 * @deprecated Use {@link GenusGene#GenusGene(List)} instead !
	 */
	private GenusGene(int type, String mother, String father) {
		super(2, 1);
		this.data.add(new UnsignedByte(type));
		this.addToData(mother, 4); // Expected length is 4
		this.addToData(father, 4); // Expected length is 4
	}
	
	public GenusGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(2, 1, datas, 1 + 4 + 4); /* size expected is 9 ! */ }

	public int getTypeSpecie()	{ return this.data.get(0).getValue(); }
	public String getMotherID()	{ return this.stringifyFromData(1, 4); }
	public String getFatherID()	{ return this.stringifyFromData(5, 4); }
}

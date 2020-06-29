package gabywald.creatures.genetics;

import gabywald.creatures.model.UnsignedByte;

import java.util.List;

/**
 * The Brain organ (of which there should be only one in any genetics file) specifies the properties of the brain organ expressed in the creature. It has the same internal format as the other organs, but a different type identifier.
 * <br><i>C2-specific</i>
 * <br><i>C3-specific</i>   
 * @author Gabriel Chandesris (2013)
 *
 */
public class BrainOrganGene extends CreatureGene {

	public BrainOrganGene(List<UnsignedByte> datas) throws CreatureGeneException 
		{ super(0, 1, datas, 5); /* size expected is 5 ! */ }

	public int getClockRate()		{ return this.data.get( 0).getValue(); }
	public int getRepairRate()		{ return this.data.get( 1).getValue(); }
	public int getLifeForce()		{ return this.data.get( 2).getValue(); }
	public int getBioTickStart()	{ return this.data.get( 3).getValue(); }
	public int getATPDamageCoEff()	{ return this.data.get( 4).getValue(); }
}

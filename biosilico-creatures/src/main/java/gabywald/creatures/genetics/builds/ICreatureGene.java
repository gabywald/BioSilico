package gabywald.creatures.genetics.builds;

import gabywald.creatures.model.UnsignedByte;

/**
 * @author Gabriel Chandesris (2026)
 */
public interface ICreatureGene {

	public UnsignedByte getType();
	public UnsignedByte getSubtype();
	
	public UnsignedByte getSequenceNumber();
	public UnsignedByte getDuplicateNumber();
	public UnsignedByte getSwitchStage();
	public UnsignedByte getMutationRate();
	
	public void setSequenceNumber(UnsignedByte sequenceNumber);
	public void setDuplicateNumber(UnsignedByte duplicateNumber);
	public void setSwitchStage(UnsignedByte switchStage);
	public void setMutationRate(UnsignedByte mutRate);
	public void setFlags(UnsignedByte flags);
	
	public void setSequenceNumber(int sequenceNumber);
	public void setDuplicateNumber(int duplicateNumber);
	public void setSwitchStage(int switchStage);
	public void setMutationRate(int mutRate);
	public void setFlags(int flags);
	
	public boolean isMutable();
	public boolean isDuplicable();
	public boolean isDeletable();
	public boolean isForMale();
	public boolean isForFemale();
	public boolean isForBoth();	
	public boolean isActive();	
	public boolean isDormant();
	
}

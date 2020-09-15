package gabywald.biosilico.model.utils.agents;

import gabywald.biosilico.model.enums.SomeChemicals;

/**
 * This class to defines an 'energy source' in some WorldCase's instances. 
 * <br/>NOTE XXX TODO review basic level(s) of energy (and if it can be push / pull / moved ... 
 * @author Gabriel Chandesris (2020)
 */
public class EnergySource extends UtilAgent {
	
	public static final int BASIC_ENERGY_LEVEL			= 25;
	
	public static final String COMMON_BIOSILICO_NAME	= "Energy Source";
	
	/**
	 * Default Constructor (not alive, not movable, not eatable). 
	 */
	public EnergySource() {
		this.setSolar( true );
		this.setHeat( true );
		
		this.setName( EnergySource.COMMON_BIOSILICO_NAME );
	}
	
	public void setSolar(boolean isSolar) {
		this.variables.setVariable(SomeChemicals.ENERGY_SOLAR.getIndex(), isSolar ? EnergySource.BASIC_ENERGY_LEVEL : 0);
	}
	
	public void setHeat(boolean isHeat) {
		this.variables.setVariable(SomeChemicals.ENERGY_HEAT.getIndex(), isHeat ? EnergySource.BASIC_ENERGY_LEVEL : 0);
	}
	
	public boolean isSolar() 
		{ return (this.variables.getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()) > 0); }
	
	public boolean isHeat() 
		{ return (this.variables.getVariable(SomeChemicals.ENERGY_HEAT.getIndex()) > 0); }
	
}

package gabywald.biosilico.model;

import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * This class to defines an 'energy source' in some WorldCase's instances. 
 * <br/>NOTE XXX TODO review basic level(s) of energy (and if it can be push / pull / moved ... 
 * @author Gabriel Chandesris (2020)
 */
public class EnergySource extends Agent {
	
	public static final int INDEX_SOLAR			= 500; // SomeChemicals.ENERGY_SOLAR.getIndex();
	public static final int INDEX_HEAT			= 501; // SomeChemicals.ENEGRY_HEAT.getIndex();
	
	public static final int BASIC_ENERGY_LEVEL	= 25;
	
	public static final String COMMON_BIOSILICO_NAME	= "Energy Source";
	
	/**
	 * Default Constructor (not alive, not movable, not eatable). 
	 */
	public EnergySource() {
		super(false, false, false);
		this.setSolar( true );
		this.setHeat( true );
		
		// ***** TypeOf is defined on Agent (default is Daemon)
		// ***** Status is not accurate (no changes)
		this.variables.setVariable(StateType.STATUS.getIndex(), StatusType.NOT_ACCURATE.getIndex());
		
		String name = EnergySource.COMMON_BIOSILICO_NAME;
		this.setNameBiosilico( name );
		this.setNameCommon( name );
	}
	
	public void setSolar(boolean isSolar) {
		this.variables.setVariable(EnergySource.INDEX_SOLAR, isSolar ? EnergySource.BASIC_ENERGY_LEVEL : 0);
	}
	
	public void setHeat(boolean isHeat) {
		this.variables.setVariable(EnergySource.INDEX_HEAT, isHeat ? EnergySource.BASIC_ENERGY_LEVEL : 0);
	}
	
	public boolean isSolar() 
		{ return (this.variables.getVariable(EnergySource.INDEX_SOLAR) > 0); }
	
	public boolean isHeat() 
		{ return (this.variables.getVariable(EnergySource.INDEX_HEAT) > 0); }
	
	public static void addEnergyTo(WorldCase local, int index, int value) {
		if (local != null) {
			local.getVariables().setVarPlus(index, value);
		}
	}
	
	@Override
	public void execution(WorldCase local) {
		EnergySource.addEnergyTo(local, EnergySource.INDEX_SOLAR, this.variables.getVariable(EnergySource.INDEX_SOLAR));
		EnergySource.addEnergyTo(local, EnergySource.INDEX_HEAT,  this.variables.getVariable(EnergySource.INDEX_HEAT));
	}

	@Override
	public boolean deplace()	{ return true; }
	@Override
	public boolean push()		{ return true; }
	@Override
	public boolean pull()		{ return true; }
	@Override
	public boolean stop()		{ return true; }
	@Override
	public boolean slap()		{ return true; }
	@Override
	public boolean rest()		{ return true; }
	@Override
	public boolean sleep()		{ return true; }

}

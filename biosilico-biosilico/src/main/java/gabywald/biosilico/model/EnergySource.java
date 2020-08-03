package gabywald.biosilico.model;

/**
 * This class to defines an 'energy source' in some WorldCase's instances. 
 * <br/>NOTE XXX TODO review basic level(s) of energy (and if it can be push / pull / moved ... 
 * @author Gabriel Chandesris (2020)
 */
public class EnergySource extends Agent {
	
	public static final int INDEX_SOLAR	= 501;
	public static final int INDEX_HEAT	= 502;
	
	public static final int BASIC_ENERGY_LEVEL = 25;
	
	/**
	 * Default Constructor (not alive, not movable, not eatable). 
	 */
	public EnergySource() {
		super(false, false, false);
		this.setSolar( true );
		this.setHeat( true );
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
		EnergySource.addEnergyTo(local, EnergySource.INDEX_HEAT, this.variables.getVariable(EnergySource.INDEX_HEAT));
	}

	@Override
	public void deplace()	{ ; }
	@Override
	public void push()		{ ; }
	@Override
	public void pull()		{ ; }
	@Override
	public void stop()		{ ; }
	@Override
	public void slap()		{ ; }

}

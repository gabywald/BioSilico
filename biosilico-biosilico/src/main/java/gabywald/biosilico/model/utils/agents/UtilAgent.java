package gabywald.biosilico.model.utils.agents;

import java.util.stream.IntStream;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * This class defines some specific agents for simulation (NRJ, Chemicals sources...). 
 * <br/> To provide some Chemicals during tests (or for other purposes). 
 * @author Gabriel Chandesris (2020)
 */
public class UtilAgent extends Agent {
	
	/**
	 * Default Constructor (not alive, not movable, not eatable). 
	 */
	public UtilAgent() {
		super(false, false, false);
		
		// ***** TypeOf is defined on Agent (default is Daemon)
		// ***** Status is not accurate (no changes)
		this.variables.setVariable(StateType.STATUS.getIndex(), StatusType.NOT_ACCURATE.getIndex());
		this.variables.setVariable(StateType.TYPEOF.getIndex(), ObjectType.BIG_ELT.getIndex());
		
	}
	
	/**
	 * To add WC variables for given chemical and value. 
	 * @param local (WorldCase)
	 * @param index (int) index of Chemical. 
	 * @param value (int) value / amount of chemical. 
	 */
	public static void addChemicalTo(WorldCase local, int index, int value) {
		if (local != null) {
			local.getVariables().setVarPlus(index, value);
		}
	}
	
	@Override
	public void execution(WorldCase local) {
		if (local == null) { return; }
		IntStream.range(0, ChemicalsHelper.CHEMICAL_STRICT_CHEM).forEach( chIndex -> {
			UtilAgent.addChemicalTo(local, chIndex, this.getChemicals().getVariable(chIndex));
		});
	}
	
	/** To Set nams of utilitaries Agents. */
	protected void setName(String name) {
		this.setNameBiosilico( name );
		this.setNameCommon( name );
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

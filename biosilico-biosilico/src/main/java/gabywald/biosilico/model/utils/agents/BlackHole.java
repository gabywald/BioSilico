package gabywald.biosilico.model.utils.agents;

import java.util.stream.IntStream;

import gabywald.biosilico.interfaces.IEnvironmentItem;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.SomeChemicals;

/**
 * The BlackHole is useful only because halflives does not apply for chemicals. 
 * <br/>Aim of this class is to reduce energy levels above a certain level.  
 * @author Gabriel Chandesris (2022)
 */
public class BlackHole extends EnergySource {
	
	public static final String COMMON_BIOSILICO_NAME	= "BlackHole (Remove Energy)";
	
	/**
	 * Constructor
	 * @param threshold (int) Level to reduce values. 
	 */
	public BlackHole(int threshold) {
		this.setSolar( true );
		this.setHeat( true );
		this.getChemicals().setVariable(SomeChemicals.NRJ_THRESHOLD.getIndex(), threshold);
		
		this.setName( BlackHole.COMMON_BIOSILICO_NAME );
	}
	
	/**
	 * Default Constructor (not alive, not movable, not eatable). 
	 * <br/> If value is above ChemicalsHelper.CHEMICAL_STRICT_CHEM index. 
	 */
	public BlackHole() {
		this(ChemicalsHelper.CHEMICAL_STRICT_CHEM / 3);
	}
	
	@Override
	public void execution(IEnvironmentItem local) {
		if (local == null) { return; }
		IntStream.range(0, ChemicalsHelper.CHEMICAL_STRICT_CHEM).forEach( chIndex -> {
			if (local.getChemicals().getVariable(chIndex) > this.getChemicals().getVariable(SomeChemicals.NRJ_THRESHOLD.getIndex()))
				{ UtilAgent.removeChemicalTo(local, chIndex, this.getChemicals().getVariable(chIndex) * 3); }
		});
	}
	
}

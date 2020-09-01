package gabywald.biosilico.anthill.tests;

import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Test Purposes for decisions (Automation object to be pull / push, stop, slap...). 
 * @author Gabriel Chandesris (2020)
 */
public class TestObjectFoodEgg extends Organism {
	
	public TestObjectFoodEgg() {
		this.setAgentType(AgentType.BIOSILICO_VIRIDITA);
		this.setObjectType(ObjectType.FOOD);
		this.setOrganismStatus(StatusType.EGG);
	}

	@Override
	public boolean push()		{ 
		String msg = "TestObjectFoodEgg {" + this.getUniqueID() + "} PUSHed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.PUSH);
		return true; 
	}
	@Override
	public boolean pull()		{ 
		String msg = "TestObjectFoodEgg {" + this.getUniqueID() + "} PULLed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.PULL);
		return true; 
	}
	@Override
	public boolean stop()		{ 
		String msg = "TestObjectFoodEgg {" + this.getUniqueID() + "} STOPed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.STOP);
		return true; 
	}
	@Override
	public boolean slap()		{ 
		String msg = "TestObjectFoodEgg {" + this.getUniqueID() + "} SLAPed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.SLAP);
		return true; 
	}
	@Override
	public boolean rest()		{ 
		String msg = "TestObjectFoodEgg {" + this.getUniqueID() + "} RESTed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.REST);
		return true; 
	}
	@Override
	public boolean sleep()		{ 
		String msg = "TestObjectFoodEgg {" + this.getUniqueID() + "} SLEEPed !";
		Logger.printlnLog(LoggerLevel.LL_INFO, msg);
		this.addState(this.getUniqueID() + "::" + msg);
		IDecision.recordInChemical(this, DecisionType.RECORDSTATE, DecisionType.SLEEP);
		return true; 
	}

	@Override
	public void execution(WorldCase local) {
		// NO EXECUTION HERE !
	}

}

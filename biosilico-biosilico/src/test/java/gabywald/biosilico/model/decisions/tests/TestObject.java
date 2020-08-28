package gabywald.biosilico.model.decisions.tests;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * Test Purposes for decisions (Automation object to be pull / push, stop, slap...). 
 * @author Gabriel Chandesris (2020)
 */
public class TestObject extends Agent {
	
	public TestObject() {
		this.setObjectType(ObjectType.FOOD);
	}

	@Override
	public boolean pull() {
		Logger.printlnLog(LoggerLevel.LL_INFO, "TestObject PULLed !");
		return false;
	}

	@Override
	public boolean push() {
		Logger.printlnLog(LoggerLevel.LL_INFO, "TestObject PUSHed !");
		return false;
	}

	@Override
	public boolean slap() {
		Logger.printlnLog(LoggerLevel.LL_INFO, "TestObject SLAPped !");
		return false;
	}

	@Override
	public boolean stop() {
		Logger.printlnLog(LoggerLevel.LL_INFO, "TestObject STOPped !");
		return false;
	}

	@Override
	public void execution(WorldCase local) {
		// NO EXECUTION HERE !
	}

}

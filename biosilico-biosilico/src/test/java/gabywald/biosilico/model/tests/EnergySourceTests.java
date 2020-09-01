package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.EnergySource;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.enums.SomeChemicals;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class EnergySourceTests {

	@Test
	void testInstanciationAndExecution() {
		EnergySource es = new EnergySource();
		
		Assertions.assertNotNull( es );
		Assertions.assertTrue( es.isSolar() );
		Assertions.assertTrue( es.isHeat() );
		
		es.setHeat( false );
		Assertions.assertTrue( es.isSolar() );
		Assertions.assertFalse( es.isHeat() );
		
		es.setSolar( false );
		Assertions.assertFalse( es.isSolar() );
		Assertions.assertFalse( es.isHeat() );
		
		es.setHeat( true );
		Assertions.assertFalse( es.isSolar() );
		Assertions.assertTrue( es.isHeat() );
		
		es.setSolar( true );
		Assertions.assertTrue( es.isSolar() );
		Assertions.assertTrue( es.isHeat() );
		
		// ***** Test nothing happened !
		es.execution( null );
		
		Assertions.assertNotNull( es );
		Assertions.assertTrue( es.isSolar() );
		Assertions.assertTrue( es.isHeat() );
		
		// ***** Test of changes !
		WorldCase wc = new WorldCase();
		
		Assertions.assertNotNull( wc );
		Assertions.assertEquals(0, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(0, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		
		es.execution( wc );
		
		Assertions.assertNotNull( es );
		Assertions.assertTrue( es.isSolar() );
		Assertions.assertTrue( es.isHeat() );
		
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		
		es.setHeat( false );
		es.execution( wc );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL * 2, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		
		es.setSolar( false );
		es.execution( wc );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL * 2, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		
		es.setHeat( true );
		es.execution( wc );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL * 2, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL * 2, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );
		
		es.setSolar( true );
		es.execution( wc );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL * 3, wc.getVariables().getVariable( SomeChemicals.ENERGY_SOLAR.getIndex() ) );
		Assertions.assertEquals(EnergySource.BASIC_ENERGY_LEVEL * 3, wc.getVariables().getVariable( SomeChemicals.ENERGY_HEAT.getIndex() ) );

	}

}

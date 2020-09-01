package gabywald.biosilico.model.tests;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.EnergySource;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class WorldCaseTests {
	public static final int INDEX_SOLAR	= 390; // SomeChemicals.ENERGY_SOLAR.getIndex();
	public static final int INDEX_HEAT	= 391; // SomeChemicals.ENERGY_HEAT.getIndex();

	@Test
	void testWorldCase() {
		WorldCase wc = new WorldCase();
		
		Assertions.assertNotNull( wc );
		Assertions.assertNotNull( wc.getVariables() );
		Assertions.assertNotNull( wc.getAgentListe() );
		Assertions.assertEquals( 0, wc.getAgentListLength() );
		Assertions.assertEquals( 0, wc.getAgentListe().size() );
		Assertions.assertNull( wc.getWorld() );
		Assertions.assertEquals(-1, wc.getPosX());
		Assertions.assertEquals(-1, wc.getPosY());
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		Arrays.asList(AgentType.values()).forEach( k -> {
			Assertions.assertEquals( 0, wc.hasAgentType( k ) );
		});
		
		Arrays.asList(StatusType.values()).forEach( k -> {
			Assertions.assertEquals( 0, wc.hasAgentStatus( k ) );
		});
		
		Arrays.asList(ObjectType.values()).forEach( k -> {
			Assertions.assertEquals( 0, wc.hasObjectType( k ) );
		});
	}
	
	@Test
	void testWorldCaseInWorld() {
		World w = new World(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getWorld() );
				Assertions.assertEquals(i, currentWC.getPosX());
				Assertions.assertEquals(j, currentWC.getPosY());
				Assertions.assertNotNull( currentWC.getVariables() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				});
				
			});
		});
		
		WorldCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.CURRENT).getPosX());
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.CURRENT).getPosY());
		
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.NorthWest).getPosX());
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.NorthWest).getPosY());
		
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.North).getPosX());
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.North).getPosY());
		
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.NorthEast).getPosX());
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.NorthEast).getPosY());
		
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.East).getPosX());
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.East).getPosY());
		
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.SouthEast).getPosX());
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.SouthEast).getPosY());
		
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.South).getPosX());
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.South).getPosY());
		
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.SouthWest).getPosX());
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.SouthWest).getPosY());
		
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.West).getPosX());
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.West).getPosY());
		
	}
	
	@Test
	void testWorldCaseInWorldWithEnergyAgent() {
		World w = new World(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getWorld() );
				Assertions.assertEquals(i, currentWC.getPosX());
				Assertions.assertEquals(j, currentWC.getPosY());
				Assertions.assertNotNull( currentWC.getVariables() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				});
				
			});
		});
		
		WorldCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertFalse( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertFalse( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		middleWC.addAgent(new EnergySource());
		Assertions.assertTrue( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNotNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertTrue( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNotNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		
		Agent daemonAgent = middleWC.getAgentType( AgentType.BIOSILICO_DAEMON );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, daemonAgent.getBioSilicoName() );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, daemonAgent.getCommonName() );
		
		Agent notAccurateAgent = middleWC.getAgentStatus( StatusType.NOT_ACCURATE );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, notAccurateAgent.getBioSilicoName() );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, notAccurateAgent.getCommonName() );
		
		Assertions.assertNotNull( middleWC );
		Assertions.assertNotNull( middleWC.getWorld() );
		Assertions.assertEquals(1, middleWC.getPosX());
		Assertions.assertEquals(1, middleWC.getPosY());
		Assertions.assertNotNull( middleWC.getVariables() );
		Assertions.assertNotNull( middleWC.getAgentListe() );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getVariables().getVariable(k) );
		});
		
		Arrays.asList(AgentType.values()).forEach( k -> {
			switch(k) {
			case BIOSILICO_DAEMON :
				Assertions.assertEquals( 1, middleWC.hasAgentType( k ) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.hasAgentType( k ) );
			}
		});
		
		Arrays.asList(StatusType.values()).forEach( k -> {
			switch(k) {
			case NOT_ACCURATE :
				Assertions.assertEquals( 1, middleWC.hasAgentStatus( k ) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.hasAgentStatus( k ) );
			}
		});
		
		Arrays.asList(ObjectType.values()).forEach( k -> {
			switch(k) {
			case BIG_ELT :
				Assertions.assertEquals( 1, middleWC.hasObjectType( k ) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.hasObjectType( k ) );
			}
		});
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (WorldCaseTests.INDEX_HEAT) :
			case (WorldCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 25, middleWC.getVariables().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getVariables().getVariable(k) );
			}
		});
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (WorldCaseTests.INDEX_HEAT) :
			case (WorldCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 50, middleWC.getVariables().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getVariables().getVariable(k) );
			}
		});
		
		// XXX NOTE [20200804] : no half-lives applies yet 
		
		Agent removedEnergySource = middleWC.remAgent( 0 );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (WorldCaseTests.INDEX_HEAT) :
			case (WorldCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 50, middleWC.getVariables().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getVariables().getVariable(k) );
			}
		});
		
		middleWC.addAgent( removedEnergySource );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (WorldCaseTests.INDEX_HEAT) :
			case (WorldCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 75, middleWC.getVariables().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getVariables().getVariable(k) );
			}
		});
		
		Assertions.assertTrue( middleWC.remAgent( removedEnergySource ) );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (WorldCaseTests.INDEX_HEAT) :
			case (WorldCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 75, middleWC.getVariables().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getVariables().getVariable(k) );
			}
		});
		
	}
	
}

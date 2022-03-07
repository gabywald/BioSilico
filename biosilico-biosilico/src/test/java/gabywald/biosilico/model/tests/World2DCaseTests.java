package gabywald.biosilico.model.tests;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Agent;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DirectionWorld;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.Condensator;
import gabywald.biosilico.model.utils.agents.ConverterPlantEgg2Fruit;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class World2DCaseTests {
	public static final int INDEX_SOLAR	= 390; // SomeChemicals.ENERGY_SOLAR.getIndex();
	public static final int INDEX_HEAT	= 391; // SomeChemicals.ENERGY_HEAT.getIndex();
	
	public static final int INDEX_CARBONDIOXID	= 181;
	public static final int INDEX_WATER			= 182;

	@Test
	void testWorldCase() {
		World2DCase wc = new World2DCase();
		
		Assertions.assertNotNull( wc );
		Assertions.assertNotNull( wc.getChemicals() );
		Assertions.assertNotNull( wc.getAgentListe() );
		Assertions.assertEquals( 0, wc.getAgentListLength() );
		Assertions.assertEquals( 0, wc.getAgentListe().size() );
		Assertions.assertNull( wc.getEnvironment() );
		Assertions.assertEquals(-1, wc.getPosition().getPosX());
		Assertions.assertEquals(-1, wc.getPosition().getPosY());
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getChemicals().getVariable(k) );
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
		World2D w = new World2D(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getEnvironment() );
				Assertions.assertEquals(i, currentWC.getPosition().getPosX());
				Assertions.assertEquals(j, currentWC.getPosition().getPosY());
				Assertions.assertNotNull( currentWC.getChemicals() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				});
				
			});
		});
		
		World2DCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.CURRENT).getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.CURRENT).getPosition().getPosY());
		
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.NorthWest).getPosition().getPosX());
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.NorthWest).getPosition().getPosY());
		
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.North).getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.North).getPosition().getPosY());
		
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.NorthEast).getPosition().getPosX());
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.NorthEast).getPosition().getPosY());
		
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.East).getPosition().getPosX());
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.East).getPosition().getPosY());
		
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.SouthEast).getPosition().getPosX());
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.SouthEast).getPosition().getPosY());
		
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.South).getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.South).getPosition().getPosY());
		
		Assertions.assertEquals(2, middleWC.getDirection(DirectionWorld.SouthWest).getPosition().getPosX());
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.SouthWest).getPosition().getPosY());
		
		Assertions.assertEquals(1, middleWC.getDirection(DirectionWorld.West).getPosition().getPosX());
		Assertions.assertEquals(0, middleWC.getDirection(DirectionWorld.West).getPosition().getPosY());
		
	}
	
	@Test
	void testWorldCaseInWorldWithEnergyAgent() {
		World2D w = new World2D(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getEnvironment() );
				Assertions.assertEquals(i, currentWC.getPosition().getPosX());
				Assertions.assertEquals(j, currentWC.getPosition().getPosY());
				Assertions.assertNotNull( currentWC.getChemicals() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				});
				
			});
		});
		
		World2DCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertFalse( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertFalse( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		middleWC.addAgent(new EnergySource());
		Assertions.assertTrue( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNotNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertTrue( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNotNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		
		// List<Function<Void, Boolean> > 
		// List<Object> functions = Arrays.asList( IAgentDeplace::deplace, IAgentPull::pull );
		Assertions.assertTrue( (new EnergySource()).deplace() );
		Assertions.assertTrue( (new EnergySource()).pull() );
		Assertions.assertTrue( (new EnergySource()).push() );
		Assertions.assertTrue( (new EnergySource()).rest() );
		Assertions.assertTrue( (new EnergySource()).slap() );
		Assertions.assertTrue( (new EnergySource()).sleep() );
		Assertions.assertTrue( (new EnergySource()).stop() );
		
		Agent daemonAgent = middleWC.getAgentType( AgentType.BIOSILICO_DAEMON );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, daemonAgent.getBioSilicoName() );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, daemonAgent.getCommonName() );
		
		Agent notAccurateAgent = middleWC.getAgentStatus( StatusType.NOT_ACCURATE );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, notAccurateAgent.getBioSilicoName() );
		Assertions.assertEquals(EnergySource.COMMON_BIOSILICO_NAME, notAccurateAgent.getCommonName() );
		
		Assertions.assertNotNull( middleWC );
		Assertions.assertNotNull( middleWC.getEnvironment() );
		Assertions.assertEquals(1, middleWC.getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getPosition().getPosY());
		Assertions.assertNotNull( middleWC.getChemicals() );
		Assertions.assertNotNull( middleWC.getAgentListe() );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
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
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 25, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 50, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		// XXX NOTE [20200804] : no half-lives applies yet 
		
		Agent removedEnergySource = middleWC.remAgent( 0 );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 50, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		middleWC.addAgent( removedEnergySource );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 75, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		Assertions.assertTrue( middleWC.remAgent( removedEnergySource ) );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 75, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
	}
	
	@Test
	void testWorldCaseInWorldWithBlackHole() {
		// TODO NOTE : make better tests (and in other additive one) here because activation of BlackHole is only at "high level" obtain from an EnergySource. 
		World2D w = new World2D(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getEnvironment() );
				Assertions.assertEquals(i, currentWC.getPosition().getPosX());
				Assertions.assertEquals(j, currentWC.getPosition().getPosY());
				Assertions.assertNotNull( currentWC.getChemicals() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				});
				
			});
		});
		
		World2DCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertFalse( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertFalse( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		middleWC.addAgent(new BlackHole());
		Assertions.assertTrue( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNotNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertTrue( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNotNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		
		// List<Function<Void, Boolean> > 
		// List<Object> functions = Arrays.asList( IAgentDeplace::deplace, IAgentPull::pull );
		Assertions.assertTrue( (new EnergySource()).deplace() );
		Assertions.assertTrue( (new EnergySource()).pull() );
		Assertions.assertTrue( (new EnergySource()).push() );
		Assertions.assertTrue( (new EnergySource()).rest() );
		Assertions.assertTrue( (new EnergySource()).slap() );
		Assertions.assertTrue( (new EnergySource()).sleep() );
		Assertions.assertTrue( (new EnergySource()).stop() );
		
		Agent daemonAgent = middleWC.getAgentType( AgentType.BIOSILICO_DAEMON );
		Assertions.assertEquals(BlackHole.COMMON_BIOSILICO_NAME, daemonAgent.getBioSilicoName() );
		Assertions.assertEquals(BlackHole.COMMON_BIOSILICO_NAME, daemonAgent.getCommonName() );
		
		Agent notAccurateAgent = middleWC.getAgentStatus( StatusType.NOT_ACCURATE );
		Assertions.assertEquals(BlackHole.COMMON_BIOSILICO_NAME, notAccurateAgent.getBioSilicoName() );
		Assertions.assertEquals(BlackHole.COMMON_BIOSILICO_NAME, notAccurateAgent.getCommonName() );
		
		Assertions.assertNotNull( middleWC );
		Assertions.assertNotNull( middleWC.getEnvironment() );
		Assertions.assertEquals(1, middleWC.getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getPosition().getPosY());
		Assertions.assertNotNull( middleWC.getChemicals() );
		Assertions.assertNotNull( middleWC.getAgentListe() );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
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
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		// XXX NOTE [20200804] : no half-lives applies yet 
		
		Agent removedEnergySource = middleWC.remAgent( 0 );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		middleWC.addAgent( removedEnergySource );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		Assertions.assertTrue( middleWC.remAgent( removedEnergySource ) );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_HEAT) :
			case (World2DCaseTests.INDEX_SOLAR) :
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
	}
	
	@Test
	void testWorldCaseInWorldWithCondensator() {
		World2D w = new World2D(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getEnvironment() );
				Assertions.assertEquals(i, currentWC.getPosition().getPosX());
				Assertions.assertEquals(j, currentWC.getPosition().getPosY());
				Assertions.assertNotNull( currentWC.getChemicals() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				});
				
			});
		});
		
		World2DCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertFalse( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertFalse( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		middleWC.addAgent(new Condensator());
		Assertions.assertTrue( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNotNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertTrue( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNotNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		
		// List<Function<Void, Boolean> > 
		// List<Object> functions = Arrays.asList( IAgentDeplace::deplace, IAgentPull::pull );
		Assertions.assertTrue( (new EnergySource()).deplace() );
		Assertions.assertTrue( (new EnergySource()).pull() );
		Assertions.assertTrue( (new EnergySource()).push() );
		Assertions.assertTrue( (new EnergySource()).rest() );
		Assertions.assertTrue( (new EnergySource()).slap() );
		Assertions.assertTrue( (new EnergySource()).sleep() );
		Assertions.assertTrue( (new EnergySource()).stop() );
		
		Agent daemonAgent = middleWC.getAgentType( AgentType.BIOSILICO_DAEMON );
		Assertions.assertEquals(Condensator.COMMON_BIOSILICO_NAME, daemonAgent.getBioSilicoName() );
		Assertions.assertEquals(Condensator.COMMON_BIOSILICO_NAME, daemonAgent.getCommonName() );
		
		Agent notAccurateAgent = middleWC.getAgentStatus( StatusType.NOT_ACCURATE );
		Assertions.assertEquals(Condensator.COMMON_BIOSILICO_NAME, notAccurateAgent.getBioSilicoName() );
		Assertions.assertEquals(Condensator.COMMON_BIOSILICO_NAME, notAccurateAgent.getCommonName() );
		
		Assertions.assertNotNull( middleWC );
		Assertions.assertNotNull( middleWC.getEnvironment() );
		Assertions.assertEquals(1, middleWC.getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getPosition().getPosY());
		Assertions.assertNotNull( middleWC.getChemicals() );
		Assertions.assertNotNull( middleWC.getAgentListe() );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
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
			case (World2DCaseTests.INDEX_CARBONDIOXID) :
			case (World2DCaseTests.INDEX_WATER) :
				Assertions.assertEquals( 25, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_CARBONDIOXID) :
			case (World2DCaseTests.INDEX_WATER) :
				Assertions.assertEquals( 50, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		// XXX NOTE [20200804] : no half-lives applies yet 
		
		Agent removedEnergySource = middleWC.remAgent( 0 );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_CARBONDIOXID) :
			case (World2DCaseTests.INDEX_WATER) :
				Assertions.assertEquals( 50, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		middleWC.addAgent( removedEnergySource );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_CARBONDIOXID) :
			case (World2DCaseTests.INDEX_WATER) :
				Assertions.assertEquals( 75, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
		Assertions.assertTrue( middleWC.remAgent( removedEnergySource ) );
		Assertions.assertEquals( 0, middleWC.getAgentListLength() );
		Assertions.assertEquals( 0, middleWC.getAgentListe().size() );
		
		w.execution();
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			switch(k) {
			case (World2DCaseTests.INDEX_CARBONDIOXID) :
			case (World2DCaseTests.INDEX_WATER) :
				Assertions.assertEquals( 75, middleWC.getChemicals().getVariable(k) );
			break;
			default:
				Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
			}
		});
		
	}
	
	@Test
	void testWorldCaseInWorldWithPlantEggConverter2Fruit() {
		World2D w = new World2D(3, 3);
		
		Assertions.assertNotNull( w );
		
		IntStream.range(0, 3).forEach( i -> {
			IntStream.range(0, 3).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				Assertions.assertNotNull( currentWC.getEnvironment() );
				Assertions.assertEquals(i, currentWC.getPosition().getPosX());
				Assertions.assertEquals(j, currentWC.getPosition().getPosY());
				Assertions.assertNotNull( currentWC.getChemicals() );
				Assertions.assertNotNull( currentWC.getAgentListe() );
				Assertions.assertEquals( 0, currentWC.getAgentListLength() );
				Assertions.assertEquals( 0, currentWC.getAgentListe().size() );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				});
				
			});
		});
		
		World2DCase middleWC = w.getWorldCase(1, 1);
		
		Assertions.assertFalse( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertFalse( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		middleWC.addAgent(new ConverterPlantEgg2Fruit());
		Assertions.assertTrue( middleWC.hasAgentType( AgentType.BIOSILICO_DAEMON ) > 0);
		Assertions.assertNotNull( middleWC.getAgentType( AgentType.BIOSILICO_DAEMON ) );
		Assertions.assertTrue( middleWC.hasAgentStatus( StatusType.NOT_ACCURATE ) > 0);
		Assertions.assertNotNull( middleWC.getAgentStatus( StatusType.NOT_ACCURATE ) );
		
		// List<Function<Void, Boolean> > 
		// List<Object> functions = Arrays.asList( IAgentDeplace::deplace, IAgentPull::pull );
		Assertions.assertTrue( (new EnergySource()).deplace() );
		Assertions.assertTrue( (new EnergySource()).pull() );
		Assertions.assertTrue( (new EnergySource()).push() );
		Assertions.assertTrue( (new EnergySource()).rest() );
		Assertions.assertTrue( (new EnergySource()).slap() );
		Assertions.assertTrue( (new EnergySource()).sleep() );
		Assertions.assertTrue( (new EnergySource()).stop() );
		
		Assertions.assertNotNull( middleWC );
		Assertions.assertNotNull( middleWC.getEnvironment() );
		Assertions.assertEquals(1, middleWC.getPosition().getPosX());
		Assertions.assertEquals(1, middleWC.getPosition().getPosY());
		Assertions.assertNotNull( middleWC.getChemicals() );
		Assertions.assertNotNull( middleWC.getAgentListe() );
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
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
		
		Assertions.assertEquals( 1, middleWC.getAgentListLength() );
		Assertions.assertEquals( 1, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
		});
		
		PlantEgg pe = new PlantEgg();
		Assertions.assertNotNull( pe );
		middleWC.addAgent( pe );
		
		Assertions.assertEquals( true, pe.isAlive() );
		Assertions.assertEquals( false, pe.isMovable() );
		Assertions.assertEquals( false, pe.isEatable() );
		Assertions.assertEquals( ObjectType.AGENT, pe.getObjectType() );
		Assertions.assertEquals( AgentType.BIOSILICO_VIRIDITA, pe.getAgentType() );
		Assertions.assertEquals( StatusType.EGG, pe.getOrganismStatus() );
		
		
		
		w.execution();
		
		Assertions.assertEquals( 2, middleWC.getAgentListLength() );
		Assertions.assertEquals( 2, middleWC.getAgentListe().size() );
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, middleWC.getChemicals().getVariable(k) );
		});
		
		Assertions.assertEquals( false, pe.isAlive() );
		Assertions.assertEquals( true, pe.isMovable() );
		Assertions.assertEquals( true, pe.isEatable() );
		Assertions.assertEquals( ObjectType.FOOD, pe.getObjectType() );
		Assertions.assertEquals( AgentType.BIOSILICO_VIRIDITA, pe.getAgentType() );
		Assertions.assertEquals( StatusType.EGG, pe.getOrganismStatus() );
		
	}
	
	/**
	 * 
	 * @author Gabriel Chandesris (2020)
	 */
	class PlantEgg extends Organism {
		public PlantEgg() {
			this.setAlive( true );
			this.setEatable( false );
			this.setMovable( false );
			this.setNameCommon("Plant Egg");
			this.setObjectType(ObjectType.AGENT);
			this.setAgentType(AgentType.BIOSILICO_VIRIDITA);
			this.setOrganismStatus(StatusType.EGG);
		}
	}
	
}

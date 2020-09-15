package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class WorldTests {
	public static final int INDEX_SOLAR	= 390; // SomeChemicals.ENERGY_SOLAR.getIndex();
	public static final int INDEX_HEAT	= 391; // SomeChemicals.ENERGY_HEAT.getIndex();

	@Test
	void testWorld01() {
		World w = new World();
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() );
		
		IntStream.range(0, World.MAX_HEIGHT).forEach( i -> {
			IntStream.range(0, World.MAX_WIDTH).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				});
				
			});
		});
	}
	
	@Test
	void testWorld02() {
		World w = new World(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() );
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				}); 
			});
		});
	}
	
	/**
	 * Test energy source without half-lives 
	 */
	@Test
	void testWorld03() {
		World w = new World(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() );
		
		Assertions.assertTrue( w.loadHalLives() );
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				}); 
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				}); 
			});
		});
		
		WorldCase currentWC2o3 = w.getWorldCase(2, 3);
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(0, currentWC2o3.getAgentListe().size());
		currentWC2o3.addAgent(new EnergySource());
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(1, currentWC2o3.getAgentListe().size());
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (WorldTests.INDEX_HEAT) :
							Assertions.assertEquals( 15, currentWC.getVariables().getVariable(k) );
						break;
						case (WorldTests.INDEX_SOLAR) :
							Assertions.assertEquals(  5, currentWC.getVariables().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getVariables().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
					}); 
				}
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (WorldTests.INDEX_HEAT) :
							Assertions.assertEquals( 30, currentWC.getVariables().getVariable(k) );
						break;
						case (WorldTests.INDEX_SOLAR) :
							Assertions.assertEquals( 10, currentWC.getVariables().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getVariables().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
					}); 
				}
			});
		});
	}
	
	/**
	 * Test energy source without half-lives 
	 */
	@Test
	void testWorld04() {
		World w = new World(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() );
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				}); 
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
				}); 
			});
		});
		
		WorldCase currentWC2o3 = w.getWorldCase(2, 3);
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(0, currentWC2o3.getAgentListe().size());
		currentWC2o3.addAgent(new EnergySource());
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(1, currentWC2o3.getAgentListe().size());
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (WorldTests.INDEX_HEAT) :
						case (WorldTests.INDEX_SOLAR) :
							Assertions.assertEquals( 25, currentWC.getVariables().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getVariables().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
					}); 
				}
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (WorldTests.INDEX_HEAT) :
						case (WorldTests.INDEX_SOLAR) :
							Assertions.assertEquals( 50, currentWC.getVariables().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getVariables().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getVariables().getVariable(k) );
					}); 
				}
			});
		});
	}
	
}

package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class World2DTests {
	public static final int INDEX_SOLAR	= 390; // SomeChemicals.ENERGY_SOLAR.getIndex();
	public static final int INDEX_HEAT	= 391; // SomeChemicals.ENERGY_HEAT.getIndex();

	@Test
	void testWorld01() {
		World2D w = new World2D(World2D.MAX_HEIGHT, World2D.MAX_WIDTH);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getChemicals() );
		
		IntStream.range(0, World2D.MAX_HEIGHT).forEach( i -> {
			IntStream.range(0, World2D.MAX_WIDTH).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				});
				
			});
		});
	}
	
	@Test
	void testWorld02() {
		World2D w = new World2D(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getChemicals() );
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				}); 
			});
		});
	}
	
	/**
	 * Test energy source without half-lives 
	 */
	@Test
	void testWorld03() {
		World2D w = new World2D(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getChemicals() );
		
		Assertions.assertTrue( w.loadHalLives() );
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				}); 
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				}); 
			});
		});
		
		World2DCase currentWC2o3 = w.getWorldCase(2, 3);
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(0, currentWC2o3.getAgentListe().size());
		currentWC2o3.addAgent(new EnergySource());
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(1, currentWC2o3.getAgentListe().size());
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (World2DTests.INDEX_HEAT) :
							Assertions.assertEquals( 15, currentWC.getChemicals().getVariable(k) );
						break;
						case (World2DTests.INDEX_SOLAR) :
							Assertions.assertEquals(  5, currentWC.getChemicals().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getChemicals().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
					}); 
				}
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (World2DTests.INDEX_HEAT) :
							Assertions.assertEquals( 30, currentWC.getChemicals().getVariable(k) );
						break;
						case (World2DTests.INDEX_SOLAR) :
							Assertions.assertEquals( 10, currentWC.getChemicals().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getChemicals().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
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
		World2D w = new World2D(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getChemicals() );
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				}); 
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				
				IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
					Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
				}); 
			});
		});
		
		World2DCase currentWC2o3 = w.getWorldCase(2, 3);
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(0, currentWC2o3.getAgentListe().size());
		currentWC2o3.addAgent(new EnergySource());
		Assertions.assertNotNull( currentWC2o3 );
		Assertions.assertEquals(1, currentWC2o3.getAgentListe().size());
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (World2DTests.INDEX_HEAT) :
						case (World2DTests.INDEX_SOLAR) :
							Assertions.assertEquals( 25, currentWC.getChemicals().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getChemicals().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
					}); 
				}
			});
		});
		
		w.execution();
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				World2DCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
				if ( (i == 2) && (j == 3) ) {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						switch(k) {
						case (World2DTests.INDEX_HEAT) :
						case (World2DTests.INDEX_SOLAR) :
							Assertions.assertEquals( 50, currentWC.getChemicals().getVariable(k) );
						break;
						default:
							Assertions.assertEquals(  0, currentWC.getChemicals().getVariable(k) );
						}
					}); 
				} else {
					IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
						Assertions.assertEquals( 0, currentWC.getChemicals().getVariable(k) );
					}); 
				}
			});
		});
	}
	
}

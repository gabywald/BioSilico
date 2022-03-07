package gabywald.biosilico.genetics.complex.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.builders.complex.PathwayBuilder;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
class PathwayBuilderTests {

	@Test
	void testPathwayBuilder() {
		PathwayBuilder pb = new PathwayBuilder();
		Assertions.assertNotNull(pb);
	}

	@Test
	void testAddparams() {
		PathwayBuilder pb = new PathwayBuilder();
		Assertions.assertNotNull(pb);
		pb = pb.addparams(50, 50, 0, 0, 0, 0, 0, 0, 1);
		Assertions.assertNotNull(pb);
	}
	
	// NOTE : Basic Pathway tests ; implementations
	
	@Test
	void testBuildBasicPathway01() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 0, 0, 1, 53, 0, 0, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway02a() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 51, 0, 0, 1, 53, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway02b() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 51, 1, 53, 0, 0, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway03a() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 2
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 0, 0, 1, 53, 1, 54, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway03b() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 2
		List<BiochemicalReaction> results = 
				pb.addparams(0, 0, 1, 50, 1, 53, 1, 54, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway04() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 2
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 52, 1, 53, 1, 54, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway05() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 0
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 0, 0, 0, 0, 0, 0, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}

	@Test
	void testBuildBasicPathway06() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 0
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 52, 0, 0, 0, 0, 1)
				.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	// TODO Other kind of pathway (possibilities !), generating more than one BiochemicalReaction Gene !?
	// TODO find algorithm (to externalize from input before pathway construction, and static in the class ?)

	@Test
	void testBuildComplexPathway01() {
		PathwayBuilder pb = new PathwayBuilder();
		// 3 => 1 : from 3 chemicals as sources => one (1) chemical as result
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 52, 1, 53, 0, 0, 1)
				  .addparams(2, 53, 0,  0, 1, 55, 0, 0, 1)
				  .build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(2, results.size());
	}
	
	@Test
	void testBuildComplexPathway02() {
		PathwayBuilder pb = new PathwayBuilder();
		// 3 => 2 : from 3 chemicals as sources => two (2) chemicals as result
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 52, 1, 53, 0,  0, 1)
				  .addparams(2, 53, 0,  0, 1, 55, 1, 56, 1)
				  .build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(2, results.size());
	}
	
	@Test
	void testBuildComplexPathway03() {
		PathwayBuilder pb = new PathwayBuilder();
		// 4 => 1 : from 4 chemicals as sources => one (1) chemical as result
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 52, 1, 53, 1, 54, 1)
				  .addparams(2, 53, 2, 54, 1, 55, 0, 0, 1)
				  .build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(2, results.size());
	}
	
	@Test
	void testBuildComplexPathway04() {
		PathwayBuilder pb = new PathwayBuilder();
		// 4 => 2 : from 4 chemicals as sources => two (2) chemicals as result
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50, 1, 52, 1, 53, 1, 54, 1)
				  .addparams(2, 53, 2, 54, 1, 55, 1, 56, 1)
				  .build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(2, results.size());
	}
	
	void testBuildComplexPathway05() {
		PathwayBuilder pb = new PathwayBuilder();
		// 4 => 4 with combinations and equilibrium !
		List<BiochemicalReaction> results = 
				pb.addparams(1, 51, 1, 52, 1, 53, 1, 54, 2)
				  .addparams(1, 53, 1, 54, 1, 51, 1, 52, 1)
				  
				  .addparams(1, 51, 1, 52, 1, 55, 1, 56, 1)
				  .addparams(1, 53, 1, 54, 1, 55, 1, 56, 1)
				  
				  .addparams(1, 51, 1, 52, 1, 57, 1, 58, 1)
				  .addparams(1, 53, 1, 54, 1, 57, 1, 58, 1)
				  
				  .addparams(1, 55, 1, 56, 1, 57, 1, 58, 2)
				  .addparams(1, 57, 1, 58, 1, 55, 1, 56, 1)
				  .build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(8, results.size());
	}
	
	// TODO More complex pathways !! re-take examples from AntHill v1 constructions / builds
	// STARCH => 6 GLUCOSE ; 
	// HEXOKINASE + GLUCOSE => 6 C02 + 6 H20 ;
	// HEXOKINASE + FRUCOSE => 5 C02 + 5 H20 ;
	// 10 GLUCOSE + 5 DIOXYGEN => GLYCOGEN ; 
	// 15 FRUCTOSE + 5 DIOXYGEN => GLYCOGEN ; 
	// GLYCOGEN => 10 GLUCOSE ; 
	// GLYCOGEN + HEXOKINASE => 8 GLUCOSE ; 

}

package gabywald.biosilico.genetics.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.builders.complex.PathwayBuilder;

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
		pb = pb.addparams(50, 50);
		Assertions.assertNotNull(pb);
	}
	
	// NOTE : Basic Pathway tests ; implementations
	
	@Test
	void testBuildBasicPathway01() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50)
					.addparams(0, 0)
					.addparams(1, 53).build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway02() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50).addparams(1, 51)
					.addparams(0, 0)
					.addparams(1, 53).build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway03() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 2
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50)
					.addparams(0, 0)
					.addparams(1, 53).addparams(1, 54).build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway04() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 2
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50).addparams(1, 52)
					.addparams(0, 0)
					.addparams(1, 53).addparams(1, 54).build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	@Test
	void testBuildBasicPathway05() {
		PathwayBuilder pb = new PathwayBuilder();
		// 1 => 0
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50)
					.addparams(0, 0).build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}

	@Test
	void testBuildBasicPathway06() {
		PathwayBuilder pb = new PathwayBuilder();
		// 2 => 0
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50).addparams(1, 52)
					.addparams(0, 0).build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(1, results.size());
	}
	
	// TODO Other kind of pathway (possibilities !), generating more than one BiochemicalReaction Gene !

	@Test
	void testBuildComplexPathway01() {
		PathwayBuilder pb = new PathwayBuilder();
		// 3 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50).addparams(1, 52).addparams(1, 53)
					.addparams(0, 0)
					.addparams(1, 55)
					.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(2, results.size());
	}
	
	@Test
	void testBuildComplexPathway02() {
		PathwayBuilder pb = new PathwayBuilder();
		// 3 => 1
		List<BiochemicalReaction> results = 
				pb.addparams(1, 50).addparams(1, 52).addparams(1, 53)
					.addparams(0, 0)
					.addparams(1, 55).addparams(1, 56)
					.build();
		Assertions.assertNotNull( results );
		Assertions.assertEquals(2, results.size());
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

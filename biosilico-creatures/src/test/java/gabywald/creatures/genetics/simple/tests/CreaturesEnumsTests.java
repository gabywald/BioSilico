package gabywald.creatures.genetics.simple.tests;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.Creatures1GenomeParser;
import gabywald.creatures.genetics.simple.CreaturesChemical;
import gabywald.creatures.genetics.simple.CreaturesEnums;
import gabywald.creatures.genetics.simple.CreaturesVersion;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class CreaturesEnumsTests {

	private static List<String> getEnumFrom(String property) {
		String propertyContent = Creatures1GenomeParser.PROPERTIES.getProperty( property );
		return Arrays.asList( propertyContent.split(", ") );
	}
	
	@Test
	void testGetLobeFlags() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.brain.lobeflags"), 
				CreaturesEnums.getLobeFlags());

		Assertions.assertEquals(	Arrays.asList(new String[] { "Winner Takes All" }), 
				CreaturesEnums.getLobeFlags());
	}
	
	@Test
	void testGetPerceptionFlags() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.brain.perceptionlobelink"), 
				CreaturesEnums.getPerceptionFlags());

		Assertions.assertEquals(	Arrays.asList(new String[] { "No", "Yes", "Mutually Exclusive" }), 
				CreaturesEnums.getPerceptionFlags());
	}
	
	@Test
	void testGetSpread() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.brain.spread"), 
				CreaturesEnums.getSpread());

		Assertions.assertEquals(	Arrays.asList(new String[] { "--- Flat", "/ \\Normal", "| \\ Saw", "/ | waS" }), 
				CreaturesEnums.getSpread());
	}
	
	@Test
	void testGetMigrate() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.brain.migrate"), 
				CreaturesEnums.getMigrate());

		Assertions.assertEquals(	Arrays.asList(new String[] { "do NOT migrate", "Migrate if ANY dendrite is loose and this cell is firing", "Migrate when ALL dendrites are loose" }), 
				CreaturesEnums.getMigrate());
	}

	@Test
	void testGetGeneBitFlags() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.genebitflags"), 
				CreaturesEnums.getGeneBitFlags());

		Assertions.assertEquals(	Arrays.asList(new String[] { "Mutable", "Duplicatable", "Deletable", "Male", "Female", "Dormant" }), 
				CreaturesEnums.getGeneBitFlags());
	}

	@Test
	void testGetBodyParts() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.bodyparts"), 
				CreaturesEnums.getBodyParts());

		Assertions.assertEquals(	Arrays.asList(new String[] { "head", "body", "leg", "arm", "tail" }), 
				CreaturesEnums.getBodyParts());
	}

	@Test
	void testGetSwitchOnStages() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.switchonstage"), 
				CreaturesEnums.getSwitchOnStages());

		Assertions.assertEquals(	Arrays.asList(new String[] { "Embryo", "Child", "Adolescent", "Youth", "Adult", "Old", "Senile" }), 
				CreaturesEnums.getSwitchOnStages());
	}

	@Test
	void testGetSpecies() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.species"), 
				CreaturesEnums.getSpecies());

		Assertions.assertEquals(	Arrays.asList(new String[] { "Norn", "Grendel", "Ettin", "Shee" }), 
				CreaturesEnums.getSpecies());
	}

	@Test
	void testGetPigmentColors() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.pigmentcolor"), 
				CreaturesEnums.getPigmentColors());

		Assertions.assertEquals(	Arrays.asList(new String[] { "red", "green", "blue" }), 
				CreaturesEnums.getPigmentColors());
	}

	@Test
	void testGetSVRules() {
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.svrules"), 
				CreaturesEnums.getSVRules());

		Assertions.assertEquals(	Arrays.asList(new String[] { "<end>", "0", "1", "64", "255", "chem0", "chem1", "chem2", "chem3", "state", "output", "thres", "type0", "type1", "anded0", "anded1", "input", "conduct", "suscept", "STW", "LTW", "strength", "32", "128", "rnd const", "chem4", "chem5", "leak in", "leak out", "curr src leak in", "TRUE", "PLUS", "MINUS", "TIMES", "INCR", "DECR", "FALSE", "multiply", "average", "move twrds", "random", "<ERROR>" }), 
				CreaturesEnums.getSVRules());
	}
	
	private static void compareChemicals(	List<CreaturesChemical> ccsExpected, 
											List<CreaturesChemical> ccsObtain, 
											int size) {
		Assertions.assertNotNull( ccsExpected );
		Assertions.assertEquals(size, ccsExpected.size());
		IntStream.range(0, size).forEach(i -> 
			{ Assertions.assertEquals(ccsExpected.get( i ), ccsObtain.get( i )); });
		Assertions.assertTrue(ccsExpected.equals(ccsObtain));
		Assertions.assertEquals(ccsExpected, ccsObtain);
	}
	
	@Test
	void testGetCreaturesChemicalsV1() {
		List<CreaturesChemical> ccs		= CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		List<CreaturesChemical> ccsEnum	= CreaturesEnums.getC1Chemicals();
		CreaturesEnumsTests.compareChemicals(ccs, ccsEnum, 256);
	}
	
	@Test
	void testGetCreaturesChemicalsV2() {
		List<CreaturesChemical> ccs		= CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES2);
		List<CreaturesChemical> ccsEnum	= CreaturesEnums.getC2Chemicals();
		CreaturesEnumsTests.compareChemicals(ccs, ccsEnum, 256);
	}
	
	@Test
	void testGetCreaturesChemicalsV3() {
		List<CreaturesChemical> ccs		= CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES3);
		List<CreaturesChemical> ccsEnum	= CreaturesEnums.getC3Chemicals();
		CreaturesEnumsTests.compareChemicals(ccs, ccsEnum,   0);
	}

}

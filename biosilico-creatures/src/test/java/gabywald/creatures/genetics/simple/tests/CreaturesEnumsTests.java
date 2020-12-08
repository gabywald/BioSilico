package gabywald.creatures.genetics.simple.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.Creatures1GenomeParser;
import gabywald.creatures.genetics.simple.CreaturesEnums;

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
		Assertions.assertEquals(	CreaturesEnumsTests.getEnumFrom( "data.enum.creatures1.lobeflags"), 
				CreaturesEnums.getLobeFlags());

		Assertions.assertEquals(	Arrays.asList(new String[] { "Winner Takes All" }), 
				CreaturesEnums.getLobeFlags());
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

}

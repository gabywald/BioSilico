package gabywald.creatures.GRtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.geneticReader.GeneticFileContent;

public class ChemicalReadings {

	@Test
	public void testGTC001() {
		// final String dir = System.getProperty("user.dir");
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/chemicals.txt");
		Assertions.assertEquals(true, gtc.isReadable());
	}
	
}

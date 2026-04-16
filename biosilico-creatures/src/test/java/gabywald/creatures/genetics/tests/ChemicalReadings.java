package gabywald.creatures.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.geneticReader.GeneticFileContent;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020, 2026)
 */
public class ChemicalReadings {

	@Test
	public void testGTC001() {
		// final String dir = System.getProperty("user.dir");
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/chemicals.bin");
		Assertions.assertNotNull(gtc);
		Assertions.assertEquals(true, gtc.isReadable());
	}
	
}

package gabywald.creatures.GRtests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

public class ChemicalReadings {

	@Test
	public void testGTC001() {
		// final String dir = System.getProperty("user.dir");
        // System.out.println("current dir = " + dir);
		GeneticFileContent gtc = new GeneticFileContent("creatures/creaturesOriginals/chemicals.txt");
		Assertions.assertEquals(true, gtc.isReadable());
	}
	
}

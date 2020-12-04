package gabywald.creatures.genetics.simple.tests;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.Creatures1GenomeParser;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GeneTypeSubTypeTests {
	
	@Test
	void testGetGeneTypeSubType() {
		Assertions.assertNotNull( GeneTypeSubType.getGeneTypeSubType("2-1") );
	}

	@Test
	void testGetGeneTypesSubTypes() {
		Assertions.assertNotNull( GeneTypeSubType.getGeneTypesSubTypes() );
	}
	
	@Test
	void testFileReading() {
		
		Map<String, GeneTypeSubType> map = GeneTypeSubType.getGeneTypesSubTypes();
		
		Assertions.assertNotNull( map );
		
		String path2geneTST = Creatures1GenomeParser.PROPERTIES.getProperty( "data.enum.creatures1.geneTypesSubTypes.creatures1and2" );
		File geneDefinitionsFile = null;
		try {
			geneDefinitionsFile = File.loadFile( path2geneTST );
		} catch (IOException e) { e.printStackTrace(); }
		Assertions.assertNotNull( geneDefinitionsFile );
		for (int i = 0 ; (i < geneDefinitionsFile.lengthFile()) ; i++) {
			String line			= geneDefinitionsFile.getChamp(i);
			if (line.startsWith( "## " )) { continue; }
			String[] splitter	= line.split("\t");
			String key			= splitter[0] + "-" + splitter[1];
			int type			= Integer.parseInt(splitter[0]);
			int subt			= Integer.parseInt(splitter[1]);
			 // TODO change this below for next version of Creatures (C2)
			if (splitter[2].equals("?")) { continue; }
			int attemptedLengthC1	= Integer.parseInt(splitter[2].split(",")[0]);
			int attemptedLengthC2	= Integer.parseInt(splitter[2].split(",")[1]);
			String name				= splitter[3].split(" -- ")[1];
			
			Logger.printlnLog(LoggerLevel.LL_INFO, "\t" + key + "\t::\t" + type + "\t" + subt + "\t" + attemptedLengthC1 + "\t" + name);
			
			Assertions.assertTrue( map.containsKey(key) );
			GeneTypeSubType gtst = map.get(key);
			Assertions.assertNotNull( gtst );
			Assertions.assertEquals(type, gtst.getType() );
			Assertions.assertEquals(subt, gtst.getSubtype() );
			Assertions.assertEquals(attemptedLengthC1, gtst.getAttemptedLengthC1() );
			Assertions.assertEquals(attemptedLengthC2, gtst.getAttemptedLengthC2() );
			Assertions.assertEquals(name, gtst.getShortName() );
		}
		
	}

}

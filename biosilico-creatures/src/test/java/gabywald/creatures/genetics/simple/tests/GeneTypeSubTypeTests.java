package gabywald.creatures.genetics.simple.tests;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.global.data.File;

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
		
		File geneDefinitionsFile = null;
		try {
			geneDefinitionsFile = File.loadFile( "creatures/creaturesDocs/geneticsParsing/data/geneC1C2definitions.txt" );
		} catch (IOException e) { e.printStackTrace(); }
		Assertions.assertNotNull( geneDefinitionsFile );
		for (int i = 0 ; (i < geneDefinitionsFile.lengthFile()) ; i++) {
			String line			= geneDefinitionsFile.getChamp(i);
			if (line.startsWith( "## " )) { continue; }
			String[] splitter	= line.split("\t");
			String key			= splitter[0] + "-" + splitter[1];
			int type			= Integer.parseInt(splitter[0]);
			int subt			= Integer.parseInt(splitter[1]);
			if (splitter[2].equals("?")) { continue; } // TODO change this for next version of Creatures (C2)
			int attemptedLength	= Integer.parseInt(splitter[2]);
			String name			= splitter[3].split(" -- ")[1];
			
			Assertions.assertTrue( map.containsKey(key) );
			GeneTypeSubType gtst = map.get(key);
			Assertions.assertNotNull( gtst );
			Assertions.assertEquals(type, gtst.getType() );
			Assertions.assertEquals(subt, gtst.getSubtype() );
			Assertions.assertEquals(attemptedLength, gtst.getAttemptedLength() );
			Assertions.assertEquals(name, gtst.getName() );
		}
		
	}

}

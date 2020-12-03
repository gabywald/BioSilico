package gabywald.creatures.genetics.simple.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.exceptions.GenomeParserException;
import gabywald.creatures.genetics.simple.CreaturesGenome;
import gabywald.creatures.genetics.simple.Creatures1GenomeParser;

class Creatures1GenomeParserTests {
	
	@Test
	void testParseGenomeNOFILE() {
		Assertions.assertThrows(GenomeParserException.class, 
				() -> Creatures1GenomeParser.parseGenome( "" ), 
				"File Path is empty !");
		Assertions.assertThrows(GenomeParserException.class, 
				() -> Creatures1GenomeParser.parseGenome( null ), 
				"File Path is null !");
	}

	@Test
	void testParseGenomeMUM1() throws GenomeParserException {
		String path2test = "creatures/creaturesOriginals/mum1.gen";
		CreaturesGenome c1gMUM = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1gMUM );;
		Assertions.assertEquals("mum1", c1gMUM.getName());
		Assertions.assertEquals(path2test, c1gMUM.getPathOfFile());
		Assertions.assertEquals(321, c1gMUM.getGenome().size());
	}
	
	@Test
	void testParseGenomeDAD1() throws GenomeParserException {
		String path2test = "creatures/creaturesOriginals/dad1.gen";
		CreaturesGenome c1gMUM = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1gMUM );;
		Assertions.assertEquals("dad1", c1gMUM.getName());
		Assertions.assertEquals(path2test, c1gMUM.getPathOfFile());
		Assertions.assertEquals(320, c1gMUM.getGenome().size());
	}

}

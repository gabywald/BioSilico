package gabywald.creatures.genetics.simple.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.exceptions.ParserException;
import gabywald.creatures.genetics.simple.CreaturesGenome;
import gabywald.creatures.genetics.simple.ICreaturesGene;
import gabywald.creatures.genetics.simple.decoder.GeneCreaturesDecoderSuite;
import gabywald.creatures.genetics.simple.decoder.IGeneCreaturesDecoder;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.creatures.genetics.simple.Creatures1GenomeParser;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class Creatures1GenomeParserTests {
	
	@Test
	void testParseGenomeNOFILE() {
		Assertions.assertThrows(ParserException.class, 
				() -> Creatures1GenomeParser.parseGenome( "" ), 
				"File Path is empty !");
		Assertions.assertThrows(ParserException.class, 
				() -> Creatures1GenomeParser.parseGenome( null ), 
				"File Path is null !");
	}

	@Test
	void testParseGenomeMUM1() throws ParserException {
		String path2test = "creatures/creaturesOriginals/mum1.gen";
		CreaturesGenome c1g = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1g );;
		Assertions.assertEquals("mum1", c1g.getName());
		Assertions.assertEquals(path2test, c1g.getPathOfFile());
		Assertions.assertEquals(321, c1g.getGenome().size());
	}
	
	@Test
	void testParseGenomeDAD1() throws ParserException {
		String path2test = "creatures/creaturesOriginals/dad1.gen";
		CreaturesGenome c1g = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1g );
		Assertions.assertEquals("dad1", c1g.getName());
		Assertions.assertEquals(path2test, c1g.getPathOfFile());
		Assertions.assertEquals(320, c1g.getGenome().size());
	}
	
	@Test
	void testParseGenomeGREN() throws ParserException {
		String path2test = "creatures/creaturesOriginals/Gren.gen";
		CreaturesGenome c1g = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1g );
		Assertions.assertEquals("Gren", c1g.getName());
		Assertions.assertEquals(path2test, c1g.getPathOfFile());
		Assertions.assertEquals(259, c1g.getGenome().size());
	}
	
	@Test
	void testParseGenomeGRENtoDecode() throws ParserException {
		String path2test = "creatures/creaturesOriginals/Gren.gen";
		CreaturesGenome c1g = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1g );
		
		
		List<IGeneCreaturesDecoder> decoders = GeneCreaturesDecoderSuite.getSuite();
		
		for (ICreaturesGene cg : c1g.getGenome()) {
			for (IGeneCreaturesDecoder igcd : decoders) {
				String result = igcd.decodeFrom( cg );
				Logger.printlnLog(LoggerLevel.LL_DEBUG, cg.printInline() + "\n*****" + result);
			}
			Logger.printlnLog(LoggerLevel.LL_FORUSER, cg.printInline() + "\n" + cg.print4human() );
		}
	}
	
	@Test
	void testParseGenomeDAD1toDecode() throws ParserException {
		String path2test = "creatures/creaturesOriginals/dad1.gen";
		CreaturesGenome c1g = Creatures1GenomeParser.parseGenome( path2test );
		Assertions.assertNotNull( c1g );
		
		
		List<IGeneCreaturesDecoder> decoders = GeneCreaturesDecoderSuite.getSuite();
		
		for (ICreaturesGene cg : c1g.getGenome()) {
			for (IGeneCreaturesDecoder igcd : decoders) {
				String result = igcd.decodeFrom( cg );
				Logger.printlnLog(LoggerLevel.LL_DEBUG, cg.printInline() + "\n*****" + result);
			}
			Logger.printlnLog(LoggerLevel.LL_FORUSER, cg.printInline() + "\n" + cg.print4human() );
		}
	}

}

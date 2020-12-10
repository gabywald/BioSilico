package gabywald.creatures.genetics.simple.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.exceptions.ParserException;
import gabywald.creatures.genetics.simple.CreaturesGenome;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
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
	
	@Test
	void testExportGenomes() throws ParserException, IOException {
		
		FilenameFilter genFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".gen");
			}
		};
		File directoryPath1 = new File("src/main/resources/creatures/creaturesOriginals");
		File[] files1 = directoryPath1.listFiles(genFilter);
		File directoryPath2 = new File("src/main/resources/creatures/creaturesDocs/genetics");
		File[] files2 = directoryPath2.listFiles(genFilter);
		
		List<File> files = new ArrayList<File>();
		files.addAll(Arrays.asList(files1));
		files.addAll(Arrays.asList(files2));
		
		GeneTypeSubType gtstBrainLobe = GeneTypeSubType.getGeneTypeSubType(0, 0);
		List<ICreaturesGene> brainLobeGenes = new ArrayList<ICreaturesGene>();
		
		for (File file : files) {
			String fileName = null;
			try {
				fileName = file.getCanonicalPath();
				// System.out.println( fileName );
				CreaturesGenome c1g = Creatures1GenomeParser.parseGenome( fileName );
				Assertions.assertNotNull( c1g );
				for (ICreaturesGene icg : c1g.getGenome()) {
					Assertions.assertNotNull( icg );
					Assertions.assertNotNull( icg.getType() );
					if (gtstBrainLobe.equals( icg.getType() )) {
						brainLobeGenes.add(icg);
					}
				}
			} catch (IOException e) {
				System.out.println( e.getMessage() );
			}
		}
		
		System.out.println( "brainLobeGenes: [" + brainLobeGenes.size() + "]" );
		FileOutputStream fosBrainLobes = new FileOutputStream( "outputBrainLobes.txt" );
		List<IGeneCreaturesDecoder> decoders = GeneCreaturesDecoderSuite.getSuite();
		for (ICreaturesGene cg : brainLobeGenes) {
			for (IGeneCreaturesDecoder igcd : decoders) {
				// String result = 
				igcd.decodeFrom( cg );
				// Logger.printlnLog(LoggerLevel.LL_DEBUG, cg.printInline() + "\n*****" + result);
			}
			String output = cg.printInline() + "\n" + cg.print4human() + "\n";
			// Logger.printlnLog(LoggerLevel.LL_FORUSER, output );
			fosBrainLobes.write( output.getBytes() );
		}
		
		fosBrainLobes.flush();
		fosBrainLobes.close();
		
	}

}

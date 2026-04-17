package gabywald.creatures.genetics.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.builds.CreatureGeneFactory;
import gabywald.creatures.genetics.builds.ICreatureGene;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020, 2026)
 */
public class GeneticFileContentTests {
	
	@Test
	public void testGFCbasicDAD() {
		GeneticFileContent gfcDad1 = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		Assertions.assertNotNull(gfcDad1);
		Assertions.assertEquals(true, gfcDad1.isReadable());
	}
	
	@Test
	public void testGFCbasicMUM() {
		GeneticFileContent gfcMum1 = new GeneticFileContent("creatures/creaturesOriginals/mum1.gen");
		Assertions.assertNotNull(gfcMum1);
		Assertions.assertEquals(true, gfcMum1.isReadable());
	}
	
	@Test
	public void testGFCbasicGREN() {
		GeneticFileContent gfcGren = new GeneticFileContent("creatures/creaturesOriginals/Gren.gen");
		Assertions.assertNotNull(gfcGren);
		Assertions.assertEquals(true, gfcGren.isReadable());
	}
	
	@Test
	public void testGFCreadingDAD1() {
		String genomeFileDAD = "creatures/creaturesOriginals/dad1.gen";
		List<ICreatureGene> genomeDAD = CreatureGeneFactory.readGenome(genomeFileDAD);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFileDAD + "] -- {" + genomeDAD.size() + " genes}");
		Assertions.assertEquals(true, (genomeDAD.size() > 0) );
	}

	@Test
	public void testGFCreadingMUM1() {
		String genomeFileMUM = "creatures/creaturesOriginals/mum1.gen";
		List<ICreatureGene> genomeMUM = CreatureGeneFactory.readGenome(genomeFileMUM);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFileMUM + "] -- {" + genomeMUM.size() + " genes}");
		Assertions.assertEquals(true, (genomeMUM.size() > 0) );
	}
	
	@Test
	public void testGFCreadingGREN() {
		String genomeFile = "creatures/creaturesOriginals/Gren.gen";
		List<ICreatureGene> genomeGREN = CreatureGeneFactory.readGenome(genomeFile);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genomeGREN.size() + " genes}");
		Assertions.assertEquals(true, (genomeGREN.size() > 0) );
	}
}


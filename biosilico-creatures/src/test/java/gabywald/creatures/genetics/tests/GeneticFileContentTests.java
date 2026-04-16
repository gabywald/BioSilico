package gabywald.creatures.genetics.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.CreatureGene;
import gabywald.creatures.genetics.CreatureGeneFactory;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020, 2026)
 */
public class GeneticFileContentTests {
	
	@Test
	public void testGFCbasic() {
		GeneticFileContent gfcDad1 = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		Assertions.assertNotNull(gfcDad1);
		Assertions.assertEquals(true, gfcDad1.isReadable());
		
		GeneticFileContent gfcMum1 = new GeneticFileContent("creatures/creaturesOriginals/mum1.gen");
		Assertions.assertNotNull(gfcMum1);
		Assertions.assertEquals(true, gfcMum1.isReadable());
		
		GeneticFileContent gfcGren = new GeneticFileContent("creatures/creaturesOriginals/Gren.gen");
		Assertions.assertNotNull(gfcGren);
		Assertions.assertEquals(true, gfcGren.isReadable());
	}
	
	@Test
	public void testGFCdad1Readings() {
		String genomeFileDAD = "creatures/creaturesOriginals/dad1.gen";
		List<CreatureGene> genomeDAD = CreatureGeneFactory.readGenome(genomeFileDAD);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFileDAD + "] -- {" + genomeDAD.size() + " genes}");
		Assertions.assertEquals(true, (genomeDAD.size() > 0) );
	}

	@Test
	public void testGFCmum1Readings() {
		String genomeFileMUM = "creatures/creaturesOriginals/mum1.gen";
		List<CreatureGene> genomeMUM = CreatureGeneFactory.readGenome(genomeFileMUM);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFileMUM + "] -- {" + genomeMUM.size() + " genes}");
		Assertions.assertEquals(true, (genomeMUM.size() > 0) );
	}
	
	@Test
	public void testGFCGrenReading() {
		String genomeFile = "creatures/creaturesOriginals/Gren.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
}


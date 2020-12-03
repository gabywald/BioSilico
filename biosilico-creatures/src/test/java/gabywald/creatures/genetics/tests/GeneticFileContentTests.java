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
 * @author Gabriel Chandesris (2010, 2020)
 */
public class GeneticFileContentTests {
	
	@Test
	public void testGFCbasic() {
		GeneticFileContent gfcDad = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		Assertions.assertEquals(true, gfcDad.isReadable());
		
		GeneticFileContent gfcMum = new GeneticFileContent("creatures/creaturesOriginals/mum1.gen");
		Assertions.assertEquals(true, gfcMum.isReadable());
	}
	
	@Test
	public void testGFCreadings() {
		String genomeFileDAD = "creatures/creaturesOriginals/dad1.gen";
		List<CreatureGene> genomeDAD = CreatureGeneFactory.readGenome(genomeFileDAD);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFileDAD + "] -- {" + genomeDAD.size() + " genes}");
		Assertions.assertEquals(true, (genomeDAD.size() > 0) );

		String genomeFileMUM = "creatures/creaturesOriginals/mum1.gen";
		List<CreatureGene> genomeMUM = CreatureGeneFactory.readGenome(genomeFileMUM);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFileMUM + "] -- {" + genomeMUM.size() + " genes}");
		Assertions.assertEquals(true, (genomeMUM.size() > 0) );
	}
	
	@Test
	public void testGFCotherReading() {
		String genomeFile = "creatures/creaturesOriginals/Gren.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);
		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
}


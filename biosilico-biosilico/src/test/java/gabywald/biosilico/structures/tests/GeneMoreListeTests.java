package gabywald.biosilico.structures.tests;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.Instinct;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.structures.GeneMoreListe;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2022)
 */
class GeneMoreListeTests {

	@Test
	void testGeneMoreListe() {
		GeneMoreListe gml = new GeneMoreListe();
		Assertions.assertNotNull( gml );
		Assertions.assertEquals(0, gml.length());
	}

	@Test
	void testGeneMoreListeStringBoolean() {
		GeneMoreListe gml = new GeneMoreListe("initialGenes.txt", false);
		Assertions.assertNotNull( gml );
		Assertions.assertEquals(0, gml.length());
		
		gml.readFile();
		// gml.printFile();
		
		Assertions.assertEquals(162, gml.getGenesNames().stream().count());
		// gml.getGenesNames().stream().forEach(System.out::println);
		LongStream.range(0, gml.getGenesNames().stream().count()).forEach( l -> {
			Logger.printlnLog(LoggerLevel.LL_NONE, l + " :: " + gml.getGene((int)l).toString() );
		});
		
		GeneMoreListe gmlBIS = new GeneMoreListe("initialGenes.txt", true);
		Assertions.assertNotNull( gmlBIS );
		Assertions.assertEquals(0, gmlBIS.length());
		
		gmlBIS.readFile();
		// gmlBIS.printFile();
		
		Assertions.assertEquals(gml.getGenesNames().stream().count(), gmlBIS.getGenesNames().stream().count());
	}

	@Test
	void testAddGenLength() {

		GeneMoreListe gml					= new GeneMoreListe();
		
		BiochemicalReactionBuilder bgmlb	= new BiochemicalReactionBuilder();
		BrainGeneBuilder bgb				= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb			= new BrainLobeGeneBuilder();
		EmitterReceptorBuilder erb			= new EmitterReceptorBuilder();
		InitialConcentrationBuilder icb		= new InitialConcentrationBuilder();
		InstinctBuilder	ib					= new InstinctBuilder();
		StimulusDecisionBuilder sdb			= new StimulusDecisionBuilder();
		
		gml.addGene(bgmlb.build());
		Assertions.assertEquals(1, gml.length());
		
		gml.addGene(bgb.build());
		Assertions.assertEquals(2, gml.length());
		
		gml.addGene(blgb.build());
		Assertions.assertEquals(3, gml.length());
		
		gml.addGene(erb.build());
		Assertions.assertEquals(4, gml.length());
		
		gml.addGene(icb.build());
		Assertions.assertEquals(5, gml.length());
		
		gml.addGene(ib.build());
		Assertions.assertEquals(6, gml.length());
		
		gml.addGene(sdb.build());
		Assertions.assertEquals(7, gml.length());
		
		Assertions.assertEquals(BiochemicalReaction.class, 	gml.getGene(0).getClass());
		Assertions.assertEquals(BrainGene.class, 			gml.getGene(1).getClass());
		Assertions.assertEquals(BrainLobeGene.class, 		gml.getGene(2).getClass());
		Assertions.assertEquals(EmitterReceptor.class, 		gml.getGene(3).getClass());
		Assertions.assertEquals(InitialConcentration.class, gml.getGene(4).getClass());
		Assertions.assertEquals(Instinct.class, 			gml.getGene(5).getClass());
		Assertions.assertEquals(StimulusDecision.class, 	gml.getGene(6).getClass());
		
		// gml.getGenesNames().stream().forEach( System.out::println );
		
	}
	
	public static void testFileExists(String path2File) {
		File file = new File( path2File );
		Assertions.assertTrue( file.fileExists() );
	}
	
	// 
	@Test
	void testExportGeneMoreList() {
		String path2file = FileBiological.BASE_TEST_DIR + FileBiological.DEFAULT_PATH_NAME + "outputGeneMoreList.txt";
		GeneMoreListe liste = new GeneMoreListe(path2file, false);

		BiochemicalReaction BR000 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				169, 1, 150, 1, 151, 1, 171, 1, 1);
		BR000.setName("BR000");
		BiochemicalReaction BR001 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				170, 1, 150, 1, 151, 1, 172, 1, 1);
		BR001.setName("BR001");
		BiochemicalReaction BR002 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				171, 1, 0, 0, 172, 1, 0, 0, 1);
		BR002.setName("BR002");
		BiochemicalReaction BR003 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				172, 1, 0, 0, 171, 1, 0, 0, 1);
		BR003.setName("BR003");
		BiochemicalReaction BR004 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				172, 1, 150, 1, 173, 1, 151, 1, 1);
		BR004.setName("BR004");
		BiochemicalReaction BR005 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				173, 1, 0, 0, 174, 1, 175, 1, 1);
		BR005.setName("BR005");
		BiochemicalReaction BR006 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				174, 1, 175, 1, 173, 1, 0, 0, 1);
		BR006.setName("BR006");
		BiochemicalReaction BR007 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				174, 1, 0, 0, 175, 1, 0, 0, 1);
		BR007.setName("BR007");
		BiochemicalReaction BR008 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				175, 1, 0, 0, 174, 1, 0, 0, 1);
		BR008.setName("BR008");
		BiochemicalReaction BR009 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				175, 1, 165, 1, 176, 1, 166, 1, 2);
		BR009.setName("BR009");
		BiochemicalReaction BR010 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				176, 1, 166, 1, 175, 1, 165, 1, 1);
		BR010.setName("BR010");
		BiochemicalReaction BR011 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				176, 1, 151, 1, 177, 1, 150, 1, 2);
		BR011.setName("BR011");
		BiochemicalReaction BR012 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				177, 1, 150, 1, 176, 1, 151, 1, 1);
		BR012.setName("BR012");
		BiochemicalReaction BR013 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				177, 1, 0, 0, 178, 1, 0, 0, 2);
		BR013.setName("BR013");
		BiochemicalReaction BR014 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				178, 1, 0, 0, 177, 1, 0, 0, 1);
		BR014.setName("BR014");
		BiochemicalReaction BR015 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				178, 1, 0, 0, 179, 1, 182, 1, 2);
		BR015.setName("BR015");
		BiochemicalReaction BR016 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				179, 1, 182, 1, 178, 1, 0, 0, 1);
		BR016.setName("BR016");
		BiochemicalReaction BR017 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				179, 1, 151, 1, 198, 1, 150, 1, 2);
		BR017.setName("BR017");
		BiochemicalReaction BR018 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				198, 1, 187, 1, 181, 1, 184, 1, 2);
		BR018.setName("BR018");
		BiochemicalReaction BR019 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				181, 1, 165, 1, 181, 1, 166, 1, 2);
		BR019.setName("BR019");
		BiochemicalReaction BR020 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				278, 1, 240, 1, 279, 1, 181, 1, 1);
		BR020.setName("BR020");
		BiochemicalReaction BR021 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				279, 1, 150, 1, 319, 1, 152, 1, 1);
		BR021.setName("BR021");
		BiochemicalReaction BR022 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 187, 2, 319, 1, 15, 2, 1);
		BR022.setName("BR022");
		BiochemicalReaction BR023 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 167, 1, 319, 1, 168, 1, 1);
		BR023.setName("BR023");
		BiochemicalReaction BR024 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 165, 1, 319, 1, 166, 1, 1);
		BR024.setName("BR024");
		BiochemicalReaction BR025 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				319, 1, 182, 1, 317, 1, 184, 1, 1);
		BR025.setName("BR025");
		BiochemicalReaction BR026 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				276, 1, 240, 1, 277, 1, 181, 1, 1);
		BR026.setName("BR026");
		BiochemicalReaction BR027 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				277, 1, 150, 1, 317, 1, 152, 1, 1);
		BR027.setName("BR027");
		BiochemicalReaction BR028 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 187, 2, 317, 1, 15, 2, 1);
		BR028.setName("BR028");
		BiochemicalReaction BR029 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 167, 1, 317, 1, 168, 1, 1);
		BR029.setName("BR029");
		BiochemicalReaction BR030 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 165, 1, 317, 1, 166, 1, 1);
		BR030.setName("BR030");
		BiochemicalReaction BR031 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				317, 1, 182, 1, 315, 1, 184, 1, 1);
		BR031.setName("BR031");
		BiochemicalReaction BR032 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				274, 1, 240, 1, 275, 1, 181, 1, 1);
		BR032.setName("BR032");
		BiochemicalReaction BR033 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				275, 1, 150, 1, 315, 1, 152, 1, 1);
		BR033.setName("BR033");
		BiochemicalReaction BR034 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 187, 2, 315, 1, 15, 2, 1);
		BR034.setName("BR034");
		BiochemicalReaction BR035 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 167, 1, 315, 1, 168, 1, 1);
		BR035.setName("BR035");
		BiochemicalReaction BR036 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 165, 1, 315, 1, 166, 1, 1);
		BR036.setName("BR036");
		BiochemicalReaction BR037 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				315, 1, 182, 1, 313, 1, 184, 1, 1);
		BR037.setName("BR037");
		BiochemicalReaction BR038 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				272, 1, 240, 1, 273, 1, 181, 1, 1);
		BR038.setName("BR038");
		BiochemicalReaction BR039 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				273, 1, 150, 1, 313, 1, 152, 1, 1);
		BR039.setName("BR039");
		BiochemicalReaction BR040 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 187, 2, 313, 1, 15, 2, 1);
		BR040.setName("BR040");
		BiochemicalReaction BR041 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 167, 1, 313, 1, 168, 1, 1);
		BR041.setName("BR041");
		BiochemicalReaction BR042 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 165, 1, 313, 1, 166, 1, 1);
		BR042.setName("BR042");
		BiochemicalReaction BR043 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				313, 1, 182, 1, 311, 1, 184, 1, 1);
		BR043.setName("BR043");
		BiochemicalReaction BR044 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				270, 1, 240, 1, 271, 1, 181, 1, 1);
		BR044.setName("BR044");
		BiochemicalReaction BR045 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				271, 1, 150, 1, 311, 1, 152, 1, 1);
		BR045.setName("BR045");
		BiochemicalReaction BR046 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 187, 2, 311, 1, 15, 2, 1);
		BR046.setName("BR046");
		BiochemicalReaction BR047 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 167, 1, 311, 1, 168, 1, 1);
		BR047.setName("BR047");
		BiochemicalReaction BR048 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 165, 1, 311, 1, 166, 1, 1);
		BR048.setName("BR048");
		BiochemicalReaction BR049 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				311, 1, 182, 1, 309, 1, 184, 1, 1);
		BR049.setName("BR049");
		BiochemicalReaction BR050 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				268, 1, 240, 1, 269, 1, 181, 1, 1);
		BR050.setName("BR050");
		BiochemicalReaction BR051 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				269, 1, 150, 1, 309, 1, 152, 1, 1);
		BR051.setName("BR051");
		BiochemicalReaction BR052 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 187, 2, 309, 1, 15, 2, 1);
		BR052.setName("BR052");
		BiochemicalReaction BR053 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 167, 1, 309, 1, 168, 1, 1);
		BR053.setName("BR053");
		BiochemicalReaction BR054 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 165, 1, 309, 1, 166, 1, 1);
		BR054.setName("BR054");
		BiochemicalReaction BR055 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				309, 1, 182, 1, 307, 1, 184, 1, 1);
		BR055.setName("BR055");
		BiochemicalReaction BR056 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				266, 1, 240, 1, 267, 1, 181, 1, 1);
		BR056.setName("BR056");
		BiochemicalReaction BR057 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				267, 1, 150, 1, 307, 1, 152, 1, 1);
		BR057.setName("BR057");
		BiochemicalReaction BR058 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 187, 2, 307, 1, 15, 2, 1);
		BR058.setName("BR058");
		BiochemicalReaction BR059 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 167, 1, 307, 1, 168, 1, 1);
		BR059.setName("BR059");
		BiochemicalReaction BR060 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 165, 1, 307, 1, 166, 1, 1);
		BR060.setName("BR060");
		BiochemicalReaction BR061 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				307, 1, 182, 1, 305, 1, 184, 1, 1);
		BR061.setName("BR061");
		BiochemicalReaction BR062 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				264, 1, 240, 1, 265, 1, 181, 1, 1);
		BR062.setName("BR062");
		BiochemicalReaction BR063 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				265, 1, 150, 1, 305, 1, 152, 1, 1);
		BR063.setName("BR063");
		BiochemicalReaction BR064 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 187, 2, 305, 1, 15, 2, 1);
		BR064.setName("BR064");
		BiochemicalReaction BR065 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 167, 1, 305, 1, 168, 1, 1);
		BR065.setName("BR065");
		BiochemicalReaction BR066 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 165, 1, 305, 1, 166, 1, 1);
		BR066.setName("BR066");
		BiochemicalReaction BR067 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				305, 1, 182, 1, 303, 1, 184, 1, 1);
		BR067.setName("BR067");
		BiochemicalReaction BR068 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				262, 1, 240, 1, 263, 1, 181, 1, 1);
		BR068.setName("BR068");
		BiochemicalReaction BR069 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				263, 1, 150, 1, 303, 1, 152, 1, 1);
		BR069.setName("BR069");
		BiochemicalReaction BR070 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 187, 2, 303, 1, 15, 2, 1);
		BR070.setName("BR070");
		BiochemicalReaction BR071 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 167, 1, 303, 1, 168, 1, 1);
		BR071.setName("BR071");
		BiochemicalReaction BR072 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 165, 1, 303, 1, 166, 1, 1);
		BR072.setName("BR072");
		BiochemicalReaction BR073 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				303, 1, 182, 1, 301, 1, 184, 1, 1);
		BR073.setName("BR073");
		BiochemicalReaction BR074 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				260, 1, 240, 1, 261, 1, 181, 1, 1);
		BR074.setName("BR074");
		BiochemicalReaction BR075 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				261, 1, 150, 1, 301, 1, 152, 1, 1);
		BR075.setName("BR075");
		BiochemicalReaction BR076 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 187, 2, 301, 1, 15, 2, 1);
		BR076.setName("BR076");
		BiochemicalReaction BR077 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 167, 1, 301, 1, 168, 1, 1);
		BR077.setName("BR077");
		BiochemicalReaction BR078 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 165, 1, 301, 1, 166, 1, 1);
		BR078.setName("BR078");
		BiochemicalReaction BR079 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				301, 1, 182, 1, 299, 1, 184, 1, 1);
		BR079.setName("BR079");
		BiochemicalReaction BR080 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				258, 1, 240, 1, 259, 1, 181, 1, 1);
		BR080.setName("BR080");
		BiochemicalReaction BR081 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				259, 1, 150, 1, 299, 1, 152, 1, 1);
		BR081.setName("BR081");
		BiochemicalReaction BR082 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 187, 2, 299, 1, 15, 2, 1);
		BR082.setName("BR082");
		BiochemicalReaction BR083 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 167, 1, 299, 1, 168, 1, 1);
		BR083.setName("BR083");
		BiochemicalReaction BR084 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 165, 1, 299, 1, 166, 1, 1);
		BR084.setName("BR084");
		BiochemicalReaction BR085 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				299, 1, 182, 1, 297, 1, 184, 1, 1);
		BR085.setName("BR085");
		BiochemicalReaction BR086 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				256, 1, 240, 1, 257, 1, 181, 1, 1);
		BR086.setName("BR086");
		BiochemicalReaction BR087 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				257, 1, 150, 1, 297, 1, 152, 1, 1);
		BR087.setName("BR087");
		BiochemicalReaction BR088 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 187, 2, 297, 1, 15, 2, 1);
		BR088.setName("BR088");
		BiochemicalReaction BR089 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 167, 1, 297, 1, 168, 1, 1);
		BR089.setName("BR089");
		BiochemicalReaction BR090 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 165, 1, 297, 1, 166, 1, 1);
		BR090.setName("BR090");
		BiochemicalReaction BR091 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				297, 1, 182, 1, 295, 1, 184, 1, 1);
		BR091.setName("BR091");
		BiochemicalReaction BR092 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				254, 1, 240, 1, 255, 1, 181, 1, 1);
		BR092.setName("BR092");
		BiochemicalReaction BR093 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				255, 1, 150, 1, 295, 1, 152, 1, 1);
		BR093.setName("BR093");
		BiochemicalReaction BR094 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 187, 2, 295, 1, 15, 2, 1);
		BR094.setName("BR094");
		BiochemicalReaction BR095 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 167, 1, 295, 1, 168, 1, 1);
		BR095.setName("BR095");
		BiochemicalReaction BR096 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 165, 1, 295, 1, 166, 1, 1);
		BR096.setName("BR096");
		BiochemicalReaction BR097 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				295, 1, 182, 1, 293, 1, 184, 1, 1);
		BR097.setName("BR097");
		BiochemicalReaction BR098 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				252, 1, 240, 1, 253, 1, 181, 1, 1);
		BR098.setName("BR098");
		BiochemicalReaction BR099 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				253, 1, 150, 1, 293, 1, 152, 1, 1);
		BR099.setName("BR099");
		BiochemicalReaction BR100 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 187, 2, 293, 1, 15, 2, 1);
		BR100.setName("BR100");
		BiochemicalReaction BR101 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 167, 1, 293, 1, 168, 1, 1);
		BR101.setName("BR101");
		BiochemicalReaction BR102 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 165, 1, 293, 1, 166, 1, 1);
		BR102.setName("BR102");
		BiochemicalReaction BR103 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				293, 1, 182, 1, 291, 1, 184, 1, 1);
		BR103.setName("BR103");
		BiochemicalReaction BR104 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				250, 1, 240, 1, 251, 1, 181, 1, 1);
		BR104.setName("BR104");
		BiochemicalReaction BR105 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				251, 1, 150, 1, 291, 1, 152, 1, 1);
		BR105.setName("BR105");
		BiochemicalReaction BR106 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 187, 2, 291, 1, 15, 2, 1);
		BR106.setName("BR106");
		BiochemicalReaction BR107 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 167, 1, 291, 1, 168, 1, 1);
		BR107.setName("BR107");
		BiochemicalReaction BR108 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 165, 1, 291, 1, 166, 1, 1);
		BR108.setName("BR108");
		BiochemicalReaction BR109 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				291, 1, 182, 1, 289, 1, 184, 1, 1);
		BR109.setName("BR109");
		BiochemicalReaction BR110 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				248, 1, 240, 1, 249, 1, 181, 1, 1);
		BR110.setName("BR110");
		BiochemicalReaction BR111 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				249, 1, 150, 1, 289, 1, 152, 1, 1);
		BR111.setName("BR111");
		BiochemicalReaction BR112 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 187, 2, 289, 1, 15, 2, 1);
		BR112.setName("BR112");
		BiochemicalReaction BR113 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 167, 1, 289, 1, 168, 1, 1);
		BR113.setName("BR113");
		BiochemicalReaction BR114 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 165, 1, 289, 1, 166, 1, 1);
		BR114.setName("BR114");
		BiochemicalReaction BR115 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				289, 1, 182, 1, 287, 1, 184, 1, 1);
		BR115.setName("BR115");
		BiochemicalReaction BR116 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				246, 1, 240, 1, 247, 1, 181, 1, 1);
		BR116.setName("BR116");
		BiochemicalReaction BR117 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				247, 1, 150, 1, 287, 1, 152, 1, 1);
		BR117.setName("BR117");
		BiochemicalReaction BR118 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 187, 2, 287, 1, 15, 2, 1);
		BR118.setName("BR118");
		BiochemicalReaction BR119 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 167, 1, 287, 1, 168, 1, 1);
		BR119.setName("BR119");
		BiochemicalReaction BR120 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 165, 1, 287, 1, 166, 1, 1);
		BR120.setName("BR120");
		BiochemicalReaction BR121 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				287, 1, 182, 1, 285, 1, 184, 1, 1);
		BR121.setName("BR121");
		BiochemicalReaction BR122 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				244, 1, 240, 1, 245, 1, 181, 1, 1);
		BR122.setName("BR122");
		BiochemicalReaction BR123 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				245, 1, 150, 1, 285, 1, 152, 1, 1);
		BR123.setName("BR123");
		BiochemicalReaction BR124 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 187, 2, 285, 1, 15, 2, 1);
		BR124.setName("BR124");
		BiochemicalReaction BR125 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 167, 1, 285, 1, 168, 1, 1);
		BR125.setName("BR125");
		BiochemicalReaction BR126 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 165, 1, 285, 1, 166, 1, 1);
		BR126.setName("BR126");
		BiochemicalReaction BR127 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				285, 1, 182, 1, 285, 1, 184, 1, 1);
		BR127.setName("BR127");
		BiochemicalReaction BR128 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				242, 1, 240, 1, 243, 1, 181, 1, 1);
		BR128.setName("BR128");
		BiochemicalReaction BR129 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				243, 1, 150, 1, 283, 1, 152, 1, 1);
		BR129.setName("BR129");
		BiochemicalReaction BR130 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 187, 2, 283, 1, 15, 2, 1);
		BR130.setName("BR130");
		BiochemicalReaction BR131 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 167, 1, 283, 1, 168, 1, 1);
		BR131.setName("BR131");
		BiochemicalReaction BR132 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 165, 1, 283, 1, 166, 1, 1);
		BR132.setName("BR132");
		BiochemicalReaction BR133 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				283, 1, 182, 1, 281, 1, 184, 1, 1);
		BR133.setName("BR133");
		BiochemicalReaction BR134 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				240, 1, 240, 1, 241, 1, 181, 1, 1);
		BR134.setName("BR134");
		BiochemicalReaction BR135 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				241, 1, 150, 1, 281, 1, 152, 1, 1);
		BR135.setName("BR135");
		BiochemicalReaction BR136 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 187, 2, 281, 1, 15, 2, 1);
		BR136.setName("BR136");
		BiochemicalReaction BR137 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 167, 1, 281, 1, 168, 1, 1);
		BR137.setName("BR137");
		BiochemicalReaction BR138 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 165, 1, 281, 1, 166, 1, 1);
		BR138.setName("BR138");
		BiochemicalReaction BR139 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				281, 1, 182, 1, 184, 1, 184, 1, 1);
		BR139.setName("BR139");
		BiochemicalReaction BR140 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				15, 2, 182, 1, 15, 2, 0, 0, 40);
		BR140.setName("BR140");
		BiochemicalReaction BR141 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				184, 1, 185, 1, 186, 1, 187, 1, 1);
		BR141.setName("BR141");
		BiochemicalReaction BR142 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				186, 1, 0, 0, 188, 1, 0, 0, 2);
		BR142.setName("BR142");
		BiochemicalReaction BR143 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				188, 1, 0, 0, 186, 1, 0, 0, 1);
		BR143.setName("BR143");
		BiochemicalReaction BR144 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				188, 1, 182, 1, 189, 1, 0, 0, 2);
		BR144.setName("BR144");
		BiochemicalReaction BR145 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				189, 1, 0, 0, 188, 1, 182, 1, 1);
		BR145.setName("BR145");
		BiochemicalReaction BR146 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				189, 1, 165, 1, 190, 1, 166, 1, 2);
		BR146.setName("BR146");
		BiochemicalReaction BR147 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				190, 1, 166, 1, 189, 1, 165, 1, 1);
		BR147.setName("BR147");
		BiochemicalReaction BR148 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				190, 1, 0, 0, 191, 1, 181, 2, 1);
		BR148.setName("BR148");
		BiochemicalReaction BR149 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				191, 1, 165, 1, 192, 1, 166, 1, 1);
		BR149.setName("BR149");
		BiochemicalReaction BR150 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				192, 1, 157, 1, 193, 1, 156, 1, 1);
		BR150.setName("BR150");
		BiochemicalReaction BR151 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				193, 1, 167, 1, 194, 1, 168, 1, 1);
		BR151.setName("BR151");
		BiochemicalReaction BR152 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				194, 1, 182, 2, 195, 1, 0, 0, 1);
		BR152.setName("BR152");
		BiochemicalReaction BR153 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				195, 1, 165, 1, 185, 1, 166, 1, 1);
		BR153.setName("BR153");
		BiochemicalReaction BR154 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				166, 1, 180, 1, 165, 1, 183, 2, 1);
		BR154.setName("BR154");
		BiochemicalReaction BR155 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				168, 1, 180, 1, 167, 1, 183, 2, 1);
		BR155.setName("BR155");
		BiochemicalReaction BR156 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				183, 1, 15, 1, 183, 1, 182, 1, 1);
		BR156.setName("BR156");
		BiochemicalReaction BR157 = new BiochemicalReaction(
				false,true, false, true, 0, 9, 5, 25,
				151, 1, 183, 1, 150, 1, 182, 1, 1);
		BR157.setName("BR157");
		liste.addGene(BR000);
		liste.addGene(BR001);
		liste.addGene(BR002);
		liste.addGene(BR003);
		liste.addGene(BR004);
		liste.addGene(BR005);
		liste.addGene(BR006);
		liste.addGene(BR007);
		liste.addGene(BR008);
		liste.addGene(BR009);
		liste.addGene(BR010);
		liste.addGene(BR011);
		liste.addGene(BR012);
		liste.addGene(BR013);
		liste.addGene(BR014);
		liste.addGene(BR015);
		liste.addGene(BR016);
		liste.addGene(BR017);
		liste.addGene(BR018);
		liste.addGene(BR019);
		liste.addGene(BR020);
		liste.addGene(BR021);
		liste.addGene(BR022);
		liste.addGene(BR023);
		liste.addGene(BR024);
		liste.addGene(BR025);
		liste.addGene(BR026);
		liste.addGene(BR027);
		liste.addGene(BR028);
		liste.addGene(BR029);
		liste.addGene(BR030);
		liste.addGene(BR031);
		liste.addGene(BR032);
		liste.addGene(BR033);
		liste.addGene(BR034);
		liste.addGene(BR035);
		liste.addGene(BR036);
		liste.addGene(BR037);
		liste.addGene(BR038);
		liste.addGene(BR039);
		liste.addGene(BR040);
		liste.addGene(BR041);
		liste.addGene(BR042);
		liste.addGene(BR043);
		liste.addGene(BR044);
		liste.addGene(BR045);
		liste.addGene(BR046);
		liste.addGene(BR047);
		liste.addGene(BR048);
		liste.addGene(BR049);
		liste.addGene(BR050);
		liste.addGene(BR051);
		liste.addGene(BR052);
		liste.addGene(BR053);
		liste.addGene(BR054);
		liste.addGene(BR055);
		liste.addGene(BR056);
		liste.addGene(BR057);
		liste.addGene(BR058);
		liste.addGene(BR059);
		liste.addGene(BR060);
		liste.addGene(BR061);
		liste.addGene(BR062);
		liste.addGene(BR063);
		liste.addGene(BR064);
		liste.addGene(BR065);
		liste.addGene(BR066);
		liste.addGene(BR067);
		liste.addGene(BR068);
		liste.addGene(BR069);
		liste.addGene(BR070);
		liste.addGene(BR071);
		liste.addGene(BR072);
		liste.addGene(BR073);
		liste.addGene(BR074);
		liste.addGene(BR075);
		liste.addGene(BR076);
		liste.addGene(BR077);
		liste.addGene(BR078);
		liste.addGene(BR079);
		liste.addGene(BR080);
		liste.addGene(BR081);
		liste.addGene(BR082);
		liste.addGene(BR083);
		liste.addGene(BR084);
		liste.addGene(BR085);
		liste.addGene(BR086);
		liste.addGene(BR087);
		liste.addGene(BR088);
		liste.addGene(BR089);
		liste.addGene(BR090);
		liste.addGene(BR091);
		liste.addGene(BR092);
		liste.addGene(BR093);
		liste.addGene(BR094);
		liste.addGene(BR095);
		liste.addGene(BR096);
		liste.addGene(BR097);
		liste.addGene(BR098);
		liste.addGene(BR099);
		liste.addGene(BR100);
		liste.addGene(BR101);
		liste.addGene(BR102);
		liste.addGene(BR103);
		liste.addGene(BR104);
		liste.addGene(BR105);
		liste.addGene(BR106);
		liste.addGene(BR107);
		liste.addGene(BR108);
		liste.addGene(BR109);
		liste.addGene(BR110);
		liste.addGene(BR111);
		liste.addGene(BR112);
		liste.addGene(BR113);
		liste.addGene(BR114);
		liste.addGene(BR115);
		liste.addGene(BR116);
		liste.addGene(BR117);
		liste.addGene(BR118);
		liste.addGene(BR119);
		liste.addGene(BR120);
		liste.addGene(BR121);
		liste.addGene(BR122);
		liste.addGene(BR123);
		liste.addGene(BR124);
		liste.addGene(BR125);
		liste.addGene(BR126);
		liste.addGene(BR127);
		liste.addGene(BR128);
		liste.addGene(BR129);
		liste.addGene(BR130);
		liste.addGene(BR131);
		liste.addGene(BR132);
		liste.addGene(BR133);
		liste.addGene(BR134);
		liste.addGene(BR135);
		liste.addGene(BR136);
		liste.addGene(BR137);
		liste.addGene(BR138);
		liste.addGene(BR139);
		liste.addGene(BR140);
		liste.addGene(BR141);
		liste.addGene(BR142);
		liste.addGene(BR143);
		liste.addGene(BR144);
		liste.addGene(BR145);
		liste.addGene(BR146);
		liste.addGene(BR147);
		liste.addGene(BR148);
		liste.addGene(BR149);
		liste.addGene(BR150);
		liste.addGene(BR151);
		liste.addGene(BR152);
		liste.addGene(BR153);
		liste.addGene(BR154);
		liste.addGene(BR155);
		liste.addGene(BR156);
		liste.addGene(BR157);

		liste.printFile();
		GeneMoreListeTests.testFileExists(path2file);
		
		liste.deleteFile();
	}
	
	// TODO complete these tests !! GeneMoreListTests

//	@Test
//	void testGetGenesNames() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetLastGeneName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetType() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveGene() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddLineOfGeneMoreListeFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetGeneString1() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testReadFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPrintFile() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddToChamps() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetChamps() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveChamps() {
//		fail("Not yet implemented");
//	}

}

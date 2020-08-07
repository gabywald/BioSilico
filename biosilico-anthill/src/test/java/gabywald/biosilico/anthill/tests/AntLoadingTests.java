package gabywald.biosilico.anthill.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chemicals;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.global.data.File;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class AntLoadingTests {
	
	private static List<Chromosome> loadingAntGenome() {
		
		File antGeneticData = new File("anthill/baseGenomeAnt.txt");
		Assertions.assertNotNull( antGeneticData );
		Assertions.assertEquals(0, antGeneticData.lengthFile());
		try {
			antGeneticData.load();
			Assertions.assertTrue( antGeneticData.lengthFile() > 0);
		} catch (IOException e) {
			e.printStackTrace();
			Assertions.fail( e.getMessage() );
		}
		
		Chromosome chr			= new Chromosome();
		
		List<String> data2use	= Arrays.asList( antGeneticData.getChampsToString().split("\n") );
		Gene currentGene				= null;
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb	= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdb		= new StimulusDecisionBuilder();
		EmitterReceptorBuilder erb		= new EmitterReceptorBuilder();
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		for (String line : data2use) {
			if ( ! line.startsWith("// ") ) { 
				Logger.printlnLog(LoggerLevel.LL_DEBUG, line );
				
				String datas[] = line.split( "\t" );
				
				// System.out.println("\t" + datas[0] + " :: " + datas[1]);
				
				switch(datas.length) {
				case (11) : 
					// ***** InitialConcentration
					currentGene = icb	.varia(Integer.parseInt(datas[ 9])).value(Integer.parseInt(datas[10]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				case (18) : 
					// ***** BiochemicalReaction
					currentGene = brb	.achem(Integer.parseInt(datas[ 9])).acoef(Integer.parseInt(datas[10]))
										.bchem(Integer.parseInt(datas[11])).bcoef(Integer.parseInt(datas[12]))
										.cchem(Integer.parseInt(datas[13])).ccoef(Integer.parseInt(datas[14]))
										.dchem(Integer.parseInt(datas[15])).dcoef(Integer.parseInt(datas[16]))
										 .kmvm(Integer.parseInt(datas[17]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				case (17) : 
					// ***** StimulusDecision
					currentGene = sdb	.perception(Boolean.parseBoolean(datas[ 9])).object(Boolean.parseBoolean(datas[10]))
										.indicator(Integer.parseInt(datas[11])).threshold(Integer.parseInt(datas[12]))
										.attribute(Integer.parseInt(datas[13])).varia(Integer.parseInt(datas[14]))
										.value(Integer.parseInt(datas[15])).script(Integer.parseInt(datas[16]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				case (16) : 
					// ***** EmitterReceptor
					currentGene = erb	.variable(Integer.parseInt(datas[ 9])).threshold(Integer.parseInt(datas[10])).ioput(Integer.parseInt(datas[11]))
										.posx(Integer.parseInt(datas[12])).posy(Integer.parseInt(datas[13]))
										.receptor(Boolean.parseBoolean(datas[14])).internal(Boolean.parseBoolean(datas[15]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				case (13) : 
					// ***** BrainGene
					currentGene = bgb	.heigth(Integer.parseInt(datas[ 9])).width(Integer.parseInt(datas[10])).depth(Integer.parseInt(datas[11])).more(Integer.parseInt(datas[12]))	
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				case (23) : 
					// ***** BrainLobeGene
					currentGene = blgb	.rest(Integer.parseInt(datas[ 9])).threshold(Integer.parseInt(datas[10])).desc(Integer.parseInt(datas[11]))
										.dmin(Integer.parseInt(datas[12])).dmax(Integer.parseInt(datas[13])).prox(Integer.parseInt(datas[14]))
										.repr(Boolean.parseBoolean(datas[15])).repy(Integer.parseInt(datas[16])).wta(Boolean.parseBoolean(datas[17]))
										.heigth(Integer.parseInt(datas[18])).width(Integer.parseInt(datas[19])).posx(Integer.parseInt(datas[20])).posy(Integer.parseInt(datas[21]))
										.replace(Boolean.parseBoolean(datas[22]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				default:
					String message = "Unknown length (" + datas.length + ")";
					Logger.printlnLog(LoggerLevel.LL_ERROR, message);
					Assertions.fail(message);
				} // END "switch(datas.length)"
				
				if (currentGene != null) {
					currentGene.setName(datas[ 0]);
					// System.out.println( "\t" + currentGene );
					chr.addGene(currentGene);
					currentGene = null;
				}
			}
		}
		
		return Arrays.asList(chr);
	}

	@Test
	void testLoadingAnt01() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntLoadingTests.loadingAntGenome() );
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("=", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("=", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		IntStream.range(0, Chemicals.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("*", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("*", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals( 95, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		Assertions.assertEquals(  5, wc.getVariables().getVariable(181));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(182));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		Assertions.assertEquals( 10, wc.getVariables().getVariable(181));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(182));

	}
	
	@Test
	void testLoadingAnt02() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntLoadingTests.loadingAntGenome() );
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("=", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("=", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		IntStream.range(0, Chemicals.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** Put DiOxygen in local WorldCase !!
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("*", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("*", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals( 95, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		Assertions.assertEquals( 80, wc.getVariables().getVariable(180));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(181));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(182));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(140, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		Assertions.assertEquals( 60, wc.getVariables().getVariable(180));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(181));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(182));

	}
	
	@Test
	void testLoadingAnt03() {
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		testAnt.setNameBiosilico("AntHill Ant Example");
		testAnt.setDivision("TESTS");
		
		testAnt.setGenome( AntLoadingTests.loadingAntGenome() );
		
		// ***** test with a World and WorldCase
		
		World w			= new World(1, 1);
		WorldCase wc	= w.getWorldCase(0,  0);
		Assertions.assertNotNull( wc );
		
		testAnt.setCurrentWorldCase( wc );
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("=", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("=", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		IntStream.range(0, Chemicals.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** Put DiOxygen in local WorldCase !!
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("*", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("*", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals( 95, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		Assertions.assertEquals( 80, wc.getVariables().getVariable(180));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(181));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(182));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		System.out.println( testAnt.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		System.out.println( wc.toString() );
		System.out.println( StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(140, testAnt.getVariables().getVariable(180));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(181));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(182));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(940));
		Assertions.assertEquals(938, testAnt.getVariables().getVariable(941));
		
		Assertions.assertEquals( 60, wc.getVariables().getVariable(180));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(181));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(182));

	}

}

package gabywald.biosilico.anthill.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.AntReceptionChemicals;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.EnergySource;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.ObjectType;
import gabywald.biosilico.model.enums.SomeChemicals;
import gabywald.biosilico.model.enums.StateType;
import gabywald.global.data.File;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 * TODO review and replace "Logger.printlnLog(LoggerLevel.LL_INFO, " with "Logger.printlnLog(LoggerLevel.LL_INFO, "
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
		
		List<Chromosome> lstCHR	= new ArrayList<Chromosome>();
		Chromosome chr			= null; // new Chromosome();
		
		List<String> data2use	= Arrays.asList( antGeneticData.getChampsToString().split("\n") );
		Gene currentGene				= null;
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb	= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdb		= new StimulusDecisionBuilder();
		InstinctBuilder igb				= new InstinctBuilder();
		EmitterReceptorBuilder erb		= new EmitterReceptorBuilder();
		BrainGeneBuilder bgb			= new BrainGeneBuilder();
		BrainLobeGeneBuilder blgb		= new BrainLobeGeneBuilder();
		for (String line : data2use) {
			if ( line.isEmpty() ) { continue; }
			if ( line.startsWith("// ") ) { continue; }
			if ( line.startsWith("## ") ) {
				chr = new Chromosome();
				chr.setName(line.split("## ")[1]);
				lstCHR.add(chr);
				continue;
			} else { 
				// ***** in case of a chromosome is not defined !
				if (chr == null) {
					chr = new Chromosome();
					lstCHR.add(chr);
				}
			}
			Logger.printlnLog(LoggerLevel.LL_DEBUG, line );
			
			String datas[] = line.split( "\t" );
			
			// Logger.printlnLog(LoggerLevel.LL_INFO, "\t" + datas[0] + " :: " + datas[1]);
			
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
				// ***** StimulusDecision || Instinct 
				if ( (datas[ 9].equals( "true" ) || (datas[ 9]).equals( "false" )) && (datas[10].equals( "true" ) || (datas[10]).equals( "false" )) ) {
					currentGene = sdb	.perception(Boolean.parseBoolean(datas[ 9])).object(Boolean.parseBoolean(datas[10]))
										.indicator(Integer.parseInt(datas[11])).threshold(Integer.parseInt(datas[12]))
										.attribute(Integer.parseInt(datas[13])).varia(Integer.parseInt(datas[14]))
										.value(Integer.parseInt(datas[15])).script(Integer.parseInt(datas[16]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
				} else {
					currentGene = igb	.inputPosX(Integer.parseInt(datas[ 9])).inputPosY(Integer.parseInt(datas[10]))
										.outputPosX(Integer.parseInt(datas[11])).outputPosY(Integer.parseInt(datas[12]))
										.weight(Integer.parseInt(datas[13])).variable(Integer.parseInt(datas[14]))
										.threshold(Integer.parseInt(datas[15])).check(Boolean.parseBoolean(datas[16]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
				}
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
				String message = "Unknown length (" + datas.length + ") {" + line + "}" ;
				Logger.printlnLog(LoggerLevel.LL_ERROR, message);
				Assertions.fail(message);
			} // END "switch(datas.length)"
			
			if (currentGene != null) {
				currentGene.setName(datas[ 0]);
				chr.addGene(currentGene);
				currentGene = null;
			}
		}
		
		return lstCHR;
	}

	/**
	 * Simply test in a Unique WorldCase !
	 */
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 95, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(100, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 90, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen !
	 */
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** Put DiOxygen in local WorldCase !!
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 100);
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 95, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(140, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 90, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));		
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water !
	 */
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getVariables().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		
		// ***** one execution in this context -- 1
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 95, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 2
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(140, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 90, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 3
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(160, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 35, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 40, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 85, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 4
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(180, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 20, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		
		// ***** one execution in this context -- 5
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(200, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 75, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 */
	@Test
	void testLoadingAnt04() {
		
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getVariables().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 95, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 2
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(140, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 60, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 90, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 3
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(160, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 35, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 40, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 85, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 4
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(180, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals( 20, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		
		// ***** one execution in this context -- 5
		testAnt.execution( wc );
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(200, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 75, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));

	}
	
	/**
	 * Simply test in a Unique WorldCase With DiOxygen and Water and EnergySource !
	 * <br/> Running World !!
	 */
	@Test
	void testLoadingAnt05() {
		
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
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		IntStream.range(0, ChemicalsHelper.CHEMICAL_LENGTH).forEach( k -> {
			Assertions.assertEquals( 0, wc.getVariables().getVariable(k) );
		});
		
		// ***** Put DiOxygen && H2O in local WorldCase !!
		wc.getVariables().setVariable(SomeChemicals.DIOXYGEN.getIndex(), 	100);
		wc.getVariables().setVariable(SomeChemicals.WATER.getIndex(), 		100);
		EnergySource es = new EnergySource();
		wc.addAgent( es );
		
		// ***** one execution in this context -- 1
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 45, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(105, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  1, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals(  5, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 95, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 25, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 25, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 2
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(140, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 40, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(110, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  2, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals( 60, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 10, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 90, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 50, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 50, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 3
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(160, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 35, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(115, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  3, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals( 40, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 85, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 75, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals( 75, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 4
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(180, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(120, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  4, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals( 20, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 80, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(100, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(100, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** one execution in this context -- 5
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(200, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(125, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  5, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertFalse( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 25, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 75, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(125, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(125, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Put some pheromones on local (00 : ch650)
		wc.getChemicals().setVarPlus(SomeChemicals.PHEROMONE_00.getIndex(), 50);
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(200, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(130, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  6, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 30, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 70, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(150, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(150, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 50, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));
		
		// ***** Loading Chemicals HalfLives in World !!
		w.loadHalLives();
		
		// ***** MORE executionS in this context -- 6
		w.execution();
		testAnt.cyclePlusPlus(); // Aging organism
		
		Logger.printlnLog(LoggerLevel.LL_INFO, testAnt.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, es.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		Logger.printlnLog(LoggerLevel.LL_INFO, wc.toString() );
		Logger.printlnLog(LoggerLevel.LL_INFO, StringUtils.repeat("+", 80) );
		
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(15));
		Assertions.assertEquals( 10, testAnt.getVariables().getVariable(150));
		Assertions.assertEquals( 20, testAnt.getVariables().getVariable(151));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(165));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(166));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(167));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(168));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(169));
		Assertions.assertEquals( 25, testAnt.getVariables().getVariable(170));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(171));
		Assertions.assertEquals(  0, testAnt.getVariables().getVariable(172));
		Assertions.assertEquals(200, testAnt.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals(135, testAnt.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals( 30, testAnt.getVariables().getVariable(SomeChemicals.HYDROXYD.getIndex()));
		Assertions.assertEquals( 15, testAnt.getVariables().getVariable(SomeChemicals.ACETYLCOA.getIndex()));
		Assertions.assertEquals(  7, testAnt.getVariables().getVariable(StateType.AGING.getIndex()));
		Assertions.assertEquals(ObjectType.AGENT.getIndex(), testAnt.getVariables().getVariable(StateType.TYPEOF.getIndex()));
		Assertions.assertEquals(  5, testAnt.getVariables().getVariable(AntReceptionChemicals.PHEROMONE_00_CURRENT.getIndex()));
		
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt( 0, 39).ckActivated() );
		Assertions.assertTrue( testAnt.getBrain().getNeuronAt(48, 39).ckActivated() );
		
		Assertions.assertEquals(  0, wc.getVariables().getVariable(SomeChemicals.DIOXYGEN.getIndex()));
		Assertions.assertEquals( 35, wc.getVariables().getVariable(SomeChemicals.CARBON_DIOXYDE.getIndex()));
		Assertions.assertEquals( 65, wc.getVariables().getVariable(SomeChemicals.WATER.getIndex()));
		Assertions.assertEquals(155, wc.getVariables().getVariable(SomeChemicals.ENERGY_SOLAR.getIndex()));
		Assertions.assertEquals(165, wc.getVariables().getVariable(SomeChemicals.ENERGY_HEAT.getIndex()));
		Assertions.assertEquals( 40, wc.getVariables().getVariable(SomeChemicals.PHEROMONE_00.getIndex()));

	}
	
	// TODO test in a World Context (3, 3) at position (1, 1) !!

}

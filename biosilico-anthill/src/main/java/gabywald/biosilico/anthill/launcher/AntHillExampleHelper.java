package gabywald.biosilico.anthill.launcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.BrainGeneBuilder;
import gabywald.biosilico.genetics.builders.BrainLobeGeneBuilder;
import gabywald.biosilico.genetics.builders.EmitterReceptorBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.InstinctBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class AntHillExampleHelper {
	
	public static final String BASE_EXPORT_MAIN_DIR = "src/main/resources/";
	public static final String BASE_EXPORT_TEST_DIR = "src/test/resources/";
	
	public static final String BASE_EXPORT_MAIN_DIR_ANTHILL = AntHillExampleHelper.BASE_EXPORT_MAIN_DIR + "anthill/";

	public static List<Chromosome> loadingAntGenome() {
		return AntHillExampleHelper.loadingGenome( AntHillExampleHelper.BASE_EXPORT_MAIN_DIR_ANTHILL + "baseGenomeAnt.txt" );
	}
	
	public static List<Chromosome> loadingPlantGenome() {
		return AntHillExampleHelper.loadingGenome( AntHillExampleHelper.BASE_EXPORT_MAIN_DIR_ANTHILL + "baseGenomePlant.txt" );
	}
	
	public static List<Chromosome> loadingGenome(String path2file) {
		
		File antGeneticData = new File( path2file );
		// if ( antGeneticData == null )  { return null; }
		try {
			antGeneticData.load();
			if (antGeneticData.lengthFile() <= 0) {
				return null; // XXX Exception ??
			}
		} catch (IOException e) {
			e.printStackTrace();
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
				break;
			case (18) : 
				// ***** Instinct || BiochemicalReaction
				if ( (datas[16].equals( "true" ) || (datas[16]).equals( "false" )) && (datas[17].equals( "true" ) || (datas[17]).equals( "false" )) ) {
					currentGene = igb	.inputPosX(Integer.parseInt(datas[ 9])).inputPosY(Integer.parseInt(datas[10]))
							.outputPosX(Integer.parseInt(datas[11])).outputPosY(Integer.parseInt(datas[12]))
							.weight(Integer.parseInt(datas[13])).variable(Integer.parseInt(datas[14]))
							.threshold(Integer.parseInt(datas[15])).check(Boolean.parseBoolean(datas[16]))
							.positiv(Boolean.parseBoolean(datas[17]))
								.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
								.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
								.build();
				} else {
					currentGene = brb	.acoef(Integer.parseInt(datas[ 9])).achem(Integer.parseInt(datas[10]))
										.bcoef(Integer.parseInt(datas[11])).achem(Integer.parseInt(datas[12]))
										.ccoef(Integer.parseInt(datas[13])).achem(Integer.parseInt(datas[14]))
										.dcoef(Integer.parseInt(datas[15])).achem(Integer.parseInt(datas[16]))
										 .kmvm(Integer.parseInt(datas[17]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
				}
				break;
			case (17) : 
				// ***** StimulusDecision 
				if ( (datas[ 9].equals( "true" ) || (datas[ 9]).equals( "false" )) && (datas[10].equals( "true" ) || (datas[10]).equals( "false" )) ) {
					currentGene = sdb	.perception(Boolean.parseBoolean(datas[ 9])).object(Boolean.parseBoolean(datas[10]))
										.indicator(Integer.parseInt(datas[11])).threshold(Integer.parseInt(datas[12]))
										.attribute(Integer.parseInt(datas[13])).varia(Integer.parseInt(datas[14]))
										.value(Integer.parseInt(datas[15])).script(Integer.parseInt(datas[16]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
				} else { currentGene = null; }
				break;
			case (16) : 
				// ***** EmitterReceptor
				currentGene = erb	.variable(Integer.parseInt(datas[ 9])).threshold(Integer.parseInt(datas[10])).ioput(Integer.parseInt(datas[11]))
									.posx(Integer.parseInt(datas[12])).posy(Integer.parseInt(datas[13]))
									.receptor(Boolean.parseBoolean(datas[14])).internal(Boolean.parseBoolean(datas[15]))
										.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
										.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
										.build();
				break;
			case (13) : 
				// ***** BrainGene
				currentGene = bgb	.heigth(Integer.parseInt(datas[ 9])).width(Integer.parseInt(datas[10])).depth(Integer.parseInt(datas[11])).more(Integer.parseInt(datas[12]))	
										.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
										.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
										.build();
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
				break;
			default:
				String message = "Unknown length (" + datas.length + ") {" + line + "}" ;
				Logger.printlnLog(LoggerLevel.LL_ERROR, message);
			} // END "switch(datas.length)"
			
			if (currentGene != null) {
				currentGene.setName(datas[ 0]);
				chr.addGene(currentGene);
				currentGene = null;
			}
		}
		
		return lstCHR;
	}
	
}

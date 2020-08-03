package gabywald.biosilico.anthill.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;
import gabywald.biosilico.genetics.builders.StimulusDecisionBuilder;
import gabywald.biosilico.model.Chromosome;
import gabywald.global.data.File;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class AntLoadingTests {

	@Test
	void testLoadingAnt() {
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
		
		Chromosome chr = new Chromosome();
		
		List<String> data2use = Arrays.asList( antGeneticData.getChampsToString().split("\n") );
		Gene currentGene				= null;
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		BiochemicalReactionBuilder brb	= new BiochemicalReactionBuilder();
		StimulusDecisionBuilder sdb		= new StimulusDecisionBuilder();
		for (String line : data2use) {
			if ( ! line.startsWith("// ") ) { 
				System.out.println( line );
				
				String datas[] = line.split( "\t" );
				switch(datas.length) {
				case (11) : 
					// ***** InitialConcentration
					System.out.println("\t" + datas[0] + " :: " + datas[1]);
					currentGene = icb	.varia(Integer.parseInt(datas[ 9])).value(Integer.parseInt(datas[10]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				case (18) : 
					// ***** BiochemicalReaction
					System.out.println("\t" + datas[0] + " :: " + datas[1]);
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
					System.out.println("\t" + datas[0] + " :: " + datas[1]);
					currentGene = sdb	.perception(Boolean.parseBoolean(datas[ 9])).object(Boolean.parseBoolean(datas[10]))
										.indicator(Integer.parseInt(datas[11])).threshold(Integer.parseInt(datas[12]))
										.attribute(Integer.parseInt(datas[13])).varia(Integer.parseInt(datas[14]))
										.value(Integer.parseInt(datas[15])).script(Integer.parseInt(datas[16]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					Assertions.assertNotNull( currentGene );
					break;
				default:
					System.out.println( "Unknown length (" + datas.length + ")" );
				} // END "switch(datas.length)"
				
				if (currentGene != null) {
					currentGene.setName(datas[ 0]);
					System.out.println( "\t" + currentGene );
					chr.addGene(currentGene);
					currentGene = null;
				}
			}
		}
		
		Ant testAnt = new Ant();
		Assertions.assertNotNull( testAnt );
		Assertions.assertEquals(0, testAnt.getGenome().size());
		
		testAnt.setRank("Rank Test");
		testAnt.setNameCommon("Test Starting Ant");
		
		testAnt.getGenome().add( chr );
		
		System.out.println( testAnt.toString() );
		
		// TODO running ant instance !
	}

}

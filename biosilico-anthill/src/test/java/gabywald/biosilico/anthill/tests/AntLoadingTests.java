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
					// ***** InitConc
					System.out.println("\t" + datas[0] + " :: " + datas[1]);
					currentGene = icb.varia(Integer.parseInt(datas[ 9])).value(Integer.parseInt(datas[10]))
											.mutate(Boolean.parseBoolean(datas[ 1]))	.duplicate(Boolean.parseBoolean(datas[ 2]))	.delete(Boolean.parseBoolean(datas[ 3])).activ(Boolean.parseBoolean(datas[ 4]))
											.agemin(Integer.parseInt(datas[ 5]))		.agemax(Integer.parseInt(datas[ 6]))		.sex(Integer.parseInt(datas[ 7]))		.mutation(Integer.parseInt(datas[ 8]))
											.build();
					currentGene.setName(datas[ 0]);
					
					Assertions.assertNotNull( currentGene );
					
					System.out.println( "\t" + currentGene );
					
					chr.addGene(currentGene);
					
					break;
				case (18) : 
					// ***** BioRea
					// TODO reading BioRea
					break;
				case (17) : 
					// ***** StiDec
					// TODO reading StiDec
					break;
				default:
					System.out.println( "Unknown length (" + datas.length + ")" );
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

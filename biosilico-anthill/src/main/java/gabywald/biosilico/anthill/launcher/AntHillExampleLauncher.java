package gabywald.biosilico.anthill.launcher;

import java.util.List;
import java.util.stream.IntStream;

import gabywald.biosilico.anthill.Ant;
import gabywald.biosilico.anthill.Plant;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.utils.agents.Condensator;
import gabywald.biosilico.model.utils.agents.ConverterPlantEgg2Fruit;
import gabywald.biosilico.model.utils.agents.EnergySource;

/**
 * 
 * @author Gabriel Chandesris (2009, 2020)
 */
public class AntHillExampleLauncher {
	

	public static void main(String[] args) {
		
		List<Chromosome> antGenome		= AntHillExampleHelper.loadingAntGenome();
		List<Chromosome> plantGenome	= AntHillExampleHelper.loadingPlantGenome();
		
		World w				= new World(1, 1);
		w.loadHalLives();

		Ant initialAnt		= new Ant( antGenome );
		initialAnt.setRank("Ant Rank");
		initialAnt.setNameCommon("Test Starting Ant");
		initialAnt.setNameBiosilico("Simple Ant Example");
		initialAnt.setDivision("INITIAL ANT");
		initialAnt.setCurrentWorldCase( w.getWorldCase(0, 0) );
		
		Plant initialPlant	= new Plant( plantGenome );
		initialPlant.setRank("Plant Rank");
		initialPlant.setNameCommon("Test Starting Plant");
		initialPlant.setNameBiosilico("Simple Plant Example");
		initialPlant.setDivision("INITIAL PLANT");
		initialPlant.setCurrentWorldCase( w.getWorldCase(0, 0) );
		
		w.getWorldCase(0, 0).addAgent( new EnergySource() );
		w.getWorldCase(0, 0).addAgent( new Condensator() );
		w.getWorldCase(0, 0).addAgent( new ConverterPlantEgg2Fruit() );
		
		w.execution();
		initialAnt.cyclePlusPlus();
		initialPlant.cyclePlusPlus();

		
		List<WorldCase> wcs = w.getWorldCases();
		BuildingGenomeHelper.showAll( initialAnt, initialPlant, wcs );
		
		// TODO Organism evolution (status changes for Ant and Plant)
		
		IntStream.range(0, 20).forEach( i -> {
			w.execution();
		});
		
		BuildingGenomeHelper.showAll( initialAnt, initialPlant, wcs );
		
		// TODO ... 
		

	}

}

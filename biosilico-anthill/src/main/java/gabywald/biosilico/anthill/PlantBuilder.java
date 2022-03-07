package gabywald.biosilico.anthill;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class PlantBuilder implements IBuilder<Plant> {

	public PlantBuilder() {
		;
	}
	
	public List<Gene> generateBasicGenome() {
		List<Gene> toReturn = new ArrayList<Gene>();
		
		// TODO see resources in src/main/resources/baseGenomePlant.txt and src/main/resources/SomeNotes.txt
		
		return toReturn;
	}
	
	@Override
	public Plant build() {
		
		return null;
	}
}

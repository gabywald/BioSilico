package gabywald.biosilico.anthill;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class AntBuilder implements IBuilder<Ant> {

	public AntBuilder() { ; }
	
	public List<Gene> generateBasicGenome() {
		List<Gene> toReturn = new ArrayList<Gene>();
		
		// TODO see resources in src/main/resources/baseGenomeAnt.txt and src/main/resources/SomeNotes.txt
		
		return toReturn;
	}
	
	@Override
	public Ant build() {
		
		return null;
	}
	
}

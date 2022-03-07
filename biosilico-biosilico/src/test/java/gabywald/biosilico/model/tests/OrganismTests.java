package gabywald.biosilico.model.tests;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Organism;
import gabywald.global.structures.StringCouple;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
class OrganismTests {

	@Test
	void testOrganismBasics() {
		Organism orga = new Organism();
		Assertions.assertNotNull(orga);
		Assertions.assertEquals(0, orga.getSimpleLinage().size());
		Assertions.assertIterableEquals(new ArrayList<String>(), orga.getSimpleLinage());
		Assertions.assertEquals(1, orga.getGeneticCodes().size());
		Assertions.assertEquals(new StringCouple("0000000010", "Gattaca01"), orga.getGeneticCodes().get(0));
	}
	
	// TODO Test simple and extended lineagez of Organism

}

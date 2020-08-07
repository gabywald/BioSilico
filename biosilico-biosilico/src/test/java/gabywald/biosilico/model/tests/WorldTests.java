package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class WorldTests {

	@Test
	void testWorld01() {
		World w = new World();
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() ); // XXX half-lives
		
		IntStream.range(0, World.MAX_HEIGHT).forEach( i -> {
			IntStream.range(0, World.MAX_WIDTH).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
			});
		});
	}
	
	@Test
	void testWorld02() {
		World w = new World(5, 5);
		
		Assertions.assertNotNull( w );
		Assertions.assertNotNull( w.getVariables() ); // XXX half-lives
		
		IntStream.range(0, 5).forEach( i -> {
			IntStream.range(0, 5).forEach( j -> {
				WorldCase currentWC = w.getWorldCase(i, j);
				Assertions.assertNotNull( currentWC );
			});
		});
	}
	
}

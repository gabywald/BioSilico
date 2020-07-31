package gabywald.biosilico.anthill;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class AntBuilder {

	public AntBuilder() {
		;
	}
	
	public List<Gene> generateBasicGenome() {
		List<Gene> toReturn = new ArrayList<Gene>();
		
		// NOTEs :: 
		// // // need some initial concentration !!
		// // // need some biochemical reaction
		// // // receptor for some variables from environment
		// // // emitter for some variables from environment
		// // // Decision to take fruits !
		// // // Decision to drop fruits ! (in the AntHill)
		// // // Decision to eat fruits !
		// // // Decision to display pheromones !
		
		// // // detection of pheromones !
		// // // choice of destination :: movement !!
		
		
		BiochemicalReactionBuilder brb = new BiochemicalReactionBuilder();
		toReturn.add(brb.achem( 20 ).acoef( 1 )
				.bchem( 21 ).bcoef( 1 )
				.cchem( 22 ).ccoef( 1 )
				.dchem( 23 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 22 ).acoef( 1 )
				.bchem( 23 ).bcoef( 1 )
				.cchem( 24 ).ccoef( 1 )
				.dchem( 25 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 24 ).acoef( 1 )
				.bchem( 25 ).bcoef( 1 )
				.cchem( 26 ).ccoef( 1 )
				.dchem( 27 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 26 ).acoef( 1 )
				.bchem( 27 ).bcoef( 1 )
				.cchem( 28 ).ccoef( 1 )
				.dchem( 29 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 28 ).acoef( 1 )
				.bchem( 29 ).bcoef( 1 )
				.cchem( 30 ).ccoef( 1 )
				.dchem( 31 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 30 ).acoef( 1 )
				.bchem( 31 ).bcoef( 1 )
				.cchem( 32 ).ccoef( 1 )
				.dchem( 33 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 32 ).acoef( 1 )
				.bchem( 33 ).bcoef( 1 )
				.cchem( 34 ).ccoef( 1 )
				.dchem( 35 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 34 ).acoef( 1 )
				.bchem( 35 ).bcoef( 1 )
				.cchem( 36 ).ccoef( 1 )
				.dchem( 37 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		return toReturn;
	}
	
	public Ant build() {
		
		return null;
	}
	
}

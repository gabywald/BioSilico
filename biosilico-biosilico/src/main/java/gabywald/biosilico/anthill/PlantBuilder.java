package gabywald.biosilico.anthill;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.builders.BiochemicalReactionBuilder;
import gabywald.biosilico.genetics.builders.InitialConcentrationBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class PlantBuilder {

	public PlantBuilder() {
		;
	}
	
	public List<Gene> generateBasicGenome() {
		List<Gene> toReturn = new ArrayList<Gene>();
		
		// NOTEs :: 
		// // // need some initial concentration !!
		// // // need some biochemical reaction
		// // // receptor for some variables from environment
		// // // emitter for some variables from environment
		// // // Decision to make fruits !
		
		InitialConcentrationBuilder icb	= new InitialConcentrationBuilder();
		toReturn.add( icb.varia( 0 ).value( 100 ).agemin( 0 ).agemax( 0 ).mutation(25).build() );
		
		BiochemicalReactionBuilder brb = new BiochemicalReactionBuilder();
		toReturn.add(brb.achem( 0 ).acoef( 1 )
				.bchem( 1 ).bcoef( 1 )
				.cchem( 2 ).ccoef( 1 )
				.dchem( 3 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 2 ).acoef( 1 )
				.bchem( 3 ).bcoef( 1 )
				.cchem( 4 ).ccoef( 1 )
				.dchem( 5 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 4 ).acoef( 1 )
				.bchem( 5 ).bcoef( 1 )
				.cchem( 6 ).ccoef( 1 )
				.dchem( 7 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 6 ).acoef( 1 )
				.bchem( 7 ).bcoef( 1 )
				.cchem( 8 ).ccoef( 1 )
				.dchem( 9 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 8 ).acoef( 1 )
				.bchem( 9 ).bcoef( 1 )
				.cchem( 10 ).ccoef( 1 )
				.dchem( 11 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 10 ).acoef( 1 )
				.bchem( 11 ).bcoef( 1 )
				.cchem( 12 ).ccoef( 1 )
				.dchem( 13 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 12 ).acoef( 1 )
				.bchem( 13 ).bcoef( 1 )
				.cchem( 14 ).ccoef( 1 )
				.dchem( 15 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 14 ).acoef( 1 )
				.bchem( 15 ).bcoef( 1 )
				.cchem( 16 ).ccoef( 1 )
				.dchem( 17 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 16 ).acoef( 1 )
				.bchem( 17 ).bcoef( 1 )
				.cchem( 18 ).ccoef( 1 )
				.dchem( 19 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
		toReturn.add(brb.achem( 18 ).acoef( 1 )
				.bchem( 19 ).bcoef( 1 )
				.cchem( 20 ).ccoef( 1 )
				.dchem( 21 ).dcoef( 1 )
				.kmvm( 5 ).agemax(999).mutation(25).build());
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
	
	public Plant build() {
		
		return null;
	}
}

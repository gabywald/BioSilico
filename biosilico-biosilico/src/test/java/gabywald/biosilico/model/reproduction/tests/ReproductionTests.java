package gabywald.biosilico.model.reproduction.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.World;
import gabywald.biosilico.model.WorldCase;
import gabywald.biosilico.model.decisions.DecisionBuilder;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class ReproductionTests {

	@Test
	void testReproductionDaemon() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);

		World w			= new World(3, 3);
		Assertions.assertNotNull( w );
		WorldCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentWorldCase());

		Assertions.assertEquals(1, wc.getAgentListe().size());

		Assertions.assertFalse(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision01	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision01 );
		if (decision01 != null) { decision01.action(); }

		// No fertility => does not work !!
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());

		// ***** ensure this is fertile !! => WORKS !!
		testOrga.getVariables().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is Pregnant !! => WORKS !!
		Assertions.assertTrue(testOrga.isPregnant());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, testOrga.getAgentListe().size());
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
	}

	
	// TODO continuing ReproductionTests !!
	

}

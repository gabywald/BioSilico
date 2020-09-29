package gabywald.biosilico.model.reproduction.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.decisions.DecisionBuilder;
import gabywald.biosilico.model.decisions.IDecision;
import gabywald.biosilico.model.enums.AgentType;
import gabywald.biosilico.model.enums.DecisionType;
import gabywald.biosilico.model.enums.StateType;
import gabywald.biosilico.model.enums.StatusType;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;

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
		testOrga.setAgentType(AgentType.BIOSILICO_DAEMON); // default AgentType
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_DAEMON));

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
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_DAEMON));
		Assertions.assertEquals(0, testOrga.getAgentListe().size());

		// ***** ensure this is fertile !! => WORKS !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
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
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_DAEMON));
		Assertions.assertEquals(1, testOrga.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, wc.hasAgentType(AgentType.BIOSILICO_DAEMON));
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
	}
	
	@Test
	void testReproductionBacter() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_BACTA); // Specific Agent type : here BACTA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_BACTA));

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
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_BACTA));
		Assertions.assertEquals(0, testOrga.getAgentListe().size());

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_BACTA));
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		
		// ***** Make gamets !!
		IDecision decisionGAMET	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMET );
		if (decisionGAMET != null) { decisionGAMET.action(); }
		if (decisionGAMET != null) { decisionGAMET.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_BACTA));
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());

		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_BACTA));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrga.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, wc.hasAgentType(AgentType.BIOSILICO_BACTA));
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
	}
	
	@Test
	void testReproductionAnimaWithSame() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_ANIMA); // Specific Agent type : here ANIMA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Chromosome basicGenome = new Chromosome();
		basicGenome.setName("Basic Genome for tests in Reproduction for Anima");
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 2, 10, 6, 5, 7, 5, 1));
		testOrga.getGenome().add(basicGenome);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_ANIMA));

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

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		
		// ***** Make gamets !!
		IDecision decisionGAMET	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMET );
		if (decisionGAMET != null) { decisionGAMET.action(); }
		if (decisionGAMET != null) { decisionGAMET.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());

		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		Assertions.assertEquals(1, wc.getAgentListe().size());

		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
	}

	@Test
	void testReproductionAnimaWithOtherWorking() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_ANIMA); // Specific Agent type : here ANIMA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Chromosome basicGenome = new Chromosome();
		basicGenome.setName("Basic Genome for tests in Reproduction for Anima");
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 2, 10, 6, 5, 7, 5, 1));
		testOrga.getGenome().add(basicGenome);
		
		Organism testOrgaOther	= new Organism( basicGenome );
		Assertions.assertNotNull( testOrgaOther );
		testOrgaOther.setAgentType(AgentType.BIOSILICO_ANIMA); // Specific Agent type : here ANIMA !!
		testOrgaOther.setOrganismStatus(StatusType.NOT_ACCURATE);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		testOrgaOther.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrgaOther.getCurrentEnvironmentItem());

		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, wc.hasAgentType(AgentType.BIOSILICO_ANIMA));

		Assertions.assertFalse(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision01	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision01 );
		if (decision01 != null) { decision01.action(); }

		// No fertility => does not work !!
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		
		testOrgaOther.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** Make gamets !! (for the two organisms !)
		IDecision decisionGAMETorga1	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga1 );
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		IDecision decisionGAMETorga2	= db.type(dTypeMAKEGAMET).organism( testOrgaOther )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga2 );
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(2, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrgaOther.getAgentListe().size());
		
		// ***** REPRODUCTION !!
		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(1, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrgaOther.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(3, wc.getAgentListe().size());
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrga.getAgentListe().size());
		Assertions.assertEquals(1, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrgaOther.getAgentListe().size());
	}

	@Test
	void testReproductionAnimaWithOtherNOTWorking() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_ANIMA); // Specific Agent type : here ANIMA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Chromosome basicGenome = new Chromosome();
		basicGenome.setName("Basic Genome for tests in Reproduction for Anima");
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 2, 10, 6, 5, 7, 5, 1));
		testOrga.getGenome().add(basicGenome);
		
		Organism testOrgaOther	= new Organism( basicGenome );
		Assertions.assertNotNull( testOrgaOther );
		testOrgaOther.setAgentType(AgentType.BIOSILICO_ANIMA); // Specific Agent type : here ANIMA !!
		testOrgaOther.setOrganismStatus(StatusType.NOT_ACCURATE);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		testOrgaOther.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrgaOther.getCurrentEnvironmentItem());

		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, wc.hasAgentType(AgentType.BIOSILICO_ANIMA));

		Assertions.assertFalse(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision01	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision01 );
		if (decision01 != null) { decision01.action(); }

		// No fertility => does not work !!
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		
		// => making simple not fertile (or no gamets... same things !)
		testOrgaOther.getChemicals().setVariable(StateType.FERTILE.getIndex(),   0);
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** Make gamets !! (for the two organisms !)
		IDecision decisionGAMETorga1	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga1 );
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		IDecision decisionGAMETorga2	= db.type(dTypeMAKEGAMET).organism( testOrgaOther )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga2 );
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** REPRODUCTION !!
		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
	}
	
	@Test
	void testReproductionViriditaWithSame() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_VIRIDITA); // Specific Agent type : here BIOSILICO_VIRIDITA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Chromosome basicGenome = new Chromosome();
		basicGenome.setName("Basic Genome for tests in Reproduction for Anima");
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 2, 10, 6, 5, 7, 5, 1));
		testOrga.getGenome().add(basicGenome);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(1, wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA));

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

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());

		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		
		// ***** Make gamets !!
		IDecision decisionGAMET	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMET );
		if (decisionGAMET != null) { decisionGAMET.action(); }
		if (decisionGAMET != null) { decisionGAMET.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());

		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		Assertions.assertEquals(1, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrga.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		Assertions.assertEquals(2, wc.getAgentListe().size());
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertEquals(0, testOrga.lineageSize());
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
	}

	@Test
	void testReproductionViriditaWithOtherWorking() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_VIRIDITA); // Specific Agent type : here BIOSILICO_VIRIDITA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Chromosome basicGenome = new Chromosome();
		basicGenome.setName("Basic Genome for tests in Reproduction for Anima");
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 2, 10, 6, 5, 7, 5, 1));
		testOrga.getGenome().add(basicGenome);
		
		Organism testOrgaOther	= new Organism( basicGenome );
		Assertions.assertNotNull( testOrgaOther );
		testOrgaOther.setAgentType(AgentType.BIOSILICO_VIRIDITA); // Specific Agent type : here BIOSILICO_VIRIDITA !!
		testOrgaOther.setOrganismStatus(StatusType.NOT_ACCURATE);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		testOrgaOther.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrgaOther.getCurrentEnvironmentItem());

		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA));

		Assertions.assertFalse(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision01	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision01 );
		if (decision01 != null) { decision01.action(); }

		// No fertility => does not work !!
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		
		testOrgaOther.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** Make gamets !! (for the two organisms !)
		IDecision decisionGAMETorga1	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga1 );
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		IDecision decisionGAMETorga2	= db.type(dTypeMAKEGAMET).organism( testOrgaOther )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga2 );
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(2, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrgaOther.getAgentListe().size());
		
		// ***** REPRODUCTION !!
		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(1, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrgaOther.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertTrue(testOrga.isPregnant());
		Assertions.assertTrue(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(3, wc.getAgentListe().size());
		Assertions.assertEquals(1, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrga.getAgentListe().size());
		Assertions.assertEquals(1, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(1, testOrgaOther.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.lineageSize());
	}

	@Test
	void testReproductionViriditaWithOtherNOTWorking() {

		DecisionType dTypeMATE 		= DecisionType.getFrom( DecisionType.MATE.getIndex() );
		Assertions.assertNotNull( dTypeMATE );
		
		DecisionType dTypeLAYEGG	= DecisionType.getFrom( DecisionType.LAY_EGG.getIndex() );
		Assertions.assertNotNull( dTypeLAYEGG );
		
		DecisionType dTypeMAKEGAMET	= DecisionType.getFrom( DecisionType.MAKE_GAMET.getIndex() );
		Assertions.assertNotNull( dTypeMAKEGAMET );

		Organism testOrga	= new Organism();
		Assertions.assertNotNull( testOrga );
		testOrga.setAgentType(AgentType.BIOSILICO_VIRIDITA); // Specific Agent type : here BIOSILICO_VIRIDITA !!
		testOrga.setOrganismStatus(StatusType.NOT_ACCURATE);
		
		Chromosome basicGenome = new Chromosome();
		basicGenome.setName("Basic Genome for tests in Reproduction for Anima");
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						1, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						2, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						3, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						4, 100));
		basicGenome.addGene(new InitialConcentration(false, false, false, true, 0, 0, 0, 0,
						5, 100));
		basicGenome.addGene(new BiochemicalReaction(false, false, false, true, 0, 10, 0, 0,
						1, 10, 2, 10, 6, 5, 7, 5, 1));
		testOrga.getGenome().add(basicGenome);
		
		Organism testOrgaOther	= new Organism( basicGenome );
		Assertions.assertNotNull( testOrgaOther );
		testOrgaOther.setAgentType(AgentType.BIOSILICO_VIRIDITA); // Specific Agent type : here BIOSILICO_VIRIDITA !!
		testOrgaOther.setOrganismStatus(StatusType.NOT_ACCURATE);

		World2D w			= new World2D(3, 3);
		Assertions.assertNotNull( w );
		World2DCase wc	= w.getWorldCase(1,  1);
		Assertions.assertNotNull( wc );

		testOrga.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrga.getCurrentEnvironmentItem());
		
		testOrgaOther.setCurrentWorldCase( wc );
		Assertions.assertEquals(wc, testOrgaOther.getCurrentEnvironmentItem());

		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, wc.hasAgentType(AgentType.BIOSILICO_VIRIDITA));

		Assertions.assertFalse(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		DecisionBuilder db	= new DecisionBuilder();
		Assertions.assertNotNull( db );
		IDecision decision01	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision01 );
		if (decision01 != null) { decision01.action(); }

		// No fertility => does not work !!
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());

		// ***** ensure this is fertile !! => not working (need gamets !) !!
		testOrga.getChemicals().setVariable(StateType.FERTILE.getIndex(), 100);
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		
		// => making simple not fertile (or no gamets... same things !)
		testOrgaOther.getChemicals().setVariable(StateType.FERTILE.getIndex(),   0);
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		
		IDecision decision02	= db.type(dTypeMATE).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision02 );
		if (decision02 != null) { decision02.action(); }
		
		// ***** ensure this is NOT Pregnant !!
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** Make gamets !! (for the two organisms !)
		IDecision decisionGAMETorga1	= db.type(dTypeMAKEGAMET).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga1 );
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		if (decisionGAMETorga1 != null) { decisionGAMETorga1.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		IDecision decisionGAMETorga2	= db.type(dTypeMAKEGAMET).organism( testOrgaOther )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decisionGAMETorga2 );
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		if (decisionGAMETorga2 != null) { decisionGAMETorga2.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** REPRODUCTION !!
		if (decision02 != null) { decision02.action(); }
		
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		
		// ***** Make LAYING EGG
		Assertions.assertTrue(testOrga.isFertile());
		Assertions.assertFalse(testOrga.isPregnant());
		Assertions.assertFalse(testOrgaOther.isFertile());
		Assertions.assertFalse(testOrgaOther.isPregnant());
		IDecision decision03	= db.type(dTypeLAYEGG).organism( testOrga )
				.object( -1 ).threshold( -1 ).attribute( -1 ).variable( -1 ).value( -1 )
				.build();
		Assertions.assertNotNull( decision03 );
		if (decision02 != null) { decision03.action(); }
		
		// ***** LAY EGG is Working !
		Assertions.assertEquals(2, wc.getAgentListe().size());
		Assertions.assertEquals(2, testOrga.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrga.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(2, testOrga.getAgentListe().size());
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.GAMET));
		Assertions.assertEquals(0, testOrgaOther.hasAgentStatus(StatusType.EGG));
		Assertions.assertEquals(0, testOrgaOther.getAgentListe().size());
		Assertions.assertEquals(0, testOrga.lineageSize());
	}
	
	// TODO testReproductionViria !!
	
}

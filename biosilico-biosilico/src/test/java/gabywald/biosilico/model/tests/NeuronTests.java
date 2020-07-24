package gabywald.biosilico.model.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.NeuronBuilder;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class NeuronTests {

	@Test
	void testNeuronInstanciation() {
		Neuron receptorActi01	= new Neuron(0, 100, 10, 0,  0, 0, false,   0);
		Assertions.assertNotNull( receptorActi01 );
		Assertions.assertEquals(0, 		receptorActi01.getRestState());
		Assertions.assertEquals(100, 	receptorActi01.getThreshold());
		Assertions.assertEquals(10, 	receptorActi01.getDescent());
		Assertions.assertEquals(0, 		receptorActi01.getDmin());
		Assertions.assertEquals(0, 		receptorActi01.getDmax());
		Assertions.assertEquals(0, 		receptorActi01.getProximity());
		Assertions.assertEquals(false, 	receptorActi01.getReproduction());
		Assertions.assertEquals(0, 		receptorActi01.getReproductibility());
		Assertions.assertEquals(false, 	receptorActi01.isWTA());
		Assertions.assertEquals(0, 		receptorActi01.getActivity());
		
		Neuron decisionTest		= new Neuron(0,  10,  1, 3,  8, 4, false,   0, true);
		Assertions.assertNotNull( decisionTest );
		Assertions.assertEquals(0, 		decisionTest.getRestState());
		Assertions.assertEquals(10, 	decisionTest.getThreshold());
		Assertions.assertEquals(1, 		decisionTest.getDescent());
		Assertions.assertEquals(3, 		decisionTest.getDmin());
		Assertions.assertEquals(8, 		decisionTest.getDmax());
		Assertions.assertEquals(4, 		decisionTest.getProximity());
		Assertions.assertEquals(false, 	decisionTest.getReproduction());
		Assertions.assertEquals(0, 		decisionTest.getReproductibility());
		Assertions.assertEquals(true, 	decisionTest.isWTA());
		Assertions.assertEquals(0, 		decisionTest.getActivity());
		
		Neuron conceptionUn		= new Neuron(0,  10,  1, 2,  6, 3, true,  100);
		Assertions.assertNotNull( conceptionUn );
		Assertions.assertEquals(0, 		conceptionUn.getRestState());
		Assertions.assertEquals(10, 	conceptionUn.getThreshold());
		Assertions.assertEquals(1, 		conceptionUn.getDescent());
		Assertions.assertEquals(2, 		conceptionUn.getDmin());
		Assertions.assertEquals(6, 		conceptionUn.getDmax());
		Assertions.assertEquals(3, 		conceptionUn.getProximity());
		Assertions.assertEquals(true, 	conceptionUn.getReproduction());
		Assertions.assertEquals(100, 	conceptionUn.getReproductibility());
		Assertions.assertEquals(false, 	conceptionUn.isWTA());
		Assertions.assertEquals(0, 		conceptionUn.getActivity());
		
		Neuron conceptionDe		= new Neuron(0,  10,  1, 2,  4, 3, false,   0);
		Assertions.assertNotNull( conceptionDe );
		Assertions.assertEquals(0, 		conceptionDe.getRestState());
		Assertions.assertEquals(10, 	conceptionDe.getThreshold());
		Assertions.assertEquals(1, 		conceptionDe.getDescent());
		Assertions.assertEquals(2, 		conceptionDe.getDmin());
		Assertions.assertEquals(4, 		conceptionDe.getDmax());
		Assertions.assertEquals(3, 		conceptionDe.getProximity());
		Assertions.assertEquals(false, 	conceptionDe.getReproduction());
		Assertions.assertEquals(0, 		conceptionDe.getReproductibility());
		Assertions.assertEquals(false, 	conceptionDe.isWTA());
		Assertions.assertEquals(0, 		conceptionDe.getActivity());
		
		Neuron receptorActi02	= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
		receptorActi02.setActivity(200);
		Assertions.assertNotNull( receptorActi02 );
		Assertions.assertEquals(0, 		receptorActi02.getRestState());
		Assertions.assertEquals(100, 	receptorActi02.getThreshold());
		Assertions.assertEquals(50, 	receptorActi02.getDescent());
		Assertions.assertEquals(0, 		receptorActi02.getDmin());
		Assertions.assertEquals(0, 		receptorActi02.getDmax());
		Assertions.assertEquals(0, 		receptorActi02.getProximity());
		Assertions.assertEquals(false, 	receptorActi02.getReproduction());
		Assertions.assertEquals(0, 		receptorActi02.getReproductibility());
		Assertions.assertEquals(false, 	receptorActi02.isWTA());
		Assertions.assertEquals(200, 	receptorActi02.getActivity());
		
		Neuron conception		= new Neuron(0, 10, 5, 1, 1, 2, false, 2);
		Assertions.assertNotNull( conception );
		Assertions.assertEquals(0, 		conception.getRestState());
		Assertions.assertEquals(10, 	conception.getThreshold());
		Assertions.assertEquals(5, 		conception.getDescent());
		Assertions.assertEquals(1, 		conception.getDmin());
		Assertions.assertEquals(1, 		conception.getDmax());
		Assertions.assertEquals(2, 		conception.getProximity());
		Assertions.assertEquals(false, 	conception.getReproduction());
		Assertions.assertEquals(2, 		conception.getReproductibility());
		Assertions.assertEquals(false, 	conception.isWTA());
		Assertions.assertEquals(0, 		conception.getActivity());
		
		Neuron emittersTest 	= new Neuron(0, 10, 8, 4, 4, 4, false, 0);
		Assertions.assertNotNull( emittersTest );
		Assertions.assertEquals(0, 		emittersTest.getRestState());
		Assertions.assertEquals(10, 	emittersTest.getThreshold());
		Assertions.assertEquals(8, 		emittersTest.getDescent());
		Assertions.assertEquals(4, 		emittersTest.getDmin());
		Assertions.assertEquals(4, 		emittersTest.getDmax());
		Assertions.assertEquals(4, 		emittersTest.getProximity());
		Assertions.assertEquals(false, 	emittersTest.getReproduction());
		Assertions.assertEquals(0, 		emittersTest.getReproductibility());
		Assertions.assertEquals(false, 	emittersTest.isWTA());
		Assertions.assertEquals(0, 		emittersTest.getActivity());
	}
		
	@Test
	void testNeuronInstanciationBuilder() {
		NeuronBuilder nb = new NeuronBuilder();
		// new Neuron(0, 100, 10, 0, 0, 0, false, 0, false)
		Neuron receptor = nb.threshold(100).desc(10).build();
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals(0, 		receptor.getActivity());
		
		nb.init();
		// new Neuron(0, 10, 8, 4, 4, 4, false, 0)
		Neuron emitter = nb.threshold(10).desc(8).dmin(4).dmax(4).prox(4).build();
		Assertions.assertNotNull( emitter );
		Assertions.assertEquals(0, 		emitter.getRestState());
		Assertions.assertEquals(10, 	emitter.getThreshold());
		Assertions.assertEquals(8, 		emitter.getDescent());
		Assertions.assertEquals(4, 		emitter.getDmin());
		Assertions.assertEquals(4, 		emitter.getDmax());
		Assertions.assertEquals(4, 		emitter.getProximity());
		Assertions.assertEquals(false, 	emitter.getReproduction());
		Assertions.assertEquals(0, 		emitter.getReproductibility());
		Assertions.assertEquals(false, 	emitter.isWTA());
		Assertions.assertEquals(0, 		emitter.getActivity());
		
		nb.init();
		// new Neuron(0,  10,  1, 2,  6, 3, true,  100);
		Neuron conceptionOne = nb.threshold(10).desc(1).dmin(2).dmax(6).prox(3).repr(true).build();
		Assertions.assertNotNull( conceptionOne );
		Assertions.assertEquals(0, 		conceptionOne.getRestState());
		Assertions.assertEquals(10, 	conceptionOne.getThreshold());
		Assertions.assertEquals(1, 		conceptionOne.getDescent());
		Assertions.assertEquals(2, 		conceptionOne.getDmin());
		Assertions.assertEquals(6, 		conceptionOne.getDmax());
		Assertions.assertEquals(3, 		conceptionOne.getProximity());
		Assertions.assertEquals(true, 	conceptionOne.getReproduction());
		Assertions.assertEquals(0, 		conceptionOne.getReproductibility());
		Assertions.assertEquals(false, 	conceptionOne.isWTA());
		Assertions.assertEquals(0, 		conceptionOne.getActivity());
		
		nb.init();
		// new Neuron(0,  10,  1, 2,  4, 3, false,   0);
		Neuron conceptionTwo = nb.threshold(10).desc(1).dmin(2).dmax(4).prox(3).build();
		Assertions.assertNotNull( conceptionTwo );
		Assertions.assertEquals(0, 		conceptionTwo.getRestState());
		Assertions.assertEquals(10, 	conceptionTwo.getThreshold());
		Assertions.assertEquals(1, 		conceptionTwo.getDescent());
		Assertions.assertEquals(2, 		conceptionTwo.getDmin());
		Assertions.assertEquals(4, 		conceptionTwo.getDmax());
		Assertions.assertEquals(3, 		conceptionTwo.getProximity());
		Assertions.assertEquals(false, 	conceptionTwo.getReproduction());
		Assertions.assertEquals(0, 		conceptionTwo.getReproductibility());
		Assertions.assertEquals(false, 	conceptionTwo.isWTA());
		Assertions.assertEquals(0, 		conceptionTwo.getActivity());
		
		nb.init();
		// new Neuron(0,  10,  1, 3,  8, 4, false,   0, true);
		Neuron decision = nb.threshold(10).desc(1).dmin(3).dmax(8).prox(4).wta(true).build();
		Assertions.assertNotNull( decision );
		Assertions.assertEquals(0, 		decision.getRestState());
		Assertions.assertEquals(10, 	decision.getThreshold());
		Assertions.assertEquals(1, 		decision.getDescent());
		Assertions.assertEquals(3, 		decision.getDmin());
		Assertions.assertEquals(8, 		decision.getDmax());
		Assertions.assertEquals(4, 		decision.getProximity());
		Assertions.assertEquals(false, 	decision.getReproduction());
		Assertions.assertEquals(0, 		decision.getReproductibility());
		Assertions.assertEquals(true, 	decision.isWTA());
		Assertions.assertEquals(0, 		decision.getActivity());
	}
	
	@Test
	void testNeuronRecompute() {
		NeuronBuilder nb = new NeuronBuilder();
		
		Neuron receptor = nb.threshold(100).desc(10).build();
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals(0, 		receptor.getActivity());
		Assertions.assertEquals(false, 	receptor.isActivated());
		
		receptor.recompute();
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals(0, 		receptor.getActivity());
		Assertions.assertEquals(false, 	receptor.isActivated());
		
		receptor.addActivity(1000);
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals(1000, 	receptor.getActivity());
		Assertions.assertEquals(true, 	receptor.isActivated());
		
		receptor.recompute();
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals( 990, 	receptor.getActivity());
		Assertions.assertEquals(true, 	receptor.isActivated());
		
		receptor.recompute();
		receptor.recompute();
		receptor.recompute();
		receptor.recompute();
		receptor.recompute();
		receptor.recompute();
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals( 930, 	receptor.getActivity());
		Assertions.assertEquals(true, 	receptor.isActivated());
		
		receptor.setActivity( 100 );
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals( 100, 	receptor.getActivity());
		Assertions.assertEquals(true, 	receptor.isActivated());
		
		receptor.recompute();
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals(  90, 	receptor.getActivity());
		Assertions.assertEquals(false, 	receptor.isActivated());
		
		receptor.recompute();
		
		Assertions.assertNotNull( receptor );
		Assertions.assertEquals(0, 		receptor.getRestState());
		Assertions.assertEquals(100, 	receptor.getThreshold());
		Assertions.assertEquals(10, 	receptor.getDescent());
		Assertions.assertEquals(0, 		receptor.getDmin());
		Assertions.assertEquals(0, 		receptor.getDmax());
		Assertions.assertEquals(0, 		receptor.getProximity());
		Assertions.assertEquals(false, 	receptor.getReproduction());
		Assertions.assertEquals(0, 		receptor.getReproductibility());
		Assertions.assertEquals(false, 	receptor.isWTA());
		Assertions.assertEquals(  80, 	receptor.getActivity());
		Assertions.assertEquals(false, 	receptor.isActivated());
	}
	
	// TODO complete these tests !! NeuronTests

}

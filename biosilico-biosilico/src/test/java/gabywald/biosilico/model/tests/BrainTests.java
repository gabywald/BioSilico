package gabywald.biosilico.model.tests;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.BrainBuilder;
import gabywald.biosilico.model.Neuron;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class BrainTests {

	@Test
	void testBrainInstanciation() {

		try {
			Brain brainInstance = BrainBuilder.brainBuilder(10, 10, 10);
			Assertions.assertEquals(10,  brainInstance.getWidth());
			Assertions.assertEquals(10,  brainInstance.getHeight());
			Assertions.assertEquals(10,  brainInstance.getDepth());
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("Brain build 01 failed");
		}
		
		try {
			Brain brainInstance = BrainBuilder.brainBuilder(10, 10);
			Assertions.assertEquals(10,  brainInstance.getWidth());
			Assertions.assertEquals(10,  brainInstance.getHeight());
			Assertions.assertEquals(0,  brainInstance.getDepth());
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("Brain build 02 failed");
		}
		
		try {
			Brain brainInstance = BrainBuilder.brainBuilder();
			Assertions.assertEquals(Brain.MAX_HEIGHT,  brainInstance.getWidth());
			Assertions.assertEquals(Brain.MAX_WIDTH,  brainInstance.getHeight());
			Assertions.assertEquals(0,  brainInstance.getDepth());
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("Brain build 03 failed");
		}
		
		BrainLengthException ble01 = Assertions.assertThrows(BrainLengthException.class, () -> BrainBuilder.brainBuilder( -10, 10, 10) );
		Assertions.assertEquals("Height cannot be under 0. ", ble01.getMessage());
		
		BrainLengthException ble02 = Assertions.assertThrows(BrainLengthException.class, () -> BrainBuilder.brainBuilder( 10, -10, 10) );
		Assertions.assertEquals("Width cannot be under 0. ", ble02.getMessage());
		
		BrainLengthException ble03 = Assertions.assertThrows(BrainLengthException.class, () -> BrainBuilder.brainBuilder( 10, 10, -10) );
		Assertions.assertEquals("Depth cannot be under 0. ", ble03.getMessage());
		
		BrainLengthException ble04 = Assertions.assertThrows(BrainLengthException.class, () -> BrainBuilder.brainBuilder( -10, 10) );
		Assertions.assertEquals("Height cannot be under 0. ", ble04.getMessage());
		
		BrainLengthException ble05 = Assertions.assertThrows(BrainLengthException.class, () -> BrainBuilder.brainBuilder( 10, -10) );
		Assertions.assertEquals("Width cannot be under 0. ", ble05.getMessage());
		
	}
	
	@Test
	void testBrainLobes() {
		
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron conception	= new Neuron(0,  10,  5, 1, 1, 2, false, 2);
			Neuron emittersTest = new Neuron(0,  10,  8, 4, 4, 4, false, 0);
	
			int brainHeight		= testBrain.getHeight();
			int brainWidth		= testBrain.getWidth();
			
			testBrain.setLobe(1, 1, 3,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 11, receptorActi, false);
			
			testBrain.setLobe(brainHeight/2, brainWidth/2, 5, 5, conception, true);
			
			testBrain.setLobe(1, 1, brainHeight-3,  8, emittersTest, false);
			testBrain.setLobe(1, 1, brainHeight-3,  9, emittersTest, false);
			testBrain.setLobe(1, 1, brainHeight-3, 10, emittersTest, false);
			testBrain.setLobe(1, 1, brainHeight-3, 11, emittersTest, false);
			
			BrainTests.neuronComparison(receptorActi, testBrain.getNeuronAt(3,  8));
			BrainTests.neuronComparison(receptorActi, testBrain.getNeuronAt(3,  9));
			BrainTests.neuronComparison(receptorActi, testBrain.getNeuronAt(3, 10));
			BrainTests.neuronComparison(receptorActi, testBrain.getNeuronAt(3, 11));
			
			BrainTests.neuronComparison(emittersTest, testBrain.getNeuronAt(17,  8));
			BrainTests.neuronComparison(emittersTest, testBrain.getNeuronAt(17,  9));
			BrainTests.neuronComparison(emittersTest, testBrain.getNeuronAt(17, 10));
			BrainTests.neuronComparison(emittersTest, testBrain.getNeuronAt(17, 11));
			
			IntStream.range(0, 10).forEach( i -> BrainTests.neuronComparison(conception, testBrain.getNeuronAt(5 + i,  5 + i)) );
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed");
		}
		
	}
	
	private static void neuronComparison(Neuron toCompare1, Neuron toCompare2) {
		Assertions.assertEquals(toCompare1.getActivity(), 			toCompare2.getActivity());
		Assertions.assertEquals(toCompare1.getRestState(), 			toCompare2.getRestState());
		Assertions.assertEquals(toCompare1.getThreshold(), 			toCompare2.getThreshold());
		Assertions.assertEquals(toCompare1.getDmin(), 				toCompare2.getDmin());
		Assertions.assertEquals(toCompare1.getDmax(), 				toCompare2.getDmax());
		Assertions.assertEquals(toCompare1.getProximity(), 			toCompare2.getProximity());
		Assertions.assertEquals(toCompare1.getReproductibility(), 	toCompare2.getReproductibility());
		Assertions.assertEquals(toCompare1.getReproduction(), 		toCompare2.getReproduction());
		Assertions.assertEquals(toCompare1.isWTA(), 				toCompare2.isWTA());
	}
	
	// TODO complete these tests !!
	
//	@Test
//	void testGetNeuronBefore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetActivityBefore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetActivityNear() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBestPositionNear() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetNeuronAt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemNeuronAt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetLobe() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testNetworking() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testObservableObject() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testChange() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetState() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetState() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddState() {
//		fail("Not yet implemented");
//	}

}

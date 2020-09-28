package gabywald.biosilico.model.tests;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.BrainBuilder;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Position;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

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
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
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
	
	@Test
	void testGetNeuronBefore01() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			
			testBrain.setLobe(1, 1, 3,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 2,  7, receptor, false);
			testBrain.setLobe(1, 1, 2,  8, receptor, false);
			testBrain.setLobe(1, 1, 2,  9, receptor, false);
			testBrain.setLobe(1, 1, 2, 10, receptor, false);
			testBrain.setLobe(1, 1, 2, 11, receptor, false);
			
			List<Neuron> testList01 = testBrain.getNeuronsBefore(new Position(4, 7), 1);
			Assertions.assertEquals(3, testList01.size());
			
			List<Neuron> testList02 = testBrain.getNeuronsBefore(new Position(4, 7), 2);
			Assertions.assertEquals(4, testList02.size());
			
			List<Neuron> testList03 = testBrain.getNeuronsBefore(new Position(4, 7), 3);
			Assertions.assertEquals(5, testList03.size());
			
			List<Neuron> testList04 = testBrain.getNeuronsBefore(new Position(4, 9), 1);
			Assertions.assertEquals(4, testList04.size());
			
			List<Neuron> testList05 = testBrain.getNeuronsBefore(new Position(4, 9), 2);
			Assertions.assertEquals(6, testList05.size());
			
			List<Neuron> testList06 = testBrain.getNeuronsBefore(new Position(4, 9), 3);
			Assertions.assertEquals(6, testList06.size());
			
			List<Neuron> testList07 = testBrain.getNeuronsBefore(new Position(4, 5), 1);
			Assertions.assertEquals(0, testList07.size());
			
			List<Neuron> testList08 = testBrain.getNeuronsBefore(new Position(4, 5), 2);
			Assertions.assertEquals(1, testList08.size());
			
			List<Neuron> testList09 = testBrain.getNeuronsBefore(new Position(4, 5), 3);
			Assertions.assertEquals(2, testList09.size());
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}
	
	@Test
	void testGetNeuronBefore02() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			
			IntStream.range(0, 20).forEach( i -> {
				try {
					testBrain.setLobe(1, 1, 3, i, receptorActi, false);
				} catch (BrainLengthException | BrainLobeReplaceException e) {
					e.printStackTrace();
					Assertions.fail("BrainLengthException|BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
				}
			} );
			
			IntStream.range(0, 20).forEach( i -> {
				try {
					testBrain.setLobe(1, 1, 2, i, receptor, false);
				} catch (BrainLengthException | BrainLobeReplaceException e) {
					e.printStackTrace();
					Assertions.fail("BrainLengthException|BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
				}
			} );
			
			List<Neuron> testList01 = testBrain.getNeuronsBefore(new Position(4, 7), 1);
			Assertions.assertEquals(4, testList01.size());
			
			List<Neuron> testList02 = testBrain.getNeuronsBefore(new Position(4, 7), 2);
			Assertions.assertEquals(6, testList02.size());
			
			List<Neuron> testList03 = testBrain.getNeuronsBefore(new Position(4, 7), 3);
			Assertions.assertEquals(8, testList03.size());
			
			List<Neuron> testList04 = testBrain.getNeuronsBefore(new Position(4, 9), 1);
			Assertions.assertEquals(4, testList04.size());
			
			List<Neuron> testList05 = testBrain.getNeuronsBefore(new Position(4, 9), 2);
			Assertions.assertEquals(6, testList05.size());
			
			List<Neuron> testList06 = testBrain.getNeuronsBefore(new Position(4, 9), 3);
			Assertions.assertEquals(8, testList06.size());
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		}
	}

	@Test
	void testGetActivityBefore01() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			
			testBrain.setLobe(1, 1, 3,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 2,  7, receptor, false);
			testBrain.setLobe(1, 1, 2,  8, receptor, false);
			testBrain.setLobe(1, 1, 2,  9, receptor, false);
			testBrain.setLobe(1, 1, 2, 10, receptor, false);
			testBrain.setLobe(1, 1, 2, 11, receptor, false);
			
			int test01 = testBrain.getActivityBefore(new Position(4, 7), 1);
			Assertions.assertEquals(1, test01);
			
			int test02 = testBrain.getActivityBefore(new Position(4, 7), 2);
			Assertions.assertEquals(2, test02);
			
			int test03 = testBrain.getActivityBefore(new Position(4, 7), 3);
			Assertions.assertEquals(3, test03);
			
			int test04 = testBrain.getActivityBefore(new Position(4, 9), 1);
			Assertions.assertEquals(2, test04);
			
			int test05 = testBrain.getActivityBefore(new Position(4, 9), 2);
			Assertions.assertEquals(4, test05);
			
			int test06 = testBrain.getActivityBefore(new Position(4, 9), 3);
			Assertions.assertEquals(4, test06);
			
			int test07 = testBrain.getActivityBefore(new Position(4, 5), 1);
			Assertions.assertEquals(0, test07);
			
			int test08 = testBrain.getActivityBefore(new Position(4, 5), 2);
			Assertions.assertEquals(1, test08);
			
			int test09 = testBrain.getActivityBefore(new Position(4, 5), 3);
			Assertions.assertEquals(2, test09);
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}
	
	@Test
	void testGetActivityBefore02() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			
			testBrain.setLobe(1, 1, 3,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 5,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 5,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 5,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 5, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 5, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 2,  7, receptor, false);
			testBrain.setLobe(1, 1, 2,  8, receptor, false);
			testBrain.setLobe(1, 1, 2,  9, receptor, false);
			testBrain.setLobe(1, 1, 2, 10, receptor, false);
			testBrain.setLobe(1, 1, 2, 11, receptor, false);
			
			int test01 = testBrain.getActivityBefore(new Position(4, 7), 1);
			Assertions.assertEquals(1, test01);
			
			int test02 = testBrain.getActivityBefore(new Position(4, 7), 2);
			Assertions.assertEquals(2, test02);
			
			int test03 = testBrain.getActivityBefore(new Position(4, 7), 3);
			Assertions.assertEquals(3, test03);
			
			int test04 = testBrain.getActivityBefore(new Position(4, 9), 1);
			Assertions.assertEquals(2, test04);
			
			int test05 = testBrain.getActivityBefore(new Position(4, 9), 2);
			Assertions.assertEquals(4, test05);
			
			int test06 = testBrain.getActivityBefore(new Position(4, 9), 3);
			Assertions.assertEquals(4, test06);
			
			int test07 = testBrain.getActivityBefore(new Position(4, 5), 1);
			Assertions.assertEquals(0, test07);
			
			int test08 = testBrain.getActivityBefore(new Position(4, 5), 2);
			Assertions.assertEquals(1, test08);
			
			int test09 = testBrain.getActivityBefore(new Position(4, 5), 3);
			Assertions.assertEquals(2, test09);
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}

	@Test
	void testGetActivityNear01() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			
			testBrain.setLobe(1, 1, 3,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 2,  7, receptor, false);
			testBrain.setLobe(1, 1, 2,  8, receptor, false);
			testBrain.setLobe(1, 1, 2,  9, receptor, false);
			testBrain.setLobe(1, 1, 2, 10, receptor, false);
			testBrain.setLobe(1, 1, 2, 11, receptor, false);
			
			int test01 = testBrain.getActivityNear(new Position(4, 7), 1);
			Assertions.assertEquals(1, test01);
			
			int test02 = testBrain.getActivityNear(new Position(4, 7), 2);
			Assertions.assertEquals(2, test02);
			
			int test03 = testBrain.getActivityNear(new Position(4, 7), 3);
			Assertions.assertEquals(3, test03);
			
			int test04 = testBrain.getActivityNear(new Position(4, 9), 1);
			Assertions.assertEquals(2, test04);
			
			int test05 = testBrain.getActivityNear(new Position(4, 9), 2);
			Assertions.assertEquals(4, test05);
			
			int test06 = testBrain.getActivityNear(new Position(4, 9), 3);
			Assertions.assertEquals(4, test06);
			
			int test07 = testBrain.getActivityNear(new Position(4, 5), 1);
			Assertions.assertEquals(0, test07);
			
			int test08 = testBrain.getActivityNear(new Position(4, 5), 2);
			Assertions.assertEquals(1, test08);
			
			int test09 = testBrain.getActivityNear(new Position(4, 5), 3);
			Assertions.assertEquals(2, test09);
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}
	
	@Test
	void testGetActivityNear02() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(200);
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			
			testBrain.setLobe(1, 1, 3,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 3,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 3, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 5,  7, receptorActi, false);
			testBrain.setLobe(1, 1, 5,  8, receptorActi, false);
			testBrain.setLobe(1, 1, 5,  9, receptorActi, false);
			testBrain.setLobe(1, 1, 5, 10, receptorActi, false);
			testBrain.setLobe(1, 1, 5, 11, receptorActi, false);
			
			testBrain.setLobe(1, 1, 2,  7, receptor, false);
			testBrain.setLobe(1, 1, 2,  8, receptor, false);
			testBrain.setLobe(1, 1, 2,  9, receptor, false);
			testBrain.setLobe(1, 1, 2, 10, receptor, false);
			testBrain.setLobe(1, 1, 2, 11, receptor, false);
			
			int test01 = testBrain.getActivityNear(new Position(4, 7), 1);
			Assertions.assertEquals(2, test01);
			
			int test02 = testBrain.getActivityNear(new Position(4, 7), 2);
			Assertions.assertEquals(4, test02);
			
			int test03 = testBrain.getActivityNear(new Position(4, 7), 3);
			Assertions.assertEquals(6, test03);
			
			int test04 = testBrain.getActivityNear(new Position(4, 9), 1);
			Assertions.assertEquals(4, test04);
			
			int test05 = testBrain.getActivityNear(new Position(4, 9), 2);
			Assertions.assertEquals(8, test05);
			
			int test06 = testBrain.getActivityNear(new Position(4, 9), 3);
			Assertions.assertEquals(8, test06);
			
			int test07 = testBrain.getActivityNear(new Position(4, 5), 1);
			Assertions.assertEquals(0, test07);
			
			int test08 = testBrain.getActivityNear(new Position(4, 5), 2);
			Assertions.assertEquals(2, test08);
			
			int test09 = testBrain.getActivityNear(new Position(4, 5), 3);
			Assertions.assertEquals(4, test09);
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}
	
	@Test
	void testWTAisActivated() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptor			= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			Neuron receptorNotWTA	= new Neuron(0, 100, 50, 0, 0, 0, false, 0, false);
			Neuron receptorWTA		= new Neuron(0, 100, 50, 0, 0, 0, false, 0, true);
			
			testBrain.setLobe(1, 10, 1,  1, receptor, 		false);
			testBrain.setLobe(1, 10, 3,  1, receptorNotWTA, false);
			testBrain.setLobe(1, 10, 5,  1, receptorWTA, 	false);
			
			// ***** test if Neuron's instances are presents !!
			BrainTests.testExistence(testBrain, 1, false);
			BrainTests.testExistence(testBrain, 3, false);
			BrainTests.testExistence(testBrain, 5, true);
			
			// NOTE : mValue will apply on this index, other will have aValue
			int aValue = 500;
			int mValue = 750;
			int mIndex = 7;
			
			// ***** test if WTA apply correctly !!
			BrainTests.initValuesToTestWTA(testBrain, 1, aValue, mValue, mIndex, false);
			BrainTests.initValuesToTestWTA(testBrain, 3, aValue, mValue, mIndex, false);
			BrainTests.initValuesToTestWTA(testBrain, 5, aValue, mValue, mIndex, true);
			
			// Check values of activity
			BrainTests.checkWTAactivity(testBrain, 1, aValue, mValue, mIndex, false);
			BrainTests.checkWTAactivity(testBrain, 3, aValue, mValue, mIndex, false);
			BrainTests.checkWTAactivity(testBrain, 5, aValue, mValue, mIndex, true);
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}
	
	private static void testExistence(final Brain testBrain, final int xIndex, final boolean isWTA) {
		IntStream.range(1, 11).forEach( i -> {
			Neuron local = testBrain.getNeuronAt(xIndex, i);
			Assertions.assertNotNull( local );
			Assertions.assertEquals(0, local.getActivity());
			Assertions.assertEquals( isWTA, local.isWTA() );
			Assertions.assertEquals(10, local.getLobe().size());
		} );
	}
	
	private static void initValuesToTestWTA(final Brain testBrain, final int xIndex, final int aValue, final int mValue, final int mIndex, final boolean isWTA) {
		IntStream.range(1, 11).forEach( i -> {
			Neuron local = testBrain.getNeuronAt(xIndex, i);
			Assertions.assertNotNull( local );
			local.addActivity( (i == mIndex) ? mValue : aValue );
			Assertions.assertEquals( isWTA, local.isWTA() );
			// Assertions.assertEquals((i == mIndex) ? ((isWTA) ? 0 : aValue) : mValue, local.getActivity());
			Assertions.assertEquals((i == mIndex) ? mValue : aValue, local.getActivity());
		} );
	}
	
	private static void checkWTAactivity(final Brain testBrain, final int xIndex, final int aValue, final int mValue, final int mIndex, final boolean isWTA) {
		IntStream.range(1, 11).forEach( i -> {
			Neuron local = testBrain.getNeuronAt(xIndex, i);
			Assertions.assertNotNull( local );
			Assertions.assertEquals((i == mIndex) ? mValue : aValue, local.getActivity());
			Assertions.assertEquals( isWTA, local.isWTA() );
			
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "xIndex: " + xIndex + "*aValue: " + aValue + "*mValue: " + mValue + "*mIndex: " + mIndex + "*isWTA: " + isWTA + "*" + local.getActivity() + "::" + i);
			
			if (isWTA) {
				Assertions.assertEquals((i == mIndex), local.isActivated());
			} else {
				Assertions.assertEquals(true, local.isActivated());
			}
			
			Logger.printlnLog(LoggerLevel.LL_DEBUG, "xIndex: " + xIndex + "*aValue: " + aValue + "*mValue: " + mValue + "*mIndex: " + mIndex + "*isWTA: " + isWTA + "*" + local.getActivity() + "::" + i);
			
			Assertions.assertEquals((i == mIndex) ? mValue : ((isWTA) ? 0 : aValue), local.getActivity());
		} );
	}
	
	@Test
	void testGetBestPositionNear() {
		try {
			Brain testBrain		= BrainBuilder.brainBuilder(20, 20);
			
			Neuron receptor		= new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptor.addActivity( 500 );
			
			testBrain.setLobe(1, 1, 1, 1, receptor, false);
			
			Position test01 = testBrain.getBestPositionNear(new Position(1, 1), 1);
			Assertions.assertEquals(new Position(0, 0), test01);
			
			testBrain.setLobe(1, 1, 0, 0, receptor, false);
			
			Position test02 = testBrain.getBestPositionNear(new Position(1, 1), 1);
			Assertions.assertEquals(new Position(0, 1), test02);
			
			testBrain.setLobe(1, 1, 0, 1, receptor, false);
			
			Position test03 = testBrain.getBestPositionNear(new Position(1, 1), 1);
			Assertions.assertEquals(new Position(1, 0), test03);
			
		} catch (BrainLengthException e) {
			e.printStackTrace();
			Assertions.fail("BrainLengthException => failed: {" + e.getMessage() + "}");
		} catch (BrainLobeReplaceException e) {
			e.printStackTrace();
			Assertions.fail("BrainLobeReplaceException => failed: {" + e.getMessage() + "}");
		}
	}

}

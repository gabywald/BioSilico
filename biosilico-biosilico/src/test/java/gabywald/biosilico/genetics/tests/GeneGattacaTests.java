package gabywald.biosilico.genetics.tests;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.exceptions.GeneException;
import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.genetics.Instinct;
import gabywald.biosilico.genetics.StimulusDecision;
import gabywald.biosilico.structures.GeneticTranslator;

class GeneGattacaTests {

	@Test
	void testDetectBooleanValue() {
		try {
			Assertions.assertTrue( GeneGattaca.detectBooleanValue('0') );
			Assertions.assertTrue( GeneGattaca.detectBooleanValue('2') );
			Assertions.assertTrue( GeneGattaca.detectBooleanValue('4') );
			Assertions.assertTrue( GeneGattaca.detectBooleanValue('6') );
			Assertions.assertTrue( GeneGattaca.detectBooleanValue('8') );
			
			Assertions.assertFalse( GeneGattaca.detectBooleanValue('1') );
			Assertions.assertFalse( GeneGattaca.detectBooleanValue('3') );
			Assertions.assertFalse( GeneGattaca.detectBooleanValue('5') );
			Assertions.assertFalse( GeneGattaca.detectBooleanValue('7') );
			Assertions.assertFalse( GeneGattaca.detectBooleanValue('9') );
		} catch (GeneException e) {
			Assertions.fail( e.getMessage() );
		}
		
		for (Character testChar : Arrays.asList( 'a', 'z', '.' ) ) {
			GeneException ge = Assertions.assertThrows(GeneException.class, () -> GeneGattaca.detectBooleanValue( testChar ) );
			Assertions.assertEquals("Not a digit [" + testChar + "]", ge.getMessage());
		}

	}

	@Test
	void testDetectIntegerValue() {
		try {
			Assertions.assertEquals(  1, GeneGattaca.detectIntegerValue( '0' , '1' ) );
			Assertions.assertEquals( 11, GeneGattaca.detectIntegerValue( '1' , '1' ) );
			Assertions.assertEquals( 10, GeneGattaca.detectIntegerValue( '1' , '0' ) );
			
			Assertions.assertEquals(100, GeneGattaca.detectIntegerValue( '1' , '0', '0' ) );
			Assertions.assertEquals(102, GeneGattaca.detectIntegerValue( '1' , '0', '2' ) );
		} catch (GeneException e) {
			Assertions.fail( e.getMessage() );
		}
		
		for (Character testChar : Arrays.asList( 'a', 'z', '.' ) ) {
			GeneException ge = Assertions.assertThrows(GeneException.class, () -> GeneGattaca.detectIntegerValue( testChar ) );
			Assertions.assertEquals("Not a digit [" + testChar + "] in {" + testChar + "}", ge.getMessage());
		}
	}
	
	@Test
	void testDetectIntValue() {
		Assertions.assertEquals(  1, GeneGattaca.detectIntValue( 0 , 1 ) );
		Assertions.assertEquals( 11, GeneGattaca.detectIntValue( 1 , 1 ) );
		Assertions.assertEquals( 10, GeneGattaca.detectIntValue( 1 , 0 ) );
		
		Assertions.assertEquals(100, GeneGattaca.detectIntValue( 1 , 0 , 0 ) );
		Assertions.assertEquals(102, GeneGattaca.detectIntValue( 1 , 0 , 2 ) );
		Assertions.assertEquals(123, GeneGattaca.detectIntValue( 1 , 2 , 3 ) );
	}

	@Test
	void testGetInstance() {
		// InitialConcentration : "M024600000000025:010100*"
		GeneGattaca icExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca("M024600000000025:010100*") );
		Assertions.assertNotNull( icExpected );
		Assertions.assertEquals(InitialConcentration.class, icExpected.getClass());
		
		GeneGattaca brExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca("M135600099900025:001010002010003010004010005*") );
		Assertions.assertNotNull( brExpected );
		Assertions.assertEquals(BiochemicalReaction.class, brExpected.getClass());
		
		GeneGattaca brainExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca("M024600000000025:30303030*") );
		Assertions.assertNotNull( brainExpected );
		Assertions.assertEquals(BrainGene.class, brainExpected.getClass());
		
		for (String sequence : Arrays.asList(	"M024600000000025:00001000100000000010003013000005*", "M024600000000025:00001000100101600410002011529005*", 
												"M024600000000025:00001000100801600410003011529155*", "M024600000000025:00001000100200400401003013001005*")) {
			GeneGattaca brainlobeExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca( sequence ) );
			Assertions.assertNotNull( brainlobeExpected );
			Assertions.assertEquals(BrainLobeGene.class, brainlobeExpected.getClass());
		}
		
		for (String sequence : Arrays.asList(	"M024600099900025:001010010001502*", "M024600099900025:001010010001512*" )) {
			GeneGattaca erExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca( sequence ) );
			Assertions.assertNotNull( erExpected );
			Assertions.assertEquals(EmitterReceptor.class, erExpected.getClass());
		}
		
		for (String sequence : Arrays.asList(	"M024600099900025:13800100880020020850*", "M024600099900025:12800100880020020850*", 
												"M024600099900025:03800100880020020850*", "M024600099900025:02800100880020020850*" )) {
			GeneGattaca sdExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca( sequence ) );
			Assertions.assertNotNull( sdExpected );
			Assertions.assertEquals(StimulusDecision.class, sdExpected.getClass());
		}
		
		for (String sequence : Arrays.asList(	"M024600099900025:0010991055502002012*", "M024600099900025:0010991055502002000*" )) {
			GeneGattaca inExpected	= GeneGattaca.getInstance( GeneticTranslator.reverseSequenceGattaca( sequence ) );
			Assertions.assertNotNull( inExpected );
			Assertions.assertEquals(Instinct.class, inExpected.getClass());
		}
		
	}
	
	// TODO Tests above to to against inheritant classes
	
//	@Test
//	void testReverseTranslation() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testTranslation() {
//		fail("Not yet implemented");
//	}

}

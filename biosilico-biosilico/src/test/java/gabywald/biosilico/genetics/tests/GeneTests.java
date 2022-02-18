package gabywald.biosilico.genetics.tests;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.interfaces.IGeneMutation;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
class GeneTests {
	
	public static String randomSequenceGenerator(char[] alphabet) {
		StringBuilder sbToReturn = new StringBuilder();
		Random rand = new Random();
		int size = rand.nextInt(5000);
		IntStream.range(0, size).forEach( i -> {
			sbToReturn.append( alphabet[rand.nextInt(alphabet.length)] );
		});
		return sbToReturn.toString();
	}
	
	@Test
	void testObtainValue() {
		Assertions.assertEquals(  0, Gene.obtainValue(0, 99,   0));
		Assertions.assertEquals( 99, Gene.obtainValue(0, 99,  99));
		Assertions.assertEquals( 99, Gene.obtainValue(0, 99, 100));
		Assertions.assertEquals(  0, Gene.obtainValue(0, 99,  -5));
		Assertions.assertEquals( 99, Gene.obtainValue(0, 99, 200));
	}
	
	@Test
	void testconvert0to999() {
		Assertions.assertEquals("000", Gene.convert0to999( 0 ));
		Assertions.assertEquals("001", Gene.convert0to999( 1 ));
		Assertions.assertEquals("099", Gene.convert0to999( 99 ));
	}
	
	@Test
	void testconvert0to99() {
		Assertions.assertEquals("00", Gene.convert0to99( 0 ));
		Assertions.assertEquals("01", Gene.convert0to99( 1 ));
		Assertions.assertEquals("99", Gene.convert0to99( 99 ));
	}

	@Test
	void testLoadGene() {
		Assertions.assertNull( Gene.loadGene("aguc") );
		Assertions.assertNull( Gene.loadGene("agucagucagucagucaguc") );
		Assertions.assertNull( Gene.loadGene( GeneTests.randomSequenceGenerator( new char[]{ 'a', 'g', 'u', 'c' } ) ) );
		
		Assertions.assertNull( Gene.loadGene("acgt") );
		Assertions.assertNull( Gene.loadGene("acgtacgtacgtacgtacgt") );
		Assertions.assertNull( Gene.loadGene( GeneTests.randomSequenceGenerator( new char[]{ 'a', 'c', 'g', 't' } ) ) );
		
		Assertions.assertNull( Gene.loadGene("UBVP") );
		Assertions.assertNull( Gene.loadGene("UBVPUBVPUBVPUBVPUBVP") );
		Assertions.assertNull( Gene.loadGene( GeneTests.randomSequenceGenerator( new char[]{ 'U', 'B', 'V', 'P' } ) ) );
		
		Assertions.assertNull( Gene.loadGene("random") );
		Assertions.assertNull( Gene.loadGene("randomrandomrandomrandomrandom") );
		Assertions.assertNull( Gene.loadGene( GeneTests.randomSequenceGenerator( new char[]{ 'r', 'a', 'n', 'd', 'o', 'm' } ) ) );
		
		Assertions.assertNull( Gene.loadGene("RANDOM") );
		Assertions.assertNull( Gene.loadGene("RANDOMRANDOMRANDOMRANDOMRANDOM") );
		Assertions.assertNull( Gene.loadGene( GeneTests.randomSequenceGenerator( new char[]{ 'R', 'A', 'N', 'D', 'O', 'M' } ) ) );
	}
	
//	@Test
//	void testIGeneMutationMutateRate100() {
//		// Some statistical analysis !
//		int sum			= 0;
//		int mutRate		= 50; // %
//		int testTotal	= 100;
//		for (int i = 0 ; i < testTotal ; i++) { 
//			sum += (IGeneMutation.mutate(mutRate)) ? 1 : 0;
//		}
//		int tolerance = testTotal / 100 * 5; // 5 % tolerance
//		int attempted = (testTotal / 100 * mutRate);
//		Assertions.assertEquals(attempted, sum, tolerance);
//	}
	
	@Test
	void testIGeneMutationMutateRate1000() {
		// Some statistical analysis !
		int sum			= 0;
		int mutRate		= 50; // %
		int testTotal	= 1000;
		for (int i = 0 ; i < testTotal ; i++) { 
			sum += (IGeneMutation.mutate(mutRate)) ? 1 : 0;
		}
		int tolerance = testTotal / 100 * 5;  // 5 % tolerance
		int attempted = (testTotal / 100 * mutRate);
		Assertions.assertEquals(attempted, sum, tolerance);
	}
	
	@Test
	void testIGeneMutationMutateRate10000() {
		// Some statistical analysis !
		int sum			= 0;
		int mutRate		= 50; // %
		int testTotal	= 10000;
		for (int i = 0 ; i < testTotal ; i++) { 
			sum += (IGeneMutation.mutate(mutRate)) ? 1 : 0;
		}
		int tolerance = testTotal / 100 * 5;  // 5 % tolerance
		int attempted = (testTotal / 100 * mutRate);
		Assertions.assertEquals(attempted, sum, tolerance);
	}
	
	@Test
	void testIGeneMutationMutateRate100000() {
		// Some statistical analysis !
		int sum			= 0;
		int mutRate		= 50; // %
		int testTotal	= 100000;
		for (int i = 0 ; i < testTotal ; i++) { 
			sum += (IGeneMutation.mutate(mutRate)) ? 1 : 0;
		}
		int tolerance = testTotal / 100 * 5;  // 5 % tolerance
		int attempted = (testTotal / 100 * mutRate);
		Assertions.assertEquals(attempted, sum, tolerance);
	}
	
	@Test
	void testIGeneMutationMutateValueMoreMax() {
		// TODO [Unit Tests]IGeneMutation.mutate(int value, boolean moreOrLess, int max) 
	}

}

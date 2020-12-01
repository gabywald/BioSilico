package gabywald.creatures.genetics.simple.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.GeneCreatures1;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class GeneCreatures1Tests {
	
	@Test
	void testGeneCreatures1() {
		String nameType = "type";
		UnsignedByte[] header = UnsignedByte.headerCutterBytes( "1234567" );
		int attempted = 0;
		
		GeneCreatures1 gc1 = new GeneCreatures1(nameType, header, attempted);
		Assertions.assertNotNull( gc1 );
		Assertions.assertEquals(nameType, gc1.getType());
		Assertions.assertEquals(header, gc1.getHeader());
		Assertions.assertEquals(attempted, gc1.getAttemptedLength());
		Assertions.assertEquals("GeneCreatures1 ( type , [49, 50, 51, 52, 53, 54, 55] )	 contents: []\n", 
								gc1.toString());
		Assertions.assertEquals("type : [49, 50, 51, 52, 53, 54, 55] => []", gc1.printInline());
		
		gc1.addContent(new UnsignedByte(42));
		Assertions.assertEquals("type : [49, 50, 51, 52, 53, 54, 55] => [42]", gc1.printInline());
		
		Assertions.assertEquals(0, gc1.getHaserror());
		gc1.oneMoreError();
		Assertions.assertEquals(1, gc1.getHaserror());
		gc1.oneMoreError();
		Assertions.assertEquals(2, gc1.getHaserror());
		
		gc1.addContent(new UnsignedByte(42));
		Assertions.assertEquals("type : [49, 50, 51, 52, 53, 54, 55] => [42, 42]\t has (2) errors ", gc1.printInline());
		
		gc1.addContents(new UnsignedByte(41), new UnsignedByte(50), new UnsignedByte(1));
		Assertions.assertEquals("type : [49, 50, 51, 52, 53, 54, 55] => [42, 42, 41, 50, 1]\t has (2) errors ", gc1.printInline());
		Assertions.assertEquals("GeneCreatures1 ( type , [49, 50, 51, 52, 53, 54, 55] )	 contents: [42, 42, 41, 50, 1]\n\t has (2) errors \n", 
									gc1.toString());
		
		gc1.addContentSTR( "EVE_" );gc1.addContentSTR( "ADAM" );
		Assertions.assertEquals("type : [49, 50, 51, 52, 53, 54, 55] => [EVE_, ADAM]\t has (2) errors ", gc1.printInline());
		Assertions.assertEquals("GeneCreatures1 ( type , [49, 50, 51, 52, 53, 54, 55] )	 contents: [EVE_, ADAM]\n\t has (2) errors \n",
									gc1.toString());
	}
	
}

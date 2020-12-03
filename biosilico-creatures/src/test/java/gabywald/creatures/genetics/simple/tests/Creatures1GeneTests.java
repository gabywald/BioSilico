package gabywald.creatures.genetics.simple.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.Creatures1Gene;
import gabywald.creatures.genetics.simple.GeneTypeSubType;
import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class Creatures1GeneTests {
	
	@Test
	void testGeneCreatures1() {
		UnsignedByte[] header = UnsignedByte.headerCutterBytes( "1234567" );
		
		GeneTypeSubType gtst = GeneTypeSubType.getGeneTypeSubType(1, 1);
		
		Creatures1Gene gc1 = new Creatures1Gene(gtst, header);
		Assertions.assertNotNull( gc1 );
		Assertions.assertEquals(gtst, gc1.getType());
		Assertions.assertEquals(header, gc1.getHeader());
		Assertions.assertEquals("GeneCreatures1 ( " + gtst.getShortName() + " , [49, 50, 51, 52, 53, 54, 55] )	 contents: []\n", 
								gc1.toString());
		Assertions.assertEquals("" + gtst.getShortName() + " : [49, 50, 51, 52, 53, 54, 55] => []", gc1.printInline());
		
		gc1.addContent(new UnsignedByte(42));
		Assertions.assertEquals("" + gtst.getShortName() + " : [49, 50, 51, 52, 53, 54, 55] => [42]", gc1.printInline());
		
		Assertions.assertEquals(0, gc1.getHaserror());
		gc1.oneMoreError();
		Assertions.assertEquals(1, gc1.getHaserror());
		gc1.oneMoreError();
		Assertions.assertEquals(2, gc1.getHaserror());
		
		gc1.addContent(new UnsignedByte(42));
		Assertions.assertEquals("" + gtst.getShortName() + " : [49, 50, 51, 52, 53, 54, 55] => [42, 42]\t has (2) errors ", gc1.printInline());
		
		gc1.addContents(new UnsignedByte(41), new UnsignedByte(50), new UnsignedByte(1));
		Assertions.assertEquals("" + gtst.getShortName() + " : [49, 50, 51, 52, 53, 54, 55] => [42, 42, 41, 50, 1]\t has (2) errors ", gc1.printInline());
		Assertions.assertEquals("GeneCreatures1 ( " + gtst.getShortName() + " , [49, 50, 51, 52, 53, 54, 55] )	 contents: [42, 42, 41, 50, 1]\n\t has (2) errors \n", 
									gc1.toString());
		
		gc1.addContentSTR( "EVE_" );gc1.addContentSTR( "ADAM" );
		Assertions.assertEquals("" + gtst.getShortName() + " : [49, 50, 51, 52, 53, 54, 55] => [EVE_, ADAM]\t has (2) errors ", gc1.printInline());
		Assertions.assertEquals("GeneCreatures1 ( " + gtst.getShortName() + " , [49, 50, 51, 52, 53, 54, 55] )	 contents: [EVE_, ADAM]\n\t has (2) errors \n",
									gc1.toString());
	}
	
}

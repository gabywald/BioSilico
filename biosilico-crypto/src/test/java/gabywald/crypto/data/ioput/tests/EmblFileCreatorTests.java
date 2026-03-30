package gabywald.crypto.data.ioput.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.ioput.EmblFileCreator;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class EmblFileCreatorTests {
	
	@Test
	void testEmblFileCreatorNullNull() {
		EmblFileCreator ffc = new EmblFileCreator(null, null);
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(0, ffc.getEncodedCont().size());
		Assertions.assertEquals(0, ffc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testEmblFileCreatorEmptyEmpty() {
		EmblFileCreator ffc = new EmblFileCreator("", "");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(0, ffc.getEncodedCont().size());
		Assertions.assertEquals(0, ffc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testEmblFileCreatorDataPath() {
		EmblFileCreator ffc = new EmblFileCreator("//path/to/data", "");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("CHCHCVCKCPDECHCPCTCHDACKCPCK", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testEmblFileCreatorDataContent() {
		EmblFileCreator ffc = new EmblFileCreator("", "some content");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctca", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}

	@Test
	void testEmblFileCreator01() {
		EmblFileCreator ffc = new EmblFileCreator("//path/to/data", "some content");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("CHCHCVCKCPDECHCPCTCHDACKCPCK", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctca", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testEmblFileCreator02() {
		EmblFileCreator ffc = new EmblFileCreator("//path/to/data", "some content" + StringUtils.repeat("acgt", 200));
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("CHCHCVCKCPDECHCPCTCHDACKCPCK", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctcacgaccgat"
				+ "cgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctct"
				+ "cacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgac"
				+ "cgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcg"
				+ "ctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctca"
				+ "cgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccg"
				+ "atcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgct"
				+ "ctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacg"
				+ "accgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgat"
				+ "cgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctct"
				+ "cacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgac"
				+ "cgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcg"
				+ "ctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctca"
				+ "cgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccg"
				+ "atcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgct"
				+ "ctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacg"
				+ "accgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgat"
				+ "cgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctct"
				+ "cacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgac"
				+ "cgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcg"
				+ "ctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctca"
				+ "cgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccg"
				+ "atcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgct"
				+ "ctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacg"
				+ "accgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgat"
				+ "cgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctct"
				+ "cacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgac"
				+ "cgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcg"
				+ "ctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctca"
				+ "cgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccg"
				+ "atcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgct"
				+ "ctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacg"
				+ "accgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgat"
				+ "cgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctct"
				+ "cacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgac"
				+ "cgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcg"
				+ "ctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctca"
				+ "cgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccg"
				+ "atcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgct"
				+ "ctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacg"
				+ "accgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgat"
				+ "cgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctct"
				+ "cacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgac"
				+ "cgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcg"
				+ "ctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctca"
				+ "cgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccgatcgctctcacgaccg"
				+ "atcgctctcacgaccgatcgctctcacgaccgatcgctctca", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}

}

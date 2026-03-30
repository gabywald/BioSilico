package gabywald.crypto.data.ioput.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.ioput.DirectFileCreator;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
class DirectlFileCreatorTests {
	
	@Test
	void testDirectFileCreatorNullNull() {
		DirectFileCreator ffc = new DirectFileCreator(null, null);
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(0, ffc.getEncodedCont().size());
		Assertions.assertEquals(0, ffc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testDirectFileCreatorEmptyEmpty() {
		DirectFileCreator ffc = new DirectFileCreator("", "");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(0, ffc.getEncodedCont().size());
		Assertions.assertEquals(0, ffc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testDirectFileCreatorDataPath() {
		DirectFileCreator ffc = new DirectFileCreator("//path/to/data", "");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("CHCHCVCKCPDECHCPCTCHDACKCPCK", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testDirectFileCreatorDataContent() {
		DirectFileCreator ffc = new DirectFileCreator("", "some content");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctca", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}

	@Test
	void testDirectFileCreator01() {
		DirectFileCreator ffc = new DirectFileCreator("//path/to/data", "some content");
		Assertions.assertNotNull( ffc );
		
		Assertions.assertEquals(1, ffc.getEncodedCont().size());
		Assertions.assertEquals(1, ffc.getEncodedPath().size());
		
		Assertions.assertEquals("CHCHCVCKCPDECHCPCTCHDACKCPCK", ffc.getEncodedPath().get(0));
		Assertions.assertEquals("ctatcgttcgtccgccagaacgatcgttcgtgctcacgcccgtgctca", ffc.getEncodedCont().get(0));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, ffc.getFullEncryption() );
	}
	
	@Test
	void testDirectFileCreator02() {
		DirectFileCreator ffc = new DirectFileCreator("//path/to/data", "some content" + StringUtils.repeat("acgt", 200));
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

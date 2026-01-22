package gabywald.crypto.data.ioput.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.ioput.GenBankFileCreator;
import gabywald.global.data.StringUtils;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class GenBankFileCreatorTests {

	@Test
	void testGenBankFileCreator() {
		GenBankFileCreator gbfc = new GenBankFileCreator();
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(0, gbfc.getEncodedCont().size());
		Assertions.assertEquals(0, gbfc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.getFullEncryption() );
	}

	@Test
	void testGenBankFileCreatorString() {
		GenBankFileCreator gbfc = new GenBankFileCreator("some content");
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(1, gbfc.getEncodedCont().size());
		Assertions.assertEquals(0, gbfc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.getFullEncryption() );
	}

	@Test
	void testGenBankFileCreatorStringString01() {

		GenBankFileCreator gbfc = new GenBankFileCreator("//path/to/data", "some content");
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(1, gbfc.getEncodedCont().size());
		Assertions.assertEquals(1, gbfc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.getFullEncryption() );
		
	}
	
	@Test
	void testGenBankFileCreatorStringString02() {

		GenBankFileCreator gbfc = new GenBankFileCreator("//path/to/data", "some content\n" + StringUtils.repeat("acgt", 100));
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(1, gbfc.getEncodedCont().size());
		Assertions.assertEquals(1, gbfc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.getFullEncryption() );
	}
	
	@Test
	void testGenBankFileCreatorStringString03() {

		GenBankFileCreator gbfc = new GenBankFileCreator("//path/to/data", "someTXT");
		Assertions.assertNotNull( gbfc );
		
		Assertions.assertEquals(1, gbfc.getEncodedCont().size());
		Assertions.assertEquals(1, gbfc.getEncodedPath().size());
		
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.toString() );
		Logger.printlnLog(LoggerLevel.LL_NONE, gbfc.getFullEncryption() );
	}
	
	// TODO complete these tests !! GenBankFileCreatorTests

}

package gabywald.crypto.launcher.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.launcher.BioSilicoCryptoCommand;
import gabywald.crypto.launcher.BioSilicoCryptoCommand.LogLevel;

/**
 * 
 * @author Gabriel Chandesris (2026)
 */
class BioSilicoCryptoCommandTests {

	@Test
	void test() {
		BioSilicoCryptoCommand bscc = new BioSilicoCryptoCommand();
		Assertions.assertNotNull(bscc);
		Assertions.assertNotNull(bscc.getLogLevel());
		// none, error, warn, info, debug, trace
		// trace, debug, info, warn, error, none
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.trace));
		
		Assertions.assertEquals(LogLevel.TheEnum.none, bscc.setLogLevel(LogLevel.TheEnum.trace) );
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.trace));
		
		Assertions.assertEquals(LogLevel.TheEnum.trace, bscc.setLogLevel(LogLevel.TheEnum.error) );
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.trace));
		
		Assertions.assertEquals(LogLevel.TheEnum.error, bscc.setLogLevel(LogLevel.TheEnum.warn) );
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.trace));
		
		Assertions.assertEquals(LogLevel.TheEnum.warn, bscc.setLogLevel(LogLevel.TheEnum.info) );
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.trace));
		
		Assertions.assertEquals(LogLevel.TheEnum.info, bscc.setLogLevel(LogLevel.TheEnum.debug) );
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.trace));
		
		Assertions.assertEquals(LogLevel.TheEnum.debug, bscc.setLogLevel(LogLevel.TheEnum.none) );
		Assertions.assertTrue(bscc.isLogEnabled(LogLevel.TheEnum.none));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.error));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.warn));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.info));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.debug));
		Assertions.assertFalse(bscc.isLogEnabled(LogLevel.TheEnum.trace));
	}

}

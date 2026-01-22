package gabywald.javabio.data.composition.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.GenBankFormat;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2011, 2020, 2022, 2026)
 */
public class GenBankTests {

	@Test
	public void testFromFile01() {
		List<GenBankFormat> tmp = GenBankFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "genbankdata01.txt" ) );
		
		Logger.printlnLog(LoggerLevel.LL_NONE, tmp.get(0).toString());
		
		Assertions.assertEquals(1, tmp.size());
	}
	
	@Test
	public void testFromFile02() {
		List<GenBankFormat> tmp = GenBankFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "genbankdata02.txt" ));
		
		Logger.printlnLog(LoggerLevel.LL_NONE, tmp.get(0).toString());
		
		Assertions.assertEquals(1, tmp.size());
	}
	
}

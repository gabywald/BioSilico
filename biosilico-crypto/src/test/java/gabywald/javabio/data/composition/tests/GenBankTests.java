package gabywald.javabio.data.composition.tests;

import java.util.List;

import org.junit.jupiter.api.Test;

import gabywald.crypto.data.GenBankFormat;
import junit.framework.TestCase;

/**
 * 
 * @author Gabriel Chandesris (2011, 2020)
 */
public class GenBankTests {

	@Test
	public void testFromFile01() {
		List<GenBankFormat> tmp = GenBankFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "genbankdata01.txt" ) );
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}
	
	@Test
	public void testFromFile02() {
		List<GenBankFormat> tmp = GenBankFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "genbankdata02.txt" ));
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}
	
}

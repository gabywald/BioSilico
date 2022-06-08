package gabywald.javabio.data.composition.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.EmblFormat;

/**
 * 
 * @author Gabriel Chandesris (2011, 2020, 2022)
 */
class EmblTests {

	@Test
	void testFromFile01() {
		List<EmblFormat> tmp = EmblFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "embldata01.txt" ) );
		
		System.out.println(tmp.get(0).toString());
		
		Assertions.assertEquals(1, tmp.size());
	}
	
	@Test
	void testFromFile02() {
		List<EmblFormat> tmp = EmblFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "embldata02.txt" ) );
		
		System.out.println(tmp.get(0).toString());
		
		Assertions.assertEquals(1, tmp.size());
	}
	
	@Test
	void testFromFile03() {
		List<EmblFormat> tmp = EmblFormat.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "embldata03.txt" ) );
		
		System.out.println(tmp.get(0).toString());
		
		Assertions.assertEquals(1, tmp.size());
	}
	
}

package gabywald.utilities.others.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabywald.utilities.others.IntegerToHexa;

class IntegerToHexaTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDecimal2hexadecimal() {
		Assertions.assertEquals("0", IntegerToHexa.decimal2hexadecimal( 0));
		Assertions.assertEquals("1", IntegerToHexa.decimal2hexadecimal( 1));
		Assertions.assertEquals("2", IntegerToHexa.decimal2hexadecimal( 2));
		Assertions.assertEquals("3", IntegerToHexa.decimal2hexadecimal( 3));
		Assertions.assertEquals("4", IntegerToHexa.decimal2hexadecimal( 4));
		Assertions.assertEquals("5", IntegerToHexa.decimal2hexadecimal( 5));
		Assertions.assertEquals("6", IntegerToHexa.decimal2hexadecimal( 6));
		Assertions.assertEquals("7", IntegerToHexa.decimal2hexadecimal( 7));
		Assertions.assertEquals("8", IntegerToHexa.decimal2hexadecimal( 8));
		Assertions.assertEquals("9", IntegerToHexa.decimal2hexadecimal( 9));
		Assertions.assertEquals("A", IntegerToHexa.decimal2hexadecimal(10));
		Assertions.assertEquals("B", IntegerToHexa.decimal2hexadecimal(11));
		Assertions.assertEquals("C", IntegerToHexa.decimal2hexadecimal(12));
		Assertions.assertEquals("D", IntegerToHexa.decimal2hexadecimal(13));
		Assertions.assertEquals("E", IntegerToHexa.decimal2hexadecimal(14));
		Assertions.assertEquals("F", IntegerToHexa.decimal2hexadecimal(15));
		Assertions.assertEquals("10", IntegerToHexa.decimal2hexadecimal(16));
		Assertions.assertEquals("11", IntegerToHexa.decimal2hexadecimal(17));
		
		Assertions.assertEquals("20", IntegerToHexa.decimal2hexadecimal(32));
		Assertions.assertEquals("21", IntegerToHexa.decimal2hexadecimal(33));
		
		Assertions.assertEquals("40", IntegerToHexa.decimal2hexadecimal(64));
		Assertions.assertEquals("80", IntegerToHexa.decimal2hexadecimal(128));
		Assertions.assertEquals("C0", IntegerToHexa.decimal2hexadecimal(192));
		
		Assertions.assertEquals("FF", IntegerToHexa.decimal2hexadecimal(255));
	}

}

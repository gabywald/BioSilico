package gabywald.creatures.genetics.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.model.UnsignedByte;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 * TODO review and replace "System.out.println(" with "Logger.printlnLog(LoggerLevel.LL_NONE, "
 */
public class UnsignedByteTests {
	
	@Test
	public void testUnsignedByte_splitToNChar() {
		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String[] attempted01 = { "ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX", "YZ" };
		String[] attempted02 = { "ABCD", "EFGH", "IJKL", "MNOP", "QRST", "UVWX", "YZ" };
		String[] attempted03 = { "ABCDE", "FGHIJ", "KLMNO", "PQRST", "UVWXY", "Z" };
		Assertions.assertArrayEquals(attempted01, UnsignedByte.splitToNChar(text, 3) );
		Assertions.assertArrayEquals(attempted02, UnsignedByte.splitToNChar(text, 4) );
		Assertions.assertArrayEquals(attempted03, UnsignedByte.splitToNChar(text, 5) );
	}
	
	@Test
	public void testUnsignedByte_headerCutter() {
		String[] attempted = {"0", "1", "2", "3", "4", "5", "6"};
		Assertions.assertArrayEquals(attempted, UnsignedByte.headerCutter( "0123456" ) );
	}
	
	@Test
	public void testUnsignedByte_headerCutterBytes() {
		int valueBase = 48; // ***** 0 <=> 48 *****
		UnsignedByte[] attempted = new UnsignedByte[7];
		for (int i = 0 ; i < attempted.length ; i++) 
			{ attempted[i] = new UnsignedByte( i + valueBase ); }
		UnsignedByte[] result = UnsignedByte.headerCutterBytes( "0123456" );
		Assertions.assertArrayEquals(attempted, result);
	}

	@Test
	public void testUB001() {
		int value = 0;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB002() {
		int value = 1;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB003() {
		int value = -1;int expected = 0;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB004() {
		int value = -127;int expected = 0;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB005() {
		int value = 100;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB006() {
		int value = 128;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB007() {
		int value = 127;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB008() {
		int value = -256;int expected = 0;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB009() {
		int value = 253;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}

	@Test
	public void testUB010() {
		int value = 254;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB011() {
		int value = 255;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB012() {
		int value = 256;int expected = 255;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB013() {
		int value = 257;int expected = 255;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
	@Test
	public void testUB014() {
		int value = 258;int expected = 255;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		Assertions.assertEquals(expected, ub.getValue());
	}
	
}

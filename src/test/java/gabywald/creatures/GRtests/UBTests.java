package gabywald.creatures.GRtests;

import gabywald.creatures.model.UnsignedByte;
import junit.framework.TestCase;

public class UBTests extends TestCase {

	public void testUB001() {
		int value = 0;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB002() {
		int value = 1;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB003() {
		int value = -1;int expected = 0;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB004() {
		int value = -127;int expected = 0;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB005() {
		int value = 100;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB006() {
		int value = 128;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB007() {
		int value = 127;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB008() {
		int value = -256;int expected = 0;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	
	
	
	public void testUB009() {
		int value = 253;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB010() {
		int value = 254;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB011() {
		int value = 255;int expected = value;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB012() {
		int value = 256;int expected = 255;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB013() {
		int value = 257;int expected = 255;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
	
	public void testUB014() {
		int value = 258;int expected = 255;
		UnsignedByte ub = new UnsignedByte(value);
		System.out.println("\t[" + value + "] => [" + ub.getShort() + " / " + ub.getValue() + "] ?==? [" + expected + "]");
		TestCase.assertEquals(expected, ub.getValue());
	}
}

package gabywald.javabio.data.composition.tests;

import java.util.List;

import gabywald.javabio.data.Embl;

import junit.framework.TestCase;

public class EmblTests extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFromString01() {
		List<Embl> tmp = Embl.fromString(EmblTestsHelper.DATA01);
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}
	
	public void testFromString02() {
		List<Embl> tmp = Embl.fromString(EmblTestsHelper.DATA02);
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}
	
	public void testFromString03() {
		List<Embl> tmp = Embl.fromString(EmblTestsHelper.DATA03);
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}
	
}

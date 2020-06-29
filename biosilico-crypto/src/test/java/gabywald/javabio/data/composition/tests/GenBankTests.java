package gabywald.javabio.data.composition.tests;

import java.util.List;

import gabywald.crypto.data.GenBank;
import junit.framework.TestCase;

public class GenBankTests extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFromString01() {
		List<GenBank> tmp = GenBank.fromString(GenBankTestsHelper.DATA01);
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}
	
	
	public void testFromString02() {
		List<GenBank> tmp = GenBank.fromString(GenBankTestsHelper.DATA02);
		
		System.out.println(tmp.get(0).toString());
		
		TestCase.assertEquals(1, tmp.size());
	}

}

package gabywald.javabio.data.composition.tests;

import java.util.List;

import gabywald.crypto.data.composition.BibTeX;
import junit.framework.TestCase;

public class BibTeXTests extends TestCase {
	

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFromString01() {
		List<BibTeX> tmp = BibTeX.fromString(BibTeXTestsHelper.DATA01);
		
		System.out.println(tmp.get(0).toStringEverything());
		
		TestCase.assertEquals(1, tmp.size());
	}

}

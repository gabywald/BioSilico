package gabywald.javabio.data.composition.tests;

import java.util.List;

import org.junit.jupiter.api.Test;

import gabywald.crypto.data.composition.BibTeX;
import junit.framework.TestCase;

/**
 * 
 * @author Gabriel Chandesris (2011, 2020)
 */
public class BibTeXTests {

	@Test
	void testFromFile01() {
		
		List<BibTeX> tmp = BibTeX.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "bibtexdata.txt" ) );

		System.out.println(tmp.get(0).toStringEverything());

		TestCase.assertEquals(1, tmp.size());
	}

}

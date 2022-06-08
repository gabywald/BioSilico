package gabywald.javabio.data.composition.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.crypto.data.composition.BibTeX;

/**
 * 
 * @author Gabriel Chandesris (2011, 2020, 2022)
 */
public class BibTeXTests {

	@Test
	void testFromFile01() {
		
		List<BibTeX> tmp = BibTeX.fromString( TestsHelper.getDataFromFile(this.getClass().getClassLoader(), "bibtexdata.txt" ) );

		System.out.println(tmp.get(0).toStringEverything());

		Assertions.assertEquals(1, tmp.size());
	}

}

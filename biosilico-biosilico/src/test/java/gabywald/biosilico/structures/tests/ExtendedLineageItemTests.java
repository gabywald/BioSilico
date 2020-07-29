package gabywald.biosilico.structures.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.biosilico.structures.ExtendedLineageItem;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class ExtendedLineageItemTests {
	
	public static final String scName	= "Scientific Name";
	public static final String fixRank	= "Lineage Rank";
	public static final String fixUID	= "0123456789";

	@Test
	void testExtendedLineageItemString() {
		ExtendedLineageItem eli = new ExtendedLineageItem( scName );
		Assertions.assertNotNull( eli );
		Assertions.assertEquals(scName, eli.getScientificName());
		Assertions.assertEquals(ExtendedLineageItem.DEFAULT_RANK, eli.getRank());
		Assertions.assertEquals(ExtendedLineageItem.DEFAULT_UID, eli.getUniqueID());
		
		Assertions.assertEquals(ExtendedLineageItemTests.generateELIstring(ExtendedLineageItem.DEFAULT_UID, scName, ExtendedLineageItem.DEFAULT_RANK), eli.toString());
	}

	@Test
	void testExtendedLineageItemStringString() {
		ExtendedLineageItem eli = new ExtendedLineageItem( scName, fixRank );
		Assertions.assertNotNull( eli );
		Assertions.assertEquals(scName, eli.getScientificName());
		Assertions.assertEquals(fixRank, eli.getRank());
		Assertions.assertEquals(ExtendedLineageItem.DEFAULT_UID, eli.getUniqueID());
		
		Assertions.assertEquals(ExtendedLineageItemTests.generateELIstring(ExtendedLineageItem.DEFAULT_UID, scName, fixRank), eli.toString());
	}

	@Test
	void testExtendedLineageItemStringStringString() {
		ExtendedLineageItem eli = new ExtendedLineageItem( fixUID, scName, fixRank );
		Assertions.assertNotNull( eli );
		Assertions.assertEquals(scName, eli.getScientificName());
		Assertions.assertEquals(fixRank, eli.getRank());
		Assertions.assertEquals(fixUID, eli.getUniqueID());
		
		Assertions.assertEquals(ExtendedLineageItemTests.generateELIstring(fixUID, scName, fixRank), eli.toString());
	}
	
	private static String generateELIstring(String uid, String name, String rank) {
		return "\tTAXON\n" + 
				"\t\tID\t" + uid + "\n" + 
				"\t\tSCIENTIFIC NAME\t" + name +"\n" + 
				"\t\tRANK\t" + rank + "";
	}

}

package gabywald.global.data.tests;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.global.data.filters.FilterUtils.FilterGroupType;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class FilterUtilsTests {

	@Test
	void testFilterGroupType() {
		List<String> listNoneExtensions = FilterGroupType.NONE.extensions();
		Assertions.assertNotNull( listNoneExtensions );
		Assertions.assertEquals(0, listNoneExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList(), listNoneExtensions);
		
		List<String> listAlnExtensions = FilterGroupType.ALIGNMENTS.extensions();
		Assertions.assertNotNull( listAlnExtensions );
		Assertions.assertEquals(2, listAlnExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList("aln", "ali"), listAlnExtensions);
		
		List<String> listAllExtensions = FilterGroupType.ALL.extensions();
		Assertions.assertNotNull( listAllExtensions );
		Assertions.assertEquals(19, listAllExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList("aln", "fasta", "fastq", "fna", "ffn", "faa", "frn", "seq", "ali", "res", "ct", "b", "tiff", "jpg", "jpeg", "gif", "png", "xml", "txt"), listAllExtensions);
		
		List<String> listImagesExtensions = FilterGroupType.IMAGES.extensions();
		Assertions.assertNotNull( listImagesExtensions );
		Assertions.assertEquals(5, listImagesExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList("tiff", "jpg", "jpeg", "gif", "png"), listImagesExtensions);
		
		List<String> listOtherExtensions = FilterGroupType.OTHER.extensions();
		Assertions.assertNotNull( listOtherExtensions );
		Assertions.assertEquals(3, listOtherExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList("res", "ct", "b"), listOtherExtensions);
		
		List<String> listSequencesExtensions = FilterGroupType.SEQUENCES.extensions();
		Assertions.assertNotNull( listSequencesExtensions );
		Assertions.assertEquals(7, listSequencesExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList("fasta", "fastq", "fna", "ffn", "faa", "frn", "seq"), listSequencesExtensions);
		
		List<String> listTextExtensions = FilterGroupType.TEXT.extensions();
		Assertions.assertNotNull( listTextExtensions );
		Assertions.assertEquals(2, listTextExtensions.size());
		Assertions.assertIterableEquals(Arrays.asList("xml", "txt"), listTextExtensions);
	}

}

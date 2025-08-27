package gabywald.global.data.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.global.data.StringUtils;

class StringUtilsTests {

	@Test
	void testRepeat() {
		Assertions.assertEquals("\t\t\t\t\t", StringUtils.repeat("\t", 5));
		Assertions.assertEquals("aaaaa", StringUtils.repeat("a", 5));
		Assertions.assertEquals("a", StringUtils.repeat("a", 1));
	}

}

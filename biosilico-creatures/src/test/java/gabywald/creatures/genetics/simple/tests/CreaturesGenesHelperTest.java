package gabywald.creatures.genetics.simple.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.CreaturesGenesHelper;
import gabywald.creatures.model.UnsignedByte;

class CreaturesGenesHelperTest {

	@Test
	void testApplyCheckContentCompletion() {
		List<UnsignedByte> lstContent = new ArrayList<UnsignedByte>();
		Assertions.assertEquals(0, lstContent.size());
		int result = CreaturesGenesHelper.applyCheckContent(lstContent, 9, UnsignedByte.class);
		Assertions.assertEquals(9, lstContent.size());
		Assertions.assertEquals(9, result);
	}
	
	@Test
	void testApplyCheckContentSmalletion() {
		List<UnsignedByte> lstContent = new ArrayList<UnsignedByte>();
		Assertions.assertEquals(0, lstContent.size());
		lstContent.addAll(IntStream.range(0, 256).mapToObj( i -> new UnsignedByte( i ) ).collect(Collectors.toList()));
		Assertions.assertEquals(256, lstContent.size());
		int result = CreaturesGenesHelper.applyCheckContent(lstContent, 9, UnsignedByte.class);
		Assertions.assertEquals(9, lstContent.size());
		Assertions.assertEquals(0, result);
	}

}

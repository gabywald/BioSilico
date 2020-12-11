package gabywald.creatures.genetics.simple.tests;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.CreaturesChemical;
import gabywald.creatures.genetics.simple.CreaturesVersion;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
class CreaturesChemicalTests {

	@Test
	void testGetCreaturesChemicalsV1() {
		List<CreaturesChemical> ccs = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		Assertions.assertNotNull( ccs );
		Assertions.assertEquals(256, ccs.size());
		for (CreaturesChemical cc : ccs) 
			{ Logger.printlnLog(LoggerLevel.LL_DEBUG, cc.toString()); }
	}
	
	@Test
	void testGetCreaturesChemicalsV2() {
		List<CreaturesChemical> ccs = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES2);
		Assertions.assertNotNull( ccs );
		Assertions.assertEquals(256, ccs.size());
	}
	
	@Test
	void testGetCreaturesChemicalsV3() {
		List<CreaturesChemical> ccs = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES3);
		Assertions.assertNotNull( ccs );
		Assertions.assertEquals(  0, ccs.size());
	}

}

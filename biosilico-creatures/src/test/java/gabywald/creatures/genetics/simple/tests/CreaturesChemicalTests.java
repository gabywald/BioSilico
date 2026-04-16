package gabywald.creatures.genetics.simple.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.genetics.simple.CreaturesChemical;
import gabywald.creatures.genetics.simple.CreaturesVersion;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020, 2026)
 */
class CreaturesChemicalTests {

	@Test
	void testGetCreaturesChemicalsV1() {
		List<CreaturesChemical> ccs = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		Assertions.assertNotNull( ccs );
		Assertions.assertEquals(256, ccs.size());
		if (Logger.isLogLevelAccurate(LoggerLevel.LL_NONE)) 
			{ ccs.stream().forEach( cc -> Logger.printlnLog(LoggerLevel.LL_NONE, cc.toString())); }; 
	}
	
	@Test
	void testGetCreaturesChemicalsV2() {
		List<CreaturesChemical> ccs = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES2);
		Assertions.assertNotNull( ccs );
		Assertions.assertEquals(256, ccs.size());
		if (Logger.isLogLevelAccurate(LoggerLevel.LL_NONE)) 
			{ ccs.stream().forEach( cc -> Logger.printlnLog(LoggerLevel.LL_NONE, cc.toString())); }; 
	}
	
	@Test
	void testGetCreaturesChemicalsV3() {
		List<CreaturesChemical> ccs = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES3);
		Assertions.assertNotNull( ccs );
		Assertions.assertEquals(  0, ccs.size());
		if (Logger.isLogLevelAccurate(LoggerLevel.LL_NONE)) 
			{ ccs.stream().forEach( cc -> Logger.printlnLog(LoggerLevel.LL_NONE, cc.toString())); }; 
	}
	
	@Test
	void testGetCreaturesChemicalsDIFFV1V2() {
		List<CreaturesChemical> ccs1 = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES1);
		Assertions.assertNotNull( ccs1 );
		Assertions.assertEquals(256, ccs1.size());
		List<CreaturesChemical> ccs2 = CreaturesChemical.getCreaturesChemicals(CreaturesVersion.CREATURES2);
		Assertions.assertNotNull( ccs2 );
		Assertions.assertEquals(256, ccs2.size());
		List<Boolean> attemptedComparisons = Arrays.asList( false,true,false,false,true,true,true,false,
															false,false,true,true,true,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															false,false,false,false,false,false,false,false,
															true,true,false,true,true,true,true,true,
															true,true,true,true,true,true,true,true,
															true,true,true,true,true,true,true,true );
		List<Boolean> comparisons = new ArrayList<Boolean>();
		for (int i = 0 ; i < ccs1.size() ; i++) {
			// Logger.printlnLog(LoggerLevel.LL_FORUSER, "" + ccs1.get(i).equals(ccs2.get(i)) );
			comparisons.add( ccs1.get(i).equals(ccs2.get(i)) );
			if ( (Logger.isLogLevelAccurate(LoggerLevel.LL_NONE)) && (! ccs1.get(i).equals(ccs2.get(i)) ) ) {
				Logger.printlnLog(LoggerLevel.LL_NONE, "::" + i + "\n\t" + ccs1.get(i).toString() + "\n\t" + ccs2.get(i).toString() + "\n");
			}
		}
		// comparison.stream().map( b -> b+",").forEach( System.out::print);
		Assertions.assertEquals(attemptedComparisons.size(), comparisons.size());
		Assertions.assertEquals(attemptedComparisons, comparisons);
	}

}

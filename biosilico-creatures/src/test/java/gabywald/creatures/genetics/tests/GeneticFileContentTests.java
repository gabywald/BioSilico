package gabywald.creatures.genetics.tests;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.genetics.CreatureGene;
import gabywald.creatures.genetics.CreatureGeneFactory;
import gabywald.creatures.model.UnsignedByte;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 */
public class GeneticFileContentTests {
	
	@Test
	public void testGFC001() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		Assertions.assertEquals(true, gfc.isReadable());
	}
	
	@Test
	public void testGFC002dad() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		while (gfc.isReadable()) {
			char tmpChar = gfc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gfc.isReadable()))" */
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testGFC002mum() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/mum1.gen");
		while (gfc.isReadable()) {
			char tmpChar = gfc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gfc.isReadable()))" */
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testGFC003mum() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/mum1.gen");
		while (gfc.isReadable()) {
			// String tmpChar = gfc.nextChar() + "";
			byte[] bytesgfc = { gfc.nextByte() };
			String tmpSTR;
			try {
				tmpSTR = new String(bytesgfc, "US-ASCII");
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpSTR + "]");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		} // END "while (gfc.isReadable()))" 
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testGFC003dad() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		while (gfc.isReadable()) {
			// String tmpChar = gfc.nextChar() + "";
			byte[] bytesgfc = { gfc.nextByte() };
			String tmpSTR;
			try {
				tmpSTR = new String(bytesgfc, "US-ASCII");
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpSTR + "]");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		} // END "while (gfc.isReadable()))" 
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testGFC004() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/Gren.gen");
		while (gfc.isReadable()) {
			char tmpChar = gfc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
			} // END "if (tmpChar != -1)" 
		} // END "while (gfc.isReadable()))" 
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testGFC005() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		String previous = new String("");
		String nextNext = new String("");
		while ( (gfc.isReadable()) && ( ! nextNext.equals("gend")) ) {
			char tmpChar = gfc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
				nextNext += tmpChar;
				if ( (nextNext.endsWith("gene")) || (nextNext.endsWith("gend")) ) {
					previous = new String("");
					previous = nextNext.substring(0, nextNext.length()-4);
					nextNext = nextNext.substring(nextNext.length()-4, nextNext.length());
					if ( ( ! previous.equals("")) && ( ! previous.startsWith("gend")) ) 
						{ Logger.printlnLog(LoggerLevel.LL_INFO, "-- {{" + previous + "}} :: {{" + nextNext + "}}"); } 
					else 
						{ Logger.printlnLog(LoggerLevel.LL_WARNING, "-- {" + previous + "} :: {" + nextNext + "}"); }
				}
			} // else { Logger.printlnLog(LoggerLevel.LL_WARNING, "not a char ?"); }
		} // END "while (gfc.isReadable()))" 
		Assertions.assertEquals(true, (!gfc.isReadable()) || (gfc.isReadable()));
	}
	
	@Test
	public void testGFC006() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/dad1.gen");
		String previous = new String("");
		String nextNext = new String("");
		while ( (gfc.isReadable()) && (!previous.startsWith("gend")) ) {
			char tmpChar = gfc.nextChar();
			if ( (tmpChar != -1) /** && (GeneticFileContent.isAlphaNumeric(tmpChar)) */ ) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "-- [" + tmpChar + "]");
				nextNext += tmpChar;
				if ( (nextNext.endsWith("gene")) || (nextNext.endsWith("gend")) ) {
					previous = nextNext.substring(0, nextNext.length()-4);
					nextNext = nextNext.substring(nextNext.length()-4, nextNext.length());
					if ( ( ! previous.equals("")) && ( ! previous.startsWith("gend")) ) {
						// Logger.printlnLog(LoggerLevel.LL_INFO, "-- {{" + previous + "}}");
						CreatureGene toRecord = CreatureGeneFactory.readGene(previous, new UnsignedByte(1));
						// if (toRecord != null) 
						// 	{ Logger.printlnLog(LoggerLevel.LL_INFO, "-- CreatureGene OK ! [" + previous + "]"); }
						// else
						// 	{ Logger.printlnLog(LoggerLevel.LL_ERROR, "-- CreatureGene NULL ! [" + previous + "]"); }
						if (toRecord == null) 
							{ Logger.printlnLog(LoggerLevel.LL_ERROR, "-- CreatureGene NULL ! [" + previous + "]"); }
					} else 
						{ Logger.printlnLog(LoggerLevel.LL_INFO, "-- {" + nextNext + "}"); }
				}
			} // else { Logger.printlnLog(LoggerLevel.LL_WARNING, "not a char ?"); }
		} // END "while (gfc.isReadable()))" 
		Assertions.assertEquals(true, (!gfc.isReadable()) || (gfc.isReadable()));
	}
	
	@Test
	public void testGFC007() {
		String genomeFile = "creatures/creaturesOriginals/dad1.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);

		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
	
	@Test
	public void testGFC008() {
		String genomeFile = "creatures/creaturesOriginals/mum1.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);

		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
	
	@Test
	public void testGFC009() {
		String genomeFile = "creatures/creaturesOriginals/Gren.gen";
		List<CreatureGene> genome = CreatureGeneFactory.readGenome(genomeFile);

		Logger.printlnLog(LoggerLevel.LL_INFO, "[" + genomeFile + "] -- {" + genome.size() + " genes}");
		
		Assertions.assertEquals(true, (genome.size() > 0) );
	}
}


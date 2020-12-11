package gabywald.creatures.genetics.tests;

import gabywald.creatures.geneticReader.GeneticFileContent;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 * TODO review and replace "System.out.println(" with "Logger.printlnLog(LoggerLevel.LL_NONE, "
 */
public class PosesReading {
	
	@Test
	public void testgfc001() {
		// final String dir = System.getProperty("user.dir");
        // System.out.println("current dir = " + dir);
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/PoseName.bin");
		Assertions.assertEquals(true, gfc.isReadable());
	}
	
	@Test
	public void testgfc002() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/PoseName.bin");
		if (gfc.isReadable()) {
			while (gfc.isReadable()) {
				char charAsCC = gfc.nextChar();
				// int charAsInt = GeneticFileContent.charToNum(charAsCC);
				if (GeneticFileContent.isAlphaNumeric( charAsCC )) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("."); }
			} // END "while (gfc.isReadable())" 
			System.out.println();
		} // END "if (gfc.isReadable())" 
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testgfc003() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/PoseName.bin");
		if (gfc.isReadable()) {
			while (gfc.isReadable()) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gfc.nextChar();
				int charAsInt = GeneticFileContent.charToNum(charAsCC);
				if (GeneticFileContent.isAlphaNumeric( charAsCC )) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("[" + charAsInt + "]"); }
			} // END "while (gfc.isReadable())" 
			System.out.println();
		} // END "if (gfc.isReadable())" 
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testgfc004() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/PoseName.bin");
		if (gfc.isReadable()) {
			boolean isDuetto		= false;
			char[] duetto			= new char[2];
			String toAppend			= new String("");
			List<String> chNames	= new ArrayList<String>();
			
			while (gfc.isReadable()) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gfc.nextChar();
				int charAsInt = GeneticFileContent.charToNum(charAsCC);
				if (GeneticFileContent.isAlphaNumeric( charAsCC )) { 
					toAppend += charAsCC;
				} else { 
					// System.out.print("[" + charAsInt + "]");
					if (charAsInt != 46) {
						if ( ! toAppend.equals("")) {
							chNames.add(toAppend);
							System.out.println("\t'"+toAppend+"'\t(" + chNames.size() + ")");
							toAppend = new String("");
						} // END "if ( ! toAppend.equals(""))" 
						if ( (!isDuetto) && (charAsInt != 0) )
							{ duetto[0] = charAsCC;isDuetto = true; }
						else { duetto[1] = charAsCC;isDuetto = false; }
					} // END "if (charAsInt != 46)" 
				}
			} // END "while (gfc.isReadable())" 
			System.out.println();
		} // END "if (gfc.isReadable())" 
		Assertions.assertEquals(true, !gfc.isReadable());
	}
	
	@Test
	public void testgfc005() {
		GeneticFileContent gfc = new GeneticFileContent("creatures/creaturesOriginals/PoseName.bin");
		if (gfc.isReadable()) {
			boolean isDuetto		= false;
			char[] duetto			= new char[2];
			String toAppend			= new String("");
			List<String> chNames	= new ArrayList<String>();
			
			while (gfc.isReadable()) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gfc.nextChar();
				int charAsInt = GeneticFileContent.charToNum(charAsCC);
				if (GeneticFileContent.isAlphaNumeric( charAsCC )) 
					{ toAppend += charAsCC; }
				else { 
					// System.out.print("[" + charAsInt + "]");
					if (charAsInt != 46) {
						if ( ! toAppend.equals("")) {
							chNames.add(toAppend);
							toAppend = new String("");
						} /** END "if ( ! toAppend.equals(""))" */
						if ( (!isDuetto) && (charAsInt != 0) )
							{ duetto[0] = charAsCC;isDuetto = true; }
						else { duetto[1] = charAsCC;isDuetto = false; }
					} /** END "if (charAsInt != 46)" */
				}
			} /** END "while (gfc.isReadable())" */
			
			for (int i = 0 ; i < 100 ; i++) 
				{ System.out.print(chNames.get(i) + ((i != 257)?";":"") ); }
			System.out.println();
			for (int i = 100 ; i < chNames.size() ; i++) 
				{ System.out.println(chNames.get(i)); }
			System.out.println();
		} /** END "if (gfc.isReadable())" */
		Assertions.assertEquals(true, !gfc.isReadable());
	}
}

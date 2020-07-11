package gabywald.creatures.GRtests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// import gabywald.creatures.geneticReader.GeneticFileContent;
import gabywald.creatures.geneticReader.GeneticFileContentOld;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

public class ChemicalReadingsOld {

	@Test
	public void testGTC001() {
		// final String dir = System.getProperty("user.dir");
        // System.out.println("current dir = " + dir);
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/chemicals.txt");
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC002() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/chemicals.txt");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				// int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("."); }
			} // END "for (int i = 0 ; i < gtc.length() ; i++)"
			System.out.println();
		} // END "if (gtc.isReaded())" 
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC003() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/chemicals.txt");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) // if (Character.isLetterOrDigit(charAsInt)) 
					{ System.out.print(charAsCC); }
				else { System.out.print("[" + charAsInt + "]"); }
			} // END "for (int i = 0 ; i < gtc.length() ; i++)" 
			System.out.println();
		} // END "if (gtc.isReaded())" 
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC004() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/chemicals.txt");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			
			boolean isDuetto		= false;
			char[] duetto			= new char[2];
			String toAppend			= new String("");
			List<String> chNames	= new ArrayList<String>();
			
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) { 
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
			} // END "for (int i = 0 ; i < gtc.length() ; i++)" 
			System.out.println();
		} // END "if (gtc.isReaded())" 
		Assertions.assertEquals(true, gtc.isReaded());
	}
	
	@Test
	public void testGTC005() {
		GeneticFileContentOld gtc = new GeneticFileContentOld("creatures/creaturesOriginals/chemicals.txt");
		if (gtc.isReaded()) {
			Logger.printlnLog(LoggerLevel.LL_INFO, "-- " + gtc.length() + "");
			
			boolean isDuetto		= false;
			char[] duetto			= new char[2];
			String toAppend			= new String("");
			List<String> chNames	= new ArrayList<String>();
			
			for (int i = 0 ; i < gtc.length() ; i++) {
				// Logger.printlnLog(LoggerLevel.LL_INFO, "[" + i + "]");
				char charAsCC = gtc.charAt(i);
				int charAsInt = gtc.toASCII(i);
				if (gtc.isAlphaNumeric(i)) 
					{ toAppend += charAsCC; }
				else { 
					// System.out.print("[" + charAsInt + "]");
					if (charAsInt != 46) {
						if ( ! toAppend.equals("")) {
							chNames.add(toAppend);
							toAppend = new String("");
						} // END "if ( ! toAppend.equals(""))" 
						if ( (!isDuetto) && (charAsInt != 0) )
							{ duetto[0] = charAsCC;isDuetto = true; }
						else { duetto[1] = charAsCC;isDuetto = false; }
					} // END "if (charAsInt != 46)" 
				}
			} // END "for (int i = 0 ; i < gtc.length() ; i++)" 
			
			for (int i = 0 ; i < 258 ; i++) 
				{ System.out.print(chNames.get(i) + ((i != 257)?";":"") ); }
			System.out.println();
			for (int i = 258 ; i < chNames.size() ; i++) 
				{ System.out.println(chNames.get(i)); }
			System.out.println();
		} // END "if (gtc.isReaded())" 
		Assertions.assertEquals(true, gtc.isReaded());
	}
}

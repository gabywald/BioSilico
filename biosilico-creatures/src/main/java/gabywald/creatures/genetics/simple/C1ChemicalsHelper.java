package gabywald.creatures.genetics.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabywald.global.data.File;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * <br/><i>DP Singleton</i>
 * @author Gabriel Chandesris (2026)
 */
public class C1ChemicalsHelper {
	private static C1ChemicalsHelper instance = null;
	
	private static Pattern pINTERVAL = Pattern.compile("([0-9]+)-([0-9]+)");
	
	private List<C1Chemical> c1chemicals = null;
	
	public static C1ChemicalsHelper getInstance() {
		if (C1ChemicalsHelper.instance == null) { 
			C1ChemicalsHelper.instance = new C1ChemicalsHelper();
			C1ChemicalsHelper.instance.c1chemicals = C1ChemicalsHelper.instanciateChemicalLists();
		}
		return C1ChemicalsHelper.instance;
	}

	private static List<C1Chemical> instanciateChemicalLists() {
		// String path2file1 = "biosilico-creatures/src/main/resources//chemicalsC1.csv";
		String path2file2 = "src/main/resources/chemicalsC1.csv";
		
		List<C1Chemical> c1chemicals2return = new ArrayList<C1Chemical>();
		
		try {
			File chemicalFile = File.loadFile( path2file2 );
			if ( ! chemicalFile.fileExists() ) { 
				Logger.printlnLog(LoggerLevel.LL_ERROR, "ERROR {" + path2file2 + "} does not exist !");
				return c1chemicals2return;
			}
			for (int i = 0 ; (i < chemicalFile.nbLines()) ; i++) {
				String line		= chemicalFile.line(i);
				if (line.startsWith("##")) { continue; } // ignore commented lines
				String[] cute	= line.split(";");
				// ## ;"Number Hex";"Number Dec";"Class";"Name";"Half-Life Hex";"Half-Life Dec"
				if (cute[2].matches("[0-9]+")) {
					c1chemicals2return.add(new C1Chemical(cute[1], cute[2], cute[3], cute[4], cute[5], cute[6]));
				} else if (cute[2].matches(pINTERVAL.pattern())) {
					Matcher match = pINTERVAL.matcher(cute[6]);
					int start = Integer.parseInt(match.group(0));
					int stopp = Integer.parseInt(match.group(1));
					for (int j = start ; j < stopp ; j++) 
						{ c1chemicals2return.add(new C1Chemical(cute[1], j + "", cute[3], cute[4], cute[5], cute[6])); }
				}
				
			}
		/** TODO treatment : IOException */
		} catch (IOException e) { e.printStackTrace(); }
		return c1chemicals2return;
	}
	
	public String getChemicalNameBy(int numDEC) {
		for (C1Chemical chemical : this.c1chemicals) {
			if (chemical.getNumlDECasINT() == numDEC)
				{ return chemical.getName(); }
		}
		return null;
	}
}

package gabywald.biosilico.model.chemicals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gabywald.global.data.File;
import gabywald.global.structures.StringCouple;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

/**
 * 
 * @author Gabriel Chandesris (2020)
 */
public class ChemicalsHelper {
	
	/** Location of the file containing the list of chemicals (abbrev. and names). */
	private static final String CHEMICAL_LIST_FILE				= "biosilico/data/ChemicalsList.txt";
	/** Location of the file containing the list of defined chemicals half lives. 
	 * <br/>Limitations of 'Strictly chemicals' to avoid changes of "positioning and concepts" used otherwise !! */
	private static final String CHEMICAL_HALFLIVES_LIST_FILE	= "biosilico/data/ChemicalsHalfLivesList.txt";
	/** Number of chemicals. */
	public static final int CHEMICAL_LENGTH						= 1000;
	/** Upper limit of "strictly defined chemical", Also "Special Chemical" for BiochemicalReaction. */
	public static final int CHEMICAL_STRICT_CHEM				= 600;
	
	/** to avoid instanciation. */
	private ChemicalsHelper() { ; }

	/** List of abbreviations of chemicals. */
	private static List<String> CHEMICAL_ABREV			= null;
	/** List of names of chemicals. */
	private static List<String> CHEMICAL_NAMES			= null;
	/** List of couples (abbrev,name) of chemicals. */
	private static List<StringCouple> CHEMICAL_LISTE	= null;
	
	/**
	 * List of names of chemicals. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringListe)
	 */
	public static List<String> getChemicalNames() {
		if ( ! ChemicalsHelper.existListsOfChemical()) 
			{ ChemicalsHelper.instanciateChemicalLists(); }
		return ChemicalsHelper.CHEMICAL_NAMES;
	}
	
	/**
	 * List of abbreviations of chemicals. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringListe)
	 */
	public static List<String> getChemicalAbbre() {
		if ( ! ChemicalsHelper.existListsOfChemical()) 
			{ ChemicalsHelper.instanciateChemicalLists(); }
		return ChemicalsHelper.CHEMICAL_ABREV;
	}
	
	/**
	 * List of couple abbrev - name of the chemical. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringCoupleListe)
	 */
	public static List<StringCouple> getChemicalListe() {
		if ( ! ChemicalsHelper.existListsOfChemical()) 
			{ ChemicalsHelper.instanciateChemicalLists(); }
		return ChemicalsHelper.CHEMICAL_LISTE;
	}
	
	/**
	 * To test if all the lists exists (abbrev, name, couple). 
	 * @return (boolean)
	 */
	private static boolean existListsOfChemical() {
		if (ChemicalsHelper.CHEMICAL_LISTE == null) { return false; }
		if (ChemicalsHelper.CHEMICAL_NAMES == null) { return false; }
		if (ChemicalsHelper.CHEMICAL_ABREV == null) { return false; }
		return true;
	}
	
	/** To instanciate / reload the lists of abbrev / name / couple for chemicals. */
	private static void instanciateChemicalLists() {
		ChemicalsHelper.CHEMICAL_LISTE = new ArrayList<StringCouple>();
		ChemicalsHelper.CHEMICAL_NAMES = new ArrayList<String>();
		ChemicalsHelper.CHEMICAL_ABREV = new ArrayList<String>();
		try {
			File chemicalFile = File.loadFile(ChemicalsHelper.CHEMICAL_LIST_FILE);
			for (int i = 0 ; (i < chemicalFile.lengthFile()) && (i < ChemicalsHelper.CHEMICAL_LENGTH) ; i++) {
				String line		= chemicalFile.getChamp(i);
				String[] cute	= line.split("\t");
				// System.out.println("\t"+i+"\t"+cute[0]);
				ChemicalsHelper.CHEMICAL_ABREV.add(cute[0]);
				ChemicalsHelper.CHEMICAL_NAMES.add(cute[1]);
				ChemicalsHelper.CHEMICAL_LISTE.add(new StringCouple(cute[0], cute[1]));
			}
		/** TODO treatment : IOException */
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static Map<Integer, Integer> loadDefaultChemicalsHalfLives() {
		final Map<Integer, Integer> toReturn = new HashMap<Integer, Integer>();
		
		try {
			File chemicalHLfile = File.loadFile(ChemicalsHelper.CHEMICAL_HALFLIVES_LIST_FILE);
			for (int i = 0 ; (i < chemicalHLfile.lengthFile()) && (i < ChemicalsHelper.CHEMICAL_LENGTH) ; i++) {
				String line		= chemicalHLfile.getChamp(i);
				String[] cute	= line.split("\t");
				try {
					toReturn.put(Integer.parseInt(cute[0]), Integer.parseInt(cute[1]));
				} catch (NumberFormatException nfe) {
					StringBuilder msg = new StringBuilder();
					msg.append("NumberFormatException: {").append(nfe.getMessage()).append("}");
					msg.append("\t[").append(cute[0]).append("]").append("\t[").append(cute[1]).append("]");
					Logger.printlnLog(LoggerLevel.LL_ERROR, msg.toString());
				}
			}
		/** TODO treatment : IOException */
		} catch (IOException e) { e.printStackTrace(); }
		
		return toReturn;
	}
}

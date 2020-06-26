package gabywald.biosilico.model;

import java.io.IOException;

import gabywald.global.data.File;
import gabywald.global.structures.IntegerListe;
import gabywald.global.structures.StringCouple;
import gabywald.global.structures.StringCoupleListe;
import gabywald.global.structures.StringListe;

/**
 * To give a specific table of variables and tools to use it. 
 * @author Gabriel Chandesris (2009-2010)
 */
public class Chemicals {
	/** Location of the file containing the list of chemicals (abbrev. and names). */
	private static final String CHEMICAL_LIST_FILE = 
			"biosilico/data/ChemicalList.txt";
	/** Number of chemicals. */
	private static final int CHEMICAL_LENGTH = 1000;
	/** The table of variables. */
	private IntegerListe vars;
	
	/** Default constructor (length of 1000). */
	public Chemicals() { 
		this.vars = new IntegerListe();
		for (int i = 0 ; i < Chemicals.CHEMICAL_LENGTH ; i++) 
			{ this.vars.addInteger(new Integer(0)); }
	}
	
	public int length() { return this.vars.length(); }
	public int getVariable(int i) { return this.vars.getInteger(i).intValue(); }
	public void setVariable(int i,int value) {
		if ( (i >= 0) && (i < this.vars.length()) )
			{ this.vars.setInteger(value, i); }
		this.regulate(i);
	}
	
	public void setVariable(String chemIndex,String chemValue) {
		int i		= Integer.parseInt(chemIndex);
		int value	= Integer.parseInt(chemValue);
		this.setVariable(i, value);
	}
	
	/**
	 * To add val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to add. 
	 */
	public void setVarPlus(int i, int val) {
		if ( (i >= 0) && (i < this.vars.length()) )
			{ this.vars.setIntegerLess(i, val); }
		this.regulate(i);
	}
	
	/**
	 * To add 1 at a specific indice. 
	 * @param i (int) indice of variable to change.
	 */
	public void setVarPlusPlus(int i) {
		if ( (i >= 0) && (i < this.vars.length()) )
			{ this.vars.setIntegerPlusPlus(i); }
		this.regulate(i);
	}
	
	/**
	 * To remove val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to remove.
	 */
	public void setVarLess(int i, int val) {
		if ( (i >= 0) && (i < this.vars.length()) )
			{ this.vars.setIntegerLess(i, val); }
		this.regulate(i);
	}
	
	/**
	 * To remove 1 at a specific indice.
	 * @param i (int) indice of variable to change.
	 */
	public void setVarLessLess(int i) {
		if ( (i >= 0) && (i < this.vars.length()) )
			{ this.vars.setIntegerLessLess(i); }
		this.regulate(i);
	}
	
	/**
	 * To "regulate" variables between 0 and 999. 
	 * @param i (int) variable to regulate. 
	 */
	private void regulate(int i) {
		if (this.vars.getInteger(i).intValue() < 0) { this.vars.setInteger(0, i); }
		if (this.vars.getInteger(i).intValue() > 999) { this.vars.setInteger(999, i); }
	}
	
	/**
	 * To add a Variables set added to current Variables.
	 * @param toSum (Variables) Vars to sum. 
	 */
	public void incorporate(Chemicals toSum) {
		for (int i = 0 ; i < this.vars.length() ; i++) { 
			this.vars.setIntegerPlus(i, toSum.getVariable(i));
			this.regulate(i);
		}
	}
	
	/** List of abbreviations of chemicals. */
	private static StringListe CHEMICAL_ABREV = null;
	/** List of names of chemicals. */
	private static StringListe CHEMICAL_NAMES = null;
	/** List of couples (abbrev,name) of chemicals. */
	private static StringCoupleListe CHEMICAL_LISTE = null;
	
	/**
	 * List of names of chemicals. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringListe)
	 */
	public static StringListe getChemicalNames() {
		if (!Chemicals.existListsOfChemical()) 
			{ Chemicals.instanciateChemicalLists(); }
		return Chemicals.CHEMICAL_NAMES;
	}
	
	/**
	 * List of abbreviations of chemicals. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringListe)
	 */
	public static StringListe getChemicalAbbre() {
		if (!Chemicals.existListsOfChemical()) 
			{ Chemicals.instanciateChemicalLists(); }
		return Chemicals.CHEMICAL_ABREV;
	}
	
	/**
	 * List of couple abbrev - name of the chemical. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringCoupleListe)
	 */
	public static StringCoupleListe getChemicalListe() {
		if (!Chemicals.existListsOfChemical()) 
			{ Chemicals.instanciateChemicalLists(); }
		return Chemicals.CHEMICAL_LISTE;
	}
	
	/**
	 * To test if all the lists exists (abbrev, name, couple). 
	 * @return (boolean)
	 */
	private static boolean existListsOfChemical() {
		if (Chemicals.CHEMICAL_LISTE == null) { return false; }
		if (Chemicals.CHEMICAL_NAMES == null) { return false; }
		if (Chemicals.CHEMICAL_ABREV == null) { return false; }
		return true;
	}
	
	/** To instanciate / reload the lists of abbrev / name / couple for chemicals. */
	private static void instanciateChemicalLists() {
		Chemicals.CHEMICAL_LISTE = new StringCoupleListe();
		Chemicals.CHEMICAL_NAMES = new StringListe();
		Chemicals.CHEMICAL_ABREV = new StringListe();
		try {
			File chemicalFile = File.loadFile(Chemicals.CHEMICAL_LIST_FILE);
			for (int i = 0 ; (i < chemicalFile.lengthFile()) && (i < 1000) ; i++) {
				String line = chemicalFile.getChamp(i);
				String[] cute = line.split("\t");
				/** System.out.println("\t"+i+"\t"+cute[0]); */
				Chemicals.CHEMICAL_ABREV.addString(cute[0]);
				Chemicals.CHEMICAL_NAMES.addString(cute[1]);
				Chemicals.CHEMICAL_LISTE.addStringCouple(new StringCouple(cute[0],cute[1]));
			}
		/** TODO traitement de IOException */
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public String toString() {
		String result = new String();
		for (int i = 0 ; i < this.vars.length() ; i++) {
			if (this.vars.getInteger(i).intValue() != 0) 
				{ result += "\t"+i+"\t"+this.vars.getInteger(i).intValue()+"\n"; }
		}
		return result;
	}
	
}

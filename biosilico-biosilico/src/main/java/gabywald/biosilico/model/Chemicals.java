package gabywald.biosilico.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import gabywald.global.data.File;
import gabywald.global.structures.StringCouple;

/**
 * To give a specific table of variables and tools to use it. 
 * @author Gabriel Chandesris (2009-2010, 2020)
 */
public class Chemicals {
	/** Location of the file containing the list of chemicals (abbrev. and names). */
	private static final String CHEMICAL_LIST_FILE	= "biosilico/data/ChemicalList.txt";
	/** Number of chemicals. */
	public static final int CHEMICAL_LENGTH			= 1000;
	/** The table of variables. */
	private List<Integer> vars;
	
	/** Default constructor (length of 1000, initiated to 0 foir each value). */
	public Chemicals() { 
		this.vars = IntStream.iterate(0, i -> i++).limit( Chemicals.CHEMICAL_LENGTH )
						.map( i -> 0 ).boxed().collect(Collectors.toList());
//		this.vars = new ArrayList<Integer>( Chemicals.CHEMICAL_LENGTH );
//		for (int i = 0 ; i < Chemicals.CHEMICAL_LENGTH ; i++) 
//			{ this.vars.add(new Integer(0)); }
	}
	
	public int length()				{ return this.vars.size(); }
	
	public int getVariable(int i)	{ return this.vars.get(i).intValue(); }
	
	public void setVariable(int i, int value) {
		if ( (i >= 0) && (i < this.vars.size()) )
			{ this.vars.set(i, value); }
		this.regulate(i);
	}
	
	/**
	 * To add val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to add. 
	 */
	public void setVarPlus(int i, int val) {
		if ( (i >= 0) && (i < this.vars.size()) )
			{ this.vars.set(i, this.vars.get(i) + val); }
		this.regulate(i);
	}
	
	/**
	 * To add 1 at a specific indice. 
	 * @param i (int) indice of variable to change.
	 */
	public void setVarPlusPlus(int i) {
		this.setVarPlus(i, 1);
	}
	
	/**
	 * To remove val to variable at indice i. 
	 * @param i (int) indice of variable to change.
	 * @param val (int) value to remove.
	 */
	public void setVarLess(int i, int val) {
		this.setVarPlus(i, -val);
	}
	
	/**
	 * To remove 1 at a specific indice.
	 * @param i (int) indice of variable to change.
	 */
	public void setVarLessLess(int i) {
		this.setVarLess(i, 1);
	}
	
	/**
	 * To "regulate" variables between 0 and 999. 
	 * @param i (int) variable to regulate. 
	 */
	private void regulate(int i) {
		if (this.vars.get(i).intValue() < 0)	{ this.vars.set(i, 0); }
		if (this.vars.get(i).intValue() > 999)	{ this.vars.set(i, 999); }
	}
	
	/**
	 * To add a Variables set added to current Variables.
	 * @param toSum (Variables) Vars to sum. 
	 */
	public void incorporate(Chemicals toSum) {
		IntStream.range(0, Chemicals.CHEMICAL_LENGTH )
			.forEach( i -> {
				this.setVarPlus(i, toSum.vars.get(i).intValue());
				this.regulate(i);
			});
	}
	
	/** List of abbreviations of chemicals. */
	private static List<String> CHEMICAL_ABREV = null;
	/** List of names of chemicals. */
	private static List<String> CHEMICAL_NAMES = null;
	/** List of couples (abbrev,name) of chemicals. */
	private static List<StringCouple> CHEMICAL_LISTE = null;
	
	/**
	 * List of names of chemicals. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringListe)
	 */
	public static List<String> getChemicalNames() {
		if ( ! Chemicals.existListsOfChemical()) 
			{ Chemicals.instanciateChemicalLists(); }
		return Chemicals.CHEMICAL_NAMES;
	}
	
	/**
	 * List of abbreviations of chemicals. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringListe)
	 */
	public static List<String> getChemicalAbbre() {
		if ( ! Chemicals.existListsOfChemical()) 
			{ Chemicals.instanciateChemicalLists(); }
		return Chemicals.CHEMICAL_ABREV;
	}
	
	/**
	 * List of couple abbrev - name of the chemical. 
	 * <a href="http://en.wikipedia.org/wiki/Periodic_table>Periodic table of elements</a>
	 * @return (StringCoupleListe)
	 */
	public static List<StringCouple> getChemicalListe() {
		if ( ! Chemicals.existListsOfChemical()) 
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
		Chemicals.CHEMICAL_LISTE = new ArrayList<StringCouple>();
		Chemicals.CHEMICAL_NAMES = new ArrayList<String>();
		Chemicals.CHEMICAL_ABREV = new ArrayList<String>();
		try {
			File chemicalFile = File.loadFile(Chemicals.CHEMICAL_LIST_FILE);
			for (int i = 0 ; (i < chemicalFile.lengthFile()) && (i < 1000) ; i++) {
				String line = chemicalFile.getChamp(i);
				String[] cute = line.split("\t");
				// System.out.println("\t"+i+"\t"+cute[0]);
				Chemicals.CHEMICAL_ABREV.add(cute[0]);
				Chemicals.CHEMICAL_NAMES.add(cute[1]);
				Chemicals.CHEMICAL_LISTE.add(new StringCouple(cute[0],cute[1]));
			}
		/** TODO traitement de IOException */
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public String toString() {
		String result = new String();
		for (int i = 0 ; i < this.vars.size() ; i++) {
			if (this.vars.get(i).intValue() != 0) 
				{ result += "\t"+i+"\t"+this.vars.get(i).intValue()+"\n"; }
		}
		return result;
	}
	
}

package gabywald.biosilico.structures;

import java.io.IOException;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.interfaces.StructureRecordFile;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
import gabywald.global.structures.ObjectListe;
import gabywald.global.view.text.Terminal;

/**
 * Aim of this class is to provide a Liste of Pathways and read / record them in a File. 
 * @author Gabriel Chandesris (2010)
 * @see Pathway
 */
public class PathwayListe extends ObjectListe implements StructureRecordFile {
	/** Location of the default file to record PathwayListe instance. */
	public static final String PATH_LIST_FILE
		= FileBiological.DEFAULT_PATH_NAME + "definedPathWays.txt";
	/** File for Pathway records. */
	private File recordingPath;
	
	/**
	 * To get the length of the current list of Object's
	 * @return (int)
	 * @see ObjectListe#length()
	 * @see ObjectListe#liste
	 */
	public int length() { return this.liste.length; }
	
	/** 
	 * Default constructor with a list of 0 elements.
	 * @see ObjectListe#ObjectListe() */
	public PathwayListe() { super(); }
	
	/**
	 * Constructor with a pre-made table of Pathway's. 
	 * @param liste (Pathway[])
	 * @see ObjectListe#ObjectListe(Object[])
	 */
	public PathwayListe(Pathway[] liste) { super(liste); }
	
	/**
	 * To get the current list of Pathway's as a table. 
	 * @return (Pathway[]) A table of Pathway's.
	 * @see ObjectListe#getListeObjects()
	 */
	public Pathway[] getListePathways() {
		Pathway[] tabReturn = new Pathway[this.length()];
		for (int i = 0 ; i < this.length() ; i++) 
			{ tabReturn[i] = this.getPathway(i); }
		return tabReturn;
		// return (Pathway[])super.getListeObjects(); 
	}
	
	/**
	 * To get a specific Pathway of the list. 
	 * @param i (int) Position of the Pathway in the list. 
	 * @return (Pathway) Pathway at position i. 
	 * @see ObjectListe#getObject(int)
	 */
	public Pathway getPathway(int i) 
		{ return (Pathway)super.getObject(i); }
	
	/**
	 * To set a new list in the instance of PathwayListe. 
	 * @param liste (Pathway[]) A table of Pathway's. 
	 * @see ObjectListe#setListe(Object[])
	 */
	public void setListe(Pathway[] liste) 
		{ super.setListe(liste); }
	
	/**
	 * To set an Pathway at a specific place in the list, replace the old one. 
	 * @param elt (Pathway) Pathway to set. 
	 * @param i (int) Greater or equal to 0, and lower to liste.length.
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void setPathway(Pathway elt,int i) 
		{ super.setObject(elt, i); }
	
	/**
	 * To add an Pathway to the end of the list. 
	 * @param elt (Pathway) Pathway to add. 
	 * @see ObjectListe#addObject(Object)
	 */
	public void addPathway(Pathway elt) 
		{ super.addObject(elt); }
	
	/**
	 * To add an Pathway at a specific position. 
	 * <br>If Position greater than current length : added at end of the list. 
	 * @param elt (Pathway)
	 * @param pos (int)
	 * @see ObjectListe#setObject(Object, int)
	 */
	public void addPathway(Pathway elt,int pos) 
		{ super.addObject(elt, pos); }
	
	/**
	 * To know if an Pathway is present is the list. 
	 * @param elt (Pathway)
	 * @return (boolean)
	 * @see ObjectListe#has(Object)
	 */
	public boolean has(Pathway elt) 
		{ return super.has(elt); }
	
	/**
	 * To remove a specific Pathway (nothing append if not present). 
	 * @param elt (Pathway) Pathway to remove. 
	 * @see ObjectListe#removeObject(Object)
	 */
	public void removePathway(Pathway elt) 
		{ super.removeObject(elt); }
	
	/**
	 * To remove an Pathway at a specific place in the liste.
	 * @param nbElt (int) Position of the Pathway
	 * @see ObjectListe#removeObject(int)
	 */
	public void removePathway(int nbElt) 
		{ super.removeObject(nbElt); }
	
	/**
	 * Adapted place for methods and functions of sorting is here. 
	 * Your Pathway and Classes used to make the table[] liste must
	 * have something to sort them...
	 */
	
	
	private void readPathWayFile() {
		try {
			this.recordingPath = File.loadFile(PathwayListe.PATH_LIST_FILE);
			if (this.recordingPath.lengthFile() == 1) {
				String firstLine = this.recordingPath.getChamp(0);
				if (firstLine.endsWith("File Not Found")) {
					this.recordingPath.setChamps(new String[0]);
					this.recordingPath.printFile();
					this.recordingPath = File.loadFile(PathwayListe.PATH_LIST_FILE);
				}
			}
			Pathway currentPath = null;
			int pathSize = 0;
			for (int i = 0 ; i < this.recordingPath.lengthFile() ; i++) {
				String line = this.recordingPath.getChamp(i);
				// System.out.println(line);
				String[] cute = line.split("\t");
				if (cute.length <= 1) { throw new DataException("Pathway File : Données incorrectes. "); }
				// System.out.println("\t"+cute[0]+"\t"+cute[1]);
				if (pathSize == 0) { /** (currentPath == null) */
					/** Read a pathway, first line. */
					if (currentPath != null) { this.addPathway(currentPath); }
					currentPath = null;
					currentPath = new Pathway(cute[0]);
					pathSize = Integer.parseInt(cute[1]);
					// System.out.println("\t"+cute[0]+"\t"+pathSize);
				} else {
					/** Other lines of a given pathway : genes. */
					line = line.substring(1);
					currentPath.getGeneMoreListe()
							.addLineOfGeneMoreListeFile(line);
					pathSize--;
					// System.out.println("\t"+cute[0]+"\t"+pathSize);
				}
				
			}
			/** Add the last parsed Pathway. */
			if (currentPath != null) { this.addPathway(currentPath); }
		} 
		catch (IOException e)	{ e.printStackTrace(); } 
		catch (DataException e)	{ Terminal.ecrireStringln(e.getRequest()); }
	}
	
	private void printPathwayFile() {
		try { this.recordingPath.printFile(); } 
		catch (DataException e) { Terminal.ecrireStringln(e.getRequest()); }
	}
	
	public void readFile() { this.readPathWayFile(); }
	
	public void printFile() { this.printPathwayFile(); }

	public void addToChamps(String line) { 
		this.recordingPath.addToChamps(line);
		this.printPathwayFile(); 
	}
	
	public void setChamps(int index,String line) { 
		this.recordingPath.setChamps(index, line);
		this.printPathwayFile(); 
	}

	public void removeChamps(int i) { 
		this.recordingPath.removeChamps(i); 
		this.printPathwayFile(); 
	}

}

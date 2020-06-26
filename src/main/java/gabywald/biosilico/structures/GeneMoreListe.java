package gabywald.biosilico.structures;


import java.io.IOException;
import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.interfaces.StructureRecordFile;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
import gabywald.global.structures.IntegerListe;
import gabywald.global.structures.StringListe;
import gabywald.global.view.text.Terminal;

/**
 * This class describes a more complete list of Gene's with types. 
 * <br>Link to a file to record the list.  
 * @author Gabriel Chandesris (2010)
 * @see GeneListe
 * @see Pathway
 * @see gabywald.biosilico.view.GeneParametersViewer
 */
public class GeneMoreListe implements StructureRecordFile {
	/** List of Gene's. */
	private GeneListe genesStock;
	/** List of gene's types. */
	private IntegerListe geneTypes;
	/** Length of the list. */
	private int length;
	/** Location of the default file to record GeneMoreListe instance. */
	public static final String GENE_LIST_FILE 
		= FileBiological.DEFAULT_PATH_NAME+"definedGenes.txt";
	/** Location of the current defined file to record GeneMoreListe instance. */
	private String geneListFile = null;
	/** File for Gene records. */
	private File recordingGene;
	
	/** Default Constructor. */
	public GeneMoreListe() 
		{ this.init(); } // this.readGenesListeFile();
	
	public GeneMoreListe(String fileName,boolean defaultDir) {
		this.init();
		this.geneListFile = 
			((defaultDir)?FileBiological.DEFAULT_PATH_NAME:"")
			+fileName;
		// this.readGenesListeFile();
	}
	
	/**
	 * To add a Gene with given type. 
	 * @param gene (Gene)
	 * @param type (int)
	 */
	public void addGene(Gene gene,int type) {
		this.genesStock.addGene(gene);
		// this.geneNames.addString(gene.getName());
		this.geneTypes.addInteger(type);
		this.length++;
	}
	
	/**
	 * To add only a Gene instance (a default name and type are added). 
	 * @param gene (Gene)
	 * @deprecated use {@link GeneMoreListe#addGene(Gene, String, int)}
	 */
	public void addGene(Gene gene) {
		this.genesStock.addGene(gene);
		// this.geneNames.addString("UnNamedGene"+this.length);
		this.geneTypes.addInteger(0);
		this.length++;
	}
	
	public int length() { return this.length; }
	
	public StringListe getGenesNames() { 
		StringListe geneNames = new StringListe();
		for (int i = 0 ; i < this.genesStock.length() ; i++) {
			String tmpName = this.genesStock.getGene(i).getName();
			if (tmpName.equals(Gene.DEFAULT_GENE_NAME)) {
				tmpName += geneNames.length();
				this.genesStock.getGene(i).setName(tmpName);
			}
			geneNames.addString(tmpName);
		}
		return geneNames;
	}
	public String getGeneName(int i) { return this.genesStock.getGene(i).getName(); }
	public Gene getGene(int i) { return this.genesStock.getGene(i); }
	
	public Gene getGene(String geneName) {
		for (int i = 0 ; i < this.length() ; i++) {
			if (geneName.equals(this.getGeneName(i))) 
				{ return this.genesStock.getGene(i); }
		}
		return null;
	}
	
	public String getLastGeneName() 
		{ return this.genesStock
			.getGene(this.genesStock.length()-1).getName(); }
	
	public int getType(int i) 
		{ return this.geneTypes.getInteger(i).intValue(); }
	
	public void removeGene(int i) {
		// this.geneNames.removeString(i);
		this.genesStock.removeGene(i);
		this.geneTypes.removeInteger(i);
		this.removeChamps(i);
		this.length--;
	}
	
	private void init() {
		this.genesStock	= new GeneListe();
		// this.geneNames	= new StringListe();
		this.geneTypes	= new IntegerListe();
		this.length = 0;
	}
	
	/**
	 * To add a Gene, its name and its type into the liste. 
	 * @param line (String) "name\ttype(int)\tsequence\t[params(int,boolean]+"
	 * @return (Gene)
	 * @throws DataException
	 * @see GeneMoreListe#readGenesListeFile()
	 * @see PathwayListe#readPathWayFile()
	 */
	public Gene addLineOfGeneMoreListeFile(String line) throws DataException {
		String[] cute = line.split("\t");
		/** nom ; type ; code ; mutate ; duplicate ; delete ; 
		 * active ; minimal age ; maximal age ; sex ; mutation rate ; 
		 * others... */
		if (cute.length <= 1) { throw new DataException
				("GeneMoreListe File : Données incorrectes. "); }
		/** GeneGattaca.translation(cute[2], 0) */
		Gene result = GeneGattaca.getInstance(cute[2]);
		result.setName(cute[0]);
		if (result != null) { this.addGene
			(result, Integer.parseInt(cute[1])); }
		return result;
	}
	
	/** To read the Gene List File.  */
	private void readGenesListeFile() {
		this.init();
		try {
			if ( (this.geneListFile == null) || (this.geneListFile.equals("")) )
				{ this.geneListFile = GeneMoreListe.GENE_LIST_FILE; }
			this.recordingGene = File.loadFile(this.geneListFile);
			/** Terminal.ecrireStringln(this.geneListFile); */
			if (this.recordingGene.lengthFile() == 1) {
				String firstLine = this.recordingGene.getChamp(0);
				if (firstLine.endsWith("File Not Found")) {
					this.recordingGene.setChamps(new String[0]);
					this.recordingGene.printFile();
					this.recordingGene = File.loadFile(GeneMoreListe.GENE_LIST_FILE);
				}
			}
			for (int i = 0 ; i < this.recordingGene.lengthFile() ; i++) {
				this.addLineOfGeneMoreListeFile(this.recordingGene.getChamp(i));
			}
		} 
		catch (IOException e)	{ e.printStackTrace(); } 
		catch (DataException e)	{ Terminal.ecrireStringln(e.getRequest()); }
	}
	
	private void printGenesListeFile() {
		try { this.recordingGene.printFile(); } 
		catch (DataException e) { Terminal.ecrireStringln(e.getRequest()); }
	}
	
	public String getGeneString(String geneName) {
		for (int i = 0 ; i < this.length() ; i++) {
			if (geneName.equals(this.getGeneName(i))) 
				{ return this.recordingGene.getChamp(i); }
		}
		return "";
	}
	
	public void readFile() { this.readGenesListeFile(); }
	
	public void printFile() { this.printGenesListeFile(); }
	
	public void addToChamps(String line) { 
		this.recordingGene.addToChamps(line);
		this.printGenesListeFile(); 
	}
	
	public void setChamps(int index,String line) { 
		this.recordingGene.setChamps(index, line);
		this.printGenesListeFile(); 
	}

	public void removeChamps(int i) { 
		this.recordingGene.removeChamps(i); 
		this.printGenesListeFile(); 
	}
}

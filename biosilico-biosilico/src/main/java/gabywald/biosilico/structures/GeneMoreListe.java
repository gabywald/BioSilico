package gabywald.biosilico.structures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.data.FileBiological;
import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.genetics.GeneGattaca;
import gabywald.biosilico.interfaces.IStructureRecordFile;
import gabywald.global.data.File;
import gabywald.global.exceptions.DataException;
import gabywald.global.view.text.Terminal;

/**
 * This class describes a more complete list of Gene's with types. 
 * <br>Link to a file to record the list.  
 * @author Gabriel Chandesris (2010, 2020)
 * @see Pathway
 * @see gabywald.biosilico.view.GeneParametersViewer
 */
public class GeneMoreListe implements IStructureRecordFile {
	/** List of Gene's instances. */
	private List<Gene> genesStock;
	/** List of Gene's types. */
	private List<Integer> geneTypes;
	/** Location of the default file to record GeneMoreListe instance. */
	public static final String GENE_LIST_FILE = FileBiological.DEFAULT_PATH_NAME + "definedGenes.txt";
	/** Location of the current defined file to record GeneMoreListe instance. */
	private String geneListFile = null;
	/** File for Gene records. */
	private File recordingGene;
	
	/** Default Constructor. */
	public GeneMoreListe() { 
		this.genesStock	= new ArrayList<Gene>();
		this.geneTypes	= new ArrayList<Integer>();
	}
	
	public GeneMoreListe(String fileName, boolean defaultDir) {
		this();
		this.geneListFile = ((defaultDir)?FileBiological.DEFAULT_PATH_NAME:"")+fileName;
	}
	
	/**
	 * To add a Gene with given type. 
	 * @param gene (Gene)
	 */
	public void addGene(Gene gene) {
		this.addGene(gene, -1);
	}
	
	/**
	 * To add a Gene with given type. 
	 * @param gene (Gene)
	 * @param type (int)
	 */
	public void addGene(Gene gene, int type) {
		this.genesStock.add(gene);
		this.geneTypes.add(type);
	}
	
	public int length() { return this.genesStock.size(); }
	
	public List<String> getGenesNames() { 
		List<String> geneNames = new ArrayList<String>();
		
		this.genesStock.stream().forEach( gene -> {
			StringBuilder sbTmp = new StringBuilder();
			sbTmp.append( gene.getName() );
			if (gene.getName().equals(Gene.DEFAULT_GENE_NAME)) {
				sbTmp.append( geneNames.size() );
				gene.setName( sbTmp.toString() );
			}
			geneNames.add( sbTmp.toString() );
		});
		return geneNames;
	}
	
	public String getGeneName(int i)	{ return this.genesStock.get(i).getName(); }
	public Gene getGene(int i)			{ return this.genesStock.get(i); }
	
	public Gene getGene(String geneName) {
		for (int i = 0 ; i < this.length() ; i++) {
			if (geneName.equals(this.getGeneName(i))) 
				{ return this.genesStock.get(i); }
		}
		return null;
	}
	
	public String getLastGeneName() 
		{ return this.genesStock.get(this.genesStock.size()-1).getName(); }
	
	public int getType(int i) 
		{ return this.geneTypes.get(i).intValue(); }
	
	public void removeGene(int i) {
		this.genesStock.remove(i);
		this.geneTypes.add(i);
		this.removeChamps(i);
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
		// ***** name ; type ; code ; mutate ; duplicate ; delete ; active ; minimal age ; maximal age ; sex ; mutation rate ; others... 
		if (cute.length <= 1)	{ throw new DataException("GeneMoreListe File : incorrect data. "); }
		// ***** GeneGattaca.translation(cute[2], 0) 
		Gene result = GeneGattaca.getInstance(cute[2]);
		result.setName(cute[0]);
		if (result != null)		{ this.addGene(result, Integer.parseInt(cute[1])); }
		return result;
	}
	
	/** To read the Gene List File.  */
	private void readGenesListeFile() {
		try {
			if ( (this.geneListFile == null) || (this.geneListFile.equals("")) )
				{ this.geneListFile = GeneMoreListe.GENE_LIST_FILE; }
			this.recordingGene = File.loadFile(this.geneListFile);

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
	
	public void readFile()	{ this.readGenesListeFile(); }
	
	public void printFile()	{ this.printGenesListeFile(); }
	
	public void addToChamps(String line) { 
		this.recordingGene.addToChamps(line);
		this.printGenesListeFile(); 
	}
	
	public void setChamps(int index, String line) { 
		this.recordingGene.setChamps(index, line);
		this.printGenesListeFile(); 
	}

	public void removeChamps(int i) { 
		this.recordingGene.removeChamps(i); 
		this.printGenesListeFile(); 
	}
}

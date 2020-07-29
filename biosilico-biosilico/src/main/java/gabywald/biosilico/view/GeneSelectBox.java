package gabywald.biosilico.view;

import java.util.ArrayList;
import java.util.List;

import gabywald.biosilico.genetics.Gene;
import gabywald.biosilico.structures.GeneMoreListe;
import gabywald.global.view.graph.SelectBox;

/**
 * To class defines specifically a Select Box for Gene's Stock. 
 * @author Gabriel Chandesris (2010)
 */
@SuppressWarnings("serial")
public class GeneSelectBox extends SelectBox {
	/** The stock of Gene's. */
	private GeneMoreListe geneStock;
	
	/** Default Constructor. */
	public GeneSelectBox() {
		super(); /** To explicitely link the superConstructor. */
		this.initSelection();
	}
	
	public void initSelection() {
		this.geneStock		= new GeneMoreListe();
		this.geneStock.readFile();
		this.stringStock	= new ArrayList<String>();
		this.stringStock.add("Select a defined Gene");
		// TODO java 8 stream on GeneStock List !!
		for (int i = 0 ; i < this.geneStock.length() ; i++) 
			{ this.stringStock.add(this.geneStock.getGeneName(i)); }
		
		super.initSelection();
	}
	
	public GeneMoreListe getGeneStock() { return this.geneStock; }
	
	public int getSelectedType() 
		{ return this.geneStock.getType(this.getSelectedIndex()-1); }
	
	public Gene getSelectedGene() 
		{ return this.geneStock.getGene(this.getSelectedIndex()-1); }
	
	public String getSelectedGeneName()	
		{ return this.getSelectedGene().getName(); }
	
	public List<String> getGeneNames()	
		{ return this.stringStock; }
	
	public String getGeneString(String name) 
		{ return this.geneStock.getGeneString(name); }
	
	public String getLastGeneName()	
		{ return this.geneStock.getLastGeneName(); }
	
	public void addGene(String oneMoreGene) 
		{ this.geneStock.addToChamps(oneMoreGene); }
	
	public void setCurrentGene(String oneMoreGene) 
		{ this.geneStock.setChamps(this.getSelectedIndex()-1, oneMoreGene); }
	
	public void remCurrentGene() 
		{ this.geneStock.removeGene(this.getSelectedIndex()-1); }
	
	public void remGene(int i) 
		{ this.geneStock.removeGene(i-1); }

}

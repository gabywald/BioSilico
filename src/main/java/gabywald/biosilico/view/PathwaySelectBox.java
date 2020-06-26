package gabywald.biosilico.view;

import gabywald.biosilico.structures.Pathway;
import gabywald.biosilico.structures.PathwayListe;
import gabywald.global.structures.StringListe;
import gabywald.global.view.graph.SelectBox;

public class PathwaySelectBox extends SelectBox {
	/** Container of Pathway's (stock) and link to File. */
	private PathwayListe pathStock;
	
	/** Default Constructor. */
	public PathwaySelectBox() {
		super(); /** To explicitely link the superConstructor. */
		this.initSelection();
	}

	public void initSelection() {
		this.pathStock = new PathwayListe();
		this.pathStock.readFile();
		
		this.stringStock = new StringListe();
		this.stringStock.addString("Select a metabolic way");
		for (int i = 0 ; i < this.pathStock.length() ; i++) 
			{ this.stringStock.addString(this.pathStock.getPathway(i).getName()); }
		
		super.initSelection();
	}
	
	public PathwayListe getPathStock()	{ return this.pathStock; }
	
	public Pathway getSelectedPathway() 
		{ return this.pathStock.getPathway(this.getSelectedIndex()-1); }
	
	public void addPathway(String oneMorePath) 
		{ this.pathStock.addToChamps(oneMorePath); }

}

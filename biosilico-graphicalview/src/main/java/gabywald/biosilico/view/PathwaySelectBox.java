package gabywald.biosilico.view;

import java.util.ArrayList;

import gabywald.biosilico.structures.Pathway;
import gabywald.biosilico.structures.PathwayListe;
import gabywald.global.view.graph.SelectBox;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
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
		
		this.stringStock = new ArrayList<String>();
		this.stringStock.add("Select a metabolic way");
		for (int i = 0 ; i < this.pathStock.length() ; i++) 
			{ this.stringStock.add(this.pathStock.getPathway(i).getName()); }
		
		super.initSelection();
	}
	
	public PathwayListe getPathStock()	{ return this.pathStock; }
	
	public Pathway getSelectedPathway() 
		{ return this.pathStock.getPathway(this.getSelectedIndex()-1); }
	
	public void addPathway(String oneMorePath) 
		{ this.pathStock.addToChamps(oneMorePath); }

}

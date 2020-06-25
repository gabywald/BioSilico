package gabywald.biosilico.view;

import javax.swing.JLabel;

import gabywald.biosilico.model.Chemicals;
import gabywald.global.structures.StringCoupleListe;
import gabywald.global.structures.StringListe;
import gabywald.global.view.graph.SelectBox;

/**
 * This class defines a selection menu of chemicals. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010)
 */
public class ChemicalSelectBox extends SelectBox {
	private static ChemicalSelectBox instance;
	/** The Stock of Chemical's Abbrv' and Names. */
	private StringCoupleListe chemicalStock;
	/** JLabel tto indicate the Selection Box. */
	private JLabel chemicalStockLabel;
	
	/** Default Constructor. */
	private ChemicalSelectBox() {
		super(); /** To explicitely link the superConstructor. */
		this.initSelection();
	}
	
	public static ChemicalSelectBox getInstance() {
		if (ChemicalSelectBox.instance == null) 
			{ ChemicalSelectBox.instance = new ChemicalSelectBox(); }
		return ChemicalSelectBox.instance;
	}
	
	
	public void initSelection() {
		this.chemicalStockLabel	= new JLabel("List of Chemicals : ");
		this.chemicalStock		= Chemicals.getChemicalListe();
		this.stringStock		= new StringListe();
		this.removeAllItems();
		for (int i = 0 ; i < this.chemicalStock.length() ; i++) {
//			System.out.println(i+"    "
//					+this.chemicalStock.getStringCouple(i).getValueA()+"    "
//					+this.chemicalStock.getStringCouple(i).getValueB());
			this.stringStock.addString(i+"    "
					+this.chemicalStock.getStringCouple(i).getValueA()+"    "
					+this.chemicalStock.getStringCouple(i).getValueB());
			this.addItem(this.stringStock.getString(i));
		}
	}
	
	public StringCoupleListe getChemicalStock() { return this.chemicalStock; }
	public JLabel getChemicalStockJLabel() { return this.chemicalStockLabel; }
}

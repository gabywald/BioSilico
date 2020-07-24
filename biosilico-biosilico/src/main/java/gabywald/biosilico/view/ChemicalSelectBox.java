package gabywald.biosilico.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import gabywald.biosilico.model.Chemicals;
import gabywald.global.structures.StringCouple;
import gabywald.global.view.graph.SelectBox;

/**
 * This class defines a selection menu of chemicals. 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public class ChemicalSelectBox extends SelectBox {
	private static ChemicalSelectBox instance;
	/** The Stock of Chemical's Abbrv' and Names. */
	private List<StringCouple> chemicalStock;
	/** JLabel to indicate the Selection Box. */
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
		this.stringStock		= new ArrayList<String>();
		this.removeAllItems();
		for (int i = 0 ; i < this.chemicalStock.size() ; i++) {
			this.stringStock.add(i+"    "
					+ this.chemicalStock.get(i).getValueA() + "    "
					+ this.chemicalStock.get(i).getValueB());
			this.addItem(this.stringStock.get(i));
		}
	}
	
	public List<StringCouple> getChemicalStock()	{ return this.chemicalStock; }
	public JLabel getChemicalStockJLabel()			{ return this.chemicalStockLabel; }
}

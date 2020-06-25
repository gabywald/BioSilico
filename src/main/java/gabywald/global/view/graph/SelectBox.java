package gabywald.global.view.graph;

import java.awt.event.ActionListener;

import gabywald.global.structures.StringListe;

import javax.swing.JComboBox;

/**
 * This class defines a specific JComboBox which can be adapted and updated by inheritance. 
 * @author Gabriel Chandesris (2010)
 */
public class SelectBox extends JComboBox {
	/** The stock of String's. */
	protected StringListe stringStock;
	/** Selection ComboBox of existent String's. */
	// protected JComboBox existentSelection;
	/** Index of selected String (if update to avoid event). */
	protected int selectedString = 0;
	
	/** Default Constructor with one item in Liste. */
	public SelectBox() {
		this.stringStock = new StringListe();
		this.stringStock.addString("Select a defined <String>"); 
		this.init();
	}
	
	/** 
	 * Constructor with a given StringListe. 
	 * @param stock (StringListe)
	 */
	public SelectBox(StringListe stock) 
		{ this.stringStock = stock;this.init(); }
	
	/**
	 * Construcotr with a given tab of String. 
	 * @param stock (String[])
	 */
	public SelectBox(String[] stock) 
		{ this.stringStock = new StringListe(stock);this.init(); }
	
	/** Constructor helper (initalization). */
	private void init() {
		this.removeAllItems();
		for (int i = 0 ; i < this.stringStock.length() ; i++) 
			{ this.addItem(this.stringStock.getString(i)); }
	}
	
	/** Aim of this method is to init the selection. */
	// public abstract void initSelection();
	// public abstract void actionPerformed(ActionEvent arg0);
	
	
	/** 
	 * This method aim to reinitialize current StringListe without any reaction of ActionListeners, keep selected index. 
	 * <br><b>Method to overload</b> and be called as super.initSelection()
	 * @see SelectBox#initSelection()
	 */
	protected void initSelection() {
		this.selectedString = this.getSelectedIndex();
		ActionListener tmp[] = this.getActionListeners();
		for (int i = 0 ; i < tmp.length ; i++) 
			{ this.removeActionListener(tmp[i]); }
		
		this.removeAllItems();
		for (int i = 0 ; i < this.stringStock.length() ; i++) 
			{ this.addItem(this.stringStock.getString(i)); }
		
		this.setSelectedIndex(0);
		
		for (int i = 0 ; i < tmp.length ; i++) 
			{ this.addActionListener(tmp[i]); }
		if (this.selectedString > 0 ) { this.setSelection(this.selectedString); }
	}
	
	/** 
	 * To get current selected index. 
	 * @return (int)
	 * @see JComboBox#getSelectedIndex()
	 */
	public int getSelected() { return this.getSelectedIndex(); }
	
	/**
	 * To set the selection without event generation (temporarily stock ActionListeners). 
	 * @param elt (int) index to select
	 */
	public void setSelection(int elt) {
		// this.selectedString = elt;
		ActionListener tmp[] = this.getActionListeners();
		for (int i = 0 ; i < tmp.length ; i++) 
			{ this.removeActionListener(tmp[i]); }
		
		this.setSelectedIndex(elt);
		
		for (int i = 0 ; i < tmp.length ; i++) 
			{ this.addActionListener(tmp[i]); }
	}
	

}

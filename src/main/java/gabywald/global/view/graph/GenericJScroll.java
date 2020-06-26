package gabywald.global.view.graph;

import gabywald.global.structures.StringListe;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * This class provides generation of a easy-to-use JScrollPane 
 * containing a <b>String's</b> JList. 
 * @author Gabriel Chandesris (2010)
 */
public class GenericJScroll extends JScrollPane {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 423L;
	/** Setted to 27. */
	private static final int DEFAULT_ROW_COUNT = 25;
	
	private int rowCount;
	private JList itemListe;
	private StringListe stringListe;

	/** Default Constructor. */
	public GenericJScroll () {
		this.rowCount 		= GenericJScroll.DEFAULT_ROW_COUNT;
		this.stringListe	= new StringListe();
		this.init();
	}
	
	/** 
	 * Constructor with given show of row. 
	 * @param rowCount (int)
	 */
	public GenericJScroll (int rowCount) {
		this.rowCount 		= rowCount;
		this.stringListe	= new StringListe();
		this.init();
	}
	
	public GenericJScroll(String[] stringListe) {
		this.rowCount 		= GenericJScroll.DEFAULT_ROW_COUNT;
		this.setStringListe(stringListe);
	}
	
	public GenericJScroll(StringListe stringListe) { 
		this.rowCount 		= GenericJScroll.DEFAULT_ROW_COUNT;
		this.setStringListe(stringListe); 
	}
	
	private void init() {
		this.itemListe		= new JList(this.stringListe.getListeStrings());
		this.itemListe.setLayoutOrientation(JList.VERTICAL);
		this.itemListe.setVisibleRowCount(this.rowCount);
		this.itemListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// this.itemListe.setSize(150, 500);
		this.setViewportView(this.itemListe);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// this.setSize(150, 300);
	}
	
	public void setStringListe(String[] stringListe) 
		{ this.setStringListe(new StringListe(stringListe)); }
	
	public void setStringListe(StringListe stringListe) 
		{ this.stringListe	= stringListe;this.init(); }
	
	public StringListe getStringListe()
		{ return this.stringListe; }
	
	public void addString(String elt) 
		{ this.stringListe.addString(elt);this.init(); }
	
	public int length() { return this.stringListe.length(); }
	
	/** Remove current selection of the list. */
	public void removeCurrentSelection() {
		int selectedIndex = this.itemListe.getSelectedIndex();
		if (selectedIndex != -1) {
			this.stringListe.removeString(selectedIndex);
			this.init();
		}
	}
	
	/**
	 * Move current selection of the list up or down. 
	 * @param upOrDown (boolean) 'true' to up and 'false' to down. 
	 */
	public void moveCurrentSelection(boolean upOrDown) {
		int selectedIndex = this.itemListe.getSelectedIndex();
		if (selectedIndex != -1) {
			if (upOrDown) 
				{ this.stringListe.exchange(selectedIndex-1, selectedIndex); } 
			else 
				{ this.stringListe.exchange(selectedIndex+1, selectedIndex); }
			this.init();
		}
	}
	
	public int getSelectedIndex() 
		{ return this.itemListe.getSelectedIndex(); }
	
	public int[] getSelectedIndices() 
		{ return this.itemListe.getSelectedIndices(); }
	
	public String getSelectedValue()
		{ return (String)this.itemListe.getSelectedValue(); }
	
	public String[] getSelectedValues() {
		StringListe tmp = new StringListe();
		Object[] bobs = this.itemListe.getSelectedValues();
		for (int i = 0 ; i < bobs.length ; i++) 
			{ tmp.addString((String)bobs[i]); }
		return tmp.getListeStrings(); 
	}
	
	public void enableItemList(boolean b) 
		{ this.itemListe.setEnabled(b); }
}

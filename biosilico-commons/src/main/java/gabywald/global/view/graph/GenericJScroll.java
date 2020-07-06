package gabywald.global.view.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * This class provides generation of a easy-to-use JScrollPane 
 * containing a <b>String's</b> JList. 
 * @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public class GenericJScroll extends JScrollPane {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 423L;
	/** Setted to 27. */
	private static final int DEFAULT_ROW_COUNT = 25;
	
	private int rowCount;
	private JList<String> itemListe;
	private List<String> stringListe;

	/** Default Constructor. */
	public GenericJScroll () {
		this(GenericJScroll.DEFAULT_ROW_COUNT);
	}
	
	/** 
	 * Constructor with given show of row. 
	 * @param rowCount (int)
	 */
	public GenericJScroll (int rowCount) {
		this.rowCount 		= rowCount;
		this.stringListe	= new ArrayList<String>();
		this.init();
	}
	
	public GenericJScroll(String[] strings) {
		this(Arrays.asList(strings));
	}
	
	public GenericJScroll(List<String> stringListe) { 
		this.rowCount 		= GenericJScroll.DEFAULT_ROW_COUNT;
		this.setStringListe(stringListe); 
		this.init();
	}
	
	private void init() {
		this.itemListe		= new JList<String>(new Vector<String>(this.stringListe));
		this.itemListe.setLayoutOrientation(JList.VERTICAL);
		this.itemListe.setVisibleRowCount(this.rowCount);
		this.itemListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// this.itemListe.setSize(150, 500);
		this.setViewportView(this.itemListe);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// this.setSize(150, 300);
	}
	
	public void setStringListe(List<String> stringListe) 
		{ this.stringListe	= stringListe; }
	
	public List<String> getStringListe()
		{ return this.stringListe; }
	
	public void addString(String elt) 
		{ this.stringListe.add(elt);this.init(); }
	
	public int length() { return this.stringListe.size(); }
	
	/** Remove current selection of the list. */
	public void removeCurrentSelection() {
		int selectedIndex = this.itemListe.getSelectedIndex();
		if (selectedIndex != -1) {
			this.stringListe.remove(selectedIndex);
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
			this.stringListe.add( selectedIndex + ((upOrDown)?+1:-1), this.stringListe.remove(selectedIndex) );
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
		List<String> tmp	= new ArrayList<String>();
		List<String> bobs	= this.itemListe.getSelectedValuesList();
		bobs.stream().forEach( f -> tmp.add(f) );
		return tmp.toArray(new String[0]); 
	}
	
	public void enableItemList(boolean b) 
		{ this.itemListe.setEnabled(b); }
}

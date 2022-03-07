package gabywald.global.view.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

/**
 * This class provides generation of a easy-to-use JScrollPane 
 * containing a <b>String's</b> JList. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 */
@SuppressWarnings("serial")
public class GenericJScroll extends JScrollPane {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 423L;
	/** Setted to 25. */
	private static final int DEFAULT_ROW_COUNT = 25;
	
	private int rowCount;
	private JList<String> itemListe;
	private List<String> stringListe;

	/** Default Constructor. */
	public GenericJScroll() 
		{ this(GenericJScroll.DEFAULT_ROW_COUNT, new ArrayList<String>()); }
	
	/** 
	 * Constructor with given show of row. 
	 * @param rowCount (int)
	 */
	public GenericJScroll(int rowCount) 
		{ this(rowCount, new ArrayList<String>()); }
	
	/**
	 * Constructor. 
	 * @param stringListe
	 */
	public GenericJScroll(List<String> stringListe) 
		{ this(GenericJScroll.DEFAULT_ROW_COUNT, stringListe); }
	
	/**
	 * Constructor. 
	 * @param rowCount
	 * @param stringListe
	 */
	public GenericJScroll(int rowCount, List<String> stringListe) { 
		this.rowCount 		= rowCount;
		this.stringListe	= stringListe;
		this.init();
	}
	
	private void init() {
		this.itemListe	= new JList<String>(new Vector<String>(this.stringListe));
		this.itemListe.setLayoutOrientation(JList.VERTICAL);
		this.itemListe.setVisibleRowCount(this.rowCount);
		this.itemListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.itemListe.setAutoscrolls( true );
		
//		this.itemListe.setCellRenderer(new ListCellRenderer<String>() {
//			@Override
//			public Component getListCellRendererComponent(	JList<? extends String> list, 
//															String value, int index,
//															boolean isSelected, 
//															boolean cellHasFocus) {
//				String s = value.toString();
//				JLabel toReturn = new JLabel( s );
//				toReturn.setText(s);
//				toReturn.setIcon( null );
//				if (isSelected) {
//					toReturn.setBackground(list.getSelectionBackground());
//					toReturn.setForeground(list.getSelectionForeground());
//				} else {
//					toReturn.setBackground(list.getBackground());
//					toReturn.setForeground(list.getForeground());
//				}
//				toReturn.setEnabled(list.isEnabled());
//				toReturn.setFont(list.getFont());
//				toReturn.setOpaque(true);
//				return toReturn;
//			}
//		});
		
		// this.itemListe.setSize(150, 500);
		this.setViewportView(this.itemListe);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setAutoscrolls(true);
		// this.setSize(150, 300);
		
		this.addListSelectionListener( new GeneJPanelListSelectionListener());
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
	
	// TODO ?? public void setEditable(); ?? 
	
	public int getSelectedIndex() 
		{ return this.itemListe.getSelectedIndex(); }
	
	public void setSelectedIndex(int index) {
		this.itemListe.setSelectedIndex(index);
		this.itemListe.setSelectedValue(this.stringListe.get(index), true);
	}
	
	public int[] getSelectedIndices() 
		{ return this.itemListe.getSelectedIndices(); }
	
	public void setSelectedValue(String value) 
		{ this.itemListe.setSelectedValue(value, true); }
	
	public String getSelectedValue()
		{ return (String)this.itemListe.getSelectedValue(); }
	
	public String[] getSelectedValues() 
		{ return this.itemListe.getSelectedValuesList().stream().toArray(String[]::new); }
	
	public void setEditable(boolean b) {
		this.enableItemList(b);
		this.setEnabled(b);
		this.verticalScrollBar.setEnabled(b);
	}
	
	public void enableItemList(boolean b) 
		{ this.itemListe.setEnabled(b); }
	
	public void addListSelectionListener(ListSelectionListener listener) 
		{ this.itemListe.addListSelectionListener(listener); }
}

package gabywald.biosilico.view;

import gabywald.biosilico.data.FileOrganism;
import gabywald.biosilico.structures.ExtendedLineageItem;
import gabywald.global.view.graph.GenericJScroll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class defines a Lineage List JScroll which can reacts. 
 * <br>Add / Removing buttons. 
 * <br>Up / Down buttons. 
 * @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public class LineageListJScroll extends GenericJScroll 
								implements ActionListener {
	/** To move up or down a lineage in the list. */
	private JButton uppeLineage, downLineage;
	/** To add / remove a lineage in the list. */
	private JButton addLineage, remLineage;
	
	/** Extended lineage of the Organism. */
	private List<ExtendedLineageItem> extendedlineage;
	
	/** Default Constructor. */
	public LineageListJScroll() {
		this.addLineage			= new JButton("Add Lineage");
		this.remLineage			= new JButton("Rem. Lineage");
		this.uppeLineage		= new JButton("Up Lineage");
		this.downLineage		= new JButton("Down Lineage");
		this.addLineage.addActionListener(this);
		this.remLineage.addActionListener(this);
		this.uppeLineage.addActionListener(this);
		this.downLineage.addActionListener(this);
		
		this.extendedlineage = new ArrayList<ExtendedLineageItem>();
	}
	
	public JButton getAddButton()	{ return this.addLineage; }
	public JButton getRemButton()	{ return this.remLineage; }
	public JButton getUppButton()	{ return this.uppeLineage; }
	public JButton getDowButton()	{ return this.downLineage; }
	
	public List<ExtendedLineageItem> getExtendedLineage() 
		{ return this.extendedlineage; }

	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.remLineage)) {
			this.removeCurrentSelection();
			int selected = this.getSelectedIndex();
			if (selected != -1) 
				{ this.extendedlineage.remove(selected); }
			if (this.length() < 1) 
				{ this.setEnabled(false); }
		} else if (source.equals(this.addLineage)) {
			/** TODO add lineage [select a file of an organism] */
			FileOrganism toLoad = OrganismSelectJPanel.loadOrganism(this);
			if (toLoad != null) {
				String rank = toLoad.getRank();
				String unID = toLoad.getUniqueID();
				String scie = toLoad.getScientificName();
				this.addString(scie);
				this.extendedlineage.add(new ExtendedLineageItem(unID, scie, rank));
			}
		}
		else if (source.equals(this.uppeLineage)) 
			{ this.moveCurrentSelection(true); } 
		else if (source.equals(this.downLineage)) 
			{ this.moveCurrentSelection(false); } 
	}
	
	public void setEnabled(boolean b) {
		this.addLineage.setEnabled(b);
		this.remLineage.setEnabled(b);
		this.uppeLineage.setEnabled(b);
		this.downLineage.setEnabled(b);
	}
	
	public void moveCurrentSelection(boolean upOrDown) {
		int selectedIndex = this.getSelectedIndex();
		if (selectedIndex != -1) {
			if (upOrDown) 
				{ Collections.swap(this.extendedlineage, selectedIndex-1, selectedIndex); } 
			else 
				{ Collections.swap(this.extendedlineage, selectedIndex+1, selectedIndex); }
		}
		super.moveCurrentSelection(upOrDown);
	}

}

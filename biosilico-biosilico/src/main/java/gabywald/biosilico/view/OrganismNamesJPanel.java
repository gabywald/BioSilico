package gabywald.biosilico.view;

import java.awt.event.ActionEvent;
import java.util.List;

import gabywald.global.view.graph.GenericJScroll;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public class OrganismNamesJPanel extends GeneKitsGBJPanel {
	/** to dhox and changes names of current organism (scientific, common and include). */
	private JTextField scientificName,biosilicosName,commonName,includName;
	/** List of Other names. */
	private GenericJScroll othersNamesList;
	/** To add a new other name. */
	private JTextField othersName;
	/** To activate the add of a new other name. */
	private JButton addOtherName;
	
	public OrganismNamesJPanel() 
		{ this.init();this.enablePanel(false); }
	
	private void init() {
		this.scientificName		= new JTextField();
		this.biosilicosName		= new JTextField();
		this.commonName			= new JTextField();
		this.includName			= new JTextField();
		this.othersNamesList	= new GenericJScroll(5);
		this.othersName			= new JTextField();
		this.addOtherName		= new JButton("Add Other Name");
		
		this.addOtherName.addActionListener(this);
		
		this.addBagComponent(new JLabel("Scientific name : ")	, 0, 0);
		this.addBagComponent(this.scientificName				, 1, 0, 2);
		this.addBagComponent(new JLabel("BioSilico name : ")	, 0, 1);
		this.addBagComponent(this.biosilicosName				, 1, 1, 2);
		this.addBagComponent(new JLabel("Common name : ")		, 0, 2);
		this.addBagComponent(this.commonName					, 1, 2, 2);
		this.addBagComponent(new JLabel("Include : ")			, 0, 3);
		this.addBagComponent(this.includName					, 1, 3, 2);
		this.addBagComponent(new JLabel("Others Names : ")		, 0, 4);
		this.addBagComponent(this.othersNamesList				, 1, 4, 2, 3);
		this.addBagComponent(this.othersName					, 0, 5);
		this.addBagComponent(this.addOtherName					, 0, 6);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.addOtherName)) {
			if (!this.othersName.getText().equals("")) {
				this.othersNamesList.addString(this.othersName.getText());
				this.othersName.setText("");
			}
		}
	}
	public void enablePanel(boolean b) {
		this.addOtherName.setEnabled(b);
		this.biosilicosName.setEnabled(b);
		this.scientificName.setEnabled(b);
		this.commonName.setEnabled(b);
		this.includName.setEnabled(b);
		this.othersName.setEnabled(b);
		this.othersNamesList.setEnabled(b);
	}
	
	public String getScientificName() 
		{ return this.scientificName.getText(); }
	public String getBioSilicoName() 
		{ return this.biosilicosName.getText(); }
	public String getCommonName() 
		{ return this.commonName.getText(); }
	public String getIncludeName() 
		{ return this.includName.getText(); }
	public List<String> getOthersNames()
		{ return this.othersNamesList.getStringListe(); }

	public void setScientificName(String name) 
		{ this.scientificName.setText(name); }
	public void setBioSilicoName(String name) 
		{ this.biosilicosName.setText(name); }
	public void setCommonName(String name)
		{ this.commonName.setText(name); }
	public void setIncludeName(String name)
		{ this.includName.setText(name); }
	public void setOtherNames(List<String> names) 
		{ this.othersNamesList.setStringListe(names); }
	public void addOtherName(String name)
		{ this.othersNamesList.addString(name); }

}

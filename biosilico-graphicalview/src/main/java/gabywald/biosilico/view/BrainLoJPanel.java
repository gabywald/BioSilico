package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BrainLobeGene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to BrainLobeGene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.BrainLobeGene
 * TODO externalize ActionListener from BrainLoJPanel !
 */
@SuppressWarnings("serial")
public class BrainLoJPanel extends GeneJPanel<BrainLobeGene>
		implements ActionListener {
	/** Some Label's. */
	private JLabel	restLabel, threLabel, descLabel, dendrminLabel, dendrmaxLabel,
					proxLabel, heightLabel, widthLabel, posxLabel, posyLabel; 
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll	restField, threField, descField, dendrminField, dendrmaxField,
							proxField, repyField, heightField, widthField, posxField, posyField;
	/** Some ChecjBox'es. */
	private JCheckBox reprBox, wtaaBox, replBox;
	
	public BrainLoJPanel() {
		
		this.reprBox	= new JCheckBox("repr. neurons",false);
		this.wtaaBox	= new JCheckBox("WTA",false);
		this.replBox	= new JCheckBox("replace lobe",false);
		
		this.restLabel		= new JLabel("Rest State : ");
		this.threLabel		= new JLabel("Threshold : ");
		this.descLabel		= new JLabel("Descent : ");
		this.dendrminLabel	= new JLabel("Min. dendrites : ");
		this.dendrmaxLabel	= new JLabel("Max. dendrites : ");
		this.proxLabel		= new JLabel("Proximity : ");
		// ***** this.repyLabel		= new JLabel("Reproduct. : "); 
		this.heightLabel	= new JLabel("Heigth : ");
		this.widthLabel		= new JLabel("Width : ");
		this.posxLabel		= new JLabel("X Position : ");
		this.posyLabel		= new JLabel("Y Position : ");
		
		this.restLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.threLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.descLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.dendrminLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.dendrmaxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.proxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		// ***** this.repyLabel.setHorizontalAlignment(SwingConstants.RIGHT); 
		this.heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.restField		= SpecificJScroll.getSpecificJScroll0to999();
		this.threField		= SpecificJScroll.getSpecificJScroll0to999();
		this.descField		= SpecificJScroll.getSpecificJScroll0to999();
		this.dendrminField	= SpecificJScroll.getSpecificJScroll0to99();
		this.dendrmaxField	= SpecificJScroll.getSpecificJScroll0to99();
		this.proxField		= SpecificJScroll.getSpecificJScroll0to99();
		this.repyField		= SpecificJScroll.getSpecificJScroll0to99();
		this.heightField	= SpecificJScroll.getSpecificJScroll0to99();
		this.widthField		= SpecificJScroll.getSpecificJScroll0to99();
		this.posxField		= SpecificJScroll.getSpecificJScroll0to99();
		this.posyField		= SpecificJScroll.getSpecificJScroll0to99();
		
		/** To enable if replBox is checked */
		this.repyField.setSelectedIndex( 0 );
		this.repyField.setEnabled(false);
		this.reprBox.addActionListener(this);
		
		/** Checkbox'es first. */
		this.addBagComponent(this.replBox, 0, 7);
		this.addBagComponent(this.wtaaBox, 1, 7);
		this.addBagComponent(this.reprBox, 2, 7);
		this.addBagComponent(this.repyField, 3, 7);
		
		// ***** Neuron's calculation parameters. 
		this.addBagComponent(this.restLabel, 0, 8);
		this.addBagComponent(this.restField, 1, 8);
		this.addBagComponent(this.threLabel, 2, 8);
		this.addBagComponent(this.threField, 3, 8);
		this.addBagComponent(this.descLabel, 0, 9);
		this.addBagComponent(this.descField, 1, 9);
		this.addBagComponent(this.proxLabel, 2, 9);
		this.addBagComponent(this.proxField, 3, 9);
		// ***** Size and position of lobe. 
		this.addBagComponent(this.heightLabel, 0, 10);
		this.addBagComponent(this.heightField, 1, 10);
		this.addBagComponent(this.widthLabel, 2, 10);
		this.addBagComponent(this.widthField, 3, 10);
		this.addBagComponent(this.posxLabel, 0, 11);
		this.addBagComponent(this.posxField, 1, 11);
		this.addBagComponent(this.posyLabel, 2, 11);
		this.addBagComponent(this.posyField, 3, 11);
		// ***** Dendritic connections. 
		this.addBagComponent(this.dendrminLabel, 0, 12);
		this.addBagComponent(this.dendrminField, 1, 12);
		this.addBagComponent(this.dendrmaxLabel, 2, 12);
		this.addBagComponent(this.dendrmaxField, 3, 12);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.reprBox)) {
			if (this.reprBox.isSelected()) 
				{ this.repyField.setEnabled(true); } 
			else { this.repyField.setEnabled(false); }
		}
	}
	
	public boolean getReproduce()	{ return this.reprBox.isSelected(); }
	public boolean getWTA()			{ return this.wtaaBox.isSelected(); }
	public boolean getReplace()		{ return this.replBox.isSelected(); }
	
	public int getRestState()	{ return this.restField.getSelectedIndex(); }
	public int getThreshold()	{ return this.threField.getSelectedIndex(); }
	public int getDescent()		{ return this.descField.getSelectedIndex(); }
	public int getProximity()	{ return this.proxField.getSelectedIndex(); }
	public int getReproduct()	{ return this.repyField.getSelectedIndex(); }
	public int getLobeHeight()	{ return this.heightField.getSelectedIndex(); }
	public int getLobeWidth()	{ return this.widthField.getSelectedIndex(); }
	public int getLobePosX()	{ return this.posxField.getSelectedIndex(); }
	public int getLobePosY()	{ return this.posyField.getSelectedIndex(); }
	public int getDendritMin()	{ return this.dendrminField.getSelectedIndex(); }
	public int getDendritMax()	{ return this.dendrmaxField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.reprBox.setSelected(false);
		this.wtaaBox.setSelected(false);
		this.replBox.setSelected(false);
		this.restField.setSelectedIndex( 000 );
		this.threField.setSelectedIndex( 010 );
		this.descField.setSelectedIndex( 001 );
		this.dendrminField.setSelectedIndex( 00 );
		this.dendrmaxField.setSelectedIndex( 10 );
		this.proxField.setSelectedIndex( 00 );
		this.repyField.setSelectedIndex( 00 );
		this.heightField.setSelectedIndex( 01 );
		this.widthField .setSelectedIndex( 01 );
		this.posxField  .setSelectedIndex( 00 );
		this.posyField  .setSelectedIndex( 00 );
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (BrainLobeGene)
	 */
	@Override
	public void setPanelSpecificValueWith(BrainLobeGene gene) {
		super.setPanelValueWith(gene);
		this.restField.setSelectedIndex(gene.getRestState());
		this.threField.setSelectedIndex(gene.getThreshold());
		this.descField.setSelectedIndex(gene.getDescent());
		this.dendrminField.setSelectedIndex(gene.getDendritMin());
		this.dendrmaxField.setSelectedIndex(gene.getDendritMax());
		this.proxField.setSelectedIndex(gene.getProximity());
		this.reprBox.setSelected(gene.getReproduce());
		this.repyField.setSelectedIndex(gene.getReproduct());
		this.wtaaBox.setSelected(gene.getWTA());
		this.heightField.setSelectedIndex(gene.getLobeHeight());
		this.widthField .setSelectedIndex(gene.getLobeWidth());
		this.posxField  .setSelectedIndex(gene.getLobePosX());
		this.posyField  .setSelectedIndex(gene.getLobePosY());
		this.replBox.setSelected(gene.getReplace());
	}
	
}

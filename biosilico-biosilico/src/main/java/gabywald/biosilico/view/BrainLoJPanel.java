package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BrainLobeGene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to BrainLobeGene. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see gabywald.biosilico.genetics.BrainLobeGene
 */
@SuppressWarnings("serial")
public class BrainLoJPanel extends GeneJPanel implements ActionListener {
	/** Some Label's. */
	private JLabel	restLabel, threLabel, descLabel, dendrminLabel, dendrmaxLabel,
					proxLabel, heightLabel, widthLabel, posxLabel, posyLabel; 
	/** Some TextField's. */
	private JTextField	restField, threField, descField, dendrminField, dendrmaxField,
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
		
		this.restField		= new JTextField("000");
		this.threField		= new JTextField("000");
		this.descField		= new JTextField("000");
		this.dendrminField	= new JTextField("00");
		this.dendrmaxField	= new JTextField("00");
		this.proxField		= new JTextField("00");
		this.repyField		= new JTextField("00");
		this.heightField	= new JTextField("00");
		this.widthField		= new JTextField("00");
		this.posxField		= new JTextField("00");
		this.posyField		= new JTextField("00");
		
		/** To enable if replBox is checked */
		this.repyField.setText("0");
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
	
	public int getRestState()	{ return Integer.parseInt(this.restField.getText()); }
	public int getThreshold()	{ return Integer.parseInt(this.threField.getText()); }
	public int getDescent()		{ return Integer.parseInt(this.descField.getText()); }
	public int getProximity()	{ return Integer.parseInt(this.proxField.getText()); }
	public int getReproduct()	{ return Integer.parseInt(this.repyField.getText()); }
	public int getLobeHeight()	{ return Integer.parseInt(this.heightField.getText()); }
	public int getLobeWidth()	{ return Integer.parseInt(this.widthField.getText()); }
	public int getLobePosX()	{ return Integer.parseInt(this.posxField.getText()); }
	public int getLobePosY()	{ return Integer.parseInt(this.posyField.getText()); }
	public int getDendritMin()	{ return Integer.parseInt(this.dendrminField.getText()); }
	public int getDendritMax()	{ return Integer.parseInt(this.dendrmaxField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.reprBox.setSelected(false);
		this.wtaaBox.setSelected(false);
		this.replBox.setSelected(false);
		this.restField.setText("000");
		this.threField.setText("000");
		this.descField.setText("000");
		this.dendrminField.setText("00");
		this.dendrmaxField.setText("00");
		this.proxField.setText("00");
		this.repyField.setText("00");
		this.heightField.setText("00");
		this.widthField.setText("00");
		this.posxField.setText("00");
		this.posyField.setText("00");
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (BrainLobeGene)
	 */
	public void setPanelSpecificValueWith(BrainLobeGene gene) {
		super.setPanelValueWith(gene);
		this.restField.setText(GeneJPanel.convertThreeChars(gene.getRestState()));
		this.threField.setText(GeneJPanel.convertThreeChars(gene.getThreshold()));
		this.descField.setText(GeneJPanel.convertThreeChars(gene.getDescent()));
		this.dendrminField.setText(GeneJPanel.convertThreeChars(gene.getDendritMin()));
		this.dendrmaxField.setText(GeneJPanel.convertThreeChars(gene.getDendritMax()));
		this.proxField.setText(GeneJPanel.convertThreeChars(gene.getProximity()));
		this.reprBox.setSelected(gene.getReproduce());
		this.repyField.setText(GeneJPanel.convertThreeChars(gene.getReproduct()));
		this.wtaaBox.setSelected(gene.getWTA());
		this.heightField.setText(GeneJPanel.convertTwoChars(gene.getLobeHeight()));
		this.widthField.setText(GeneJPanel.convertTwoChars(gene.getLobeWidth()));
		this.posxField.setText(GeneJPanel.convertTwoChars(gene.getLobePosX()));
		this.posyField.setText(GeneJPanel.convertTwoChars(gene.getLobePosY()));
		this.replBox.setSelected(gene.getReplace());
	}
	
}

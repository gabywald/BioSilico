package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BrainGene;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to BrainGene. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see gabywald.biosilico.genetics.BrainGene
 */
@SuppressWarnings("serial")
public class BrainGeJPanel extends GeneJPanel {
	/** Some Label's. */
	private JLabel heightLabel, widthLabel, depthLabel, moreLabel;
	/** Some TextField's. */
	private JTextField heightField, widthField, depthField, moreField;
	
	public BrainGeJPanel() {
		this.heightLabel	= new JLabel("Height : ");
		this.widthLabel		= new JLabel("Width : ");
		this.depthLabel		= new JLabel("Depth : ");
		this.moreLabel		= new JLabel("More : ");
		
		this.heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.widthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.depthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.moreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.heightField	= new JTextField("99"); // TODO JScroll 00-99, seletion to "99" ?
		this.widthField		= new JTextField("99"); // TODO JScroll 00-99, seletion to "99" ?
		this.depthField		= new JTextField("01"); // TODO JScroll 00-99, seletion to "01" ?
		this.moreField		= new JTextField("01"); // TODO JScroll 00-99, seletion to "01" ?
		
		this.addBagComponent(this.heightLabel, 0, 7);
		this.addBagComponent(this.heightField, 1, 7);
		this.addBagComponent(this.widthLabel, 2, 7);
		this.addBagComponent(this.widthField, 3, 7);
		
		// ***** Not used parameters (but show and disabled). 
		this.addBagComponent(this.depthLabel, 0, 8);
		this.addBagComponent(this.depthField, 1, 8);
		this.addBagComponent(this.moreLabel, 2, 8);
		this.addBagComponent(this.moreField, 3, 8);
		this.depthLabel.setEnabled(false);this.moreLabel.setEnabled(false);
		this.depthField.setEnabled(false);this.moreField.setEnabled(false);
		this.depthField.setEditable(false);this.moreField.setEditable(false);
		
		this.setAgeMax("000");
	}
	
	public int getBrainHeight()	{ return Integer.parseInt(this.heightField.getText()); }
	public int getBrainWidth()	{ return Integer.parseInt(this.widthField.getText()); }
	public int getBrainDepth()	{ return Integer.parseInt(this.depthField.getText()); }
	public int getBrainMore()	{ return Integer.parseInt(this.moreField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.setAgeMax("000");
		this.heightField.setText("00");
		this.widthField.setText("00");
		this.depthField.setText("01");
		this.moreField.setText("01");
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (BrainGene)
	 */
	public void setPanelSpecificValueWith(BrainGene gene) {
		super.setPanelValueWith(gene);
		this.heightField.setText(GeneJPanel.convertTwoChars(gene.getBrainHeight()));
		this.widthField.setText(GeneJPanel.convertTwoChars(gene.getBrainWidth()));
		this.depthField.setText(GeneJPanel.convertTwoChars(gene.getBrainDepth()));
		this.moreField.setText(GeneJPanel.convertTwoChars(gene.getBrainMore()));
	}
	
}

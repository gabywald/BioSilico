package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BrainGene;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to BrainGene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.BrainGene
 */
@SuppressWarnings("serial")
public class BrainGeJPanel extends GeneJPanel<BrainGene> {
	/** Some Label's. */
	private JLabel heightLabel, widthLabel, depthLabel, moreLabel;
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll heightField, widthField, depthField, moreField;
	
	public BrainGeJPanel() {
		super();
		this.heightLabel	= new JLabel("Height : ");
		this.widthLabel		= new JLabel("Width : ");
		this.depthLabel		= new JLabel("Depth : ");
		this.moreLabel		= new JLabel("More : ");
		
		this.heightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.widthLabel .setHorizontalAlignment(SwingConstants.RIGHT);
		this.depthLabel .setHorizontalAlignment(SwingConstants.RIGHT);
		this.moreLabel  .setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.heightField	= SpecificJScroll.getSpecificJScroll0to99();
		this.widthField		= SpecificJScroll.getSpecificJScroll0to99();
		this.depthField		= SpecificJScroll.getSpecificJScroll0to99();
		this.moreField		= SpecificJScroll.getSpecificJScroll0to99();
		
		this.addBagComponent(this.heightLabel, 0, 7);
		this.addBagComponent(this.heightField, 1, 7);
		this.addBagComponent(this.widthLabel , 2, 7);
		this.addBagComponent(this.widthField , 3, 7);
		
		// ***** Not used parameters (but show and disabled). 
		this.addBagComponent(this.depthLabel, 0, 8);
		this.addBagComponent(this.depthField, 1, 8);
		this.addBagComponent(this.moreLabel , 2, 8);
		this.addBagComponent(this.moreField , 3, 8);
		this.depthLabel.setEnabled(false);this.moreLabel.setEnabled(false);
		this.depthField.setEnabled(false);this.moreField.setEnabled(false);
		this.depthField.setEditable(false);this.moreField.setEditable(false);
		
		this.setDefaultValues();
	}
	
	public int getBrainHeight()	{ return this.heightField.getSelectedIndex(); }
	public int getBrainWidth()	{ return this.widthField.getSelectedIndex(); }
	public int getBrainDepth()	{ return this.depthField.getSelectedIndex(); }
	public int getBrainMore()	{ return this.moreField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.setAgeMax( 1 );
		this.heightField.setSelectedIndex( 99 );
		this.widthField .setSelectedIndex( 99 );
		this.depthField .setSelectedIndex(  1 );
		this.moreField  .setSelectedIndex(  1 );
		
		this.revalidate();
		this.repaint();
		
		super.revalidate();
		super.repaint();
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (BrainGene)
	 */
	@Override
	public void setPanelSpecificValueWith(BrainGene gene) {
		super.setPanelValueWith(gene);
		this.heightField.setSelectedIndex(gene.getBrainHeight());
		this.widthField .setSelectedIndex(gene.getBrainWidth());
		this.depthField .setSelectedIndex(gene.getBrainDepth());
		this.moreField  .setSelectedIndex(gene.getBrainMore());
	}
	
}

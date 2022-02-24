package gabywald.biosilico.view;

import gabywald.biosilico.genetics.EmitterReceptor;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to EmitterReceptor Gene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.EmitterReceptor
 */
@SuppressWarnings("serial")
public class EmitterJPanel extends GeneJPanel<EmitterReceptor> {
	/** Some Label's. */
	private JLabel variabLabel, threshLabel, ionputLabel, posxneLabel, posyneLabel;
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll variabField, threshField, ionputField, posxneField, posyneField;
	/** Some CheckBox's. */
	private JCheckBox receptBox, internBox;
	
	public EmitterJPanel() {
		this.variabLabel	= new JLabel("Chemical : ");
		this.threshLabel	= new JLabel("Threshold : ");
		this.ionputLabel	= new JLabel("In/Output : ");
		this.posxneLabel	= new JLabel("X Pos. : ");
		this.posyneLabel	= new JLabel("Y Pos. : ");
		
		this.variabLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.threshLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.ionputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posxneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posyneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.variabField	= SpecificJScroll.getSpecificJScroll0to999();
		this.threshField	= SpecificJScroll.getSpecificJScroll0to999();
		this.ionputField	= SpecificJScroll.getSpecificJScroll0to999();
		this.posxneField	= SpecificJScroll.getSpecificJScroll0to99();
		this.posyneField	= SpecificJScroll.getSpecificJScroll0to99();
		
		this.receptBox = new JCheckBox("Receptor",true);
		this.internBox = new JCheckBox("Internal",true);
		
		this.addBagComponent(this.variabLabel, 0, 7);
		this.addBagComponent(this.variabField, 1, 7);
		this.addBagComponent(this.threshLabel, 2, 7);
		this.addBagComponent(this.threshField, 3, 7);
		
		this.addBagComponent(this.posxneLabel, 0, 8);
		this.addBagComponent(this.posxneField, 1, 8);
		this.addBagComponent(this.posyneLabel, 2, 8);
		this.addBagComponent(this.posyneField, 3, 8);
		
		this.addBagComponent(this.ionputLabel, 0, 9);
		this.addBagComponent(this.ionputField, 1, 9);
		this.addBagComponent(this.receptBox, 2, 9);
		this.addBagComponent(this.internBox, 3, 9);
	}

	public boolean getReceptor() { return this.receptBox.isSelected(); }
	public boolean getInternal() { return this.internBox.isSelected(); }
	
	public int getVariable()	{ return this.variabField.getSelectedIndex(); }
	public int getThreshold()	{ return this.threshField.getSelectedIndex(); }
	public int getIOnput()		{ return this.ionputField.getSelectedIndex(); }
	public int getPosXNeurone()	{ return this.posxneField.getSelectedIndex(); }
	public int getPosYNeurone()	{ return this.posyneField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.variabField.setSelectedIndex( 000 );
		this.threshField.setSelectedIndex( 000 );
		this.ionputField.setSelectedIndex( 000 );
		this.posxneField.setSelectedIndex( 00 );
		this.posyneField.setSelectedIndex( 00 );
		this.receptBox.setSelected(true);
		this.internBox.setSelected(true);
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (EmitterReceptor)
	 */
	@Override
	public void setPanelSpecificValueWith(EmitterReceptor gene) {
		super.setPanelValueWith(gene);
		this.variabField.setSelectedIndex(gene.getVariable());
		this.threshField.setSelectedIndex(gene.getThreshold());
		this.ionputField.setSelectedIndex(gene.getIOnput());
		this.posxneField.setSelectedIndex(gene.getPosXNeurone());
		this.posyneField.setSelectedIndex(gene.getPosYNeurone());
		this.receptBox.setSelected(gene.getReceptor());
		this.internBox.setSelected(gene.getInternal());
	}
	
}

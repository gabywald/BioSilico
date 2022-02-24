package gabywald.biosilico.view;

import gabywald.biosilico.genetics.StimulusDecision;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to StimulusDecision Gene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.StimulusDecision
 */
@SuppressWarnings("serial")
public class StimuluJPanel extends GeneJPanel<StimulusDecision> {
	/** Some Label's. */
	private JLabel indicaLabel, threshLabel, attribLabel, variabLabel, valuesLabel, scriptLabel;
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll indicaField, threshField, attribField, variabField, valuesField, scriptField;
	/** Some CheckBox's. */
	private JCheckBox percepBox, objectBox;
	
	public StimuluJPanel() {
		this.indicaLabel	= new JLabel("Indicator : ");
		this.threshLabel	= new JLabel("Threshold : ");
		this.attribLabel	= new JLabel("Attribute : ");
		this.variabLabel	= new JLabel("Chemical : ");
		this.valuesLabel	= new JLabel("Value : ");
		this.scriptLabel	= new JLabel("Script : ");
		
		this.indicaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.threshLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.attribLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.variabLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.valuesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.scriptLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.indicaField	= SpecificJScroll.getSpecificJScroll0to999();
		this.threshField	= SpecificJScroll.getSpecificJScroll0to999();
		this.attribField	= SpecificJScroll.getSpecificJScroll0to999();
		this.variabField	= SpecificJScroll.getSpecificJScroll0to999();
		this.valuesField	= SpecificJScroll.getSpecificJScroll0to999();
		this.scriptField	= SpecificJScroll.getSpecificJScroll0to999();
		
		this.percepBox = new JCheckBox("Perceptor",true);
		this.objectBox = new JCheckBox("Object",true);
		
		this.addBagComponent(this.indicaLabel, 0, 7);
		this.addBagComponent(this.indicaField, 1, 7);
		this.addBagComponent(this.threshLabel, 2, 7);
		this.addBagComponent(this.threshField, 3, 7);
		
		this.addBagComponent(this.percepBox, 0, 8, 2);
		this.addBagComponent(this.objectBox, 2, 8, 2);

		this.addBagComponent(this.attribLabel, 0, 9);
		this.addBagComponent(this.attribField, 1, 9);
		this.addBagComponent(this.variabLabel, 2, 9);
		this.addBagComponent(this.variabField, 3, 9);
		
		this.addBagComponent(this.valuesLabel, 0, 10);
		this.addBagComponent(this.valuesField, 1, 10);
		this.addBagComponent(this.scriptLabel, 2, 10);
		this.addBagComponent(this.scriptField, 3, 10);
	}
	
	public boolean getPerception() 	{ return this.percepBox.isSelected(); }
	public boolean getObject() 		{ return this.objectBox.isSelected(); }
	
	public int getIndicator()	{ return this.indicaField.getSelectedIndex(); }
	public int getThreshold()	{ return this.threshField.getSelectedIndex(); }
	public int getAttribute()	{ return this.attribField.getSelectedIndex(); }
	public int getVariable()	{ return this.variabField.getSelectedIndex(); }
	public int getValue()		{ return this.valuesField.getSelectedIndex(); }
	public int getScript()		{ return this.scriptField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.indicaField.setSelectedIndex( 000 );
		this.threshField.setSelectedIndex( 000 );
		this.attribField.setSelectedIndex( 000 );
		this.variabField.setSelectedIndex( 000 );
		this.valuesField.setSelectedIndex( 000 );
		this.scriptField.setSelectedIndex( 000 );
		
		this.percepBox.setSelected(true);
		this.objectBox.setSelected(true);
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (StimulusDecision)
	 */
	@Override
	public void setPanelSpecificValueWith(StimulusDecision gene) {
		super.setPanelValueWith(gene);
		this.percepBox.setSelected(gene.getPerception());
		this.objectBox.setSelected(gene.getObject());
		this.indicaField.setSelectedIndex(gene.getIndicator());
		this.threshField.setSelectedIndex(gene.getThreshold());
		this.attribField.setSelectedIndex(gene.getAttribute());
		this.variabField.setSelectedIndex(gene.getVariable());
		this.valuesField.setSelectedIndex(gene.getValue());
		this.scriptField.setSelectedIndex(gene.getScript());
	}
	
}

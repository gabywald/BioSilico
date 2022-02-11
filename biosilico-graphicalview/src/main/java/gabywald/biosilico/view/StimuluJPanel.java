package gabywald.biosilico.view;

import gabywald.biosilico.genetics.StimulusDecision;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to StimulusDecision Gene. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see gabywald.biosilico.genetics.StimulusDecision
 */
@SuppressWarnings("serial")
public class StimuluJPanel extends GeneJPanel {
	/** Some Label's. */
	private JLabel indicaLabel, threshLabel, attribLabel, variabLabel, valuesLabel, scriptLabel;
	/** Some TextField's. */
	private JTextField indicaField, threshField, attribField, variabField, valuesField, scriptField;
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
		
		this.indicaField	= new JTextField("000");
		this.threshField	= new JTextField("000");
		this.attribField	= new JTextField("000");
		this.variabField	= new JTextField("000");
		this.valuesField	= new JTextField("000");
		this.scriptField	= new JTextField("000");
		
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
	
	public int getIndicator()	{ return Integer.parseInt(this.indicaField.getText()); }
	public int getThreshold()	{ return Integer.parseInt(this.threshField.getText()); }
	public int getAttribute()	{ return Integer.parseInt(this.attribField.getText()); }
	public int getVariable()	{ return Integer.parseInt(this.variabField.getText()); }
	public int getValue()		{ return Integer.parseInt(this.valuesField.getText()); }
	public int getScript()		{ return Integer.parseInt(this.scriptField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.indicaField.setText("000");
		this.threshField.setText("000");
		this.attribField.setText("000");
		this.variabField.setText("000");
		this.valuesField.setText("000");
		this.scriptField.setText("000");
		
		this.percepBox.setSelected(true);
		this.objectBox.setSelected(true);
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (StimulusDecision)
	 */
	public void setPanelSpecificValueWith(StimulusDecision gene) {
		super.setPanelValueWith(gene);
		this.percepBox.setSelected(gene.getPerception());
		this.objectBox.setSelected(gene.getObject());
		this.indicaField.setText(GeneJPanel.convertThreeChars(gene.getIndicator()));
		this.threshField.setText(GeneJPanel.convertThreeChars(gene.getThreshold()));
		this.attribField.setText(GeneJPanel.convertThreeChars(gene.getAttribute()));
		this.variabField.setText(GeneJPanel.convertThreeChars(gene.getVariable()));
		this.valuesField.setText(GeneJPanel.convertThreeChars(gene.getValue()));
		this.scriptField.setText(GeneJPanel.convertThreeChars(gene.getScript()));
	}
	
}

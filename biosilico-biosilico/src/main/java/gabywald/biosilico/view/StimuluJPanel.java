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
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 108L;
	/** Some Label's. */
	private JLabel indicaLabel,threshLabel,attribLabel,variabLabel,valuesLabel,scriptLabel;
	/** Some TextField's. */
	private JTextField indicaField,threshField,attribField,variabField,valuesField,scriptField;
	/** Some CheckBox's. */
	private JCheckBox percepBox,objectBox;
	
	public StimuluJPanel() {
		this.indicaLabel	= new JLabel("Indicator : ");
		this.threshLabel	= new JLabel("Threshold : ");
		this.attribLabel	= new JLabel("Attribute : ");
		this.variabLabel	= new JLabel("Chemical : ");
		this.valuesLabel	= new JLabel("Value : ");
		this.scriptLabel	= new JLabel("Script : ");
		
		this.indicaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.threshLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.attribLabel.setHorizontalAlignment(SwingConstants.RIGHT);		this.variabLabel.setHorizontalAlignment(SwingConstants.RIGHT);
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
	
	/**
			boolean perception	= ((StimulusDecision)gene).getPerception();
			boolean object		= ((StimulusDecision)gene).getObject();
			int indicator		= ((StimulusDecision)gene).getIndicator();
			int threshold		= ((StimulusDecision)gene).getThreshold();
			int attribute		= ((StimulusDecision)gene).getAttribute();
			int variaSD			= ((StimulusDecision)gene).getVariable();
			int valueSD			= ((StimulusDecision)gene).getValue();
			int scripSD			= ((StimulusDecision)gene).getScript();

			((StimuluJPanel)this.geneticParam[type]).setMutate(mutate);
			((StimuluJPanel)this.geneticParam[type]).setDuplic(duplic);
			((StimuluJPanel)this.geneticParam[type]).setMutate(delete);
			((StimuluJPanel)this.geneticParam[type]).setMutate(activi);
			((StimuluJPanel)this.geneticParam[type]).setAgeMin(minimalAge);
			((StimuluJPanel)this.geneticParam[type]).setAgeMax(maximalAge);
			((StimuluJPanel)this.geneticParam[type]).setSex(sex);
			((StimuluJPanel)this.geneticParam[type]).setMutRat(mutateRate);
			
			((StimuluJPanel)this.geneticParam[type]).setPerception(perception);
			((StimuluJPanel)this.geneticParam[type]).setObject(object);
			((StimuluJPanel)this.geneticParam[type]).setIndicator(indicator);
			((StimuluJPanel)this.geneticParam[type]).setThreshold(threshold);
			((StimuluJPanel)this.geneticParam[type]).setAttribute(attribute);
			((StimuluJPanel)this.geneticParam[type]).setVariable(variaSD);
			((StimuluJPanel)this.geneticParam[type]).setValue(valueSD);
			((StimuluJPanel)this.geneticParam[type]).setScript(scripSD);
	 */
}

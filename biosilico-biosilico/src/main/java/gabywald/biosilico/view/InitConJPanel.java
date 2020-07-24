package gabywald.biosilico.view;

import gabywald.biosilico.genetics.InitialConcentration;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to InitialConcentration Gene. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see gabywald.biosilico.genetics.InitialConcentration
 */
@SuppressWarnings("serial")
public class InitConJPanel extends GeneJPanel {
	/** Some Label's. */
	private JLabel varLabel,valLabel;
	/** Some TextField's. */
	private JTextField valField,varField;
	
	public InitConJPanel() {
		this.varLabel = new JLabel("Chemical n° : ");
		this.valLabel = new JLabel("Init. Value : ");
		this.varLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.valLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.valField = new JTextField("000");
		this.varField = new JTextField("000");
		
		this.addBagComponent(this.varLabel, 0, 7);
		this.addBagComponent(this.varField, 1, 7);
		this.addBagComponent(this.valLabel, 2, 7);
		this.addBagComponent(this.valField, 3, 7);
		
		this.setAgeMax("000");
	}
	
	public int getVariable()	{ return Integer.parseInt(this.varField.getText()); }
	public int getValue()		{ return Integer.parseInt(this.valField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.setAgeMax("000");
		this.valField.setText("000");
		this.varField.setText("000");
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (InitialConcentration)
	 */
	public void setPanelSpecificValueWith(InitialConcentration gene) {
		super.setPanelValueWith(gene);
		this.varField.setText(GeneJPanel.convertThreeChars(gene.getVariable()));
		this.valField.setText(GeneJPanel.convertThreeChars(gene.getValue()));
	}
	
}

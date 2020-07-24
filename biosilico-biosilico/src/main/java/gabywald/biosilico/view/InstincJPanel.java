package gabywald.biosilico.view;

import gabywald.biosilico.genetics.Instinct;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to Instinct Gene. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see gabywald.biosilico.genetics.Instinct
 */
@SuppressWarnings("serial")
public class InstincJPanel extends GeneJPanel {
	/** Some Label's. */
	private JLabel posxOrgLabel,posyOrgLabel,posxDesLabel,posyDesLabel,
					weightLabel,variablLabel,threshoLabel;
	/** Some TextField's. */
	private JTextField posxOrgField,posyOrgField,posxDesField,posyDesField,
						weightField,variablField,threshoField;
	/** A checkBox. */
	private JCheckBox checkBox;
	
	public InstincJPanel() {
		this.posxOrgLabel = new JLabel("Origin X Pos. : ");
		this.posyOrgLabel = new JLabel("Origin Y Pos. : ");
		this.posxDesLabel = new JLabel("Destin X Pos. : ");
		this.posyDesLabel = new JLabel("Destin Y Pos. : ");
		this.weightLabel  = new JLabel("Weight : ");
		this.variablLabel = new JLabel("Chemical : ");
		this.threshoLabel = new JLabel("Threshold : ");
		this.posxOrgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posyOrgLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posxDesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posyDesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.weightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.variablLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.threshoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posxOrgField = new JTextField("00");
		this.posyOrgField = new JTextField("00");
		this.posxDesField = new JTextField("00");
		this.posyDesField = new JTextField("00");
		this.weightField  = new JTextField("000");
		this.variablField = new JTextField("000");
		this.threshoField = new JTextField("000");
		this.checkBox = new JCheckBox("check (or add)",true);
		
		this.addBagComponent(this.posxOrgLabel, 0, 7);
		this.addBagComponent(this.posxOrgField, 1, 7);
		this.addBagComponent(this.posyOrgLabel, 2, 7);
		this.addBagComponent(this.posyOrgField, 3, 7);
		this.addBagComponent(this.posxDesLabel, 0, 8);
		this.addBagComponent(this.posxDesField, 1, 8);
		this.addBagComponent(this.posyDesLabel, 2, 8);
		this.addBagComponent(this.posyDesField, 3, 8);
		this.addBagComponent(this.weightLabel, 0, 9);
		this.addBagComponent(this.weightField, 1, 9);
		this.addBagComponent(this.checkBox, 2, 9,2);
		this.addBagComponent(this.variablLabel, 0, 10);
		this.addBagComponent(this.variablField, 1, 10);
		this.addBagComponent(this.threshoLabel, 2, 10);
		this.addBagComponent(this.threshoField, 3, 10);
		
		this.setAgeMax("000");
	}
	
	public boolean getCheck() { return this.checkBox.isSelected(); }
	
	public int getVariable()	{ return Integer.parseInt(this.variablField.getText()); }
	public int getThreshold()	{ return Integer.parseInt(this.threshoField.getText()); }
	public int getWeight()		{ return Integer.parseInt(this.weightField.getText()); }
	public int getPosXOrg()		{ return Integer.parseInt(this.posxOrgField.getText()); }
	public int getPosYOrg()		{ return Integer.parseInt(this.posyOrgField.getText()); }
	public int getPosXDes()		{ return Integer.parseInt(this.posxDesField.getText()); }
	public int getPosYDes()		{ return Integer.parseInt(this.posyDesField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.setAgeMax("000");
		this.posxOrgField.setText("00");
		this.posyOrgField.setText("00");
		this.posxDesField.setText("00");
		this.posyDesField.setText("00");
		this.weightField .setText("000");
		this.variablField.setText("000");
		this.threshoField.setText("000");
		this.checkBox.setSelected(true);
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (Instinct)
	 */
	public void setPanelSpecificValueWith(Instinct gene) {
		super.setPanelValueWith(gene);
		this.posxOrgField.setText(GeneJPanel.convertTwoChars(gene.getPosXOrg()));
		this.posyOrgField.setText(GeneJPanel.convertTwoChars(gene.getPosYOrg()));
		this.posxDesField.setText(GeneJPanel.convertTwoChars(gene.getPosXDes()));
		this.posyDesField.setText(GeneJPanel.convertTwoChars(gene.getPosYDes()));
		this.weightField.setText(GeneJPanel.convertThreeChars(gene.getWeight()));
		this.variablField.setText(GeneJPanel.convertThreeChars(gene.getVariable()));
		this.threshoField.setText(GeneJPanel.convertThreeChars(gene.getThreshold()));
		this.checkBox.setSelected(gene.getCheck());
	}
	
}

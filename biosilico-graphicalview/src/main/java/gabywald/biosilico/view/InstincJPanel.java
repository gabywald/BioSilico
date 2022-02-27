package gabywald.biosilico.view;

import gabywald.biosilico.genetics.Instinct;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to Instinct Gene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.Instinct
 */
@SuppressWarnings("serial")
public class InstincJPanel extends GeneJPanel<Instinct> {
	/** Some Label's. */
	private JLabel posxOrgLabel, posyOrgLabel, posxDesLabel, posyDesLabel, 
					weightLabel, variablLabel, threshoLabel;
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll posxOrgField, posyOrgField, posxDesField, posyDesField, 
							weightField, variablField, threshoField;
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
		this.posxOrgField = SpecificJScroll.getSpecificJScroll0to99();
		this.posyOrgField = SpecificJScroll.getSpecificJScroll0to99();
		this.posxDesField = SpecificJScroll.getSpecificJScroll0to99();
		this.posyDesField = SpecificJScroll.getSpecificJScroll0to99();
		this.weightField  = SpecificJScroll.getSpecificJScroll0to999();
		this.variablField = SpecificJScroll.getSpecificJScroll0to999();
		this.threshoField = SpecificJScroll.getSpecificJScroll0to999();
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
		
		this.setAgeMax( 0 );
	}
	
	public boolean getCheck() { return this.checkBox.isSelected(); }
	
	public int getVariable()	{ return this.variablField.getSelectedIndex(); }
	public int getThreshold()	{ return this.threshoField.getSelectedIndex(); }
	public int getWeight()		{ return this.weightField .getSelectedIndex(); }
	public int getPosXOrg()		{ return this.posxOrgField.getSelectedIndex(); }
	public int getPosYOrg()		{ return this.posyOrgField.getSelectedIndex(); }
	public int getPosXDes()		{ return this.posxDesField.getSelectedIndex(); }
	public int getPosYDes()		{ return this.posyDesField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.setAgeMax( 0 );
		this.posxOrgField.setSelectedIndex( 00 );
		this.posyOrgField.setSelectedIndex( 00 );
		this.posxDesField.setSelectedIndex( 00 );
		this.posyDesField.setSelectedIndex( 00 );
		this.weightField .setSelectedIndex( 000 );
		this.variablField.setSelectedIndex( 000 );
		this.threshoField.setSelectedIndex( 000 );
		this.checkBox.setSelected(true);
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (Instinct)
	 */
	@Override
	public void setPanelSpecificValueWith(Instinct gene) {
		super.setPanelValueWith(gene);
		this.posxOrgField.setSelectedIndex(gene.getPosXOrg());
		this.posyOrgField.setSelectedIndex(gene.getPosYOrg());
		this.posxDesField.setSelectedIndex(gene.getPosXDes());
		this.posyDesField.setSelectedIndex(gene.getPosYDes());
		this.weightField .setSelectedIndex(gene.getWeight());
		this.variablField.setSelectedIndex(gene.getVariable());
		this.threshoField.setSelectedIndex(gene.getThreshold());
		this.checkBox.setSelected(gene.getCheck());
	}

	@Override
	public Instinct getPanelSpecificValueWithGene() {
		return new Instinct(super.getMutate(), super.getDuplic(),
							super.getDelete(), super.getActivi(), 
							super.getAgeMin(), super.getAgeMax(), 
							super.getSex(), super.getMutRat(), 
							this.getPosXOrg(), this.getPosYOrg(), 
							this.getPosXDes(), this.getPosYDes(), 
							this.getWeight(), this.getVariable(), 
							this.getThreshold(), this.getCheck(), 
							true ); // TODO add visualization of Pos / Neg creation for instinct !
	}
	
}

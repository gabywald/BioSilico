package gabywald.biosilico.view;

import gabywald.biosilico.genetics.InitialConcentration;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to InitialConcentration Gene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.InitialConcentration
 */
@SuppressWarnings("serial")
public class InitConJPanel extends GeneJPanel<InitialConcentration> {
	/** Some Label's. */
	private JLabel varLabel, valLabel;
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll valField, varField;
	
	public InitConJPanel() {
		this.varLabel = new JLabel("Chemical n° : ");
		this.valLabel = new JLabel("Init. Value : ");
		this.varLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.valLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.valField = SpecificJScroll.getSpecificJScroll0to999();
		this.varField = SpecificJScroll.getSpecificJScroll0to999();
		
		this.addBagComponent(this.varLabel, 0, 7);
		this.addBagComponent(this.varField, 1, 7);
		this.addBagComponent(this.valLabel, 2, 7);
		this.addBagComponent(this.valField, 3, 7);
		
		this.setAgeMax( 0 );
	}
	
	public int getVariable()	{ return this.varField.getSelectedIndex(); }
	public int getValue()		{ return this.valField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.setAgeMax( 0 );
		this.valField.setSelectedIndex( 000 );
		this.varField.setSelectedIndex( 000 );
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (InitialConcentration)
	 */
	@Override
	public void setPanelSpecificValueWith(InitialConcentration gene) {
		super.setPanelValueWith(gene);
		this.varField.setSelectedIndex(gene.getVariable());
		this.valField.setSelectedIndex(gene.getValue());
	}

	@Override
	public InitialConcentration getPanelSpecificValueWithGene() {
		return new InitialConcentration(super.getMutate(), super.getDuplic(),
										super.getDelete(), super.getActivi(), 
										super.getAgeMin(), super.getAgeMax(), 
										super.getSex(), super.getMutRat(), 
										this.getVariable(), this.getValue());
	}
	
}

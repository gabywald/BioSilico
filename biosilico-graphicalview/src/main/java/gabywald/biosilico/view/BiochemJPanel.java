package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BiochemicalReaction;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to BiochemicalReaction Gene. 
 * @author Gabriel Chandesris (2010, 2020, 2022)
 * @see gabywald.biosilico.genetics.BiochemicalReaction
 */
@SuppressWarnings("serial")
public class BiochemJPanel extends GeneJPanel<BiochemicalReaction> {
	/** Some Label's. */
	private JLabel	AcoefLabel, BcoefLabel, CcoefLabel, DcoefLabel, 
					AchemLabel, BchemLabel, CchemLabel, DchemLabel, 
					KMLabel;
	/** Some TextField's / SpeceficJScroll's. */
	private SpecificJScroll	AcoefField, BcoefField, CcoefField, DcoefField, 
							AchemField, BchemField, CchemField, DchemField, 
							KMField;
	
	public BiochemJPanel() {
		this.AcoefLabel = new JLabel("Acoef : ");
		this.BcoefLabel = new JLabel("Bcoef : ");
		this.CcoefLabel = new JLabel("Ccoef : ");
		this.DcoefLabel = new JLabel("Dcoef : ");
		this.AchemLabel = new JLabel("* Achem : ");
		this.BchemLabel = new JLabel("* Bchem : ");
		this.CchemLabel = new JLabel("* Cchem : ");
		this.DchemLabel = new JLabel("* Dchem : ");
		this.KMLabel = new JLabel("KM : ");
		
		this.AcoefLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.BcoefLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.CcoefLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.DcoefLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.AchemLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.BchemLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.CchemLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.DchemLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.KMLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.AcoefField = SpecificJScroll.getSpecificJScroll0to999();
		this.BcoefField = SpecificJScroll.getSpecificJScroll0to999();
		this.CcoefField = SpecificJScroll.getSpecificJScroll0to999();
		this.DcoefField = SpecificJScroll.getSpecificJScroll0to999();
		this.AchemField = SpecificJScroll.getSpecificJScroll0to999();
		this.BchemField = SpecificJScroll.getSpecificJScroll0to999();
		this.CchemField = SpecificJScroll.getSpecificJScroll0to999();
		this.DchemField = SpecificJScroll.getSpecificJScroll0to999();
		this.KMField = SpecificJScroll.getSpecificJScroll0to999();
		
		this.addBagComponent(this.AcoefLabel, 0, 7);
		this.addBagComponent(this.AcoefField, 1, 7);
		this.addBagComponent(this.AchemLabel, 2, 7);
		this.addBagComponent(this.AchemField, 3, 7);
		
		this.addBagComponent(new JLabel(" + (reacts with) "), 0, 8, 5);
		
		this.addBagComponent(this.BcoefLabel, 0, 9);
		this.addBagComponent(this.BcoefField, 1, 9);
		this.addBagComponent(this.BchemLabel, 2, 9);
		this.addBagComponent(this.BchemField, 3, 9);
		
		this.addBagComponent(new JLabel(" => to give at speed "), 0, 10, 2);
		this.addBagComponent(this.KMLabel, 2, 10);
		this.addBagComponent(this.KMField, 3, 10);
		
		
		this.addBagComponent(this.CcoefLabel, 0, 11);
		this.addBagComponent(this.CcoefField, 1, 11);
		this.addBagComponent(this.CchemLabel, 2, 11);
		this.addBagComponent(this.CchemField, 3, 11);
		
		this.addBagComponent(new JLabel(" + (and)"), 0, 12, 5);
		
		this.addBagComponent(this.DcoefLabel, 0, 13);
		this.addBagComponent(this.DcoefField, 1, 13);
		this.addBagComponent(this.DchemLabel, 2, 13);
		this.addBagComponent(this.DchemField, 3, 13);
	}
	
	public int getAcoef() { return this.AcoefField.getSelectedIndex(); }
	public int getAchem() { return this.AchemField.getSelectedIndex(); }
	public int getBcoef() { return this.BcoefField.getSelectedIndex(); }
	public int getBchem() { return this.BchemField.getSelectedIndex(); }
	public int getCcoef() { return this.CcoefField.getSelectedIndex(); }
	public int getCchem() { return this.CchemField.getSelectedIndex(); }
	public int getDcoef() { return this.DcoefField.getSelectedIndex(); }
	public int getDchem() { return this.DchemField.getSelectedIndex(); }
	public int getKMVMs() { return this.KMField.getSelectedIndex(); }
	
	@Override
	public void setDefaultValues() {
		super.setDefaultValues();
		this.AcoefField.setSelectedIndex( 001 );
		this.BcoefField.setSelectedIndex( 001 );
		this.CcoefField.setSelectedIndex( 001 );
		this.DcoefField.setSelectedIndex( 001 );
		this.AchemField.setSelectedIndex( 000 );
		this.BchemField.setSelectedIndex( 000 );
		this.CchemField.setSelectedIndex( 000 );
		this.DchemField.setSelectedIndex( 000 );
		this.KMField.setSelectedIndex( 001 );
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (BiochemicalReaction)
	 */
	@Override
	public void setPanelSpecificValueWith(BiochemicalReaction gene) {
		super.setPanelValueWith(gene);
		this.AchemField.setSelectedIndex(gene.getAchem());
		this.BchemField.setSelectedIndex(gene.getBchem());
		this.CchemField.setSelectedIndex(gene.getCchem());
		this.DchemField.setSelectedIndex(gene.getDchem());
		this.AcoefField.setSelectedIndex(gene.getAcoef());
		this.BcoefField.setSelectedIndex(gene.getBcoef());
		this.CcoefField.setSelectedIndex(gene.getCcoef());
		this.DcoefField.setSelectedIndex(gene.getDcoef());
		this.KMField.setSelectedIndex(gene.getKMVMs());
	}
	
}

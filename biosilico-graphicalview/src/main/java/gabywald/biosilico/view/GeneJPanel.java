package gabywald.biosilico.view;

import gabywald.biosilico.genetics.Gene;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class defines a GridBagJPanel with fields and labels for user for Genes. 
 * @author Gabriel Chandesris (2009, 2020, 2022)
 */
@SuppressWarnings("serial")
public abstract class GeneJPanel<T extends Gene> extends GeneKitsGBJPanel {
	/** Some Title Label's. */
	private JLabel generalLabel, specifiLabel;
	/** Some CheckBoxe's. */
	private JCheckBox mutateBox, duplicBox, deleteBox, activiBox;
	/** Some Label's. */
	private JLabel ageMinLabel, ageMaxLabel, sexLabel, mutRatLabel;
	/** Some SpecificJScroll. */
	private SpecificJScroll ageMinTexte, ageMaxTexte, sexTexte, mutRatTexte;
	
	/** Default constructor. */
	public GeneJPanel() {
		/** Initialize instance items */
		Font writing = new Font("writing", Font.BOLD, 12);
		this.generalLabel = new JLabel("\nGeneral Parameters");
		this.specifiLabel = new JLabel("\nSpecific Parameters");
		this.generalLabel.setForeground(Color.MAGENTA);
		this.specifiLabel.setForeground(Color.MAGENTA);
		this.generalLabel.setFont(writing);
		this.specifiLabel.setFont(writing);
		
		this.mutateBox = new JCheckBox("mutate", true);
		this.duplicBox = new JCheckBox("duplic", true);
		this.deleteBox = new JCheckBox("delete", true);
		this.activiBox = new JCheckBox("actif", true);
		
		this.ageMinLabel	= new JLabel("Min. age : ");
		this.ageMaxLabel	= new JLabel("Max. age : ");
		this.sexLabel		= new JLabel("sex : ");
		this.mutRatLabel	= new JLabel("Mutation rate (%) : ");
		
		this.ageMinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.ageMaxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.sexLabel   .setHorizontalAlignment(SwingConstants.RIGHT);
		this.mutRatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.ageMinTexte	= SpecificJScroll.getSpecificJScroll0to999();
		this.ageMaxTexte	= SpecificJScroll.getSpecificJScroll0to999();
		this.sexTexte		= SpecificJScroll.getSpecificJScroll0to99();
		this.mutRatTexte	= SpecificJScroll.getSpecificJScroll0to99();
		
		/** Setting locations of items */
		this.addBagComponent(this.generalLabel, 0, 0, 4);
		this.addBagComponent(this.mutateBox, 0, 1);
		this.addBagComponent(this.duplicBox, 1, 1);
		this.addBagComponent(this.deleteBox, 2, 1);
		this.addBagComponent(this.activiBox, 3, 1);
		
		this.addBagComponent(this.ageMinLabel, 0, 2);
		this.addBagComponent(this.ageMinTexte, 1, 2);
		this.addBagComponent(this.ageMaxLabel, 2, 2);
		this.addBagComponent(this.ageMaxTexte, 3, 2);
		
		this.addBagComponent(this.sexLabel, 0, 3);
		this.addBagComponent(this.sexTexte, 1, 3);
		this.addBagComponent(this.mutRatLabel, 2, 3);
		this.addBagComponent(this.mutRatTexte, 3, 3);
		
		this.addBagComponent(new JPanel(), 0, 4, 4, 2); /** blank space */
		this.addBagComponent(this.specifiLabel, 0, 6, 4);
	}
	
	
	public boolean getMutate() { return this.mutateBox.isSelected(); }
	public boolean getDuplic() { return this.duplicBox.isSelected(); }
	public boolean getDelete() { return this.deleteBox.isSelected(); }
	public boolean getActivi() { return this.activiBox.isSelected(); }
	
	public int getAgeMin()	{ return Integer.parseInt(this.ageMinTexte.getSelectedValue()); }
	public int getAgeMax()	{ return Integer.parseInt(this.ageMaxTexte.getSelectedValue()); }
	public int getSex() 	{ return Integer.parseInt(this.sexTexte   .getSelectedValue()); }
	public int getMutRat()	{ return Integer.parseInt(this.mutRatTexte.getSelectedValue()); }
	
	protected void setAgeMax(int agemax)	{ this.ageMaxTexte.setSelectedIndex(agemax); }
	
	/**
	 * Set Some default selection value for the current instance of GeneJPanel. 
	 */
	public void setDefaultValues() {
		this.mutateBox.setSelected(true);
		this.duplicBox.setSelected(true);
		this.deleteBox.setSelected(true);
		this.activiBox.setSelected(true);
		
		this.ageMinTexte.setSelectedIndex( 000 );
		this.ageMaxTexte.setSelectedIndex( 999 );
		this.sexTexte   .setSelectedIndex( 000 );		
		this.mutRatTexte.setSelectedIndex( 25 );
		
		// TODO change / actualize view !!
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (Gene)
	 */
	public void setPanelValueWith(Gene gene) {
		this.mutateBox.setSelected(gene.canMutate());
		this.duplicBox.setSelected(gene.canDuplicate());
		this.deleteBox.setSelected(gene.canDelete());
		this.activiBox.setSelected(gene.isActiv());
		this.ageMinTexte.setSelectedIndex(gene.getAgeMin());
		this.ageMaxTexte.setSelectedIndex(gene.getAgeMax());
		this.sexTexte   .setSelectedIndex(gene.getSexAct());
		this.mutRatTexte.setSelectedIndex(gene.getMutationRate());
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (Gene)
	 */
	@SuppressWarnings("unchecked")
	public void setPanelSpecificValueWithGene(Gene gene) { 
		this.setPanelSpecificValueWith((T)gene);
	}

	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene Inheritant Class of Gene. 
	 */
	public abstract void setPanelSpecificValueWith(T gene);
	
	/**
	 * Get Generated Gene created from this (inheritant) Panel. 
	 * @return (Gene)
	 */
	public abstract T getPanelSpecificValueWithGene();

}

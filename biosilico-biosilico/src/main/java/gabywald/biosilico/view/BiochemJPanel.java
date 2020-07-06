package gabywald.biosilico.view;

import gabywald.biosilico.genetics.BiochemicalReaction;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to BiochemicalReaction Gene. 
 * @author Gabriel Chandesris (2010, 2020)
 * @see gabywald.biosilico.genetics.BiochemicalReaction
 */
@SuppressWarnings("serial")
public class BiochemJPanel extends GeneJPanel {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 104L;
	/** Some Label's. */
	private JLabel AcoefLabel,BcoefLabel,CcoefLabel,DcoefLabel,
					AchemLabel,BchemLabel,CchemLabel,DchemLabel,
					KMLabel;
	/** Some TextField's. */
	private JTextField AcoefField,BcoefField,CcoefField,DcoefField,
						AchemField,BchemField,CchemField,DchemField,
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
		
		this.AcoefField = new JTextField("001");
		this.BcoefField = new JTextField("001");
		this.CcoefField = new JTextField("001");
		this.DcoefField = new JTextField("001");
		this.AchemField = new JTextField("000");
		this.BchemField = new JTextField("000");
		this.CchemField = new JTextField("000");
		this.DchemField = new JTextField("000");
		this.KMField = new JTextField("001");
		
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
	
	public int getAcoef() { return Integer.parseInt(this.AcoefField.getText()); }
	public int getAchem() { return Integer.parseInt(this.AchemField.getText()); }
	public int getBcoef() { return Integer.parseInt(this.BcoefField.getText()); }
	public int getBchem() { return Integer.parseInt(this.BchemField.getText()); }
	public int getCcoef() { return Integer.parseInt(this.CcoefField.getText()); }
	public int getCchem() { return Integer.parseInt(this.CchemField.getText()); }
	public int getDcoef() { return Integer.parseInt(this.DcoefField.getText()); }
	public int getDchem() { return Integer.parseInt(this.DchemField.getText()); }
	public int getKMVMs() { return Integer.parseInt(this.KMField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.AcoefField.setText("001");
		this.BcoefField.setText("001");
		this.CcoefField.setText("001");
		this.DcoefField.setText("001");
		this.AchemField.setText("000");
		this.BchemField.setText("000");
		this.CchemField.setText("000");
		this.DchemField.setText("000");
		this.KMField.setText("001");
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (BiochemicalReaction)
	 */
	public void setPanelSpecificValueWith(BiochemicalReaction gene) {
		super.setPanelValueWith(gene);
		this.AchemField.setText(GeneJPanel.convertThreeChars(gene.getAchem()));
		this.BchemField.setText(GeneJPanel.convertThreeChars(gene.getBchem()));
		this.CchemField.setText(GeneJPanel.convertThreeChars(gene.getCchem()));
		this.DchemField.setText(GeneJPanel.convertThreeChars(gene.getDchem()));
		this.AcoefField.setText(GeneJPanel.convertThreeChars(gene.getAcoef()));
		this.BcoefField.setText(GeneJPanel.convertThreeChars(gene.getBcoef()));
		this.CcoefField.setText(GeneJPanel.convertThreeChars(gene.getCcoef()));
		this.DcoefField.setText(GeneJPanel.convertThreeChars(gene.getDcoef()));
		this.KMField.setText(GeneJPanel.convertThreeChars(gene.getKMVMs()));
	}
	
	/**
			int Achem = ((BiochemicalReaction)gene).getAchem();
			int Bchem = ((BiochemicalReaction)gene).getBchem();
			int Cchem = ((BiochemicalReaction)gene).getCchem();
			int Dchem = ((BiochemicalReaction)gene).getDchem();
			int Acoef = ((BiochemicalReaction)gene).getAcoef();
			int Bcoef = ((BiochemicalReaction)gene).getBcoef();
			int Ccoef = ((BiochemicalReaction)gene).getCcoef();
			int Dcoef = ((BiochemicalReaction)gene).getDcoef();
			int KMVM  = ((BiochemicalReaction)gene).getKMVMs();
			
			((BiochemJPanel)this.geneticParam[type]).setMutate(mutate);
			((BiochemJPanel)this.geneticParam[type]).setDuplic(duplic);
			((BiochemJPanel)this.geneticParam[type]).setMutate(delete);
			((BiochemJPanel)this.geneticParam[type]).setMutate(activi);
			((BiochemJPanel)this.geneticParam[type]).setAgeMin(minimalAge);
			((BiochemJPanel)this.geneticParam[type]).setAgeMax(maximalAge);
			((BiochemJPanel)this.geneticParam[type]).setSex(sex);
			((BiochemJPanel)this.geneticParam[type]).setMutRat(mutateRate);
			
			((BiochemJPanel)this.geneticParam[type]).setAchem(Achem);
			((BiochemJPanel)this.geneticParam[type]).setBchem(Bchem);
			((BiochemJPanel)this.geneticParam[type]).setCchem(Cchem);
			((BiochemJPanel)this.geneticParam[type]).setDchem(Dchem);
			((BiochemJPanel)this.geneticParam[type]).setAcoef(Acoef);
			((BiochemJPanel)this.geneticParam[type]).setBcoef(Bcoef);
			((BiochemJPanel)this.geneticParam[type]).setCcoef(Ccoef);
			((BiochemJPanel)this.geneticParam[type]).setDcoef(Dcoef);
			((BiochemJPanel)this.geneticParam[type]).setKMVMs(KMVM);
		*/

}

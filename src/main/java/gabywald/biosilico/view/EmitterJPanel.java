package gabywald.biosilico.view;

import gabywald.biosilico.genetics.EmitterReceptor;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * JPanel Card Interface of the Genetic Kit dedicaced to EmitterReceptor Gene. 
 * @author Gabriel Chandesris (2010)
 * @see gabywald.biosilico.genetics.EmitterReceptor
 */
public class EmitterJPanel extends GeneJPanel {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 107L;
	/** Some Label's. */
	private JLabel variabLabel,threshLabel,ionputLabel,posxneLabel,posyneLabel;
	/** Some TextField's. */
	private JTextField variabField,threshField,ionputField,posxneField,posyneField;
	/** Some CheckBox's. */
	private JCheckBox receptBox,internBox;
	
	public EmitterJPanel() {
		this.variabLabel	= new JLabel("Chemical : ");
		this.threshLabel	= new JLabel("Threshold : ");
		this.ionputLabel	= new JLabel("In/Output : ");
		this.posxneLabel	= new JLabel("X Pos. : ");
		this.posyneLabel	= new JLabel("Y Pos. : ");
		
		this.variabLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.threshLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.ionputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posxneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.posyneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		this.variabField	= new JTextField("000");
		this.threshField	= new JTextField("000");
		this.ionputField	= new JTextField("000");
		this.posxneField	= new JTextField("00");
		this.posyneField	= new JTextField("00");
		
		this.receptBox = new JCheckBox("Receptor",true);
		this.internBox = new JCheckBox("Internal",true);
		
		this.addBagComponent(this.variabLabel, 0, 7);
		this.addBagComponent(this.variabField, 1, 7);
		this.addBagComponent(this.threshLabel, 2, 7);
		this.addBagComponent(this.threshField, 3, 7);
		
		this.addBagComponent(this.posxneLabel, 0, 8);
		this.addBagComponent(this.posxneField, 1, 8);
		this.addBagComponent(this.posyneLabel, 2, 8);
		this.addBagComponent(this.posyneField, 3, 8);
		
		this.addBagComponent(this.ionputLabel, 0, 9);
		this.addBagComponent(this.ionputField, 1, 9);
		this.addBagComponent(this.receptBox, 2, 9);
		this.addBagComponent(this.internBox, 3, 9);
	}

	public boolean getReceptor() { return this.receptBox.isSelected(); }
	public boolean getInternal() { return this.internBox.isSelected(); }
	
	public int getVariable()	{ return Integer.parseInt(this.variabField.getText()); }
	public int getThreshold()	{ return Integer.parseInt(this.threshField.getText()); }
	public int getIOnput()		{ return Integer.parseInt(this.ionputField.getText()); }
	public int getPosXNeurone()	{ return Integer.parseInt(this.posxneField.getText()); }
	public int getPosYNeurone()	{ return Integer.parseInt(this.posyneField.getText()); }
	
	public void setDefaultValues() {
		super.setDefaultValues();
		this.variabField.setText("000");
		this.threshField.setText("000");
		this.ionputField.setText("000");
		this.posxneField.setText("00");
		this.posyneField.setText("00");
		this.receptBox.setSelected(true);
		this.internBox.setSelected(true);
	}
	
	/**
	 * To set-up attribute view values with specific Gene instance. 
	 * @param gene (EmitterReceptor)
	 */
	public void setPanelSpecificValueWith(EmitterReceptor gene) {
		super.setPanelValueWith(gene);
		this.variabField.setText(GeneJPanel.convertThreeChars(gene.getVariable()));
		this.threshField.setText(GeneJPanel.convertThreeChars(gene.getThreshold()));
		this.ionputField.setText(GeneJPanel.convertThreeChars(gene.getIOnput()));
		this.posxneField.setText(GeneJPanel.convertTwoChars(gene.getPosXNeurone()));
		this.posyneField.setText(GeneJPanel.convertTwoChars(gene.getPosYNeurone()));
		this.receptBox.setSelected(gene.getReceptor());
		this.internBox.setSelected(gene.getInternal());
	}
	
	/**
			int var				= ((EmitterReceptor)gene).getVariable();
			int thr				= ((EmitterReceptor)gene).getThreshold();
			int put				= ((EmitterReceptor)gene).getIOnput();
			int posxER			= ((EmitterReceptor)gene).getPosXNeurone();
			int posyER			= ((EmitterReceptor)gene).getPosYNeurone();
			boolean receptor	= ((EmitterReceptor)gene).getReceptor();
			boolean internal	= ((EmitterReceptor)gene).getInternal();

			((EmitterJPanel)this.geneticParam[type]).setMutate(mutate);
			((EmitterJPanel)this.geneticParam[type]).setDuplic(duplic);
			((EmitterJPanel)this.geneticParam[type]).setMutate(delete);
			((EmitterJPanel)this.geneticParam[type]).setMutate(activi);
			((EmitterJPanel)this.geneticParam[type]).setAgeMin(minimalAge);
			((EmitterJPanel)this.geneticParam[type]).setAgeMax(maximalAge);
			((EmitterJPanel)this.geneticParam[type]).setSex(sex);
			((EmitterJPanel)this.geneticParam[type]).setMutRat(mutateRate);
			
			((EmitterJPanel)this.geneticParam[type]).setVariable(var);
			((EmitterJPanel)this.geneticParam[type]).setThreshold(thr);
			((EmitterJPanel)this.geneticParam[type]).setIOnput(put);
			((EmitterJPanel)this.geneticParam[type]).setPosXNeurone(posxER);
			((EmitterJPanel)this.geneticParam[type]).setPosYNeurone(posyER);
			((EmitterJPanel)this.geneticParam[type]).setReceptor(receptor);
			((EmitterJPanel)this.geneticParam[type]).setInternal(internal);
	 */
	
}

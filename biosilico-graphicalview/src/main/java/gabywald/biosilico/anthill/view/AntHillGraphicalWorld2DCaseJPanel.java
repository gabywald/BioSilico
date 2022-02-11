package gabywald.biosilico.anthill.view;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gabywald.biosilico.view.GeneKitsGBJPanel;

/**
 * 
 * @author GAbriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalWorld2DCaseJPanel extends GeneKitsGBJPanel {
	/** Some CheckBoxe's. */
	private JCheckBox energySourceBox, blackHoleBox, condenstorBox;
	/** Some Label's. */
	private JLabel worldNameLabel, worldCaseNameLabel, worldCasePosLabel;
	/** Some TextField's. */
	private JTextField worldNameTexte, worldCaseNameTexte, worldCasePosX, worldCasePosY;
	
	
	public AntHillGraphicalWorld2DCaseJPanel() {
		this.setSize(100, 100);
		this.setPreferredSize(new Dimension(100, 100));
		
		this.energySourceBox	= new JCheckBox("Energy Source", true);
		this.blackHoleBox		= new JCheckBox("Black Hole", true);
		this.condenstorBox		= new JCheckBox("Condensator", false);
		
		this.worldNameLabel		= new JLabel("World : ");
		this.worldCaseNameLabel	= new JLabel("World Case : ");
		this.worldCasePosLabel	= new JLabel("Position : ");
		
		this.worldNameTexte		= new JTextField("---");
		this.worldCaseNameTexte	= new JTextField("---");
		this.worldCasePosX		= new JTextField("---");
		this.worldCasePosY		= new JTextField("---");
		
		this.addBagComponent(this.worldNameLabel,		 0, 0);
		this.addBagComponent(this.worldNameTexte,		 1, 0, 2);
		this.addBagComponent(this.worldCaseNameLabel,	 0, 1);
		this.addBagComponent(this.worldCaseNameTexte,	 1, 1, 2);
		this.addBagComponent(this.worldCasePosLabel,	 0, 2);
		this.addBagComponent(this.worldCasePosX,		 1, 2);
		this.addBagComponent(this.worldCasePosY,		 2, 2);
		
		this.addBagComponent(this.energySourceBox,		 0, 3);
		this.addBagComponent(this.blackHoleBox,			 1, 3);
		this.addBagComponent(this.condenstorBox,		 2, 3);
		
		this.setEnabled(false);
	}
	
	@Override
	public void setEnabled(boolean b) {
		this.energySourceBox.setEnabled( b );
		this.blackHoleBox.setEnabled( b );
		this.condenstorBox.setEnabled( b );
		
		this.worldNameLabel.setEnabled( b );
		this.worldCaseNameLabel.setEnabled( b );
		this.worldCasePosLabel.setEnabled( b );
		
		this.worldNameTexte.setEnabled( b );
		this.worldCaseNameTexte.setEnabled( b );
		this.worldCasePosX.setEnabled( b );
		this.worldCasePosY.setEnabled( b );
	}

	public void emptyInfos() {
		this.energySourceBox.setSelected( true );
		this.blackHoleBox.setSelected( true );
		this.condenstorBox.setSelected( false );
		
		this.worldNameTexte.setText( "" );
		this.worldCaseNameTexte.setText( "" );
		this.worldCasePosX.setText( "" );
		this.worldCasePosY.setText( "" );
	}
}

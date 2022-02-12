package gabywald.biosilico.anthill.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.Condensator;
import gabywald.biosilico.model.utils.agents.EnergySource;
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
	/** Some Button's. */
	private JButton applyButton;
	
	private World2DCase current = null;
	
	
	public AntHillGraphicalWorld2DCaseJPanel() {
		this.setSize(100, 150);
		this.setPreferredSize(new Dimension(100, 150));
		
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
		
		this.applyButton		= new JButton("Apply");
		
		this.addBagComponent(this.worldNameLabel,		 0, 0);
		this.addBagComponent(this.worldNameTexte,		 1, 0, 2);
		this.addBagComponent(this.worldCaseNameLabel,	 0, 1);
		this.addBagComponent(this.worldCaseNameTexte,	 1, 1, 2);
		this.addBagComponent(this.worldCasePosLabel,	 0, 2);
		this.addBagComponent(this.worldCasePosX,		 1, 2);
		this.addBagComponent(this.worldCasePosY,		 2, 2);
		
		this.addBagComponent(this.energySourceBox,		 0, 3, 3);
		this.addBagComponent(this.blackHoleBox,			 0, 4, 3);
		this.addBagComponent(this.condenstorBox,		 0, 5, 3);
		
		this.addBagComponent(this.applyButton,			 0, 6, 3);
		
		this.setEnabled(false);
		
		this.applyButton.addActionListener( this );
	}
	
	@Override
	public void setEnabled(boolean b) {
		this.worldNameLabel.setEnabled( b );
		this.worldCaseNameLabel.setEnabled( b );
		this.worldCasePosLabel.setEnabled( b );
		
		this.worldNameTexte.setEnabled( b );
		this.worldCaseNameTexte.setEnabled( b );
		this.worldCasePosX.setEnabled( b );
		this.worldCasePosY.setEnabled( b );
		
		this.energySourceBox.setEnabled( b );
		this.blackHoleBox.setEnabled( b );
		this.condenstorBox.setEnabled( b );
		
		this.applyButton.setEnabled( b );
	}

	public void emptyInfos() {
		this.current = null;
		
		this.worldNameTexte.setText( "" );
		this.worldCaseNameTexte.setText( "" );
		this.worldCasePosX.setText( "" );
		this.worldCasePosY.setText( "" );
		
		this.energySourceBox.setSelected( true );
		this.blackHoleBox.setSelected( true );
		this.condenstorBox.setSelected( false );
	}

	public void setCurrentWorldCase(World2DCase element) {
		this.current = element; 
		
		this.worldNameTexte.setText( element.getEnvironment().getName() );
		this.worldCaseNameTexte.setText(element.getName() );
		this.worldCasePosX.setText( element.getPosition().getPosX() + "" );
		this.worldCasePosY.setText( element.getPosition().getPosY() + "" );
		
		this.energySourceBox.setSelected( element.hasAgentWithName( EnergySource.COMMON_BIOSILICO_NAME ) > 0 );
		this.blackHoleBox.setSelected( element.hasAgentWithName( BlackHole.COMMON_BIOSILICO_NAME ) > 0 );
		this.condenstorBox.setSelected( element.hasAgentWithName( Condensator.COMMON_BIOSILICO_NAME ) > 0 );
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(this.applyButton)) {
			if (this.current != null) {
				/** Only change presence of any "*Box" element ! */
				/** Removal cases. */
				if ( (this.current.hasAgentWithName( EnergySource.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.energySourceBox.isSelected()) ) 
					{ this.current.remAgent( this.current.getAgentWithName( EnergySource.COMMON_BIOSILICO_NAME )); }
				
				if ( (this.current.hasAgentWithName( BlackHole.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.blackHoleBox.isSelected()) ) 
					{ this.current.remAgent( this.current.getAgentWithName( BlackHole.COMMON_BIOSILICO_NAME )); }
				
				if ( (this.current.hasAgentWithName( Condensator.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.condenstorBox.isSelected()) ) 
					{ this.current.remAgent( this.current.getAgentWithName( Condensator.COMMON_BIOSILICO_NAME )); }
				/** Adding cases. */
				if ( (this.current.hasAgentWithName( EnergySource.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.energySourceBox.isSelected()) ) 
					{ this.current.addAgent( new EnergySource() ); }
				
				if ( (this.current.hasAgentWithName( BlackHole.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.blackHoleBox.isSelected()) ) 
					{ this.current.addAgent( new BlackHole() ); }
				
				if ( (this.current.hasAgentWithName( Condensator.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.condenstorBox.isSelected()) ) 
					{ this.current.addAgent( new Condensator() ); }				
				/** Only change presence of any "*Box" element ! */
			}
		}
	}
}

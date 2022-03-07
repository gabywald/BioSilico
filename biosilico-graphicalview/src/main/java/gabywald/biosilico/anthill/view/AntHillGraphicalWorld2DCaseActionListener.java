package gabywald.biosilico.anthill.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gabywald.biosilico.model.utils.agents.BlackHole;
import gabywald.biosilico.model.utils.agents.Condensator;
import gabywald.biosilico.model.utils.agents.EnergySource;
import gabywald.biosilico.model.utils.agents.TestObjectFoodEgg;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalWorld2DCaseActionListener implements ActionListener {
	
	private AntHillGraphicalWorld2DCaseJPanel localPanel = null;
	
	public AntHillGraphicalWorld2DCaseActionListener(AntHillGraphicalWorld2DCaseJPanel panel) 
		{ this.localPanel = panel; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(this.localPanel.getApplyButton())) {
			if (this.localPanel.getCurrentWorldCase() != null) {
				/** Only change presence of any "*Box" element ! */
				
				// NOTE TODO reworking this part with lambda generalisation ? [readability][maintain]
				
				/** Removal cases. */
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( EnergySource.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.localPanel.getEnergySourceBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().remAgent( this.localPanel.getCurrentWorldCase().getAgentWithName( EnergySource.COMMON_BIOSILICO_NAME )); }
				
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( BlackHole.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.localPanel.getBlackHoleBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().remAgent( this.localPanel.getCurrentWorldCase().getAgentWithName( BlackHole.COMMON_BIOSILICO_NAME )); }
				
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( Condensator.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.localPanel.getCondensatorBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().remAgent( this.localPanel.getCurrentWorldCase().getAgentWithName( Condensator.COMMON_BIOSILICO_NAME )); }
				
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( TestObjectFoodEgg.COMMON_BIOSILICO_NAME ) > 0) 
						&& ( ! this.localPanel.getCondensatorBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().remAgent( this.localPanel.getCurrentWorldCase().getAgentWithName( TestObjectFoodEgg.COMMON_BIOSILICO_NAME )); }
				
				
				/** Adding cases. */
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( EnergySource.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.localPanel.getEnergySourceBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().addAgent( new EnergySource() ); }
				
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( BlackHole.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.localPanel.getBlackHoleBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().addAgent( new BlackHole() ); }
				
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( Condensator.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.localPanel.getCondensatorBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().addAgent( new Condensator() ); }
				
				if ( (this.localPanel.getCurrentWorldCase().hasAgentWithName( TestObjectFoodEgg.COMMON_BIOSILICO_NAME ) == 0) 
						&& (this.localPanel.getTestEggFoodBox().isSelected()) ) 
					{ this.localPanel.getCurrentWorldCase().addAgent( new TestObjectFoodEgg() ); }
				/** Only change presence of any "*Box" element ! */
			}
		}
	}

}

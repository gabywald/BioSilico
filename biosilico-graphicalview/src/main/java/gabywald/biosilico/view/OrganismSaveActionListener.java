package gabywald.biosilico.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener to Save / Test Organism. 
 * @author Gabriel Chandesris (2022)
 * @see OrganismSaveJPanel
 */
public class OrganismSaveActionListener implements ActionListener {
	
	private OrganismSaveJPanel localPanel = null;

	public OrganismSaveActionListener(OrganismSaveJPanel panel) 
		{ this.localPanel = panel; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localPanel.getSaveOrganism())) 
			{ this.localPanel.saveOrganism(); }
	}

}

package gabywald.biosilico.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Organism Selection Action Listener. 
 * @author Gabriel Chandesris (2022)
 * @see OrganismSelectJPanel
 */
public class OrganismSelectActionListener implements ActionListener {
	
	private OrganismSelectJPanel localPanel = null;

	public OrganismSelectActionListener(OrganismSelectJPanel panel) 
		{ this.localPanel = panel; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localPanel.getOpenAgentFile())) 
			{ this.localPanel.loadOrganism(); }
		else if (source.equals(this.localPanel.getCreateAgent())) {
			int type = this.localPanel.getAgentTypeSelected();
			if (type != 0) { this.localPanel.actionCreateAgent(type); }
		}
	}

}

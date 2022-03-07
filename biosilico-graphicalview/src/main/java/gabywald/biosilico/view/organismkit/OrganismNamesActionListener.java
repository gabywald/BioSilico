package gabywald.biosilico.view.organismkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener for Adding new Name in Organism Other Names. 
 * @author GAbriel Chandesris (2022)
 * @see OrganismNamesJPanel
 */
public class OrganismNamesActionListener implements ActionListener {
	
	private OrganismNamesJPanel localPanel = null;
	
	public OrganismNamesActionListener(OrganismNamesJPanel panel) 
		{ this.localPanel = panel; }

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source.equals(this.localPanel.getAddOtherName())) {
			if ( ! this.localPanel.getOtherName().getText().equals("")) {
				this.localPanel.getOthersNamesList().addString( this.localPanel.getOtherName().getText() );
				this.localPanel.getOtherName().setText("");
			}
		}
	}
	
}

package gabywald.biosilico.anthill.view;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gabywald.biosilico.model.Organism;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalJScrollOrganismListener implements ListSelectionListener {

	private AntHillGraphicalFrame localFrame = null;
	
	public AntHillGraphicalJScrollOrganismListener(AntHillGraphicalFrame frame) 
		{ this.localFrame = frame; }
	
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		int selectedIndex = this.localFrame.getOrganismJScroll().getSelectedIndex();
		if (selectedIndex >= 0) {
			Organism orga = this.localFrame.getOrganismJScroll().getElement(selectedIndex);
			
			System.out.println( orga.getBioSilicoName() + " (" + orga.getCommonName() + ")" );
			
			this.localFrame.setBrainPanelSelectionWith( orga );
			this.localFrame.setChemicalsPanelSelectionWith( orga );
			
			// TODO treat selection of organism to be viewed correctly (in tabbed panel ?!)
			
		} else { 
			// TODO If no selection
		}
	}
}

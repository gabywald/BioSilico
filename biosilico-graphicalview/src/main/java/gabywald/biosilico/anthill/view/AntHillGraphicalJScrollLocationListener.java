package gabywald.biosilico.anthill.view;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public class AntHillGraphicalJScrollLocationListener implements ListSelectionListener {

	private AntHillGraphicalFrame localFrame = null;
	
	public AntHillGraphicalJScrollLocationListener(AntHillGraphicalFrame frame) 
		{ this.localFrame = frame; }
	
	@Override
	public void valueChanged(ListSelectionEvent lse) {
		int selectedIndex = this.localFrame.getLocationJScroll().getSelectedIndex();
		if (selectedIndex >= 0) {
			this.localFrame.getWcInfosPanel().setEnabled( true );
			this.localFrame.getWcInfosPanel().setCurrentWorldCase( 
					this.localFrame.getLocationJScroll().getElement( selectedIndex ) );
		} else { 
			this.localFrame.getWcInfosPanel().setEnabled( false );
			this.localFrame.getWcInfosPanel().emptyInfos();
		}
	}
}

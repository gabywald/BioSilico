package gabywald.global.view;

import javax.swing.JPanel;

/**
 * To build and access easily a set of panels. 
 * <br>Easy-to-Use class to avoid some exceeding imports...
 * <br><i>DPsingleton</i><br><i>DP(Abstract)Factory</i>
 * @author Gabriel Chandesris (2011)
 * @see DataBasesTabbedPanel
 */
public abstract class PanelsFactory {
	public abstract JPanel getPanelWithName(String name);
}

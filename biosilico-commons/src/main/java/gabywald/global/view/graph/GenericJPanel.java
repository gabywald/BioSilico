package gabywald.global.view.graph;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * A generic easy-to-use JPanel, with BorderLayout. 
 * @author Gabriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public abstract class GenericJPanel extends JPanel implements IEnablerBorderLayoutPanels {
	
	/** Default Constructor. */
	protected GenericJPanel() {
		this.setLayout(new BorderLayout());
	}
	
	@Override
	public abstract void enableWesternPanel(boolean b);
	@Override
	public abstract void enableEasternPanel(boolean b);
	@Override
	public abstract void enableNorthernPanel(boolean b);
	@Override
	public abstract void enableSouthPanel(boolean b);
	@Override
	public abstract void enableCenterPanel(boolean b);
	
	/**
	 * Enable or disable parts of current frame. 
	 * <p></p>
	 * <ul>
	 * 		<li><b>1 : </b>West Panel</li>
	 * 		<li><b>2 : </b>East Panel</li>
	 * 		<li><b>3 : </b>North Panel</li>
	 * 		<li><b>4 : </b>South Panel</li>
	 * 		<li><b>5 : </b>Center Panel</li>
	 * </ul>
	 * @param b (boolean)
	 * @param location (int) [1-5]
	 */
	public void setEnable(boolean b, int location) {
		switch(location) {
		case(1): /** West */
			this.enableWesternPanel(b);break;
		case(2): /** East */
			this.enableEasternPanel(b);break;
		case(3): /** North */
			this.enableNorthernPanel(b);break;
		case(4): /** South */
			this.enableSouthPanel(b);break;
		case(5): /** Center */
			this.enableCenterPanel(b);break;
		}
	}
	
}

package gabywald.global.view.graph;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * A generic easy-to-use JFrame, with BorderLayout. 
 * Inheritants have to follow <i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2009 - 2010, 2020)
 */
@SuppressWarnings("serial")
public abstract class GenericJFrame extends JFrame {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 421L;
	/** Unique instance of this view. */
	// private static GenericJFrame instance = null;
	/** Default width of view. */
	public static final int WIDTH	= 1024;
	/** Default height of view. */
	public static final int HEIGHT	= 600;
	
	/** Default Constructor. */
	protected GenericJFrame() {
		this.setLocation(50, 50);
		this.setSize(GenericJFrame.WIDTH, GenericJFrame.HEIGHT); 
		this.getContentPane().setLayout(new BorderLayout());
		/** this.pack(); */
		this.setDefaultCloseOperation(GenericJFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (GenericJFrame)
	 */
// public static GenericJFrame getInstance();
//	{
//		if (GenericJFrame.instance == null) 
//			{ GenericJFrame.instance = new GenericJFrame(); }
//		return GenericJFrame.instance;
//	}
	
	// public abstract static GenericJFrame getInstance();

	public abstract void enableWesternPanel(boolean b);
	public abstract void enableEasternPanel(boolean b);
	public abstract void enableNorthernPanel(boolean b);
	public abstract void enableSouthPanel(boolean b);	
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

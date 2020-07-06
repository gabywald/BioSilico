package gabywald.global.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import gabywald.global.data.samples.TextualInfoFile;

/**
 * Generic abstract class to define generic JFrame. 
 * @author Gabriel Chandesris (2011, 2020)
 */
@SuppressWarnings("serial")
public abstract class GenericJFrame extends JFrame {
	/** Configuration for Generic Frame and inheritants... */
	public static final TextualInfoFile confGenericJFrame = 
		TextualInfoFile.getFrameGenericContext();
	/** Default Width of Frame. */
	public static final int FRAME_WIDTH = 
		Integer.parseInt(GenericJFrame.confGenericJFrame.getValueOf("width"));
	/** Default Height of Frame. */
	public static final int FRAME_HEIGH = 
		Integer.parseInt(GenericJFrame.confGenericJFrame.getValueOf("height"));
	/** Default x-position of Frame. */
	public static final int FRAME_LOCATION_X = 
		Integer.parseInt(GenericJFrame.confGenericJFrame.getValueOf("xPos"));
	/** Default y-position of Frame. */
	public static final int FRAME_LOCATION_Y = 
		Integer.parseInt(GenericJFrame.confGenericJFrame.getValueOf("yPos"));
	
	/**
	 * Basic constructor to use in inheritant class to add subPanels and set Frame visible. 
	 * @param title (String) name of the frame instance. 
	 */
	protected GenericJFrame(String title) {
		this.setTitle(GenericJFrame.confGenericJFrame.getValueOf("title")
						+" -- "+title);

		this.setLayout(new BorderLayout());
		
		boolean isResizeable = Boolean.parseBoolean
			(GenericJFrame.confGenericJFrame.getValueOf("isResizeable"));
		this.setResizable(isResizeable);
		this.setLocation(GenericJFrame.FRAME_LOCATION_X, GenericJFrame.FRAME_LOCATION_Y);
		this.setSize(GenericJFrame.FRAME_WIDTH, GenericJFrame.FRAME_HEIGH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/** this.setVisible(true); */
	}
}

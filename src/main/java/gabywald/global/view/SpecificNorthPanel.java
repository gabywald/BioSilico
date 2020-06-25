package gabywald.global.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gabywald.global.data.samples.TextualInfoFile;

/**
 * This class defines the North Panel of the Frame (title, help...). 
 * <br><i>DPSingleton</i>
 * @author Gabriel Chandesris (2011)
 * @see SpecificJFrame
 */
public class SpecificNorthPanel extends JPanel {
	private static SpecificNorthPanel instance;
	/** Default width of the Panel (same as Frame). */
	public static final int WIDTH = GenericJFrame.FRAME_WIDTH;
	/** Default height of the Panel (27). */
	public static final int HEIGH = 27;
	/** Default Dimension of the Panel (width*height). */
	public static final Dimension DIMENSION = new Dimension(SpecificNorthPanel.WIDTH,SpecificNorthPanel.HEIGH);
	/** Contain main title of the Frame. */
	private JLabel title;
	
	private SpecificNorthPanel() {
		this.setLayout(new BorderLayout());
		this.title = new JLabel(TextualInfoFile.getGeneralContext().getValueOf("company"));
		this.title.setFont(new Font("Serif",Font.BOLD,24));
		this.title.setHorizontalTextPosition(SwingConstants.CENTER);
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(this.title,"Center");
		// this.add(new HelpButton("Main Help Message"),"East");
		// this.setBackground(GenericTab.BACK);
		this.setPreferredSize(SpecificNorthPanel.DIMENSION);
	}
	
	public static SpecificNorthPanel getInstance() {
		if (SpecificNorthPanel.instance == null) 
			{ SpecificNorthPanel.instance = new SpecificNorthPanel(); }
		return SpecificNorthPanel.instance;
	}
	
	public void setMainTitle(String mainTitle) { this.title.setText(mainTitle); }
	
}
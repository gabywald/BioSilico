package gabywald.global.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gabywald.global.data.TextualInfoFile;

/**
 * South (bottom) part of the main Frame / Window. 
 * <br><i>DPSingleton</i>
 * <!-- <br><b><i>Uses CardLayout</i></b> -->
 * @author Gabriel Chandesris (2011)
 * @see SpecificJFFrame
 */
@SuppressWarnings("serial")
public class SpecificSouthPanel extends JPanel {
	private static SpecificSouthPanel instance;
	/** Half the width remained without the compass width). */
	public static final int WIDTH_LESS_HALF = (GenericJFrame.FRAME_WIDTH)/2 - 4;
	/** Default with of the Panel (same as Frame). */
	public static final int WIDTH = GenericJFrame.FRAME_WIDTH;
	/** Default height of the Panel (75px). */
	public static final int HEIGH = 50;
	/** Default Dimension of the Panel (width*height). */
	public static final Dimension DIMENSION = new Dimension(SpecificSouthPanel.WIDTH,SpecificSouthPanel.HEIGH);

	/** Bottom-South-East : JLabel for the copyright message. */
	private JLabel copyright;
	/** Bottom-South-West : Informative message (Help...). */
	private JLabel currentMsg;
	
	protected SpecificSouthPanel(boolean hasCompass)	{ this.init(hasCompass); }
	protected SpecificSouthPanel()						{ this.init(true); }
	
	private void init(boolean hasCompass) {
		this.setLayout(new BorderLayout());
		this.copyright		= new JLabel(TextualInfoFile.getGeneralContext().getValueOf("copyright"));
		this.copyright.setForeground(Color.RED);
		this.currentMsg		= new JLabel("");
		this.currentMsg.setBounds(0, 0, 200, 100);
		
		/** JPanel with Guidelines !! */ /** 
		GuidelinePanel msrMore = GuidelinePanel.getInstance();
		msrMore.setPreferredSize(new Dimension(SpecificSouthPanel.WIDTH_LESS_HALF, DSCatiaCompass.HEIGH));
		msrMore.selectCurrentActive(0); */
		
		JPanel center		= new JPanel(new BorderLayout());

		if (hasCompass) {
			JPanel completion01 = new JPanel();
			completion01.setPreferredSize(new Dimension(SpecificSouthPanel.WIDTH_LESS_HALF, 75));
			JPanel completion02 = new JPanel();
			completion02.setPreferredSize(new Dimension(SpecificSouthPanel.WIDTH_LESS_HALF, 75));
			center.add(completion01,					"West");
			center.add(completion02 /** msrMore*/,		"East");
		}
		center.add(new JPanel(),	"South");	/** The ToolBar !!*/
		center.setPreferredSize(new Dimension(GenericJFrame.FRAME_WIDTH, 
							   (hasCompass)?75:75 + 20));
		
		JPanel south		= new JPanel(new BorderLayout());
		south.setPreferredSize(new Dimension(SpecificSouthPanel.WIDTH,14));
		south.add(this.copyright,	"East");
		south.add(this.currentMsg,	"West");
		
		this.add(center,	"Center");
		this.add(south,		"South");
		this.setBackground(Color.WHITE);
		// this.setPreferredSize(SouthPanel.DIMENSION);
	}
	
	public static SpecificSouthPanel getInstance(boolean hasCompass) {
		if (SpecificSouthPanel.instance == null) 
			{ SpecificSouthPanel.instance = new SpecificSouthPanel(hasCompass); }
		return SpecificSouthPanel.instance;
	}
	
	
	public static SpecificSouthPanel getInstance() {
		if (SpecificSouthPanel.instance == null) 
			{ SpecificSouthPanel.instance = new SpecificSouthPanel(); }
		return SpecificSouthPanel.instance;
	}
	
	/**
	 * To change the public message. 
	 * @param text (String) Message to put in the view. 
	 */
	public void setCurrentMessage(String text) 
		{ this.currentMsg.setText(text); }
	
	/** 
	public int getSelectedGuideline() { return GuidelinePanel.getInstance().getCurrentActive(); }
	public void selectGuideline(int i) 
		{ GuidelinePanel.getInstance().selectCurrentActive(i); }
	*/
	
}

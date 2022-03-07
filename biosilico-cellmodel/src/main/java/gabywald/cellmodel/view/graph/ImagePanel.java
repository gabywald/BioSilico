package gabywald.cellmodel.view.graph;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gabywald.utilities.others.PropertiesLoader;

/**
 * A Panel where an image is put. 
 * <br>Design-Pattern <i>Singleton</i> 
 * @author Gabriel Chandesris (2009)
 */
public class ImagePanel extends JPanel {
	/** To Avoid Warning */
	private static final long serialVersionUID = 1L;
	/** Where image is loaded. */
	private BufferedImage fond;
	/** Unique instance of this panel. */
	private static ImagePanel instance = null;

	/** Default constructor. */
	private ImagePanel() {
		// try { this.fond = ImageIO.read(new File("bin/gabywald/cellmodel/view/graph/interfacejava.png")); }
		try { this.fond = ImageIO.read(PropertiesLoader.openResource("interfaceJavaCellModel.png")); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.fond, 0, 0,503,412, this);
	}
	
	/**
	 * To get instance of the image Panel. 
	 * @return (ImagePanel)
	 */
	public static ImagePanel getImagePanel() { 
		if (ImagePanel.instance == null) 
			{ ImagePanel.instance = new ImagePanel(); }
		return ImagePanel.instance;
	}
}

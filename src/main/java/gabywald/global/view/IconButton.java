package gabywald.global.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * This class defines Iconic Buttons, seized to their icon size. 
 * @author Gabriel Chandesris (2011)
 */
public class IconButton extends JButton {
	/**
	 * Constructor with given icon path. 
	 * @param iconPath (String)
	 */
	public IconButton(String iconPath) {
		this.setIcon(new ImageIcon(iconPath));
		this.init();
	}
	
	/**
	 * Constructor with given ImageIcon. 
	 * @param icon
	 */
	public IconButton(ImageIcon icon) 
		{ this.setIcon(icon);this.init(); }
	
	/** Constructors' helper. */
	private void init() {
		this.setSize(this.getIcon().getIconWidth(), 
				 this.getIcon().getIconHeight());
		this.setPreferredSize(
				new Dimension(this.getIcon().getIconWidth(), 
							  this.getIcon().getIconHeight()));
	}
}

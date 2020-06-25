package gabywald.global.view;

import gabywald.global.controller.HelpButtonListener;

/**
 * This class to define Help Buttons with help content (tool tip text). 
 * @author Gabriel Chandesris (2011)
 */
public class HelpButton extends IconButton {
	/**
	 * Constructor.
	 * @param content (String) help to show. 
	 */
	public HelpButton(String content) {
		super(AvailableImages.getHelpImage());
		this.setToolTipText(content);
		this.addMouseListener(new HelpButtonListener(this));
		this.setBorder(null);
		// this.setBackground(GenericTab.BACK);
	}
}

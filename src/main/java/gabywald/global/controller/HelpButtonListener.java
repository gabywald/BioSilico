package gabywald.global.controller;

import gabywald.global.view.SpecificSouthPanel;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonListener;

/** 
 * Basic Button Listener for Help Buttons : showing help data into the interface (SouthPanel). 
 * @author Gabriel Chandesris (2011)
 * gabywald.global.view.SpecificSouthPanel
 */
public class HelpButtonListener extends BasicButtonListener {
	/** Help Button instance. */
	private JButton button;

	/** 
	 * Constructor. 
	 * @param butt (HelpButton)
	 */
	public HelpButtonListener(JButton butt) 
		{ super(butt);this.button = butt; }
	
	@Override
	public void mouseClicked(MouseEvent e)	{ ; }
	@Override
	public void mouseEntered(MouseEvent e)	
		{ SpecificSouthPanel.getInstance().setCurrentMessage
							(this.button.getToolTipText()); }
	@Override
	public void mouseExited(MouseEvent e)	
		{ SpecificSouthPanel.getInstance().setCurrentMessage(""); }
	@Override
	public void mousePressed(MouseEvent e)	{ ; }
	@Override
	public void mouseReleased(MouseEvent e)	{ ; }

}

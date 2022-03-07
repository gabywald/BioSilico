package gabywald.global.view.graph;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * Aim of this class is to define a JPanel with a default GridBagLayout. 
 * @author Gabriel Chandesris (2009-2010, 2020, 2022)
 * @see <a href="http://java.sun.com/docs/books/tutorial/uiswing/layout/gridbag.html">How to Use GridBagLayout</a>
 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/awt/GridBagLayout.html">Class GridBagLayout (Java 1.4.2)</a>
 * @see <a href="http://louis.cova.neuf.fr/blocs-notes/page11.html">Maitriser le GridBagLayout</a>
 */
@SuppressWarnings("serial")
public abstract class GridBagJPanel extends JPanel {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 422L;
	
	/** GenericJFrame where JPanel is included in. */
	private IEnablerBorderLayoutPanels mainEnabler;
	
	/** Default Constructor. */
	public GridBagJPanel() { 
		super(new GridBagLayout());
		this.setBackground( Color.LIGHT_GRAY );
		this.setSize(	new Dimension(	GenericJFrame.WIDTH, 
										GenericJFrame.HEIGHT));
	}
	
	public void addBagComponent(Component comp, int gridx, int gridy) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = gridx;c.gridy = gridy;
		this.add(comp,c);
	}
	
	public void addBagComponent(Component comp, int gridx, int gridy, int gridwidth) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = gridx;c.gridy = gridy;c.gridwidth = gridwidth;
		this.add(comp,c);
	}
	
	public void addBagComponent(Component comp, int gridx, int gridy, int gridwidth, int gridHeight) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = gridx;c.gridy = gridy;
		c.gridwidth = gridwidth;c.gridheight = gridHeight;
		this.add(comp,c);
	}
	
	public IEnablerBorderLayoutPanels getMainEnabler() 
		{ return this.mainEnabler; }

	public void setMainEnabler(IEnablerBorderLayoutPanels main) 
		{ this.mainEnabler = main; }
}

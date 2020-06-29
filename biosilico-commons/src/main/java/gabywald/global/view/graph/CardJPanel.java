package gabywald.global.view.graph;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

/**
 * This kind of JPanel provides a Card Layout JPanel. 
 *  @author Gabriel Chandesris (2010, 2020)
 */
@SuppressWarnings("serial")
public abstract class CardJPanel extends JPanel {
	/** To avoid a Warning. */
	// private static final long serialVersionUID = 424L;
	/** The list of String's. */
	private String stringParam[];
	/** List of Card JPanel's. */
	private JPanel cardParam[];
	
	/**
	 * Main constructor for this kind of JPanel. 
	 * Initialize the Cards JPanel for parameters. 
	 * @see <a href="http://java.sun.com/docs/books/tutorial/uiswing/examples/layout/CardLayoutDemoProject/src/layout/CardLayoutDemo.java">Card Layout Demo</a>
	 * @param strings (String[]) names of cards
	 * @param cards (JPanel[])
	 */
	public CardJPanel(String[] strings,JPanel[] cards) {
		this.stringParam	= strings;
		this.cardParam		= cards;
		/** The whole CardLayout */
		this.setLayout(new CardLayout());
		this.setBackground(Color.LIGHT_GRAY);
		for (int i = 0 ; i < this.stringParam.length ; i++) 
			{ this.add(this.cardParam[i], this.stringParam[i]); }
		this.selectCard(0);
	}
	
	/**
	 * To select and Card JPanel at position i. 
	 * @param i (int)
	 */
	public void selectCard(int i) {
		if ( (i >= 0) && (i < this.stringParam.length) ) {
			CardLayout cl = (CardLayout)(this.getLayout());
		    cl.show(this, this.stringParam[i]);
		}
	}
	
	/**
	 * To get the JPanelat position i. 
	 * @param i (int)
	 * @return (JPanel)
	 */
	public JPanel getCard(int i) {
		if ( (i >= 0) && (i < this.cardParam.length) )
			{ return this.cardParam[i]; }
		else { return null; }
	}
}

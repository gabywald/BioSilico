package gabywald.biosilico.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gabywald.global.structures.StringListe;
import gabywald.global.view.graph.GenericJScroll;

/**
 * This class defines a Gene List JScroll which can reacts. 
 * <br>Removing button : remove current selected element. 
 * <br>Up / Down buttons : moving current selected element. 
 * @author Gabriel Chandesris (2010)
 */
public class GeneListJScroll extends GenericJScroll
							 implements ActionListener {
	/** Associated removing button. */
	private JButton removeGene;
	/** To move up or down a Gene in the list. */
	private JButton uppeGene,downGene,addChromosomeDelim;
	
	/** Default Constructor. */
	public GeneListJScroll() { this.init(); }
	
	/**
	 * Constructor with a list of Gene's. 
	 * @param stringListe (StringListe)
	 */
	public GeneListJScroll(StringListe stringListe) { 
		super(stringListe);
		this.init();
	}
	
	/** Helpêr for constructors (initialization). */
	private void init() {
		this.setSize(20, 400);
		this.setPreferredSize(new Dimension(30,400));
		this.removeGene	= new JButton("Rem. Gene");
		this.uppeGene	= new JButton("Up Gene");
		this.downGene	= new JButton("Down Gene");
		this.addChromosomeDelim = new JButton("Add Chromosome");
		this.removeGene.addActionListener(this);
		this.uppeGene.addActionListener(this);
		this.downGene.addActionListener(this);
		this.addChromosomeDelim.addActionListener(this);
		this.removeGene.setEnabled(false);
	}
	
	public JButton getRemoveButton()	{ return this.removeGene; }
	public JButton getUppeButton() 		{ return this.uppeGene; }
	public JButton getDownButton()		{ return this.downGene; }
	public JButton getAddChromDelim()	{ return this.addChromosomeDelim; }

	public void addString(String elt) {
		super.addString(elt);
		this.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source.equals(this.removeGene)) {
			/** Removing a Gene from current Pathway. */
			this.removeCurrentSelection();
			if (this.length() < 1) { this.setEnabled(false); }
		} 
		else if (source.equals(this.uppeGene)) 
			{ this.moveCurrentSelection(true); } 
		else if (source.equals(this.downGene)) 
			{ this.moveCurrentSelection(false); }
		else if (source.equals(this.addChromosomeDelim)) { 
			String delim = StringListe.repeat("-", 50);
			this.addString(delim);
		}
	}
	
	public void setEnabled(boolean b) {
		this.removeGene.setEnabled(b);
		this.uppeGene.setEnabled(b);
		this.downGene.setEnabled(b);
		this.addChromosomeDelim.setEnabled(b);
	}
}

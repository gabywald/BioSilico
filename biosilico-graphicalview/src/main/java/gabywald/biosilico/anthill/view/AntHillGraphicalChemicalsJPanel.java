package gabywald.biosilico.anthill.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gabywald.biosilico.interfaces.IChemicals;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.chemicals.ChemicalsHelper;
import gabywald.biosilico.model.enums.StateType;
import gabywald.global.view.graph.GridBagJPanel;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalChemicalsJPanel extends GridBagJPanel implements Observer {

	private Organism localOrga = null;

	private JPanel wholeChemicalView				= new JPanel();
	/** Chemicals 331 to 336 */
	private ChemicalPanel partialChemicalViewCreatureBase = null;
	/** Chemicals 351 to 360 */
	private ChemicalPanel partialChemicalViewPheromones = null;
	/** Chemicals 371 to 380 */
	private ChemicalPanel partialChemicalViewHormones = null;
	/** Chemicals 391 to 396 */
	private ChemicalPanel partialChemicalViewEnergies = null;

	public AntHillGraphicalChemicalsJPanel(Organism orga) {
		this.localOrga		= orga;
		if ( (this.localOrga == null) || (this.localOrga.getChemicals() == null) ) {
			// ***** Organism has NO chemicals !! (or no organism selected at all !)
			this.setLayout(new BorderLayout());
			this.add(new JLabel("No Data To Show !"), BorderLayout.CENTER);
			this.add(new JLabel((this.localOrga == null)?"No Organism !":"No Chemicals !!"), BorderLayout.NORTH);
		} else {
			// IChemicals localChemicals = this.localOrga.getChemicals();
			// ***** Organism HAS Chemicals !!

			this.wholeChemicalView = new ChemicalPanel("Global Chemical", 	0, ChemicalsHelper.CHEMICAL_LENGTH , this.localOrga);

			this.addBagComponent(this.wholeChemicalView, 0, 0, 1, 9);

			this.partialChemicalViewCreatureBase	= new ChemicalPanel("C-Chemical Base  : ", 	330, 341, this.localOrga);
			this.partialChemicalViewPheromones		= new ChemicalPanel("Pheromones : ", 		350, 361, this.localOrga);
			this.partialChemicalViewHormones		= new ChemicalPanel("Hormones : ", 			370, 381, this.localOrga);
			this.partialChemicalViewEnergies		= new ChemicalPanel("Energies : ", 			390, 396, this.localOrga);

			this.addBagComponent(new JLabel(this.partialChemicalViewCreatureBase.getName()), 	1, 0);
			this.addBagComponent(this.partialChemicalViewCreatureBase, 							1, 1);
			this.addBagComponent(new JLabel(this.partialChemicalViewPheromones.getName()), 		1, 2);
			this.addBagComponent(this.partialChemicalViewPheromones, 							1, 3);
			this.addBagComponent(new JLabel(this.partialChemicalViewHormones.getName()), 		1, 4);
			this.addBagComponent(this.partialChemicalViewHormones, 								1, 5);
			this.addBagComponent(new JLabel(this.partialChemicalViewEnergies.getName()), 		1, 6);
			this.addBagComponent(this.partialChemicalViewEnergies, 								1, 7);

			this.localOrga.addObserver( this );
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		IChemicals localChemicals = this.localOrga.getChemicals();

		for (int i = 0 ; i < ChemicalsHelper.CHEMICAL_LENGTH ; i++) {
			AntHillGraphicalChemicalsJPanel.status(i, (ChemicalButton) this.wholeChemicalView.getComponent(i), localChemicals);
			// NOTE here : readability more than optimization (could be factorized for ToolTipText and BG Color for same index !!)
			AntHillGraphicalChemicalsJPanel.partialUpdate(i, 330, 341, this.partialChemicalViewCreatureBase, localChemicals);
			AntHillGraphicalChemicalsJPanel.partialUpdate(i, 350, 361, this.partialChemicalViewPheromones, localChemicals);
			AntHillGraphicalChemicalsJPanel.partialUpdate(i, 370, 381, this.partialChemicalViewHormones, localChemicals);
			AntHillGraphicalChemicalsJPanel.partialUpdate(i, 390, 396, this.partialChemicalViewEnergies, localChemicals);
		}
	}

	/**
	 * Update for a given ChemicalPanel. 
	 * @param index (int)
	 * @param min (int)
	 * @param max (int)
	 * @param panel (ChemicalPanel)
	 * @param localChemicals (IChemicals)
	 */
	private static void partialUpdate(int index, int min, int max, ChemicalPanel panel, IChemicals localChemicals) {
		if ( (index >= min) && (index < max) ) 
			{ AntHillGraphicalChemicalsJPanel.status(	index, 
														(ChemicalButton) panel.getComponent( index - min ), 
														localChemicals); }
	}
	
	/**
	 * Changing status of viewing buttons.
	 * @param index (int)
	 * @param cb (ChemicalButton)
	 * @param localChemicals (IChemicals)
	 */
	private static void status(int index, ChemicalButton cb, IChemicals localChemicals) {
		int val = Math.min(localChemicals.getVariable( index ), 255);
		cb.setBackground(new Color(255-val, 255-val, 255-val));
		cb.setToolTipText( AntHillGraphicalChemicalsJPanel.generateToolTipText(localChemicals, index) );
	}

	/**
	 * To Generate the ToolTipText for a given Chemical (index).  
	 * @param localChemicals (IChemicals)
	 * @param chemicalIndex (int)
	 * @return Generated String. 
	 */
	private static String generateToolTipText(IChemicals localChemicals, int chemicalIndex) {
		// ToolTipText
		String chemicalName			= ChemicalsHelper.getChemicalNames().get( chemicalIndex );
		StringBuilder sbToolTipTXT	= new StringBuilder();
		sbToolTipTXT.append("varia : [").append( chemicalName ).append( "] [" );
		if ( ( chemicalIndex < ChemicalsHelper.CHEMICAL_STRICT_CHEM) 
				|| (chemicalIndex == StateType.AGING.getIndex()) )
			{ sbToolTipTXT.append( localChemicals.getVariable( chemicalIndex ) ); } 
		else 
			{ sbToolTipTXT.append( ChemicalsHelper.getChemicalNames().get( localChemicals.getVariable( chemicalIndex ) ) ); }
		sbToolTipTXT.append("]");
		return sbToolTipTXT.toString();
	}

	/**
	 * To view a group of Chemicals. 
	 * @author Gabriel Chandesris (2022)
	 */
	public class ChemicalPanel extends JPanel {

		private Organism localOrga	= null;

		public ChemicalPanel(String label, int start, int stop, Organism orga) {
			this.setName( label );
			this.localOrga	= orga;
			int halfSize	= 100; // ChemicalsHelper.CHEMICAL_LENGTH / 2;
			this.setLayout(new GridLayout( (start != 0)?1:halfSize, (start != 0)?stop - start:halfSize));
			this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			for (int i = start ; i < stop ; i++) 
			{ this.add( new ChemicalButton( i, this.localOrga ) ); }
		}

	}

	/**
	 * To represent a Chemical / Variable in the ChemicalPanel. 
	 * @author Gabriel Chandesris (2022)
	 */
	public class ChemicalButton extends JButton {

		private int index;
		private Organism localOrga	= null;

		public ChemicalButton(int i, Organism orga) {
			this.setBackground(new Color(255, 255, 255)); // (new Color(0, 0, 0));
			this.setForeground(Color.RED);
			this.setEnabled(false);
			this.setPreferredSize(new Dimension(7, 7));

			this.index		= i;
			this.localOrga	= orga;

			if (this.localOrga != null) 
				{ AntHillGraphicalChemicalsJPanel.status(this.index, this, this.localOrga.getChemicals()); }
			else { this.setBackground(Color.ORANGE); }
		}

	}

}

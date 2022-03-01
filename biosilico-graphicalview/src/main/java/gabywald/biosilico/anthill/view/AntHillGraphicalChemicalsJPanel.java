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

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalChemicalsJPanel extends JPanel implements Observer {
	
	private Organism localOrga = null;
	
	public AntHillGraphicalChemicalsJPanel(Organism orga) {
		this.localOrga		= orga;
		if ( (this.localOrga == null) || (this.localOrga.getChemicals() == null) ) {
			// ***** Organism has NO chemicals !! (or no organism selected at all !)
			this.setLayout(new BorderLayout());
			this.add(new JLabel("No Data To Show !"), BorderLayout.CENTER);
			this.add(new JLabel((this.localOrga == null)?"No Organism !":"No Chemicals !!"), BorderLayout.NORTH);
		} else {
			// IChemicals localChemicals = this.localOrga.getChemicals();
			// ***** Organism HAS brain !!
			int halfSize = 100; // ChemicalsHelper.CHEMICAL_LENGTH / 2;
			this.setLayout(new GridLayout(halfSize, halfSize));
			this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			for (int i = 0 ; i < ChemicalsHelper.CHEMICAL_LENGTH ; i++) {
				this.add( new ChemicalButton( i, this.localOrga ) );
			}
			
			this.localOrga.addObserver( this );
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		IChemicals localChemicals = this.localOrga.getChemicals();
		
		for (int i = 0 ; i < ChemicalsHelper.CHEMICAL_LENGTH ; i++) {
			int val = localChemicals.getVariable(i) / 4;
			this.getComponent(i).setBackground(new Color(val, val, val));
			StringBuilder sbToolTipTXT = new StringBuilder();
			sbToolTipTXT.append("varia : [").append( i).append( "] [" )
						.append( this.localOrga.getChemicals().getVariable( i ) ).append("]");
			((ChemicalButton)this.getComponent(i)).setToolTipText( sbToolTipTXT.toString() );
		}
	}
	
	
	/**
	 * To represent a Chemical / Variable in the JPanel. 
	 * @author Gabriel Chandesris (2022)
	 */
	public class ChemicalButton extends JButton {
		
		private int index;
		private Organism localOrga	= null;
		
		public ChemicalButton(int i, Organism orga) {
			this.setBackground(new Color(0, 0, 0));
			this.setForeground(Color.RED);
			this.setEnabled(false);
			this.setPreferredSize(new Dimension(7, 7));
			
			this.index		= i;
			this.localOrga	= orga;
			
			if (this.localOrga != null) {
				// ToolTipText
				StringBuilder sbToolTipTXT = new StringBuilder();
				sbToolTipTXT.append("varia : [").append( this.index ).append( "] [" )
							.append( this.localOrga.getChemicals().getVariable( this.index ) ).append("]");
				this.setToolTipText( sbToolTipTXT.toString() );
			} else { this.setBackground(Color.ORANGE); }
		}
		
	}


}

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

import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Organism;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;
import gabywald.biosilico.model.Neuron;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalBrainJPanel extends JPanel {

	private Organism localOrga = null;

	public AntHillGraphicalBrainJPanel(Organism orga) {
		this.localOrga		= orga;
		if ( (this.localOrga == null) || (this.localOrga.getBrain() == null) ) {
			// ***** Organism has NO brain !! (or no organism selected at all !)
			this.setLayout(new BorderLayout());
			this.add(new JLabel("No Data To Show !"), BorderLayout.CENTER);
			this.add(new JLabel((this.localOrga == null)?"No Organism !":"No Brain !!"), BorderLayout.NORTH);
		} else {
			Brain localBrain = this.localOrga.getBrain();
			// ***** Organism HAS brain !!
			this.setLayout(new GridLayout(localBrain.getHeight(), localBrain.getWidth()));
			this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			for (int j = 0 ; j < localBrain.getHeight() ; j++) {
				for (int i = 0 ; i < localBrain.getWidth() ; i++) {
					this.add( new NeuronButton( localBrain.getNeuronAt(i, j) ) );
				}
			}
		}

		System.out.println( ( ( (this.localOrga == null) || (this.localOrga.getBrain() == null) )?"NULL":"not null") + " LOADED => " + this );

	}

	/**
	 * To represent a Neuron in the JPanel. 
	 * @author Gabriel Chandesris (2022)
	 */
	public class NeuronButton extends JButton implements Observer {
		
		private Neuron localNeuron = null;
		
		public NeuronButton(Neuron neuron) {
			this.setBackground(new Color(255, 255, 255)); // (new Color(0, 0, 0));
			this.setForeground(Color.RED);
			this.setEnabled(false);
			this.setPreferredSize(new Dimension(7, 7));
			
			this.localNeuron = neuron;
			
			if (this.localNeuron != null) {
				// ToolTipText
				StringBuilder sbPositionToolTipTXT = new StringBuilder();
				sbPositionToolTipTXT.append("x : ").append(this.localNeuron.getPosition().getPosX())
									.append( "    " )
									.append("y : ").append(this.localNeuron.getPosition().getPosY());
				this.setToolTipText( sbPositionToolTipTXT.toString() );
				// Observation
				this.localNeuron.addObserver( this );
			} else { this.setBackground(Color.ORANGE); }
		}

		@Override
		public void update(Observable o, Object arg) {
			Neuron neu = (Neuron)o;
			
			int val = neu.getActivity();
			if (val > 255) { val = 255; }
			this.setBackground(new Color(255-val, 255-val, 255-val));
			
			StringBuilder sbPositionToolTipTXT = new StringBuilder();
			sbPositionToolTipTXT.append("x : ").append(this.localNeuron.getPosition().getPosX())
								.append( "    " )
								.append("y : ").append(this.localNeuron.getPosition().getPosY())
								.append(" [").append(this.localNeuron.getActivity()).append("]");
			this.setToolTipText( sbPositionToolTipTXT.toString() );
			
			if (this.localNeuron.getActivity() > 0) {
				Logger.printlnLog(LoggerLevel.LL_WARNING, this.getToolTipText() + " : " + this.localNeuron.getActivity());
			}
		}

	}
}

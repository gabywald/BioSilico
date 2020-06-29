package gabywald.neuralnetwork.view.version1;

import gabywald.neuralnetwork.essentials.version1.Brain;
import gabywald.neuralnetwork.essentials.version1.Neuron;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;

import javax.swing.JPanel;


public class BrainPanel extends JPanel {
	private Brain toStudy;
	
	public BrainPanel(int width, int height, boolean studies[]) {
		this.setLayout(new GridLayout(width,height));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		this.toStudy = new Brain(width, height);
		for (int i = 0 ; i < width ; i++) {
			for (int j = 0 ; j < height ; j++) {
				Neuron neu = new Neuron(i, j, this.toStudy);
				/** Set studies... */
				for (int k = 0 ; k < studies.length ; k++)
					{ if (studies[k]) { neu.activateStudy(k); } }
				/** Setting inputs at previous line. */ /** "Instincts". */
				if (i > 0) {
					/** System.out.println(neu.toStringCoordinates()); */
					neu.setInputs(this.toStudy.getLineAt(i-1));
					/** System.out.println(this.toStudy.getLineAt(i-1)); */
//					for (int k = 0 ; k < width ; k++) 
//						{ neu.addInput(this.toStudy.getNeuron(k, i-1)); }
				}
				/** Adding to the Brain. */
				this.toStudy.setNeuron(j, i, neu);
				
				NeuronButton butt = new NeuronButton();
				butt.setToolTipText("x : "+j+"    y : "+i);
				neu.addNeuronButtonObserver(butt);
				this.add(butt);
			}
		}
	}
	
	public Brain getBrain() { return this.toStudy; }
	
}

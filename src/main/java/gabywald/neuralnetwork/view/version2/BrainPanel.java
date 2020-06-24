package gabywald.neuralnetwork.view.version2;

import gabywald.neuralnetwork.essentials.version2.Brain;
import gabywald.neuralnetwork.essentials.version2.Neuron;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;

import javax.swing.JPanel;


public class BrainPanel extends JPanel {
	private Brain toStudy;
	
	public BrainPanel(int width, int height) {
		this.setLayout(new GridLayout(height, width));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		this.toStudy = new Brain(width, height);
		for (int j = 0 ; j < height ; j++) {
			for (int i = 0 ; i < width ; i++) {
				boolean activator = ( ((int)(Math.random()*1000))%3 != 0);
				/** System.out.print(((int)(Math.random()*1000))%2+"-"+activator+"\t"); */
				Neuron neu = new Neuron(activator, i, j, this.toStudy);
				/** Adding to the Brain. */
				this.toStudy.setNeuron(i, j, neu);
				NeuronButton butt = new NeuronButton();
				butt.setToolTipText("x : "+i+"    y : "+j);
				// if (i == 0) { System.out.println(neu.toStringCoordinates()+" ("+i+", "+j+")"); }
				neu.addObserver(butt);
				this.add(butt);
			}
		}
		
		/** Setting inputs at previous line (3 for each Neuron instance except first line). */ 
		/** XXX "Instincts". */
		for (int j = 1 ; j < height ; j++) {
			for (int i = 0 ; i < width ; i++) {
				Neuron neu = this.toStudy.getNeuron(i, j);
				int start = ((i-1 >= 0)?i-1:0);
				int stopp = ((i+1 < width-1)?i+1:width-1);
				for (int k = start ; k <= stopp ; k++) {
					Neuron input = this.toStudy.getNeuron(k, j-1);
					neu.addInput(input);
				}
				/** If Neuron instance in border of Brain, add neuron up to inputs. */
				if (neu.getInputLength() == 2) 
					{ neu.addInput(this.toStudy.getNeuron(neu.getPosX(), neu.getPosY()-1)); }
				/** if (j == 1) { System.out.println(neu); } */
			}
		}
		// for (int i = 0 ; i < width ; i++) {
			// for (int j = 0 ; j < height ; j++) {
				// Neuron neu = this.toStudy.getNeuron(i, j);
				// System.out.println(neu.toStringCoordinates()+"\t("+i+", "+j+") ");
			// }
		// } 
		
		double[] datas = new double[this.toStudy.getWidth()];
		for (int j = 0 ; j < datas.length ; j++) { 
			datas[j] = (int)(Math.random()*255);
		}
		this.toStudy.submit(datas);
		
		this.toStudy.start();
	}
	
	public Brain getBrain() { return this.toStudy; }
	
}

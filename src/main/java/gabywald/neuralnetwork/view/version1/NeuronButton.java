package gabywald.neuralnetwork.view.version1;

import gabywald.neuralnetwork.essentials.version1.Neuron;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;


public class NeuronButton extends JButton 
							implements Observer {
	private boolean test;
	
	public NeuronButton() {
		this.setBackground(new Color(0, 0, 0));
		this.setForeground(Color.RED);
		this.setEnabled(false);
		this.test = false;
	}

	public void update(Observable o, Object arg) {
		Neuron neu = (Neuron)o;
		int val = (int)(neu.output()*100); // /(int)255;
		// int blu = ((this.test)?255:0);
		val = (val > 255)?255:val;
		this.setBackground(new Color(val, val, val));
		// this.setText(neu.getInputsNotNull().length+"");
		this.test = (!this.test);
	}

}

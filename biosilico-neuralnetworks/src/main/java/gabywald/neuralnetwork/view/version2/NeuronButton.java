package gabywald.neuralnetwork.view.version2;

import gabywald.neuralnetwork.essentials.version2.Neuron;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;


public class NeuronButton extends JButton 
							implements Observer {
	
	public NeuronButton() {
		this.setBackground(new Color(0, 0, 0));
		this.setForeground(Color.RED);
		this.setEnabled(false);
	}

	public void update(Observable o, Object arg) {
		Neuron neu = (Neuron)o;
		boolean neg = false;
		int val = (int)(neu.output()*255);
		if (val < 0) { neg = true; val = -val; }
		val = (val > 255)?255:val;
		this.setBackground(new Color((neg)?val:0, 0, (!neg)?val:0));
		/** this.setText(neu.getInputLength()+""); */
	}

}

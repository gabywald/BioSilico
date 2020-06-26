package gabywald.biosilico.tests.graph;


import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Neuron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class BrainView01 extends JFrame {
	private static final long serialVersionUID = 1L;
	private static BrainView01 instance = null;
	private static int FRAME_HEIGHT = 800;
	private static int FRAME_WIDTH = 600;
	private static int LABEL_HEIGHT = 20;
	
	private JButton listButts[][];
	private JPanel contentPanel;
	private JLabel contentLabel;
	private Brain testBrain;
	private int brainHeight;
	private int brainWidth;
	
	private BrainView01() {
		this.contentPanel = new JPanel();
		this.contentPanel.setSize(FRAME_HEIGHT, FRAME_WIDTH-LABEL_HEIGHT);
		this.contentLabel = new JLabel();
		this.contentLabel.setSize(FRAME_HEIGHT,LABEL_HEIGHT);
		this.contentLabel.setText("<nothing yet>");
		
		this.testBrain = Brain.getBrain();
		this.brainHeight = this.testBrain.getHeight();
		this.brainWidth = this.testBrain.getWidth();
		/** Building the Brain : lobes at first and last line
		 * and 'conception' lobe between the two previous... */
		Neuron receptorType = Neuron.getReceptorNeuron();
		Neuron receptorActi = new Neuron(0,100,0,0,0,0,false,0);
		Neuron conception = // Neuron.getConceptionNeuron();
			new Neuron (0,10,1,3,8,2,true,2);
		Neuron emittersType = Neuron.getEmitterNeuron();
		receptorActi.setActivity(200);
		try {
			this.testBrain.setLobe(1, this.brainWidth/2, 0, 0, 
					receptorActi, false);
			this.testBrain.setLobe(1, this.brainWidth/2, 0, this.brainWidth/2, 
					receptorType, false);
			this.testBrain.setLobe(this.brainHeight-2, this.brainWidth-7, 1, 3, 
					conception, true);
			this.testBrain.setLobe(1, this.brainWidth, 
					this.brainHeight-1, 0, emittersType, false);
		} 
		catch (BrainLengthException e) { e.printStackTrace(); } 
		catch (BrainLobeReplaceException e) { e.printStackTrace(); }
		
		/** Building graphical view */
		int max_height = this.brainHeight;
		int max_width = this.brainWidth;
		this.listButts = new JButton[max_width][max_height];
		this.contentPanel.setLayout(new GridLayout(max_width,max_height));
		for (int i = 0 ; i < max_height ; i++) {
			for (int j = 0 ; j < max_width ; j++) {
				JButton tmp = new JButton();
				Neuron tmpNeu = this.testBrain.getNeuronAt(i, j);
				if (tmpNeu != null) { 
					if (tmpNeu.isActivated()) { tmp.setSelected(true); }
					else { tmp.setSelected(false); }
					tmp.setEnabled(true);
				}
				else { tmp.setEnabled(false); }
				this.contentPanel.add(tmp);
				this.listButts[i][j] = tmp;
			}
		}
		/**
		JButton stdButton01 = new JButton();
		stdButton01.setEnabled(true);
		JButton stdButton02 = new JButton();
		stdButton02.setEnabled(false);
		JButton stdButton03 = new JButton();
		stdButton03.setSelected(true);
		JButton stdButton04 = new JButton();
		stdButton04.setSelected(false);
		this.contentPanel.add(stdButton01);
		this.contentPanel.add(stdButton02);
		this.contentPanel.add(stdButton03);
		this.contentPanel.add(stdButton04);
		*/
		
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.contentPanel,"Center");
		this.getContentPane().add(this.contentLabel,"South");
		
		this.setTitle("Brain simulation / Modelisation of Neural Network");
		this.setLocation(50, 50);
		this.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void refreshment(int count) {
		this.testBrain.networking();
		for (int i = 0 ; i < this.brainHeight ; i++) {
			for (int j = 0 ; j < this.brainWidth ; j++) {
				JButton tmp = this.listButts[i][j];
				Neuron tmpNeu = this.testBrain.getNeuronAt(i, j);
				if (tmpNeu != null) { 
					if (tmpNeu.isActivated()) { tmp.setSelected(true);tmp.setBackground(Color.black); }
					else { tmp.setSelected(false);tmp.setBackground(Color.white); }
					tmp.setEnabled(true);
				}
				else { tmp.setEnabled(false); }
			}
		}
		// this.contentLabel.setText(count+"");
	}
	
	public static BrainView01 getBrainGraphicalView() {
		if (BrainView01.instance == null) 
			{ BrainView01.instance = new BrainView01(); }
		return BrainView01.instance;
	}
	
	public static void main(String[] args) {
		BrainView01 bgv = BrainView01.getBrainGraphicalView();
		for (int i = 0 ; i < 1000 ; i++) { bgv.refreshment(i); }
	}
}

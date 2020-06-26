package gabywald.biosilico.tests.graph;


import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.Neuron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class BrainView04 extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private static BrainView04 instance = null;
	private static int FRAME_HEIGHT = 800;
	private static int FRAME_WIDTH = 600;
	private static int LABEL_HEIGHT = 20;
	
	private JButton listButts[][];
	private JPanel contentPanel;
	private JLabel contentLabel;
	private Brain testBrain;
	private int brainHeight;
	private int brainWidth;

	private BrainView04() {
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
		Neuron receptorActi = new Neuron(0,100,10,0,0,0,false,0);
		Neuron decisionTest = new Neuron (0,10,1,1,16,4,false,0,true);
		Neuron conceptionUn = new Neuron (0,10,1,2,4,4,true,100);
		// Neuron conceptionDe = new Neuron (0,10,1,1,5,1,false,2);
		
		try {
			this.testBrain.setLobe(1, this.brainWidth, 0, 0, receptorActi, false);
			this.testBrain.setLobe(1, this.brainWidth,this.brainHeight-1, 0, decisionTest, false);
			this.testBrain.setLobe(1, this.brainWidth, 1, 0, conceptionUn, true);
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
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.contentPanel,"Center");
		this.getContentPane().add(this.contentLabel,"South");
		
		this.setTitle("Brain simulation / Modelisation of Neural Network");
		this.setLocation(50, 50);
		this.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public Brain getBrain() { return this.testBrain; }
	
	public static BrainView04 getBrainGraphicalView() {
		if (BrainView04.instance == null) 
			{ BrainView04.instance = new BrainView04(); }
		return BrainView04.instance;
	}
	
	public static void main(String[] args) {
		BrainView04 bgv = BrainView04.getBrainGraphicalView();
		try { Thread.sleep(2000); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		Brain test = bgv.getBrain();
		test.addObserver(bgv);
		Thread brainThread = new Thread(test);
		brainThread.start();
	}
	
	public void update(Observable arg0, Object arg1) {
		try { Thread.sleep(200); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		Brain obj_change = (Brain)arg0;
		for (int i = 0 ; i < this.brainHeight ; i++) {
			for (int j = 0 ; j < this.brainWidth ; j++) {
				JButton tmp = this.listButts[i][j];
				Neuron tmpNeu = obj_change.getNeuronAt(i, j);
				if (tmpNeu != null) { 
					if (tmpNeu.isActivated()) { tmp.setSelected(true);tmp.setBackground(Color.black); }
					else { tmp.setSelected(false);tmp.setBackground(Color.white); }
					tmp.setEnabled(true);
				}
				else { tmp.setEnabled(false); }
				
				Random rand = new Random();
				if (rand.nextInt(100)%20 == 0) {
					int alea = rand.nextInt(this.brainWidth);
					int acti = obj_change.getNeuronAt(0, alea).getActivity();
					acti += rand.nextInt(100);
					if (acti < 500) 
						{ obj_change.getNeuronAt(0, alea).setActivity(acti); }
				}
			}
		}
	}
}

package gabywald.biosilico.tests.neuralnetworks;


import gabywald.biosilico.exceptions.BrainLengthException;
import gabywald.biosilico.exceptions.BrainLobeReplaceException;
import gabywald.biosilico.model.Brain;
import gabywald.biosilico.model.BrainBuilder;
import gabywald.biosilico.model.Neuron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class BrainViewSample01 extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private static BrainViewSample01 instance = null;
	private static int FRAME_HEIGHT	= 800;
	private static int FRAME_WIDTH	= 600;
	private static int LABEL_HEIGHT	= 20;
	
	private JButton listButts[][];
	private JPanel contentPanel;
	private JLabel contentLabel;
	private Brain testBrain;
	private int brainHeight;
	private int brainWidth;

	private BrainViewSample01() {
		this.contentPanel = new JPanel();
		this.contentPanel.setSize(FRAME_HEIGHT, FRAME_WIDTH-LABEL_HEIGHT);
		this.contentLabel = new JLabel();
		this.contentLabel.setSize(FRAME_HEIGHT,LABEL_HEIGHT);
		this.contentLabel.setText("<nothing yet>");
		
		try {
			this.testBrain = BrainBuilder.brainBuilder(20, 20);

			this.brainHeight	= this.testBrain.getHeight();
			this.brainWidth		= this.testBrain.getWidth();
			/** Building the Brain : lobes at first and last line
			 * and 'conception' lobe between the two previous... */
			Neuron receptorActi = new Neuron(0, 100, 50, 0, 0, 0, false, 0);
			receptorActi.setActivity(1000);
			Neuron conception	= new Neuron(0, 10, 5, 1, 3, 4, false, 2);
			Neuron emittersTest = new Neuron(0, 10, 8, 4, 4, 4, false, 0);

			int interval = 4;
			IntStream.iterate(2, i -> i+interval).limit( this.brainWidth / interval)
			.forEach( i -> {
				try {
					this.testBrain.setLobe(1, 1, 1, i, receptorActi, false);
				} catch (BrainLengthException | BrainLobeReplaceException e) {
					e.printStackTrace();
				}
			});
			
			this.testBrain.setLobe(12, 18, 3, 1, conception, true);
			
//			IntStream.iterate(2, i -> i+interval).limit( this.brainWidth / interval)
//			.forEach( i -> {
//				try {
//					this.testBrain.setLobe(15, interval, 3, i, conception, true);
//				} catch (BrainLengthException | BrainLobeReplaceException e) {
//					e.printStackTrace();
//				}
//			});
			
			this.testBrain.setLobe(1, 1, this.brainHeight-3, 8, emittersTest, false);
			this.testBrain.setLobe(1, 1, this.brainHeight-3, 9, emittersTest, false);
			this.testBrain.setLobe(1, 1, this.brainHeight-3, 10, emittersTest, false);
			this.testBrain.setLobe(1, 1, this.brainHeight-3, 11, emittersTest, false);
			this.testBrain.setLobe(1, 1, this.brainHeight-3, 12, emittersTest, false);
		} 
		catch (BrainLengthException e)		{ e.printStackTrace(); } 
		catch (BrainLobeReplaceException e)	{ e.printStackTrace(); }
		
		// ***** Building graphical view 
		int max_height	= this.brainHeight;
		int max_width	= this.brainWidth;
		this.listButts	= new JButton[max_width][max_height];
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
	
	public static BrainViewSample01 getBrainGraphicalView() {
		if (BrainViewSample01.instance == null) 
			{ BrainViewSample01.instance = new BrainViewSample01(); }
		return BrainViewSample01.instance;
	}
	
	public static void main(String[] args) {
		BrainViewSample01 bgv = BrainViewSample01.getBrainGraphicalView();
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
			}
		}
	}
}

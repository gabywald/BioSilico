package gabywald.biosilico.tests.graph;

import gabywald.biosilico.genetics.BiochemicalReaction;
import gabywald.biosilico.genetics.BrainGene;
import gabywald.biosilico.genetics.BrainLobeGene;
import gabywald.biosilico.genetics.EmitterReceptor;
import gabywald.biosilico.genetics.InitialConcentration;
import gabywald.biosilico.model.Chromosome;
import gabywald.biosilico.model.Neuron;
import gabywald.biosilico.model.Organism;
import gabywald.global.view.text.Terminal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Gabriel Chandesris (2010)
 */
public class AlifeModelView01 extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private static AlifeModelView01 instance = null;
	private static int FRAME_HEIGHT = 800;
	private static int FRAME_WIDTH = 600;
	private static int LABEL_HEIGHT = 20;
	private JButton listButts[][];
	private JPanel contentPanel;
	private JLabel contentLabel;
	
	private AlifeModelView01(Organism orga) { 
		orga.addObserver(this); 
		this.contentPanel = new JPanel();
		this.contentPanel.setSize(FRAME_HEIGHT, FRAME_WIDTH-LABEL_HEIGHT);
		this.contentLabel = new JLabel();
		this.contentLabel.setSize(FRAME_HEIGHT,LABEL_HEIGHT);
		this.contentLabel.setText("<nothing yet>");
		
		/** Building graphical view */
		int max_height = 100; //orga.getBrain().getHeight();
		int max_width = 100; //orga.getBrain().getWidth();
		this.listButts = new JButton[max_width][max_height];
		this.contentPanel.setLayout(new GridLayout(max_width,max_height));
		for (int i = 0 ; i < max_height ; i++) {
			for (int j = 0 ; j < max_width ; j++) {
				JButton tmp = new JButton();
				Neuron tmpNeu = null; // orga.getBrain().getNeuronAt(i, j);
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
		
		this.setTitle("Organism-Brain simulation / Modelisation of Neural Network and Biochemistry");
		this.setLocation(50, 50);
		this.setSize(FRAME_HEIGHT, FRAME_WIDTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	private static AlifeModelView01 getAlifeModelView(Organism orga) {
		if (AlifeModelView01.instance == null) 
			{ AlifeModelView01.instance = new AlifeModelView01(orga); }
		return AlifeModelView01.instance;
	}
	
	public static void main (String args[]) {
		Terminal.ecrireStringln("Launch ALIFE Model GRAPHIC View");
		
		Chromosome basic_genome = new Chromosome();
		/** Brain Gene */
		int brain_height = 99;
		int brain_width = 99;
		int brain_depth = 99;
		int brain_more = 99;
		basic_genome.addGene(new BrainGene(
				true, false, false, true, 0, 0, 0, 25,
				brain_height,brain_width,brain_depth,brain_more));
		
		/** Some BrainLobe Genes */
		/** receptors */
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 0, 0, 0, false, 0, false, 
				1, brain_width, 0, 0, false));
		/** decision */
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 1, 16, 4, false, 0, true, 
				1, brain_width, brain_height/2-2, 0, false));
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 1, 16, 4, false, 0, true, 
				1, brain_width, brain_height/2-1, 0, false));
		
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 1, 16, 4, false, 0, true, 
				2, brain_width, 18, 0, false));
		
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 1, 16, 4, false, 0, true, 
				2, brain_width, 38, 0, false));
		/**
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 
				0, 10, 1, 1, 16, 4, false, 0, true, 
				2, brain_width/2, brain_height/4*3, 0, false));
		
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 
				0, 10, 1, 1, 16, 4, false, 0, true, 
				2, brain_width/4, brain_height/4*3, brain_width/4*3, false));
		*/
		
		for (int i = 0 ; i < brain_width/2-4 ; i += 2) {
			basic_genome.addGene(new BrainLobeGene(
					true, false, false, true, 0, 0, 0, 25,
					0, 10, 1, 1, 16, 4, false, 0, true, 
					brain_height/8-4, 1, brain_height/16*15, i, false));
		}
		
		for (int i = brain_width/2-4 ; i < brain_width ; i += 2) {
			basic_genome.addGene(new BrainLobeGene(
					true, false, false, true, 0, 0, 0, 25,
					0, 10, 1, 1, 16, 4, false, 0, true, 
					brain_height/4-2, 1, brain_height/4*3, i, false));
		}
		
		
		/** decision part 1*/
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 1, 16, 4, false, 0, false, /** <= last boolean flag here for WTA */
				1, brain_width/2, brain_height-1, 0, false));		
		/** decision part 2 / "emitter" */
		basic_genome.addGene(new BrainLobeGene(
				true, false, false, true, 0, 0, 0, 25,
				0, 10, 1, 8, 16, 4, false, 0, true, /** <= last boolean flag here for WTA */
				1, brain_width/2, brain_height-1, brain_width/2, false));
		
		/** conception */
		/** basic_genome.addGene(new BrainLobeGene(
				true, true, false, true, 0, 0, 0, 
				0, 10, 1, 2, 4, 4, true, brain_height/2, false, 
				1, brain_width, 1, 0, false));
		basic_genome.addGene(new BrainLobeGene(
				true, true, false, true, 0, 0, 0, 
				0, 10, 1, 2, 4, 4, true, brain_height/2, false, 
				1, brain_width, brain_height/2+1, 0, false));
		basic_genome.addGene(new BrainLobeGene(
				true, true, false, true, 0, 0, 0, 
				0, 10, 1, 2, 4, 4, true, brain_height/2, false, 
				1, brain_width, brain_height/4*3, 0, false)); */
		for (int i = 0 ; i < brain_width ; i += 10) {
			basic_genome.addGene(new BrainLobeGene(
					true, true, false, true, 0, 0, 0, 25,
					0, 10, 1, 2, 4, 4, true, brain_height/2, false, 
					5, brain_width/10, brain_height/2+1, i, false));
		}
		for (int i = 0 ; i < brain_width ; i += 10) {
			basic_genome.addGene(new BrainLobeGene(
					true, true, false, true, 0, 0, 0, 25,
					0, 10, 1, 2, 4, 4, true, brain_height/5, false, 
					5, brain_width/10, 1, i, false));
		}
		
		for (int i = 0 ; i < brain_width ; i += 10) {
			basic_genome.addGene(new BrainLobeGene(
					true, true, false, true, 0, 0, 0, 25,
					0, 10, 1, 2, 4, 4, true, brain_height/5, false, 
					5, brain_width/10, 20, i, false));
		}
		
		for (int i = 0 ; i < brain_width ; i += 10) {
			basic_genome.addGene(new BrainLobeGene(
					true, true, false, true, 0, 0, 0, 25,
					0, 10, 1, 2, 4, 4, true, brain_height/5, false, 
					5, brain_width/10, 40, i, false));
		}
		
		/** Receptor */
		for (int i = 0 ; i < brain_width ; i++) {
			basic_genome.addGene(new EmitterReceptor(
					true, true, true, true, 0, 999, 0, 25,
					i,10,100,0,i,true,true));
		}
		
		/** Emitter */
		for (int i = 0 ; i < brain_width ; i++) {
			basic_genome.addGene(new EmitterReceptor(
					true, true, true, true, 0, 999, 0, 25,
					i,10,10,brain_height-1,i,false,true));
		}
		
		
		/** Some Initial Concentration Genes */
		basic_genome.addGene(new InitialConcentration(
				false, false, false, true, 0, 0, 0, 25,
				1, 100));
		basic_genome.addGene(new InitialConcentration(
				false, false, false, true, 0, 0, 0, 25,
				2, 100));
		basic_genome.addGene(new InitialConcentration(
				false, false, false, true, 0, 0, 0, 25,
				3, 100));
		basic_genome.addGene(new InitialConcentration(
				false, false, false, false, 0, 0, 0, 25,
				4, 100));
		basic_genome.addGene(new InitialConcentration(
				false, false, false, true, 0, 0, 0, 25,
				5, 100));
		
		
		/** Some Biochemical Reaction Genes */
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				1, 10, 2, 10, 3, 5, 4, 5, 1));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				4, 10, 5, 10, 6, 5, 7, 5, 1));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				6, 10, 0, 0, 7, 10, 0, 0, 4));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				7, 5, 0, 0, 8, 5, 0, 0, 4));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				7, 1, 8, 1, 9, 5, 10, 5, 10));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				9, 6, 10, 6, 1, 10, 2, 2, 20));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				9, 5, 10, 5, 7, 10, 0, 0, 4));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				9, 5, 10, 5, 6, 10, 0, 0, 4));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				3, 5, 0, 0, 8, 5, 0, 0, 4));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				8, 10, 0, 0, 9, 2, 2, 8, 10));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				4, 1, 0, 0, 15, 1, 0, 0, 10));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				2, 5, 5, 5, 6, 5, 7, 5, 10));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				2, 10, 0, 0, 1, 5, 0, 0, 10));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				1, 5, 0, 0, 2, 10, 0, 0, 10));
		basic_genome.addGene(new BiochemicalReaction(
				false, false, false, true, 0, 999, 0, 25,
				1, 200, 0, 0, 50, 20, 0, 0, 5));
		
		Organism test = new Organism(basic_genome);
		AlifeModelView01.getAlifeModelView(test);
		Thread testThread = new Thread(test);
		testThread.start();
	}

	public void update(Observable arg0, Object arg1) {
		/** ObjetObservable obj_change = (ObjetObservable)arg0;
		Terminal.ecrireStringln(obj_change.getState()); */
		try { Thread.sleep(200); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		Organism obj_change = (Organism)arg0;
		this.contentLabel.setText(""+obj_change.getState());
		for (int i = 0 ; i < obj_change.getBrain().getHeight() ; i++) {
			for (int j = 0 ; j < obj_change.getBrain().getWidth() ; j++) {
				JButton tmp = this.listButts[i][j];
				Neuron tmpNeu = obj_change.getBrain().getNeuronAt(i, j);
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

package gabywald.neuralnetwork.view.version1;

import gabywald.neuralnetwork.essentials.version1.Brain;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;

import javax.swing.JFrame;


public class BrainFrame extends JFrame {
	private static BrainFrame instance;

	private BrainFrame() {
		this.setTitle("Neuralnetwork && Brain Tests");
		this.setLayout(new GridLayout(2,4));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		int size = 50;
		BrainPanel[] panels = new BrainPanel[8];
		panels[0] = new BrainPanel(size, size, new boolean[]  {  true, false, false,  true } );
		panels[1] = new BrainPanel(size, size, new boolean[]  {  true,  true,  true,  true } );
		panels[2] = new BrainPanel(size, size, new boolean[]  {  true,  true, false, false } );
		panels[3] = new BrainPanel(size, size, new boolean[]  { false, false, false, false } );
		
		panels[4] = new BrainPanel(size, size, new boolean[]  {  true, false, false, false } );
		panels[5] = new BrainPanel(size, size, new boolean[]  { false,  true, false, false } );
		panels[6] = new BrainPanel(size, size, new boolean[]  { false, false,  true, false } );
		panels[7] = new BrainPanel(size, size, new boolean[]  { false, false, false,  true } );
		
		for (int k = 0 ; k < panels.length ; k++) { 
			this.add(panels[k]);
			Brain currentBrain = panels[k].getBrain();
			// System.out.println(currentBrain.toString());
			currentBrain.start();
		}
		
		this.setLocation(100, 100);
		this.setSize(1024, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for (int i = 0 ; i < size ; i++) {
			double[] datas = new double[size];
//			for (int j = 0 ; j < datas.length ; j++) 
//				{ datas[j] = Math.random()*10+1; }
			for (int j = 0 ; j < datas.length ; j++) { 
				if (j == size/2) { datas[j] = 1; }
				else { datas[j] = 0; }
			}
			for (int j = 0 ; j < panels.length ; j++) 
				{ panels[j].getBrain().submit(datas); }
		}
	}

	public static BrainFrame getInstance() {
		if (BrainFrame.instance == null) 
			{ BrainFrame.instance = new BrainFrame(); }
		return BrainFrame.instance;
	}

}

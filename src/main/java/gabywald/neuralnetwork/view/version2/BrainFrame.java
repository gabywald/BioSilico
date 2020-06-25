package gabywald.neuralnetwork.view.version2;

import gabywald.neuralnetwork.controller.StartStopListener;
import gabywald.neuralnetwork.essentials.version2.Brain;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BrainFrame extends JFrame {
	private static BrainFrame instance;
	
	private BrainPanel panel;
	
	private static final String[] NAMES = {
		"Record", "Start", "Stop", 
	};
	private JButton[] buttons;
	
	private BrainFrame() {
		this.setTitle("Some visuals and tests about a Neural Network...");
		this.setLayout(new BorderLayout());
		
		this.panel = new BrainPanel(100, 25);
		this.add(panel, "Center");
		
		JPanel south	= new JPanel(null);
		south.setPreferredSize(new Dimension(1024, 25));
		this.buttons	= new JButton[BrainFrame.NAMES.length];
		for (int i = 0 ; i < BrainFrame.NAMES.length ; i++) {
			this.buttons[i] = new JButton(BrainFrame.NAMES[i]);
			this.buttons[i].setBounds(100*i, 0, 100, 20);
			south.add(this.buttons[i]);
			if (i == 1) { this.buttons[i].addActionListener(new StartStopListener(true)); }
			if (i == 2) { this.buttons[i].addActionListener(new StartStopListener(false)); }
		}
		this.add(south, "South");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 100);
		this.setSize(1024, 768);
		this.setVisible(true);
	}
	
	public static BrainFrame getInstance() {
		if (BrainFrame.instance == null) 
			{ BrainFrame.instance = new BrainFrame(); }
		return BrainFrame.instance;
	}
	
	public Brain getBrain()	{ return this.panel.getBrain(); }
	
}

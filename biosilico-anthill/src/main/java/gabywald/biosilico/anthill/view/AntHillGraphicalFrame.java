package gabywald.biosilico.anthill.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import gabywald.biosilico.anthill.data.DataCollector;
import gabywald.global.view.graph.GenericJFrame;

/**
 * 
 * <br><i>Design-Pattern Singleton. </i>
 * @author Gabriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalFrame	extends GenericJFrame 
									implements ActionListener {
	/** Unique instance of this view. */
	private static AntHillGraphicalFrame instance = null;
	
	/** Panel for plot evolution of Chemicals... */
	private ChartPanel cPanel = null;
	/** JPanel in Center of the JFrame. */
	private JPanel centerPanel;
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (AntHillGraphicalFrame)
	 */
	public static AntHillGraphicalFrame getInstance(DataCollector dc) {
		if (AntHillGraphicalFrame.instance == null) 
			{ AntHillGraphicalFrame.instance = new AntHillGraphicalFrame( dc ); }
		return AntHillGraphicalFrame.instance;
	}
	
	private AntHillGraphicalFrame(DataCollector dc) {
		
		// TODO complete view !
		// TODO Western Panel
		// TODO Northern Panel
		
		this.initCenterPanel( dc );
		
		/** Positions in content of JFrame */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.centerPanel, BorderLayout.CENTER);
		
		this.setTitle("AntHill Graphical Frame");
		this.setVisible(true);
	}
	
	private void initCenterPanel(DataCollector dc) {
		// CenterPanel : ChartPanel
		// this.enableCenterPanel(false);
		
		this.cPanel = dc.generateChartPanel();
		
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BorderLayout());
		this.centerPanel.add(this.cPanel, BorderLayout.CENTER);
	}
	
	public ChartPanel setChartPanel(ChartPanel cp) {
		ChartPanel tmp = this.cPanel;
		this.cPanel = cp;
		return tmp;
	}
	
	public ChartPanel getChartPanel() {
		return this.cPanel;
	}
	
	@Override
	public void enableCenterPanel(boolean b) {
		this.centerPanel.setEnabled( b );
		this.cPanel.setEnabled( b );
	}


	@Override
	public void enableWesternPanel(boolean b) {
		// "Little" map of World, can be selected (set of buttons). 
		// JList of organism
		// Controls of simulation (start, stop, step...)
		// TODO enableWesternPanel
		// TODO initWesternPanel
	}

	@Override
	public void enableNorthernPanel(boolean b) {
		// (alternative) Map of World / WorldCase
		// List of statistics
		// Events
		// TODO enableNorthernPanel
		// TODO initNorthernPanel
	}

	@Override
	public void enableSouthPanel(boolean b)		{ ; }
	
	@Override
	public void enableEasternPanel(boolean b)	{ ; }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}

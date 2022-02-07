package gabywald.biosilico.anthill.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

import gabywald.biosilico.anthill.data.DataCollector;
import gabywald.biosilico.anthill.launcher.AntHillGraphicalLauncher;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.environment.World2D;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.global.view.graph.GenericJFrame;
import gabywald.global.view.graph.GridBagJPanel;

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
	private GridBagJPanel westernPanel;
	
	private AntHillSelectionJScroll<Organism> organismsJScroll		= null;
	private AntHillSelectionJScroll<World2DCase> locationsJScroll	= null;
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (AntHillGraphicalFrame)
	 */
	public static AntHillGraphicalFrame getInstance(AntHillGraphicalLauncher agl) {
		if (AntHillGraphicalFrame.instance == null) 
			{ AntHillGraphicalFrame.instance = new AntHillGraphicalFrame( agl ); }
		return AntHillGraphicalFrame.instance;
	}
	
	private AntHillGraphicalFrame(AntHillGraphicalLauncher agl) {
		
		// TODO complete view !
		// TODO Western Panel
		// TODO Northern Panel
		
		this.initCenterPanel( agl.getDataCollector() );
		this.initWesternPanel( agl );
		
		/** Positions in content of JFrame */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.westernPanel, BorderLayout.WEST);
		
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
	
	private void initWesternPanel(AntHillGraphicalLauncher agl) {
		this.organismsJScroll = new AntHillSelectionJScroll<Organism>(agl.getOrganisms());
		this.locationsJScroll = new AntHillSelectionJScroll<World2DCase>(agl.getLocations());
		
		this.westernPanel = new GridBagJPanel() { };
		// this.westernPanel.setPreferredSize(new Dimension(100, 0));
		this.westernPanel.setSize(this.getWidth() / 2, this.getHeight());
		this.westernPanel.addBagComponent(new JLabel("Organisms List")		, 0, 0);
		this.westernPanel.addBagComponent(this.organismsJScroll				, 0, 1);
		this.westernPanel.addBagComponent(new JLabel("Locations List")		, 0, 2);
		this.westernPanel.addBagComponent(this.locationsJScroll				, 0, 3);
		
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

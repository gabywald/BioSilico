package gabywald.biosilico.anthill.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

import gabywald.biosilico.anthill.data.DataCollector;
import gabywald.biosilico.model.Organism;
import gabywald.biosilico.model.environment.World2DCase;
import gabywald.biosilico.view.GeneKitsGBJPanel;
import gabywald.biosilico.view.genecreator.GeneCreatorAsJPanel;
import gabywald.biosilico.view.genetickit.GeneticKitAsJPanel;
import gabywald.biosilico.view.organismkit.OrganismKitAsJPanel;
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
	
	private AntHillGraphicalModel localModel	= null;
	
	/** Panel for plot evolution of Chemicals... */
	private ChartPanel cPanel = null;
	/** JPanel in Center of the JFrame. */
	private JPanel centerPanel;
	private GeneKitsGBJPanel westernPanel;
	
	private AntHillGraphicalJScroll<Organism> organismsJScroll		= null;
	private AntHillGraphicalJScroll<World2DCase> locationsJScroll	= null;
	
	private JTextField stepsTextField	= new JTextField("0");
	private JButton oneStepButton		= new JButton("One Step"), 
					startButton			= new JButton("Start"), 
					stopButton			= new JButton("Stop");
	
	private AntHillGraphicalWorld2DCaseJPanel wcInfosPanel			= new AntHillGraphicalWorld2DCaseJPanel();
	private AntHillGraphicalRunner agr								= null;
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (AntHillGraphicalFrame)
	 */
	public static AntHillGraphicalFrame getInstance(AntHillGraphicalModel agm) {
		if (AntHillGraphicalFrame.instance == null) 
			{ AntHillGraphicalFrame.instance = new AntHillGraphicalFrame( agm ); }
		return AntHillGraphicalFrame.instance;
	}
	
	private AntHillGraphicalFrame(AntHillGraphicalModel agm) {
		
		// TODO complete view !
		// TODO Western Panel
		// TODO Northern Panel
		
		this.localModel = agm;
		
		this.initCenterPanel( agm.getDataCollector() );
		this.initWesternPanel( agm );
		
		/** Positions in content of JFrame */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.centerPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.westernPanel, BorderLayout.WEST);
		
		this.setSize(1152, 864); 
		this.setTitle("AntHill Graphical Frame");
		this.setVisible(true);
	}
	
	private void initCenterPanel(DataCollector dc) {
		// CenterPanel : ChartPanel
		// this.enableCenterPanel(false);
		
		this.cPanel = dc.generateChartPanel();
		
		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Graphic Results", null, this.cPanel, dc.getTitle());
		
		tabbedPane.addTab("Organism Kit", null, new OrganismKitAsJPanel(), "Organism Creation & modifications");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_F10);
		
		tabbedPane.addTab("Genetic Kit", null, new GeneticKitAsJPanel(), "Manipulate Gene of Organism");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_F11);
		
		tabbedPane.addTab("Gene Creator", null, new GeneCreatorAsJPanel(), "Gene Creation");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_F12);
		
		JPanel panel4 = new JPanel();
		tabbedPane.addTab("World Editor", null, panel4, "Does nothing");
		
		JPanel panel5 = new JPanel();
		tabbedPane.addTab("World Case Editor", null, panel5, "Does nothing");
		
		this.centerPanel.add(tabbedPane, BorderLayout.CENTER);
		
	}
	
	private void initWesternPanel(AntHillGraphicalModel agm) {
		this.organismsJScroll = new AntHillGraphicalJScroll<Organism>(agm.getOrganisms());
		this.locationsJScroll = new AntHillGraphicalJScroll<World2DCase>(agm.getLocations());
		// TODO add Listener here !! (container ?)
		
		this.westernPanel = new GeneKitsGBJPanel();
		// this.westernPanel.setPreferredSize(new Dimension(100, 0));
		// this.westernPanel.setSize(this.getWidth() / 5, this.getHeight());
		this.westernPanel.addBagComponent(new JLabel("Organisms List")		, 0, 0, 2);
		this.westernPanel.addBagComponent(this.organismsJScroll				, 0, 1, 2);
		this.westernPanel.addBagComponent(new JLabel("Locations List")		, 0, 2, 2);
		this.westernPanel.addBagComponent(this.locationsJScroll				, 0, 3, 2);
		this.westernPanel.addBagComponent(new JSeparator()					, 0, 4, 2);
		this.westernPanel.addBagComponent(this.stepsTextField				, 0, 5);
		this.westernPanel.addBagComponent(this.oneStepButton				, 1, 5);
		this.westernPanel.addBagComponent(this.startButton					, 0, 6);
		this.westernPanel.addBagComponent(this.stopButton					, 1, 6);
		
		this.westernPanel.addBagComponent(new JPanel(), 0, 7, 3); /** blank space */
		
		this.westernPanel.addBagComponent(this.wcInfosPanel, 0, 8, 3);
		
		this.oneStepButton.addActionListener(this);
		this.startButton.addActionListener(this);
		this.stopButton.addActionListener(this);
		
		this.locationsJScroll.addListSelectionListener( new AntHillGraphicalJScrollLocationListener(this) );
		this.organismsJScroll.addListSelectionListener( new AntHillGraphicalJScrollOrganismListener(this) );
	}
	
	public ChartPanel setChartPanel(ChartPanel cp) {
		ChartPanel tmp = this.cPanel;
		this.cPanel = cp;
		return tmp;
	}
	
	public ChartPanel getChartPanel() 
		{ return this.cPanel; }
	
	public AntHillGraphicalModel getLocalModel() 
		{ return this.localModel; }
	
	public void setSteps(int stepsCount) {
		this.stepsTextField.setText( stepsCount + "" );
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

	// TODO externalize ActionListener behavior (controller)
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source.equals(this.oneStepButton)) { 
			this.localModel.oneStep();
			this.setSteps( this.localModel.getStepsCounter() );
		} else if (source.equals(this.startButton)) {
			if (this.agr == null) 
				{ this.agr = new AntHillGraphicalRunner( this ); }
			this.agr.setActive( true );
			Thread runner = new Thread( this.agr );
			runner.start();
		} else if (source.equals(this.stopButton)) {
			if (this.agr != null) { this.agr.setActive( false ); }
		}
	}

	public AntHillGraphicalWorld2DCaseJPanel getWcInfosPanel() 
		{ return this.wcInfosPanel; }

	public AntHillGraphicalJScroll<World2DCase> getLocationJScroll() 
		{ return this.locationsJScroll; }

	public AntHillGraphicalJScroll<Organism> getOrganismJScroll() 
		{ return this.organismsJScroll; }

}

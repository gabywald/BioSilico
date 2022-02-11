package gabywald.biosilico.anthill.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JPanel panel1 = new JPanel();
		tabbedPane.addTab("Organism Kit", null, panel1, "Does nothing");
		// tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);
		
		JPanel panel2 = new JPanel();
		tabbedPane.addTab("Genetic Kit", null, panel2, "Does nothing");
		// tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);
		
		JPanel panel3 = new JPanel();
		tabbedPane.addTab("Gene Creator", null, panel3, "Does nothing");
		// tabbedPane.setMnemonicAt(3, KeyEvent.VK_3);
		
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
		
		// TODO : JTextField to be extracted and controlled (cannot be changed by user ! )
		// TODO : actionPerformed on OneStep Button
		// TODO : actionPerformed on Stop Button
		// TODO : actionPerformed on Start Button
		
		// TODO : interact and show data about elements in the two (2) lists above !!
		
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
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		System.out.println( source );
		
		// TODO actions for Buttons : OneStep, Start, Stop
		
		if (source.equals(this.oneStepButton)) { 
			this.localModel.oneStep();
			this.stepsTextField.setText( this.localModel.getStepsCounter() + "" );
		}
		
		else if (source.equals(this.locationsJScroll)) {
			// TODO chedck specific listener to activate here !!
			if (this.locationsJScroll.getSelectedIndex() > 0) {
				
				System.out.println( "locationJScroll : " + this.locationsJScroll.getSelectedIndex() );
				
				this.wcInfosPanel.setEnabled( true );
			} else { 
				this.wcInfosPanel.setEnabled( false );
				this.wcInfosPanel.emptyInfos();
			}
		}
	}
	
	
}

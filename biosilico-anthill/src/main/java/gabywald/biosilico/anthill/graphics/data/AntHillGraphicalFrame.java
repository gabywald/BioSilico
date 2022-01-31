package gabywald.biosilico.anthill.graphics.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartPanel;

import gabywald.global.view.graph.GenericJFrame;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
@SuppressWarnings("serial")
public class AntHillGraphicalFrame	extends GenericJFrame 
									implements ActionListener {
	/** Unique instance of this view. */
	private static AntHillGraphicalFrame instance = null;
	/** Panel for plot evoltuion of Chemicals... */
	private ChartPanel cPanel = null;
	
	/**
	 * To get the current instance of graphical view. 
	 * @return (AntHillGraphicalFrame)
	 */
	public static AntHillGraphicalFrame getInstance() {
		if (AntHillGraphicalFrame.instance == null) {
			AntHillGraphicalFrame.instance = new AntHillGraphicalFrame();
		}
		return AntHillGraphicalFrame.instance;
	}
	
	private AntHillGraphicalFrame() {
		
		// TODO complete view !
		// ***** CenterPanel : ChartPanel
		// ***** WestPanel : Right => JList of Organism, 'JMap' of WorldCase, selection(s), controls (stop / start
		// ***** NorthPanel : Some Statistics about World / WorldCase
		
		this.setTitle("AntHill Graphical Frame");
		this.setVisible(true);
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
	public void enableWesternPanel(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableEasternPanel(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableNorthernPanel(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableSouthPanel(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableCenterPanel(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}

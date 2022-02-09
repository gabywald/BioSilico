package gabywald.biosilico.anthill.data;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * This class defines some built-in functions to build build graphical visualisation. 
 * @author Gabriel Chandesris (2022)
 */
public class DataCollector {
	
	private DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();
	
	private String	titleLabel			= null,
					categoryAxisLabel	= null, 
					valueAxisLabel		= null;
	
	private JFrame frame		= null;
	
	private ChartFrame cFrame	= null;
	
	/**
	 * 
	 * @param title Title of the graphical output. 
	 * @param category Title of category of Data. 
	 * @param value Title of the value of Data. 
	 */
	public DataCollector(String title, String category, String value) {
		this.titleLabel			= title;
		this.categoryAxisLabel	= category;
		this.valueAxisLabel		= value;
	}
	
	/**
	 * To add elementary data line. 
	 * @param value
	 * @param row
	 * @param col
	 */
	public void addValue(int value, String row, String col) {
		this.lineChartDataset.addValue(value, row, col);
	}
	
	private JFreeChart generate() {
		JFreeChart lineChartObject = ChartFactory.createLineChart(
				this.titleLabel,
				this.categoryAxisLabel,
				this.valueAxisLabel,
				this.lineChartDataset, 
				PlotOrientation.VERTICAL,
				true, true, false);
		return lineChartObject;
	}
	
	public ChartPanel generateChartPanel() {
		return new ChartPanel(this.generate());
	}
	
	
	/**
	 * 
	 * @param width Width of the image. 
	 * @param height Height of the image. 
	 * @param path2file Path and name of the image to generate (with '.jpeg' | '.jpg' | '.png' extension).  
	 */
	public void buildImage(int width, int height, String path2file) {
		JFreeChart lineChartObject = this.generate();

		File lineChartFile = new File( path2file ); 
		try {
			if (path2file.endsWith(".png")) {
				ChartUtils.saveChartAsPNG ( lineChartFile, lineChartObject, width, height );
			} else if ( (path2file.endsWith(".jpg")) || (path2file.endsWith(".jpeg")) ){
				ChartUtils.saveChartAsJPEG( lineChartFile, lineChartObject, width, height );
			} else {
				throw new IOException("Unknown / unsupported export format ! [" + path2file + "]"); 
			}
		} catch (IOException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	
	/**
	 * Create image as 1024 x 768 px. 
	 * @param path2file Path and name of the image to generate (with '.jpeg' | '.jpg' | '.png' extension). 
	 */
	public void buildImage(String path2file) {
		this.buildImage(1024, 768, path2file);
	}
	
	public void showJFrameWithChartPanel() {
		if (this.frame == null) {
			this.frame = new JFrame();
		}

		this.frame.setSize(1024, 768);
		
		this.frame.add(new ChartPanel(this.generate()));
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}
	
	public void showChartFrame() {
		if (this.cFrame == null) {
			this.cFrame = new ChartFrame("ChartFrame", this.generate());
		}

		this.cFrame.setSize(1024, 768);
		
		this.cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.cFrame.setVisible(true);
	}

	public String getTitle() { return this.titleLabel; }
	
}

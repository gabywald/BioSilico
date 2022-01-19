package gabywald.biosilico.anthill.computations;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 * @author Gabriel Chandesris (2022)
 */
public class DataCollector {
	
	private DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();
	
	private String	titleLabel			= null,
					categoryAxisLabel	= null, 
					valueAxisLabel		= null;
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
	
	/**
	 * 
	 * @param width Width of the image. 
	 * @param height Height of the image. 
	 * @param path2file Path and name of the image to generate (with '.jpeg' | '.jpg' | '.png' extension).  
	 */
	public void buildImage(int width, int height, String path2file) {
		JFreeChart lineChartObject = ChartFactory.createLineChart(
				this.titleLabel,
				this.categoryAxisLabel,
				this.valueAxisLabel,
				this.lineChartDataset, 
				PlotOrientation.VERTICAL,
				true, true, false);

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
	 * Create image as 640x480px. 
	 * @param path2file Path and name of the image to generate (with '.jpeg' | '.jpg' | '.png' extension). 
	 */
	public void buildImage(String path2file) {
		this.buildImage(640, 480, path2file);
	}
	
}

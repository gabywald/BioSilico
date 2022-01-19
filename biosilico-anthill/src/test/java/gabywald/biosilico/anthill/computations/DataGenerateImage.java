package gabywald.biosilico.anthill.computations;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DataGenerateImage {

	public static void main(String[] args) {

		// https://www.tutorialspoint.com/jfreechart/jfreechart_xy_chart.htm
		final XYSeries firefox = new XYSeries( "Firefox" );
		firefox.add( 1.0 , 1.0 );
		firefox.add( 2.0 , 4.0 );
		firefox.add( 3.0 , 3.0 );

		final XYSeries chrome = new XYSeries( "Chrome" );
		chrome.add( 1.0 , 4.0 );
		chrome.add( 2.0 , 5.0 );
		chrome.add( 3.0 , 6.0 );

		final XYSeries iexplorer = new XYSeries( "InternetExplorer" );
		iexplorer.add( 3.0 , 4.0 );
		iexplorer.add( 4.0 , 5.0 );
		iexplorer.add( 5.0 , 4.0 );

		final XYSeriesCollection dataset = new XYSeriesCollection( );
		dataset.addSeries( firefox );
		dataset.addSeries( chrome );
		dataset.addSeries( iexplorer );

		JFreeChart xylineChart = ChartFactory.createXYLineChart(
				"Browser usage statastics", 
				"Category",
				"Score", 
				dataset,
				PlotOrientation.VERTICAL, 
				true, true, false);

		int width = 640;   /* Width of the image */
		int height = 480;  /* Height of the image */ 
		File XYChart = new File( "src/test/resources/XYLineChart.jpeg" ); 
		try {
			ChartUtils.saveChartAsJPEG( XYChart, xylineChart, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

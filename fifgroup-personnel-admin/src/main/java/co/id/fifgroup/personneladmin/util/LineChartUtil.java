package co.id.fifgroup.personneladmin.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import co.id.fifgroup.personneladmin.constant.PerformanceRating;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.zkoss.image.AImage;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.personneladmin.dto.PerformanceReviewDTO;

public class LineChartUtil {

	public static CategoryDataset createDataset(List<PerformanceReviewDTO> listPerformance) {
		final String series = "Performance";
		
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (PerformanceReviewDTO pr : listPerformance) {
			dataset.addValue(PerformanceRating.getValueByCode(pr.getPerformanceRating()), 
					series, FormatDateUI.formatDate(pr.getReviewDate()));
		}
//		dataset.addValue(1, series, "date 1");		
//		dataset.addValue(2, series, "date 2");
//		dataset.addValue(1, series, "date 3");
//		dataset.addValue(4, series, "date 4");
//		dataset.addValue(3, series, "date 5");
//		dataset.addValue(5, series, "date 6");
		return dataset;
	}
	
	private static JFreeChart createChart(final CategoryDataset dataset) {
		final JFreeChart chart = ChartFactory.createLineChart(
				"Performance Review", 
				"Periode", 
				"Performance Rating", 
				dataset, 
				PlotOrientation.VERTICAL, 
				false, 
				true, 
				false);
		
		chart.setBackgroundPaint(Color.white);
		
		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);
		
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		
		final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		
		renderer.setSeriesStroke(0, new BasicStroke());
		
		return chart;
	}
	
	public static AImage getByteChart(List<PerformanceReviewDTO> listPerformance) throws IOException {
		JFreeChart chart = createChart(createDataset(listPerformance));
		BufferedImage bi = chart.createBufferedImage(700, 300, BufferedImage.TRANSLUCENT, null);
		byte[] bytes = EncoderUtil.encode(bi, ImageFormat.PNG, true);
		return new AImage("Performance Chart", bytes); 
	}
	
}

/**
 * 
 */
package com.freescale.iastate.hvac.energy;

import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * @author Namara
 * 
 */
public class ChartSettings {

	ChartSettings() {
	}

	public void setTextSize(XYMultipleSeriesRenderer renderer,
			int axisTitleSize, int chartTitleSize, int LabelsSize) {
		renderer.setAxisTitleTextSize(axisTitleSize);
		renderer.setChartTitleTextSize(chartTitleSize);
		renderer.setLabelsTextSize(LabelsSize);
	}

	public void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	/**
	 * @param renderer
	 */
	public void setAutoSpacing(XYMultipleSeriesRenderer renderer) {
		double barSpacing = .05;
		int[] margins = { 25, 50, 25, 15 };

		renderer.setMargins(margins);
		renderer.setBarSpacing(barSpacing);
	}

}

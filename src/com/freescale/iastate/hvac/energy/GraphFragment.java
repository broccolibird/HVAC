/**
 * 
 */
package com.freescale.iastate.hvac.energy;

/**
 * @author Namara
 *
 */

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.freescale.iastate.hvac.R;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author Namara
 * 
 */
public class GraphFragment extends Fragment {
	final int MONTH = 30;
	final int WEEK = 7;
	final int DAY = 24;
	final int MAXKWH = 100;
	
	private String seriesTitle;
	private int numDataPoints;
	private int barColor;
	private int axesColor;
	private int fontColor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.graphs_fragment, container, false);
		
		seriesTitle = "";
		numDataPoints = WEEK;
		barColor = Color.RED;
		axesColor = Color.RED;
		fontColor = Color.RED;

		GraphicalView graph = ChartFactory.getBarChartView(getActivity(),
				getGeneratedDataset(numDataPoints), getChartRenderer(), Type.DEFAULT);
		LinearLayout ll = (LinearLayout) view
				.findViewById(R.id.first_fragment_root);

		ll.addView(graph, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

		return view;
	}

	/*
	 * Generates an example data set
	 * Currently from 65+/- 17
	 */
	private XYMultipleSeriesDataset getGeneratedDataset(int numDataPoints) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int floor = 65;
		final int variation = 17;

		XYSeries series = new XYSeries(seriesTitle);
		for (int k = 0; k < numDataPoints; k++) {
			Double rando = Math.random() * variation;
			int kWattHr = (int) (Math.floor(rando) + floor);
			series.add(k, kWattHr);
		}
		dataset.addSeries(series);

		return dataset;
	}
	
	private XYMultipleSeriesRenderer getChartRenderer() {
		ChartSettings s = new ChartSettings();
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		//TODO CHANGE DAYS SO THAT IS IS RESPECTIVE TO MONTH OR NUMDATAPOINTS
		s.setChartSettings(renderer, seriesTitle, "Days", "kWh", 0, numDataPoints, 0, MAXKWH, axesColor, fontColor);		
		s.setTextSize(renderer, 16, 20, 15, 15);
		s.setAutoSpacing(renderer);
		
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(barColor);
		renderer.addSeriesRenderer(r);
		return renderer;
	}

}
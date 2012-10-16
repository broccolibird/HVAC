/**
 * 
 */
package com.freescale.iastate.hvac.energy;

/**
 * @author Namara
 *
 */
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		View view = inflater
				.inflate(R.layout.graphs_fragment, container, false);
		
		GraphicalView graph = ChartFactory.getBarChartView(getActivity(), getDemoDataset(), getDemoRenderer(), Type.DEFAULT);
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.first_fragment_root);
		
	    ll.addView(graph, new LayoutParams(LayoutParams.MATCH_PARENT,
	              LayoutParams.MATCH_PARENT));
        
        return view;
	}
	
	private XYMultipleSeriesDataset getDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 30;
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries("Demo series " + (i + 1));
			for (int k = 0; k < nr; k++) {
				
				Double rando = Math.random() * 17;
				int kWattHr = (int) (Math.floor(rando) + 65);
				
				series.add(k, kWattHr);
			}
			dataset.addSeries(series);
		}
		
		return dataset;
	}

	private XYMultipleSeriesRenderer getDemoRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		double[] range = {0, 30, 0, 100};
		renderer.setRange(range);
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		//renderer.setMargins(new int[] { 20, 30, 15, 0 });
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.WHITE);
		renderer.addSeriesRenderer(r);
		r = new XYSeriesRenderer();
		renderer.addSeriesRenderer(r);
		renderer.setAxesColor(Color.DKGRAY);
		renderer.setLabelsColor(Color.LTGRAY);
		return renderer;
	}

}
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
import com.freescale.iastate.hvac.R.id;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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

//		GraphicalView graph = ChartFactory.getLineChartView(getActivity(), getDemoDataset(), getDemoRenderer());
//		GraphicalView graph_view = (GraphicalView) view.findViewById(R.id.frag_graph_view);
//		graph_view = graph;
        //GraphicalView graph = (GraphicalView) view.findViewById(R.id.frag_graph_view);
		
		GraphicalView graph = ChartFactory.getBarChartView(getActivity(), getDemoDataset(), getDemoRenderer(), Type.DEFAULT);
		//LinearLayout ll = (LinearLayout)findViewById(R.id.first_fragment_root);
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.first_fragment_root);
		
	    ll.addView(graph, new LayoutParams(LayoutParams.MATCH_PARENT,
	              LayoutParams.MATCH_PARENT));
        
        return view;
	}
	

	private XYMultipleSeriesDataset getDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 10;
		Random r = new Random();
		for (int i = 0; i < 2; i++) {
			XYSeries series = new XYSeries("Demo series " + (i + 1));
			for (int k = 0; k < nr; k++) {
				series.add(k, 20 + r.nextInt() % 100);
			}
			dataset.addSeries(series);
		}
		return dataset;
	}

	private XYMultipleSeriesRenderer getDemoRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.BLUE);
		r.setPointStyle(PointStyle.SQUARE);
		r.setFillBelowLine(true);
		r.setFillBelowLineColor(Color.WHITE);
		r.setFillPoints(true);
		renderer.addSeriesRenderer(r);
		r = new XYSeriesRenderer();
		r.setPointStyle(PointStyle.CIRCLE);
		r.setColor(Color.GREEN);
		r.setFillPoints(true);
		renderer.addSeriesRenderer(r);
		renderer.setAxesColor(Color.DKGRAY);
		renderer.setLabelsColor(Color.LTGRAY);
		return renderer;
	}

}
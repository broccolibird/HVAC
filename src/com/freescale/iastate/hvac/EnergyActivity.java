package com.freescale.iastate.hvac;


import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EnergyActivity extends Activity implements MenuInterface, DisplayInterface {
	private static final int SERIES_NR = 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.energy);

		//Finds view, then uses DisplayInterface to change background color
		View view = findViewById(R.id.energy);
		ColorDisplay background_color = new ColorDisplay();
		background_color.setBackgroundColor(view, getBaseContext());

		//GraphicalView graph = ChartFactory.getChartView(this, getDemoDataset(), getDemoRenderer());
		//GraphicalView graph = ChartFactory.getBarChartView(this, getDemoDataset(), getDemoRenderer(), Type.DEFAULT);
		//LinearLayout ll = (LinearLayout)findViewById(R.id.chart);
		
	    //ll.addView(graph, new LayoutParams(LayoutParams.MATCH_PARENT,
	              //LayoutParams.MATCH_PARENT));
				
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Toast.makeText(getBaseContext(), "Toast", Toast.LENGTH_LONG).show();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return rootIntent.onOptionsItemSelected(this, item);
	}

	public void dayGraphClick(View v) {

		Toast.makeText(getBaseContext(), "Day",
				Toast.LENGTH_LONG).show();

	}

	public void weekGraphClick(View v) {

		Toast.makeText(getBaseContext(), "week",
				Toast.LENGTH_LONG).show();
	}

	public void monthGraphClick(View v) {

		Toast.makeText(getBaseContext(), "Month",
				Toast.LENGTH_LONG).show();

	}

	public void yearGraphClick(View v) {
		Toast.makeText(getBaseContext(), "year",
				Toast.LENGTH_LONG).show();
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
package com.freescale.iastate.hvac;


import java.util.Random;

import com.freescale.iastate.hvac.DisplayInterface.ColorDisplay;

//import org.achartengine.model.XYMultipleSeriesDataset;
//import org.achartengine.model.XYSeries;

import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
//
//import org.achartengine.ChartFactory;
//import org.achartengine.chart.PointStyle;
//import org.achartengine.chart.TimeChart;
//import org.achartengine.chart.BarChart.Type;
//import org.achartengine.model.CategorySeries;
//import org.achartengine.model.TimeSeries;
//import org.achartengine.renderer.SimpleSeriesRenderer;
//import org.achartengine.renderer.XYMultipleSeriesRenderer;
//import org.achartengine.renderer.XYSeriesRenderer;

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
	

//	private XYMultipleSeriesDataset getDemoDataset() {
//		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//		final int nr = 10;
//		Random r = new Random();
//		for (int i = 0; i < SERIES_NR; i++) {
//			XYSeries series = new XYSeries("Demo series " + (i + 1));
//			for (int k = 0; k < nr; k++) {
//				series.add(k, 20 + r.nextInt() % 100);
//			}
//			dataset.addSeries(series);
//		}
//		return dataset;
//	}
}
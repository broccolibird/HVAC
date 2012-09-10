package com.freescale.iastate.hvac;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.freescale.iastate.hvac.weather.JSONParser;
import com.freescale.iastate.hvac.weather.WeatherFragment;
import com.freescale.iastate.hvac.weather.WeatherObject;

public class WeatherActivity extends Activity implements MenuInterface {
	final char degree = 0x00B0;
	WeatherObject[] dayArray = new WeatherObject[12];

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// this sets the help string for the current activity.
		// copy paste
		rootIntent.setHelpText(getText(R.string.weather_help));

		ForecastWeatherTask task = new ForecastWeatherTask();
		task.execute();

	}

	private class ForecastWeatherTask extends AsyncTask<Void, Void, String> {

		String day1Text, day2Text, day3Text;
		Drawable day1Image, day2Image, day3Image;

		protected String doInBackground(Void... params) {
			try {

				SharedPreferences settings = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				String zip = settings.getString("zip_code_key", "50014");

				JSONParser parser = new JSONParser();
				JSONParser.zip = zip;
				JSONObject json = parser.getJSONFromUrl(JSONParser.threedayURL);
				String readable = json.toString(5);

				JSONObject forecast = json.getJSONObject("forecast");
				JSONObject simpleForecast = forecast
						.getJSONObject("simpleforecast");
				JSONArray forecastDay = null;
				forecastDay = simpleForecast.getJSONArray("forecastday");

				
				for (int i = 0; i < forecastDay.length(); i++) {
					JSONObject day = forecastDay.getJSONObject(i);
					JSONObject date = day.getJSONObject("date");
					JSONObject high = day.getJSONObject("high");
					JSONObject low = day.getJSONObject("low");
					// String readable2 = day.toString(5);
					dayArray[i] = new WeatherObject();
					dayArray[i].weekday = date.getString("weekday");
					dayArray[i].high = high.getString("fahrenheit");
					dayArray[i].low = low.getString("fahrenheit");
					dayArray[i].conditions = day.getString("conditions");
					dayArray[i].image = weatherImage(day.getString("conditions"));
				}

				return null;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(String result) {

			
			WeatherFragment day1 = (WeatherFragment) getFragmentManager().findFragmentById(
					R.id.day1fragment);
			day1.updateWeather(dayArray[0]);
			
			WeatherFragment frag2 = (WeatherFragment) getFragmentManager().findFragmentById(
					R.id.day2fragment);
			frag2.updateWeather(dayArray[1]);		

			WeatherFragment frag3 = (WeatherFragment) getFragmentManager().findFragmentById(
					R.id.day3fragment);
			frag3.updateWeather(dayArray[2]);

		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return rootIntent.onOptionsItemSelected(this, item);
	}

	public Drawable weatherImage(String condition) {
		Resources res = getResources();
		Drawable conditionDrawable = null;
		if (condition.contains("rain") || condition.contains("Rain")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_rain);
		}
		if (condition.contains("sun") || condition.contains("Sun")
				|| condition.contains("clear") || condition.contains("Clear")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_clear);
		}
		if (condition.contains("storm") || condition.contains("Storm")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_storm);
		}
		if (condition.contains("cloudy") || condition.contains("Cloudy")
				|| condition.contains("Overcast")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_cloudy);
		}
		if (condition.contains("snow") || condition.contains("Snow")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_snow);
		}
		return conditionDrawable;

	}

}

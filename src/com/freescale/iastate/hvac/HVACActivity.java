package com.freescale.iastate.hvac;

import java.net.URL;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.freescale.iastate.hvac.weather.*;
import com.freescale.iastate.hvac.*;

import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
//import android.widget.ImageView;
import android.widget.TextView;

public class HVACActivity extends Activity implements MenuInterface {
	final char degree = 0x00B0;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		SharedPreferences.Editor edit = settings.edit();
		edit.putInt("brightness_key", 100);
		edit.putInt("temp_key", 70);
		edit.commit();

		int temp = settings.getInt("temp_key", 0);
		String displayTemp = "" + temp;
		TextView textView = (TextView) findViewById(R.id.currentSysTemp);
		textView.setText(displayTemp);

		// execute background weather gathering
		CurrentWeatherTask task = new CurrentWeatherTask();
		task.execute();

		// refresh weather data when image button pressed
		ImageButton refreshWeather = (ImageButton) findViewById(R.id.WeatherRefreshButton);
		refreshWeather.setOnClickListener(refreshWeatherListener);

		((RadioGroup) findViewById(R.id.fanGroup)).setOnCheckedChangeListener(ToggleListener);
		((RadioGroup) findViewById(R.id.modeGroup)).setOnCheckedChangeListener(ToggleListener);
		// buttons for fan
		Button fanOn = (Button) findViewById(R.id.sys_fan_on);
		Button fanOff = (Button) findViewById(R.id.sys_fan_off);
		Button fanAuto = (Button) findViewById(R.id.sys_fan_auto);

		// buttons for system mode
		Button sysHeat = (Button) findViewById(R.id.sys_mode_heat);
		Button sysCool = (Button) findViewById(R.id.sys_mode_cool);
		Button sysFan = (Button) findViewById(R.id.sys_mode_fan);

		// buttons for temp adjustment
		Button adjustUp = (Button) findViewById(R.id.tempUp);
		Button adjustDown = (Button) findViewById(R.id.tempDown);

		fanOn.setOnClickListener(fanOnListener);
		fanOff.setOnClickListener(fanOffListener);
		fanAuto.setOnClickListener(fanAutoListener);
		sysHeat.setOnClickListener(sysHeatListener);
		sysCool.setOnClickListener(sysCoolListener);
		sysFan.setOnClickListener(sysFanListener);
		adjustUp.setOnClickListener(tempUpListener);
		adjustDown.setOnClickListener(tempDownListener);

		// this sets the help string for the current activity.
		// copy paste
		rootIntent.setHelpText(getText(R.string.main_help));

		initIntents();

	}

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());

		int temp = settings.getInt("temp_key", 0);
		String displayTemp = "" + temp;
		TextView textView = (TextView) findViewById(R.id.currentSysTemp);
		textView.setText(displayTemp);

	}

	
	static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };
	
	private class CurrentWeatherTask extends AsyncTask<Void, Void, String> {
		ImageButton currentWeatherImageView = (ImageButton) findViewById(R.id.WeatherRefreshButton);
		TextView currentWeatherTextView = (TextView) findViewById(R.id.WeatherText);
		String currentWeatherText;
		Drawable currentWeatherImage;

		protected String doInBackground(Void... params) {
			try {
				SharedPreferences settings = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				String zip = settings.getString("zip_code_key", "50014");

				JSONParser parser = new JSONParser();
				JSONParser.zip = zip;
				JSONObject json = parser
						.getJSONFromUrl(JSONParser.conditionsURL);

				//String readable = json.toString(5);

				JSONObject currentObservation = json
						.getJSONObject("current_observation");
				String weather = currentObservation.getString("weather");
				String tempString = currentObservation
						.getString("temperature_string");
				String windString = currentObservation.getString("wind_string");
				String relativeHumidity = currentObservation
						.getString("relative_humidity");
				String obsTime = currentObservation
						.getString("observation_time");
				currentWeatherText = "Weather for: " + zip + "\n" + weather
						+ "\n" + tempString + "\n" + windString + "\n"
						+ relativeHumidity + "\n" + obsTime;
				currentWeatherImage = weatherImage(currentWeatherText);

				return currentWeatherText;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(String result) {
			currentWeatherImageView.setImageDrawable(currentWeatherImage);
			currentWeatherTextView.setText(currentWeatherText);
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	private OnClickListener refreshWeatherListener = new OnClickListener() {
		public void onClick(View v) {
			CurrentWeatherTask task = new CurrentWeatherTask();
			task.execute();
		};
	};
	private OnClickListener fanOnListener = new OnClickListener() {
		public void onClick(View v) {
		    ((RadioGroup)v.getParent()).check(v.getId());
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("fan_key", "Fan: On");
			edit.commit();

			String output = settings.getString("fan_key", "Fan key not found");
			Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
		}
	};
	private OnClickListener fanOffListener = new OnClickListener() {
		public void onClick(View v) {
		    ((RadioGroup)v.getParent()).check(v.getId());
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("fan_key", "Fan: Off");
			edit.commit();

			String output = settings.getString("fan_key", "Fan key not found");
			Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
		}
	};
	private OnClickListener fanAutoListener = new OnClickListener() {
		public void onClick(View v) {
		    ((RadioGroup)v.getParent()).check(v.getId());
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("fan_key", "Fan: Auto");
			edit.commit();

			String output = settings.getString("fan_key", "Fan key not found");
			Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
		};
	};

	private OnClickListener sysHeatListener = new OnClickListener() {
		public void onClick(View v) {
		    ((RadioGroup)v.getParent()).check(v.getId());

			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("sys_key", "System: Heating");
			edit.commit();

			String output = settings.getString("sys_key", "Fan key not found");
			Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
		};
	};
	private OnClickListener sysCoolListener = new OnClickListener() {
		public void onClick(View v) {
		    ((RadioGroup)v.getParent()).check(v.getId());
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("sys_key", "System: Air Conditioning");
			edit.commit();

			String output = settings.getString("sys_key", "Fan key not found");
			Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
		};
	};
	private OnClickListener sysFanListener = new OnClickListener() {
		public void onClick(View v) {
		    ((RadioGroup)v.getParent()).check(v.getId());
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor edit = settings.edit();
			edit.putString("sys_key", "System: Fan Only");
			edit.commit();

			String output = settings.getString("sys_key", "Fan key not found");
			Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
		};
	};
	private OnClickListener tempUpListener = new OnClickListener() {
		public void onClick(View v) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			int temp = settings.getInt("temp_key", 0);
			temp++;

			SharedPreferences.Editor edit = settings.edit();
			edit.putInt("temp_key", temp);
			edit.commit();

			String displayTemp = "" + temp;
			TextView textView = (TextView) findViewById(R.id.currentSysTemp);
			textView.setText(displayTemp);
		};
	};
	private OnClickListener tempDownListener = new OnClickListener() {
		public void onClick(View v) {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(getBaseContext());
			int temp = settings.getInt("temp_key", 0);
			temp--;

			SharedPreferences.Editor edit = settings.edit();
			edit.putInt("temp_key", temp);
			edit.commit();

			String displayTemp = "" + temp;
			TextView textView = (TextView) findViewById(R.id.currentSysTemp);
			textView.setText(displayTemp);
		};
	};

	// All this stuff comes from MenuInterface
	public void initIntents() {
		rootIntent.homeIntent = new Intent(this, HVACActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.calendarIntent = new Intent(this, CalendarActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.weatherIntent = new Intent(this, WeatherActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.settingsIntent = new Intent(this, SettingsActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		rootIntent.energyIntent = new Intent(this, EnergyActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}

	@Override
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
		if (condition.contains("storm")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_storm);
		}

		if (condition.contains("cloudy") || condition.contains("Cloudy")
				|| condition.contains("Overcast") || condition.contains("Haze") || condition.contains("haze")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_cloudy);
		}
		if (condition.contains("snow") || condition.contains("Snow")) {
			conditionDrawable = res.getDrawable(R.drawable.weather_snow);
		}
		if (condition.contains("Partly")){
			conditionDrawable = res.getDrawable(R.drawable.weather_partlycloudy);
		}
		return conditionDrawable;

	}

	
}


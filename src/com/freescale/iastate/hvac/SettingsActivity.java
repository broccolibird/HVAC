package com.freescale.iastate.hvac;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;

import com.freescale.iastate.hvac.R;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements
		MenuInterface, DisplayInterface {

	TextView myListPref;
	AlertDialog brightAlert;
	int brightness;
	SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		addPreferencesFromResource(R.xml.preferences);
		settings = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		brightness = settings.getInt("brightness_key", 0);

		// Uncomment to display settings
		// getSettings();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences prefs,
					String key) {
				ListView preferencesView = getListView();
				View view = preferencesView.getRootView();
				ColorDisplay background_color = new ColorDisplay();
				background_color.setBackgroundColor(view, getBaseContext());
			}
		};
		prefs.registerOnSharedPreferenceChangeListener(listener);

		// Finds view, then uses DisplayInterface to change background color
		ListView preferencesView = getListView();
		View view = preferencesView.getRootView();
		ColorDisplay background_color = new ColorDisplay();
		background_color.setBackgroundColor(view, getBaseContext());

		// Fix using non-deprecated method
		Preference brightnessPref = (Preference) findPreference("b_key");
		brightnessPref
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						changeBrightness();
						return true;
					}
				});
	}

	private void changeBrightness() {
		final AlertDialog.Builder brightnessBuilder = new AlertDialog.Builder(
				this)
				.setTitle("Screen Brightness")
				.setMessage(
						"Adjust Brightness \nLevel: "
								+ settings.getInt("brightness_key", 0))
				.setPositiveButton("+", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences.Editor edit = settings.edit();

						brightness += 25;
						edit = settings.edit();
						edit.putInt("brightness_key", brightness);
						edit.commit();

						// Should change brightness on Archos
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.screenBrightness = brightness;
						getWindow().setAttributes(lp);
					}
				})
				.setNeutralButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Include something if needed later
							}
						})
				.setNegativeButton("-", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences.Editor edit = settings.edit();

						brightness -= 25;
						edit = settings.edit();
						edit.putInt("brightness_key", brightness);
						edit.commit();

						// Should change brightness on Archos
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.screenBrightness = brightness;
						getWindow().setAttributes(lp);
					}
				});

		brightAlert = brightnessBuilder.create();
		brightAlert.show();
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
}
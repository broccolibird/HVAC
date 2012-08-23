package com.freescale.iastate.hvac;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;

import com.freescale.iastate.hvac.R;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity implements MenuInterface{
	
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
		settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        brightness = settings.getInt("brightness_key", 0);
	    
        //Uncomment to display settings
	    //getSettings();
        
	    Preference brightnessPref = (Preference) findPreference("b_key");
	    brightnessPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				changeBrightness();
				return true;
			}
		});
	}

	private void changeBrightness(){
		final AlertDialog.Builder brightnessBuilder = new AlertDialog.Builder(this)
		.setTitle("Screen Brightness")
		.setMessage("Adjust Brightness \nLevel: " + settings.getInt("brightness_key", 0))
		.setPositiveButton("+", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferences.Editor edit = settings.edit();
				
				brightness += 25;
				edit = settings.edit();
		    	edit.putInt("brightness_key", brightness);
		    	edit.commit();

		    	//Should change brightness on Archos
				WindowManager.LayoutParams lp = getWindow().getAttributes(); 
				lp.screenBrightness = brightness; 
				getWindow().setAttributes(lp); 
			}
		}).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//Include something if needed later
			}
		}).setNegativeButton("-", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferences.Editor edit = settings.edit();
			
				brightness -= 25;
				edit = settings.edit();
				edit.putInt("brightness_key", brightness);
		    	edit.commit();
			
		    	//Should change brightness on Archos
				WindowManager.LayoutParams lp = getWindow().getAttributes(); 
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
	
	private void getSettings(){
		settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

	    String output = settings.getString("button_size_key", "Button Size not found");
        Toast.makeText(getBaseContext(), "Button Size: " + output, Toast.LENGTH_LONG).show();
	    output = settings.getString("color_scheme_key", "Color scheme not found");
        Toast.makeText(getBaseContext(),"Color Scheme: " +  output, Toast.LENGTH_LONG).show();
	    output = settings.getString("time_zone_key", "Time zone not found");
        Toast.makeText(getBaseContext(), "Timezone: " + output, Toast.LENGTH_LONG).show();
        
        int out = settings.getInt("brightness_key", 0);
	    output = "Brightness: " + out;
        Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG).show();
	}
}
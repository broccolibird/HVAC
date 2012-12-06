package com.freescale.iastate.hvac;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public interface MenuInterface {
	
	RootIntent rootIntent = new RootIntent();
	
	public boolean onCreateOptionsMenu(Menu menu);
	public boolean onOptionsItemSelected(MenuItem item);
	
	public static class RootIntent {
		public Intent calendarIntent;
		public Intent settingsIntent;
		public Intent weatherIntent;
		public Intent homeIntent;
		public Intent energyIntent;

		public RootIntent() {
			
		}
		public boolean onOptionsItemSelected( Activity act, MenuItem item) {

			String itemID = (String) item.getTitle();
			
			if(itemID.compareTo("HVAC") == 0){
				act.startActivity(rootIntent.homeIntent);
				return true;
			} else if(itemID.compareTo("Help") == 0){
				AlertDialog dialog = new AlertDialog.Builder(act).create();
				dialog.setTitle(helpTitle);
				dialog.setMessage(helpText);
								
				dialog.show();
				return true;
			} else if(itemID.compareTo("Schedule") == 0){
				act.startActivity(rootIntent.calendarIntent);
				return true;
			} else if(itemID.compareTo("Weather") == 0){
				act.startActivity(rootIntent.weatherIntent);
				return true;
			} else if(itemID.compareTo("Settings") == 0){
				act.startActivity(rootIntent.settingsIntent);
				return true;
			} else if(itemID.compareTo("Energy Efficiency") == 0){
				act.startActivity(rootIntent.energyIntent);
				return true;
			} else {
				return false;
			}
		}
		
		private String helpText;
		private String helpTitle;
		
		public void setHelpText(CharSequence title, CharSequence text) {
			this.helpTitle = (String) title;
			this.helpText = (String) text;
		}
		
		public void initIntents(Activity act) {
			rootIntent.homeIntent = new Intent(act, HVACActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			rootIntent.calendarIntent = new Intent(act, CalendarActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			rootIntent.weatherIntent = new Intent(act, WeatherActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			rootIntent.settingsIntent =  new Intent(act, SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			rootIntent.energyIntent =  new Intent(act, EnergyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		}
	}
}

/**
 * 
 */
package com.freescale.iastate.hvac;

import com.freescale.iastate.hvac.MenuInterface.RootIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;

/**
 * @author Namara
 * 
 */
public interface DisplayInterface {

	final static int HVAC_GREEN = 0xFF0B9D0E;
	final static int HVAC_BLUE = 0xFF116BB4;
	final static int HVAC_RED = 0xFFCD3333;

	public static class ColorDisplay {
		public ColorDisplay() {

		}

		public void setBackgroundColor(View view, Context context) {

			View root = view.getRootView();
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			String color_string = settings.getString("color_scheme_key",
					"Black");

			if ((color_string.compareTo("Green") == 0)) {
				root.setBackgroundColor(HVAC_GREEN);
			} else if (color_string.compareTo("Blue") == 0) {
				root.setBackgroundColor(HVAC_BLUE);
			} else if (color_string.compareTo("Gray") == 0) {
				root.setBackgroundColor(Color.GRAY);
			} else if (color_string.compareTo("Red") == 0) {
				root.setBackgroundColor(HVAC_RED);
			} else {
				root.setBackgroundColor(Color.BLACK);
			}
		}
	}

}

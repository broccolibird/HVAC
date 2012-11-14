package com.freescale.iastate.hvac.weather;

import com.freescale.iastate.hvac.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;





public class WeatherFragment extends Fragment {
	
	private Activity activity;
	private Context context;
	private String forecastText;
	
	private OnClickListener detailsListener = new OnClickListener() {
		public void onClick(View v) {
			String output = forecastText;
			Toast.makeText(getActivity().getApplicationContext(), output, Toast.LENGTH_LONG).show();
		}
	};
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.weatherfragment, container, false);
		
	}
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		activity = getActivity();
		
		
		
	}
	
	public void updateWeather(WeatherObject obj){
		TextView weekday = (TextView) getView().findViewById(R.id.weekday);
		ImageButton image = (ImageButton) getView().findViewById(R.id.dayimage);
		image.setOnClickListener(detailsListener);
		TextView high = (TextView) getView().findViewById(R.id.temphigh);
		TextView low = (TextView) getView().findViewById(R.id.templow);
		TextView conditions = (TextView) getView().findViewById(R.id.condition);
		
		
		
		
		Drawable imageDrawable = obj.image;
		
		weekday.setText(obj.weekday);
		image.setImageDrawable(imageDrawable);
		high.setText(obj.high);
		low.setText(obj.low);
		conditions.setText(obj.conditions);
		forecastText = obj.forecast;
		

	}
}

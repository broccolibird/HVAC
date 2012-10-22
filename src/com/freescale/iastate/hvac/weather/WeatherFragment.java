package com.freescale.iastate.hvac.weather;

import com.freescale.iastate.hvac.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;





public class WeatherFragment extends Fragment {
	
	private Activity activity;
	private Context context;
	
	
	
	
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
		ImageView image = (ImageView) getView().findViewById(R.id.dayimage);
		TextView high = (TextView) getView().findViewById(R.id.temphigh);
		TextView low = (TextView) getView().findViewById(R.id.templow);
		TextView conditions = (TextView) getView().findViewById(R.id.condition);
		
		Drawable imageDrawable = obj.image;
		
		weekday.setText(obj.weekday);
		image.setImageDrawable(imageDrawable);
		high.setText(obj.high);
		low.setText(obj.low);
		conditions.setText(obj.conditions);
		

	}
}

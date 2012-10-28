package com.freescale.iastate.hvac.calendar;

import java.util.Vector;

import com.freescale.iastate.hvac.R;
import com.freescale.iastate.hvac.util.HVACState;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class StateAdapter extends ArrayAdapter<HVACState> implements EventCommon {

	public Vector<HVACState> views = null;
	
	public StateAdapter(Context context, int layoutResourceId, Vector<HVACState> views) {
		super(context, layoutResourceId, views);
		eventToolbox.SCALE = context.getResources().getDisplayMetrics().density;
		this.setNotifyOnChange(true);
//		this.layoutResourceId = layoutResourceId;
		this.views = views;
	}
	/*
	 * Big fat note: getView() doesn't come with a null convertView after it's been used with a ListView.
	 * Found this on:
	 * 
	 * http://grokbase.com/t/gg/android-developers/11bnqk5sfq/arrayadapter-not-updating-its-listview
	 * 
	 * So inflate the view every time you make a row, and do it from scratch.  Otherwise you'll always
	 * get the last element in your adapter as your convertView, and nothing will update.
	 * 
	 * (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null) {
			LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=lf.inflate(R.layout.calendar_listview_cell, null);
		}
		else { //reference big fat note
			LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=lf.inflate(R.layout.calendar_listview_cell, null);
		}

		TextView tv = (TextView)convertView.findViewById(R.id.calendar_listview_title);
		TextView tvDesc = (TextView)convertView.findViewById(R.id.calendar_listview_description);
		tv.setTextColor(Color.WHITE);
		tv.setText(views.get(position).title);
		tvDesc.setText(views.get(position).description);

		ImageView heatImage = (ImageView)convertView.findViewById(R.id.calendar_heat_image);
		switch(views.get(position).getTemperatureOperation()) {
			case TEMP_HEAT:
				heatImage.setImageResource(R.drawable.campfire);
				break;
			
			case TEMP_COOL:
				heatImage.setImageResource(R.drawable.snowflake);
				break;
				
			case TEMP_OFF:
				heatImage.setImageResource(R.drawable.temp_off);
				break;
				
			default:
				heatImage.setImageResource(R.drawable.blank);
				break;
			
		}
		views.get(position).heatImage = heatImage;
		
		ImageView fanImage = (ImageView)convertView.findViewById(R.id.calendar_fan_image);
		switch(views.get(position).getFanSpeed()) {
			case FAN_MAX:
				fanImage.setImageResource(R.drawable.fan_on);
				break;
		
			case FAN_HIGH:
				fanImage.setImageResource(R.drawable.fan_on);
				break;
				
			case FAN_MED:
				fanImage.setImageResource(R.drawable.fan_on);
				break;
				
			case FAN_SLOW:
				fanImage.setImageResource(R.drawable.fan_on);
				break;
				
			case FAN_AUTO:
				fanImage.setImageResource(R.drawable.fan_auto);
				break;
				
			case FAN_OFF:
				fanImage.setImageResource(R.drawable.fan_off);
				break;
				
			default:
				fanImage.setImageResource(R.drawable.blank);
				break;
		}
		views.get(position).fanImage = fanImage;
		
		ImageView energySaveImage = (ImageView)convertView.findViewById(R.id.calendar_energysave_image);
		switch(views.get(position).getEnergySave()) {
			case SAVE_ON:
				energySaveImage.setImageResource(R.drawable.leaf);
				break;
	
			case SAVE_OFF:
				energySaveImage.setImageResource(R.drawable.blank);
				break;
				
			default:
				energySaveImage.setImageResource(R.drawable.blank);
				break;
		
		}
		views.get(position).energySaveImage = energySaveImage;
			
		return convertView;
	}

}

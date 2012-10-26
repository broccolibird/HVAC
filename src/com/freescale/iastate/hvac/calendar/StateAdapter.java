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
	Context context;
	public int layoutResourceId;
	public Vector<HVACState> views = null;
	
	public StateAdapter(Context context, int layoutResourceId, Vector<HVACState> views) {
		super(context, layoutResourceId, views);
		eventToolbox.SCALE = context.getResources().getDisplayMetrics().density;
		this.setNotifyOnChange(true);
//		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.views = views;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null) {
			LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=lf.inflate(R.layout.calendar_listview_cell, null);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.calendar_listview_title);
		tv.setTextColor(Color.WHITE);
		tv.setText(views.get(position).title);

		ImageView heatImage = (ImageView)convertView.findViewById(R.id.calendar_heat_image);
		switch(views.get(position).getTemperatureOperation()) {
			case TEMP_HEAT:
				heatImage.setImageResource(R.drawable.campfire);
				break;
			
			case TEMP_COOL:
				heatImage.setImageResource(R.drawable.snowflake);
				break;
				
			default:
				heatImage.setImageResource(R.drawable.blank);
				break;
		}
		
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
			
		return convertView;
	}

}

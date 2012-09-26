package com.freescale.iastate.hvac.calendar;

import java.util.Vector;

import com.freescale.iastate.hvac.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class TimeAdapter extends ArrayAdapter<EventWrapper> implements EventCommon {
	Context context;
	int layoutResourceId;
	Vector<EventWrapper> data = null;
	
	public TimeAdapter(Context context, int layoutResourceId, Vector<EventWrapper> data) {
		super(context, layoutResourceId, data);
		eventToolbox.SCALE = context.getResources().getDisplayMetrics().density;
		
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView==null) {
			LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=lf.inflate(R.layout.calendar_weekview_cell, null);
		}
		
		float individualHeight = (data.get(position).getTimeDuration()*(DP_PER_HOUR+1));
	    
		TextView tv = (TextView)convertView;//.findViewById(R.layout.calendar_weekview_cell);
		tv.setAlpha(1);
		if(this.data.get(position).transparent == true) {
			tv.setAlpha(0);
			tv.setClickable(false);
			tv.setHeight(eventToolbox.convertDpToPixels((individualHeight - 1f)));
			return tv;
		} else {
			
		tv.setTextColor(Color.BLACK);
		tv.setText(this.data.get(position).contents);
		tv.setTextSize(eventToolbox.convertDpToPixels((10)));

		//tv.setHeight(eventToolbox.convertDpToPixels((individualHeight - 1f)));
	//	tv.setHeight(eventToolbox.convertDpToPixels(16));
		}
		return convertView;
	}
	public void removeElementAt(int position){
		data.remove(position);
	}
}

package com.freescale.iastate.hvac.calendar;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freescale.iastate.hvac.R;

public class TimeViewAdapter<T> extends ArrayAdapter<T> {
	final int DP_PER_HOUR = 12;
	static float SCALE = 0;
	private T[] objects;
	@SuppressWarnings("unused")
	private List<T> listObjects;
	private HashMap<Integer,DayWrapper> dayList= new HashMap<Integer,DayWrapper>();
	
	public TimeViewAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	public TimeViewAdapter(Context context, int textViewResourceId, HashMap<Integer,DayWrapper> hmw) {
		super(context, textViewResourceId);
		this.dayList = hmw;
		
	}
	
	public TimeViewAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}
	public TimeViewAdapter(Context context, int textViewResourceId, T[] objects) {
		super(context,textViewResourceId,objects);
		this.objects = objects;
		SCALE = context.getResources().getDisplayMetrics().density;
	}
	public TimeViewAdapter(Context context, int resource, int textViewResourceId, T[] objects){
		super(context,resource,textViewResourceId, objects);
		this.objects = objects;
		SCALE = context.getResources().getDisplayMetrics().density;
	}
	public TimeViewAdapter(Context context, int resource, int textViewResourceId, List<T> objects){
		super(context,resource,textViewResourceId,objects);
		this.listObjects = objects;
		SCALE = context.getResources().getDisplayMetrics().density;
	}
	public View getView(int pos, View convertView, ViewGroup parent){
		if(convertView==null) {
			LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=lf.inflate(R.layout.timeview_cell, null);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.timeview_cell);

		tv.setTextColor(Color.BLACK);
		tv.setText(objects[pos].toString());

		tv.setHeight(convertDpToPixels(DP_PER_HOUR));


		return convertView;
	}
	public int convertDpToPixels(int dpLength){
		return (int)((float)dpLength*SCALE + 0.5f);
	}
	public HashMap<Integer,DayWrapper> getDayList() {
		return dayList;
	}
	public void setDayList(HashMap<Integer,DayWrapper> dayList) {
		this.dayList = dayList;
	}

}
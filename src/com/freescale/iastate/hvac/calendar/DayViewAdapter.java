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

public class DayViewAdapter<T> extends ArrayAdapter<T> {
	final int DP_PER_HOUR = 12;
	static float SCALE = 0;
	@SuppressWarnings("unused")
	private T[] objects;
	@SuppressWarnings("unused")
	private List<T> listObjects;
	private HashMap<Integer,DayWrapper> dayMap= new HashMap<Integer,DayWrapper>();

	@SuppressWarnings("unchecked")
	public T[] getStringArray(HashMap<Integer,DayWrapper> hm){
		String[] output = new String[hm.size()];
		for(int i = 0; i < hm.size(); i++) {
			output[i] = hm.get(i).contents;
		}
		return (T[]) output;
	}

	public DayViewAdapter(Context context, int textViewResourceId, HashMap<Integer,DayWrapper> hm) {
		super(context, textViewResourceId);
		this.addAll(this.getStringArray(hm));
		SCALE = context.getResources().getDisplayMetrics().density;
		this.dayMap = hm;

	}
	
//	public DayViewAdapter(Context context, int resource, int textViewResourceId) {
//		super(context, resource, textViewResourceId);
//		scale = getContext().getResources().getDisplayMetrics().density;
//	}
//	public DayViewAdapter(Context context, int textViewResourceId, T[] objects) {
//		super(context,textViewResourceId,objects);
//		scale = getContext().getResources().getDisplayMetrics().density;
//		this.objects = objects;
//	}
////	public DayViewAdapter(Context context, int resource, int textViewResourceId, T[] objects){
//		super(context,resource,textViewResourceId, objects);
//		scale = getContext().getResources().getDisplayMetrics().density;
//		this.objects = objects;
//	}
//	public DayViewAdapter(Context context, int resource, int textViewResourceId, List<T> objects){
//		super(context,resource,textViewResourceId,objects);
//		scale = getContext().getResources().getDisplayMetrics().density;
//		this.listObjects = objects;
//	}
	
	public View getView(int pos, View convertView, ViewGroup parent){
		if(convertView==null) {
			LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=lf.inflate(R.layout.calendar_listview_cell, null);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.calendar_listview_cell);

		tv.setTextColor(Color.BLACK);
		tv.setText(this.dayMap.get(pos).contents);
		float individualHeight = dayMap.get(pos).getTimeDuration()*(DP_PER_HOUR+1);
		tv.setHeight(convertDpToPixels((int)(individualHeight- 1)));

		return convertView;
	}
	public int convertDpToPixels(int dpLength){
		return (int)((float)dpLength*SCALE + 0.5f);
	}
	public HashMap<Integer,DayWrapper> getDayList() {
		return dayMap;
	}
	public void setDayList(HashMap<Integer,DayWrapper> dayList) {
		this.dayMap = dayList;
	}
}
package com.freescale.iastate.hvac.calendar;
//WARNING: CONTAINTS SPAGHETTI CODE
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.freescale.iastate.hvac.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FSDayView extends ListView {
	public FSDayView(Context context) {
		super(context);
	}
	final int DP_PER_HOUR = 20;
	static float SCALE = 0;
	/** Called when the activity is first created. */
//	public void init() {
//
//		//setContentView(R.layout.fsday_view_layout);
//
//
//		Resources res = getResources();
//		Drawable background = res.getDrawable(R.drawable.list_background);
//
//		ListView timeView1 = (ListView) findViewById(R.id.calendar_weekview_list_times);
//		ListView dayView1 = (ListView) findViewById(R.id.calendar_weekview_list_1);
//		ListView dayView2 = (ListView) findViewById(R.id.calendar_weekview_list_2);
//		ListView dayView3 = (ListView) findViewById(R.id.calendar_weekview_list_3);
//		ListView dayView4 = (ListView) findViewById(R.id.calendar_weekview_list_4);
//		ListView dayView5 = (ListView) findViewById(R.id.calendar_weekview_list_5);
//		RelativeLayout dayViewContainer = (RelativeLayout) findViewById(R.id.dayview_layout_container);
//		
//		
//	
//		dayViewContainer.addView(timeView1);
//		//dayViewContainer.addView(dayView1);
//		//dayViewContainer.addView(dayView2);
//		
//		this.addView(dayViewContainer);
//		TextView textcontent = new TextView(getContext());
//
//		dayViewContainer.setBackgroundDrawable(background);
//		
//		String[] areas = getResources().getStringArray(R.array.dayview_array);
//		String[] times = getResources().getStringArray(R.array.timeview_array);
//		
//		DayWrapper []hmw = new DayWrapper[areas.length];
//		
//		for(int i = 0; i < areas.length; i++)
//			hmw[i]=new DayWrapper();
//		
//		hmw[0].startTime = 0f;
//		hmw[0].endTime = 5f;
//		
//		hmw[1].startTime = 5f;
//		hmw[1].endTime = 9f;
//		
//		hmw[2].startTime = 9f;
//		hmw[2].endTime = 20f;
//		
//		hmw[3].startTime = 20f;
//		hmw[3].endTime = 24f;
//		
//		HashMap<Integer,DayWrapper> tempMap = new HashMap<Integer,DayWrapper>();
//		
//		for(int i = 0; i < areas.length; i++) {
//			hmw[i].contents = areas[i].toString();
//			tempMap.put(i, hmw[i]);
//		}
//		
//		
//		timeView1.setEmptyView(textcontent);
//		timeView1.setAdapter(new TimeViewAdapter<String>(getContext(), R.layout.timeview_cell, times));
//		
//		dayView1.setEmptyView(textcontent);
//		dayView1.setAdapter(new DayViewAdapter<String>(getContext(), R.layout.listview_cell, tempMap));
//	
//	}
//	HashMap<Integer,DayWrapper> dayMap = new HashMap<Integer,DayWrapper>();
//	
//	
//	public class DayWrapper {
//		public final float HOURS = 24;
//		public final int dpPerHour = 20;
//		public float scale;
//		public float timeOfDay;
//		public float startTime=0f;
//		public float endTime=0f;
//		public String contents = new String();
//		
//		public float getTimeDuration() {
//			if(endTime > startTime) return endTime-startTime;
//			else return -1f;
//		}
//	
//	}
//	public class DayViewAdapter<T> extends ArrayAdapter<T> {
//		@SuppressWarnings("unused")
//		private T[] objects;
//		@SuppressWarnings("unused")
//		private List<T> listObjects;
//		private HashMap<Integer,DayWrapper> dayMap= new HashMap<Integer,DayWrapper>();
//
//		@SuppressWarnings("unchecked")
//		public T[] getStringArray(HashMap<Integer,DayWrapper> hm){
//			String[] output = new String[hm.size()];
//			for(int i = 0; i < hm.size(); i++) {
//				output[i] = hm.get(i).contents;
//			}
//			return (T[]) output;
//		}
//
//		public DayViewAdapter(Context context, int textViewResourceId, HashMap<Integer,DayWrapper> hm) {
//			super(context, textViewResourceId);
//			this.addAll(this.getStringArray(hm));
//			SCALE = getResources().getDisplayMetrics().density;
//			this.dayMap = hm;
//
//		}
//
//		public View getView(int pos, View convertView, ViewGroup parent){
//			if(convertView==null) {
//				LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				convertView=lf.inflate(R.layout.listview_cell, null);
//			}
//			TextView tv = (TextView)convertView.findViewById(R.id.calendar_list_cell);
//
//			tv.setTextColor(Color.BLACK);
//			tv.setText(this.dayMap.get(pos).contents);
//			float individualHeight = dayMap.get(pos).getTimeDuration()*(DP_PER_HOUR+1);
//			tv.setHeight(convertDpToPixels((int)(individualHeight- 1)));
//
//			return convertView;
//		}
//		public int convertDpToPixels(int dpLength){
//			return (int)((float)dpLength*SCALE + 0.5f);
//		}
//		public HashMap<Integer,DayWrapper> getDayList() {
//			return dayMap;
//		}
//		public void setDayList(HashMap<Integer,DayWrapper> dayList) {
//			this.dayMap = dayList;
//		}
//	}
//	
//	public class TimeViewAdapter<T> extends ArrayAdapter<T> {
//		private T[] objects;
//		@SuppressWarnings("unused")
//		private List<T> listObjects;
//		private HashMap<Integer,DayWrapper> dayList= new HashMap<Integer,DayWrapper>();
//		
//		public TimeViewAdapter(Context context, int textViewResourceId) {
//			super(context, textViewResourceId);
//		}
//		
//		public TimeViewAdapter(Context context, int textViewResourceId, HashMap<Integer,DayWrapper> hmw) {
//			super(context, textViewResourceId);
//			this.dayList = hmw;
//			
//		}
//		
//		public TimeViewAdapter(Context context, int resource, int textViewResourceId) {
//			super(context, resource, textViewResourceId);
//		}
//		public TimeViewAdapter(Context context, int textViewResourceId, T[] objects) {
//			super(context,textViewResourceId,objects);
//			this.objects = objects;
//		}
//		public TimeViewAdapter(Context context, int resource, int textViewResourceId, T[] objects){
//			super(context,resource,textViewResourceId, objects);
//			this.objects = objects;
//		}
//		public TimeViewAdapter(Context context, int resource, int textViewResourceId, List<T> objects){
//			super(context,resource,textViewResourceId,objects);
//			this.listObjects = objects;
//		}
//		public View getView(int pos, View convertView, ViewGroup parent){
//			if(convertView==null) {
//				LayoutInflater lf = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				convertView=lf.inflate(R.layout.timeview_cell, null);
//			}
//			TextView tv = (TextView)convertView.findViewById(R.id.timeview_cell);
//
//			tv.setTextColor(Color.BLACK);
//			tv.setText(objects[pos].toString());
//
//			tv.setHeight(convertDpToPixels(DP_PER_HOUR));
//
//
//			return convertView;
//		}
//		public int convertDpToPixels(int dpLength){
//			return (int)((float)dpLength*SCALE + 0.5f);
//		}
//		public HashMap<Integer,DayWrapper> getDayList() {
//			return dayList;
//		}
//		public void setDayList(HashMap<Integer,DayWrapper> dayList) {
//			this.dayList = dayList;
//		}
//
//	}
}
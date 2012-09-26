package com.freescale.iastate.hvac;
//#####6:30
//

import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import com.freescale.iastate.hvac.calendar.MyScaler;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.freescale.iastate.hvac.calendar.EventAdapter;
import com.freescale.iastate.hvac.calendar.EventCommon;
import com.freescale.iastate.hvac.calendar.EventWrapper;
import com.freescale.iastate.hvac.calendar.TimeAdapter;
import com.freescale.iastate.util.FSEvent.RecurrenceType;

import android.app.ActionBar;
//import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemClickListener;
//import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CalendarActivity extends Activity implements MenuInterface {
	CalendarView monthCalendar;
	CalendarView weekCalendar;

	Intent monthView;
	Intent weekView;
	Intent dayView;
	Intent eventview;
	Resources res;
	TabHost tabHost;

	private enum TabState {
		TAB_OPEN,
		TAB_CLOSED;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.calendar_tabs);

		res = getResources();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		//this sets the help string for the current activity.
		//copy paste 
		rootIntent.setHelpText(getText(R.string.calendar_help));
		
		tabHost = (TabHost)findViewById(R.id.calendar_tabhost);
		tabHost.setup();
		addTabs();

	}
	
	public void addTabs() {
		
		TabHost.TabSpec programTabSpec = tabHost.newTabSpec("_program"); //move into strings.xml
		Calendar cal = Calendar.getInstance();
		/**
		 * This is the Program Tab in the Calendar's Tab View
		 * 
		 */

		TabState tabStateStart = TabState.TAB_CLOSED;
		TabState tabStateStop = TabState.TAB_CLOSED;

	//	LinearLayout tableRowStart = (LinearLayout) findViewById(R.id.calendar_eventview_startdate_drawer);
	//	LinearLayout tableRowStop = (LinearLayout) findViewById(R.id.calendar_eventview_stopdate_drawer);

	//	tableRowStart.setVisibility(View.GONE);
	//	tableRowStop.setVisibility(View.GONE);

//		Button eventviewTopDrawerButton = (Button) findViewById(R.id.calendar_eventview_top_drawer_button);
//		Button eventviewBottomDrawerButton = (Button) findViewById(R.id.calendar_eventview_bottom_drawer_button);
//
//		String []startString = res.getStringArray(R.array.calendar_eventview_top_tab_text);
//		String []endString = res.getStringArray(R.array.calendar_eventview_bottom_tab_text);

//		DatePicker startDatePicker = (DatePicker) findViewById(R.id.calendar_eventview_startdate);
//		DatePicker endDatePicker = (DatePicker) findViewById(R.id.calendar_eventview_enddate);

//		final TimePicker startTimePicker = (TimePicker) findViewById(R.id.calendar_eventview_starttime);
//		final TimePicker endTimePicker = (TimePicker) findViewById(R.id.calendar_eventview_endtime);
//
//		TextView textStartDate = (TextView) findViewById(R.id.calendar_eventview_stopdate_value);
//		TextView textEndDate = (TextView) findViewById(R.id.calendar_eventview_startdate_value);
//
//		eventviewTopDrawerButton.setOnClickListener(new TabClickListener(tableRowStart,tabStateStart, eventviewTopDrawerButton, startString));
//		eventviewBottomDrawerButton.setOnClickListener(new TabClickListener(tableRowStop,tabStateStop, eventviewBottomDrawerButton, endString));

//		CalendarChangeListener listenerStartDate = new CalendarChangeListener(textStartDate);
//		CalendarChangeListener listenerEndDate = new CalendarChangeListener(textEndDate);

//		startDatePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),listenerStartDate);
//		startTimePicker.setOnTimeChangedListener(listenerStartDate);

//		endDatePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),listenerEndDate);
//		endTimePicker.setOnTimeChangedListener(listenerEndDate);


		//======   START ANDROID SDK BUG FIX ======//
		//At this point, we want to avoid the TimePicker am/pm bug.

		//not implemented

		//======   END ANDROID SDK BUG FIX ======//

		programTabSpec.setContent(R.id.calendar_day_tab);
		programTabSpec.setIndicator(res.getString(R.string.calendar_programview_label));
		// === END PROGRAM TAB
		
		
		TabHost.TabSpec dayTabSpec = tabHost.newTabSpec("_day");
		dayTabSpec.setContent(R.id.calendar_day_tab);
		dayTabSpec.setIndicator("DAY");

		/**
		 * This is the week view tab
		 */
		dayViewContainer = (LinearLayout) findViewById(R.id.calendar_week_tab);



		//		dayViews[0].setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		//		    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
		//		            tabHost.setCurrentTab(1);
		//		    	return true;
		//		    }
		//		});

		this.setupWeekTab();
		this.setupDayTab();
		TabHost.TabSpec weekTabSpec = tabHost.newTabSpec("_week");
		weekTabSpec.setContent(R.id.calendar_week_tab);
		weekTabSpec.setIndicator("WEEK");

		TabHost.TabSpec monthTabSpec = tabHost.newTabSpec("_month");
		monthTabSpec.setContent(R.id.month_tab);
		monthTabSpec.setIndicator("MONTH");

		TabHost.TabSpec eventTabSpec = tabHost.newTabSpec("_event");
		eventTabSpec.setContent(R.id.calendar_day_tab);
		eventTabSpec.setIndicator("EVENT");


	//	tabHost.addTab(programTabSpec);
		tabHost.addTab(dayTabSpec);
		tabHost.addTab(weekTabSpec);
	//	tabHost.addTab(monthTabSpec);
	//	tabHost.addTab(eventTabSpec);
	}
	
	
	LinearLayout dayViewContainer;
	//Shared components
	Vector<Vector<EventWrapper>> events_data;
	TextView timesHeader;
	ListView timesView;
	ListView dayViewPreview1;
	ListView dayViewPreview2;
	TextView day1Header;
	TextView day2Header;
	TextView day3Header;
	TextView day4Header;
	TextView day5Header;
	TextView day6Header;
	TextView day7Header;
	TextView emptyview;
	Vector<TextView> dayHeaders = new Vector<TextView>();
	Vector<ListView>dayViews = new Vector<ListView>();
	Vector<DayClickListener> dayClickListeners = new Vector<DayClickListener>();

	public void setupDayTab() {

		Vector<EventWrapper> dayView_data = new Vector<EventWrapper>();
		Vector<EventWrapper> time_data = new Vector<EventWrapper>();
		
		dayView_data.add(new EventWrapper(0f,5f).setContents("DayView Period #1"));
		dayView_data.add(new EventWrapper(5f, 9f).setContents("DayView Period #2"));
		dayView_data.add(new EventWrapper(10f, 15f).setContents("DayView Period #3"));
		dayView_data.add(new EventWrapper(14f, 24f).setContents("DayView Period #4"));
		
		ScrollView dayViewMain = (ScrollView)findViewById(R.id.calendar_dayview_scollview);
		int totalHeight = dayViewMain.getHeight();
		Drawable background = res.getDrawable(R.drawable.list_background);
		int count = 2;
		TextView []previewView = new TextView[count];
		TextView []timeView = new TextView[count];
		LinearLayout []eventCell = new LinearLayout[count];
		
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout eventCells = (LinearLayout)findViewById(R.id.calendar_dayview_linearlayout_deep);

		for(int i = 0; i < dayView_data.size(); i++) {
			View v = inflater.inflate(R.layout.calendar_dayview_shallow,null);
			
			//LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.calendar_dayview_shallow, null);
			
			TextView time_textView = (TextView)v.findViewById(R.id.calendar_dayview_time_view);
			TextView event_textView = (TextView)v.findViewById(R.id.calendar_dayview_event_view);
			time_textView.setText(dayView_data.get(i).getTimeStart() + " - " + dayView_data.get(i).getTimeEnd());
			event_textView.setText(dayView_data.get(i).getContents());
			
			eventCells.addView(v,i,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			//parent.setText("asdfasdfasdf!!!!!");
			
//			previewView[i] = (TextView)parent.findViewById(R.id.calendar_dayview_time_preview);
			//previewView[i].setText("asdfasdfasdf!!!!!");
			//previewView[i].setText(dayView_data.get(i).getContents());
//			eventCells.addView(previewView[i],0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//			timeView[i] = (TextView)v.findViewById(R.id.calendar_dayview_time_view);
			//eventCell[i] = (LinearLayout)findViewById(R.id.calendar_dayview_linearlayout_shallow);
//			previewView[i].setText(dayView_data.get(i).getContents());
//			eventCell[i].addView(previewView[i]);
//			timeView[i].setText(dayView_data.get(i).getTimeStart());
		//	eventCell[i].addView(timeView[i]);
		//	eventCells.addView(eventCell[i]);
			
		}
		
		//previewView.set
		
	//	dayViewContainer.setBackground(background);
		
		
//		TextView testView = new TextView(this);
//		testView.setText("eep!");
//		TextView testView2 = new TextView(this);
//		testView2.setText("bleep!");
//		dayViewMain.addView(testView, 0);
//		dayViewMain.addView(testView2,1);
		
	/*	
		//weekview list is used for day list header
		timesHeader = (TextView)findViewById(R.id.calendar_weekview_header);
		timesHeader.setText("Time:");
		timesView = (ListView) findViewById(R.id.calendar_dayview_list_times);
		//dayViewPreview1 = (ListView)findViewById(R.id.calendar_dayview_list_day_actual);
		dayViewPreview2 = (ListView)findViewById(R.id.calendar_dayview_list_day_preview);
		
		
		
		String[] times_arr = getResources().getStringArray(R.array.calendar_timeview_array);
		for(int k= 0; k < times_arr.length; k++){
			time_data.add(new EventWrapper(k*1f,(k+1)*1f).setContents(times_arr[k]));
		}
		
		//weekview_cell is copied for day 12:00AM - 11:00PM time cells
		emptyview = new TextView(this);
		
		timesView.setEmptyView(emptyview);
		timesView.setAdapter(new TimeAdapter(this, R.id.calendar_dayview_list_times, time_data));
		
		//dayViewPreview1.setEmptyView(emptyview);
		//dayViewPreview1.setAdapter(new EventAdapter(this, R.id.calendar_dayview_list_day_actual, dayView_data));
		
		//dayViewPreview2.setEmptyView(emptyview);
		dayViewPreview2.setAdapter(new EventAdapter(this, R.id.calendar_dayview_list_day_preview, dayView_data));
	*/}
	public void setupWeekTab() {
		dayViews = new Vector<ListView>();
		timesHeader = (TextView) findViewById(R.id.calendar_weekview_header);
		timesHeader.setText("Time:");

		timesView = (ListView) findViewById(R.id.calendar_weekview_list_times);
		
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_1));
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_2));
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_3));
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_4));
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_5));
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_6));
		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_7));

		
		//set the values for all the headers (Stand-alone TextViews) in the calendar
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_1));
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_2));
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_3));
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_4));
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_5));
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_6));
		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_7));

		this.setHeaders(new String[] { "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"});
		//set an empty view for initializing TextView classes
		emptyview = new TextView(this);

		Resources res = getResources();
		Drawable background = res.getDrawable(R.drawable.list_background);
		dayViewContainer.setBackground(background);

		Vector<EventWrapper> time_data = new Vector<EventWrapper>();
		String[] times_arr = getResources().getStringArray(R.array.calendar_timeview_array);

		//make the time cells in increments of 1f
		for(int k= 0; k < times_arr.length; k++){
			time_data.add(new EventWrapper(k*1f,(k+1)*1f).setContents(times_arr[k]));
		}

		events_data = new Vector<Vector<EventWrapper>>();

		Vector<EventWrapper> event_data = new Vector<EventWrapper>();
		event_data.add(new EventWrapper(0f,5f).setContents("Period #1"));
		event_data.add(new EventWrapper(5f, 9f).setContents("Period #2"));
		event_data.add(new EventWrapper(10f, 15f).setContents("Period #3"));
		event_data.add(new EventWrapper(14f, 24f).setContents("Period #4"));
		events_data.add(event_data);
		
		event_data = new Vector<EventWrapper>();
		event_data.add(new EventWrapper(0f,6f).setContents("Morning"));
		event_data.add(new EventWrapper(6f,8f).setContents("Wake Up"));
		event_data.add(new EventWrapper(8f,12f).setContents("Heat"));
		event_data.add(new EventWrapper(12f,18f).setContents("Cool"));
		event_data.add(new EventWrapper(18f,24f).setContents("Evening"));
		events_data.add(event_data);
		
		event_data = new Vector<EventWrapper>();
		event_data.add(new EventWrapper(0f,6f).setContents("Morning"));
		event_data.add(new EventWrapper(6f,18f).setContents("Vacation"));
		event_data.add(new EventWrapper(18f,24f).setContents("Evening"));
		events_data.add(event_data);
		events_data.add(event_data);
		events_data.add(event_data);
		events_data.add(event_data);
		
		event_data = new Vector<EventWrapper>();
		event_data.add(new EventWrapper(0f,4f).setContents("Idle"));
		event_data.add(new EventWrapper(4f,6f).setContents("High Heat"));
		event_data.add(new EventWrapper(6f,12f).setContents("Heat"));
		event_data.add(new EventWrapper(12f,18f).setContents("Cool"));
		event_data.add(new EventWrapper(18f,24f).setContents("Idle"));
		events_data.add(event_data);

		for(int i = 0; i < 7; i++)
			dayClickListeners.add(new DayClickListener().setIndex(i));

		timesView.setEmptyView(emptyview);
		timesView.setAdapter(new TimeAdapter(this, R.layout.calendar_weekview_cell, time_data));

		dayViews.get(0).setEmptyView(emptyview);
		dayViews.get(0).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_1, events_data.get(0)));
		dayViews.get(0).setOnItemClickListener(dayClickListeners.get(0));

		dayViews.get(1).setEmptyView(emptyview);
		dayViews.get(1).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_2, events_data.get(1)));
		dayViews.get(1).setOnItemClickListener(dayClickListeners.get(1));


		dayViews.get(2).setEmptyView(emptyview);
		dayViews.get(2).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_3, events_data.get(2)));
		dayViews.get(2).setOnItemClickListener(dayClickListeners.get(2));


		dayViews.get(3).setEmptyView(emptyview);
		dayViews.get(3).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_4, events_data.get(3)));
		dayViews.get(3).setOnItemClickListener(dayClickListeners.get(3));


		dayViews.get(4).setEmptyView(emptyview);
		dayViews.get(4).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_5, events_data.get(4)));
		dayViews.get(4).setOnItemClickListener(dayClickListeners.get(4));

		dayViews.get(5).setEmptyView(emptyview);
		dayViews.get(5).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_6, events_data.get(5)));
		dayViews.get(5).setOnItemClickListener(dayClickListeners.get(5));

		dayViews.get(6).setEmptyView(emptyview);
		dayViews.get(6).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_7, events_data.get(6)));
		dayViews.get(6).setOnItemClickListener(dayClickListeners.get(6));

	}
	public void setHeaders(String []values){
		int count = 7;
		if(values.length == count)
			for(int i = 0; i < values.length; i++) dayHeaders.get(i).setText(values[i]);
		
		else new EventCommon.HeaderNumberInvalidException(count);

	}

	public class DayClickListener implements  OnItemClickListener {
		boolean isInitialized = false;
		boolean [] isSelected;
		int index;
		public int selected = -1;
		public DayClickListener setIndex(int index) {
			this.index = index;
			return this;
		}
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			EventAdapter ea = ((EventAdapter)parent.getAdapter());
			if(!isInitialized) {
				isSelected = new boolean[parent.getCount()];
				isInitialized =true;

			}
			for(int i = 0; i < dayViews.size(); i++) {
				if(dayViews.get(i) == parent) {
					for(int j = 0; j < dayViews.get(i).getChildCount(); j++){
						dayViews.get(i).getChildAt(j).setBackgroundResource(R.drawable.calendar_dayview_selected);
					}
					selected = i;
				}
				else {
					for(int j = 0; j < dayViews.get(i).getChildCount(); j++){
						dayViews.get(i).getChildAt(j).setBackgroundResource(R.drawable.calendar_dayview_deselected);
					}
				}
			}
		}
	}
			
			
			
			
			
//			if(dayViews.get(index) == parent) {
//				if(isSelected[position] == true) {
//					v.setBackgroundResource(R.drawable.calendar_dayview_selected);
//					isSelected[position] = false;
//				}
//				else if (isSelected[position] == false) {
//					v.setBackgroundResource(R.drawable.calendar_dayview_deselected);			
//					isSelected[position] = true;
//				}
//			}
//			else {
//			for(int i = 0; i < dayViews.size(); i++) {
//				if(dayViews.get(i) != parent) {
//					for(int j = 0; j < parent.getChildCount(); j++){
//						parent.getChildAt(j).setBackgroundResource(R.drawable.calendar_dayview_deselected);
//					}
//				}
//				else {
//					for(int j = 0; j < parent.getChildCount(); j++){
//						parent.getChildAt(j).setBackgroundResource(R.drawable.calendar_dayview_selected);
//					}
//				}
//			}

				//for(int j = 0; j < parent.getChildCount(); j++){
					
				
//				v.setBackgroundResource(R.drawable.calendar_dayview_deselected);			
//				isSelected[position] = true;
//			}
//				if(((EventAdapter)parent.getAdapter()).hasStableIds())Toast.makeText(getBaseContext(), "IDs are stable", Toast.LENGTH_SHORT).show();
//				else  Toast.makeText(getBaseContext(), "IDs are unstable", Toast.LENGTH_SHORT).show();
				
			//	v.setVisibility(View.GONE);
		//		ea.clear();
		//		ea.remove(events_data.get(index).get(position));
	//			v.invalidate();
		//		ea.notifyDataSetChanged();
		//		parent.setEmptyView(emptyview);
			

			//			Toast.makeText(getBaseContext(), "Count = " + av.getCount() + "\n" + 
			//					"Selected Item Position = " + position, Toast.LENGTH_SHORT).show();
			//			Toast.makeText(getBaseContext(), "ListID = " + av.getId() + "\n" + 
			//					"Sunday = " + R.id.calendar_weekview_list_1, Toast.LENGTH_SHORT).show();
			
			//	((EventAdapter)parent.getAdapter()).remove(events_data.get(index).get(position));
			//((EventAdapter)av.getAdapter()).notifyDataSetChanged();
			
//			EventAdapter ea = ((EventAdapter)parent.getAdapter());
//			if(!isInitialized) {
//				isSelected = new boolean[parent.getCount()];
//				isInitialized =true;
//
//			}
//			if(dayViews.get(index) == parent) {
//				if(isSelected[position] == true) {
//					for(int i = 0; i < parent.getChildCount(); i++) {
//						parent.getChildAt(i).setBackgroundResource(R.drawable.calendar_dayview_selected);
//						
////					isSelected[position] = false;
//							
//				}
//				else if (isSelected[position] == false) {
//					for(int i = 0; i < parent.getChildCount(); i++)
//						parent.getChildAt(i).setBackgroundResource(R.drawable.calendar_dayview_deselected);	
////					for(int i = 0; i < parent.getChildCount(); i++)
////						if(dayViews.get(i) != index) {
////							parent.getChildAt(i).setBackgroundResource(R.drawable.calendar_dayview_deselected);
////							isSelected[position] = false;
////						}	else isSelected[position] = true;
//						
//				}
////				if(((EventAdapter)parent.getAdapter()).hasStableIds())Toast.makeText(getBaseContext(), "IDs are stable", Toast.LENGTH_SHORT).show();
//				else  Toast.makeText(getBaseContext(), "IDs are unstable", Toast.LENGTH_SHORT).show();
			//	ea.clear();
		//		ea.remove(events_data.get(index).get(position));
				//v.invalidate();
				//ea.notifyDataSetChanged();
			//	parent.setEmptyView(emptyview);
				//for(int i = 0; i < parent.getChildCount(); i++)
				//parent.getChildAt(i).setVisibility(View.GONE);
				

			//			Toast.makeText(getBaseContext(), "Count = " + av.getCount() + "\n" + 
			//					"Selected Item Position = " + position, Toast.LENGTH_SHORT).show();
			//			Toast.makeText(getBaseContext(), "ListID = " + av.getId() + "\n" + 
			//					"Sunday = " + R.id.calendar_weekview_list_1, Toast.LENGTH_SHORT).show();
			
			//	((EventAdapter)parent.getAdapter()).remove(events_data.get(index).get(position));
			//((EventAdapter)av.getAdapter()).notifyDataSetChanged();
			
//		}
//	}
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return rootIntent.onOptionsItemSelected(this, item);
	}
	public class TabClickListener implements OnClickListener {

		private LinearLayout tableRow;
		private TabState state;
		private Button button;
		private String stringOn;
		private String stringOff;

		public TabClickListener(LinearLayout tableRow, TabState state, Button button, String []string){
			//setState(state);
			this.state = state;
			this.tableRow = tableRow;
			this.button = button;
			this.stringOff = string[0];
			this.stringOn = string[1];
		}
		public void onClick(View view) {
			switch(state){
			case TAB_OPEN:
				//Animation is dependent on LayoutParams for given layout; is very clunky
				//			tableRow.startAnimation(new MyScaler(1.0f, 1.0f, 1.0f, 0.0f, 5000, tableRow, true));
				tableRow.setVisibility(View.GONE);
				setState(TabState.TAB_CLOSED);
				button.setText(stringOff);
				return;
			case TAB_CLOSED:
				tableRow.setVisibility(View.VISIBLE);
				setState(TabState.TAB_OPEN);
				button.setText(stringOn);
				return;
			}
		}
		public void setState(TabState tabState) {
			this.state = tabState;
		}

	}
}
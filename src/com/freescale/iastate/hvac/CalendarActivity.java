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

import com.freescale.iastate.hvac.calendar.DayWrapper;
import com.freescale.iastate.hvac.calendar.StateAdapter;
import com.freescale.iastate.hvac.calendar.EventCommon;
import com.freescale.iastate.hvac.calendar.EventWrapper;
import com.freescale.iastate.hvac.calendar.TimeAdapter;
import com.freescale.iastate.hvac.util.HVACState;
import com.freescale.iastate.util.FSEvent.RecurrenceType;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
//import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
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

public class CalendarActivity extends Activity implements MenuInterface, EventCommon {
	CalendarView monthCalendar;
	CalendarView weekCalendar;

	Intent monthView;
	Intent weekView;
	Intent dayView;
	Intent eventview;
	Resources res;
	TabHost tabHost;
	TextView emptyview;
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
		eventToolbox.SCALE = getResources().getDisplayMetrics().density;

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
		emptyview = new TextView(this);


		//		dayViews[0].setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		//		    public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
		//		            tabHost.setCurrentTab(1);
		//		    	return true;
		//		    }
		//		});


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
	//	this.setupWeekTab();
		this.setupDayTab();
		this.setupEventDialog();
	}
	
	EventSelectionDialog esd;
	Vector<HVACState> stateData = new Vector<HVACState>();
	

	
	private ListView eventList;
	public void setupEventDialog() {
		
		
		esd = new EventSelectionDialog();
		Button insertButton = (Button)findViewById(R.id.calendar_dayview_insertevent_button);
		insertButton.setOnClickListener(new EventDialogListener());
		
	}
	public class EventDialogListener implements OnClickListener {
		public void onClick(View v) {
			esd.show(getFragmentManager(), "eventdialog");
		}
	}
	public class EventSelectionDialog extends DialogFragment {
//		private View layout;
//		
//		public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
//			layout = inflater.inflate(R.layout.calendar_dayview_event_dialog, parent, false);
//			return layout;
//		}
		
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
//			Button selectButton = (Button)findViewById(R.id.calendar_dayview_eventdialog_select);
//			Button cancelButton = (Button)findViewById(R.id.calendar_dayview_eventdialog_cancel);
			stateData.add(new HVACState("state x"));
			stateData.add(new HVACState("state y"));
			stateData.add(new HVACState("state z"));
			

			LayoutInflater lf = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout v = (LinearLayout) lf.inflate(R.layout.calendar_dayview_eventdialog, null);
			
			ListView eventList = (ListView)v.findViewById(R.id.calendar_dayview_eventdialog_list);
			eventList.setEmptyView(emptyview);
			eventList.setAdapter(new StateAdapter(getBaseContext(), R.id.calendar_dayview_eventdialog_list,stateData));
			eventList.setOnItemClickListener(new StateSelectionSelector());
			
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			LayoutInflater inflater = getActivity().getLayoutInflater();
			
			//builder.setView(inflater.inflate(R.layout.calendar_dayview_eventdialog, null))
			builder.setView(v)
			.setTitle("Choose Event...").setMessage("Select an Event to Use")
			.setPositiveButton("Select Event", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					EventSelectionDialog.this.getDialog().cancel();
					
				}
			});
			
			
			return builder.create();
		}
	}
	public class StateSelectionSelector implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			int selected = 0;
			for(int i = 0; i < parent.getChildCount(); i++) {
				if(i == position) 
					parent.getChildAt(i).setBackgroundResource(R.drawable.calendar_dayview_selected);
				else {
					parent.getChildAt(i).setBackgroundResource(R.drawable.calendar_dayview_deselected);
				}
			}

			
		}
		
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

	Vector<TextView> dayHeaders = new Vector<TextView>();
	Vector<ListView>dayViews = new Vector<ListView>();
	Vector<DayClickListener> dayClickListeners = new Vector<DayClickListener>();
	Vector<Integer> heights = new Vector<Integer>();
	DayWrapper dayWrapper;

	HashMap<EventWrapper,TextView> eventMap = new HashMap<EventWrapper,TextView>();
	HashMap<EventWrapper,TextView> timeMap = new HashMap<EventWrapper,TextView>();
	HashMap<EventWrapper,LinearLayout> viewMap = new HashMap<EventWrapper,LinearLayout>();
	Vector<EventWrapper> eventWrapperKeys = new Vector<EventWrapper>();

	TextView transparentView;
	TextView sampleView;
	Button timeView1Button;
	Button timeView2Button;
	TimePicker timePicker1;
	TimePicker timePicker2;
	int timeViewCellHeight = 0;
	
	
	public void setupDayTab() {

		eventWrapperKeys.add(new EventWrapper(0f,5f).setContents("DayView Period #1"));
		eventWrapperKeys.add(new EventWrapper(5f, 9f).setContents("DayView Period #2\nnewline1\njjhfghc"));
		eventWrapperKeys.add(new EventWrapper(9f, 14f).setContents("DayView Period #3"));
		eventWrapperKeys.add(new EventWrapper(14f, 24f).setContents("DayView Period #4"));


		dayWrapper = new DayWrapper(eventWrapperKeys);
		
//		ScrollView dayViewMain = (ScrollView)findViewById(R.id.calendar_day_tab);
		timeView1Button = (Button)findViewById(R.id.calendar_dayview_time1_button);
		timeView2Button = (Button)findViewById(R.id.calendar_dayview_time2_button);
		TabState tabState1 = TabState.TAB_CLOSED;
		TabState tabState2 = TabState.TAB_CLOSED;

		String []toggleString = new String[2];
		toggleString[0]="Select Time...";
		toggleString[1]="Hide Selector";

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);

		timePicker1 = (TimePicker)findViewById(R.id.calendar_dayview_sidebar_timepicker1);
		timePicker1.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker1.setCurrentMinute(cal.get(Calendar.MINUTE));
		timePicker1.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
		timePicker1.setOnTimeChangedListener(new TimePickerListener());

		timePicker2 = (TimePicker)findViewById(R.id.calendar_dayview_sidebar_timepicker2);
		timePicker2.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker2.setCurrentMinute(cal.get(Calendar.MINUTE));
		timePicker2.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
		timePicker2.setOnTimeChangedListener(new TimePickerListener());

		DrawerClickListener dcl1 = new DrawerClickListener(timePicker1,tabState1,timeView1Button,toggleString);
		DrawerClickListener dcl2 = new DrawerClickListener(timePicker2,tabState2,timeView2Button,toggleString);
		timeView1Button.setOnClickListener(dcl1);
		timeView2Button.setOnClickListener(dcl2);

		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout eventCells = (LinearLayout)findViewById(R.id.calendar_dayview_linearlayout_deep);



		transparentView = (TextView)findViewById(R.id.calendar_dayview_preview_transparent);
		sampleView = (TextView)findViewById(R.id.calendar_dayview_time_preview);

		Vector<LinearLayout> v = new Vector<LinearLayout>();

		for(int i = 0; i < eventWrapperKeys.size(); i++) {

			v.add((LinearLayout)inflater.inflate(R.layout.calendar_dayview_shallow,null));
			TextView time_textView = (TextView)v.get(i).findViewById(R.id.calendar_dayview_time_view);
			TextView event_textView = (TextView)v.get(i).findViewById(R.id.calendar_dayview_event_view);

			if(i%2 == 0) v.get(i).setBackgroundResource(R.drawable.calendar_dayview_shallow_light);
			else v.get(i).setBackgroundResource(R.drawable.calendar_dayview_shallow_dark);

			time_textView.setText(eventWrapperKeys.get(i).collapseTimes());
			event_textView.setText(eventWrapperKeys.get(i).getContents());
			
			timeMap.put(eventWrapperKeys.get(i), time_textView);
			eventMap.put(eventWrapperKeys.get(i), event_textView);
			viewMap.put(eventWrapperKeys.get(i), v.get(i));

			eventCells.addView(v.get(i),i,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			
			if(i == 0) {
				time_textView.measure(0,0);
				timeViewCellHeight = time_textView.getMeasuredHeight(); //the height for 1 hour
				Log.i("ViewLoop","TimeViewCelHeight = " + String.valueOf(timeViewCellHeight));
			}

//			heights.add(v.get(i).getMeasuredHeight());
		}
	}
		
		
		//		for(int i = 0; i < heights.size()-1; i++) hTrans += heights.get(i);
		//		h2 += heights.get(heights.size()-1);
		//		
		//		transparentView.invalidate();
		//		sampleView.invalidate();
		//		transparentView.setHeight(hTrans);
		//		sampleView.setHeight(h2);


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
		emptyview = new (this);

		timesView.setEmptyView(emptyview);
		timesView.setAdapter(new TimeAdapter(this, R.id.calendar_dayview_list_times, time_data));

		//dayViewPreview1.setEmptyView(emptyview);
		//dayViewPreview1.setAdapter(new EventAdapter(this, R.id.calendar_dayview_list_day_actual, dayView_data));

		//dayViewPreview2.setEmptyView(emptyview);
		dayViewPreview2.setAdapter(new EventAdapter(this, R.id.calendar_dayview_list_day_preview, dayView_data));
		 *///}
//	public void setupWeekTab() {
//		dayViews = new Vector<ListView>();
//		timesHeader = (TextView) findViewById(R.id.calendar_weekview_header);
//		timesHeader.setText("Time:");
//
//		timesView = (ListView) findViewById(R.id.calendar_weekview_list_times);
//
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_1));
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_2));
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_3));
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_4));
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_5));
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_6));
//		dayViews.add((ListView) findViewById(R.id.calendar_weekview_list_7));
//
//
//		//set the values for all the headers (Stand-alone TextViews) in the calendar
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_1));
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_2));
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_3));
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_4));
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_5));
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_6));
//		dayHeaders.add((TextView) findViewById(R.id.calendar_timeview_7));
//
//		this.setHeaders(new String[] { "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"});
//		//set an empty view for initializing TextView classes
//		emptyview = new TextView(this);
//
//		Resources res = getResources();
//		Drawable background = res.getDrawable(R.drawable.list_background);
//		dayViewContainer.setBackground(background);
//
//		Vector<EventWrapper> time_data = new Vector<EventWrapper>();
//		String[] times_arr = getResources().getStringArray(R.array.calendar_timeview_array);
//
//		//make the time cells in increments of 1f
//		for(int k= 0; k < times_arr.length; k++){
//			time_data.add(new EventWrapper(k*1f,(k+1)*1f).setContents(times_arr[k]));
//		}
//
//		events_data = new Vector<Vector<EventWrapper>>();
//
//		Vector<EventWrapper> event_data = new Vector<EventWrapper>();
//		event_data.add(new EventWrapper(0f,5f).setContents("Period #1"));
//		event_data.add(new EventWrapper(5f, 9f).setContents("Period #2"));
//		event_data.add(new EventWrapper(10f, 15f).setContents("Period #3"));
//		event_data.add(new EventWrapper(14f, 24f).setContents("Period #4"));
//		events_data.add(event_data);
//
//		event_data = new Vector<EventWrapper>();
//		event_data.add(new EventWrapper(0f,6f).setContents("Morning"));
//		event_data.add(new EventWrapper(6f,8f).setContents("Wake Up"));
//		event_data.add(new EventWrapper(8f,12f).setContents("Heat"));
//		event_data.add(new EventWrapper(12f,18f).setContents("Cool"));
//		event_data.add(new EventWrapper(18f,24f).setContents("Evening"));
//		events_data.add(event_data);
//
//		event_data = new Vector<EventWrapper>();
//		event_data.add(new EventWrapper(0f,6f).setContents("Morning"));
//		event_data.add(new EventWrapper(6f,18f).setContents("Vacation"));
//		event_data.add(new EventWrapper(18f,24f).setContents("Evening"));
//		events_data.add(event_data);
//		events_data.add(event_data);
//		events_data.add(event_data);
//		events_data.add(event_data);
//
//		event_data = new Vector<EventWrapper>();
//		event_data.add(new EventWrapper(0f,4f).setContents("Idle"));
//		event_data.add(new EventWrapper(4f,6f).setContents("High Heat"));
//		event_data.add(new EventWrapper(6f,12f).setContents("Heat"));
//		event_data.add(new EventWrapper(12f,18f).setContents("Cool"));
//		event_data.add(new EventWrapper(18f,24f).setContents("Idle"));
//		events_data.add(event_data);
//
//		for(int i = 0; i < 7; i++)
//			dayClickListeners.add(new DayClickListener().setIndex(i));
//
//		timesView.setEmptyView(emptyview);
//		timesView.setAdapter(new TimeAdapter(this, R.layout.calendar_weekview_cell, time_data));
//
//		dayViews.get(0).setEmptyView(emptyview);
//		dayViews.get(0).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_1, events_data.get(0)));
//		dayViews.get(0).setOnItemClickListener(dayClickListeners.get(0));
//
//		dayViews.get(1).setEmptyView(emptyview);
//		dayViews.get(1).setAdapter(new EventAdapter(this, R.id.calendar_weekview_list_2, events_data.get(1)));
//		dayViews.get(1).setOnItemClickListener(dayClickListeners.get(1));
// 		}
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
			StateAdapter ea = ((StateAdapter)parent.getAdapter());
			if(!isInitialized) {
				isSelected = new boolean[parent.getCount()];
				isInitialized =true;

			}
			for(int i = 0; i < dayViews.size(); i++) {
				if(dayViews.get(i) == parent) {
					for(int j = 0; j < dayViews.get(i).getChildCount(); j++){
						parent.getChildAt(i).setBackgroundResource(R.drawable.calendar_dayview_selected);
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
	int transparentHeight= 0;
	int exampleHeight = 0;
	public class TimePickerListener implements TimePicker.OnTimeChangedListener {

		public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
			
			float hour1 = 0, minute1 = 0;
			float hour2 = 0, minute2 = 0;
			if(timePicker.equals(timePicker1)) {
				hour1 = hourOfDay;
				minute1 = minute;
				hour2 = timePicker2.getCurrentHour();
				minute2 = timePicker2.getCurrentMinute();
			}
			else if (timePicker.equals(timePicker2)){
				hour1 = hourOfDay;
				minute1 = minute;
				hour2 = timePicker1.getCurrentHour();
				minute2 = timePicker1.getCurrentMinute();
			}

			float time1 = hour1 + minute1*MINUTE_MULT_CONSTANT_T2F*0.01f;
			float time2 = hour2 + minute2*MINUTE_MULT_CONSTANT_T2F*0.01f;

			EventTimeIndex eti = dayWrapper.getTimeStartsInEventByEventIndex(time1,time2);
			int count = 0;
			
			while( count < eti.startIndex) {
				timeMap.get(eventWrapperKeys.get(count)).setText(eventWrapperKeys.get(count).collapseTimes());
				viewMap.get(eventWrapperKeys.get(count)).measure(0,0);
				transparentHeight += viewMap.get(eventWrapperKeys.get(count)).getMeasuredHeight();

				Log.i("TimePickerLoop","1st Loop Executed - index = " + count);
				count++;
			}
			transparentView.invalidate();
			transparentView.setHeight(transparentHeight);
			transparentHeight = 0;
			while(count < eti.stopIndex || count == eti.startIndex || count == eti.stopIndex) {
				timeMap.get(eventWrapperKeys.get(count)).setText(eventWrapperKeys.get(count).expandTimes());
				viewMap.get(eventWrapperKeys.get(count)).measure(0,0);
//				Log.i("TimePickerLoop","2nd Loop Executed - index = " + count);
				//Do stuff for the previews
				
				count++;
				
			}
			while(count < eventWrapperKeys.size()) {
				timeMap.get(eventWrapperKeys.get(count)).setText(eventWrapperKeys.get(count).collapseTimes());
				count++;
			}


		}
		public int getTransparentHeight(int count) {
			int value = 0;
			for(int i = 0; i < count; i++){
				viewMap.get(eventWrapperKeys.get(i)).measure(0, 0);
				value += viewMap.get(eventWrapperKeys.get(i)).getMeasuredHeight();

			}
			return value;
		}
	}
	
	
	public class DrawerClickListener implements OnClickListener {

		private TimePicker timePicker;
		private TabState state;
		private Button button;
		private String stringOn;
		private String stringOff;
		SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
		public DrawerClickListener(TimePicker timePicker, TabState state, Button button, String []string){
			//setState(state);
			this.state = state;
			this.timePicker = timePicker;
			this.button = button;
			this.stringOff = string[0];
			this.stringOn = string[1];
		}
		public void onClick(View view) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
			calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());


			switch(state){
			case TAB_OPEN:
				//Animation is dependent on LayoutParams for given layout; is very clunky
				//			tableRow.startAnimation(new MyScaler(1.0f, 1.0f, 1.0f, 0.0f, 5000, tableRow, true));
				timePicker.setVisibility(View.GONE);
				setState(TabState.TAB_CLOSED);
				button.setText(stringOff + " [" + timeFormatter.format(calendar.getTime())+"]");
				return;
			case TAB_CLOSED:
				timePicker.setVisibility(View.VISIBLE);
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
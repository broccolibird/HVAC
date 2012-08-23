package com.freescale.iastate.hvac;

//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import com.freescale.iastate.hvac.calendar.MyScaler;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.freescale.iastate.hvac.calendar.DayViewAdapter;
import com.freescale.iastate.hvac.calendar.DayWrapper;
import com.freescale.iastate.hvac.calendar.TimeViewAdapter;

import android.app.ActionBar;
//import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TimePicker;


@SuppressWarnings("deprecation")
public class CalendarActivity extends TabActivity implements MenuInterface {
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

		addTabs();

	}


	
	public class CalendarChangeListener implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
		private TextView textToModify;
		private Calendar cal;
		private int hourOfDay;
		private int minute;
		private int year;
		private int monthOfYear;
		private int dayOfMonth;
		
		public CalendarChangeListener(TextView textToModify){
			this.textToModify = textToModify;
			this.cal = Calendar.getInstance();
			hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
			minute = cal.get(Calendar.MINUTE);
			year = cal.get(Calendar.YEAR);
			monthOfYear = cal.get(Calendar.MONTH);
			dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			
		}

		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			this.hourOfDay = hourOfDay;
			this.minute = minute;
			updateTextView();

		}

		public void onDateChanged(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			this.year = year;
			this.monthOfYear = monthOfYear;
			this.dayOfMonth = dayOfMonth;
			updateTextView();
		}
		
		public void updateTextView() {
			cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
//			if(hourOfDay < 12) cal.set(Calendar.AM_PM,Calendar.AM);
//			else cal.set(Calendar.AM_PM,Calendar.PM);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.YEAR,year);
			cal.set(Calendar.MONTH,monthOfYear);
			cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
			
			String s = DateFormat.getDateTimeInstance(
		            DateFormat.LONG, DateFormat.LONG).format(new Date(cal.getTimeInMillis()));
			this.textToModify.setText(s);
		}
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
	public void addTabs() {
		tabHost = getTabHost();	
		TabSpec programTabSpec = tabHost.newTabSpec("_program"); //move into strings.xml
		Calendar cal = Calendar.getInstance();
		/**
		 * This is the Program Tab in the Calendar's Tab View
		 * 
		 */

		TabState tabStateStart = TabState.TAB_CLOSED;
		TabState tabStateStop = TabState.TAB_CLOSED;

		LinearLayout tableRowStart = (LinearLayout) findViewById(R.id.calendar_eventview_startdate_drawer);
		LinearLayout tableRowStop = (LinearLayout) findViewById(R.id.calendar_eventview_stopdate_drawer);

		tableRowStart.setVisibility(View.GONE);
		tableRowStop.setVisibility(View.GONE);

		Button eventviewTopDrawerButton = (Button) findViewById(R.id.calendar_eventview_top_drawer_button);
		Button eventviewBottomDrawerButton = (Button) findViewById(R.id.calendar_eventview_bottom_drawer_button);

		String []startString = res.getStringArray(R.array.calendar_eventview_top_tab_text);
		String []endString = res.getStringArray(R.array.calendar_eventview_bottom_tab_text);

		DatePicker startDatePicker = (DatePicker) findViewById(R.id.calendar_eventview_startdate);
		DatePicker endDatePicker = (DatePicker) findViewById(R.id.calendar_eventview_enddate);
		
		final TimePicker startTimePicker = (TimePicker) findViewById(R.id.calendar_eventview_starttime);
		final TimePicker endTimePicker = (TimePicker) findViewById(R.id.calendar_eventview_endtime);
		
		TextView textStartDate = (TextView) findViewById(R.id.calendar_eventview_stopdate_value);
		TextView textEndDate = (TextView) findViewById(R.id.calendar_eventview_startdate_value);
		
		eventviewTopDrawerButton.setOnClickListener(new TabClickListener(tableRowStart,tabStateStart, eventviewTopDrawerButton, startString));
		eventviewBottomDrawerButton.setOnClickListener(new TabClickListener(tableRowStop,tabStateStop, eventviewBottomDrawerButton, endString));
		
		CalendarChangeListener listenerStartDate = new CalendarChangeListener(textStartDate);
		CalendarChangeListener listenerEndDate = new CalendarChangeListener(textEndDate);

		startDatePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),listenerStartDate);
		startTimePicker.setOnTimeChangedListener(listenerStartDate);
		
		endDatePicker.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),listenerEndDate);
		endTimePicker.setOnTimeChangedListener(listenerEndDate);
		
		
		//======   START ANDROID SDK BUG FIX ======//
		//At this point, we want to avoid the TimePicker am/pm bug.
		
		//not implemented
		
		//======   END ANDROID SDK BUG FIX ======//
		
		programTabSpec.setContent(R.id.calendar_event_tab);
		programTabSpec.setIndicator(res.getString(R.string.calendar_programview_label));
		// === END PROGRAM TAB
		TabSpec dayTabSpec = tabHost.newTabSpec("_day");
		dayTabSpec.setContent(R.id.day_tab);
		dayTabSpec.setIndicator("DAY");
		
		/**
		 * This is the week view tab
		 */
		LinearLayout dayViewContainer = (LinearLayout) findViewById(R.id.week_tab);
		
		Resources res = getResources();
		Drawable background = res.getDrawable(R.drawable.list_background);

		ListView timeView = (ListView) findViewById(R.id.calendar_weekview_list_times);
		ListView dayView1 = (ListView) findViewById(R.id.calendar_weekview_list_1);
		ListView dayView2 = (ListView) findViewById(R.id.calendar_weekview_list_2);
		ListView dayView3 = (ListView) findViewById(R.id.calendar_weekview_list_3);
		ListView dayView4 = (ListView) findViewById(R.id.calendar_weekview_list_4);
		ListView dayView5 = (ListView) findViewById(R.id.calendar_weekview_list_5);
		ListView dayView6 = (ListView) findViewById(R.id.calendar_weekview_list_6);
		ListView dayView7 = (ListView) findViewById(R.id.calendar_weekview_list_7);
		
		
		TextView textcontent = new TextView(this);

		dayViewContainer.setBackgroundDrawable(background);
		
		String[] areas = getResources().getStringArray(R.array.calendar_dayview_array);
		String[] times = getResources().getStringArray(R.array.calendar_timeview_array);
		
		DayWrapper []hmw = new DayWrapper[areas.length];
		
		for(int i = 0; i < areas.length; i++)
			hmw[i]=new DayWrapper();
		
		hmw[0].startTime = 0f;
		hmw[0].endTime = 5f;
		
		hmw[1].startTime = 5f;
		hmw[1].endTime = 9f;
		
		hmw[2].startTime = 9f;
		hmw[2].endTime = 20f;
		
		hmw[3].startTime = 20f;
		hmw[3].endTime = 24f;
		
		HashMap<Integer,DayWrapper> tempMap = new HashMap<Integer,DayWrapper>();
		
		for(int i = 0; i < areas.length; i++) {
			hmw[i].contents = areas[i].toString();
			tempMap.put(i, hmw[i]);
		}
		TextView tv_time = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv1 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv2 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv3 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv4 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv5 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv6 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		TextView tv7 = (TextView)TextView.inflate(this, R.layout.calendar_day_header_view, null);
		
		timeView.setEmptyView(textcontent);
		tv_time.setText("Time:");
		timeView.addHeaderView(tv_time);
		timeView.setAdapter(new TimeViewAdapter<String>(this, R.layout.timeview_cell, times));
		
		
		dayView1.setEmptyView(textcontent);
		tv1.setText("Sunday");
		dayView1.addHeaderView(tv1);
		dayView1.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));

		dayView2.setEmptyView(textcontent);
		tv2.setText("Monday");
		dayView2.addHeaderView(tv2);
		dayView2.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));
		
		dayView3.setEmptyView(textcontent);
		tv3.setText("Tuesday");
		dayView3.addHeaderView(tv3);
		dayView3.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));
		
		dayView4.setEmptyView(textcontent);
		tv4.setText("Wednesday");
		dayView4.addHeaderView(tv4);
		dayView4.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));
		
		dayView5.setEmptyView(textcontent);
		tv5.setText("Thursday");
		dayView5.addHeaderView(tv5);
		dayView5.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));
		
		hmw[0]=new DayWrapper();
		hmw[0].startTime = 0f;
		hmw[0].endTime = 24f;
		tempMap = new HashMap<Integer,DayWrapper>();
			hmw[0].contents = areas[0].toString();
			tempMap.put(0, hmw[0]);
		dayView6.setEmptyView(textcontent);
		tv6.setText("Friday");
		dayView6.addHeaderView(tv6);
		dayView6.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));
		
		dayView7.setEmptyView(textcontent);
		tv7.setText("Saturday");
		dayView7.addHeaderView(tv7);
		dayView7.setAdapter(new DayViewAdapter<String>(this, R.layout.calendar_listview_cell, tempMap));
		
		TabSpec weekTabSpec = tabHost.newTabSpec("_week");
		weekTabSpec.setContent(R.id.week_tab);
		weekTabSpec.setIndicator("WEEK");

		TabSpec monthTabSpec = tabHost.newTabSpec("_month");
		monthTabSpec.setContent(R.id.month_tab);
		monthTabSpec.setIndicator("MONTH");

		TabSpec eventTabSpec = tabHost.newTabSpec("_event");
		eventTabSpec.setContent(R.id.calendar_event_tab);
		eventTabSpec.setIndicator("EVENT");


		tabHost.addTab(programTabSpec);
		tabHost.addTab(dayTabSpec);
		tabHost.addTab(weekTabSpec);
		tabHost.addTab(monthTabSpec);
		tabHost.addTab(eventTabSpec);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return rootIntent.onOptionsItemSelected(this, item);
	}
}
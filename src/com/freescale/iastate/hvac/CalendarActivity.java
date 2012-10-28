package com.freescale.iastate.hvac;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.freescale.iastate.hvac.DisplayInterface.ColorDisplay;
import com.freescale.iastate.hvac.calendar.DayWrapper;
import com.freescale.iastate.hvac.calendar.StateAdapter;

import com.freescale.iastate.hvac.calendar.EventCommon;
import com.freescale.iastate.hvac.calendar.EventWrapper;

import com.freescale.iastate.hvac.util.HVACState;
import com.freescale.iastate.util.FSEvent.RecurrenceType;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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

	public enum TabState {
		TAB_OPEN,
		TAB_CLOSED;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.calendar_tabs);
		
		//Finds view, then uses DisplayInterface to change background color
		View calendarView = findViewById(R.id.calendar_event_tab);
		View view = calendarView.getRootView();
		ColorDisplay background_color = new ColorDisplay();
		background_color.setBackgroundColor(view, getBaseContext());

		res = getResources();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		eventToolbox.SCALE = getResources().getDisplayMetrics().density;

		//this sets the help string for the current activity.
		//copy paste 
		rootIntent.setHelpText(getText(R.string.calendar_help));

		tabHost =  (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		addTabs();

	}

	public void addTabs() {

		TabHost.TabSpec programTabSpec = tabHost.newTabSpec("_program"); //move into strings.xml
		/**
		 * This is the Program Tab in the Calendar's Tab View
		 * 
		 */

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

		TabSpec monthTabSpec = tabHost.newTabSpec("_month");
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
	private ListView stateList;

	public void setupEventDialog() {
		HVACState state1 = new HVACState("State 1","cool, fan max, save off")
		.setStates(HVACState.TempOperation.TEMP_COOL,
				HVACState.FanSpeed.FAN_MAX,
				HVACState.EnergySave.SAVE_OFF);

		HVACState state2 = new HVACState("State 2","heat, fan max, save on")
		.setStates(HVACState.TempOperation.TEMP_HEAT,
				HVACState.FanSpeed.FAN_MAX,
				HVACState.EnergySave.SAVE_ON);

		HVACState state3 = new HVACState("State 3","heat, fan auto, save off")
		.setStates(HVACState.TempOperation.TEMP_HEAT,
				HVACState.FanSpeed.FAN_AUTO,
				HVACState.EnergySave.SAVE_OFF);

		HVACState state4 = new HVACState("State 4","temp off, fan off, save on")
		.setStates(HVACState.TempOperation.TEMP_OFF,
				HVACState.FanSpeed.FAN_OFF,
				HVACState.EnergySave.SAVE_ON);
		HVACState state5 = new HVACState("State 5","temp cool, fan off, save on")
		.setStates(HVACState.TempOperation.TEMP_COOL,
				HVACState.FanSpeed.FAN_OFF,
				HVACState.EnergySave.SAVE_ON);

		stateData.add(state1);
		stateData.add(state2);
		stateData.add(state3);
		stateData.add(state4);
		stateData.add(state5);
		
		esd = new EventSelectionDialog();

		Button insertButton = (Button)findViewById(R.id.calendar_dayview_insertevent_button);
		insertButton.setOnClickListener(new EventDialogListener()); 

		heatIcon = (ImageView)sampleView.findViewById(R.id.calendar_heat_image);
		fanIcon = (ImageView)sampleView.findViewById(R.id.calendar_fan_image);
		energySaveIcon = (ImageView)sampleView.findViewById(R.id.calendar_energysave_image);
	}
	ImageView heatIcon;
	ImageView fanIcon;
	ImageView energySaveIcon;
	StateAdapter sa;
	LinearLayout listRow;
	public class EventDialogListener implements OnClickListener {
		public void onClick(View v) {
			esd.show(getFragmentManager(), "statedialog");
		}
	}

	public class EventSelectionDialog extends DialogFragment {

		public Dialog onCreateDialog(Bundle savedInstanceState) {

			LayoutInflater lf = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			listRow = (LinearLayout) lf.inflate(R.layout.calendar_dayview_eventdialog, null);

			stateList = (ListView)listRow.findViewById(R.id.calendar_dayview_eventdialog_list);
			stateList.setEmptyView(emptyview);
			sa = new StateAdapter(getBaseContext(), R.id.calendar_dayview_eventdialog_list,stateData);
			stateList.setAdapter(sa);

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setView(listRow)
			.setTitle("Choose State...").setMessage("Select a State to Use")
			.setPositiveButton("Select State", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					int index = stateList.getCheckedItemPosition();
					//Log.i("Dialog:PreviewParameters","checkedItem = "+index);
					if(index < 0){
						EventSelectionDialog.this.getDialog().cancel();

					} else 
						CalendarActivity.this.setPreviewParameters(index);

				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					EventSelectionDialog.this.getDialog().cancel();

				}
			});


			return builder.create();
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
	Vector<Integer> heights = new Vector<Integer>();
	DayWrapper dayWrapper;

	HashMap<EventWrapper,RelativeLayout> eventMap = new HashMap<EventWrapper,RelativeLayout>();
	HashMap<EventWrapper,TextView> timeMap = new HashMap<EventWrapper,TextView>();
	HashMap<EventWrapper,LinearLayout> viewMap = new HashMap<EventWrapper,LinearLayout>();
	Vector<EventWrapper> eventWrapperKeys = new Vector<EventWrapper>();

	TextView previewSideTransparentView;
	RelativeLayout sampleView;
	Button timeView1Button;
	Button timeView2Button;
	TimePicker timePicker1;
	TimePicker timePicker2;
	int timeViewHourHeight = 0;
	RelativeLayout sampleParent;

	public void setPreviewParameters(int position) {
		TextView title = (TextView)sampleView.findViewById(R.id.calendar_listview_title);
		TextView description = (TextView)sampleView.findViewById(R.id.calendar_listview_description);


		title.setText(sa.getItem(position).title);
		description.setText(sa.getItem(position).description);

		heatIcon.setImageDrawable(sa.getItem(position).heatImage.getDrawable());
		fanIcon.setImageDrawable(sa.getItem(position).fanImage.getDrawable());
		energySaveIcon.setImageDrawable(sa.getItem(position).energySaveImage.getDrawable());
		heatIcon.requestLayout();
		fanIcon.requestLayout();
		energySaveIcon.requestLayout();

	}
	View timeSelection;
	View timePadding;
	public void setupDayTab() {

		eventWrapperKeys.add(new EventWrapper(0f,24f));
//		eventWrapperKeys.add(new EventWrapper(5f, 9f).setContents("DayView Period #2\nnewline1\njjhfghc"));
//		eventWrapperKeys.add(new EventWrapper(9f, 14f).setContents("DayView Period #3"));
//		eventWrapperKeys.add(new EventWrapper(14f, 24f).setContents("DayView Period #4"));

		sampleParent = (RelativeLayout)findViewById(R.id.calendar_dayview_relativelayout);
		sampleView = (RelativeLayout)findViewById(R.id.calendar_dayview_time_preview);

		timeSelection = (View)findViewById(R.id.calendar_dayview_timeselection);
		timePadding = (View)findViewById(R.id.calendar_dayview_timeremove);


		dayWrapper = new DayWrapper(eventWrapperKeys);

		timeView1Button = (Button)findViewById(R.id.calendar_dayview_time1_button);
		timeView2Button = (Button)findViewById(R.id.calendar_dayview_time2_button);
		TabState tabState1 = TabState.TAB_CLOSED;
		TabState tabState2 = TabState.TAB_CLOSED;

		String []toggleString = new String[2];
		toggleString[0] = "Select Time...";
		toggleString[1] = "Hide Selector";

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



		previewSideTransparentView = (TextView)findViewById(R.id.calendar_dayview_preview_transparent);
		previewSideTransparentView.getLayoutParams().height = 0;
		previewSideTransparentView.requestLayout();


		Vector<LinearLayout> v = new Vector<LinearLayout>();

		for(int i = 0; i < eventWrapperKeys.size(); i++) {

			v.add((LinearLayout)inflater.inflate(R.layout.calendar_dayview_shallow,null));
			TextView time_textView = (TextView)v.get(i).findViewById(R.id.calendar_dayview_time_view);
			RelativeLayout event_layout = (RelativeLayout)v.get(i).findViewById(R.id.calendar_dayview_event_view);

			if(i%2 == 0) v.get(i).setBackgroundResource(R.drawable.calendar_dayview_shallow_light);
			else v.get(i).setBackgroundResource(R.drawable.calendar_dayview_shallow_dark);

			time_textView.setText(eventWrapperKeys.get(i).collapseTimes());
			if(i == 0) {
				time_textView.measure(0, 0);
				timeViewHourHeight = time_textView.getMeasuredHeightAndState()-4;
				
				if(eventWrapperKeys.size()>1) timeViewHourHeight = time_textView.getMeasuredHeightAndState()+2;
			}
			//			event_layout.setText(eventWrapperKeys.get(i).getContents());

			timeMap.put(eventWrapperKeys.get(i), time_textView);
			eventMap.put(eventWrapperKeys.get(i), event_layout);
			viewMap.put(eventWrapperKeys.get(i), v.get(i));

			eventCells.addView(v.get(i),i,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		}
		
	}

	public void setHeaders(String []values){
		int count = 7;
		if(values.length == count)
			for(int i = 0; i < values.length; i++) dayHeaders.get(i).setText(values[i]);

		else new EventCommon.HeaderNumberInvalidException(count);

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
				transparentHeight += viewMap.get(eventWrapperKeys.get(count)).getMeasuredHeightAndState();

				//				Log.i("TimePickerLoop","1st Loop Executed - index = " + count);
				count++;
			}

			previewSideTransparentView.getLayoutParams().height = transparentHeight;
			previewSideTransparentView.requestLayout();
			transparentHeight = 0;
			float timePaddingHeight = 0;
	
			while(count < eti.stopIndex || count == eti.startIndex || count == eti.stopIndex) {
				timeMap.get(eventWrapperKeys.get(count)).setText(eventWrapperKeys.get(count).expandTimes());
				//viewMap.get(eventWrapperKeys.get(count)).measure(0,0);

				
				int additionalHeight = 0;
				if(eti.startIndex == eti.stopIndex) additionalHeight = -9;
				if(count == eti.stopIndex && eti.stopIndex > eti.startIndex) additionalHeight = timeViewHourHeight;
				
				float smallTime = dayWrapper.getSmallerTime(time1, time2);
				timePaddingHeight += dayWrapper.getSmallTimeHeightDifference(count, smallTime);

				timePadding.getLayoutParams().height = (int) Math.ceil(timeViewHourHeight*timePaddingHeight);
				timePadding.requestLayout();
				
				timeSelection.getLayoutParams().height = (int) Math.ceil(timeViewHourHeight*dayWrapper.getAbsoluteTime(time1, time2)-additionalHeight);
				timeSelection.requestLayout();

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

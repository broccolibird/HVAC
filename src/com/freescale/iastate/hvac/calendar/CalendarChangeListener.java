package com.freescale.iastate.hvac.calendar;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

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
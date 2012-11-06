package com.freescale.iastate.hvac.calendar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Vector;

import android.util.FloatMath;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventWrapper implements EventCommon {
	private String Id = null;
	
	//View Parameters
	public boolean transparent = false;
	public String contents = new String();
	public int fontsize = 8;
	//View and Data Parameters
	DecimalFormat scannerFormat = new DecimalFormat("00.00");
	public float startTime = 0f;
	public float endTime = 0f;
	public float height = 0;
	public LinearLayout linearView;
	public RelativeLayout eventView;
	public TextView timeView;
	
//	public Vector<Integer> heights = new Vector<Integer>(); //? use
	
	// Linear Layout
	public LinearLayout getLinearView() {
		return this.linearView;
	}
	public void setLinearView(LinearLayout linearView) {
		this.linearView = linearView;
	}
	// Relative Layout
	public RelativeLayout getEventView() {
		return this.eventView;
	}
	public void setEventView(RelativeLayout eventView)  {
		this.eventView = eventView;
	}
	// TextView
	public TextView getTimeView() {
		return this.timeView;
	}
	public void setTimeView(TextView timeView){
		this.timeView = timeView;
	}
	
	
	
	private void sortTimes(float startTime, float endTime) {
		if(startTime < endTime) {
			this.startTime = startTime;
			this.endTime = endTime;
		} else {
			this.startTime = endTime;
			this.endTime = startTime;
		}
	}
	//Constructors
	public EventWrapper(float startTime, float endTime){
		sortTimes(startTime,endTime);
		
		this.contents = Float.valueOf(getTimeDuration()).toString();
		
	}
	public EventWrapper(float startTime, float endTime, boolean transparent){
		sortTimes(startTime,endTime);
		
		this.transparent = transparent;
		this.contents = Float.valueOf(getTimeDuration()).toString();
	}
	public EventWrapper(float startTime, float endTime, String id){
		sortTimes(startTime,endTime);
		
		this.contents = Float.valueOf(getTimeDuration()).toString();
		this.Id = id;
	}
	public EventWrapper(float startTime, float endTime, boolean transparent, String id){
		sortTimes(startTime,endTime);
		
		this.transparent = transparent;
		this.contents = Float.valueOf(getTimeDuration()).toString();
		this.Id = id;
	}

	//methods
	public EventWrapper setContents(String s) {
		this.contents = s;
		return this;
	}
	public void setHeight(float pixels) {
		height =eventToolbox.convertDpToPixels(pixels);
		
	}
	public float getHeight() {
		return this.height;
	}
	public String getContents(){
		return this.contents;
	}
	
	public boolean compareOverlap(EventWrapper dw){
		//returns true if an overlap or invalid format is detected in the argument
		if(this.timeIsCoherent() || dw.timeIsCoherent()) return true;
		if(this.endTime > dw.startTime) return true;
		if(this.endTime > dw.endTime) return true;
		if(this.startTime > dw.endTime) return true;
		if(this.startTime > dw.startTime) return true;
		return false;			
	}
	
	public float getTimeDuration() {
		if(endTime > startTime) return endTime-startTime;
		else return -1f;
	}
	
	public String getTime_end() {
		return this.getTime(this.endTime);
	}
	
	public String getTime_start(){
		return this.getTime(this.startTime);
	}
	
	private String getTime(float time) {
		Calendar cal = new GregorianCalendar();

		String output = scannerFormat.format(time);
		Scanner s = new Scanner(output.replace('.',' '));
		   
	    int hour = s.nextInt();
	    int minute = s.nextInt();
	    s.close();
	    int scaledMinute = (int)((float)minute/MINUTE_MULT_CONSTANT_T2F);
	    
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, scaledMinute);

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		sdf.setCalendar(cal);
		return sdf.format(cal.getTime()).replaceAll("^0", "  ");
	}
	
	public String expandTimes(){
		String s = new String();
			s = getTime(this.startTime);
			float index = this.startTime;
			while(index < this.startTime + getTimeDuration()) {
				index += 1f;
				index = (float) FloatMath.floor(index);
				s += "\n"+getTime(index);
			}
		return s;
	}
	
	public String collapseTimes() {
		return getTime(this.startTime) + " - " + getTime(this.endTime);
	}
	
	public String getID() {
		return this.Id;
	}
	
	public void setID(String id) {
		this.Id = id;
	}
	
	public boolean timeIsCoherent() {
		if(this.getTimeDuration() == -1f) return false;
		if(this.endTime < 0 || this.startTime < 0) return false;
		return true;
	}
	
	public EventWrapper getWrapperObject() {
		return this;
	}
	public float getStopTime() {
		return this.endTime;
	}
	public void setStopTime(float f){
		this.endTime = f;
	}

	public float getStartTime() {
		return this.startTime;
	}
	public void setStartTime(float f) {
		this.startTime = f;
	}
}
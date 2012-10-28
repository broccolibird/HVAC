package com.freescale.iastate.hvac.calendar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Vector;

import android.util.FloatMath;
import android.util.Log;

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
	
	public Vector<Integer> heights = new Vector<Integer>(); //? use
	
	//Constructors
	public EventWrapper(float startTime, float endTime){
		this.startTime = startTime;
		this.endTime = endTime;
		this.contents = Float.valueOf(getTimeDuration()).toString();
		
	}
	public EventWrapper(float startTime, float endTime, boolean transparent){
		this.startTime = startTime;
		this.endTime = endTime;
		this.transparent = transparent;
		this.contents = Float.valueOf(getTimeDuration()).toString();
	}
	public EventWrapper(float startTime, float endTime, String id){
		this.startTime = startTime;
		this.endTime = endTime;
		this.contents = Float.valueOf(getTimeDuration()).toString();
		this.Id = id;
	}
	public EventWrapper(float startTime, float endTime, boolean transparent, String id){
		this.startTime = startTime;
		this.endTime = endTime;
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
	
	public String getTimeEnd() {
		return this.getTime(this.endTime);
	}
	
	public String getTimeStart(){
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

		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
		sdf.setCalendar(cal);
		return sdf.format(cal.getTime());
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
	
	public boolean getTransparency() {
		return this.transparent;
	}
	
	public void setTransparency(boolean transparent) {
		this.transparent = transparent;
	}
	
	public EventWrapper getWrapperObject() {
		return this;
	}
	public float getEndTime() {
		return this.endTime;
	}
	public float getStartTime() {
		return this.startTime;
	}
}
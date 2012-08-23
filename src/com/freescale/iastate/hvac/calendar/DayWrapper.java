package com.freescale.iastate.hvac.calendar;

public class DayWrapper {
	public final float HOURS = 24;
	public final int dpPerHour = 20;
	public float scale;
	public float timeOfDay;
	public float startTime=0f;
	public float endTime=0f;
	public String contents = new String();
	
	public float getTimeDuration() {
		if(endTime > startTime) return endTime-startTime;
		else return -1f;
	}

}
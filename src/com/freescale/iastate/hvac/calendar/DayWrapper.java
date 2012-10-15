package com.freescale.iastate.hvac.calendar;

import java.util.Vector;

import com.freescale.iastate.hvac.calendar.EventCommon.EventTimeIndex;

import android.util.Log;

public class DayWrapper {
	private Vector<EventWrapper> events = new Vector<EventWrapper>();
	
	public DayWrapper(Vector<EventWrapper> events) {
		this.events = events;

	}

	public EventTimeIndex getTimeStartsInEventByEventIndex(float time1, float time2) {
		int eventIndex = 0;
		int startIndex = 0, stopIndex= 0;
		float largerTime = 0f, smallerTime = 0f;
		boolean smallFlagSet = true;///, largeFlagSet = true;
		if(time1 > time2) {
			largerTime = time1;
			smallerTime = time2;
		}
		else {
			largerTime = time2;
			smallerTime = time1;
		}
		float cumulativeDuration = 0f;
		for(eventIndex = 0; eventIndex < events.size(); eventIndex++) {
			float startTime =events.get(eventIndex).getStartTime();
			float duration = events.get(eventIndex).getTimeDuration();
			
			if(	startTime <= smallerTime || startTime >= smallerTime - duration && smallFlagSet) {
//				Log.i("DayWrapper","Smaller Time: "+
//					String.valueOf(events.get(eventIndex).getStartTime()) + " <=> "+ String.valueOf(smallerTime));
				
				startIndex = eventIndex;
				smallFlagSet = false;
				cumulativeDuration += duration;
			}
			else if(startTime < largerTime || startTime < largerTime - duration - cumulativeDuration ){
//				Log.i("DayWrapper","Bigger Time: "+
//					String.valueOf(events.get(eventIndex).getStartTime()) + " <=> "+ String.valueOf(largerTime));
				
				stopIndex = eventIndex;
				cumulativeDuration += duration;

			}
		}
		return new EventTimeIndex(startIndex,stopIndex);
	}
	public int getTimeEndsInEventByIndex(float time){
		int eventIndex = 0;
		for(eventIndex = 0; eventIndex < this.size(); eventIndex++)
			if(events.get(eventIndex).getEndTime() > time) {
				return eventIndex ;
			}
		return eventIndex;
	}
	public void addEvent(EventWrapper e) {
		events.add(e);
	}
	public void insertEvent(EventWrapper e, int index){
		events.insertElementAt(e, index);
	}
	public void deleteEvent(EventWrapper e) {
		events.removeElement(e);
	}
	public EventWrapper getEvent(int index){
		return events.get(index);
	}
	public int size() {
		return events.size();
	}
public int getEventIndex(EventWrapper e){
	int i = 0;
	for(i=0; i < events.size(); i++){
		if(events.get(i).equals(e)) return i;
	}
	Log.i("DayWrapper","getEventIndex() returned 0 after failing to find a match.");
	return 0;
}
	public void trim() {
		events.trimToSize();
	}
}


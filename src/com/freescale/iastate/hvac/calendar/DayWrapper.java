package com.freescale.iastate.hvac.calendar;

import java.util.Vector;

import android.util.Log;

public class DayWrapper {
	private Vector<EventWrapper> events = new Vector<EventWrapper>();
	
	public DayWrapper(Vector<EventWrapper> events) {
		this.events = events;
		
	}
	
	public int timeStartsInEventByEventIndex(float time) {
		int eventIndex = 0;
		for(eventIndex = 0; eventIndex < events.size(); eventIndex++)
			if(events.get(eventIndex).getStartTime() > time) {
				return eventIndex;
			}
		return 0;
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

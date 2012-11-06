package com.freescale.iastate.hvac.calendar;

import java.util.Vector;

import com.freescale.iastate.hvac.calendar.EventCommon.EventTimeIndex;

import android.util.FloatMath;
import android.util.Log;

public class DayWrapper {
	private Vector<EventWrapper> events = new Vector<EventWrapper>();

	public DayWrapper(Vector<EventWrapper> events) {
		this.events = events;

	}
	//	float cumulativeDuration;
	//	public float getCumulativeDuration() {
	//		return this.cumulativeDuration;
	//	}

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

				startIndex = eventIndex;
				smallFlagSet = false;
				cumulativeDuration += duration;
			}
			else if(startTime < largerTime || startTime < largerTime - duration - cumulativeDuration ){

				stopIndex = eventIndex;
				cumulativeDuration += duration;

			}

		}
		return new EventTimeIndex(startIndex,stopIndex);
	}
	public int getTimeEndsInEventByIndex(float time){
		int eventIndex = 0;
		for(eventIndex = 0; eventIndex < this.size(); eventIndex++)
			if(events.get(eventIndex).getStopTime() > time) {
				return eventIndex ;
			}
		return eventIndex;
	}
	public float getSmallTimeHeightDifference(int eventIndex, float smallTime) {
		if(smallTime - events.get(eventIndex).getStartTime() > 0){
			return smallTime - events.get(eventIndex).getStartTime();
		}
		else return 0;

	}
	public float getSmallerTime(float time1, float time2) {
		if(time1 > time2) return time2;
		else return time1;
	}
	public float getAbsoluteTime(float time1, float time2) {
		return Math.abs(time1-time2);
	}

	public void addEvent(EventWrapper e) {
		events.add(e);
	}
	//TODO
	public void insertEvent(EventWrapper newEvent, EventTimeIndex eti){
		Log.i("DayWrapper","insertEvent");
		EventWrapper []oldEvents = new EventWrapper[this.size()];
		for(int i = 0; i < this.size(); i++) {
			oldEvents[i] = getEvent(i);
		}
		EventWrapper prefixEvent = getEvent(0);
		EventWrapper suffixEvent = getEvent(this.size()-1);
		Vector<EventWrapper> newVector = new Vector<EventWrapper>();
		
		int count = 0;

		while( count < eti.startIndex) {		
			newVector.add(oldEvents[count]);
			count++;
			Log.i("daywrapper","old");
		}
//		if(prefixEvent.getStopTime() < newEvent.getStartTime() ){
//			prefixEvent.setStopTime(newEvent.getStartTime());
//			newVector.add(prefixEvent);
//			Log.i("daywrapper","prefix");
//		}
		//count up to where we insert the new event
		while(count < eti.stopIndex || count == eti.startIndex || count == eti.stopIndex) {
			count++;
			Log.i("daywrapper","count = " + count);
		}
		if(newEvent != null) {
			newVector.add(newEvent);
			Log.i("daywrapper","new");
		}
		if(suffixEvent.getStartTime() <= newEvent.getStopTime()){
			suffixEvent.setStartTime(newEvent.getStopTime());
			Log.i("daywrapper","suffix");
			newVector.add(suffixEvent);
		}
		while(count < oldEvents.length) {
			newVector.add(oldEvents[count]);
			count++;
			Log.i("daywrapper","new_old");
		}
		events = newVector;
	}
	public void deleteEvent(EventWrapper e) {
		events.removeElement(e);
	}
	public EventWrapper getEvent(int index){
		return events.get(index);
	}
	public EventWrapper get(int index){
		return events.get(index);
	}
	public int size() {
		return events.size();
	}
	public void removeAll(){
		events.removeAllElements();
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


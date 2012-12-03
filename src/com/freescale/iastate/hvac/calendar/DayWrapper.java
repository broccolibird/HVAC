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

	public void insertEvent(EventWrapper newEvent, EventTimeIndex eti){
		Log.i("DayWrapper","insertEvent");
		int intersectionStart = 0;
		int intersectionStop = 0;
		if(newEvent.getStartTime() == newEvent.getStopTime()) return;
		
		Vector<EventWrapper> newVector = new Vector<EventWrapper>();
		Vector<EventWrapper> oldEvents = new Vector<EventWrapper>();
		for(int i = 0; i < this.size(); i++) {
			oldEvents.add(getEvent(i));
			Log.i("OldEvents [before]","Time: "+oldEvents.get(i).getStartTime() + " ==> " +  oldEvents.get(i).getStopTime() + "  ["+ i+"]");
		}

		for(int i = 0; i < oldEvents.size(); i++){
			//determine start index for intersection
			if(newEvent.getStartTime() > oldEvents.get(i).getStopTime()){
				intersectionStart++;
			}
			//determine stop index for intersection
			if(newEvent.getStopTime() > oldEvents.get(i).getStopTime()){
				intersectionStop++;
			}
		}
		
			
		for(int i = 0; i < intersectionStart; i++) {
//			if(oldEvents[i].getStartTime() == oldEvents[i].getStopTime()) continue;
			newVector.add(oldEvents.get(i));
		}
		if(newEvent.getStartTime() < oldEvents.get(intersectionStart).getStopTime() &&
				intersectionStart == intersectionStop) {
			float oldStopTime = oldEvents.get(intersectionStart).getStopTime();
			oldEvents.get(intersectionStart).setStopTime(newEvent.getStopTime());

			EventWrapper ew1a = oldEvents.get(intersectionStart).clone();
			ew1a.setStartTime(newEvent.getStopTime());
			ew1a.setStopTime(oldStopTime);
			newVector.add(ew1a);
			
			Log.i("##case 1","Start: "+oldEvents.get(intersectionStart).getStartTime()+ "\tStop: "+newEvent.getStopTime()+"\tindex ["+ intersectionStart+"]");
			Log.i("##case 1a","Start: "+ew1a.getStartTime()+ "\tStop: "+ew1a.getStopTime());
			intersectionStop++;
		
			for(int i = intersectionStop; i < oldEvents.size(); i++){
//				if(oldEvents[i].getStartTime() == oldEvents[i].getStopTime()) continue;
				newVector.add(oldEvents.get(i));
			}

		} 
		else if(newEvent.getStartTime() <= oldEvents.get(intersectionStart).getStopTime() &&
				intersectionStop > intersectionStart){
			float lowerTime1 = oldEvents.get(intersectionStart).getStopTime();
			float lowerTime2 = newEvent.getStartTime();
			
			float middleTime1 = oldEvents.get(intersectionStop).getStopTime();
			
			float upperTime1 = oldEvents.get(intersectionStop).getStartTime();
			float upperTime2 = newEvent.getStopTime();
			
			Log.i("##case2","Lower Times = (1) "+ lowerTime1 + " > (2) " + lowerTime2);
			Log.i("##case2","Middle Times = (1) "+ middleTime1);
			Log.i("##case2","Upper Times = (1) "+ upperTime1 + " < (2) " + upperTime2);
			
			EventWrapper ew2a = oldEvents.get(intersectionStart).clone();
			EventWrapper ew2b = oldEvents.get(intersectionStop).clone();
			EventWrapper ew2n = newEvent.clone();
			
			ew2n.setStartTime(lowerTime2);
			ew2n.setStopTime(upperTime2);
			
			for(int i = intersectionStop; i < oldEvents.size(); i++){
				if(oldEvents.get(i).getStartTime() == oldEvents.get(i).getStopTime()) continue;
					newVector.add(oldEvents.get(i));
			}
			newVector.get(intersectionStart).setStartTime(upperTime2);
			newVector.insertElementAt(ew2n, intersectionStart);
			if(newVector.get(0).getStartTime() != 0f ) {
				ew2a.setStartTime(0);
				ew2a.setStopTime(lowerTime2);
				Log.i("Time"," " + lowerTime2);
				if(ew2a.getStopTime() != ew2a.getStartTime())
					newVector.insertElementAt(ew2a,intersectionStart);
			} else {
				newVector.get(0).setStopTime(lowerTime2);
			}
	
			

			
//			Log.i("ChangedEvent Suffix 2","Start: "+ + "\tStop: "+ +"\tindex ["+ intersectionStop+"]");
//			Log.i("ChangedEvent Suffix 2a","Start: "+ + "\tStop: "+ +"\tindex ["+ intersectionStop+"]");
		}
		else Log.i("NO CHANGE","!!!!!!!!!!!!");
		
		
		for(int i = 0; i < oldEvents.size(); i++) {
			
			Log.i("OldEvents [after]","Time: "+oldEvents.get(i).getStartTime() + " ==> " +  oldEvents.get(i).getStopTime() + "  ["+ i+"]");
		}
		Log.i("Spacing","########");
		for(int i = 0; i < newVector.size(); i++) {
			Log.i("newVector [after]","Time: "+newVector.get(i).getStartTime() + " ==> " +  newVector.get(i).getStopTime() + "  ["+ i+"]");
		}
		Log.i("Index [start] [stop]","[" + intersectionStart+"]  ["+intersectionStop+"]");
		this.events.removeAllElements();
		this.events = newVector;

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


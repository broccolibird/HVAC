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
	boolean firstTime = true;
	@SuppressWarnings("unchecked")
	public void insertEvent(EventWrapper newEvent){
		Log.i("DayWrapper","insertEvent");
		int intersectionStart = 0;
		int intersectionStop = 0;
		if(newEvent.getStartTime() == newEvent.getStopTime()) return;

		Vector<EventWrapper> newVector = new Vector<EventWrapper>();
		newVector.removeAllElements();
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
//
//
//		for(int i = 0; i < intersectionStart; i++) {
//			if(oldEvents.get(i).getStartTime() == oldEvents.get(i).getStopTime()) continue;  //not strictly necessary for operation, but a good precaution.
//			if(oldEvents.get(i).getStartTime() != oldEvents.get(i).getStopTime()) newVector.add(oldEvents.get(i));
//		}
//		
		
		float lowerTime1 = oldEvents.get(intersectionStart).getStopTime();
		float lowerTime2 = newEvent.getStartTime();
		float middleTime1 = oldEvents.get(intersectionStop).getStopTime();
		float middleTime2 = oldEvents.get(intersectionStart).getStartTime();
		float upperTime1 = oldEvents.get(intersectionStop).getStartTime();
		float upperTime2 = newEvent.getStopTime();

		//float superTime1  = 0;
//		Vector<EventWrapper> tempVector = new Vector<EventWrapper>();
//		tempVector = (Vector<EventWrapper>) oldEvents.clone();
		
//		if(oldEvents.get(i).oldEvents.get(intersectionStop-1).getStopTime();
	//	float superTime2
		Log.i("##case2","Lower Times = (1) "+ lowerTime1 + " > (2) " + lowerTime2);
		Log.i("##case2","Middle Times = (1) "+ middleTime1 +" > (2) " +middleTime2) ;
		Log.i("##case2","Upper Times = (1) "+ upperTime1 + " < (2) " + upperTime2);
		//Log.i("##case2","Super Times = (1) "+ superTime1 + " : ["+ (intersectionStop-1) + "]");
	//	EventWrapper ew2a = oldEvents.get(intersectionStart).clone();
///		EventWrapper ew2b = oldEvents.get(intersectionStop).clone();
//		EventWrapper ew2n = newEvent.clone();

//		ew2n.setStartTime(lowerTime2);
//		ew2n.setStopTime(upperTime2);

		
		//if(newVector.get(intersectionStart).getStartTime() == 0) newVector.get(intersectionStart).setStartTime(upperTime2);
		
		//if(ew2n.getStartTime() != ew2n.getStopTime()) newVector.insertElementAt(ew2n, intersectionStart);
		if(oldEvents.get(0).getStartTime()== 0 ) {
			//ew2a.setStartTime(lowerTime2);
		//	ew2a.setStopTime(upperTime2);
			Log.i("Time", lowerTime2 + " > " + upperTime2  + " => start ["+intersectionStart+"]");
			
			if(intersectionStart < intersectionStop) {
				//oldEvents.get(intersectionStart).setStartAndStopTimes(lowerTime2, upperTime2);
				oldEvents.get(intersectionStart).setStartAndStopTimes(lowerTime1, upperTime2);
				
				for(int i = 0; i < intersectionStart-1; i++){
					if(oldEvents.get(i).getStartTime() == oldEvents.get(i).getStopTime()) continue;
					newVector.add(oldEvents.get(i));
				}
				Log.i("Inner if statement","Intersection start/stop = "+ intersectionStart+" => Time: "+ upperTime2);
				
				
				if(oldEvents.get(0).getStartTime() != 0 ) {
					//newVector.get(0).setStartTime(0);
					//oldEvents.get(0).setStartTime(0);
					if(newVector.size() == 0) {
						float startTime = oldEvents.get(intersectionStart).getStartTime();
						float stopTime = oldEvents.get(intersectionStart).getStopTime();
						EventWrapper startBuffer = oldEvents.get(0).clone();
						startBuffer.setStartAndStopTimes(startTime, stopTime);
						if(startBuffer.getStartTime() != startBuffer.getStopTime() &&
								startBuffer.getStopTime() != newEvent.getStopTime()) newVector.add(startBuffer);
						Log.i("Times","StartBuffer if = "  +startBuffer.getStartTime() + " : " +startBuffer.getStopTime());
					} else {
					EventWrapper startBuffer = oldEvents.get(intersectionStart).clone();
					startBuffer.setStartAndStopTimes(oldEvents.get(0).getStartTime(),newEvent.getStartTime());
					if(startBuffer.getStartTime() != startBuffer.getStopTime())  newVector.add(startBuffer);
					Log.i("Times","StartBuffer else = "  +startBuffer.getStartTime() + " : " +startBuffer.getStopTime());
					}
					Log.i("Times","Start time does not equal zero => "  +lowerTime2+" > "+  newEvent.getStartTime());
					
				} else { // if(middleTime2 != newVector.get(0).getStopTime()) {
					for(int i = 0; i < newVector.size(); i++) {
						Log.i("newVector [inside]","Time: "+newVector.get(i).getStartTime() + " ==> " +  newVector.get(i).getStopTime() + "  ["+ i+"]");
					}
					EventWrapper startBuffer = oldEvents.get(intersectionStart).clone();
//					float bufferStartTime = newVector.get(newVector.size()-intersectionStart).getStartTime();
					float bufferStopTime = oldEvents.get(intersectionStart-1).getStopTime();
					startBuffer.setStartAndStopTimes(bufferStopTime, lowerTime2);
					Log.i("eep", "middletime2 != newvector(ints).stoptime "+ bufferStopTime);
					if(startBuffer.getStartTime() != startBuffer.getStopTime())  newVector.add(startBuffer);
					//newVector.get(newVector.size()-1).setStartAndStopTimes(bufferStopTime,newEvent.getStartTime());
				}
				if(newEvent.getStartTime() != oldEvents.get(intersectionStart).getStopTime() ||
						newEvent.getStopTime()!=oldEvents.get(intersectionStop).getStartTime()) newVector.add(newEvent);
				
				if(oldEvents.get(intersectionStop).getStartTime() < newEvent.getStopTime()) {
					Log.i("Times","End time is bigger newEvent end time => " + oldEvents.get(intersectionStop).getStartTime() + " < "+newEvent.getStopTime());
					EventWrapper endBuffer = oldEvents.get(intersectionStop).clone();
					endBuffer.setStartTime(lowerTime2);
					Log.i("endTime", endBuffer.getStartTime() + " " + endBuffer.getStopTime());	
					oldEvents.get(intersectionStop).setStartTime(newEvent.getStopTime());

				}
				
				
				for(int i = intersectionStop; i < oldEvents.size(); i++){
					if(oldEvents.get(i).getStartTime() == oldEvents.get(i).getStopTime()) continue;
					newVector.add(oldEvents.get(i));
				}
				//##############################################################################################//
			} else {
				for(int i = 0; i < intersectionStart-1; i++){
					if(oldEvents.get(i).getStartTime() == oldEvents.get(i).getStopTime()) continue;
					newVector.add(oldEvents.get(i));
				}
				//In this specific case we need to make a new event and clone it
			//	EventWrapper ew1o = newEvent.clone();
//				ew1o.setStopTime(upperTime2);
//				ew1o.setStartTime(lowerTime2);
				Log.i("Inner else statement","inner else");
				//if(ew1o.getStartTime() != ew1o.getStopTime() ) {
					Log.i("Inner else if","inner else if [" + intersectionStart+ "]");
					oldEvents.get(intersectionStart).setStartAndStopTimes(0f,0f);
					
				//	if(ew1o.getStartTime() != ew1o.getStopTime()) newVector.add(ew1o);
					if(oldEvents.get(0).getStartTime() == 0 && newEvent.getStartTime() < upperTime2) {
						EventWrapper ew2o = oldEvents.get(intersectionStart).clone();
						if(upperTime1 < lowerTime2) ew2o.setStartAndStopTimes(upperTime1,lowerTime2 );
						else ew2o.setStartAndStopTimes(upperTime2,lowerTime1 );
						if(ew2o.getStartTime() != ew2o.getStopTime()) newVector.add(ew2o);
					
						Log.i("Later Time 4",upperTime2 + " > " + middleTime1 + " ["+ newVector.size() +"]");
					}
					
					if(newEvent.getStartTime() < upperTime2) {
						EventWrapper ew2o = oldEvents.get(intersectionStart).clone();
						if(newEvent.getStartTime() < upperTime2) ew2o.setStartAndStopTimes(newEvent.getStartTime(),upperTime2);
						else ew2o.setStartAndStopTimes(upperTime2,newEvent.getStartTime());
						if(ew2o.getStartTime() != ew2o.getStopTime())  newVector.add(ew2o);
						Log.i("Later Time 1",newEvent.getStartTime() + " > " + upperTime2 + " ["+ newVector.size() +"]");
					} else
					if(oldEvents.get(intersectionStart).getStartTime() < newEvent.getStopTime() ) {
						EventWrapper ew2o = oldEvents.get(intersectionStart).clone();
						if(upperTime1 < lowerTime2) ew2o.setStartAndStopTimes(upperTime1,lowerTime2 );
						else ew2o.setStartAndStopTimes(upperTime2,lowerTime1 );
						if(ew2o.getStartTime() != ew2o.getStopTime()) newVector.add(ew2o);
					
						Log.i("Later Time 2",upperTime2 + " > " + middleTime1 + " ["+ newVector.size() +"]");
					}
					
					if(middleTime1 > upperTime2) {
						EventWrapper ew2o = oldEvents.get(intersectionStart).clone();
						if(upperTime2 < middleTime1) ew2o.setStartAndStopTimes(upperTime2,middleTime1);
						else ew2o.setStartAndStopTimes(middleTime1,upperTime2);
						if(ew2o.getStartTime() != ew2o.getStopTime()) newVector.add(ew2o);
						Log.i("Later Time 5",upperTime2 + " > " + middleTime1 + " ["+ newVector.size() +"]");
					} else
					if(newEvent.getStartTime() < middleTime1) {
						EventWrapper ew2o = oldEvents.get(intersectionStart).clone();
						if(lowerTime2 < middleTime1) ew2o.setStartAndStopTimes(middleTime1,lowerTime2);
						else ew2o.setStartAndStopTimes(lowerTime2,middleTime1);
						if(ew2o.getStartTime() != ew2o.getStopTime())  newVector.add(ew2o);
						Log.i("Later Time 3",middleTime1 + " > " + lowerTime2 + " ["+ newVector.size() +"]");
					}

				
					for(int i = 0; i < newVector.size(); i++) {
						Log.i("newVector [inside]","Time: "+ newVector.get(i).getStartTime() + " ==> " +  newVector.get(i).getStopTime() + "  ["+ i+"]");
					}
			//	}
				for(int i = intersectionStop; i < oldEvents.size(); i++){
					if(oldEvents.get(i).getStartTime() == oldEvents.get(i).getStopTime()) continue;
					newVector.add(oldEvents.get(i));
				}
			}
			
			//	ew2a.setStopTime(newVector.get(intersectionStart).getStopTime());
				//newVector.insertElementAt(ew2a,intersectionStart);
				
		} else if(oldEvents.get(0).getStartTime() != oldEvents.get(0).getStopTime()) {
			Log.i("else"," " + upperTime2 + " intersection start ["+intersectionStart+"]");
			for(int i = 0; i < oldEvents.size(); i++) {
				if(oldEvents.get(i).getStartTime() != oldEvents.get(i).getStopTime()) newVector.add(oldEvents.get(i));
			}
		}
		this.events.removeAllElements();
		for(int i = 0; i < newVector.size(); i++) {
			//if(newVector.get(i).getStartTime() != newVector.get(i).getStopTime()) 
			this.events.add(newVector.get(i));
		}
		for(int i = 0; i < oldEvents.size(); i++) {

			Log.i("OldEvents [after]","Time: "+oldEvents.get(i).getStartTime() + " ==> " +  oldEvents.get(i).getStopTime() + "  ["+ i+"]");
		}
		Log.i("Spacing","########");
		for(int i = 0; i < newVector.size(); i++) {
			Log.i("newVector [after]","Time: "+newVector.get(i).getStartTime() + " ==> " +  newVector.get(i).getStopTime() + "  ["+ i+"]");
		}
		Log.i("Index [start] [stop]","[" + intersectionStart+"]  ["+intersectionStop+"]");
		
		//this.events = newVector;

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


package com.freescale.iastate.hvac.calendar;

import android.util.DisplayMetrics;

public interface EventCommon {
	
	public EventLib eventToolbox = new EventLib();
	public float DP_PER_HOUR = 14f;

	//Base parameters
	public final float HOURS = 24;
	public final float MINUTE_MULT_CONSTANT_T2F = 100f/60f; // T2F = convert regular time to float
	
	
	public class EventLib {
		public float SCALE = 0f;
		
		public void setScale(float scale){
			this.SCALE = scale;
		}
		public int convertDpToPixels(float dp){
			return (int)((float)dp*SCALE + 0.5f);
		}
		//does not work
//		public float convertPixelsToDp(int pixels) {
//			return ((pixels- 0.5f)/SCALE);
//		}
//		public EventLib() {
//			DisplayMetrics metrics = new DisplayMetrics();
//			getWindowManager().getDefaultDisplay
//		}
	}
	public class EventTimeIndex {
		public final int startIndex;
		public final int stopIndex;
		public final int totalSlots;
		public EventTimeIndex(int startIndex, int stopIndex){
			this.startIndex = startIndex;
			this.stopIndex = stopIndex;
			this.totalSlots = stopIndex-startIndex;
		}
	}
	public class HeaderNumberInvalidException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public HeaderNumberInvalidException(int count) {
			super("The array supplied to the calendarview headers is not "+count+" element(s) long in its context.");
		}
		
	}
}
package com.freescale.iastate.hvac.calendar;

public interface EventCommon {
	
	public EventLib eventToolbox = new EventLib();
	public float DP_PER_HOUR = 14f;

	public class EventLib {
		public float SCALE = 0f;
		
		public void setScale(float scale){
			this.SCALE = scale;
		}
		public int convertDpToPixels(float dpLength){
			return (int)((float)dpLength*SCALE + 0.5f);
		}
	}
	public class HeaderNumberInvalidException extends Exception {
		private static final long serialVersionUID = 1L;
		
		public HeaderNumberInvalidException(int count) {
			super("The array supplied to the calendarview headers is not "+count+" element(s) long in its context.");
		}
		
	}
}

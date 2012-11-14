package com.freescale.iastate.hvac.util;

import com.freescale.iastate.util.FSEvent;


public class FSHVACEvent extends FSEvent implements FSHVACEventInterface, FSHVACStateInterface {

	public FSHVACEvent(Recurring isRecurring, int times) {
		 super(isRecurring,times);
	}
	
	HVACState startState;
	HVACState stopState;
	HVACState middleState;
	
	public void setStartState(HVACState state){
		this.startState = state;
		
	}
	public void setMiddleState(HVACState state){
		this.middleState = state;
	}
	
	public void setStopState(HVACState state) {
		this.stopState = state;
	}
	
	public HVACState getStartState() {
		return this.startState;
	}
	
	public HVACState getStopState() {
		return this.stopState;
	}
	

}

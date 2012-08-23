package com.freescale.iastate.hvac.util;

import com.freescale.iastate.util.FSEvent;


public class FSHVACEvent extends FSEvent implements FSHVACEventInterface, FSHVACStateInterface {

	public FSHVACEvent(Recurring isRecurring, int times) {
		 super(isRecurring,times);
	}
	
	FSHVACState startState;
	FSHVACState stopState;
	FSHVACState middleState;
	
	public void setStartState(FSHVACState state){
		this.startState = state;
		
	}
	public void setMiddleState(FSHVACState state){
		this.middleState = state;
	}
	
	public void setStopState(FSHVACState state) {
		this.stopState = state;
	}
	
	public FSHVACState getStartState() {
		return this.startState;
	}
	
	public FSHVACState getStopState() {
		return this.stopState;
	}
	

}

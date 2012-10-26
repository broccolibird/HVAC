package com.freescale.iastate.hvac.util;

import com.freescale.iastate.util.FSState;

public class HVACState extends FSState implements FSHVACStateInterface {
	public FanSpeed fanSpeed;
	public TempOperation tempOperation;
	public QuickAdjust quickAdjust;
	public EnergySave energySave;
	public String title = new String("null");
	public String details = new String("null");
	
	/**
	 * FanSpeed
	 * @author ahoyt
	 * FanSpeed determines the enum Constant, Label, and Register values of 
	 * each fan mode.  The constant is the FAN_VALUE itself, as stored 
	 * automatically in Java
	 */
	public static enum FanSpeed {
		FAN_MAX ("Maximum",0x0001),
		FAN_HIGH ("High",0x0002),
		FAN_MED ("Medium",0x0003),
		FAN_SLOW ("Slow",0x0004),
		FAN_AUTO ("Auto",0x0005),
		FAN_OFF ("Off",0x0000);
		
		private final String label;
		private final int register;
		
		FanSpeed(String label, int register) {
			this.label = label;
			this.register = register;
		}
		
		public String getLabel() { return label; }
		public int getRegister() { return register; }
	}
	public FanSpeed getFanSpeed() {
		return this.fanSpeed;
	}
	public void printFanSpeed() {
		System.out.printf("Fan Name:\t%s\nFAN CONSTANT: \t%s\nRegister:\t0x%04X\n",
				fanSpeed.getLabel(),fanSpeed,fanSpeed.getRegister());
	}
	public static enum EnergySave {
		SAVE_ON("Energy save on",0x0001),
		SAVE_OFF("Energy save off",0x0000);
		
		private final String label;
		private final int register;
		
		EnergySave(String label, int register) {
			this.label = label;
			this.register = register;
		}
		
		public String getLabel() { return label; }
		public int getRegister() { return register; }
	}
	public EnergySave getEnergySave() {
		return energySave;
	}
	/**
	 * TempSet
	 * @author ahoyt
	 * enum Constants, Labels and Register values to set the temperature
	 *
	 */
	public static enum TempOperation {
		TEMP_HEAT ("Heat",0x0001),
		TEMP_COOL ("Cool",0x0002),
		TEMP_AUTO ("Auto",0x0003),
		TEMP_OFF ("Off",0x0000);
	
		private final String label;
		private final int register;
		
		TempOperation(String label, int register) {
			this.label = label;
			this.register = register;
		}
		
		public String getLabel() { return label; }
		public int getRegister() { return register; }
	}
	public void printTempSet() {
		System.out.printf("Temp Name:\t%s\nTEMP CONSTANT: \t%s\nRegister:\t0x%04X\n",
				tempOperation.getLabel(),tempOperation,tempOperation.getRegister());
	}
	public TempOperation getTemperatureOperation() {
		return tempOperation;
	}
	/**
	 * QuickAdjust
	 * @author ahoyt
	 * Fan Quick Adjust: set enum Constant, Label, and Register values for each 
	 * fan mode
	 */
	public enum QuickAdjust {
		ADJUST_UP ("Temperature Up",0x0001),
		ADJUST_DOWN ("Temperature Down",0x0002),
		ADJUST_STOP("Temperature Steady",0x0000);
		
		private final String label;
		private final int register;
		
		QuickAdjust(String label, int register) {
			this.label = label;
			this.register = register;
		}
		
		public String getLabel() { return label; }
		public int getRegister() { return register; }
	}
	public void printQuickAdjust() {
		System.out.printf("Fan Name:\t%s\nQUICKADJUST CONSTANT: \t%s\nRegister:\t0x%04X\n",
				quickAdjust.getLabel(),quickAdjust,quickAdjust.getRegister());
	}
	public HVACState setStates(TempOperation temp, FanSpeed fans, EnergySave esave) {
		this.tempOperation = temp;
		this.fanSpeed = fans;
		this.energySave = esave;
		return this;
	}
	/**
	 * FSHVACState
	 * @author ahoyt
	 * Constructor for FSHVACState, setting all parameters to "off."
	 */
	public HVACState(String title) {
		fanSpeed = FanSpeed.FAN_OFF;
		tempOperation = TempOperation.TEMP_OFF;
		quickAdjust = QuickAdjust.ADJUST_STOP;
		this.title=title;
		
	}
	public HVACState(String title, String details) {
		fanSpeed = FanSpeed.FAN_OFF;
		tempOperation = TempOperation.TEMP_OFF;
		quickAdjust = QuickAdjust.ADJUST_STOP;
		this.title=title;
		this.details = details;
		
	}
}

package com.freescale.iastate.hvac.util;

public interface FSHVACStateInterface {
	
	HVACProperties HVAC_prop = new HVACProperties();
	public class HVACProperties {
		public boolean isFarenheit = true;
		public double currentTemperature = 100;
		public double lowestTemperature = 68;
		public double highestTemperature = 72;
		public double meanTemperature = (highestTemperature+lowestTemperature)/2;
		public HVACProperties() { }
	}
}

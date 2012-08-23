package com.freescale.iastate.util;


//import com.freescale.iastate.util.hvac.FSHVACEventInterface.FanSpeed;

public class FSEvent implements FSEventInterface {

	private Recurring recurring;
	private String message = new String();

	public enum Recurring {
		HOURLY ("Hours",0x0001),
		DAILY ("Days",0x0002),
		WEEKLY ("Weeks",0x0003),
		MONTHLY ("Months",0x0004),
		YEARLY ("Years",0x0005),
		NEVER("Never",0x0000);
	
		RecurrenceType recurrenceType;
		private final String label;
		private final int register;
		private int numberOfOccurrences;
		
		private Recurring(String label, int register) {
			this.label = label;
			this.register = register;
		}
	
		public String getLabel() { return label; }
		public int getRegister() { return register; }
		
		public void setRecurrenceType(RecurrenceType recurrenceType){
			this.recurrenceType = recurrenceType;
		}
		public RecurrenceType getRecurrenceType() {
			return this.recurrenceType;
		}
		public int getNumberOfOccurrences(){
			return numberOfOccurrences;
		}
		public void setNumberOfOccurrences(int numberOfOccurrances){
			this.numberOfOccurrences = numberOfOccurrances;
		}
		
	}
	public enum RecurrenceType {
		ONCE ("Once",0x0001),
		EVERY_OTHER ("Every other",0x0002),
		EVERY_ARBITRATY ("Every",0x0003);
		
		private final String label;
		private final int register;
		private RecurrenceType(String label, int register) {
			this.label = label;
			this.register = register;
		}
		public String getLabel() { return label; }
		public int getRegister() { return register; }
	}

	public void printRecurrenceInfo() {
		System.out.printf("Label:\t%s\nRECURRING CONSTANT: \t%s\nRegister:\t0x%04X",
				recurring.getLabel(),recurring,recurring.getRegister());
	}
	
	/**
	 * FSEvent
	 * @param recurring
	 * @param numberOfOccurrences
	 */
	public FSEvent(Recurring recurring,int numberOfOccurrences) {
		this.recurring = recurring;
		this.recurring.numberOfOccurrences = numberOfOccurrences;
	}
	
	public void setRecurring(Recurring recurring) {
		this.recurring = recurring;
	}
	public Recurring getRecurring() {
		return this.recurring;
	}
	
	
	
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	
}


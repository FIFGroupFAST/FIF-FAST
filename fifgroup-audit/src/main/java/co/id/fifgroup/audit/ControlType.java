package co.id.fifgroup.audit;

import java.io.Serializable;

public class ControlType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5161907028714590883L;

	public enum Control {
		CREATE(1), UPDATE(2), DELETE(3), LOGIN(7), LOGOUT(8), LOCK_USER(9), UNLOCK_USER(10),
		RESET_PASSWORD(11), CHANGE_PASSWORD(12), RELEASE_WORKSTATION(13), START_SCHEDULER(14), STOP_SCHEDULER(15), RUN_JOB(16),
		RELEASE_USER(17), RUN_END_OF_DAY(18), UT_GENERATE_AUTO_BLOCKING(19), UT_UPDATE_AUTO_BLOCKING(20),
		UT_AUTO_SETTLEMENT(21), SYNCH_SCHEDULER_JOB(22);
		
		private int code;
		private Control(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return this.code;
		}
	}
		
	public static String getValue(Integer code) {
		String n = "";
		for (Control ex : Control.values()) {
			if(ex.getCode() == code) {
				n = ex.toString();
			}
		}
		return n;
	}
	
	public static int getControlValue(Control control) {
		return control.code;
	}
}

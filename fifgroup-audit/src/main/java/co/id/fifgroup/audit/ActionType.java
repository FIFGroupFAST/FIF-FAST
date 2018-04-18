package co.id.fifgroup.audit;

import java.io.Serializable;

public class ActionType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5590644979106110350L;

	public enum Action {
		SUBMIT(2), APPROVE(1), REJECT(-1), CANCEL(-2), RESUBMIT(3), LOGIN(7), LOGOUT(8),
		OTHERS(10);
		
		private int code;
		private Action(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	}
	

	public static int getActionValue(Action action) {
		switch (action) {
		case APPROVE:
			return 1;
		case SUBMIT:
			return 2;
		case CANCEL:
			return -2;
		case REJECT:
			return -1;
		case RESUBMIT:
			return 3;
		case LOGIN:
			return 7;
		case LOGOUT:
			return 8;
		default:
			return 0;
		}
	}
}

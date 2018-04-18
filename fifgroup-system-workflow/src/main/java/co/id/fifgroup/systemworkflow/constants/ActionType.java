package co.id.fifgroup.systemworkflow.constants;

public enum ActionType {
	SELECT(-2, "-Select-"),
	APPROVE(1, "Approve"),
	CANCEL_TRX(-3, "Cancel Transaction"),
	READY_TO_APPROVE(0, "Ready to Approve"),
	REJECT(-1, "Reject"),
	SUBMIT(3, "Submit");
	
	private int code;
	private String message;
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	private ActionType(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ActionType fromCode(int code) {
		for (ActionType actionType : ActionType.values()) {
			if (actionType.getCode() == code) {
				return actionType;
			}
		}
		return null;
	}
	
	public static ActionType fromMessage(String message) {
		for (ActionType actionType : ActionType.values()) {
			if (actionType.toString().equalsIgnoreCase(message.toUpperCase())) {
				return actionType;
			}
		}
		return null;
	}
}

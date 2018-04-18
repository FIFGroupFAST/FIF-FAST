package co.id.fifgroup.notification.constant;

public enum MessageType {

	APPROVAL_MESSAGE(0),
	FYI_MESSAGE(1);
	
	private int code;
	
	public int getCode() {
		return code;
	}
	
	private MessageType (int code) {
		this.code = code;
	}
}

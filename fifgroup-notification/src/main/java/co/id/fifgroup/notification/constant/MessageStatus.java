package co.id.fifgroup.notification.constant;

public enum MessageStatus {

	UNREAD(0),
	READ(1),
	CLOSE(2);
	
	private int code;
	
	public int getCode() {
		return code;
	}
	
	private MessageStatus (int code) {
		this.code = code;
	}
}

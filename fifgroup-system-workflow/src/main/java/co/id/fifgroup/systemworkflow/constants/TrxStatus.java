package co.id.fifgroup.systemworkflow.constants;

public enum TrxStatus {
	SELECT(-3, "- Select -"),
	IN_PROGRESS(0, "In Progress"), 
	APPROVED(1, "Approved"), 
	REJECTED(-1, "Rejected"), 
	CANCELED(-2, "Canceled");
	
	private int code;
	private String message;
	
	private TrxStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static TrxStatus fromCode(int code) {
		for (TrxStatus status : TrxStatus.values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}
	
	public static Integer getCodeFromStatus (String status) {
		for (TrxStatus trxStatus : TrxStatus.values()) {
			if (trxStatus.toString().equals(status)) {
				return trxStatus.getCode();
			}
		}
		return null;
	}
	
	public static String getMessageFromType(String type) {
		for(TrxStatus status : TrxStatus.values()) {
			if(status.toString().equals(type)) {
				return status.getMessage();
			}
		}
		return null;
	}
}

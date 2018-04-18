package co.id.fifgroup.audit;

public enum BooleanStatus {

	YES(1), NO(0);
	
	private int code;
	
	private BooleanStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}

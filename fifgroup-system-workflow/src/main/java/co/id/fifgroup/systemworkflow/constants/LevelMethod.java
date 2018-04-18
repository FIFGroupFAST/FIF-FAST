package co.id.fifgroup.systemworkflow.constants;

public enum LevelMethod {
	
	OR(0, "OR"),
	AND_ALL(1, "AND ALL"),
	AND_MINIMUM(2, "AND MINIMUM");
	
	private LevelMethod(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static LevelMethod fromCode(int code) {
		for (LevelMethod method : LevelMethod.values()) {
			if (method.getCode() == code) {
				return method;
			}
		}
		return null;
	}
}

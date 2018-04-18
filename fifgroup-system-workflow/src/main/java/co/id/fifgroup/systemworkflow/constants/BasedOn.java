package co.id.fifgroup.systemworkflow.constants;

public enum BasedOn {
	CurrentAssignment(1),
	NewAssignment(2);
	
	private int code;
	
	private BasedOn(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

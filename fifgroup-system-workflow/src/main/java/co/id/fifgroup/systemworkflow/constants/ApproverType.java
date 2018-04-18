package co.id.fifgroup.systemworkflow.constants;

public enum ApproverType {
	JobOwner((short)10),
	DepartmentOwner((short)4),
	DepartmentHead((short)9),
	Employee((short)7),
	Job((short)6),
	JobGroup((short)8),
	JobRole((short)5),
	Manager((short)2),
	Role((short)3),
	Supervisor((short)1);
	
	private short code;
	
	private ApproverType(short code) {
		this.code = code;
	}

	public short getCode() {
		return code;
	}
}

package co.id.fifgroup.workstructure.constant;

public enum BranchSizeType {

	SELECT("-Select-"),
	L("Big"),
	M("Medium"),
	S("Small");
	
	private String desc;
	
	private BranchSizeType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	public static String fromDesc(String desc) {
		for (BranchSizeType branchSize : BranchSizeType.values()) {
			if (branchSize.getDesc().equalsIgnoreCase(desc)) {
				return branchSize.name();
			}
		}
		return null;
	}
	
}

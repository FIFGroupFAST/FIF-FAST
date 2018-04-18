package co.id.fifgroup.ssoa.domain;

public class ApprovalFlow {
	private String approverName;
	private String remarks;
	private String approvedTime;

	public ApprovalFlow() {
		
	}
	
	public ApprovalFlow(String approverName, String remarks, String approvedTime) {
		super();
		this.approverName = approverName;
		this.remarks = remarks;
		this.approvedTime = approvedTime;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
}
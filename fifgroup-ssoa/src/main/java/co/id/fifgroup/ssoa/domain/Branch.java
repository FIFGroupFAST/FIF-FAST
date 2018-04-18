package co.id.fifgroup.ssoa.domain;

public class Branch {
	private Long branchId;
	private Long organizationId;
	private Long companyId;
	private Long orgHierId;
	private String branchCode;
	private String branchName;
	

	public Branch() {
		
	}


	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public Long getBranchId() {
		return branchId;
	}


	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}


	public Long getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public Long getOrgHierId() {
		return orgHierId;
	}


	public void setOrgHierId(Long orgHierId) {
		this.orgHierId = orgHierId;
	}
	
	
	


	
}
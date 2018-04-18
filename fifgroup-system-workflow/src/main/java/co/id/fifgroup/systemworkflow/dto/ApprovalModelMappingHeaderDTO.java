package co.id.fifgroup.systemworkflow.dto;

import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeader;

public class ApprovalModelMappingHeaderDTO extends ApprovalModelMappingHeader {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String companyName;
	private String orgHierName;
	private String transactionType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrgHierName() {
		return orgHierName;
	}

	public void setOrgHierName(String orgHierName) {
		this.orgHierName = orgHierName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
}

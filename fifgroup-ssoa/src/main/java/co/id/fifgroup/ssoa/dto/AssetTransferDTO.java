package co.id.fifgroup.ssoa.dto;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.ssoa.domain.AssetTransfer;


public class AssetTransferDTO extends AssetTransfer implements CommonTrx {
	
	private static final long serialVersionUID = 5749051688120495054L;
	
	private List<AssetTransferDetailDTO> assetTransferDetail;
	private List<AssetTransferDetailDTO> assetTransferAdd;
	private String userName;
	
	
	private String ebsFlag;
	private Long codeCombinationS;
	private Long codeCombinationId;
	private Long ebsAssetId;
	private String bookTypeCode;
	private Long assignedTo;
	private Long expenseId;
	private String resultOut;
	private Long locationId;	
	private String segment1;
	private String segment2;
	private String segment3;
	private String segment4;
	private String segment5;
	private String segment6;
	private String segment7;
	private String segment8;
	private String accountCode;
	private String atBranchType;
	private String atBranchDestinationType;
	
	public List<AssetTransferDetailDTO> getAssetTransferAdd() {
		return assetTransferAdd;
	}
	public void setAssetTransferAdd(List<AssetTransferDetailDTO> assetTransferAdd) {
		this.assetTransferAdd = assetTransferAdd;
	}
	public List<AssetTransferDetailDTO> getAssetTransferDetail() {
		return assetTransferDetail;
	}
	public void setAssetTransferDetail(List<AssetTransferDetailDTO> assetTransferDetail) {
		this.assetTransferDetail = assetTransferDetail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	*/
	public Long getBranchType() {
		return branchType;
	}
	public void setBranchType(Long branchType) {
		this.branchType = branchType;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getBusinessGroupId() {
		return businessGroupId;
	}
	public void setBusinessGroupId(Long businessGroupId) {
		this.businessGroupId = businessGroupId;
	}
	public Long getKeyJob() {
		return keyJob;
	}
	public void setKeyJob(Long keyJob) {
		this.keyJob = keyJob;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public UUID getObjectEmployeeUUID() {
		return objectEmployeeUUID;
	}
	public void setObjectEmployeeUUID(UUID objectEmployeeUUID) {
		this.objectEmployeeUUID = objectEmployeeUUID;
	}
	public boolean isBasedLineRequestor() {
		return basedLineRequestor;
	}
	public void setBasedLineRequestor(boolean basedLineRequestor) {
		this.basedLineRequestor = basedLineRequestor;
	}
	public Long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}
	public int getTransactionApprovalStatus() {
		return transactionApprovalStatus;
	}
	public void setTransactionApprovalStatus(int transactionApprovalStatus) {
		this.transactionApprovalStatus = transactionApprovalStatus;
	}
	public Long getOrganizationDestinationId() {
		return organizationDestinationId;
	}
	public void setOrganizationDestinationId(Long organizationDestinationId) {
		this.organizationDestinationId = organizationDestinationId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getEbsAssetId() {
		return ebsAssetId;
	}
	public void setEbsAssetId(Long ebsAssetId) {
		this.ebsAssetId = ebsAssetId;
	}
	public String getBookTypeCode() {
		return bookTypeCode;
	}
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}
	public String getResultOut() {
		return resultOut;
	}
	public void setResultOut(String resultOut) {
		this.resultOut = resultOut;
	}
	public Long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Long getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}
	public String getSegment1() {
		return segment1;
	}
	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}
	public String getSegment2() {
		return segment2;
	}
	public void setSegment2(String segment2) {
		this.segment2 = segment2;
	}
	public String getSegment3() {
		return segment3;
	}
	public void setSegment3(String segment3) {
		this.segment3 = segment3;
	}
	public String getSegment4() {
		return segment4;
	}
	public void setSegment4(String segment4) {
		this.segment4 = segment4;
	}
	public String getSegment5() {
		return segment5;
	}
	public void setSegment5(String segment5) {
		this.segment5 = segment5;
	}
	public String getSegment6() {
		return segment6;
	}
	public void setSegment6(String segment6) {
		this.segment6 = segment6;
	}
	public String getSegment7() {
		return segment7;
	}
	public void setSegment7(String segment7) {
		this.segment7 = segment7;
	}
	public String getSegment8() {
		return segment8;
	}
	public void setSegment8(String segment8) {
		this.segment8 = segment8;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Long getCodeCombinationId() {
		return codeCombinationId;
	}
	public void setCodeCombinationId(Long codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}
	public Long getCodeCombinationS() {
		return codeCombinationS;
	}
	public void setCodeCombinationS(Long codeCombinationS) {
		this.codeCombinationS = codeCombinationS;
	}
	public String getAtBranchType() {
		return atBranchType;
	}
	public void setAtBranchType(String atBranchType) {
		this.atBranchType = atBranchType;
	}
	public String getAtBranchDestinationType() {
		return atBranchDestinationType;
	}
	public void setAtBranchDestinationType(String atBranchDestinationType) {
		this.atBranchDestinationType = atBranchDestinationType;
	}
	public String getEbsFlag() {
		return ebsFlag;
	}
	public void setEbsFlag(String ebsFlag) {
		this.ebsFlag = ebsFlag;
	}
	
	
	
	
}

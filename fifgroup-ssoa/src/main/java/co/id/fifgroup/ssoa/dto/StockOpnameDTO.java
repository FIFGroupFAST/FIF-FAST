package co.id.fifgroup.ssoa.dto;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.CommonTrx;
import co.id.fifgroup.ssoa.domain.StockOpname;


public class StockOpnameDTO extends StockOpname implements CommonTrx {
	
	private static final long serialVersionUID = 8153696528893553702L;
	
	private List<StockOpnameDetailDTO> stockOpnameDetail;
	private List<StockOpnameUnregAssetsDTO> stockOpnameUnregAssets;
	private String userName;
	private String branchName;
	private String status;
	private Long numberOfAssetReg; 
	private Long numberOfAssetUnReg;
	private Long numberOfProcessAssetReg;
	private Long numberOfProcessAssetUnReg;
	private Long numberOfUnProcessAssetReg;
	private Long numberOfUnProcessAssetUnReg;
	private Long SoResultFoundGoodUsed;
	private Long SoResultFoundGoodNotUsed;
	private Long SoResultFoundBroken;
	private Long SoResultNotFound;
	private String remark;
	private UUID personUUID;
	private String avmTrxIdString;
	private String reportNo;
	private String reportStatus;
	private Long reportStatusId;
	private String reportStatusCode;
	private Boolean isBranchSubmitter;
	private String branchNameSubmitter;
	private Long branchDestination;
	private Long branchOrigin;
	private Long assetId;
	private String assetNumber;
	
	
	private Long functionId;
	private Long responsibilityId;
	private String moduleName;
	private String actionUrl;
	private String responsibilityName;
	private String soBranchType;
	
	
	
	public List<StockOpnameUnregAssetsDTO> getStockOpnameUnregAssets() {
		return stockOpnameUnregAssets;
	}
	public void setStockOpnameUnregAssets(List<StockOpnameUnregAssetsDTO> stockOpnameUnregAssets) {
		this.stockOpnameUnregAssets = stockOpnameUnregAssets;
	}
	public List<StockOpnameDetailDTO> getStockOpnameDetail() {
		return stockOpnameDetail;
	}
	public void setStockOpnameDetail(List<StockOpnameDetailDTO> stockOpnameDetail) {
		this.stockOpnameDetail = stockOpnameDetail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
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
	public Long getNumberOfAssetReg() {
		return numberOfAssetReg;
	}
	public void setNumberOfAssetReg(Long numberOfAssetReg) {
		this.numberOfAssetReg = numberOfAssetReg;
	}
	public Long getNumberOfAssetUnReg() {
		return numberOfAssetUnReg;
	}
	public void setNumberOfAssetUnReg(Long numberOfAssetUnReg) {
		this.numberOfAssetUnReg = numberOfAssetUnReg;
	}
	public Long getNumberOfProcessAssetReg() {
		return numberOfProcessAssetReg;
	}
	public void setNumberOfProcessAssetReg(Long numberOfProcessAssetReg) {
		this.numberOfProcessAssetReg = numberOfProcessAssetReg;
	}
	public Long getNumberOfProcessAssetUnReg() {
		return numberOfProcessAssetUnReg;
	}
	public void setNumberOfProcessAssetUnReg(Long numberOfProcessAssetUnReg) {
		this.numberOfProcessAssetUnReg = numberOfProcessAssetUnReg;
	}
	public Long getNumberOfUnProcessAssetReg() {
		return numberOfUnProcessAssetReg;
	}
	public void setNumberOfUnProcessAssetReg(Long numberOfUnProcessAssetReg) {
		this.numberOfUnProcessAssetReg = numberOfUnProcessAssetReg;
	}
	public Long getNumberOfUnProcessAssetUnReg() {
		return numberOfUnProcessAssetUnReg;
	}
	public void setNumberOfUnProcessAssetUnReg(Long numberOfUnProcessAssetUnReg) {
		this.numberOfUnProcessAssetUnReg = numberOfUnProcessAssetUnReg;
	}
	
	public Long getSoResultFoundGoodUsed() {
		return SoResultFoundGoodUsed;
	}
	public void setSoResultFoundGoodUsed(Long soResultFoundGoodUsed) {
		SoResultFoundGoodUsed = soResultFoundGoodUsed;
	}
	public Long getSoResultFoundGoodNotUsed() {
		return SoResultFoundGoodNotUsed;
	}
	public void setSoResultFoundGoodNotUsed(Long soResultFoundGoodNotUsed) {
		SoResultFoundGoodNotUsed = soResultFoundGoodNotUsed;
	}
	public Long getSoResultFoundBroken() {
		return SoResultFoundBroken;
	}
	public void setSoResultFoundBroken(Long soResultFoundBroken) {
		SoResultFoundBroken = soResultFoundBroken;
	}
	public Long getSoResultNotFound() {
		return SoResultNotFound;
	}
	public void setSoResultNotFound(Long soResultNotFound) {
		SoResultNotFound = soResultNotFound;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public UUID getPersonUUID() {
		return personUUID;
	}
	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}
	public String getAvmTrxIdString() {
		return avmTrxIdString;
	}
	public void setAvmTrxIdString(String avmTrxIdString) {
		this.avmTrxIdString = avmTrxIdString;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public Long getReportStatusId() {
		return reportStatusId;
	}
	public void setReportStatusId(Long reportStatusId) {
		this.reportStatusId = reportStatusId;
	}
	public String getReportStatusCode() {
		return reportStatusCode;
	}
	public void setReportStatusCode(String reportStatusCode) {
		this.reportStatusCode = reportStatusCode;
	}
	public Boolean getIsBranchSubmitter() {
		return isBranchSubmitter;
	}
	public void setIsBranchSubmitter(Boolean isBranchSubmitter) {
		this.isBranchSubmitter = isBranchSubmitter;
	}
	public String getBranchNameSubmitter() {
		return branchNameSubmitter;
	}
	public void setBranchNameSubmitter(String branchNameSubmitter) {
		this.branchNameSubmitter = branchNameSubmitter;
	}

	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public Long getResponsibilityId() {
		return responsibilityId;
	}
	public void setResponsibilityId(Long responsibilityId) {
		this.responsibilityId = responsibilityId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getResponsibilityName() {
		return responsibilityName;
	}
	public void setResponsibilityName(String responsibilityName) {
		this.responsibilityName = responsibilityName;
	}
	
	public Long getBranchDestination() {
		return branchDestination;
	}
	public void setBranchDestination(Long branchDestination) {
		this.branchDestination = branchDestination;
	}
	public Long getBranchOrigin() {
		return branchOrigin;
	}
	public void setBranchOrigin(Long branchOrigin) {
		this.branchOrigin = branchOrigin;
	}
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getSoBranchType() {
		return soBranchType;
	}
	public void setSoBranchType(String soBranchType) {
		this.soBranchType = soBranchType;
	}

	
}

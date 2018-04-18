package co.id.fifgroup.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.CommonTrx;

public class Information implements CommonTrx, Serializable {

	private static final long serialVersionUID = -6675950412485403644L;

	private int informationId;
	private int requestorNumber;
	private String requestorName;
	private Date requestDate;
	private String informationData;
	private String secretInfo;
	private Date createdDate;
	private String createdBy;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	
	private UUID avmTrxId;
	private UUID objectEmployeeUUID;
	private String approvalStatus;
	
	private String senderName;
	
	private long organizationIDEmployee;
	//inquiry
	private List<Long> inOrganizationId;
	private List<Long> notInOrganizationId;
	//ManagerSelfService
	private int managerNumber;
	
	public int getInformationId() {
		return informationId;
	}
	public void setInformationId(int informationId) {
		this.informationId = informationId;
	}
	public int getRequestorNumber() {
		return requestorNumber;
	}
	public void setRequestorNumber(int requestorNumber) {
		this.requestorNumber = requestorNumber;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getInformationData() {
		return informationData;
	}
	public void setInformationData(String informationData) {
		this.informationData = informationData;
	}
	public String getSecretInfo() {
		return secretInfo;
	}
	public void setSecretInfo(String secretInfo) {
		this.secretInfo = secretInfo;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public UUID getAvmTrxId() {
		return avmTrxId;
	}
	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
	}
	public UUID getObjectEmployeeUUID() {
		return objectEmployeeUUID;
	}
	public void setObjectEmployeeUUID(UUID objectEmployeeUUID) {
		this.objectEmployeeUUID = objectEmployeeUUID;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public long getOrganizationIDEmployee() {
		return organizationIDEmployee;
	}
	public void setOrganizationIDEmployee(long organizationIDEmployee) {
		this.organizationIDEmployee = organizationIDEmployee;
	}
	public List<Long> getInOrganizationId() {
		return inOrganizationId;
	}
	public void setInOrganizationId(List<Long> inOrganizationId) {
		this.inOrganizationId = inOrganizationId;
	}
	public List<Long> getNotInOrganizationId() {
		return notInOrganizationId;
	}
	public void setNotInOrganizationId(List<Long> notInOrganizationId) {
		this.notInOrganizationId = notInOrganizationId;
	}
	public int getManagerNumber() {
		return managerNumber;
	}
	public void setManagerNumber(int managerNumber) {
		this.managerNumber = managerNumber;
	}
	
	@Override
	public Long getBranchType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getJobId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getGradeId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getOrganizationId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getLocationId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getJobGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getBusinessGroupId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getKeyJob() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getEmploymentStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isBasedLineRequestor() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Long getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getTransactionApprovalStatus() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Long getOrganizationDestinationId() {
		// TODO Auto-generated method stub
		return null;
	}
}
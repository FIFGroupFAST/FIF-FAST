package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

import co.id.fifgroup.core.util.DateUtil;

public class PersonTransferWithinGroupDTO implements Serializable {

	private static final long serialVersionUID = 8102164310098348888L;
	private Long requestId;
	private Long companyId;
	private String originCompany;
	private Long personId;
	private String employeeNumber;
	private String fullName;
	private String photoFilePath;
	private Long terminationTypeId;
	private String terminationReason;
	private Date terminationDate;
	private Long destinationCompanyId;
	private String approvalStatus;
	private String transactionStatus;
	private String transferStatus;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private String userName;

	private Date terminationDateFrom;
	private Date terminationDateTo;

	private int number = 0;
	// 14071111002547_Modifikasi Menu Termination_RAH
	private String comments;
	// 14071111002547_Modifikasi Menu Termination_RAH
	private String terminationDateString;
	/*add By RIM PIR Modifikasi Menu Termination*/
	private String notEqualTransactionStatus;
	/*end add by RIM*/
	
	
	public int getNumber() {
		return this.number;
	}
	
	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getOriginCompany() {
		return originCompany;
	}

	public void setOriginCompany(String originCompany) {
		this.originCompany = originCompany;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
		try {
			this.number =  Integer.parseInt(employeeNumber);
		} catch(NumberFormatException e) { }
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhotoFilePath() {
		return photoFilePath;
	}

	public void setPhotoFilePath(String photoFilePath) {
		this.photoFilePath = photoFilePath;
	}

	public Long getTerminationTypeId() {
		return terminationTypeId;
	}

	public void setTerminationTypeId(Long terminationTypeId) {
		this.terminationTypeId = terminationTypeId;
	}

	public String getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public Long getDestinationCompanyId() {
		return destinationCompanyId;
	}

	public void setDestinationCompanyId(Long destinationCompanyId) {
		this.destinationCompanyId = destinationCompanyId;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getTerminationDateFrom() {
		return terminationDateFrom;
	}

	public void setTerminationDateFrom(Date terminationDateFrom) {
		this.terminationDateFrom = terminationDateFrom;
	}

	public Date getTerminationDateTo() {
		return terminationDateTo;
	}

	public void setTerminationDateTo(Date terminationDateTo) {
		this.terminationDateTo = terminationDateTo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTerminationDateString() {
		if (terminationDate != null) {
			terminationDateString = DateUtil.format(terminationDate,
					DateUtil.DEFAULT_FORMAT);
		} else {
			terminationDateString = null;
		}

		return terminationDateString;
	}

	public void setTerminationDateString(String terminationDateString) {
		this.terminationDateString = terminationDateString;
	}

	public String getNotEqualTransactionStatus() {
		return notEqualTransactionStatus;
	}

	/*add By RIM PIR Modifikasi Menu Termination*/
	public void setNotEqualTransactionStatus(String notEqualTransactionStatus) {
		this.notEqualTransactionStatus = notEqualTransactionStatus;
	}
	/* end add By RIM PIR Modifikasi Menu Termination*/
	
	
	
	

}

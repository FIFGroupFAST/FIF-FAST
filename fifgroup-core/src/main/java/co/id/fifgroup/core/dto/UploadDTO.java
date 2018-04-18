package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class UploadDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long companyId;
	private String batchNumber;
	private Integer batchNumFrom;
	private Integer batchNumTo;
	private Date uploadDate;
	private Date processFrom;
	private Date processTo;
	private String status;
	private Date lastUpdateDate;
	private Long lastUpdatedBy;
	private Date creationDate;
	private Long createdBy;
	private int totalRecords;
	private int succeedRecords;
	private int failedRecords;
	private Long uploadedBy;
	private String accountType;
	private String batchNumberFrom;
	private String batchNumberTo;
	private String userName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getProcessFrom() {
		return processFrom;
	}
	public void setProcessFrom(Date processFrom) {
		this.processFrom = processFrom;
	}
	public Date getProcessTo() {
		return processTo;
	}
	public void setProcessTo(Date processTo) {
		this.processTo = processTo;
	}
	public Integer getBatchNumFrom() {
		return batchNumFrom;
	}
	public void setBatchNumFrom(Integer batchNumFrom) {
		this.batchNumFrom = batchNumFrom;
	}
	public Integer getBatchNumTo() {
		return batchNumTo;
	}
	public void setBatchNumTo(Integer batchNumTo) {
		this.batchNumTo = batchNumTo;
	}
	public int getTotalRecords() {
		return succeedRecords+failedRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getSucceedRecords() {
		return succeedRecords;
	}
	public void setSucceedRecords(int succeedRecords) {
		this.succeedRecords = succeedRecords;
	}
	public int getFailedRecords() {
		return failedRecords;
	}
	public void setFailedRecords(int failedRecords) {
		this.failedRecords = failedRecords;
	}
	public Long getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(Long uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}	
	public String getBatchNumberFrom() {
		return batchNumberFrom;
	}
	public void setBatchNumberFrom(String batchNumberFrom) {
		this.batchNumberFrom = batchNumberFrom;
	}
	public String getBatchNumberTo() {
		return batchNumberTo;
	}
	public void setBatchNumberTo(String batchNumberTo) {
		this.batchNumberTo = batchNumberTo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return "UploadInquiryDto [batchNum=" + batchNumber + ", uploadDate="
				+ uploadDate + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batchNumber == null) ? 0 : batchNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadDTO other = (UploadDTO) obj;
		if (batchNumber == null) {
			if (other.batchNumber != null)
				return false;
		} else if (!batchNumber.equals(other.batchNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}

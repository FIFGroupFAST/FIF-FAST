package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.util.Date;

public class UploadElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187537157780081123L;
	private Long uploadId;
	private String batchNumber;
	private Date uploadDate;
	private Integer successRows;
	private Integer failedRows;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	
	public Long getUploadId() {
		return uploadId;
	}
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
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
	public Integer getSuccessRows() {
		return successRows;
	}
	public void setSuccessRows(Integer successRows) {
		this.successRows = successRows;
	}
	public Integer getFailedRows() {
		return failedRows;
	}
	public void setFailedRows(Integer failedRows) {
		this.failedRows = failedRows;
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
	
}

package co.id.fifgroup.ssoa.domain;

import java.sql.Clob;
import java.util.Date;

public class AssetSynchronizing {
	private Long id;
	private Long syncTypeId;
	private String syncTypeCode;

	private Date processDate;
	private String description;
	private byte[] errorLog;
	private Long statusId;
	private String statusCode;
	private Long createdBy;

	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	private String syncTypeName;
	private String statusName;

	public String getSyncTypeName() {
		return syncTypeName;
	}

	public void setSyncTypeName(String syncTypeName) {
		this.syncTypeName = syncTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public AssetSynchronizing() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSyncTypeId() {
		return syncTypeId;
	}

	public void setSyncTypeId(Long syncTypeId) {
		this.syncTypeId = syncTypeId;
	}

	public String getSyncTypeCode() {
		return syncTypeCode;
	}

	public void setSyncTypeCode(String syncTypeCode) {
		this.syncTypeCode = syncTypeCode;
	}

	public byte[] getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(byte[] errorLog) {
		this.errorLog = errorLog;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
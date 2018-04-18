package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class OrganizationHierarchyUploadStage implements Serializable {
	private static final long serialVersionUID = 614707325595496349L;
	private Long uploadId;
	private Date dateFrom;
	private String orgHierType;
	private Boolean isDeptOwner;
	private String orgHierName;
	private String description;
	private String parentId;
	private String organizationId;
    private Integer rowNumber;
    private String rawData;
    private Boolean isClosed;
	private String batchNumber;
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Long getUploadId() {
		return uploadId;
	}
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getOrgHierType() {
		return orgHierType;
	}
	public void setOrgHierType(String orgHierType) {
		this.orgHierType = orgHierType;
	}
	public Boolean getIsDeptOwner() {
		return isDeptOwner;
	}
	public void setIsDeptOwner(Boolean isDeptOwner) {
		this.isDeptOwner = isDeptOwner;
	}
	public String getOrgHierName() {
		return orgHierName;
	}
	public void setOrgHierName(String orgHierName) {
		this.orgHierName = orgHierName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public Boolean getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
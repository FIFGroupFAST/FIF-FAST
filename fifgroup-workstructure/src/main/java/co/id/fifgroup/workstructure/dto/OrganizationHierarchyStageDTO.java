package co.id.fifgroup.workstructure.dto;

import java.util.Date;

import co.id.fifgroup.core.domain.UploadStage;

public class OrganizationHierarchyStageDTO extends UploadStage {
	private static final long serialVersionUID = 1066377523895539579L;
	private Date dateFrom;
	private String orgHierType;
	private Boolean isDeptOwner;
	private String orgHierName;
	private String description;
	private String parentId;
	private String organizationId;
	private String batchNumber;
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
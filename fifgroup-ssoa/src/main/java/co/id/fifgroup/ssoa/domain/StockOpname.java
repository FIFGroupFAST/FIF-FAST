package co.id.fifgroup.ssoa.domain;

import java.util.Date;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.AbstractCommonTrx;

public class StockOpname extends AbstractCommonTrx{
	private Long id;
	private Long companyId;
	private Long branchId;
	private String description;
	private Date startDate;
	private Date endDate;
	private Long soPeriodDtlId;
	private Long statusId;
	private String statusCode;
	
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private UUID avmTrxId;

	public StockOpname() {
		
	}

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

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getSoPeriodDtlId() {
		return soPeriodDtlId;
	}

	public void setSoPeriodDtlId(Long soPeriodDtlId) {
		this.soPeriodDtlId = soPeriodDtlId;
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

	public UUID getAvmTrxId() {
		return avmTrxId;
	}

	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
	}

	

	
	
	
	
}
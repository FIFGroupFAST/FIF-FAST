package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.constant.BatchStatus;

public class BatchUpload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8143361615720850246L;

	private String batchNumber;
	private Long companyId;
	private Long branchId;
	private Long formatReferenceId;
	private BatchStatus status;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private String updatedBy;
	private Date lastUpdateDate;

	private String batchNumberFrom;
	private String batchNumberTo;
	private Date processDateFrom;
	private Date processDateTo;
	
	private int number = 0;

	private List<UploadElement> elements = new ArrayList<>();
	
	public int getNumber() {
		return this.number;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
		try {
			this.number =  Integer.parseInt(batchNumber);
		} catch(NumberFormatException e) { }
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

	public Long getFormatReferenceId() {
		return formatReferenceId;
	}

	public void setFormatReferenceId(Long formatReferenceId) {
		this.formatReferenceId = formatReferenceId;
	}

	public BatchStatus getStatus() {
		return status;
	}

	public void setStatus(BatchStatus status) {
		this.status = status;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
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

	public Date getProcessDateFrom() {
		return processDateFrom;
	}

	public void setProcessDateFrom(Date processDateFrom) {
		this.processDateFrom = processDateFrom;
	}

	public Date getProcessDateTo() {
		return processDateTo;
	}

	public void setProcessDateTo(Date processDateTo) {
		this.processDateTo = processDateTo;
	}

	public List<UploadElement> getElements() {
		return elements;
	}

	public void setElements(List<UploadElement> elements) {
		this.elements = elements;
	}

	public Date getMinUploadDate() {
		if (elements.size() > 0)
			return elements.get(0).getUploadDate();
		return null;
	}

	public Date getMaxUploadDate() {
		if (elements.size() > 0)
			return elements.get(elements.size() - 1).getUploadDate();
		return null;
	}

}

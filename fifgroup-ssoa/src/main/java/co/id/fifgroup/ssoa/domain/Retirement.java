package co.id.fifgroup.ssoa.domain;

import java.util.Date;
import java.util.UUID;

import co.id.fifgroup.core.domain.transaction.AbstractCommonTrx;

public class Retirement extends AbstractCommonTrx{
	
	private Long id;
	private Long companyId;
	private Long branchId;
	private String requestNo;
	private String bastDocNo;
	private Date requestDate;
	private Long retirementTypeId;
	private String retirementTypeCode;
	private String recipient;
	private String quotationPrice;
	private String notes;
	private Long statusId;
	private String statusCode;
	private Long bastStatusId;
	private String bastStatusCode;
	private UUID avmTrxId;
	private UUID avmTrxIdBast;
	private String avmTrxIdString;
	private String avmTrxIdBastString;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private String rvNumber;

	public Retirement() {
		
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

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Long getRetirementTypeId() {
		return retirementTypeId;
	}

	public void setRetirementTypeId(Long retirementTypeId) {
		this.retirementTypeId = retirementTypeId;
	}

	public String getRetirementTypeCode() {
		return retirementTypeCode;
	}

	public void setRetirementTypeCode(String retirementTypeCode) {
		this.retirementTypeCode = retirementTypeCode;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public Long getBastStatusId() {
		return bastStatusId;
	}

	public void setBastStatusId(Long bastStatusId) {
		this.bastStatusId = bastStatusId;
	}

	public String getBastStatusCode() {
		return bastStatusCode;
	}

	public void setBastStatusCode(String bastStatusCode) {
		this.bastStatusCode = bastStatusCode;
	}

	public UUID getAvmTrxId() {
		return avmTrxId;
	}

	public void setAvmTrxId(UUID avmTrxId) {
		this.avmTrxId = avmTrxId;
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


	public String getAvmTrxIdString() {
		return avmTrxIdString;
	}


	public void setAvmTrxIdString(String avmTrxIdString) {
		this.avmTrxIdString = avmTrxIdString;
	}


	public String getQuotationPrice() {
		return quotationPrice;
	}


	public void setQuotationPrice(String quotationPrice) {
		this.quotationPrice = quotationPrice;
	}


	public UUID getAvmTrxIdBast() {
		return avmTrxIdBast;
	}


	public void setAvmTrxIdBast(UUID avmTrxIdBast) {
		this.avmTrxIdBast = avmTrxIdBast;
	}


	public String getAvmTrxIdBastString() {
		return avmTrxIdBastString;
	}


	public void setAvmTrxIdBastString(String avmTrxIdBastString) {
		this.avmTrxIdBastString = avmTrxIdBastString;
	}


	public String getBastDocNo() {
		return bastDocNo;
	}


	public void setBastDocNo(String bastDocNo) {
		this.bastDocNo = bastDocNo;
	}


	public String getRvNumber() {
		return rvNumber;
	}


	public void setRvNumber(String rvNumber) {
		this.rvNumber = rvNumber;
	}

	
	
	
}

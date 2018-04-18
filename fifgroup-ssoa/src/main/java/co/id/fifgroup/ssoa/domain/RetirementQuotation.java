package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class RetirementQuotation {
	private Long id;
	private Long retirementId;
	private String vendorName;
	private String quotationPrice;
	private Boolean winnerFlag;
	private String docFileName;
	private String docFilePath;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	public RetirementQuotation() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRetirementId() {
		return retirementId;
	}

	public void setRetirementId(Long retirementId) {
		this.retirementId = retirementId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Boolean getWinnerFlag() {
		return winnerFlag;
	}

	public void setWinnerFlag(Boolean winnerFlag) {
		this.winnerFlag = winnerFlag;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public String getDocFilePath() {
		return docFilePath;
	}

	public void setDocFilePath(String docFilePath) {
		this.docFilePath = docFilePath;
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

	public String getQuotationPrice() {
		return quotationPrice;
	}

	public void setQuotationPrice(String quotationPrice) {
		this.quotationPrice = quotationPrice;
	}

	

	

	
	

	
	
	
	
}
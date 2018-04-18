package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class StockOpnameReporting {
	private Long id;
	private Long stockOpnameId;
	private String reportNo;
	private Long statusId;
	private String statusCode;
	private String status;
	private Date reportDate;
	private String remark;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	private String avmTrxId;
	
	public StockOpnameReporting() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockOpnameId() {
		return stockOpnameId;
	}

	public void setStockOpnameId(Long stockOpnameId) {
		this.stockOpnameId = stockOpnameId;
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

	public String getAvmTrxId() {
		return avmTrxId;
	}

	public void setAvmTrxId(String avmTrxId) {
		this.avmTrxId = avmTrxId;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
	
	
	
}
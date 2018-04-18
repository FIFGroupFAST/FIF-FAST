package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class RetirementDetail {
	
	private Long id;
	private Long retirementId;
	private Long stockOpnameDtlId;
	private Long assetId;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	

	public RetirementDetail() {
		
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


	public Long getStockOpnameDtlId() {
		return stockOpnameDtlId;
	}


	public void setStockOpnameDtlId(Long stockOpnameDtlId) {
		this.stockOpnameDtlId = stockOpnameDtlId;
	}


	public Long getAssetId() {
		return assetId;
	}


	public void setAssetId(Long assetId) {
		this.assetId = assetId;
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

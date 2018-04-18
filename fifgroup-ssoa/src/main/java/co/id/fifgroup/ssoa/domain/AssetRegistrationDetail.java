package co.id.fifgroup.ssoa.domain;


import java.util.Date;
public class AssetRegistrationDetail {
	private boolean check;
	private Long registrationDtlId;
	private Long registrationId;
	private Long stockOpnameUnregAssetId;	
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Long getRegistrationDtlId() {
		return registrationDtlId;
	}

	public void setRegistrationDtlId(Long registrationDtlId) {
		this.registrationDtlId = registrationDtlId;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public Long getStockOpnameUnregAssetId() {
		return stockOpnameUnregAssetId;
	}

	public void setStockOpnameUnregAssetId(Long stockOpnameUnregAssetId) {
		this.stockOpnameUnregAssetId = stockOpnameUnregAssetId;
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

	public AssetRegistrationDetail() {
		
	}
	
		
	
}

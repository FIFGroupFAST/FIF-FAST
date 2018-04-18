package co.id.fifgroup.ssoa.domain;


import java.util.Date;
public class AssetTransferDetail {
	private boolean check;
	private Long transferDtlId;
	private Long transferId;
	private Long stockOpnameDtlId;	
	private Long assetId;	
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

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public Long getTransferDtlId() {
		return transferDtlId;
	}

	public void setTransferDtlId(Long transferDtlId) {
		this.transferDtlId = transferDtlId;
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

	public AssetTransferDetail() {
		
	}
	
		
	
}

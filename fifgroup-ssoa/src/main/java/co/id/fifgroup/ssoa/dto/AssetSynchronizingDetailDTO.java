package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.AssetSynchronizingDetail;


public class AssetSynchronizingDetailDTO extends AssetSynchronizingDetail {
	
	private static final long serialVersionUID = 8153696528893553702L;
	
	private AssetSynchronizingDTO assetSynchronizingDto;
	private String userName;
	private String transactionType;
	private String assetType;
	private String ebsRetireStatus;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AssetSynchronizingDTO getAssetSynchronizingDto() {
		return assetSynchronizingDto;
	}

	public void setAssetSynchronizingDto(AssetSynchronizingDTO assetSynchronizingDto) {
		this.assetSynchronizingDto = assetSynchronizingDto;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getEbsRetireStatus() {
		return ebsRetireStatus;
	}

	public void setEbsRetireStatus(String ebsRetireStatus) {
		this.ebsRetireStatus = ebsRetireStatus;
	}
	
	
	
}

package co.id.fifgroup.ssoa.dto;

import java.util.List;

import co.id.fifgroup.ssoa.domain.AssetSynchronizing;

public class AssetSynchronizingDTO extends AssetSynchronizing {

	private static final long serialVersionUID = 8153696528893553702L;

	private List<AssetSynchronizingDetailDTO> assetSynchronizingDetailDtoList;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<AssetSynchronizingDetailDTO> getAssetSynchronizingDetailDtoList() {
		return assetSynchronizingDetailDtoList;
	}

	public void setAssetSynchronizingDetailDtoList(List<AssetSynchronizingDetailDTO> assetSynchronizingDetailDtoList) {
		this.assetSynchronizingDetailDtoList = assetSynchronizingDetailDtoList;
	}

}

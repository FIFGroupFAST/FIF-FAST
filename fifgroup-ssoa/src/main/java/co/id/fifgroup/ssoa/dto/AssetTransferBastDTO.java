package co.id.fifgroup.ssoa.dto;

import java.util.List;

import co.id.fifgroup.ssoa.domain.AssetTransferBast;

public class AssetTransferBastDTO extends AssetTransferBast {
	
	private static final long serialVersionUID = -3283726987209046312L;
	
	private List<AssetTransferBastDTO> assetTransferBastDetail;
	
	public List<AssetTransferBastDTO> getAssetTransferBastDetail() {
		return assetTransferBastDetail;
	}
	public void setAssetTransferBastDetail(List<AssetTransferBastDTO> assetTransferBastDetail) {
		this.assetTransferBastDetail = assetTransferBastDetail;
	}
	
	
	
	
	
	
}

package co.id.fifgroup.ssoa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;

public interface AssetSynchronizingDetailMapper {
	public List<AssetSynchronizingDetailDTO> getAssetSynchronizingDetailDtoByAssetSynchronizingId(@Param("assetSynchronizingId") Long assetSynchronizingId);
	public void insert(AssetSynchronizingDetailDTO record);
}
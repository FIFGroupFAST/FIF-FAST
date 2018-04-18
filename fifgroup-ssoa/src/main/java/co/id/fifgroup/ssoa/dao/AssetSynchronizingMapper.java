package co.id.fifgroup.ssoa.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetSyncronizingExample;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;

public interface AssetSynchronizingMapper {
	public AssetSynchronizingDTO getAssetSynchronizingDtoById(@Param("assetSynchronizingId") Long assetSynchronizingId);

	public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);
	public List<AssetSynchronizingDTO> getAssetSynchronizingDto(AssetSyncronizingExample example,RowBounds rowBounds);
	public List<AssetSynchronizingDTO> getAssetSynchronizingDto(AssetSyncronizingExample example);

	public int getCountAssetSynchronizingDto(AssetSyncronizingExample example);
	
	public void insert(AssetSynchronizingDTO record);
	
	public int updateByPrimaryKeySelective(AssetSynchronizingDTO record);
}
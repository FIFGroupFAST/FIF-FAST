package co.id.fifgroup.ssoa.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetSyncronizingExample;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;

public interface AssetSynchronizingService {
	public AssetSynchronizingDTO getAssetSynchronizingDtoById(Long id);

	public List<AssetSynchronizingDetailDTO> getAssetSynchronizingDetailDtoByAssetSynchronizingId(
			Long assetSynchronizingId);

	public List<AssetSynchronizingDTO> getAssetSynchronizingDto(AssetSyncronizingExample example, int limit, int offset);
	
	public List<AssetSynchronizingDTO> getAssetSynchronizingDto(AssetSyncronizingExample example);

	public int getCountAssetSynchronizingDto(AssetSyncronizingExample example);

	public void save(AssetSynchronizingDTO assetSynchronizingDTO) throws Exception;

	public int update(AssetSynchronizingDTO assetSynchronizingDTO) throws Exception;
	
	public List<KeyValue> getStatusAssetSync();

}
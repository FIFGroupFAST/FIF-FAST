package co.id.fifgroup.ssoa.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.constants.AssetSyncronizingStatus;
import co.id.fifgroup.ssoa.constants.SOPeriodStatus;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingMapper;
import co.id.fifgroup.ssoa.domain.AssetSyncronizingExample;
import co.id.fifgroup.ssoa.domain.SOPeriodExample;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.service.AssetSynchronizingService;


@Transactional
@Service("assetSynchronizingService")
public class AssetSynchronizingServiceImpl implements AssetSynchronizingService {

	@Autowired
	private AssetSynchronizingMapper assetSynchronizingMapper;

	@Autowired
	private AssetSynchronizingDetailMapper assetSynchronizingDetailMapper;

	@Override
	@Transactional(readOnly = true)
	public AssetSynchronizingDTO getAssetSynchronizingDtoById(Long assetSynchronizingId) {
		return assetSynchronizingMapper.getAssetSynchronizingDtoById(assetSynchronizingId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getStatusAssetSync() {
		return assetSynchronizingMapper.getLookupKeyValues(AssetSyncronizingStatus.CODE.getCode(), null);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetSynchronizingDetailDTO> getAssetSynchronizingDetailDtoByAssetSynchronizingId(Long id) {
		return assetSynchronizingDetailMapper.getAssetSynchronizingDetailDtoByAssetSynchronizingId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetSynchronizingDTO> getAssetSynchronizingDto(AssetSyncronizingExample example,  int limit, int offset) {
		return assetSynchronizingMapper.getAssetSynchronizingDto(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetSynchronizingDTO> getAssetSynchronizingDto(AssetSyncronizingExample example) {
		return assetSynchronizingMapper.getAssetSynchronizingDto(example);
	}

	@Override
	@Transactional(readOnly = true)
	public int getCountAssetSynchronizingDto(AssetSyncronizingExample example) {
		return assetSynchronizingMapper.getCountAssetSynchronizingDto(example);
	}

	@Override
	@Transactional(readOnly = true)
	public void save(AssetSynchronizingDTO assetSynchronizingDTO) throws Exception {
		assetSynchronizingMapper.insert(assetSynchronizingDTO);
	}

	@Override
	@Transactional(readOnly = true)
	public int update(AssetSynchronizingDTO assetSynchronizingDTO) throws Exception {
		return assetSynchronizingMapper.updateByPrimaryKeySelective(assetSynchronizingDTO);
	}

}

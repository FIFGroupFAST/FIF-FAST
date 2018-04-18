package co.id.fifgroup.ssoa.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.id.fifgroup.ssoa.dao.AssetsMapper;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.finder.AssetFinder;
import co.id.fifgroup.ssoa.service.AssetsService;

@Transactional
@Service("assetsService")
public class AssetsServiceImpl implements AssetsService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AssetsMapper assetsMapper;
	
	@Autowired
	private AssetFinder assetFinder;
	
	@Autowired
	private BranchMapper branchMapper;
	
	
	public Branch getBranchById(Long id,Long companyId) {
		return branchMapper.selectByPrimaryKey(id,companyId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<AssetDTO> getAssetDtoByExample(Assets example, int limit, int offset, Long taskGroupId) {
		return assetFinder.selectByExample(example, new RowBounds(offset, limit), taskGroupId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countAssetByCriteria(AssetsExample example) {
		return assetsMapper.countAssetByCriteria(example);
		
	}


	@Override
	@Transactional(readOnly = true)
	public List<AssetDTO> getAssetByCriteria(AssetsExample example, int limit, int offset) {
		return assetsMapper.selectAssetByBranchId(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetDTO> getAssetByCriteria(AssetsExample example) {
		return assetsMapper.selectAssetByBranchId(example);
	}


}
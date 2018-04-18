package co.id.fifgroup.ssoa.service;

import java.util.List;

import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;


public interface AssetsService{

	public Branch getBranchById(Long id,Long companyId);
	public List<AssetDTO> getAssetDtoByExample(Assets example, int limit, int offset, Long taskGroupId);
	
	public int countAssetByCriteria(AssetsExample example);
	public List<AssetDTO> getAssetByCriteria(AssetsExample example, int limit, int offset);
	public List<AssetDTO> getAssetByCriteria(AssetsExample example);
}

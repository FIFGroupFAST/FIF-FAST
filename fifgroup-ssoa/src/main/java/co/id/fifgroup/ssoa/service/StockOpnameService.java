package co.id.fifgroup.ssoa.service;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetCategory;
import co.id.fifgroup.ssoa.domain.AssetCategoryExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.domain.StockOpnameDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssetsExample;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;


public interface StockOpnameService extends WorkflowService{
	
	public List<StockOpnameDTO> getStockOpnameDtoByExample(StockOpnameExample example, int limit, int offset);
	
	public StockOpnameDTO getStockOpnameById(Long id);
	public StockOpnameDTO getStockOpnameByAvmTrxId(String avmTrxId);
	public List<StockOpnameImgDTO> getStockOpnameImgByStockOpnameDtlId(Long id);
	public List<StockOpnameUnregAssetImgDTO> getStockOpnameUnregAssetImgBySOUnregAssetId(Long id);
	
	public List<StockOpnameDTO> getStockOpnameByExample(StockOpnameExample example, int limit, int offset);
	public List<StockOpnameDTO> getStockOpnameByExample(StockOpnameExample example);
	public List<StockOpnameDetailDTO> getStockOpnameDtlByExample(StockOpnameDetailExample example);
	public List<StockOpnameDetailDTO> getStockOpnameDtlByExampleWithRowbounds(StockOpnameDetailExample example, int limit, int offset);
	public int countStockOpnameByExample(StockOpnameExample example);
	public int countSODtlByExample(StockOpnameDetailExample example);
	
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset);
	public int countBranchByExample(BranchExample example);
	public List<AssetCategory> getAssetCategoryByExample(AssetCategoryExample example, int limit, int offset);
	public int countAssetCategoryByExample(AssetCategoryExample example);
	public AssetCategory getAssetCategoryById(Long id);
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset);
	public int countLocationByExample(SSOALocationExample example);
	public List<Assets> getAssetsByExample(AssetsExample example, int limit, int offset);
	public int countAssetsByExample(AssetsExample example);
	public Branch getBranchById(Long id,Long companyId);
	public SSOALocation getLocationById(Long id);
	public Assets getAssetsById(Long id);
	
	public int countByExample(StockOpnameExample example);
	public void save(StockOpnameDTO stockOpnameDto) throws Exception;
	public void saveImage(StockOpnameDetailDTO stockOpnameDetail) throws Exception;
	public void submit(StockOpnameDTO stockOpnameDTO, UUID personUUID,String note) throws Exception;
	
	public List<Branch> getBranchAll();

	public List<StockOpnameUnregAssetsDTO> getStockOpnameUnregAssetsByExample(StockOpnameUnregAssetsExample example);
	public List<KeyValue> getLookupKeyValues(String lookupName,String key);
	public List<KeyValue> getSOResultList();
	public List<KeyValue> getSORecommendList();
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code);
	public StockOpnameDTO getOnePersonByBranchId(Long branchId, Long companyId);
	public int countSONotClosed(Long branchId,Long companyId);
	public int countTransferNotClosed(Long branchId,Long companyId);
	public int countRetirementNotClosed(Long branchId,Long companyId);
	
	public int countDecsriptionByExample(AssetsExample example);
	public List<Assets> getDescriptionByExample(AssetsExample example, int limit, int offset);
	public int countAssetByBranchCode(Long branchId,Long companyId);
	public List<StockOpnameDTO> getResponsibilityIdSO();
	public List<StockOpnameDTO> getResponsibilityIdAT();
	
}

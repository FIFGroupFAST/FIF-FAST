package co.id.fifgroup.ssoa.service;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.AssetTransferDetail;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.AssetTransferExample;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
//import co.id.fifgroup.ssoa.domain.StockOpnameImg;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
//import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
//import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;

import java.util.List;
import java.util.UUID;


public interface AssetTransferService extends WorkflowService{
	
	public List<AssetTransferDTO> getAssetTransferDtoByExample(AssetTransferExample example, int limit, int offset);
	public List<AssetTransferDTO> getAssetTransferDtoByExample(AssetTransferExample example);
	
	public AssetTransferDTO getAssetTransferById(Long id);
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code);
	public int countAssetSODtlByCriteria(AssetTransferDetailExample example);
	public List<AssetTransferDetailDTO> getAssetSODtlByCriteria(AssetTransferDetailExample example, int limit, int offset,String action);
	public AssetTransferDetailDTO getSOResultByAssetId(Long assetId);
	public List<AssetTransferDTO> getAssetTransferByExample(AssetTransferExample example, int limit, int offset);
	public List<AssetTransferDTO> getAssetTransferByExample(AssetTransferExample example);
	public int countAssetTransferByExample(AssetTransferExample example);
	
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset);
	public int countBranchByExample(BranchExample example);
	public Branch getBranchById(Long id,Long companyId);
	
	public int countByExample(AssetTransferExample example);
	
	public void submit(AssetTransferDTO assetTransferDTO, UUID personUUID, List<AssetTransferDetailDTO> assetTransferDetailDTO )
			throws Exception;
	
	public List<Branch> getBranchAll();	

	public int countByBranchId(Assets example,Long taskGroupId);

	public List<AssetTransferDetailDTO> getDetailAssetsByPrimaryKey(Long id);
	
	public List<AssetTransferBast> getDetailBastByPrimaryKey(Long id);

	public void submitBast(AssetTransferBastDTO assetTransferBastDTO) throws FifException, Exception;
	
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset);
	
	public int countLocationByExample(SSOALocationExample example);

	List<KeyValue> getLookupKeyValues(String lookupName, String key);

	public AssetTransferDTO getAssetTransferByAvmTrxId(String avmTrxId);
	
	public void printBAST(AssetTransferDTO assetTransferDTO) throws Exception;

}

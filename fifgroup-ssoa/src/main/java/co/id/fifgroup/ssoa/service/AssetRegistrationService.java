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
import co.id.fifgroup.ssoa.domain.AssetRegistration;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetail;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetailExample;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import java.util.List;
import java.util.UUID;


public interface AssetRegistrationService extends WorkflowService{
	
	public List<AssetRegistrationDTO> getAssetRegistrationDtoByExample(AssetRegistrationExample example, int limit, int offset);
	public List<AssetRegistrationDTO> getAssetRegistrationDtoByExample(AssetRegistrationExample example);
	
	public AssetRegistrationDTO getAssetRegistrationById(Long id);
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code);
	public int countAssetSODtlByCriteria(AssetRegistrationDetailExample example);
	public List<AssetRegistrationDetailDTO> getAssetSODtlByCriteria(AssetRegistrationDetailExample example, int limit, int offset,String action);
	public AssetRegistrationDetailDTO getSOResultByAssetId(Long assetId);
	public List<AssetRegistrationDTO> getAssetRegistrationByExample(AssetRegistrationExample example, int limit, int offset);
	public List<AssetRegistrationDTO> getAssetRegistrationByExample(AssetRegistrationExample example);
	public int countAssetRegistrationByExample(AssetRegistrationExample example);
	
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset);
	public int countBranchByExample(BranchExample example);
	public Branch getBranchById(Long id,Long companyId);
	
	public int countByExample(AssetRegistrationExample example);
	
	public void save(AssetRegistrationDTO assetRegiistrationDTO, UUID personUUID, List<AssetRegistrationDetailDTO> assetRegistrationDetailDTO )
			throws Exception;
	
	public List<Branch> getBranchAll();	

	public int countByBranchId(Assets example,Long taskGroupId);

	public List<AssetRegistrationDetailDTO> getDetailAssetsByPrimaryKey(Long id);
	
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset);
	
	public int countLocationByExample(SSOALocationExample example);

	List<KeyValue> getLookupKeyValues(String lookupName, String key);

	public AssetRegistrationDTO getAssetRegistrationByAvmTrxId(String avmTrxId);

}

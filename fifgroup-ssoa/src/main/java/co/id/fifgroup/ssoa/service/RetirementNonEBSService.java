package co.id.fifgroup.ssoa.service;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.RV;
import co.id.fifgroup.ssoa.domain.RVExample;
import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.domain.RetirementDetail;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.domain.RetirementImg;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementQuotationDTO;
import co.id.fifgroup.ssoa.dto.RetirementRVDTO;


public interface RetirementNonEBSService extends WorkflowService{
	
	public List<RetirementDTO> getRetirementDtoByExample(RetirementExample example, int limit, int offset);
	public List<RetirementDTO> getRetirementDtoByExample(RetirementExample example);
	public int countByExample(RetirementExample example); 
	public List<KeyValue> getLookupKeyValues(String lookupName, String key);
	
	public Branch getBranchById(Long id,Long companyId);
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset);
	public int countBranchByExample(BranchExample example);
	
	public List<Assets> getAssetsByExample(AssetsExample example, int limit, int offset);
	public int countAssetsByExample(AssetsExample example);
	
	public List<RetirementDetailDTO> getAssetSODtlByCriteria(RetirementDetailExample example, int limit, int offset,String action);
	public int countAssetSODtlByCriteria(RetirementDetailExample example,String action);
	public void save(RetirementDTO retirementDTO, UUID personUUID,List<RetirementDetailDTO> listDetail,List<RetirementQuotationDTO> listQuotation) throws FifException, Exception;
	public void saveRV(RetirementDTO retirementDTO, List<RetirementRVDTO> listRV) throws FifException, Exception;
	
	/*public void submitBAST(RetirementDTO retirementDTO, UUID personUUID, List<RetirementBastDTO> listBast) throws FifException, Exception;*/
	public RetirementDTO getRetirementById(Long id);
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code);
	public RetirementDTO getRetirementByAvmTrxId(String avmTrxId);
	public RetirementDTO getRetirementByAvmTrxIdBast(String avmTrxId);
	public void printBAST(RetirementDTO retirementDTO) throws Exception;
	public RetirementDetailDTO getSOResultByAssetId(Long assetId);
	public List<RetirementBast> getDetailBastByPrimaryKey(Long id);
	public List<RetirementImg> getDetailImageByPrimaryKey(Long id);
	public RetirementDetailDTO getDetailIdForImages(Long id, Long assetId);
	public void createRV(RetirementDTO retirementDTO)throws Exception;
	public String getBastDocumentNumber(String branchName);
	public List<RV> getRVByCriteria(RVExample example, int limit, int offset,String action);
	public int countRVByCriteria(RVExample example,String action);
	
	

	
}
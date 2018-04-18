package co.id.fifgroup.ssoa.service;

import java.util.List;

import org.zkoss.util.media.AMedia;

import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetLabeling;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetail;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetailExample;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.Parameter;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;

public interface AssetLabelingService {
	public List<AssetLabeling> findAllAsset();
	public List<AssetLabeling> findAll();
	public List<AssetLabeling> search(String param1);
	public List<AssetLabeling> remove(Boolean param1);
	public int countAssetByCriteria(AssetLabelingDetailExample example);
	public List<AssetLabelingDetailDTO> getAssetByCriteria(AssetLabelingDetailExample example, int limit, int offset);
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset);
	public int countBranchByExample(BranchExample example);
	public Branch getBranchById(Long id,Long companyId);
	public List<KeyValue> getLabelingPrintingType();
	public AMedia save(AssetLabelingDTO assetLabelingDTO) throws Exception;
	
	public void delete(AssetLabelingDTO assetLabelingDTO) throws Exception;
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset);
	
	public int countLocationByExample(SSOALocationExample example);
	public List<AssetLabelingDTO> getParameterDtoByExample(AssetLabelingExample example, int limit, int offset);
	public List<AssetLabelingDTO> getParameterDtoByExample(AssetLabelingExample example);
	public int countByExample(AssetLabelingExample example); 
	public AssetLabelingDTO getParameterById(Long id);
	public AssetLabelingDetail getDetailByPrimaryKey(Long id);
	public List<AssetLabelingDetailDTO> getAssetLabelingDetailDtoByParameterId(Long id);
	public List<AssetLabelingDetailDTO> getDetailAssetsByPrimaryKey(Long id);
}
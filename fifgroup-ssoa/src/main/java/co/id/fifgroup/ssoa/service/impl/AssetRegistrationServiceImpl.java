package co.id.fifgroup.ssoa.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dao.CompanyFinder;
import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dao.LookupFinderMapper;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.ContentType;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.SOPeriodMapper;
import co.id.fifgroup.ssoa.dao.SSOALocationMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameDetailMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameUnregAssetsMapper;
import co.id.fifgroup.ssoa.common.SSOACommons;
import co.id.fifgroup.ssoa.constants.AssetRegistrationApprovalStatus;
import co.id.fifgroup.ssoa.constants.ProcessType;
import co.id.fifgroup.ssoa.constants.SOActionStatus;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingMapper;
import co.id.fifgroup.ssoa.dao.AssetRegistrationDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetRegistrationMapper;
import co.id.fifgroup.ssoa.dao.AssetsMapper;
import co.id.fifgroup.ssoa.domain.AssetRegistration;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetail;
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetailExample;
import co.id.fifgroup.ssoa.domain.AssetRegistrationExample;
import co.id.fifgroup.ssoa.domain.AssetTransferDetail;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;

import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.finder.AssetRegistrationFinder;
import co.id.fifgroup.ssoa.service.AssetRegistrationService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JRDataSource;

@Transactional
@Service("assetRegistrationService")
public class AssetRegistrationServiceImpl implements AssetRegistrationService {

	// Task Runner Entity
	@Autowired
	private AssetRegistrationMapper assetRegistrationMapper;
	
	@Autowired
	private SOPeriodMapper soPeriodMapper;
	
	@Autowired
	private AssetSynchronizingDetailMapper assetSynchronizingDetailMapper;
	
	@Autowired
	private AssetSynchronizingMapper assetSynchronizingMapper;
	
	@Autowired
	private AssetsMapper assetsMapper;
	
	@Autowired
	private StockOpnameMapper stockOpnameMapper;
	
	@Autowired
	private StockOpnameDetailMapper stockOpnameDtlMapper;
	
	@Autowired
	private StockOpnameUnregAssetsMapper stockOpnameUnregAssetsMapper;

	@Autowired
	private LookupFinderMapper lookupFinderMapper;
	
	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;
	
	
	@Autowired
	private AssetRegistrationFinder assetRegistrationFinder;

	@Autowired
	private CompanyFinder companyFinder;
	
	@Autowired
	private AssetRegistrationDetailMapper assetRegistrationDetailMapper;

	@Autowired
	private SSOALocationMapper ssoaLocationMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BranchMapper branchMapper;

	public static AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) ApplicationContextUtil.getApplicationContext().getBean("avmAdapterServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}

	public static CompanyService getCompanyService() {
		return (CompanyService) ApplicationContextUtil.getApplicationContext().getBean("companyServiceImpl");
	}
	
	public static OrganizationSetupService getOrganizationSetupService() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	public static OrganizationSetupService getCostCenterByPersonId() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	
	

	@Autowired
	private SecurityService securityServiceImpl;

	@Autowired
	private DataSource dataSource;
	
	@Override
	@Transactional(readOnly = true)
	
	public AssetRegistrationDTO getAssetRegistrationById(Long id) {
		AssetRegistrationDTO assetRegistrationDto = new AssetRegistrationDTO();
		AssetRegistration assetRegistration = assetRegistrationMapper.selectByPrimaryKey(id);

		assetRegistrationDto = modelMapper.map(assetRegistration, AssetRegistrationDTO.class);
		List<AssetRegistrationDetailDTO> assetRegistrationDetail = assetRegistrationDetailMapper.selectByHeaderKey(id);
		assetRegistrationDto.setAssetRegistrationDetail(assetRegistrationDetail);
		
		/*List<AssetTransferBastDTO> assetTransferBastDTO = (List<AssetTransferBastDTO>) assetTransferBastMapper.selectByHeaderKey(id);
		retirementDto.setRetirementBast(retirementBastDTO);
		*/
		return assetRegistrationDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetRegistrationDTO> getAssetRegistrationByExample(AssetRegistrationExample example, int limit, int offset) {
		return assetRegistrationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetRegistrationByExample(AssetRegistrationExample example) {
		return assetRegistrationMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetSODtlByCriteria(AssetRegistrationDetailExample example) {
		return assetRegistrationDetailMapper.countAssetSODtlByCriteria(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetRegistrationDetailDTO> getAssetSODtlByCriteria(AssetRegistrationDetailExample example, int limit, int offset, String action) {
		return assetRegistrationDetailMapper.selectAssetSODtlByCriteria(example, new RowBounds(offset, limit));
	}
	
	public AssetRegistrationDetailDTO getSOResultByAssetId(Long assetId) {
		return assetRegistrationDetailMapper.getSOResultByAssetId(assetId);
	}
	
	@Transactional(readOnly = true)
	public List<AssetRegistrationDTO> getAssetRegistrationDtoByExample(AssetRegistrationExample example, int limit, int offset) {
		return assetRegistrationFinder.selectByExample(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public List<AssetRegistrationDTO> getAssetRegistrationDtoByExample(AssetRegistrationExample example) {
		return assetRegistrationFinder.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetRegistrationDTO> getAssetRegistrationByExample(AssetRegistrationExample example) {
		return assetRegistrationMapper.selectByExample(example);
	}

	
	@Transactional(readOnly = true)
	public int countByExample(AssetRegistrationExample example) {
		return assetRegistrationFinder.countByExample(example);
	}


	public Branch getBranchById(Long id,Long companyId) {
		return branchMapper.selectByPrimaryKey(id,companyId);
	}

	public List<Branch> getBranchAll() {
		return branchMapper.selectBranch();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset) {
		return branchMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countBranchByExample(BranchExample example) {
		return branchMapper.countByExample(example);
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public void save(AssetRegistrationDTO assetRegistrationDto, UUID personUUID, List<AssetRegistrationDetailDTO> assetRegistrationDetailDTO ) throws Exception{
		
		assetRegistrationDto.setAvmTrxId(UUID.randomUUID());
		assetRegistrationDto.setAvmTrxIdString(assetRegistrationDto.getAvmTrxId().toString());
		
		AssetRegistration assetRegistration = assetRegistrationMapper.selectByPrimaryKey(assetRegistrationDto.getRegistrationId());
		if (assetRegistration == null || assetRegistration.getRegistrationId() == null) {

			AssetRegistration asset = assetRegistrationMapper.getBranchCode(assetRegistrationDto.getBranchId());
			
			String branchCode;
			if (assetRegistrationDto.getBranchId() == -1){
				branchCode = "HEADOFFICE"; 
			}else {
				branchCode = asset.getBranchCode();
			}
			
			String docNo = getDocumentNumber(branchCode);
			assetRegistrationDto.setRegistrationNo(docNo);
			assetRegistrationDto.setBranchId(assetRegistrationDto.getBranchId());
			assetRegistrationDto.setStatusCode(AssetRegistrationApprovalStatus.IN_PROCESS.getCode());
			assetRegistrationDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetRegistrationDto.setLastUpdateDate(new Date());
			assetRegistrationMapper.insert(assetRegistrationDto);
			
			for (AssetRegistrationDetail assetDetail : assetRegistrationDetailDTO) {
				AssetRegistrationDetail assetRegistrationDetail = modelMapper.map(assetDetail, AssetRegistrationDetail.class);
				assetRegistrationDetail.setRegistrationId(assetRegistrationDto.getRegistrationId());
				assetRegistrationDetail.setStockOpnameUnregAssetId(assetDetail.getStockOpnameUnregAssetId());
				assetRegistrationDetail.setCreatedBy(assetRegistrationDto.getCreatedBy());
				assetRegistrationDetail.setCreationDate(new Date());
				assetRegistrationDetail.setLastUpdateBy(assetRegistrationDto.getCreatedBy());
				assetRegistrationDetail.setLastUpdateDate(new Date());
				
				assetRegistrationDetailMapper.insert(assetRegistrationDetail);

			}
			
		assetRegistrationDto.setObjectEmployeeUUID(personUUID);
		getAvmAdapterService().submitAvmTransaction(assetRegistrationDto.getAvmTrxId(), personUUID, assetRegistrationDto,
				co.id.fifgroup.systemworkflow.constants.TrxType.ASSET_REGISTRATION, 1L);
					
		}
	}
	
	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		return assetRegistrationMapper.getLookupKeyValues(lookupName, key);
	}
	
	@Override
	public void doAfterApproved(Object transaction) throws Exception {
		AssetRegistrationDTO assetRegistrationDTO = (AssetRegistrationDTO) transaction;
		List<KeyValue> listLookup = (List<KeyValue>)getLookupKeyValues(AssetRegistrationApprovalStatus.CODE.getCode(),AssetRegistrationApprovalStatus.APPROVED.getCode());
		if(listLookup.size()>0){
			KeyValue key = (KeyValue)listLookup.get(0);
			assetRegistrationDTO.setStatusCode((String)key.getValue());
			assetRegistrationDTO.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
		}
		//update asset Registration
		assetRegistrationMapper.updateByPrimaryKey(assetRegistrationDTO);
		
		//Insert into soc assets
		List<AssetRegistrationDTO> assetsList = assetRegistrationMapper.selectUnregAssetByBranchId(assetRegistrationDTO.getBranchId(), assetRegistrationDTO.getCompanyId(), assetRegistrationDTO.getRegistrationId());
		for (AssetRegistrationDTO assets : assetsList) {
			AssetRegistrationDTO asset = new AssetRegistrationDTO();
			asset.setTagNumber(assets.getTagNumber());
			asset.setSerialNumber(assets.getSerialNumber());
			asset.setDescription(assets.getDescription());
			asset.setCategoryId(assets.getCategoryId());
			asset.setCompanyId(assets.getCompanyId());
			asset.setBranchId(assets.getBranchId());
			asset.setLocationId(assets.getLocationId());
			asset.setPosition(assets.getPosition());
			asset.setDatePlacedInService(assets.getDatePlacedInService());
			asset.setLifeInMonths(assets.getLifeInMonths());
			asset.setOriginalCost(assets.getOriginalCost());
			asset.setUnrevaluedCost(assets.getUnrevaluedCost());
			asset.setBookTypeCode("COMMERCIAL FIF");
			asset.setRetiredFlag("N");
			asset.setAssetTypeId(assets.getAssetTypeId());
			asset.setAssetTypeCode(assets.getAssetTypeCode());
			asset.setNetBookValue(Long.parseLong("0"));
			asset.setCreatedBy(assets.getCreatedBy());
			asset.setCreationDate(new Date());
			asset.setLastUpdateDate(new Date());
			asset.setLastUpdateBy(assets.getLastUpdateBy());
			assetRegistrationMapper.insertSocAsset(asset);
			
			assetRegistrationMapper.updateAssetIdSOUnregAsset(assets);
		}
		
	}
	
	@Override
	public void doAfterRejected(Object transaction) throws Exception {
		AssetRegistrationDTO assetTransferDTO = (AssetRegistrationDTO) transaction;
		
		List<KeyValue> list = (List<KeyValue>)getLookupKeyValues(AssetRegistrationApprovalStatus.CODE.getCode(),AssetRegistrationApprovalStatus.REJECTED.getCode());
		if(list.size()>0){
			KeyValue key = (KeyValue)list.get(0);
			assetTransferDTO.setStatusCode((String)key.getValue());
			assetTransferDTO.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
		}
		assetRegistrationMapper.updateByPrimaryKey(assetTransferDTO);
	}
	

	
	@Override
	public void doAfterCanceled(Object transaction) throws Exception {
		
	}

	@Override
	public void doValidateBeforeApprove(Object transaction) throws ValidationException {
		
	}

	@Override
	public void doValidateBeforeReject(Object transaction) throws ValidationException {
		
	}

	@Override
	public void doApprove(Object transaction, UUID avmTrxId, UUID approverId) throws Exception {
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetRegistrationDetailDTO> getDetailAssetsByPrimaryKey(Long id) {
		return assetRegistrationDetailMapper.selectDetailByHeaderId(id);	
	}	
	

	@Override
	@Transactional(readOnly = true)
	public int countByBranchId(Assets example,  Long taskGroupId) {
		return assetRegistrationFinder.countAssetByBranchId(example, taskGroupId );
	}

	
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}

	
	
	public AssetRegistrationDTO getAssetRegistrationByAvmTrxId(String avmTrxId) {
		return assetRegistrationMapper.selectByAvmTrxId(avmTrxId);
	}
	
	public String getDocumentNumber(String branchId){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		AssetRegistrationDTO assetRegistrationDTO = (AssetRegistrationDTO)assetRegistrationMapper.getLastRegistrationNo();
		
		int no =0;
		String lastYear = null;
		if(assetRegistrationDTO != null){
			String lastRegistrationNo = assetRegistrationDTO.getRegistrationNo();
			no = Integer.parseInt(lastRegistrationNo.substring(0, 3));
			lastYear = lastRegistrationNo.substring(lastRegistrationNo.length()-4);
		}
		else{
			lastYear= yearFormat.format(new Date());
		}
		
		no++;
		CompanyDTO companyDTO = (CompanyDTO)companyFinder.getCompanyById(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), new Date());
		String companyName = companyDTO.getCompanyName();
		String month = monthFormat.format(new Date());
		String year = yearFormat.format(new Date());
		String transferType ="TRANSFER";
		if(!lastYear.equals(year)){
			no = 1;
		}
		
		String monthRoman = convertToRoman(Integer.parseInt(month));
		String number = String.format("%03d", no);
		return number+"/"+companyName+"/"+"Registration"+"-"+branchId+"/GSD/"+monthRoman+"/"+year;
	}
	
	
	private static String convertToRoman(int mInt) {
	    String[] rnChars = { "M",  "CM", "D", "C",  "XC", "L",  "X", "IX", "V", "I" };
	    int[] rnVals = {  1000, 900, 500, 100, 90, 50, 10, 9, 5, 1 };
	    String retVal = "";

	    for (int i = 0; i < rnVals.length; i++) {
	        int numberInPlace = mInt / rnVals[i];
	        if (numberInPlace == 0) continue;
	        retVal += numberInPlace == 4 && i > 0? rnChars[i] + rnChars[i - 1]:
	            new String(new char[numberInPlace]).replace("\0",rnChars[i]);
	        mInt = mInt % rnVals[i];
	    }
	    return retVal;
	}

	@Override
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countLocationByExample(SSOALocationExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
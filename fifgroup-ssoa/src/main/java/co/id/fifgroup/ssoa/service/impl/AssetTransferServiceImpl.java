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
import co.id.fifgroup.ssoa.constants.AssetTransferApprovalStatus;
import co.id.fifgroup.ssoa.constants.ProcessType;
import co.id.fifgroup.ssoa.constants.SOActionStatus;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingMapper;
import co.id.fifgroup.ssoa.dao.AssetTransferBastMapper;
import co.id.fifgroup.ssoa.dao.AssetTransferDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetTransferMapper;
import co.id.fifgroup.ssoa.dao.AssetsMapper;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.AssetTransferDetail;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.AssetTransferExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;

import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.finder.AssetTransferFinder;
import co.id.fifgroup.ssoa.service.AssetTransferService;
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
@Service("assetTransferService")
public class AssetTransferServiceImpl implements AssetTransferService {

	// Task Runner Entity
	@Autowired
	private AssetTransferMapper assetTransferMapper;
	
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
	private AssetTransferBastMapper assetTransferBastMapper;

	// Finder
	@Autowired
	private AssetTransferFinder assetTransferFinder;

	@Autowired
	private CompanyFinder companyFinder;
	
	@Autowired
	private AssetTransferDetailMapper assetTransferDetailMapper;

	@Autowired
	private SSOALocationMapper ssoaLocationMapper;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BranchMapper branchMapper;

	//@Autowired
	//private AvmAdapterService avmAdapterServiceImpl;
	
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
	
	public AssetTransferDTO getAssetTransferById(Long id) {
		AssetTransferDTO assetTransferDto = new AssetTransferDTO();
		AssetTransfer assetTransfer = assetTransferMapper.selectByPrimaryKey(id);

		assetTransferDto = modelMapper.map(assetTransfer, AssetTransferDTO.class);
		List<AssetTransferDetailDTO> assetTransferDetail = assetTransferDetailMapper.selectByHeaderKey(id);
		assetTransferDto.setAssetTransferDetail(assetTransferDetail);
		
		/*List<AssetTransferBastDTO> assetTransferBastDTO = (List<AssetTransferBastDTO>) assetTransferBastMapper.selectByHeaderKey(id);
		retirementDto.setRetirementBast(retirementBastDTO);
		*/
		return assetTransferDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferByExample(AssetTransferExample example, int limit, int offset) {
		return assetTransferMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetTransferByExample(AssetTransferExample example) {
		return assetTransferMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetSODtlByCriteria(AssetTransferDetailExample example) {
		return assetTransferDetailMapper.countAssetSODtlByCriteria(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferDetailDTO> getAssetSODtlByCriteria(AssetTransferDetailExample example, int limit, int offset, String action) {
		return assetTransferDetailMapper.selectAssetSODtlByCriteria(example, new RowBounds(offset, limit));
	}
	
	public AssetTransferDetailDTO getSOResultByAssetId(Long assetId) {
		return assetTransferDetailMapper.getSOResultByAssetId(assetId);
	}
	
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferDtoByExample(AssetTransferExample example, int limit, int offset) {
		return assetTransferFinder.selectByExample(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferDtoByExample(AssetTransferExample example) {
		return assetTransferFinder.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferByExample(AssetTransferExample example) {
		return assetTransferMapper.selectByExample(example);
	}

	
	@Transactional(readOnly = true)
	public int countByExample(AssetTransferExample example) {
		return assetTransferFinder.countByExample(example);
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
	public void submit(AssetTransferDTO assetTransferDto, UUID personUUID, List<AssetTransferDetailDTO> assetTransferDetailDTO ) throws Exception{
		
		assetTransferDto.setAvmTrxId(UUID.randomUUID());
		assetTransferDto.setAvmTrxIdString(assetTransferDto.getAvmTrxId().toString());
		assetTransferDto.setOrganizationDestinationId(assetTransferDto.getBranchDestination());
		
		AssetTransfer assetTransfer = assetTransferMapper.selectByPrimaryKey(assetTransferDto.getTransferId());
		if (assetTransfer == null || assetTransfer.getTransferId() == null) {

			AssetTransfer asset = assetTransferMapper.getBranchCode(assetTransferDto.getBranchOrigin());
			
			String branchCode;
			if (assetTransferDto.getBranchOrigin() == -1){
				branchCode = "HEADOFFICE"; 
			}else {
				branchCode = asset.getBranchCodeOrigin();
			}
			
			String docNo = getDocumentNumber(branchCode);
			assetTransferDto.setRequestNo(docNo);
			assetTransferDto.setEbsFlag("Y");
			assetTransferDto.setBastDocNo("");
			assetTransferDto.setBranchId(assetTransferDto.getBranchId());
			assetTransferDto.setStatusCode(AssetTransferApprovalStatus.IN_PROCESS.getCode());
			assetTransferDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetTransferDto.setLastUpdateDate(new Date());
			assetTransferMapper.insert(assetTransferDto);
			
			for (AssetTransferDetail assetDetail : assetTransferDetailDTO) {
				AssetTransferDetail assetTransferDetail = modelMapper.map(assetDetail, AssetTransferDetail.class);
				assetTransferDetail.setTransferId(assetTransferDto.getTransferId());
				assetTransferDetail.setAssetId(assetDetail.getAssetId());
				assetTransferDetail.setCreatedBy(assetTransferDto.getCreatedBy());
				assetTransferDetail.setCreationDate(new Date());
				assetTransferDetail.setLastUpdateBy(assetTransferDto.getCreatedBy());
				assetTransferDetail.setLastUpdateDate(new Date());
				
				assetTransferDetailMapper.insert(assetTransferDetail);

			}
			
			assetTransferDto.setObjectEmployeeUUID(personUUID);
			getAvmAdapterService().submitAvmTransaction(assetTransferDto.getAvmTrxId(), personUUID, assetTransferDto,
					co.id.fifgroup.systemworkflow.constants.TrxType.ASSET_TRANSFER, 1L);
					
		}
	}
	

	@Override
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public void submitBast(AssetTransferBastDTO assetTransferBastDTO) throws FifException, Exception {
			
		String tmpFile = null;
		String pathFile = null;

		List<GlobalSettingDTO> dataImgPathTmp = getGlobalsettingByCode(SSOAConstants.imagePathTmp);
		if (dataImgPathTmp != null) {
			tmpFile = dataImgPathTmp.get(0).getGlobalSetting().getValue().toString();
		}
		List<GlobalSettingDTO> dataImgPath = getGlobalsettingByCode(SSOAConstants.imagePath);
		if (dataImgPath != null) {
			pathFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
		// update status Asset Transfer to complete
		AssetTransfer assetTransfer = new AssetTransfer();
		assetTransfer.setTransferId(assetTransferBastDTO.getTransferId());
		assetTransfer.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
		assetTransfer.setLastUpdateDate(new Date());
		
		List<KeyValue> list = (List<KeyValue>)getLookupKeyValues(AssetTransferApprovalStatus.CODE.getCode(),AssetTransferApprovalStatus.COMPLETED.getCode());
		if(list.size()>0){
			KeyValue key = (KeyValue)list.get(0);
			assetTransfer.setStatusCode((String)key.getValue());
			assetTransfer.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
		}
		assetTransferMapper.updateBASTByPrimaryKey(assetTransfer);
		
		// insert into SOC_TRANSFER_BAST
		for (AssetTransferBastDTO assetTransferBastDto : assetTransferBastDTO.getAssetTransferBastDetail()) {
			AssetTransferBast assetTransferBast = modelMapper.map(assetTransferBastDto, AssetTransferBast.class);
			assetTransferBast.setTransferId(assetTransferBastDTO.getTransferId());
			assetTransferBast.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetTransferBast.setCreationDate(new Date());
			assetTransferBast.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetTransferBast.setLastUpdateDate(new Date());
			
			if(assetTransferBastDto.getBastDocPath()!=null){
				File file = new File(assetTransferBastDto.getBastDocPath());
				String name = file.getName();
				SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
				assetTransferBastDto.setBastDocPath(pathFile+name);
			}
			assetTransferBastMapper.insert(assetTransferBast);
		}
		
		
		List<AssetTransferDetailDTO> assetTransferDetail = assetTransferDetailMapper.selectByHeaderKey(assetTransferBastDTO.getTransferId());
		
		KeyValue successSyncStatus = (KeyValue)stockOpnameMapper
				.getLookupKeyValues("ASSET_SYNC_STATUS", "ASSET_SYNC_STATUS_SUCCESS").get(0);
		KeyValue syncType = (KeyValue)stockOpnameMapper.getLookupKeyValues("ASSET_SYNC_TYPE", "ASSET_SYNC_TYPE_SSOA_TO_EBS")
				.get(0);
		AssetSynchronizingDTO assetSynchronizingDTO = new AssetSynchronizingDTO();
		assetSynchronizingDTO.setSyncTypeId(((BigDecimal) syncType.getKey()).longValue());
		assetSynchronizingDTO.setSyncTypeCode((String) syncType.getValue());
		assetSynchronizingDTO.setProcessDate(new Timestamp(System.currentTimeMillis()));
		assetSynchronizingDTO.setDescription("i-SCAN TO EBS");
		assetSynchronizingDTO.setStatusId(((BigDecimal) successSyncStatus.getKey()).longValue());
		assetSynchronizingDTO.setStatusCode((String) successSyncStatus.getValue());
		assetSynchronizingDTO.setCreatedBy(new Long(-1));
		assetSynchronizingDTO.setCreationDate(new Timestamp(System.currentTimeMillis()));
		assetSynchronizingDTO.setLastUpdateBy(new Long(-1));
		assetSynchronizingDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
		assetSynchronizingMapper.insert(assetSynchronizingDTO);
	
		
		//Map<String, Assets> assetss = new HashMap<String, Assets>();
		for (int i = 0; i < assetTransferDetail.size(); i++) {
			Assets asset = assetsMapper.selectByPrimaryKey(assetTransferDetail.get(i).getAssetId());
			String paramOut = null;
			
			// get location id branch destination
			asset.setBranchId(assetTransferDetail.get(i).getBranchId());
			
			Assets assets = new Assets();
			if(asset.getBranchId() == -1){
				assets = assetsMapper.getLocationIdHO(asset);
			 }else{
				 assets = assetsMapper.getLocationIdBranch(asset);
			 }

			// update asset from branch origin to branch destination
			asset.setLocationId(assets.getLocationId());
			asset.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			asset.setLastUpdateDate(new Date());
			assetsMapper.updateBranchIdById(asset);
			
			/* insert assetTransferToEBS */
			Assets gatAsset = assetsMapper.selectByPrimaryKey(assetTransferDetail.get(i).getAssetId());
			AssetTransferDTO assetTransferDto = new AssetTransferDTO();
			assetTransferDto.setBranchId(gatAsset.getBranchId());
			assetTransferDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			assetTransferDto.setAssetId(gatAsset.getId());
			assetTransferDto.setLastUpdateBy(gatAsset.getLastUpdateBy());
			assetTransferDto.setCreatedBy(gatAsset.getCreatedBy());
			assetTransferDto.setTransferId(assetTransferBastDTO.getTransferId());
			assetTransferDto.setEbsAssetId(gatAsset.getEbsAssetId());
			assetTransferDto.setLocationId(gatAsset.getLocationId());
			
			PersonDTO personDTO = getPersonService().getPersonById(securityServiceImpl.getSecurityProfile().getPersonId(), DateUtil.getToday());
			
			
			AssetTransferDTO dataLocation = assetTransferMapper.getLocationIdForEBS(assetTransferDto);
			
			if(dataLocation == null || dataLocation.getLocationId() == null){
				throw new Exception("Process to EBS failed, location code "+assets.getLocationCode()+"("+assets.getLocationName()+") tidak ada di EBS");
			}
			
			AssetTransferDTO dataAcc = assetTransferMapper.getDataAccount(assetTransferDto);
			
			
			//assetTransferDto.setSegment1(getCompanyService().getCompanyGlCodeById(personDTO.getCompanyId()));
			assetTransferDto.setSegment1(getCompanyService().getCompanyGlCodeById(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId()));
			
			assetTransferDto.setSegment2(getOrganizationSetupService().findById(gatAsset.getBranchId()).getBranchOwner());
			assetTransferDto.setSegment3("817");
			assetTransferDto.setSegment4(dataAcc.getSegment4());
			assetTransferDto.setSegment5("0000");
			assetTransferDto.setSegment6("00");
			assetTransferDto.setSegment7("00000");
			assetTransferDto.setSegment8("00");
			
			AssetTransferDTO expenseId = assetTransferMapper.getExpenseId(assetTransferDto);
			if (expenseId == null || expenseId.getCodeCombinationId() == null)
			{
				assetTransferMapper.insertToGLCombinationCode(assetTransferDto);
				AssetTransferDTO expenseId1 = assetTransferMapper.getExpenseId(assetTransferDto);
				assetTransferDto.setExpenseId(expenseId1.getCodeCombinationId()); 
			}
			else{
				assetTransferDto.setExpenseId(expenseId.getCodeCombinationId());	
			}
			
			assetTransferDto.setEbsAssetId(gatAsset.getEbsAssetId());
			assetTransferDto.setBookTypeCode(gatAsset.getBookTypeCode());
			assetTransferDto.setLocationId(dataLocation.getLocationId());
			//assetTransferDto.setAssignedTo(asset.getBranchId());
			assetTransferDto.setAssignedTo(null);
			
			System.out.println("dataAcc.getSegment4()--" + dataAcc.getSegment4());
			System.out.println("assetTransferDTO.getSegment2()--" + assetTransferDto.getSegment2());
			System.out.println("assetTransferDTO.getCodeCombinationId()--" + assetTransferDto.getExpenseId());
			System.out.println("assetTransfer.getLocationId()" + dataLocation.getLocationId());
			assetTransferMapper.insertAssetTransferToEbs(assetTransferDto);
			
			/* end insert assetTransferToEBS */
			
			if(assetTransferDto.getResultOut()!=null && assetTransferDto.getResultOut().equals("SUCCESS")){
				
			}else{
				/*Messagebox.show(assetTransferDTO.getResultOut() , "Information", Messagebox.YES,
						Messagebox.ERROR);*/
				throw new Exception("Process to EBS failed, "+assetTransferDto.getResultOut());
			}
			
				AssetSynchronizingDetailDTO assetSynchronizingDetailDTO = new AssetSynchronizingDetailDTO();
				assetSynchronizingDetailDTO.setAssetSyncId(assetSynchronizingDTO.getId());
				assetSynchronizingDetailDTO.setAssetId(asset.getId());
				List<KeyValue> listProccesType = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_TRANSFER.getCode());
				if(listProccesType.size()>0){
					assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listProccesType.get(0).getKey()).longValue());
					assetSynchronizingDetailDTO.setProcessTypeCode((String)listProccesType.get(0).getValue());
				}
				assetSynchronizingDetailDTO.setCreatedBy(new Long(-1));
				assetSynchronizingDetailDTO.setCreationDate(new Timestamp(System.currentTimeMillis()));
				assetSynchronizingDetailDTO.setLastUpdateBy(new Long(-1));
				assetSynchronizingDetailDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
				assetSynchronizingDetailMapper.insert(assetSynchronizingDetailDTO);
			//}
			
			List<Long> dataSOIdList = stockOpnameMapper.selectSOIdbyAssetIdAndActionStatusNotStarted(assetTransferDetail.get(i).getAssetId());
			for(int x=0;x<dataSOIdList.size();x++){
				if(stockOpnameMapper.countActionStatusNotStartedBySOId(dataSOIdList.get(x))==1){
					StockOpnameDTO stockOpnameDto = (StockOpnameDTO)stockOpnameMapper.selectByPrimaryKey(dataSOIdList.get(x));
					List<KeyValue> listSOApproveStatus = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.CLOSED.getCode());
					if(listSOApproveStatus.size()>0){
						stockOpnameDto.setStatusId(((BigDecimal)listSOApproveStatus.get(0).getKey()).longValue());
						stockOpnameDto.setStatusCode((String)listSOApproveStatus.get(0).getValue());
					}
					stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDto);
					stockOpnameMapper.updateSOPeriodDetailStatus(stockOpnameDto);
					stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDto);
					stockOpnameMapper.updateCountStatus(stockOpnameDto);
					soPeriodMapper.updateStatusSOPeriod(stockOpnameDto);
				}
				
				StockOpnameDetail stockOpnameDetail = stockOpnameDtlMapper
						.selectByHeaderIdAndAssetId(dataSOIdList.get(x), assetTransferDetail.get(i).getAssetId());
				if (stockOpnameDetail != null) {
					// stockOpnameDetail.setOpnameActionStatus(SOApprovalStatus.CLOSED.getCode());
					stockOpnameDetail.setOpnameActionStatusCode(SOActionStatus.CLOSED.getCode());
					List<KeyValue> lookupActionStatus = stockOpnameMapper
							.getLookupKeyValues(SOActionStatus.CODE.getCode(), SOActionStatus.CLOSED.getCode());
					if (lookupActionStatus.size() > 0) {
						stockOpnameDetail
								.setOpnameActionStatusId(((BigDecimal) lookupActionStatus.get(0).getKey()).longValue());
					}
					stockOpnameDtlMapper.updateOpnameStatus(stockOpnameDetail);
				}

				StockOpnameUnregAssetsDTO stockOpnameUnregAssets = stockOpnameUnregAssetsMapper
						.selectByHeaderIdAndAssetId(dataSOIdList.get(x), assetTransferDetail.get(i).getAssetId());
				if (stockOpnameUnregAssets != null) {
					// stockOpnameUnregAssets.setOpnameActionStatus(SOApprovalStatus.CLOSED.getCode());
					stockOpnameUnregAssets.setOpnameActionStatusCode(SOActionStatus.CLOSED.getCode());
					List<KeyValue> lookupActionStatus = stockOpnameMapper
							.getLookupKeyValues(SOActionStatus.CODE.getCode(), SOActionStatus.CLOSED.getCode());
					if (lookupActionStatus.size() > 0) {
						stockOpnameUnregAssets
								.setOpnameActionStatusId(((BigDecimal) lookupActionStatus.get(0).getKey()).longValue());
					}
					stockOpnameUnregAssetsMapper.updateOpnameStatus(stockOpnameUnregAssets);
				}
				
			}
		
		}
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset) {
		return ssoaLocationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countLocationByExample(SSOALocationExample example) {
		return ssoaLocationMapper.countByExample(example);
	}

	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		return assetTransferMapper.getLookupKeyValues(lookupName, key);
	}
	
	@Override
	public void doAfterApproved(Object transaction) throws Exception {
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO) transaction;
		
		List<KeyValue> listLookup = (List<KeyValue>)getLookupKeyValues(AssetTransferApprovalStatus.CODE.getCode(),AssetTransferApprovalStatus.APPROVED.getCode());
		if(listLookup.size()>0){
			KeyValue key = (KeyValue)listLookup.get(0);
			assetTransferDTO.setStatusCode((String)key.getValue());
			assetTransferDTO.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
		}
		//update bast asset tranfer
		AssetTransfer asset = assetTransferMapper.getBranchCode(assetTransferDTO.getBranchOrigin());
		
		String branchCode;
		if (assetTransferDTO.getBranchOrigin() == -1){
			branchCode = "HEADOFFICE"; 
		}else {
			branchCode = asset.getBranchCodeOrigin();
		}
		
		String bastDocNo = getBastDocumentNumber(branchCode);
		assetTransferDTO.setBastDocNo(bastDocNo);
		assetTransferMapper.updateByPrimaryKey(assetTransferDTO);
	}
	
	@Override
	public void doAfterRejected(Object transaction) throws Exception {
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO) transaction;
		
		//assetTransferDTO.setTransferId(Long.parseLong("10"));
		List<KeyValue> list = (List<KeyValue>)getLookupKeyValues(AssetTransferApprovalStatus.CODE.getCode(),AssetTransferApprovalStatus.REJECTED.getCode());
		if(list.size()>0){
			KeyValue key = (KeyValue)list.get(0);
			assetTransferDTO.setStatusCode((String)key.getValue());
			assetTransferDTO.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
		}
		assetTransferMapper.updateByPrimaryKey(assetTransferDTO);
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
	public List<AssetTransferDetailDTO> getDetailAssetsByPrimaryKey(Long id) {
		return assetTransferDetailMapper.selectDetailByHeaderId(id);
		
	}	
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferBast> getDetailBastByPrimaryKey(Long id) {
		return assetTransferDetailMapper.selectDetailBastByHeaderId(id);
		
	}	

	
	@Override
	@Transactional(readOnly = true)
	public int countByBranchId(Assets example,  Long taskGroupId) {
		return assetTransferFinder.countAssetByBranchId(example, taskGroupId );
	}

	
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}

	
	
	public AssetTransferDTO getAssetTransferByAvmTrxId(String avmTrxId) {
		return assetTransferMapper.selectByAvmTrxId(avmTrxId);
	}
	
	public String getDocumentNumber(String branchCodeOrigin){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO)assetTransferMapper.getLastRequestNo();
		
		int no =0;
		String lastYear = null;
		if(assetTransferDTO != null){
			String lastRequestNo = assetTransferDTO.getRequestNo();
			no = Integer.parseInt(lastRequestNo.substring(0, 3));
			lastYear = lastRequestNo.substring(lastRequestNo.length()-4);
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
		return number+"/"+companyName+"/"+transferType+"-"+branchCodeOrigin+"/GSD/"+monthRoman+"/"+year;
	}
	
	
	public String getBastDocumentNumber(String branchNameOrigin){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO)assetTransferMapper.getLastBastDocNo();
		
		int no =0;
		String lastYear = null;
		if(assetTransferDTO != null){
			String lastBastDocNo = assetTransferDTO.getBastDocNo();
			no = Integer.parseInt(lastBastDocNo.substring(0, 3));
			lastYear = lastBastDocNo.substring(lastBastDocNo.length()-4);
		}
		else{
			lastYear= yearFormat.format(new Date());
		}
		
		no++;
		CompanyDTO companyDTO = (CompanyDTO)companyFinder.getCompanyById(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), new Date());
		String companyName = companyDTO.getCompanyName();
		String month = monthFormat.format(new Date());
		String year = yearFormat.format(new Date());
		String transferType ="BASTB";
		if(!lastYear.equals(year)){
			no = 1;
		}
		else {
			lastYear= yearFormat.format(new Date());
		}
		
		String monthRoman = convertToRoman(Integer.parseInt(month));
		String number = String.format("%03d", no);
		return number+"/"+companyName+"/"+transferType+"/"+branchNameOrigin+"/GSD/"+monthRoman+"/"+year;
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

	
	public void printBAST(AssetTransferDTO assetTransferDTO) throws Exception {
		
		Connection conn = DataSourceUtils.getConnection(dataSource);
		Map<String, Object> param = new HashMap<String, Object>();
		
		String outputFileName;
		outputFileName = assetTransferDTO.getTransferId()+".pdf";
		
		System.out.println("outputFileName=="+outputFileName);
		System.out.println("assetTransferDTO.getAvmTrxIdString()=="+ assetTransferDTO.getAvmTrxIdString());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferId", assetTransferDTO.getTransferId());
		params.put("avmTrxId", assetTransferDTO.getAvmTrxIdString());
		
		InputStream jasperStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/report/BAST_MUTASI.jasper");
		
		ClassLoader classLoader = getClass().getClassLoader();
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(classLoader.getResource("report/BAST_MUTASI.jasper"));
		jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		jasperReport.setWhenResourceMissingType(WhenResourceMissingTypeEnum.NULL);
	
		JasperPrint jPrint = (JasperPrint) JasperFillManager .fillReport(jasperReport, params, conn);
		
		try {
			File temp_file = File.createTempFile("AssetTranferBAST",".pdf");
			JasperExportManager.exportReportToPdfFile(jPrint, temp_file.getAbsolutePath());
			DownloadUtil.downloadFile(temp_file.getAbsolutePath(), ContentType.APPLICATIONFILE.getValue());
//			File f = new File(Sessions.getCurrent().getWebApp().getRealPath("PsychogramHomeScreen.pdf"));
			File f = new File(temp_file.getAbsolutePath());
			f.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
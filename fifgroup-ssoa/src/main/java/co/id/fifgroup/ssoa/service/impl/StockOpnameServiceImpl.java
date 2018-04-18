package co.id.fifgroup.ssoa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.avm.AVMEngine;
import co.id.fifgroup.avm.constants.AVMActionType;
import co.id.fifgroup.avm.constants.AVMApproverPriorityLevel;
import co.id.fifgroup.avm.constants.AVMReturnCode;
import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.dao.AVMLevelDAO;
import co.id.fifgroup.avm.dao.AVMTransactionDAO;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMRejectionDoer;
import co.id.fifgroup.avm.domain.AVMTransaction;
import co.id.fifgroup.avm.domain.AVMVersions;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.avm.manager.AVMApprovalManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.avm.manager.AVMTransactionManager;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.basicsetup.dao.CompanyFinder;
import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dao.GlobalSettingMapper;
import co.id.fifgroup.basicsetup.dao.LookupFinderMapper;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.notification.manager.SendEmailManager;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.finder.PersonFinder;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.common.SSOACommons;
import co.id.fifgroup.ssoa.constants.RetirementType;
import co.id.fifgroup.ssoa.constants.SOActionStatus;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SORecommend;
import co.id.fifgroup.ssoa.constants.SOReportStatus;
import co.id.fifgroup.ssoa.constants.SOResult;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.AssetCategoryMapper;
import co.id.fifgroup.ssoa.dao.AssetsMapper;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.SOPeriodMapper;
import co.id.fifgroup.ssoa.dao.SSOALocationMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameDetailMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameImgMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameReportingMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameUnregAssetImgMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameUnregAssetsMapper;
import co.id.fifgroup.ssoa.domain.AssetCategory;
import co.id.fifgroup.ssoa.domain.AssetCategoryExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.domain.StockOpname;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.StockOpnameDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameExample;
import co.id.fifgroup.ssoa.domain.StockOpnameImg;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssetImg;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssetsExample;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameReportingDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.finder.StockOpnameFinder;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.ApproverType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.domain.TemplateMessageMapping;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;
import co.id.fifgroup.systemworkflow.service.WorkflowResolverService;

@Transactional
@Service("stockOpnameService")
public class StockOpnameServiceImpl implements StockOpnameService {

	// Task Runner Entity
	@Autowired
	private StockOpnameMapper stockOpnameMapper;

	// Finder
	@Autowired
	private StockOpnameFinder stockOpnameFinder;
	
	@Autowired
	private LookupFinderMapper lookupFinderMapper;
	
	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;
	
	@Autowired
	private StockOpnameReportingMapper stockOpnameReportingMapper;

	@Autowired
	private StockOpnameDetailMapper stockOpnameDetailMapper;
	
	@Autowired
	private SOPeriodMapper soPeriodMapper;

	@Autowired
	private StockOpnameUnregAssetsMapper stockOpnameUnregAssetsMapper;

	@Autowired
	private StockOpnameImgMapper stockOpnameImgMapper;

	@Autowired
	private StockOpnameUnregAssetImgMapper stockOpnameUnregAssetImgMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AssetsMapper assetsMapper;

	@Autowired
	private BranchMapper branchMapper;
	
	@Autowired
	private AssetCategoryMapper assetCategoryMapper;
	
	@Autowired
	private SSOALocationMapper ssoaLocationMapper;

	//@Autowired
	//private AvmAdapterService avmAdapterServiceImpl;
	
	public static AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) ApplicationContextUtil.getApplicationContext().getBean("avmAdapterServiceImpl");
	}

	@Autowired
	private AVMApprovalManager avmApprovalManagerHcms;

	@Autowired
	private AVMEngine avmEngineHcms;
	private AVMLevelDAO avmLevelDAO;
	private AVMSetupManager avmSetupManager;
	private AVMTransactionDAO avmTransactionDAO;

	//@Autowired
	//private WorkflowResolverService workflowResolverServiceImpl;
	
	public static WorkflowResolverService getWorkflowResolverService() {
		return (WorkflowResolverService) ApplicationContextUtil.getApplicationContext().getBean("workflowResolverServiceImpl");
	}

	@Autowired
	private AVMTransactionManager avmTransactionManager;

	//@Autowired
	//private TemplateMessageService templateMessageServiceImpl;
	public static TemplateMessageService getTemplateMessageService() {
		return (TemplateMessageService) ApplicationContextUtil.getApplicationContext().getBean("templateMessageServiceImpl");
	}
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}

	@Autowired
	private SendEmailManager sendEmailManager;

	@Autowired
	private PersonFinder personFinder;

	//@Autowired
	//private ApproverMappingService approverMappingServiceImpl;
	
	public static ApproverMappingService getApproverMappingService() {
		return (ApproverMappingService) ApplicationContextUtil.getApplicationContext().getBean("approverMappingServiceImpl");
	}
	
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Autowired
	private CompanyFinder companyFinder;
	
	@Autowired
	private NotificationManager notificationManager;

	@Override
	@Transactional(readOnly = true)
	public StockOpnameDTO getStockOpnameById(Long id) {
		StockOpnameDTO stockOpnameDto = new StockOpnameDTO();
		StockOpname stockOpname = stockOpnameMapper.selectByPrimaryKey(id);

		stockOpnameDto = modelMapper.map(stockOpname, StockOpnameDTO.class);
		List<StockOpnameDetailDTO> stockOpnameDetail = stockOpnameDetailMapper.selectByHeaderKey(id);
		stockOpnameDto.setStockOpnameDetail(stockOpnameDetail);
		/*for (int i = 0; i < stockOpnameDetail.size(); i++) {
			List<StockOpnameImgDTO> stockOpnameImg = stockOpnameImgMapper
					.selectByHeaderKey(stockOpnameDetail.get(i).getId());
			stockOpnameDto.getStockOpnameDetail().get(i).setStockOpnameImg(stockOpnameImg);
		}*/
		List<StockOpnameUnregAssetsDTO> stockOpnameUnregAssets = stockOpnameUnregAssetsMapper.selectByHeaderKey(id);
		stockOpnameDto.setStockOpnameUnregAssets(stockOpnameUnregAssets);
		for (int i = 0; i < stockOpnameUnregAssets.size(); i++) {
			List<StockOpnameUnregAssetImgDTO> stockOpnameImgUnregAsset = stockOpnameUnregAssetImgMapper
					.selectByHeaderKey(stockOpnameUnregAssets.get(i).getId());
			stockOpnameDto.getStockOpnameUnregAssets().get(i).setStockOpnameUnregAssetImg(stockOpnameImgUnregAsset);
		}

		return stockOpnameDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public StockOpnameDTO getStockOpnameByAvmTrxId(String avmTrxId) {
		StockOpnameDTO stockOpnameDto = stockOpnameMapper.selectByAvmTrxId(avmTrxId);
		return stockOpnameDto;
	}


	@Override
	@Transactional(readOnly = true)
	public List<StockOpnameDTO> getStockOpnameByExample(StockOpnameExample example, int limit, int offset) {
		return stockOpnameMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countStockOpnameByExample(StockOpnameExample example) {
		return stockOpnameMapper.countByExample(example);
	}

	/*@Override
	@Transactional(readOnly = true)
	public List<StockOpnameDTO> getStockOpnameDtoByExample(StockOpnameExample example, Integer start, Integer end) {
		return stockOpnameFinder.selectByExample(example, start, end);
	}*/
	
	@Transactional(readOnly = true)
	public List<StockOpnameDTO> getStockOpnameDtoByExample(StockOpnameExample example, int limit, int offset) {
		return stockOpnameFinder.selectByExample(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockOpnameDTO> getStockOpnameByExample(StockOpnameExample example) {
		return stockOpnameFinder.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<StockOpnameDetailDTO> getStockOpnameDtlByExample(StockOpnameDetailExample example) {
		return stockOpnameDetailMapper.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<StockOpnameDetailDTO> getStockOpnameDtlByExampleWithRowbounds(StockOpnameDetailExample example, int limit, int offset) {
		return stockOpnameDetailMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<StockOpnameUnregAssetsDTO> getStockOpnameUnregAssetsByExample(StockOpnameUnregAssetsExample example) {
		return stockOpnameUnregAssetsMapper.selectByExample(example);
	}
	

	@Override
	@Transactional(readOnly = true)
	public int countByExample(StockOpnameExample example) {
		return stockOpnameFinder.countByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countSODtlByExample(StockOpnameDetailExample example) {
		return stockOpnameDetailMapper.countByExample(example);
	}
	
	@Transactional(readOnly = true)
	public List<Assets> getAssetsByExample(AssetsExample example, int limit, int offset) {
		return assetsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public List<AssetCategory> getAssetCategoryByExample(AssetCategoryExample example, int limit, int offset) {
		return assetCategoryMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public int countAssetCategoryByExample(AssetCategoryExample example) {
		return assetCategoryMapper.countByExample(example);
	}
	
	@Transactional(readOnly = true)
	public AssetCategory getAssetCategoryById(Long id) {
		return assetCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetsByExample(AssetsExample example) {
		return assetsMapper.countByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countDecsriptionByExample(AssetsExample example) {
		return assetsMapper.countDescriptionByExample(example);
	}
	
	@Transactional(readOnly = true)
	public List<Assets> getDescriptionByExample(AssetsExample example, int limit, int offset) {
		return assetsMapper.selectDescriptionWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Assets getAssetsById(Long id) {
		return assetsMapper.selectByPrimaryKey(id);
	}
	
	public void sendNotificationAndEmail(StockOpnameDTO stockOpnameDTO){
		List<GlobalSettingDTO> dataPICGS = globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(SSOAConstants.picGSPersonId, null); 
		String picGSPersonId = null;
		if(dataPICGS!=null){
			picGSPersonId = dataPICGS.get(0).getGlobalSetting().getValue().toString();
		}
		//PersonDTO personDTO = getPersonService().getPersonById(new Long(picGSPersonId), new Date());
		StockOpnameDTO personDTO = stockOpnameMapper.getPersonByPersonId(new Long(picGSPersonId));
		if(personDTO!=null){
			try {
				sendNotificationAndSendEmail(stockOpnameMapper.getNotificationMessageByName(SSOAConstants.templateNameForNotifSOBranch),stockOpnameDTO.getPersonUUID() , personDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.NOTIFICATION_SO_INPUT,stockOpnameDTO.getBranchNameSubmitter(),stockOpnameDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public void save(StockOpnameDTO stockOpnameDto) throws Exception {
		
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
		
		StockOpname stockOpname = stockOpnameMapper.selectByPrimaryKey(stockOpnameDto.getId());
		if (stockOpname == null || stockOpname.getId() == null) {
			
			List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.NOT_STARTED.getCode());
			if(listLookup.size()>0){
				stockOpnameDto.setStatusId(((BigDecimal)listLookup.get(0).getKey()).longValue());
				stockOpnameDto.setStatusCode((String)listLookup.get(0).getValue());
			}
			
			stockOpnameMapper.insert(stockOpnameDto);
			List<Assets> assetsList = assetsMapper.selectByBranchCode(stockOpnameDto.getBranchId(),stockOpnameDto.getCompanyId());

			for (Assets assets : assetsList) {
				StockOpnameDetail stockOpnameDetail = new StockOpnameDetail();
				stockOpnameDetail.setStockOpnameId(stockOpnameDto.getId());
				stockOpnameDetail.setAssetId(assets.getId());
				stockOpnameDetail.setCreatedBy(stockOpnameDto.getCreatedBy());
				stockOpnameDetail.setCreationDate(new Date());
				stockOpnameDetail.setLastUpdateBy(stockOpnameDto.getCreatedBy());
				stockOpnameDetail.setLastUpdateDate(new Date());
				stockOpnameDetailMapper.insert(stockOpnameDetail);
			}
			
			if(stockOpnameDto.getIsBranchSubmitter()){ // kirim email ke PIC GS
				sendNotificationAndEmail(stockOpnameDto);
			}
			
		} else {
			
			
			if(!stockOpname.getStatusCode().equals(SOApprovalStatus.APPROVED.getCode()) && !stockOpname.getStatusCode().equals(SOApprovalStatus.SUBMIT.getCode())){
				List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.ON_PROGRESS.getCode());
				if(listLookup.size()>0){
					stockOpnameDto.setStatusId(((BigDecimal)listLookup.get(0).getKey()).longValue());
					stockOpnameDto.setStatusCode((String)listLookup.get(0).getValue());
				}
			}
			
			//System.out.println("stockOpnameDto.getSoPeriodDtlId()..." +stockOpnameDto.getSoPeriodDtlId());
			stockOpnameMapper.updateSOPeriodDetailStatus(stockOpnameDto);
			stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDto);
			stockOpnameMapper.updateCountStatus(stockOpnameDto);
			soPeriodMapper.updateStatusSOPeriod(stockOpnameDto);
			
			List<StockOpnameDetailDTO> stockOpnameDetail = stockOpnameDto.getStockOpnameDetail();
			for (int i = 0; i < stockOpnameDetail.size(); i++) {
				List<KeyValue> lookupHOSugest = stockOpnameMapper.getLookupKeyValues(SORecommend.CODE.getCode(),stockOpnameDetail.get(i).getOpnameHOSugestCode());
				if(lookupHOSugest.size()>0){
					stockOpnameDetail.get(i).setOpnameHOSugestId(((BigDecimal)lookupHOSugest.get(0).getKey()).longValue());	
				}
				if(stockOpnameDetail.get(i).getOpnameSugestCode()!=null && (stockOpnameDetail.get(i).getOpnameActionStatusCode() == null || stockOpnameDetail.get(i).getOpnameActionStatusCode().equals(SOActionStatus.NOT_STARTED.getCode()))){
					stockOpnameDetail.get(i).setOpnameActionStatusCode(SOActionStatus.NOT_STARTED.getCode());
					List<KeyValue> lookupActionStatus = stockOpnameMapper.getLookupKeyValues(SOActionStatus.CODE.getCode(),SOActionStatus.NOT_STARTED.getCode());
					if(lookupActionStatus.size()>0){
						stockOpnameDetail.get(i).setOpnameActionStatusId(((BigDecimal)lookupActionStatus.get(0).getKey()).longValue());	
					}
				}else{
					stockOpnameDetail.get(i).setOpnameActionStatusCode(null);
					stockOpnameDetail.get(i).setOpnameActionStatusId(null);
				}
				stockOpnameDetail.get(i).setLastUpdateBy(stockOpnameDto.getCreatedBy());
				stockOpnameDetail.get(i).setLastUpdateDate(new Date());
				stockOpnameDetailMapper.updateByPrimaryKey(stockOpnameDetail.get(i));
				List<StockOpnameImgDTO> listStockOpnameImgDTO = (List<StockOpnameImgDTO>) stockOpnameImgMapper
						.selectByHeaderKey(stockOpnameDetail.get(i).getId());
				if (listStockOpnameImgDTO.size() > 0) {
					for (int x = 0; x < stockOpnameDetail.get(i).getStockOpnameImg().size(); x++) {
						StockOpnameImg stockOpnameImg = (StockOpnameImg) stockOpnameDetail.get(i).getStockOpnameImg()
								.get(x);
						stockOpnameImg.setStockOpnameDtlId(stockOpnameDetail.get(i).getId());
						stockOpnameImg.setLastUpdateBy(stockOpnameDetail.get(i).getCreatedBy());
						stockOpnameImg.setLastUpdateDate(new Date());
						if (stockOpnameImg.getId() == null) {
							stockOpnameImg.setCreatedBy(stockOpnameDetail.get(i).getCreatedBy());
							stockOpnameImg.setCreationDate(new Date());
							
							if(stockOpnameImg.getImageFilePath()!=null){
								File file = new File(stockOpnameImg.getImageFilePath());
								String name = file.getName();
								SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
								stockOpnameImg.setImageFilePath(pathFile+name);
							}
							stockOpnameImgMapper.insert(stockOpnameImg);
							
							
						} else {
							
							if(stockOpnameImg.getImageFilePath()!=null){
								StockOpnameImgDTO stockOpnameImgDTO = stockOpnameImgMapper.selectByPrimaryKey(stockOpnameImg.getId());
								
								File file = new File(stockOpnameImg.getImageFilePath());
								String name = file.getName();
		
								stockOpnameImg.setImageFilePath(pathFile+name);
								
								if(stockOpnameImgDTO.getImageFilePath()!=null){
									SSOACommons.deleteFile(tmpFile+name);
								}
								try{
								SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
								}catch(Exception e){}
							}
							stockOpnameImgMapper.updateByPrimaryKey(stockOpnameImg);

						}
					}
					for (int y = 0; y < listStockOpnameImgDTO.size(); y++) {
						StockOpnameImg stockOpnameImgFromDb = (StockOpnameImg) listStockOpnameImgDTO.get(y);
						boolean flag = false;
						for (int z = 0; z < stockOpnameDetail.get(i).getStockOpnameImg().size(); z++) {
							StockOpnameImg stockOpnameImgNewInput = (StockOpnameImg) stockOpnameDetail.get(i)
									.getStockOpnameImg().get(z);
							if (stockOpnameImgFromDb.getId() != null && stockOpnameImgNewInput.getId() != null
									&& stockOpnameImgFromDb.getId().intValue() == stockOpnameImgNewInput.getId().intValue()) {
								flag = true;
							}
						}
						if (!flag) {
							stockOpnameImgMapper.deleteByPrimaryKey(stockOpnameImgFromDb.getId());
						}
					}

				} else {
					for (int x = 0; x < stockOpnameDetail.get(i).getStockOpnameImg().size(); x++) {
						StockOpnameImg stockOpnameImg = (StockOpnameImg) stockOpnameDetail.get(i).getStockOpnameImg()
								.get(x);
						stockOpnameImg.setStockOpnameDtlId(stockOpnameDetail.get(i).getId());
						stockOpnameImg.setCreatedBy(stockOpnameDetail.get(i).getCreatedBy());
						stockOpnameImg.setCreationDate(new Date());
						stockOpnameImg.setLastUpdateBy(stockOpnameDetail.get(i).getCreatedBy());
						stockOpnameImg.setLastUpdateDate(new Date());
						
						if(stockOpnameImg.getImageFilePath()!=null){
							File file = new File(stockOpnameImg.getImageFilePath());
							String name = file.getName();
							SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
							stockOpnameImg.setImageFilePath(pathFile+name);
						}
						
						stockOpnameImgMapper.insert(stockOpnameImg);
						
						

					}

				}

			}
			
			List<StockOpnameUnregAssetsDTO> listStockOpnameUnregAssetListFromDb = (List<StockOpnameUnregAssetsDTO>) stockOpnameUnregAssetsMapper
					.selectByHeaderKey(stockOpnameDto.getId());
			for (int k = 0; k < listStockOpnameUnregAssetListFromDb.size(); k++) {
				StockOpnameUnregAssetsDTO stockOpnameUnregAssetsFromDb = (StockOpnameUnregAssetsDTO) listStockOpnameUnregAssetListFromDb
						.get(k);
				boolean flag = false;
				for (int l = 0; l < stockOpnameDto.getStockOpnameUnregAssets().size(); l++) {
					StockOpnameUnregAssetsDTO stockOpnameUnregAssetsNewInput = (StockOpnameUnregAssetsDTO) stockOpnameDto
							.getStockOpnameUnregAssets().get(l);
					if (stockOpnameUnregAssetsFromDb.getId() != null && stockOpnameUnregAssetsNewInput.getId() != null
							&& stockOpnameUnregAssetsFromDb.getId().intValue() == stockOpnameUnregAssetsNewInput.getId().intValue()) {
						flag = true;
					}
				}
				if (!flag) {
					stockOpnameUnregAssetImgMapper.deleteByHeaderKey(stockOpnameUnregAssetsFromDb.getId());
					stockOpnameUnregAssetsMapper.deleteByPrimaryKey(stockOpnameUnregAssetsFromDb.getId());
				}
			}

			List<StockOpnameUnregAssetsDTO> stockOpnameUnregAssetDTO = stockOpnameDto.getStockOpnameUnregAssets();
			for (int i = 0; i < stockOpnameUnregAssetDTO.size(); i++) {
				StockOpnameUnregAssetsDTO data = stockOpnameUnregAssetsMapper
						.selectByPrimaryKey(stockOpnameUnregAssetDTO.get(i).getId());
				List<KeyValue> lookupHOSugest = stockOpnameMapper.getLookupKeyValues(SORecommend.CODE.getCode(),stockOpnameUnregAssetDTO.get(i).getOpnameHOSugestCode());
				if(lookupHOSugest.size()>0){
					stockOpnameUnregAssetDTO.get(i).setOpnameHOSugestId(((BigDecimal)lookupHOSugest.get(0).getKey()).longValue());
				}
				if(stockOpnameUnregAssetDTO.get(i).getAssetId()!=null && stockOpnameUnregAssetDTO.get(i).getOpnameSugestCode()!=null && (stockOpnameUnregAssetDTO.get(i).getOpnameActionStatusCode() == null || stockOpnameUnregAssetDTO.get(i).getOpnameActionStatusCode().equals(SOActionStatus.NOT_STARTED.getCode()))){
					//stockOpnameUnregAssetDTO.get(i).setOpnameActionStatus(SOApprovalStatus.NOT_STARTED.getCode());
					stockOpnameUnregAssetDTO.get(i).setOpnameActionStatusCode(SOActionStatus.NOT_STARTED.getCode());
					List<KeyValue> lookupActionStatus = stockOpnameMapper.getLookupKeyValues(SOActionStatus.CODE.getCode(),SOActionStatus.NOT_STARTED.getCode());
					if(lookupActionStatus.size()>0){
						stockOpnameUnregAssetDTO.get(i).setOpnameActionStatusId(((BigDecimal)lookupActionStatus.get(0).getKey()).longValue());	
					}
				}else{
					stockOpnameUnregAssetDTO.get(i).setOpnameActionStatusCode(null);
					stockOpnameUnregAssetDTO.get(i).setOpnameActionStatusId(null);
				}
				if (data != null && data.getId() != null) {
					stockOpnameUnregAssetDTO.get(i).setCreatedBy(stockOpnameDto.getCreatedBy());
					stockOpnameUnregAssetDTO.get(i).setCreationDate(new Date());
					stockOpnameUnregAssetDTO.get(i).setLastUpdateBy(stockOpnameDto.getCreatedBy());
					stockOpnameUnregAssetDTO.get(i).setLastUpdateDate(new Date());
					stockOpnameUnregAssetsMapper.updateByPrimaryKey(stockOpnameUnregAssetDTO.get(i));
				} else {
					stockOpnameUnregAssetDTO.get(i).setCreatedBy(stockOpnameDto.getCreatedBy());
					stockOpnameUnregAssetDTO.get(i).setCreationDate(new Date());
					stockOpnameUnregAssetDTO.get(i).setLastUpdateBy(stockOpnameDto.getCreatedBy());
					stockOpnameUnregAssetDTO.get(i).setLastUpdateDate(new Date());
					stockOpnameUnregAssetDTO.get(i).setStockOpnameId(stockOpnameDto.getId());
					stockOpnameUnregAssetsMapper.insert(stockOpnameUnregAssetDTO.get(i));
				}
				List<StockOpnameUnregAssetImgDTO> listStockOpnameUnregAssetImgDTO = (List<StockOpnameUnregAssetImgDTO>) stockOpnameUnregAssetImgMapper
						.selectByHeaderKey(stockOpnameDto.getStockOpnameUnregAssets().get(i).getId());
				if (listStockOpnameUnregAssetImgDTO.size() > 0) {
					for (int x = 0; x < stockOpnameUnregAssetDTO.get(i).getStockOpnameUnregAssetImg().size(); x++) {
						StockOpnameUnregAssetImg stockOpnameUnregAssetImg = (StockOpnameUnregAssetImg) stockOpnameUnregAssetDTO
								.get(i).getStockOpnameUnregAssetImg().get(x);
						stockOpnameUnregAssetImg.setStockOpnameUnregAssetId(stockOpnameUnregAssetDTO.get(i).getId());
						stockOpnameUnregAssetImg.setLastUpdateBy(stockOpnameUnregAssetDTO.get(i).getCreatedBy());
						stockOpnameUnregAssetImg.setLastUpdateDate(new Date());
						if (stockOpnameUnregAssetImg.getId() == null) {
							stockOpnameUnregAssetImg.setCreatedBy(stockOpnameUnregAssetDTO.get(i).getCreatedBy());
							stockOpnameUnregAssetImg.setCreationDate(new Date());
							
							if(stockOpnameUnregAssetImg.getImageFilePath()!=null){
								File file = new File(stockOpnameUnregAssetImg.getImageFilePath());
								String name = file.getName();
								SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
								stockOpnameUnregAssetImg.setImageFilePath(pathFile+name);
							}
							
							stockOpnameUnregAssetImgMapper.insert(stockOpnameUnregAssetImg);
						} else {
							if(stockOpnameUnregAssetImg.getImageFilePath()!=null){
								StockOpnameUnregAssetImgDTO stockOpnameUnregAssetImgDTO = stockOpnameUnregAssetImgMapper.selectByPrimaryKey(stockOpnameUnregAssetImg.getId());
								
								File file = new File(stockOpnameUnregAssetImg.getImageFilePath());
								String name = file.getName();
								
								stockOpnameUnregAssetImg.setImageFilePath(pathFile+name);
								if(stockOpnameUnregAssetImgDTO.getImageFilePath()!=null){
									SSOACommons.deleteFile(tmpFile+name);
								}
								try{
								SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
								}catch(Exception e){}
							}
							stockOpnameUnregAssetImgMapper.updateByPrimaryKey(stockOpnameUnregAssetImg);
						}
					}
					for (int y = 0; y < listStockOpnameUnregAssetImgDTO.size(); y++) {
						StockOpnameUnregAssetImg stockOpnameImgFromDb = (StockOpnameUnregAssetImg) listStockOpnameUnregAssetImgDTO
								.get(y);
						boolean flag = false;
						for (int z = 0; z < stockOpnameUnregAssetDTO.get(i).getStockOpnameUnregAssetImg().size(); z++) {
							StockOpnameUnregAssetImg stockOpnameImgNewInput = (StockOpnameUnregAssetImg) stockOpnameUnregAssetDTO
									.get(i).getStockOpnameUnregAssetImg().get(z);
							if (stockOpnameImgFromDb.getId() != null && stockOpnameImgNewInput.getId() != null
									&& stockOpnameImgFromDb.getId().intValue() == stockOpnameImgNewInput.getId().intValue()) {
								flag = true;
							}
						}
						if (!flag) {
							stockOpnameUnregAssetImgMapper.deleteByPrimaryKey(stockOpnameImgFromDb.getId());
						}
					}
				} else {
					for (int x = 0; x < stockOpnameUnregAssetDTO.get(i).getStockOpnameUnregAssetImg().size(); x++) {
						StockOpnameUnregAssetImgDTO stockOpnameUnregAssetImg = (StockOpnameUnregAssetImgDTO) stockOpnameUnregAssetDTO
								.get(i).getStockOpnameUnregAssetImg().get(x);
						stockOpnameUnregAssetImg.setStockOpnameUnregAssetId(stockOpnameUnregAssetDTO.get(i).getId());
						stockOpnameUnregAssetImg.setCreatedBy(stockOpnameUnregAssetDTO.get(i).getCreatedBy());
						stockOpnameUnregAssetImg.setCreationDate(new Date());
						stockOpnameUnregAssetImg.setLastUpdateBy(stockOpnameUnregAssetDTO.get(i).getCreatedBy());
						stockOpnameUnregAssetImg.setLastUpdateDate(new Date());
						
						if(stockOpnameUnregAssetImg.getImageFilePath()!=null){
							File file = new File(stockOpnameUnregAssetImg.getImageFilePath());
							String name = file.getName();
							SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
							stockOpnameUnregAssetImg.setImageFilePath(pathFile+name);
						}
						
						stockOpnameUnregAssetImgMapper.insert(stockOpnameUnregAssetImg);

					}

				}
			}


		}

	}

	@Override
	@Transactional
	public void saveImage(StockOpnameDetailDTO stockOpnameDetail) throws Exception {
		stockOpnameDetailMapper.updateByPrimaryKey(stockOpnameDetail);
		List<StockOpnameImgDTO> listStockOpnameImgDTO = (List<StockOpnameImgDTO>) stockOpnameImgMapper
				.selectByHeaderKey(stockOpnameDetail.getId());
		if (listStockOpnameImgDTO.size() > 0) {
			for (int i = 0; i < stockOpnameDetail.getStockOpnameImg().size(); i++) {
				StockOpnameImg stockOpnameImg = (StockOpnameImg) stockOpnameDetail.getStockOpnameImg().get(i);
				stockOpnameImg.setStockOpnameDtlId(stockOpnameDetail.getId());
				stockOpnameImg.setLastUpdateBy(stockOpnameDetail.getCreatedBy());
				stockOpnameImg.setLastUpdateDate(new Date());
				if (stockOpnameImg.getId() == null) {
					stockOpnameImg.setCreatedBy(stockOpnameDetail.getCreatedBy());
					stockOpnameImg.setCreationDate(new Date());
					stockOpnameImgMapper.insert(stockOpnameImg);
				} else {
					stockOpnameImgMapper.updateByPrimaryKey(stockOpnameImg);
				}
			}
		} else {
			for (int i = 0; i < stockOpnameDetail.getStockOpnameImg().size(); i++) {
				StockOpnameImg stockOpnameImg = (StockOpnameImg) stockOpnameDetail.getStockOpnameImg().get(i);
				stockOpnameImg.setStockOpnameDtlId(stockOpnameDetail.getId());
				stockOpnameImg.setCreatedBy(stockOpnameDetail.getCreatedBy());
				stockOpnameImg.setCreationDate(new Date());
				stockOpnameImg.setLastUpdateBy(stockOpnameDetail.getCreatedBy());
				stockOpnameImg.setLastUpdateDate(new Date());
				stockOpnameImgMapper.insert(stockOpnameImg);

			}

		}
	}

	public List<StockOpnameImgDTO> getStockOpnameImgByStockOpnameDtlId(Long id) {
		return stockOpnameImgMapper.selectByHeaderKey(id);
	}

	public List<StockOpnameUnregAssetImgDTO> getStockOpnameUnregAssetImgBySOUnregAssetId(Long id) {
		return stockOpnameUnregAssetImgMapper.selectByHeaderKey(id);
	}
	
	public Branch getBranchById(Long id, Long companyId) {
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
	@Transactional(readOnly = true)
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset) {
		return ssoaLocationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}
	
	public SSOALocation getLocationById(Long id) {
		return ssoaLocationMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true)
	public int countLocationByExample(SSOALocationExample example) {
		return ssoaLocationMapper.countByExample(example);
	}
	
	public StockOpnameDTO getOnePersonByBranchId(Long branchId,Long companyId){
		return stockOpnameMapper.getOnePersonByBranchId(branchId,  companyId);
	}
	

	@Override
	@Transactional(rollbackFor={Exception.class})
	public void submit(StockOpnameDTO stockOpnameDTO, UUID personUUID,String note) throws Exception {
		stockOpnameDTO.setAvmTrxId(UUID.randomUUID());
		stockOpnameDTO.setObjectEmployeeUUID(personUUID);
		stockOpnameDTO.setOrganizationDestinationId(stockOpnameDTO.getBranchId());
		getAvmAdapterService().submitAvmTransaction(stockOpnameDTO.getAvmTrxId(), personUUID, stockOpnameDTO,
				co.id.fifgroup.systemworkflow.constants.TrxType.STOCK_OPNAME, 1L);
		
		List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.SUBMIT.getCode());
		if(listLookup.size()>0){
			stockOpnameDTO.setStatusId(((BigDecimal)listLookup.get(0).getKey()).longValue());
			stockOpnameDTO.setStatusCode((String)listLookup.get(0).getValue());
		}
		
		
		//stockOpnameDTO.setStatusCode(ApprovalStatusSO.SUBMIT.getCode());
		stockOpnameDTO.setLastUpdateBy(securityServiceImpl
				.getSecurityProfile().getUserId());
		stockOpnameDTO.setLastUpdateDate(new Date());
		
		stockOpnameMapper.updateSOPeriodDetailStatus(stockOpnameDTO);
		stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDTO);
		stockOpnameMapper.updateCountStatus(stockOpnameDTO);
		soPeriodMapper.updateStatusSOPeriod(stockOpnameDTO);
		
		StockOpnameReportingDTO stockOpnameReportingDTO = new StockOpnameReportingDTO();
		stockOpnameReportingDTO.setReportNo(getDocumentNumber(stockOpnameDTO.getBranchName()));
		stockOpnameReportingDTO.setReportDate(new Date());
		stockOpnameReportingDTO.setRemark(note);
		stockOpnameReportingDTO.setAvmTrxId(stockOpnameDTO.getAvmTrxId().toString());
		stockOpnameReportingDTO.setCreatedBy(securityServiceImpl
				.getSecurityProfile().getUserId());
		stockOpnameReportingDTO.setCreationDate(new Date());
		stockOpnameReportingDTO.setLastUpdateBy(securityServiceImpl
				.getSecurityProfile().getUserId());
		stockOpnameReportingDTO.setLastUpdateDate(new Date());
		stockOpnameReportingDTO.setStockOpnameId(stockOpnameDTO.getId());
		List<KeyValue> listLookupOnApproval = stockOpnameMapper.getLookupKeyValues(SOReportStatus.CODE.getCode(),SOReportStatus.ON_APPROVAL.getCode());
		if(listLookupOnApproval.size()>0){
			stockOpnameReportingDTO.setStatusId(((BigDecimal)listLookupOnApproval.get(0).getKey()).longValue());
			stockOpnameReportingDTO.setStatusCode((String)listLookupOnApproval.get(0).getValue());
		}
		stockOpnameReportingMapper.insert(stockOpnameReportingDTO);
		
		//sendNotification(new Long(14), personUUID, personUUID, stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.STOCK_OPNAME);
	}

	@Override
	public void doValidateBeforeApprove(Object transaction) throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doValidateBeforeReject(Object transaction) throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doApprove(Object transaction, UUID avmTrxId, UUID approverId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void doAfterApproved(Object transaction) throws Exception {
		// TODO Auto-generated method stub
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) transaction;
		List<KeyValue> listLookupApproved = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.APPROVED.getCode());
		List<KeyValue> listLookupReportApproved = stockOpnameMapper.getLookupKeyValues(SOReportStatus.CODE.getCode(),SOReportStatus.APPROVED.getCode());
		List<KeyValue> listLookupClosed = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.CLOSED.getCode());
		
		//stockOpnameDTO.setStatusCode(ApprovalStatusSO.APPROVED.getCode());
		stockOpnameDTO.setLastUpdateBy(securityServiceImpl
				.getSecurityProfile().getUserId());
		stockOpnameDTO.setLastUpdateDate(new Date());
		
		
		
		int isSugest = stockOpnameMapper.countIsSugest(stockOpnameDTO.getId());
		if(isSugest>0){
			if(listLookupApproved.size()>0){
				stockOpnameDTO.setStatusId(((BigDecimal)listLookupApproved.get(0).getKey()).longValue());
				stockOpnameDTO.setStatusCode((String)listLookupApproved.get(0).getValue());
			}
		}else{
			if(listLookupClosed.size()>0){
				stockOpnameDTO.setStatusId(((BigDecimal)listLookupClosed.get(0).getKey()).longValue());
				stockOpnameDTO.setStatusCode((String)listLookupClosed.get(0).getValue());
			}
		}
		
		stockOpnameMapper.updateSOPeriodDetailStatus(stockOpnameDTO);
		stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDTO);
		stockOpnameMapper.updateCountStatus(stockOpnameDTO);
		soPeriodMapper.updateStatusSOPeriod(stockOpnameDTO);
		
		
		List<StockOpnameReportingDTO> stockOpnameReportingDTOList = (List<StockOpnameReportingDTO>)stockOpnameReportingMapper.selectByHeaderKey(stockOpnameDTO.getId());
		if(stockOpnameReportingDTOList.size()>0){
			StockOpnameReportingDTO stockOpnameReportingDTO = stockOpnameReportingDTOList.get(0);
			if(listLookupReportApproved.size()>0){
				stockOpnameReportingDTO.setStatusId(((BigDecimal)listLookupReportApproved.get(0).getKey()).longValue());
				stockOpnameReportingDTO.setStatusCode((String)listLookupReportApproved.get(0).getValue());
			}
			stockOpnameReportingMapper.updateByPrimaryKey(stockOpnameReportingDTO);
		}
		
		/* Start Send notification for transfer asset to branch in unregistered asset */ 
		List<StockOpnameUnregAssetsDTO> stockOpnameUnregAssets = stockOpnameUnregAssetsMapper.selectByHeaderKey(stockOpnameDTO.getId());
		for (int i = 0; i < stockOpnameUnregAssets.size(); i++) {
			StockOpnameUnregAssetsDTO stockOpnameUnregAssetsDTO = stockOpnameUnregAssets.get(i);
			if(stockOpnameUnregAssetsDTO != null || stockOpnameUnregAssetsDTO.getAssetId() != null)
			{
				//PIC GS HO
				List<GlobalSettingDTO> dataPICGS = globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(SSOAConstants.picGSPersonId, null); 
				String picGSPersonId = null;
				if(dataPICGS!=null){
					picGSPersonId = dataPICGS.get(0).getGlobalSetting().getValue().toString();
				}
				
				StockOpnameDTO personDTO = stockOpnameMapper.getPersonByPersonId(new Long(picGSPersonId));
				List<PersonAssignmentDTO> personApproverPICGS = new ArrayList<PersonAssignmentDTO>();
				personApproverPICGS = getPersonService().selectPersonByAssignment(null, null,
						stockOpnameUnregAssetsDTO.getBranchId(), null, Long.parseLong(picGSPersonId), stockOpnameUnregAssetsDTO.getCompanyId(), null);
	
				for(int x=0;x<personApproverPICGS.size();x++){
					
				PersonAssignmentDTO personAssignmentDTO = personApproverPICGS.get(x);
				if(personDTO!=null){
					try {
						sendNotification(soPeriodMapper.getNotificationTransfer(),stockOpnameDTO.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.ASSET_TRANSFER_NOTIFICATION, stockOpnameDTO.getId(), stockOpnameUnregAssetsDTO.getAssetNo());
					} catch (Exception e) {
						e.printStackTrace();
					
					}
				}
				}
				//GS Coordinator Branch
				List<PersonAssignmentDTO> personApproverGSCoordinator = new ArrayList<PersonAssignmentDTO>();
				personApproverGSCoordinator = getPersonService().selectPersonByAssignment(null, null,
						stockOpnameUnregAssetsDTO.getBranchId(), null, null, stockOpnameUnregAssetsDTO.getCompanyId(), "239");
	
				for(int x=0;x<personApproverGSCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverGSCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationTransfer(),stockOpnameDTO.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.ASSET_TRANSFER_NOTIFICATION, stockOpnameDTO.getId(), stockOpnameUnregAssetsDTO.getAssetNo());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	
				//Service Coordinator Parenting/ CAC
				List<PersonAssignmentDTO> personApproverServiceCoordinator = new ArrayList<PersonAssignmentDTO>();
				personApproverServiceCoordinator = getPersonService().selectPersonByAssignment(null, null,
						stockOpnameUnregAssetsDTO.getBranchId(), null, null, stockOpnameUnregAssetsDTO.getCompanyId(), "218");
	
				for(int x=0;x<personApproverServiceCoordinator.size();x++){
					
					PersonAssignmentDTO personAssignmentDTO = personApproverServiceCoordinator.get(x);
					try {
						sendNotification(soPeriodMapper.getNotificationTransfer(),stockOpnameDTO.getObjectEmployeeUUID() , personAssignmentDTO.getPersonUUID(), stockOpnameDTO.getCompanyId(), co.id.fifgroup.systemworkflow.constants.TrxType.ASSET_TRANSFER_NOTIFICATION, stockOpnameDTO.getId(), stockOpnameUnregAssetsDTO.getAssetNo());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		/* End Send notification for transfer asset to branch in unregistered asset */
		
		AVMTransaction currentAVMTransaction = avmTransactionManager
				 .getAVMTransactionById(stockOpnameDTO.getAvmTrxId());
				 /*AVMReturnCode returnCode = approveTransaction(
						 stockOpnameDTO.getAvmTrxId(), avmApprovalManagerHcms
				 .getApproverIdByAVMTrxId(stockOpnameDTO.getAvmTrxId())
				 .get(0).getApproverId(), null, null);*/
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
				 UUID submitterId = avmApprovalManagerHcms
				 .getTransactionSubmitter(stockOpnameDTO.getAvmTrxId());
	   
		doSendMessage(currentAVMTransaction.getAvmId(),
				stockOpnameDTO.getAvmTrxId(), returnCode, submitterId,
				 transaction,
				 TrxType.fromCode((long) currentAVMTransaction.getTrxType()),
				 currentAVMTransaction.getCompanyId(),
				 DateUtil.truncate(currentAVMTransaction.getAvmTimeStamp()));
		
		System.out.println("end");

	}
	
	public void sendNotification(Long templateId, UUID submitterId,UUID approverId, Long companyId,TrxType trxType, Long StockOpnameId, String assetNumber) throws Exception {
		
		TemplateMessage templateMessage = getTemplateMessageService()
				.getTemplateMessageById(templateId);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setReceivedTime(new Date());
		notificationMessage.setToId(approverId);
		notificationMessage.setFromId(submitterId);
		Map<String, Object> map = new HashMap<String, Object>();
		String approverName = getAvmAdapterService().getName(approverId, companyId);
		map.put("approverName", approverName);
		String requestorName = getAvmAdapterService().getName(submitterId, companyId);

		StockOpname stockOpname = stockOpnameMapper.selectByPrimaryKey(StockOpnameId);
		
		Branch branch = branchMapper.selectByPrimaryKey(stockOpname.getBranchId(), stockOpname.getCompanyId());
		map.put("branchName", branch.getBranchName());
		map.put("assetNumber", assetNumber);
		map.put("requestorName", requestorName);
		List<ParameterMessage> parameterMessages = getTemplateMessageService()
				.getParameterListByTransactionId(trxType.getCode());
		String contentMessage = templateMessage.getTemplateContent();

		if (parameterMessages.size() > 0) {
			for (ParameterMessage parameter : parameterMessages) {
				contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
						Matcher.quoteReplacement(parameter.getParameterKey()));
			}

			templateMessage.setTemplateContent(contentMessage);
		}

		Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		/*notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail() != null
				? templateMessageMapping.getUrlDetail() + "?avmTrxId=" + avmApprovalHistory.getAvmTrxId() : null);*/
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		insertNewMessage(notificationMessage);
		PersonAssignmentDTO personAssignmentDTO =getPersonService().getAssignmentByPersonUUID(approverId);
		sendEmailManager.sendEmail(notificationMessage, personAssignmentDTO.getEmployeeNumber());
	
	}

	@Override
	public void doAfterRejected(Object transaction) throws Exception {
		// TODO Auto-generated method stub
		StockOpnameDTO stockOpnameDTO = (StockOpnameDTO) transaction;
		List<KeyValue> listLookupReportRejected = stockOpnameMapper.getLookupKeyValues(SOReportStatus.CODE.getCode(),SOReportStatus.REJECTED.getCode());
		List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.ON_PROGRESS.getCode());
		if(listLookup.size()>0){
			stockOpnameDTO.setStatusId(((BigDecimal)listLookup.get(0).getKey()).longValue());
			stockOpnameDTO.setStatusCode((String)listLookup.get(0).getValue());
		}
		
		//stockOpnameDTO.setStatus(ApprovalStatusSO.REJECTED.getCode());
		stockOpnameDTO.setLastUpdateBy(securityServiceImpl
				.getSecurityProfile().getUserId());
		stockOpnameDTO.setLastUpdateDate(new Date());

		stockOpnameMapper.updateSOPeriodDetailStatus(stockOpnameDTO);
		stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDTO);
		stockOpnameMapper.updateCountStatus(stockOpnameDTO);
		soPeriodMapper.updateStatusSOPeriod(stockOpnameDTO);
		
		List<StockOpnameReportingDTO> stockOpnameReportingDTOList = (List<StockOpnameReportingDTO>)stockOpnameReportingMapper.selectByHeaderKey(stockOpnameDTO.getId());
		if(stockOpnameReportingDTOList.size()>0){
			StockOpnameReportingDTO stockOpnameReportingDTO = stockOpnameReportingDTOList.get(0);
			if(listLookupReportRejected.size()>0){
				stockOpnameReportingDTO.setStatusId(((BigDecimal)listLookupReportRejected.get(0).getKey()).longValue());
				stockOpnameReportingDTO.setStatusCode((String)listLookupReportRejected.get(0).getValue());
			}
			stockOpnameReportingMapper.updateByPrimaryKey(stockOpnameReportingDTO);
		}

	}

	@Override
	public void doAfterCanceled(Object transaction) throws Exception {
		// TODO Auto-generated method stub

	}

	public AVMReturnCode doApproval(AVMTransaction currentAVMTransaction, AVMApprovalHistory approversHistory,
			String remarks, UUID actualApproverId) throws FifException {

		int currentRemainingApproval = currentAVMTransaction.getRemainingApproval();

		UUID trxId = currentAVMTransaction.getAvmTrxId();
		int levelSequence = approversHistory.getLevelSequence();
		UUID avmId = currentAVMTransaction.getAvmId();
		int avmVersionId = currentAVMTransaction.getAvmVersionId();
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

		try {
			int priorityOfApprover = 0;
			currentRemainingApproval = currentRemainingApproval - 1;
			currentAVMTransaction.setRemainingApproval(currentRemainingApproval);

			if (currentRemainingApproval == 0) {
				if (isNowTheLastLevel(avmId, levelSequence, avmVersionId)) {
					returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
				} else {
					returnCode = moveOnToNextLevel(currentAVMTransaction);
				}
			} else {
				if (avmApprovalManagerHcms.countApproversStillDoNothing(trxId, levelSequence) == 0
						&& priorityOfApprover != AVMApproverPriorityLevel.EQUAL.getCode()) {
					int nextLowerPriority = priorityOfApprover;// + 1;

					List<AVMApprover> approverList = avmSetupManager.getAVMApproversByLevelAndPriority(avmId,
							levelSequence, nextLowerPriority, avmVersionId);

					returnCode = notifyApproversNew(approverList, currentAVMTransaction, nextLowerPriority);
				}
			}
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}

	public AVMReturnCode doAutoapprove(List<AVMApprovalHistory> avmApprovalHistories,
			AVMTransaction currentAVMTransaction) throws FifException {
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;
		for (AVMApprovalHistory avmApprovalHistory : avmApprovalHistories) {
			if (avmApprovalHistory.getApproverId().equals(UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM))) {
				String remarks = "Autoapprove by system";
				returnCode = doApproval(currentAVMTransaction, avmApprovalHistory, remarks,
						UUID.fromString(AVMApprovalManager.APPROVER_SYSTEM));
			}
		}
		return returnCode;
	}

	public AVMReturnCode doRejection(AVMTransaction avmTransaction, AVMApprovalHistory avmApprovalHistory,
			String remarks, UUID actualApproverId) throws FifException {
		AVMReturnCode returnCode = null;

		try {
			returnCode = AVMReturnCode.TRANSACTION_IS_REJECTED;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return returnCode;
	}

	public void doSendMessage(UUID avmId, UUID avmTrxId, AVMReturnCode returnCode, UUID submitterId, Object application,
			TrxType trxType, Long companyId, Date transactionDate) throws Exception {
		ApprovalModelMappingDetail approvalModelMappingDetail = getAvmAdapterService().getApplicableApprovalModelMapping(
				trxType.getCode(), companyId, application, DateUtil.truncate(new Date()));

		if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_COMPLETED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId,
					AVMActionType.APPROVE_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());

			sendNotificationEmail(avmId, application, avmApprovalHistory, submitterId,
					AVMActionType.APPROVE_TRX.getCode(), trxType, companyId, transactionDate,
					approvalModelMappingDetail);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_IN_PROGRESS)) {
			List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
			approvalHistories = avmApprovalManagerHcms.getApproverReadyByAVMTrxId(avmTrxId);

			sendMultipleNotificationEmail(approvalHistories, companyId, avmId, trxType,
					AVMActionType.SUBMIT_TRX.getCode(), transactionDate, approvalModelMappingDetail, submitterId,
					application);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_REJECTED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId,
					AVMActionType.REJECT_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());

			sendNotificationEmail(avmId, application, avmApprovalHistory, submitterId,
					AVMActionType.REJECT_TRX.getCode(), trxType, companyId, transactionDate,
					approvalModelMappingDetail);
		} else if (returnCode.equals(AVMReturnCode.TRANSACTION_IS_CANCELLED)) {
			AVMRejectionDoer avmDoer = avmTransactionManager.getLastApproverResponseTransaction(avmTrxId,
					AVMActionType.CANCEL_TRX.getCode());
			AVMApprovalHistory avmApprovalHistory = new AVMApprovalHistory();
			avmApprovalHistory.setAvmTrxId(avmTrxId);
			avmApprovalHistory.setApproverId(avmDoer.getApproverId());
			avmApprovalHistory.setSequenceNumber(avmDoer.getSequenceNumber());
			avmApprovalHistory.setLevelSequence(avmDoer.getLevelSequence());

			sendNotificationEmail(avmId, application, avmApprovalHistory, submitterId,
					AVMActionType.REJECT_TRX.getCode(), trxType, companyId, transactionDate,
					approvalModelMappingDetail);
		}
	}

	public AVMReturnCode approveTransaction(UUID trxId, UUID approverId, String remarks, UUID actualApproverId)
			throws FifException {
		try {
			return giveReponseToTransaction(trxId, approverId, remarks, AVMActionType.APPROVE_TRX, actualApproverId);
		} catch (Exception ex) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), ex);
		}
	}

	public AVMReturnCode giveReponseToTransaction(UUID trxId, UUID approverId, String remarks, AVMActionType actionType,
			UUID actualApproverId) throws FifException {
		return giveResponseToTransaction(trxId, approverId, remarks, actionType, actualApproverId);
	}

	public AVMReturnCode giveResponseToTransaction(UUID avmTrxId, UUID approverId, String remarks,
			AVMActionType actionType, UUID actualApproverId) throws FifException {
		AVMReturnCode returnCode = null;

		try {
			AVMApprovalHistory approversApprovalHistory = avmApprovalManagerHcms
					.getRecentApproversApprovalHistory(avmTrxId, approverId);

			if (approversApprovalHistory != null) {
				AVMTransaction currentAVMTransaction = avmTransactionDAO.getAVMTransactionById(avmTrxId);

				if (actionType.equals(AVMActionType.APPROVE_TRX)) {
					returnCode = doApproval(currentAVMTransaction, approversApprovalHistory, remarks, actualApproverId);
				} else if (actionType.equals(AVMActionType.REJECT_TRX)) {
					returnCode = doRejection(currentAVMTransaction, approversApprovalHistory, remarks,
							actualApproverId);
				}
			}
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}

		return returnCode;
	}

	private boolean isNowTheLastLevel(UUID avmId, int levelSequence, int avmVersionId) throws FifException {
		boolean isTheLastLevel = false;
		try {
			int lastLevelSequence = avmLevelDAO.getTheLastLevelSequence(avmId, avmVersionId);
			isTheLastLevel = levelSequence == lastLevelSequence ? true : false;
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
		return isTheLastLevel;
	}

	private AVMReturnCode moveOnToNextLevel(AVMTransaction avmTransaction) throws FifException {
		UUID avmId = avmTransaction.getAvmId();
		int levelSequence = avmTransaction.getLevelSequence();
		AVMVersions currentVersion = new AVMVersions();
		currentVersion.setAvmId(avmId);
		currentVersion.setAvmVersionId(avmTransaction.getAvmVersionId());
		AVMLevel nextLevel;
		AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

		try {
			Object application = Serialization.deserializedObject(avmTransaction.getSerializedData());
			int nextLevelSequence = levelSequence + 1;
			nextLevel = avmApprovalManagerHcms.getApplicableLevelSequence(currentVersion, nextLevelSequence,
					application);

			if (nextLevel != null) {
				avmTransaction.setLevelSequence(nextLevel.getLevelSequence());

				List<AVMApprover> approverList = avmSetupManager.getTopPriorityApproverList(avmId,
						nextLevel.getLevelSequence(), avmTransaction.getAvmVersionId());
				int priority = approverList.get(0).getPriority();

				int numberApproval = 1;

				if (nextLevel.getLevelMethod() == 1) {
					numberApproval = approverList.size();
				} else if (nextLevel.getLevelMethod() == 2) {
					numberApproval = nextLevel.getNumberOfApprovals();
				}

				nextLevel.setNumberOfApprovals(numberApproval);
				avmTransaction.setRemainingApproval(nextLevel.getNumberOfApprovals());

				returnCode = notifyApproversNew(approverList, avmTransaction, priority);
			} else {
				avmTransaction.setAvmTrxStatus(TransactionStatusType.COMPLETED.getCode());
				returnCode = AVMReturnCode.TRANSACTION_IS_COMPLETED;
			}
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}

		return returnCode;
	}

	public AVMReturnCode notifyApproversNew(List<AVMApprover> approverList, AVMTransaction avmTransaction, int priority)
			throws FifException {
		try {
			// add returnCode for handling autoapprove
			AVMReturnCode returnCode = AVMReturnCode.TRANSACTION_IS_IN_PROGRESS;

			// List<UUID> avmApproverList = resolveAVMApproverList(approverList,
			// avmTransaction);
			List<AVMApprovalHistory> historyList = resolveAVMApproverList(approverList, avmTransaction);

			// check same approver
			List<AVMApprovalHistory> removeIndex = new ArrayList<AVMApprovalHistory>();
			int k = 1;

			for (int i = 0; i < approverList.size(); i++) {
				for (int j = k; j < approverList.size(); j++) {
					if (approverList.get(i).getApproverId().equals(approverList.get(j).getApproverId())
							&& approverList.get(i).getApproverId().equals(approverList.get(j).getApproverId())) {
						if (historyList.get(i).getApproverId().equals(historyList.get(j).getApproverId())
								&& (historyList.get(i).getOrganizationId() == historyList.get(j).getOrganizationId()
										|| (historyList.get(i).getOrganizationId() != null
												&& historyList.get(j).getOrganizationId() != null
												&& historyList.get(i).getOrganizationId()
														.equals(historyList.get(j).getOrganizationId())))
								&& (historyList.get(i).getBranchId() == historyList.get(j).getBranchId()
										|| (historyList.get(i).getBranchId() != null
												&& historyList.get(j).getBranchId() != null && historyList.get(i)
														.getBranchId().equals(historyList.get(j).getBranchId())))) {
							removeIndex.add(historyList.get(j));
						}
					}
					k++;
				}
			}

			AVMLevel level = avmLevelDAO.getAVMLevelByAVMIdAndLevelSequence(avmTransaction.getAvmId(),
					avmTransaction.getLevelSequence(), avmTransaction.getAvmVersionId());

			if (level != null && removeIndex.size() > 0) {
				Integer remainingApproval = null;

				if (level.getLevelMethod() == 1) {
					remainingApproval = avmTransaction.getRemainingApproval() - removeIndex.size();
				} else if (level.getLevelMethod() == 2) {
					remainingApproval = approverList.size() - removeIndex.size();
					remainingApproval = remainingApproval < avmTransaction.getRemainingApproval() ? remainingApproval
							: avmTransaction.getRemainingApproval();
				}

				historyList.removeAll(removeIndex);
			}

			// check autoapprove
			returnCode = doAutoapprove(historyList, avmTransaction);

			return returnCode;
		} catch (FifException e) {
			throw e;
		} catch (Exception e) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), e);
		}
	}

	private void prepareNotificationEmail(UUID avmTrxId, UUID submitterId, Object application, TrxType trxType,
			Long companyId) throws FifException, Exception {
		AVMReturnCode returnCode = null;
		Long transactionId = trxType.getCode();

		ApprovalModelMappingDetail approvalModelMappingDetail = getAvmAdapterService().getApplicableApprovalModelMapping(
				transactionId, companyId, application, DateUtil.truncate(new Date()));

		if (approvalModelMappingDetail != null && approvalModelMappingDetail.getApprovalModel() != null) {
			List<AVMApprovalHistory> approvalHistories = new ArrayList<AVMApprovalHistory>();
			approvalHistories = avmApprovalManagerHcms.getApproverReadyByAVMTrxId(avmTrxId);

			sendMultipleNotificationEmail(approvalHistories, companyId, approvalModelMappingDetail.getApprovalModel(),
					trxType, AVMActionType.SUBMIT_TRX.getCode(), DateUtil.truncate(new Date()),
					approvalModelMappingDetail, submitterId, application);
			// }
		} else {
			// ketika tidak ada mapping approval yang ditemukan
			returnCode = AVMReturnCode.NO_APPLICABLE_AVM_FOUND;
			getAvmAdapterService().sendNotificationErrorToAdmin(returnCode, trxType.getMessage());
			throw new FifException(returnCode.getCode(), returnCode.getMessage());
		}

	}

	public AVMReturnCode rejectTransaction(UUID trxId, UUID approverId, String remarks, UUID actualApproverId)
			throws FifException {
		try {
			return giveReponseToTransaction(trxId, approverId, remarks, AVMActionType.REJECT_TRX, actualApproverId);
		} catch (Exception ex) {
			throw new FifException(AVMReturnCode.ERROR_MANAGER.getCode(), ex);
		}
	}

	protected List<AVMApprovalHistory> resolveAVMApproverList(List<AVMApprover> rawApproverList,
			AVMTransaction avmTransaction) throws FifException {
		List<AVMApprovalHistory> historyList = new ArrayList<AVMApprovalHistory>();
		UUID submitterId = avmApprovalManagerHcms.getTransactionSubmitter(avmTransaction.getAvmTrxId());
		historyList = getWorkflowResolverService().getResolvingApproverList(rawApproverList, avmTransaction,
				submitterId);
		return historyList;
	}

	private void sendMultipleNotificationEmail(List<AVMApprovalHistory> approvalHistories, Long companyId, UUID avmId,
			TrxType trxType, int actionType, Date transactionDate,
			ApprovalModelMappingDetail approvalModelMappingDetail, UUID submitterId, Object application)
			throws Exception {
		ActionType action = ActionType.fromCode(actionType);

		TemplateMessageMapping templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(
				companyId, avmId, trxType.getCode(), (long) approvalHistories.get(0).getLevelSequence(), action,
				DateUtil.truncate(transactionDate), approvalModelMappingDetail.getPriority());

		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(companyId, avmId,
					trxType.getCode(), (long) 0, action, transactionDate, approvalModelMappingDetail.getPriority());
		}

		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageService()
					.getTemplateMessageById(templateMessageMapping.getTemplateId());

			for (AVMApprovalHistory avmApprovalHistory : approvalHistories) {
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setReceivedTime(new Date());
				notificationMessage.setToId(avmApprovalHistory.getApproverId());
				notificationMessage.setFromId(submitterId);

				List<PersonAssignmentDTO> personAssignmentDTOs = getMember(avmApprovalHistory.getApproverId(),
						(StockOpnameDTO) application);

				if (!personAssignmentDTOs.isEmpty()) {
					for (PersonAssignmentDTO personAssignmentDTO : personAssignmentDTOs) {
						Map<String, Object> map = new HashMap<String, Object>();
						String approverName = personAssignmentDTO.getFullName();
						map.put("approverName", approverName);
						String requestorName = getAvmAdapterService().getName(submitterId, companyId);
						map.put("requestorName", requestorName);
						map.put("object", application);

						List<ParameterMessage> parameterMessages = getTemplateMessageService()
								.getParameterListByTransactionId(trxType.getCode());
						String contentMessage = templateMessage.getTemplateContent();

						if (parameterMessages.size() > 0) {
							for (ParameterMessage parameter : parameterMessages) {
								contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
										Matcher.quoteReplacement(parameter.getParameterKey()));
							}

							templateMessage.setTemplateContent(contentMessage);
						}

						Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
						notificationMessage.setSubject(message.getSubject());
						notificationMessage.setContentMessage(message.getContent());
						notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail());
						notificationMessage.setMessageType(MessageType.APPROVAL_MESSAGE.getCode());
						sendEmailManager.sendEmail(notificationMessage, "out163");
						// sendEmailManager.sendEmail(notificationMessage,
						// personAssignmentDTO.getEmployeeNumber());
					}
				}
			}
		}
	}

	public void sendNotificationEmail(UUID avmId, Object application, AVMApprovalHistory avmApprovalHistory,
			UUID submitterId, int actionType, TrxType trxType, Long companyId, Date transactionDate,
			ApprovalModelMappingDetail approvalModelMappingDetail) throws Exception {
		ActionType action = ActionType.fromCode(actionType);
		TemplateMessageMapping templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(
				companyId, avmId, trxType.getCode(), (long) avmApprovalHistory.getLevelSequence(), action,
				DateUtil.truncate(transactionDate), approvalModelMappingDetail.getPriority());

		if (templateMessageMapping == null) {
			templateMessageMapping = getTemplateMessageService().getTemplateMessageMappingByCriteria(companyId, avmId,
					trxType.getCode(), (long) 0, action, DateUtil.truncate(transactionDate),
					approvalModelMappingDetail.getPriority());
		}

		if (templateMessageMapping != null) {
			TemplateMessage templateMessage = getTemplateMessageService()
					.getTemplateMessageById(templateMessageMapping.getTemplateId());
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setReceivedTime(new Date());
			notificationMessage.setToId(avmApprovalHistory.getApproverId());
			notificationMessage.setFromId(submitterId);
			Map<String, Object> map = new HashMap<String, Object>();
			String approverName = getAvmAdapterService().getName(avmApprovalHistory.getApproverId(), companyId);
			map.put("approverName", approverName);
			String requestorName = getAvmAdapterService().getName(submitterId, companyId);
			//String employeeNumber = personFinder.selectPersonByPersonUUID(submitterId, null).getEmployeeNumber();
			map.put("requestorName", requestorName);
			map.put("object", application);
			List<ParameterMessage> parameterMessages = getTemplateMessageService()
					.getParameterListByTransactionId(trxType.getCode());
			String contentMessage = templateMessage.getTemplateContent();

			if (parameterMessages.size() > 0) {
				for (ParameterMessage parameter : parameterMessages) {
					contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
							Matcher.quoteReplacement(parameter.getParameterKey()));
				}

				templateMessage.setTemplateContent(contentMessage);
			}

			Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
			notificationMessage.setSubject(message.getSubject());
			notificationMessage.setContentMessage(message.getContent());
			notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail() != null
					? templateMessageMapping.getUrlDetail() + "?avmTrxId=" + avmApprovalHistory.getAvmTrxId() : null);
			notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
			 /*sendEmailManager.sendEmail(notificationMessage,
			 "luthfi.noviandi");*/
			PersonAssignmentDTO personAssignmentDTO =getPersonService().getAssignmentByPersonUUID(avmApprovalHistory.getApproverId());
			sendEmailManager.sendEmail(notificationMessage, personAssignmentDTO.getEmployeeNumber());
		}
	}
	
	public void sendNotification(Long templateId, UUID submitterId,UUID approverId, Long companyId,TrxType trxType) throws Exception {
		

			TemplateMessage templateMessage = getTemplateMessageService()
					.getTemplateMessageById(templateId);
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setReceivedTime(new Date());
			notificationMessage.setToId(approverId);
			notificationMessage.setFromId(submitterId);
			Map<String, Object> map = new HashMap<String, Object>();
			String approverName = getAvmAdapterService().getName(approverId, companyId);
			map.put("approverName", approverName);
			String requestorName = getAvmAdapterService().getName(submitterId, companyId);
			//String employeeNumber = personFinder.selectPersonByPersonUUID(submitterId, null).getEmployeeNumber();
			map.put("requestorName", requestorName);
			List<ParameterMessage> parameterMessages = getTemplateMessageService()
					.getParameterListByTransactionId(trxType.getCode());
			String contentMessage = templateMessage.getTemplateContent();

			if (parameterMessages.size() > 0) {
				for (ParameterMessage parameter : parameterMessages) {
					contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
							Matcher.quoteReplacement(parameter.getParameterKey()));
				}

				templateMessage.setTemplateContent(contentMessage);
			}

			Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
			notificationMessage.setSubject(message.getSubject());
			notificationMessage.setContentMessage(message.getContent());
			/*notificationMessage.setUrlDetail(templateMessageMapping.getUrlDetail() != null
					? templateMessageMapping.getUrlDetail() + "?avmTrxId=" + avmApprovalHistory.getAvmTrxId() : null);*/
			notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
			insertNewMessage(notificationMessage);
			//sendEmailManager.sendEmail(notificationMessage, employeeNumber);
		
	}
	
	public void sendNotificationAndSendEmail(Long templateId, UUID submitterId,UUID approverId, Long companyId,TrxType trxType,String branchName,StockOpnameDTO stockOpnameDTO) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		TemplateMessage templateMessage = getTemplateMessageService()
				.getTemplateMessageById(templateId);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setReceivedTime(new Date());
		notificationMessage.setToId(approverId);
		notificationMessage.setFromId(submitterId);
		Map<String, Object> map = new HashMap<String, Object>();
		String approverName = getAvmAdapterService().getName(approverId, companyId);
		map.put("approverName", approverName);
		String requestorName = getAvmAdapterService().getName(submitterId, companyId);
		//String employeeNumber = personFinder.selectPersonByPersonUUID(submitterId, null).getEmployeeNumber();
		map.put("requestorName", requestorName);
		map.put("branchName", branchName);
		map.put("periode", sdf.format(stockOpnameDTO.getStartDate())+" - "+sdf.format(stockOpnameDTO.getEndDate()));
		List<ParameterMessage> parameterMessages = getTemplateMessageService()
				.getParameterListByTransactionId(trxType.getCode());
		String contentMessage = templateMessage.getTemplateContent();

		if (parameterMessages.size() > 0) {
			for (ParameterMessage parameter : parameterMessages) {
				contentMessage = contentMessage.replaceAll(Pattern.quote(parameter.getAliasName()),
						Matcher.quoteReplacement(parameter.getParameterKey()));
			}

			templateMessage.setTemplateContent(contentMessage);
		}

		Message message = getTemplateMessageService().templateMessageResolver(templateMessage, map);
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		insertNewMessage(notificationMessage);
		PersonAssignmentDTO personAssignmentDTO =getPersonService().getAssignmentByPersonUUID(approverId);
		sendEmailManager.sendEmail(notificationMessage, personAssignmentDTO.getEmployeeNumber());
	
    }

	private List<PersonAssignmentDTO> getMember(UUID personUUID, StockOpnameDTO application) {
		ApproverMapping approverMapping = getApproverMappingService().getApproverMappingByApproverId(personUUID);

		if (approverMapping != null) {
			if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.Role.name())
					|| approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobRole.name())
					|| approverMapping.getApproverType().equalsIgnoreCase(ApproverType.DepartmentOwner.name())) {
				List<PersonAssignmentDTO> personAssignmentDTOs = personFinder.selectPersonByAssignment(null, null,
						application.getOrganizationId(), approverMapping.getApproverName(), null, null, null);

				return personAssignmentDTOs;
			} else if (approverMapping.getApproverType().equalsIgnoreCase(ApproverType.JobGroup.name())) {
				List<PersonAssignmentDTO> personAssignmentDTOs = personFinder.selectPersonByAssignment(null, null,
						application.getOrganizationId(), approverMapping.getApproverName(), null, null, null);
				// List<KeyValue> listJobGroup = lookupServiceImpl
				// .getLookupKeyValues(
				// WorkflowReference.WSU_JOB_GROUP.name(),
				// approverMapping.getApproverName());

				return personAssignmentDTOs;
			}

			return null;
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<KeyValue> getSOResultList() {
		return stockOpnameMapper.getLookupKeyValues(SOResult.CODE.getCode(), null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<KeyValue> getSORecommendList() {
		return stockOpnameMapper.getLookupKeyValues(SORecommend.CODE.getCode(), null);
	}

	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		// TODO Auto-generated method stub
		return stockOpnameMapper.getLookupKeyValues(lookupName, key);
	}
	
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}
	
	public String getDocumentNumber(String branchCode){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		StockOpnameReportingDTO stockOpnameReportingDTO = (StockOpnameReportingDTO)stockOpnameReportingMapper.getLastReportNo();
		//String lastRequestNo = stockOpnameDTO.get;
		String lastRequestNo = null;
		int no =0;
		String lastYear = null;
		if(stockOpnameReportingDTO != null){
			lastRequestNo = stockOpnameReportingDTO.getReportNo();
			no = Integer.parseInt(lastRequestNo.substring(0, 3));
			lastYear = lastRequestNo.substring(lastRequestNo.length()-4);
		}else{
			lastYear = yearFormat.format(new Date());
		}
		no++;
		CompanyDTO companyDTO = (CompanyDTO)companyFinder.getCompanyById(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), new Date());
		String companyName = companyDTO.getCompanyName();
		String month = monthFormat.format(new Date());
		String year = yearFormat.format(new Date());
		
		if(lastRequestNo!= null && !lastYear.equals(year)){
			no = 1;
		}
		
		String monthRoman = convertToRoman(Integer.parseInt(month));
		String number = String.format("%03d", no);
		return number+"/"+companyName+"/"+"STOCK OPNAME"+"/"+branchCode+"/"+monthRoman+"/"+year;
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

	public void insertNewMessage(NotificationMessage notificationMessage)
			throws Exception {
		notificationManager.insertNewMessage(notificationMessage);
	}
	
	public int countSONotClosed(Long branchId,Long companyId){
		return stockOpnameMapper.countIsSONotClosed(branchId,companyId);
	}
	
	public int countRetirementNotClosed(Long branchId,Long companyId){
		return stockOpnameMapper.countIsRetirementNotClosed(branchId,companyId);
	}
	
	public int countTransferNotClosed(Long branchId,Long companyId){
		return stockOpnameMapper.countIsTransferNotClosed(branchId,companyId);
	}
	
	public int countAssetByBranchCode(Long branchId,Long companyId){
	return assetsMapper.countByBranchCode(branchId,companyId);
	}

	public List<StockOpnameDTO> getResponsibilityIdSO(){
		return stockOpnameMapper.getResponsibilityIdSO();
	}
	
	public List<StockOpnameDTO> getResponsibilityIdAT(){
		return stockOpnameMapper.getResponsibilityIdAT();
	}
}

package co.id.fifgroup.ssoa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dao.CompanyFinder;
import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dao.LookupFinderMapper;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.ContentType;
import co.id.fifgroup.core.domain.interfacing.RvInterface;
import co.id.fifgroup.core.service.FIFAppsServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.common.SSOACommons;
import co.id.fifgroup.ssoa.constants.ProcessType;
import co.id.fifgroup.ssoa.constants.RetirementApprovalStatus;
import co.id.fifgroup.ssoa.constants.RetirementType;
import co.id.fifgroup.ssoa.constants.SOActionStatus;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingMapper;
import co.id.fifgroup.ssoa.dao.AssetsMapper;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.RVMapper;
import co.id.fifgroup.ssoa.dao.RetirementBastMapper;
import co.id.fifgroup.ssoa.dao.RetirementDetailMapper;
import co.id.fifgroup.ssoa.dao.RetirementImgMapper;
import co.id.fifgroup.ssoa.dao.RetirementMapper;
import co.id.fifgroup.ssoa.dao.RetirementQuotationMapper;
import co.id.fifgroup.ssoa.dao.RetirementRVMapper;
import co.id.fifgroup.ssoa.dao.SOPeriodMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameDetailMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameUnregAssetsMapper;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.RV;
import co.id.fifgroup.ssoa.domain.RVExample;
import co.id.fifgroup.ssoa.domain.Retirement;
import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.RetirementExample;
import co.id.fifgroup.ssoa.domain.RetirementImg;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.StockOpnameDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssets;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssetsExample;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementImgDTO;
import co.id.fifgroup.ssoa.dto.RetirementQuotationDTO;
import co.id.fifgroup.ssoa.dto.RetirementRVDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.finder.RetirementFinder;
import co.id.fifgroup.ssoa.service.RetirementService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

@Transactional
@Service("retirementService")
public class RetirementServiceImpl implements RetirementService {

	// Finder
	@Autowired
	private RetirementFinder retirementFinder;
	
	@Autowired
	private AssetsMapper assetsMapper;
	
	@Autowired
	private SOPeriodMapper soPeriodMapper;
	
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
	private SecurityService securityServiceImpl;
	
	@Autowired
	private RetirementMapper retirementMapper;
	
	@Autowired
	private RetirementDetailMapper retirementDtlMapper;
	
	@Autowired
	private RetirementImgMapper retirementImgMapper;
	
	@Autowired
	private RetirementBastMapper retirementBastMapper;
	
	@Autowired
	private RetirementRVMapper retirementRVMapper;
	
	@Autowired
	private RetirementQuotationMapper retirementQuotationMapper;
	
	@Autowired
	private BranchMapper branchMapper;
	
	@Autowired
	private AssetSynchronizingMapper assetSynchronizingMapper;
	
	@Autowired
	private AssetSynchronizingDetailMapper assetSynchronizingDetailMapper;
	
	@Autowired
	private RVMapper rvMapper;
	
	@Autowired
	private DataSource dataSource;
	
	//@Autowired
	//private AvmAdapterService avmAdapterServiceImpl;
	
	public static AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) ApplicationContextUtil.getApplicationContext().getBean("avmAdapterServiceImpl");
	}
	
	public static OrganizationSetupService getOrganizationSetupServiceImpl() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	
	@Autowired
	private CompanyFinder companyFinder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public static PersonService getPersonService() {
		return (PersonService) ApplicationContextUtil.getApplicationContext().getBean("personService");
	}
	
	public static CompanyService getCompanyService() {
		return (CompanyService) ApplicationContextUtil.getApplicationContext().getBean("companyServiceImpl");
	}
	
	public static OrganizationSetupService getOrganizationSetupService() {
		return (OrganizationSetupService) ApplicationContextUtil.getApplicationContext().getBean("organizationSetupServiceImpl");
	}
	
	public static FIFAppsServiceAdapter getFIFAppsServiceAdapter() {
		return (FIFAppsServiceAdapter) ApplicationContextUtil.getApplicationContext().getBean("fifappsService");
	}
	
	@Transactional(readOnly = true)
	public List<RetirementDTO> getRetirementDtoByExample(RetirementExample example, int limit, int offset) {
		return retirementFinder.selectByExample(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public List<RetirementDTO> getRetirementDtoByExample(RetirementExample example) {
		return retirementFinder.selectByExample(example);
	}
	
	@Transactional(readOnly = true)
	public int countByExample(RetirementExample example) {
		return retirementFinder.countByExample(example);
	}
	
	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		// TODO Auto-generated method stub
		return retirementMapper.getLookupKeyValues(lookupName, key);
	}
	
	public Branch getBranchById(Long id,Long companyId) {
		return branchMapper.selectByPrimaryKey(id,companyId);
	}
	
	
	public RetirementDetailDTO getDetailIdForImages(Long id, Long assetId) {
		return retirementDtlMapper.getDetailIdForImages(id, assetId);
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
	public List<Assets> getAssetsByExample(AssetsExample example, int limit, int offset) {
		return assetsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetsByExample(AssetsExample example) {
		return assetsMapper.countByExample(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RetirementDetailDTO> getAssetSODtlByCriteria(RetirementDetailExample example, int limit, int offset,String action) {
		if(action!=null && action.equals("addNew")){
			return retirementDtlMapper.selectAssetSODtlByCriteria(example, new RowBounds(offset, limit));	
		}else{
		    return retirementDtlMapper.selectAssetSODtl(example, new RowBounds(offset, limit));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RetirementBast> getDetailBastByPrimaryKey(Long id) {
		return retirementBastMapper.selectDetailBastByHeaderId(id);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RetirementImg> getDetailImageByPrimaryKey(Long id) {
		return retirementImgMapper.selectDetailImageByHeaderId(id);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countAssetSODtlByCriteria(RetirementDetailExample example,String action) {
		if(action!=null && action.equals("addNew")){
		return retirementDtlMapper.countAssetSODtlByCriteria(example);
		}else{
			return retirementDtlMapper.countAssetSODtl(example);	
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public RetirementDTO getRetirementById(Long id) {
		RetirementDTO retirementDto = new RetirementDTO();
		Retirement retirement = retirementMapper.selectByPrimaryKey(id);
		retirementDto = modelMapper.map(retirement, RetirementDTO.class);
		List<RetirementDetailDTO> retirementDetail = retirementDtlMapper.selectByHeaderKey(id);
		retirementDto.setRetirementDetail(retirementDetail);
		for (int i = 0; i < retirementDetail.size(); i++) {
			List<RetirementImgDTO> retirementImg = retirementImgMapper
					.selectByHeaderKey(retirementDetail.get(i).getId());
			retirementDto.getRetirementDetail().get(i).setRetirementImg(retirementImg);
		}
		List<RetirementQuotationDTO> retirementQuotationDTO = (List<RetirementQuotationDTO>) retirementQuotationMapper.selectByHeaderKey(id);
		retirementDto.setRetirementQuotation(retirementQuotationDTO);
		
		List<RetirementRVDTO> retirementRVDTO = (List<RetirementRVDTO>) retirementRVMapper.selectByHeaderKey(id);
		retirementDto.setRetirementRV(retirementRVDTO);
		
		List<RetirementBastDTO> retirementBastDTO = (List<RetirementBastDTO>) retirementBastMapper.selectByHeaderKey(id);
		retirementDto.setRetirementBast(retirementBastDTO);
		
		return retirementDto;
	}
	
	public RetirementDTO getRetirementByAvmTrxId(String avmTrxId) {
		return retirementMapper.selectByAvmTrxId(avmTrxId);
	}
	
	
	
	public RetirementDTO getRetirementByAvmTrxIdBast(String avmTrxId) {
		return retirementMapper.selectByAvmTrxIdBast(avmTrxId);
	}
	
	public RetirementDetailDTO getSOResultByAssetId(Long assetId) {
		return retirementDtlMapper.getSOResultByAssetId(assetId);
	}
	
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public void saveRV(RetirementDTO retirementDTO,List<RetirementRVDTO> listRV) throws FifException, Exception{
		String rvNumber="";
		for(int y=0;y<listRV.size();y++){
			RetirementRVDTO retirementRVDTO =(RetirementRVDTO)listRV.get(y);
			if(y==0){
				rvNumber = retirementRVDTO.getRvNo();
			}else{
				rvNumber = rvNumber +","+retirementRVDTO.getRvNo();
			}
			retirementRVDTO.setRetirementId(retirementDTO.getId());
			retirementRVDTO.setCreatedBy(retirementDTO.getCreatedBy());
			retirementRVDTO.setCreationDate(retirementDTO.getCreationDate());
			retirementRVDTO.setLastUpdateBy(retirementDTO.getCreatedBy());
			retirementRVDTO.setLastUpdateDate(retirementDTO.getCreationDate());
			retirementRVMapper.insert(retirementRVDTO);
		}
		
		retirementDTO.setRvNumber(rvNumber);
		retirementMapper.updateRvNumber(retirementDTO);
	}
	
	@Transactional(rollbackFor={Exception.class,FifException.class})
	public void save(RetirementDTO retirementDTO, UUID personUUID,List<RetirementDetailDTO> listDetail,List<RetirementQuotationDTO> listQuotation) throws FifException, Exception{
		
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
		
		retirementDTO.setAvmTrxId(UUID.randomUUID());
		retirementDTO.setEbsFlag("Y");
		retirementDTO.setAvmTrxIdString(retirementDTO.getAvmTrxId().toString());
		if(retirementDTO.getId() == null){
			List<KeyValue> listStatus = getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(),RetirementApprovalStatus.ON_PROGRESS.getCode());
			if(listStatus.size()>0){
					KeyValue keyStatus =(KeyValue)listStatus.get(0);
					retirementDTO.setStatusId(((java.math.BigDecimal)keyStatus.getKey()).longValue());
					retirementDTO.setStatusCode((String)keyStatus.getValue());
			}
			
			RetirementDTO asset = retirementMapper.getBranchCode(retirementDTO.getBranchId());
			
			String branchCode;
			if (retirementDTO.getBranchId() == -1){
				branchCode = "HEADOFFICE"; 
			}else {
				branchCode = asset.getBranchCode();
			}
			String docNo = getDocumentNumber(retirementDTO.getRetirementTypeCode(), branchCode);
			retirementDTO.setRequestNo(docNo);
			retirementDTO.setBastDocNo("");
			retirementMapper.insert(retirementDTO);
			
			for(int i=0;i<listDetail.size();i++){
				RetirementDetailDTO	retirementDetailDTO =(RetirementDetailDTO)listDetail.get(i);
				retirementDetailDTO.setRetirementId(retirementDTO.getId());
				retirementDetailDTO.setCreatedBy(retirementDTO.getCreatedBy());
				retirementDetailDTO.setCreationDate(retirementDTO.getCreationDate());
				retirementDetailDTO.setLastUpdateBy(retirementDTO.getCreatedBy());
				retirementDetailDTO.setLastUpdateDate(retirementDTO.getCreationDate());
				retirementDtlMapper.insert(retirementDetailDTO);
				
				if(retirementDetailDTO.getRetirementImg()!=null){
					for(int x=0;x<retirementDetailDTO.getRetirementImg().size();x++){
						RetirementImgDTO retirementImgDTO = (RetirementImgDTO)retirementDetailDTO.getRetirementImg().get(x);
						retirementImgDTO.setRetirementDtlId(retirementDetailDTO.getId());
						retirementImgDTO.setCreatedBy(retirementDTO.getCreatedBy());
						retirementImgDTO.setCreationDate(retirementDTO.getCreationDate());
						retirementImgDTO.setLastUpdateBy(retirementDTO.getCreatedBy());
						retirementImgDTO.setLastUpdateDate(retirementDTO.getCreationDate());
						
						if(retirementImgDTO.getImageFilePath()!=null){
							File file = new File(retirementImgDTO.getImageFilePath());
							String name = file.getName();
							SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
							retirementImgDTO.setImageFilePath(pathFile+name);
						}
						
						retirementImgMapper.insert(retirementImgDTO);
					}
				}
			}
			String recipient = retirementDTO.getRecipient();
			String quotationPrice = null;
			for(int y=0;y<listQuotation.size();y++){
				RetirementQuotationDTO retirementQuotationDTO =(RetirementQuotationDTO)listQuotation.get(y);
				if(retirementQuotationDTO.getWinnerFlag().booleanValue()){
					recipient = retirementQuotationDTO.getVendorName();
					quotationPrice = retirementQuotationDTO.getQuotationPrice();
				}
				retirementQuotationDTO.setRetirementId(retirementDTO.getId());
				retirementQuotationDTO.setCreatedBy(retirementDTO.getCreatedBy());
				retirementQuotationDTO.setCreationDate(retirementDTO.getCreationDate());
				retirementQuotationDTO.setLastUpdateBy(retirementDTO.getCreatedBy());
				retirementQuotationDTO.setLastUpdateDate(retirementDTO.getCreationDate());
				retirementQuotationMapper.insert(retirementQuotationDTO);
			}
			
			retirementDTO.setRecipient(recipient);
			retirementDTO.setQuotationPrice(quotationPrice);
			retirementMapper.updateByPrimaryKey(retirementDTO);
		}
		
		retirementDTO.setObjectEmployeeUUID(personUUID);
		if(retirementDTO.getRetirementTypeCode()!=null && retirementDTO.getRetirementTypeCode().equals(RetirementType.SELL.getCode())){
			getAvmAdapterService().submitAvmTransaction(retirementDTO.getAvmTrxId(), personUUID, retirementDTO,co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT_SELL, 1L);
		}
		else if(retirementDTO.getRetirementTypeCode()!=null && retirementDTO.getRetirementTypeCode().equals(RetirementType.GRANT.getCode())){
			getAvmAdapterService().submitAvmTransaction(retirementDTO.getAvmTrxId(), personUUID, retirementDTO,co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT_GRANT, 1L);
		}
		else if(retirementDTO.getRetirementTypeCode()!=null && retirementDTO.getRetirementTypeCode().equals(RetirementType.INSURANCE.getCode())){
			getAvmAdapterService().submitAvmTransaction(retirementDTO.getAvmTrxId(), personUUID, retirementDTO,co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT_INSURANCE, 1L);
		}
		else if(retirementDTO.getRetirementTypeCode()!=null && retirementDTO.getRetirementTypeCode().equals(RetirementType.LOSE.getCode())){
			getAvmAdapterService().submitAvmTransaction(retirementDTO.getAvmTrxId(), personUUID, retirementDTO,co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT_LOSE, 1L);
		}
		//getAvmAdapterService().submitAvmTransaction(retirementDTO.getAvmTrxId(), personUUID, retirementDTO,co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT, 1L);
		
		}
	
	@Transactional(rollbackFor={Exception.class})
	public void createRV(RetirementDTO retirementDTO)throws Exception{
		
		//RV
				PersonDTO personDTO = getPersonService().getPersonById(securityServiceImpl.getSecurityProfile().getPersonId(), DateUtil.getToday());
				String segment1 = getCompanyService().getCompanyGlCodeById(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				String segment2 = getOrganizationSetupService().findById(personDTO.getPrimaryAssignmentDTO().getOrganizationId())
						.getBranchOwner();
				
				String coyId = getCompanyService().getCompanyCodeById(personDTO.getCompanyId());
				
				List<RetirementDetailDTO> retirementDetail = retirementDtlMapper.selectByHeaderKey(retirementDTO.getId());
				Double price = Double.parseDouble(retirementDTO.getQuotationPrice())/retirementDetail.size();
				
				Map<String, RetirementDetailDTO> dataAccount = new HashMap<String, RetirementDetailDTO>();
				for(int i=0;i<retirementDetail.size();i++){
					RetirementDetailDTO retirementDetailDTO = retirementDetail.get(i);
					//System.out.println("dataAccount.get(retirementDetailDTO.getAccountCode())=="+dataAccount.get(retirementDetailDTO.getAccountCode()));
					if(retirementDetailDTO.getAccountCode()!=null && dataAccount.get(retirementDetailDTO.getAccountCode()) == null){
						retirementDetailDTO.setPrice(price);
						dataAccount.put(retirementDetailDTO.getAccountCode(), retirementDetailDTO);
					}else{
						RetirementDetailDTO data =dataAccount.get(retirementDetailDTO.getAccountCode());
						retirementDetailDTO.setPrice(data.getPrice()+price);
						dataAccount.put(retirementDetailDTO.getAccountCode(), data);
					}
				}
				
				
				String rvNumber = "";
				for (Map.Entry<String, RetirementDetailDTO> entry : dataAccount.entrySet()) {
				RetirementDetailDTO dataAcc =dataAccount.get(entry.getKey());
				
				RvInterface rvInterface = new RvInterface();
				rvInterface.setSequenceNumber(1);
				rvInterface.setTransType("SSOA");
				rvInterface.setFromId(retirementDTO.getCreatedBy().toString());
				rvInterface.setBranchId(segment2);
				rvInterface.setTransDate(new Date());
				rvInterface.setDocumentNumber(null);
				rvInterface.setDescription("NPK " + personDTO.getEmployeeNumber() + " - " + retirementDTO.getRequestNo() );
				rvInterface.setReceivedMethod("CASH");
				rvInterface.setCurrency("IDR");
				rvInterface.setExchangeRate(1);
				rvInterface.setReceivedAmount(new BigDecimal(dataAcc.getPrice()));
				rvInterface.setFee(BigDecimal.ZERO);
				rvInterface.setSegment1(segment1);
				rvInterface.setSegment2(segment2);
				
				rvInterface.setRvNumber(getFIFAppsServiceAdapter().bookRVNumber(segment2, securityServiceImpl.getSecurityProfile().getEmployeeNumber()).getRvNumber());
				rvInterface.setReferenceNumber(rvInterface.getRvNumber());
				//rvInterface.setSegment3(getOrganizationSetupServiceImpl().getCostCenterByPersonId(securityServiceImpl.getSecurityProfile().getPersonId(), personDTO.getCompanyId()));
				//rvInterface.setSegment3("000");
				rvInterface.setSegment3("000");
				rvInterface.setSegment4(dataAcc.getAccountCode()!=null ? dataAcc.getAccountCode() : SSOAConstants.accountCodeBiayaLainLain);
				rvInterface.setSegment5("0000");
				rvInterface.setSegment6("00");
				rvInterface.setSegment7("00000");
				rvInterface.setSegment8("00");
				
				rvInterface.setProcedureName("AP.UPDATE_DESC_HDR(:1, '" + rvInterface.getRvNumber() + "', '" + retirementDTO.getRequestNo() + "')");
				rvInterface.setCreatedBy(securityServiceImpl.getSecurityProfile().getEmployeeNumber());
				rvInterface.setCreateDate(new Date());
				rvInterface.setCompanyId(coyId);
				rvInterface.setRoundRcv(0);
				rvInterface.setSource(segment2);
				
				//Boolean flag = false;
				
				getFIFAppsServiceAdapter().createRv(rvInterface);
				
				
				if(rvInterface.getRvNumber()!=null){
					if(rvNumber.isEmpty()){
						rvNumber = rvInterface.getRvNumber();}
					else{
						rvNumber = rvNumber+","+rvInterface.getRvNumber();
					}
					retirementDTO.setRvNumber(rvNumber);
					retirementMapper.updateRvNumber(retirementDTO);
					
					retirementDTO.setRvNumber(rvInterface.getRvNumber());
					retirementDTO.setAccountCode(dataAcc.getAccountCode());
					retirementDtlMapper.updateRvNumber(retirementDTO);
				}
				
				}
				
	}
	
	
	/*public void submitBAST(RetirementDTO retirementDTO, UUID personUUID, List<RetirementBastDTO> listBast) throws FifException, Exception{
		
		retirementDTO.setAvmTrxId(UUID.randomUUID());
		retirementDTO.setAvmTrxIdString(retirementDTO.getAvmTrxId().toString());
		avmAdapterServiceImpl.submitAvmTransaction(retirementDTO.getAvmTrxId(), personUUID, retirementDTO,co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT, 1L);
	
		List<KeyValue> listStatus = getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(),RetirementApprovalStatus.ON_PROGRESS.getCode());
		if(listStatus.size()>0){
				KeyValue keyStatus =(KeyValue)listStatus.get(0);
				retirementDTO.setBastStatusId(((java.math.BigDecimal)keyStatus.getKey()).longValue());
				retirementDTO.setBastStatusCode((String)keyStatus.getValue());
		}
		
		for(int i=0;i<listBast.size();i++){
			RetirementBastDTO retirementBastDTO =(RetirementBastDTO)listBast.get(i);
			retirementBastDTO.setRetirementId(retirementDTO.getId());
			retirementBastDTO.setCreatedBy(retirementDTO.getCreatedBy());
			retirementBastDTO.setCreationDate(retirementDTO.getCreationDate());
			retirementBastDTO.setLastUpdateBy(retirementDTO.getCreatedBy());
			retirementBastDTO.setLastUpdateDate(retirementDTO.getCreationDate());
			retirementBastMapper.insert(retirementBastDTO);
		}
		
		retirementMapper.updateByPrimaryKey(retirementDTO);
		System.out.println("end");
	}*/

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
		RetirementDTO retirementDTO =(RetirementDTO)transaction;
		//retirementDTO.sets
		
		List<KeyValue> list = (List<KeyValue>)getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(), RetirementApprovalStatus.APPROVED.getCode());
		if(list.size()>0){
			KeyValue key = (KeyValue)list.get(0);
			retirementDTO.setStatusCode((String)key.getValue());
			retirementDTO.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
		}
		
		if(retirementDTO.getRetirementTypeCode()!=null && (retirementDTO.getRetirementTypeCode().equals(RetirementType.GRANT.getCode()) || retirementDTO.getRetirementTypeCode().equals(RetirementType.SELL.getCode()))){
			
			RetirementDTO asset = retirementMapper.getBranchCode(retirementDTO.getBranchId());
			
			String branchCode;
			if (retirementDTO.getBranchId() == -1){
				branchCode = "HEADOFFICE"; 
			}else {
				branchCode = asset.getBranchCode();
			}	
		String bastDocNo = getBastDocumentNumber(branchCode);
		retirementDTO.setBastDocNo(bastDocNo);
		}
		retirementMapper.updateBASTByPrimaryKey(retirementDTO);
		
		if (retirementDTO.getRetirementTypeCode() != null
				&& !retirementDTO.getRetirementTypeCode().equals(RetirementType.GRANT.getCode())
				&& !retirementDTO.getRetirementTypeCode().equals(RetirementType.SELL.getCode())) {
			
			
			List<RetirementDetailDTO> retirementDetail = retirementDtlMapper.selectByHeaderKey(retirementDTO.getId());
			
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
			
			//Map<String, Assets> assets = new HashMap<String, Assets>();
			for (int i = 0; i < retirementDetail.size(); i++) {
				Assets asset = assetsMapper.selectByPrimaryKey(retirementDetail.get(i).getAssetId());
				String paramOut = null;
				asset.setRetiredFlag("Y");
				assetsMapper.updateRetiredFlagById(asset);
				
				//if(assets.get(asset.getAssetNumber()) == null){
					//assets.put(asset.getAssetNumber(), asset);
					retirementDTO.setEbsAssetId(asset.getEbsAssetId());
					retirementDTO.setBookTypeCode(asset.getBookTypeCode());
					retirementMapper.insertRetirementToEbs(retirementDTO);
					
					if(retirementDTO.getResultOut()!=null && retirementDTO.getResultOut().equals("SUCCESS")){
						
						
					}else{
						Messagebox.show(retirementDTO.getResultOut() , "Information", Messagebox.YES,
								Messagebox.ERROR);
						throw new Exception("Process to EBS failed, "+retirementDTO.getResultOut());
					}
					
					AssetSynchronizingDetailDTO assetSynchronizingDetailDTO = new AssetSynchronizingDetailDTO();
					assetSynchronizingDetailDTO.setAssetSyncId(assetSynchronizingDTO.getId());
					assetSynchronizingDetailDTO.setAssetId(asset.getId());
					List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_RETIRE.getCode());
					if(listLookup.size()>0){
						assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
						assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
					}
					assetSynchronizingDetailDTO.setCreatedBy(new Long(-1));
					assetSynchronizingDetailDTO.setCreationDate(new Timestamp(System.currentTimeMillis()));
					assetSynchronizingDetailDTO.setLastUpdateBy(new Long(-1));
					assetSynchronizingDetailDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
					assetSynchronizingDetailMapper.insert(assetSynchronizingDetailDTO);
					
				//}
				
				
				List<Long> dataSOIdList = stockOpnameMapper.selectSOIdbyAssetIdAndActionStatusNotStarted(retirementDetail.get(i).getAssetId());
				for(int x=0;x<dataSOIdList.size();x++){
					if(stockOpnameMapper.countActionStatusNotStartedBySOId(dataSOIdList.get(x))==1){
						StockOpnameDTO stockOpnameDto = (StockOpnameDTO)stockOpnameMapper.selectByPrimaryKey(dataSOIdList.get(x));
						List<KeyValue> listLookup1 = stockOpnameMapper.getLookupKeyValues(SOApprovalStatus.CODE.getCode(),SOApprovalStatus.CLOSED.getCode());
						if(listLookup1.size()>0){
							stockOpnameDto.setStatusId(((BigDecimal)listLookup1.get(0).getKey()).longValue());
							stockOpnameDto.setStatusCode((String)listLookup1.get(0).getValue());
						}
						stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDto);
						stockOpnameMapper.updateSOPeriodDetailStatus(stockOpnameDto);
						stockOpnameMapper.updateByPrimaryKeySelective(stockOpnameDto);
						stockOpnameMapper.updateCountStatus(stockOpnameDto);
						soPeriodMapper.updateStatusSOPeriod(stockOpnameDto);
					}
					
					StockOpnameDetail stockOpnameDetail = stockOpnameDtlMapper
							.selectByHeaderIdAndAssetId(dataSOIdList.get(x), retirementDetail.get(i).getAssetId());
					if (stockOpnameDetail != null) {
						// stockOpnameDetail.setOpnameActionStatus(SOApprovalStatus.CLOSED.getCode());
						stockOpnameDetail.setOpnameActionStatusCode(SOActionStatus.CLOSED.getCode());
						List<KeyValue> lookupActionStatus = stockOpnameMapper.getLookupKeyValues(
								SOActionStatus.CODE.getCode(), SOActionStatus.CLOSED.getCode());
						if (lookupActionStatus.size() > 0) {
							stockOpnameDetail.setOpnameActionStatusId(
									((BigDecimal) lookupActionStatus.get(0).getKey()).longValue());
						}
						stockOpnameDtlMapper.updateOpnameStatus(stockOpnameDetail);
					}
					
					StockOpnameUnregAssetsDTO stockOpnameUnregAssets = stockOpnameUnregAssetsMapper
							.selectByHeaderIdAndAssetId(dataSOIdList.get(x), retirementDetail.get(i).getAssetId());
					if (stockOpnameUnregAssets != null) {
						// stockOpnameUnregAssets.setOpnameActionStatus(SOApprovalStatus.CLOSED.getCode());
						stockOpnameUnregAssets.setOpnameActionStatusCode(SOActionStatus.CLOSED.getCode());
						List<KeyValue> lookupActionStatus = stockOpnameMapper
								.getLookupKeyValues(SOActionStatus.CODE.getCode(), SOActionStatus.CLOSED.getCode());
						if (lookupActionStatus.size() > 0) {
							stockOpnameUnregAssets.setOpnameActionStatusId(
									((BigDecimal) lookupActionStatus.get(0).getKey()).longValue());
						}
						stockOpnameUnregAssetsMapper.updateOpnameStatus(stockOpnameUnregAssets);
					}
					
				}
				
			}
		}
		
		
		
		
		
		
	}

	@Override
	public void doAfterRejected(Object transaction) throws Exception {
		// TODO Auto-generated method stub
				RetirementDTO retirementDTO =(RetirementDTO)transaction;
				//retirementDTO.sets
				List<KeyValue> list = (List<KeyValue>)getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(), RetirementApprovalStatus.REJECTED.getCode());
				if(list.size()>0){
					KeyValue key = (KeyValue)list.get(0);
					retirementDTO.setStatusCode((String)key.getValue());
					retirementDTO.setStatusId(((java.math.BigDecimal)key.getKey()).longValue());
				}
				retirementMapper.updateByPrimaryKey(retirementDTO);
	}

	@Override
	public void doAfterCanceled(Object transaction) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}
	
	public String getDocumentNumber(String retireType, String branchCode){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		RetirementDTO retirementDTO = (RetirementDTO)retirementMapper.getLastRequestNo();
		
		int no =0;
		String lastYear = null;
		if(retirementDTO != null){
			String lastRequestNo = retirementDTO.getRequestNo();
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
		
		if(!lastYear.equals(year)){
			no = 1;
		}
		if(retireType.equals(RetirementType.SELL.getCode())){
			retireType = "JUAL";
		}
		else if(retireType.equals(RetirementType.GRANT.getCode())){
			retireType = "HIBAH";
		}
		else if(retireType.equals(RetirementType.INSURANCE.getCode())){
			retireType = "CLAIM ASS";
		}
		else if(retireType.equals(RetirementType.LOSE.getCode())){
			retireType = "LOSE";
		}
		String monthRoman = convertToRoman(Integer.parseInt(month));
		String number = String.format("%03d", no);
		return number+"/"+companyName+"/"+retireType+"/"+branchCode+"/"+"GSD"+"/"+monthRoman+"/"+year;
	}
	
	
	public String getBastDocumentNumber(String branchName){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		RetirementDTO retirementDTO = (RetirementDTO)retirementMapper.getLastBastDocNo();
		
		int no =0;
		String lastYear = null;
		if(retirementDTO != null){
			String lastRequestNo = retirementDTO.getBastDocNo();
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
		
		if(!lastYear.equals(year)){
			no = 1;
		}
		
		String monthRoman = convertToRoman(Integer.parseInt(month));
		String number = String.format("%03d", no);
		return number+"/"+companyName+"/"+"BASTB"+"/"+branchName+"/"+"GSD"+"/"+monthRoman+"/"+year;
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
	
	
	public void printBAST(RetirementDTO retirementDTO) throws Exception {
		
		Connection conn = DataSourceUtils.getConnection(dataSource);
		Map<String, Object> param = new HashMap<String, Object>();
		
		String outputFileName;
		outputFileName = retirementDTO.getId() +".pdf";
		
		System.out.println("outputFileName=="+outputFileName);
		
		CompanyDTO companyDTO = (CompanyDTO)companyFinder.getCompanyById(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), new Date());
		retirementDTO.setCompanyId(companyDTO.getCompanyId());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("retirementId", retirementDTO.getId());
		params.put("companyId", retirementDTO.getCompanyId());
		
		/*InputStream jasperStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/report/BASTB Hibah.jasper");
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);*/
		ClassLoader classLoader = getClass().getClassLoader();
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(classLoader.getResource("report/BASTB Hibah.jasper"));
		jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		jasperReport.setWhenResourceMissingType(WhenResourceMissingTypeEnum.NULL);
	
		JasperPrint jPrint = (JasperPrint) JasperFillManager .fillReport(jasperReport, params, conn);
		
		try {
			File temp_file = File.createTempFile("RetirementBAST",".pdf");
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

	
	@Override
	@Transactional(readOnly = true)
	public List<RV> getRVByCriteria(RVExample example, int limit, int offset,String action) {
			return rvMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));	
	}
	
	@Override
	@Transactional(readOnly = true)
	public int countRVByCriteria(RVExample example,String action) {
			return rvMapper.countByExample(example);	
		
	}
	

}

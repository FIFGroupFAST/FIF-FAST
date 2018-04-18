package co.id.fifgroup.ssoa.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
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

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dao.CompanyFinder;
import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.ContentType;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.SOPeriodMapper;
import co.id.fifgroup.ssoa.dao.SSOALocationMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameDetailMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameUnregAssetsMapper;
import co.id.fifgroup.ssoa.common.SSOACommons;
import co.id.fifgroup.ssoa.constants.AssetTransferApprovalStatus;
import co.id.fifgroup.ssoa.constants.SOActionStatus;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.AssetTransferNonEBSBastMapper;
import co.id.fifgroup.ssoa.dao.AssetTransferNonEBSDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetTransferNonEBSMapper;
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
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.finder.AssetTransferNonEBSFinder;
import co.id.fifgroup.ssoa.service.AssetTransferNonEBSService;
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
@Service("assetTransferNonEBSService")
public class AssetTransferNonEBSServiceImpl implements AssetTransferNonEBSService {

	// Task Runner Entity
	@Autowired
	private AssetTransferNonEBSMapper assetTransferNonEBSMapper;
	
	@Autowired
	private SOPeriodMapper soPeriodMapper;
	
	@Autowired
	private AssetsMapper assetsMapper;
	
	@Autowired
	private StockOpnameMapper stockOpnameMapper;
	
	@Autowired
	private StockOpnameDetailMapper stockOpnameDtlMapper;
	
	@Autowired
	private StockOpnameUnregAssetsMapper stockOpnameUnregAssetsMapper;
	
	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;
	
	@Autowired
	private AssetTransferNonEBSBastMapper assetTransferNonEBSBastMapper;

	@Autowired
	private AssetTransferNonEBSFinder assetTransferNonEBSFinder;

	@Autowired
	private CompanyFinder companyFinder;
	
	@Autowired
	private AssetTransferNonEBSDetailMapper assetTransferNonEBSDetailMapper;

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
		AssetTransfer assetTransfer = assetTransferNonEBSMapper.selectByPrimaryKey(id);

		assetTransferDto = modelMapper.map(assetTransfer, AssetTransferDTO.class);
		List<AssetTransferDetailDTO> assetTransferDetail = assetTransferNonEBSDetailMapper.selectByHeaderKey(id);
		assetTransferDto.setAssetTransferDetail(assetTransferDetail);
		
		/*List<AssetTransferBastDTO> assetTransferBastDTO = (List<AssetTransferBastDTO>) assetTransferNonEBSBastMapper.selectByHeaderKey(id);
		retirementDto.setRetirementBast(retirementBastDTO);
		*/
		return assetTransferDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferByExample(AssetTransferExample example, int limit, int offset) {
		return assetTransferNonEBSMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetTransferByExample(AssetTransferExample example) {
		return assetTransferNonEBSMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public int countAssetSODtlByCriteria(AssetTransferDetailExample example) {
		return assetTransferNonEBSDetailMapper.countAssetSODtlByCriteria(example);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferDetailDTO> getAssetSODtlByCriteria(AssetTransferDetailExample example, int limit, int offset, String action) {
		return assetTransferNonEBSDetailMapper.selectAssetSODtlByCriteria(example, new RowBounds(offset, limit));
	}
	
	public AssetTransferDetailDTO getSOResultByAssetId(Long assetId) {
		return assetTransferNonEBSDetailMapper.getSOResultByAssetId(assetId);
	}
	
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferDtoByExample(AssetTransferExample example, int limit, int offset) {
		return assetTransferNonEBSFinder.selectByExample(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferDtoByExample(AssetTransferExample example) {
		return assetTransferNonEBSFinder.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferDTO> getAssetTransferByExample(AssetTransferExample example) {
		return assetTransferNonEBSMapper.selectByExample(example);
	}

	
	@Transactional(readOnly = true)
	public int countByExample(AssetTransferExample example) {
		return assetTransferNonEBSFinder.countByExample(example);
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
		
		AssetTransfer assetTransfer = assetTransferNonEBSMapper.selectByPrimaryKey(assetTransferDto.getTransferId());
		if (assetTransfer == null || assetTransfer.getTransferId() == null) {

			AssetTransfer asset = assetTransferNonEBSMapper.getBranchCode(assetTransferDto.getBranchOrigin());
			
			String branchCode;
			if (assetTransferDto.getBranchOrigin() == -1){
				branchCode = "HEADOFFICE"; 
			}else {
				branchCode = asset.getBranchCodeOrigin();
			}
			
			String docNo = getDocumentNumber(branchCode);
			assetTransferDto.setRequestNo(docNo);
			assetTransferDto.setBastDocNo("");
			assetTransferDto.setEbsFlag("N");
			assetTransferDto.setBranchId(assetTransferDto.getBranchId());
			assetTransferDto.setStatusCode(AssetTransferApprovalStatus.IN_PROCESS.getCode());
			assetTransferDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetTransferDto.setLastUpdateDate(new Date());
			assetTransferNonEBSMapper.insert(assetTransferDto);
			
			for (AssetTransferDetail assetDetail : assetTransferDetailDTO) {
				AssetTransferDetail assetTransferDetail = modelMapper.map(assetDetail, AssetTransferDetail.class);
				assetTransferDetail.setTransferId(assetTransferDto.getTransferId());
				assetTransferDetail.setAssetId(assetDetail.getAssetId());
				assetTransferDetail.setCreatedBy(assetTransferDto.getCreatedBy());
				assetTransferDetail.setCreationDate(new Date());
				assetTransferDetail.setLastUpdateBy(assetTransferDto.getCreatedBy());
				assetTransferDetail.setLastUpdateDate(new Date());
				
				assetTransferNonEBSDetailMapper.insert(assetTransferDetail);

			}
			
			assetTransferDto.setObjectEmployeeUUID(personUUID);
			getAvmAdapterService().submitAvmTransaction(assetTransferDto.getAvmTrxId(), personUUID, assetTransferDto,
					co.id.fifgroup.systemworkflow.constants.TrxType.ASSET_TRANSFER_NON_EBS, 1L);
					
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
		assetTransferNonEBSMapper.updateBASTByPrimaryKey(assetTransfer);
		
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
			assetTransferNonEBSBastMapper.insert(assetTransferBast);
		}
		
		
		List<AssetTransferDetailDTO> assetTransferDetail = assetTransferNonEBSDetailMapper.selectByHeaderKey(assetTransferBastDTO.getTransferId());
		
		
		//Map<String, Assets> assetss = new HashMap<String, Assets>();
		for (int i = 0; i < assetTransferDetail.size(); i++) {
			Assets asset = assetsMapper.selectByPrimaryKey(assetTransferDetail.get(i).getAssetId());
			
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
		return assetTransferNonEBSMapper.getLookupKeyValues(lookupName, key);
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
		AssetTransfer asset = assetTransferNonEBSMapper.getBranchCode(assetTransferDTO.getBranchOrigin());
		
		String branchCode;
		if (assetTransferDTO.getBranchOrigin() == -1){
			branchCode = "HEADOFFICE"; 
		}else {
			branchCode = asset.getBranchCodeOrigin();
		}
		
		String bastDocNo = getBastDocumentNumber(branchCode);
		assetTransferDTO.setBastDocNo(bastDocNo);
		assetTransferNonEBSMapper.updateByPrimaryKey(assetTransferDTO);
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
		assetTransferNonEBSMapper.updateByPrimaryKey(assetTransferDTO);
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
		return assetTransferNonEBSDetailMapper.selectDetailByHeaderId(id);
		
	}	
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetTransferBast> getDetailBastByPrimaryKey(Long id) {
		return assetTransferNonEBSDetailMapper.selectDetailBastByHeaderId(id);
		
	}	

	
	@Override
	@Transactional(readOnly = true)
	public int countByBranchId(Assets example,  Long taskGroupId) {
		return assetTransferNonEBSFinder.countAssetByBranchId(example, taskGroupId );
	}

	
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}

	
	
	public AssetTransferDTO getAssetTransferByAvmTrxId(String avmTrxId) {
		return assetTransferNonEBSMapper.selectByAvmTrxId(avmTrxId);
	}
	
	public String getDocumentNumber(String branchCodeOrigin){
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO)assetTransferNonEBSMapper.getLastRequestNo();
		
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
		
		AssetTransferDTO assetTransferDTO = (AssetTransferDTO)assetTransferNonEBSMapper.getLastBastDocNo();
		
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
		
		String outputFileName;
		outputFileName = assetTransferDTO.getTransferId()+".pdf";
		
		System.out.println("outputFileName=="+outputFileName);
		System.out.println("assetTransferDTO.getAvmTrxIdString()=="+ assetTransferDTO.getAvmTrxIdString());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferId", assetTransferDTO.getTransferId());
		params.put("avmTrxId", assetTransferDTO.getAvmTrxIdString());
		
		ClassLoader classLoader = getClass().getClassLoader();
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(classLoader.getResource("report/BAST_MUTASI.jasper"));
		jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		jasperReport.setWhenResourceMissingType(WhenResourceMissingTypeEnum.NULL);
	
		JasperPrint jPrint = (JasperPrint) JasperFillManager .fillReport(jasperReport, params, conn);
		
		try {
			File temp_file = File.createTempFile("AssetTranferBAST",".pdf");
			JasperExportManager.exportReportToPdfFile(jPrint, temp_file.getAbsolutePath());
			DownloadUtil.downloadFile(temp_file.getAbsolutePath(), ContentType.APPLICATIONFILE.getValue());
			File f = new File(temp_file.getAbsolutePath());
			f.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
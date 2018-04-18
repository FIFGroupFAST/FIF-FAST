package co.id.fifgroup.ssoa.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dao.CompanyFinder;
import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.validation.ValidationException;
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
import co.id.fifgroup.ssoa.dao.RetirementBastMapper;
import co.id.fifgroup.ssoa.dao.RetirementDetailMapper;
import co.id.fifgroup.ssoa.dao.RetirementMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameDetailMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameUnregAssetsMapper;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.RetirementBastService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

@Transactional
@Service("retirementBastService")
public class RetirementBastServiceImpl implements RetirementBastService {

	//@Autowired
	//private AvmAdapterService avmAdapterServiceImpl;
	
	public static AvmAdapterService getAvmAdapterService() {
		return (AvmAdapterService) ApplicationContextUtil.getApplicationContext().getBean("avmAdapterServiceImpl");
	}
	
	@Autowired
	private StockOpnameMapper stockOpnameMapper;
	
	@Autowired
	private StockOpnameDetailMapper stockOpnameDtlMapper;
	
	@Autowired
	private StockOpnameUnregAssetsMapper stockOpnameUnregAssetsMapper;

	@Autowired
	private RetirementMapper retirementMapper;

	@Autowired
	private RetirementBastMapper retirementBastMapper;
	
	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;
	
	@Autowired
	private AssetsMapper assetsMapper;
	
	@Autowired
	private RetirementDetailMapper retirementDtlMapper;
	
	@Autowired
	private CompanyFinder companyFinder;
	
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Autowired
	private AssetSynchronizingMapper assetSynchronizingMapper;
	
	@Autowired
	private AssetSynchronizingDetailMapper assetSynchronizingDetailMapper;

	@Override
	public List<KeyValue> getLookupKeyValues(String lookupName, String key) {
		// TODO Auto-generated method stub
		return retirementMapper.getLookupKeyValues(lookupName, key);
	}

	@Transactional(rollbackFor={Exception.class,FifException.class})
	public void submitBAST(RetirementDTO retirementDTO, UUID personUUID, List<RetirementBastDTO> listBast)
			throws FifException, Exception {
		
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
		
		retirementDTO.setObjectEmployeeUUID(personUUID);
		retirementDTO.setAvmTrxIdBast(UUID.randomUUID());
		retirementDTO.setAvmTrxIdBastString(retirementDTO.getAvmTrxIdBast().toString());
		getAvmAdapterService().submitAvmTransaction(retirementDTO.getAvmTrxIdBast(), personUUID, retirementDTO,
				co.id.fifgroup.systemworkflow.constants.TrxType.RETIREMENT_BAST, 1L);

		List<KeyValue> listStatus = getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(),
				RetirementApprovalStatus.ON_PROGRESS.getCode());
		if (listStatus.size() > 0) {
			KeyValue keyStatus = (KeyValue) listStatus.get(0);
			retirementDTO.setBastStatusId(((java.math.BigDecimal) keyStatus.getKey()).longValue());
			retirementDTO.setBastStatusCode((String) keyStatus.getValue());
		}

		for (int i = 0; i < listBast.size(); i++) {
			RetirementBastDTO retirementBastDTO = (RetirementBastDTO) listBast.get(i);
			retirementBastDTO.setRetirementId(retirementDTO.getId());
			retirementBastDTO.setCreatedBy(retirementDTO.getCreatedBy());
			retirementBastDTO.setCreationDate(retirementDTO.getCreationDate());
			retirementBastDTO.setLastUpdateBy(retirementDTO.getCreatedBy());
			retirementBastDTO.setLastUpdateDate(retirementDTO.getCreationDate());
			if(retirementBastDTO.getBastDocPath()!=null){
				File file = new File(retirementBastDTO.getBastDocPath());
				String name = file.getName();
				SSOACommons.moveToRealPath(tmpFile+name, pathFile+name);
				retirementBastDTO.setBastDocPath(pathFile+name);
			}
			retirementBastMapper.insert(retirementBastDTO);
		}

		retirementMapper.updateByPrimaryKey(retirementDTO);
		System.out.println("end");
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
		// TODO Auto-generated method stub
		RetirementDTO retirementDTO = (RetirementDTO) transaction;
		// retirementDTO.sets
		List<KeyValue> list = (List<KeyValue>) getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(),
				RetirementApprovalStatus.APPROVED.getCode());
		if (list.size() > 0) {
			KeyValue key = (KeyValue) list.get(0);
			retirementDTO.setBastStatusCode((String) key.getValue());
			retirementDTO.setBastStatusId(((java.math.BigDecimal) key.getKey()).longValue());
		}
		String bastDocNo = getBastDocumentNumber(retirementDTO.getBranchName());
		retirementDTO.setBastDocNo(bastDocNo);
		retirementMapper.updateByPrimaryKey(retirementDTO);
		
		
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

	@Override
	public void doAfterRejected(Object transaction) throws Exception {
		// TODO Auto-generated method stub
		RetirementDTO retirementDTO = (RetirementDTO) transaction;
		// retirementDTO.sets
		List<KeyValue> list = (List<KeyValue>) getLookupKeyValues(RetirementApprovalStatus.CODE.getCode(),
				RetirementApprovalStatus.REJECTED.getCode());
		if (list.size() > 0) {
			KeyValue key = (KeyValue) list.get(0);
			retirementDTO.setBastStatusCode((String) key.getValue());
			retirementDTO.setBastStatusId(((java.math.BigDecimal) key.getKey()).longValue());
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

}

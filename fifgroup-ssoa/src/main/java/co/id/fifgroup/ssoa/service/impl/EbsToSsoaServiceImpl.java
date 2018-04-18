package co.id.fifgroup.ssoa.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.poi.ss.usermodel.Cell;
import org.zkoss.poi.ss.usermodel.Row;
import org.zkoss.poi.xssf.usermodel.XSSFSheet;
import org.zkoss.poi.xssf.usermodel.XSSFWorkbook;

import co.id.fifgroup.basicsetup.dao.GlobalSettingFinderMapper;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.constants.ProcessType;
import co.id.fifgroup.ssoa.constants.SOApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetSynchronizingMapper;
import co.id.fifgroup.ssoa.dao.EbsToSsoaMapper;
import co.id.fifgroup.ssoa.dao.StockOpnameMapper;
import co.id.fifgroup.ssoa.dto.AssetDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDTO;
import co.id.fifgroup.ssoa.dto.AssetSynchronizingDetailDTO;
import co.id.fifgroup.ssoa.service.EbsToSsoaService;


@Transactional
@Service("ebsToSsoaService")
public class EbsToSsoaServiceImpl implements EbsToSsoaService {

	@Autowired
	private EbsToSsoaMapper ebsToSsoaMapper;
	
	@Autowired
	private GlobalSettingFinderMapper globalsettingMapper;

	@Autowired
	private StockOpnameMapper stockOpnameMapper;

	@Autowired
	private AssetSynchronizingMapper assetSynchronizingMapper;

	@Autowired
	private AssetSynchronizingDetailMapper assetSynchronizingDetailMapper;

	@Override
	@Transactional(readOnly = true)
	public String ebsToSsoaSynchronizing() {
		int initialRow = 0;
		int column = 0;
		Boolean errorFlag = false;

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Sheet1");

		Row row = sheet.createRow(initialRow);
		Cell cell;
		cell = row.createCell(column++);
		cell.setCellValue((String) "ASSET NUMBER");
		cell = row.createCell(column++);
		cell.setCellValue((String) "ERROR MESSAGE");

		KeyValue successSyncStatus = (KeyValue)stockOpnameMapper
				.getLookupKeyValues("ASSET_SYNC_STATUS", "ASSET_SYNC_STATUS_SUCCESS").get(0);
		KeyValue errorSyncStatus = (KeyValue)stockOpnameMapper.getLookupKeyValues("ASSET_SYNC_STATUS", "ASSET_SYNC_STATUS_ERROR")
				.get(0);
		KeyValue onProgressSyncStatus = (KeyValue)stockOpnameMapper
				.getLookupKeyValues("ASSET_SYNC_STATUS", "ASSET_SYNC_STATUS_ON_PROGRESS").get(0);
		KeyValue syncType = (KeyValue)stockOpnameMapper.getLookupKeyValues("ASSET_SYNC_TYPE", "ASSET_SYNC_TYPE_EBS_TO_SSOA")
				.get(0);

		// System.out.println(syncType.getKey() + "== "+ syncType.getValue());
		AssetSynchronizingDTO assetSynchronizingDTO = new AssetSynchronizingDTO();
		assetSynchronizingDTO.setSyncTypeId(((BigDecimal) syncType.getKey()).longValue());
		assetSynchronizingDTO.setSyncTypeCode((String) syncType.getValue());
		assetSynchronizingDTO.setProcessDate(new Timestamp(System.currentTimeMillis()));
		assetSynchronizingDTO.setDescription("EBS TO i-SCAN");
		assetSynchronizingDTO.setStatusId(((BigDecimal) onProgressSyncStatus.getKey()).longValue());
		assetSynchronizingDTO.setStatusCode((String) onProgressSyncStatus.getValue());
		assetSynchronizingDTO.setCreatedBy(new Long(-1));
		assetSynchronizingDTO.setCreationDate(new Timestamp(System.currentTimeMillis()));
		assetSynchronizingDTO.setLastUpdateBy(new Long(-1));
		assetSynchronizingDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
		assetSynchronizingMapper.insert(assetSynchronizingDTO);

		Timestamp syncTime = new Timestamp(System.currentTimeMillis());
		List<AssetDTO> vwAssetEbsDtoList = ebsToSsoaMapper.getViewAssetFromEbsDaily();
		
		int startRowData = 1;
		
		//Sinkronisai Master EBS
		ebsToSsoaMapper.deleteSocEbsBookTypes();
		ebsToSsoaMapper.deleteSocEbsCategories();
		ebsToSsoaMapper.insertSocEbsBookTypes();
		ebsToSsoaMapper.insertSocEbsCategories();
		
		if (vwAssetEbsDtoList != null) {
			for (int i = 0; i < vwAssetEbsDtoList.size(); i++) {
				AssetDTO assetEbsDto = vwAssetEbsDtoList.get(i);

				if (assetEbsDto.getCompanyId() == null) {
					errorFlag = true;
					row = sheet.createRow(startRowData++);
					column = 0;
					cell = row.createCell(column++);
					cell.setCellValue((Long) assetEbsDto.getEbsAssetId());
					cell = row.createCell(column++);
					cell.setCellValue((String) "Company ID tidak ditemukan");
				}

				if (assetEbsDto.getBranchId() == null) {
					errorFlag = true;
					row = sheet.createRow(startRowData++);
					column = 0;
					cell = row.createCell(column++);
					cell.setCellValue((Long) assetEbsDto.getEbsAssetId());
					cell = row.createCell(column++);
					cell.setCellValue((String) "Branch ID tidak ditemukan");
				}

				if (assetEbsDto.getLocationId() == null) {
					errorFlag = true;
					row = sheet.createRow(startRowData++);
					column = 0;
					cell = row.createCell(column++);
					cell.setCellValue((Long) assetEbsDto.getEbsAssetId());
					cell = row.createCell(column++);
					cell.setCellValue((String) "Location ID tidak ditemukan");
				}

				if (assetEbsDto.getCategoryId() == null) {
					errorFlag = true;
					row = sheet.createRow(startRowData++);
					column = 0;
					cell = row.createCell(column++);
					cell.setCellValue((Long) assetEbsDto.getEbsAssetId());
					cell = row.createCell(column++);
					cell.setCellValue((String) "Category ID tidak ditemukan");
				}

				if (!errorFlag) {
					List<AssetDTO> checkAsset = ebsToSsoaMapper.getAssetbyEbsId(assetEbsDto.getEbsAssetId());
					
					if (checkAsset.size() == 0) {
						System.out.println("INSERT==" + assetEbsDto.getEbsAssetId());
						
						Long maxCostLVA = null;

						List<GlobalSettingDTO> maxOriginCostForLVA = getGlobalsettingByCode(SSOAConstants.maxOriginalCostForLVA);
						if (maxOriginCostForLVA != null) {
							maxCostLVA = Long.parseLong(maxOriginCostForLVA.get(0).getGlobalSetting().getValue());
						}
						// insert
						for (int j = 0; j < assetEbsDto.getInitialUnits(); j++) {
							
							if(assetEbsDto.getOriginalCost() < maxCostLVA)
							{
								assetEbsDto.setAssetTypeCode("ASSET_TYPE_LVA");
							}
							else 
							{
								assetEbsDto.setAssetTypeCode("ASSET_TYPE_AST");
							}
							
							
							ebsToSsoaMapper.insert(assetEbsDto);

							AssetSynchronizingDetailDTO assetSynchronizingDetailDTO = new AssetSynchronizingDetailDTO();
							assetSynchronizingDetailDTO.setAssetSyncId(assetSynchronizingDTO.getId());
							assetSynchronizingDetailDTO.setAssetId(assetEbsDto.getId());
							
							if ("Y".equals(assetEbsDto.getRetiredFlag()) == true) {
								assetSynchronizingDetailDTO.setStockOpnameUnregAssetId(assetEbsDto.getEbsAssetId());
							}
							
							if(assetEbsDto.getTransactionTypeCode()!=null && assetEbsDto.getTransactionTypeCode().contains("TRANSFER")){
								List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_TRANSFER.getCode());
								if(listLookup.size()>0){
									assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
									assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
								}
							}else if(assetEbsDto.getTransactionTypeCode()!=null && assetEbsDto.getTransactionTypeCode().contains("ADDITION")){
								List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_ADDITION.getCode());
								if(listLookup.size()>0){
									assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
									assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
								}
							}else if(assetEbsDto.getTransactionTypeCode()!=null && assetEbsDto.getTransactionTypeCode().contains("RETIREMENT")){
								List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_RETIRE.getCode());
								if(listLookup.size()>0){
									assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
									assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
								}
							}
							
							assetSynchronizingDetailDTO.setCreatedBy(new Long(-1));
							assetSynchronizingDetailDTO.setCreationDate(new Timestamp(System.currentTimeMillis()));
							assetSynchronizingDetailDTO.setLastUpdateBy(new Long(-1));
							assetSynchronizingDetailDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
							assetSynchronizingDetailMapper.insert(assetSynchronizingDetailDTO);
						}
					} else {
						System.out.println("UPDATE==" + assetEbsDto.getEbsAssetId());
						// update
						if (assetEbsDto.getInitialUnits().longValue() == 1) {
							ebsToSsoaMapper.updateByEbsId(assetEbsDto);

							Long assetIdExisting = checkAsset.get(0).getId();
							AssetSynchronizingDetailDTO assetSynchronizingDetailDTO = new AssetSynchronizingDetailDTO();
							assetSynchronizingDetailDTO.setAssetSyncId(assetSynchronizingDTO.getId());
							assetSynchronizingDetailDTO.setAssetId(assetIdExisting);
							
							if(assetEbsDto.getTransactionTypeCode()!=null && assetEbsDto.getTransactionTypeCode().contains("TRANSFER")){
								List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_TRANSFER.getCode());
								if(listLookup.size()>0){
									assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
									assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
								}
							}else if(assetEbsDto.getTransactionTypeCode()!=null && assetEbsDto.getTransactionTypeCode().contains("ADDITION")){
								List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_ADDITION.getCode());
								if(listLookup.size()>0){
									assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
									assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
								}
							}else if(assetEbsDto.getTransactionTypeCode()!=null && assetEbsDto.getTransactionTypeCode().contains("RETIREMENT")){
								List<KeyValue> listLookup = stockOpnameMapper.getLookupKeyValues(ProcessType.CODE.getCode(),ProcessType.PROCESS_TYPE_RETIRE.getCode());
								if(listLookup.size()>0){
									assetSynchronizingDetailDTO.setProcessTypeId(((BigDecimal)listLookup.get(0).getKey()).longValue());
									assetSynchronizingDetailDTO.setProcessTypeCode((String)listLookup.get(0).getValue());
								}
							}
							assetSynchronizingDetailDTO.setCreatedBy(new Long(-1));
							assetSynchronizingDetailDTO.setCreationDate(new Timestamp(System.currentTimeMillis()));
							assetSynchronizingDetailDTO.setLastUpdateBy(new Long(-1));
							assetSynchronizingDetailDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
							assetSynchronizingDetailMapper.insert(assetSynchronizingDetailDTO);
						} else {
							// "Unit lebih besar dari 1 tidak di update");
						}
					}
				}
			}
		}

		try {
			// Write the workbook
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			out.close();

			byte[] xls = out.toByteArray();

			if (errorFlag == true) {
				assetSynchronizingDTO.setErrorLog(xls);
				assetSynchronizingDTO.setStatusId(((BigDecimal) errorSyncStatus.getKey()).longValue());
				assetSynchronizingDTO.setStatusCode((String) errorSyncStatus.getValue());
				assetSynchronizingDTO.setLastUpdateBy(new Long(-1));
				assetSynchronizingDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
				assetSynchronizingMapper.updateByPrimaryKeySelective(assetSynchronizingDTO);
			} else {
				assetSynchronizingDTO.setErrorLog(xls);
				assetSynchronizingDTO.setStatusId(((BigDecimal) successSyncStatus.getKey()).longValue());
				assetSynchronizingDTO.setStatusCode((String) successSyncStatus.getValue());
				assetSynchronizingDTO.setLastUpdateBy(new Long(-1));
				assetSynchronizingDTO.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
				assetSynchronizingMapper.updateByPrimaryKeySelective(assetSynchronizingDTO);
			}

			ebsToSsoaMapper.updateLastSyncDate(syncTime, "YYYY/MM/DD HH24:MI:SS", "LAST_SYNC_DATE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<GlobalSettingDTO> getGlobalsettingByCode(String code){
		return globalsettingMapper.getGlobalSettingDtoByCodeAndDescription(code, null);
	}

}

package co.id.fifgroup.eligibility.upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.core.service.LookupServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.eligibility.constant.FieldType;
import co.id.fifgroup.eligibility.constant.LookupValueFrom;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.service.DecisionTableService;

@Service
public class TemplateGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateGenerator.class);
	
	@Autowired
	private LookupServiceAdapter lookupService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private DecisionTableService decisionTableServiceImpl;
	
	/**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * Untuk data yg banyak, query findDecisionTableById akan butuh waktu yg cukup lama
	 * dari pada query berkali2, jadikan DecisionTableDTO sebagai parameter
	 * By Jatis (HS) 24/03/2016
	 */
	public byte[] generate(String moduleName, DecisionTableModelDTO model, Long decisionTableId) {
		return generate( moduleName, model, decisionTableId, null); 
	}
	
	public byte[] generate(String moduleName, DecisionTableModelDTO model, Long decisionTableId, DecisionTableDTO decTable) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFFont boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle centerCellStyle = workbook.createCellStyle();
		centerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		centerCellStyle.setFont(boldFont);
		HSSFSheet mainSheet = workbook.createSheet("Decision Table - " + model.getName());
		HSSFRow headerRow = mainSheet.createRow(0);
		
		//DecisionTableDTO decTable = 
		if(decTable == null)
			decTable = decisionTableServiceImpl.findDecisionTableById(moduleName, decisionTableId);
		//END 16022316000474
				
		int colNum = 0;
		
		for (int i = 0; i < model.getConditions().size(); i++) {
			DecisionTableColumnDTO column = model.getConditions().get(i);
			HSSFCell headerCell = headerRow.createCell(i);
			headerCell.setCellValue(new HSSFRichTextString(column.getName()));
			mainSheet.setColumnWidth(colNum++, 5000);
			headerCell.setCellStyle(centerCellStyle);
			
			if (column.getField().getFieldType() == FieldType.LOOKUP) {
				List<KeyValue> lookups = lookupService.getLookups(column.getField().getLookupName(),
						securityService.getSecurityProfile().getWorkspaceBusinessGroupId(), 
						securityService.getSecurityProfile().getWorkspaceCompanyId(), true);
				if (workbook.getSheet("Lookup " + column.getField().getLookupName()) == null) {
					HSSFSheet lookupSheet = workbook.createSheet("Lookup " + column.getField().getLookupName());
					int j = 1;
					HSSFRow hRow = lookupSheet.createRow(0);
					LookupValueFrom lookupValueFrom = column.getField().getLookupValueFrom();
					if (lookupValueFrom == LookupValueFrom.CODE)
						hRow.createCell(0).setCellValue("Lookup Code");
					if (lookupValueFrom == LookupValueFrom.MEANING)
						hRow.createCell(0).setCellValue("Lookup Meaning");
					hRow.createCell(1).setCellValue("Lookup Description");
					if (lookups != null){
						for (KeyValue lookup : lookups) {
							HSSFRow row = lookupSheet.createRow(j++);
							HSSFCell cell = row.createCell(0);
							if (lookupValueFrom == LookupValueFrom.CODE) {	
								cell.setCellValue(String.valueOf(lookup.getKey()));
								lookupSheet.setColumnWidth(0, 5000);
							}
							if (lookupValueFrom == LookupValueFrom.MEANING) {	
								cell.setCellValue(String.valueOf(lookup.getValue()));
								lookupSheet.setColumnWidth(0, 5000);
							}
							cell = row.createCell(1);
							cell.setCellValue(String.valueOf(lookup.getDescription()));
							lookupSheet.setColumnWidth(1, 5000);
						}
					}
				}
			}
		}
		
		for (int i = 0; i < model.getResults().size(); i++) {
			DecisionTableColumnDTO column = model.getResults().get(i);
			HSSFCell headerCell = headerRow.createCell(colNum);
			headerCell.setCellValue(new HSSFRichTextString(column.getName()));
			mainSheet.setColumnWidth(colNum++, 5000);
			headerCell.setCellStyle(centerCellStyle);
			
			if (column.getField().getFieldType() == FieldType.LOOKUP) {
				List<KeyValue> lookups = lookupService.getLookups(column.getField().getLookupName(),
						securityService.getSecurityProfile().getWorkspaceBusinessGroupId(), 
						securityService.getSecurityProfile().getWorkspaceCompanyId(), true);
				if (null == workbook.getSheet(column.getField().getLookupName())) {
					HSSFSheet lookupSheet = workbook.createSheet(column.getField().getLookupName());
					int j = 1;
					HSSFRow hRow = lookupSheet.createRow(0);
					hRow.createCell(0).setCellValue("Lookup Code");
					hRow.createCell(1).setCellValue("Lookup Description");
					for (KeyValue lookup : lookups) {
						HSSFRow row = lookupSheet.createRow(j++);
						HSSFCell cell = row.createCell(0);
						cell.setCellValue(String.valueOf(lookup.getKey()));
						lookupSheet.setColumnWidth(0, 5000);
						cell = row.createCell(1);
						cell.setCellValue(String.valueOf(lookup.getDescription()));
						lookupSheet.setColumnWidth(1, 5000);
					}
				}
			}
		}
		
		int rowNum = 1;
		for (int i = 0; i < decTable.getRows().size(); i++) {
			HSSFRow row = mainSheet.createRow(rowNum++);
			List<String> conditionList = decTable.getRows().get(i).getConditionList();
			List<String> resultList = decTable.getRows().get(i).getResultList();
			for(int j=0; j<model.getConditions().size(); j++) {
				HSSFCell keyCell = row.createCell(j);
				keyCell.setCellValue(new HSSFRichTextString(
						conditionList.size() - 1 < j ? "" : conditionList.get(j)));
			}
			int lastCol = model.getConditions().size();
			for (int k = 0; k < model.getResults().size(); k++) {
				HSSFCell keyCell = row.createCell(lastCol++);
				keyCell.setCellValue(new HSSFRichTextString(
						resultList.size() - 1 < k ? "" : resultList.get(k)));
			}
			
		}
		
		return toByteArray(workbook);
	}
	
	private byte[] toByteArray(HSSFWorkbook workbook) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook.write(baos);
			return baos.toByteArray();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}
	
	public byte[] generateFailedRecords(List<UploadError> uploadErrors, List<String> headers) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Row Number")
		  .append(",");
		
		for (int i = 0 ; i < headers.size() ; i++) {
			sb.append(headers.get(i))
			  .append(",");
		}
		sb.append("Error Message")
		  .append("\n");
		
		for (UploadError error : uploadErrors) {
			sb.append(error.getRowNumber())
			  .append(",")
			  .append(error.getRawData())
			  .append(",")
			  .append(error.getErrorMessage())
			  .append("\n");
		}
		return sb.toString().getBytes();
		
	}
	
}

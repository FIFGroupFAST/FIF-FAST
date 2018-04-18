package co.id.fifgroup.systemadmin.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
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
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;

import co.id.fifgroup.core.util.DateUtil;

import co.id.fifgroup.audit.ControlType;
import co.id.fifgroup.systemadmin.dto.AuditTrailDTO;
import co.id.fifgroup.systemadmin.finder.AuditFinder;
import co.id.fifgroup.systemadmin.service.AuditTrailService;

@Service("auditTrailService")
@Transactional(readOnly=true)
public class AuditTrailServiceImpl implements AuditTrailService {
	
	private static Logger log = LoggerFactory.getLogger(AuditTrailServiceImpl.class);
	
	@Autowired
	AuditFinder auditFinder;

	@Override
	public int countAuditByExample(AuditTrailDTO auditTrailDTO, Date dateFrom, Date dateTo) {
		return auditFinder.countAuditByExample(dateFrom, dateTo, auditTrailDTO.getModuleId(), auditTrailDTO.getTransactionType(), auditTrailDTO.getUserName());
	}

	@Override
	public List<AuditTrailDTO> getAuditByExample(AuditTrailDTO auditTrailDTO, Date dateFrom, Date dateTo, int limit, int offset) {
		return auditFinder.findAuditByExample(dateFrom, dateTo, auditTrailDTO.getModuleId(), auditTrailDTO.getTransactionType(), auditTrailDTO.getUserName()
				, new RowBounds(offset, limit));
	}

	private void getAuditSheet(HSSFWorkbook workbook, HSSFFont boldfont, HSSFCellStyle centerCellStyle, ListModelList<AuditTrailDTO> listModelAudit) {
		HSSFSheet templateSheet = workbook.createSheet("Audit Trail");
		
		HSSFRow headerRow = templateSheet.createRow(0);
		headerRow.createCell(0).setCellValue(new HSSFRichTextString("No."));
		headerRow.createCell(1).setCellValue(new HSSFRichTextString("Module Name"));
		headerRow.createCell(2).setCellValue(new HSSFRichTextString("Transaction Type"));
		headerRow.createCell(3).setCellValue(new HSSFRichTextString("Activity"));
		headerRow.createCell(4).setCellValue(new HSSFRichTextString("Transaction ID"));
		headerRow.createCell(5).setCellValue(new HSSFRichTextString("Attribute"));
		headerRow.createCell(6).setCellValue(new HSSFRichTextString("Old Value"));
		headerRow.createCell(7).setCellValue(new HSSFRichTextString("New Value"));
		headerRow.createCell(8).setCellValue(new HSSFRichTextString("User Name"));
		headerRow.createCell(9).setCellValue(new HSSFRichTextString("Log Time"));
						
		for(int i=0; i<headerRow.getLastCellNum(); i++) {
			templateSheet.setColumnWidth(i, 5000);
			headerRow.getCell(i).setCellStyle(centerCellStyle);
		}
		
		int rowNum = 0;
		for (AuditTrailDTO data : listModelAudit) {
			HSSFRow row = templateSheet.createRow(++rowNum);
			HSSFCell cellNo = row.createCell(0);
			HSSFCell cellModule = row.createCell(1);
			HSSFCell cellTransactionType = row.createCell(2);
			HSSFCell cellActivity = row.createCell(3);
			HSSFCell cellTransactionId = row.createCell(4);
			HSSFCell cellAttribute = row.createCell(5);
			HSSFCell cellOldValue = row.createCell(6);
			HSSFCell cellNewValue = row.createCell(7);
			HSSFCell cellUserName = row.createCell(8);
			HSSFCell cellLogTime = row.createCell(9);
			
			cellNo.setCellValue(new HSSFRichTextString(String.valueOf(data.getNumber())));
			cellModule.setCellValue(new HSSFRichTextString(data.getModuleName()));
			cellTransactionType.setCellValue(new HSSFRichTextString(data.getTransactionType()));
			cellActivity.setCellValue(new HSSFRichTextString(data.getControlType() != null ?
					ControlType.getValue(Integer.valueOf(data.getControlType().toString())) : ""));
			cellTransactionId.setCellValue(new HSSFRichTextString(
					data.getTrxId() != null ? String.valueOf(data.getTrxId()) : ""));
			cellAttribute.setCellValue(new HSSFRichTextString(data.getAuditDetail().getAttributeName()));
			cellOldValue.setCellValue(new HSSFRichTextString(data.getAuditDetail().getAttributeCurrentValue()));
			cellNewValue.setCellValue(new HSSFRichTextString(data.getAuditDetail().getAttributeNewValue()));
			cellUserName.setCellValue(new HSSFRichTextString(data.getUserName()));
			cellLogTime.setCellValue(new HSSFRichTextString(DateUtil.format(data.getAuditTimestamp(), "dd MMM yyyy HH:mm:ss")));
		}
	}
		
	@Override
	public void generatePDF(ListModelList<AuditTrailDTO> listModelAudit) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFFont boldfont = workbook.createFont();
		boldfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle centerCellStyle = workbook.createCellStyle();
		centerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		centerCellStyle.setFont(boldfont);
		getAuditSheet(workbook, boldfont, centerCellStyle, listModelAudit);
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			AMedia amedia = new AMedia("AuditTrail.xlsx", "xls", "application/file", out.toByteArray());
			Filedownload.save(amedia);
			out.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}

package co.id.fifgroup.workstructure.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrMinMax;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.constant.BranchSizeType;
import co.id.fifgroup.workstructure.domain.BranchSizeUploadStage;
import co.id.fifgroup.workstructure.domain.BranchSizeUploadStageExample;
import co.id.fifgroup.workstructure.service.BranchSizeSetupService;
import co.id.fifgroup.workstructure.service.BranchSizeUploadService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.constant.BatchStatus;
import co.id.fifgroup.core.constant.UploadErrorStatus;
import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.core.service.AbstractUploadDownloadServiceCSV;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.workstructure.dao.BranchSizeUploadStageMapper;
import co.id.fifgroup.workstructure.dto.BranchSizeDTO;
import co.id.fifgroup.workstructure.dto.BranchSizeStageDTO;
import co.id.fifgroup.workstructure.dto.BranchSizeUploadsDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.finder.BranchSizeUploadFinder;
import co.id.fifgroup.workstructure.validation.BranchSizeUploadValidator;

@Transactional
@Service
public class BranchSizeUploadServiceImpl extends AbstractUploadDownloadServiceCSV<BranchSizeStageDTO> implements BranchSizeUploadService {

	private Logger logger = LoggerFactory.getLogger(BranchSizeUploadServiceImpl.class);
	
	@Autowired private BranchSizeUploadStageMapper branchSizeUploadStageMapper;
	@Autowired private BranchSizeUploadFinder branchSizeUploadFinder;
	@Autowired private BranchSizeUploadValidator branchSizeUploadValidator;
	@Autowired private BranchSizeSetupService branchSizeSetupServiceImpl;
	@Autowired private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired private ModelMapper modelMapper;
	
	private final String headerStartDate = "Effective Start Date";
	private final String headerCode = "Organization Code";
	private final String headerBranchSize = "Branch Size (Code)";
	private final String headerErrorMessage = "Error Messages";
	
	@Override
	public void downloadTemplate(Object... args) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFFont boldfont = workbook.createFont();
		boldfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle centerCellStyle = workbook.createCellStyle();
		centerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		centerCellStyle.setFont(boldfont);
		
		getTemplate(workbook);
		getOrganizationSheet(workbook, boldfont, centerCellStyle);
		getBranchSizeSheet(workbook, boldfont, centerCellStyle);
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			AMedia amedia = new AMedia("BranchSizeTemplate.xls", "xls", "application/file", out.toByteArray());
			Filedownload.save(amedia);
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void getTemplate(HSSFWorkbook workbook) {
		HSSFSheet templateSheet = workbook.createSheet("Template");
		
		HSSFRow headerRow = templateSheet.createRow(0);
		headerRow.createCell(0).setCellValue(new HSSFRichTextString(headerStartDate));
		headerRow.createCell(1).setCellValue(new HSSFRichTextString(headerCode));
		headerRow.createCell(2).setCellValue(new HSSFRichTextString(headerBranchSize));
		
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
				
		for(int i=0; i<headerRow.getLastCellNum(); i++) {
			templateSheet.setColumnWidth(i, 5000);
			headerRow.getCell(i).setCellStyle(cellStyle);
		}
	}

	@Transactional(readOnly=true)
	private void getOrganizationSheet(HSSFWorkbook workbook, HSSFFont boldfont, HSSFCellStyle centerCellStyle) {
		OrganizationDTO example = new OrganizationDTO();
		example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setEffectiveOnDate(DateUtil.truncate(new Date()));
		List<OrganizationDTO> orgs = organizationSetupServiceImpl.findByExample(example);
		
		HSSFSheet sheet = workbook.createSheet("Organization List");
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 10000);
		
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		
		int rowNum = 0;
		
		HSSFRow headerRow = sheet.createRow(rowNum++);
		headerRow.createCell(0).setCellValue(new HSSFRichTextString("Organization Code"));
		headerRow.createCell(1).setCellValue(new HSSFRichTextString("Organization Name"));
		headerRow.getCell(0).setCellStyle(cellStyle);
		headerRow.getCell(1).setCellStyle(cellStyle);
		
		for(OrganizationDTO org : orgs) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(new HSSFRichTextString(org.getCode()));
			row.createCell(1).setCellValue(new HSSFRichTextString(org.getName()));
		}
	}
	
	private void getBranchSizeSheet(HSSFWorkbook workbook, HSSFFont boldfont, HSSFCellStyle centerCellStyle) {
		List<BranchSizeType> sizes = Arrays.asList(BranchSizeType.values());
		
		HSSFSheet sheet = workbook.createSheet("Branch Size List");
		sheet.setColumnWidth(0, 7000);
		
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		
		int rowNum = 0;
		
		HSSFRow headerRow = sheet.createRow(rowNum++);
		headerRow.createCell(0).setCellValue(new HSSFRichTextString("Branch Size"));
		headerRow.getCell(0).setCellStyle(cellStyle);
		
		for(BranchSizeType type : sizes) {
			if(!type.equals(BranchSizeType.SELECT)) {
				HSSFRow row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(new HSSFRichTextString(type.getDesc()));
			}
		}
	}

	@Override
	public void downloadFailedRecords(Long uploadId) {
		List<UploadError> uploadErrors = getFailedRows(uploadId);
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmYYYYhhMMS");
		ICsvMapWriter mapWriter = null;
		String fileName = File.separator + "writeWithCsvMapWriter"+ sdf.format(new Date()) +".csv";
		try {
			mapWriter = new CsvMapWriter(new FileWriter(GlobalVariable.getAppsRootFolder() + fileName), 
					CsvPreference.STANDARD_PREFERENCE);
			List<String> fieldMapping = new ArrayList<String>();
			fieldMapping.add("Row Number");
			fieldMapping.add("Effective Start Date");
			fieldMapping.add("Organization Code");
			fieldMapping.add("Branch Size (Code)");
			fieldMapping.add("Error Message");
			String[] header = fieldMapping.toArray(new String[] {});
			
			List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});
			mapWriter.writeHeader(header);
			for (UploadError error : uploadErrors) {
				Map<String, Object> map = new HashMap<String, Object>();
//				List<String> data = FileUtil.unwrapAndMark(error.getRawData());
				String[] rawData = error.getRawData().split(",");
				map.put(header[0], error.getRowNumber());
				for (int i = 0; i < rawData.length; i++) {
					map.put(header[i+1], rawData[i]);					
				}
				map.put(header[header.length-1], error.getErrorMessage());
				mapWriter.write(map, header, processors);
			}
			File fileCsv = new File(GlobalVariable.getAppsRootFolder() + fileName);
			InputStream is = new FileInputStream(fileCsv);
			AMedia amedia = new AMedia("Branch Size Error.csv", "xls", "application/file", is);
			Filedownload.save(amedia);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(mapWriter != null) {
				try {
					mapWriter.close();
					File file = new File(GlobalVariable.getAppsRootFolder() + fileName);
					file.delete();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	@Override
	public BatchUpload getOpenStatusObject(Object... args) {
		BatchUpload batchUpload = new BatchUpload();
		batchUpload.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		batchUpload.setStatus(BatchStatus.OPEN);
		List<BatchUpload> batchUploads = findBatchUploadByExample(batchUpload);
		if(batchUploads.size() == 1) {
			return batchUploads.get(0);
		}
		return null;
	}

	@Override
	public List<BranchSizeUploadStage> selectByExample(
			BranchSizeUploadStageExample branchSizeStageExample) {
		return branchSizeUploadStageMapper.selectByExample(branchSizeStageExample);
	}
	
	@Override
	public Integer countByExample(
			BranchSizeUploadStageExample branchSizeStageExample) {
		return branchSizeUploadStageMapper.countByExample(branchSizeStageExample);
	}

	@Override
	public List<BranchSizeUploadsDTO> getBranchSizeUploads(BranchSizeUploadsDTO example) {
		return branchSizeUploadFinder.getBranchSizeUploads(example);
	}

	@Override
	protected String[] getFieldMapping(Object... args) {
		String[] fieldMapping = new String[] {
				"effectiveStartDate",
				"organizationCode",
				"sizeCode"
		};
		return fieldMapping;
	}

	@Override
	protected CellProcessor[] getProcessors(Object... args) {
		CellProcessor[] cellProcessor = new CellProcessor[] {
			new Optional(new Trim(new ParseDate("dd-MMM-yyyy"))), //startDate
			new Optional(new Unique(new StrMinMax(1, 60))), //orgCode
			new Optional(new Trim(new NotNull(new StrMinMax(1, 20))))  //size
		};		
		return cellProcessor;
	}

	@Override
	protected String getBatchUploadTableName() {
		return "WOS_BRANCH_SIZE_UPLOADS";
	}

	@Override
	protected BatchUpload getBatchUploadObject(Object... args) {
		return new BatchUpload();
	}

	@Override
	protected String getUploadElementTableName() {
		return "WOS_BRANCH_SIZE_ELEMENTS";
	}

	@Override
	protected String getUploadElementSequenceName() {
		return "WOS_BRANCH_SIZE_ELEMENTS_S";
	}

	@Override
	protected String getUploadErrorTableName() {
		return "WOS_BRANCH_SIZE_ERRORS";
	}

	@Override
	protected String getUploadErrorSequenceName() {
		return "WOS_BRANCH_SIZE_ERRORS_S";
	}

	@Override
	protected String getUploadStageTableName() {
		return "WOS_BRANCH_SIZE_STG";
	}

	@Override
	protected void validate(BranchSizeStageDTO stageObject, Object... args) throws Exception {
		branchSizeUploadValidator.validate(stageObject);
	}

	@Override
	protected void insertStageUpload(BranchSizeStageDTO stageObject) {
		
		BranchSizeUploadStage branchSizeUploadStage = modelMapper.map(stageObject, BranchSizeUploadStage.class);
		branchSizeUploadStage.setEffectiveEndDate(DateUtil.MAX_DATE);
		branchSizeUploadStage.setSizeCode(stageObject.getSizeCode().toUpperCase());
		branchSizeUploadStageMapper.insert(branchSizeUploadStage);
	}

	@Override
	protected void moveToOriginalTable(BranchSizeStageDTO stageObject) {
		
	}

	@Override
	protected void validateBeforeClose(BranchSizeStageDTO stageObject)
			throws Exception {
		BranchSizeDTO branchSize = new BranchSizeDTO();
		branchSize.setEffectiveStartDate(stageObject.getEffectiveStartDate());
		branchSize.setEffectiveEndDate(stageObject.getEffectiveEndDate());
		branchSize.setOrganizationId(stageObject.getOrganizationId());
		branchSize.setSizeCode(BranchSizeType.fromDesc(stageObject.getSizeCode()));
		branchSize.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		branchSize.setCreationDate(new Date());
		branchSize.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		branchSize.setLastUpdateDate(new Date());
		branchSize.setIsUpload(true);
		
		Date dateFromBeforeCurrentEdit = null;
		Date dateToBeforeCurrentEdit = null;
		List<Long> histories = branchSizeSetupServiceImpl.findHistoriesById(branchSize.getOrganizationId());
		if(histories.size() > 0) {
			Long lastHistoryId = histories.get(histories.size()-1);
			BranchSizeDTO lastBranchSize = branchSizeSetupServiceImpl.findByIdAndHistoryId(lastHistoryId, branchSize.getOrganizationId());
			dateFromBeforeCurrentEdit = lastBranchSize.getEffectiveStartDate();
			dateToBeforeCurrentEdit = lastBranchSize.getEffectiveEndDate();
			branchSize.setId(lastBranchSize.getId());
		}			
			branchSizeSetupServiceImpl.save(branchSize, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
	}

	@Override
	protected List<BranchSizeStageDTO> getStageObjectByUploadId(Long uploadId) {
		return branchSizeUploadFinder.getBranchSizeStage(uploadId);
	}

	@Override
	public void downloadFailedRecords(String batchNumber,
			UploadErrorStatus uploadErrorStatus) {
		List<UploadError> uploadErrors = getUploadError(batchNumber, uploadErrorStatus);
		SimpleDateFormat sdf = new SimpleDateFormat("ddmmYYYYhhMMS");
		ICsvMapWriter mapWriter = null;
		String fileName = File.separator + "writeWithCsvMapWriter"+ sdf.format(new Date()) +".csv";
		try {
			mapWriter = new CsvMapWriter(new FileWriter(GlobalVariable.getAppsRootFolder() + fileName), 
					CsvPreference.STANDARD_PREFERENCE);
			List<String> fieldMapping = new ArrayList<String>();
			fieldMapping.add("Effective Start Date");
			fieldMapping.add("Organization Code");
			fieldMapping.add("Branch Size (Code)");
			fieldMapping.add("Error Message");
			String[] header = fieldMapping.toArray(new String[] {});

			List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});
			
			mapWriter.writeHeader(header);
			for (UploadError error : uploadErrors) {
				Map<String, Object> map = new HashMap<String, Object>();
//				List<String> data = FileUtil.unwrapAndMark(error.getRawData());
				String[] rawData = error.getRawData().split(",");
//				map.put(header[0], error.getRowNumber());
				for (int i = 0; i < rawData.length; i++) {
					map.put(header[i], rawData[i]);					
				}
				map.put(header[header.length-1], error.getErrorMessage());
				mapWriter.write(map, header, processors);
			}
			File fileCsv = new File(GlobalVariable.getAppsRootFolder() + fileName);
			InputStream is = new FileInputStream(fileCsv);
			AMedia amedia = new AMedia("Branch Size Error.csv", "xls", "application/file", is);
			Filedownload.save(amedia);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(mapWriter != null) {
				try {
					mapWriter.close();
					File file = new File(GlobalVariable.getAppsRootFolder() + fileName);
					file.delete();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	protected List<String> getHeaders(Object... args) {
		List<String> headers = new ArrayList<String>();
		headers.add("Effective Start Date");
		headers.add("Organization Code");
		headers.add("Branch Size (Code)");
		
		return headers;
	}
}

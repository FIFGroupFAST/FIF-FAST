package co.id.fifgroup.workstructure.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
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
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.UploadDownloadFileValidator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.LobUploadStage;
import co.id.fifgroup.workstructure.domain.LobUploadStageExample;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LobSetupService;
import co.id.fifgroup.workstructure.service.LobUploadService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import com.google.common.base.Strings;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.constant.BatchStatus;
import co.id.fifgroup.core.constant.UploadErrorStatus;
import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.domain.UploadElement;
import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.core.dto.UploadElementDTO;
import co.id.fifgroup.core.service.AbstractUploadDownloadServiceCSV;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.workstructure.dao.LobUploadStageMapper;
import co.id.fifgroup.workstructure.dto.JobDTO;
import co.id.fifgroup.workstructure.dto.LobDTO;
import co.id.fifgroup.workstructure.dto.LobElementDTO;
import co.id.fifgroup.workstructure.dto.LobStageDTO;
import co.id.fifgroup.workstructure.dto.LobUploadsDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.finder.LobUploadFinder;
import co.id.fifgroup.workstructure.util.LobElementProcessor;
import co.id.fifgroup.workstructure.validation.LobUploadValidator;
import co.id.fifgroup.workstructure.validation.LobValidator;

@Transactional
@Service
public class LobUploadServiceImpl extends AbstractUploadDownloadServiceCSV<LobStageDTO> implements LobUploadService {

	private Logger logger = LoggerFactory.getLogger(LobUploadServiceImpl.class);
	
	@Autowired private LobUploadStageMapper lobUploadStageMapper;
	@Autowired private LobUploadFinder lobUploadFinder;
	@Autowired private LobUploadValidator lobUploadValidator;
	@Autowired private LobValidator lobValidator;
	@Autowired private LookupService lookupServiceImpl;
	@Autowired private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired private JobSetupService jobSetupServiceImpl;
	@Autowired private LobSetupService lobSetupServiceImpl;
	@Autowired private ModelMapper modelMapper;
	
	private final String productLookup = "WSU_PRODUCT";
	private final String headerStartDate = "Effective Start Date";
	private final String headerOrgCode = "Organization Code";
	private final String headerJobCode = "Job Code";
	private final String headerProduct = "Product (Code)";
	private final String headerPercentage = "Percentage";
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
		getJobSheet(workbook, boldfont, centerCellStyle);
		getProductSheet(workbook, boldfont, centerCellStyle);
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			workbook.write(out);
			AMedia amedia = new AMedia("LobTemplate.xls", "xls", "application/file", out.toByteArray());
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
		headerRow.createCell(1).setCellValue(new HSSFRichTextString(headerOrgCode));
		headerRow.createCell(2).setCellValue(new HSSFRichTextString(headerJobCode));
		headerRow.createCell(3).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(4).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(5).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(6).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(7).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(8).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(9).setCellValue(new HSSFRichTextString(headerProduct));
		headerRow.createCell(10).setCellValue(new HSSFRichTextString(headerProduct));
		
		
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
	
	private void getJobSheet(HSSFWorkbook workbook, HSSFFont boldfont, HSSFCellStyle centerCellStyle) {
		JobDTO example = new JobDTO();
		example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		example.setEffectiveDate(DateUtil.truncate(new Date()));
		List<JobDTO> jobs = jobSetupServiceImpl.findByExample(example);
		
		HSSFSheet sheet = workbook.createSheet("Job List");
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 10000);
		
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);
		
		int rowNum = 0;
		
		HSSFRow headerRow = sheet.createRow(rowNum++);
		headerRow.createCell(0).setCellValue(new HSSFRichTextString("Job Code"));
		headerRow.createCell(1).setCellValue(new HSSFRichTextString("Job Name"));
		headerRow.getCell(0).setCellStyle(cellStyle);
		headerRow.getCell(1).setCellStyle(cellStyle);
		
		for(JobDTO job : jobs) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(new HSSFRichTextString(job.getJobCode()));
			row.createCell(1).setCellValue(new HSSFRichTextString(job.getJobName()));
		}
	}
	
	private void getProductSheet(HSSFWorkbook workbook, HSSFFont boldfont, HSSFCellStyle centerCellStyle) {
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		try {
			keyValues = lookupServiceImpl.getLookups(productLookup, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		} catch (Exception e) {
			e.getMessage();
		}
		
		HSSFSheet sheet = workbook.createSheet("Product List");
		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 7000);
		
		int rowNum = 0;
		
		HSSFRow headerRow = sheet.createRow(rowNum);
		headerRow.createCell(0).setCellValue(new HSSFRichTextString("Product Code"));
		headerRow.createCell(1).setCellValue(new HSSFRichTextString("Product Name"));
		headerRow.getCell(0).setCellStyle(centerCellStyle);
		headerRow.getCell(1).setCellStyle(centerCellStyle);
		
		for(KeyValue keyValue : keyValues) {
			HSSFRow row = sheet.createRow(++rowNum);
			HSSFCell keyCell = row.createCell(0);
			HSSFCell valueCell = row.createCell(1);
			keyCell.setCellValue(new HSSFRichTextString((String) keyValue.getKey()));
			valueCell.setCellValue(new HSSFRichTextString((String) keyValue.getDescription()));
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
			fieldMapping.add(headerStartDate);
			fieldMapping.add(headerOrgCode);
			fieldMapping.add(headerJobCode);
			
			UploadError uploadError = uploadErrors.get(0);
			String[] headers = uploadError.getRawData().split(",");
			for(int i=3; i<headers.length; i++) {
				String[] products = headers[i].split(":");
				fieldMapping.add(products[0]);
			}
			fieldMapping.add("Error Message");
			String[] header = fieldMapping.toArray(new String[] {});

			List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			for(int i=3; i<headers.length; i++) {
				cellProcessors.add(new Optional());
			}
			cellProcessors.add(new Optional());
			CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});

			mapWriter.writeHeader(header);
			for (UploadError error : uploadErrors) {
				Map<String, Object> map = new HashMap<String, Object>();
//				List<String> data = FileUtil.unwrapAndMark(error.getRawData());
				String[] rawData = error.getRawData().split(",");
				map.put(header[0], error.getRowNumber());
				for (int i = 0; i < rawData.length; i++) {
					String data = rawData[i];
					if(i >= 3) {
						String[] products = data.split(":");
						data = products[1];
					}
					map.put(header[i+1], data);					
				}
				map.put(header[header.length-1], error.getErrorMessage());
				mapWriter.write(map, header, processors);
			}
			File fileCsv = new File(GlobalVariable.getAppsRootFolder() + fileName);
			InputStream is = new FileInputStream(fileCsv);
			AMedia amedia = new AMedia("Lob Error.csv", "xls", "application/file", is);
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
	public List<LobUploadStage> selectByExample(
			LobUploadStageExample lobStageExample) {
		return lobUploadStageMapper.selectByExample(lobStageExample);
	}

	@Override
	public List<LobUploadsDTO> getLobUploads(LobUploadsDTO example) {
		return lobUploadFinder.getLobUploads(example);
	}

	@Override
	protected String[] getFieldMapping(Object... args) {
		String[] headers = (String[]) args[0];
		List<String> fieldMapping = new ArrayList<>();
		fieldMapping.add("effectiveStartDate");
		fieldMapping.add("organizationCode");
		fieldMapping.add("jobCode");
		
		for (int i = 3; i < headers.length ; i++) {
			if (!Strings.isNullOrEmpty(headers[i]))
				fieldMapping.add("lobElements["+ (i - 3) +"]");
		}	
		
		return fieldMapping.toArray(new String[]{ });
	}
	
	private Class<?>[] getHintTypes (Object... args) {
		String[] headers = (String[]) args[0];
		List<Class<?>> hintTypes = new ArrayList<>();
		hintTypes.add(null);
		hintTypes.add(null);
		hintTypes.add(null);
		
		for (int i = 3; i < headers.length ; i++) {
			if (!Strings.isNullOrEmpty(headers[i]))
				hintTypes.add(LobElementDTO.class);
		}	
		
		return hintTypes.toArray(new Class<?>[] { });
	}

	@Override
	protected CellProcessor[] getProcessors(Object... args) {
		
		String[] headers = (String[]) args[0];
		
		List<CellProcessor> cellProcessor = new ArrayList<>();
		cellProcessor.add(new Optional(new Trim(new ParseDate("dd-MMM-yyyy"))));//startDate
		cellProcessor.add(new Optional(new Trim(new StrMinMax(1, 20))));  //orgCode
		cellProcessor.add(new Optional(new Trim(new StrMinMax(1, 20))));  //jobCode;
		for (int i = 3; i < headers.length ; i++) {
			if (!Strings.isNullOrEmpty(headers[i]))
				cellProcessor.add(new Optional(new LobElementProcessor(headers[i])));
		}		
				
		return cellProcessor.toArray(new CellProcessor[] { });
	}

	@Override
	protected String getBatchUploadTableName() {
		return "WOS_LOB_UPLOADS";
	}

	@Override
	protected BatchUpload getBatchUploadObject(Object... args) {
		return new BatchUpload();
	}

	@Override
	protected String getUploadElementTableName() {
		return "WOS_LOB_UPLOAD_ELEMENTS";
	}

	@Override
	protected String getUploadElementSequenceName() {
		return "WOS_LOB_UPLOAD_ELEMENTS_S";
	}

	@Override
	protected String getUploadErrorTableName() {
		return "WOS_LOB_ERRORS";
	}

	@Override
	protected String getUploadErrorSequenceName() {
		return "WOS_LOB_ERRORS_S";
	}

	@Override
	protected String getUploadStageTableName() {
		return "WOS_LOB_STG";
	}

	@Override
	protected void validate(LobStageDTO stageObject, Object... args) throws Exception {
		lobUploadValidator.validate(stageObject);
	}

	@Override
	protected void insertStageUpload(LobStageDTO stageObject) {
		LobUploadStage lobStage = new LobUploadStage();
		List<LobElementDTO> elements = stageObject.getLobElements();
		
		for (LobElementDTO element : elements) {
			if (element != null) {
				lobStage.setUploadId(stageObject.getUploadId());
				lobStage.setRowNumber(stageObject.getRowNumber());
				lobStage.setEffectiveStartDate(stageObject.getEffectiveStartDate());
				lobStage.setEffectiveEndDate(DateUtil.MAX_DATE);
				lobStage.setOrganizationId(stageObject.getOrganizationId());
				lobStage.setJobId(stageObject.getJobId());
				lobStage.setProductCode(element.getProductCode());
				lobStage.setPercentage(element.getPercentage());
				lobStage.setRawData(stageObject.getRawData());
				lobUploadStageMapper.insert(lobStage);
			}
		}
	}
	
	@Override
	protected void postUpload(UploadElement uploadElement, Object... args) {
		
		List<LobStageDTO> lobs = lobUploadFinder.findStageByUploadId(uploadElement.getUploadId());
		Set<LobStageDTO> setLobs = new HashSet<>(lobs);
		for(LobStageDTO lob : setLobs) {
			List<LobElementDTO> elements = new ArrayList<LobElementDTO>();
			for(LobStageDTO lob2 : lobs) {
				if(lob.getOrganizationId().equals(lob2.getOrganizationId()) && lob.getJobId().equals(lob2.getJobId())) {
					elements.addAll(lob2.getLobElements());
				}
			}
			try {
				lob.setLobElements(elements);
				LobDTO lobDto = new LobDTO();
				lobDto.setValidLobElements(true);
				lobDto.setEffectiveStartDate(lob.getEffectiveStartDate());
				lobDto.setEffectiveEndDate(lob.getEffectiveEndDate());
				lobDto.setOrganizationId(lob.getOrganizationId());
				lobDto.setJobId(lob.getJobId());
				lobDto.setLobElements(lob.getLobElements());
				lobValidator.validate(lobDto);				
			} catch (ValidationException e) {
				logger.error(e.getMessage());
				
				LobUploadStageExample example = new LobUploadStageExample();
				example.createCriteria().andUploadIdEqualTo(lob.getUploadId())
					.andOrganizationIdEqualTo(lob.getOrganizationId())
					.andJobIdEqualTo(lob.getJobId());
				lobUploadStageMapper.deleteByExample(example);
				
				OrganizationDTO org = organizationSetupServiceImpl.findById(lob.getOrganizationId());
				if(org != null) lob.setOrganizationCode(org.getCode());
				JobDTO job = jobSetupServiceImpl.findById(lob.getJobId());
				if(job != null) lob.setJobCode(job.getJobCode());
				
				UploadError uploadError = modelMapper.map(lob, UploadError.class);
				uploadError.setErrorMessage(e.getAllMessages());
				uploadError.setRawData(lob.toString());	
				uploadError.setIsClosed(false);
				uploadError.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				uploadError.setCreationDate(DateUtil.truncate(new Date()));
				uploadError.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				uploadError.setLastUpdateDate(DateUtil.truncate(new Date()));
				insertErrorUpload(getUploadErrorTableName(), getUploadErrorSequenceName(), uploadError);
			}
		}
	}

	@Override
	public Integer countByExample(LobUploadStageExample example) {
		return lobUploadStageMapper.countByExample(example);
	}

	@Override
	protected void moveToOriginalTable(LobStageDTO stageObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeClose(LobStageDTO stageObject)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeBatch(BatchUpload batchUpload) {
		List<LobStageDTO> allStage = lobUploadFinder.findStageByBatchNumber(batchUpload.getBatchNumber());
		Set<LobStageDTO> setLobs = new HashSet<>(allStage);
		UploadElement uploadElement = new UploadElement();
		uploadElement.setBatchNumber(batchUpload.getBatchNumber());
		List<UploadElement> uploadElements = uploadElementFinder.selectByExample(getUploadElementTableName(), uploadElement);
		LobDTO lobDto = new LobDTO();
		boolean hasError = false;
		for(LobStageDTO lob : setLobs) {
			try {
				lobDto = new LobDTO();
				lobDto.setBranchId(lob.getUploadId());
				lobDto.setOrganizationId(lob.getOrganizationId());
				lobDto.setJobId(lob.getJobId());
				lobDto.setEffectiveStartDate(lob.getEffectiveStartDate());
				lobDto.setEffectiveEndDate(lob.getEffectiveEndDate());
				lobDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				lobDto.setCreationDate(new Date());
				lobDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				lobDto.setLastUpdateDate(new Date());
				List<LobElementDTO> elements = new ArrayList<LobElementDTO>();
				for(LobStageDTO lob2 : allStage) {
					if(lob.getOrganizationId().equals(lob2.getOrganizationId()) && lob.getJobId().equals(lob2.getJobId())) {
						elements.addAll(lob2.getLobElements());						
					}
				}
				lobDto.setLobElements(elements);
				
				if(lobDto.getEffectiveStartDate().compareTo(new Date()) <= 0) {
					Map<String, String> errors = new HashMap<>();
					errors.put("EFFECTIVE_START_DATE", Labels.getLabel("common.mustBeGreaterThan", new Object[] {Labels.getLabel("wos.effectiveStartDate"), "sysdate"}));
					throw new ValidationException(errors);
				} else {
					Date dateFromBeforeCurrentEdit = null;
					Date dateToBeforeCurrentEdit = null;
					List<Long> histories = lobSetupServiceImpl.findHistoriesById(lob.getJobId(), lob.getOrganizationId());
					if(histories.size() > 0) {
						Long lastHistoryId = histories.get(histories.size()-1);
						LobDTO lastLob = lobSetupServiceImpl.findByIdAndHistoryId(lastHistoryId, lob.getJobId(), lob.getOrganizationId());
						dateFromBeforeCurrentEdit = lastLob.getEffectiveStartDate();
						dateToBeforeCurrentEdit = lastLob.getEffectiveEndDate();
					}
					lobDto.setBranchId(null);
					lobDto.setValidLobElements(true);
					lobSetupServiceImpl.save(lobDto, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
				}
			} catch (ValidationException vex) {
				hasError = true;
				logger.error(vex.getAllMessages());
				setErrorDomain(lob.getUploadId(), lob.getRawData(), vex.getMessage(), lob.getRowNumber(), UploadErrorStatus.CLOSE_BATCH);
			} catch (Exception e) {
				hasError = true;
				logger.error(e.getMessage(), e);
				setErrorDomain(lob.getUploadId(), lob.getRawData(), e.getMessage(), lob.getRowNumber(), UploadErrorStatus.CLOSE_BATCH);
			}
		}
		batchUpload.setStatus(BatchStatus.CLOSED);
		batchUpload.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		batchUpload.setLastUpdateDate(new Date());
		updateBatchUpload(getBatchUploadTableName(), batchUpload);
		for(UploadElement uploadElmt : uploadElements) {
			updateStatusToClosedUploadErrorByUploadId(getUploadErrorTableName(), uploadElmt.getUploadId());
			updateStatusToClosedUploadStageByUploadId(getUploadStageTableName(), uploadElmt.getUploadId());
		}
		doAfterCloseBatch(batchUpload);
		
		if(hasError)
			Messagebox.show("Transaction has been closed with error. Please download the close batch failed rows. Batch number : "+batchUpload.getBatchNumber(), "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
	}

	@Override
	protected List<LobStageDTO> getStageObjectByUploadId(Long uploadId) {
		// TODO Auto-generated method stub
		return null;
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
//			fieldMapping.add("Row Number");
			fieldMapping.add(headerStartDate);
			fieldMapping.add(headerOrgCode);
			fieldMapping.add(headerJobCode);
			
			UploadError uploadError = uploadErrors.get(0);
			String[] headers = uploadError.getRawData().split(",");
			for(int i=3; i<headers.length; i++) {
				String[] products = headers[i].split(":");
				fieldMapping.add(products[0]);
			}
			fieldMapping.add("Error Message");
			String[] header = fieldMapping.toArray(new String[] {});

			List<CellProcessor> cellProcessors = new ArrayList<CellProcessor>();
//			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			cellProcessors.add(new Optional());
			for(int i=3; i<headers.length; i++) {
				cellProcessors.add(new Optional());
			}
			cellProcessors.add(new Optional());
			CellProcessor[] processors = cellProcessors.toArray(new CellProcessor[] {});

			mapWriter.writeHeader(header);
			for (UploadError error : uploadErrors) {
				Map<String, Object> map = new HashMap<String, Object>();
//				List<String> data = FileUtil.unwrapAndMark(error.getRawData());
				String[] rawData = error.getRawData().split(",");
//				map.put(header[0], error.getRowNumber());
				for (int i = 0; i < rawData.length; i++) {
					String data = rawData[i];
					if(i >= 3) {
						String[] products = data.split(":");
						data = products[1];
					}
					map.put(header[i], data);					
				}
				map.put(header[header.length-1], error.getErrorMessage());
				mapWriter.write(map, header, processors);
			}
			File fileCsv = new File(GlobalVariable.getAppsRootFolder() + fileName);
			InputStream is = new FileInputStream(fileCsv);
			AMedia amedia = new AMedia("Lob Error.csv", "xls", "application/file", is);
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
	public UploadElementDTO process(InputStream inputStream, boolean isHeader,
			Object... args) throws Exception {
		beanReader = new CsvDozerBeanReader(new BufferedReader(new InputStreamReader(inputStream)), CsvPreference.STANDARD_PREFERENCE);
		String[] headers = beanReader.getHeader(true);
		
		// validation header template
		if(isHeader) {
			List<String> headersTemplate = getHeaders(args);
			if(headersTemplate != null && !headersTemplate.isEmpty()) {
				for(int i=0; i<headersTemplate.size(); i++) {
					if(!headers[i].equals(headersTemplate.get(i))) {
						Map<String, String> errors = new HashMap<String, String>();
						errors.put(UploadDownloadFileValidator.HEADER_TEMPLATE, "Template is invalid.");
						throw new ValidationException(errors);
					}
				}
			}
		}
		
		LobStageDTO stageObject =null;
		
		String batchNumber = insertBatchUpload(args);
		Long uploadId = insertUploadElement(batchNumber);
		
		
		Class<?>[] hintTypes = getHintTypes(headers, null);
		beanReader.configureBeanMapping(LobStageDTO.class, getFieldMapping(headers, null), hintTypes);
		
		boolean isRead = true;
		while(isRead) {
			try {
				while((stageObject = beanReader.read(LobStageDTO.class, getProcessors(headers, null))) != null) {
					stageObject.setUploadId(uploadId);
					logger.info(stageObject.toString());
					StringBuilder rawData = new StringBuilder();
					rawData.append(DateUtil.format(stageObject.getEffectiveStartDate(), "dd-MMM-yyyy")).append(",")
						.append(stageObject.getOrganizationCode()).append(",")
						.append(stageObject.getJobCode()).append(",");
					
					List<LobElementDTO> elements = stageObject.getLobElements();
					for (int i = 0 ; i < elements.size() ; i++) {
						if(elements.get(i) != null) {
							rawData.append(elements.get(i).getProductCode()).append(":")
							.append(elements.get(i).getPercentage());
							if (i < elements.size() - 1)
								rawData.append(",");
						}
					}
					try {
						stageObject.setRowNumber(beanReader.getRowNumber());
						
						
						stageObject.setRawData(rawData.toString());
						stageObject.setIsClosed(false);
						stageObject.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						stageObject.setCreationDate(new Date());
						stageObject.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						stageObject.setLastUpdateDate(new Date());
						validate(stageObject, args);
						insertStageUpload(stageObject);
					} catch (ValidationException e) {
						logger.error(e.getMessage(), e);
						setErrorDomain(uploadId, rawData.toString(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						setErrorDomain(uploadId, rawData.toString(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
					}
				}
				isRead = false;
			} catch (SuperCsvException e) {
				logger.error(e.getMessage(), e);
				setErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
			}
		}
		insertSuccessRowAndFailedRow(batchNumber, uploadId);
		UploadElement uploadElement = getUploadElementByUploadId(uploadId);
		postUpload(uploadElement, args);
		return getUploadElementDto(uploadId);
	}
	
	@Override
	protected void insertSuccessRowAndFailedRow(String batchNumber,
			Long uploadId) {
		UploadElement uploadElement = getUploadElementByUploadId(uploadId);
		uploadElement.setSuccessRows(lobUploadFinder.countSuccessRowByUploadId(uploadId));
		UploadError uploadError = new UploadError();
		uploadError.setUploadId(uploadId);
		uploadElement.setFailedRows(uploadErrorFinder.countByExample(getUploadErrorTableName(), uploadError));
		updateUploadElement(getUploadElementTableName(), uploadElement);
		BatchUpload batchUpload = getBatchUploadByBatchNumber(getBatchUploadTableName(), batchNumber);
		batchUpload.setLastUpdateDate(new Date());
		batchUpload.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		updateBatchUpload(getBatchUploadTableName(), batchUpload);
	}

	@Override
	protected List<String> getHeaders(Object... args) {
		List<String> headers = new ArrayList<String>();
		headers.add(headerStartDate);
		headers.add(headerOrgCode);
		headers.add(headerJobCode);
			
		return headers;
	}
}

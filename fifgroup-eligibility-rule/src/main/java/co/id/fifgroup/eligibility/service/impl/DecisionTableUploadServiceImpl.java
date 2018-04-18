package co.id.fifgroup.eligibility.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.core.validation.UploadDownloadFileValidator;
import co.id.fifgroup.core.validation.ValidationException;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import co.id.fifgroup.core.constant.BatchStatus;
import co.id.fifgroup.core.constant.UploadErrorStatus;
import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.domain.UploadElement;
import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.core.dto.UploadElementDTO;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.AbstractUploadDownloadServiceCSV;
import co.id.fifgroup.eligibility.dao.DecisionTableRowMapper;
import co.id.fifgroup.eligibility.dao.DecisionTableStageMapper;
import co.id.fifgroup.eligibility.domain.DecisionTableRow;
import co.id.fifgroup.eligibility.domain.DecisionTableRowExample;
import co.id.fifgroup.eligibility.domain.DecisionTableStage;
import co.id.fifgroup.eligibility.dto.DecisionTableColumnDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableStageDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableUploadDTO;
import co.id.fifgroup.eligibility.finder.DecisionTableUploadFinder;
import co.id.fifgroup.eligibility.service.BatchUploadFilter;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.service.DecisionTableService;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;
import co.id.fifgroup.eligibility.upload.Argument;
import co.id.fifgroup.eligibility.upload.TemplateGenerator;
import co.id.fifgroup.eligibility.upload.Argument.Type;

@Service
public class DecisionTableUploadServiceImpl extends AbstractUploadDownloadServiceCSV<DecisionTableStageDTO>
	implements DecisionTableUploadService{
	
	private Logger logger = LoggerFactory.getLogger(DecisionTableUploadServiceImpl.class);
	
	protected static String BATCH_UPLOAD_TABLE = "_BATCH_UPLOADS";
	protected static String UPLOAD_STAGE_TABLE = "_UPLOAD_STG";
	protected static String UPLOAD_ELEMENT_TABLE = "_UPLOAD_ELEMENTS";
	protected static String UPLOAD_ELEMENT_SEQUENCE = "_UPLOAD_ELEMENTS_S";
	protected static String UPLOAD_ERROR_TABLE = "_UPLOAD_ERRORS";
	protected static String UPLOAD_ERROR_SEQUENCE = "_UPLOAD_ERRORS_S";
	
	protected String moduleName = "ELR";
	
	@Autowired
	DecisionTableModelSetupService dtModelService;
	@Autowired
	DecisionTableService dtService;
	@Autowired
	TemplateGenerator templateGenerator;
	@Autowired
	DecisionTableStageMapper stageMapper;
	@Autowired
	DecisionTableUploadFinder finder;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	DecisionTableRowMapper dtRowMapper; 
	
	private BatchUploadFilter batchUploadFilter;
		
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public void downloadTemplate(Object... args) throws Exception {
		Long id = 0L;
		for (Object argument : args) {
			Argument arg = (Argument) argument;
			switch(arg.getType()) {
			case ID:
				id =  arg.getActualValue();
				break;
			default:
				break;
			}
		}
		
		/**
		 * [16022316000474] HCMS ph 2- CAM - performance upload career path
		 * tugas yg berat dengan hasil yg sama, jgn di lakukan Berkali2
		 * hidup ini sudah berat, jgn di buat tambah berat lagi.
		 * By Jatis (HS) 24/03/2016
		 */
//		if(getTemplate(moduleName, id)==null) {
//			return;
//		}
//		AMedia amedia = new AMedia("Decision Table Template.xls", "xls", "application/file",getTemplate(moduleName, id));
		byte[] template = getTemplate(moduleName, id);
		if(template==null) {
			return;
		}
		AMedia amedia = new AMedia("Decision Table Template.xls", "xls", "application/file",template);
		//END 16022316000474
		Filedownload.save(amedia);
		
	}
	
	/**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * implement closeBatch & process method
	 * tuning tidak bisa di lakukan di parent class, karena ada beberapa perlakukan khusus
	 * untuk memudahkan, lakukan override
	 * By Jatis (HS) 24/03/2016
	 */
	@Override
	public void closeBatch(BatchUpload batchUpload) {
		boolean hasError = false;
		List<DecisionTableRow> validData = new ArrayList<DecisionTableRow>();
		List<UploadError> errorDomainData = new ArrayList<UploadError>();
		Date trxDate = new Date();
		SecurityProfile secProfile = securityServiceImpl.getSecurityProfile();
		
		UploadElement uploadElement = new UploadElement();
		uploadElement.setBatchNumber(batchUpload.getBatchNumber());
		List<UploadElement> uploadElements = uploadElementFinder.selectByExample(getUploadElementTableName(), uploadElement);
		doBeforeCloseBatch(batchUpload);
		for(UploadElement uploadElmt : uploadElements) {
			List<DecisionTableStageDTO> stageObjects = getStageObjectByUploadId(uploadElmt.getUploadId());
			for(DecisionTableStageDTO stageObject : stageObjects) {
				try {
					validateBeforeClose(stageObject);
					//moveToOriginalTable(stageObject);
					validData.add(moveToOriginalTable(stageObject, secProfile, trxDate));
				} catch (ValidationException e) {
					logger.error(e.getMessage());
					//setErrorDomain(stageObject.getUploadId(), stageObject.getRawData(), e.getMessage(), stageObject.getRowNumber(), UploadErrorStatus.CLOSE_BATCH);
					errorDomainData.add(getErrorDomain(stageObject.getUploadId(), stageObject.getRawData(), e.getMessage(), 
							stageObject.getRowNumber(), UploadErrorStatus.CLOSE_BATCH, secProfile, trxDate));
					hasError = true;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					//setErrorDomain(stageObject.getUploadId(), stageObject.getRawData(), e.getMessage(), stageObject.getRowNumber(), UploadErrorStatus.CLOSE_BATCH);
					errorDomainData.add(getErrorDomain(stageObject.getUploadId(), stageObject.getRawData(), e.getMessage(), 
							stageObject.getRowNumber(), UploadErrorStatus.CLOSE_BATCH, secProfile, trxDate));
				}
			}
		}
		
		
		List<List<DecisionTableRow>> partitionOriginal = Lists.partition(validData, 500);
		for (List<DecisionTableRow> list : partitionOriginal) {
			dtRowMapper.insertBulk(moduleName, list);
		}
		
		List<List<UploadError>> partitionErrors = Lists.partition(errorDomainData, 500);
		for(List<UploadError> list : partitionErrors){
			insertErrorUploadBulk(getUploadErrorTableName(), getUploadErrorSequenceName(), list);
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
	
	public UploadElementDTO process(InputStream inputStream, boolean isHeader, Object... args) throws Exception {
		
		beanReader = new CsvDozerBeanReader(new BufferedReader(new InputStreamReader(inputStream)), CsvPreference.STANDARD_PREFERENCE);
		
		BatchUpload batchUpload = (BatchUpload) args[0];
		DecisionTableDTO dt = null;
		if (null != batchUpload && null != batchUpload.getFormatReferenceId()) {
			dt = dtService.findDecisionTableHeaderById(moduleName, batchUpload.getFormatReferenceId());			
			if (null != dt) {
				dt.setModel(dtModelService.findByIdAndVersionNumber(dt.getModel().getId(), dt.getModel().getVersionNumber()));
			}
		}
		
		CellProcessor[] processors = getProcessors(dt);
		Date trxDate = new Date();
		SecurityProfile secProfile = securityServiceImpl.getSecurityProfile();
		
		List<DecisionTableStageDTO> validData = new ArrayList<DecisionTableStageDTO>();
		List<UploadError> errorDomainData = new ArrayList<UploadError>();
		
		initBeforeProcessData(args);
		
		if(isHeader) {
			String[] headers = beanReader.getHeader(true);
			List<String> headersTemplate = getHeaders(args);
			if(headersTemplate != null && !headersTemplate.isEmpty()) {
				for(int i=0; i<headers.length; i++) {
					if(!headers[i].equals(headersTemplate.get(i))) {
						Map<String, String> errors = new HashMap<String, String>();
						errors.put(UploadDownloadFileValidator.HEADER_TEMPLATE, "Template is invalid.");
						throw new ValidationException(errors);
					}
				}
			}
			if(headers.length != processors.length) {
				Map<String, String> errors = new HashMap<String, String>();
				errors.put(UploadDownloadFileValidator.NUMBER_OF_COLUMN, "Number Of Column Not Valid.");
				throw new ValidationException(errors);
			}
		}
		
		String batchNumber = insertBatchUpload(args);
		Long uploadId = insertUploadElement(batchNumber);
		beanReader.configureBeanMapping(DecisionTableStageDTO.class, getFieldMapping(dt));
		
		DecisionTableStageDTO stageObject = null;
		
		boolean isRead = true;
		while(isRead) {
			try {
				while((stageObject = beanReader.read(DecisionTableStageDTO.class, processors)) != null) {
					stageObject.setUploadId(uploadId);
					try {
						stageObject.setRowNumber(beanReader.getRowNumber());
						stageObject.setRawData(beanReader.getUntokenizedRow());
						stageObject.setIsClosed(false);
						stageObject.setCreatedBy(secProfile.getUserId());
						stageObject.setCreationDate(trxDate);
						stageObject.setLastUpdatedBy(secProfile.getUserId());
						stageObject.setLastUpdateDate(trxDate);
						validate(stageObject, args);
						
						//insertStageUpload(stageObject);
						validData.add(stageObject);
					} catch (ValidationException e) {
						logger.error(e.getMessage());
						//setErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
						errorDomainData.add(getErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), 
								beanReader.getRowNumber(), UploadErrorStatus.PROCESS, secProfile, trxDate));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
//						setErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
						errorDomainData.add(getErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), 
								beanReader.getRowNumber(), UploadErrorStatus.PROCESS, secProfile, trxDate));
					}
				}
				isRead = false;
			} catch (SuperCsvException e) {
				logger.error(e.getMessage(), e);
				setErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
			}
		}
		
		//insert bulk
		List<List<DecisionTableStageDTO>> partitionStage = Lists.partition(validData, 500);
		for (List<DecisionTableStageDTO> list : partitionStage) {
			insertStageUploadBulk(list);
		}
		
		List<List<UploadError>> partitionErrors = Lists.partition(errorDomainData, 500);
		for(List<UploadError> list : partitionErrors){
			insertErrorUploadBulk(getUploadErrorTableName(), getUploadErrorSequenceName(), list);
		}
		//insert bulk
		
		insertSuccessRowAndFailedRow(batchNumber, uploadId);
		UploadElement uploadElement = getUploadElementByUploadId(uploadId);
		postUpload(uploadElement, args);
		return getUploadElementDto(uploadId);
	}
	//END 16022316000474

	@Override
	public void downloadFailedRecords(Long uploadId) {
		List<UploadError> uploadErrors = getFailedRows(uploadId);
		UploadElementDTO uploadElement = getUploadElementDto(uploadId);
		Long decisionTableId = uploadElement.getBatchUpload().getFormatReferenceId();
		List<String> headers = getHeaders(new Argument(Type.ID, decisionTableId.toString()));
		AMedia amedia = new AMedia("Failed Records.csv", "csv", "application/file",templateGenerator.generateFailedRecords(uploadErrors, headers));
		Filedownload.save(amedia);
		
	}

	@Override
	public BatchUpload getOpenStatusObject(Object... args) {
		DecisionTableUploadDTO decisionTableUploadDTO = (DecisionTableUploadDTO) args[0];
		BatchUpload batchUpload = new BatchUpload();
		batchUpload.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		batchUpload.setFormatReferenceId(decisionTableUploadDTO.getFormatReferenceId());
		batchUpload.setStatus(BatchStatus.OPEN);
		List<BatchUpload> batchUploads = findBatchUploadByExample(batchUpload);
		if (batchUploads.size() == 1) {
			return batchUploads.get(0);
		}
		return null;
	}

	@Override
	protected String[] getFieldMapping(Object... args) {
		/**
		 * [16022316000474] HCMS ph 2- CAM - performance upload career path
		 * Untuk data yg banyak, query findDecisionTableById akan butuh waktu yg cukup lama
		 * dari pada query berkali2, jadikan DecisionTableDTO sebagai parameter
		 * By Jatis (HS) 24/03/2016
		 */
		//BatchUpload batchUpload = (BatchUpload) args[0];
		DecisionTableDTO dt = (DecisionTableDTO) args[0];
		List<String> fieldMapping = new ArrayList<>();
		//if (null != batchUpload && null != batchUpload.getFormatReferenceId()) {
			//DecisionTableDTO dt = dtService.findDecisionTableById(moduleName, batchUpload.getFormatReferenceId());			
			if (null != dt) {
				for (int i=0 ; i < dt.getModel().getConditions().size(); i++) {
					fieldMapping.add("listConditions[" + i + "]");
				}
				for (int i=0 ; i < dt.getModel().getResults().size(); i++) {
					fieldMapping.add("listResults[" + i + "]");
				}
			}
		//}
		//END 16022316000474
		return fieldMapping.toArray(new String[]{});
	}

	@Override
	protected CellProcessor[] getProcessors( Object... args) {
		/**
		 * [16022316000474] HCMS ph 2- CAM - performance upload career path
		 * Untuk data yg banyak, query findDecisionTableById akan butuh waktu yg cukup lama
		 * dari pada query berkali2, jadikan DecisionTableDTO sebagai parameter
		 * By Jatis (HS) 24/03/2016
		 */
		//BatchUpload batchUpload = (BatchUpload) args[0];
		DecisionTableDTO dt = (DecisionTableDTO) args[0];
		List<CellProcessor> processors = new ArrayList<>();
		//if (null != batchUpload && null != batchUpload.getFormatReferenceId()) {
			//DecisionTableDTO dt = dtService.findDecisionTableById(moduleName, batchUpload.getFormatReferenceId());
			if (null != dt) {
				for (DecisionTableColumnDTO condition : dt.getModel().getConditions()) {
					switch(condition.getField().getFieldType()) {
					case DATE:
						processors.add(new Optional(new ParseDate("dd-MMM-yyyy")));
						break;
					case LOOKUP:
						processors.add(new Optional());
						break;
					case NUMBER:
						processors.add(new Optional(new ParseBigDecimal()));
						break;
					case TEXT:
						processors.add(new Optional());
						break;
					default:
						break;
					}
				}
				for (DecisionTableColumnDTO result : dt.getModel().getResults()) {
					switch(result.getField().getFieldType()) {
					case DATE:
						processors.add(new Optional(new ParseDate("dd-MMM-yyyy")));
						break;
					case LOOKUP:
						processors.add(new Optional());
						break;
					case NUMBER:
						processors.add(new Optional(new ParseBigDecimal()));
						break;
					case TEXT:
						processors.add(new Optional());
						break;
					default:
						break;
					}
				}
			}
		//}
		//END 16022316000474
		return processors.toArray(new CellProcessor[]{ });
	}

	@Override
	protected String getBatchUploadTableName() {
		return moduleName +  BATCH_UPLOAD_TABLE;
	}

	@Override
	protected BatchUpload getBatchUploadObject(Object... args) {
		DecisionTableUploadDTO decisionTable = (DecisionTableUploadDTO) args[0];
		BatchUpload batchUpload = new BatchUpload();
		batchUpload.setFormatReferenceId(decisionTable.getFormatReferenceId());
		return batchUpload;
	}

	@Override
	protected String getUploadElementTableName() {
		return moduleName + UPLOAD_ELEMENT_TABLE;
	}

	@Override
	protected String getUploadElementSequenceName() {
		return moduleName + UPLOAD_ELEMENT_SEQUENCE;
	}

	@Override
	protected String getUploadErrorTableName() {
		return moduleName + UPLOAD_ERROR_TABLE;
	}

	@Override
	protected String getUploadErrorSequenceName() {
		return moduleName + UPLOAD_ERROR_SEQUENCE;
	}

	@Override
	protected String getUploadStageTableName() {
		return moduleName + UPLOAD_STAGE_TABLE;
	}

	@Override
	public byte[] getTemplate(String module, Long decisionTableId) {
		DecisionTableDTO decisionTable = dtService.findDecisionTableById(module, decisionTableId);
		if (null != decisionTable) {
			DecisionTableModelDTO model = dtModelService.findByIdAndVersionNumber(decisionTable.getModel().getId(), decisionTable.getModel().getVersionNumber());
			return templateGenerator.generate(moduleName, model, decisionTableId,decisionTable);
		}
		return null;
	}

	@Override
	protected void validate(DecisionTableStageDTO stageObject, Object... args) throws Exception {
		DecisionTableUploadDTO decisionTable = (DecisionTableUploadDTO) args[0];
		stageObject.setDecisionTableId(decisionTable.getFormatReferenceId());
		
	}
	
	/**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * By Jatis (HS) 24/03/2016
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)	
	private void insertStageUploadBulk(List<DecisionTableStageDTO> data){
		List<DecisionTableStage> records = new ArrayList<DecisionTableStage>();
		
		for(DecisionTableStageDTO stageObject : data){
			DecisionTableStage record =modelMapper.map(stageObject, DecisionTableStage.class);
			record.setDecTableId(stageObject.getDecisionTableId());
			
			List<String> conditions = stageObject.getListConditions();
			if (null != conditions && conditions.size() > 0)
				record.setConditions(Joiner.on("|").useForNull("").join(conditions));
			
			List<String> results = stageObject.getListResults();
			if (null != results && results.size() > 0)
				record.setResults(Joiner.on("|").useForNull("").join(results));
				
			records.add(record);
			
		}
		
		stageMapper.insertBulk(moduleName,records);
	}

	
	public DecisionTableRow moveToOriginalTable(DecisionTableStageDTO stageObject, 
			SecurityProfile secProfile, Date trxDate){
	
		DecisionTableRow row = new DecisionTableRow();
		row.setDecisionTableId(stageObject.getDecisionTableId());
		row.setConditions(stageObject.getConditions());
		row.setResults(stageObject.getResults());
		row.setCreatedBy(secProfile.getUserId());
		row.setCreationDate(trxDate);
		row.setLastUpdatedBy(secProfile.getUserId());
		row.setLastUpdateDate(trxDate);
		
		return row;
	}
	//16022316000474

	@Override
	protected void insertStageUpload(DecisionTableStageDTO stageObject) {
		DecisionTableStage record = modelMapper.map(stageObject, DecisionTableStage.class);
		record.setDecTableId(stageObject.getDecisionTableId());
		
		List<String> conditions = stageObject.getListConditions();
		if (null != conditions && conditions.size() > 0)
			record.setConditions(Joiner.on("|").useForNull("").join(conditions));
		
		List<String> results = stageObject.getListResults();
		if (null != results && results.size() > 0)
			record.setResults(Joiner.on("|").useForNull("").join(results));
		
		stageMapper.insert(moduleName, record);
	}

	@Override
	protected void moveToOriginalTable(DecisionTableStageDTO stageObject) {
		Long userId = securityServiceImpl.getSecurityProfile().getUserId();
		Date today = new Date();
		
		DecisionTableRow row = new DecisionTableRow();
		row.setDecisionTableId(stageObject.getDecisionTableId());
		row.setConditions(stageObject.getConditions());
		row.setResults(stageObject.getResults());
		row.setCreatedBy(userId);
		row.setCreationDate(today);
		row.setLastUpdatedBy(userId);
		row.setLastUpdateDate(today);
		
		dtRowMapper.insert(moduleName, row);
		
	}

	@Override
	protected void validateBeforeClose(DecisionTableStageDTO stageObject)
			throws Exception {
		
	}

	@Override
	protected List<DecisionTableStageDTO> getStageObjectByUploadId(Long uploadId) {
		return finder.findByUploadId(moduleName, uploadId);
	}

	@Override
	public void downloadFailedRecords(String batchNumber,
			UploadErrorStatus uploadErrorStatus) {
		List<UploadError> uploadErrors = getUploadError(batchNumber, uploadErrorStatus);
		BatchUpload batchUpload = getBatchUploadByBatchNumber(getBatchUploadTableName(), batchNumber);
		Long decisionTableId = batchUpload.getFormatReferenceId();
		List<String> headers = getHeaders(new Argument(Type.ID, decisionTableId.toString()));
		AMedia amedia = new AMedia("Failed Records.xls", "csv", "application/file",templateGenerator.generateFailedRecords(uploadErrors, headers));
		Filedownload.save(amedia);
	}
	
	@Override
	protected void doBeforeCloseBatch(BatchUpload batchUpload) {
		DecisionTableRowExample example = new DecisionTableRowExample();
		example.createCriteria()
			.andDecisionTableIdEqualTo(batchUpload.getFormatReferenceId());
		
		dtRowMapper.deleteByExample(moduleName, example);
	}
	
	@Override
	protected void doAfterCloseBatch(BatchUpload batchUpload) {		
		DecisionTableDTO dt = dtService.findDecisionTableById(moduleName, batchUpload.getFormatReferenceId());
		dtService.save(dt);
	}

	@Override
	public List<BatchUpload> findBatchUploadByExample(BatchUpload batchUpload) {
		if (batchUploadFilter != null)
			return batchUploadFilter.findBatchUploadByExample(moduleName, getBatchUploadTableName(), getUploadElementTableName(), batchUpload);
		return super.findBatchUploadByExample(batchUpload);
	}
	
	public void setBatchUploadFilter(BatchUploadFilter batchUploadFilter) {
		this.batchUploadFilter = batchUploadFilter;
	}

	@Override
	public List<BatchUpload> findByExample(BatchUploadFilter filter, BatchUpload batchUpload) {
		return filter.findBatchUploadByExample(moduleName, getBatchUploadTableName(), getUploadElementTableName(), batchUpload);
	}

	@Override
	protected List<String> getHeaders(Object... args) {
		Long id = 0L;
		for (Object argument : args) {
			if(argument instanceof Argument) {
				Argument arg = (Argument) argument;
				switch(arg.getType()) {
				case ID:
					id =  arg.getActualValue();
					break;
				default:
					break;
				}
			}
		}
		
		List<String> headers = new ArrayList<String>();
		DecisionTableDTO decisionTable = dtService.findDecisionTableHeaderById(moduleName, id);
		if (null != decisionTable) {
			DecisionTableModelDTO model = dtModelService.findByIdAndVersionNumber(decisionTable.getModel().getId(), decisionTable.getModel().getVersionNumber());
			for (int i = 0; i < model.getConditions().size(); i++) {
				DecisionTableColumnDTO column = model.getConditions().get(i);
				headers.add(column.getName());
			}
			for (int i = 0; i < model.getResults().size(); i++) {
				DecisionTableColumnDTO column = model.getResults().get(i);
				headers.add(column.getName());
			}
		}
		return headers;
	}
	
}

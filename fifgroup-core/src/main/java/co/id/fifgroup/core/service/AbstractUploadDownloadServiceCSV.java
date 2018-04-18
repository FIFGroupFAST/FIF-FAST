package co.id.fifgroup.core.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.Media;

import co.id.fifgroup.core.validation.UploadDownloadFileValidator;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.core.constant.UploadErrorStatus;
import co.id.fifgroup.core.domain.UploadElement;
import co.id.fifgroup.core.domain.UploadStage;
import co.id.fifgroup.core.dto.UploadElementDTO;

//@Transactional
public abstract class AbstractUploadDownloadServiceCSV<S extends UploadStage> extends AbstractUploadDownloadService<S> {
	
	private Logger logger = LoggerFactory.getLogger(AbstractUploadDownloadServiceCSV.class);
	
	@SuppressWarnings("unchecked")
	private Class<S> stagePersistentClass = (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	protected ICsvDozerBeanReader beanReader = null;
	
	protected abstract String[] getFieldMapping(Object... args);
	protected abstract CellProcessor[] getProcessors(Object... args);
	protected abstract List<String> getHeaders(Object... args);
	
	@Override
	public UploadElementDTO process(Media media, boolean isHeader, Object... args) throws Exception {
		UploadDownloadFileValidator uploadDownloadFileValidator = new UploadDownloadFileValidator();
		uploadDownloadFileValidator.setExtension("csv");
		uploadDownloadFileValidator.validate(media);
		
		return process(media.getStreamData(), isHeader, args);
	}
	
	
	public UploadElementDTO process(InputStream inputStream, boolean isHeader, Object... args) throws Exception {
		
		beanReader = new CsvDozerBeanReader(new BufferedReader(new InputStreamReader(inputStream)), CsvPreference.STANDARD_PREFERENCE);

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
			if(headers.length != getProcessors(args).length) {
				Map<String, String> errors = new HashMap<String, String>();
				errors.put(UploadDownloadFileValidator.NUMBER_OF_COLUMN, "Number Of Column Not Valid.");
				throw new ValidationException(errors);
			}
		}
		
		String batchNumber = insertBatchUpload(args);
		Long uploadId = insertUploadElement(batchNumber);
		beanReader.configureBeanMapping(stagePersistentClass, getFieldMapping(args));
		
		S stageObject = null;
		
		/**
		 * [16022316000474] HCMS ph 2- CAM - performance upload career path
		 * tugas yg berat dengan hasil yg sama, jgn di lakukan perkali2
		 * hidup ini sudah berat, jgn di buat tambah berat lagi.
		 * By Jatis (HS)
		 */
		CellProcessor[] processors = getProcessors(args);
		// END 16022316000474
		
		boolean isRead = true;
		while(isRead) {
			try {
				/**
				 * [16022316000474] HCMS ph 2- CAM - performance upload career path
				 * tugas yg berat dengan hasil yg sama, jgn di lakukan Berkali2
				 * hidup ini sudah berat, jgn di buat tambah berat lagi.
				 * By Jatis (HS) 24/03/2016
				 */
				while((stageObject = beanReader.read(stagePersistentClass, processors)) != null) {
				// END 16022316000474
					stageObject.setUploadId(uploadId);
					try {
						stageObject.setRowNumber(beanReader.getRowNumber());
						stageObject.setRawData(beanReader.getUntokenizedRow());
						stageObject.setIsClosed(false);
						stageObject.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						stageObject.setCreationDate(new Date());
						stageObject.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
						stageObject.setLastUpdateDate(new Date());
						validate(stageObject, args);
						insertStageUpload(stageObject);
					} catch (ValidationException e) {
						logger.error(e.getMessage());
						setErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						setErrorDomain(uploadId, beanReader.getUntokenizedRow(), e.getMessage(), beanReader.getRowNumber(), UploadErrorStatus.PROCESS);
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
	
}

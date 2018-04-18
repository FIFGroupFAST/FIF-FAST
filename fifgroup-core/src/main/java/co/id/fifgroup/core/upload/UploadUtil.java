package co.id.fifgroup.core.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.Media;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.core.validation.UploadFileValidator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.Validator;

import co.id.fifgroup.core.constant.BatchStatus;
import co.id.fifgroup.core.dto.UploadDTO;
import co.id.fifgroup.core.dto.UploadErrorDTO;
import co.id.fifgroup.core.service.SecurityService;

public class UploadUtil<T> {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(UploadUtil.class);

	public static final String HEADER_ID = "headerId";
	public static final String VERSION_NUMBER = "versionNumber";
	public static final String VERSION_ID = "versionId";
	public static final String DATE_TO = "dateTo";
	public static final String ROW_NUMBER = "rowNumber";
	private final CellProcessor[] processors;
	private final List<Validator<T>> validators;
	private final Map<Integer, String> errorMessages = new HashMap<>();
	private final Converter<T> converter;
	private final ConverterList<T> converterList;
	private UploadService<T> uploadService;
	private UploadErrorDTO uploadErrorDTO;
	private List<T> list = new ArrayList<T>();
	
	private SecurityService securityServiceImpl = (SecurityService) SpringUtil.getBean("securityServiceImpl");
	
	public UploadUtil(CellProcessor[] processors, List<Validator<T>> validators,
			Converter<T> converter, ConverterList<T> converterList, String serviceName) {
		this.processors = processors;
		this.validators = validators;
		this.converter = converter;
		this.converterList = converterList;
		this.uploadService = (UploadService) SpringUtil.getBean(serviceName);
	}
	
	public void validateFile(Media media, final Integer columns) throws ValidationException {
		UploadFileValidator uploadFileValidator = new UploadFileValidator() {
			
			@Override
			public int getColumns() {
				return columns;
			}
		};
		uploadFileValidator.validate(media);
	}
	
	public void processData(Media media, UploadDTO uploadDTO) throws IOException {
		File file = new File(media.getName());
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(media.getByteData());
		fos.flush();
		fos.close();
		
		try {
			uploadFile(file, uploadDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Transactional
	public void uploadFile(File file, UploadDTO uploadDTO) throws Exception{
		if(uploadDTO.getId() == null) {
			uploadDTO.setStatus(BatchStatus.OPEN.toString());
			uploadDTO.setBatchNumber(uploadService.getBatchNumber());
			uploadDTO.setUploadedBy(securityServiceImpl.getSecurityProfile().getUserId());
			uploadDTO.setUploadDate(new Date());
			uploadDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			uploadDTO.setCreationDate(new Date());
			uploadDTO.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			uploadDTO.setLastUpdateDate(new Date());
			uploadDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			uploadService.insertHeaderUpload(uploadDTO);
		}
		else 
			uploadService.deleteDetailError(uploadDTO.getId());
		
		ICsvMapReader reader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
		String[] headers = reader.getHeader(true);
		
		if(converterList != null) {
			try{
				for (int rowNum = 1; rowNum <= reader.getRowNumber(); rowNum++ ) {
					uploadErrorDTO = new UploadErrorDTO();
					try {
						Map<String, Object> row = reader.read(headers, processors);
						if(row != null) {
							row.put(ROW_NUMBER, String.valueOf(rowNum));
							T object = converter.convert(row);
							list.add(object);
						}
					} catch(SuperCsvException ex) {
						addError(rowNum, ex.getMessage());
						if(getMessages(rowNum) != null) {
							uploadErrorDTO.setId(uploadDTO.getId());
							uploadErrorDTO.setErrorMessage(getMessages(rowNum));
							uploadErrorDTO.setRowNumber(rowNum);
							uploadErrorDTO.setRawData(reader.getUntokenizedRow());
							uploadService.insertDetailError(uploadErrorDTO);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					clearError();
				}
				List<T> objects = converterList.convert(list);
				for(T obj : objects) {
					for (Validator<T> validator : validators) {
						try {
							validator.validate(obj);
						} catch(ValidationException vex) {
							clearError();
							addError(-1, vex.getAllMessages());
						}
					}
					if(getMessages(-1) != null) {
						uploadErrorDTO.setId(uploadDTO.getId());
						uploadErrorDTO.setErrorMessage(getMessages(-1));
						uploadErrorDTO.setRowNumber(-1);
						uploadErrorDTO.setRawData(obj.toString());
						uploadService.insertDetailError(uploadErrorDTO);
					} else {
						uploadService.insertDetailUpload(obj, uploadDTO.getId());
					}
				}
			} finally {
				
			}
		} else {
			try{
				for (int rowNum = 1; rowNum <= reader.getRowNumber(); rowNum++ ) {
					uploadErrorDTO = new UploadErrorDTO();
					try {					
						Map<String, Object> row = reader.read(headers, processors);
						if(row != null) {
							T object = converter.convert(row);							
							for (Validator<T> validator : validators) {
								try {					
									validator.validate(object);
								} catch(ValidationException vex) {
									addError(rowNum, vex.getAllMessages());
								}
							}
							if(getMessages(rowNum) != null) {
								uploadErrorDTO.setId(uploadDTO.getId());
								uploadErrorDTO.setErrorMessage(getMessages(rowNum));
								uploadErrorDTO.setRowNumber(rowNum);
								uploadErrorDTO.setRawData(reader.getUntokenizedRow());
								uploadService.insertDetailError(uploadErrorDTO);
							}
							else
								uploadService.insertDetailUpload(object, uploadDTO.getId());
						}						
					} catch(SuperCsvException ex) {
						addError(rowNum, ex.getMessage());
						if(getMessages(rowNum) != null) {
							uploadErrorDTO.setId(uploadDTO.getId());
							uploadErrorDTO.setErrorMessage(getMessages(rowNum));
							uploadErrorDTO.setRowNumber(rowNum);
							uploadErrorDTO.setRawData(reader.getUntokenizedRow());
							uploadService.insertDetailError(uploadErrorDTO);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					clearError();
				}			
			} catch (Exception e) {
				reader.close();
			}
		}		
		
		Integer countValidRow = uploadService.getCountValidRow(null, uploadDTO.getId());
		Integer countErrorRow = uploadService.getCountErrorRow(null, uploadDTO.getId());
		
		uploadDTO.setSucceedRecords(countValidRow);
		uploadDTO.setFailedRecords(countErrorRow);
		uploadDTO.setTotalRecords(countErrorRow + countValidRow);
		uploadService.updateHeaderUpload(uploadDTO);
	}
	
	@Transactional
	public void closeBatch(UploadDTO uploadDTO) {
		if(!uploadDTO.getStatus().equalsIgnoreCase(BatchStatus.CLOSED.toString())) {
			Long uploadId = uploadDTO.getId();
			uploadService.deleteDetailError(uploadId);
			List<T> subjectList = uploadService.findAllByUploadId(uploadId);
			for(T subject : subjectList) {
				try{
					uploadService.validate(subject);
					Long id = uploadService.getIdGenerator();
					uploadService.insertHeader(subject, id);
					uploadService.insertDetail(subject, id, 1);
				} catch (ValidationException ve) {
					if(ve.getConstraintViolations().get(HEADER_ID) != null) {
							Long headerId = Long.parseLong(ve.getConstraintViolations().get(HEADER_ID));
							Integer versionNumber = Integer.parseInt(ve.getConstraintViolations().get(VERSION_NUMBER) != null ? ve.getConstraintViolations().get(VERSION_NUMBER) : null);
							if(versionNumber == null) versionNumber = 0;
							uploadService.insertDetail(subject, headerId, (versionNumber+1));
					} else {
						UploadErrorDTO uploadErrorDTO = new UploadErrorDTO();
						uploadErrorDTO.setId(uploadId);
						uploadErrorDTO.setErrorMessage(ve.getAllMessages());
						uploadService.insertDetailError(uploadErrorDTO);
					}
				}
			}
			uploadDTO.setStatus(BatchStatus.CLOSED.toString());
			uploadService.deleteDetailUpload(uploadId);
			
			Integer countValidRow = uploadService.getCountValidRow(null, uploadDTO.getId());
			Integer countErrorRow = uploadService.getCountErrorRow(null, uploadDTO.getId());
			
			uploadDTO.setSucceedRecords(countValidRow);
			uploadDTO.setFailedRecords(countErrorRow);
			uploadDTO.setTotalRecords(countErrorRow + countValidRow);
			uploadService.updateHeaderUpload(uploadDTO);
		}
	}
	
	@Transactional
	public void cancelBatch(UploadDTO uploadDTO) {
		if(!uploadDTO.getStatus().equalsIgnoreCase(BatchStatus.CLOSED.toString())) {
			Long uploadId = uploadDTO.getId();
			uploadService.deleteDetailError(uploadId);
			uploadService.deleteDetailUpload(uploadId);
			uploadDTO.setStatus(BatchStatus.CANCELLED.toString());
			uploadService.updateHeaderUpload(uploadDTO);
		}
	}
	
	public void clearError() {
		errorMessages.clear();
	}

	public String getMessages(Integer rowNum) {
		if (errorMessages.containsKey(rowNum)) {
			return errorMessages.get(rowNum);
		}
		return null;
	}
	
	private void addError(Integer rowNum, String message) {
		if (errorMessages.containsKey(rowNum)) {
			String temp = errorMessages.get(rowNum);
			errorMessages.put(rowNum, message + " | " + temp);
		} else {
			errorMessages.put(rowNum, message);
		}
	}
	
	public static String[] getHeader(File file) throws IOException {
		ICsvMapReader mapReader = null;
		String[] headers = null;
		try {
			mapReader = new CsvMapReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
			headers = mapReader.getHeader(true);
		}
		finally {
			if(mapReader != null)
				mapReader.close();
		}
		return headers;	
	}
	
}

package co.id.fifgroup.core.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.core.constant.BatchStatus;
import co.id.fifgroup.core.constant.UploadErrorStatus;
import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.domain.UploadElement;
import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.core.domain.UploadStage;
import co.id.fifgroup.core.dto.UploadElementDTO;
import co.id.fifgroup.core.finder.BatchUploadFinder;
import co.id.fifgroup.core.finder.UploadElementFinder;
import co.id.fifgroup.core.finder.UploadErrorFinder;
import co.id.fifgroup.core.finder.UploadStageFinder;
import co.id.fifgroup.core.security.SecurityProfile;

//@Transactional
public abstract class AbstractUploadDownloadService<S extends UploadStage> implements UploadDownloadService<S> {
	
	private Logger logger = LoggerFactory.getLogger(AbstractUploadDownloadService.class);
	
	private DateFormat df = new SimpleDateFormat("YY");
	
	protected final String trxCodeBatchNumber = "MST_BATCH_NUMBER";
	
	protected abstract String getBatchUploadTableName();
	protected abstract BatchUpload getBatchUploadObject(Object... args);
	protected abstract String getUploadElementTableName();
	protected abstract String getUploadElementSequenceName();
	protected abstract String getUploadErrorTableName();
	protected abstract String getUploadErrorSequenceName();
	protected abstract String getUploadStageTableName();
	protected abstract void validate(S stageObject, Object... args) throws Exception;
	protected abstract void insertStageUpload(S stageObject);
	protected abstract void moveToOriginalTable(S stageObject);
	protected abstract void validateBeforeClose(S stageObject) throws Exception;
	protected abstract List<S> getStageObjectByUploadId(Long uploadId);
	
	@Autowired
	protected SecurityService securityServiceImpl;
	@Autowired
	protected SequenceGeneratorUtilServiceAdapter sequenceGeneratorUtilServiceImpl;
	@Autowired
	private BatchUploadFinder batchUploadFinder;
	@Autowired
	protected UploadElementFinder uploadElementFinder;
	@Autowired
	protected UploadErrorFinder uploadErrorFinder;
	@Autowired
	private UploadStageFinder uploadStageFinder;
	
	protected String getBatchNumber() {
		return df.format(new Date()) + sequenceGeneratorUtilServiceImpl.getSequenceGeneratorFormat(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), trxCodeBatchNumber);
	}
	
	@Transactional
	protected void insertBatchUpload(String tableName, BatchUpload batchUpload) {
		batchUploadFinder.insert(tableName, batchUpload);
	}
	
	@Transactional(readOnly=true)
	protected BatchUpload getBatchUploadByBatchNumber(String tableName, String batchNumber) {
		return batchUploadFinder.getBatchUploadByBatchNumber(getBatchUploadTableName(), batchNumber);
	}
	
	@Transactional
	protected void updateBatchUpload(String tableName, BatchUpload batchUpload) {
		batchUploadFinder.updateById(tableName, batchUpload);
	}
	
	@Transactional
	protected void updateStatusToClosedUploadErrorByUploadId(String tableName, Long uploadId) {
		uploadErrorFinder.updateStatusToClosedUploadErrorByUploadId(tableName, uploadId);
	}
	
	@Transactional
	protected void deleteUploadErrorByUploadId(String tableName, Long uploadId) {
		uploadErrorFinder.deleteUploadErrorByUploadId(tableName, uploadId);
	}
	
	@Transactional
	protected void updateStatusToClosedUploadStageByUploadId(String tableName, Long uploadId) {
		uploadStageFinder.updateStatusToClosedUploadStageByUploadId(tableName, uploadId);
	}
	
	@Transactional
	protected void insertUploadElement(String tableName, String sequenceName, UploadElement uploadElement) {
		uploadElementFinder.insert(tableName, sequenceName, uploadElement);
	}
	
	@Transactional
	protected void updateUploadElement(String tableName, UploadElement uploadElement) {
		uploadElementFinder.updateById(tableName, uploadElement);
	}
	
	@Transactional
	protected void insertErrorUpload(String tableName, String sequenceName, UploadError uploadError) {
		uploadErrorFinder.insert(tableName, sequenceName, uploadError);
	}
	
	@Transactional(readOnly=true)
	protected UploadElementDTO getUploadElementDtoByUploadId(Long uploadId) {
		return uploadElementFinder.selectUploadElementDtoById(getBatchUploadTableName(), getUploadElementTableName(), uploadId);
	}
	
	@Transactional(readOnly=true)
	protected UploadElement getUploadElementByUploadId(Long uploadId) {
		return uploadElementFinder.getUploadElementById(getUploadElementTableName(), uploadId);
	}
	
	@Transactional(readOnly=true)
	public List<UploadError> getUploadError(String batchNumber, UploadErrorStatus uploadErrorStatus) {
		return uploadErrorFinder.getUploadError(getUploadElementTableName(), getUploadErrorTableName(), batchNumber, uploadErrorStatus);
	}
	
	//START ITSM 15052708450385 by: HS
	//menambahkan method untuk insert bulk ke database
	@Transactional
	protected void insertErrorUploadBulk(String tableName, String sequenceName, List<UploadError> uploadErrors) {
		uploadErrorFinder.insertBulk(tableName, sequenceName, uploadErrors);
	}
	
	//Error domain di kumpulkan dalam 1 list agar bisa di lempar ke insertBulk method
	protected UploadError getErrorDomain(Long uploadId, String rawData, String errorMessage, int rowNumber, UploadErrorStatus uploadErrorStatus, 
			SecurityProfile securityProfile, Date trxDate) {
		UploadError uploadError = new UploadError();
		uploadError.setUploadId(uploadId);
		uploadError.setRowNumber(rowNumber);
		uploadError.setRawData(rawData);
		uploadError.setIsClosed(false);
		uploadError.setSource(uploadErrorStatus);
		uploadError.setErrorMessage(errorMessage != null ? errorMessage.length() > 1000 ? errorMessage.substring(0, 1000) : errorMessage : "");
		uploadError.setCreatedBy(securityProfile.getUserId());
		uploadError.setCreationDate(trxDate);
		uploadError.setLastUpdatedBy(securityProfile.getUserId());
		uploadError.setLastUpdateDate(trxDate);

		return uploadError;
	}
	//END ITSM 15052708450385
	
	protected void setErrorDomain(Long uploadId, String rawData, String errorMessage, int rowNumber, UploadErrorStatus uploadErrorStatus) {
		UploadError uploadError = new UploadError();
		uploadError.setUploadId(uploadId);
		uploadError.setRowNumber(rowNumber);
		uploadError.setRawData(rawData);
		uploadError.setIsClosed(false);
		uploadError.setSource(uploadErrorStatus);
		uploadError.setErrorMessage(errorMessage != null ? errorMessage.length() > 1000 ? errorMessage.substring(0, 1000) : errorMessage : "");
		uploadError.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		uploadError.setCreationDate(new Date());
		uploadError.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		uploadError.setLastUpdateDate(new Date());
		insertErrorUpload(getUploadErrorTableName(), getUploadErrorSequenceName(), uploadError);
	}
	
	@Override
	public void cancelBatch(BatchUpload batchUpload) {
		batchUpload.setStatus(BatchStatus.CANCELLED);
		batchUpload.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		batchUpload.setLastUpdateDate(new Date());
		updateBatchUpload(getBatchUploadTableName(), batchUpload);
		
		UploadElement uploadElement = new UploadElement();
		uploadElement.setBatchNumber(batchUpload.getBatchNumber());
		List<UploadElement> uploadElements = uploadElementFinder.selectByExample(getUploadElementTableName(), uploadElement);
		for(UploadElement uploadElmt : uploadElements) {
			updateStatusToClosedUploadErrorByUploadId(getUploadErrorTableName(), uploadElmt.getUploadId());
			updateStatusToClosedUploadStageByUploadId(getUploadStageTableName(), uploadElmt.getUploadId());
		}
	}
	
	@Override
	public void closeBatch(BatchUpload batchUpload) {
		boolean hasError = false;
		UploadElement uploadElement = new UploadElement();
		uploadElement.setBatchNumber(batchUpload.getBatchNumber());
		List<UploadElement> uploadElements = uploadElementFinder.selectByExample(getUploadElementTableName(), uploadElement);
		doBeforeCloseBatch(batchUpload);
		for(UploadElement uploadElmt : uploadElements) {
			List<S> stageObjects = getStageObjectByUploadId(uploadElmt.getUploadId());
			for(S stageObject : stageObjects) {
				try {
					validateBeforeClose(stageObject);
					moveToOriginalTable(stageObject);
				} catch (ValidationException e) {
					logger.error(e.getMessage());
					setErrorDomain(stageObject.getUploadId(), stageObject.getRawData(), e.getMessage(), stageObject.getRowNumber(), UploadErrorStatus.CLOSE_BATCH);
					hasError = true;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					setErrorDomain(stageObject.getUploadId(), stageObject.getRawData(), e.getMessage(), stageObject.getRowNumber(), UploadErrorStatus.CLOSE_BATCH);
				}
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
	public List<UploadError> getFailedRows(Long uploadId) {
		UploadError uploadError = new UploadError();
		uploadError.setUploadId(uploadId);
		return uploadErrorFinder.selectByExample(getUploadErrorTableName(), uploadError);
	}
	
	protected String insertBatchUpload(Object... args) {
		Date date = new Date();
		BatchUpload batchUpload = getOpenStatusObject(args);
		if(batchUpload == null) {
			batchUpload = getBatchUploadObject(args);
			batchUpload.setBatchNumber(getBatchNumber());
			batchUpload.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			batchUpload.setStatus(BatchStatus.OPEN);
			batchUpload.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			batchUpload.setCreationDate(date);
			batchUpload.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			batchUpload.setLastUpdateDate(date);
			insertBatchUpload(getBatchUploadTableName(), batchUpload);
		}
		return batchUpload.getBatchNumber();
	}
	
	protected Long insertUploadElement(String batchNumber) {
		Date date = new Date();
		UploadElement uploadElement = new UploadElement();
		uploadElement.setBatchNumber(batchNumber);
		uploadElement.setUploadDate(date);
		uploadElement.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		uploadElement.setCreationDate(date);
		uploadElement.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		uploadElement.setLastUpdateDate(date);
		insertUploadElement(getUploadElementTableName(), getUploadElementSequenceName(), uploadElement);
		return uploadElement.getUploadId();
	}
	
	@Transactional(readOnly=false)
	protected void insertSuccessRowAndFailedRow(String batchNumber, Long uploadId) {
		UploadElement uploadElement = getUploadElementByUploadId(uploadId);
		uploadElement.setSuccessRows(uploadStageFinder.countByUploadId(getUploadStageTableName(), uploadId));
		UploadError uploadError = new UploadError();
		uploadError.setUploadId(uploadId);
		uploadElement.setFailedRows(uploadErrorFinder.countByExample(getUploadErrorTableName(), uploadError));
		updateUploadElement(getUploadElementTableName(), uploadElement);
		BatchUpload batchUpload = getBatchUploadByBatchNumber(getBatchUploadTableName(), batchNumber);
		batchUpload.setLastUpdateDate(new Date());
		batchUpload.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		updateBatchUpload(getBatchUploadTableName(), batchUpload);
	}
	
	@Transactional(readOnly=true)
	protected UploadElementDTO getUploadElementDto(Long uploadId) {
		UploadElementDTO uploadElementDto = getUploadElementDtoByUploadId(uploadId);
		return uploadElementDto;
	}
	
	@Override
	public List<BatchUpload> findBatchUploadByExample(BatchUpload batchUpload) {
		return batchUploadFinder.selectByExample(getBatchUploadTableName(), getUploadElementTableName(), batchUpload);
	}
	@Override
	public List<UploadElementDTO> findUploadElementByExample(UploadElementDTO uploadElementDto) {
		return uploadElementFinder.selectUploadElementDtoByExample(getBatchUploadTableName(), getUploadElementTableName(), uploadElementDto);
	}
	
	protected void initBeforeProcessData(Object... args) throws Exception {
		
	}
	
	protected void postUpload(UploadElement uploadElement, Object... args) { }
	
	protected void doBeforeCloseBatch(BatchUpload batchUpload) { }
	
	protected void doAfterCloseBatch(BatchUpload batchUpload) {	}
	
}

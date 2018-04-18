package co.id.fifgroup.eligibility.service;

import java.util.List;

import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.service.UploadDownloadService;
import co.id.fifgroup.eligibility.dto.DecisionTableStageDTO;

public interface DecisionTableUploadService extends UploadDownloadService<DecisionTableStageDTO>{

	byte[] getTemplate(String module, Long decisionTableId);

	void downloadFailedRecords(Long uploadId);

	BatchUpload getOpenStatusObject(Object... args);

	void downloadTemplate(Object... args) throws Exception;
	
	List<BatchUpload> findByExample(BatchUploadFilter filter, BatchUpload batchUpload);
}

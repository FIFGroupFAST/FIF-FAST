package co.id.fifgroup.eligibility.service;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.core.domain.BatchUpload;

public interface BatchUploadFilter extends Serializable {
	List<BatchUpload> findBatchUploadByExample(String moduleName, String tableName, String elementName, BatchUpload batchUpload);
}

package co.id.fifgroup.core.dto;

import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.domain.UploadElement;

public class UploadElementDTO extends UploadElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4946784985412016746L;
	private BatchUpload batchUpload;
	private String uploadedBy;
	
	public BatchUpload getBatchUpload() {
		return batchUpload;
	}
	public void setBatchUpload(BatchUpload batchUpload) {
		this.batchUpload = batchUpload;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
}

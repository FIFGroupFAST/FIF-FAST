package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.util.Date;

public class ExitInterviewDocument implements Serializable {

	private static final long serialVersionUID = -7438236235085620285L;

	private Long questionSetId;
	private String uploadedFileName;
	private Date exitInterviewDate;

	public Long getQuestionSetId() {
		return questionSetId;
	}

	public void setQuestionSetId(Long questionSetId) {
		this.questionSetId = questionSetId;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public Date getExitInterviewDate() {
		return exitInterviewDate;
	}

	public void setExitInterviewDate(Date exitInterviewDate) {
		this.exitInterviewDate = exitInterviewDate;
	}

}

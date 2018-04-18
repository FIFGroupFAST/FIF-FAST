package co.id.fifgroup.eligibility.ui;

import java.io.Serializable;

import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;
import co.id.fifgroup.eligibility.upload.Argument;
import co.id.fifgroup.eligibility.upload.Argument.Type;

public abstract class UploadDownloadDelegate implements Serializable{
	public void doDownload(DecisionTableDTO decisionTableDTO, DecisionTableUploadService decisionTableUploadService) throws Exception{
		decisionTableUploadService.downloadTemplate(new Argument(
				Type.ID, String.valueOf(decisionTableDTO
						.getId())));
	}
	
	public abstract void doUpload(DecisionTableDTO decisionTableDTO);
}

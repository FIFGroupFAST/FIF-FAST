package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingDetail;

public class ApprovalModelMessageMappingDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ApprovalModelMappingDetail modelMappingDetail;
	private List<LevelMessageDTO> listLevelMessage;
	private boolean validateRule;

	public ApprovalModelMappingDetail getModelMappingDetail() {
		return modelMappingDetail;
	}

	public void setModelMappingDetail(
			ApprovalModelMappingDetail modelMappingDetail) {
		this.modelMappingDetail = modelMappingDetail;
	}

	public List<LevelMessageDTO> getListLevelMessage() {
		return listLevelMessage;
	}

	public void setListLevelMessage(List<LevelMessageDTO> listLevelMessage) {
		this.listLevelMessage = listLevelMessage;
	}

	public boolean isValidateRule() {
		return validateRule;
	}

	public void setValidateRule(boolean validateRule) {
		this.validateRule = validateRule;
	}
	
}

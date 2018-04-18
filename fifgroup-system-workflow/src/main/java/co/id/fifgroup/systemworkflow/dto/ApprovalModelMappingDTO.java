package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.systemworkflow.domain.ApprovalModelMappingHeader;

public class ApprovalModelMappingDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ApprovalModelMappingHeader modelMappingHeader;
	private List<ApprovalModelMessageMappingDTO> listModelMessageMapping;

	public ApprovalModelMappingHeader getModelMappingHeader() {
		return modelMappingHeader;
	}

	public void setModelMappingHeader(
			ApprovalModelMappingHeader modelMappingHeader) {
		this.modelMappingHeader = modelMappingHeader;
	}

	public List<ApprovalModelMessageMappingDTO> getListModelMessageMapping() {
		return listModelMessageMapping;
	}

	public void setListModelMessageMapping(
			List<ApprovalModelMessageMappingDTO> listModelMessageMapping) {
		this.listModelMessageMapping = listModelMessageMapping;
	}

}

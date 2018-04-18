package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.ParameterDetail;

public class ParameterDetailDTO extends ParameterDetail {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3283726987209046312L;
	
	private ParameterDTO mainTask;
	private ParameterDTO successTask;
	private ParameterDTO errorTask;
	
	public ParameterDTO getMainTask() {
		return mainTask;
	}
	public void setMainTask(ParameterDTO mainTask) {
		this.mainTask = mainTask;
	}
	public ParameterDTO getSuccessTask() {
		return successTask;
	}
	public void setSuccessTask(ParameterDTO successTask) {
		this.successTask = successTask;
	}
	public ParameterDTO getErrorTask() {
		return errorTask;
	}
	public void setErrorTask(ParameterDTO errorTask) {
		this.errorTask = errorTask;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}

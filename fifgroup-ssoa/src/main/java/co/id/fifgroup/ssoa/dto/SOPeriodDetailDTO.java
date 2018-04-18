package co.id.fifgroup.ssoa.dto;

import co.id.fifgroup.ssoa.domain.SOPeriodDetail;

public class SOPeriodDetailDTO extends SOPeriodDetail {
	private static final long serialVersionUID = -3283726987209046312L;
	private SOPeriodDTO mainTask;
	private SOPeriodDTO successTask;
	private SOPeriodDTO errorTask;

	public SOPeriodDTO getMainTask() {
		return mainTask;
	}

	public void setMainTask(SOPeriodDTO mainTask) {
		this.mainTask = mainTask;
	}

	public SOPeriodDTO getSuccessTask() {
		return successTask;
	}

	public void setSuccessTask(SOPeriodDTO successTask) {
		this.successTask = successTask;
	}

	public SOPeriodDTO getErrorTask() {
		return errorTask;
	}

	public void setErrorTask(SOPeriodDTO errorTask) {
		this.errorTask = errorTask;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
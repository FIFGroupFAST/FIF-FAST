package co.id.fifgroup.personneladmin.dto;

import java.util.Date;

import co.id.fifgroup.core.domain.UploadStage;

public class PtkpStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private String employeeNumber;
	private String ptkpStatus;
	private Date effectiveStartDate;
	private Long workingScheduleId;

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getPtkpStatus() {
		return ptkpStatus;
	}

	public void setPtkpStatus(String ptkpStatus) {
		this.ptkpStatus = ptkpStatus;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}

	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}

}

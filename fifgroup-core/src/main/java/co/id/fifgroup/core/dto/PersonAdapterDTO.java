package co.id.fifgroup.core.dto;

public class PersonAdapterDTO {

	//jika person tidak punya workingscheduleId gunakan hierarchy grade > job > organisasi > location > company
	Long personId;
	Long personWorkingScheduleId;
	Long gradeId;
	Long gradeWorkingScheduleId;
	Long jobId;
	Long jobWorkingScheduleId;
	Long organizationId;
	Long organizationWorkingScheduleId;
	Long locationId;
	Long locationWorkingScheduleId;
	Long companyId;
	Long companyWorkingScheduleId;
	
	
	/**
	 * @return the personWorkingScheduleId
	 */
	public Long getPersonWorkingScheduleId() {
		return personWorkingScheduleId;
	}
	/**
	 * @param personWorkingScheduleId the personWorkingScheduleId to set
	 */
	public void setPersonWorkingScheduleId(Long personWorkingScheduleId) {
		this.personWorkingScheduleId = personWorkingScheduleId;
	}
	/**
	 * @return the gradeWorkingScheduleId
	 */
	public Long getGradeWorkingScheduleId() {
		return gradeWorkingScheduleId;
	}
	/**
	 * @param gradeWorkingScheduleId the gradeWorkingScheduleId to set
	 */
	public void setGradeWorkingScheduleId(Long gradeWorkingScheduleId) {
		this.gradeWorkingScheduleId = gradeWorkingScheduleId;
	}
	/**
	 * @return the jobWorkingScheduleId
	 */
	public Long getJobWorkingScheduleId() {
		return jobWorkingScheduleId;
	}
	/**
	 * @param jobWorkingScheduleId the jobWorkingScheduleId to set
	 */
	public void setJobWorkingScheduleId(Long jobWorkingScheduleId) {
		this.jobWorkingScheduleId = jobWorkingScheduleId;
	}
	/**
	 * @return the organizationWorkingScheduleId
	 */
	public Long getOrganizationWorkingScheduleId() {
		return organizationWorkingScheduleId;
	}
	/**
	 * @param organizationWorkingScheduleId the organizationWorkingScheduleId to set
	 */
	public void setOrganizationWorkingScheduleId(Long organizationWorkingScheduleId) {
		this.organizationWorkingScheduleId = organizationWorkingScheduleId;
	}
	/**
	 * @return the locationWorkingScheduleId
	 */
	public Long getLocationWorkingScheduleId() {
		return locationWorkingScheduleId;
	}
	/**
	 * @param locationWorkingScheduleId the locationWorkingScheduleId to set
	 */
	public void setLocationWorkingScheduleId(Long locationWorkingScheduleId) {
		this.locationWorkingScheduleId = locationWorkingScheduleId;
	}
	/**
	 * @return the companyWorkingScheduleId
	 */
	public Long getCompanyWorkingScheduleId() {
		return companyWorkingScheduleId;
	}
	/**
	 * @param companyWorkingScheduleId the companyWorkingScheduleId to set
	 */
	public void setCompanyWorkingScheduleId(Long companyWorkingScheduleId) {
		this.companyWorkingScheduleId = companyWorkingScheduleId;
	}
	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * @return the jobId
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}
	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	/**
	 * @return the companyId
	 */
	public Long getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}

package co.id.fifgroup.core.dto;


import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.domain.IdpEmployee;
import co.id.fifgroup.core.domain.transaction.CommonTrx;

public class IdpEmployeeDTO extends IdpEmployee implements CommonTrx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long jobId;
	public String jobName;
	public String employeeNumber;
	public String fullName;
	public String userName;
	public List<IdpDetailEmployeeDTO> listIdpDetails;
	public String requestBy;
	public String requestByName;
	private UUID objectEmployeeUUID;
	private String branchName;
	private String organizationName;
	private String grade;
	private UUID submitterPersonUUID;
	public List<IdpDetailEmployeeDTO> listIdpDetailsForCoeRequest;
	public List<IdpDetailEmployeeDTO> listIdpDetailsForDevelopmentRequest;
	private Long totalIdp;
	private Long totalAccomplishIdp;
	private Long totalNotAccomplishIdp;
	private boolean isValidDetail;
	public String peopleType;

	public String getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}
	
	public List<IdpDetailEmployeeDTO> getListIdpDetailsForCoeRequest() {
		return listIdpDetailsForCoeRequest;
	}

	public void setListIdpDetailsForCoeRequest(
			List<IdpDetailEmployeeDTO> listIdpDetailsForCoeRequest) {
		this.listIdpDetailsForCoeRequest = listIdpDetailsForCoeRequest;
	}

	public List<IdpDetailEmployeeDTO> getListIdpDetailsForDevelopmentRequest() {
		return listIdpDetailsForDevelopmentRequest;
	}

	public void setListIdpDetailsForDevelopmentRequest(
			List<IdpDetailEmployeeDTO> listIdpDetailsForDevelopmentRequest) {
		this.listIdpDetailsForDevelopmentRequest = listIdpDetailsForDevelopmentRequest;
	}

	public UUID getSubmitterPersonUUID() {
		return submitterPersonUUID;
	}

	public void setSubmitterPersonUUID(UUID submitterPersonUUID) {
		this.submitterPersonUUID = submitterPersonUUID;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setObjectEmployeeUUID(UUID objectEmployeeUUID) {
		this.objectEmployeeUUID = objectEmployeeUUID;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public String getRequestByName() {
		return requestByName;
	}

	public void setRequestByName(String requestByName) {
		this.requestByName = requestByName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public List<IdpDetailEmployeeDTO> getListIdpDetails() {
		return listIdpDetails;
	}

	public void setListIdpDetails(List<IdpDetailEmployeeDTO> listIdpDetails) {
		this.listIdpDetails = listIdpDetails;
	}

	@Override
	public Long getBranchType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getJobId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getGradeId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getOrganizationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLocationId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJobGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBusinessGroupId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getKeyJob() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmploymentStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID getObjectEmployeeUUID() {
		// TODO Auto-generated method stub
		return this.objectEmployeeUUID;
	}

	@Override
	public boolean isBasedLineRequestor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransactionApprovalStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long getOrganizationDestinationId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getTotalIdp() {
		return totalIdp;
	}

	public void setTotalIdp(Long totalIdp) {
		this.totalIdp = totalIdp;
	}

	public Long getTotalAccomplishIdp() {
		return totalAccomplishIdp;
	}

	public void setTotalAccomplishIdp(Long totalAccomplishIdp) {
		this.totalAccomplishIdp = totalAccomplishIdp;
	}

	public Long getTotalNotAccomplishIdp() {
		return totalNotAccomplishIdp;
	}

	public void setTotalNotAccomplishIdp(Long totalNotAccomplishIdp) {
		this.totalNotAccomplishIdp = totalNotAccomplishIdp;
	}

	public boolean isValidDetail() {
		return isValidDetail;
	}

	public void setValidDetail(boolean isValidDetail) {
		this.isValidDetail = isValidDetail;
	}

}


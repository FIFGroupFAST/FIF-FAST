package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.constant.PeopleType;

public class PersonCriteriaDTO implements Serializable {

	private static final long serialVersionUID = -3020793891253793560L;

	private Long branchId;
	private Long organizationId;
	private Long jobId;
	private String peopleType;
	private String employeeNumber;
	private String employmentCategory;
	private Date effectiveOnDate;
	private String fullName;
	private Date birthDate;
	private String gender;
	private Boolean isAffco;
	private List<Long> gradeExclusions;
	private Boolean isKeyJob;
	private List<String> gradeInclusions;
	private Long companyId;
	private List<Long> inOrganizationId;
	private List<Long> notInOrganizationId;
	private Boolean selectedPeopleType;
	private Long personId;
	private Boolean isEmployee;
	private Long supervisorId;
	private List<String> employmentCategories;
	private List<String> inEmployeeNumber = new ArrayList<String>();
	private Long employeeNumberFrom;
	private Long employeeNumberTo;
	private List<PeopleType> inPeopleTypes = new ArrayList<PeopleType>();
	private Boolean isEmployeeInThisMonth;
	private Long payrollTrnId;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	
	// start added for phase 2
	private String companyName;
	// end added for phase 2
	
	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory(String employmentCategory) {
		this.employmentCategory = employmentCategory;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getIsAffco() {
		return isAffco;
	}

	public void setIsAffco(Boolean isAffco) {
		this.isAffco = isAffco;
	}

	public List<Long> getGradeExclusions() {
		return gradeExclusions;
	}

	public void setGradeExclusions(List<Long> gradeExclusions) {
		this.gradeExclusions = gradeExclusions;
	}

	public Boolean getIsKeyJob() {
		return isKeyJob;
	}

	public void setIsKeyJob(Boolean isKeyJob) {
		this.isKeyJob = isKeyJob;
	}

	public List<String> getGradeInclusions() {
		return gradeInclusions;
	}

	public void setGradeInclusions(List<String> gradeInclusions) {
		this.gradeInclusions = gradeInclusions;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<Long> getInOrganizationId() {
		return inOrganizationId;
	}

	public void setInOrganizationId(List<Long> inOrganizationId) {
		this.inOrganizationId = inOrganizationId;
	}

	public List<Long> getNotInOrganizationId() {
		return notInOrganizationId;
	}

	public void setNotInOrganizationId(List<Long> notInOrganizationId) {
		this.notInOrganizationId = notInOrganizationId;
	}

	public Boolean getSelectedPeopleType() {
		return selectedPeopleType;
	}

	public void setSelectedPeopleType(Boolean selectedPeopleType) {
		this.selectedPeopleType = selectedPeopleType;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Boolean getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public Long getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Long supervisorId) {
		this.supervisorId = supervisorId;
	}

	public List<String> getEmploymentCategories() {
		return employmentCategories;
	}

	public void setEmploymentCategories(List<String> employmentCategories) {
		this.employmentCategories = employmentCategories;
	}

	public List<String> getInEmployeeNumber() {
		return inEmployeeNumber;
	}

	public void setInEmployeeNumber(List<String> inEmployeeNumber) {
		this.inEmployeeNumber = inEmployeeNumber;
	}

	public Long getEmployeeNumberFrom() {
		return employeeNumberFrom;
	}

	public void setEmployeeNumberFrom(Long employeeNumberFrom) {
		this.employeeNumberFrom = employeeNumberFrom;
	}

	public Long getEmployeeNumberTo() {
		return employeeNumberTo;
	}

	public void setEmployeeNumberTo(Long employeeNumberTo) {
		this.employeeNumberTo = employeeNumberTo;
	}

	public List<PeopleType> getInPeopleTypes() {
		return inPeopleTypes;
	}

	public void setInPeopleTypes(List<PeopleType> inPeopleTypes) {
		this.inPeopleTypes = inPeopleTypes;
	}

	public Boolean getIsEmployeeInThisMonth() {
		return isEmployeeInThisMonth;
	}

	public void setIsEmployeeInThisMonth(Boolean isExEmployeeInThisMonth) {
		this.isEmployeeInThisMonth = isExEmployeeInThisMonth;
	}

	public Long getPayrollTrnId() {
		return payrollTrnId;
	}

	public void setPayrollTrnId(Long payrollTrnId) {
		this.payrollTrnId = payrollTrnId;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	
	// start added for phase 2
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	// end added for phase 2
	
}

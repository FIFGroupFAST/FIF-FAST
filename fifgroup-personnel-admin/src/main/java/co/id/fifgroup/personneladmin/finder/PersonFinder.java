package co.id.fifgroup.personneladmin.finder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.dto.PeopleAffcoDTO;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface PersonFinder {

	public List<PersonDTO> selectPersonByBusinessGroup(@Param("criteria") PersonCriteriaDTO criteria, @Param("groupId") Long groupId);

	public List<Long> selectPersonIdsByBusinessGroup(@Param("criteria") PersonCriteriaDTO criteria, @Param("groupId") Long groupId);

	public List<PersonDTO> selectPersonByBusinessGroup(@Param("criteria") PersonCriteriaDTO criteria, @Param("groupId") Long groupId, RowBounds rowBounds);

	public List<PersonDTO> selectPersonByCompany(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<PersonDTO> selectPersonByCompanyTemp(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);
	
	public List<PersonDTO> selectPersonByCompanyInvalidDate(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	public List<PersonDTO> selectPersonByCompanyForPayroll(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<PersonDTO> selectPersonByCompanyInquiry(@Param("criteria") PersonCriteriaDTO criteria, RowBounds rowBounds);

	public List<PersonDTO> selectPersonByCompany(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);
	
	public List<PersonDTO> selectPersonByCompanyTemp(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	public Integer countPersonByCompanyInquiry(@Param("criteria") PersonCriteriaDTO criteria);

	public PersonDTO selectPersonById(@Param("personId") Long personId, @Param("criteria") PersonCriteriaDTO criteria);

	public PersonDTO selectPersonByPersonUUID(@Param("personUUID") UUID personUUID, @Param("criteria") PersonCriteriaDTO criteria);

	//add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	public PersonDTO selectPersonByIdOrganizationActiveandInactive(@Param("personId") Long personId, @Param("criteria") PersonCriteriaDTO criteria);
	
	public PersonDTO selectPersonByPersonUUIDInactive(@Param("personUUID") UUID personUUID, @Param("criteria") PersonCriteriaDTO criteria);
	//end add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review

	public List<PersonAssignmentDTO> selectPersonByAssignment(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId, @Param("branchId") Long branchId,
			@Param("roleCode") String roleCode, @Param("personId") Long personId, @Param("companyId") Long companyId, @Param("jobGroupCode") String jobGroupCode);

	public PersonAssignmentDTO selectAssignmentByPersonId(@Param("personId") Long personId, @Param("companyId") Long companyId);

	public List<KeyValue> selectPersonByCompanyKeyValue(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	public int countPersonByCompany(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);
	
	public int countPersonByCompanyTemp(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);
	
	public int countPersonByCompanyInvalidDate(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<PersonDTO> selectBySecurityFilter(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("gradeExclusions") List<Long> gradeExclusions, @Param("personId") Long personId, @Param("companyId") Long companyId);

	public List<PersonDTO> selectBySecurityFilterWitRowBounds(@Param("inOrganizationId") List<Long> inOrganizationId,
			@Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("gradeExclusions") List<Long> gradeExclusions, @Param("personId") Long personId,
			@Param("companyId") Long companyId, @Param("employeeNumber") String employeeNumber, @Param("employeeName") String employeeName, RowBounds rowBounds);

	public List<PersonDTO> selectAlltBySecurityFilterWitRowBounds(@Param("inOrganizationId") List<Long> inOrganizationId,
			@Param("notInOrganizationId") List<Long> notInOrganizationId, @Param("gradeExclusions") List<Long> gradeExclusions, @Param("personId") Long personId,
			@Param("companyId") Long companyId, @Param("employeeNumber") String employeeNumber, @Param("employeeName") String employeeName, RowBounds rowBounds,@Param("supervisorId") Long supervisorId);

	public int countBySecurityFilter(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("gradeExclusions") List<Long> gradeExclusions, @Param("personId") Long personId, @Param("companyId") Long companyId,
			@Param("employeeNumber") String employeeNumber, @Param("employeeName") String employeeName);

	public int countAllBySecurityFilter(@Param("inOrganizationId") List<Long> inOrganizationId, @Param("notInOrganizationId") List<Long> notInOrganizationId,
			@Param("gradeExclusions") List<Long> gradeExclusions, @Param("personId") Long personId, @Param("companyId") Long companyId,
			@Param("employeeNumber") String employeeNumber, @Param("employeeName") String employeeName,@Param("supervisorId") Long supervisorId);

	public List<PeopleAffcoDTO> selectPeopleAffcoByCompanyId(@Param("personId") Long personId, @Param("effectiveOnDate") Date effectiveOnDate,
			@Param("companyId") Long companyId, @Param("employeeNumber") String employeeNumber, @Param("fullName") String fullName);

	public List<PeopleAffcoDTO> selectPeopleAffcoByCompanyId(@Param("personId") Long personId, @Param("effectiveOnDate") Date effectiveOnDate,
			@Param("companyId") Long companyId, @Param("employeeNumber") String employeeNumber, @Param("fullName") String fullName, RowBounds rowBounds);

	public int countPeopleAffcoByCompanyId(@Param("employeeNumber") String employeeNumber, @Param("fullName") String fullName, @Param("companyId") Long companyId);

	public List<PersonDTO> getPersonActiveByChildBirthdate(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<KeyValue> selectAllPersonByCompanyKeyValue(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	public int countAllPersonByCompany(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<String> getLastEducation(@Param("personId") Long personId, @Param("companyId") Long companyId);

	public List<PersonAssignmentDTO> selectPersonByAssignmentForLov(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId,
			@Param("branchId") Long branchId, @Param("roleCode") String roleCode, @Param("personId") Long personId, @Param("companyId") Long companyId,
			@Param("employeeNumber") String employeeNumber, @Param("employeeName") String employeeName, RowBounds rowBounds);

	public Integer countPersonByAssignmentForLov(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId, @Param("branchId") Long branchId,
			@Param("roleCode") String roleCode, @Param("personId") Long personId, @Param("companyId") Long companyId, @Param("employeeNumber") String employeeNumber,
			@Param("employeeName") String employeeName);

	public Integer countPersonByBusinessGroup(@Param("criteria") PersonCriteriaDTO criteria, @Param("groupId") Long groupId);

	public List<PersonDTO> selectSimplePersonByUUID(@Param("personUUID") UUID personUUID, @Param("criteria") PersonCriteriaDTO criteria);

	public PersonDTO selectPersonalData(@Param("criteria") PersonCriteriaDTO criteria);

	public List<PersonDTO> selectEmployeeByNameGenderAndBirthDate(@Param("fullName") String fullName, @Param("birthDate") Date birthDate, @Param("gender") String gender,
			@Param("companyId") Long companyId, @Param("groupId") Long groupId, @Param("effectiveOnDate") Date effectiveOnDate);

	public Long selectCompanyPerson(@Param("personId") Long personId, @Param("effectiveDate") Date effectiveDate);

	public List<PersonDTO> selectPersonByCompanyInquiry(@Param("criteria") PersonCriteriaDTO criteria);

	public List<PersonDTO> selectEmployeeDeptOwnerAndManager(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	public Integer countEmployeeDeptOwnerAndManager(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<PersonDTO> selectPersonByCompanyForEpmd(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	List<PersonDTO> selectExEmployeeForPayroll(@Param("companyId") Long companyId, @Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

	public List<PersonDTO> getPersonForEpdtNotification(@Param("companyId") Long companyId, @Param("responsibilityName") String responsibilityName);

	public List<PersonDTO> getPayrollPerson(@Param("companyId") Long companyId, @Param("listPersonId") List<Long> listPersonId,
			@Param("listBranchId") List<Long> listBranchId, @Param("listOrganizationId") List<Long> listOrganizationId, @Param("listJobId") List<Long> listJobId,
			@Param("employeeNumbers") List<String> employeeNumbers, @Param("processDate") Date processDate);

	public List<Long> selectPersonIdsByCompany(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<PersonDTO> getPayrollPersonByCriteria(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);

	public int countPayrollPersonByCriteria(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<PersonDTO> getLastPersonInfo(@Param("personId") Long personId, @Param("effectiveOnDate") Date effectiveOnDate);

	public PersonDTO getActiveEmployee(@Param("employeeNumber") String employeeNumber, @Param("effectiveOnDate") Date effectiveOnDate);

	public int countPersonByCompanyForEpmd(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);

	public List<String> getLastMajor(@Param("personId") Long personId, @Param("companyId") Long companyId);

	// 14040715241425_CR HCMS – Recruitment_RAH 20141105 Start
	public List<PersonDTO> selectPersonAllCompany(@Param("criteria") PersonCriteriaDTO criteria);

	public List<PersonDTO> selectPersonAllCompanyRowBounds(@Param("criteria") PersonCriteriaDTO criteria, RowBounds rowBounds);
	// 14040715241425_CR HCMS – Recruitment_RAH 20141105 End
	
	public PersonDTO getCwkPersonByPersonUUID(@Param("personUUID") UUID personUUID,
			@Param("effectiveDateOn") Date effectiveDateOn, @Param("companyId")Long companyId);

	// 14071714192817_CR HCMS – Recruitment_LUK 20141209 Start
	public List<PersonDTO> selectPersonFilterCompany(@Param("criteria") PersonCriteriaDTO criteria);

	public List<PersonDTO> selectPersonFilterCompanyRowBounds(@Param("criteria") PersonCriteriaDTO criteria, RowBounds rowBounds);
	// 14071714192817_CR HCMS – Recruitment_LUK 20141209 End

	public PersonDTO selectPersonByIdInvalidDate(@Param("personId") Long personId,
			@Param("criteria") PersonCriteriaDTO criteria);
	
	// Added by Jatis for CWK
	// created to handle approval resolver with people type = 'INTERSHIP'
	public List<PersonAssignmentDTO> selectPersonByAssignmentInternship(@Param("jobId") Long jobId, @Param("organizationId") Long organizationId, @Param("branchId") Long branchId,
			@Param("roleCode") String roleCode, @Param("personId") Long personId, @Param("companyId") Long companyId, @Param("jobGroupCode") String jobGroupCode);
	// End Added by Jatis for CWK

	public List<PersonDTO> findEmployeeByManagerId(
			@Param("inManagerRootBranch") List<Long> inManagerRootBranch, 
			@Param("inManagerBranchWithHead") List<Long> inManagerBranchWithHead, 
			@Param("companyId") Long companyId, 
			@Param("managerId") Long managerId, 
			@Param("criteria") PersonCriteriaDTO criteria, 
			RowBounds rowBounds);
	
	public List<PersonDTO> findEmployeeByManagerIdSortByEmployeeNumber(
			@Param("inManagerRootBranch") List<Long> inManagerRootBranch, 
			@Param("inManagerBranchWithHead") List<Long> inManagerBranchWithHead, 
			@Param("companyId") Long companyId, 
			@Param("managerId") Long managerId, 
			@Param("criteria") PersonCriteriaDTO criteria, 
			RowBounds rowBounds);

	public int countEmployeeByManagerId(
			@Param("inManagerRootBranch") List<Long> inManagerRootBranch, 
			@Param("inManagerBranchWithHead") List<Long> inManagerBranchWithHead, 
			@Param("companyId") Long companyId, 
			@Param("managerId") Long managerId,
			@Param("criteria") PersonCriteriaDTO criteria);

	public List<Long> getManagerRootBranch(Long managerId);

	public List<Long> getManagerBranchWithHead(
			@Param("inOrganizationId") List<Long> inOrganizationId, 
			@Param("notInOrganizationId") List<Long> notInOrganizationId, 
			@Param("companyId") Long companyId, 
			@Param("managerId") Long managerId);

	public List<PersonDTO> selectPersonByCompanyName(
			@Param("criteria") PersonCriteriaDTO criteria,
			@Param("businessGroupId") Long businessGroupId,
			RowBounds rowBounds);
	
	public int countPersonByCompanyName(
			@Param("criteria") PersonCriteriaDTO criteria,
			@Param("businessGroupId") Long businessGroupId);

	public List<PersonDTO> getPersonByJob(@Param("jobId")Long jobId, @Param("effectiveOnDate")Date effectiveOnDate);
	
	//added by jatis for cam
	
	public List<PersonDTO> selectPersonForPeopleRevBound(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId,@Param("orgParent") Long orgParent, RowBounds rowBounds);

	public List<PersonDTO> selectPersonForPeopleRev(@Param("criteria")PersonCriteriaDTO criteria,@Param("companyId")Long workspaceCompanyId,@Param("orgParent") Long orgParent);

	//add by JTS [15123108364420] [HCMS-CAM]BUGFIX-SubDep tdk bisa entry People Revi
	public List<PersonDTO> getSubordinatesForPeopleReview(@Param("inManagerRootBranch") List<Long> inManagerRootBranch, 
			@Param("inManagerBranchWithHead") List<Long> inManagerBranchWithHead,
			@Param("companyId") Long companyId, 
			@Param("managerId") Long managerId, 
			@Param("criteria") PersonCriteriaDTO criteria);
	//end by JTS [15123108364420] [HCMS-CAM]BUGFIX-SubDep tdk bisa entry People Revi
	
	//end added by jatis for cam
	
	//16022214360832 :PErformance system pada irrprom. By HS
	//Untuk data yang banyak, penggunaan join lebih bagus dari pada 
	//penggunakan or statement yang banyak
	public int countPersonReadyForActing(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId);
	
	public List<PersonDTO> selectPersonReadyForActing(@Param("criteria") PersonCriteriaDTO criteria, @Param("companyId") Long companyId, RowBounds rowBounds);
	//END 16022214360832
}

package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.ContactsDTO;
import co.id.fifgroup.personneladmin.dto.DocumentDTO;
import co.id.fifgroup.personneladmin.dto.PeopleAffcoDTO;
import co.id.fifgroup.personneladmin.dto.PerformanceReviewDTO;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.dto.PersonMainDTO;
import co.id.fifgroup.personneladmin.dto.PrimaryAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.ProbationReviewDTO;
import co.id.fifgroup.personneladmin.dto.SecondaryAssignmentDTO;
/*import co.id.fifgroup.transfer.dto.TransferDTO;*/

import co.id.fifgroup.core.constant.TerminationReason;
import co.id.fifgroup.core.dto.BenefitEntitlementDTO;
import co.id.fifgroup.core.dto.LeaveEntitlementDTO;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.personneladmin.domain.Communication;
import co.id.fifgroup.personneladmin.domain.Person;


public interface PersonService {

//	public void saveNewPerson(PersonMainDTO personMainDTO) throws Exception;

	public Long savePerson(Person person);

	public List<PersonDTO> getPersonByBusinessGroup(PersonCriteriaDTO criteria, Long groupId);

	public List<Long> getPersonIdsByBusinessGroup(PersonCriteriaDTO criteria, Long groupId);

	public List<PersonDTO> getPersonByBusinessGroup(PersonCriteriaDTO criteria, Long groupId, int offset, int limit);

	public List<PersonDTO> getPersonByCompany(PersonCriteriaDTO criteria, Long companyId);
	
	public List<PersonDTO> getPersonByCompanyTemp(PersonCriteriaDTO criteria, Long companyId);

//	public List<PersonDTO> getPersonByCompanyForPayroll(PersonCriteriaDTO criteria, Long companyId);

	public List<PersonDTO> getPersonByCompany(PersonCriteriaDTO criteria, Long companyId, int offset, int limit);
	
	public List<PersonDTO> getPersonByCompanyTemp(PersonCriteriaDTO criteria, Long companyId, int offset, int limit);

	public List<PersonDTO> getPersonByCompanyInquiry(PersonCriteriaDTO criteria, int offset, int limit);

	public Integer countPersonByCompanyInquiry(PersonCriteriaDTO criteria);

	public PersonDTO getPersonById(Long personId, Date effectiveDateOn);

	/*
	 * add and remark_14060515085266_Tidak bisa entri overtime karena Transfer
	 * group by_RIM
	 */
	public PersonDTO getPersonByIdForOvertime(Long personId, Date effectiveDateOn);

	/* end add */

	public PersonDTO getPersonByPersonUUID(UUID personUUID, Date effectiveDateOn);

	public List<PersonAssignmentDTO> selectPersonByAssignment(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId,
			String jobGroupCode);

	public PersonAssignmentDTO selectAssignmentByPersonId(Long personId, Long companyId);

	public List<PersonDTO> getPersonBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId,
			Long companyId);

	public List<PersonDTO> getAllPersonBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId,
			Long companyId, String employeeNumber, String employeeName, int limit, int offset, Long supervisorId);

//	public List<Communication> getPersonCommunicationByPersonId(Long personId, Long companyId);

	public List<PersonDTO> getPersonBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId,
			Long companyId, String employeeNumber, String employeeName, int limit, int offset);

	public int countBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId, Long companyId,
			String employeeNumber, String employeeName);

	public int countAllBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId, Long companyId,
			String employeeNumber, String employeeName, Long supervisorId);

	public List<PersonDTO> selectPersonByCompany(PersonCriteriaDTO criteria, Long companyId);

	public List<PersonDTO> selectPersonByCompanyInquiry(PersonCriteriaDTO criteria);

	public void saveDocument(Long personId, Long companyId, String employeeNumber, List<DocumentDTO> listDocuments);

	/*public void saveSecondaryAssignment(Long personId, Long companyId, SecondaryAssignmentDTO secondaryAssignmentDTO) throws Exception;

	public void terminateInternship(PersonDTO personDTO) throws ValidationException, Exception;

	public void hiredInternshipToEmployee(PersonMainDTO personMain) throws Exception;

	public void changeTraineeToPermanent(PersonDTO person) throws Exception;

	public void saveTransferWithinGroup(PersonDTO person, Long refId, Date terminationDate) throws Exception;

	public void cancelJoin(PersonDTO personDTO) throws Exception;

	public void deletePeopleData(PersonDTO person);

	public List<PeopleAffcoDTO> selectPeopleAffcoByCompanyId(Long personId, Date effectiveOnDate, Long companyId, String employeeNumber, String fullName);

	public List<PeopleAffcoDTO> selectPeopleAffcoByCompanyId(Long personId, Date effectiveOnDate, Long companyId, String employeeNumber, String fullName, int limit,
			int offset);

	public void saveAffcoMutation(PersonMainDTO personMain) throws Exception;

	public void updatePrimaryAssignment(PrimaryAssignmentDTO primaryAssignment, boolean isFromUpload) throws Exception;
	
	public void savePerformanceReview(List<PerformanceReviewDTO> listPerformanceReviewDTOs, String employeeNumber, Long personId, Long companyId);

	public void probationReview(ProbationReviewDTO probationReviewDTO) throws Exception;*/

	public int countPersonByCompany(PersonCriteriaDTO personCriteriaDTO, Long companyId);
	
	public int countPersonByCompanyTemp(PersonCriteriaDTO personCriteriaDTO, Long companyId);
	
//	public void saveTransfer(PrimaryAssignmentDTO primaryAssignmentDTO,String originLocation,String destLocation,String employeeCat,Long isKeyJob,Long isPlacmentStatus) throws Exception;

//	public void saveTransferTrainee(SecondaryAssignmentDTO secondaryAssignmentDTO) throws Exception;

	public List<KeyValue> getAllPersonByCompany(PersonCriteriaDTO personCriteriaDTO, Long companyId, int limit, int offset);

	public int countAllPersonByCompany(PersonCriteriaDTO personCriteriaDTO, Long companyId);

	/*public int countPeopleAffcoByCompanyId(String employeeNumber, String fullName, Long companyId);

	public void deletePrimaryAssignment(PrimaryAssignmentDTO primaryAssignmentDTO) throws Exception;

	public void savePromotion(PrimaryAssignmentDTO primaryAssignmentDTO,String locationOrigin,String locationDest,String employeeCat,Long isKeyjob,Long isPlacmentStatus) throws Exception;

	public void updatePersonalInformation(PersonDTO personDTO, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;

	public void adjustLeaveEntitlement(List<LeaveEntitlementDTO> leaveEntitlements);*/

	public List<PersonDTO> getPersonActiveByChildBirthdate(Long companyId);

	/*public void adjustBenefitEntitilement(List<BenefitEntitlementDTO> benefitEntitlements);

	public void updateContact(Long personId, Long companyId, ContactsDTO contacts) throws Exception;

	public void createSpouse(PersonDTO personDTO) throws Exception;

	public void deleteFuturePersonalInfo(PersonDTO personDTO) throws Exception;

	public void reverseTermination(PersonDTO personDTO);

	public String getLastEducation(Long personId, Long companyId);

	public Date getValidHireDate(Date approvedDate);*/

	public boolean isActivePerson(PersonDTO personDTO, Date effectiveOnDate);

//	public void terminateEmployee(PersonDTO person, Date terminationDate, Long refId, boolean isTransferWithinGroup, TerminationReason terminationReason)
//			throws Exception;

	public List<PersonAssignmentDTO> selectPersonByAssignmentForLov(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId,
			String employeeNumber, String employeeName, int limit, int offset);

	public Integer countPersonByAssignmentForLov(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId, String employeeNumber,
			String employeeName);

	public PersonDTO getPersonById(Long personId, Date effectiveDateOn, Long companyId);

	public PersonDTO getPersonByPersonUUID(UUID personUUID, Date effectiveDateOn, Long companyId);

	//add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	public PersonDTO getPersonByIdOrganizationActiveandInactive(Long personId, Date effectiveOnDate, Long companyId);
	
	public PersonDTO getPersonByPersonUUIDInactive(UUID personUUID, Date effectiveDateOn, Long companyId);
	//end add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review

	public Integer countPersonByBusinessGroup(PersonCriteriaDTO criteria, Long groupId);

	public PersonDTO getSimplePersonByUUID(UUID personUUID, Date effectiveOnDate);

	public PersonDTO getPersonalData(Long personId, Long companyId, Date effectiveOnDate);

	public List<PersonDTO> getEmployeeByNameGenderAndBirthDate(String fullName, Date birthDate, String gender, Long companyId, Long groupId, Date effectiveOnDate);

	public Long getCompanyPerson(Long personId, Date effectiveDate);

	public void updateLastUpdatePerson(Long personId);

	public List<PersonDTO> getPersonByCompanyInquiry(PersonCriteriaDTO criteria);

	public void sendNotificationUpdateData(PersonDTO personDTO) throws Exception;

	public List<PersonDTO> getEmployeeDeptOwnerAndManager(PersonCriteriaDTO criteria, Long companyId, int limit, int offset);

	public Integer countEmployeeDeptOwnerAndManager(PersonCriteriaDTO criteria, Long companyId);

	/*public List<PersonDTO> getPersonByCompanyForEpmd(PersonCriteriaDTO criteria, Long companyId, int limit, int offset);

	public int countPersonByCompanyForEpmd(PersonCriteriaDTO criteria, Long companyId);

	public void extendInternship(PersonDTO person) throws ValidationException, Exception;

	List<PersonDTO> findExEmployeeForPayroll(Long companyId, Date dateFrom, Date dateTo);

	public List<PersonDTO> getPersonForEpdtNotification(Long companyId, String responsibilityName);

	public List<PersonDTO> getPayrollPerson(Long companyId, List<Long> listPersonId, List<Long> listBranchId, List<Long> listOrganizationId, List<Long> listJobId,
			List<String> employeeNumbers, Date processDate);*/

	public List<Long> getPersonIdsByCompany(PersonCriteriaDTO criteria, Long companyId);

//	public List<PersonDTO> getPayrollPersonByCriteria(PersonCriteriaDTO criteria, Long companyId, int limit, int offset);

//	public int countPayrollPersonByCriteria(PersonCriteriaDTO criteria, Long companyId);

	public PersonAssignmentDTO getAssignmentByPersonUUID(UUID personUUID);

	public PersonDTO getLastPersonInfo(Long personId, Date effectiveOnDate);

	public void updateUrlPhoto(Long personId, Long companyId, String photoFilePath);

	public PersonDTO getPersonByPersonUUIDForInternship(UUID personUUID, Date effectiveOnDate, Long companyId);

	public PersonDTO getActiveEmployee(String employeeNumber, Date effectiveOnDate);

	public String getLastMajor(Long personId, Long companyId);

	// 14040715241425_CR HCMS â€“ Recruitment_RAH 20141105
	public List<PersonDTO> getPersonAllCompany(String employeeNumber, String employeeName);

	// 14040715241425_CR HCMS â€“ Recruitment_RAH 20141105
	public List<PersonDTO> getPersonAllCompanyRowBounds(String employeeNumber, String employeeName, RowBounds rowBounds);

	// 14071714192817_CR HCMS â€“ Recruitment_LUK 20141209
	public List<PersonDTO> getPersonFilterCompany(Long companyId, String employeeNumber, String employeeName);

	// 14071714192817_CR HCMS â€“ Recruitment_LUK 20141209
	public List<PersonDTO> getPersonFilterCompanyRowBounds(Long companyId, String employeeNumber, String employeeName, RowBounds rowBounds);
	
	public PersonDTO getCwkPersonByPersonUUID(UUID personUUID, Date effectiveDateOn, Long companyId);

	public List<PersonDTO> getPersonByCompanyInvalidDate(PersonCriteriaDTO criteria,
			Long companyId, int offset, int limit);

	public int countPersonByCompanyInvalidDate(PersonCriteriaDTO personCriteriaDTO, Long companyId);

	
	public PersonDTO getPersonByIdInvalidDate(Long personId, Date effectiveOnDate);
	
	public PersonDTO getPersonByIdInvalidDate(Long personId, Date effectiveOnDate,
			Long companyId);

	public List<PersonAssignmentDTO> selectPersonByAssignmentInternship(Long jobId,
			Long organizationId, Long branchId, String roleCode, Long personId,
			Long companyId, String jobGroupCode);
	
	// added for PEM, by Willy
	public List<PersonDTO> findEmployeeByManagerId(List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead, Long companyId, Long managerId, PersonCriteriaDTO personCriteriaDTO, int offset, int limit);

	public int countEmployeeByManagerId(List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead, Long companyId, Long managerId, PersonCriteriaDTO criteria);

	public List<Long> getManagerRootBranch(Long orgId);

	public List<Long> getManagerBranchWithHead(List<Long> inOrganizationId, List<Long> notInOrganizationId, Long companyId, Long managerId);

	List<PersonDTO> findEmployeeByManagerIdSortByEmployeeNumber(
			List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead,
			Long companyId, Long managerId, PersonCriteriaDTO criteria,
			int offset, int limit);
	
	// start added by WLY for phase 2 
	public List<PersonDTO> getPersonByCompanyName(PersonCriteriaDTO criteria,
			Long businessGroupId, int offset, int limit);

	public int countPersonByCompanyName(PersonCriteriaDTO criteria,
			Long businessGroupId);
	// end added by WLY for phase 2 
	
	// start added by JATIS for phase 2 CAM
	public List<PersonDTO> getPersonByJob(Long jobId, Date effectiveOnDate);
	

	public List<PersonDTO> getSubordinatesForPeopleReview(List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead,
			Long companyId, Long managerId, PersonCriteriaDTO criteria);
	
	// end added by JATIS for phase 2 CAM
	
	//16022214360832 :PErformance system pada irrprom. By HS
	//Untuk data yang banyak, penggunaan join lebih bagus dari pada 
	//penggunakan or statement yang banyak
	public int countPersonReadyForActing(PersonCriteriaDTO personCriteriaDTO, Long companyId);
	
	public List<PersonDTO> getPersonReadyForActing(PersonCriteriaDTO criteria, Long companyId, int offset, int limit);
	//END 16022214360832
}

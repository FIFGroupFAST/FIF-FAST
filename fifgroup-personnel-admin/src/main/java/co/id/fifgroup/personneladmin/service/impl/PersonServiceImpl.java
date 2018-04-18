package co.id.fifgroup.personneladmin.service.impl;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.constant.ActionType;
import co.id.fifgroup.personneladmin.constant.AssignmentStatus;
import co.id.fifgroup.personneladmin.constant.DocumentType;
import co.id.fifgroup.personneladmin.constant.EmployeeFlag;
import co.id.fifgroup.personneladmin.constant.EmploymentCategory;
import co.id.fifgroup.personneladmin.constant.EventType;
import co.id.fifgroup.personneladmin.constant.ExitClearanceStatus;
import co.id.fifgroup.personneladmin.constant.HousingAllowance;
import co.id.fifgroup.personneladmin.constant.PeopleSource;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.constant.ProbationStatus;
import co.id.fifgroup.personneladmin.constant.ReferencePersonnelAdministration;
import co.id.fifgroup.personneladmin.constant.TransferBy;
import co.id.fifgroup.personneladmin.dto.AccountInformationDTO;
import co.id.fifgroup.personneladmin.dto.AddressDTO;
import co.id.fifgroup.personneladmin.dto.AwardDTO;
import co.id.fifgroup.personneladmin.dto.BankInformationDTO;
import co.id.fifgroup.personneladmin.dto.ContactsDTO;
import co.id.fifgroup.personneladmin.dto.DocumentDTO;
import co.id.fifgroup.personneladmin.dto.HousingAllowanceDTO;
import co.id.fifgroup.personneladmin.dto.OtherInfoDTO;
import co.id.fifgroup.personneladmin.dto.PeopleAffcoDTO;
import co.id.fifgroup.personneladmin.dto.PeopleTypeDTO;
import co.id.fifgroup.personneladmin.dto.PerformanceReviewDTO;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.dto.PersonMainDTO;
import co.id.fifgroup.personneladmin.dto.PersonalInformationDTO;
import co.id.fifgroup.personneladmin.dto.PrimaryAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.ProbationReviewDTO;
import co.id.fifgroup.personneladmin.dto.SecondaryAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.VitalStatisticDTO;
/*import co.id.fifgroup.personneladmin.service.AccountInformationService;
import co.id.fifgroup.personneladmin.service.AddressService;
import co.id.fifgroup.personneladmin.service.AwardInformationService;
import co.id.fifgroup.personneladmin.service.BankInformationService;
import co.id.fifgroup.personneladmin.service.CatisService;
import co.id.fifgroup.personneladmin.service.CommunicationService;*/
import co.id.fifgroup.personneladmin.service.ContactService;
import co.id.fifgroup.personneladmin.service.DocumentService;
/*import co.id.fifgroup.personneladmin.service.EducationService;
import co.id.fifgroup.personneladmin.service.ExitClearanceService;
import co.id.fifgroup.personneladmin.service.HousingAllowanceService;*/
import co.id.fifgroup.personneladmin.service.InterfaceBufferService;
//import co.id.fifgroup.personneladmin.service.OtherInfoPersonnelService;
import co.id.fifgroup.personneladmin.service.PeopleHistoryService;
import co.id.fifgroup.personneladmin.service.PeopleTypeService;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.service.PersonalInformationService;
import co.id.fifgroup.personneladmin.service.PrimaryAssignmentService;
import co.id.fifgroup.personneladmin.service.RoleService;
/*import co.id.fifgroup.personneladmin.service.SecondaryAssignmentService;
import co.id.fifgroup.personneladmin.service.VitalStatisticService;
import co.id.fifgroup.personneladmin.service.WorkingEquipmentService;
import co.id.fifgroup.personneladmin.service.WorkingExperienceService;*/
import co.id.fifgroup.personneladmin.util.FileUtil;
import co.id.fifgroup.personneladmin.util.SupervisorUtil;
/*import co.id.fifgroup.transfer.dto.TransferDTO;*/
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.service.GradeSetupService;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LocationSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.SequenceGeneratorUtilService;
import co.id.fifgroup.core.constant.ContactType;
import co.id.fifgroup.core.constant.DocumentSource;
import co.id.fifgroup.core.constant.Reason;
import co.id.fifgroup.core.constant.TerminationReason;
import co.id.fifgroup.core.dto.BenefitEntitlementDTO;
import co.id.fifgroup.core.dto.LeaveEntitlementDTO;
import co.id.fifgroup.core.dto.MppJournalTransactionDTO;
import co.id.fifgroup.core.dto.PersonTransferWithinGroupDTO;
import co.id.fifgroup.core.security.SecurityProfile;
/*import co.id.fifgroup.core.service.BenefitEntitlementServiceAdapter;
import co.id.fifgroup.core.service.DisciplinaryLetterServiceAdapter;
import co.id.fifgroup.core.service.FlagBoardingAutomaticService;
import co.id.fifgroup.core.service.IdpServiceAdapter;
import co.id.fifgroup.core.service.IrregularPromotionServiceAdapter;
import co.id.fifgroup.core.service.LeaveEntitlementServiceAdapter;
import co.id.fifgroup.core.service.LoanServiceAdapter;
import co.id.fifgroup.core.service.MppServiceAdapter;*/
import co.id.fifgroup.core.service.PersonServiceAdapter;
/*import co.id.fifgroup.core.service.RecruitmentServiceAdapter;
import co.id.fifgroup.core.service.SalaryServiceAdapter;*/
import co.id.fifgroup.core.service.SecurityService;
/*import co.id.fifgroup.core.service.TerminationServiceAdapter;
import co.id.fifgroup.core.service.TransferServiceAdapter;*/
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.notification.constant.MessageType;
import co.id.fifgroup.notification.domain.Message;
import co.id.fifgroup.notification.domain.NotificationMessage;
import co.id.fifgroup.notification.domain.TemplateMessage;
import co.id.fifgroup.notification.manager.NotificationManager;
import co.id.fifgroup.personneladmin.dao.PeopleTypeMapper;
import co.id.fifgroup.personneladmin.dao.PersonMapper;
import co.id.fifgroup.personneladmin.dao.ProbationReviewMapper;
import co.id.fifgroup.personneladmin.domain.Communication;
import co.id.fifgroup.personneladmin.domain.CommunicationExample;
import co.id.fifgroup.personneladmin.domain.Contact;
import co.id.fifgroup.personneladmin.domain.Document;
import co.id.fifgroup.personneladmin.domain.DocumentExample;
import co.id.fifgroup.personneladmin.domain.Education;
import co.id.fifgroup.personneladmin.domain.ExitClearance;
import co.id.fifgroup.personneladmin.domain.PeopleHistory;
import co.id.fifgroup.personneladmin.domain.Person;
import co.id.fifgroup.personneladmin.domain.PersonalInformation;
import co.id.fifgroup.personneladmin.domain.PersonalInformationExample;
import co.id.fifgroup.personneladmin.domain.PrimaryAssignment;
import co.id.fifgroup.personneladmin.domain.ProbationReview;
import co.id.fifgroup.personneladmin.domain.ProbationReviewExample;
import co.id.fifgroup.personneladmin.domain.WorkingExperience;
import co.id.fifgroup.personneladmin.finder.PeopleTypeFinder;
import co.id.fifgroup.personneladmin.finder.PersonFinder;
/*import co.id.fifgroup.personneladmin.validation.EndInternshipValidator;
import co.id.fifgroup.personneladmin.validation.ExtendInternshipValidator;*/
import co.id.fifgroup.workstructure.dto.GradeDTO;
import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@Service("personService")
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService, PersonServiceAdapter {

	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private SequenceGeneratorUtilService sequenceGeneratorUtilServiceImpl;
	@Autowired
	private CompanyService companyServiceImpl;
	/*@Autowired
	private LeaveEntitlementServiceAdapter leaveEntitlementServiceAdapterImpl;
	@Autowired
	private BenefitEntitlementServiceAdapter benefitEntitlementServiceAdapterImpl;
	@Autowired
	private LookupService lookupServiceImpl;
	@Autowired
	private MppServiceAdapter mppServiceImpl;
	@Autowired
	private RecruitmentServiceAdapter applicantServiceImpl;
	@Autowired
	private TransferServiceAdapter transferTransactionServiceImpl;
	@Autowired
	private IrregularPromotionServiceAdapter irregularPromotionServiceImpl;
	@Autowired
	private LocationSetupService locationSetupServiceImpl;
	@Autowired
	private TerminationServiceAdapter terminationServiceAdapterImpl;
	@Autowired
	private ExitClearanceService exitClearanceServiceImpl;
	@Autowired
	private SalaryServiceAdapter salaryServiceImpl;*/
	@Autowired
	private GradeSetupService gradeSetupServiceImpl;
	@Autowired
	private OrganizationSetupService organizationSetupServiceImpl;
	@Autowired
	private JobSetupService jobSetupServiceImpl;
	/*@Autowired
	private LoanServiceAdapter loanProcessServiceImpl;
	@Autowired
	private DisciplinaryLetterServiceAdapter disciplinaryRequestServiceImpl;
	@Autowired
	private FlagBoardingAutomaticService flagBoardingAutomaticServiceImpl;
	@Autowired
	private IdpServiceAdapter idpEmployeeServiceImpl;*/

	// Historical Data
	@Autowired
	private PeopleTypeService peopleTypeService;
	@Autowired
	private PersonalInformationService personalInformationService;
	@Autowired
	private PrimaryAssignmentService primaryAssignmentService;
	/*@Autowired
	private AccountInformationService accountInformationServiceImpl;
	@Autowired
	private BankInformationService bankInformationServiceImpl;
	@Autowired
	private OtherInfoPersonnelService otherInfoPersonnelServiceImpl;
	@Autowired
	private InterfaceBufferService interfaceBufferServiceImpl;*/

	// Non Historical Data
	@Autowired
	private PersonMapper personMapper;
	/*@Autowired
	private CommunicationService communicationServiceImpl;
	@Autowired
	private AddressService addressServiceImpl;
	@Autowired
	private EducationService educationServiceImpl;*/
	@Autowired
	private ContactService contactServiceImpl;
	@Autowired
	private PeopleHistoryService peopleHistoryServiceImpl;
	/*@Autowired
	private AwardInformationService awardInformationServiceImpl;*/
	@Autowired
	private DocumentService documentServiceImpl;
	/*@Autowired
	private RoleService roleServiceImpl;
	@Autowired
	private VitalStatisticService vitalStatisticServiceImpl;
	@Autowired
	private WorkingEquipmentService workingEquipmentServiceImpl;
	@Autowired
	private WorkingExperienceService workingExperienceServiceImpl;
	@Autowired
	private SecondaryAssignmentService secondaryAssignmentServiceImpl;*/
	@Autowired
	private NotificationManager notificationManager;

	// Finder
	@Autowired
	private PersonFinder personFinder;
	/*@Autowired
	private CatisService catisServiceImpl;*/
	@Autowired
	private PeopleTypeFinder peopleTypeFinder;
	/*@Autowired
	private ProbationReviewMapper probationReviewMapper;
	@Autowired
	private PeopleTypeMapper peopleTypeMapper;*/
	// 15052216463256 - 29/05/2015 | PHI
	/*@Autowired
	private transient HousingAllowanceService housingAllowanceServiceImpl;*/
	// end 15052216463256 - 29/05/2015 | PHI

	// Validation
	/*@Autowired
	private EndInternshipValidator endInternshipValidator;
	@Autowired
	private ExtendInternshipValidator extendInternshipValidator;*/
	/*
	@Autowired
	private TransferDTO transferDTO;*/

	/*@Override
	@Transactional(readOnly = false)
	public void saveNewPerson(PersonMainDTO personMainDTO) throws Exception {
		Long companyId = personMainDTO.getPersonDTO().getCompanyId();

		Person person = new Person();
		person.setPersonUUID(UUID.randomUUID());
		savePerson(person);

		personMainDTO.getPersonDTO().setPersonId(person.getPersonId());
		personMainDTO.getPersonDTO().setPersonUUID(person.getPersonUUID());

		PeopleTypeDTO peopleType = new PeopleTypeDTO();
		peopleType.setPersonId(personMainDTO.getPersonDTO().getPersonId());
		peopleType.setCompanyId(personMainDTO.getPersonDTO().getCompanyId());
		peopleType.setPeopleType(personMainDTO.getPersonDTO().getPeopleType());
		peopleType.setEmploymentCategory(personMainDTO.getPersonDTO().getEmploymentCategory());
		peopleType.setSource(personMainDTO.getPersonDTO().getSource());
		peopleType.setRefId(personMainDTO.getPersonDTO().getRefId());
		peopleType.setAffco(personMainDTO.getPersonDTO().isAffco());
		peopleType.setCanceled(personMainDTO.getPersonDTO().isCanceled());
		peopleType.setEffectiveStartDate(personMainDTO.getPersonDTO().getEffectiveStartDate());
		peopleType.setEffectiveEndDate(personMainDTO.getPersonDTO().getEffectiveEndDate());
		peopleTypeService.savePeopleType(peopleType, null, null);

		if (personMainDTO.getPersonDTO().getPeopleType().equals(co.id.fifgroup.personneladmin.constant.PeopleType.INTERNSHIP.name())
				&& !personMainDTO.getPersonDTO().getInternshipEndDate().equals(DateUtil.MAX_DATE)) {
			PeopleTypeDTO newPeopleTypeHistory = new PeopleTypeDTO();
			newPeopleTypeHistory.setPersonId(personMainDTO.getPersonDTO().getPersonId());
			newPeopleTypeHistory.setCompanyId(personMainDTO.getPersonDTO().getCompanyId());
			newPeopleTypeHistory.setPeopleType(co.id.fifgroup.personneladmin.constant.PeopleType.EX_INTERNSHIP.name());
			newPeopleTypeHistory.setEmploymentCategory(personMainDTO.getPersonDTO().getEmploymentCategory());
			newPeopleTypeHistory.setSource(personMainDTO.getPersonDTO().getSource());
			newPeopleTypeHistory.setRefId(personMainDTO.getPersonDTO().getRefId());
			newPeopleTypeHistory.setAffco(personMainDTO.getPersonDTO().isAffco());
			newPeopleTypeHistory.setCanceled(personMainDTO.getPersonDTO().isCanceled());
			newPeopleTypeHistory.setEffectiveStartDate(DateUtil.add(personMainDTO.getPersonDTO().getInternshipEndDate(), Calendar.DATE, 1));
			newPeopleTypeHistory.setEffectiveEndDate(personMainDTO.getPersonDTO().getEffectiveEndDate());
			peopleTypeService.savePeopleType(newPeopleTypeHistory, peopleType.getEffectiveStartDate(), peopleType.getEffectiveEndDate());
		}

		String employeeNumber = generateEmployeeNumber(personMainDTO.getPersonDTO().getPeopleType());
		personMainDTO.getPersonDTO().setEmployeeNumber(employeeNumber);
		personalInformationService.savePersonalInformation(personMainDTO.getPersonDTO(), null, null);

		if (personMainDTO.getPersonDTO().getInternalMarriedWith() != null) {
			createSpouse(personMainDTO.getPersonDTO());
		}

		personMainDTO.getAccountInformationDTO().setPersonId(personMainDTO.getPersonDTO().getPersonId());
		personMainDTO.getAccountInformationDTO().setCompanyId(personMainDTO.getPersonDTO().getCompanyId());
		accountInformationServiceImpl.saveAccountInformation(employeeNumber, personMainDTO.getAccountInformationDTO(), null, null);

		personMainDTO.getBankInformationDTO().setCompanyId(personMainDTO.getPersonDTO().getCompanyId());
		personMainDTO.getBankInformationDTO().setPersonId(personMainDTO.getPersonDTO().getPersonId());
		bankInformationServiceImpl.saveBankInformation(employeeNumber, personMainDTO.getBankInformationDTO(), null, null);

		communicationServiceImpl.saveCommunication(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getCompanyId(),
				personMainDTO.getListCommunication());

		addressServiceImpl.saveAddress(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getCompanyId(), personMainDTO.getListAddress());

		contactServiceImpl.saveContact(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getCompanyId(), personMainDTO.getContacts()
				.getListContact());

		educationServiceImpl.saveEducation(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getCompanyId(), personMainDTO.getListEducation());

		personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().setPersonId(personMainDTO.getPersonDTO().getPersonId());
		personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().setCompanyId(personMainDTO.getPersonDTO().getCompanyId());
		primaryAssignmentService.savePrimaryAssignment(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO(), null, null);
		if (personMainDTO.getPersonDTO().getPeopleType().equals(co.id.fifgroup.personneladmin.constant.PeopleType.INTERNSHIP.name())
				&& !personMainDTO.getPersonDTO().getInternshipEndDate().equals(DateUtil.MAX_DATE)) {
			PrimaryAssignmentDTO newPrimaryAssignmentDTO = (PrimaryAssignmentDTO) DeepCopy.copy(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO());
			newPrimaryAssignmentDTO.setActionType(ActionType.TERMINATION.name());
			newPrimaryAssignmentDTO.setEffectiveStartDate(DateUtil.add(personMainDTO.getPersonDTO().getInternshipEndDate(), Calendar.DATE, 1));
			newPrimaryAssignmentDTO.setEffectiveEndDate(personMainDTO.getPersonDTO().getEffectiveEndDate());
			primaryAssignmentService.savePrimaryAssignment(newPrimaryAssignmentDTO, personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate(),
					personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveEndDate());
		}

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = null;
		String status = null;
		if (personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getInternship()) {
			eventName = EventType.HIRED_INTERNSHIP.name();
			status = PeopleType.INTERNSHIP.getDescription();
		} else {
			if (personMainDTO.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.PROBATION.name())) {
				eventName = EventType.HIRED_PROBATION.name();
			} else if (personMainDTO.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.PERMANENT.name())) {
				eventName = EventType.HIRED_PERMANENT.name();
			} else if (personMainDTO.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.TRAINEE.name())) {
				eventName = EventType.HIRED_TRAINEE.name();
			}
			status = personMainDTO.getPersonDTO().getEmploymentCategory();
		}

		peopleHistory.setEventName(eventName);
		String description = Labels.getLabel("pea.hiredOnDateWithStatus", new Object[] { personMainDTO.getPersonDTO().getEmployeeNumber(),
				personMainDTO.getPersonDTO().getFullName(), DateUtil.format(personMainDTO.getPersonDTO().getHireDate()), status });
		peopleHistory.setMessageDescription(description);
		peopleHistoryServiceImpl.savePeopleHistory(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getCompanyId(), peopleHistory);

		// 15052216463256 - 29/05/2015 | PHI (Save housing allowance)
		HousingAllowanceDTO housingAllowanceDTO = new HousingAllowanceDTO();
		housingAllowanceDTO.setCompanyId(personMainDTO.getPersonDTO().getCompanyId());
		housingAllowanceDTO.setCreatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : securityServiceImpl
				.getSecurityProfile().getUserId());
		housingAllowanceDTO.setCreationDate(new Date());
		housingAllowanceDTO.setEffectiveStartDate(personMainDTO.getPersonDTO().getEffectiveStartDate());
		housingAllowanceDTO.setEffectiveEndDate(personMainDTO.getPersonDTO().getEffectiveEndDate());
		housingAllowanceDTO.setHouseAllowance(HousingAllowance.NONE.name());
		housingAllowanceDTO.setLastUpdateDate(new Date());
		housingAllowanceDTO.setLastUpdatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : securityServiceImpl
				.getSecurityProfile().getUserId());
		housingAllowanceDTO.setPersonId(person.getPersonId());
		housingAllowanceDTO.setHousingAllowanceId(null);
		housingAllowanceServiceImpl.save(housingAllowanceDTO, null, null);
		// end 15052216463256 - 29/05/2015 | PHI (Save housing allowance)
		
		// createJournal
		if (personMainDTO.getPersonDTO().getPeopleType().equals(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name())) {
			if (personMainDTO.getPersonDTO().getSource() != null) {
				// MppJournalTransactionDTO journalReserved = new
				// MppJournalTransactionDTO();
				// journalReserved.setMppTrnDate(DateUtil.truncate(new Date()));
				// journalReserved.setSource(PeopleSource.RECRUITMENT.name());
				// journalReserved.setSourceTrnId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getRefId());
				// journalReserved.setOrganizationId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId());
				// journalReserved.setJobId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobId());
				// journalReserved.setPersonId(personMainDTO.getPersonDTO().getPersonId());
				// journalReserved.setReserved(1L);
				// journalReserved.setExisting(0L);
				// journalReserved.setRemark("Hire Approval");
				// mppServiceImpl.createJournal(journalReserved, companyId);

				mppServiceImpl.updateMppJournal(personMainDTO.getPersonDTO().getMppTrnId(), personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO()
						.getPrimaryAssignmentDTO().getRefId(), "Hire Approval");

				MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
				journalExisted.setMppTrnDate(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate());
				journalExisted.setSource(PeopleSource.RECRUITMENT.name());
				journalExisted.setSourceTrnId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getRefId());
				journalExisted.setOrganizationId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId());
				journalExisted.setJobId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobId());
				journalExisted.setPersonId(personMainDTO.getPersonDTO().getPersonId());
				journalExisted.setExisting(1L);
				journalExisted.setReserved(-1L);
				journalExisted.setRemark("Hire Date");
				mppServiceImpl.createJournal(journalExisted, companyId);
			} else {
				MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
				journalExisted.setMppTrnDate(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate());
				journalExisted.setSource(PeopleSource.PERSONNEL_ADMIN.name());
				journalExisted.setSourceTrnId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getRefId());
				journalExisted.setOrganizationId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId());
				journalExisted.setJobId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobId());
				journalExisted.setPersonId(personMainDTO.getPersonDTO().getPersonId());
				journalExisted.setExisting(1L);
				journalExisted.setReserved(0L);
				journalExisted.setRemark("Hire Date");
				mppServiceImpl.createJournal(journalExisted, companyId);

				// create salary
				OrganizationDTO branch = organizationSetupServiceImpl.getBranch(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId(),
						DateUtil.truncate(personMainDTO.getPersonDTO().getEffectiveStartDate()), personMainDTO.getPersonDTO().getCompanyId());
				BigDecimal minSalary = gradeSetupServiceImpl.getMinSalaryByGradeIdAndBranchId(personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getGradeId(),
						branch != null ? branch.getId() : null, DateUtil.truncate(personMainDTO.getPersonDTO().getEffectiveStartDate()));
				if (minSalary.compareTo(BigDecimal.ZERO) > 0) {
					salaryServiceImpl.createNewSalaryWithoutValidation(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getCompanyId(),
							personMainDTO.getPersonDTO().getEffectiveStartDate(), minSalary, "PERSONEEL_ADMINISTRATION");
				}
			}
		}

		// interfacing to other system if people type employee
		if (personMainDTO.getPersonDTO().getPeopleType().equals(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name())) {

			// insert benefit entitlement source
			benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO()
					.getPrimaryAssignmentDTO().getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(), Reason.NEW_HIRE.name(), personMainDTO.getPersonDTO()
					.getCompanyId(), personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getAssignmentId());

			// insert leave entitlement source
			leaveEntitlementServiceAdapterImpl.insertEntitlementSource(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getPrimaryAssignmentDTO()
					.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(), Reason.NEW_HIRE.name(), personMainDTO.getPersonDTO().getCompanyId());

			Date effectiveDate = personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate();
			String employeePhoneNumber = getEmployeePhoneNumberActive(personMainDTO.getListCommunication(), effectiveDate);
			interfaceBufferServiceImpl.insertInterfaceBuffer(personMainDTO.getPersonDTO().getPersonId(), personMainDTO.getPersonDTO().getEmployeeNumber(), personMainDTO
					.getPersonDTO().getFullName(), personMainDTO.getPersonDTO().getEmploymentCategory(), personMainDTO.getPersonDTO().getPrimaryAssignmentDTO()
					.getEffectiveStartDate(), employeePhoneNumber, personMainDTO.getPersonDTO().getPrimaryAssignmentDTO().getJobId(), personMainDTO.getPersonDTO()
					.getPrimaryAssignmentDTO().getOrganizationId(), null, true, personMainDTO.getPersonDTO().getCompanyId(), EmployeeFlag.NEW.getDescription());
		}
	}*/

	public String generateEmployeeNumber(String peopleType) {
		Date today = DateUtil.truncate(new Date());
		String employeeNumber = null;
		CompanyExample example = new CompanyExample();
		example.createCriteria().andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId()).andEffectiveStartDateLessThanOrEqualTo(today)
				.andEffectiveEndDateGreaterThanOrEqualTo(today);
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
		if (listCompany.size() > 0) {
			Company company = listCompany.get(0);
			if (peopleType.equals(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name())) {
				employeeNumber = sequenceGeneratorUtilServiceImpl.getSequenceGeneratorFormat(company.getGroupId(), company.getCompanyId(),
						ReferencePersonnelAdministration.SEQUENCE_EMPLOYEE_NUMBER.getReference());
			} else if (peopleType.equals(co.id.fifgroup.personneladmin.constant.PeopleType.CWK.name())) {
				employeeNumber = sequenceGeneratorUtilServiceImpl.getSequenceGeneratorFormat(company.getGroupId(), company.getCompanyId(),
						ReferencePersonnelAdministration.SEQUENCE_CWK_NUMBER.getReference());
			} else if (peopleType.equals(co.id.fifgroup.personneladmin.constant.PeopleType.INTERNSHIP.name())) {
				employeeNumber = sequenceGeneratorUtilServiceImpl.getSequenceGeneratorFormat(company.getGroupId(), company.getCompanyId(),
						ReferencePersonnelAdministration.SEQUENCE_INTERNSHIP_NUMBER.getReference());
			}

		}
		return employeeNumber;
	}

	@Override
	@Transactional(readOnly = false)
	public Long savePerson(Person person) {
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();

		person.setCreatedBy(createdBy);
		person.setCreationDate(createdDate);
		person.setLastUpdatedBy(createdBy);
		person.setLastUpdateDate(createdDate);
		personMapper.insert(person);
		return person.getPersonId();
	}

	@Override
	public List<PersonDTO> getPersonByBusinessGroup(PersonCriteriaDTO criteria, Long groupId) {
		return personFinder.selectPersonByBusinessGroup(criteria, groupId);
	}

	@Override
	public List<PersonDTO> getPersonByBusinessGroup(PersonCriteriaDTO criteria, Long groupId, int offset, int limit) {
		return personFinder.selectPersonByBusinessGroup(criteria, groupId, new RowBounds(offset, limit));
	}

	@Override
	public List<PersonDTO> getPersonByCompany(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.selectPersonByCompany(criteria, companyId);
	}
	
	@Override
	public List<PersonDTO> getPersonByCompanyTemp(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.selectPersonByCompanyTemp(criteria, companyId);
	}

	@Override
	public List<PersonDTO> getPersonByCompanyInquiry(PersonCriteriaDTO criteria, int offset, int limit) {
		return personFinder.selectPersonByCompanyInquiry(criteria, new RowBounds(offset, limit));
	}

	@Override
	public Integer countPersonByCompanyInquiry(PersonCriteriaDTO criteria) {
		return personFinder.countPersonByCompanyInquiry(criteria);
	}

	@Override
	public PersonDTO getPersonById(Long personId, Date effectiveOnDate) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		// criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		criteria.setCompanyId(getCompanyPerson(personId, effectiveOnDate));
		return personFinder.selectPersonById(personId, criteria);
	}
	
	// add By AAA 15031814272238 [HCMS - Tidak Bisa Definitive Status Atas Organisasi NonAktif] 
	@Override
	public PersonDTO getPersonByIdInvalidDate(Long personId, Date effectiveOnDate) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setCompanyId(getCompanyPerson(personId, effectiveOnDate));
		return personFinder.selectPersonByIdInvalidDate(personId, criteria);
	}	
	// end add By AAA 15031814272238 [HCMS - Tidak Bisa Definitive Status Atas Organisasi NonAktif]

	/*
	 * add and remark_14060515085266_Tidak bisa entri overtime karena Transfer
	 * group by_RIM
	 */
	@Override
	public PersonDTO getPersonByIdForOvertime(Long personId, Date effectiveOnDate) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setCompanyId(personFinder.selectCompanyPerson(personId, effectiveOnDate));
		return personFinder.selectPersonById(personId, criteria);
	}

	/*
	 * end add 14060515085266_Tidak bisa entri overtime karena Transfer group
	 * by_RIM
	 */

	@Override
	public PersonDTO getPersonByPersonUUID(UUID personUUID, Date effectiveOnDate) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		return personFinder.selectPersonByPersonUUID(personUUID, criteria);
	}

	@Override
	public List<PersonAssignmentDTO> selectPersonByAssignment(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId,
			String jobGroupCode) {
		return personFinder.selectPersonByAssignment(jobId, organizationId, branchId, roleCode, personId, companyId, jobGroupCode);
	}

	@Override
	public PersonAssignmentDTO selectAssignmentByPersonId(Long personId, Long companyId) {
		return personFinder.selectAssignmentByPersonId(personId, companyId);
	}

	@Override
	public List<KeyValue> getPersonByCompany(String employeeNumber, String employeeName, Long companyId, int limit, int offset) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEmployeeNumber(employeeNumber);
		criteria.setFullName(employeeName);
		criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());
		return personFinder.selectPersonByCompanyKeyValue(criteria, companyId, new RowBounds(offset, limit));
	}
	
	@Override
	public int countPersonByCompany(String employeeNumber, String employeeName, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEmployeeNumber(employeeNumber);
		criteria.setFullName(employeeName);
		criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());
		return personFinder.countPersonByCompany(criteria, companyId);
	}

	@Override
	public List<PersonDTO> getPersonBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId,
			Long companyId) {
		return personFinder.selectBySecurityFilter(inOrganizationId, notInOrganizationId, gradeExclusions, personId, companyId);
	}

	/*@Override
	public List<Communication> getPersonCommunicationByPersonId(Long personId, Long companyId) {
		CommunicationExample example = new CommunicationExample();
		example.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId);
		return communicationServiceImpl.selectByExample(example);
	}*/

	@Override
	public List<PersonDTO> getPersonBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId,
			Long companyId, String employeeNumber, String employeeName, int limit, int offset) {
		return personFinder.selectBySecurityFilterWitRowBounds(inOrganizationId, notInOrganizationId, gradeExclusions, personId, companyId, employeeNumber, employeeName,
				new RowBounds(offset, limit));
	}

	@Override
	public int countBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId, Long companyId,
			String employeeNumber, String employeeName) {
		return personFinder.countBySecurityFilter(inOrganizationId, notInOrganizationId, gradeExclusions, personId, companyId, employeeNumber, employeeName);
	}

	@Override
	public List<PersonDTO> selectPersonByCompany(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.selectPersonByCompany(criteria, companyId);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDocument(Long personId, Long companyId, String employeeNumber, List<DocumentDTO> listDocuments) {
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		for (DocumentDTO documentDTO : listDocuments) {
			if (documentDTO.getMediaDocument() != null && (documentDTO.isEditable() || !DocumentSource.PERSONNEL_ADMIN.name().equalsIgnoreCase(documentDTO.getSource()))) {
				if (!isNullOrEmpty(documentDTO.getFilePath())) {
					FileUtil.deleteFile(documentDTO.getFilePath());
				}
				documentDTO.getMediaDocument().setEmployeeNumber(employeeNumber);
				String filePath = FileUtil.doUpload(documentDTO.getMediaDocument());
				documentDTO.setFilePath(filePath);
				documentDTO.setFileName(documentDTO.getMediaDocument().getFileName());
			}

			documentDTO.setLastUpdatedBy(createdBy);
			documentDTO.setLastUpdateDate(createdDate);

			if (documentDTO.getDocumentId() != null) {
				documentServiceImpl.update(documentDTO);
			} else {
				DocumentExample documentExample = new DocumentExample();
				documentExample.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId).andDocumentTypeEqualTo(documentDTO.getDocumentType());
				List<Document> listDocument = documentServiceImpl.selectByExample(documentExample);
				if (listDocument.size() > 0 && !listDocument.get(0).isEditable()) {
					documentServiceImpl.updateByExample(documentDTO, documentExample);
				} else {
					documentDTO.setPersonId(personId);
					documentDTO.setCompanyId(companyId);
					documentDTO.setCreatedBy(createdBy);
					documentDTO.setCreationDate(createdDate);
					documentServiceImpl.save(documentDTO);
				}
			}
		}
	}

	/*@Override
	@Transactional(readOnly = false)
	public void saveSecondaryAssignment(Long personId, Long companyId, SecondaryAssignmentDTO secondaryAssignmentDTO) throws Exception {
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();

		secondaryAssignmentDTO.setPersonId(personId);
		secondaryAssignmentDTO.setCompanyId(companyId);
		secondaryAssignmentDTO.setLastUpdatedBy(createdBy);
		secondaryAssignmentDTO.setLastUpdateDate(createdDate);
		if (secondaryAssignmentDTO.getAssignmentId() != null) {
			secondaryAssignmentServiceImpl.update(secondaryAssignmentDTO);
		} else {
			secondaryAssignmentDTO.setCreatedBy(createdBy);
			secondaryAssignmentDTO.setCreationDate(createdDate);
			secondaryAssignmentServiceImpl.save(secondaryAssignmentDTO);
		}

		updateLastUpdatePerson(personId);
		PersonDTO personDTO = getPersonalData(personId, companyId, secondaryAssignmentDTO.getStartDate());
		sendNotificationUpdateData(personDTO);

		// interfacing to other system
		if (personDTO.getPeopleType().equalsIgnoreCase(PeopleType.EMPLOYEE.name()) && secondaryAssignmentDTO.isFifappsAccess()) {
			Date effectiveDate = secondaryAssignmentDTO.getStartDate();
			String employeePhoneNumber = getEmployeePhoneNumberActive(
					communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(secondaryAssignmentDTO.getPersonId(), secondaryAssignmentDTO.getCompanyId()),
					effectiveDate);
			interfaceBufferServiceImpl.insertInterfaceBuffer(personDTO.getPersonId(), personDTO.getEmployeeNumber(), personDTO.getFullName(),
					personDTO.getEmploymentCategory(), secondaryAssignmentDTO.getStartDate(), employeePhoneNumber, secondaryAssignmentDTO.getJobId(),
					secondaryAssignmentDTO.getOrganizationId(), null, false, personDTO.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());
		}
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void terminateInternship(PersonDTO personDTO) throws ValidationException, Exception {
		endInternshipValidator.validate(personDTO);

		PeopleTypeDTO peopleTypeBefore = peopleTypeService.getPeopleTypeByEffectiveOnDate(personDTO.getPersonId(), personDTO.getCompanyId(),
				personDTO.getInternshipEndDate());

		if (peopleTypeBefore != null) {
			PeopleTypeDTO peopleType = new PeopleTypeDTO();
			peopleType.setPersonId(personDTO.getPersonId());
			peopleType.setCompanyId(personDTO.getCompanyId());
			peopleType.setPeopleType(co.id.fifgroup.personneladmin.constant.PeopleType.EX_INTERNSHIP.name());
			peopleType.setEmploymentCategory(null);
			peopleType.setSource(null);
			peopleType.setRefId(null);
			peopleType.setAffco(peopleTypeBefore.getAffco());
			peopleType.setCanceled(false);
			peopleType.setEffectiveStartDate(DateUtil.truncate(personDTO.getInternshipEndDate()));
			peopleType.setEffectiveEndDate(DateUtil.MAX_DATE);
			peopleTypeService.savePeopleType(peopleType, peopleTypeBefore.getEffectiveStartDate(), peopleTypeBefore.getEffectiveEndDate());

			PrimaryAssignmentDTO primaryAssignmentBefore = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(personDTO.getPersonId(),
					personDTO.getCompanyId(), DateUtil.truncate(personDTO.getInternshipEndDate()));
			PrimaryAssignmentDTO primaryAssignmentDTO = personDTO.getPrimaryAssignmentDTO();
			primaryAssignmentDTO.setPersonId(personDTO.getPersonId());
			primaryAssignmentDTO.setCompanyId(personDTO.getCompanyId());
			primaryAssignmentDTO.setActionType(ActionType.TERMINATION.name());
			primaryAssignmentDTO.setEffectiveStartDate(personDTO.getInternshipEndDate());
			primaryAssignmentDTO.setEffectiveEndDate(DateUtil.MAX_DATE);
			primaryAssignmentService.savePrimaryAssignment(primaryAssignmentDTO, primaryAssignmentBefore.getEffectiveStartDate(),
					primaryAssignmentBefore.getEffectiveEndDate());

			PeopleHistory peopleHistory = new PeopleHistory();
			peopleHistory.setEventName(EventType.TERMINATION_INTERNSHIP.name());
			String messageDescription = Labels.getLabel(
					"pea.terminatedOn",
					new Object[] { personDTO.getEmployeeNumber(), personDTO.getFullName(), PeopleType.INTERNSHIP.getDescription(),
							FormatDateUI.formatDate(personDTO.getInternshipEndDate()) });
			peopleHistory.setMessageDescription(messageDescription);
			peopleHistory.setDocumentType(null);
			peopleHistory.setDocumentPath(null);
			peopleHistoryServiceImpl.savePeopleHistory(personDTO.getPersonId(), personDTO.getCompanyId(), peopleHistory);

			updateLastUpdatePerson(personDTO.getPersonId());
		}

	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void hiredInternshipToEmployee(PersonMainDTO personMain) throws Exception {
		Date currentDate = DateUtil.truncate(new Date());

		PeopleTypeDTO peopleTypeBefore = peopleTypeService.getPeopleTypeByEffectiveOnDate(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO()
				.getCompanyId(), currentDate);

		PeopleTypeDTO peopleType = new PeopleTypeDTO();
		peopleType.setPersonId(personMain.getPersonDTO().getPersonId());
		peopleType.setCompanyId(personMain.getPersonDTO().getCompanyId());
		peopleType.setPeopleType(personMain.getPersonDTO().getPeopleType());
		peopleType.setEmploymentCategory(personMain.getPersonDTO().getEmploymentCategory());
		peopleType.setSource(personMain.getPersonDTO().getSource());
		peopleType.setRefId(personMain.getPersonDTO().getRefId());
		peopleType.setAffco(personMain.getPersonDTO().isAffco());
		peopleType.setCanceled(personMain.getPersonDTO().isCanceled());
		peopleType.setEffectiveStartDate(personMain.getPersonDTO().getEffectiveStartDate());
		peopleType.setEffectiveEndDate(personMain.getPersonDTO().getEffectiveEndDate());
		peopleTypeService.savePeopleType(peopleType, peopleTypeBefore.getEffectiveStartDate(), peopleTypeBefore.getEffectiveEndDate());

		PersonalInformationDTO personalInformationBefore = personalInformationService.getPersonalInformationByEffectiveOnDate(personMain.getPersonDTO().getPersonId(),
				personMain.getPersonDTO().getCompanyId(), null, currentDate);
		if (personalInformationService.hasFuture(personalInformationBefore)) {
			personalInformationService.deleteFuturePersonalInformation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId());
		}

		String employeeNumber = generateEmployeeNumber(PeopleType.EMPLOYEE.name());
		personMain.getPersonDTO().setEmployeeNumber(employeeNumber);
		personalInformationService.savePersonalInformation(personMain.getPersonDTO(), personalInformationBefore.getEffectiveStartDate(),
				personalInformationBefore.getEffectiveEndDate());

		AccountInformationDTO accountInfoBefore = accountInformationServiceImpl.getAccountInformationByEffectiveOnDate(personMain.getPersonDTO().getPersonId(),
				personMain.getPersonDTO().getCompanyId(), currentDate);

		if (accountInformationServiceImpl.hasFuture(accountInfoBefore)) {
			accountInformationServiceImpl.deleteFutureAccountInformation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId());
		}

		personMain.getAccountInformationDTO().setPersonId(personMain.getPersonDTO().getPersonId());
		personMain.getAccountInformationDTO().setCompanyId(personMain.getPersonDTO().getCompanyId());
		accountInformationServiceImpl.saveAccountInformation(employeeNumber, personMain.getAccountInformationDTO(), accountInfoBefore.getEffectiveStartDate(),
				accountInfoBefore.getEffectiveEndDate());

		BankInformationDTO bankInfoBefore = bankInformationServiceImpl.getBankInformationByEffectiveOnDate(personMain.getPersonDTO().getPersonId(), personMain
				.getPersonDTO().getCompanyId(), currentDate);

		if (bankInformationServiceImpl.hasFuture(bankInfoBefore)) {
			bankInformationServiceImpl.deleteFutureBankInformation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId());
		}

		personMain.getBankInformationDTO().setCompanyId(personMain.getPersonDTO().getCompanyId());
		personMain.getBankInformationDTO().setPersonId(personMain.getPersonDTO().getPersonId());
		bankInformationServiceImpl.saveBankInformation(employeeNumber, personMain.getBankInformationDTO(), bankInfoBefore.getEffectiveStartDate(),
				bankInfoBefore.getEffectiveEndDate());

		communicationServiceImpl.saveCommunication(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getListCommunication());

		addressServiceImpl.saveAddress(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getListAddress());

		contactServiceImpl.saveContact(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getContacts().getListContact());

		educationServiceImpl.saveEducation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getListEducation());

		PrimaryAssignmentDTO primaryAssignmentBefore = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(personMain.getPersonDTO().getPersonId(), personMain
				.getPersonDTO().getCompanyId(), currentDate);

		if (primaryAssignmentService.hasFuture(primaryAssignmentBefore)) {
			primaryAssignmentService.deleteFuturePrimaryAssignment(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId());
		}

		personMain.getPersonDTO().getPrimaryAssignmentDTO().setPersonId(personMain.getPersonDTO().getPersonId());
		personMain.getPersonDTO().getPrimaryAssignmentDTO().setCompanyId(personMain.getPersonDTO().getCompanyId());
		primaryAssignmentService.savePrimaryAssignment(personMain.getPersonDTO().getPrimaryAssignmentDTO(), primaryAssignmentBefore.getEffectiveStartDate(),
				primaryAssignmentBefore.getEffectiveEndDate());

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = null;
		if (personMain.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.PROBATION.name())) {
			eventName = EventType.HIRED_PROBATION.name();
		} else if (personMain.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.PERMANENT.name())) {
			eventName = EventType.HIRED_PERMANENT.name();
		}
		peopleHistory.setEventName(eventName);
		String description = Labels.getLabel("pea.hiredOnDateWithStatus", new Object[] { personMain.getPersonDTO().getEmployeeNumber(),
				personMain.getPersonDTO().getFullName(), DateUtil.format(personMain.getPersonDTO().getHireDate()), personMain.getPersonDTO().getEmploymentCategory() });
		peopleHistory.setMessageDescription(description);
		peopleHistoryServiceImpl.savePeopleHistory(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), peopleHistory);

		// 15052216463256 - 29/05/2015 | PHI (Save housing allowance)
		HousingAllowanceDTO housingAllowanceDTO = new HousingAllowanceDTO();
		housingAllowanceDTO.setCompanyId(personMain.getPersonDTO()
				.getCompanyId());
		housingAllowanceDTO.setCreatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : securityServiceImpl
				.getSecurityProfile().getUserId());
		housingAllowanceDTO.setCreationDate(new Date());
		housingAllowanceDTO.setEffectiveStartDate(personMain.getPersonDTO()
				.getEffectiveStartDate());
		housingAllowanceDTO.setEffectiveEndDate(personMain.getPersonDTO()
				.getEffectiveEndDate());
		housingAllowanceDTO.setHouseAllowance(HousingAllowance.NONE.name());
		housingAllowanceDTO.setLastUpdateDate(new Date());
		housingAllowanceDTO.setLastUpdatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : securityServiceImpl
				.getSecurityProfile().getUserId());
		housingAllowanceDTO
				.setPersonId(personMain.getPersonDTO().getPersonId());
		housingAllowanceDTO.setHousingAllowanceId(null);
		housingAllowanceServiceImpl.save(housingAllowanceDTO, null, null);
		// end 15052216463256 - 29/05/2015 | PHI (Save housing allowance)

		// insert benefit entitlement source
		benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getPrimaryAssignmentDTO()
				.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(), Reason.NEW_HIRE.name(), personMain.getPersonDTO().getCompanyId(), personMain
				.getPersonDTO().getPrimaryAssignmentDTO().getAssignmentId());

		// insert leave entitlement source
		leaveEntitlementServiceAdapterImpl.insertEntitlementSource(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getPrimaryAssignmentDTO()
				.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(), Reason.NEW_HIRE.name(), personMain.getPersonDTO().getCompanyId());

		// create journal
		MppJournalTransactionDTO journalReserved = new MppJournalTransactionDTO();
		journalReserved.setMppTrnDate(DateUtil.truncate(new Date()));
		journalReserved.setSource(PeopleSource.PERSONNEL_ADMIN.name());
		journalReserved.setSourceTrnId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getRefId());
		journalReserved.setOrganizationId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId());
		journalReserved.setJobId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getJobId());
		journalReserved.setPersonId(personMain.getPersonDTO().getPersonId());
		journalReserved.setReserved(1L);
		journalReserved.setExisting(0L);
		journalReserved.setRemark("Hire Approval");
		mppServiceImpl.createJournal(journalReserved, personMain.getPersonDTO().getCompanyId());

		MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
		journalExisted.setMppTrnDate(personMain.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate());
		journalExisted.setSource(PeopleSource.PERSONNEL_ADMIN.name());
		journalExisted.setSourceTrnId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getRefId());
		journalExisted.setOrganizationId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId());
		journalExisted.setJobId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getJobId());
		journalExisted.setPersonId(personMain.getPersonDTO().getPersonId());
		journalExisted.setReserved(-1L);
		journalExisted.setExisting(1L);
		journalExisted.setRemark("Hire Date");
		mppServiceImpl.createJournal(journalExisted, personMain.getPersonDTO().getCompanyId());

		OrganizationDTO branch = organizationSetupServiceImpl.getBranch(personMain.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId(),
				DateUtil.truncate(personMain.getPersonDTO().getEffectiveStartDate()), personMain.getPersonDTO().getCompanyId());
		BigDecimal minSalary = gradeSetupServiceImpl.getMinSalaryByGradeIdAndBranchId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getGradeId(),
				branch != null ? branch.getId() : null, DateUtil.truncate(personMain.getPersonDTO().getEffectiveStartDate()));
		if (minSalary.compareTo(BigDecimal.ZERO) > 0) {
			salaryServiceImpl.createNewSalaryWithoutValidation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain
					.getPersonDTO().getEffectiveStartDate(), minSalary, "PERSONEEL_ADMINISTRATION");
		}

		// interfacing ke fifapps dan ldap
		Date effectiveDate = personMain.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate();
		String employeePhoneNumber = getEmployeePhoneNumberActive(personMain.getListCommunication(), effectiveDate);
		interfaceBufferServiceImpl.insertInterfaceBuffer(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getEmployeeNumber(), personMain
				.getPersonDTO().getFullName(), personMain.getPersonDTO().getEmploymentCategory(), personMain.getPersonDTO().getPrimaryAssignmentDTO()
				.getEffectiveStartDate(), employeePhoneNumber, personMain.getPersonDTO().getPrimaryAssignmentDTO().getJobId(), personMain.getPersonDTO()
				.getPrimaryAssignmentDTO().getOrganizationId(), null, true, personMain.getPersonDTO().getCompanyId(), EmployeeFlag.NEW.getDescription());

		updateLastUpdatePerson(personMain.getPersonDTO().getPersonId());
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void changeTraineeToPermanent(PersonDTO person) throws Exception {
		PeopleTypeDTO peopleTypeBefore = peopleTypeService.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), person.getTrainingEndDate());

		if (peopleTypeBefore != null) {
			PeopleTypeDTO peopleType = new PeopleTypeDTO();
			peopleType.setPersonId(person.getPersonId());
			peopleType.setCompanyId(person.getCompanyId());
			peopleType.setPeopleType(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name());
			peopleType.setEmploymentCategory(EmploymentCategory.PERMANENT.name());
			peopleType.setSource(null);
			peopleType.setRefId(null);
			peopleType.setAffco(false);
			peopleType.setCanceled(false);
			peopleType.setEffectiveStartDate(person.getTrainingEndDate());
			peopleType.setEffectiveEndDate(DateUtil.MAX_DATE);
			peopleTypeService.savePeopleType(peopleType, peopleTypeBefore.getEffectiveStartDate(), peopleTypeBefore.getEffectiveEndDate());

			secondaryAssignmentServiceImpl.updateEndDateSecondaryAssignment(person.getPersonId(), person.getCompanyId(), person.getTrainingEndDate());

			PeopleHistory peopleHistory = new PeopleHistory();
			peopleHistory.setEventName(EventType.HIRED_PERMANENT.name());
			String description = Labels.getLabel("pea.hiredOnDateWithStatus",
					new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(person.getTrainingEndDate()), EmploymentCategory.PERMANENT.name() });
			peopleHistory.setMessageDescription(description);
			peopleHistoryServiceImpl.savePeopleHistory(person.getPersonId(), person.getCompanyId(), peopleHistory);

			updateLastUpdatePerson(person.getPersonId());

			// interfacing to other system
			Date effectiveDate = person.getPrimaryAssignmentDTO().getEffectiveStartDate();
			String employeePhoneNumber = getEmployeePhoneNumberActive(
					communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId()), effectiveDate);
			interfaceBufferServiceImpl.insertInterfaceBuffer(person.getPersonId(), person.getEmployeeNumber(), person.getFullName(), person.getEmploymentCategory(),
					person.getPrimaryAssignmentDTO().getEffectiveStartDate(), employeePhoneNumber, person.getPrimaryAssignmentDTO().getJobId(), person
							.getPrimaryAssignmentDTO().getOrganizationId(), null, false, person.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());
		}
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void saveTransferWithinGroup(PersonDTO person, Long refId, Date terminationDate) throws Exception {
		Long companyOrigin = new Long(person.getCompanyId());
		Long companyDestination = securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId();
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date creationDate = new Date();

		PeopleTypeDTO peopleType = new PeopleTypeDTO();
		peopleType.setPersonId(person.getPersonId());
		peopleType.setCompanyId(companyDestination);
		peopleType.setPeopleType(PeopleType.EMPLOYEE.name());
		peopleType.setEmploymentCategory(person.getEmploymentCategory());
		peopleType.setSource(PeopleSource.TERMINATION.name());
		peopleType.setRefId(refId);
		peopleType.setAffco(false);
		peopleType.setCanceled(false);
		peopleType.setEffectiveStartDate(terminationDate);
		peopleType.setEffectiveEndDate(DateUtil.MAX_DATE);
		peopleTypeService.savePeopleType(peopleType, null, null);

		if (!isNullOrEmpty(person.getHomeBaseCode())) {
			LocationDTO location = locationSetupServiceImpl.findByCode(person.getHomeBaseCode());
			if (location == null) {
				person.setHomeBaseCode(null);
			}
		}
		person.setCompanyId(companyDestination);
		person.setEffectiveStartDate(terminationDate);
		person.setEffectiveEndDate(DateUtil.MAX_DATE);
		personalInformationService.savePersonalInformation(person, null, null);

		AccountInformationDTO accountInfoBefore = accountInformationServiceImpl.getAccountInformationByEffectiveOnDate(person.getPersonId(), companyOrigin,
				terminationDate);

		accountInfoBefore.setCompanyId(companyDestination);
		accountInfoBefore.setEffectiveStartDate(terminationDate);
		accountInformationServiceImpl.saveAccountInformation(person.getEmployeeNumber(), accountInfoBefore, null, null);

		BankInformationDTO bankInfoBefore = bankInformationServiceImpl.getBankInformationByEffectiveOnDate(person.getPersonId(), companyOrigin, terminationDate);

		bankInfoBefore.setCompanyId(companyDestination);
		bankInfoBefore.setEffectiveStartDate(terminationDate);
		bankInformationServiceImpl.saveBankInformation(person.getEmployeeNumber(), bankInfoBefore, null, null);

		List<OtherInfoDTO> otherInfosBefore = otherInfoPersonnelServiceImpl.getOtherInfoByEffectiveOnDate(person.getPersonId(), companyOrigin, terminationDate);
		if (otherInfosBefore.size() > 0) {
			for (OtherInfoDTO otherInfo : otherInfosBefore) {
				otherInfo.setCompanyId(companyDestination);
				otherInfo.setCreatedBy(createdBy);
				otherInfo.setCreationDate(creationDate);
				otherInfo.setLastUpdatedBy(createdBy);
				otherInfo.setLastUpdateDate(creationDate);
			}
			otherInfoPersonnelServiceImpl.save(otherInfosBefore, null, null);
		}

		List<Communication> listCommunications = communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(person.getPersonId(), companyOrigin);
		if (listCommunications.size() > 0) {
			for (Communication communication : listCommunications) {
				communication.setCommunicationId(null);
			}
			communicationServiceImpl.saveCommunication(person.getPersonId(), companyDestination, listCommunications);
		}

		List<AddressDTO> lisAddress = addressServiceImpl.getAddressByPersonIdAndCompanyId(person.getPersonId(), companyOrigin);
		if (lisAddress.size() > 0) {
			for (AddressDTO address : lisAddress) {
				address.setAddressId(null);
			}
			addressServiceImpl.saveAddress(person.getPersonId(), companyDestination, lisAddress);
		}

		VitalStatisticDTO vitalStatistic = vitalStatisticServiceImpl.getVitalStatisticByPersondIdAndCompanyId(person.getPersonId(), companyOrigin);
		if (vitalStatistic != null) {
			vitalStatistic.setVitalStatisticId(null);
			// add TPS untuk handle transfer within group, karena ini selalu false saat validate
			// harusnya true karena sudah divalidate saat entry, sementara transfer within group ini hanya copy
			// merge numpang ticket [15052216463256]  HOUSING - proses Add New People
			vitalStatistic.setValidApparels(true);
			vitalStatisticServiceImpl.saveVitalStatistic(person.getPersonId(), companyDestination, vitalStatistic);
		}

		List<Education> listEducations = educationServiceImpl.getEducationsByPersonIdAndCompanyId(person.getPersonId(), companyOrigin);
		if (listEducations.size() > 0) {
			for (Education education : listEducations) {
				education.setEducationId(null);
			}
			educationServiceImpl.saveEducation(person.getPersonId(), companyDestination, listEducations);
		}

		List<WorkingExperience> listExperiences = workingExperienceServiceImpl.getWorkingExperiencesByPersonIdAndCompanyId(person.getPersonId(), companyOrigin);
		if (listExperiences.size() > 0) {
			for (WorkingExperience experience : listExperiences) {
				experience.setExperienceId(null);
			}
			workingExperienceServiceImpl.saveWorkingExperience(person.getPersonId(), companyDestination, listExperiences);
		}

		List<Contact> listContacts = contactServiceImpl.getContactsByPersonIdAndCompanyId(person.getPersonId(), companyOrigin);
		if (listContacts.size() > 0) {
			for (Contact contact : listContacts) {
				contact.setContactId(null);
			}
			contactServiceImpl.saveContact(person.getPersonId(), companyDestination, listContacts);
		}

		List<AwardDTO> listAwards = awardInformationServiceImpl.getAwardByPersonIdAndCompanyId(person.getPersonId(), companyOrigin);
		for (AwardDTO award : listAwards) {
			award.setJobId(null);
			award.setAwardId(null);
		}
		awardInformationServiceImpl.saveAwardInformation(person.getPersonId(), companyDestination, listAwards, companyOrigin);

		List<DocumentDTO> listDocuments = documentServiceImpl.getDocumentsByPersonIdAndCompanyId(person.getPersonId(), companyOrigin, null);
		if (listDocuments.size() > 0) {
			List<DocumentDTO> newListDocuments = new ArrayList<DocumentDTO>();
			for (DocumentDTO document : listDocuments) {
				document.setDocumentId(null);
				document.setCompanyId(companyDestination);
				newListDocuments.add(document);
			}
			saveDocument(person.getPersonId(), companyDestination, person.getEmployeeNumber(), newListDocuments);
		}

		person.getPrimaryAssignmentDTO().setCompanyId(companyDestination);
		person.getPrimaryAssignmentDTO().setSource(PeopleSource.TERMINATION.name());
		person.getPrimaryAssignmentDTO().setRefId(refId);
		
		 * add by Putut
		 * 
		 * Long dest = brchDestId;
		Long origin  = brchId;
		String permanent = employeeCat;
		String transferBy = primaryAssignmentDTO.getTransferedBy();
		String assignmentStatus =  primaryAssignmentDTO.getAssignmentStatus();
		String transfer = "";
		int companyId  = (int) (long) primaryAssignmentBefore.getCompanyId();
		
		switch(transferBy){
		case "TRANSFERED_BY_OWN" :
			transfer = "By Own";
			break;
		case "TRANSFERED_BY_COMPANY":
			transfer = "By Company";
		break;
		}
	
		 String update = flagBoardingAutomaticServiceImpl.getUpdateFlagBoarding(origin,
				 dest, assignmentStatus,employeeCat,transfer, (int) (long) isKeyjob
				 ,primaryAssignmentBefore.getHousingAllowance(), 0,companyId);
		 
		update = (update == null ? "NONE" : update);
		person.getPrimaryAssignmentDTO().setHousingAllowance(update);
		primaryAssignmentService.savePrimaryAssignment(person.getPrimaryAssignmentDTO(), null, null);

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = null;
		if (person.getEmploymentCategory().equals(EmploymentCategory.PROBATION.name())) {
			eventName = EventType.HIRED_PROBATION.name();
		} else if (person.getEmploymentCategory().equals(EmploymentCategory.PERMANENT.name())) {
			eventName = EventType.HIRED_PERMANENT.name();
		}
		peopleHistory.setEventName(eventName);
		String description = Labels.getLabel("pea.hiredOnDateWithStatus",
				new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(terminationDate), person.getEmploymentCategory() });
		peopleHistory.setMessageDescription(description);
		peopleHistoryServiceImpl.savePeopleHistory(person.getPersonId(), companyDestination, peopleHistory);

		// 15052216463256 - 29/05/2015 | PHI (Save housing allowance)
		HousingAllowanceDTO housingAllowanceDTO = housingAllowanceServiceImpl
				.getHousingAllowanceByEffectiveOnDate(person.getPersonId(),
						person.getCompanyId(), DateUtil.truncate(new Date()));
		Date dateStartBefore = null;
		Date dateEndBefore = null;
		if (housingAllowanceDTO == null) {
			housingAllowanceDTO = new HousingAllowanceDTO();
			housingAllowanceDTO.setCreatedBy(Long.valueOf(securityServiceImpl.getSecurityProfile().getUserId()));
			housingAllowanceDTO.setCreationDate(new Date());
			housingAllowanceDTO.setHouseAllowance(HousingAllowance.NONE.name());
			housingAllowanceDTO.setPersonId(person.getPersonId());
		} else {
			dateStartBefore = housingAllowanceDTO.getEffectiveStartDate();
			dateEndBefore = housingAllowanceDTO.getEffectiveEndDate();
		}
		housingAllowanceDTO.setCompanyId(companyDestination);
		housingAllowanceDTO.setEffectiveEndDate(DateUtil.MAX_DATE);
		housingAllowanceDTO.setEffectiveStartDate(terminationDate);
		housingAllowanceDTO.setLastUpdateDate(new Date());
		housingAllowanceDTO.setLastUpdatedBy(Long.valueOf(securityServiceImpl.getSecurityProfile().getUserId()));
		housingAllowanceDTO.setHousingAllowanceId(null);
		housingAllowanceServiceImpl.save(housingAllowanceDTO, dateStartBefore, dateEndBefore);
		// end 15052216463256 - 29/05/2015 | PHI (Save housing allowance)

		updateLastUpdatePerson(person.getPersonId());

		// insert leave entitlement source
		leaveEntitlementServiceAdapterImpl.insertEntitlementSource(person.getPersonId(), person.getPrimaryAssignmentDTO().getEffectiveStartDate(),
				PeopleSource.PERSONNEL_ADMIN.name(), Reason.TRANSFER_WITHIN_GROUP.name(), person.getCompanyId());

		// insert benefit entitlement source
		benefitEntitlementServiceAdapterImpl.insertEntitlementSource(person.getPersonId(), person.getPrimaryAssignmentDTO().getEffectiveStartDate(),
				PeopleSource.PERSONNEL_ADMIN.name(), Reason.TRANSFER_WITHIN_GROUP.name(), person.getCompanyId(), person.getPrimaryAssignmentDTO().getAssignmentId());

		// create journal
		MppJournalTransactionDTO journalReserved = new MppJournalTransactionDTO();
		journalReserved.setMppTrnDate(DateUtil.truncate(new Date()));
		journalReserved.setSource(PeopleSource.TERMINATION.name());
		journalReserved.setSourceTrnId(person.getPrimaryAssignmentDTO().getRefId());
		journalReserved.setOrganizationId(person.getPrimaryAssignmentDTO().getOrganizationId());
		journalReserved.setJobId(person.getPrimaryAssignmentDTO().getJobId());
		journalReserved.setPersonId(person.getPrimaryAssignmentDTO().getPersonId());
		journalReserved.setReserved(1L);
		journalReserved.setExisting(0L);
		journalReserved.setRemark("Transfer Within Group");
		mppServiceImpl.createJournal(journalReserved, person.getPrimaryAssignmentDTO().getCompanyId());

		MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
		journalExisted.setMppTrnDate(person.getPrimaryAssignmentDTO().getEffectiveStartDate());
		journalExisted.setSource(PeopleSource.TERMINATION.name());
		journalExisted.setSourceTrnId(person.getPrimaryAssignmentDTO().getRefId());
		journalExisted.setOrganizationId(person.getPrimaryAssignmentDTO().getOrganizationId());
		journalExisted.setJobId(person.getPrimaryAssignmentDTO().getJobId());
		journalExisted.setPersonId(person.getPrimaryAssignmentDTO().getPersonId());
		journalExisted.setReserved(-1L);
		journalExisted.setExisting(1L);
		journalExisted.setRemark("Transfer Within Group");
		mppServiceImpl.createJournal(journalExisted, person.getPrimaryAssignmentDTO().getCompanyId());

		// notify to other system
		Date effectiveDate = person.getPrimaryAssignmentDTO().getEffectiveStartDate();
		String employeePhoneNumber = getEmployeePhoneNumberActive(listCommunications, effectiveDate);
		interfaceBufferServiceImpl.insertInterfaceBuffer(person.getPersonId(), person.getEmployeeNumber(), person.getFullName(), person.getEmploymentCategory(), person
				.getPrimaryAssignmentDTO().getEffectiveStartDate(), employeePhoneNumber, person.getPrimaryAssignmentDTO().getJobId(), person.getPrimaryAssignmentDTO()
				.getOrganizationId(), null, false, person.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());

		terminationServiceAdapterImpl.updateStatusTermination(refId, "HIRED", null);

		// create salary
		Long branchId = null;
		if (organizationSetupServiceImpl.isHeadOffice(person.getPrimaryAssignmentDTO().getOrganizationId())) {
			branchId = -1L;
		} else {
			OrganizationDTO branch = organizationSetupServiceImpl.getBranch(person.getPrimaryAssignmentDTO().getOrganizationId(),
					DateUtil.truncate(person.getPrimaryAssignmentDTO().getEffectiveStartDate()), person.getPrimaryAssignmentDTO().getCompanyId());
			branchId = branch != null ? branch.getId() : null;
		}
		BigDecimal minSalary = gradeSetupServiceImpl.getMinSalaryByGradeIdAndBranchId(person.getPrimaryAssignmentDTO().getGradeId(), branchId,
				DateUtil.truncate(person.getEffectiveStartDate()));
		BigDecimal currentSalary = salaryServiceImpl.getEmployeeSalary(person.getPersonId(), companyOrigin, DateUtil.truncate(terminationDate));
		BigDecimal salary = currentSalary.compareTo(minSalary) > 0 ? currentSalary : minSalary;
		if (salary.compareTo(BigDecimal.ZERO) > 0) {
			salaryServiceImpl.createNewSalaryWithoutValidation(person.getPersonId(), person.getCompanyId(), person.getEffectiveStartDate(), salary,
					"PERSONEEL_ADMINISTRATION");
		}

		PrimaryAssignmentDTO prevAssignment = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(person.getPersonId(), companyOrigin, terminationDate);
		// Melakukan generate installment, invoice, jurnal mutasi inter company
		loanProcessServiceImpl.joinWithinGroup(person.getPersonId(), prevAssignment.getOrganizationId(), person.getPrimaryAssignmentDTO().getOrganizationId(),
				companyOrigin, companyDestination);
		
		//disciplinary letter impact
		disciplinaryRequestServiceImpl.endDateSpTransfer(person.getPersonId(), companyOrigin, person.getPersonUUID(), 
				person.getPrimaryAssignmentDTO().getJobId(), person.getPrimaryAssignmentDTO().getOrganizationId(),
				terminationDate);
		
		//---add by jatis for CAM purpose; bugzilla no 16615
		//---start
		// generate IDP		
		try {
			//add null parameter by cahyo For ITSM 16030817095479
			idpEmployeeServiceImpl.generateFromTransfer(person.getPersonId(), person.getPrimaryAssignmentDTO().getJobId(), null);
			//end of add
		}catch (Exception e) {
//			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}		
		//--end
		//--add by jatis for CAM purpose; bugzilla no 16615
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void deletePeopleData(PersonDTO person) {
		documentServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		roleServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		awardInformationServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		vitalStatisticServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		workingEquipmentServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		workingExperienceServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		communicationServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		addressServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		educationServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		contactServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		otherInfoPersonnelServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		primaryAssignmentService.deleteFuturePrimaryAssignment(person.getPersonId(), person.getCompanyId());
		secondaryAssignmentServiceImpl.deleteByPersonIdCompanyId(person.getPersonId(), person.getCompanyId());
		accountInformationServiceImpl.deleteFutureAccountInformation(person.getPersonId(), person.getCompanyId());
		bankInformationServiceImpl.deleteFutureBankInformation(person.getPersonId(), person.getCompanyId());
		personalInformationService.deleteFuturePersonalInformation(person.getPersonId(), person.getCompanyId());
		peopleHistoryServiceImpl.deleteByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId());
		peopleTypeService.deleteFuturePeopleType(person.getPersonId(), person.getCompanyId());
		personMapper.deleteByPrimaryKey(person.getPersonId());
	}*/

	/*@Override
	public List<PeopleAffcoDTO> selectPeopleAffcoByCompanyId(Long personId, Date effectiveOnDate, Long companyId, String employeeNumber, String fullName) {
		return personFinder.selectPeopleAffcoByCompanyId(personId, effectiveOnDate, companyId, employeeNumber, fullName);
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void saveAffcoMutation(PersonMainDTO personMain) throws Exception {
		Long companyId = securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId();

		Person person = new Person();
		person.setPersonUUID(UUID.randomUUID());
		savePerson(person);

		personMain.getPersonDTO().setPersonId(person.getPersonId());
		personMain.getPersonDTO().setPersonUUID(person.getPersonUUID());
		personMain.getPersonDTO().setCompanyId(companyId);

		PeopleTypeDTO peopleType = new PeopleTypeDTO();
		peopleType.setPersonId(personMain.getPersonDTO().getPersonId());
		peopleType.setCompanyId(personMain.getPersonDTO().getCompanyId());
		peopleType.setPeopleType(personMain.getPersonDTO().getPeopleType());
		peopleType.setEmploymentCategory(personMain.getPersonDTO().getEmploymentCategory());
		peopleType.setSource(personMain.getPersonDTO().getSource());
		peopleType.setRefId(personMain.getPersonDTO().getRefId());
		peopleType.setAffco(true);
		peopleType.setCanceled(personMain.getPersonDTO().isCanceled());
		peopleType.setEffectiveStartDate(personMain.getPersonDTO().getEffectiveStartDate());
		peopleType.setEffectiveEndDate(personMain.getPersonDTO().getEffectiveEndDate());
		peopleTypeService.savePeopleType(peopleType, null, null);

		String employeeNumber = generateEmployeeNumber(personMain.getPersonDTO().getPeopleType());
		personMain.getPersonDTO().setEmployeeNumber(employeeNumber);
		personalInformationService.savePersonalInformation(personMain.getPersonDTO(), null, null);

		if (personMain.getPersonDTO().getInternalMarriedWith() != null) {
			createSpouse(personMain.getPersonDTO());
		}

		personMain.getAccountInformationDTO().setPersonId(personMain.getPersonDTO().getPersonId());
		personMain.getAccountInformationDTO().setCompanyId(personMain.getPersonDTO().getCompanyId());
		accountInformationServiceImpl.saveAccountInformation(employeeNumber, personMain.getAccountInformationDTO(), null, null);

		personMain.getBankInformationDTO().setCompanyId(personMain.getPersonDTO().getCompanyId());
		personMain.getBankInformationDTO().setPersonId(personMain.getPersonDTO().getPersonId());
		bankInformationServiceImpl.saveBankInformation(employeeNumber, personMain.getBankInformationDTO(), null, null);

		communicationServiceImpl.saveCommunication(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getListCommunication());

		addressServiceImpl.saveAddress(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getListAddress());

		contactServiceImpl.saveContact(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getContacts().getListContact());

		educationServiceImpl.saveEducation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain.getListEducation());

		// save performance review
		savePerformanceReview(personMain.getListPerformance(), personMain.getPersonDTO().getEmployeeNumber(), personMain.getPersonDTO().getPersonId(), personMain
				.getPersonDTO().getCompanyId());

		personMain.getPersonDTO().getPrimaryAssignmentDTO().setPersonId(personMain.getPersonDTO().getPersonId());
		personMain.getPersonDTO().getPrimaryAssignmentDTO().setCompanyId(personMain.getPersonDTO().getCompanyId());
		primaryAssignmentService.savePrimaryAssignment(personMain.getPersonDTO().getPrimaryAssignmentDTO(), null, null);

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = null;
		if (personMain.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.PROBATION.name())) {
			eventName = EventType.HIRED_PROBATION.name();
		} else if (personMain.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.PERMANENT.name())) {
			eventName = EventType.HIRED_PERMANENT.name();
		} else if (personMain.getPersonDTO().getEmploymentCategory().equals(EmploymentCategory.TRAINEE.name())) {
			eventName = EventType.HIRED_TRAINEE.name();
		}
		peopleHistory.setEventName(eventName);
		String description = Labels.getLabel("pea.hiredOnDateWithStatus", new Object[] { personMain.getPersonDTO().getEmployeeNumber(),
				personMain.getPersonDTO().getFullName(), DateUtil.format(personMain.getPersonDTO().getHireDate()), personMain.getPersonDTO().getEmploymentCategory() });
		peopleHistory.setMessageDescription(description);
		peopleHistoryServiceImpl.savePeopleHistory(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), peopleHistory);

		// create journal
		MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
		journalExisted.setMppTrnDate(personMain.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate());
		journalExisted.setSource(PeopleSource.PERSONNEL_ADMIN.name());
		journalExisted.setSourceTrnId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getRefId());
		journalExisted.setOrganizationId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId());
		journalExisted.setJobId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getJobId());
		journalExisted.setPersonId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getPersonId());
		journalExisted.setReserved(0L);
		journalExisted.setExisting(1L);
		journalExisted.setRemark("Transfer Affco (Effective Date)");
		mppServiceImpl.createJournal(journalExisted, personMain.getPersonDTO().getPrimaryAssignmentDTO().getCompanyId());

		// insert benefit entitlement source
		benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getPrimaryAssignmentDTO()
				.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(), Reason.NEW_HIRE.name(), personMain.getPersonDTO().getCompanyId(), personMain
				.getPersonDTO().getPrimaryAssignmentDTO().getAssignmentId());

		// insert leave entitlement source
		leaveEntitlementServiceAdapterImpl.insertEntitlementSource(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getPrimaryAssignmentDTO()
				.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(), Reason.NEW_HIRE.name(), personMain.getPersonDTO().getPersonId());

		// interfacing to other system
		Date effectiveDate = personMain.getPersonDTO().getPrimaryAssignmentDTO().getEffectiveStartDate();
		String employeePhoneNumber = getEmployeePhoneNumberActive(personMain.getListCommunication(), effectiveDate);

		interfaceBufferServiceImpl.insertInterfaceBuffer(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getEmployeeNumber(), personMain
				.getPersonDTO().getFullName(), personMain.getPersonDTO().getEmploymentCategory(), personMain.getPersonDTO().getPrimaryAssignmentDTO()
				.getEffectiveStartDate(), employeePhoneNumber, personMain.getPersonDTO().getPrimaryAssignmentDTO().getJobId(), personMain.getPersonDTO()
				.getPrimaryAssignmentDTO().getOrganizationId(), null, true, personMain.getPersonDTO().getCompanyId(), EmployeeFlag.NEW.getDescription());

		// create salary
		OrganizationDTO branch = organizationSetupServiceImpl.getBranch(personMain.getPersonDTO().getPrimaryAssignmentDTO().getOrganizationId(),
				DateUtil.truncate(personMain.getPersonDTO().getEffectiveStartDate()), personMain.getPersonDTO().getPrimaryAssignmentDTO().getCompanyId());
		BigDecimal minSalary = gradeSetupServiceImpl.getMinSalaryByGradeIdAndBranchId(personMain.getPersonDTO().getPrimaryAssignmentDTO().getGradeId(),
				branch != null ? branch.getId() : null, DateUtil.truncate(personMain.getPersonDTO().getEffectiveStartDate()));
		if (minSalary.compareTo(BigDecimal.ZERO) > 0) {
			salaryServiceImpl.createNewSalaryWithoutValidation(personMain.getPersonDTO().getPersonId(), personMain.getPersonDTO().getCompanyId(), personMain
					.getPersonDTO().getEffectiveStartDate(), minSalary, "PERSONEEL_ADMINISTRATION");
		}
	}*/
	
	/*@Override
	@Transactional(readOnly = false)
	public void updatePrimaryAssignment(PrimaryAssignmentDTO primaryAssignment, boolean isFromUpload) throws Exception {
		PrimaryAssignmentDTO primaryAssignmentBefore = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(primaryAssignment.getPersonId(),
				primaryAssignment.getCompanyId(), primaryAssignment.getEffectiveStartDate());
		Date effectiveDate = DateUtil.truncate(new Date());
		PersonDTO person = getPersonalData(primaryAssignment.getPersonId(), primaryAssignment.getCompanyId(), effectiveDate);

		// primaryAssignment.setTransferedBy(primaryAssignmentBefore.getTransferedBy());
		if (isFromUpload) {
			primaryAssignment.setHousingAllowance(primaryAssignmentBefore.getHousingAllowance());
		}
		primaryAssignmentService.savePrimaryAssignment(primaryAssignment, primaryAssignmentBefore.getEffectiveStartDate(), primaryAssignmentBefore.getEffectiveEndDate());

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = null;
		String description = "";
		if (primaryAssignment.getActionType().equals(ActionType.PROMOTION.name())) {
			eventName = EventType.REGULAR_PROMOTION.name();
			description = Labels.getLabel("pea.hasBeenPromotedRegularly",
					new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(primaryAssignment.getEffectiveStartDate()) });
		} else if (primaryAssignment.getActionType().equals(ActionType.TRANSFER.name())) {
			eventName = EventType.TRANSFER.name();
			description = Labels.getLabel("pea.hasBeenTransferedOn",
					new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(primaryAssignment.getEffectiveStartDate()) });
		} else if (primaryAssignment.getActionType().equals(ActionType.DEMOTION.name())) {
			eventName = EventType.DEMOTION.name();
			description = Labels.getLabel("pea.hasBeenDemoted",
					new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(primaryAssignment.getEffectiveStartDate()) });
		}
		peopleHistory.setEventName(eventName);
		peopleHistory.setMessageDescription(setDescriptionPeopleHistory(eventName, description, primaryAssignmentBefore, primaryAssignment));
		peopleHistoryServiceImpl.savePeopleHistory(primaryAssignment.getPersonId(), primaryAssignment.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(primaryAssignment.getPersonId());

		// create mpp journal
		if (!primaryAssignment.getInternship()) {
			MppJournalTransactionDTO journalExistedSource = new MppJournalTransactionDTO();
			journalExistedSource.setMppTrnDate(primaryAssignment.getEffectiveStartDate());
			journalExistedSource.setSource(PeopleSource.PERSONNEL_ADMIN.name());
			journalExistedSource.setSourceTrnId(primaryAssignmentBefore.getRefId());
			journalExistedSource.setOrganizationId(primaryAssignmentBefore.getOrganizationId());
			journalExistedSource.setJobId(primaryAssignmentBefore.getJobId());
			journalExistedSource.setPersonId(primaryAssignmentBefore.getPersonId());
			journalExistedSource.setReserved(0L);
			journalExistedSource.setExisting(-1L);
			journalExistedSource.setRemark("Change assignment status (Effective Date)");
			mppServiceImpl.createJournal(journalExistedSource, primaryAssignment.getCompanyId());

			MppJournalTransactionDTO journalExistedDestination = new MppJournalTransactionDTO();
			journalExistedDestination.setMppTrnDate(primaryAssignment.getEffectiveStartDate());
			journalExistedDestination.setSource(PeopleSource.PERSONNEL_ADMIN.name());
			journalExistedDestination.setSourceTrnId(primaryAssignment.getRefId());
			journalExistedDestination.setOrganizationId(primaryAssignment.getOrganizationId());
			journalExistedDestination.setJobId(primaryAssignment.getJobId());
			journalExistedDestination.setPersonId(primaryAssignment.getPersonId());
			journalExistedDestination.setReserved(0L);
			journalExistedDestination.setExisting(1L);
			journalExistedDestination.setRemark("Change assignment status (Effective Date)");
			mppServiceImpl.createJournal(journalExistedDestination, primaryAssignment.getCompanyId());

			// create salary
			Add Remark By RIM - 15013009295537 - Perbaikan upload assignment
			OrganizationDTO branch = organizationSetupServiceImpl.getBranch(primaryAssignment.getOrganizationId(),
					DateUtil.truncate(primaryAssignment.getEffectiveStartDate()), primaryAssignment.getCompanyId());
			BigDecimal minSalary = gradeSetupServiceImpl.getMinSalaryByGradeIdAndBranchId(primaryAssignment.getGradeId(), branch != null ? branch.getId() : null,
					DateUtil.truncate(primaryAssignment.getEffectiveStartDate()));
			BigDecimal currentSalary = salaryServiceImpl.getEmployeeSalary(primaryAssignment.getPersonId(), primaryAssignment.getCompanyId(),
					DateUtil.truncate(primaryAssignment.getEffectiveStartDate()));
			BigDecimal salary = currentSalary.compareTo(minSalary) > 0 ? currentSalary : minSalary;
			if (salary.compareTo(BigDecimal.ZERO) > 0 && !currentSalary.equals(salary)) {
				salaryServiceImpl.updateSalaryWithoutValidation(primaryAssignment.getPersonId(), primaryAssignment.getCompanyId(),
						primaryAssignment.getEffectiveStartDate(), DateUtil.MAX_DATE, "PERSONEEL_ADMINISTRATION", salary);
			}
			Add Remark By RIM - 15013009295537 - Perbaikan upload assignment
			
		}

		// interfacing to other system
		if (person.getPeopleType().equalsIgnoreCase(PeopleType.EMPLOYEE.name())) {
			String sourceReason = Reason.NEW_ASSIGNMENT.name();
			if (!isNullOrEmpty(primaryAssignment.getTransferedBy()) && primaryAssignment.getTransferedBy().equalsIgnoreCase(TransferBy.TRANSFERED_BY_COMPANY.name())) {
				sourceReason = Reason.TRANSFER_BY_COMPANY.name();
			}

			// insert leave entitlement
			leaveEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignment.getPersonId(), primaryAssignment.getEffectiveStartDate(),
					PeopleSource.PERSONNEL_ADMIN.name(), sourceReason, primaryAssignment.getCompanyId());

			// insert benefit entitlement source
			benefitEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignment.getPersonId(), primaryAssignment.getEffectiveStartDate(),
					PeopleSource.PERSONNEL_ADMIN.name(), sourceReason, primaryAssignment.getCompanyId(), primaryAssignment.getAssignmentId());

			String employeePhoneNumber = getEmployeePhoneNumberActive(
					communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(primaryAssignment.getPersonId(), primaryAssignment.getCompanyId()), effectiveDate);
			interfaceBufferServiceImpl.insertInterfaceBuffer(person.getPersonId(), person.getEmployeeNumber(), person.getFullName(), person.getEmploymentCategory(),
					primaryAssignment.getEffectiveStartDate(), employeePhoneNumber, primaryAssignment.getJobId(), primaryAssignmentBefore.getOrganizationId(),
					primaryAssignment.getOrganizationId(), false, primaryAssignment.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());
		}

		// send notification to employee
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage
				.setTemplateSubject("Personnel Administration - ( ${person.employeeNumber} - ${person.fullName} ) - ${(assignment.effectiveStartDate?date)!} to ${(assignment.effectiveEndDate?date)!}");
		templateMessage.setTemplateContent("<p>Dear ${person.fullName},</p>" + "<p>Kindly be informed there are changes of your Assignment.</p>"
				+ "<p>If the information is incorrect, please contact me. Thank you for your attention.</p>" + "<p>Regards,</p>" + "<p>${updaterName}</p>");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("person", person);
		map.put("assignment", primaryAssignment);
		map.put("updaterName", securityServiceImpl.getSecurityProfile() == null ? "HCMS Admin" : securityServiceImpl.getSecurityProfile().getFullName());
		Message message = notificationManager.templateMessageResolver(templateMessage, map);
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setSubject(message.getSubject());
		notificationMessage.setContentMessage(message.getContent());
		notificationMessage.setFromId(securityServiceImpl.getSecurityProfile() == null ? UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF") : securityServiceImpl
				.getSecurityProfile().getPersonUUID());
		notificationMessage.setToId(person.getPersonUUID());
		notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
		notificationMessage.setReceivedTime(new Date());
		notificationManager.insertNewMessage(notificationMessage);

		// send notification to supervisor
		List<PersonAssignmentDTO> firstSupervisor = SupervisorUtil.getSupervisor(primaryAssignmentBefore.getJobId(), primaryAssignmentBefore.getOrganizationId(),
				primaryAssignmentBefore.getCompanyId(), 1);
		if (firstSupervisor != null && firstSupervisor.size() > 0) {
			for (PersonAssignmentDTO spv : firstSupervisor) {
				templateMessage = new TemplateMessage();
				templateMessage
						.setTemplateSubject("Personnel Administration - ( ${person.employeeNumber} - ${person.fullName} ) - ${(assignment.effectiveStartDate?date)!} to ${(assignment.effectiveEndDate?date)!}");
				templateMessage.setTemplateContent("<p>Dear ${spv.fullName},</p>"
						+ "<p>Kindly be informed there are changes of your subordinateâ€™s Assignment whose name is ${person.employeeNumber} - ${person.fullName}</p>"
						+ "<p>Thank you for your attention.</p>" + "<p>Regards,</p>" + "<p>${updaterName}</p>");
				map = new HashMap<String, Object>();
				map.put("person", person);
				map.put("assignment", primaryAssignment);
				map.put("spv", spv);
				map.put("updaterName", securityServiceImpl.getSecurityProfile() == null ? "HCMS Admin" : securityServiceImpl.getSecurityProfile().getFullName());
				message = notificationManager.templateMessageResolver(templateMessage, map);
				notificationMessage = new NotificationMessage();
				notificationMessage.setSubject(message.getSubject());
				notificationMessage.setContentMessage(message.getContent());
				notificationMessage.setFromId(securityServiceImpl.getSecurityProfile() == null ? UUID.fromString("FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF")
						: securityServiceImpl.getSecurityProfile().getPersonUUID());
				notificationMessage.setToId(spv.getPersonUUID());
				notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
				notificationMessage.setReceivedTime(new Date());
				notificationManager.insertNewMessage(notificationMessage);
			}
		}
		
		//---add by jatis for CAM purpose
		//---start
		// generate IDP
		if(eventName.equalsIgnoreCase(EventType.TRANSFER.name())){
			try {
				//add secProf by Cahyo For ITSM 16030817095479
				SecurityProfile securityProfile = new SecurityProfile();
				securityProfile.setUserId(primaryAssignment.getCreatedBy());
				idpEmployeeServiceImpl.generateFromTransfer(person.getPersonId(), primaryAssignment.getJobId(), securityProfile);
				//end of add
			}catch (Exception e) {
//				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		
		//--end
		//--add by jatis for CAM purpose
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void savePerformanceReview(List<PerformanceReviewDTO> listPerformanceReviewDTOs, String employeeNumber, Long personId, Long companyId) {
		Date createdDate = new Date();
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		// Long personIdHrms = catisServiceImpl.getPersonId(employeeNumber);
		// if (personIdHrms != null) {
		for (PerformanceReviewDTO performanceReviewDTO : listPerformanceReviewDTOs) {
			performanceReviewDTO.setPersonId(personId);
			performanceReviewDTO.setCompanyId(companyId);
			performanceReviewDTO.setLastUpdateDate(createdDate);
			performanceReviewDTO.setLastUpdatedBy(createdBy);
			performanceReviewDTO.setLastUpdateLogin(-1L);
			performanceReviewDTO.setCreatedBy(createdBy);
			performanceReviewDTO.setCreationDate(createdDate);
			catisServiceImpl.insertPerformanceReview(performanceReviewDTO);
		}
		// }
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void probationReview(ProbationReviewDTO probationReviewDTO) throws Exception {
		PeopleTypeDTO peopleTypeBefore = peopleTypeService.getPeopleTypeByEffectiveOnDate(probationReviewDTO.getPersonId(), probationReviewDTO.getCompanyId(),
				probationReviewDTO.getEffectiveOnDate());
		PeopleTypeDTO peopleType = new PeopleTypeDTO();

		if (peopleTypeBefore != null) {
			peopleType.setPersonId(probationReviewDTO.getPersonId());
			peopleType.setCompanyId(probationReviewDTO.getCompanyId());
			peopleType.setPeopleType(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name());
			peopleType.setEmploymentCategory(EmploymentCategory.PERMANENT.name());
			peopleType.setSource(null);
			peopleType.setRefId(null);
			peopleType.setAffco(peopleTypeBefore.getAffco());
			peopleType.setCanceled(false);
			peopleType.setEffectiveStartDate(probationReviewDTO.getEffectiveOnDate());
			peopleType.setEffectiveEndDate(DateUtil.MAX_DATE);
			peopleTypeService.savePeopleType(peopleType, peopleTypeBefore.getEffectiveStartDate(), peopleTypeBefore.getEffectiveEndDate());

			// insert leave entitlement source
			leaveEntitlementServiceAdapterImpl.insertEntitlementSource(probationReviewDTO.getPersonId(), probationReviewDTO.getEffectiveOnDate(),
					PeopleSource.PERSONNEL_ADMIN.name(), Reason.EMPLOYMENT_CATEGORY.name(), probationReviewDTO.getCompanyId());

			PeopleHistory peopleHistory = new PeopleHistory();
			String eventName = EventType.PASSED_PROBATION.name();
			peopleHistory.setEventName(eventName);
			String description = Labels.getLabel("pea.probationPassed",
					new Object[] { probationReviewDTO.getEmployeeNumber(), probationReviewDTO.getFullName(), DateUtil.format(probationReviewDTO.getEffectiveOnDate()),
							peopleType.getEmploymentCategory() });
			peopleHistory.setMessageDescription(description);
			peopleHistoryServiceImpl.savePeopleHistory(probationReviewDTO.getPersonId(), probationReviewDTO.getCompanyId(), peopleHistory);

			updateLastUpdatePerson(probationReviewDTO.getPersonId());
		}

	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void cancelJoin(PersonDTO personDTO) throws Exception {
		PeopleTypeDTO peopleTypeCurrent = peopleTypeService.getPeopleTypeByEffectiveOnDate(personDTO.getPersonId(), personDTO.getCompanyId(),
				DateUtil.truncate(new Date()));
		if (peopleTypeCurrent != null) {
			deletePeopleHistory(personDTO);

			// create journal
			MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
			journalExisted.setMppTrnDate(personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate());
			journalExisted.setSource(PeopleSource.PERSONNEL_ADMIN.name());
			journalExisted.setSourceTrnId(personDTO.getPrimaryAssignmentDTO().getRefId());
			journalExisted.setOrganizationId(personDTO.getPrimaryAssignmentDTO().getOrganizationId());
			journalExisted.setJobId(personDTO.getPrimaryAssignmentDTO().getJobId());
			journalExisted.setPersonId(personDTO.getPrimaryAssignmentDTO().getPersonId());
			journalExisted.setReserved(0L);
			journalExisted.setExisting(-1L);
			journalExisted.setRemark("Cancel Join");
			mppServiceImpl.createJournal(journalExisted, personDTO.getPrimaryAssignmentDTO().getCompanyId());
		} else {
			deletePeopleData(personDTO);
			mppServiceImpl.deleteMppJournal(personDTO.getPersonId());
		}

		// delete benefit entitlement
		benefitEntitlementServiceAdapterImpl.deleteEntitlementSource(personDTO.getPersonId(), personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate(),
				personDTO.getCompanyId());

		// delete leave entitilement
		leaveEntitlementServiceAdapterImpl.deleteEntitlementSource(personDTO.getPersonId(), personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate(),
				personDTO.getCompanyId());

		// delete interface buffer
		interfaceBufferServiceImpl.deleteInterfaceBuffer(personDTO.getPersonId(), personDTO.getCompanyId(), personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate());

		// Melakukan update ke tabel recruitment berdasarkan hire_trx_id pada
		// peopleType dengan merubah status menjadi "CANCELED"
		if (personDTO.getSource() != null) {
			if (personDTO.getSource().equalsIgnoreCase(PeopleSource.RECRUITMENT.name())) {
				applicantServiceImpl.cancelJoinApplicant(personDTO.getRefId(), personDTO.getCompanyId(), personDTO.getPersonId(), personDTO.getEffectiveStartDate());
			}
		} else {
			salaryServiceImpl.deleteNewSalary(personDTO.getPersonId(), personDTO.getCompanyId(), personDTO.getEffectiveStartDate());
		}
	}*/

	@Override
	public int countPersonByCompany(PersonCriteriaDTO personCriteriaDTO, Long companyId) {
		return personFinder.countPersonByCompany(personCriteriaDTO, companyId);
	}
	
	@Override
	public int countPersonByCompanyTemp(PersonCriteriaDTO personCriteriaDTO, Long companyId) {
		return personFinder.countPersonByCompanyTemp(personCriteriaDTO, companyId);
	}
	
	@Override
	public int countPersonByCompanyInvalidDate(PersonCriteriaDTO personCriteriaDTO, Long companyId) {
		return personFinder.countPersonByCompanyInvalidDate(personCriteriaDTO, companyId);
	}

	/*@Override

	@Transactional(readOnly = false)
	public void saveTransfer(PrimaryAssignmentDTO primaryAssignmentDTO,String originLocation,String destLocation,String employeeCat,Long isKeyjob,Long isPlacmentStatus) throws Exception {
	public void saveTransfer(PrimaryAssignmentDTO primaryAssignmentDTO, TransferDTO transfer ) throws Exception {

		PrimaryAssignmentDTO primaryAssignmentBefore = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(primaryAssignmentDTO.getPersonId(),
				primaryAssignmentDTO.getCompanyId(), primaryAssignmentDTO.getEffectiveStartDate());
		// primaryAssignmentDTO.setGradeId(primaryAssignmentBefore.getGradeId());

		// Grade mengambil dari grade set job tujuan yang memiliki grade name
		// sama.
		Long destinationGradeId = transferTransactionServiceImpl.getGradeDestinationByJob(primaryAssignmentDTO.getJobId(), primaryAssignmentBefore.getGradeId(),
				DateUtil.truncate(primaryAssignmentDTO.getEffectiveStartDate()));
		
		if (destinationGradeId == null)
			throw new Exception("Grade destination is null");
		primaryAssignmentBefore.getLocationId()
		
		 add by PMW, ticket 15111616041796, 15-Feb-2016 
		primaryAssignmentDTO.setGradeId(destinationGradeId);
		
		
		
		String permanent = employeeCat;
		if(permanent == "TASK_FORCE"){
			permanent = "Task Force";
		}
		String transferBy = primaryAssignmentDTO.getTransferedBy();
		String assignmentStatus =  primaryAssignmentDTO.getAssignmentStatus();
		String transfer = "";
		int companyId  = (int) (long) primaryAssignmentBefore.getCompanyId();
		
		Long dest = Long.parseLong(flagBoardingAutomaticServiceImpl.getIdLocation(destLocation, companyId), 10);
		Long origin = Long.parseLong(flagBoardingAutomaticServiceImpl.getIdLocation(originLocation, companyId), 10);
		
		switch(transferBy){
		case "TRANSFERED_BY_OWN" :
			transfer = "By Own";
			break;
		case "TRANSFERED_BY_COMPANY":
			transfer = "By Company";
		break;
		}
	
	//	isPlacmentStatus  = (int) (long) 2;
		
		String placementStatus = flagBoardingAutomaticServiceImpl.getPlacementStatus(primaryAssignmentDTO.getPersonId(),companyId);
		
		int isPlacementStatus = -1;
		if(placementStatus != null){
			switch(placementStatus){
				case "OPEN" :
					isPlacementStatus = 1;
					break;
				case "CLOSED":
					isPlacementStatus = 0;
				break;
				case "FUTURE":
					isPlacementStatus = 2;
				break;
				default:
					isPlacementStatus = 0;
				break;
			}
		}
		
		String flagBoardingPrevious = "";
		
		flagBoardingPrevious = flagBoardingAutomaticServiceImpl.getFlagBoarding(primaryAssignmentDTO.getPersonId(), companyId);// flagBoardingAutomaticServiceImpl.getFlagBoarding(, companyId);
		
		flagBoardingPrevious = (flagBoardingPrevious == null ? "" : flagBoardingPrevious);
		
		 String update = flagBoardingAutomaticServiceImpl.getUpdateFlagBoarding(origin,
				 dest, assignmentStatus,employeeCat,transfer, (int) (long) isKeyjob
				 ,flagBoardingPrevious, isPlacementStatus,companyId);
		 
		update = (update == null ? "NONE" : update).toUpperCase();
		//primaryAssignmentDTO.getLocationId();
		//String flagUpdate = ( update == null ? "NONE" : update );
		primaryAssignmentDTO.setHousingAllowance(updateupdateprimaryAssignmentBefore.getHousingAllowance());
		primaryAssignmentDTO.setInternship(false);
		primaryAssignmentDTO.setSource(PeopleSource.TRANSFER.name());
		primaryAssignmentService.savePrimaryAssignment(primaryAssignmentDTO, primaryAssignmentBefore.getEffectiveStartDate(),
				primaryAssignmentBefore.getEffectiveEndDate());
		
		// add by PMW, ticket 15111616041796, 15-Feb-2016
		HousingAllowanceDTO housingAllowanceDTO = housingAllowanceServiceImpl
				.getHousingAllowanceByEffectiveOnDate(primaryAssignmentDTO.getPersonId(),
						primaryAssignmentDTO.getCompanyId(), DateUtil.truncate(new Date()));
		Date dateStartBefore = null;
		Date dateEndBefore = null;
		
		if (housingAllowanceDTO == null) {
			housingAllowanceDTO = new HousingAllowanceDTO();
		} else {
			dateStartBefore = housingAllowanceDTO.getEffectiveStartDate();
			dateEndBefore = housingAllowanceDTO.getEffectiveEndDate();
		}
		
		housingAllowanceDTO.setCompanyId(primaryAssignmentDTO.getCompanyId());
		housingAllowanceDTO.setCreatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : Long.parseLong(securityServiceImpl
						.getSecurityProfile().getEmployeeNumber(),10));
		housingAllowanceDTO.setCreationDate(new Date());
		housingAllowanceDTO.setEffectiveStartDate(primaryAssignmentDTO.getEffectiveStartDate());
		housingAllowanceDTO.setEffectiveEndDate(primaryAssignmentDTO.getEffectiveEndDate());
		housingAllowanceDTO.setHouseAllowance(HousingAllowance.NONE.name());
		housingAllowanceDTO.setLastUpdateDate(new Date());
		housingAllowanceDTO.setLastUpdatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : Long.parseLong(securityServiceImpl
				.getSecurityProfile().getEmployeeNumber(),10));
		housingAllowanceDTO.setPersonId(primaryAssignmentDTO.getPersonId());
		housingAllowanceDTO.setHouseAllowance(update);
		housingAllowanceDTO.setHousingAllowanceId(null);
		 end add PMW, ticket 15111616041796, 15-Feb-2016 
		housingAllowanceServiceImpl.save(housingAllowanceDTO,  dateStartBefore, dateEndBefore);

		String sourceReason = Reason.NEW_ASSIGNMENT.name();
		if (!isNullOrEmpty(primaryAssignmentDTO.getTransferedBy()) && primaryAssignmentDTO.getTransferedBy().equalsIgnoreCase(TransferBy.TRANSFERED_BY_COMPANY.name())) {
			sourceReason = Reason.TRANSFER_BY_COMPANY.name();
		}

		if (primaryAssignmentDTO.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.TASK_FORCE.name())) {
			primaryAssignmentBefore.setEffectiveStartDate(DateUtil.add(primaryAssignmentDTO.getEffectiveEndDate(), Calendar.DATE, 1));
			primaryAssignmentBefore.setEffectiveEndDate(DateUtil.MAX_DATE);
			primaryAssignmentBefore.setSource(PeopleSource.TRANSFER.name());
			primaryAssignmentBefore.setRefId(primaryAssignmentDTO.getRefId());
			primaryAssignmentService.savePrimaryAssignment(primaryAssignmentBefore, primaryAssignmentDTO.getEffectiveStartDate(),
					primaryAssignmentDTO.getEffectiveEndDate());

			// insert leave entitlement source
			leaveEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignmentBefore.getPersonId(), primaryAssignmentBefore.getEffectiveStartDate(),
					PeopleSource.PERSONNEL_ADMIN.name(), sourceReason, primaryAssignmentBefore.getCompanyId());

			// insert benefit entitilement source
			benefitEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignmentBefore.getPersonId(), primaryAssignmentBefore.getEffectiveStartDate(),
					PeopleSource.PERSONNEL_ADMIN.name(), sourceReason, primaryAssignmentBefore.getCompanyId(), primaryAssignmentBefore.getAssignmentId());
		}

		PersonDTO person = getPersonalData(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId(), DateUtil.truncate(new Date()));

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = "";
		String description = "";
		if (primaryAssignmentDTO.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.DEFINITIVE.name())) {
			eventName = EventType.TRANSFER.name();
			description = Labels.getLabel("pea.hasBeenTransferedOn",
					new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(primaryAssignmentBefore.getEffectiveStartDate()), });
		} else if (primaryAssignmentDTO.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.TASK_FORCE.name())) {
			eventName = EventType.TASKFORCE.name();
			description = Labels.getLabel(
					"pea.hasBeenTransferedTaskforce",
					new Object[] { person.getEmployeeNumber(), person.getFullName(), DateUtil.format(primaryAssignmentDTO.getEffectiveStartDate()),
							DateUtil.format(primaryAssignmentDTO.getEffectiveEndDate()) });
		}
		peopleHistory.setEventName(eventName);
		peopleHistory.setMessageDescription(setDescriptionPeopleHistory(eventName, description, primaryAssignmentBefore, primaryAssignmentDTO));
		peopleHistoryServiceImpl.savePeopleHistory(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(primaryAssignmentDTO.getPersonId());

		// insert leave entitlement source
		leaveEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getEffectiveStartDate(),
				PeopleSource.PERSONNEL_ADMIN.name(), sourceReason, primaryAssignmentDTO.getCompanyId());

		// insert benefit entitilement source
		benefitEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getEffectiveStartDate(),
				PeopleSource.PERSONNEL_ADMIN.name(), sourceReason, primaryAssignmentDTO.getCompanyId(), primaryAssignmentDTO.getAssignmentId());

		// assignment status = definitive
		if (primaryAssignmentDTO.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.DEFINITIVE.name())) {
			MppJournalTransactionDTO journalReservedSource = new MppJournalTransactionDTO();
			journalReservedSource.setMppTrnDate(DateUtil.truncate(new Date()));
			journalReservedSource.setSource(PeopleSource.TRANSFER.name());
			journalReservedSource.setSourceTrnId(primaryAssignmentBefore.getRefId());
			journalReservedSource.setOrganizationId(primaryAssignmentBefore.getOrganizationId());
			journalReservedSource.setJobId(primaryAssignmentBefore.getJobId());
			journalReservedSource.setPersonId(primaryAssignmentBefore.getPersonId());
			journalReservedSource.setReserved(-1L);
			journalReservedSource.setExisting(0L);
			journalReservedSource.setRemark("Transfer Approval");
			mppServiceImpl.createJournal(journalReservedSource, primaryAssignmentBefore.getCompanyId());

			MppJournalTransactionDTO journalExistedSource = new MppJournalTransactionDTO();
			journalExistedSource.setMppTrnDate(primaryAssignmentDTO.getEffectiveStartDate());
			journalExistedSource.setSource(PeopleSource.TRANSFER.name());
			journalExistedSource.setSourceTrnId(primaryAssignmentBefore.getRefId());
			journalExistedSource.setOrganizationId(primaryAssignmentBefore.getOrganizationId());
			journalExistedSource.setJobId(primaryAssignmentBefore.getJobId());
			journalExistedSource.setPersonId(primaryAssignmentBefore.getPersonId());
			journalExistedSource.setReserved(1L);
			journalExistedSource.setExisting(-1L);
			journalExistedSource.setRemark("Transfer Effective Date");
			mppServiceImpl.createJournal(journalExistedSource, primaryAssignmentBefore.getCompanyId());

			MppJournalTransactionDTO journalReservedDestination = new MppJournalTransactionDTO();
			journalReservedDestination.setMppTrnDate(DateUtil.truncate(new Date()));
			journalReservedDestination.setSource(PeopleSource.TRANSFER.name());
			journalReservedDestination.setSourceTrnId(primaryAssignmentDTO.getRefId());
			journalReservedDestination.setOrganizationId(primaryAssignmentDTO.getOrganizationId());
			journalReservedDestination.setJobId(primaryAssignmentDTO.getJobId());
			journalReservedDestination.setPersonId(primaryAssignmentDTO.getPersonId());
			journalReservedDestination.setReserved(1L);
			journalReservedDestination.setExisting(0L);
			journalReservedDestination.setRemark("Transfer Approval");
			mppServiceImpl.createJournal(journalReservedDestination, primaryAssignmentDTO.getCompanyId());

			MppJournalTransactionDTO journalExistedDestination = new MppJournalTransactionDTO();
			journalExistedDestination.setMppTrnDate(primaryAssignmentDTO.getEffectiveStartDate());
			journalExistedDestination.setSource(PeopleSource.TRANSFER.name());
			journalExistedDestination.setSourceTrnId(primaryAssignmentDTO.getRefId());
			journalExistedDestination.setOrganizationId(primaryAssignmentDTO.getOrganizationId());
			journalExistedDestination.setJobId(primaryAssignmentDTO.getJobId());
			journalExistedDestination.setPersonId(primaryAssignmentDTO.getPersonId());
			journalExistedDestination.setReserved(-1L);
			journalExistedDestination.setExisting(1L);
			journalExistedDestination.setRemark("Transfer Effective Date");
			mppServiceImpl.createJournal(journalExistedDestination, primaryAssignmentDTO.getCompanyId());
		}

		// interfacing to other system
		Date effectiveDate = primaryAssignmentDTO.getEffectiveStartDate();
		String employeePhoneNumber = getEmployeePhoneNumberActive(
				communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId()), effectiveDate);
		interfaceBufferServiceImpl.insertInterfaceBuffer(person.getPersonId(), person.getEmployeeNumber(), person.getFullName(), person.getEmploymentCategory(),
				primaryAssignmentDTO.getEffectiveStartDate(), employeePhoneNumber, primaryAssignmentDTO.getJobId(), person.getPrimaryAssignmentDTO().getOrganizationId(),
				primaryAssignmentDTO.getOrganizationId(), false, primaryAssignmentDTO.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());
		
		//flag housing allowance
		
		
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void saveTransferTrainee(SecondaryAssignmentDTO secondaryAssignmentDTO) throws Exception {
		saveSecondaryAssignment(secondaryAssignmentDTO.getPersonId(), secondaryAssignmentDTO.getCompanyId(), secondaryAssignmentDTO);
	}*/

	@Override
	public List<KeyValue> getAllPersonByCompany(PersonCriteriaDTO criteria, Long companyId, int limit, int offset) {
		criteria.setEffectiveOnDate(DateUtil.MAX_DATE);
		return personFinder.selectAllPersonByCompanyKeyValue(criteria, companyId, new RowBounds(offset, limit));
	}

	@Override
	public int countAllPersonByCompany(PersonCriteriaDTO criteria, Long companyId) {
		criteria.setEffectiveOnDate(DateUtil.MAX_DATE);
		return personFinder.countAllPersonByCompany(criteria, companyId);
	}

	/*@Override
	public int countPeopleAffcoByCompanyId(String employeeNumber, String fullName, Long companyId) {
		return personFinder.countPeopleAffcoByCompanyId(employeeNumber, fullName, companyId);
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void deletePrimaryAssignment(PrimaryAssignmentDTO primaryAssignmentDTO) throws Exception {
		PrimaryAssignmentDTO primaryAssignmentCurrent = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(primaryAssignmentDTO.getPersonId(),
				primaryAssignmentDTO.getCompanyId(), DateUtil.truncate(new Date()));
		PrimaryAssignment taskForceAssignment = null;

		if (primaryAssignmentDTO.getSource() != null && primaryAssignmentDTO.getRefId() != null) {
			taskForceAssignment = primaryAssignmentService.getTaskForceTransaction(primaryAssignmentDTO);
		}
		// delete mpp
		if (taskForceAssignment == null) {
			MppJournalTransactionDTO journalExistedSource = new MppJournalTransactionDTO();
			journalExistedSource.setMppTrnDate(primaryAssignmentDTO.getEffectiveStartDate());
			journalExistedSource.setSource(PeopleSource.PERSONNEL_ADMIN.name());
			journalExistedSource.setSourceTrnId(primaryAssignmentCurrent.getRefId());
			journalExistedSource.setOrganizationId(primaryAssignmentCurrent.getOrganizationId());
			journalExistedSource.setJobId(primaryAssignmentCurrent.getJobId());
			journalExistedSource.setPersonId(primaryAssignmentCurrent.getPersonId());
			journalExistedSource.setReserved(0L);
			journalExistedSource.setExisting(1L);
			journalExistedSource.setRemark("Delete Primary Assignment");
			mppServiceImpl.createJournal(journalExistedSource, primaryAssignmentDTO.getCompanyId());

			MppJournalTransactionDTO journalExistedDestination = new MppJournalTransactionDTO();
			journalExistedDestination.setMppTrnDate(primaryAssignmentDTO.getEffectiveStartDate());
			journalExistedDestination.setSource(PeopleSource.PERSONNEL_ADMIN.name());
			journalExistedDestination.setSourceTrnId(primaryAssignmentDTO.getRefId());
			journalExistedDestination.setOrganizationId(primaryAssignmentDTO.getOrganizationId());
			journalExistedDestination.setJobId(primaryAssignmentDTO.getJobId());
			journalExistedDestination.setPersonId(primaryAssignmentDTO.getPersonId());
			journalExistedDestination.setReserved(0L);
			journalExistedDestination.setExisting(-1L);
			journalExistedSource.setRemark("Delete Primary Assignment");
			mppServiceImpl.createJournal(journalExistedDestination, primaryAssignmentDTO.getCompanyId());
		}

		benefitEntitlementServiceAdapterImpl.deleteEntitlementSource(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getEffectiveStartDate(),
				primaryAssignmentDTO.getCompanyId());

		leaveEntitlementServiceAdapterImpl.deleteEntitlementSource(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getEffectiveStartDate(),
				primaryAssignmentDTO.getCompanyId());

		if (taskForceAssignment != null) {
			benefitEntitlementServiceAdapterImpl.deleteEntitlementSource(taskForceAssignment.getPersonId(), taskForceAssignment.getEffectiveStartDate(),
					taskForceAssignment.getCompanyId());

			leaveEntitlementServiceAdapterImpl.deleteEntitlementSource(taskForceAssignment.getPersonId(), taskForceAssignment.getEffectiveStartDate(),
					taskForceAssignment.getCompanyId());
		}

		interfaceBufferServiceImpl.deleteInterfaceBuffer(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId(),
				primaryAssignmentDTO.getEffectiveStartDate());

		primaryAssignmentService.delete(primaryAssignmentDTO);

		PeopleHistory peopleHistory = new PeopleHistory();
		peopleHistory.setEventName(EventType.DELETED_ASSIGNMENT.name());
		String description = Labels.getLabel("pea.hasBeenDeletedAssignment");
		peopleHistory.setMessageDescription(description);
		peopleHistoryServiceImpl.savePeopleHistory(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(primaryAssignmentDTO.getPersonId());

		if (!isNullOrEmpty(primaryAssignmentDTO.getSource())) {
			if (primaryAssignmentDTO.getSource().equals(PeopleSource.TRANSFER.name())) {
				
				 * add and remark_15031814050744_Tidak Bisa Delete Future Assignment Transfer
				 * group by_HBP
				 
				//transferTransactionServiceImpl.deleteTransferTransaction(primaryAssignmentDTO.getRefId());
				transferTransactionServiceImpl.deleteTransferTransaction(primaryAssignmentDTO.getRefId(), primaryAssignmentDTO.getPersonId(), 
						primaryAssignmentDTO.getCompanyId(), primaryAssignmentDTO.getEffectiveStartDate());
			} else if (primaryAssignmentDTO.getSource().equals(PeopleSource.PROMOTION.name())) {
				irregularPromotionServiceImpl.deleteIrregularPromotionTransaction(primaryAssignmentDTO.getRefId());
			}
		} else {
			if (!primaryAssignmentDTO.getInternship()) {
				try {
					salaryServiceImpl.deleteSalary(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId(), primaryAssignmentDTO.getEffectiveStartDate());
				} catch (ValidationException e) {

				}
			}
		}
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void savePromotion(PrimaryAssignmentDTO primaryAssignmentDTO,String locationOrigin,String locationDest,String employeeCat,Long isKeyjob,Long isPlacmentStatus) throws Exception {

		PrimaryAssignmentDTO primaryAssignmentBefore = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(primaryAssignmentDTO.getPersonId(),
				primaryAssignmentDTO.getCompanyId(), primaryAssignmentDTO.getEffectiveStartDate());
		primaryAssignmentDTO.setActionType(ActionType.PROMOTION.name());
		primaryAssignmentDTO.setSource(PeopleSource.PROMOTION.name());
		primaryAssignmentDTO.setTransferedBy(TransferBy.TRANSFERED_BY_COMPANY.name());
		primaryAssignmentDTO.setMentorId(null);
		primaryAssignmentDTO.setInternship(false);
		primaryAssignmentDTO.setEffectiveEndDate(DateUtil.MAX_DATE);
		
		 add by PMW, ticket 15111616041796, 15-Feb-2016 
		
		String transferBy = primaryAssignmentDTO.getTransferedBy();
		String assignmentStatus =  primaryAssignmentDTO.getAssignmentStatus();
		String transfer = "";
		int companyId  = (int) (long) primaryAssignmentBefore.getCompanyId();
		String permanent = flagBoardingAutomaticServiceImpl.getEmployementCategory(primaryAssignmentDTO.getPersonId(),companyId);
		if(permanent == "TASK_FORCE"){
			permanent = "Task Force";
		}
		Long dest = Long.parseLong(flagBoardingAutomaticServiceImpl.getIdLocation(locationOrigin, companyId), 10);
		Long origin = Long.parseLong(flagBoardingAutomaticServiceImpl.getIdLocation(locationDest, companyId), 10);
		switch(transferBy){
		case "TRANSFERED_BY_OWN" :
			transfer = "By Own";
			break;
		case "TRANSFERED_BY_COMPANY":
			transfer = "By Company";
		break;
		}
		
		String placementStatus =  flagBoardingAutomaticServiceImpl.getPlacementStatus(primaryAssignmentDTO.getPersonId(),companyId);
		
	
		int isPlacementStatus = -1;
		
		if(placementStatus != null){
			switch(placementStatus){
				case "OPEN" :
					isPlacementStatus = 1;
					break;
				case "CLOSED":
					isPlacementStatus = 0;
				break;
				case "FUTURE":
					isPlacementStatus = 2;
				break;
				default:
					isPlacementStatus = 0;
				break;
			}
		}
		
		String flagBoardingPrevious = "";
		
		flagBoardingPrevious = flagBoardingAutomaticServiceImpl.getFlagBoarding(primaryAssignmentDTO.getPersonId(), companyId);
		
		flagBoardingPrevious = (flagBoardingPrevious == null ? "" : flagBoardingPrevious);
		
		 String flagBoarding = flagBoardingAutomaticServiceImpl.getUpdateFlagBoarding(origin,
				 dest, assignmentStatus,permanent,transfer, (int) (long) isKeyjob
				 ,flagBoardingPrevious,isPlacementStatus,companyId);
		 
		 flagBoarding = (flagBoarding == null ? "NONE" : flagBoarding).toUpperCase();
		 add by PMW, ticket 15111616041796, 15-Feb-2016 
		
		primaryAssignmentDTO.setHousingAllowance(flagBoarding);
		primaryAssignmentService.savePrimaryAssignment(primaryAssignmentDTO, primaryAssignmentBefore.getEffectiveStartDate(),
				primaryAssignmentBefore.getEffectiveEndDate());
		
		add by icha, ticket 15111616041796 - Perbaikan transfer flag boarding, 29-Feb-2016
		HousingAllowanceDTO housingAllowanceDTO = housingAllowanceServiceImpl
				.getHousingAllowanceByEffectiveOnDate(primaryAssignmentDTO.getPersonId(),
						primaryAssignmentDTO.getCompanyId(), DateUtil.truncate(new Date()));
		Date dateStartBefore = null;
		Date dateEndBefore = null;
		
		if (housingAllowanceDTO == null) {
			housingAllowanceDTO = new HousingAllowanceDTO();
		} else {
			dateStartBefore = housingAllowanceDTO.getEffectiveStartDate();
			dateEndBefore = housingAllowanceDTO.getEffectiveEndDate();
		}
		
		housingAllowanceDTO.setCompanyId(primaryAssignmentDTO.getCompanyId());
		housingAllowanceDTO.setCreatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : Long.parseLong(securityServiceImpl
						.getSecurityProfile().getEmployeeNumber(),10));
		housingAllowanceDTO.setCreationDate(new Date());
		housingAllowanceDTO.setEffectiveStartDate(primaryAssignmentDTO.getEffectiveStartDate());
		housingAllowanceDTO.setEffectiveEndDate(primaryAssignmentDTO.getEffectiveEndDate());
		housingAllowanceDTO.setHouseAllowance(HousingAllowance.NONE.name());
		housingAllowanceDTO.setLastUpdateDate(new Date());
		housingAllowanceDTO.setLastUpdatedBy(securityServiceImpl
				.getSecurityProfile() == null ? -1L : Long.parseLong(securityServiceImpl
						.getSecurityProfile().getEmployeeNumber(),10));
		housingAllowanceDTO.setPersonId(primaryAssignmentDTO.getPersonId());
		housingAllowanceDTO.setHouseAllowance(flagBoarding);
//		housingAllowanceDTO.setHousingAllowanceId(null);
		housingAllowanceServiceImpl.save(housingAllowanceDTO,  dateStartBefore, dateEndBefore);
		 end by icha, ticket 15111616041796 - Perbaikan transfer flag boarding, 29-Feb-2016 
		
		//change By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
		//Remark By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
		//PersonDTO person = getPersonById(primaryAssignmentDTO.getPersonId(), DateUtil.truncate(new Date()), primaryAssignmentDTO.getCompanyId());
		PersonDTO person = getPersonByIdOrganizationActiveandInactive(primaryAssignmentDTO.getPersonId(), DateUtil.truncate(new Date()), primaryAssignmentDTO.getCompanyId());
		//end change By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
		
		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = "";
		if (primaryAssignmentDTO.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.DEFINITIVE.name())) {
			eventName = EventType.IRRPROM_DEFINITIVE.name();
		} else if (primaryAssignmentDTO.getAssignmentStatus().equalsIgnoreCase(AssignmentStatus.ACTING.name())) {
			eventName = EventType.IRRPROM_ACTING.name();
		}
		peopleHistory.setEventName(eventName);
		String description = Labels.getLabel(
				"pea.hasBeenPromotedIrregularly",
				new Object[] { person.getEmployeeNumber(), person.getFullName(), primaryAssignmentDTO.getAssignmentStatus(),
						DateUtil.format(primaryAssignmentBefore.getEffectiveStartDate()) });
		peopleHistory.setMessageDescription(setDescriptionPeopleHistory(eventName, description, primaryAssignmentBefore, primaryAssignmentDTO));
		peopleHistoryServiceImpl.savePeopleHistory(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(primaryAssignmentDTO.getPersonId());

		// insert leave entitlement source
		leaveEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getEffectiveStartDate(),
				PeopleSource.PERSONNEL_ADMIN.name(), Reason.TRANSFER_BY_COMPANY.name(), primaryAssignmentDTO.getCompanyId());

		// insert benefit entitlement source
		benefitEntitlementServiceAdapterImpl.insertEntitlementSource(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getEffectiveStartDate(),
				PeopleSource.PERSONNEL_ADMIN.name(), Reason.TRANSFER_BY_COMPANY.name(), primaryAssignmentDTO.getCompanyId(), primaryAssignmentDTO.getAssignmentId());

		// create promotion mpp journal when changing job or organization
		if (primaryAssignmentBefore.getJobId() != primaryAssignmentDTO.getJobId()
				|| primaryAssignmentBefore.getOrganizationId() != primaryAssignmentDTO.getOrganizationId()) {

			MppJournalTransactionDTO journalReservedSource = new MppJournalTransactionDTO();
			journalReservedSource.setMppTrnDate(DateUtil.truncate(new Date()));
			journalReservedSource.setSource(PeopleSource.PROMOTION.name());
			journalReservedSource.setSourceTrnId(primaryAssignmentBefore.getRefId());
			journalReservedSource.setOrganizationId(primaryAssignmentBefore.getOrganizationId());
			journalReservedSource.setJobId(primaryAssignmentBefore.getJobId());
			journalReservedSource.setPersonId(primaryAssignmentBefore.getPersonId());
			journalReservedSource.setReserved(-1L);
			journalReservedSource.setExisting(0L);
			journalReservedSource.setRemark("Promotion Approval");
			mppServiceImpl.createJournal(journalReservedSource, primaryAssignmentBefore.getCompanyId());

			MppJournalTransactionDTO journalExistedSource = new MppJournalTransactionDTO();
			journalExistedSource.setMppTrnDate(primaryAssignmentDTO.getEffectiveStartDate());
			journalExistedSource.setSource(PeopleSource.PROMOTION.name());
			journalExistedSource.setSourceTrnId(primaryAssignmentBefore.getRefId());
			journalExistedSource.setOrganizationId(primaryAssignmentBefore.getOrganizationId());
			journalExistedSource.setJobId(primaryAssignmentBefore.getJobId());
			journalExistedSource.setPersonId(primaryAssignmentBefore.getPersonId());
			journalExistedSource.setReserved(1L);
			journalExistedSource.setExisting(-1L);
			journalExistedSource.setRemark("Promotion Effective Date");
			mppServiceImpl.createJournal(journalExistedSource, primaryAssignmentBefore.getCompanyId());

			MppJournalTransactionDTO journalReservedDestination = new MppJournalTransactionDTO();
			journalReservedDestination.setMppTrnDate(DateUtil.truncate(new Date()));
			journalReservedDestination.setSource(PeopleSource.PROMOTION.name());
			journalReservedDestination.setSourceTrnId(primaryAssignmentDTO.getRefId());
			journalReservedDestination.setOrganizationId(primaryAssignmentDTO.getOrganizationId());
			journalReservedDestination.setJobId(primaryAssignmentDTO.getJobId());
			journalReservedDestination.setPersonId(primaryAssignmentDTO.getPersonId());
			journalReservedDestination.setReserved(1L);
			journalReservedDestination.setExisting(0L);
			journalReservedDestination.setRemark("Promotion Approval");
			mppServiceImpl.createJournal(journalReservedDestination, primaryAssignmentDTO.getCompanyId());

			MppJournalTransactionDTO journalExistedDestination = new MppJournalTransactionDTO();
			journalExistedDestination.setMppTrnDate(primaryAssignmentDTO.getEffectiveStartDate());
			journalExistedDestination.setSource(PeopleSource.PROMOTION.name());
			journalExistedDestination.setSourceTrnId(primaryAssignmentDTO.getRefId());
			journalExistedDestination.setOrganizationId(primaryAssignmentDTO.getOrganizationId());
			journalExistedDestination.setJobId(primaryAssignmentDTO.getJobId());
			journalExistedDestination.setPersonId(primaryAssignmentDTO.getPersonId());
			journalExistedDestination.setReserved(-1L);
			journalExistedDestination.setExisting(1L);
			journalExistedDestination.setRemark("Promotion Effective Date");
			mppServiceImpl.createJournal(journalExistedDestination, primaryAssignmentDTO.getCompanyId());
		}

		// interfacing to other system
		Date effectiveDate = primaryAssignmentDTO.getEffectiveStartDate();
		String employeePhoneNumber = getEmployeePhoneNumberActive(
				communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(primaryAssignmentDTO.getPersonId(), primaryAssignmentDTO.getCompanyId()), effectiveDate);
		interfaceBufferServiceImpl.insertInterfaceBuffer(person.getPersonId(), person.getEmployeeNumber(), person.getFullName(), person.getEmploymentCategory(),
				primaryAssignmentDTO.getEffectiveStartDate(), employeePhoneNumber, primaryAssignmentDTO.getJobId(), person.getPrimaryAssignmentDTO().getOrganizationId(),
				primaryAssignmentDTO.getOrganizationId(), false, person.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void updatePersonalInformation(PersonDTO personDTO, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception {
		String reasonLeave = null;
		String reasonBenefit = null;

		personalInformationService.savePersonalInformation(personDTO, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);

		// create future history for internal married
		if (personDTO.isChangeMaritalStatus() || personDTO.isChangeMarriedWith()) {
			if (personDTO.getInternalMarriedWith() != null) {
				createSpouse(personDTO);
			}
		}

		updateLastUpdatePerson(personDTO.getPersonId());

		if (personDTO.isChangeMarriedWith()) {
			reasonBenefit = Reason.CHANGE_INTERNAL_MARRIED.name();
			benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(),
					reasonBenefit, personDTO.getCompanyId(), null);
		}

		if (personDTO.isChangeMaritalStatus()) {
			reasonLeave = Reason.MARITAL_STATUS.name();
			reasonBenefit = Reason.MARITAL_STATUS.name();
			leaveEntitlementServiceAdapterImpl.insertEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(),
					reasonLeave, personDTO.getCompanyId());
			benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(),
					reasonBenefit, personDTO.getCompanyId(), null);
		}

		if (personDTO.isChangeReligion()) {
			reasonLeave = Reason.RELIGION.name();
			leaveEntitlementServiceAdapterImpl.insertEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(),
					reasonLeave, personDTO.getCompanyId());
		}

		if (personDTO.isChangeHomebase()) {
			reasonBenefit = Reason.HOME_BASE.name();
			benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(),
					reasonBenefit, personDTO.getCompanyId(), null);
		}

		// interfacing to other system
		if (personDTO.getPeopleType().equalsIgnoreCase(PeopleType.EMPLOYEE.name())) {
			Date effectiveDate = personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate();
			String employeePhoneNumber = getEmployeePhoneNumberActive(
					communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(personDTO.getPersonId(), personDTO.getCompanyId()), effectiveDate);
			interfaceBufferServiceImpl.insertInterfaceBuffer(personDTO.getPersonId(), personDTO.getEmployeeNumber(), personDTO.getFullName(), personDTO
					.getEmploymentCategory(), personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate(), employeePhoneNumber, personDTO.getPrimaryAssignmentDTO()
					.getJobId(), personDTO.getPrimaryAssignmentDTO().getOrganizationId(), null, false, personDTO.getCompanyId(), EmployeeFlag.MUTTATION.getDescription());
		}

		sendNotificationUpdateData(personDTO);
	}*/

	@Override
	@Transactional(readOnly = false)
	public void sendNotificationUpdateData(PersonDTO personDTO) throws Exception {
		if (personDTO != null) {
			List<PersonAssignmentDTO> listReceiver = new ArrayList<PersonAssignmentDTO>();

			if (securityServiceImpl.getSecurityProfile() != null && securityServiceImpl.getSecurityProfile().getPersonId().equals(personDTO.getPersonId())) {
				if (personDTO.getPrimaryAssignmentDTO().getBranchId() != null) {
					listReceiver = selectPersonByAssignment(null, null, personDTO.getPrimaryAssignmentDTO().getBranchId(), "HCAdm", null, null, null);
				}
			} else {
				PersonAssignmentDTO employee = new PersonAssignmentDTO();
				employee.setFullName(personDTO.getFullName());
				employee.setPersonUUID(personDTO.getPersonUUID());
				listReceiver.add(employee);
			}

			// send notification
			TemplateMessage templateMessage = new TemplateMessage();
			templateMessage
					.setTemplateSubject("Personnel Administration - ( ${person.employeeNumber!} - ${person.fullName!} ) - ${(person.effectiveStartDate?date)!} to ${(person.effectiveEndDate?date)!}");
			templateMessage.setTemplateContent("<p>Dear ${receiverName!},</p>" + "<p>Kindly be informed there are changes Personal Information.</p>"
					+ "<p>If the information is incorrect, please contact me. Thank you for your attention.</p>" + "<p>Regards,</p>" + "<p>${updaterName!}</p>");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("person", personDTO);
			map.put("updaterName", securityServiceImpl.getSecurityProfile() != null ? securityServiceImpl.getSecurityProfile().getFullName() : "SYSTEM");

			for (PersonAssignmentDTO receiver : listReceiver) {
				map.put("receiverName", receiver.getFullName());
				Message message = notificationManager.templateMessageResolver(templateMessage, map);
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setSubject(message.getSubject());
				notificationMessage.setContentMessage(message.getContent());
				notificationMessage.setFromId(securityServiceImpl.getSecurityProfile() != null ? securityServiceImpl.getSecurityProfile().getPersonUUID() : UUID
						.fromString(ReferencePersonnelAdministration.APPROVER_SYSTEM.getReference()));
				notificationMessage.setToId(receiver.getPersonUUID());
				notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
				notificationMessage.setReceivedTime(new Date());
				notificationManager.insertNewMessage(notificationMessage);
			}
		}
	}

	/*@Override
	@Transactional(readOnly = false)
	public void createSpouse(PersonDTO personDTO) throws Exception {
		Long companySpouse = getCompanyPerson(personDTO.getInternalMarriedWith(), null);
		if (companySpouse != null) {
			PersonalInformationDTO spouse = personalInformationService.getPersonalInformationByEffectiveOnDate(personDTO.getInternalMarriedWith(), companySpouse, null,
					personDTO.getEffectiveStartDate());

			if (spouse != null && personalInformationService.isCurrent(spouse) && !personalInformationService.hasFuture(spouse)) {
				Date dateFromBefore = spouse.getEffectiveStartDate();
				Date dateToBefore = spouse.getEffectiveEndDate();
				String maritalStatusBefore = spouse.getMaritalStatus();
				spouse.setMaritalDate(personDTO.getMaritalDate());
				spouse.setMaritalStatus(personDTO.getMaritalStatus());
				spouse.setInternalMarriedWith(personDTO.getPersonId());
				spouse.setEffectiveStartDate(personDTO.getEffectiveStartDate());
				spouse.setEffectiveEndDate(DateUtil.MAX_DATE);
				spouse.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				spouse.setLastUpdateDate(new Date());
				personalInformationService.save(spouse, dateFromBefore, dateToBefore);

				updateLastUpdatePerson(spouse.getPersonId());

				// if marital status change
				if (!maritalStatusBefore.equalsIgnoreCase(spouse.getMaritalStatus())) {
					leaveEntitlementServiceAdapterImpl.insertEntitlementSource(spouse.getPersonId(), spouse.getEffectiveStartDate(), PeopleSource.PERSONNEL_ADMIN.name(),
							Reason.MARITAL_STATUS.name(), spouse.getCompanyId());

					benefitEntitlementServiceAdapterImpl.insertEntitlementSource(spouse.getPersonId(), spouse.getEffectiveStartDate(),
							PeopleSource.PERSONNEL_ADMIN.name(), Reason.MARITAL_STATUS.name(), spouse.getCompanyId(), null);
				}

				// interfacing to other system
				Date effectiveDate = DateUtil.truncate(new Date());
				PersonDTO spouseData = getPersonById(spouse.getPersonId(), effectiveDate, spouse.getCompanyId());
				String employeePhoneNumber = getEmployeePhoneNumberActive(
						communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(spouseData.getPersonId(), spouseData.getCompanyId()), effectiveDate);
				interfaceBufferServiceImpl.insertInterfaceBuffer(spouseData.getPersonId(), spouseData.getEmployeeNumber(), spouseData.getFullName(), spouseData
						.getEmploymentCategory(), spouseData.getPrimaryAssignmentDTO().getEffectiveStartDate(), employeePhoneNumber, spouseData.getPrimaryAssignmentDTO()
						.getJobId(), spouseData.getPrimaryAssignmentDTO().getOrganizationId(), null, false, spouseData.getPrimaryAssignmentDTO().getCompanyId(),
						EmployeeFlag.MUTTATION.getDescription());

				// send notification
				TemplateMessage templateMessage = new TemplateMessage();
				templateMessage
						.setTemplateSubject("Personnel Administration - ( ${person.employeeNumber!} - ${person.fullName!} ) - ${(person.effectiveStartDate?date)!} to ${(person.effectiveEndDate?date)!}");
				templateMessage.setTemplateContent("<p>Dear ${person.fullName!},</p>" + "<p>Kindly be informed there are changes of your Personal Information.</p>"
						+ "<p>if the information is incorrect, please contact me. Thank you for your attention.</p>" + "<p>Regards,</p>" + "<p>${updaterName!}</p>");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("person", spouseData);
				map.put("updaterName", securityServiceImpl.getSecurityProfile().getFullName());
				Message message = notificationManager.templateMessageResolver(templateMessage, map);
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setSubject(message.getSubject());
				notificationMessage.setContentMessage(message.getContent());
				notificationMessage.setFromId(securityServiceImpl.getSecurityProfile().getPersonUUID());
				notificationMessage.setToId(spouseData.getPersonUUID());
				notificationMessage.setMessageType(MessageType.FYI_MESSAGE.getCode());
				notificationMessage.setReceivedTime(new Date());
				notificationManager.insertNewMessage(notificationMessage);
			}
		}
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void adjustLeaveEntitlement(List<LeaveEntitlementDTO> leaveEntitlements) {
		Long lastUpdatedBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date lastUpdateDate = new Date();
		for (LeaveEntitlementDTO leaveEntitlementDTO : leaveEntitlements) {
			leaveEntitlementDTO.setLastUpdateDate(lastUpdateDate);
			leaveEntitlementDTO.setLastUpdatedBy(lastUpdatedBy);
			leaveEntitlementServiceAdapterImpl.updateEntitlement(leaveEntitlementDTO);
		}
		if (leaveEntitlements.size() > 0) {
			updateLastUpdatePerson(leaveEntitlements.get(0).getPersonId());
		}
	}*/

	@Override
	public List<PersonDTO> getPersonActiveByChildBirthdate(Long companyId) {
		PersonCriteriaDTO personCriteriaDTO = new PersonCriteriaDTO();
		personCriteriaDTO.setEffectiveOnDate(DateUtil.truncate(new Date()));
		personCriteriaDTO.setPeopleType(PeopleType.EMPLOYEE.name());
		return personFinder.getPersonActiveByChildBirthdate(personCriteriaDTO, companyId);
	}

	/*@Override
	@Transactional(readOnly = false)
	public void adjustBenefitEntitilement(List<BenefitEntitlementDTO> benefitEntitlements) {
		Long lastUpdatedBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date lastUpdateDate = new Date();
		for (BenefitEntitlementDTO benefitEntitlementDTO : benefitEntitlements) {
			benefitEntitlementDTO.setLastUpdateDate(lastUpdateDate);
			benefitEntitlementDTO.setLastUpdatedBy(lastUpdatedBy);
			benefitEntitlementServiceAdapterImpl.updateEntitlement(benefitEntitlementDTO);
		}

		if (benefitEntitlements.size() > 0) {
			updateLastUpdatePerson(benefitEntitlements.get(0).getPersonId());
		}
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void updateContact(Long personId, Long companyId, ContactsDTO contacts) throws Exception {
		Date effectiveStartDate = DateUtil.truncate(new Date());
		contactServiceImpl.saveContact(personId, companyId, contacts.getListContact());
		updateLastUpdatePerson(personId);
		PersonDTO personDTO = getPersonalData(personId, companyId, effectiveStartDate);
		sendNotificationUpdateData(personDTO);
		if (personDTO != null && personDTO.getPeopleType().equalsIgnoreCase(PeopleType.EMPLOYEE.name())) {
			if (contacts.isChangeGenderChild() || contacts.isChangeMaritalStatus() || contacts.isChangeNumberOfChild() || contacts.isChangeFamilyMember()) {
				leaveEntitlementServiceAdapterImpl.insertEntitlementSource(personId, effectiveStartDate, PeopleSource.PERSONNEL_ADMIN.name(),
						Reason.PERSONNEL_CONTACT_CHANGE.name(), companyId);

				benefitEntitlementServiceAdapterImpl.insertEntitlementSource(personId, effectiveStartDate, PeopleSource.PERSONNEL_ADMIN.name(),
						Reason.PERSONNEL_CONTACT_CHANGE.name(), companyId, null);
			}
		}
	}*/

	private String getEmployeePhoneNumberActive(List<Communication> communications, Date effectiveDate) {

		if (communications != null) {
			for (Communication communication : communications) {
				if ((communication.getCommType().equals(ContactType.HOME_PHONE.name()) || communication.getCommType().equals(ContactType.MOBILE_PHONE.name()))
				// && (communication.getStartDate().before(effectiveDate) ||
				// communication.getStartDate().equals(effectiveDate))
				// && (communication.getEndDate().after(effectiveDate) ||
				// communication.getEndDate().equals(effectiveDate))
				) {
					return communication.getCommInformation();
				}
			}
		}
		return null;
	}

	/*@Override
	@Transactional(readOnly = false)
	public void deleteFuturePersonalInfo(PersonDTO personDTO) throws Exception {
		leaveEntitlementServiceAdapterImpl.deleteEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), personDTO.getCompanyId());
		benefitEntitlementServiceAdapterImpl.deleteEntitlementSource(personDTO.getPersonId(), personDTO.getEffectiveStartDate(), personDTO.getCompanyId());
		interfaceBufferServiceImpl.deleteInterfaceBuffer(personDTO.getPersonId(), personDTO.getCompanyId(), personDTO.getPrimaryAssignmentDTO().getEffectiveStartDate());
		personalInformationService.delete(personDTO);
		// delete future internal married
		PersonalInformationDTO personalInfoCurrent = personalInformationService.getPersonalInformationByEffectiveOnDate(personDTO.getPersonId(),
				personDTO.getCompanyId(), null, DateUtil.truncate(new Date()));
		if ((personalInfoCurrent.getInternalMarriedWith() == null && personDTO.getInternalMarriedWith() != null)
				|| (personalInfoCurrent.getInternalMarriedWith() != null && personDTO.getInternalMarriedWith() != null && !personalInfoCurrent.getInternalMarriedWith()
						.equals(personDTO.getInternalMarriedWith()))) {
			Long companySpouse = getCompanyPerson(personDTO.getInternalMarriedWith(), DateUtil.truncate(new Date()));
			if (companySpouse != null) {
				PersonalInformationDTO spouseInfo = personalInformationService.getPersonalInformationByEffectiveOnDate(personDTO.getInternalMarriedWith(), companySpouse,
						null, DateUtil.MAX_DATE);
				leaveEntitlementServiceAdapterImpl.deleteEntitlementSource(spouseInfo.getPersonId(), spouseInfo.getEffectiveStartDate(), spouseInfo.getCompanyId());
				benefitEntitlementServiceAdapterImpl.deleteEntitlementSource(spouseInfo.getPersonId(), spouseInfo.getEffectiveStartDate(), spouseInfo.getCompanyId());
				interfaceBufferServiceImpl.deleteInterfaceBuffer(spouseInfo.getPersonId(), spouseInfo.getCompanyId(), DateUtil.truncate(spouseInfo.getCreationDate()));
				personalInformationService.delete(spouseInfo);
			}
		}
	}*/

	/*@Override
	@Transactional(readOnly = false)
	public void reverseTermination(PersonDTO personDTO) {
		PeopleTypeDTO peopleTypeDTO = peopleTypeService.getPeopleTypeByEffectiveOnDate(personDTO.getPersonId(), personDTO.getCompanyId(), DateUtil.MAX_DATE);
		peopleTypeService.delete(peopleTypeDTO);

		PrimaryAssignmentDTO primaryAssignmentBefore = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(personDTO.getPersonId(), personDTO.getCompanyId(),
				DateUtil.MAX_DATE);
		primaryAssignmentService.delete(primaryAssignmentBefore);

		interfaceBufferServiceImpl.deleteInterfaceBuffer(primaryAssignmentBefore.getPersonId(), primaryAssignmentBefore.getCompanyId(),
				primaryAssignmentBefore.getEffectiveStartDate());

		// update exit clearance
		ExitClearance updateExitClearanceStatus = new ExitClearance();
		updateExitClearanceStatus.setStatus(ExitClearanceStatus.CANCELED.name());
		updateExitClearanceStatus.setPersonId(personDTO.getPersonId());
		updateExitClearanceStatus.setCompanyId(personDTO.getCompanyId());
		updateExitClearanceStatus.setTerminationTrxId(primaryAssignmentBefore.getRefId());
		exitClearanceServiceImpl.updateByExample(updateExitClearanceStatus);

		// update termination status
		terminationServiceAdapterImpl.updateStatusTermination(primaryAssignmentBefore.getRefId(), null, "REVERSED");

		// create mpp journal
		MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
		journalExisted.setMppTrnDate(primaryAssignmentBefore.getEffectiveStartDate());
		journalExisted.setSource(PeopleSource.PERSONNEL_ADMIN.name());
		journalExisted.setSourceTrnId(primaryAssignmentBefore.getRefId());
		journalExisted.setOrganizationId(primaryAssignmentBefore.getOrganizationId());
		journalExisted.setJobId(primaryAssignmentBefore.getJobId());
		journalExisted.setPersonId(primaryAssignmentBefore.getPersonId());
		journalExisted.setReserved(0L);
		journalExisted.setExisting(1L);
		journalExisted.setRemark("Reverse Termination");
		mppServiceImpl.createJournal(journalExisted, primaryAssignmentBefore.getCompanyId());

		PeopleHistory peopleHistory = new PeopleHistory();
		peopleHistory.setEventName(EventType.REVERSED_TERMINATION.name());
		String messageDescription = Labels.getLabel("pea.reversedTermination", new Object[] { personDTO.getEmployeeNumber(), personDTO.getFullName(),
				PeopleType.EX_EMPLOYEE.getDescription(), FormatDateUI.formatDate(new Date()) });
		peopleHistory.setMessageDescription(messageDescription);
		peopleHistoryServiceImpl.savePeopleHistory(personDTO.getPersonId(), personDTO.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(personDTO.getPersonId());
	}*/

	/*@Override
	public String getLastEducation(Long personId, Long companyId) {
		List<String> educations = personFinder.getLastEducation(personId, companyId);
		if (educations.size() > 0) {
			KeyValue keyValue = lookupServiceImpl.getLookup(ReferencePersonnelAdministration.LOOKUP_EDUCATION_LEVEL.getReference(), educations.get(0).toString(),
					securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			return keyValue != null ? keyValue.getDescription().toString() : null;
		}
		return null;
	}*/

	/*@Override
	public Date getValidHireDate(Date approvedDate) {
		boolean validDate = false;
		if (DateUtil.truncate(approvedDate).before(DateUtil.truncate(new Date()))) {
			approvedDate = DateUtil.truncate(new Date());
		}
		LocalDate approved = new LocalDate(approvedDate.getTime());
		int dayOfMonth = approved.get(DateTimeFieldType.dayOfMonth());
		LocalDate maxResult = approved;
		LocalDate minresult = approved;
		List<KeyValue> listValidHireDate = lookupServiceImpl.getLookups(ReferencePersonnelAdministration.LOOKUP_VALID_HIRE_DATE.getReference(), securityServiceImpl
				.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		List<Integer> validHireDate = new ArrayList<Integer>();
		for (KeyValue keyValue : listValidHireDate) {
			validHireDate.add(Integer.parseInt(keyValue.getKey().toString()));
		}

		for (Integer val : validHireDate) {
			if (dayOfMonth == val.intValue() && (approvedDate.after(DateUtil.truncate(new Date())) || approvedDate.equals(DateUtil.truncate(new Date())))) {
				validDate = true;
				break;
			} else if (minresult.getDayOfMonth() >= val.intValue()) {
				minresult = minresult.withDayOfMonth(val);
			}

			// if (dayOfMonth <= val.intValue()) {
			if (maxResult.getDayOfMonth() == dayOfMonth) {
				maxResult = maxResult.withDayOfMonth(val);
			} else if (maxResult.getDayOfMonth() <= val.intValue()) {
				maxResult = maxResult.withDayOfMonth(val);
			}
			// }
		}

		if (validDate) {
			return approvedDate;
		} else {
			if (DateUtil.truncate(maxResult.toDate()).after(DateUtil.truncate(new Date())) || DateUtil.truncate(maxResult.toDate()).equals(DateUtil.truncate(new Date()))) {
				return maxResult.toDate();
			} else {
				return minresult.plusMonths(1).toDate();
			}
		}
	}*/

	@Override
	public boolean isActivePerson(PersonDTO personDTO, Date effectiveOnDate) {
		PeopleTypeDTO peopleTypeDTO = peopleTypeService.getPeopleTypeByEffectiveOnDate(personDTO.getPersonId(), personDTO.getCompanyId(), DateUtil.truncate(new Date()));
		if (peopleTypeDTO != null) {
			if ((!peopleTypeDTO.getPeopleType().equalsIgnoreCase(PeopleType.EX_EMPLOYEE.name()) && !peopleTypeDTO.getPeopleType().equalsIgnoreCase(
					PeopleType.EX_INTERNSHIP.name()))) {
				PeopleTypeDTO futurePeopleType = peopleTypeService.getPeopleTypeByEffectiveOnDate(personDTO.getPersonId(), personDTO.getCompanyId(),
						DateUtil.truncate(effectiveOnDate));
				if ((!futurePeopleType.getPeopleType().equalsIgnoreCase(PeopleType.EX_EMPLOYEE.name()) && !futurePeopleType.getPeopleType().equalsIgnoreCase(
						PeopleType.EX_INTERNSHIP.name()))) {
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}

	/*@Override
	@Transactional(readOnly = false)
	public void terminateEmployee(PersonDTO person, Date terminationDate, Long refId, boolean isTransferWithinGroup, TerminationReason terminationReason)
			throws Exception {
		PeopleTypeDTO peopleTypeBefore = peopleTypeService.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), terminationDate);

		if (peopleTypeBefore != null) {

			PeopleTypeDTO futurePeopleType = peopleTypeService.getFuturePeopleType(person.getPersonId(), person.getCompanyId());

			if (futurePeopleType != null) {
				// delete people type future
				peopleTypeService.deleteFuturePeopleType(person.getPersonId(), person.getCompanyId());

				PeopleTypeDTO currentType = peopleTypeService.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), DateUtil.truncate(new Date()));

				co.id.fifgroup.personneladmin.domain.PeopleType peopleType = new co.id.fifgroup.personneladmin.domain.PeopleType();
				peopleType.setPeopleTypeId(currentType.getPeopleTypeId());
				peopleType.setEffectiveEndDate(DateUtil.MAX_DATE);
				peopleType.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				peopleType.setLastUpdateDate(new Date());
				peopleTypeMapper.updateByPrimaryKeySelective(peopleType);

				// update probation review
				ProbationReviewExample example = new ProbationReviewExample();
				example.createCriteria().andCompanyIdEqualTo(person.getCompanyId()).andPersonIdEqualTo(person.getPersonId());

				ProbationReview record = new ProbationReview();
				record.setProbationStatus(ProbationStatus.OPEN.toString());
				record.setLastUpdateDate(new Date());
				record.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());

				probationReviewMapper.updateByExampleSelective(record, example);

				// Get People Type as Of Termination Date
				peopleTypeBefore = peopleTypeService.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), terminationDate);
			}

			PeopleTypeDTO peopleType = new PeopleTypeDTO();
			peopleType.setPersonId(person.getPersonId());
			peopleType.setCompanyId(person.getCompanyId());
			peopleType.setPeopleType(co.id.fifgroup.personneladmin.constant.PeopleType.EX_EMPLOYEE.name());
			peopleType.setEmploymentCategory(person.getEmploymentCategory());
			peopleType.setSource(PeopleSource.TERMINATION.name());
			peopleType.setRefId(refId);
			peopleType.setAffco(peopleTypeBefore.getAffco());
			peopleType.setCanceled(false);
			peopleType.setEffectiveStartDate(terminationDate);
			peopleType.setEffectiveEndDate(DateUtil.MAX_DATE);
			peopleTypeService.savePeopleType(peopleType, peopleTypeBefore.getEffectiveStartDate(), peopleTypeBefore.getEffectiveEndDate());

			Date dateFromBefore = person.getPrimaryAssignmentDTO().getEffectiveStartDate();
			Date dateToBefore = person.getPrimaryAssignmentDTO().getEffectiveEndDate();
			PrimaryAssignmentDTO primaryAssignment = person.getPrimaryAssignmentDTO();
			primaryAssignment.setPersonId(person.getPersonId());
			primaryAssignment.setCompanyId(person.getCompanyId());
			primaryAssignment.setActionType(ActionType.TERMINATION.name());
			primaryAssignment.setSource(PeopleSource.TERMINATION.name());
			primaryAssignment.setRefId(refId);
			primaryAssignment.setEffectiveStartDate(terminationDate);
			primaryAssignment.setEffectiveEndDate(DateUtil.MAX_DATE);
			primaryAssignmentService.savePrimaryAssignment(primaryAssignment, dateFromBefore, dateToBefore);

			String messageDescription = "";
			PeopleHistory peopleHistory = new PeopleHistory();
			if (terminationReason != null && terminationReason.equals(TerminationReason.PROBATION_FAILED)) {
				peopleHistory.setEventName(EventType.FAILED_PROBATION.name());
				messageDescription = Labels.getLabel("pea.probationFailed",
						new Object[] { person.getEmployeeNumber(), person.getFullName(), FormatDateUI.formatDate(terminationDate) });
			} else {
				peopleHistory.setEventName(EventType.TERMINATION.name());
				messageDescription = Labels
						.getLabel(
								"pea.terminatedOn",
								new Object[] { person.getEmployeeNumber(), person.getFullName(), PeopleType.EMPLOYEE.getDescription(),
										FormatDateUI.formatDate(terminationDate) });
			}
			peopleHistory.setMessageDescription(messageDescription);
			if (!isNullOrEmpty(person.getDocumentPath())) {
				peopleHistory.setDocumentPath(person.getDocumentPath());
				peopleHistory.setDocumentType(DocumentType.TERMINATION_AGREEMENT.name());
			}
			peopleHistoryServiceImpl.savePeopleHistory(person.getPersonId(), person.getCompanyId(), peopleHistory);

			updateLastUpdatePerson(person.getPersonId());

			// create journal
			MppJournalTransactionDTO journalReserved = new MppJournalTransactionDTO();
			journalReserved.setMppTrnDate(DateUtil.truncate(new Date()));
			journalReserved.setSource(PeopleSource.TERMINATION.name());
			journalReserved.setSourceTrnId(primaryAssignment.getRefId());
			journalReserved.setOrganizationId(primaryAssignment.getOrganizationId());
			journalReserved.setJobId(primaryAssignment.getJobId());
			journalReserved.setPersonId(primaryAssignment.getPersonId());
			journalReserved.setReserved(-1L);
			journalReserved.setExisting(0L);
			journalReserved.setRemark("Termination Approval");
			mppServiceImpl.createJournal(journalReserved, primaryAssignment.getCompanyId());

			MppJournalTransactionDTO journalExisted = new MppJournalTransactionDTO();
			journalExisted.setMppTrnDate(primaryAssignment.getEffectiveStartDate());
			journalExisted.setSource(PeopleSource.TERMINATION.name());
			journalExisted.setSourceTrnId(primaryAssignment.getRefId());
			journalExisted.setOrganizationId(primaryAssignment.getOrganizationId());
			journalExisted.setJobId(primaryAssignment.getJobId());
			journalExisted.setPersonId(primaryAssignment.getPersonId());
			journalExisted.setReserved(1L);
			journalExisted.setExisting(-1L);
			journalExisted.setRemark("Termination End Date");
			mppServiceImpl.createJournal(journalExisted, primaryAssignment.getCompanyId());

			// interfacing to other system
			Date effectiveDate = terminationDate;
			String employeePhoneNumber = getEmployeePhoneNumberActive(
					communicationServiceImpl.getCommunicationsByPersonIdAndCompanyId(person.getPersonId(), person.getCompanyId()), effectiveDate);

			// for employee 'RESIGN' only (is not 'TRANSFER WITHIN GROUP')
			// 14090116321650_Modifikasi Create CSV to LDAP untuk mutasi
			// company_WIJ
			if (!isTransferWithinGroup) {
				interfaceBufferServiceImpl.insertInterfaceBuffer(person.getPersonId(), person.getEmployeeNumber(), person.getFullName(), person.getEmploymentCategory(),
						person.getPrimaryAssignmentDTO().getEffectiveStartDate(), employeePhoneNumber, person.getPrimaryAssignmentDTO().getJobId(), person
								.getPrimaryAssignmentDTO().getOrganizationId(), null, false, person.getCompanyId(), EmployeeFlag.RESIGN.getDescription());
			}

			PersonalInformationDTO personalInformation = personalInformationService.getPersonalInformationByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
					null, DateUtil.truncate(terminationDate));
			if (personalInformation != null && personalInformation.getInternalMarriedWith() != null && !isTransferWithinGroup) {
				benefitEntitlementServiceAdapterImpl.insertEntitlementSource(person.getPersonId(), DateUtil.truncate(terminationDate),
						PeopleSource.PERSONNEL_ADMIN.name(), Reason.TERMINATE_INTERNAL_MARRIED_WITH.name(), person.getCompanyId(), null);

			}

			// if transfer within group, do transferWithin group to loan
			if (isTransferWithinGroup) {
				PersonTransferWithinGroupDTO criteria = new PersonTransferWithinGroupDTO();
				criteria.setPersonId(person.getPersonId());
				criteria.setCompanyId(person.getCompanyId());
				List<PersonTransferWithinGroupDTO> list = terminationServiceAdapterImpl.selectByExample(criteria);
				loanProcessServiceImpl.transferWithinGroup(person.getPersonId(), person.getCompanyId(), list.get(0).getDestinationCompanyId());
			}
		}
	}*/

	@Override
	public List<PersonAssignmentDTO> selectPersonByAssignmentForLov(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId,
			String employeeNumber, String employeeName, int limit, int offset) {
		return personFinder.selectPersonByAssignmentForLov(jobId, organizationId, branchId, roleCode, personId, companyId, employeeNumber, employeeName, new RowBounds(
				offset, limit));
	}

	@Override
	public Integer countPersonByAssignmentForLov(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId, String employeeNumber,
			String employeeName) {
		return personFinder.countPersonByAssignmentForLov(jobId, organizationId, branchId, roleCode, personId, companyId, employeeNumber, employeeName);
	}

	/*@Override
	public List<PersonDTO> getPersonByCompanyForPayroll(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.selectPersonByCompanyForPayroll(criteria, companyId);
	}*/

	@Override
	@Transactional(readOnly = true)
	public List<Long> getPersonIdsByBusinessGroup(PersonCriteriaDTO criteria, Long groupId) {
		return personFinder.selectPersonIdsByBusinessGroup(criteria, groupId);
	}

	@Override
	public PersonDTO getPersonById(Long personId, Date effectiveOnDate, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setCompanyId(companyId);
		return personFinder.selectPersonById(personId, criteria);
	}

	@Override
	public PersonDTO getPersonByPersonUUID(UUID personUUID, Date effectiveOnDate, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());
		criteria.setCompanyId(companyId);
		return personFinder.selectPersonByPersonUUID(personUUID, criteria);
	}

	//add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review
	@Override
	public PersonDTO getPersonByIdOrganizationActiveandInactive(Long personId, Date effectiveOnDate, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setCompanyId(companyId);
		return personFinder.selectPersonByIdOrganizationActiveandInactive(personId, criteria);
	}

	@Override
	public PersonDTO getPersonByPersonUUIDInactive(UUID personUUID,
			Date effectiveDateOn, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveDateOn));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());
		criteria.setCompanyId(companyId);
		return personFinder.selectPersonByPersonUUIDInactive(personUUID, criteria);
	}
	//end add By HBP 15021210450311 [HCMS-DAILY Perbaikan acting review(extend /failed)] Tidak bisa melakukan submit Acting Review

	@Override
	public Integer countPersonByBusinessGroup(PersonCriteriaDTO criteria, Long groupId) {
		return personFinder.countPersonByBusinessGroup(criteria, groupId);
	}

	/*@Override
	public List<PeopleAffcoDTO> selectPeopleAffcoByCompanyId(Long personId, Date effectiveOnDate, Long companyId, String employeeNumber, String fullName, int limit,
			int offset) {
		return personFinder.selectPeopleAffcoByCompanyId(personId, effectiveOnDate, companyId, employeeNumber, fullName, new RowBounds(offset, limit));
	}*/

	@Override
	public List<PersonDTO> getPersonByCompany(PersonCriteriaDTO criteria, Long companyId, int offset, int limit) {
		return personFinder.selectPersonByCompany(criteria, companyId, new RowBounds(offset, limit));
	}
	
	@Override
	public List<PersonDTO> getPersonByCompanyTemp(PersonCriteriaDTO criteria, Long companyId, int offset, int limit) {
		return personFinder.selectPersonByCompanyTemp(criteria, companyId, new RowBounds(offset, limit));
	}
	
	@Override
	public List<PersonDTO> getPersonByCompanyInvalidDate(PersonCriteriaDTO criteria, Long companyId, int offset, int limit) {
		return personFinder.selectPersonByCompanyInvalidDate(criteria, companyId, new RowBounds(offset, limit));
	}
	@Override
	public PersonDTO getSimplePersonByUUID(UUID personUUID, Date effectiveOnDate) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(effectiveOnDate);
		List<PersonDTO> personDTOs = personFinder.selectSimplePersonByUUID(personUUID, criteria);
		return personDTOs.size() > 0 ? personDTOs.get(0) : null;
	}

	@Override
	public PersonDTO getPersonalData(Long personId, Long companyId, Date effectiveOnDate) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setPersonId(personId);
		criteria.setCompanyId(companyId);
		criteria.setEffectiveOnDate(effectiveOnDate);
		return personFinder.selectPersonalData(criteria);
	}

	@Override
	public List<PersonDTO> getEmployeeByNameGenderAndBirthDate(String fullName, Date birthDate, String gender, Long companyId, Long groupId, Date effectiveOnDate) {
		return personFinder.selectEmployeeByNameGenderAndBirthDate(fullName, birthDate, gender, companyId, groupId, effectiveOnDate);
	}

	@Override
	public Long getCompanyPerson(Long personId, Date effectiveDate) {
		if (effectiveDate == null) {
			effectiveDate = DateUtil.truncate(new Date());
		}
		return personFinder.selectCompanyPerson(personId, effectiveDate);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateLastUpdatePerson(Long personId) {
		Person person = new Person();
		person.setPersonId(personId);
		person.setLastUpdatedBy(securityServiceImpl.getSecurityProfile() == null ? -1L : securityServiceImpl.getSecurityProfile().getUserId());
		person.setLastUpdateDate(new Date());

		personMapper.updateByPrimaryKeySelective(person);
	}

	@Override
	public List<PersonDTO> getPersonByCompanyInquiry(PersonCriteriaDTO criteria) {
		return personFinder.selectPersonByCompanyInquiry(criteria);
	}

	@Override
	public List<PersonDTO> getEmployeeDeptOwnerAndManager(PersonCriteriaDTO criteria, Long companyId, int limit, int offset) {
		return personFinder.selectEmployeeDeptOwnerAndManager(criteria, companyId, new RowBounds(offset, limit));
	}

	@Override
	public Integer countEmployeeDeptOwnerAndManager(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.countEmployeeDeptOwnerAndManager(criteria, companyId);
	}

	/*@Override
	@Transactional(readOnly = true)
	public List<PersonDTO> getPersonByCompanyForEpmd(PersonCriteriaDTO criteria, Long companyId, int limit, int offset) {
		return personFinder.selectPersonByCompanyForEpmd(criteria, companyId, new RowBounds(offset, limit));
	}

	@Override
	public void extendInternship(PersonDTO person) throws ValidationException, Exception {
		extendInternshipValidator.validate(person);

		PeopleTypeDTO peopleTypeDTO = peopleTypeService.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), DateUtil.MAX_DATE);
		peopleTypeService.delete(peopleTypeDTO);

		PrimaryAssignmentDTO primaryAssignmentDTO = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
				DateUtil.MAX_DATE);
		primaryAssignmentService.delete(primaryAssignmentDTO);

		if (!person.getInternshipEndDate().equals(DateUtil.MAX_DATE)) {
			PeopleTypeDTO peopleTypeCurrent = peopleTypeService
					.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), DateUtil.truncate(new Date()));
			PeopleTypeDTO newPeopleTypeHistory = new PeopleTypeDTO();
			newPeopleTypeHistory.setPersonId(person.getPersonId());
			newPeopleTypeHistory.setCompanyId(person.getCompanyId());
			newPeopleTypeHistory.setPeopleType(co.id.fifgroup.personneladmin.constant.PeopleType.EX_INTERNSHIP.name());
			newPeopleTypeHistory.setEmploymentCategory(null);
			newPeopleTypeHistory.setSource(null);
			newPeopleTypeHistory.setRefId(null);
			newPeopleTypeHistory.setAffco(false);
			newPeopleTypeHistory.setCanceled(false);
			newPeopleTypeHistory.setEffectiveStartDate(DateUtil.add(person.getInternshipEndDate(), Calendar.DATE, 1));
			newPeopleTypeHistory.setEffectiveEndDate(DateUtil.MAX_DATE);
			peopleTypeService.savePeopleType(newPeopleTypeHistory, peopleTypeCurrent.getEffectiveStartDate(), peopleTypeCurrent.getEffectiveEndDate());

			PrimaryAssignmentDTO primaryAssignmentCurrent = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
					DateUtil.truncate(new Date()));
			PrimaryAssignmentDTO newPrimaryAssignmentDTO = (PrimaryAssignmentDTO) DeepCopy.copy(primaryAssignmentCurrent);
			newPrimaryAssignmentDTO.setActionType(ActionType.TERMINATION.name());
			newPrimaryAssignmentDTO.setEffectiveStartDate(DateUtil.add(person.getInternshipEndDate(), Calendar.DATE, 1));
			newPrimaryAssignmentDTO.setEffectiveEndDate(DateUtil.MAX_DATE);
			primaryAssignmentService.savePrimaryAssignment(newPrimaryAssignmentDTO, primaryAssignmentCurrent.getEffectiveStartDate(),
					primaryAssignmentCurrent.getEffectiveEndDate());
		}

		PeopleHistory peopleHistory = new PeopleHistory();
		peopleHistory.setEventName(EventType.EXTEND_INTERNSHIP.name());
		String messageDescription = Labels.getLabel("pea.eventInternshipExtended",
				new Object[] { person.getEmployeeNumber(), person.getFullName(), FormatDateUI.formatDate(new Date()) });
		peopleHistory.setMessageDescription(messageDescription);
		peopleHistoryServiceImpl.savePeopleHistory(person.getPersonId(), person.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(person.getPersonId());
	}*/

	/*private void deletePeopleHistory(PersonDTO person) throws Exception {
		PeopleTypeDTO peopleType = peopleTypeService.getPeopleTypeByEffectiveOnDate(person.getPersonId(), person.getCompanyId(), DateUtil.MAX_DATE);
		peopleTypeService.delete(peopleType);
		PersonalInformationDTO personalInformation = personalInformationService.getPersonalInformationByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
				null, DateUtil.MAX_DATE);
		personalInformationService.delete(personalInformation);
		BankInformationDTO bankInformation = bankInformationServiceImpl.getBankInformationByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
				DateUtil.MAX_DATE);
		bankInformationServiceImpl.delete(bankInformation);
		AccountInformationDTO accountInformation = accountInformationServiceImpl.getAccountInformationByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
				DateUtil.MAX_DATE);
		accountInformationServiceImpl.delete(accountInformation);
		PrimaryAssignmentDTO primaryAssignment = primaryAssignmentService.getPrimaryAssignmentByEffectiveOnDate(person.getPersonId(), person.getCompanyId(),
				DateUtil.MAX_DATE);
		primaryAssignmentService.delete(primaryAssignment);

		PeopleHistory peopleHistory = new PeopleHistory();
		String eventName = EventType.CANCEL_JOIN.name();
		peopleHistory.setEventName(eventName);
		String description = Labels.getLabel("pea.cancelJoinInternshipToEmployee",
				new Object[] { person.getEmployeeNumber(), person.getFullName(), person.getHireDate() });
		peopleHistory.setMessageDescription(description);
		peopleHistoryServiceImpl.savePeopleHistory(person.getPersonId(), person.getCompanyId(), peopleHistory);

		updateLastUpdatePerson(person.getPersonId());
	}*/

	/*@Override
	public List<PersonDTO> findExEmployeeForPayroll(Long companyId, Date dateFrom, Date dateTo) {
		return personFinder.selectExEmployeeForPayroll(companyId, dateFrom, dateTo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonDTO> getPersonForEpdtNotification(Long companyId, String responsibilityName) {
		return personFinder.getPersonForEpdtNotification(companyId, responsibilityName);
	}

	@Override
	public List<PersonDTO> getPayrollPerson(Long companyId, List<Long> listPersonId, List<Long> listBranchId, List<Long> listOrganizationId, List<Long> listJobId,
			List<String> employeeNumbers, Date processDate) {
		return personFinder.getPayrollPerson(companyId, listPersonId, listBranchId, listOrganizationId, listJobId, employeeNumbers, processDate);
	}*/

	private String setDescriptionPeopleHistory(String eventType, String description, PrimaryAssignmentDTO assignmentBefore, PrimaryAssignmentDTO assignmentAfter) {
		StringBuilder sb = new StringBuilder();
		sb.append(description + "\n");
		sb.append("from Original Assigment\n");
		OrganizationDTO assignmentOldDto = organizationSetupServiceImpl.findById(assignmentBefore.getOrganizationId());
		if (assignmentOldDto == null) {
			assignmentOldDto = organizationSetupServiceImpl.findByIdAndDate(assignmentBefore.getOrganizationId(),
					DateUtil.truncate(assignmentBefore.getEffectiveStartDate()));
		}
		Job jobOld = jobSetupServiceImpl.findByPrimaryKey(assignmentBefore.getJobId());
		if (assignmentOldDto != null) {
			sb.append("Old Organization " + assignmentOldDto.getName() + "\n");
			sb.append("Old Location " + assignmentOldDto.getLocation().getLocationName() + "\n");
		}
		sb.append("Old Job " + jobOld.getJobName() + "\n");
		if (eventType.equals(EventType.REGULAR_PROMOTION.name()) || eventType.equals(EventType.IRRPROM_ACTING.name())
				|| eventType.equals(EventType.IRRPROM_DEFINITIVE.name()) || eventType.equals(EventType.DEMOTION.name())) {
			GradeDTO gradeOldDto = gradeSetupServiceImpl.findByIdAndDate(assignmentBefore.getGradeId(), DateUtil.truncate(new Date()));
			if (gradeOldDto != null) {
				sb.append("Old Grade " + gradeOldDto.getGrade() + "\n");
			}
		}
		OrganizationDTO assignmentNewDto = organizationSetupServiceImpl.findById(assignmentAfter.getOrganizationId());
		Job jobNew = jobSetupServiceImpl.findByPrimaryKey(assignmentAfter.getJobId());
		sb.append("to Destination Assigment\n");
		if (assignmentNewDto != null) {
			sb.append("New Organization " + assignmentNewDto.getName() + "\n");
			sb.append("New Location " + assignmentNewDto.getLocation().getLocationName() + "\n");
		}
		sb.append("New Job " + jobNew.getJobName() + "\n");
		if (eventType.equals(EventType.REGULAR_PROMOTION.name()) || eventType.equals(EventType.IRRPROM_ACTING.name())
				|| eventType.equals(EventType.IRRPROM_DEFINITIVE.name()) || eventType.equals(EventType.DEMOTION.name())) {
			GradeDTO gradeNewDto = gradeSetupServiceImpl.findByIdAndDate(assignmentAfter.getGradeId(), DateUtil.truncate(new Date()));
			if (gradeNewDto != null) {
				sb.append("New Grade " + gradeNewDto.getGrade() + "\n");
			}
		}
		return sb.toString();
	}

	@Override
	public List<PersonDTO> getAllPersonBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId,
			Long companyId, String employeeNumber, String employeeName, int limit, int offset, Long supervisorId) {
		return personFinder.selectAlltBySecurityFilterWitRowBounds(inOrganizationId, notInOrganizationId, gradeExclusions, personId, companyId, employeeNumber,
				employeeName, new RowBounds(offset, limit), supervisorId);
	}

	@Override
	public int countAllBySecurityFilter(List<Long> inOrganizationId, List<Long> notInOrganizationId, List<Long> gradeExclusions, Long personId, Long companyId,
			String employeeNumber, String employeeName, Long supervisorId) {
		return personFinder.countAllBySecurityFilter(inOrganizationId, notInOrganizationId, gradeExclusions, personId, companyId, employeeNumber, employeeName, supervisorId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Long> getPersonIdsByCompany(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.selectPersonIdsByCompany(criteria, companyId);
	}

	/*@Override
	public List<PersonDTO> getPayrollPersonByCriteria(PersonCriteriaDTO criteria, Long companyId, int limit, int offset) {
		return personFinder.getPayrollPersonByCriteria(criteria, companyId, new RowBounds(offset, limit));
	}

	@Override
	public int countPayrollPersonByCriteria(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.countPayrollPersonByCriteria(criteria, companyId);
	}*/

	@Override
	public PersonAssignmentDTO getAssignmentByPersonUUID(UUID personUUID) {
		List<PeopleTypeDTO> peopleTypes = peopleTypeFinder.getPeopleTypeByUUID(personUUID);
		List<PersonAssignmentDTO> personAssignments = null;
		if (peopleTypes.size() > 1) {
			for (PeopleTypeDTO peopleType : peopleTypes) {
				if (peopleType.getPeopleType().equalsIgnoreCase(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name())) {
					personAssignments = selectPersonByAssignment(null, null, null, null, peopleType.getPersonId(), peopleType.getCompanyId(), null);
					return personAssignments.size() > 0 ? personAssignments.get(0) : null;
				}
			}
		} else {
			personAssignments = selectPersonByAssignment(null, null, null, null, peopleTypes.get(0).getPersonId(), peopleTypes.get(0).getCompanyId(), null);
			return personAssignments.size() > 0 ? personAssignments.get(0) : null;
		}
		return null;
	}

	@Override
	public PersonDTO getLastPersonInfo(Long personId, Date effectiveOnDate) {
		List<PersonDTO> personDtos = personFinder.getLastPersonInfo(personId, effectiveOnDate);
		return personDtos.size() > 0 ? personDtos.get(0) : null;
	}

	@Override
	public void updateUrlPhoto(Long personId, Long companyId, String photoFilePath) {
		PersonalInformationExample example = new PersonalInformationExample();
		example.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId);

		PersonalInformation personalInformation = new PersonalInformation();
		personalInformation.setPhotoFilePath(photoFilePath);

		personalInformationService.updateByExample(personalInformation, example);
	}

	@Override
	public PersonDTO getPersonByPersonUUIDForInternship(UUID personUUID, Date effectiveOnDate, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setPeopleType(PeopleType.INTERNSHIP.name());
		criteria.setCompanyId(companyId);
		return personFinder.selectPersonByPersonUUID(personUUID, criteria);
	}

	@Override
	public PersonDTO getActiveEmployee(String employeeNumber, Date effectiveOnDate) {
		return personFinder.getActiveEmployee(employeeNumber, effectiveOnDate);
	}

	/*@Override
	@Transactional(readOnly = true)
	public int countPersonByCompanyForEpmd(PersonCriteriaDTO criteria, Long companyId) {
		return personFinder.countPersonByCompanyForEpmd(criteria, companyId);
	}*/

	@Override
	@Transactional(readOnly = true)
	public String getLastMajor(Long personId, Long companyId) {
		List<String> majors = personFinder.getLastMajor(personId, companyId);
		if (majors.size() > 0) {
			return majors.get(0);
		}
		return null;
	}

	/*
	 * Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 -
	 * Time Service
	 */
	@Override
	public List<PersonDTO> selectPersonByCompanyInquiry(PersonCriteriaDTO criteria) {
		// TODO Auto-generated method stub
		return personFinder.selectPersonByCompanyInquiry(criteria);
	}

	/*
	 * End Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 -
	 * Time Service
	 */

	/**
	 * 14040715241425_CR HCMS â€“ Recruitment_RAH 20141105
	 * 
	 * @param employeeNumber
	 * @param employeeName
	 * @param rowBounds
	 * @return
	 */
	public List<PersonDTO> getPersonAllCompany(String employeeNumber, String employeeName) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEmployeeNumber(employeeNumber);
		criteria.setFullName(employeeName);
		criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());

		return personFinder.selectPersonAllCompany(criteria);
	}

	/**
	 * 14040715241425_CR HCMS â€“ Recruitment_RAH 20141105
	 * 
	 * @param employeeNumber
	 * @param employeeName
	 * @param rowBounds
	 * @return
	 */
	public List<PersonDTO> getPersonAllCompanyRowBounds(String employeeNumber, String employeeName, RowBounds rowBounds) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEmployeeNumber(employeeNumber);
		criteria.setFullName(employeeName);
		criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());

		return personFinder.selectPersonAllCompanyRowBounds(criteria, rowBounds);
	}

	@Override
	public PersonDTO getCwkPersonByPersonUUID(UUID personUUID,
			Date effectiveDateOn, Long companyId) {
		return personFinder.getCwkPersonByPersonUUID(personUUID,
				effectiveDateOn, companyId);
	}

	/**
	 * 14071714192817_CR HCMS â€“ Recruitment_LUK 20141209
	 * 
	 * @param companyId
	 * @param employeeNumber
	 * @param employeeName
	 * @param rowBounds
	 * @return
	 */
	public List<PersonDTO> getPersonFilterCompany(Long companyId, String employeeNumber, String employeeName) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setCompanyId(companyId);
		criteria.setEmployeeNumber(employeeNumber);
		criteria.setFullName(employeeName);
		criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
		criteria.setPeopleType(PeopleType.EMPLOYEE.name());

		return personFinder.selectPersonFilterCompany(criteria);
	}

	@Override
	public List<PersonDTO> getPersonFilterCompanyRowBounds(Long companyId,
			String employeeNumber, String employeeName, RowBounds rowBounds) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PersonDTO getPersonByIdInvalidDate(Long personId,
			Date effectiveOnDate, Long companyId) {
		PersonCriteriaDTO criteria = new PersonCriteriaDTO();
		criteria.setEffectiveOnDate(DateUtil.truncate(effectiveOnDate));
		criteria.setCompanyId(getCompanyPerson(personId, effectiveOnDate));
		return personFinder.selectPersonByIdInvalidDate(personId, criteria);
	}
	
	@Override
	public List<PersonAssignmentDTO> selectPersonByAssignmentInternship(Long jobId, Long organizationId, Long branchId, String roleCode, Long personId, Long companyId,
			String jobGroupCode) {
		return personFinder.selectPersonByAssignmentInternship(jobId, organizationId, branchId, roleCode, personId, companyId, jobGroupCode);
	}

	@Override
	public List<PersonDTO> findEmployeeByManagerId(
			List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead, Long companyId, 
			Long managerId,	PersonCriteriaDTO criteria, int offset, int limit) {
		return personFinder.findEmployeeByManagerId(inManagerRootBranch, inManagerBranchWithHead, 
				companyId, managerId, criteria, new RowBounds(offset, limit));
	}
	
	@Override
	public List<PersonDTO> findEmployeeByManagerIdSortByEmployeeNumber(
			List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead, Long companyId, 
			Long managerId,	PersonCriteriaDTO criteria, int offset, int limit) {
		return personFinder.findEmployeeByManagerIdSortByEmployeeNumber(inManagerRootBranch, inManagerBranchWithHead, 
				companyId, managerId, criteria, new RowBounds(offset, limit));
	}

	@Override
	public int countEmployeeByManagerId(
			List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead, Long companyId, 
			Long managerId, PersonCriteriaDTO criteria) {
		return personFinder.countEmployeeByManagerId(inManagerRootBranch, inManagerBranchWithHead, 
				companyId, managerId, criteria);
	}
	
	@Override
	public List<Long> getManagerRootBranch(Long managerId) {
		return personFinder.getManagerRootBranch(managerId);
	}

	@Override
	public List<Long> getManagerBranchWithHead(
			List<Long> inOrganizationId, List<Long> notInOrganizationId, 
			Long companyId, Long managerId) {
		return personFinder.getManagerBranchWithHead(inOrganizationId, notInOrganizationId,
				companyId, managerId);
	}
	
	// start added by WLY for phase 2 
	@Override
	public List<PersonDTO> getPersonByCompanyName(PersonCriteriaDTO criteria,
			Long businessGroupId, int offset, int limit) {
		return personFinder.selectPersonByCompanyName(criteria, businessGroupId, new RowBounds(offset, limit));
	}

	@Override
	public int countPersonByCompanyName(PersonCriteriaDTO criteria,
			Long businessGroupId) {
		return personFinder.countPersonByCompanyName(criteria, businessGroupId);
	}
	// end added by WLY for phase 2 

	// start added by JATIS for phase 2 CAM
	@Override
	public List<PersonDTO> getPersonByJob(Long jobId, Date effectiveOnDate) {
		return personFinder.getPersonByJob(jobId, effectiveOnDate);
	}
	// end added by JATIS for phase 2 CAM

	@Override
	public List<PersonDTO> getSubordinatesForPeopleReview(
			List<Long> inManagerRootBranch, List<Long> inManagerBranchWithHead,
			Long companyId, Long managerId, PersonCriteriaDTO criteria) {
		
		return personFinder.getSubordinatesForPeopleReview(inManagerRootBranch,inManagerBranchWithHead,companyId,managerId,criteria);
		
	}
	
	//16022214360832 :PErformance system pada irrprom. By HS
	//Untuk data yang banyak, penggunaan join lebih bagus dari pada 
	//penggunakan or statement yang banyak
	@Override
	public int countPersonReadyForActing(PersonCriteriaDTO personCriteriaDTO, Long companyId) {
		return personFinder.countPersonReadyForActing(personCriteriaDTO, companyId);
	}
	
	@Override
	public List<PersonDTO> getPersonReadyForActing(PersonCriteriaDTO criteria, Long companyId, int offset, int limit) {
		return personFinder.selectPersonReadyForActing(criteria, companyId, new RowBounds(offset, limit));
	}
	//END 16022214360832
	
}
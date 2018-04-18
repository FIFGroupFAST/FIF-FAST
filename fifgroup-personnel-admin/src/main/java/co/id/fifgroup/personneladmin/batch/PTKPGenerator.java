package co.id.fifgroup.personneladmin.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.personneladmin.constant.Gender;
import co.id.fifgroup.personneladmin.constant.MaritalStatus;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.constant.PtkpStatus;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.dto.PersonalInformationDTO;
import co.id.fifgroup.personneladmin.service.ContactService;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.service.PersonalInformationService;

import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.personneladmin.domain.Contact;
import co.id.fifgroup.personneladmin.domain.ContactExample;

public class PTKPGenerator extends ExecutableTask {

	@Override
	public void execute() throws TaskExecutionException {
		debug("Executing PTKP Generator..");
		Date currentDate = DateUtil.truncate(new Date());
		Long companyId = Long.parseLong(getParameter().get(StaticParameterKey.COMPANY_ID.name()).toString());
		String ptkpStatus = null;
		List<PersonDTO> listAllPerson = new ArrayList<PersonDTO>();
		debug("Getting list of person active..");
		PersonCriteriaDTO criteriaEmployee = new PersonCriteriaDTO();
		criteriaEmployee.setPeopleType(PeopleType.EMPLOYEE.name());
		criteriaEmployee.setEffectiveOnDate(currentDate);
		List<PersonDTO> listPersonEmployee = getPersonService().getPersonByCompany(criteriaEmployee, companyId);
		listAllPerson.addAll(listPersonEmployee);
		
		PersonCriteriaDTO criteriaInternship = new PersonCriteriaDTO();
		criteriaInternship.setPeopleType(PeopleType.INTERNSHIP.name());
		criteriaInternship.setEffectiveOnDate(currentDate);
		List<PersonDTO> listPersonInternship = getPersonService().getPersonByCompany(criteriaInternship, companyId);
		listAllPerson.addAll(listPersonInternship);
		debug(listAllPerson.size()+" person found");
		debug("Preparing to update ptkp status..");
		for (PersonDTO person : listAllPerson) {
			try {
				debug("Preparing to update " + person.getEmployeeNumber() + " - " + person.getFullName());
				if (person.getMaritalStatus().equalsIgnoreCase(MaritalStatus.SINGLE.name())
						|| person.getGenderCode().equalsIgnoreCase(Gender.FEMALE.name())) {
					ptkpStatus = PtkpStatus.T00.name();
				} else {
					int numberOfChildren = getNumberOfChildren(person.getPersonId(), person.getCompanyId(), currentDate);
					if (person.getMaritalStatus().equalsIgnoreCase(MaritalStatus.MARRIED.name())) {
						ptkpStatus = getPtkpStatus("K", numberOfChildren);
					} else if (person.getMaritalStatus().equalsIgnoreCase(MaritalStatus.DIVORCE.name())) {
						ptkpStatus = getPtkpStatus("X", numberOfChildren);
					}				
				}
				
				PersonalInformationDTO personalInformationBefore = getPersonalInformationService().getPersonalInformationByEffectiveOnDate(
						person.getPersonId(), person.getCompanyId(), null, currentDate);
				if (personalInformationBefore != null) {
					if (getPersonalInformationService().isCurrent(personalInformationBefore) && !getPersonalInformationService().hasFuture(personalInformationBefore)) {
						Date dateFromBefore = personalInformationBefore.getEffectiveStartDate();
						Date dateToBefore = personalInformationBefore.getEffectiveEndDate();
						personalInformationBefore.setPtkpStatus(ptkpStatus);
						personalInformationBefore.setEffectiveStartDate(currentDate);
						personalInformationBefore.setEffectiveEndDate(DateUtil.MAX_DATE);
							getPersonalInformationService().save(personalInformationBefore, dateFromBefore, dateToBefore);
							debug("update personal information success : " + person.getEmployeeNumber() + " - " + person.getFullName() + " with ptkp status " + ptkpStatus);
					} else {
						debug(person.getEmployeeNumber() + " - " + person.getFullName() + " has a future history personal information");
					}				
				} else {
					debug("no current history data");
				}			
			} catch (Exception e) {
				debug("error for " + person.getEmployeeNumber() + " - " + person.getFullName() + " : " + e.getMessage());
				log.error(e.getMessage(), e);
			}
		}
		debug("Finished!");
	}
	
	private PersonService getPersonService() {
		return (PersonService) getApplicationContext().getBean("personService");
	}
	
	private ContactService getContactService() {
		return (ContactService) getApplicationContext().getBean("contactServiceImpl");
	}
	
	private PersonalInformationService getPersonalInformationService() {
		return (PersonalInformationService) getApplicationContext().getBean("personalInformationService");
	}
	
	private int getNumberOfChildren(Long personId, Long companyId, Date effectiveOnDate) {
		int numberOfChildren = 0;
		ContactExample example = new ContactExample();
		example.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId)
			.andStartDateLessThanOrEqualTo(effectiveOnDate).andEndDateGreaterThanOrEqualTo(effectiveOnDate);
		List<Contact> listContact = getContactService().selectByExample(example);
		for (Contact contact : listContact) {
			if (contact.getRelationship().matches("^(CHILD).*$")) {
				numberOfChildren++;
			}
		}
		return numberOfChildren;
	}
	
	private String getPtkpStatus(String prefix, int numberOfChildren) {
		String children = String.valueOf(numberOfChildren);
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		if (children.length() == 1) {
			sb.append("0");
		}
		sb.append(children);
		return sb.toString();
	}

}

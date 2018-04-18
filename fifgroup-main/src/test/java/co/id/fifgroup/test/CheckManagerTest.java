package co.id.fifgroup.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.PersonAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.util.ManagerUtil;

public class CheckManagerTest {

	private static final Logger logger = LoggerFactory
			.getLogger(CheckManagerTest.class);

	public static void main(String[] args) {
		logger.info("Enter");

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext-test.xml");
		PersonService personService = (PersonService) context
				.getBean("personService");
		PersonCriteriaDTO personCriteriaDTO = new PersonCriteriaDTO();

		personCriteriaDTO.setCompanyId(1L);
		personCriteriaDTO.setPeopleType(PeopleType.EMPLOYEE.name());
		personCriteriaDTO.setEffectiveOnDate(DateUtil.truncate(new Date()));

		List<PersonDTO> persons = new ArrayList<PersonDTO>();
		persons = personService.getPersonByCompanyInquiry(personCriteriaDTO);

		personCriteriaDTO = new PersonCriteriaDTO();

		personCriteriaDTO.setCompanyId(2L);
		personCriteriaDTO.setPeopleType(PeopleType.EMPLOYEE.name());
		personCriteriaDTO.setEffectiveOnDate(DateUtil.truncate(new Date()));

		List<PersonDTO> personsAMF = new ArrayList<PersonDTO>();
		personsAMF = personService.getPersonByCompanyInquiry(personCriteriaDTO);

		System.out.println("FIF : " + persons.size());
		System.out.println("AMF : " + personsAMF.size());

		persons.addAll(personsAMF);

		System.out.println("Total : " + persons.size());

		int countLevel = 1;

		int i = 0;
		for (PersonDTO person : persons) {
			i++;
			List<PersonAssignmentDTO> managers = ManagerUtil.getManager(person
					.getPrimaryAssignmentDTO().getJobId(), person
					.getPrimaryAssignmentDTO().getOrganizationId(), person
					.getCompanyId(), countLevel);
			if (managers.size() == 0) {
				logger.error(i + "|" + person.getEmployeeNumber() + "|"
						+ person.getFullName() + "|" + "No manager");
			} else if (managers.size() == 1) {
				for (PersonAssignmentDTO manager : managers) {
					logger.info(i + "|" + person.getEmployeeNumber() + "|"
							+ person.getFullName() + "|"
							+ manager.getEmployeeNumber() + "|"
							+ manager.getFullName());
				}

			} else {
				logger.error(i + "|" + person.getEmployeeNumber() + "|"
						+ person.getFullName() + "|" + "Too many manager");
			}
		}

		logger.info("Finish");

	}
}

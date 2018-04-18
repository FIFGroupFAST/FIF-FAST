package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.dto.EquipmentAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.dto.PersonalInformationDTO;

import co.id.fifgroup.basicsetup.common.HistoricalService;
import co.id.fifgroup.personneladmin.domain.PersonalInformation;
import co.id.fifgroup.personneladmin.domain.PersonalInformationExample;
import co.id.fifgroup.workstructure.dto.LocationDTO;

public interface PersonalInformationService extends HistoricalService<PersonalInformationDTO>{
	
	public void save(PersonalInformationDTO personalInformation, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	
	public void delete(PersonalInformationDTO personalInformation) throws Exception;
	
	public List<PersonalInformation> selectByExample(PersonalInformationExample personalInformationExample);
	
	public PersonalInformationDTO getPersonalInformationByEffectiveOnDate(Long personId, Long companyId, String employeeNumber, Date effectiveOnDate);
	
	public void deleteFuturePersonalInformation(Long personId, Long companyId);
	
	public int countByExample(PersonalInformationExample personalInformationExample);
	
	public void savePersonalInformation(PersonDTO personDTO, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	
	public String getMaritalStatusByEffectiveOnDate(Long personId, Long companyId, Date effectiveDate);
	
	public Date getMaxHireDate(Long personId, Long companyId);

	public LocationDTO getLocationByHomebase(Long personId, Long companyId, Date effectiveOnDate);
	
	public void updateByExample(PersonalInformation personalInformation, PersonalInformationExample example);

	public List<EquipmentAssignmentDTO> getWorkingEquipmentList(Long personId,Long companyId);
}

package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.personneladmin.dto.PeopleTypeDTO;

import co.id.fifgroup.basicsetup.common.HistoricalService;
import co.id.fifgroup.personneladmin.domain.PeopleType;
import co.id.fifgroup.personneladmin.domain.PeopleTypeExample;

public interface PeopleTypeService extends HistoricalService<PeopleTypeDTO>{
	
	public void save(PeopleTypeDTO peopleType, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	
	public List<PeopleType> selectByExample(PeopleTypeExample example);
	
	public PeopleTypeDTO getPeopleTypeByEffectiveOnDate(Long personId, Long companyId, Date effectiveOnDate);
	
	public void deleteFuturePeopleType(Long personId, Long companyId);
	
	public void savePeopleType(PeopleTypeDTO peopleType, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception;
	
	public void delete(PeopleTypeDTO peopleTypeDTO);
	
	public Date getMaxEffectiveStartDate(Long personId, Long companyId, co.id.fifgroup.personneladmin.constant.PeopleType peopleType);
	
	public Date getMinEffectiveStartDateEmployee(Long personId, Long companyId);
	
	public List<PeopleTypeDTO> getPeopleTypeEmployee(Long personId, Long companyId);
	
	public int countPeopleInCompany(Long personId, Long companyId);
	
	public boolean isActivePersonByUUID(UUID personUUID);
	
	public PeopleTypeDTO getFuturePeopleType(Long personId, Long companyId);
	
}

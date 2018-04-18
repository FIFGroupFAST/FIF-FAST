package co.id.fifgroup.personneladmin.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.dto.PeopleTypeDTO;
import co.id.fifgroup.personneladmin.service.PeopleTypeService;

import co.id.fifgroup.basicsetup.common.AbstractHistoricalService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.personneladmin.dao.PeopleTypeMapper;
import co.id.fifgroup.personneladmin.domain.PeopleType;
import co.id.fifgroup.personneladmin.domain.PeopleTypeExample;
import co.id.fifgroup.personneladmin.finder.PeopleTypeFinder;

@Service("peopleTypeService")
@Transactional(readOnly=true)
public class PeopleTypeServiceImpl extends AbstractHistoricalService<PeopleTypeDTO> implements PeopleTypeService{

	@Autowired
	private PeopleTypeMapper peopleTypeMapper;
	@Autowired
	private PeopleTypeFinder peopleTypeFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Override
	@Transactional(readOnly=false)
	public void insertNewHistory(PeopleTypeDTO object) {
		object.setEffectiveStartDate(DateUtil.truncate(object.getEffectiveStartDate()));
		object.setEffectiveEndDate(DateUtil.truncate(object.getEffectiveEndDate()));
		peopleTypeMapper.insert(object);
	}

	@Override
	@Transactional(readOnly=false)
	public void endDateCurrentHistory(PeopleTypeDTO object, Date dateTo, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
	
		PeopleTypeExample peopleTypeExample = new PeopleTypeExample();
		peopleTypeExample.createCriteria().andPersonIdEqualTo(object.getPersonId())
			.andCompanyIdEqualTo(object.getCompanyId())
			.andEffectiveStartDateEqualTo(DateUtil.truncate(dateFromBeforeCurrentEdit))
			.andEffectiveEndDateEqualTo(DateUtil.truncate(dateToBeforeCurrentEdit));
		PeopleType peopleTypeEndDate = new PeopleType();
		peopleTypeEndDate.setEffectiveEndDate(DateUtil.truncate(dateTo));
		
		peopleTypeMapper.updateByExampleSelective(peopleTypeEndDate, peopleTypeExample);
		
	}

	@Override
	public boolean hasFuture(PeopleTypeDTO object) {
		if (isCurrent(object)) {
			PeopleTypeExample peopleTypeExample = new PeopleTypeExample();
			peopleTypeExample.createCriteria().andPersonIdEqualTo(object.getPersonId())
				.andCompanyIdEqualTo(object.getCompanyId())
				.andEffectiveStartDateGreaterThan(DateUtil.truncate(object.getEffectiveEndDate()));
			List<PeopleType> resultPeopleType = peopleTypeMapper.selectByExample(peopleTypeExample);
			if(resultPeopleType.size() == 1) {
				return true;
			}			
		} else {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly=false)
	public void save(PeopleTypeDTO peopleType, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception {
		saveHistory(peopleType, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
	}

	@Override
	public List<PeopleType> selectByExample(PeopleTypeExample example) {
		return peopleTypeMapper.selectByExample(example);
	}

	@Override
	public PeopleTypeDTO getPeopleTypeByEffectiveOnDate(Long personId,
			Long companyId, Date effectiveOnDate) {
		return peopleTypeFinder.getPeopleTypeByEffectiveOnDate(personId, companyId, effectiveOnDate);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteFuturePeopleType(Long personId, Long companyId) {
		PeopleTypeExample example = new PeopleTypeExample();
		example.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId)
			.andEffectiveStartDateGreaterThan(DateUtil.truncate(new Date()));
		peopleTypeMapper.deleteByExample(example);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void savePeopleType(PeopleTypeDTO peopleType, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception {
		Long createdBy = securityServiceImpl.getSecurityProfile() == null ? -1L : securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		peopleType.setCreatedBy(createdBy);
		peopleType.setCreationDate(createdDate);
		peopleType.setLastUpdatedBy(createdBy);
		peopleType.setLastUpdateDate(createdDate);
		save(peopleType, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(PeopleTypeDTO peopleTypeDTO) {
		peopleTypeMapper.deleteByPrimaryKey(peopleTypeDTO.getPeopleTypeId());
		
		PeopleTypeExample peopleTypeExample = new PeopleTypeExample();
		peopleTypeExample.createCriteria()
			.andPersonIdEqualTo(peopleTypeDTO.getPersonId())
			.andCompanyIdEqualTo(peopleTypeDTO.getCompanyId())
			.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(DateUtil.add(peopleTypeDTO.getEffectiveStartDate(), Calendar.DATE, -1)))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(DateUtil.add(peopleTypeDTO.getEffectiveStartDate(), Calendar.DATE, -1)));
		PeopleType peopleTypeEndDate = new PeopleType();
		peopleTypeEndDate.setEffectiveEndDate(DateUtil.MAX_DATE);
		
		peopleTypeMapper.updateByExampleSelective(peopleTypeEndDate, peopleTypeExample);
	}

	@Override
	public Date getMaxEffectiveStartDate(Long personId, Long companyId, co.id.fifgroup.personneladmin.constant.PeopleType peopleType) {
		return peopleTypeFinder.getMaxEffectiveStartDate(personId, companyId, peopleType);
	}

	@Override
	public Date getMinEffectiveStartDateEmployee(Long personId, Long companyId) {
		return peopleTypeFinder.getMinEffectiveStartDate(personId, companyId);
	}

	@Override
	public List<PeopleTypeDTO> getPeopleTypeEmployee(Long personId,
			Long companyId) {
		return peopleTypeFinder.getPeopleTypeEmployee(personId, companyId);
	}

	@Override
	public int countPeopleInCompany(Long personId, Long companyId) {
		return peopleTypeFinder.countPeopleInCompany(personId, companyId);
	}

	@Override
	public boolean isActivePersonByUUID(UUID personUUID) {
		List<PeopleTypeDTO> peopleTypes = peopleTypeFinder.getPeopleTypeByUUID(personUUID);
		for (PeopleTypeDTO peopleType : peopleTypes) {
			if (peopleType.getPeopleType().equalsIgnoreCase(co.id.fifgroup.personneladmin.constant.PeopleType.EMPLOYEE.name())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public PeopleTypeDTO getFuturePeopleType(Long personId, Long companyId) {
		return peopleTypeFinder.getFuturePeopleType(personId, companyId);
	}	
}

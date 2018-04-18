package co.id.fifgroup.personneladmin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.constant.DocumentType;
import co.id.fifgroup.personneladmin.dto.DocumentDTO;
import co.id.fifgroup.personneladmin.dto.EquipmentAssignmentDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.dto.PersonalInformationDTO;
import co.id.fifgroup.personneladmin.service.ContactService;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.personneladmin.service.PersonalInformationService;
import co.id.fifgroup.personneladmin.util.FileUtil;

import co.id.fifgroup.basicsetup.common.AbstractHistoricalService;
import co.id.fifgroup.core.constant.DocumentSource;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.personneladmin.dao.PersonalInformationMapper;
import co.id.fifgroup.personneladmin.domain.PersonalInformation;
import co.id.fifgroup.personneladmin.domain.PersonalInformationExample;
import co.id.fifgroup.personneladmin.finder.PersonalInformationFinder;
import co.id.fifgroup.workstructure.dto.LocationDTO;

@Service("personalInformationService")
@Transactional(readOnly=true)
public class PersonalInformationServiceImpl extends AbstractHistoricalService<PersonalInformationDTO> implements PersonalInformationService {
	
	@Autowired
	private PersonalInformationMapper personalInformationMapper;
	@Autowired
	private PersonalInformationFinder personalInformationFinder;
	@Autowired
	private SecurityService securityServiceImpl;
	@Autowired
	private PersonService personService;
	@Autowired
	private ContactService contactServiceImpl;
	
	@Override
	@Transactional(readOnly=false)
	public void insertNewHistory(PersonalInformationDTO object) {
		object.setEffectiveStartDate(DateUtil.truncate(object.getEffectiveStartDate()));
		object.setEffectiveEndDate(DateUtil.truncate(object.getEffectiveEndDate()));
		personalInformationMapper.insert(object);
	}

	@Override
	@Transactional(readOnly=false)
	public void endDateCurrentHistory(PersonalInformationDTO object, Date dateTo, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) {
		PersonalInformationExample personalInformationExample = new PersonalInformationExample();
		personalInformationExample.createCriteria().andPersonIdEqualTo(object.getPersonId())
			.andCompanyIdEqualTo(object.getCompanyId())
			.andEffectiveStartDateEqualTo(DateUtil.truncate(dateFromBeforeCurrentEdit))
			.andEffectiveEndDateEqualTo(DateUtil.truncate(dateToBeforeCurrentEdit));
		PersonalInformation personalInfoDtoEndDate = new PersonalInformation();
		personalInfoDtoEndDate.setEffectiveEndDate(DateUtil.truncate(dateTo));		
		personalInformationMapper.updateByExampleSelective(personalInfoDtoEndDate, personalInformationExample);
		
	}

	@Override
	@Transactional(readOnly=true)
	public boolean hasFuture(PersonalInformationDTO object) {
		if (isCurrent(object)) {
			PersonalInformationExample personalInformationExample = new PersonalInformationExample();
			personalInformationExample.createCriteria().andPersonIdEqualTo(object.getPersonId())
				.andCompanyIdEqualTo(object.getCompanyId())
				.andEffectiveStartDateGreaterThan(DateUtil.truncate(object.getEffectiveEndDate()));
			List<PersonalInformation> resultPeronalInfo = personalInformationMapper.selectByExample(personalInformationExample);
			if(resultPeronalInfo.size() == 1) {
				return true;
			}			
		} else {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly=false)
	public void save(PersonalInformationDTO personalInformation, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception {
		saveHistory(personalInformation, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);		
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(PersonalInformationDTO personalInformation)
			throws Exception {
		personalInformationMapper.deleteByPrimaryKey(personalInformation.getInformationId());
		
		PersonalInformationExample personalInformationExample = new PersonalInformationExample();
		personalInformationExample.createCriteria().andPersonIdEqualTo(personalInformation.getPersonId())
			.andCompanyIdEqualTo(personalInformation.getCompanyId())
			.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		PersonalInformation personalInfoDtoEndDate = new PersonalInformation();
		personalInfoDtoEndDate.setEffectiveEndDate(DateUtil.MAX_DATE);		
		personalInformationMapper.updateByExampleSelective(personalInfoDtoEndDate, personalInformationExample);
	}

	@Override
	public List<PersonalInformation> selectByExample(
			PersonalInformationExample personalInformationExample) {
		return personalInformationMapper.selectByExample(personalInformationExample);
	}

	@Override
	public PersonalInformationDTO getPersonalInformationByEffectiveOnDate(
			Long personId, Long companyId, String employeeNumber, Date effectiveOnDate) {
		return personalInformationFinder.getPersonalInformationByEffectiveOnDate(personId, companyId, employeeNumber, effectiveOnDate);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteFuturePersonalInformation(Long personId, Long companyId) {
		PersonalInformationExample example = new PersonalInformationExample();
		example.createCriteria().andPersonIdEqualTo(personId).andCompanyIdEqualTo(companyId)
			.andEffectiveStartDateGreaterThan(DateUtil.truncate(new Date()));
		for (PersonalInformation personalInfo : personalInformationMapper.selectByExample(example)) {
			if (personalInfo.getPhotoFilePath() != null) {
				FileUtil.deleteFile(personalInfo.getPhotoFilePath());
			}
		}
		personalInformationMapper.deleteByExample(example);
	}

	@Override
	public int countByExample(
			PersonalInformationExample personalInformationExample) {
		return countByExample(personalInformationExample);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void savePersonalInformation(PersonDTO personDTO, Date dateFromBeforeCurrentEdit, Date dateToBeforeCurrentEdit) throws Exception {
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		
		if (personDTO.getMediaPhoto() != null) {
			personDTO.getMediaPhoto().setEmployeeNumber(personDTO.getEmployeeNumber());
			String filePath = FileUtil.doUpload(personDTO.getMediaPhoto());
			personDTO.setPhotoFilePath(filePath);
			
			DocumentDTO documentDTO = new DocumentDTO();
			documentDTO.setDocumentType(DocumentType.PHOTO.name());
			documentDTO.setSource(DocumentSource.PERSONNEL_ADMIN.name());
			documentDTO.setFileName(personDTO.getMediaPhoto().getFileName());
			documentDTO.setFilePath(filePath);
			documentDTO.setUploadDate(createdDate);
			List<DocumentDTO> listDocument = new ArrayList<DocumentDTO>();
			listDocument.add(documentDTO);
			personService.saveDocument(personDTO.getPersonId(), personDTO.getCompanyId(), personDTO.getEmployeeNumber(), listDocument);
		}
		personDTO.setCreatedBy(createdBy);
		personDTO.setCreationDate(createdDate);
		personDTO.setLastUpdatedBy(createdBy);
		personDTO.setLastUpdateDate(createdDate);
		save(personDTO, dateFromBeforeCurrentEdit, dateToBeforeCurrentEdit);
		
		// end date spouse active if is divorce
//		if (personDTO.getMaritalStatus().equalsIgnoreCase(MaritalStatus.DIVORCE.name())) {
//			List<Contact> spouses = contactServiceImpl.getSpouse(personDTO.getPersonId(), 
//					personDTO.getCompanyId(), personDTO.getEffectiveStartDate());
//			for (Contact spouse : spouses) {
//				spouse.setEndDate(personDTO.getEffectiveStartDate());
//				contactServiceImpl.update(spouse);
//			}
//		}
	}

	@Override
	public String getMaritalStatusByEffectiveOnDate(Long personId,
			Long companyId, Date effectiveDate) {
		return personalInformationFinder.getMaritalStatusByEffectiveOnDate(personId, companyId, effectiveDate);
	}

	@Override
	public Date getMaxHireDate(Long personId, Long companyId) {
		return personalInformationFinder.getMaxHireDate(personId, companyId);
	}

	@Override
	public LocationDTO getLocationByHomebase(Long personId, Long companyId,
			Date effectiveOnDate) {
		return personalInformationFinder.getLocationByHomebase(personId, companyId, effectiveOnDate);
	}

	@Override
	public void updateByExample(PersonalInformation personalInformation,
			PersonalInformationExample example) {
		personalInformationMapper.updateByExampleSelective(personalInformation, example);
	}

	@Override
	public List<EquipmentAssignmentDTO> getWorkingEquipmentList(Long personId,
			Long companyId) {
		// TODO Auto-generated method stub
		return personalInformationFinder.getWorkingEquipmentList(personId, companyId);
	}

}

package co.id.fifgroup.personneladmin.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.service.ContactService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.personneladmin.dao.ContactMapper;
import co.id.fifgroup.personneladmin.domain.Contact;
import co.id.fifgroup.personneladmin.domain.ContactExample;

@Transactional
@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactMapper contactMapper;
	@Autowired
	private SecurityService securityServiceImpl;
	
	@Override
	public void save(Contact contact) {
		contactMapper.insert(contact);
	}

	@Override
	public void update(Contact contact) {
		contactMapper.updateByPrimaryKey(contact);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Contact> selectByExample(ContactExample example) {
		return contactMapper.selectByExample(example);
	}
	
	@Override
	@Transactional(readOnly=true)
	public int countByExample(ContactExample example) {
		return contactMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Contact> getContactsByPersonIdAndCompanyId(Long personId,
			Long companyId) {
		ContactExample example = new ContactExample();
		example.createCriteria().andPersonIdEqualTo(personId).
			andCompanyIdEqualTo(companyId);
		return selectByExample(example);
	}

	@Override
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId) {
		ContactExample example = new ContactExample();
		example.createCriteria().andPersonIdEqualTo(personId).
			andCompanyIdEqualTo(companyId);
		contactMapper.deleteByExample(example);
	}

	@Override
	public void updateByExample(Contact contact) {
		ContactExample example = new ContactExample();
		example.createCriteria().andPersonIdEqualTo(contact.getPersonId())
			.andCompanyIdEqualTo(contact.getCompanyId())
			.andRelationshipEqualTo(contact.getRelationship());
		contactMapper.updateByExampleSelective(contact, example);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Contact> getContactChilds(Long personId, Long companyId,
			String maritalStatus, Date effectiveOnDate) {
		ContactExample example = new ContactExample();
		ContactExample.Criteria critera = example.createCriteria().andPersonIdEqualTo(personId)
			.andCompanyIdEqualTo(companyId)
			.andRelationshipLikeInsensitive("CHILD_%");
		if (maritalStatus != null) {
			critera.andMaritalStatusEqualTo(maritalStatus);
		}
		if (effectiveOnDate != null) {
			critera.andStartDateLessThanOrEqualTo(effectiveOnDate).andEndDateGreaterThanOrEqualTo(effectiveOnDate);
		}
		
		return selectByExample(example);
	}
	
	
	@Override
	public void saveContact(Long personId, Long companyId, List<Contact> listContacts) {
		Long createdBy = securityServiceImpl.getSecurityProfile().getUserId();
		Date createdDate = new Date();
		
		for (Contact contact : listContacts) {
			contact.setLastUpdatedBy(createdBy);
			contact.setLastUpdateDate(createdDate);
			if (contact.getContactId() != null) {
				update(contact);
			} else {
				contact.setPersonId(personId);
				contact.setCompanyId(companyId);
				contact.setCreatedBy(createdBy);
				contact.setCreationDate(createdDate);
				save(contact);				
			}
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Contact> getSpouse(Long personId, Long companyId,
			Date effectiveOnDate) {
		ContactExample example = new ContactExample();
		ContactExample.Criteria critera = example.createCriteria().andPersonIdEqualTo(personId)
			.andCompanyIdEqualTo(companyId)
			.andRelationshipEqualTo("SPOUSE");
		if (effectiveOnDate != null) {
			critera.andStartDateLessThanOrEqualTo(effectiveOnDate).andEndDateGreaterThanOrEqualTo(effectiveOnDate);
		}
		
		return selectByExample(example);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean validateInsuranceNumber(Contact contact) {
		Date currentDate = DateUtil.truncate(new Date());
		ContactExample example = new ContactExample();
		example.createCriteria().andMedicalInsuranceNumberEqualTo(contact.getMedicalInsuranceNumber())
			.andStartDateLessThanOrEqualTo(currentDate)
			.andEndDateGreaterThanOrEqualTo(currentDate)
			.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId())
			.andContactIdNotEqualTo(contact.getContactId() != null ? contact.getContactId() : -1L);
		List<Contact> contacts = selectByExample(example);
		return contacts.size() < 1 ? true : false;
	}

}

package co.id.fifgroup.personneladmin.service;

import java.util.Date;
import java.util.List;

import co.id.fifgroup.personneladmin.domain.Contact;
import co.id.fifgroup.personneladmin.domain.ContactExample;

public interface ContactService {

	public void save(Contact contact);
	
	public List<Contact> selectByExample(ContactExample example);

	public void update(Contact contact);

	public int countByExample(ContactExample example);
	
	public List<Contact> getContactsByPersonIdAndCompanyId(Long personId, Long companyId);
	
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId);
	
	public void updateByExample(Contact contact);
	
	public List<Contact> getContactChilds(Long personId, Long companyId, String maritalStatus, Date effectiveOnDate);
	
	public void saveContact(Long personId, Long companyId, List<Contact> listContacts);
	
	public List<Contact> getSpouse(Long personId, Long companyId, Date effectiveOnDate);
	
	public boolean validateInsuranceNumber(Contact contact);
}

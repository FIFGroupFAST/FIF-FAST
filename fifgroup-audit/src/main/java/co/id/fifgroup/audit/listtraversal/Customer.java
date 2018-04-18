package co.id.fifgroup.audit.listtraversal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Example only
 * @author kiton
 *
 */
public class Customer implements Serializable{
	private static final long serialVersionUID = 7510641980960267722L;
	private String name;
	private int age;
	private String employmentStatus;
	private Date dateOfBirth;
	private Collection<Address> addresslist;
	private Collection <Contact> contactList;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public Collection<Address> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(Collection<Address> addresslist) {
		this.addresslist = addresslist;
	}

	public void setContactList(Collection<Contact> contactList) {
		this.contactList = contactList;
	}

	public Collection<Contact> getContactList() {
		return contactList;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	
}

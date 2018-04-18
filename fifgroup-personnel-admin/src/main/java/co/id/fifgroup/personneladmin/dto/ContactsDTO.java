package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.personneladmin.domain.Contact;

public class ContactsDTO implements Serializable {

	private static final long serialVersionUID = -392100995834106790L;

	private List<Contact> listContact;
	private boolean changeNumberOfChild;
	private boolean changeMaritalStatus;
	private boolean changeGenderChild;
	private boolean changeFamilyMember;
	
	public List<Contact> getListContact() {
		return listContact;
	}

	public void setListContact(List<Contact> listContact) {
		this.listContact = listContact;
	}

	public boolean isChangeNumberOfChild() {
		return changeNumberOfChild;
	}

	public void setChangeNumberOfChild(boolean changeNumberOfChild) {
		this.changeNumberOfChild = changeNumberOfChild;
	}

	public boolean isChangeMaritalStatus() {
		return changeMaritalStatus;
	}

	public void setChangeMaritalStatus(boolean changeMaritalStatus) {
		this.changeMaritalStatus = changeMaritalStatus;
	}

	public boolean isChangeGenderChild() {
		return changeGenderChild;
	}

	public void setChangeGenderChild(boolean changeGenderChild) {
		this.changeGenderChild = changeGenderChild;
	}

	public boolean isChangeFamilyMember() {
		return changeFamilyMember;
	}

	public void setChangeFamilyMember(boolean changeFamilyMember) {
		this.changeFamilyMember = changeFamilyMember;
	}
	
}

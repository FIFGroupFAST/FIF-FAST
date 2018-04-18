package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.personneladmin.domain.Communication;
import co.id.fifgroup.personneladmin.domain.Education;

public class PersonMainDTO implements Serializable, Traversable {

	private static final long serialVersionUID = -6617007039176398467L;
	
	private PersonDTO personDTO;
	private AccountInformationDTO accountInformationDTO;
	private BankInformationDTO bankInformationDTO;
	private ContactsDTO contacts;
	private List<Education> listEducation;
	private List<AddressDTO> listAddress;
	private List<Communication> listCommunication;
	private List<MediaDTO> listMediaUpload;
	private List<String> listFileDeleted;
	private List<PerformanceReviewDTO> listPerformance;

	public void setPersonDTO(PersonDTO personDTO) {
		this.personDTO = personDTO;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}

	public AccountInformationDTO getAccountInformationDTO() {
		return accountInformationDTO;
	}

	public void setAccountInformationDTO(
			AccountInformationDTO accountInformationDTO) {
		this.accountInformationDTO = accountInformationDTO;
	}

	public BankInformationDTO getBankInformationDTO() {
		return bankInformationDTO;
	}

	public void setBankInformationDTO(BankInformationDTO bankInformationDTO) {
		this.bankInformationDTO = bankInformationDTO;
	}

	public ContactsDTO getContacts() {
		return contacts;
	}

	public void setContacts(ContactsDTO contacts) {
		this.contacts = contacts;
	}

	public List<Education> getListEducation() {
		return listEducation;
	}

	public void setListEducation(List<Education> listEducation) {
		this.listEducation = listEducation;
	}

	public List<AddressDTO> getListAddress() {
		return listAddress;
	}

	public void setListAddress(List<AddressDTO> listAddress) {
		this.listAddress = listAddress;
	}

	public List<Communication> getListCommunication() {
		return listCommunication;
	}

	public void setListCommunication(List<Communication> listCommunication) {
		this.listCommunication = listCommunication;
	}

	public List<MediaDTO> getListMediaUpload() {
		return listMediaUpload;
	}

	public void setListMediaUpload(List<MediaDTO> listMediaUpload) {
		this.listMediaUpload = listMediaUpload;
	}

	public List<String> getListFileDeleted() {
		return listFileDeleted;
	}

	public void setListFileDeleted(List<String> listFileDeleted) {
		this.listFileDeleted = listFileDeleted;
	}

	public List<PerformanceReviewDTO> getListPerformance() {
		return listPerformance;
	}

	public void setListPerformance(List<PerformanceReviewDTO> listPerformance) {
		this.listPerformance = listPerformance;
	}

	@Override
	public Object getId() {
		return this.personDTO.getPersonId();
	}

}

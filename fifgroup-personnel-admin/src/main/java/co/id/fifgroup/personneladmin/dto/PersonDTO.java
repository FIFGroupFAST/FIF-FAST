package co.id.fifgroup.personneladmin.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.id.fifgroup.core.audit.Traversable;

public class PersonDTO extends PersonalInformationDTO implements Traversable, Comparable<PersonDTO> {

	private static final long serialVersionUID = 1L;

	private UUID personUUID;
	private String peopleType;
	private String employmentCategory;
	private String source;
	private Long refId;
	private boolean affco;
	private boolean canceled;
	private String homeBaseName;
	private Date internshipEndDate;
	private Long groupId;
	private String userName;
	private boolean changeMaritalStatus;
	private boolean changeHomebase;
	private boolean changeReligion;
	private boolean changeMarriedWith;
	private Long spouseCompanyId;
	private String peopleCategory;
	private PrimaryAssignmentDTO primaryAssignmentDTO;
	private MediaDTO mediaPhoto;

	private List<ContactDTO> contacts = new ArrayList<ContactDTO>();

	private String documentPath;
	private Boolean isHeadOffice;

	private Long castEmployeeNumber;
	private Long mppTrnId;
	private String terminationReason;

	public String getPeopleType() {
		return peopleType;
	}

	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}

	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory(String employmentCategory) {
		this.employmentCategory = employmentCategory;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isAffco() {
		return affco;
	}

	public void setAffco(boolean affco) {
		this.affco = affco;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public PrimaryAssignmentDTO getPrimaryAssignmentDTO() {
		return primaryAssignmentDTO;
	}

	public void setPrimaryAssignmentDTO(
			PrimaryAssignmentDTO primaryAssignmentDTO) {
		this.primaryAssignmentDTO = primaryAssignmentDTO;
	}

	public UUID getPersonUUID() {
		return personUUID;
	}

	public void setPersonUUID(UUID personUUID) {
		this.personUUID = personUUID;
	}

	public String getHomeBaseName() {
		return homeBaseName;
	}

	public void setHomeBaseName(String homeBaseName) {
		this.homeBaseName = homeBaseName;
	}

	public Date getInternshipEndDate() {
		return internshipEndDate;
	}

	public void setInternshipEndDate(Date internshipEndDate) {
		this.internshipEndDate = internshipEndDate;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public MediaDTO getMediaPhoto() {
		return mediaPhoto;
	}

	public void setMediaPhoto(MediaDTO mediaPhoto) {
		this.mediaPhoto = mediaPhoto;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isChangeMaritalStatus() {
		return changeMaritalStatus;
	}

	public void setChangeMaritalStatus(boolean changeMaritalStatus) {
		this.changeMaritalStatus = changeMaritalStatus;
	}

	public boolean isChangeHomebase() {
		return changeHomebase;
	}

	public void setChangeHomebase(boolean changeHomebase) {
		this.changeHomebase = changeHomebase;
	}

	public boolean isChangeReligion() {
		return changeReligion;
	}

	public void setChangeReligion(boolean changeReligion) {
		this.changeReligion = changeReligion;
	}

	public boolean isChangeMarriedWith() {
		return changeMarriedWith;
	}

	public void setChangeMarriedWith(boolean changeMarriedWith) {
		this.changeMarriedWith = changeMarriedWith;
	}

	public Long getSpouseCompanyId() {
		return spouseCompanyId;
	}

	public void setSpouseCompanyId(Long spouseCompanyId) {
		this.spouseCompanyId = spouseCompanyId;
	}

	public List<ContactDTO> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactDTO> contacts) {
		this.contacts = contacts;
	}

	@Override
	public Object getId() {
		return super.getPersonId();
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public Boolean getIsHeadOffice() {
		return isHeadOffice;
	}

	public void setIsHeadOffice(Boolean isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
	}

	public Long getCastEmployeeNumber() {
		return castEmployeeNumber;
	}

	public void setCastEmployeeNumber(Long castEmployeeNumber) {
		this.castEmployeeNumber = castEmployeeNumber;
	}

	public String getPeopleCategory() {
		return peopleCategory;
	}

	public void setPeopleCategory(String peopleCategory) {
		this.peopleCategory = peopleCategory;
	}

	public Long getMppTrnId() {
		return mppTrnId;
	}

	public void setMppTrnId(Long mppTrnId) {
		this.mppTrnId = mppTrnId;
	}

	public String getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

	@Override
	// TPS 20150406 ITSM 15033108454764 biar lebih kelihatan di log
	public int compareTo(PersonDTO compare) {
		int result = 0;

		try {
			Integer thisEmployeeNumber = Integer.parseInt(this.getEmployeeNumber());
			Integer compareEmployeeNumber = Integer.parseInt(compare.getEmployeeNumber());

			result = thisEmployeeNumber.compareTo(compareEmployeeNumber);
		} catch (Exception ex) {
			result = this.getEmployeeNumber().compareTo(compare.getEmployeeNumber());
		}
		
		return result;
	}

	@Override
	// TPS 20150406 ITSM 15033108454764 biar lebih kelihatan di log
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PersonDTO [employeeNumber=");
		builder.append(getEmployeeNumber());
		builder.append("]");
		return builder.toString();
	}
	
}

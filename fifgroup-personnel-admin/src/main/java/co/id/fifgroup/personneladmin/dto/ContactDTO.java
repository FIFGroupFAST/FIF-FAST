package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.personneladmin.domain.Contact;

public class ContactDTO extends Contact implements Comparable<ContactDTO> {

	private static final long serialVersionUID = 1L;

	private int sequenceFamilyMemberPriority;

	public int getSequenceFamilyMemberPriority() {
		return sequenceFamilyMemberPriority;
	}

	public void setSequenceFamilyMemberPriority(int sequenceFamilyMemberPriority) {
		this.sequenceFamilyMemberPriority = sequenceFamilyMemberPriority;
	}	

	@Override
	public int compareTo(ContactDTO arg0) {
		return this.sequenceFamilyMemberPriority
				- arg0.getSequenceFamilyMemberPriority();
	}

}

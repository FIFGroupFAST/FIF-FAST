package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.basicsetup.common.History;
import co.id.fifgroup.personneladmin.domain.BankInformation;

public class BankInformationDTO extends BankInformation implements History {

	private static final long serialVersionUID = 1L;

	private MediaDTO mediaBankBook;

	public MediaDTO getMediaBankBook() {
		return mediaBankBook;
	}

	public void setMediaBankBook(MediaDTO mediaBankBook) {
		this.mediaBankBook = mediaBankBook;
	}

}

package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.personneladmin.domain.Document;

public class DocumentDTO extends Document {

	private static final long serialVersionUID = 1L;

	private MediaDTO mediaDocument;

	public MediaDTO getMediaDocument() {
		return mediaDocument;
	}

	public void setMediaDocument(MediaDTO mediaDocument) {
		this.mediaDocument = mediaDocument;
	}

}

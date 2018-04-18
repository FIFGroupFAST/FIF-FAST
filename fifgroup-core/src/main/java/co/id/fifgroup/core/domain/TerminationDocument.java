package co.id.fifgroup.core.domain;

import java.io.Serializable;

public class TerminationDocument implements Serializable {

	private static final long serialVersionUID = 8776367203362945299L;

	private Long requestId;
	private Long typeDocumentId;
	private Long requestDocumentId;
	private String documentCode;
	private String description;
	private Boolean done;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getTypeDocumentId() {
		return typeDocumentId;
	}

	public void setTypeDocumentId(Long typeDocumentId) {
		this.typeDocumentId = typeDocumentId;
	}

	public Long getRequestDocumentId() {
		return requestDocumentId;
	}

	public void setRequestDocumentId(Long requestDocumentId) {
		this.requestDocumentId = requestDocumentId;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

}

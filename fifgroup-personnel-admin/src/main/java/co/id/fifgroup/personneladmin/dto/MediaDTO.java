package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;

import org.zkoss.util.media.Media;

public class MediaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Media media;
	private String documentType;
	private String employeeNumber;
	private String fileName;

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

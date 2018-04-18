package co.id.fifgroup.core.dto;

import java.io.Serializable;

public class UploadErrorDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String errorMessage;
	private Integer rowNumber;
	private String rawData;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	@Override
	public String toString() {
		return "UploadDto [id=" + id + ", errorMessage=" + errorMessage
				+ ", rowNumber=" + rowNumber + ", rawData=" + rawData + "]";
	}
	
	
	
}

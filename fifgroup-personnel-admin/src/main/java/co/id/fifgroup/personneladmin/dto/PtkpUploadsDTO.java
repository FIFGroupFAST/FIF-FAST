package co.id.fifgroup.personneladmin.dto;

import java.util.Date;

import co.id.fifgroup.core.domain.BatchUpload;

public class PtkpUploadsDTO extends BatchUpload {

	private static final long serialVersionUID = 1L;

	private String userName;
	private Date processDateFrom;
	private Date processDateTo;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getProcessDateFrom() {
		return processDateFrom;
	}

	public void setProcessDateFrom(Date processDateFrom) {
		this.processDateFrom = processDateFrom;
	}

	public Date getProcessDateTo() {
		return processDateTo;
	}

	public void setProcessDateTo(Date processDateTo) {
		this.processDateTo = processDateTo;
	}

}

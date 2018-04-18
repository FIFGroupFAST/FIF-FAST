package co.id.fifgroup.workstructure.dto;

import java.util.Date;

import co.id.fifgroup.core.domain.BatchUpload;

public class OrganizationHierarchyUploadsDTO extends BatchUpload {
	private static final long serialVersionUID = 1621722302274530797L;
	private String userName;
	private Date processDateFrom;
	private Date processDateTo;
	private Boolean isClosed = false;
	private String orgHierName;
	private Date dateFrom;
	private Date dateTo;
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public String getOrgHierName() {
		return orgHierName;
	}
	public void setOrgHierName(String orgHierName) {
		this.orgHierName = orgHierName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
	public Boolean getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}	
	
}

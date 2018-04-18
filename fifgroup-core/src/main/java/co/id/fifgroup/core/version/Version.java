package co.id.fifgroup.core.version;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class Version implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long versionId;
	private Integer versionNumber;
	private Date dateFrom;
	private Date dateTo;
	
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;

	public Version() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

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
	
	public Integer getNextVersion() {
		return versionNumber + 1;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isCurrent() {
		if (null == dateFrom || null == dateTo)
			return false;
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		Date dateFromObject = DateUtils.truncate(dateFrom, Calendar.DATE);
		Date dateToObject = DateUtils.truncate(dateTo, Calendar.DATE);
		if(dateFromObject != null && dateToObject != null) {
			return ( (today.compareTo(dateFromObject) > 0 || today.compareTo(dateFromObject) == 0) && (today.compareTo(dateToObject) < 0 || today.compareTo(dateToObject) == 0) );
		}
		return false;
	}
	
	public boolean isFuture() {
		if (null == dateFrom) 
			return false;
		Date dateFromObject = DateUtils.truncate(dateFrom, Calendar.DATE);
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		return (dateFromObject.compareTo(today) > 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((versionId == null) ? 0 : versionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (versionId == null) {
			if (other.versionId != null)
				return false;
		} else if (!versionId.equals(other.versionId))
			return false;
		return true;
	}
	
	

}

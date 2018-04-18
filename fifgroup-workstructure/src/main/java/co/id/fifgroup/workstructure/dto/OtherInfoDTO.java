package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.util.Date;

public class OtherInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long versionId;
	private Long infoId;
	private String infoValue;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	
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
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	public String getInfoValue() {
		return infoValue;
	}
	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
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
	@Override
	public String toString() {
		return "OtherInfoDTO [versionId=" + versionId + ", otherInfoId="
				+ infoId + ", otherInfoValue=" + infoValue + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((infoValue == null) ? 0 : infoValue.hashCode());
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
		OtherInfoDTO other = (OtherInfoDTO) obj;
		if (infoValue == null) {
			if (other.infoValue != null)
				return false;
		} else if (!infoValue.equals(other.infoValue))
			return false;
		return true;
	}
}

package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.eligibility.constant.RetrievalMode;

public class FactTypeDTO implements Serializable{

	private static final long serialVersionUID = -5320263210992990708L;
	
	private String id;
	private String name;
	private Long companyId;
	private RetrievalMode retrievalMode;
	private String sqlQuery;
	private Boolean editable = Boolean.TRUE;

	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;

	private List<FactTypeFieldDTO> fields = new ArrayList<>();

	public FactTypeDTO() { }
	
	public FactTypeDTO(String id) {
		this.id = id;
	}

	public FactTypeDTO(String name, Long companyId,
			RetrievalMode retrievalMode, String sqlQuery) {
		this.name = name;
		this.companyId = companyId;
		this.retrievalMode = retrievalMode;
		this.sqlQuery = sqlQuery;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public RetrievalMode getRetrievalMode() {
		return retrievalMode;
	}

	public void setRetrievalMode(RetrievalMode retrievalMode) {
		this.retrievalMode = retrievalMode;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
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

	public List<FactTypeFieldDTO> getFields() {
		return fields;
	}

	public void setFields(List<FactTypeFieldDTO> fields) {
		this.fields = fields;
	}

	public Boolean isEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		return "FactTypeDTO [id=" + id + ", name=" + name + ", companyId="
				+ companyId + ", retrievalMode=" + retrievalMode
				+ ", sqlQuery=" + sqlQuery + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastUpdateDate=" + lastUpdateDate
				+ ", fields=" + fields + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FactTypeDTO other = (FactTypeDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	

}

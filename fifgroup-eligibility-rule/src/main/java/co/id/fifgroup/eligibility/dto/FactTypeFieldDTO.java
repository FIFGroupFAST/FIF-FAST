package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.Objects;

import co.id.fifgroup.eligibility.constant.FieldType;
import co.id.fifgroup.eligibility.constant.LookupValueFrom;

public class FactTypeFieldDTO implements Serializable {

	private static final long serialVersionUID = -5407930885098479635L;
	
	private String id;
	private String factTypeId;
	private String name;
	private FieldType fieldType;
	private String lookupName;
	private LookupValueFrom lookupValueFrom;
	
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	
	private Boolean editable = Boolean.TRUE;
	
	public FactTypeFieldDTO() { }
	
	public FactTypeFieldDTO(String id, String name, FieldType fieldType, String lookupName) {
		this.id = id;
		this.name = name;
		this.fieldType = fieldType;
		this.lookupName = lookupName;
	}
	
	public FactTypeFieldDTO(String id, String name, FieldType fieldType) {
		this(id, name, fieldType, null);
	}
	
	public String getFactTypeId() {
		return factTypeId;
	}

	public void setFactTypeId(String factTypeId) {
		this.factTypeId = factTypeId;
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

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	public String getLookupName() {
		return lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
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
	
	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	
	public LookupValueFrom getLookupValueFrom() {
		return lookupValueFrom;
	}

	public void setLookupValueFrom(LookupValueFrom lookupValueFrom) {
		this.lookupValueFrom = lookupValueFrom;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FactTypeFieldDTO other = (FactTypeFieldDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("name", name)
				.add("fieldType", fieldType)
				.add("lookupName", lookupName)
				.toString();
	}
}

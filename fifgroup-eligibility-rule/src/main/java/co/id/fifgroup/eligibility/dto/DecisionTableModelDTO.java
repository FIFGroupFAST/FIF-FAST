package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DecisionTableModelDTO implements Serializable {

	private static final long serialVersionUID = -2157465123659228060L;

	private String id;
	private Long companyId;
	private String name;
	private Integer versionNumber;
	
	private List<DecisionTableColumnDTO> conditions = new ArrayList<>();
	
	private FactTypeDTO resultType = new FactTypeDTO();
	private List<DecisionTableColumnDTO> results = new ArrayList<>();

	private Long createdBy;
	private Date creationDate;
	private Long lastUpdatedBy;
	private Date lastUpdateDate;
	private Boolean editable = Boolean.TRUE;
	
	public DecisionTableModelDTO () { }
	public DecisionTableModelDTO(String id, Integer versionNumber) {
		this.id = id;
		this.versionNumber = versionNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
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

	public List<DecisionTableColumnDTO> getConditions() {
		return conditions;
	}

	public void setConditions(List<DecisionTableColumnDTO> conditions) {
		this.conditions = conditions;
	}

	public FactTypeDTO getResultType() {
		return resultType;
	}

	public void setResultType(FactTypeDTO resultType) {
		this.resultType = resultType;
	}

	public List<DecisionTableColumnDTO> getResults() {
		return results;
	}

	public void setResults(List<DecisionTableColumnDTO> results) {
		this.results = results;
	}
	
	public List<String> getFactTypes() {
		List<String> result = new ArrayList<>();
		
		for(DecisionTableColumnDTO column : conditions) {
			if (!result.contains(column.getFactType().getId()))
				result.add(column.getFactType().getId());
		}
		
		return result;
	}

	public Boolean getEditable() {
		return editable;
	}
	
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	@Override
	public String toString() {
		return "DecisionTableModelDTO [id=" + id + ", companyId=" + companyId
				+ ", name=" + name + ", versionNumber=" + versionNumber
				+ ", conditions=" + conditions + ", resultType=" + resultType
				+ ", results=" + results + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastUpdateDate=" + lastUpdateDate + "]";
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
		DecisionTableModelDTO other = (DecisionTableModelDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}

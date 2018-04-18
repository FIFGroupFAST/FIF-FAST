package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class RetirementRV {
	private Long id;
	private String rvNo;
	private Long retirementId;
	
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	public RetirementRV() {
		
	}

	public String getRvNo() {
		return rvNo;
	}

	public void setRvNo(String rvNo) {
		this.rvNo = rvNo;
	}

	public Long getRetirementId() {
		return retirementId;
	}

	public void setRetirementId(Long retirementId) {
		this.retirementId = retirementId;
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

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	
	

	
	
	
	
}
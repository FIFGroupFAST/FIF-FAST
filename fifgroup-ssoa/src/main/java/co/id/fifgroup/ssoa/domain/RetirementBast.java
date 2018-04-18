package co.id.fifgroup.ssoa.domain;

import java.util.Date;

public class RetirementBast {
	private Long id;
	private Long retirementId;
	private String bastDocPath;
	private String bastDocName;
	private byte[] imageFile;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

	public RetirementBast() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRetirementId() {
		return retirementId;
	}

	public void setRetirementId(Long retirementId) {
		this.retirementId = retirementId;
	}

	public String getBastDocPath() {
		return bastDocPath;
	}

	public void setBastDocPath(String bastDocPath) {
		this.bastDocPath = bastDocPath;
	}

	public String getBastDocName() {
		return bastDocName;
	}

	public void setBastDocName(String bastDocName) {
		this.bastDocName = bastDocName;
	}

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
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

	

	
	
	

	
	
	
	
}
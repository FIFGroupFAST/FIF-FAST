package co.id.fifgroup.ssoa.domain;

import java.io.Serializable;
import java.util.Date;

public class SystemProperty implements Serializable {
	private String action;
	private String systemPropertyCode;
	private String systemPropertyName;
	private String systemPropertyValue;
	private Long createdBy;
	private Date creationDate;
	private Long lastUpdateBy;
	private Date lastUpdateDate;

/*	public SystemProperty() { }

	public SystemProperty(String action, String systemPropertyCode, String systemPropertyName, 
						String systemPropertyValue, Number createdBy, Date creationDate, 
						Number lastUpdateBy, Date lastUpdateDate) {
		super();
		this.action = action;
		this.systemPropertyCode = systemPropertyCode;
		this.systemPropertyName = systemPropertyName;
		this.systemPropertyValue = systemPropertyValue;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.lastUpdateBy = lastUpdateBy;
		this.lastUpdateDate = lastUpdateDate;
	}*/
	
	private static final long serialVersionUID = 1L;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSystemPropertyCode() {
		return systemPropertyCode;
	}

	public void setSystemPropertyCode(String systemPropertyCode) {
		this.systemPropertyCode = systemPropertyCode;
	}

	public String getSystemPropertyName() {
		return systemPropertyName;
	}

	public void setSystemPropertyName(String systemPropertyName) {
		this.systemPropertyName = systemPropertyName;
	}

	public String getSystemPropertyValue() {
		return systemPropertyValue;
	}

	public void setSystemPropertyValue(String systemPropertyValue) {
		this.systemPropertyValue = systemPropertyValue;
	}

	

	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
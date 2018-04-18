package co.id.fifgroup.basicsetup.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaultParameterFormula implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5308975483745471036L;
	private Long businessGroupId = 0L;
	private Long companyId = 0L;
	private Long personId = 0L;
	private String moduleType = "";
	private Date processDate = new Date();
	private Map<String, Object> additionalParam = new HashMap<String, Object>();
	
	public Long getBusinessGroupId() {
		return businessGroupId;
	}
	public void setBusinessGroupId(Long businessGroupId) {
		this.businessGroupId = businessGroupId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public Map<String, Object> getAdditionalParam() {
		return additionalParam;
	}
	public void setAdditionalParam(Map<String, Object> additionalParam) {
		this.additionalParam = additionalParam;
	}
	
	
}

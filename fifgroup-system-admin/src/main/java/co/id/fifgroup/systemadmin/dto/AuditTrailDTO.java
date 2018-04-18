package co.id.fifgroup.systemadmin.dto;

import co.id.fifgroup.systemadmin.domain.AuditDetail;
import co.id.fifgroup.systemadmin.domain.AuditTrail;

public class AuditTrailDTO extends AuditTrail {
	
	private static final long serialVersionUID = 1L;
	private AuditDetail auditDetail;
	private int number;
	private String moduleName;
	private String activityName;
	private String userName;
	
	public AuditDetail getAuditDetail() {
		return auditDetail;
	}
	public void setAuditDetail(AuditDetail auditDetail) {
		this.auditDetail = auditDetail;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
}

package co.id.fifgroup.core.dto;

import java.util.Date;


public class EmployeeCompetencyDTO {

	public static final String FLAG_EMP_SELF_SERV = "com.emp.lev.src.hide";
	public static final String FLAG_TLD_PERSONEL_ADMIN = "com.emp.tld.soft.tech.hide";
	public static final String FLAG_HCOD_ACHIEV_HIDE = "com.pea.hcod.achiev.hide";
	
	private String competencyGroup;
	private String competencyCode;
	private String competencyName;
	private String description;
	private String parentName;
	private String minimumReq;
	private String achievmentLevel;
	private String achievmentSource;
	private Date achievmentDate;
	private Date expiredDate;
	
	public String getCompetencyCode() {
		return competencyCode;
	}
	public void setCompetencyCode(String competencyCode) {
		this.competencyCode = competencyCode;
	}
	public String getCompetencyName() {
		return competencyName;
	}
	public void setCompetencyName(String competencyName) {
		this.competencyName = competencyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getMinimumReq() {
		return minimumReq;
	}
	public void setMinimumReq(String minimumReq) {
		this.minimumReq = minimumReq;
	}
	public String getAchievmentLevel() {
		return achievmentLevel;
	}
	public void setAchievmentLevel(String achievmentLevel) {
		this.achievmentLevel = achievmentLevel;
	}
	public String getAchievmentSource() {
		return achievmentSource;
	}
	public void setAchievmentSource(String achievmentSource) {
		this.achievmentSource = achievmentSource;
	}
	public Date getAchievmentDate() {
		return achievmentDate;
	}
	public void setAchievmentDate(Date achievmentDate) {
		this.achievmentDate = achievmentDate;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getCompetencyGroup() {
		return competencyGroup;
	}
	public void setCompetencyGroup(String competencyGroup) {
		this.competencyGroup = competencyGroup;
	}
}

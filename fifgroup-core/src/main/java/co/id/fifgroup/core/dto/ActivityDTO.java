package co.id.fifgroup.core.dto;

import java.io.Serializable;

public class ActivityDTO implements Serializable{

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
//	private String costCenterCode;
//	private Long activityCode;
//	private String activityName;
//	private String strategicObjective;
//	private String startegicInitiative;
//	private String activityType;
//	private String subActivity;
//	private String deliverable;
//	private String kpiMeasurement;
//	private String kpiTarget;
//	private String ippWeight;
//	private String ippTarget;
//	private Date startDate;
//	private Date endDate;
//	private String leader;
//	private String teamMember;
//	private String relatedFunction;
//	private Boolean capex;
//	private Boolean opex;
//	private Boolean status;
//	private String createdBy;
//	private Date createdDate;
//	private String updatedBy;
//	private Date updatedDate;
//	public String getCostCenterCode() {
//		return costCenterCode;
//	}
//	public void setCostCenterCode(String costCenterCode) {
//		this.costCenterCode = costCenterCode;
//	}
//	public Long getActivityCode() {
//		return activityCode;
//	}
//	public void setActivityCode(Long activityCode) {
//		this.activityCode = activityCode;
//	}
//	public String getActivityName() {
//		return activityName;
//	}
//	public void setActivityName(String activityName) {
//		this.activityName = activityName;
//	}
//	public String getStrategicObjective() {
//		return strategicObjective;
//	}
//	public void setStrategicObjective(String strategicObjective) {
//		this.strategicObjective = strategicObjective;
//	}
//	public String getStartegicInitiative() {
//		return startegicInitiative;
//	}
//	public void setStartegicInitiative(String startegicInitiative) {
//		this.startegicInitiative = startegicInitiative;
//	}
//	public String getActivityType() {
//		return activityType;
//	}
//	public void setActivityType(String activityType) {
//		this.activityType = activityType;
//	}
//	public String getSubActivity() {
//		return subActivity;
//	}
//	public void setSubActivity(String subActivity) {
//		this.subActivity = subActivity;
//	}
//	public String getDeliverable() {
//		return deliverable;
//	}
//	public void setDeliverable(String deliverable) {
//		this.deliverable = deliverable;
//	}
//	public String getKpiMeasurement() {
//		return kpiMeasurement;
//	}
//	public void setKpiMeasurement(String kpiMeasurement) {
//		this.kpiMeasurement = kpiMeasurement;
//	}
//	public String getKpiTarget() {
//		return kpiTarget;
//	}
//	public void setKpiTarget(String kpiTarget) {
//		this.kpiTarget = kpiTarget;
//	}
//	public String getIppWeight() {
//		return ippWeight;
//	}
//	public void setIppWeight(String ippWeight) {
//		this.ippWeight = ippWeight;
//	}
//	public String getIppTarget() {
//		return ippTarget;
//	}
//	public void setIppTarget(String ippTarget) {
//		this.ippTarget = ippTarget;
//	}
//	public Date getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//	public Date getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//	public String getLeader() {
//		return leader;
//	}
//	public void setLeader(String leader) {
//		this.leader = leader;
//	}
//	public String getTeamMember() {
//		return teamMember;
//	}
//	public void setTeamMember(String teamMember) {
//		this.teamMember = teamMember;
//	}
//	public String getRelatedFunction() {
//		return relatedFunction;
//	}
//	public void setRelatedFunction(String relatedFunction) {
//		this.relatedFunction = relatedFunction;
//	}
//	public Boolean getCapex() {
//		return capex;
//	}
//	public void setCapex(Boolean capex) {
//		this.capex = capex;
//	}
//	public Boolean getOpex() {
//		return opex;
//	}
//	public void setOpex(Boolean opex) {
//		this.opex = opex;
//	}
//	public Boolean getStatus() {
//		return status;
//	}
//	public void setStatus(Boolean status) {
//		this.status = status;
//	}
//	public String getCreatedBy() {
//		return createdBy;
//	}
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//	public String getUpdatedBy() {
//		return updatedBy;
//	}
//	public void setUpdatedBy(String updatedBy) {
//		this.updatedBy = updatedBy;
//	}
//	public Date getUpdatedDate() {
//		return updatedDate;
//	}
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}
private static final long serialVersionUID = 1L;
	
	private String trxCode;
	private String trxDesc;
	private String systemDesc;
	private String coaCode;
	private String activityCode;
	private String activityName;
	
	public String getTrxCode() {
		return trxCode;
	}
	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}
	public String getTrxDesc() {
		return trxDesc;
	}
	public void setTrxDesc(String trxDesc) {
		this.trxDesc = trxDesc;
	}
	public String getSystemDesc() {
		return systemDesc;
	}
	public void setSystemDesc(String systemDesc) {
		this.systemDesc = systemDesc;
	}
	public String getCoaCode() {
		return coaCode;
	}
	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activityCode == null) ? 0 : activityCode.hashCode());
		result = prime * result
				+ ((activityName == null) ? 0 : activityName.hashCode());
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
		ActivityDTO other = (ActivityDTO) obj;
		if (activityCode == null) {
			if (other.activityCode != null)
				return false;
		} else if (!activityCode.equals(other.activityCode))
			return false;
		if (activityName == null) {
			if (other.activityName != null)
				return false;
		} else if (!activityName.equals(other.activityName))
			return false;
		return true;
	}
	
	
	
}

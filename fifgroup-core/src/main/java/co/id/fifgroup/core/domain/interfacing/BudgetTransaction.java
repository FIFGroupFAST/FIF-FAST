package co.id.fifgroup.core.domain.interfacing;

import java.util.Date;

public class BudgetTransaction extends Budget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String activityPlanPeriod;
	private Integer actionCode;
	private String executor;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityPlanPeriod() {
		return activityPlanPeriod;
	}

	public void setActivityPlanPeriod(String activityPlanPeriod) {
		this.activityPlanPeriod = activityPlanPeriod;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}

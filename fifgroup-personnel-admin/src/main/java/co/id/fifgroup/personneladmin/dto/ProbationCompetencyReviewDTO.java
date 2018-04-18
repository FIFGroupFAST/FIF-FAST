package co.id.fifgroup.personneladmin.dto;

import co.id.fifgroup.personneladmin.domain.ProbationCompetencyReview;

public class ProbationCompetencyReviewDTO extends ProbationCompetencyReview {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long compGroupDtlId;
	private String compGroupName;
	private String competencyCode;
	private String competencyName;
	private String parentName;
	private boolean mandatory;
	private String minimumLevelName;
	private String minimumLevelDescription;
	private Integer minimumSequence;
	private String actualLevelName;
	private String actualLevelDescription;
	private Integer actualSequence;
	private String currentLevel;
	private String achievmentSource;
	private String competencyType;

	public Long getCompGroupDtlId() {
		return compGroupDtlId;
	}

	public void setCompGroupDtlId(Long compGroupDtlId) {
		this.compGroupDtlId = compGroupDtlId;
	}

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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getMinimumLevelName() {
		return minimumLevelName;
	}

	public void setMinimumLevelName(String minimumLevelName) {
		this.minimumLevelName = minimumLevelName;
	}

	public String getMinimumLevelDescription() {
		return minimumLevelDescription;
	}

	public void setMinimumLevelDescription(String minimumLevelDescription) {
		this.minimumLevelDescription = minimumLevelDescription;
	}

	public Integer getMinimumSequence() {
		return minimumSequence;
	}

	public void setMinimumSequence(Integer minimumSequence) {
		this.minimumSequence = minimumSequence;
	}

	public String getActualLevelName() {
		return actualLevelName;
	}

	public void setActualLevelName(String actualLevelName) {
		this.actualLevelName = actualLevelName;
	}

	public String getActualLevelDescription() {
		return actualLevelDescription;
	}

	public void setActualLevelDescription(String actualLevelDescription) {
		this.actualLevelDescription = actualLevelDescription;
	}

	public Integer getActualSequence() {
		return actualSequence;
	}

	public void setActualSequence(Integer actualSequence) {
		this.actualSequence = actualSequence;
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getAchievmentSource() {
		return achievmentSource;
	}

	public void setAchievmentSource(String achievmentSource) {
		this.achievmentSource = achievmentSource;
	}

	public String getCompetencyType() {
		return competencyType;
	}

	public void setCompetencyType(String competencyType) {
		this.competencyType = competencyType;
	}

	public String getCompGroupName() {
		return compGroupName;
	}

	public void setCompGroupName(String compGroupName) {
		this.compGroupName = compGroupName;
	}

}

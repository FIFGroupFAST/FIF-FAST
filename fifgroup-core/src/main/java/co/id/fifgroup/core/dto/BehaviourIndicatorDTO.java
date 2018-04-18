package co.id.fifgroup.core.dto;

import java.io.Serializable;

public class BehaviourIndicatorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long behaviourId;
	private Long groupDtlId;
	private String behaviourIndicator;
	private Long proficiencyDetailId;
	private Integer sequence;
	private String levelName;
	private String description;
	private Boolean isDefault;

	public Long getBehaviourId() {
		return behaviourId;
	}

	public void setBehaviourId(Long behaviourId) {
		this.behaviourId = behaviourId;
	}

	public Long getGroupDtlId() {
		return groupDtlId;
	}

	public void setGroupDtlId(Long groupDtlId) {
		this.groupDtlId = groupDtlId;
	}

	public String getBehaviourIndicator() {
		return behaviourIndicator;
	}

	public void setBehaviourIndicator(String behaviourIndicator) {
		this.behaviourIndicator = behaviourIndicator;
	}

	public Long getProficiencyDetailId() {
		return proficiencyDetailId;
	}

	public void setProficiencyDetailId(Long proficiencyDetailId) {
		this.proficiencyDetailId = proficiencyDetailId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}

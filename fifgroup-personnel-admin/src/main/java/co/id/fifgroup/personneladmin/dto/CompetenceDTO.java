package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class CompetenceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private String behaviouralIndicator;
	private Long score;
	private Date effectiveStartDate;
	private Long competenceId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBehaviouralIndicator() {
		return behaviouralIndicator;
	}

	public void setBehaviouralIndicator(String behaviouralIndicator) {
		this.behaviouralIndicator = behaviouralIndicator;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	public Long getCompetenceId() {
		return competenceId;
	}

	public void setCompetenceId(Long competenceId) {
		this.competenceId = competenceId;
	}

}

package co.id.fifgroup.personneladmin.dto;

import java.io.Serializable;
import java.util.Date;

public class TrainingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String traineeName;
	private String traineeEmployeeNumber;
	private String title;
	private Date trainingStartDate;
	private Date trainingEndDate;
	private String objective;
	private String score;
	private String idpCategory;

	public String getTraineeName() {
		return traineeName;
	}

	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}

	public String getTraineeEmployeeNumber() {
		return traineeEmployeeNumber;
	}

	public void setTraineeEmployeeNumber(String traineeEmployeeNumber) {
		this.traineeEmployeeNumber = traineeEmployeeNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTrainingStartDate() {
		return trainingStartDate;
	}

	public void setTrainingStartDate(Date trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
	}

	public Date getTrainingEndDate() {
		return trainingEndDate;
	}

	public void setTrainingEndDate(Date trainingEndDate) {
		this.trainingEndDate = trainingEndDate;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getIdpCategory() {
		return idpCategory;
	}

	public void setIdpCategory(String idpCategory) {
		this.idpCategory = idpCategory;
	}

}

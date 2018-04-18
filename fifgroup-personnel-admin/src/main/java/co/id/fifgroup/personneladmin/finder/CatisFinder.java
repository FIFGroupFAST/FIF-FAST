package co.id.fifgroup.personneladmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.AssessmentDTO;
import co.id.fifgroup.personneladmin.dto.CompetenceDTO;
import co.id.fifgroup.personneladmin.dto.HavDTO;
import co.id.fifgroup.personneladmin.dto.PerformanceReviewDTO;
import co.id.fifgroup.personneladmin.dto.PresentationDTO;
import co.id.fifgroup.personneladmin.dto.TrainingDTO;

import co.id.fifgroup.core.dto.DisciplinaryLetterDTO;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.personneladmin.domain.ValidationLearningPath;

public interface CatisFinder {

	public List<TrainingDTO> getTrainingInformationByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
	
	public List<DisciplinaryLetterDTO> getDiciplinaryLetterByPersonId(@Param("personId") Long personId);
	
	public List<PerformanceReviewDTO> getPerformanceReviewByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
	
	public List<HavDTO> getHavByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
	
	public List<CompetenceDTO> getCompetencesByEmployeeNumberAndJobCodeHrms(@Param("employeeNumber") String employeeNumber, @Param("jobCodeHrms") String jobCodeHrms);
	
	public List<AssessmentDTO> getAssesmentByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
	
	public List<PresentationDTO> getPresentationByEmployeeNumber(@Param("employeeNumber") String employeeNumber);
	
	public List<KeyValue> getPerformanceRating();
	
	public void insertPerformanceReview(PerformanceReviewDTO performanceReviewDTO);
	
	public void getValidationLearningPath(ValidationLearningPath validationLearningPath);
	
	public Long getPersonId(@Param("employeeNumber") String employeeNumber);
	
	public List<PerformanceReviewDTO> getPerformanceAppraisalInquiry(@Param("personId") Long personId);
}

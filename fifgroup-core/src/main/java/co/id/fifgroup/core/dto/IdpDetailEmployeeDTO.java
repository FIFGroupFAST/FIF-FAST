package co.id.fifgroup.core.dto;

import java.util.List;

import co.id.fifgroup.core.domain.IdpDetail;

public class IdpDetailEmployeeDTO extends IdpDetail{

	private static final long serialVersionUID = 1L;
	
	/* Used to get IDP from iLearning */
	public Integer courseVersionNumber;
	public Long courseVersionId;
	public Long courseId;
	/* ----------------------------- */
	
	public String developmentName;
	public String coeName;
//	public List<CompetencyDetailDTO> competencyDetails;
//	private CoeRequestDTO coeRequestDTO;
	private boolean isSubmitted = false;
	
	public Integer getCourseVersionNumber() {
		return courseVersionNumber;
	}

	public void setCourseVersionNumber(Integer courseVersionNumber) {
		this.courseVersionNumber = courseVersionNumber;
	}

	public Long getCourseVersionId() {
		return courseVersionId;
	}

	public void setCourseVersionId(Long courseVersionId) {
		this.courseVersionId = courseVersionId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
//	public List<CompetencyDetailDTO> getCompetencyDetails() {
//		return competencyDetails;
//	}
//
//	public void setCompetencyDetails(List<CompetencyDetailDTO> competencyDetails) {
//		this.competencyDetails = competencyDetails;
//	}

	public String getCoeName() {
		return coeName;
	}

	public void setCoeName(String coeName) {
		this.coeName = coeName;
	}

	public String getDevelopmentName() {
		return developmentName;
	}

	public void setDevelopmentName(String developmentName) {
		this.developmentName = developmentName;
	}

//	public CoeRequestDTO getCoeRequestDTO() {
//		return coeRequestDTO;
//	}
//
//	public void setCoeRequestDTO(CoeRequestDTO coeRequestDTO) {
//		this.coeRequestDTO = coeRequestDTO;
//	}

	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}

}

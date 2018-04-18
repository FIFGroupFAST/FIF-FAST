package co.id.fifgroup.systemadmin.dto;

import java.util.List;

import co.id.fifgroup.core.audit.Traversable;
import co.id.fifgroup.systemadmin.domain.ResponsibilityCompany;

public class ResponsibilityCompanyDTO extends ResponsibilityCompany implements Traversable {
	
	private static final long serialVersionUID = 1L;
	private List<IncludedOrganizationDTO> includedOrganizationByAssignments;
	private List<GradeExclusionDTO> gradeExclusionByAssignments;
	private List<IncludedOrganizationDTO> includedOrganizationMultiCompanys;
	private List<GradeExclusionDTO> gradeExclusionMultiCompanys;
	private String companyName;
	
	@Override
	public Long getId() {
		return super.getId();
	}
	
	public List<IncludedOrganizationDTO> getIncludedOrganizationByAssignments() {
		return includedOrganizationByAssignments;
	}
	public void setIncludedOrganizationByAssignments(List<IncludedOrganizationDTO> includedOrganizationByAssignments) {
		this.includedOrganizationByAssignments = includedOrganizationByAssignments;
	}
	public List<GradeExclusionDTO> getGradeExclusionByAssignments() {
		return gradeExclusionByAssignments;
	}
	public void setGradeExclusionByAssignments(List<GradeExclusionDTO> gradeExclusionByAssignments) {
		this.gradeExclusionByAssignments = gradeExclusionByAssignments;
	}
	public List<IncludedOrganizationDTO> getIncludedOrganizationMultiCompanys() {
		return includedOrganizationMultiCompanys;
	}
	public void setIncludedOrganizationMultiCompanys(List<IncludedOrganizationDTO> includedOrganizationMultiCompanys) {
		this.includedOrganizationMultiCompanys = includedOrganizationMultiCompanys;
	}
	public List<GradeExclusionDTO> getGradeExclusionMultiCompanys() {
		return gradeExclusionMultiCompanys;
	}
	public void setGradeExclusionMultiCompanys(List<GradeExclusionDTO> gradeExclusionMultiCompanys) {
		this.gradeExclusionMultiCompanys = gradeExclusionMultiCompanys;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}

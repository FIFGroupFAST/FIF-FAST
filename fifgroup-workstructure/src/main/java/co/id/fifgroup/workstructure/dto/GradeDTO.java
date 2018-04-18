package co.id.fifgroup.workstructure.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.version.Version;

import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;

public class GradeDTO extends Version {

	private static final long serialVersionUID = 1L;

	private Long gradeId;
	private Long companyId;
	private String code;
	private String name;
	private String grade;
	private String subGrade;
	private String personalGradeCode;
	private String personalGrade;
	private String description;
	private Long workingScheduleId;
	private List<GradeRateDTO> gradeRates = new ArrayList<>();
	private List<OtherInfoDTO> gradeInfos = new ArrayList<>();
	private Date effectiveOnDate;
	private String userName;
	private OtherInfoComponent otherInfoComponent;
	private boolean isUpload = true;
	private boolean isValidGradeRates;
	private boolean isValidOtherInfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		if(name != null) {
			return name;
		} else if(grade != null && subGrade != null) {
			return grade.concat("-").concat(subGrade);
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubGrade() {
		return subGrade;
	}

	public void setSubGrade(String subGrade) {
		this.subGrade = subGrade;
	}

	public String getPersonalGradeCode() {
		return personalGradeCode;
	}

	public void setPersonalGradeCode(String personalGradeCode) {
		this.personalGradeCode = personalGradeCode;
	}

	public String getPersonalGrade() {
		return personalGrade;
	}

	public void setPersonalGrade(String personalGrade) {
		this.personalGrade = personalGrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}

	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}

	public List<GradeRateDTO> getGradeRates() {
		return gradeRates;
	}

	public void setGradeRates(List<GradeRateDTO> gradeRates) {
		this.gradeRates = gradeRates;
	}

	public Date getEffectiveOnDate() {
		return effectiveOnDate;
	}

	public void setEffectiveOnDate(Date effectiveOnDate) {
		this.effectiveOnDate = effectiveOnDate;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<OtherInfoDTO> getGradeInfos() {
		return gradeInfos;
	}

	public void setGradeInfos(List<OtherInfoDTO> gradeInfos) {
		this.gradeInfos = gradeInfos;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public OtherInfoComponent getOtherInfoComponent() {
		return otherInfoComponent;
	}

	public void setOtherInfoComponent(OtherInfoComponent otherInfoComponent) {
		this.otherInfoComponent = otherInfoComponent;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}
	
	@Override
	public String toString() {
		return "GradeDTO [gradeId=" + gradeId + ", grade=" + grade
				+ ", subGrade=" + subGrade + ", personalGradeCode="
				+ personalGradeCode + ", gradeRates=" + gradeRates
				+ ", gradeInfos=" + gradeInfos + ", getDateFrom()="
				+ getDateFrom() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradeDTO other = (GradeDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public boolean isValidGradeRates() {
		return isValidGradeRates;
	}

	public void setValidGradeRates(boolean isValidGradeRates) {
		this.isValidGradeRates = isValidGradeRates;
	}

	public boolean isValidOtherInfo() {
		return isValidOtherInfo;
	}

	public void setValidOtherInfo(boolean isValidOtherInfo) {
		this.isValidOtherInfo = isValidOtherInfo;
	}
	
}

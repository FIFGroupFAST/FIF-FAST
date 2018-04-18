package co.id.fifgroup.workstructure.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.UploadStage;

public class GradeStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;
	
	private Date dateFrom;
	private String dateFr;
	private String code;
	private String grade;
	private String subGrade;
	private String description;
	private String personalGradeCode;
	private String branchCode;
	private BigDecimal minSalary;
	private String minSal;
	private BigDecimal maxSalary;
	private String maxSal;
	private Long branchId;
	private Date dateTo;
	private List<GradeRateDTO> gradeRates;
	private String workingSchedule;
	private Long workingScheduleId;
	private Long companyId;
	private List<String> otherInfoStrings = new ArrayList<>();
	public String getMinSal() {
		return minSal;
	}
	public void setMinSal(String minSal) {
		this.minSal = minSal;
	}
	public String getMaxSal() {
		return maxSal;
	}
	public void setMaxSal(String maxSal) {
		this.maxSal = maxSal;
	}
	public List<String> getOtherInfoStrings() {
		return otherInfoStrings;
	}
	public void setOtherInfoStrings(List<String> otherInfoStrings) {
		this.otherInfoStrings = otherInfoStrings;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateFr() {
		return dateFr;
	}
	public void setDateFr(String dateFr) {
		this.dateFr = dateFr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPersonalGradeCode() {
		return personalGradeCode;
	}
	public void setPersonalGradeCode(String personalGradeCode) {
		this.personalGradeCode = personalGradeCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public BigDecimal getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(BigDecimal minSalary) {
		this.minSalary = minSalary;
	}
	public BigDecimal getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(BigDecimal maxSalary) {
		this.maxSalary = maxSalary;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public List<GradeRateDTO> getGradeRates() {
		return gradeRates;
	}
	public void setGradeRates(List<GradeRateDTO> gradeRates) {
		this.gradeRates = gradeRates;
	}
	public String getWorkingSchedule() {
		return workingSchedule;
	}
	public void setWorkingSchedule(String workingSchedule) {
		this.workingSchedule = workingSchedule;
	}
	public Long getWorkingScheduleId() {
		return workingScheduleId;
	}
	public void setWorkingScheduleId(Long workingScheduleId) {
		this.workingScheduleId = workingScheduleId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "GradeStageDTO [dateFrom=" + dateFrom + ", code=" + code
				+ ", grade=" + grade + ", subGrade=" + subGrade
				+ ", description=" + description + ", personalGradeCode="
				+ personalGradeCode + ", branchCode=" + branchCode
				+ ", minSalary=" + minSalary + ", maxSalary=" + maxSalary
				+ ", branchId=" + branchId + ", gradeRates=" + gradeRates + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		GradeStageDTO other = (GradeStageDTO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}	
}
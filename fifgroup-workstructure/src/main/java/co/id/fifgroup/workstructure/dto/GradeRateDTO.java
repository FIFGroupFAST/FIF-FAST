package co.id.fifgroup.workstructure.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class GradeRateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long branchId;
	private String branchCode;
	private String branchName;
	private BigDecimal minSalary;
	private BigDecimal maxSalary;

	public GradeRateDTO() { }
	
	public GradeRateDTO(Long branchId, BigDecimal minSalary, BigDecimal maxSalary) {
		this.branchId = branchId;
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
	}
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	@Override
	public String toString() {
		return "GradeRateDto [branchId=" + branchId + ", minSalary="
				+ minSalary + ", maxSalary=" + maxSalary + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchId == null) ? 0 : branchId.hashCode());
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
		GradeRateDTO other = (GradeRateDTO) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		return true;
	}

}

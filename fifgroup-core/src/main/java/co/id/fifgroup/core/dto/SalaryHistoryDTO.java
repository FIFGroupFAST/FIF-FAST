package co.id.fifgroup.core.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SalaryHistoryDTO {

	private Long personId;
	private Long companyId;
	private Date effectiveDateFrom;
	private Date effectiveDateTo;
	private BigDecimal basicSalary;
	private Double incrementPercentage;
	private Double percentagePenetration;
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Date getEffectiveDateFrom() {
		return effectiveDateFrom;
	}
	public void setEffectiveDateFrom(Date effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}
	public Date getEffectiveDateTo() {
		return effectiveDateTo;
	}
	public void setEffectiveDateTo(Date effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}
	public Double getIncrementPercentage() {
		return incrementPercentage;
	}
	public void setIncrementPercentage(Double incrementPercentage) {
		this.incrementPercentage = incrementPercentage;
	}
	public Double getPercentagePenetration() {
		return percentagePenetration;
	}
	public void setPercentagePenetration(Double percentagePenetration) {
		this.percentagePenetration = percentagePenetration;
	}
	
	
	
	
}

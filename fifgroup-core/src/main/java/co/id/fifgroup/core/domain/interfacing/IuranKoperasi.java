package co.id.fifgroup.core.domain.interfacing;

import java.math.BigDecimal;

public class IuranKoperasi {

	private Long personId;
	private Long paycodeId;
	private String paycode;
	private BigDecimal amount;
	private String employeeNumber;
	private String savProdTypeId;
	private String userName;
	private Long moduleId;
	private Integer savSequence;
	
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}	
	public Long getPaycodeId() {
		return paycodeId;
	}
	public void setPaycodeId(Long paycodeId) {
		this.paycodeId = paycodeId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getSavProdTypeId() {
		return savProdTypeId;
	}
	public void setSavProdTypeId(String savProdTypeId) {
		this.savProdTypeId = savProdTypeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getSavSequence() {
		return savSequence;
	}
	public void setSavSequence(Integer savSequence) {
		this.savSequence = savSequence;
	}
	
	
	
}

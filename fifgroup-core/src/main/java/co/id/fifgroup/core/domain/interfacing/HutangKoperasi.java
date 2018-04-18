package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HutangKoperasi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long personId;
	private String npk;
	private String instlNo;
	private String contractNumber;
	private String invNo;
	private BigDecimal invAmount;
	private BigDecimal rspAmount;
	private String rspSts;
	private String rspRemark;
	private Date processDate;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Long paycodeId;
	private String paycode;
	private Long moduleId;
	private boolean closing;
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getNpk() {
		return npk;
	}
	public void setNpk(String npk) {
		this.npk = npk;
	}
	public String getInstlNo() {
		return instlNo;
	}
	public void setInstlNo(String instlNo) {
		this.instlNo = instlNo;
	}
	public String getInvNo() {
		return invNo;
	}
	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}
	public BigDecimal getInvAmount() {
		return invAmount;
	}
	public void setInvAmount(BigDecimal invAmount) {
		this.invAmount = invAmount;
	}
	public BigDecimal getRspAmount() {
		return rspAmount;
	}
	public void setRspAmount(BigDecimal rspAmount) {
		this.rspAmount = rspAmount;
	}
	public String getRspSts() {
		return rspSts;
	}
	public void setRspSts(String rspSts) {
		this.rspSts = rspSts;
	}
	public String getRspRemark() {
		return rspRemark;
	}
	public void setRspRemark(String rspRemark) {
		this.rspRemark = rspRemark;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public Long getPaycodeId() {
		return paycodeId;
	}
	public void setPaycodeId(Long paycodeId) {
		this.paycodeId = paycodeId;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public boolean isClosing() {
		return closing;
	}
	public void setClosing(boolean closing) {
		this.closing = closing;
	}
	
	

}

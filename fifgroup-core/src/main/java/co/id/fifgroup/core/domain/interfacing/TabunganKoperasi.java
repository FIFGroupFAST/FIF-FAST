package co.id.fifgroup.core.domain.interfacing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TabunganKoperasi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//add by lisa,1-sep-2015,tiket - 14102810453648//
	private Long personId;
	private String npk;
	private Long moduleId;
	private Long paycodeId;
	private BigDecimal amount;
	private String tabContractNo;
	private String tabInstNo;
	private Date processDate;
	private String createdBy;
	private Date creationDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String tabStatus;
	private String tabRemark;
	private boolean closing;
	private String tabPaidBy;
	private Date tabPaidDate;
	
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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
	public String getTabContractNo() {
		return tabContractNo;
	}
	public void setTabContractNo(String tabContractNo) {
		this.tabContractNo = tabContractNo;
	}
	public String getTabInstNo() {
		return tabInstNo;
	}
	public void setTabInstNo(String tabInstNo) {
		this.tabInstNo = tabInstNo;
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
	public String getTabStatus() {
		return tabStatus;
	}
	public void setTabStatus(String tabStatus) {
		this.tabStatus = tabStatus;
	}
	public String getTabRemark() {
		return tabRemark;
	}
	public void setTabRemark(String tabRemark) {
		this.tabRemark = tabRemark;
	}
	public boolean isClosing() {
		return closing;
	}
	public void setClosing(boolean closing) {
		this.closing = closing;
	}
	public String getTabPaidBy() {
		return tabPaidBy;
	}
	public void setTabPaidBy(String tabPaidBy) {
		this.tabPaidBy = tabPaidBy;
	}
	public Date getTabPaidDate() {
		return tabPaidDate;
	}
	public void setTabPaidDate(Date tabPaidDate) {
		this.tabPaidDate = tabPaidDate;
	}
	public String getNpk() {
		return npk;
	}
	public void setNpk(String npk) {
		this.npk = npk;
	}
	
}

package co.id.fifgroup.core.dto;

import java.io.Serializable;
import java.util.Date;

public class PaymentCyclePaycodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1299410626182385712L;
    private Long paymentCyclePaycodeId;
    private Long versionId;
    private Long paycodeId;
    private Long createdBy;
    private Date creationDate;
    private Long lastUpdatedBy;
    private Date lastUpdateDate;
	private String paycode;
	private String description;
	
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPaymentCyclePaycodeId() {
		return paymentCyclePaycodeId;
	}
	public void setPaymentCyclePaycodeId(Long paymentCyclePaycodeId) {
		this.paymentCyclePaycodeId = paymentCyclePaycodeId;
	}
	public Long getVersionId() {
		return versionId;
	}
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	public Long getPaycodeId() {
		return paycodeId;
	}
	public void setPaycodeId(Long paycodeId) {
		this.paycodeId = paycodeId;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
}

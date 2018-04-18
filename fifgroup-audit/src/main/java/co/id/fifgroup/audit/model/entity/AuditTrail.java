package co.id.fifgroup.audit.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class AuditTrail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7397329015182996445L;
	private long sequenceNumber;
	private UUID auditId;
	private int controlType;
	private int actionType;
	private int moduleId;
	private Date auditTimestamp;
	private UUID sessionId;
	private String remarks;
	private String attributeCodeLabel;
	private String attributeCodeValue;
	private long refSequenceNumber;
	private String transactionType;
	private Long userId;
	private Long trxId;

	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public void setAuditId(UUID auditId) {
		this.auditId = auditId;
	}

	public UUID getAuditId() {
		return auditId;
	}

	public int getControlType() {
		return controlType;
	}

	public void setControlType(int controlType) {
		this.controlType = controlType;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public Date getAuditTimestamp() {
		return auditTimestamp;
	}

	public void setAuditTimestamp(Date auditTimestamp) {
		this.auditTimestamp = auditTimestamp;
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setAttributeCodeLabel(String attributeCodeName) {
		this.attributeCodeLabel = attributeCodeName;
	}

	public String getAttributeCodeLabel() {
		return attributeCodeLabel;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setAttributeCodeValue(String attributeCodeValue) {
		this.attributeCodeValue = attributeCodeValue;
	}

	public String getAttributeCodeValue() {
		return attributeCodeValue;
	}

	public void setRefSequenceNumber(long refSequenceNumber) {
		this.refSequenceNumber = refSequenceNumber;
	}

	public long getRefSequenceNumber() {
		return refSequenceNumber;
	}
	
	

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTrxId() {
		return trxId;
	}

	public void setTrxId(Long trxId) {
		this.trxId = trxId;
	}

	@Override
	public String toString() {
		return "AuditTrail [actionType=" + actionType + ", auditId=" + auditId
				+ ", auditTimestamp=" + auditTimestamp + ", controlType="
				+ controlType + ", moduleId=" + moduleId + ", remarks="
				+ remarks + ", sequenceNumber=" + sequenceNumber
				+ ", sessionId=" + sessionId + "]";
	}
}

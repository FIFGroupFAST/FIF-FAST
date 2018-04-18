package co.id.fifgroup.avm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class AVMApprovalProcessData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int companyId;
	private int sequenceNumber;
	private UUID approverId;
	private int trxType;
	private int actionType;
	private Date avmReceivedTimestamp;
	private Date avmActionTimestamp;	
	private String subject;
	private int trxStatus;
	private String remarks;

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public UUID getApproverId() {
		return approverId;
	}

	public void setApproverId(UUID approverId) {
		this.approverId = approverId;
	}

	public int getTrxType() {
		return trxType;
	}

	public void setTrxType(int trxType) {
		this.trxType = trxType;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public Date getAvmReceivedTimestamp() {
		return avmReceivedTimestamp;
	}

	public void setAvmReceivedTimestamp(Date avmReceivedTimestamp) {
		this.avmReceivedTimestamp = avmReceivedTimestamp;
	}

	public Date getAvmActionTimestamp() {
		return avmActionTimestamp;
	}

	public void setAvmActionTimestamp(Date avmActionTimestamp) {
		this.avmActionTimestamp = avmActionTimestamp;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTrxStatus() {
		return trxStatus;
	}

	public void setTrxStatus(int trxStatus) {
		this.trxStatus = trxStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "AVMApprovalProcessData [companyId=" + companyId
				+ ", sequenceNumber=" + sequenceNumber + ", approverId="
				+ approverId + ", trxType=" + trxType + ", actionType="
				+ actionType + ", avmReceivedTimestamp=" + avmReceivedTimestamp
				+ ", avmActionTimestamp=" + avmActionTimestamp + ", subject="
				+ subject + ", trxStatus=" + trxStatus + ", remarks=" + remarks
				+ "]";
	}	
}

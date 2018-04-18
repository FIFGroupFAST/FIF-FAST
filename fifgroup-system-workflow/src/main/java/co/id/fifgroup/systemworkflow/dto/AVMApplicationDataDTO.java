package co.id.fifgroup.systemworkflow.dto;

import java.util.Date;

import co.id.fifgroup.avm.domain.AVMApplicationData;

public class AVMApplicationDataDTO extends AVMApplicationData {

	private static final long serialVersionUID = -4238426001308375561L;

	private Date approvedTime;
	private String lastRemarks;
	private String name;
	// Rachmad Agus D. 20140425
	private boolean processed;
	private String transactionName;

	public Date getApprovedTime() {
		return approvedTime;
	}

	public String getLastRemarks() {
		return lastRemarks;
	}

	public String getName() {
		return name;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
	}

	public void setLastRemarks(String lastRemarks) {
		this.lastRemarks = lastRemarks;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

}

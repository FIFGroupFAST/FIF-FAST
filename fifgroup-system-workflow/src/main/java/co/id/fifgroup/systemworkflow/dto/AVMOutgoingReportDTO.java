package co.id.fifgroup.systemworkflow.dto;

import co.id.fifgroup.avm.domain.AVMOutgoingReport;

public class AVMOutgoingReportDTO extends AVMOutgoingReport {

	private static final long serialVersionUID = 7039976100329045714L;
	
	private String transactionName;

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	
}

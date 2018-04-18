package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.UUID;

public class SupervisorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long sequenceNumber;
	private UUID approverId;
	private Long branchId;
	private Long organizationId;
	private String approverName;
	private String approverType;
	private int sequence;
	private UUID vacationApproverId;

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public UUID getApproverId() {
		return approverId;
	}

	public void setApproverId(UUID approverId) {
		this.approverId = approverId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public UUID getVacationApproverId() {
		return vacationApproverId;
	}

	public void setVacationApproverId(UUID vacationApproverId) {
		this.vacationApproverId = vacationApproverId;
	}
}

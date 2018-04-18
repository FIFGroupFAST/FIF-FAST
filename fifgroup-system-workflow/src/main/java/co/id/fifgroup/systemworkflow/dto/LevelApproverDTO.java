package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;

public class LevelApproverDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AVMLevel level;
	private List<AVMApprover> approvers;
	private boolean validateRule;
	private boolean validateTabular;

	public AVMLevel getLevel() {
		return level;
	}

	public void setLevel(AVMLevel level) {
		this.level = level;
	}

	public List<AVMApprover> getApprovers() {
		return approvers;
	}

	public void setApprovers(List<AVMApprover> approvers) {
		this.approvers = approvers;
	}

	public boolean isValidateRule() {
		return validateRule;
	}

	public void setValidateRule(boolean validateRule) {
		this.validateRule = validateRule;
	}

	public boolean isValidateTabular() {
		return validateTabular;
	}

	public void setValidateTabular(boolean validateTabular) {
		this.validateTabular = validateTabular;
	}
}

package co.id.fifgroup.systemworkflow.dto;

import java.io.Serializable;
import java.util.List;

import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMApprover;
import co.id.fifgroup.avm.domain.AVMLevel;
import co.id.fifgroup.avm.domain.AVMVersions;

public class ApprovalModelDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AVM avm;
	private AVMVersions avmVersions;
	private List<AVMLevel> listLevel;
	private List<AVMApprover> listApprovers;

	public AVM getAvm() {
		return avm;
	}

	public void setAvm(AVM avm) {
		this.avm = avm;
	}

	public AVMVersions getAvmVersions() {
		return avmVersions;
	}

	public void setAvmVersions(AVMVersions avmVersions) {
		this.avmVersions = avmVersions;
	}

	public List<AVMLevel> getListLevel() {
		return listLevel;
	}

	public void setListLevel(List<AVMLevel> listLevel) {
		this.listLevel = listLevel;
	}

	public List<AVMApprover> getListApprovers() {
		return listApprovers;
	}

	public void setListApprovers(List<AVMApprover> listApprovers) {
		this.listApprovers = listApprovers;
	}

}

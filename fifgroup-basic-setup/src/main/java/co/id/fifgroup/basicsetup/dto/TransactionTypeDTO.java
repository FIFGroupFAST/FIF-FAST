package co.id.fifgroup.basicsetup.dto;

import co.id.fifgroup.basicsetup.domain.Module;
import co.id.fifgroup.basicsetup.domain.TransactionType;

public class TransactionTypeDTO extends TransactionType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7225543265081679108L;
	private Module module;
	
	public Module getModule() {
		return module;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
	
}

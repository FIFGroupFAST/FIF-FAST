package co.id.fifgroup.core.security;

import java.io.Serializable;

public class FunctionPermission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long functionId;
	private boolean createable;
	private boolean editable;
	private boolean deletable;
	
	
	
	public FunctionPermission(boolean createable, boolean editable,
			boolean deletable) {
		super();
		this.createable = createable;
		this.editable = editable;
		this.deletable = deletable;
	}
	
	
	public FunctionPermission() {
		super();
	}


	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public boolean isCreateable() {
		return createable;
	}
	public void setCreateable(boolean createable) {
		this.createable = createable;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isDeletable() {
		return deletable;
	}
	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}


	@Override
	public String toString() {
		return "FunctionPermission [functionId=" + functionId + ", createable="
				+ createable + ", editable=" + editable + ", deletable="
				+ deletable + "]";
	}
	
	
	
}

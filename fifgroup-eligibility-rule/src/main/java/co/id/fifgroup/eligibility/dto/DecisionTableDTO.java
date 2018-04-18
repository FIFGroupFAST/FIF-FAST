package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecisionTableDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private DecisionTableModelDTO model;
	
	private List<DecisionTableRowDTO> rows = new ArrayList<>();
	
	private String drl;
	
	private String modulePrefix;
	
	public DecisionTableDTO() { }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DecisionTableModelDTO getModel() {
		return model;
	}

	public void setModel(DecisionTableModelDTO model) {
		this.model = model;
	}

	public List<DecisionTableRowDTO> getRows() {
		return rows;
	}

	public void setRows(List<DecisionTableRowDTO> rows) {
		this.rows = rows;
	}
	
	public String getDrl() {
		return drl;
	}

	public void setDrl(String drl) {
		this.drl = drl;
	}

	public String getModulePrefix() {
		return modulePrefix;
	}

	public void setModulePrefix(String modulePrefix) {
		this.modulePrefix = modulePrefix;
	}

	@Override
	public String toString() {
		return "DecisionTableDTO [model=" + model + ", rows=" + rows + "]";
	}
	
	
	
	
	
}

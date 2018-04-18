package co.id.fifgroup.eligibility.dto;

import java.util.ArrayList;
import java.util.List;

import co.id.fifgroup.core.domain.UploadStage;

public class DecisionTableStageDTO extends UploadStage {

	private static final long serialVersionUID = 1L;

	private Long rowId;
	private Long decisionTableId;
	private Integer sequence;
	private String conditions;
	private String results;
	
	private List<String> listConditions = new ArrayList<>();
	private List<String> listResults = new ArrayList<>();
	

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getDecisionTableId() {
		return decisionTableId;
	}

	public void setDecisionTableId(Long decisionTableId) {
		this.decisionTableId = decisionTableId;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<String> getListConditions() {
		return listConditions;
	}

	public void setListConditions(List<String> conditions) {
		this.listConditions = conditions;
	}

	public List<String> getListResults() {
		return listResults;
	}

	public void setListResults(List<String> results) {
		this.listResults = results;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}
	
	
}

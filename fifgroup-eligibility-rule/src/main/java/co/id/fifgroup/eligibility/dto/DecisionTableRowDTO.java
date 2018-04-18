package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.id.fifgroup.core.util.DateUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class DecisionTableRowDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer sequence = 0;
	private String conditions = "";
	private String results = "";
	private DecisionTableModelDTO model;

	public Map<Long, Object> values = new HashMap<>();
	

	public DecisionTableRowDTO() {	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	public DecisionTableModelDTO getModel() {
		return model;
	}

	public void setModel(DecisionTableModelDTO model) {
		this.model = model;
	}

	public List<String> getConditionList() {
		return Lists.newArrayList(Splitter.on('|').trimResults()
				.split(null != conditions ? conditions : ""));
	}
	
	public List<String> getResultList() {
		return Lists.newArrayList(Splitter.on('|').trimResults()
				.split(null != results ? results : ""));
	}

	public void put(Long key, Object value) {
		this.values.put(key, value);
	}

	public void refresh() {
		// Condition columns 
		List<String> conditions = new ArrayList<>();
		for (DecisionTableColumnDTO column : model.getConditions()) {
			Object value = values.get(column.getId());
			if (value instanceof Date)
				conditions.add(null != value ? DateUtil.format((Date) value, "dd-MMM-yyyy")  : "");
			else
				conditions.add(null != value ? String.valueOf(value) : "");
		}
		this.conditions = Joiner.on("|").join(conditions);
		// Result columns 
		List<String> results = new ArrayList<>();
		for (DecisionTableColumnDTO column : model.getResults()) {
			Object value = values.get(column.getId());
			if (value instanceof Date)
				results.add(null != value ? DateUtil.format((Date) value, "dd-MMM-yyyy")  : "");
			else
				results.add(null != value ? String.valueOf(value) : "");
		}
		this.results = Joiner.on("|").join(results);
	}

	@Override
	public String toString() {
		return "DecisionTableRowDTO [conditions=" + conditions + ", results="
				+ results + ", model=" + model + ", values=" + values + "]";
	}

// added by jatis for cam --start
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		DecisionTableRowDTO other = (DecisionTableRowDTO) obj;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		return true;
	}
	
	
// added by jatis for cam --end
}

package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

public class Results implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Result> elements = new ArrayList<>();

	private String module;
	private String type;
	private Integer version;

	public List<Result> getElements() {
		return bySalienceOrdering.immutableSortedCopy(elements);
	}

	public void setElements(List<Result> elements) {
		this.elements = elements;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	private static Ordering<Result> bySalienceOrdering = Ordering.natural().reverse().onResultOf(new Function<Result, Integer>() {

		@Override
		public Integer apply(Result input) {
			if (null != input) 
				return input.getSalience();
			return null;
		}
		
	});
	
	public void add(Result result) {
		elements.add(result);
	}
	
	public boolean isEligible() {
		return elements.size() > 0;
	}
}

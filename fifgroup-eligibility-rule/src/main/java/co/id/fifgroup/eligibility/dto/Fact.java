package co.id.fifgroup.eligibility.dto;

import java.util.Map;

public class Fact {

	private String name;
	
	Map<String, Object> properties;
	
	public Fact() { }
	
	public Fact(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "Fact [name=" + name + ", properties=" + properties + "]";
	}

	
	
	
}


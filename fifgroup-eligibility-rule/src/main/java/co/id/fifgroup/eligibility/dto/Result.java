package co.id.fifgroup.eligibility.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> properties = new HashMap<String, Object>();
	
	private Long id;
	private String name;
	private Integer salience;
	
	public Result() { }
	
	public Result(Integer salience) {
		this.salience = salience;
	}
	
	public Result(String name) {
		this.name = name;
	}
	
	public Result(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSalience() {
		return salience;
	}

	public void setSalience(Integer salience) {
		this.salience = salience;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		if (properties != null) {
			return (T) properties.get(key);
		}
		return null;
	}
	
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "Result [properties=" + properties + ", id=" + id + ", name="
				+ name + ", salience=" + salience + "]";
	}
}

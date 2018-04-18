package co.id.fifgroup.core.ui.lookup;

import java.io.Serializable;

public class TripleBandKeyValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object key;
	private Object value;
	private Object description;
	private Object description2;
	
	public TripleBandKeyValue() {
		
	}
	
	public TripleBandKeyValue(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public TripleBandKeyValue(Object key, Object value, Object description) {
		super();
		this.key = key;
		this.value = value;
		this.description = description;
	}
	
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public Object getDescription() {
		return description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}
	
	public Object getDescription2() {
		return description2;
	}

	public void setDescription2(Object description2) {
		this.description2 = description2;
	}

	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + ", description="
				+ description + "]";
	}
}

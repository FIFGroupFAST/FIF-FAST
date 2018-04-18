package co.id.fifgroup.core.ui.lookup;

import java.io.Serializable;

public class QuadrupleBandKeyValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object key;
	private Object value;
	private Object description;
	private Object description2;
	private Object description3;
	
	public QuadrupleBandKeyValue() {
		
	}
	
	public QuadrupleBandKeyValue(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public QuadrupleBandKeyValue(Object key, Object value, Object description) {
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

	public Object getDescription3() {
		return description3;
	}

	public void setDescription3(Object description3) {
		this.description3 = description3;
	}
}

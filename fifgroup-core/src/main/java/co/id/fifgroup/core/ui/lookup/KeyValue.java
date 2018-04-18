package co.id.fifgroup.core.ui.lookup;

import java.io.Serializable;

public class KeyValue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object key;
	private Object value;
	private Object description;
	
	public KeyValue() {
		
	}
	
	public KeyValue(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public KeyValue(Object key, Object value, Object description) {
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

	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + ", description="
				+ description + "]";
	}
}

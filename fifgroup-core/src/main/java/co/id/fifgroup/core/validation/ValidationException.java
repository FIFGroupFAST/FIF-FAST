package co.id.fifgroup.core.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Multimap<String, String> constraintViolations = ArrayListMultimap.create();
	
	public ValidationException() {
		super();
	}

	public ValidationException(ValidationException... vex) {
		for (ValidationException ex : vex)
			this.constraintViolations.putAll(ex.constraintViolations);
	}
	
	public ValidationException(String message){
		super(message);
	}


	public ValidationException(Map<String, String> constraintViolations) {
		for (String key : constraintViolations.keySet()) {
			this.constraintViolations.put(key, constraintViolations.get(key));
		}
	}

	public Map<String, String> getConstraintViolations() {
		Map<String, String> map = new HashMap<>();
		for (String key : constraintViolations.keySet()) {
			Collection<String> result = constraintViolations.get(key);
			map.put(key, (result != null && result.size() > 0) ? Joiner.on(", ").useForNull("").join(result) : "");
		}
		return map;
	}
	
	public Multimap<String, String> getViolations() {
		return constraintViolations;
	}

	public void setConstraintViolations(Map<String, String> constraintViolations) {
		for (String key : constraintViolations.keySet()) {
			this.constraintViolations.put(key, constraintViolations.get(key));
		}
	}
	
	public String getMessage(String key) {
		return Joiner.on(", ").useForNull("").join(constraintViolations.get(key));
	}

	
	public void put(String key, String message) {
		constraintViolations.put(key, message);
	}
	
	public boolean hasErrors() {
		return !constraintViolations.isEmpty();
	}
	
	@Override
	public String getMessage() {
		return constraintViolations.toString();
	}

	public String getAllMessages() {
		Collection<String> result = constraintViolations.values();
		return (result != null && result.size() > 0) ? Joiner.on(", ").useForNull("").join(result) : "";
	}
	


}

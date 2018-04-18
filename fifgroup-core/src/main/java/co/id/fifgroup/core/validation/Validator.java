package co.id.fifgroup.core.validation;

public interface Validator<T> {

	void validate(T subject) throws ValidationException;
	
}

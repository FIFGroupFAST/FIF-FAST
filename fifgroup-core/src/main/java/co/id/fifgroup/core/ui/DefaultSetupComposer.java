package co.id.fifgroup.core.ui;

import co.id.fifgroup.core.validation.ValidationException;

public interface DefaultSetupComposer<T> {

	void populate(T subject);
	
	void render(T subject);
	
	void showErrorMessages(ValidationException ex);
	
	void clearErrorMessages();
}

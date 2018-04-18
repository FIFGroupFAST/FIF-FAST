package co.id.fifgroup.core.ui.tabularentry;

import java.io.Serializable;
import java.util.List;



public interface TabularValidationRule<T> extends Serializable {
	
	boolean validate(T data, List<String> errorRow);
	
}

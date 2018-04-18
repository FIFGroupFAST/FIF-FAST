package co.id.fifgroup.core.ui.tabularentry;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;


public class CheckboxListcell<T> extends TabularListcell<T, Boolean, Checkbox> {

	public CheckboxListcell(T data, Boolean value, String fieldName) {
		super(data, value, fieldName, Events.ON_CHECK);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected Checkbox createComponent(Boolean value) {
		Checkbox component = new Checkbox();
		component.setChecked(null == value ? false : value);
		return component;
	}

	@Override
	protected Boolean getComponentValue() {
		return component.isChecked();
	}

	@Override
	protected Checkbox createComponent(Boolean value, int maxLength,
			String width) {
		return createComponent(value);
	}

}

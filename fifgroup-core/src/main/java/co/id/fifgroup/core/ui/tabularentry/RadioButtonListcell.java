package co.id.fifgroup.core.ui.tabularentry;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Radio;

public class RadioButtonListcell<T> extends TabularListcell<T, Boolean, Radio> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1396739890681090116L;

	public RadioButtonListcell(T data, Boolean value, String fieldName) {
		super(data, value, fieldName, Events.ON_CHECK);
	}

	@Override
	protected Radio createComponent(Boolean value) {
		Radio component = new Radio();
		component.setChecked(null == value ? false : value);
		
		return component;
	}

	@Override
	protected Boolean getComponentValue() {
		return component.isChecked();
	}

	@Override
	protected Radio createComponent(Boolean value, int maxLength,
			String width) {
		return createComponent(value);
	}
	
}

package co.id.fifgroup.core.ui.tabularentry;

import org.zkoss.zul.Longbox;

public class LongboxListcell<T> extends TabularListcell<T, Long, Longbox> {

	private static final long serialVersionUID = 1L;
	
	public LongboxListcell(T data, Long value, String fieldName, boolean readonly) {
		super(data, value, fieldName, readonly);
	}
	
	public LongboxListcell(T data, Long value, String fieldName) {
		super(data, value, fieldName);
	}

	@Override
	protected Longbox createComponent(Long value) {
		if(value == null) {
			return new Longbox();
		}
		return new Longbox(value);
	}

	@Override
	protected Long getComponentValue() {
		return this.component.getValue();
	}

	@Override
	protected Longbox createComponent(Long value, int maxLength, String width) {
		Longbox longbox = new Longbox();
		if(value == null) {
			longbox.setValue(value);
		}
		if(maxLength != 0)
			longbox.setMaxlength(maxLength);
		if(width != null)
			longbox.setWidth(width);
		return longbox;
	}
	
}

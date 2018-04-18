package co.id.fifgroup.core.ui.tabularentry;

import org.zkoss.zul.Label;

public class LabelListcell<T> extends TabularListcell<T, String, Label> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2754742349838287591L;

	public LabelListcell(T data, String value, String fieldName, String event) {
		super(data, value, fieldName, event, false, 25, "", null);
	}
		
	public LabelListcell(T data, String value, String fieldName) {
		super(data, value, fieldName);
	}

	@Override
	protected Label createComponent(String value) {
		return new Label(value);
	}

	@Override
	protected String getComponentValue() {
		return this.component.getValue().equals("") ? null : this.component.getValue();
	}

	@Override
	protected Label createComponent(String value, int maxLength, String width) {
		Label label = new Label(value);
		if(maxLength != 0)
			label.setMaxlength(maxLength);
		if(width != null)
			label.setWidth(width);
		return label;
	}

}

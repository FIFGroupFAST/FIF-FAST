package co.id.fifgroup.core.ui.tabularentry;

import java.math.BigDecimal;

import org.zkoss.zul.Decimalbox;

public class DecimalboxListcell<T> extends TabularListcell<T, BigDecimal, Decimalbox> {

	private static final long serialVersionUID = 1L;

	public DecimalboxListcell(T data, BigDecimal value, String fieldName, boolean readonly) {
		super(data, value, fieldName, readonly);
	}

	public DecimalboxListcell(T data, BigDecimal value, String fieldName) {
		super(data, value, fieldName);
	}

	@Override
	protected Decimalbox createComponent(BigDecimal value) {
		return new Decimalbox(value);
	}

	@Override
	protected BigDecimal getComponentValue() {
		return this.component.getValue();
	}

	@Override
	protected Decimalbox createComponent(BigDecimal value, int maxLength, String width) {
		Decimalbox decimalbox = new Decimalbox(value);
		if(maxLength != 0)
			decimalbox.setMaxlength(maxLength);
		if(width != null)
			decimalbox.setWidth(width);
		return decimalbox;
	}
	
}

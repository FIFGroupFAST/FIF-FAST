package co.id.fifgroup.core.ui.tabularentry;

import java.util.Date;

import org.zkoss.zul.Datebox;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.FormatDateUI;

public class DateboxListcell<T> extends TabularListcell<T, Date, Datebox> {

	private static final long serialVersionUID = 1L;
	
	public DateboxListcell(T data, Date value, String fieldName, boolean readOnly) {
		super(data, value, fieldName, readOnly);
	}
	
	public DateboxListcell(T data, Date value, String fieldName) {
		super(data, value, fieldName);
	}
	
	public DateboxListcell(T data, Date value, String fieldName, String event) {
		super(data, value, fieldName, event);
	}

	@Override
	protected Datebox createComponent(Date value) {
		Datebox datebox = new Datebox(value);
		datebox.setFormat(FormatDateUI.getDateFormat());
		return datebox;
	}

	@Override
	protected Date getComponentValue() {
		if(this.component.getValue() != null)
		return DateUtil.truncate(this.component.getValue());
		return this.component.getValue();
	}

	@Override
	protected Datebox createComponent(Date value, int maxLength, String width) {
		Datebox datebox = new Datebox(value);
		datebox.setFormat(FormatDateUI.getDateFormat());
		if(maxLength != 0)
			datebox.setMaxlength(maxLength);
		if(width != null)
			datebox.setWidth(width);
		return datebox;
	}
	
}

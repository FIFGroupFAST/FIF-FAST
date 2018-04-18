package co.id.fifgroup.core.ui;

import java.util.Calendar;

import org.zkoss.zul.Datebox;

import co.id.fifgroup.core.util.DateUtil;

public class DateBoxFrom extends Datebox{

	private static final long serialVersionUID = 1L;

	public DateBoxFrom() {
		if(this.getValue() == null) {
			Calendar tomorrow = Calendar.getInstance();
			tomorrow.add(Calendar.DATE, 1);
			this.setValue(tomorrow.getTime());
			this.setFormat(DateUtil.DEFAULT_FORMAT);
		}
		this.setFormat(DateUtil.DEFAULT_FORMAT);
		this.setWidth("130px");
	}

}

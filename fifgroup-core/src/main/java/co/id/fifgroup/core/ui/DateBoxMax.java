package co.id.fifgroup.core.ui;

import java.util.Date;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Datebox;

import co.id.fifgroup.core.util.DateUtil;

public class DateBoxMax extends Datebox {

	private static final long serialVersionUID = 1L;
	
	public DateBoxMax() {
		this.setValue(DateUtil.MAX_DATE);
		this.setFormat(DateUtil.DEFAULT_FORMAT);
		this.setWidth("130px");
	}
	
	@Override
	public Date getValue() throws WrongValueException {
		Date value =  super.getValue();
		if(value == null)
			value = DateUtil.MAX_DATE;
		
		return value;
	}
}

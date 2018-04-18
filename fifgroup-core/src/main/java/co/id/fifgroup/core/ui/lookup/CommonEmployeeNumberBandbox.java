package co.id.fifgroup.core.ui.lookup;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

public class CommonEmployeeNumberBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3824065910127288654L;
	private Logger log = LoggerFactory.getLogger(CommonEmployeeNumberBandbox.class);
	private KeyValue value;
	
	public CommonEmployeeNumberBandbox() {
		setReadonly(true);
		addEventListener(Events.ON_OPEN, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1094219907580335036L;

			@Override
			public void onEvent(Event event) throws Exception {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("source", CommonEmployeeNumberBandbox.this);
				Window win = (Window) Executions.createComponents("~./common/employee_number_list_of_value.zul", null, params);
				win.doModal();
			}
		});
	}

	@Override
	public void setRawValue(Object value) {
		if(value != null && (value instanceof KeyValue)) {
			KeyValue keyValue = (KeyValue) value;
			if(keyValue.getDescription() != null && keyValue.getValue() != null) {
				super.setValue(keyValue.getValue().toString() + " - " + keyValue.getDescription().toString());
			} else {
				super.setValue(null);
			}
		} else {
			super.setValue(null);
		}
		this.value = (KeyValue) value;
	}
	
	public KeyValue getKeyValue() {
		return this.value;
	}
	
	public void setValue(Object value){
		if(value != null && (value instanceof KeyValue)) {
			KeyValue keyValue = (KeyValue) value;
			if(keyValue.getDescription() != null && keyValue.getValue() != null) {
				super.setValue(keyValue.getValue().toString());
			} else {
				super.setValue(null);
			}
		} else {
			super.setValue(null);
		}
		this.value = (KeyValue) value;
	}
	
}

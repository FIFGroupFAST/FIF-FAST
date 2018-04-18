package co.id.fifgroup.core.ui.tabularentry;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;

public class BandboxListcell<T,V> extends Listcell {

	private static final long serialVersionUID = 1L;
	private T object;
	private String fieldName;
	private SerializableSearchDelegate<?> searchDeleagte;
	private boolean readOnly = false;
	private Bandbox bandbox;
	private BandboxListcell<T, V> bandBoxListcell = this;
	
	private static final Logger logger = LoggerFactory.getLogger(BandboxListcell.class);
	
	public BandboxListcell() {}
	
	public BandboxListcell(String value, T object, String fieldName, SerializableSearchDelegate<?> searchDelegate) {
		this.object = object;
		this.fieldName = fieldName;
		this.searchDeleagte = searchDelegate;
		Bandbox bandbox = buildBandbox();
		bandbox.setReadonly(readOnly);
		bandbox.setValue(value);
		this.bandbox = bandbox;
		appendChild(bandbox);
	}
	
	public BandboxListcell(String value, T object, String fieldName, SerializableSearchDelegate<?> searchDelegate, boolean readOnly) {
		this.object = object;
		this.fieldName = fieldName;
		this.searchDeleagte = searchDelegate;
		Bandbox bandbox = buildBandbox();
		bandbox.setReadonly(readOnly);
		bandbox.setValue(value);
		this.bandbox = bandbox;
		appendChild(bandbox);
	}
	
	public BandboxListcell(String value, boolean readOnly) {
		Bandbox bandbox = buildBandbox();
		bandbox.setDisabled(readOnly);
		bandbox.setValue(value);
		this.bandbox = bandbox;
		appendChild(bandbox);
	}
	
	private Bandbox buildBandbox() {
		Bandbox bandbox = new Bandbox();
		bandbox.addEventListener(Events.ON_OPEN, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("searchDelegate", searchDeleagte);
				param.put("bandBoxListcell", bandBoxListcell);
				Window win = (Window) Executions.createComponents("~./common/generic_list_of_value.zul", null, param);
				win.doModal();
			}
		});
		return bandbox;
	}
	
	@SuppressWarnings("unchecked")
	public void setRawValue(Object value) {
		KeyValue keyValue = (KeyValue) value;
		changeValue((V)keyValue.getKey());
		
	}
	
	private void changeValue(V value) {
		PropertyDescriptor p = null;
		try {
			p = new PropertyDescriptor(fieldName, object.getClass());
		} catch (IntrospectionException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			p.getWriteMethod().invoke(object, value);
		} catch (WrongValueException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void setValue(String value) {
		bandbox.setValue(value);
	}
	
	public Bandbox getBandbox(){
		return bandbox;
	}

}

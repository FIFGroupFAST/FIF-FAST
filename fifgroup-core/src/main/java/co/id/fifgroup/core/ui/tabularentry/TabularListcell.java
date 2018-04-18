package co.id.fifgroup.core.ui.tabularentry;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

public abstract class TabularListcell<T, V, C extends XulElement> extends Listcell {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(TabularListcell.class);
	
	protected T data;
	protected String fieldName;
	protected C component;
	protected V initialValue;
	
	public TabularListcell(T data, V value, String fieldName) {
		this(data, value, fieldName, false);
	}
	
	public TabularListcell(T data, V value, String fieldName, String event, boolean readonly,
			int maxLength, String width, V initialValue){
		this.data = data;
		this.fieldName = fieldName;
		this.initialValue = initialValue;
		this.component = createComponent(value, maxLength, width);
		
		try {
			setDataValue();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		this.component.addEventListener(event, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					setDataValue();
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
					throw ex;
				}
			}
		
		});
		if (component instanceof InputElement) {
			((InputElement) component).setReadonly(readonly);
			((InputElement) component).setDisabled(readonly);
		}
		this.component.setParent(this);
	}
	
	public TabularListcell(T data, V value, String fieldName, String event) {
		this(data, value, fieldName, event, false, 25, "", null);
	}
	
	public TabularListcell(T data, V value, String fieldName, boolean readonly) {
		this(data, value, fieldName, Events.ON_BLUR, readonly, 25, "", null);
	}
	
	public TabularListcell(T data, V value, String fieldName, boolean readonly, int maxLength, String width) {
		this(data, value, fieldName, Events.ON_BLUR, readonly, maxLength, width, null);
	}
	
	protected void setDataValue() throws WrongValueException, IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		PropertyDescriptor p = new PropertyDescriptor(fieldName, data.getClass());
		p.getWriteMethod().invoke(data, getComponentValue());
	}
	
	public C getComponent() {
		return this.component;
	}
	
	protected abstract C createComponent(V value) ;
	protected abstract V getComponentValue();
	protected abstract C createComponent(V value, int maxLength, String width) ;
	
}

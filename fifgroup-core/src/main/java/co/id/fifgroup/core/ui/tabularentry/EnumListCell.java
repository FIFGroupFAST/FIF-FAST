package co.id.fifgroup.core.ui.tabularentry;

import org.zkoss.zk.ui.event.Events;

import co.id.fifgroup.core.ui.EnumListbox;

public class EnumListCell<T, E extends Enum<E>> extends TabularListcell<T, E, EnumListbox<E>>{

	private static final long serialVersionUID = 1L;
	
	public EnumListCell(T data, E value, String fieldName, E initialValue) {
		super(data, value, fieldName, Events.ON_SELECT, false, 25, "", initialValue);
	}


	@Override
	protected EnumListbox<E> createComponent(E value) {
		EnumListbox<E> comp = new EnumListbox<>();
		if (null != value) {
			comp.setEnum(value);
			comp.setSelectedValue(value);
		} else if (null != initialValue) {
			comp.setEnum(initialValue);
		}
		
		return comp;
	}

	@Override
	protected E getComponentValue() {
		return component.getSelectedValue();
	}

	@Override
	protected EnumListbox<E> createComponent(E value, int maxLength,
			String width) {
		return createComponent(value);
	}

	
}

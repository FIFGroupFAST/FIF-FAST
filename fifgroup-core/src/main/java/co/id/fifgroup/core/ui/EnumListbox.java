package co.id.fifgroup.core.ui;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public class EnumListbox<E extends Enum<E>> extends Listbox
	implements SerializableListItemRenderer<E>{

	private static final long serialVersionUID = 4830628299948917508L;
	
	private ListModelList<E> model;
	
	private EnumFilter<E> filter;
	
	public EnumListbox() {
		super();
	}
	
	public void setFilter(EnumFilter<E> filter) {
		this.filter = filter;
	}
	
	public void setEnum(E enumeration) {
		setMold("select");
		model = new ListModelList<>();
		model.add(null);
		for (E constant : enumeration.getDeclaringClass().getEnumConstants()) {
			if(null == filter || filter.apply(constant))
				model.add(constant);
		}
		setModel(model);
		setItemRenderer(this);
		renderAll();
		setSelectedValue(null);
	}
	
	@Override
	public void render(Listitem item, E data, int index)
			throws Exception {
		item.setValue(data);
		item.setStyle("font-family: tahoma,arial,sans-serif;");
		if (null != data) {
			if (data instanceof DescriptiveEnum)
				item.setLabel(((DescriptiveEnum) data).getDescription());
			else
				item.setLabel(String.valueOf(data));
		} else {
			item.setLabel(Labels.getLabel("common.pleaseSelect"));
		}
		
	}
	
	public E getSelectedValue() {
		if (null == getSelectedItem())
			return null;
		return getSelectedItem().getValue();
	}
	
	public void setSelectedValue(E enumeration) {
		for (Listitem item : getItems()) {
			if (item.getValue() == enumeration) {
				item.setSelected(true);
				return;
			} 
		}
		Events.postEvent("onSelect", this, null); 
		
	}
	
}

package co.id.fifgroup.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;

import co.id.fifgroup.core.constant.DescriptiveEnum;

public class EnumCheckbox<E extends Enum<E>> extends Hlayout{

	private static final long serialVersionUID = 1L;
	
	private EnumFilter<E> filter;
	
	public EnumCheckbox() {
		super();
		setValign("middle");
	}
	
	public void setFilter(EnumFilter<E> filter){
		this.filter = filter;
	}
	
	public void setEnum(E enumeration) {
		Components.removeAllChildren(this);
		for(E constant : enumeration.getDeclaringClass().getEnumConstants()) {
			if (null == filter || filter.apply(constant)) {
				Checkbox check = new Checkbox(constant.name());
				check.setValue(constant);
				if (constant instanceof DescriptiveEnum)
					check.setLabel(((DescriptiveEnum) constant).getDescription());
				this.appendChild(check);
			}
		}
	}
	
	public void setValues(List<E> values) {
		for(Component component : getChildren()) {
			if (component instanceof Checkbox) {
				for (E value : values) {
					if (value.equals(((Checkbox) component).getValue())) {
						((Checkbox) component).setChecked(true);
						break;
					} else 
						((Checkbox) component).setChecked(false);
				}
			}
				
		}
	}
	
	public void setDisabled(boolean disabled) {
		for(Component component : getChildren()) {
			if (component instanceof Checkbox) {
				((Checkbox) component).setDisabled(disabled);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getValues() {
		List<E> values = new ArrayList<>();
		for(Component component : getChildren()) {
			if (component instanceof Checkbox) {
				if (((Checkbox) component).isChecked())
					values.add((E) ((Checkbox) component).getValue());
			}
		}
		return values;
	}
		
	
}

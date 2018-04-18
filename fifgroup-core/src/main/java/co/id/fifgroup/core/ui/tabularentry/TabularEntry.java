package co.id.fifgroup.core.ui.tabularentry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Box;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.impl.InputElement;

public class TabularEntry<T> extends Listbox {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(TabularEntry.class);
	
	private TabularValidationRule<T> validationRule;
	
	private Class<T> dataType;

	private Boolean disabled;
	
	public TabularEntry() { 
		super();
		setCheckmark(true);
		addEventListener(ZulEvents.ON_AFTER_RENDER, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 828847008759610650L;

			@Override
			public void onEvent(Event event) throws Exception {
				if(disabled != null) {
					setDisabledComponent(disabled);
				}
			}
		});
//		addEventListener("onDisabledListbox", new SerializableEventListener<Event>() {
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 828847008759610650L;
//
//			@Override
//			public void onEvent(Event event) throws Exception {
//				if(event.getData() instanceof Boolean) {
//					setDisabledComponent((boolean) event.getData());					
//				}
//			}
//		});
	}
	
	public void setDataType(Class<T> dataType) {
		this.dataType = dataType;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteRow() {
		clearErrorMessage();
		ListModelList<T> model = (ListModelList<T>) getModel();
		model.removeAll(model.getSelection());
	}
		
	@Override
	public void setModel(ListModel<?> model) {
		super.setModel(model);
		if (model instanceof ListModelList<?>) {
			((ListModelList<?>) model).setMultiple(true);
		}
		renderAll();
	}

	@SuppressWarnings("unchecked")
	public void addRow() {
		Class<?> cl;
		if (getModel().getSize() > 0)
			cl = getModel().getElementAt(0).getClass();
		else 
			cl = dataType;
		ListModelList<T> model = (ListModelList<T>) getModel();
		try {
			model.add((T) cl.newInstance());
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getValue() {
		return (List<T>) getModel();
	}
	
	@SuppressWarnings("unchecked")
	public boolean validate() {
		int index = 0;
		boolean valid = true;
		Set<Listitem> items = new HashSet<Listitem>();
		List<List<String>> errorMessages = new ArrayList<List<String>>();
		List<T> selection = new ArrayList<T>();
		clearErrorMessage();
		for (T data : getValue()) {
			if(validationRule != null){
				List<String> errorRow = new ArrayList<String>();
				if(!validationRule.validate(data, errorRow)) {
					items.add(getItemAtIndex(index));	
					selection.add(data);
					errorRow.add(0, "Error at row " + (index+1));
					if(errorRow.size() > 1) {
						errorMessages.add(errorRow);
					}
				}
			}
			index++;
		}
			
		if(items.size() > 0) {
			clearSelection();
			setSelectedItems(items);
			ListModelList<T> model = (ListModelList<T>) getModel();
			model.setSelection(selection);
			showErrorMessage(errorMessages);
			valid = false;
		}		
		return valid;
	}
	
	private void showErrorMessage(List<List<String>> errorMessages) {
		Vlayout vlayout = new Vlayout();
		for(List<String> row : errorMessages) {
			Label label = new Label(StringUtils.join(row, ", "));
			label.setStyle("color:red");
			vlayout.appendChild(label);
		}
		
		clearErrorMessage();
		
		getParent().insertBefore(vlayout, this);	
	}
	
	public void clearErrorMessage() {
		if (this.getPreviousSibling() instanceof Vlayout) {
			getParent().removeChild(this.getPreviousSibling());
		}
	}

	public TabularValidationRule<T> getValidationRule() {
		return validationRule;
	}

	public void setValidationRule(TabularValidationRule<T> validationRule) {
		this.validationRule = validationRule;
	}

	@Override
	public void setDisabled(boolean disabled) {
//		Events.postEvent("onDisabledListbox", this, Boolean.valueOf(disabled));
		this.disabled = disabled;
	}

	@SuppressWarnings("rawtypes")
	private void setDisabledComponent(boolean disabled) {
		for(Listitem listitem : getItems()) {
			listitem.setDisabled(disabled);
			for(Component component : listitem.getChildren()) {
				if(component instanceof Listcell) {
					for(Component inputElement : component.getChildren()) {
						if(!(inputElement instanceof Label)) {
							if(inputElement instanceof Box) {
								for(Component childBox : inputElement.getChildren()) {
									AbstractComponent ac = (AbstractComponent) childBox;
									try {
										Method m = ac.getClass().getMethod("setDisabled", Boolean.TYPE);
										try {
											m.invoke(ac, Boolean.valueOf(disabled));
										} catch (IllegalAccessException
												| IllegalArgumentException
												| InvocationTargetException e) {
										}
									} catch (NoSuchMethodException e) {
									} catch (SecurityException e) {
									}
								}
							} else {
								AbstractComponent ac = (AbstractComponent) inputElement;
								try {
									Method m = ac.getClass().getMethod("setDisabled", Boolean.TYPE);
									try {
										m.invoke(ac, Boolean.valueOf(disabled));
									} catch (IllegalAccessException
											| IllegalArgumentException
											| InvocationTargetException e) {
									}
								} catch (NoSuchMethodException e) {
									logger.error(e.getMessage(), e);
								} catch (SecurityException e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					}
				} else if(component instanceof TabularListcell) {
					TabularListcell tabularListcell = (TabularListcell) component;
					if(tabularListcell.getComponentValue() instanceof InputElement) {
						AbstractComponent ac = (AbstractComponent) tabularListcell.getComponentValue();
						try {
							Method m = ac.getClass().getMethod("setDisabled", Boolean.TYPE);
							try {
								m.invoke(ac, Boolean.valueOf(disabled));
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
							}
						} catch (NoSuchMethodException e) {
							logger.error(e.getMessage(), e);
						} catch (SecurityException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		}
	}
	
}

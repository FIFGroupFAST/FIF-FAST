package co.id.fifgroup.systemworkflow.util;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public class PopupJobRoleBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String searchText;
	private String headerLabel;
	private PopupJobRoleBandbox source = this;
	private Object value;
	
	public PopupJobRoleBandbox() {
		addEventListener(Events.ON_OPEN, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -91355005227901153L;

			@Override
			public void onEvent(Event event) throws Exception {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("title", title);
				params.put("searchText", searchText);
				params.put("headerLabel", headerLabel);
				params.put("source", source);
				Window win = (Window) Executions.createComponents("~./hcms/workflow/job_role_list_of_value.zul", null, params);
				win.doModal();
			}
		});
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getHeaderLabel() {
		return headerLabel;
	}

	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	public PopupJobRoleBandbox getSource() {
		return source;
	}

	public void setSource(PopupJobRoleBandbox source) {
		this.source = source;
	}

	@Override
	public void setRawValue(Object value) {
		KeyValue selectedData = (KeyValue) value;
		if(selectedData != null && selectedData.getDescription() != null) {
			super.setValue(selectedData.getDescription().toString());
		} else {
			super.setValue(null);
		}
		this.value = value;
	}
	
	public KeyValue getKeyValue() {
		return (KeyValue)  this.value;
	}
}

package co.id.fifgroup.core.ui.lookup;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

public class CommonPopupBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7266841144076628484L;

	private String title;
	private String searchText;
	private String headerLabel;
	private CommonPopupBandbox source = this;
	private SerializableSearchDelegate<?> searchDelegate;
	private Object value;
	
	public CommonPopupBandbox() {
		setReadonly(true);
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
				params.put("searchDelegate", searchDelegate);
				Window win = (Window) Executions.createComponents("~./common/generic_list_of_value.zul", null, params);
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

	public SerializableSearchDelegate<?> getSearchDelegate() {
		return searchDelegate;
	}

	public void setSearchDelegate(SerializableSearchDelegate<?> searchDelegate) {
		this.searchDelegate = searchDelegate;
	}

	public String getHeaderLabel() {
		return headerLabel;
	}

	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	public CommonPopupBandbox getSource() {
		return source;
	}

	public void setSource(CommonPopupBandbox source) {
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

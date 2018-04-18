package co.id.fifgroup.ssoa.common;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

public class SSOACommonPopupBandbox extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7266841144076628484L;

	private String title;
	private String searchTextCode;
	private String searchTextName;
	private String headerLabel;
	private String descLabel;
	private SSOACommonPopupBandbox source = this;
	private SerializableSearchDelegateDoubleCriteria<?> searchDelegate;
	private Object value;
	private Boolean otherTemplate;
	
	public SSOACommonPopupBandbox() {
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
				params.put("searchTextCode", searchTextCode);
				params.put("searchTextName", searchTextName);
				params.put("headerLabel", headerLabel);
				params.put("descLabel", descLabel);
				params.put("source", source);
				params.put("searchDelegate", searchDelegate);
				params.put("otherTemplate", otherTemplate);
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/ssoa_generic_list_of_value.zul", null, params);
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

	

	public String getSearchTextCode() {
		return searchTextCode;
	}

	public void setSearchTextCode(String searchTextCode) {
		this.searchTextCode = searchTextCode;
	}

	
	public String getSearchTextName() {
		return searchTextName;
	}

	public void setSearchTextName(String searchTextName) {
		this.searchTextName = searchTextName;
	}

	

	public SerializableSearchDelegateDoubleCriteria<?> getSearchDelegate() {
		return searchDelegate;
	}

	public void setSearchDelegate(SerializableSearchDelegateDoubleCriteria<?> searchDelegate) {
		this.searchDelegate = searchDelegate;
	}

	public String getHeaderLabel() {
		return headerLabel;
	}

	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	public SSOACommonPopupBandbox getSource() {
		return source;
	}

	public void setSource(SSOACommonPopupBandbox source) {
		this.source = source;
	}
	
	

	public String getDescLabel() {
		return descLabel;
	}

	public void setDescLabel(String descLabel) {
		this.descLabel = descLabel;
	}

	@Override
	public void setRawValue(Object value) {
		KeyValue selectedData = (KeyValue) value;
		if(selectedData != null && selectedData.getValue() != null) {
			super.setValue(selectedData.getValue().toString());
		} else {
			super.setValue(null);
		}
		this.value = value;
	}
	
	public KeyValue getKeyValue() {
		return (KeyValue)  this.value;
	}

	public Boolean getOtherTemplate() {
		return otherTemplate;
	}

	public void setOtherTemplate(Boolean otherTemplate) {
		this.otherTemplate = otherTemplate;
	}

	

	
	
	
}

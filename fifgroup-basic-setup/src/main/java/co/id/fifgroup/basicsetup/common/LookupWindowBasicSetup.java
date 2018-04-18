package co.id.fifgroup.basicsetup.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.service.LookupServiceAdapter;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.util.ErrorMessageUtil;

public class LookupWindowBasicSetup extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2140898613271534727L;
	private Logger log = LoggerFactory.getLogger(LookupWindowBasicSetup.class);
	private String title;
	private String searchText;
	private String headerLabel;
	private LookupWindowBasicSetup own = this;
	private SerializableSearchDelegate<KeyValue> searchDelegate;
	private Object value;
	private LookupCriteriaBasicSetup lookupCriteria;
	private LookupServiceAdapter lookupServiceImpl = (LookupServiceAdapter) SpringUtil.getBean("lookupServiceImpl");
	private boolean active = true;
	
	public LookupWindowBasicSetup() {
		setReadonly(true);
		searchDelegate = new SerializableSearchDelegate<KeyValue>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 3265001674058205637L;

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey(data.getKey());
				keyValue.setValue(data.getValue());
				keyValue.setDescription(data.getDescription());
			}
			
			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				return lookupServiceImpl.getLookupValues(searchCriteria, 
														lookupCriteria.getBusinessGroupId(),
														lookupCriteria.getLookupName(), 
														lookupCriteria.getScope(), 
														lookupCriteria.getParentDetailCode(), 
														isActive(), limit, offset);
			}
			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return lookupServiceImpl.getCountLookupValues(searchCriteria, 
															lookupCriteria.getBusinessGroupId(),
															lookupCriteria.getLookupName(), 
															lookupCriteria.getScope(), 
															lookupCriteria.getParentDetailCode(),
															isActive());
			}
		};
		
		addEventListener(Events.ON_OPEN, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9122433436647795127L;

			@Override
			public void onEvent(Event event) throws Exception {
				if(lookupCriteria.getBusinessGroupId() == null) {
					throw new IllegalArgumentException("Business group id must not be empty.");
				}
				if(lookupCriteria.getLookupName() == null) {
					throw new IllegalArgumentException("Lookup Name must not be empty.");
				}
				if(lookupCriteria.getScope() == null) {
					throw new IllegalArgumentException("Scope must not be empty.");
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("title", title);
				params.put("searchText", searchText);
				params.put("headerLabel", headerLabel);
				params.put("source", own);
				params.put("searchDelegate", searchDelegate);
				Window win = (Window) Executions.createComponents("~./common/generic_list_of_value.zul", null, params);
				win.doModal();
			}

			
		});
	}

	public void setLookupCriteria(LookupCriteriaBasicSetup lookupCriteria) {
		this.lookupCriteria = lookupCriteria;
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

	@Override
	public Object getRawValue() {
		return this.value;
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
	
	public void setValueByCode(String lookupName, Long groupId, String detailCode, Long scope) throws RuntimeException {
		try {
			ErrorMessageUtil.clearErrorMessage(own);
			KeyValue keyValue = lookupServiceImpl.getLookup(lookupName, detailCode, groupId, scope);
			setRawValue(keyValue);
		} catch (Exception e) {
			ErrorMessageUtil.setErrorMessage(own, "System Error");
			log.error(e.getMessage(), e);
		}
	}

	public void setValueByCode(String detailCode) throws RuntimeException {
		try {
			ErrorMessageUtil.clearErrorMessage(own);
			KeyValue keyValue = lookupServiceImpl.getLookup(lookupCriteria.getLookupName(), detailCode, lookupCriteria.getBusinessGroupId(), lookupCriteria.getScope());
			setRawValue(keyValue);
		} catch (Exception e) {
			ErrorMessageUtil.setErrorMessage(own, "System Error");
			log.error(e.getMessage(), e);
		}
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public KeyValue getKeyValue() {
		return (KeyValue) this.value;
	}
}

package co.id.fifgroup.core.ui.lookup;

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

import co.id.fifgroup.core.util.ErrorMessageUtil;

import co.id.fifgroup.core.service.LookupServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;

public class LookupWindow extends Bandbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2140898613271534727L;
	private Logger log = LoggerFactory.getLogger(LookupWindow.class);
	private String title;
	private String searchText;
	private String headerLabel;
	private LookupWindow own = this;
	private SerializableSearchDelegate<KeyValue> searchDelegate;
	private Object value;
	private LookupCriteria lookupCriteria;

	private boolean active = true;
	private Long groupId = null;
	private Long companyId = null;
	
	protected LookupServiceAdapter getLookupService() {
		return (LookupServiceAdapter) SpringUtil.getBean("lookupServiceImpl");
	}
	
	protected SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}
	
	public LookupWindow() {
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
				return getLookupService().getLookupValues(searchCriteria, 
														groupId == null ? getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId() : groupId, 
														lookupCriteria.getLookupName(), 
														companyId == null ? getSecurityService().getSecurityProfile().getWorkspaceCompanyId() : companyId, 
														lookupCriteria.getParentDetailCode(), 
														isActive(), limit, offset);
			}
			
			@Override
			public int countBySearchCriteria(String searchCriteria) {
				return getLookupService().getCountLookupValues(searchCriteria, 
															groupId == null ? getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId() : groupId, 
															lookupCriteria.getLookupName(), 
															companyId == null ? getSecurityService().getSecurityProfile().getWorkspaceCompanyId() : companyId, 
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
				ErrorMessageUtil.clearErrorMessage(own);
				if(lookupCriteria.getLookupName() == null) {
					throw new IllegalArgumentException("Lookup Name must not be empty.");
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

	public void setLookupCriteria(LookupCriteria lookupCriteria) {
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
	public void setRawValue(Object value) {
		KeyValue selectedData = (KeyValue) value;
		if(selectedData != null && selectedData.getDescription() != null) {
			super.setValue(selectedData.getDescription().toString());
		} else {
			super.setValue(null);
		}
		this.value = value;
	}
	
	public void setValueByCode(String lookupName, String detailCode) throws RuntimeException {
		try {
			ErrorMessageUtil.clearErrorMessage(own);
			KeyValue keyValue = getLookupService().getLookup(lookupName, detailCode, groupId == null ? getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId() : groupId, companyId == null ? getSecurityService().getSecurityProfile().getWorkspaceCompanyId() : companyId);
			setRawValue(keyValue);
		} catch (Exception e) {
			ErrorMessageUtil.setErrorMessage(own, "System Error");
			log.error(e.getMessage(), e);
		}
	}
	
	public void setValueByCode(String detailCode) throws RuntimeException {
		try {
			ErrorMessageUtil.clearErrorMessage(own);
			KeyValue keyValue = getLookupService().getLookup(lookupCriteria.getLookupName(), detailCode, groupId == null ? getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId() : groupId, companyId == null ? getSecurityService().getSecurityProfile().getWorkspaceCompanyId() : companyId);
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	
}

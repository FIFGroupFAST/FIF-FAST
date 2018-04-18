package co.id.fifgroup.workstructure.renderer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.workstructure.dto.OrganizationContactDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

public class OrganizationContactItemRenderer 
	implements Serializable, SerializableListItemRenderer<OrganizationContactDTO> {

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(OrganizationContactItemRenderer.class);
	private final String lookupNameContact = "MST_COMMUNICATION_TYPE";
	private OrganizationDTO selectedOrg;
	
	private LookupService lookupServiceImpl = (LookupService) SpringUtil.getBean("lookupServiceImpl");
	private SecurityService securityServiceImpl = (SecurityService) SpringUtil.getBean("securityServiceImpl");
	private OrganizationSetupService organizationSetupServiceImpl = (OrganizationSetupService) SpringUtil.getBean("organizationSetupServiceImpl");
	
	public OrganizationContactItemRenderer(OrganizationDTO selectedOrg) {
		super();
		this.selectedOrg = selectedOrg;
	}

	@Override
	public void render(Listitem item, final OrganizationContactDTO data, int index)
			throws Exception {
		new Listcell().setParent(item);
		Listcell contactType = new Listcell();
		final Listbox listboxContactType = getComponentContactType(data);
		listboxContactType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = -3811961348386980411L;

			@Override
			public void onEvent(Event event) throws Exception {
				KeyValue selectedContact = (KeyValue) listboxContactType.getSelectedItem().getValue();
				data.setTypeCode(selectedContact.getKey() == null ? null : selectedContact.getKey().toString());
			}
		});
		listboxContactType.setParent(contactType);
		contactType.setParent(item);
		
		TextboxListcell<OrganizationContactDTO> contactInformation = new TextboxListcell<OrganizationContactDTO>(data, data.getInformation(),  "information", false);
		contactInformation.getComponent().setMaxlength(30);
		contactInformation.setParent(item);
		
		DateboxListcell<OrganizationContactDTO> startDate = new DateboxListcell<OrganizationContactDTO>(data, data.getStartDate(), "startDate");
		startDate.getComponent().setFormat(DateUtil.DEFAULT_FORMAT);
		startDate.getComponent().setValue(DateUtil.add(new Date(), Calendar.DATE, 1));
		startDate.getComponent().setWidth("100px");
		startDate.setParent(item);
		data.setStartDate(startDate.getComponent().getValue());
		
		DateboxListcell<OrganizationContactDTO> endDate = new DateboxListcell<OrganizationContactDTO>(data, data.getEndDate(), "endDate");
		endDate.getComponent().setFormat(DateUtil.DEFAULT_FORMAT);
		endDate.getComponent().setValue(DateUtil.MAX_DATE);
		endDate.getComponent().setWidth("100px");
		endDate.setParent(item);
		
		if(selectedOrg.getId() != null) {
			if((selectedOrg.isFuture()) || (selectedOrg.isCurrent() && organizationSetupServiceImpl.isHaveFuture(selectedOrg.getId()))) {
				listboxContactType.setDisabled(true);
				contactInformation.getComponent().setDisabled(true);
			}
		}
	}
	
	private Listbox getComponentContactType(final OrganizationContactDTO selectedData) {
		Listbox contactType = new Listbox();
		contactType.setMold("select");
		KeyValue keyValueSelect = new KeyValue(null, Labels.getLabel("common.select"), Labels.getLabel("common.select"));
		List<KeyValue> contacts = new ArrayList<KeyValue>();
		contacts.add(keyValueSelect);
		try {
			List<KeyValue> keyValueContact = lookupServiceImpl.getLookups(lookupNameContact, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId()); 
			contacts.addAll(keyValueContact);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		final ListModelList<KeyValue> listModelContact = new ListModelList<KeyValue>(contacts);
		contactType.setModel(listModelContact);
		contactType.renderAll();
		contactType.setItemRenderer(new SerializableListItemRenderer<KeyValue>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, KeyValue data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(String.valueOf(data.getDescription())));
				if(data.getKey() != null && data.getKey().toString().equals(selectedData.getTypeCode())) {
					listModelContact.addToSelection(data);
				}
			}
		});
		if(listModelContact.getSelection().size() < 1) {
			listModelContact.addToSelection(keyValueSelect);
		}
		
		return contactType;
	}

}

package co.id.fifgroup.core.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hlayout;

import co.id.fifgroup.core.service.LookupServiceAdapter;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.lookup.KeyValue;

public class LookupCheckbox extends Hlayout {

	private static final long serialVersionUID = 1L;
	
	public LookupCheckbox() {
		super();
	}
	
	public void setLookupName(String lookupName) {
		Components.removeAllChildren(this);
		List<KeyValue> lookups = getLookupService().getLookups(lookupName
				, getSecurityService().getSecurityProfile().getWorkspaceBusinessGroupId()
				, getSecurityService().getSecurityProfile().getWorkspaceCompanyId()
				, true);
		for (KeyValue lookup : lookups) {
			Checkbox check = new Checkbox((String) lookup.getValue());
			check.setValue(lookup.getKey());
			if (null != lookup.getDescription())
				check.setLabel((String)lookup.getDescription());
			this.appendChild(check);
		}
		
	}
	
	public void setValues(List<String> lookupCodes) {
		for(Component component : getChildren()) {
			if (component instanceof Checkbox) {
				for (String code : lookupCodes) {
					if (code.equals(((Checkbox) component).getValue())) {
						((Checkbox) component).setChecked(true);
						break;
					} else 
						((Checkbox) component).setChecked(false);
				}
			}
				
		}
	}
	
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		for(Component component : getChildren()) {
			if (component instanceof Checkbox) {
				if (((Checkbox) component).isChecked())
					values.add((String) ((Checkbox) component).getValue());
			}
		}
		return values;
	}
	
	private LookupServiceAdapter getLookupService() {
		return (LookupServiceAdapter) SpringUtil.getBean("lookupServiceImpl");
	}
	
	private SecurityService getSecurityService() {
		return (SecurityService) SpringUtil.getBean("securityServiceImpl");
	}
	
	public void setDisabled(boolean disabled) {
		for(Component component : getChildren()) {
			if (component instanceof Checkbox) {
				((Checkbox) component).setDisabled(disabled);
			}
		}
	}
	

}

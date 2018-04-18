package co.id.fifgroup.personneladmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.personneladmin.constant.ActionControl;
import co.id.fifgroup.personneladmin.constant.FieldPermissionsPersonnelAdmin;
import co.id.fifgroup.personneladmin.constant.ReferencePersonnelAdministration;
import co.id.fifgroup.personneladmin.service.RoleService;

import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.personneladmin.domain.Role;
import co.id.fifgroup.personneladmin.domain.RoleExample;

@VariableResolver(DelegatingVariableResolver.class)
public class RoleInformationComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(RoleInformationComposer.class);
	
	private RoleInformationComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient RoleService roleServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	
	@Wire
	private TabularEntry<Role> lbxRoles;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDeleteRow;
	@Wire
	private Button btnSave;
	
	private PersonalInfoMainComposer parentComposer;
	private ActionControl control;
	private List<Role> listRoles;
	private ListModelList<Role> listModelRoles;
	private boolean disabledActiveRole;
		
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		parentComposer = (PersonalInfoMainComposer) arg.get("parentPage");
		control = (ActionControl) arg.get("actionControl");
		populateData();
		disabledComponent();
		buildRoles();
	}
	
	public void populateData() {
		try {
			if (control.equals(ActionControl.VIEW) 
					|| control.equals(ActionControl.NEW_HISTORY)) {
				RoleExample example = new RoleExample();
				example.createCriteria().andPersonIdEqualTo(parentComposer.getPersonMainDTO().getPersonDTO().getPersonId())
				.andCompanyIdEqualTo(parentComposer.getPersonMainDTO().getPersonDTO().getCompanyId());
				listRoles = roleServiceImpl.selectByExample(example);
			}					
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void disabled(boolean disable) {
		btnSave.setDisabled(disable);
		btnAddRow.setDisabled(disable);
		btnDeleteRow.setDisabled(disable);
		disabledActiveRole = disable;
	}
	
	public void disabledComponent() {
		if (control.equals(ActionControl.VIEW)) {
			disabled(true);
		} else if (control.equals(ActionControl.NEW_HISTORY)){
			disabled(true);
			if (GlobalVariable.hasPermission(FieldPermissionsPersonnelAdmin.ROLE_INFO_EDIT_ENABLED)) {
				disabled(false);
			}
		}
	}
	
	@Listen("onClick=#btnAddRow")
	public void addRow(){
		lbxRoles.addRow();
	}
	
	@Listen("onClick=#btnDeleteRow")
	public void deleteRow(){
		List<Role> removeableSelection = new ArrayList<Role>();
		Set<Role> listRole = listModelRoles.getSelection();
		for(Role role : listRole) {
			if(role.getRoleId() != null) {
				removeableSelection.add(role);
			}
		}
		if (removeableSelection.size() > 0) {			
			for(Role role : removeableSelection) {
				listModelRoles.removeFromSelection(role);
			}
			Messagebox.show(Labels.getLabel("pea.err.existingDataCannotBeDeleted"));
		}
		
		lbxRoles.deleteRow();
	}
	
	private void buildRoles() {
		lbxRoles.setDataType(Role.class);
		lbxRoles.setModel(getModel());
		lbxRoles.setItemRenderer(getListItemRenderer());
		lbxRoles.setValidationRule(getTabularValidationRule());
	}
	
	private ListModelList<Role> getModel() {
		if(listRoles == null) {
			listRoles = new ArrayList<Role>();
		}
		listModelRoles = new ListModelList<Role>(listRoles);
		listModelRoles.setMultiple(true);
		return listModelRoles;
	}
	
	@Listen("onClick = #btnSave")
	public void doSave() {
		Messagebox.show(Labels.getLabel("common.confirmationSave"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					try {
						if (lbxRoles.validate()) {
							listRoles = lbxRoles.getValue();
							roleServiceImpl.updateRoles(parentComposer.getPersonMainDTO().getPersonDTO().getPersonId(), 
									parentComposer.getPersonMainDTO().getPersonDTO().getCompanyId(), listRoles);
							Messagebox.show(Labels.getLabel("pea.dataSave"));
							buildRoles();
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		});		
	}
	
	private SerializableListItemRenderer<Role> getListItemRenderer() {
		return new SerializableListItemRenderer<Role>() {

			private static final long serialVersionUID = -7443434000545426106L;

			@Override
			public void render(Listitem item, final Role data, int index)
					throws Exception {
				new Listcell("").setParent(item);
				
				Listcell roleCell = new Listcell();
				final Listbox cmbRoleType = getComponentRoleType(data);
				if (data.getRoleId() != null) {
					cmbRoleType.setDisabled(true);					
				}
				cmbRoleType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -3811961348386980411L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue selectedContact = (KeyValue) cmbRoleType.getSelectedItem().getValue();
						data.setRoleType(selectedContact.getKey() == null ? null : selectedContact.getKey().toString());
					}
				});
				cmbRoleType.setParent(roleCell);
				roleCell.setParent(item);
				
				Listcell activeCell = new Listcell();
				final Checkbox chkActive = new Checkbox();
				chkActive.setDisabled(disabledActiveRole);
				if (data.isActive()) {
					chkActive.setChecked(true);
				}
				chkActive.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -3811961348386980411L;

					@Override
					public void onEvent(Event event) throws Exception {
						data.setActive(chkActive.isChecked());
					}
				});
				chkActive.setParent(activeCell);
				activeCell.setParent(item);				
			}
		};
	}
	
	private TabularValidationRule<Role> getTabularValidationRule() {
		return new TabularValidationRule<Role>() {

			private static final long serialVersionUID = -6859890541380033577L;

			@Override
			public boolean validate(Role data, List<String> errorRow) {
				if(data.getRoleType() == null) {
					errorRow.add(Labels.getLabel("common.err.fieldRequired", new Object[] {Labels.getLabel("pea.typeHcManagement")}));				
				} else {
					for (Role role : listModelRoles) {
						if (role.getRoleType() != null && !data.equals(role)) {
							if (data.getRoleType().equalsIgnoreCase(role.getRoleType())) {
								errorRow.add(Labels.getLabel("pea.err.roleTypeMustBeUnique"));
							}
						}
					}
				}
				
				if(errorRow.size() > 0) return false;
				return true;
			}
		};
	}
	
	private Listbox getComponentRoleType(final Role selectedData) {
		Listbox cmbRole = new Listbox();
		cmbRole.setMold("select");
		KeyValue keyValueSelect = new KeyValue(null, Labels.getLabel("common.pleaseSelect"), Labels.getLabel("common.pleaseSelect"));
		List<KeyValue> listRoles = new ArrayList<KeyValue>();
		listRoles.add(keyValueSelect);
		try {
			List<KeyValue> keyValueRoles = lookupServiceImpl.getLookups(ReferencePersonnelAdministration.LOOKUP_ROLE.getReference(), 
					securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId()); 
			listRoles.addAll(keyValueRoles);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		final ListModelList<KeyValue> listModelAwardTypes = new ListModelList<KeyValue>(listRoles);
		cmbRole.setModel(listModelAwardTypes);
		cmbRole.renderAll();
		cmbRole.setItemRenderer(new SerializableListItemRenderer<KeyValue>() {
			
			private static final long serialVersionUID = 6141520755917006080L;

			@Override
			public void render(Listitem item, KeyValue data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(String.valueOf(data.getDescription())));
				if(data.getKey() != null && data.getKey().toString().equals(selectedData.getRoleType())) {
					listModelAwardTypes.addToSelection(data);
				}
			}
		});
		if(listModelAwardTypes.getSelection().size() < 1) {
			listModelAwardTypes.addToSelection(keyValueSelect);
		}
		
		return cmbRole;
	}	
}
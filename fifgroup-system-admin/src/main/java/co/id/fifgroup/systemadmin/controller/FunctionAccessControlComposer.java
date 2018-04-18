package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import com.google.common.base.Strings;

import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.core.constant.FunctionAccessType;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.systemadmin.domain.FunctionAccess;
import co.id.fifgroup.systemadmin.domain.MenuDetail;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.dto.FunctionAccessDTO;
import co.id.fifgroup.systemadmin.dto.ItemExclusionDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.MenuService;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;

@VariableResolver(DelegatingVariableResolver.class)
public class FunctionAccessControlComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(FunctionAccessControlComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityService responsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient AuditPerformer auditPerformer;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient MenuService menuService;
	@Wire
	private Label lblResponsibilityName;
	@Wire
	private Label lblMenuName;
	@Wire
	private Radiogroup rdogrpDefaultAccess;
	@Wire
	private Groupbox grbAccessControl;
	@Wire
	private TabularEntry<FunctionAccess> tbnCustomAccessControl;
	@Wire
	private Button btnSave;
	@Wire
	private Checkbox chkAllCreate;
	@Wire
	private Checkbox chkAllUpdate;
	@Wire
	private Checkbox chkAllDelete;
	
	private ResponsibilityDTO responsibilityDto;
	private List<FunctionAccessDTO> functionAccessList;
	private String functionDefaultAccessValue;
	private ResponsibilityDTO previousObject;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameter();
		/*if(rdogrpDefaultAccess.getSelectedIndex() == 2) {
			grbAccessControl.setVisible(true);
			buildTabularAccessControl();
			
		}*/
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			Vbox v1 = (Vbox) rdogrpDefaultAccess.getChildren().get(0);
			for (Component rd : v1.getChildren()) {
				Radio r = (Radio)rd;
				r.setDisabled(true);
			}
			btnSave.setDisabled(true);
		}
	}

	private void loadParameter() {
		functionDefaultAccessValue = new String();
		responsibilityDto = (ResponsibilityDTO) arg.get("responsibility");
		previousObject = (ResponsibilityDTO) arg.get("previousObject");
		if(responsibilityDto != null) {
			lblResponsibilityName.setValue(responsibilityDto.getResponsibilityName());
			lblMenuName.setValue(responsibilityDto.getMenu().getMenuName());
			/*long item = 0L;
			for (ItemExclusionDTO itemExclusionDTO : responsibilityDto.getItemExclusions()) {
				item += itemExclusionDTO.getActionId();			
			}
			
			if(item != 0L) {
				functionAccessList = responsibilityService.findFunctionAndSubmenuByNotInActionIdUseItemExclusion(responsibilityDto);
			} else {
				functionAccessList = responsibilityService.findFunctionAndSubmenuByNotInActionIdNotUseItemExclusion(responsibilityDto);
			}
						
			resp = responsibilityService.selectByPrimaryKey(responsibilityDto.getId());
			if(resp != null) {
				responsibilityDto.setFunctionDefaultAccess(resp.getFunctionDefaultAccess());			
				if(responsibilityDto.getFunctionDefaultAccess().equalsIgnoreCase(FunctionAccessType.FULL.toString())) {
					rdogrpDefaultAccess.setSelectedIndex(0);
					functionDefaultAccessValue = FunctionAccessType.FULL.getValue();
				} else if(responsibilityDto.getFunctionDefaultAccess().equalsIgnoreCase(FunctionAccessType.READ.toString())) {
					rdogrpDefaultAccess.setSelectedIndex(1);
					functionDefaultAccessValue = FunctionAccessType.READ.getValue();
				} else if(responsibilityDto.getFunctionDefaultAccess().equalsIgnoreCase(FunctionAccessType.CUSTOM.toString())) {
					rdogrpDefaultAccess.setSelectedIndex(2);
					functionDefaultAccessValue = FunctionAccessType.CUSTOM.getValue();
				}
			}*/
			
			if(functionAccessList == null){
				functionAccessList = new ArrayList<FunctionAccessDTO>();
				
			}
			loadFunctionItem(responsibilityDto.getMenuId(), responsibilityDto.getId(), responsibilityDto.getFunctionDefaultAccess());
			if(Strings.isNullOrEmpty(responsibilityDto.getFunctionDefaultAccess())){
				rdogrpDefaultAccess.setSelectedIndex(0);
				functionDefaultAccessValue = FunctionAccessType.FULL.toString();
			}else{
				String dAccess = responsibilityDto.getFunctionDefaultAccess();
				if(dAccess.equals(FunctionAccessType.FULL.toString())){
					rdogrpDefaultAccess.setSelectedIndex(0);
					functionDefaultAccessValue = FunctionAccessType.FULL.toString();}
				else if(dAccess.equals(FunctionAccessType.READ.toString())){
					rdogrpDefaultAccess.setSelectedIndex(1);
					functionDefaultAccessValue = FunctionAccessType.READ.toString();
					}
				else{
					functionDefaultAccessValue = FunctionAccessType.CUSTOM.toString();
					rdogrpDefaultAccess.setSelectedIndex(2);
					grbAccessControl.setVisible(true);
					buildTabularAccessControl();
				}
			}
			
		}
	}
	
	private void loadFunctionItem(Long menuId, Long respId, String type){
		List<FunctionAccessDTO> menuDetails = getMenuDetail(menuId, respId, type);
		for (FunctionAccessDTO menuDetail : menuDetails) {
			if(menuDetail.getItemType().equals(MenuItemType.SUBMENU.toString())){
				loadFunctionItem(menuDetail.getFunctionId(), respId, type);
			}else{
				functionAccessList.add(menuDetail);
			}
		}
		setCheckedAllHeader();
	}

	private void setCheckedAllHeader() {
		boolean disabledCreate = true;
		boolean disabledUpdate = true;
		boolean disabledDelete = true;
		for (FunctionAccessDTO functionAccess : functionAccessList) {
			if (disabledCreate && !functionAccess.isWritable()) {
				disabledCreate = false;
			}
			if (disabledUpdate && !functionAccess.isEditable()) {
				disabledUpdate = false;
			}
			if (disabledDelete && !functionAccess.isErasable()) {
				disabledDelete = false;
			}
		}
		chkAllCreate.setChecked(disabledCreate);
		chkAllUpdate.setChecked(disabledUpdate);
		chkAllDelete.setChecked(disabledDelete);
	}
	
	private List<FunctionAccessDTO> getMenuDetail(Long menuId, Long respId, String type){
		List<FunctionAccessDTO> menuDetails = responsibilityService.getFunctionsAccessByMenuId(menuId, respId, type);
		return menuDetails;
	}
	
	private void buildTabularAccessControl() {
		tbnCustomAccessControl.setModel(getCustomAccessModel());
		tbnCustomAccessControl.setItemRenderer(getCustomAccessRenderer());
	}
	
	private ListModelList<FunctionAccessDTO> getCustomAccessModel() {
		if(functionAccessList == null) {
			functionAccessList = new ArrayList<FunctionAccessDTO>();
			functionAccessList.add(new FunctionAccessDTO());
		}
		
		ListModelList<FunctionAccessDTO> model = new ListModelList<FunctionAccessDTO>(functionAccessList);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<FunctionAccessDTO> getCustomAccessRenderer() {
		return new SerializableListItemRenderer<FunctionAccessDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final FunctionAccessDTO data, int index) throws Exception {
				new TextboxListcell<FunctionAccessDTO>(data, data.getItemName() != null ? data.getItemName() : "", "itemName", true).setParent(item);
				
				Listcell cellCreate = new Listcell();
				final Checkbox checkBoxCreate = new Checkbox();		
				checkBoxCreate.setChecked(data.isWritable());
				checkBoxCreate.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if(checkBoxCreate.isChecked()) {
							data.setWritable(true);			
						} else {
							data.setWritable(false);
							chkAllCreate.setChecked(false);
						}
					}
				});
				cellCreate.appendChild(checkBoxCreate);
				cellCreate.setParent(item);
				
				Listcell cellUpdate = new Listcell();
				final Checkbox checkBoxUpdate = new Checkbox();
				checkBoxUpdate.setChecked(data.isEditable());
				checkBoxUpdate.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if(checkBoxUpdate.isChecked()) {
							data.setEditable(true);			
						} else {
							data.setEditable(false);
							chkAllUpdate.setChecked(false);
						}
					}
				});
				cellUpdate.appendChild(checkBoxUpdate);
				cellUpdate.setParent(item);
				
				Listcell cellDelete = new Listcell();
				final Checkbox checkBoxDelete = new Checkbox();
				checkBoxDelete.setChecked(data.isErasable());
				checkBoxDelete.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {
			
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if(checkBoxDelete.isChecked()) {
							data.setErasable(true);			
						} else {
							data.setErasable(false);
							chkAllDelete.setChecked(false);
						}
					}
				});
				cellDelete.appendChild(checkBoxDelete);
				cellDelete.setParent(item);
			}
		};
	}
	
	private boolean validate() {
		if(rdogrpDefaultAccess.getSelectedIndex() == -1) {
			return false;
		}
		return true;
	}
	
	private void validation() {
		ErrorMessageUtil.clearErrorMessage(rdogrpDefaultAccess);
		if(rdogrpDefaultAccess.getSelectedIndex() == -1) {
			ErrorMessageUtil.setErrorMessage(rdogrpDefaultAccess, "Default access can't empty");
			return;
		}
	}
	
	@Listen ("onClick = button#btnSave")
	public void save() {
		if (validate() == true) {
			final Control control;
				
			if(previousObject == null)
				control = Control.CREATE;
			else
				control = Control.UPDATE;
			
			if(responsibilityDto != null) {
				try {									
					responsibilityDto.setFunctionDefaultAccess(functionDefaultAccessValue);
					responsibilityDto.setCustomFunctionAccesss(tbnCustomAccessControl.getValue());
										
					Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {	
							int status = (int) event.getData();
							if(status == 16) {		
								try {
									responsibilityService.save(responsibilityDto);
									auditPerformer.doAudit(UUID.randomUUID(), UUID.randomUUID(), previousObject, responsibilityDto, 1, control, Action.SUBMIT, securityServiceImpl.getSecurityProfile().getUserId(), TrxType.RESPONSIBILITY_SETUP.getValue(), responsibilityDto.getId());
									Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
									Map<String, Object> param = new HashMap<String, Object>();
									param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
									Executions.createComponents("~./hcms/systemadmin/responsibility_inquiry.zul", getSelf().getParent(), param);
									getSelf().detach();
								} catch (Exception e) {
									log.error(e.getMessage());
									Messagebox.show(Labels.getLabel("notif.accessDataError"), "ERROR", Messagebox.OK, Messagebox.ERROR);
								}
								
							} else {
								return;
							}
						}
					});
				} catch (Exception e) {
					log.error("error", e);
				}
			}
		} else {
			validation();
			return;
		}
	}
	
	@Listen ("onClick = button#btnBack")
	public void back() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("responsibility", responsibilityDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/responsibility_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen ("onClick = radiogroup#rdogrpDefaultAccess")
	public void rdogrpDefaultAccessSelected() {
		if(rdogrpDefaultAccess.getSelectedIndex() == 0) {
			functionDefaultAccessValue = FunctionAccessType.FULL.toString();
			grbAccessControl.setVisible(false);
		} else if(rdogrpDefaultAccess.getSelectedIndex() == 1) {
			functionDefaultAccessValue = FunctionAccessType.READ.toString();
			grbAccessControl.setVisible(false);
		} else if(rdogrpDefaultAccess.getSelectedIndex() == 2) {
			functionDefaultAccessValue = FunctionAccessType.CUSTOM.toString();
			grbAccessControl.setVisible(true);
			buildTabularAccessControl();
		}
	}
	
	@Listen("onCheck = #chkAllCreate")
	public void onCheckAllCreate() {
		for (FunctionAccessDTO functionAccess : functionAccessList) {
			if(chkAllCreate.isChecked()) {
				functionAccess.setWritable(true);			
			} else {
				functionAccess.setWritable(false);
			}
		}
		tbnCustomAccessControl.setModel(getCustomAccessModel());
		tbnCustomAccessControl.renderAll();
	}
	
	@Listen("onCheck = #chkAllUpdate")
	public void onCheckAllUpdate() {
		for (FunctionAccessDTO functionAccess : functionAccessList) {
			if(chkAllUpdate.isChecked()) {
				functionAccess.setEditable(true);			
			} else {
				functionAccess.setEditable(false);
			}
		}
		tbnCustomAccessControl.setModel(getCustomAccessModel());
		tbnCustomAccessControl.renderAll();
	}
	
	@Listen("onCheck = #chkAllDelete")
	public void onCheckAllDelete() {
		for (FunctionAccessDTO functionAccess : functionAccessList) {
			if(chkAllDelete.isChecked()) {
				functionAccess.setErasable(true);			
			} else {
				functionAccess.setErasable(false);
			}
		}
		tbnCustomAccessControl.setModel(getCustomAccessModel());
		tbnCustomAccessControl.renderAll();
	}
}

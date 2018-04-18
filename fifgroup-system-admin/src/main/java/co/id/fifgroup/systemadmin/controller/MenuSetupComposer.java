package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;

import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.IntboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.systemadmin.domain.MenuItem;
import co.id.fifgroup.systemadmin.dto.MenuDetailDto;
import co.id.fifgroup.systemadmin.dto.MenuDto;
import co.id.fifgroup.systemadmin.service.MenuService;
import co.id.fifgroup.systemadmin.validation.MenuValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class MenuSetupComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory
			.getLogger(MenuSetupComposer.class);

	@WireVariable(rewireOnActivate = true)
	private transient MenuService menuService;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private MenuValidator menuValidator;

	@Wire
	private TabularEntry<MenuDetailDto> tbnMenuDetail;
	@Wire
	private Textbox txtMenuName;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private Datebox dtbDateTo;
	@Wire
	private Label lblErrorMenu;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnSave;

	@WireVariable(rewireOnActivate = true)
	private transient AuditPerformer auditPerformer;

	private List<MenuDetailDto> menuDetailDto;
	private MenuDto menuDto;
	private MenuDto selectedMenu;
	private MenuDto prevObject;
	private FunctionPermission functionPermission;
	private boolean disabled = false;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameter();
		buildMenuDetail();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}

	private void initFunctionSecurity() {
		if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}
		
		if(selectedMenu != null){
			if(functionPermission.isEditable())
				disableComponent(false);
			else
				disableComponent(true);
		}
	}
	
	private void disableComponent(boolean disabled){
		txtMenuName.setDisabled(disabled);
		txtDesc.setDisabled(disabled);
		dtbDateTo.setDisabled(disabled);
		btnAddRow.setDisabled(disabled);
		btnDelete.setDisabled(disabled);
		btnSave.setDisabled(disabled);
		this.disabled = disabled;
	}

	private void loadParameter() {
		selectedMenu = (MenuDto) arg.get("menuDto");
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		if (selectedMenu != null) {

			menuDto = selectedMenu;
			menuDto = menuService.getMenuById(selectedMenu.getId());

			if (menuDto != null) {
				menuDetailDto = menuDto.getMenuDetailDto();
				menuDto.setMenuDetailDto(menuDetailDto);
			}
			prevObject = (MenuDto) DeepCopy.copy(menuDto);
			txtMenuName.setDisabled(true);
			txtMenuName.setValue(selectedMenu.getMenuName());
			txtDesc.setValue(selectedMenu.getDescription());
			dtbDateFrom.setValue(selectedMenu.getDateFrom());
			dtbDateTo.setValue(selectedMenu.getDateTo());
			dtbDateFrom.setDisabled(true);
		}else{
			dtbDateFrom.setValue(new Date());
			dtbDateTo.setValue(DateUtil.MAX_DATE);
		}
	}

	private void buildMenuDetail() {
		tbnMenuDetail.setDataType(MenuDetailDto.class);
		tbnMenuDetail.setModel(getMenuDetailModel());
		tbnMenuDetail.setItemRenderer(getListItemRenderer());
		tbnMenuDetail.setValidationRule(menuDetailValidationRule());
	}

	@Listen("onClick=#btnAddRow")
	public void addRow() {
		tbnMenuDetail.addRow();
	}

	@Listen("onClick=#btnDelete")
	public void deleteRow() {
		lblErrorMenu.setValue("");
		Set<Listitem> itemToBeDeleted = tbnMenuDetail.getSelectedItems();
		boolean hasError = false;
		ListModelList<Object> model = (ListModelList<Object>)tbnMenuDetail.getModel();
		
		for (Listitem listitem : itemToBeDeleted) {
			MenuDetailDto menuDetailDto = listitem.getValue();
			if (menuDetailDto.getId() != null) {
				listitem.setSelected(false);
				model.removeFromSelection(listitem.getValue());
				lblErrorMenu.setValue(Labels.getLabel("sam.existingDataCannotBeDeletd"));
				hasError = true;
				break;
			}
		}
		
		if(!hasError)
			tbnMenuDetail.deleteRow();
		
	}

	private boolean validationTabularMenu() {
		int i, j;
		if (tbnMenuDetail.getItemCount() <= 0) {
			lblErrorMenu.setValue(Labels
					.getLabel("sam.menuItemMustSelectedMinimalOneItem"));
			return false;
		}

		for (i = 0; i < tbnMenuDetail.getItemCount(); i++) {
			if (txtMenuName
					.getValue()
					.toUpperCase()
					.trim()
					.equalsIgnoreCase(
							tbnMenuDetail.getValue().get(i).getItemName()
									.toUpperCase().trim())) {
				lblErrorMenu.setValue(Labels
						.getLabel("sam.menuItemIsREcursive"));
				return false;
			}

			for (j = 0; j < tbnMenuDetail.getItemCount(); j++) {
				if (i == j) {
					continue;
				}
				if (tbnMenuDetail
						.getValue()
						.get(i)
						.getItemName()
						.trim()
						.equalsIgnoreCase(
								tbnMenuDetail.getValue().get(j).getItemName()
										.trim())
						&& tbnMenuDetail
								.getValue()
								.get(i)
								.getMenuItemType()
								.trim()
								.equalsIgnoreCase(
										tbnMenuDetail.getValue().get(j)
												.getMenuItemType().trim())) {
					lblErrorMenu.setValue(Labels
							.getLabel("sam.menuItemCannotBeDouble"));
					return false;
				}
			}
		}
		return true;
	}

	@Listen("onClick = button#btnSave")
	public void save() {
		lblErrorMenu.setValue("");

		final Control control;

		if (menuDto == null) {
			menuDto = new MenuDto();
			control = Control.CREATE;
		} else
			control = Control.UPDATE;

		menuDto.setMenuName(txtMenuName.getValue());
		menuDto.setDescription(txtDesc.getValue());
		menuDto.setDateFrom(dtbDateFrom.getValue() != null ? DateUtil.truncate(dtbDateFrom.getValue()) : null);
		menuDto.setDateTo(dtbDateTo.getValue() != null ? DateUtil.truncate(dtbDateTo.getValue()) : null);
		menuDetailDto = tbnMenuDetail.getValue();
		menuDto.setMenuDetailDto(menuDetailDto);
		if (selectedMenu == null) {
			menuDto.setCreatedBy(securityServiceImpl.getSecurityProfile()
					.getUserId());
			menuDto.setCreationDate(new Date());
		} else {
			menuDto.setCreatedBy(selectedMenu.getCreatedBy());
			menuDto.setCreationDate(selectedMenu.getCreationDate());
		}
		menuDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile()
				.getUserId());
		menuDto.setLastUpdateDate(new Date());

		Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					/**
				 * 
				 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {
								clearErrorMessage();

								menuValidator.validate(menuDto);
								if (tbnMenuDetail.validate()
										&& validationTabularMenu()) {
									menuService.save(menuDto);
									auditPerformer.doAudit(UUID.randomUUID(),
											UUID.randomUUID(), prevObject,
											menuDto, 1, control, Action.SUBMIT,
											securityServiceImpl
													.getSecurityProfile()
													.getUserId(), TrxType.MENU_SETUP.getValue(),
											menuDto.getId());
									Messagebox.show(
											Labels.getLabel("sam.dataSave"),
											"Information", Messagebox.YES,
											Messagebox.INFORMATION);
									Map<String, Object> param = new HashMap<String, Object>();
									param.put(
											GlobalVariable.FUNCTION_PERMISSION_KEY,
											functionPermission);
									Executions
											.createComponents(
													"~./hcms/systemadmin/menu_inquiry.zul",
													getSelf().getParent(),
													param);
									getSelf().detach();
								}
							} catch (ValidationException e) {
								showErrorMessage(e);
							} catch (Exception e) {
								log.error(e.getMessage());
								Messagebox.show(Labels
										.getLabel("notif.accessDataError"),
										"ERROR", Messagebox.OK,
										Messagebox.ERROR);
							}
						} else {
							return;
						}
					}
				});

	}

	private void showErrorMessage(ValidationException e) {
		ErrorMessageUtil.setErrorMessage(txtMenuName,
				e.getMessage(MenuValidator.MENU_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtMenuName,
				e.getMessage(MenuValidator.MENU_NAME_NOT_UNIQUE));
		ErrorMessageUtil.setErrorMessage(dtbDateFrom,
				e.getMessage(MenuValidator.DATE_FROM_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtDesc,
				e.getMessage(MenuValidator.DESCRIPTION_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(dtbDateTo,
				e.getMessage(MenuValidator.DATE_TO_NOT_SAME_DATE_FROM));
	}

	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtMenuName);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
		ErrorMessageUtil.clearErrorMessage(txtDesc);
	}

	@Listen("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("sam.cancelMessage"), "Information",
				Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION,
				new SerializableEventListener<Event>() {

					/**
			 * 
			 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY,
									functionPermission);
							Executions.createComponents(
									"~./hcms/systemadmin/menu_inquiry.zul",
									getSelf().getParent(), param);
							getSelf().detach();
						} else {
							return;
						}
					}
				});
	}

	private ListModelList<MenuDetailDto> getMenuDetailModel() {
		if (menuDetailDto == null) {
			menuDetailDto = new ArrayList<MenuDetailDto>();
			menuDetailDto.add(new MenuDetailDto());
		}

		ListModelList<MenuDetailDto> model = new ListModelList<MenuDetailDto>(
				menuDetailDto);
		model.setMultiple(true);
		return model;
	}

	private TabularValidationRule<MenuDetailDto> menuDetailValidationRule() {
		return new TabularValidationRule<MenuDetailDto>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(MenuDetailDto data, List<String> errorRow) {
				if (data.getSequence() == null){
					errorRow.add(Labels.getLabel("common.err.fieldRequired",
							new Object[] { Labels.getLabel("sam.sequence") }));
				}else{
					if(data.getSequence() < 0){
						errorRow.add(Labels.getLabel("common.mustBeEqualOrGreaterThan",
							new Object[] { Labels.getLabel("sam.sequence"), 0 }));
					}
				}
				if (data.getPrompt() == null)
					errorRow.add(Labels.getLabel("common.err.fieldRequired",
							new Object[] { Labels.getLabel("sam.prompt") }));
				if (data.getActionId() == null)
					errorRow.add(Labels.getLabel("common.err.fieldRequired",
							new Object[] { Labels.getLabel("sam.menuItem") }));
				if (data.getDateFrom() == null)
					errorRow.add(Labels.getLabel("common.err.fieldRequired",
							new Object[] { Labels.getLabel("ben.dateFrom") }));
				if (data.getDateTo() != null) {
					if (DateUtil.compareDate(data.getDateFrom(),
							data.getDateTo())) {
						errorRow.add(Labels
								.getLabel("ben.dateFromMustBeLessThanOrEqualThanDateTo"));
					}
				}

				if (errorRow.size() > 0) {
					return false;
				}
				return true;
			}
		};
	}

	private SerializableListItemRenderer<MenuDetailDto> getListItemRenderer() {
		return new SerializableListItemRenderer<MenuDetailDto>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final MenuDetailDto data, int index) throws Exception {
				item.setValue(data);
				new Listcell("").setParent(item);

				Listcell typeCell = new Listcell();
				final Listbox type = createMenuItemType(data.getMenuItemType());
				MenuItemType selected = (MenuItemType) type.getSelectedItem().getValue();
				data.setMenuItemType(selected.toString());
				
				typeCell.appendChild(type);
				typeCell.setParent(item);

				if (data.getItemName() != null)
					type.setDisabled(true);

				new IntboxListcell<MenuDetailDto>(data,data.getSequence() == null ? 0 : (int) data.getSequence(), "sequence", disabled, 5, "90%").setParent(item);
				new TextboxListcell<MenuDetailDto>(data, data.getPrompt(),"prompt", disabled, 50, "90%").setParent(item);

				Listcell typeCellMenuItem = new Listcell();
				final CommonPopupBandbox commonPopupBandboxMenuItem = new CommonPopupBandbox();
				commonPopupBandboxMenuItem.setTitle("List of Menu Item");
				commonPopupBandboxMenuItem.setSearchText("Menu Item Name");
				commonPopupBandboxMenuItem.setHeaderLabel("Menu Item");
				commonPopupBandboxMenuItem.setSearchDelegate(new SerializableSearchDelegate<MenuItem>() {

					private static final long serialVersionUID = 1L;

					@Override
					public List<MenuItem> findBySearchCriteria(String searchCriteria, int limit, int offset) {
						MenuItem menuItem = new MenuItem();
						menuItem.setItemName(searchCriteria);
						MenuItemType selected = (MenuItemType) type.getSelectedItem().getValue();
						return menuService.getMenuItemByExample(menuItem, selected, limit, offset);
					}

					@Override
					public int countBySearchCriteria(String searchCriteria) {
						MenuItem menuItem = new MenuItem();
						menuItem.setItemName(searchCriteria);
						MenuItemType selected = (MenuItemType) type.getSelectedItem().getValue();
						return menuService.countMenuItemByExample(menuItem, selected);
					}

					@Override
					public void mapper(KeyValue keyValue, MenuItem data) {
						keyValue.setKey(data.getActionId());
						keyValue.setValue(data.getItemName());
						keyValue.setDescription(data.getItemName());
					}
				});
				commonPopupBandboxMenuItem.setWidth("90%");
				commonPopupBandboxMenuItem.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if (keyValue != null) {
							data.setActionId((long) keyValue.getKey());
							data.setItemName(keyValue.getValue().toString());
						} else {
							data.setActionId(null);
							data.setItemName(null);
						}
					}
				});

				KeyValue setKeyValueCompany = new KeyValue(data.getActionId(),data.getItemName(), data.getItemName());
				commonPopupBandboxMenuItem.setRawValue(setKeyValueCompany);
				commonPopupBandboxMenuItem.setReadonly(true);
				typeCellMenuItem.appendChild(commonPopupBandboxMenuItem);
				typeCellMenuItem.setParent(item);
				
				if (data.getItemName() != null)
					commonPopupBandboxMenuItem.setDisabled(true);

				if (data.getItemName() != null){
					
					DateboxListcell<MenuDetailDto> detail = new DateboxListcell<MenuDetailDto>(data,data.getDateFrom(), "dateFrom", true);
//					detail.setWidth("50px"); //FIXME : menjadi bug ketika membuka detail menu setup. dengan message "set width instead."
					detail.setParent(item);
				}else
					new DateboxListcell<MenuDetailDto>(data,data.getDateFrom(), "dateFrom", false).setParent(item);
				
				new DateboxListcell<MenuDetailDto>(data, data.getDateTo(),"dateTo", disabled).setParent(item);
				
				type.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						MenuItemType selected = (MenuItemType) type.getSelectedItem().getValue();
						data.setMenuItemType(selected.toString());
						
						data.setActionId(null);
						commonPopupBandboxMenuItem.setRawValue(null);
						commonPopupBandboxMenuItem.setValue(null);
					}
				});
			}
		};
	}

	private Listbox createMenuItemType(String selected) {
		Listbox type = new Listbox();
		type.setMold("select");
		ListModelList<MenuItemType> model = new ListModelList<MenuItemType>(
				MenuItemType.values());
		type.setModel(model);
		type.renderAll();
		type.setWidth("90%");
		if (selected != null) {
			int idx = 0;
			for (MenuItemType menuItemType : model) {
				if (menuItemType.toString().equals(selected))
					type.setSelectedItem(type.getItemAtIndex(idx));
				idx++;
			}
		} else
			type.setSelectedIndex(0);

		return type;
	}
}

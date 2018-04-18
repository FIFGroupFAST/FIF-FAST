package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
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
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import com.google.common.base.Strings;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.UserExample;
import co.id.fifgroup.systemadmin.dto.JobResponsibilityDetailDTO;
import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.dto.UserResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;
import co.id.fifgroup.systemadmin.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UserDetailComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(UserDetailComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient UserService userService;
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityService responsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private TabularEntry<UserResponsibilityDTO> tbnDirectRespListbox;
	@Wire
	private TabularEntry<UserResponsibilityDTO> tbnJobRespListbox;
	@Wire
	private Textbox txtUserName;
	@Wire
	private Datebox dtbDateFrom;
	@Wire
	private Datebox dtbDateTo;
	@Wire
	private Label lblErrorDirectResponsibility;
	@Wire
	private Label lblErrorJobResponsibility;
	@Wire
	private Button btnDirectAddRow;
	@Wire
	private Button btnDirectDelete;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnValidate;
	@Wire
	private CommonPopupDoubleBandbox bnbEmployee;
	
	private UserDTO userDto;
	private UserDTO selectedUser;
	private List<UserResponsibilityDTO> directResponsibilities;
	private List<UserResponsibilityDTO> newDirectResponsibilities;
	private List<UserResponsibilityDTO> jobResponsibilities;
	private List<UserResponsibilityDTO> newJobResponsibilities;
	private FunctionPermission functionPermission;
	private boolean isError = false;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		bnbEmployee.setConcatValueDescription(true);
		loadParameters();
		buildEmployeePopup();
		buildDirectResponsibilityList();
		buildJobResponsibilityList();
	}
		
	private void loadParameters() {
		userDto = new UserDTO();
		dtbDateFrom.setFormat(FormatDateUI.getDateFormat());
		dtbDateTo.setFormat(FormatDateUI.getDateFormat());
		selectedUser = (UserDTO) arg.get("userDTO");
		if(selectedUser != null) {
			userDto = userService.getUserById(selectedUser.getId());
			directResponsibilities = userDto.getDirectResponsibilityDto();
			jobResponsibilities = userDto.getJobResponsibilityDto();
			newDirectResponsibilities = directResponsibilities;
			newJobResponsibilities = jobResponsibilities;
			txtUserName.setValue(selectedUser.getUserName());
			txtUserName.setDisabled(true);
			if(selectedUser.getPersonId() != null && selectedUser.getEmployeeName() != null) {
				bnbEmployee.setRawValue(new KeyValue(selectedUser.getPersonId(), selectedUser.getUserName(), selectedUser.getEmployeeName()));
			}
			dtbDateFrom.setValue(selectedUser.getDateFrom());
			dtbDateTo.setValue(selectedUser.getDateTo());
			dtbDateFrom.setDisabled(true);
			bnbEmployee.setDisabled(true);
		}else{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, 1);
			dtbDateFrom.setValue(cal.getTime());
			dtbDateTo.setValue(DateUtil.MAX_DATE);
		}
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
		
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			txtUserName.setDisabled(true);
			bnbEmployee.setDisabled(true);
			dtbDateFrom.setDisabled(true);
			dtbDateTo.setDisabled(true);
			btnDirectAddRow.setDisabled(true);
			btnDirectDelete.setDisabled(true);
			btnSave.setDisabled(true);
			btnValidate.setDisabled(true);
		}
	}
	
	@Listen("onClick=#btnValidate")
	public void btnValidateUserName() {
		clearMessage();
		isError = false;
		if(!Strings.isNullOrEmpty(txtUserName.getValue())){
			ErrorMessageUtil.clearErrorMessage(txtUserName);
			if(!validateUsername()){
				ErrorMessageUtil.setErrorMessage(txtUserName, Labels.getLabel("sam.invalidUserName"));
				isError = true;
			}else 
				ErrorMessageUtil.setErrorMessage(txtUserName, "User name is valid");;	
		}
		else{
			ErrorMessageUtil.setErrorMessage(txtUserName, "Please fill User Name");
		}
	}
	
	private boolean validateUsername(){
		try {
			return userService.validateLdapUser(txtUserName.getValue());
		} catch (WrongValueException e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
		} catch (Exception e) {
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return false;
	}
	

	
	private void buildEmployeePopup() {
		bnbEmployee.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

			private static final long serialVersionUID = -3390151775044854377L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria1, String searchCriteria2, int limit, int offset) {
				PersonCriteriaDTO criteria = new PersonCriteriaDTO();
				criteria.setEmployeeNumber(searchCriteria1);
				criteria.setFullName(searchCriteria2);
				List<PeopleType> inPeopleTypes = new ArrayList<PeopleType>();
				inPeopleTypes.add(PeopleType.EMPLOYEE);
				inPeopleTypes.add(PeopleType.INTERNSHIP);
				criteria.setInPeopleTypes(inPeopleTypes);
				return personService.getAllPersonByCompany(criteria, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1, String searchCriteria2) {
				PersonCriteriaDTO criteria = new PersonCriteriaDTO();
				criteria.setEmployeeNumber(searchCriteria1);
				criteria.setFullName(searchCriteria2);
				List<PeopleType> inPeopleTypes = new ArrayList<PeopleType>();
				inPeopleTypes.add(PeopleType.EMPLOYEE);
				inPeopleTypes.add(PeopleType.INTERNSHIP);
				criteria.setInPeopleTypes(inPeopleTypes);
				return personService.countAllPersonByCompany(criteria, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey(data.getKey());
				keyValue.setDescription(data.getDescription());
				keyValue.setValue(data.getValue());
			}
		});
	}
	
	//Start Direct Responsibility Tabular Entry
	public void buildDirectResponsibilityList() {
		tbnDirectRespListbox.setDataType(UserResponsibilityDTO.class);
		tbnDirectRespListbox.setModel(getDirectResponsibilityModel());
		tbnDirectRespListbox.setItemRenderer(getDirectRespItemRenderer());
		tbnDirectRespListbox.setValidationRule(directResponsibilityValidationRule());
	}
		
	@Listen("onClick=#btnDirectAddRow")
	public void addDirectRespRow() {
		tbnDirectRespListbox.addRow();
	}
	
	@Listen("onClick=#btnDirectDelete")
	public void deleteDirtectRespRow() {
		/*lblErrorDirectResponsibility.setValue("");
		Set<Listitem> itemToBeDeleted = tbnDirectRespListbox.getSelectedItems();
		for (Listitem listitem : itemToBeDeleted) {
			if(((UserResponsibilityDTO)listitem.getValue()).getResponsibilityId() != null)
				lblErrorDirectResponsibility.setValue("The existing data cannot be deleted");
		}
		
		tbnDirectRespListbox.deleteRow();*/
		
		lblErrorDirectResponsibility.setValue("");
		Set<Listitem> itemToBeDeleted = tbnDirectRespListbox.getSelectedItems();
		boolean hasError = false;
		ListModelList<Object> model = (ListModelList<Object>)tbnDirectRespListbox.getModel();
		
		for (Listitem listitem : itemToBeDeleted) {
			UserResponsibilityDTO userResponsibilityDTO = listitem.getValue();
			if (userResponsibilityDTO.getId() != null) {
				listitem.setSelected(false);
				model.removeFromSelection(listitem.getValue());
				lblErrorDirectResponsibility.setValue(Labels.getLabel("sam.existingDataCannotBeDeletd"));
				hasError = true;
				break;
			}
		}
		
		if(!hasError)
			tbnDirectRespListbox.deleteRow();
	}
	
	private ListModelList<UserResponsibilityDTO> getDirectResponsibilityModel() {
		if(newDirectResponsibilities == null || newDirectResponsibilities.size() < 1) {
			newDirectResponsibilities = new ArrayList<UserResponsibilityDTO>();
			newDirectResponsibilities.add(new UserResponsibilityDTO());
		}
		ListModelList<UserResponsibilityDTO> model = new ListModelList<UserResponsibilityDTO>(newDirectResponsibilities);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<UserResponsibilityDTO> getDirectRespItemRenderer() {
		return new SerializableListItemRenderer<UserResponsibilityDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final UserResponsibilityDTO data, int index) throws Exception {
				new Listcell().setParent(item);
				item.setValue(data);
				Listcell cellResponsibility = new Listcell();
				CommonPopupBandbox commonPopupBandboxResponsibility = new CommonPopupBandbox();
				commonPopupBandboxResponsibility.setWidth("90%");
				commonPopupBandboxResponsibility.setTitle("List of Responsibility");
				commonPopupBandboxResponsibility.setSearchText("Responsibility Name");
				commonPopupBandboxResponsibility.setHeaderLabel("Responsibility");
				commonPopupBandboxResponsibility.setSearchDelegate(new SerializableSearchDelegate<Responsibility>() {

					private static final long serialVersionUID = 1L;

					@Override
					public List<Responsibility> findBySearchCriteria(String searchCriteria, int limit, int offset) {
						ResponsibilityExample example = new ResponsibilityExample();
						example.createCriteria().andResponsibilityNameLikeInsensitive(searchCriteria)
							.andDateFromLessThanOrEqualTo(DateUtil.truncate(new Date()))
							.andDateToGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
						example.setOrderByClause("UPPER(RESPONSIBILITY_NAME) ASC");
						return responsibilityService.selectByExampleWithRowbounds(example, limit, offset);
					}

					@Override
					public int countBySearchCriteria(String searchCriteria) {
						ResponsibilityExample example = new ResponsibilityExample();
						example.createCriteria().andResponsibilityNameLikeInsensitive(searchCriteria)
							.andDateFromLessThanOrEqualTo(DateUtil.truncate(new Date()))
							.andDateToGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
						return responsibilityService.countResponsibilityByExample(example);
					}

					@Override
					public void mapper(KeyValue keyValue, Responsibility data) {
						keyValue.setKey(data.getId());
						keyValue.setValue(data.getResponsibilityName());
						keyValue.setDescription(data.getResponsibilityName());
					}
				});
				
				commonPopupBandboxResponsibility.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if(keyValue != null) {
							data.setResponsibilityId((long) keyValue.getKey());
							data.setDirectResponsibilityName(keyValue.getValue().toString());
						} else {
							data.setResponsibilityId(null);
							data.setDirectResponsibilityName(null);
						}
					}
				});
				
				KeyValue setKeyValueResponsibility = new KeyValue(data.getId(), data.getDirectResponsibilityName(), data.getDirectResponsibilityName());
				commonPopupBandboxResponsibility.setRawValue(setKeyValueResponsibility);
				commonPopupBandboxResponsibility.setReadonly(true);
				cellResponsibility.appendChild(commonPopupBandboxResponsibility);
				cellResponsibility.setParent(item);
				if(data.getResponsibilityId() != null)
					commonPopupBandboxResponsibility.setDisabled(true);
				
				if(data.getResponsibilityId() != null)
					new DateboxListcell<UserResponsibilityDTO>(data, data.getDateFrom() != null ? data.getDateFrom() : null, "dateFrom", true).setParent(item);			
				else
					new DateboxListcell<UserResponsibilityDTO>(data, data.getDateFrom() != null ? data.getDateFrom() : null, "dateFrom", false).setParent(item);			
				new DateboxListcell<UserResponsibilityDTO>(data, data.getDateTo() != null ? data.getDateTo() : null, "dateTo", false).setParent(item);
				
/*				Listcell cellRemark = new Listcell();
				Textbox txtRemark = new Textbox();
				txtRemark.setValue(data.getRemark() != null ? data.getRemark() : "");
				cellRemark.appendChild(txtRemark);
				cellRemark.setParent(item);*/
				new TextboxListcell<UserResponsibilityDTO>(data, data.getRemark() != null ? data.getRemark() : "", "remark", false, 255, "90%").setParent(item);
			}
		};
	}
	
	private TabularValidationRule<UserResponsibilityDTO> directResponsibilityValidationRule() {
		return new TabularValidationRule<UserResponsibilityDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(UserResponsibilityDTO data, List<String> errorRow) {
				if(data.getResponsibilityId() == null) {
					errorRow.add("Please fill Responsibility");
				}
				if(data.getDateFrom() == null) {
					errorRow.add("Please fill Date From");
				}
				if(dtbDateTo.getValue() != null) {
					if(DateUtil.compareDate(data.getDateFrom(), data.getDateTo())) {
						errorRow.add("Date To less than Date From");
					}
				}
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
		
	//End Direct Responsibility Tabular Entry
	
	//Start Job Responsibility Tabular Entry
	@Listen("onClick=#btnJobRespAddRow")
	public void buildJobResponsibilityList() {
		tbnJobRespListbox.setDataType(UserResponsibilityDTO.class);
		tbnJobRespListbox.setModel(getJobResponsibilityModel());
		tbnJobRespListbox.setItemRenderer(getJobRespItemRenderer());
		tbnJobRespListbox.setValidationRule(jobRespValidationRule());
	}
		
	private ListModelList<UserResponsibilityDTO> getJobResponsibilityModel() {
		if(newJobResponsibilities == null) {
			newJobResponsibilities = new ArrayList<UserResponsibilityDTO>();
			newJobResponsibilities.add(new UserResponsibilityDTO());
		}
		ListModelList<UserResponsibilityDTO> model = new ListModelList<UserResponsibilityDTO>(newJobResponsibilities);
		return model;
	}
	
	private SerializableListItemRenderer<UserResponsibilityDTO> getJobRespItemRenderer() {
		return new SerializableListItemRenderer<UserResponsibilityDTO>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final UserResponsibilityDTO data, int index) throws Exception {
				new Listcell().setParent(item);				
				new TextboxListcell<UserResponsibilityDTO>(data, data.getJobResponsibilityName() != null ? data.getJobResponsibilityName() : null, "jobResponsibilityName", true, 255, "90%").setParent(item);
				new DateboxListcell<UserResponsibilityDTO>(data, data.getDateFrom() != null ? data.getDateFrom() : null, "dateFrom", true).setParent(item);
				new DateboxListcell<UserResponsibilityDTO>(data, data.getDateTo() != null ? data.getDateTo() : null, "dateTo", false).setParent(item);
			}
		};
	}
	
	private TabularValidationRule<UserResponsibilityDTO> jobRespValidationRule() {
		return new TabularValidationRule<UserResponsibilityDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(UserResponsibilityDTO data, List<String> errorRow) {
				if(dtbDateTo.getValue() != null) {
					if(DateUtil.compareDate(data.getDateFrom(), data.getDateTo())) {
						errorRow.add("Date To less than Date From");
					}
				}
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	//End Job Responsibility Tabular Entry
	
	private boolean validate() {
		if(txtUserName.getValue().equalsIgnoreCase("")) {
			return false;
		}
		if(dtbDateFrom.getValue() == null) {
			return false;
		}
		if(dtbDateTo.getValue() != null) {
			if(DateUtil.compareDate(dtbDateFrom.getValue(), dtbDateTo.getValue())) {
				return false;
			}
		}
		return true;
	}

	private void clearMessage(){
		ErrorMessageUtil.clearErrorMessage(txtUserName);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
	}
	
	private void validation() {
		clearMessage();
		if(txtUserName.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtUserName, "Please fill User Name");
		}else if(!validateUsername()){
			ErrorMessageUtil.setErrorMessage(txtUserName, Labels.getLabel("sam.invalidUserName"));
		}
		if(dtbDateFrom.getValue() == null) {
			ErrorMessageUtil.setErrorMessage(dtbDateFrom, "Please fill Date From");
		}
		if(dtbDateTo.getValue() != null && dtbDateFrom.getValue() != null) {
			if(DateUtil.compareDate(dtbDateFrom.getValue(), dtbDateTo.getValue())) {
				ErrorMessageUtil.setErrorMessage(dtbDateTo, Labels.getLabel("common.mustBeGreaterThanOrEqual", new Object[]{Labels.getLabel("sam.dateTo"), Labels.getLabel("sam.dateFrom")}));
			}
		}
		if(userDto.getId() == null){
			UserExample example = new UserExample();
			example.createCriteria().andUserNameEqualTo(txtUserName.getValue());
			int result = userService.countUserByExample(example);
			if(result > 0){
				ErrorMessageUtil.setErrorMessage(txtUserName, "Username must be unique");
			}
		}
	} 
	
	private boolean validationTabularDirectResponsibility() {
		int i, j;
		for(i = 0; i < tbnDirectRespListbox.getItemCount(); i++) {
			if(tbnDirectRespListbox.getValue().get(i).getRemark() != null) {
				if(tbnDirectRespListbox.getValue().get(i).getRemark().length() > 255) {
					lblErrorDirectResponsibility.setValue("Remark at row " + (i+1) + " more than 255 character");		
					return false;
				}
			}
			
			for(j = 0; j < tbnDirectRespListbox.getItemCount(); j++) {
				if(i == j) {
					continue;
				}
				if(tbnDirectRespListbox.getValue().get(i).getResponsibilityId().equals(tbnDirectRespListbox.getValue().get(j).getResponsibilityId())) {
					lblErrorDirectResponsibility.setValue("Direct responsibility name can't double at row " + (i+1) + " and row " + (j+1));					
					return false;
				}
			}
		}
		return true;
	}
		
	private boolean validationTabular() {
		int i, j;
		for(i = 0; i < tbnDirectRespListbox.getItemCount(); i++) {
			if(tbnDirectRespListbox.getValue().get(i).getResponsibilityId() == null) {
				return true;
			}
			
			for(j = 0; j < tbnJobRespListbox.getItemCount(); j++) {
				if(tbnJobRespListbox.getValue().get(j).getResponsibilityId() == null) {
					return true;
				}
				
				if(tbnDirectRespListbox.getValue().get(i).getResponsibilityId().equals(tbnJobRespListbox.getValue().get(j).getResponsibilityId())) {
					lblErrorDirectResponsibility.setValue("There are same responsibility between Direct responsibility at row " + (i+1) + " and job responsibility at row " + (j+1) + ". Please Remove it");
					lblErrorJobResponsibility.setValue("There are same responsibility between Direct responsibility at row " + (i+1) + " and job responsibility at row " + (j+1) + ". Please Remove it");			
					return false;
				}
			}
		}
		return true;
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		lblErrorDirectResponsibility.setValue("");
		lblErrorJobResponsibility.setValue("");
		ErrorMessageUtil.clearErrorMessage(txtUserName);
		ErrorMessageUtil.clearErrorMessage(dtbDateFrom);
		ErrorMessageUtil.clearErrorMessage(dtbDateTo);
			if(userDto == null) {
				userDto = new UserDTO();
			}
		
		if(bnbEmployee.getKeyValue() != null)
			userDto.setPersonId((Long) bnbEmployee.getKeyValue().getKey());
			
		
		userDto.setUserName(txtUserName.getValue());
			//userDto.setPersonId(userDto.getPersonId());			
			userDto.setDateFrom(dtbDateFrom.getValue());
			userDto.setDateTo(dtbDateTo.getValue());
			if(selectedUser == null) {
				userDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				userDto.setCreationDate(new Date());
			} else {
				userDto.setCreatedBy(selectedUser.getCreatedBy());
				userDto.setCreationDate(selectedUser.getCreationDate());
			}
			userDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			userDto.setLastUpdateDate(new Date());
			userDto.setDirectResponsibilityDto(tbnDirectRespListbox.getValue());
			userDto.setJobResponsibilityDto(tbnJobRespListbox.getValue());
			
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {	
					int status = (int) event.getData();
					if(status == 16) {
						
						if(validateTabular()){
							if(validateUsername() || userDto.getId() != null){
								try {
									userService.save(userDto);
									Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
									Map<String, Object> param = new HashMap<String, Object>();
									param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
									Executions.createComponents("~./hcms/systemadmin/user_inquiry.zul", getSelf().getParent(), param);
									getSelf().detach();
								} catch (Exception e) {
									log.error(e.getMessage());
									Messagebox.show(Labels.getLabel("notif.accessDataError"), "ERROR", Messagebox.OK, Messagebox.ERROR);
								}
							
							}else
								ErrorMessageUtil.setErrorMessage(txtUserName, Labels.getLabel("sam.invalidUserName"));
						}
						
						
					} else {
						return;
					}
				}
			});

	}
	
	private boolean validateTabular(){
		boolean status = true;
		if(!validate()){
			status = false;
			validation();
		}
			
		if(!tbnDirectRespListbox.validate()) {
			status = false;
		}
		if(!tbnJobRespListbox.validate()) {
			status = false;
		}
		if(!validationTabular()) {
			status = false;
		}
		if(!validationTabularDirectResponsibility()) {
			status = false;
		}
		return status;
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("sam.cancelMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/systemadmin/user_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
}

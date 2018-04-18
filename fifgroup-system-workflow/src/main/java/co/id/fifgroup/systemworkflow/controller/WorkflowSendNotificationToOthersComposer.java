package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.personneladmin.constant.PeopleType;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.domain.Employee;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowSendNotificationToOthersComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(WorkflowSendNotificationToOthersComposer.class);
	
	private Map<String, Object> params = new HashMap<String, Object>();
	private WorkflowSendNotificationToOthersComposer thisComposer = this;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire
	private TabularEntry<PersonDTO> lbxEmployee;
	@Wire
	private Checkbox chkSendToApprovalPath;
	
	private AVMApplicationData applicationData;
	
	ListModelList<PersonDTO> model;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
		populateData();
	}
	
	public void initComponent() {
		buildEmployeeTabular();
	}
	
	public void populateData() {
		applicationData = (AVMApplicationData) arg.get("applicationData");
	}
	
	public void buildEmployeeTabular() {
		lbxEmployee.setDataType(PersonDTO.class);
		lbxEmployee.setModel(getEmployee());
		lbxEmployee.setItemRenderer(getListItemRenderer());
		lbxEmployee.setValidationRule(validationRule());
	}
	
	private ListModelList<PersonDTO> getEmployee(){
		List<PersonDTO> listEmployee = new ArrayList<PersonDTO>();
		model = new ListModelList<PersonDTO>(listEmployee);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<PersonDTO> getListItemRenderer() {
		return new SerializableListItemRenderer<PersonDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final PersonDTO data, int index) throws Exception {
				new Listcell("").setParent(item);
				Listcell cell = new Listcell();
				CommonPopupDoubleBandbox bandbox = new CommonPopupDoubleBandbox();
				bandbox.setConcatValueDescription(true);
				bandbox.setTitle(Labels.getLabel("pea.employee"));
				bandbox.setSearchText1(Labels.getLabel("pea.employeeNumber"));
				bandbox.setSearchText2(Labels.getLabel("pea.employeeName"));
				bandbox.setHeaderLabel1(Labels.getLabel("pea.employeeNumber"));
				bandbox.setHeaderLabel2(Labels.getLabel("pea.employeeName"));
				bandbox.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<PersonDTO>() {
				
					private static final long serialVersionUID = 1L;

					@Override
					public List<PersonDTO> findBySearchCriteria(String searchCriteria1, String searchCriteria2, int limit, int offset) {
						PersonCriteriaDTO criteria = new PersonCriteriaDTO();
						criteria.setPeopleType(PeopleType.EMPLOYEE.name());
						criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
						criteria.setEmployeeNumber(searchCriteria1);
						criteria.setFullName(searchCriteria2);
						return personService.getPersonByCompany(criteria, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId(), offset, limit);
					}

					@Override
					public int countBySearchCriteria(String searchCriteria1, String searchCriteria2) {
						PersonCriteriaDTO criteria = new PersonCriteriaDTO();
						criteria.setPeopleType(PeopleType.EMPLOYEE.name());
						criteria.setEffectiveOnDate(DateUtil.truncate(new Date()));
						criteria.setEmployeeNumber(searchCriteria1);
						criteria.setFullName(searchCriteria2);
						return personService.countPersonByCompany(criteria, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					}

					@Override
					public void mapper(KeyValue keyValue, PersonDTO data) {
						keyValue.setKey(data.getPersonUUID());
						keyValue.setValue(data.getEmployeeNumber());
						keyValue.setDescription(data.getFullName());
					}
				});
				bandbox.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if (keyValue != null) {
							data.setPersonUUID((UUID) keyValue.getKey());
							data.setFullName(keyValue.getDescription().toString());							
						} else {
							data.setPersonUUID(null);
							data.setFullName(null);
						}
					}
				});
				bandbox.setParent(cell);
				cell.setParent(item);
			}
		};
	}
	
	private TabularValidationRule<PersonDTO> validationRule() {
		return new TabularValidationRule<PersonDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(PersonDTO data, List<String> errorRow) {
				if(data == null || data.getPersonUUID() == null)
					errorRow.add(Labels.getLabel("swf.err.employeeCantBeEmpty"));
								
				if(errorRow.size() > 0)	{
					return false;
				}
				return true;
			}			
		};
	}
	
	@Listen("onClick = button#btnAddRow")
	public void addRow() {
		lbxEmployee.addRow();
	}
	
	@Listen("onClick = button#btnDeleteRow")
	public void deleteRow() {
		lbxEmployee.deleteRow();
	}
	
	@Listen("onClick = button#btnSend")
	public void sendMessage() {		
		try {
			if (lbxEmployee.validate()) {				
				List<PersonDTO> listPerson = lbxEmployee.getValue();
				if (chkSendToApprovalPath.isChecked()) {
					List<AVMApprovalHistory> approvalHistories = avmAdapterServiceImpl.getApprovalHistoryByAVMTrxId(applicationData.getAvmTrxId());
					for (AVMApprovalHistory approvalHistory : approvalHistories) {
						PersonDTO person = new PersonDTO();
						person.setPersonUUID(approvalHistory.getApproverId());
						listPerson.add(person);
					}
				}
				if (listPerson.size() > 0) {
					List<Employee> listEmployees = getEmployees(listPerson);
					avmAdapterServiceImpl.sendMultipleFYIMessage(listEmployees, ActionType.APPROVE.getCode(), applicationData);				
				}
				closeWindow();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	private List<Employee> getEmployees(List<PersonDTO> listEmployee) {
		List<Employee> list = new ArrayList<Employee>();
		for (PersonDTO person : listEmployee) {
			Employee employee = new Employee();
			employee.setEmployeeUUID(person.getPersonUUID());
			list.add(employee);
		}
		return list;
	}
	
	@Listen("onClick = button#btnClose")
	public void closeWindow() {
		Messagebox.show(Labels.getLabel("swf.approveSuccessfully"));
		getSelf().onClose();
	}
}

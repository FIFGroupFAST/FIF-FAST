package co.id.fifgroup.systemadmin.controller;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LocationSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.domain.PrinterExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.PrinterService;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.constant.OutputFileFormat;
import co.id.fifgroup.systemadmin.constant.TaskAfterCompleted;
import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.TaskParameterValue;
import co.id.fifgroup.systemadmin.domain.TaskRequest;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDetailDTO;
import co.id.fifgroup.systemadmin.service.TaskRequestService;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;
import co.id.fifgroup.systemadmin.validation.TaskRequestValidator;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskRequestDetailComposer extends SelectorComposer<Window>{
	
	private static Logger log = LoggerFactory.getLogger(TaskRequestDetailComposer.class);

	private static final long serialVersionUID = 1L;
	private TaskRequestDetailComposer thisComposer = this;
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskRequestService taskRequestService;
	@WireVariable(rewireOnActivate = true)
	private transient TaskRunnerService taskRunnerService;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient PrinterService printerServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient JobSetupService jobSetupServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private CommonPopupBandbox txtTaskRunnerName;
	@Wire
	private Textbox txtRunTheTask;
	@Wire
	private Button btnSchedule;
	@Wire
	private Bandbox txtTaskParameter;
	@Wire
	private Button btnSubmit;
	@Wire
	private Button btnCancel;
	@Wire
	private Listbox formatListbox;
	@Wire
	private Radiogroup afterComplted;
	@Wire
	private CommonPopupBandbox bdnPrinter;
	
	private List<TaskParameterValue> taskParameterValues = null;
	private List<String> taskParameterDescription = null;

	private TaskRequestDTO taskRequestDto;
	private boolean setDisabled = false;
	private TaskRunner editTaskRunner;
	private Long requestId;
	private TaskRequestDTO selectedTaskRequest;
	private FunctionPermission functionPermission;
	private int valueIndex;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameter();
		buildTaskRunnerPopup();
		buildOutputFileFormatListbox();
		buildPrinterPopup();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
	}
	
	private void loadParameter() {
		selectedTaskRequest = (TaskRequestDTO) arg.get("taskRequestDto");
		taskParameterDescription = new ArrayList<>();
		if(arg.containsKey("setDisabled")) {
			setDisabled = (boolean) arg.get("setDisabled");
			editTaskRunner = selectedTaskRequest.getTaskRunner();
			requestId = selectedTaskRequest.getId();
			TaskRunnerExample example = new TaskRunnerExample();
			example.createCriteria().andTaskRunnerNameEqualTo(editTaskRunner.getTaskRunnerName());
			editTaskRunner = taskRunnerService.getTaskRunnerByExample(example).get(0);
			
			btnSubmit.setVisible(!setDisabled);
			btnCancel.setLabel(Labels.getLabel("common.back"));
			txtTaskParameter.setReadonly(true);
			
 		}
		if(selectedTaskRequest != null) {
			taskRequestDto = selectedTaskRequest; 
			txtTaskRunnerName.setDisabled(true);
			txtTaskRunnerName.setValue(selectedTaskRequest.getTaskRunner().getTaskRunnerName());
			
			TaskRequest task = taskRequestService.getTaskRequestByTaskRequestId(taskRequestDto.getId());
			txtRunTheTask.setDisabled(true);
			txtRunTheTask.setValue(task.getScheduleType());
			
			if(selectedTaskRequest.getAfterCompleted() != null)
				afterComplted.setSelectedIndex(TaskAfterCompleted.getKey(selectedTaskRequest.getAfterCompleted()));
			
			if(selectedTaskRequest.getPrinterId() != null){
				PrinterExample example = new PrinterExample();
				example.createCriteria().andPrinterIdEqualTo(selectedTaskRequest.getPrinterId());
				bdnPrinter.setValue(printerServiceImpl.getPrinterByExample(example).get(0).getPrinterName());
			}
			
		}
		if(selectedTaskRequest!=null){
			showParameter(selectedTaskRequest);
		}
	}
	
	private void showParameter(TaskRequestDTO selectedTaskRequest) {
		taskParameterValues = taskRequestService.getTaskParameterValuesByTaskRunnerId(selectedTaskRequest.getId());
		List<TaskParameterDTO>listOfParams = taskRequestService.getTaskParameterByTaskId(selectedTaskRequest.getTaskId());

		StringBuilder sb = new StringBuilder();
		for(TaskParameterDTO param : listOfParams){
			sb.append(getValue(param));
			sb.append(";");
		}
		txtTaskParameter.setValue(sb.toString());
	}

	private String getValue(final TaskParameterDTO param) {
		if(param.getDataType().trim().equals(TaskDataType.NUMERIC.toString())) {
			return getComponentValue(param);
		} else if(param.getDataType().trim().equals(TaskDataType.TEXT.toString())) {
			Object value = getComponentValue(param);
			if(value != null ){
				if(!value.equals(""))
					return (String)value;
			}
		} 
		else if(param.getDataType().trim().equals(TaskDataType.DATE.toString())) {
			Object value = getComponentValue(param);
			if(value != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
				return (String) value;
			}
		}else if(param.getDataType().trim().equals(TaskDataType.LOOKUP.toString())) {
			if(!param.isStaticParam()){
				Object value = getComponentValue(param);
				if (value != null) {
					return value.toString();
				}
			}else{
				if(param.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH.toString())){
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
							if (org == null && value.toString().equalsIgnoreCase("-1")) {
								org = organizationSetupServiceImpl.getHeadOffice();
							}
							return org.getName();
						}
					}
					
				}else if(param.getKey().equals(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString())){
					
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()));
							return org.getName();
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.STATIC_PARAM_EMPLOYEE.toString())){
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());
							criteria.setEmployeeNumber(value.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompany(criteria, securityServiceImpl.getSecurityProfile().getCompanyId());
							if(!persons.isEmpty()){
								return persons.get(0).getFullName();
							}
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH_COMMON.toString())){
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
							if (org == null && value.toString().equalsIgnoreCase("-1")) {
								org = organizationSetupServiceImpl.getHeadOffice();
							}
							return org.getName();
						}
					}
					
				}
				
				/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				else if(param.getKey().equals(StaticParameterKey.STATIC_PARAM_EMPLOYEE_COMMON.toString())){
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							/*criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());*/
							criteria.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
							criteria.setEmployeeNumber(value.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompanyInquiry(criteria);
							if(!persons.isEmpty()){
								return persons.get(0).getFullName();
							}
						}
					}
				}
				/*End Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				/*Add new behaviour predefined parameter */
				else if(param.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString())){
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							Company company = companyServiceImpl.getCompanyByIdAndEffectiveDate(Long.valueOf(value.toString()), DateUtil.truncate(new Date()));
							if (company != null) {
								return company.getCompanyName();
							}
							
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString())){
					
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
							if (org == null && value.toString().equalsIgnoreCase("-1")) {
								org = organizationSetupServiceImpl.getHeadOffice();
							}
							return org.getName();
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString())){
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							Location location = locationSetupServiceImpl.findByPrimaryKey(Long.valueOf(value.toString()));
							if(location != null){
								return location.getLocationName();
							}
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString())){
					
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()));
							return org.getName();
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString())){
					
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());
							criteria.setEmployeeNumber(value.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompanyInquiry(criteria);
							if(!persons.isEmpty()){
								return persons.get(0).getFullName();
							}
						}
					}
				}else if(param.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_JOB.toString())){
					
					Object value = getComponentValue(param);
					if(value != null){
						if(!value.equals("")){
							Job job = jobSetupServiceImpl.findByPrimaryKey(Long.valueOf(value.toString()));
							if(job != null){
								return job.getJobName();
							}
						}
					}
				}
			}
			}else if(param.getDataType().trim().equals(TaskDataType.BOOLEAN.toString())) {
				Object value = getComponentValue(param);
				if(value != null)
					return value.toString().equalsIgnoreCase("1") ? "true" : value.toString().equalsIgnoreCase("true") ? "true" : "false";
			}	
			return null;
		}

	private String getComponentValue(TaskParameterDTO param) {
		/*Object value = taskParameterValues.get(valueIndex++).getValue();
		if(value!=null){
			return value.toString();
		}else{
			return null;
		}*/
		for (TaskParameterValue taskParameterValue : taskParameterValues) {
			if(taskParameterValue.getTaskParameterId().equals(param.getId())){
				Object value = taskParameterValue.getValue();
				if(value!=null){
					return value.toString();
				}else{
					return null;
				}
			}
		}
		return null;
	}

	private void buildOutputFileFormatListbox() {
		ListModelList<OutputFileFormat> model = new ListModelList<OutputFileFormat>(OutputFileFormat.values());
		formatListbox.setModel(model);
		formatListbox.renderAll();
		formatListbox.setSelectedIndex(0);
		if(selectedTaskRequest != null && selectedTaskRequest.getOutputFormat() != null){
			int idx = 0;
			for (OutputFileFormat outputFileFormat : model) {
				if(outputFileFormat.toString().equals(selectedTaskRequest.getOutputFormat()))
					formatListbox.setSelectedIndex(idx);
				idx++;
			}
		}
	}
	
	private void buildPrinterPopup(){
		SerializableSearchDelegate<Printer> printerSearchDelegate = new SerializableSearchDelegate<Printer>() {

			

			private static final long serialVersionUID = 1L;

			@Override
			public List<Printer> findBySearchCriteria(String searchCriteria,
					int limit, int offset) {
				PrinterExample example = new PrinterExample();
				example.createCriteria().andPrinterNameLikeInsensitive(searchCriteria);
				return printerServiceImpl.getPrinterByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				PrinterExample example = new PrinterExample();
				example.createCriteria().andPrinterNameLikeInsensitive(searchCriteria);
				return printerServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Printer data) {
				keyValue.setKey(data.getPrinterId());
				keyValue.setValue(data.getPrinterName());
				keyValue.setDescription(data.getPrinterName());
			}
		};
		bdnPrinter.setSearchText("Printer Name");
		bdnPrinter.setTitle("List of Printer");
		bdnPrinter.setSearchDelegate(printerSearchDelegate);
	}
	
	private void buildTaskRunnerPopup() {
		
		SerializableSearchDelegate<TaskRunnerDTO> taskRunnerSearchDelegate = new SerializableSearchDelegate<TaskRunnerDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<TaskRunnerDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				Long authorizedTaskGroupId = securityServiceImpl.getSecurityProfile().getSecurity().getTaskGroupId();
				String responsibilityName = securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName();
				TaskRunner example = new TaskRunner();
				example.setTaskRunnerName(searchCriteria);
				/*if(responsibilityName.equalsIgnoreCase("System Administration") || 
						responsibilityName.equalsIgnoreCase("System Workflow")){
					return taskRunnerService.getTaskRunnerDtoByExample(example, limit, offset, null);
				}else{
					return taskRunnerService.getTaskRunnerDtoByExample(example, limit, offset, null == authorizedTaskGroupId ? 0L : authorizedTaskGroupId);

				}*/
				return taskRunnerService.getTaskRunnerDtoByExample(example, limit, offset, null);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				Long authorizedTaskGroupId = securityServiceImpl.getSecurityProfile().getSecurity().getTaskGroupId();
				String responsibilityName = securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName();
				TaskRunner example = new TaskRunner();
				example.setTaskRunnerName(searchCriteria);
				if(responsibilityName.equalsIgnoreCase("System Administration") || 
						responsibilityName.equalsIgnoreCase("System Workflow")){
					return taskRunnerService.countByExample(example, null);
				}else{
					return taskRunnerService.countByExample(example, null == authorizedTaskGroupId ? 0L : authorizedTaskGroupId);
				}
			}

			@Override
			public void mapper(KeyValue keyValue, TaskRunnerDTO data) {
				keyValue.setKey(data);
				keyValue.setValue(data.getTaskRunnerName());
				keyValue.setDescription(data.getTaskRunnerName());
			}
		};
		txtTaskRunnerName.setTitle("List of Task Runner");
		txtTaskRunnerName.setSearchText("Task Runner Name");
		txtTaskRunnerName.setHeaderLabel("Task Runner");
		txtTaskRunnerName.setSearchDelegate(taskRunnerSearchDelegate);
	}
	
	@Listen("onClose=bandbox#txtTaskRunnerName")
	public void onCloseTaskRunnerNameBandbox(Event event) {
		KeyValue keyValue = (KeyValue) event.getData();
		if (keyValue != null){
			if(taskRequestService.isRequiredEntryTaskParameter((TaskRunner) keyValue.getKey()))
				showTaskParameterPopup(true);
			else
				txtTaskParameter.setDisabled(false);
			
			//Get default output Report if any
			
			TaskRunner selectedTaskRunner = (TaskRunner) keyValue.getKey();
			TaskRunnerDTO taskRunerDetail = taskRunnerService.getTaskRunnerById(selectedTaskRunner.getId());
			for (TaskRunnerDetailDTO taskRunnerDetailDTO : taskRunerDetail.getTaskRunnerDetailDto()) {
				if(taskRunnerDetailDTO.getMainTask().getTaskType().equals(TaskType.REPORT.toString())){ // If has Report
					
					//Set default value for Output Format File
					if(taskRunnerDetailDTO.getMainTask().getOutputFormat() != null){
						int idx = 0;
						for (OutputFileFormat outputFileFormat : OutputFileFormat.values()) {
							if(outputFileFormat.toString().equals(taskRunnerDetailDTO.getMainTask().getOutputFormat()))
								formatListbox.setSelectedIndex(idx);
							idx++;
						}
					}
					
					//Set Default value for action after completed
					if(taskRunnerDetailDTO.getMainTask().getAfterCompleted() != null)
						afterComplted.setSelectedIndex(TaskAfterCompleted.getKey(taskRunnerDetailDTO.getMainTask().getAfterCompleted()));
					
					
					//Set Default value for Printer
					if(taskRunnerDetailDTO.getMainTask().getPrinterId() != null){
						PrinterExample example = new PrinterExample();
						example.createCriteria().andPrinterIdEqualTo(taskRunnerDetailDTO.getMainTask().getPrinterId());
						bdnPrinter.setValue(printerServiceImpl.getPrinterByExample(example).get(0).getPrinterName());
					}
					
					break;
				}
			}
			
		}
	}
	
	@Listen("onOpen=bandbox#txtTaskParameter")
	public void onOpenTaskParameterBandbox() {
		if(isNullOrEmpty(txtTaskRunnerName.getValue())) {
			Messagebox.show("Please select Task Runner Name first.");
		} else {
			showTaskParameterPopup(false);
		}
	}
	
	private void showTaskParameterPopup(boolean refreshList) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(setDisabled) {
			param.put("requestId", requestId);	
			param.put("taskRunner", editTaskRunner);
		} else {
			KeyValue keyValue = txtTaskRunnerName.getKeyValue();
			param.put("taskRunner", keyValue.getKey());
		}
		param.put("taskParameterValues", taskParameterValues);
		param.put("refreshList", refreshList);
		param.put("composer", thisComposer);
		param.putAll(arg);
		if(setDisabled == true) {
			param.put("setDisabled", setDisabled);
		}
		Window win = (Window) Executions.createComponents("~./hcms/systemadmin/entry_task_parameter.zul", null, param);
		win.doModal();
	}
	
	public void setTaskParameterValues(List<TaskParameterValue> taskParameterValues){
		this.taskParameterValues = taskParameterValues;
	}
	
	@Listen("onClick=button#btnSchedule")
	public void buildSchedulePopup() {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("taskSchedule", taskRequestDto);
			param.put("composer", thisComposer);
			if(setDisabled==true){
				param.put("setDisabled", setDisabled);
			}
			param.putAll(arg);
			Window com = (Window) Executions.createComponents("~./hcms/systemadmin/task_schedule.zul", null, param);
			com.doModal();
	}
	
	public void setTaskSchedule(TaskRequestDTO taskRequestDto) {
		this.taskRequestDto = taskRequestDto;
		txtRunTheTask.setValue(taskRequestDto.getScheduleType());
	}
	
	@Listen("onClick=button#btnSubmit")
	public void onSubmit() {
		if(taskRequestDto == null)
			taskRequestDto = new TaskRequestDTO();
		
		KeyValue keyValue = txtTaskRunnerName.getKeyValue();
		if(keyValue != null){
			TaskRunner taskRunner = (TaskRunner) keyValue.getKey();
			taskRequestDto.setTaskRunnerId(taskRunner.getId());
		}
		if(taskParameterValues != null && taskParameterValues.size() != 0)
			taskRequestDto.setTaskParameterValues(taskParameterValues);
		
		if(formatListbox.getSelectedItem() != null) {
			OutputFileFormat val = formatListbox.getSelectedItem().getValue();
			if(val.equals(OutputFileFormat.Select))
				taskRequestDto.setOutputFormat(null);
			else
				taskRequestDto.setOutputFormat(val.toString());
		}
		
		taskRequestDto.setAfterCompleted(TaskAfterCompleted.getValue(afterComplted.getSelectedIndex()));
		
		if(bdnPrinter.getKeyValue() != null) {
			KeyValue kv = bdnPrinter.getKeyValue();
			taskRequestDto.setPrinterId((Long) kv.getKey());
		}
		
		clearErrorMessage();
		TaskRequestValidator validator = new TaskRequestValidator();
		try {
			validator.validate(taskRequestDto);
			
			Messagebox.show(Labels.getLabel("common.confirmationSubmit"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if(status == 16) {					
						taskRequestService.save(taskRequestDto);
						Messagebox.show(Labels.getLabel("common.submitSuccessfully"), "Information", Messagebox.YES, Messagebox.INFORMATION);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
						param.putAll(arg);
						Executions.createComponents("~./hcms/systemadmin/task_request_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();					
					} else {
						return;
					}
				}
			});
		} catch (ValidationException e) {
			showErrorMessage(e);
			log.error(e.getMessage());
		}
	}
	
	private void showErrorMessage(ValidationException vex) {
		ErrorMessageUtil.setErrorMessage(txtTaskRunnerName, vex.getConstraintViolations().get(TaskRequestValidator.TASK_RUNNER_NAME_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(btnSchedule, vex.getConstraintViolations().get(TaskRequestValidator.TASK_SCHEDULE_NOT_EMPTY));
		ErrorMessageUtil.setErrorMessage(txtTaskParameter, vex.getConstraintViolations().get(TaskRequestValidator.TASK_PARAMETER_NOT_EMPTY));
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtTaskRunnerName);
		ErrorMessageUtil.clearErrorMessage(btnSchedule);
		ErrorMessageUtil.clearErrorMessage(txtTaskParameter);
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		if(setDisabled) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
			param.putAll(arg);
			Executions.createComponents("~./hcms/systemadmin/task_request_inquiry.zul", getSelf().getParent(), param);
			getSelf().detach();
		} else {
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
						param.putAll(arg);
						Executions.createComponents("~./hcms/systemadmin/task_request_inquiry.zul", getSelf().getParent(), param);
						getSelf().detach();
					} else {
						return;
					}
				}
			});
		}
	}

	public void setTaskParameterDescription(
			List<String> taskParameterDescription) {
		this.taskParameterDescription = taskParameterDescription;
		String taskDesc = "";
		for(String desc : this.taskParameterDescription){
			taskDesc+=desc+";";
		}
		txtTaskParameter.setValue(taskDesc);
	}
}

package co.id.fifgroup.systemadmin.controller;

import java.util.ArrayList;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;

import co.id.fifgroup.audit.AuditPerformer;
import co.id.fifgroup.audit.TrxType;
import co.id.fifgroup.audit.ActionType.Action;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.objectcopy.DeepCopy;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.systemadmin.domain.TaskGroup;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.TaskGroupDTO;
import co.id.fifgroup.systemadmin.dto.TaskGroupDetailDTO;
import co.id.fifgroup.systemadmin.service.TaskGroupService;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskGroupDetailComposer extends SelectorComposer<Window> {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TaskGroupDetailComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient TaskRunnerService taskRunnerService;
	@WireVariable(rewireOnActivate = true)
	private transient TaskGroupService taskGroupServiceImpl;	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AuditPerformer auditPerformer;
	
	@Wire
	private Textbox txtGroupName;
	@Wire
	private Textbox txtDescription;
	@Wire
	private TabularEntry<TaskGroupDetailDTO> tbnTask;
	@Wire
	private Label lblErrorTaskGroup;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDeleteRow;
	
	private TaskGroup taskGroup;
	private TaskGroup selectedTaskGroup;
	private List<TaskGroupDetailDTO> taskGroupDetailList;
	private TaskGroupDTO taskGroupDto;
	private FunctionPermission functionPermission;
	private TaskGroupDTO previousObject;
	private Control control;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadParameter();
		buildTaskGroupDetail();

		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtGroupName.setDisabled(true);
			txtDescription.setDisabled(true);
			btnAddRow.setDisabled(true);
			btnDeleteRow.setDisabled(true);
		}
	}
	
	private void loadParameter() {
		selectedTaskGroup = (TaskGroup) arg.get("taskGroup");
		if(selectedTaskGroup != null) {
			taskGroup = selectedTaskGroup;
			TaskGroupDTO taskGroupDTO = new TaskGroupDTO();
			taskGroupDTO.setId(selectedTaskGroup.getId());
			taskGroupDTO.setTaskGroupName(selectedTaskGroup.getTaskGroupName());
			taskGroupDTO.setDescription(selectedTaskGroup.getDescription());
			txtGroupName.setDisabled(true);
			txtGroupName.setValue(selectedTaskGroup.getTaskGroupName());
			txtDescription.setValue(selectedTaskGroup.getDescription());
			
			taskGroupDetailList = taskGroupServiceImpl.getTaskGroupIdAndRunnerName(selectedTaskGroup.getId());
			taskGroupDTO.setTaskGroupDetailDto(taskGroupDetailList);
			previousObject = (TaskGroupDTO) DeepCopy.copy(taskGroupDTO);
		}
	}
	
	private void buildTaskGroupDetail() {
		tbnTask.setDataType(TaskGroupDetailDTO.class);
		tbnTask.setModel(getTaskGroupModel());
		tbnTask.setItemRenderer(getListItemRenderer());
		tbnTask.setValidationRule(taskGroupValidationRule());
	}
	
	private TabularValidationRule<TaskGroupDetailDTO> taskGroupValidationRule() {
		return new TabularValidationRule<TaskGroupDetailDTO>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean validate(TaskGroupDetailDTO data, List<String> errorRow) {
				if(data.getTaskRunnerId() == null) {
					errorRow.add("Please fill Task Runner");
				}
				if(errorRow.size() > 0)	
					return false;
				return true;
			}			
		};
	}
	
	private ListModelList<TaskGroupDetailDTO> getTaskGroupModel() {
		if(taskGroupDetailList == null) {
			taskGroupDetailList = new ArrayList<TaskGroupDetailDTO>();
			taskGroupDetailList.add(new TaskGroupDetailDTO());
		}
		
		ListModelList<TaskGroupDetailDTO> model = new ListModelList<TaskGroupDetailDTO>(taskGroupDetailList);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<TaskGroupDetailDTO> getListItemRenderer() {
		return new SerializableListItemRenderer<TaskGroupDetailDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final TaskGroupDetailDTO data, int index) throws Exception {
				new Listcell("").setParent(item);
				
				Listcell typeCellTaskGroupDetail = new Listcell();
				CommonPopupBandbox commonPopupBandboxTaskGroupDetail = new CommonPopupBandbox();
				commonPopupBandboxTaskGroupDetail.setWidth("50%");
				commonPopupBandboxTaskGroupDetail.setTitle("List of Task Runner");
				commonPopupBandboxTaskGroupDetail.setSearchText("Task Runner Name");
				commonPopupBandboxTaskGroupDetail.setHeaderLabel("Task Runner");
				commonPopupBandboxTaskGroupDetail.setSearchDelegate(new SerializableSearchDelegate<TaskRunner>() {

					private static final long serialVersionUID = 1L;
	
					@Override
					public List<TaskRunner> findBySearchCriteria(String searchCriteria, int limit, int offset) {
						TaskRunnerExample taskRunnerExample = new TaskRunnerExample();
						taskRunnerExample.createCriteria().andTaskRunnerNameLikeInsensitive(searchCriteria);
						taskRunnerExample.setOrderByClause("TASK_RUNNER_NAME ASC");
								
						return taskRunnerService.getTaskRunnerByExample(taskRunnerExample, limit, offset);
					}
	
					@Override
					public int countBySearchCriteria(String searchCriteria) {
						TaskRunnerExample taskRunnerExample = new TaskRunnerExample();
						taskRunnerExample.createCriteria().andTaskRunnerNameLikeInsensitive(searchCriteria);
						return taskRunnerService.countTaskRunnerByExample(taskRunnerExample, null);
					}
	
					@Override
					public void mapper(KeyValue keyValue, TaskRunner data) {
						keyValue.setKey(data.getId());
						keyValue.setValue(data.getTaskRunnerName());
						keyValue.setDescription(data.getTaskRunnerName());
					}
				});
			
				commonPopupBandboxTaskGroupDetail.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
	
					private static final long serialVersionUID = 1L;
	
					@Override
					public void onEvent(Event event) throws Exception {
						KeyValue keyValue = (KeyValue) event.getData();
						if(keyValue != null) {
							data.setTaskRunnerId((long) keyValue.getKey());
							data.setTaskRunnerName(keyValue.getValue().toString());
						} else {
							data.setTaskRunnerId(null);
							data.setTaskRunnerName(null);
						}
						
					}
				});

				KeyValue setKeyValueTaskGroupDetail = new KeyValue(data.getTaskRunnerId(), data.getTaskRunnerName(), data.getTaskRunnerName());
				commonPopupBandboxTaskGroupDetail.setRawValue(setKeyValueTaskGroupDetail);
				commonPopupBandboxTaskGroupDetail.setReadonly(true);
				typeCellTaskGroupDetail.appendChild(commonPopupBandboxTaskGroupDetail);
				typeCellTaskGroupDetail.setParent(item);
			}
		};
	}
	
	@Listen ("onClick=#btnAddRow")
	public void addRow() {
		tbnTask.addRow();
	}
	
	@Listen ("onClick=#btnDeleteRow")
	public void deleteRow() {
		lblErrorTaskGroup.setValue("");
		Set<Listitem> itemToBeDeleted = tbnTask.getSelectedItems();
		for (Listitem listitem : itemToBeDeleted) {
			try {
				taskGroupDetailList.get(listitem.getIndex());
				listitem.setSelected(false);
				lblErrorTaskGroup.setValue("The existing data cannot be deleted");
				return;
			} catch (Exception e) {
				log.debug("The existing data cannot be deleted");
			}
		}
		
		tbnTask.deleteRow();
	}
	
	private boolean validationTabularTaskGroup() {
		int i, j;
		if(tbnTask.getItemCount() <= 0) {
			lblErrorTaskGroup.setValue("Task Runner name must selected minimal 1 item");					
			return false;
		}
		List<TaskGroupDetailDTO> list = tbnTask.getValue();
		for(i = 0; i < tbnTask.getItemCount(); i++) {
			for(j = 0; j < tbnTask.getItemCount(); j++) {
				if(i == j) {
					continue;
				}
				if(list.get(i).getTaskRunnerId().equals(list.get(j).getTaskRunnerId())) {
					lblErrorTaskGroup.setValue("Task Runner cannot be duplicated at row " + (i+1) + " and row " + (j+1));					
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean validate() {
		if(txtGroupName.getValue().equalsIgnoreCase("")) {
			return false;
		}
		
		return true;
	}
	
	private void validation() {
		ErrorMessageUtil.clearErrorMessage(txtGroupName);
		if(txtGroupName.getValue().equalsIgnoreCase("")) {
			ErrorMessageUtil.setErrorMessage(txtGroupName, "Please fill Group Name");
		}
	}
	
	@Listen ("onClick = button#btnSave")
	public void save() {
		lblErrorTaskGroup.setValue("");
		if(validate() == false) {
			validation();
			return;
		} else {
			validation();
		}
		
		if(tbnTask.validate()) {
			if(taskGroupDto == null) {
				taskGroupDto = new TaskGroupDTO();
				control = Control.CREATE;
			}else
				control = Control.UPDATE;

			if(taskGroup != null) {
				taskGroupDto.setId(taskGroup.getId());
			}
			
			if(validationTabularTaskGroup() == false) {
				return;
			}
			
			taskGroupDto.setTaskGroupName(txtGroupName.getValue());
			taskGroupDto.setDescription(txtDescription.getValue());
			if(selectedTaskGroup == null) {
				taskGroupDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				taskGroupDto.setCreationDate(new Date());
			} else {
				taskGroupDto.setCreatedBy(selectedTaskGroup.getCreatedBy());
				taskGroupDto.setCreationDate(selectedTaskGroup.getCreationDate());
			}
			taskGroupDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			taskGroupDto.setLastUpdateDate(new Date());		
			taskGroupDto.setTaskGroupDetailDto(tbnTask.getValue());
			try {
				Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
					
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {	
						int status = (int) event.getData();
						if(status == 16) {
/*							try {
								clearErrorMessage();
								taskGroupServiceImpl.validateTaskGroup(taskGroupDto);*/
								taskGroupServiceImpl.save(taskGroupDto);
								auditPerformer.doAudit(UUID.randomUUID(), UUID.randomUUID(), previousObject, taskGroupDto, 1, control, Action.SUBMIT, securityServiceImpl.getSecurityProfile().getUserId(), TrxType.TASK_GROUP_SETUP.getValue(), taskGroupDto.getId());
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/systemadmin/task_group_inquiry.zul", getSelf().getParent(), param);
								getSelf().detach();
/*							} catch (ValidationException e) {
								showErrorMessage(e);
							}*/						
						} else {
							return;
						}
					}
				});
			} catch (Exception e) {
				log.error("error", e);
			}
		}
	}
	
/*	private void clearErrorMessage(){
		ErrorMessageUtil.clearErrorMessage(txtGroupName);
	}
	
	private void showErrorMessage(ValidationException e){
		ErrorMessageUtil.setErrorMessage(txtGroupName, e.getMessage(TaskGroupValidator.GROUP_NAME_NOT_EMPTY));
	}*/
		
	@Listen ("onClick = button#btnCancel")
	public void cancel() {
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
					Executions.createComponents("~./hcms/systemadmin/task_group_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
}

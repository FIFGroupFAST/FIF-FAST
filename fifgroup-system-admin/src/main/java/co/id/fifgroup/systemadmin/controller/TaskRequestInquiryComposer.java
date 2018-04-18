package co.id.fifgroup.systemadmin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.core.constant.ContentType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.MessageBoxUtil;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.systemadmin.common.FieldPermission;
import co.id.fifgroup.systemadmin.constant.TaskPhase;
import co.id.fifgroup.systemadmin.constant.TaskStatus;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetail;
import co.id.fifgroup.systemadmin.domain.TaskRequestDetailExample;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.TaskRequestDTO;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.service.TaskRequestService;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskRequestInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TaskRequestInquiryComposer.class);

	@WireVariable(rewireOnActivate = true)
	private transient TaskRequestService taskRequestService;
	@WireVariable(rewireOnActivate = true)
	private transient TaskRunnerService taskRunnerService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private SecurityService securityServiceImpl;
	
	@Wire
	private Textbox txtRequestId;
	@Wire
	private CommonPopupBandbox txtTaskRunnerName;
	@Wire
	private Listbox lstPhase;
	@Wire
	private Listbox lstStatus;
	@Wire
	private Datebox dtbDateSubmitted;
	@Wire
	private Listbox lstTaskRequest;
	@Wire
	private Paging pgTaskRequest;
	@Wire
	private Paging pgTaskRequestTop;
	@Wire
	private Button btnNew;
	
	

	private TaskRequestDTO taskRequestDto;
	private List<TaskRequestDTO> listTaskRequestDto;
	private ListModelList<TaskRequestDTO> listModelTaskRequestDto;
	private int totalSize;
	private Date dateSubmited;
	private String requestId;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		renderTaskRunnerPopUp();
		populatePhase();
		populateStatus();
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	

	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		dtbDateSubmitted.setFormat(FormatDateUI.getDateFormat());
		listModelTaskRequestDto = new ListModelList<TaskRequestDTO>();
		lstTaskRequest.setModel(listModelTaskRequestDto);
	}
	
	@Listen("onDownloadUserManual = #winTaskRequestInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void renderTaskRunnerPopUp() {
		txtTaskRunnerName.setReadonly(true);
		txtTaskRunnerName.setTitle("List of Task Runner");
		txtTaskRunnerName.setSearchText("Task Runner Name");
		txtTaskRunnerName.setHeaderLabel("Task Runner");
		txtTaskRunnerName.setSearchDelegate(new SerializableSearchDelegate<TaskRunnerDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<TaskRunnerDTO> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				TaskRunner example = new TaskRunner();
				example.setTaskRunnerName(searchCriteria);
				return taskRunnerService.getTaskRunnerDtoByExample(example, limit, offset, null);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				TaskRunnerExample example = new TaskRunnerExample();
				example.createCriteria().andTaskRunnerNameLikeInsensitive(searchCriteria);
				return taskRunnerService.countTaskRunnerByExample(example, null);
			}

			@Override
			public void mapper(KeyValue keyValue, TaskRunnerDTO data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getTaskRunnerName());
				keyValue.setDescription(data.getTaskRunnerName());
			}
		});
	}
	
	private void populatePhase() {
		ListModelList<TaskPhase> model = new ListModelList<TaskPhase>(TaskPhase.values());
		lstPhase.setModel(model);
		lstPhase.renderAll();
		lstPhase.setSelectedIndex(0);
	}
	
	private void populateStatus() {
		ListModelList<TaskStatus> model = new ListModelList<TaskStatus>(TaskStatus.values());
		lstStatus.setModel(model);
		lstStatus.renderAll();
		lstStatus.setSelectedIndex(0);
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.putAll(arg);
		Executions.createComponents("~./hcms/systemadmin/task_request_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		requestId = txtRequestId.getValue();
		dateSubmited = dtbDateSubmitted.getValue();
		if(requestId == "" && txtTaskRunnerName.getKeyValue() == null && lstPhase.getSelectedIndex() == 0 && 
				lstStatus.getSelectedIndex() == 0 && dateSubmited == null) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = -8756250972566999901L;
				
				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}
			});
		} else {
			search();
		}
	}
	
	private void search() {
		try {
//			TaskRequestDTO taskRequestDto = new TaskRequestDTO();
//			KeyValue keyValueMenu = txtTaskRunnerName.getKeyValue();
//			taskRequestDto.setTaskRequestDetail(new TaskRequestDetail());
//			if (!GlobalVariable.hasPermission(FieldPermission.flagSearchTaskRequest))
//				taskRequestDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
//			
//			if (txtRequestId.getValue() != "") {
//				taskRequestDto.setId(txtRequestId.getValue().equalsIgnoreCase("") ? null : Long.parseLong(txtRequestId.getValue()));
//			}
//			if (keyValueMenu != null) {
//				taskRequestDto.setTaskRunnerId((long) keyValueMenu.getKey());
//			}
//			if (lstPhase.getSelectedIndex() > 0) {
//				taskRequestDto.getTaskRequestDetail().setTaskPhase(lstPhase.getSelectedItem().getValue().toString());
//			}
//			if (lstStatus.getSelectedIndex() > 0) {
//				taskRequestDto.getTaskRequestDetail().setTaskStatus(lstStatus.getSelectedItem().getValue().toString());
//			}
//			if (dtbDateSubmitted.getValue() != null) {
//				taskRequestDto.setDateSubmitted(DateUtil.truncate(dtbDateSubmitted.getValue()));
//			}
//
//			totalSize = taskRequestService.countTaskRequestDtoByExample(taskRequestDto);
//			pgTaskRequest.setTotalSize(totalSize);
//			pgTaskRequest.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskRequest.setActivePage(0);
//			pgTaskRequestTop.setTotalSize(totalSize);
//			pgTaskRequestTop.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskRequestTop.setActivePage(0);
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen("onDetail= #winTaskRequestInquiry")
	public void onDetail(ForwardEvent event) {
		taskRequestDto = (TaskRequestDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskRequestDto", taskRequestDto);
		param.put("setDisabled", true);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/task_request_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDelete=#winTaskRequestInquiry")
	public void onDelete(ForwardEvent forwardEvent) {
		taskRequestDto = (TaskRequestDTO) forwardEvent.getData();
		MessageBoxUtil.deleteConfirmation(new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				String jobkey = taskRequestService.getTaskRequestParentRequestIdByTaskRequestId(taskRequestDto.getId());
				if (!taskRequestDto.getTaskRequestDetail().getTaskPhase().equalsIgnoreCase(TaskPhase.COMPLETED.getValue())) {
					taskRequestService.deleteJob(jobkey, taskRequestDto.getId(), taskRequestDto.getTaskRequestDetail().getTaskPhase());
					search();
				} else {
					Messagebox.show("You can only Delete Task Request with Running or Pending Phase.");
				}
				
			}
		});
	}
	
	@Listen("onCancel= #winTaskRequestInquiry")
	public void onCancel(ForwardEvent event) {
		taskRequestDto = (TaskRequestDTO) event.getData();
		Messagebox.show(Labels.getLabel("sam.cancelTaskRequest"),
			"Information", Messagebox.YES | Messagebox.NO,
			Messagebox.INFORMATION, new SerializableEventListener<Event>() {
	
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int result = (int) event.getData();
					if(result == 16){
						String jobkey = taskRequestService.getTaskRequestParentRequestIdByTaskRequestId(taskRequestDto.getId());
						if (taskRequestDto.getTaskRequestDetail().getTaskPhase().equalsIgnoreCase(TaskPhase.RUNNING.getValue())) {
							taskRequestService.cancelJob(jobkey, taskRequestDto.getId(), taskRequestDto.getTaskRequestDetail().getTaskPhase());
							search();
						} else {
							Messagebox.show("You can only cancel Task Request with Running Phase.");
						}
					}else
						return;
					
				}
			});
	}
	
	@Listen("onOutput=#winTaskRequestInquiry")
	public void onOutput(ForwardEvent event) {
		taskRequestDto = (TaskRequestDTO) event.getData();
		TaskRequestDetailExample example = new TaskRequestDetailExample();
		example.createCriteria().andTaskRequestIdEqualTo(taskRequestDto.getId());
		TaskRequestDetail taskRequestDetail = taskRequestService.getTaskRequestDetailByExample(example);
		String path = taskRequestDetail.getOutputFile();
		if (taskRequestDto.getTaskType().equals(TaskType.REPORT.getValue())) {
			if (taskRequestDto.getTaskRequestDetail().getTaskPhase().equals(TaskPhase.COMPLETED.getValue())) {
				DownloadUtil.downloadFile(path, ContentType.APPLICATIONPDF.getValue());
			} else {
				Messagebox.show("Current task is not yet finished, please wait and try to refresh the browser.");
			}
		} else {
			Messagebox.show("This task is not resulting an output file.");
		}
	}
	
	// add comment biar ke-merge,TPS ITSM 15102208595234 20151002
	@Listen("onLog= #winTaskRequestInquiry")
	public void onLog(ForwardEvent event) {
		taskRequestDto = (TaskRequestDTO) event.getData();
		/*Map<String, Object> param = new HashMap<String, Object>();
		String path = taskRequestService.getOutputFilePathByTaskRequestId(taskRequestDto.getId());*/
		Map<String, Object> param = new HashMap<String, Object>();
		String path = taskRequestDto.getTaskRequestDetail().getOutputLog();
		if(path != null){
			File txtFile = new File(path);
			if(!txtFile.exists()) {
				log.debug("Text file with path "+ path + " is not found.");
				Messagebox.show("File not found.");
			} else {
				param.put("fileContent", getStringsFromTxtFile(txtFile));		
				Window win = (Window) Executions.createComponents("~./hcms/systemadmin/view_log.zul", this.getSelf(), param);
				win.doModal();
			}
		}else{
			Messagebox.show("File log is not available for report");
		}
		
	}
	
	private String getStringsFromTxtFile(File txtFile){
		String returnValue = "";
		try {
			Scanner scanner = new Scanner(txtFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				returnValue += line + "\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		}
		return returnValue;
	}
	
	@Listen("onPaging = #winTaskRequestInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}
	
	private void generateDataAndPaging(ForwardEvent evt) {
//		if (evt != null && evt.getOrigin() != null && evt.getOrigin().getTarget().getId().equals("pgTaskRequest")) {
//			pgTaskRequestTop.setActivePage(pgTaskRequest.getActivePage());
//		} else {
//			pgTaskRequest.setActivePage(pgTaskRequestTop.getActivePage());
//		}
		
		TaskRequestDTO taskRequestDto = new TaskRequestDTO();
		KeyValue keyValueMenu = txtTaskRunnerName.getKeyValue();
		taskRequestDto.setTaskRequestDetail(new TaskRequestDetail());
		if (!GlobalVariable.hasPermission(FieldPermission.flagSearchTaskRequest))
			taskRequestDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		
		if (txtRequestId.getValue() != "") {
			taskRequestDto.setId(txtRequestId.getValue().equalsIgnoreCase("") ? null : Long.parseLong(txtRequestId.getValue()));
		}
		if (keyValueMenu != null) {
			taskRequestDto.setTaskRunnerId((long) keyValueMenu.getKey());
		}
		if (lstPhase.getSelectedIndex() > 0) {
			taskRequestDto.getTaskRequestDetail().setTaskPhase(lstPhase.getSelectedItem().getValue().toString());
		}
		if (lstStatus.getSelectedIndex() > 0) {
			taskRequestDto.getTaskRequestDetail().setTaskStatus(lstStatus.getSelectedItem().getValue().toString());
		}
		if (dtbDateSubmitted.getValue() != null) {
			taskRequestDto.setDateSubmitted(DateUtil.truncate(dtbDateSubmitted.getValue()));
		}

		if (txtRequestId.getValue() != "") {
			taskRequestDto.setId(txtRequestId.getValue().equalsIgnoreCase("") ? null : Long.parseLong(txtRequestId.getValue()));
		}
		if (keyValueMenu != null) {
			taskRequestDto.setTaskRunnerId((Long) keyValueMenu.getKey());
		}
		if (lstPhase.getSelectedIndex() > 0) {
			taskRequestDto.getTaskRequestDetail().setTaskPhase(lstPhase.getSelectedItem().getValue().toString());
		}
		if (lstStatus.getSelectedIndex() > 0) {
			taskRequestDto.getTaskRequestDetail().setTaskStatus(lstStatus.getSelectedItem().getValue().toString());
		}
		if (dtbDateSubmitted.getValue() != null) {
			taskRequestDto.setDateSubmitted(DateUtil.truncate(dtbDateSubmitted.getValue()));
		}

		listTaskRequestDto = taskRequestService.getTaskRequestByExample(taskRequestDto, Integer.MAX_VALUE, 0);
		listModelTaskRequestDto.clear();
		listModelTaskRequestDto.addAll(listTaskRequestDto);
	}
}

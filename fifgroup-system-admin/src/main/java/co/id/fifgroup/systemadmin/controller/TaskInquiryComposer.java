package co.id.fifgroup.systemadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.systemadmin.constant.TaskType;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskExample;
import co.id.fifgroup.systemadmin.dto.TaskDTO;
import co.id.fifgroup.systemadmin.service.TaskService;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TaskInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskService taskServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtName;
	@Wire
	private Listbox taskListbox;
	@Wire
	private Listbox cbType;
	@Wire
	private Paging pgTask;
	@Wire
	private Paging pgTaskTop;
	@Wire
	private Button btnNew;
	
	private ListModelList<TaskDTO> listModelTask;
	private List<TaskDTO> listTask;
	private int totalSize;
	private String taskName;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		listModelTask = new ListModelList<TaskDTO>();
		taskListbox.setModel(listModelTask);
	}
	
	@Listen("onDownloadUserManual = #winTaskInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onCreate = listbox#cbType")
	public void populateTaskType() {
		ListModelList<TaskType> model = new ListModelList<TaskType>(TaskType.values());
		cbType.setModel(model);
		cbType.renderAll();
		cbType.setSelectedIndex(0);
	}
	
	@Listen("onClick = button#btnFind")
	public void doFind() {
		taskName = txtName.getValue();
		if(taskName.equals("%%") && cbType.getSelectedIndex() == 0) {
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
//			TaskExample example = new TaskExample();
//			if(cbType.getSelectedIndex() > 0) {
//				TaskType type = cbType.getSelectedItem().getValue();
//				example.createCriteria().andTaskNameLikeInsensitive(txtName.getValue()).andTaskTypeLikeInsensitive(type.getValue());
//			} else {
//				example.createCriteria().andTaskNameLikeInsensitive(txtName.getValue());
//			}		
//			totalSize = taskServiceImpl.countByExample(example);
//			pgTask.setTotalSize(totalSize);
//			pgTask.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTask.setActivePage(0);
//			pgTaskTop.setTotalSize(totalSize);
//			pgTaskTop.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskTop.setActivePage(0);
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/task_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winTaskInquiry")
	public void onDetail(ForwardEvent event) {
		Task task = (Task) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("task", task);
		param.put("edit",true);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/task_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	private void generateDataAndPaging(ForwardEvent evt) {
		
//		if (evt != null && evt.getOrigin() != null && evt.getOrigin().getTarget().getId().equals("pgTask")) {
//			pgTaskTop.setActivePage(pgTask.getActivePage());
//		} else {
//			pgTask.setActivePage(pgTaskTop.getActivePage());
//		}
		
		TaskDTO example = new TaskDTO();
		if(cbType.getSelectedIndex() > 0) {
			example.setTaskType(cbType.getSelectedItem().getValue().toString().toUpperCase());
			example.setTaskName(txtName.getValue());
		} else {
			example.setTaskName(txtName.getValue());
		}
		
		listTask = taskServiceImpl.findTaskByExample(example, Integer.MAX_VALUE, 0);
		listModelTask.clear();
		listModelTask.addAll(listTask);
	}
	
	@Listen("onPaging = #winTaskInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}
}

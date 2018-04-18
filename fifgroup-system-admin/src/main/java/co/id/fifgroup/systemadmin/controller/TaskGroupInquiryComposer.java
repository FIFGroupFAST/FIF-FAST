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
import co.id.fifgroup.systemadmin.domain.TaskGroup;
import co.id.fifgroup.systemadmin.domain.TaskGroupExample;
import co.id.fifgroup.systemadmin.dto.TaskGroupDTO;
import co.id.fifgroup.systemadmin.service.TaskGroupService;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskGroupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TaskGroupInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskGroupService taskGroupServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtGroupName;
	@Wire
	private Searchtextbox txtDescription;
	@Wire
	private Listbox lstGroup;
	@Wire
	private Paging pgTaskGroup;
	@Wire
	private Paging pgTaskGroupTop;
	@Wire
	private Button btnNew;
	
	private TaskGroup taskGroup;
	private ListModelList<TaskGroupDTO> listModelTaskGroup;
	private List<TaskGroupDTO> listTaskGroups;
	private String taskGroupName;
	private String description;
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
		listModelTaskGroup = new ListModelList<TaskGroupDTO>();
		lstGroup.setModel(listModelTaskGroup);
	}
	
	@Listen("onDownloadUserManual = #winTaskGroupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = button#btnFind")
	public void find() {		
		taskGroupName = txtGroupName.getValue();
		description = txtDescription.getValue();
		if(taskGroupName.equals("%%") && description.equals("%%")) {
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
//			TaskGroupExample taskGroupExample = new TaskGroupExample();
//			taskGroupExample.createCriteria().andTaskGroupNameLikeInsensitive(txtGroupName.getValue())
//				.andDescriptionLikeInsensitive(txtDescription.getValue());
//				
//			int totalSize = taskGroupServiceImpl.countByExample(taskGroupExample);
//			pgTaskGroup.setTotalSize(totalSize);
//			pgTaskGroup.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskGroup.setActivePage(0);
//			pgTaskGroupTop.setTotalSize(totalSize);
//			pgTaskGroupTop.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskGroupTop.setActivePage(0);
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/task_group_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winTaskGroupInquiry")
	public void onDetail(ForwardEvent event) {
		taskGroup = (TaskGroup) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskGroup", taskGroup);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/task_group_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onPaging = #winTaskGroupInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}
	
	private void generateDataAndPaging(ForwardEvent evt) {
//		if (evt != null && evt.getOrigin() != null && evt.getOrigin().getTarget().getId().equals("pgTaskGroup")) {
//			pgTaskGroupTop.setActivePage(pgTaskGroup.getActivePage());
//		} else {
//			pgTaskGroup.setActivePage(pgTaskGroupTop.getActivePage());
//		}
		TaskGroup taskGroup = new TaskGroup();
		taskGroup.setTaskGroupName(txtGroupName.getValue());
		taskGroup.setDescription(txtDescription.getValue());
		listTaskGroups = taskGroupServiceImpl.getTaskGroupByExampleByRowBounds(taskGroup, Integer.MAX_VALUE, 0);
		listModelTaskGroup.clear();
		listModelTaskGroup.addAll(listTaskGroups);
	}
}

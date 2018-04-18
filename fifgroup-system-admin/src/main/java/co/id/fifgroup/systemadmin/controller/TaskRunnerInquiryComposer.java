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
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerExample;
import co.id.fifgroup.systemadmin.dto.TaskRunnerDTO;
import co.id.fifgroup.systemadmin.service.TaskRunnerService;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskRunnerInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(TaskRunnerInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskRunnerService taskRunnerService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtName;
	@Wire
	private Listbox taskRunnerListbox;
	@Wire
	private Paging pgTaskRunner;
	@Wire
	private Paging pgTaskRunnerTop;
	@Wire
	private Button btnNew;
	
	private List<TaskRunnerDTO> listTaskRunnerDto;
	private ListModelList<TaskRunnerDTO> listModelTaskRunnerDto;
	private int totalSize;
	private String taskRunnerName;
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
		listModelTaskRunnerDto = new ListModelList<TaskRunnerDTO>();
		taskRunnerListbox.setModel(listModelTaskRunnerDto);
	}
	
	@Listen("onDownloadUserManual = #winTaskRunnerInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = button#btnFind")
	public void find() {
		taskRunnerName = txtName.getValue();
		if(taskRunnerName.equals("%%")) {
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
//			TaskRunnerExample taskRunnerExample = new TaskRunnerExample();
//			taskRunnerExample.createCriteria().andTaskRunnerNameLikeInsensitive(txtName.getValue());
//			totalSize = taskRunnerService.countTaskRunnerByExample(taskRunnerExample, null);
//			pgTaskRunner.setTotalSize(totalSize);
//			pgTaskRunner.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskRunner.setActivePage(0);
//			pgTaskRunnerTop.setTotalSize(totalSize);
//			pgTaskRunnerTop.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgTaskRunnerTop.setActivePage(0);
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/task_runner_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winTaskRunnerInquiry")
	public void onDetail(ForwardEvent event){
		TaskRunnerDTO taskRunnerDto = (TaskRunnerDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskRunnerDto", taskRunnerDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("edit", true);
		Executions.createComponents("~./hcms/systemadmin/task_runner_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onPaging = #winTaskRunnerInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}

	private void generateDataAndPaging(ForwardEvent evt) {
//		if(evt != null && evt.getOrigin() != null && evt.getOrigin().getTarget().getId().equals("pgTaskRunner")) {
//			pgTaskRunnerTop.setActivePage(pgTaskRunner.getActivePage());
//		} else {
//			pgTaskRunner.setActivePage(pgTaskRunnerTop.getActivePage());
//		}
		TaskRunner taskRunner = new TaskRunner();
		taskRunner.setTaskRunnerName(txtName.getValue());
		listTaskRunnerDto = taskRunnerService.getTaskRunnerDtoByExample(taskRunner, Integer.MAX_VALUE, 0, null);
//		listTaskRunnerDto = taskRunnerService.getTaskRunnerByExample(taskRunner, 0, GlobalVariable.getMaxRowPerPage());
		listModelTaskRunnerDto.clear();
		listModelTaskRunnerDto.addAll(listTaskRunnerDto);
	}
}

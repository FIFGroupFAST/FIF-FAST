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
import co.id.fifgroup.systemadmin.dto.UserDTO;
import co.id.fifgroup.systemadmin.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UserInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(UserInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient UserService userService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Searchtextbox txtUserName;
	@Wire
	private Searchtextbox txtEmployeeName;
	@Wire
	private Listbox userListbox;
	@Wire
	private Paging pgUser;
	@Wire
	private Paging pgUserTop;
	@Wire
	private Button btnNew;
	
	private ListModelList<UserDTO> listModelUserDTO;
	private List<UserDTO> listUserDTO;
	private int totalSize;
	private String userName;
	private String employeeName;
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
		listModelUserDTO = new ListModelList<UserDTO>();
		userListbox.setModel(listModelUserDTO);
	}
	
	@Listen("onDownloadUserManual = #winUserInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onClick=button#btnFind")
	public void doSearch() {
		userName = txtUserName.getValue();
		employeeName = txtEmployeeName.getValue();
		
		if(userName.equals("%%"))
			userName = null;
		if(employeeName.equals("%%"))
			employeeName = null;
		if(userName == null && employeeName == null) {
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
//			UserDTO userDTO = new UserDTO();
//			userDTO.setUserName(userName);
//			userDTO.setEmployeeName(employeeName);
//					
//			totalSize = userService.countUserByUsernameAndEmployeeName(userDTO);
//			pgUser.setTotalSize(totalSize);
//			pgUser.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgUser.setActivePage(0);
//			pgUserTop.setTotalSize(totalSize);
//			pgUserTop.setPageSize(GlobalVariable.getMaxRowPerPage());
//			pgUserTop.setActivePage(0);
			generateDataAndPaging(null);
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	private void generateDataAndPaging(ForwardEvent evt) {
//		if (evt != null && evt.getOrigin() != null && evt.getOrigin().getTarget().getId().equals("pgUser")) {
//			pgUserTop.setActivePage(pgUser.getActivePage());
//		} else {
//			pgUser.setActivePage(pgUserTop.getActivePage());
//		}
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(userName);
		userDTO.setEmployeeName(employeeName);
				
		listUserDTO = userService.getUserByUsernameAndEmployeeName(userDTO, Integer.MAX_VALUE, 0);
		listModelUserDTO.clear();
		listModelUserDTO.addAll(listUserDTO);	
	}

	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/user_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onDetail= #winUserInquiry")
	public void onDetail(ForwardEvent event){
		UserDTO userDTO = (UserDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userDTO", userDTO);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/user_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onPaging = #winUserInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging(evt);
	}
}

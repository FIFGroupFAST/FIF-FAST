package co.id.fifgroup.systemadmin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.constant.FunctionAccessType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.security.SimpleUserDetail;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;

@VariableResolver(DelegatingVariableResolver.class)
public class UserManagementMainComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(UserManagementMainComposer.class);
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Toolbar toolbar;
	@Wire
	private Vlayout centerId;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		renderToolbarMenu();
		authenticate();
		
/*		super.doAfterCompose(comp);
		Labels.reset();
		authenticate();
		renderToolbarMenu();*/
		/*username.setValue(SecurityContextHolder.getContext().getAuthentication().getName());
		FIFUserDetails userDetails = (FIFUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails.getFifUser());*/
	}
	
	private void renderToolbarMenu(){
		addToolbarButton("Function", "~./hcms/systemadmin/function_inquiry.zul");
		addToolbarButton("Menu", "~./hcms/systemadmin/menu_inquiry.zul");
		addToolbarButton("Responsibility", "~./hcms/systemadmin/responsibility_inquiry.zul");
		addToolbarButton("Job Responsibility", "~./hcms/systemadmin/job_responsibility_inquiry.zul");
		addToolbarButton("User", "~./hcms/systemadmin/user_inquiry.zul");
		addToolbarButton("Upload Executable File", "~./hcms/systemadmin/task_file_upload.zul");
		addToolbarButton("Task", "~./hcms/systemadmin/task_inquiry.zul");
		addToolbarButton("Task Runner", "~./hcms/systemadmin/task_runner_inquiry.zul");
		addToolbarButton("Task Request", "~./hcms/systemadmin/task_request_inquiry.zul");
		addToolbarButton("Task Group", "~./hcms/systemadmin/task_group_inquiry.zul");
		addToolbarButton("Audit Trail", "~./hcms/systemadmin/audit_trail.zul");
		addToolbarButton("User Access Record", "~./hcms/systemadmin/user_access_record.zul");
		addToolbarButton("Error Log", "~./hcms/systemadmin/error_log.zul");
		addToolbarButton("Test", "~./hcms/systemadmin/test.zul");
	}
	
	private void addToolbarButton(String label, final String url){
		Toolbarbutton t = new Toolbarbutton();
		t.setLabel(label);
		t.addEventListener("onClick",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -937092478271882623L;

					public void onEvent(Event event) throws Exception {
						FunctionPermission functionPermission = getFunctionPermissions();
						buildForm(centerId, "USERMANAGEMENT", url, functionPermission);
					}
				});
		toolbar.appendChild(t);
	}
	
	private void buildForm(Component parent, String code, String url, FunctionPermission functionPermission){
		if(parent.getFirstChild() != null)
			parent.getFirstChild().detach();
		
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(GlobalVariable.USER_MANUAL_KEY, "D:\\file.txt");
			param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
			Executions.createComponents(url, parent, param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private FunctionPermission getFunctionPermissions(){
		FunctionPermission functionPermission = null;
		if(securityServiceImpl.getSecurityProfile().getSecurity().getFunctionDefaultAccess().equals(FunctionAccessType.FULL.toString()))
			functionPermission = new FunctionPermission(true, true, true);
		else if (securityServiceImpl.getSecurityProfile().getSecurity().getFunctionDefaultAccess().equals(FunctionAccessType.READ.toString()))
			functionPermission = new FunctionPermission(false, false, false);
		
		System.out.println("Function Permissions "+functionPermission);
		return functionPermission;
	}
		
	protected void authenticate() {
		SimpleUserDetail userDetail = (SimpleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		SecurityProfile securityProfile = securityServiceImpl.getSecurityProfile(userDetail.getUsername(), 1L);
		securityProfile.setWorkspaceCompanyId(1L);
		securityProfile.setWorkspaceBusinessGroupId(1L);
		auth.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth);
		UsernamePasswordAuthenticationToken auth2 = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		securityProfile.setSecurity(securityServiceImpl.getSecurityByResponsibilityId(142L, 1L, true));
		auth2.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth2);
	}
	
/*	protected void authenticate() {
		SimpleUserDetail userDetail = (SimpleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		SecurityProfile securityProfile = securityServiceImpl.getSecurityProfile(userDetail.getUsername(), 1L);
		securityProfile.setSecurity(securityServiceImpl.getSecurityByResponsibilityId(142L, 1L, true));
		auth.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}*/
	
/*	protected void authenticate() {
		AuthenticationProvider authProvider = (AuthenticationProvider) SpringUtil.getBean("authenticationProvider");
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("ADMIN", "password");
		Authentication auth = authProvider.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}*/
}

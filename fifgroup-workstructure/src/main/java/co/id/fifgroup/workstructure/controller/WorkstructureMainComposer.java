package co.id.fifgroup.workstructure.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
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
public class WorkstructureMainComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory
			.getLogger(WorkstructureMainComposer.class);
	
	@Wire
	private Toolbar toolbar;
	@Wire
	private Vlayout centerId;
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		Labels.reset();
		renderToolbarMenu();
		authenticate();
	}
	
	private void renderToolbarMenu(){
		addToolbarButton("Location", "~./hcms/workstructure/location_setup_inquiry.zul");
		addToolbarButton("Uplaod Location", "~./hcms/workstructure/upload_location_inquiry.zul");
		addToolbarButton("Organization", "~./hcms/workstructure/organization_setup_inquiry.zul");
		addToolbarButton("Org Hierarchy", "~./hcms/workstructure/organization_hierarchy_setup_inquiry.zul");
		addToolbarButton("Org Level Hierarchy", "~./hcms/workstructure/organization_level_hierarchy_setup.zul");
		addToolbarButton("Org Level", "~./hcms/workstructure/organization_level_setup_inquiry.zul");
		addToolbarButton("Grade", "~./hcms/workstructure/grade_setup_inquiry.zul");
		addToolbarButton("Grade Set", "~./hcms/workstructure/grade_set_setup_inquiry.zul");
		addToolbarButton("Upload Grade", "~./hcms/workstructure/upload_grade_inquiry.zul");
		addToolbarButton("Job", "~./hcms/workstructure/job_setup_inquiry.zul");
		addToolbarButton("LOB", "~./hcms/workstructure/lob_setup_inquiry.zul");
		addToolbarButton("Upload LOB", "~./hcms/workstructure/upload_lob_inquiry.zul");
		addToolbarButton("Upload Job", "~./hcms/workstructure/upload_job_inquiry.zul");
		addToolbarButton("Branch Size", "~./hcms/workstructure/branch_size_setup_inquiry.zul");
		addToolbarButton("Upload Organization", "~./hcms/workstructure/upload_organization_inquiry.zul");
		addToolbarButton("Upload BS", "~./hcms/workstructure/upload_branch_size_inquiry.zul");
	}
	
	private void addToolbarButton(String label, final String url){
		Toolbarbutton t = new Toolbarbutton();
		t.setLabel(label);
		t.addEventListener("onClick",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -937092478271882623L;

					public void onEvent(Event event) throws Exception {
						FunctionPermission functionPermission = getFunctionPermissions();
						buildForm(centerId, "WORKSTRUCTURE", url, functionPermission);
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
			//logger.error(e.getMessage(), e);
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
		securityProfile.setWorkspaceCompanyId(1L);
		securityProfile.setWorkspaceBusinessGroupId(1L);
		auth2.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth2);
	}
	
}

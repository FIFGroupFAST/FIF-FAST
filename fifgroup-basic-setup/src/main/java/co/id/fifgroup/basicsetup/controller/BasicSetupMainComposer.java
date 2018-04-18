package co.id.fifgroup.basicsetup.controller;

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
public class BasicSetupMainComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(BasicSetupMainComposer.class);
	
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
		/*authenticate();
		username.setValue(SecurityContextHolder.getContext().getAuthentication().getName());*/
		renderToolbarMenu();
		authenticate();
	}
	
	private void renderToolbarMenu(){
		addToolbarButton("Business Group", "~./hcms/basic-setup/business_group_inquiry.zul");
		addToolbarButton("Company", "~./hcms/basic-setup/company_inquiry.zul");
		addToolbarButton("Lookup", "~./hcms/basic-setup/lookup_inquiry.zul");
		addToolbarButton("Other Info Setup", "~./hcms/basic-setup/other_info_setup.zul");
		addToolbarButton("Global Setting", "~./hcms/basic-setup/global_setting_inquiry.zul");
		addToolbarButton("Module", "~./hcms/basic-setup/module_inquiry.zul");
		addToolbarButton("Transaction Type", "~./hcms/basic-setup/transaction_type_inquiry.zul");
		addToolbarButton("Sequence Generator", "~./hcms/basic-setup/sequence_generator_inquiry.zul");
		addToolbarButton("Printer", "~./hcms/basic-setup/printer_inquiry.zul");
		addToolbarButton("Formula", "~./hcms/basic-setup/formula_inquiry.zul");
		addToolbarButton("Parameter Formula", "~./hcms/basic-setup/formula_parameter_inquiry.zul");
		addToolbarButton("Formula Usage", "~./hcms/basic-setup/formula_usage.zul");
	}
	
	private void addToolbarButton(String label, final String url){
		Toolbarbutton t = new Toolbarbutton();
		t.setLabel(label);
		t.addEventListener("onClick",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -937092478271882623L;

					@Override
					public void onEvent(Event event) throws Exception {
						FunctionPermission functionPermission = getFunctionPermissions();
						buildForm(centerId, "BASIC SETUP", url, functionPermission);
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

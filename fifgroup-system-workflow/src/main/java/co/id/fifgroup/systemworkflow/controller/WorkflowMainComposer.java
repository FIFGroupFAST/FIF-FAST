package co.id.fifgroup.systemworkflow.controller;

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

import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.security.SimpleUserDetail;
import co.id.fifgroup.core.service.SecurityService;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowMainComposer extends SelectorComposer<Window> {

	private Logger logger = LoggerFactory.getLogger(WorkflowMainComposer.class);
	
	private static final long serialVersionUID = 1L;
	
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
	}
	
	private void renderToolbarMenu(){
		addToolbarButton("Setup Approval Model", "~./hcms/workflow/workflow_approval_model_inquiry.zul");
		addToolbarButton("Setup Template Message", "~./hcms/workflow/workflow_template_message_inquiry.zul");
		addToolbarButton("Setup Approval Model Mapping", "~./hcms/workflow/workflow_approval_model_mapping_inquiry.zul");
		addToolbarButton("Vacation Rule Inquiry", "~./hcms/workflow/workflow_vacation_rule_inquiry.zul");
		addToolbarButton("Approval Dashboard", "~./hcms/workflow/workflow_dashboard.zul");
		addToolbarButton("Monitoring Transaction", "~./hcms/workflow/workflow_monitoring_transaction.zul");
		addToolbarButton("Pending Approval", "~./hcms/workflow/workflow_pending_approval_inquiry.zul");
		addToolbarButton("Approval Process", "~./hcms/workflow/workflow_approval_process_inquiry.zul");
		addToolbarButton("Completed Task", "~./hcms/workflow/workflow_completed_task.zul");
	}
	
	private void addToolbarButton(String label, final String url){
		Toolbarbutton t = new Toolbarbutton();
		t.setLabel(label);
		t.addEventListener("onClick",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -937092478271882623L;

					@Override
					public void onEvent(Event event) throws Exception {
						buildForm(centerId, "WORKFLOW", url);
					}
				});
		toolbar.appendChild(t);
	}
	
	private void buildForm(Component parent, String code, String url){
		
		if(parent.getFirstChild() != null)
			parent.getFirstChild().detach();
		
		try {
			Executions.createComponents(url, centerId, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

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
}

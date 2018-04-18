package co.id.fifgroup.eligibility.controller;

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

import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.service.SecurityService;

@VariableResolver(DelegatingVariableResolver.class)
public class EligibilityRuleMainComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory
			.getLogger(EligibilityRuleMainComposer.class);
	
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
		addToolbarButton("Fact Type", "~./hcms/eligibility-rule/fact_type_inquiry.zul");
		addToolbarButton("Decision Table Model", "~./hcms/eligibility-rule/decision_table_model_inquiry.zul");
		addToolbarButton("Decision Table Test", "~./hcms/eligibility-rule/decision_table_test.zul");
		addToolbarButton("Decision Table Show Test", "~./hcms/eligibility-rule/decision_table_show_test.zul");
		addToolbarButton("Decision Table Upload", "~./hcms/eligibility-rule/decision_table_upload_inquiry.zul");
	}
	
	private void addToolbarButton(String label, final String url){
		Toolbarbutton t = new Toolbarbutton();
		t.setLabel(label);
		t.addEventListener("onClick",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -937092478271882623L;

					@Override
					public void onEvent(Event event) throws Exception {
						buildForm(centerId, "LEAVE", url);
						log.info("menu clicked");
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
			log.error(e.getMessage(), e);
		}

	}
	
	

	
	protected void authenticate() {
		//SimpleUserDetail userDetail = (SimpleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		SecurityProfile securityProfile = new SecurityProfile();
		securityProfile.setUserId(1L);
		securityProfile.setWorkspaceBusinessGroupId(1L);
		securityProfile.setUserName("test");
		securityProfile.setCompanyId(1L);
		securityProfile.setWorkspaceCompanyId(1L);
		auth.setDetails(securityProfile);
	}
	
}

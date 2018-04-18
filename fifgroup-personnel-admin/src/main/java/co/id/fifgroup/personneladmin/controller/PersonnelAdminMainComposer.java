package co.id.fifgroup.personneladmin.controller;

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
public class PersonnelAdminMainComposer extends SelectorComposer<Window> {

	private Logger logger = LoggerFactory.getLogger(PersonnelAdminMainComposer.class);
	
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
		addToolbarButton("People Search", "~./hcms/personneladmin/people_search.zul");
		addToolbarButton("Transfer In People Within Group", "~./hcms/personneladmin/transfer_in_within_group.zul");
		addToolbarButton("Affco Mutation", "~./hcms/personneladmin/affco_mutation.zul");
		addToolbarButton("Setup Question Set", "~./hcms/personneladmin/setup_question_set_for_probation_review.zul");
		addToolbarButton("Probation Review Search", "~./hcms/personneladmin/probation_review_search.zul");
		addToolbarButton("Exit Clearance Search", "~./hcms/personneladmin/exit_clearance_search.zul");
		addToolbarButton("Upload Account Number", "~./hcms/personneladmin/upload_account_number_search.zul");
		addToolbarButton("Upload Assignment", "~./hcms/personneladmin/upload_assignment_search.zul");
		addToolbarButton("Upload Award", "~./hcms/personneladmin/upload_award_search.zul");
		addToolbarButton("Upload PTKP Status", "~./hcms/personneladmin/upload_ptkp_search.zul");
		addToolbarButton("Upload Work Equipment", "~./hcms/personneladmin/upload_work_equipment_search.zul");
		addToolbarButton("Upload DPA Account", "~./hcms/personneladmin/upload_dpa_account_search.zul");
		addToolbarButton("Upload DPA Amount", "~./hcms/personneladmin/upload_dpa_amount_search.zul");
	}
	
	private void addToolbarButton(String label, final String url){
		Toolbarbutton t = new Toolbarbutton();
		t.setLabel(label);
		t.addEventListener("onClick",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -937092478271882623L;

					@Override
					public void onEvent(Event event) throws Exception {
						buildForm(centerId, "Personnel Admin", url);
					}
				});
		toolbar.appendChild(t);
	}
	
	private void buildForm(Component parent, String code, String url) {
		if(parent.getFirstChild() != null)
			parent.getFirstChild().detach();
		
		try {
			Executions.createComponents(url, centerId, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	// responsibility 
	// employee self service 126
	// manager self service 127
	// hc self service 142
	// global super hc administrator 161
	// global super hc 162
	// global super hc admin 163
	// hc area self service 164
	// global hc self service 165
	// hcec self service 166
	// supervisor self service 167
	
	protected void authenticate() {
		SimpleUserDetail userDetail = (SimpleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		SecurityProfile securityProfile = securityServiceImpl.getSecurityProfile(userDetail.getUsername(), 1L);
		securityProfile.setWorkspaceCompanyId(1L);
		securityProfile.setWorkspaceBusinessGroupId(1L);
		auth.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth);
		UsernamePasswordAuthenticationToken auth2 = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		securityProfile.setSecurity(securityServiceImpl.getSecurityByResponsibilityId(161L, 1L, true));
		auth2.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth2);
	}
	
}

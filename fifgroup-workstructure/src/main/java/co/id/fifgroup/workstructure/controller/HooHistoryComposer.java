package co.id.fifgroup.workstructure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.workstructure.dto.HeadOfOrganizationDTO;
import co.id.fifgroup.workstructure.dto.OrgHierarchyDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class HooHistoryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(HooHistoryComposer.class);

	@WireVariable("arg") private Map<Object, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	
	@Wire private Label lblOrgCode;
	@Wire private Label lblOrgName;
	@Wire private Label lblOrgDesc;
	@Wire private Listbox lstHoo;
	
	private OrganizationDTO selectedOrg;
	private OrgHierarchyDTO selectedHier;
	private List<HeadOfOrganizationDTO> hooHistories;
	private HeadOfOrganizationDTO selectedEmployee;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		selectedHier = (OrgHierarchyDTO) arg.get(OrgHierarchyDTO.class.getName());
		selectedOrg = (OrganizationDTO) arg.get(OrganizationDTO.class.getName());
		renderDetail();
	}
	
	private void renderDetail() {
		lblOrgCode.setValue(selectedOrg.getCode());
		lblOrgName.setValue(selectedOrg.getName());
		lblOrgDesc.setValue(selectedOrg.getDescription());
		hooHistories = organizationSetupServiceImpl.findHooHistoryByOrganizationId(selectedOrg.getId());
		lstHoo.setModel(new ListModelList<HeadOfOrganizationDTO>(hooHistories));
	}
	
	@Listen ("onClick = button#btnBack")
	public void onBack() {
		Map<Object, Object> param = new HashMap<>();
		param.put(OrganizationDTO.class.getName(), selectedOrg);
		param.put(OrgHierarchyDTO.class.getName(), selectedHier);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_diagram.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onEmployeeProfile= #winHooHistory")
	public void onDiagram(ForwardEvent event){
		selectedEmployee = (HeadOfOrganizationDTO) event.getData();
		Map<Object, Object> param = new HashMap<>();
		param.put(OrganizationDTO.class.getName(), selectedOrg);
		param.put(OrgHierarchyDTO.class.getName(), selectedHier);
		param.put(HeadOfOrganizationDTO.class.getName(), selectedEmployee);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/employee_profile.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
}

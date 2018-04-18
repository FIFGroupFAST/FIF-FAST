package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.systemworkflow.constants.FieldPermissionsWorkflow;
import co.id.fifgroup.systemworkflow.dto.VacationRuleDTO;
import co.id.fifgroup.systemworkflow.service.VacationRuleService;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonEmployeeNumberBandbox;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowVacationRuleInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowVacationRuleInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	private WorkflowVacationRuleInquiryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient VacationRuleService vacationRuleServiceImpl;
	
	@Wire
	private Row rowApproverName;
	@Wire
	private CommonEmployeeNumberBandbox bnbApprover;
	@Wire
	private CommonEmployeeNumberBandbox bnbSubstituteApprover;
	@Wire
	private Datebox dtbStartDate;
	@Wire
	private Datebox dtbEndDate;
	@Wire
	private Listbox lbxVacationRule;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
	}
	
	@Listen("onDownloadUserManual = #winWorkflowVacationRuleInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	public void initComponent() {
		lbxVacationRule.setMold("paging");
		lbxVacationRule.setPageSize(GlobalVariable.getMaxRowPerPage());
		
		dtbStartDate.setFormat(FormatDateUI.getDateFormat());
		dtbEndDate.setFormat(FormatDateUI.getDateFormat());
	
		if (GlobalVariable.hasPermission(FieldPermissionsWorkflow.VACATION_RULE_BNB_APPROVER_VISIBLE)) {
			rowApproverName.setVisible(true);
		} else {
			rowApproverName.setVisible(false);
		}		
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtApproverName; onOK = #txtSubstituteName")
	public void doFind() {
		if (((bnbApprover.getKeyValue() == null && rowApproverName.isVisible()) || !rowApproverName.isVisible()) && bnbSubstituteApprover.getKeyValue() == null 
				&& dtbStartDate.getValue() == null && dtbEndDate.getValue() == null) {
			Messagebox.show(Labels.getLabel("common.searchMightBeSlow"), "Question", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {				
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals("onYes")) {
						doSearch();
					}
				}
			});			
		} else {
			doSearch();
		}
	}
	
	public void doSearch() {
		try {
			Date dateFrom = dtbStartDate.getValue() != null ? DateUtil.truncate(dtbStartDate.getValue()) : null;
			Date dateTo = dtbEndDate.getValue() != null ? DateUtil.truncate(dtbEndDate.getValue()) : null;
			List<VacationRuleDTO> listVacationRules = new ArrayList<VacationRuleDTO>();
			String substituteApprover = bnbSubstituteApprover.getKeyValue() != null ? bnbSubstituteApprover.getKeyValue().getDescription().toString() : "";
			if (GlobalVariable.hasPermission(FieldPermissionsWorkflow.VACATION_RULE_BNB_APPROVER_VISIBLE)) {
				String approver = bnbApprover.getKeyValue() != null ? bnbApprover.getKeyValue().getDescription().toString() : "";
				listVacationRules = vacationRuleServiceImpl.getVacationByCriteria(approver, substituteApprover, dateFrom, dateTo);
			} else {
				listVacationRules = vacationRuleServiceImpl.getVacationRuleByApproverId(securityServiceImpl.getSecurityProfile().getPersonUUID(), substituteApprover, dateFrom, dateTo);
			}
			ListModel<VacationRuleDTO> listModelVacationRules = new ListModelList<VacationRuleDTO>(listVacationRules);
			lbxVacationRule.setModel(listModelVacationRules);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workflow/workflow_vacation_rule_request.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen("onDetail = #lbxVacationRule")
	public void onDetail(ForwardEvent evt) {
		VacationRuleDTO selectedData = (VacationRuleDTO) evt.getData();
		params.put("vacationRule", selectedData);
		Executions.createComponents("~./hcms/workflow/workflow_vacation_rule_request.zul", getSelf().getParent(), params);
		getSelf().detach();
	}
}

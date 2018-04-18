package co.id.fifgroup.eligibility.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import com.google.common.base.Strings;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.MessageBoxUtil;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableModelInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	@WireVariable("arg")
	private Map<String, Object> arg;
	private static Logger logger = LoggerFactory.getLogger(DecisionTableModelInquiryComposer.class);
	
	@Wire private Searchtextbox txtModelName;
	
	@Wire private Listbox lstModel;
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableModelSetupService decisionTableModelSetupServiceImpl;
	
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
	}
	
	@Listen("onDownloadUserManual = #winDecisionTableModelInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = #btnFind")
	public void onSearch() {
		final DecisionTableModelDTO example = new DecisionTableModelDTO();
		example.setName(txtModelName.getValue());
		// example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		
		if (Strings.isNullOrEmpty(example.getName())) {
			MessageBoxUtil.searchMightBeSlow(new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					List<DecisionTableModelDTO> result = decisionTableModelSetupServiceImpl.search(example);
					lstModel.setModel(new ListModelList<DecisionTableModelDTO>(result));					
				}
			});
		} else {
			List<DecisionTableModelDTO> result = decisionTableModelSetupServiceImpl.search(example);
			lstModel.setModel(new ListModelList<DecisionTableModelDTO>(result));
		}
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		logger.debug("go to decision table model setup page");
		Component parent = getSelf().getParent();
		getSelf().detach();
		Executions.createComponents("~./hcms/eligibility-rule/decision_table_model_setup.zul", parent, null);
	}
	
	@Listen ("onModelDetail = #winDecisionTableModelInquiry")
	public void showDetail(ForwardEvent event) {
		Map<String, Object> param = new HashMap<>();
		param.put("subject", event.getData());
		Component parent = getSelf().getParent();
		getSelf().detach();
		Executions.createComponents("~./hcms/eligibility-rule/decision_table_model_setup.zul", parent, param);
	}

}

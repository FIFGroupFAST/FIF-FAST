package co.id.fifgroup.eligibility.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import com.google.common.base.Strings;

import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.MessageBoxUtil;
import co.id.fifgroup.eligibility.dto.FactTypeDTO;
import co.id.fifgroup.eligibility.service.FactTypeSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class FactTypeInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(FactTypeInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire private Textbox txtFactTypeName;
	@Wire private Listbox lstFactType;
	
	@WireVariable(rewireOnActivate = true)
	private transient FactTypeSetupService factTypeSetupServiceImpl;
	
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
			
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		logger.debug("Create Fact Type Inquiry...");
	}
	
	@Listen("onDownloadUserManual = #winFactTypeInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/eligibility-rule/fact_type_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	@Listen ("onClick = #btnFind")
	public void onSearch() {
		final FactTypeDTO example = new FactTypeDTO();
		example.setName(txtFactTypeName.getValue());
		
		// example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		
		if (Strings.isNullOrEmpty(example.getName())) {
			MessageBoxUtil.searchMightBeSlow(new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					List<FactTypeDTO> result = factTypeSetupServiceImpl.findByExample(example);
					lstFactType.setModel(new ListModelList<>(result));
					
				}
			});
		} else {
			List<FactTypeDTO> result = factTypeSetupServiceImpl.findByExample(example);
			lstFactType.setModel(new ListModelList<>(result));
		}
				
	}
	
	@Listen("onFactTypeDetail = #winFactTypeInquiry")
	public void onFactTypeDetail(ForwardEvent event) {
		Map<String, Object> arg = new HashMap<>();
		arg.put("subject", event.getData());
		Executions.createComponents("~./hcms/eligibility-rule/fact_type_setup.zul", getSelf().getParent(), arg);
		getSelf().detach();
	}


}

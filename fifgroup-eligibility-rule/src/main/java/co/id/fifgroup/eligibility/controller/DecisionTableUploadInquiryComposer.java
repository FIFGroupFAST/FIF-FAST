package co.id.fifgroup.eligibility.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.MessageBoxUtil;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableUploadInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DecisionTableUploadInquiryComposer.class);
	
	@Wire private Datebox dtbDateFrom;
	@Wire private Datebox dtbDateTo;
	@Wire private Textbox txtBatchNumberFrom;
	@Wire private Textbox txtBatchNumberTo;
	@Wire private Listbox lstBatchUpload;
	@Wire private Button btnNew;
	
	@WireVariable
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableUploadService decisionTableUploadServiceImpl;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		if (null != functionPermission && !functionPermission.isCreateable())
			btnNew.setVisible(true);
	}
	
	@Listen("onDownloadUserManual = #winDecisionTableUploadInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onClick = #btnFind")
	public void onFind() {
		if (null == dtbDateFrom.getValue() && null == dtbDateTo.getValue()) {
			MessageBoxUtil.searchMightBeSlow(new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals(Events.ON_OK))
						doFind();
				}
			});
		} else {
			doFind();
		}
	}
	
	protected void doFind() {
		try {
			BatchUpload batchUpload = new BatchUpload();
			batchUpload.setProcessDateFrom(dtbDateFrom.getValue());
			batchUpload.setProcessDateTo(dtbDateTo.getValue());
			List<BatchUpload> result = decisionTableUploadServiceImpl.findBatchUploadByExample(batchUpload);
			lstBatchUpload.setModel(new ListModelList<>(result));
			lstBatchUpload.renderAll();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
	
	@Listen("onClick = #btnNew")
	public void onNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Component parent = getSelf().getParent();
		getSelf().detach();
		Executions.createComponents("~./hcms/eligibility-rule/decision_table_upload_create.zul", parent, param);
	}
	
	@Listen("onDetail=#winDecisionTableUploadInquiry")
	public void onDetail(ForwardEvent event) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("batchUpload", event.getData());
		Component parent = getSelf().getParent();
		getSelf().detach();
		Executions.createComponents("~./hcms/eligibility-rule/decision_table_upload_detail.zul", parent, param);
	}
	
	@Listen("onCloseBatch = #winDecisionTableUploadInquiry")
	public void onCloseBatch(ForwardEvent event) {
		
	}
	
	@Listen("onCancelBatch = #winDecisionTableUploadInquiry")
	public void onCancelBatch(ForwardEvent event) {
		
	}
	
	
	

}

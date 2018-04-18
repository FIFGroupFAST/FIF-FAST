package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.core.ui.NavigationTransactionForm;
import co.id.fifgroup.notification.constant.MessageStatus;
import co.id.fifgroup.notification.domain.NotificationMessage;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowNotificationMessageDetailComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowNotificationMessageDetailComposer.class);

	private WorkflowNotificationMessageDetailComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();

	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient TemplateMessageService templateMessageServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire
	private Label lblSubject;
	@Wire
	private Label lblFrom;
	@Wire
	private Label lblReceivedTime;
	@Wire
	private Label lblTo;
	@Wire
	private Html htmlDetailMessage;
	@Wire
	private Hbox hboxLinkDetail;
	
	NotificationMessage notificationMessage = null;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateData();
	}
	
	public void populateData() {
		
		try {
			if (arg.containsKey("notificationMessage")) {
				notificationMessage = (NotificationMessage) arg.get("notificationMessage");
			} else if (arg.containsKey("AVMApprovalProcessData")) {
				AVMApprovalProcessData data = (AVMApprovalProcessData) arg.get("AVMApprovalProcessData");
				notificationMessage = templateMessageServiceImpl.getNotificationMessageByHistorySeqNumber((long) data.getSequenceNumber());
				if (notificationMessage != null) {
					notificationMessage.setUrlDetail(null);
				}
			} else if (arg.containsKey("AVMApprovalHistory")) {
				AVMApprovalHistory data = (AVMApprovalHistory) arg.get("AVMApprovalHistory");
				notificationMessage = templateMessageServiceImpl.getNotificationMessageByHistorySeqNumber((long) data.getSequenceNumber());
				if (notificationMessage != null) {
					notificationMessage.setUrlDetail(null);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(Labels.getLabel("swf.err.notificationMessageNotFound"));
			onClose();
		}
		
		if (notificationMessage != null) {
			if (notificationMessage.getStatus() == MessageStatus.UNREAD.getCode()) {
				templateMessageServiceImpl.updateMessageStatus(notificationMessage.getMessageId(), MessageStatus.READ.getCode());
			}
			lblSubject.setValue(notificationMessage.getSubject());
			lblFrom.setValue(avmAdapterServiceImpl.getName(notificationMessage.getFromId(), null));
			lblReceivedTime.setValue(FormatDateUI.formatDateTime(notificationMessage.getReceivedTime()));
			lblTo.setValue(avmAdapterServiceImpl.getName(notificationMessage.getToId(), null));
			htmlDetailMessage.setContent(notificationMessage.getContentMessage());
			if (notificationMessage.getUrlDetail() != null) {
				hboxLinkDetail.setVisible(true);
			}
		} else {
			Messagebox.show(Labels.getLabel("swf.err.notificationMessageNotFound"));
			onClose();
		}
	}
	
	@Listen("onClick = #linkDetailTrx")
	public void onClickLink() {
		try {
			int idx = notificationMessage.getUrlDetail().indexOf("?");
			String url = notificationMessage.getUrlDetail();
			
			if(idx > -1) {
				url = notificationMessage.getUrlDetail().substring(0, idx);
				String parsedParameter = notificationMessage.getUrlDetail().substring(idx+1);
				params = getParsedParameter(parsedParameter);
				
				if (params.containsKey("avmTrxId")) {
					Object applicationData = avmAdapterServiceImpl.getDataTransactionByAvmTrxId(UUID.fromString((String) params.get("avmTrxId")));
					params.put("applicationData", applicationData);
				}
			}
			params.put("workspaceMain", (NavigationTransactionForm) arg.get("workspaceMain"));
			Window window = (Window)Executions.createComponents(
					url, getSelf().getParent(), params);
			window.setClosable(true);
			window.setMaximized(false);
			window.setWidth("80%");
			window.setHeight("600px");
			window.setContentStyle("overflow:auto");
			window.doModal();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private Map<String, Object> getParsedParameter(String parsedParameter) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		List<String> unwrap = unwrapAndMark(parsedParameter);
		for (String wrap : unwrap) {
			int index = wrap.indexOf("=");
			String key = wrap.substring(0, index);
			String value = wrap.substring(index+1);
			logger.info("key : " + key + " , value : " + value);
			params.put(key, value);
		}
		return params;
	}
	
	public static List<String> unwrapAndMark(String string2bTokenized) {
		String separator = "&";
		List<String> vectorReturned = new ArrayList<String>();
		if (string2bTokenized != null && !string2bTokenized.isEmpty()) {
			StringTokenizer tokens = new StringTokenizer(string2bTokenized,
					separator);
			int count = tokens.countTokens();
			for (int i = 0; i < count; i++) {
				String temp = tokens.nextToken().trim();
				vectorReturned.add(temp);
			}			
		}	
		
		return vectorReturned;
	}
	
	@Listen("onClick = button#btnClose")
	public void onClose() {
		if (arg.containsKey("uriParent")) {
			params = new HashMap<String, Object>();
			params.put("workspaceMain", (NavigationTransactionForm) arg.get("workspaceMain"));
			Executions.createComponents((String) arg.get("uriParent"), getSelf().getParent(), params);
			getSelf().detach();			
		} else {
			getSelf().onClose();
		}
	}	
	
}

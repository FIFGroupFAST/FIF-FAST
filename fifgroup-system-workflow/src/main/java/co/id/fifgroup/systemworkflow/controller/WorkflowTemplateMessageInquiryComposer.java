package co.id.fifgroup.systemworkflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageDTO;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowTemplateMessageInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowTemplateMessageInquiryComposer.class);

	@WireVariable("arg")
	private Map<String, Object> arg;
	private WorkflowTemplateMessageInquiryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable(rewireOnActivate=true)
	private transient TemplateMessageService templateMessageServiceImpl;
	
	@Wire
	private Textbox txtNotificationMessageName;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Listbox cmbActionType;
	@Wire
	private Listbox lbxTemplateMessage;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();		
	}
	
	public void initComponent() {
		lbxTemplateMessage.setMold("paging");
		lbxTemplateMessage.setPageSize(GlobalVariable.getMaxRowPerPage());
		
		getTransactionType();
		getActionType();
	}
	
	@Listen("onDownloadUserManual = #winWorkflowTemplateMessageInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtNotificationMessageName")
	public void doFind() {
		if (txtNotificationMessageName.getValue().isEmpty() && cmbActionType.getSelectedIndex() == 0 && cmbTrxType.getSelectedIndex() == 0) {
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
	
	public void doSearch()  {
		try {
			TrxType trxType = null;
			ActionType actionType = null;
			if (cmbTrxType.getSelectedIndex() != -1)
				trxType = (TrxType) cmbTrxType.getSelectedItem().getValue();
			if (cmbActionType.getSelectedIndex() != -1)
				actionType = (ActionType) cmbActionType.getSelectedItem().getValue();
			List<TemplateMessageDTO> listTemplateMessages = templateMessageServiceImpl.getTemplateMessageByCriteria(txtNotificationMessageName.getValue(), trxType, actionType);
			ListModelList<TemplateMessageDTO> listModelTemplateMessage = new ListModelList<TemplateMessageDTO>(listTemplateMessages);
			lbxTemplateMessage.setModel(listModelTemplateMessage);
			lbxTemplateMessage.setItemRenderer(getItemRenderer());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<TemplateMessageDTO> getItemRenderer() {
		ListitemRenderer<TemplateMessageDTO> listitemRenderer = new SerializableListItemRenderer<TemplateMessageDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final TemplateMessageDTO data, final int index)
					throws Exception {
				
				new Listcell(data.getTemplateMessage().getTemplateName()).setParent(item);
				new Listcell(TrxType.fromCode(data.getTemplateMessageMappingDTO().getTransactionId()) != null ? TrxType.fromCode(data.getTemplateMessageMappingDTO().getTransactionId()).getMessage() : "").setParent(item);
				new Listcell(ActionType.fromMessage(data.getTemplateMessageMappingDTO().getActionType()).getMessage()).setParent(item);
				new Listcell(data.getTemplateMessageMappingDTO().getUserName()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getTemplateMessage().getLastUpdatedDate())).setParent(item);
												
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
//				link.setDisabled(disabledHyperlinkDetail);
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("templateMessageDto", data);
						Executions.createComponents("~./hcms/workflow/workflow_template_message.zul", getSelf().getParent(), params);
						getSelf().detach();
					}
				});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);				
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Executions.createComponents("~./hcms/workflow/workflow_template_message.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	private void getTransactionType() {
		cmbTrxType.setMold("select");
		ListModelList<TrxType> model = new ListModelList<TrxType>(TrxType.values());
		cmbTrxType.setModel(model);
		cmbTrxType.setItemRenderer(new SerializableListItemRenderer<TrxType>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, TrxType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getMessage()));
			}
		});
		cmbTrxType.renderAll();
		cmbTrxType.setSelectedIndex(0);
		
	}
	
	private void getActionType() {
		cmbActionType.setMold("select");
		ListModelList<ActionType> model = new ListModelList<ActionType>(ActionType.values());
		model.remove(ActionType.READY_TO_APPROVE);
		cmbActionType.setModel(model);
		cmbActionType.setItemRenderer(new SerializableListItemRenderer<ActionType>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, ActionType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getMessage()));
			}
		});
		cmbActionType.renderAll();
		cmbActionType.setSelectedIndex(0);
	}
}

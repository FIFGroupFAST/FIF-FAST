package co.id.fifgroup.systemworkflow.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkforge.ckez.CKeditor;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageDTO;
import co.id.fifgroup.systemworkflow.dto.TemplateMessageMappingDTO;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;
import co.id.fifgroup.systemworkflow.validation.TemplateMessageValidator;

import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.notification.domain.TemplateMessage;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowTemplateMessageComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowTemplateMessageComposer.class);

	private WorkflowTemplateMessageComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable(rewireOnActivate=true)
	private transient TemplateMessageService templateMessageServiceImpl;
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Textbox txtTemplateMessageName;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Listbox cmbActionType;
	@Wire
	private Textbox txtSubject;
	@Wire
	private CKeditor ckeditorMessage;
	@Wire
	private Textbox txtUrlDetail;
	
	private int cursorPositionTxtSubject;
	
	private TemplateMessageDTO templateMessageDto;
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		templateMessageDto = (TemplateMessageDTO) arg.get("templateMessageDto");
		if (templateMessageDto != null) {
			txtTemplateMessageName.setValue(templateMessageDto.getTemplateMessage().getTemplateName());
			txtTemplateMessageName.setDisabled(true);
			txtSubject.setValue(templateMessageDto.getTemplateMessage().getTemplateSubject());
			ckeditorMessage.setValue(templateMessageDto.getTemplateMessage().getTemplateContent());
			
			getTransactionType(templateMessageDto.getTemplateMessageMappingDTO().getTransactionId());
			cmbTrxType.setDisabled(true);
			getActionType(ActionType.valueOf(templateMessageDto.getTemplateMessageMappingDTO().getActionType()));
			cmbActionType.setDisabled(true);
			txtUrlDetail.setValue(templateMessageDto.getTemplateMessageMappingDTO().getUrlDetail());
		} else {
			templateMessageDto = new TemplateMessageDTO();
			templateMessageDto.setTemplateMessage(new TemplateMessage());
			templateMessageDto.setTemplateMessageMappingDTO(new TemplateMessageMappingDTO());
			getTransactionType(null);
			getActionType(null);			
		}
		
		// to get cursor position in textbox
		txtSubject.setWidgetListener("onClick", "zAu.send(new zk.Event(this,\"onMyClick\"," +
				"zk(this.$n()).getSelectionRange()[1]));");
				
	}
	
	@Listen("onClick = button#btnParameter")
	public void addParameterToSubject() {
		TrxType trxType = (TrxType) cmbTrxType.getSelectedItem().getValue();
		if (!trxType.equals(TrxType.SELECT)) {
			params = new HashMap<String, Object>();
			params.put("trxType", trxType);
			params.put("parent", thisComposer);
			params.put("setTo", 1);
			Window win = (Window) Executions.createComponents("~./hcms/workflow/workflow_add_parameter_message.zul", getSelf().getParent(), params);
			win.setClosable(true);
			win.setMaximized(false);
			win.setWidth("50%");
			try {
				win.doModal();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}			
		} else {
			Messagebox.show(Labels.getLabel("swf.pleaseSelectTrxType"));
		}
	}
	
	public void setParameterToTxtSubject(String addMessage) {
		String subject = txtSubject.getValue();
		if (subject.length() > 0 && cursorPositionTxtSubject == 0) {
			subject += addMessage;
		} else {
			String firstSubject = subject.substring(0, cursorPositionTxtSubject);
			String secondSubject = subject.substring(cursorPositionTxtSubject);
			subject = firstSubject + addMessage + secondSubject;
		}
		txtSubject.setValue(subject);
		txtSubject.setFocus(true);
	}
	
	@Listen("onMyClick = textbox#txtSubject") 
	public void getCursorPosition(Event e) {
		cursorPositionTxtSubject = (int) e.getData();
	}
	
	@Listen("onClick = button#btnParameterMessage")
	public void addParameterToContent() {
		TrxType trxType = (TrxType) cmbTrxType.getSelectedItem().getValue();
		if (!trxType.equals(TrxType.SELECT)) {
			params = new HashMap<String, Object>();
			params.put("trxType", (TrxType) cmbTrxType.getSelectedItem().getValue());
			params.put("parent", thisComposer);
			params.put("setTo", 2);
			Window win = (Window) Executions.createComponents("~./hcms/workflow/workflow_add_parameter_message.zul", getSelf().getParent(), params);
			win.setClosable(true);
			win.setMaximized(false);
			win.setWidth("50%");
			try {
				win.doModal();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			Messagebox.show("please select transaction type");
		}
	}
	
	public void setCKeditorMessage(String addMessage) {
		addMessage = "'" + addMessage + "'";
		Clients.evalJavaScript("insertText(" + addMessage + ");");
	}
	
	@Listen("onSelect = listbox#cmbTrxType")
	public void onSelectTrxType() {
		txtSubject.setValue("");
		ckeditorMessage.setValue("");
	}
	
	@Listen("onClick = button#btnSave")
	public void onSave() {
		String confirmation = templateMessageDto.getTemplateMessage().getTemplateId() == null ? Labels.getLabel("common.confirmationSave") : Labels.getLabel("common.confirmationSaveVersion");
		Messagebox.show(confirmation, Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					templateMessageDto.getTemplateMessage().setTemplateName(txtTemplateMessageName.getValue());
					templateMessageDto.getTemplateMessage().setTemplateSubject(txtSubject.getValue());
					templateMessageDto.getTemplateMessage().setTemplateContent(ckeditorMessage.getValue());
					
					templateMessageDto.getTemplateMessageMappingDTO().setTransactionId(((TrxType)cmbTrxType.getSelectedItem().getValue()).getCode());
					templateMessageDto.getTemplateMessageMappingDTO().setActionType(((ActionType)cmbActionType.getSelectedItem().getValue()).name());
					templateMessageDto.getTemplateMessageMappingDTO().setUrlDetail(txtUrlDetail.getValue());
					
					try {
						clearErrorMessage();
						templateMessageServiceImpl.saveTemplateMessageDto(templateMessageDto);
						Messagebox.show(Labels.getLabel("common.saveSuccessfully"));
						Executions.createComponents("~./hcms/workflow/workflow_template_message_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();
					} catch (ValidationException ve) {
						showErrorMessage(ve.getConstraintViolations());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}		
				}
			}
		});		
		
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtTemplateMessageName);
		ErrorMessageUtil.clearErrorMessage(cmbTrxType);
		ErrorMessageUtil.clearErrorMessage(cmbActionType);
		ErrorMessageUtil.clearErrorMessage(txtSubject);
		ErrorMessageUtil.clearErrorMessage(ckeditorMessage);
	}
	
	private void showErrorMessage(Map<String, String> maps) {				
		if(maps.get(TemplateMessageValidator.TEMPLATE_MESSAGE_NAME_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(txtTemplateMessageName, 
				maps.get(TemplateMessageValidator.TEMPLATE_MESSAGE_NAME_VALIDATE));
		}
		if(maps.get(TemplateMessageValidator.TRANSACTION_TYPE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(cmbTrxType, 
				maps.get(TemplateMessageValidator.TRANSACTION_TYPE_VALIDATE));
		}
		if(maps.get(TemplateMessageValidator.ACTION_TYPE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(cmbActionType, 
				maps.get(TemplateMessageValidator.ACTION_TYPE_VALIDATE));
		}
		if(maps.get(TemplateMessageValidator.TEMPLATE_SUBJECT_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(txtSubject, 
				maps.get(TemplateMessageValidator.TEMPLATE_SUBJECT_VALIDATE));
		}
		if(maps.get(TemplateMessageValidator.TEMPLATE_CONTENT_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(ckeditorMessage, 
				maps.get(TemplateMessageValidator.TEMPLATE_CONTENT_VALIDATE));
		}		
	}
	
	@Listen("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					Executions.createComponents("~./hcms/workflow/workflow_template_message_inquiry.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}
		});		
	}
	
	private void getTransactionType(Long selected) {
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
		
		if(selected != null) {
			int idx = 0;
			for (TrxType trxType : model) {
				if (trxType.getCode() != null) {
					if(trxType.getCode().equals(selected))
						cmbTrxType.setSelectedItem(cmbTrxType.getItemAtIndex(idx));
				}
				idx++;					
			}
		} else
			cmbTrxType.setSelectedIndex(0);
	}
	
	private void getActionType(ActionType selected) {
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
		
		if(selected != null) {
			int idx = 0;
			for (ActionType actionType : model) {
				if(actionType.equals(selected))
					cmbActionType.setSelectedItem(cmbActionType.getItemAtIndex(idx));
				idx++;
			}
		} else
			cmbActionType.setSelectedIndex(0);
	}
}

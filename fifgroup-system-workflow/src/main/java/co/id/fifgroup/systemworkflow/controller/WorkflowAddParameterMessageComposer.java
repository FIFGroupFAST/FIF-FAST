package co.id.fifgroup.systemworkflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ParameterMessage;
import co.id.fifgroup.systemworkflow.service.TemplateMessageService;

import co.id.fifgroup.core.ui.GlobalVariable;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowAddParameterMessageComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowAddParameterMessageComposer.class);

	private WorkflowAddParameterMessageComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable(rewireOnActivate=true)
	private transient TemplateMessageService templateMessageServiceImpl;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
	private Listbox lbxParameter;
	
	private WorkflowTemplateMessageComposer parent;
	private int setTo;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		setTo = (int) arg.get("setTo");
		parent = (WorkflowTemplateMessageComposer) arg.get("parent");
		lbxParameter.setPageSize(GlobalVariable.getMaxRowPerPage());
		getListParameter((TrxType) arg.get("trxType"));
	}
	
	public void getListParameter(TrxType trxType) {
		try {
			List<ParameterMessage> listParams = null;
			if (setTo == 1)
				listParams = templateMessageServiceImpl.getParameterByTransactionId(trxType.getCode(), (short) setTo);
			else 
				listParams = templateMessageServiceImpl.getParameterByTransactionId(trxType.getCode(), (short) setTo);
			ListModel<ParameterMessage> listModelParams = new ListModelList<ParameterMessage>(listParams);
			lbxParameter.setModel(listModelParams);			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Listen("onClick = button#btnSelect")
	public void addParameter() {
		
		ParameterMessage selected = (ParameterMessage) lbxParameter.getSelectedItem().getValue(); 
		if (setTo == 1) {
			parent.setParameterToTxtSubject(selected.getParameterKey());
		} else {
			if (selected.getListData() == 0)
				parent.setCKeditorMessage(selected.getParameterKey());
			else
				parent.setCKeditorMessage(selected.getAliasName());
		}
		getSelf().onClose();
	}
	
	@Listen("onClick = button#btnBack")
	public void doBack() {
		getSelf().onClose();
	}
	
}

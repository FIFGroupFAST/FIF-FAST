package co.id.fifgroup.systemworkflow.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalHistoryComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalHistoryComposer.class);
	
	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@Wire
	private Listbox lbxApprovalHistory;
	
	private UUID avmTrxId;
	private Long companyId;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		avmTrxId = (UUID) arg.get("avmTrxId");
		companyId = (Long) arg.get("companyId");
		getApprovalHistory(avmTrxId);
		getSelf().setClosable(true);
		getSelf().setMaximized(false);
		getSelf().setWidth("60%");
		getSelf().doModal();
	}
	
	private void getApprovalHistory(UUID avmTrxId) {
		try {
			List<AVMApprovalHistory> listHistory = avmAdapterServiceImpl.getApprovalHistoryByAVMTrxId(avmTrxId);
			ListModelList<AVMApprovalHistory> listModel = new ListModelList<AVMApprovalHistory>(listHistory);
			lbxApprovalHistory.setModel(listModel);
			lbxApprovalHistory.setItemRenderer(getItemRenderer());
		} catch (FifException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<AVMApprovalHistory> getItemRenderer() {
		ListitemRenderer<AVMApprovalHistory> listitemRenderer = new SerializableListItemRenderer<AVMApprovalHistory>() {

			private static final long serialVersionUID = 6176838159341552044L;

			@Override
			public void render(Listitem item, final AVMApprovalHistory data, final int index)
					throws Exception {
				new Listcell(avmAdapterServiceImpl.getActualName(data, companyId)).setParent(item);
				String actionTime = data.getAvmActionTimeStamp() != null ? FormatDateUI.formatDateTime(data.getAvmActionTimeStamp()) : "";
				new Listcell(actionTime).setParent(item);
				new Listcell(ActionType.fromCode(data.getAvmActionType()).getMessage()).setParent(item);
				new Listcell(data.getRemarks()).setParent(item);
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = #btnClose")
	public void onClose() {
		getSelf().onClose();
	}

}

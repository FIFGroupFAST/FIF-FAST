package co.id.fifgroup.ssoa.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.domain.AVMOutgoingReport;
import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

@VariableResolver(DelegatingVariableResolver.class)
public class ApproverPopupComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(ApproverPopupComposer.class);

	private ApproverPopupComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	Map<String, Object> arg;
	
	@Wire
	private Window winApproverPopup;
	
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	
	@Wire 
	private Listbox lbxApprovalHistory;
	
	private AVMOutgoingReport avmOutgoingReport;
	
	private Long companyId;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateData();
	}
	
	public void populateData() {
		UUID avmTrxId = (UUID) arg.get("avmTrxId");
		companyId = (Long) arg.get("companyId");
		getApprovalHistory(avmTrxId);
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

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApprovalHistory data, final int index)
					throws Exception {
				
				new Listcell((index+1)+"").setParent(item);
				new Listcell(avmAdapterServiceImpl.getActualName(data, companyId)).setParent(item);
				new Listcell(ActionType.fromCode(data.getAvmActionType()).getMessage()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmReceivedTimeStamp())).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmActionTimeStamp())).setParent(item);
											
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = #btnClose")
	public void modalImage(Event e) {
		winApproverPopup.detach();
	}
	
}

package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.systemworkflow.constants.ActionType;
import co.id.fifgroup.systemworkflow.constants.FieldPermissionsWorkflow;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;

import co.id.fifgroup.avm.domain.AVMApprovalProcessData;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowCompletedTaskComposer extends SelectorComposer<Window> {
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowCompletedTaskComposer.class);

	private WorkflowCompletedTaskComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	@WireVariable("arg")
	private Map<String, Object> arg;

	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	@Wire
	private Textbox txtSubject;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Listbox cmbTrxStatus;
	@Wire
	private Datebox dtbStartDate;
	@Wire
	private Datebox dtbEndDate;
	@Wire
	private Listbox lbxCompletedTask;
	
	private ListModelList<AVMApprovalProcessData> dataModelList = new ListModelList<AVMApprovalProcessData>();
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
	}
	
	public void initComponent() {
		lbxCompletedTask.setMold("paging");
		lbxCompletedTask.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbStartDate.setFormat(FormatDateUI.getDateFormat());
		dtbEndDate.setFormat(FormatDateUI.getDateFormat());
		getTransactionType();
		getTransactionStatus();
	}
	
	@Listen("onDownloadUserManual = #winWorkflowApprovedTask")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void getTransactionType() {
		cmbTrxType.setMold("select");
		ListModelList<TrxType> model = new ListModelList<TrxType>(TrxType.values());
		model.remove(TrxType.DEFAULT);
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
	
	private void getTransactionStatus() {
		cmbTrxStatus.setMold("select");
		ListModelList<TrxStatus> model = new ListModelList<TrxStatus>(TrxStatus.values());
		cmbTrxStatus.setModel(model);
		cmbTrxStatus.setItemRenderer(new SerializableListItemRenderer<TrxStatus>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, TrxStatus data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getMessage()));
			}
		});
		cmbTrxStatus.renderAll();
		cmbTrxStatus.setSelectedIndex(0);
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtSubject")
	public void doFind() {
		if (txtSubject.getValue().isEmpty() && cmbTrxType.getSelectedIndex() == 0
				&& cmbTrxStatus.getSelectedIndex() == 0 && dtbStartDate.getValue() == null && dtbEndDate.getValue() == null) {
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
		List<AVMApprovalProcessData> dataList = new ArrayList<AVMApprovalProcessData>();
		try {
			int trxType = 0;
			if (cmbTrxType.getSelectedIndex() != -1 && !((TrxType) cmbTrxType.getSelectedItem().getValue()).equals(TrxType.SELECT))
				trxType = ((TrxType) cmbTrxType.getSelectedItem().getValue()).getCode().intValue();
			int trxStatus = -3;
			if (cmbTrxStatus.getSelectedIndex() != -1 && !((TrxStatus) cmbTrxStatus.getSelectedItem().getValue()).equals(TrxStatus.SELECT))
				trxStatus = ((TrxStatus) cmbTrxStatus.getSelectedItem().getValue()).getCode();
			Date dateFrom = dtbStartDate.getValue() != null ? DateUtil.truncate(dtbStartDate.getValue()) : null;
			Date dateTo = dtbEndDate.getValue() != null ? DateUtil.truncate(dtbEndDate.getValue()) : null;
			
			dataList = avmAdapterServiceImpl.getCompletedTaskByApproverId(GlobalVariable.hasPermission(FieldPermissionsWorkflow.SHOW_ALL_DATA_COMPLETED_TASK) ? null : securityServiceImpl.getSecurityProfile().getPersonUUID(), txtSubject.getValue(), trxType, trxStatus, dateFrom, dateTo);
			dataModelList = new ListModelList<AVMApprovalProcessData>(dataList);
			lbxCompletedTask.setModel(dataModelList);
			lbxCompletedTask.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<AVMApprovalProcessData> getItemRenderer() {
		ListitemRenderer<AVMApprovalProcessData> listitemRenderer = new SerializableListItemRenderer<AVMApprovalProcessData>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApprovalProcessData data, final int index)
					throws Exception {
				
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(TrxType.fromCode((long) data.getTrxType()).getMessage()).setParent(item);
				new Listcell(TrxStatus.fromCode(data.getTrxStatus()).getMessage()).setParent(item);
				new Listcell(ActionType.fromCode(data.getActionType()).getMessage()).setParent(item);
				new Listcell(data.getRemarks()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getAvmActionTimestamp())).setParent(item);				
			}
		};
		
		return listitemRenderer;
	}
}

package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.domain.ApproverMapping;
import co.id.fifgroup.systemworkflow.service.ApproverMappingService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.util.PopupJobRoleBandbox;

import co.id.fifgroup.avm.domain.AVMApplicationData;
import co.id.fifgroup.avm.domain.AVMApprovalHistory;
import co.id.fifgroup.avm.util.UUIDUtil;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.service.WorkflowLookupServiceAdapter;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowPendingApprovalInquiryComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowPendingApprovalInquiryComposer.class);

	private WorkflowPendingApprovalInquiryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient WorkflowLookupServiceAdapter workflowLookupServiceAdapterImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ApproverMappingService approverMappingServiceImpl;
	
	@Wire
	private Listbox cmbCompany;
	@Wire
	private PopupJobRoleBandbox bnbJob;
	@Wire
	private CommonPopupDoubleBandbox bnbApproverName;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Datebox dtbStartDate;
	@Wire
	private Datebox dtbEndDate;
	@Wire
	private Listbox lbxPendingApproval;
	
	private ListModelList<AVMApplicationData> dataModelList = new ListModelList<AVMApplicationData>();
	private UUID searchApproverId = null; 
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
	}
	
	@Listen("onDownloadUserManual = #winWorkflowPendingApprovalInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	public void initComponent() {
		lbxPendingApproval.setMold("paging");
		lbxPendingApproval.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbStartDate.setFormat(FormatDateUI.getDateFormat());
		dtbEndDate.setFormat(FormatDateUI.getDateFormat());
		getCompany();
		getTransactionType();
		loadApprover();
	}
	
	private void getCompany() {
		cmbCompany.setMold("select");
		List<Company> companies = new ArrayList<Company>();
				
		Company select = new Company();
		select.setCompanyId(ScopeType.SELECT.getValue());
		select.setCompanyName(ScopeType.SELECT.getDesc());
		
		CompanyExample example = new CompanyExample();
		example.createCriteria().andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		example.setOrderByClause("UPPER(COMPANY_NAME) ASC");
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
		
		companies.add(select);
		companies.addAll(listCompany);
		
		ListModelList<Company> listModelCompany = new ListModelList<Company>(companies);
		cmbCompany.setModel(listModelCompany);
		cmbCompany.setItemRenderer(new SerializableListItemRenderer<Company>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, Company data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getCompanyName()));
			}
		});
		cmbCompany.renderAll();
		listModelCompany.addToSelection(select);
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
	
	private void loadApprover() {
		bnbApproverName.setConcatValueDescription(true);
		bnbApproverName.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<KeyValue> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				return workflowLookupServiceAdapterImpl.getAllEmployeeCriteria(searchCriteria1, searchCriteria2, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				return workflowLookupServiceAdapterImpl.countAllEmployeeCriteria(searchCriteria1, searchCriteria2);	
			}

			@Override
			public void mapper(KeyValue keyValue, KeyValue data) {
				keyValue.setKey((UUID) data.getKey());
				keyValue.setValue(data.getValue().toString());
				keyValue.setDescription(data.getDescription().toString());
			}
		});
	}
	
	@Listen("onClick = button#btnFind; onOK = #txtApproverName")
	public void doFind() {
		if (cmbCompany.getSelectedIndex() == 0 && bnbJob.getKeyValue() == null && bnbApproverName.getKeyValue() == null && cmbTrxType.getSelectedIndex() == 0
				&& dtbStartDate.getValue() == null && dtbEndDate.getValue() == null) {
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
		List<AVMApplicationData> dataList = new ArrayList<AVMApplicationData>();
		try {
			int companyId = 0;
			if (cmbCompany.getSelectedIndex() != -1 && (((Company) cmbCompany.getSelectedItem().getValue()).getCompanyId() != null))
				companyId = ((Company) cmbCompany.getSelectedItem().getValue()).getCompanyId().intValue();
			int trxType = 0;
			if (cmbTrxType.getSelectedIndex() != -1 && !((TrxType) cmbTrxType.getSelectedItem().getValue()).equals(TrxType.SELECT))
				trxType = ((TrxType) cmbTrxType.getSelectedItem().getValue()).getCode().intValue();
			Date dateFrom = dtbStartDate.getValue() != null ? DateUtils.truncate(dtbStartDate.getValue(), Calendar.DATE) : null;
			Date dateTo = dtbEndDate.getValue() != null ? DateUtils.truncate(dtbEndDate.getValue(), Calendar.DATE) : null;
			
			KeyValue approver = null;
			if (bnbApproverName.getKeyValue() != null) {
				approver = bnbApproverName.getKeyValue();
			}
			
			KeyValue keyValue = bnbJob.getKeyValue();
			if (keyValue != null) {
				try {
					searchApproverId = (UUID) keyValue.getKey();
				} catch (Exception e) {
					ApproverMapping approverMapping = approverMappingServiceImpl.getApproverMappingByName(bnbJob.getKeyValue().getKey().toString());
					if (approverMapping != null) {
						searchApproverId = approverMapping.getApproverId();
					}
				}
			}
						
			dataList = avmAdapterServiceImpl.getPendingApprovalByApproverIdAndTrxType(searchApproverId, null, trxType, companyId, dateFrom, dateTo, approver);
			dataModelList = new ListModelList<AVMApplicationData>(dataList);
			dataModelList.setMultiple(true);
			lbxPendingApproval.setModel(dataModelList);
			lbxPendingApproval.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<AVMApplicationData> getItemRenderer() {
		ListitemRenderer<AVMApplicationData> listitemRenderer = new SerializableListItemRenderer<AVMApplicationData>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final AVMApplicationData data, final int index)
					throws Exception {
				
				new Listcell("").setParent(item);
				
				String companyName = "";
				CompanyExample example = new CompanyExample();
				example.createCriteria().andCompanyIdEqualTo(data.getCompanyId())
					.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
					.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
				List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
				if (listCompany.size() > 0) {
					companyName = listCompany.get(0).getCompanyName();
				}
				
				new Listcell(companyName).setParent(item);
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(TrxType.fromCode((long) data.getTrxType()).getMessage()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getActionTime())).setParent(item);
				
				AVMApprovalHistory recentApprover = avmAdapterServiceImpl.getRecentApprovalHistory(data.getAvmTrxId(), data.getSequenceNumber());
				new Listcell(avmAdapterServiceImpl.getActualName(recentApprover, data.getCompanyId())).setParent(item);
				new Listcell(DateUtil.getProgressTime(recentApprover.getAvmReceivedTimeStamp(), new Date())).setParent(item);
				
				AVMApprovalHistory lastApproverResponse = avmAdapterServiceImpl.getLastApprovalHistory(data.getAvmTrxId());
				if (lastApproverResponse != null) {
					new Listcell(avmAdapterServiceImpl.getActualName(lastApproverResponse, data.getCompanyId())).setParent(item);
					new Listcell(lastApproverResponse.getRemarks()).setParent(item);					
				} else {
					new Listcell("").setParent(item);
					new Listcell("").setParent(item);
				}
								
			}
		};
		
		return listitemRenderer;
	}	
	
	@Listen("onClick = button#btnReassign")
	public void doReassign() {
		if (dataModelList.getSelection().size() > 0) {
			params = new HashMap<String, Object>();
			params.put("selected", dataModelList.getSelection());
			Executions.createComponents("~./hcms/workflow/workflow_confirmation_reassign.zul", getSelf().getParent(), params);
			getSelf().detach();
		}		
	}		
}

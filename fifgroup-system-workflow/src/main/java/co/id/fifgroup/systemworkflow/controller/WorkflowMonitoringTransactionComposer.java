package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.systemworkflow.constants.TrxStatus;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.dto.AVMOutgoingReportDTO;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.WorkflowUpdateTransactionService;

import co.id.fifgroup.avm.constants.TransactionStatusType;
import co.id.fifgroup.avm.util.Serialization;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowMonitoringTransactionComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(WorkflowMonitoringTransactionComposer.class);

	private WorkflowMonitoringTransactionComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient WorkflowUpdateTransactionService workflowUpdateTransactionServiceImpl;
	
	@Wire
	private Listbox cmbCompany;
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
	private Listbox lbxTransaction;
	
	ListModelList<AVMOutgoingReportDTO> dataModelList = new ListModelList<AVMOutgoingReportDTO>();
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
	} 
	
	@Listen("onDownloadUserManual = #winWorkflowMonitoringTransaction")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	public void initComponent() {
		lbxTransaction.setMold("paging");
		lbxTransaction.setPageSize(GlobalVariable.getMaxRowPerPage());
		
		dtbStartDate.setFormat(FormatDateUI.getDateFormat());
		dtbEndDate.setFormat(FormatDateUI.getDateFormat());
		
		getTransactionType();
		getCompany();
		getTransactionStatus();
	}
	
	private void getCompany() {
		cmbCompany.setMold("select");
		List<Company> companies = new ArrayList<Company>();
				
		Company select = new Company();
		select.setCompanyId(ScopeType.SELECT.getValue());
		select.setCompanyName(ScopeType.SELECT.getDesc());
		
		CompanyExample example = new CompanyExample();
		example.createCriteria().andEffectiveStartDateLessThanOrEqualTo(new Date())
			.andEffectiveEndDateGreaterThanOrEqualTo(new Date());
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

			private static final long serialVersionUID = -3697093348192239699L;

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

			private static final long serialVersionUID = -1402738026833001938L;

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
		if (cmbCompany.getSelectedIndex() == 0 && txtSubject.getValue().isEmpty() && cmbTrxStatus.getSelectedIndex() == 0 && cmbTrxType.getSelectedIndex() == 0
				&& dtbStartDate.getValue() == null && dtbEndDate.getValue() == null) {
			Messagebox.show(Labels.getLabel("common.searchMightBeSlow"), "Question", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new SerializableEventListener<Event>() {				

				private static final long serialVersionUID = -4624019833544001284L;

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
		List<AVMOutgoingReportDTO> dataList = new ArrayList<AVMOutgoingReportDTO>();
		try {
			int companyId = 0;
			if (cmbCompany.getSelectedIndex() != -1 && (((Company) cmbCompany.getSelectedItem().getValue()).getCompanyId() != null))
				companyId = ((Company) cmbCompany.getSelectedItem().getValue()).getCompanyId().intValue();
			int trxType = 0;
			if (cmbTrxType.getSelectedIndex() != -1 && !((TrxType) cmbTrxType.getSelectedItem().getValue()).equals(TrxType.SELECT))
				trxType = ((TrxType) cmbTrxType.getSelectedItem().getValue()).getCode().intValue();
			int trxStatus = -3;
			if (cmbTrxStatus.getSelectedIndex() != -1 && !((TrxStatus) cmbTrxStatus.getSelectedItem().getValue()).equals(TrxStatus.SELECT))
				trxStatus = ((TrxStatus) cmbTrxStatus.getSelectedItem().getValue()).getCode();
			Date dateFrom = dtbStartDate.getValue() != null ? DateUtil.truncate(dtbStartDate.getValue()) : null;
			Date dateTo = dtbEndDate.getValue() != null ? DateUtil.truncate(dtbEndDate.getValue()) : null;
			
			dataList = avmAdapterServiceImpl.getAVMOutgoingReport(null, trxType, txtSubject.getValue(), companyId, trxStatus, dateFrom, dateTo);
			dataModelList = new ListModelList<AVMOutgoingReportDTO>(dataList);
			dataModelList.setMultiple(true);
			lbxTransaction.setModel(dataModelList);
			lbxTransaction.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ListitemRenderer<AVMOutgoingReportDTO> getItemRenderer() {
		ListitemRenderer<AVMOutgoingReportDTO> listitemRenderer = new SerializableListItemRenderer<AVMOutgoingReportDTO>() {

			private static final long serialVersionUID = -1325951035377035886L;

			@Override
			public void render(Listitem item, final AVMOutgoingReportDTO data, final int index)
					throws Exception {
				
				new Listcell("").setParent(item);
				String src = Labels.getLabel("image.link.success");
				if (data.getFlagSuccess() == 0) {
					src = Labels.getLabel("image.link.failed");
				} 
				Image image = new Image(src);
				Listcell lcImage = new Listcell();
				lcImage.setLabel(" ");
				image.setParent(lcImage);
				lcImage.setParent(item);
				
				String companyName = "";
				CompanyExample example = new CompanyExample();
				example.createCriteria().andCompanyIdEqualTo(data.getCompanyId())
					.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
					.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
				List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
				if (listCompany.size() > 0) {
					companyName = listCompany.get(0).getCompanyName();
				}
				final String company = companyName;
				
				new Listcell(companyName).setParent(item);
				new Listcell(data.getSubject()).setParent(item);
				new Listcell(data.getTransactionName()).setParent(item);
				new Listcell(TrxStatus.fromCode(data.getTrxStatus()).getMessage()).setParent(item);
				final UUID submitterId = avmAdapterServiceImpl.getTransactionSubmitter(data.getAvmTrxId());
				String fullName = avmAdapterServiceImpl.getName(submitterId, data.getCompanyId());
				final String submitterName = fullName; 
				new Listcell(fullName).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getSubmittedTime())).setParent(item);
				final Date completedTime = avmAdapterServiceImpl.getLastCompletedActionApproval(data.getAvmTrxId());
				new Listcell(FormatDateUI.formatDateTime(completedTime)).setParent(item);
				
				Hbox hbox = new Hbox();
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
//				link.setDisabled(disabledHyperlinkDetail);
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("companyName", company);
						params.put("selected", data);
						params.put("completedTime", completedTime);
						params.put("submitterName", submitterName);
						Executions.createComponents("~./hcms/workflow/workflow_transaction_approval_detail.zul", getSelf().getParent(), params);
						getSelf().detach();
					}
				});
				
				link.setParent(hbox);
				
				if (data.getTrxStatus() == 0) {
					A update = new A(Labels.getLabel("common.update"));
					update.setStyle("color:blue");
					update.addEventListener("onClick", new SerializableEventListener<Event>() {

						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event arg0) throws Exception {
							String url = workflowUpdateTransactionServiceImpl.getUrl((long) data.getTrxType());
							if (url != null) {
								params = new HashMap<String, Object>();
								Object application = Serialization
										.deserializedObject(data.getSerializedData());
								params.put("applicationData", application);
								Window window = (Window) Executions.createComponents(
										workflowUpdateTransactionServiceImpl.getUrl((long) data.getTrxType()), getSelf().getParent(), params);
								window.setClosable(true);
								window.setMaximized(false);
								window.setWidth("80%");
								window.setHeight("600px");
								window.setContentStyle("overflow:auto");
								window.doModal();
							} else {
								Messagebox.show("Link update not available for this transaction");
							}
							
						}
					});
					Space space = new Space();
					space.setWidth("10px");
					space.setParent(hbox);
					update.setParent(hbox);
				}
				
				Listcell lc = new Listcell();
				hbox.setParent(lc);
				lc.setParent(item);				
			}
		};
		
		return listitemRenderer;
	}
	
	@Listen("onClick = button#btnCancelTask")
	public void cancelTransaction() {
		if (dataModelList.getSelection().size() > 0) {
			if (doValidateTransactionStatus(dataModelList.getSelection())) {
				params = new HashMap<String, Object>();
				params.put("selected", dataModelList.getSelection());
				Executions.createComponents("~./hcms/workflow/workflow_confirmation_cancel_transaction.zul", getSelf().getParent(), params);
				getSelf().detach();
			} else {
				Messagebox.show(Labels.getLabel("swf.err.validateTransactionStatus"));
			}
		}
	}
	
	private boolean doValidateTransactionStatus(Set<AVMOutgoingReportDTO> listTrx) {
		Iterator<AVMOutgoingReportDTO> iterator = new LinkedHashSet<AVMOutgoingReportDTO>(listTrx).iterator();
		while (iterator.hasNext()) {
			AVMOutgoingReportDTO trx = iterator.next();
			if (trx.getTrxStatus() != TransactionStatusType.IN_PROGRESS.getCode()) {
				return false;
			}
		}
		return true;
	}
}

package co.id.fifgroup.systemworkflow.controller;

import java.util.ArrayList;
import java.util.Date;
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
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingHeaderDTO;
import co.id.fifgroup.systemworkflow.service.ApprovalModelMappingService;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchy;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;

import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalModelMappingInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalModelMappingInquiryComposer.class);
	@WireVariable("arg")
	private Map<String, Object> arg;
	private WorkflowApprovalModelMappingInquiryComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable(rewireOnActivate=true)
	private transient ApprovalModelMappingService approvalModelMappingServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationHierarchySetupService organizationHierarchySetupServiceImpl;
	
	@Wire
	private Listbox cmbScope;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Listbox cmbHierarchy;
	@Wire
	private Datebox dtbEffectiveOnDate;
	@Wire
	private Listbox lbxApprovalModelMapping;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
	}
	
	@Listen("onDownloadUserManual = #winWorkflowApprovalModelMappingInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	public void initComponent() {
		lbxApprovalModelMapping.setMold("paging");
		lbxApprovalModelMapping.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setFormat(FormatDateUI.getDateFormat());
		getTransactionType();
		getScope();
		getHierarchy();
	}
	
	private void getHierarchy() {
		cmbHierarchy.setMold("select");
		List<OrganizationHierarchy> hierarchy = new ArrayList<OrganizationHierarchy>();
		OrganizationHierarchy orgSelect = new OrganizationHierarchy();
		orgSelect.setId(null);
		orgSelect.setOrgHierName("- Select -");
		
		OrganizationHierarchy orgDefault = new OrganizationHierarchy();
		orgDefault.setId((long) 0);
		orgDefault.setOrgHierName("Default");
		
		hierarchy.add(orgSelect);
		hierarchy.add(orgDefault);

		OrganizationHierarchyExample example = new OrganizationHierarchyExample();
		example.createCriteria().andOrgHierTypeEqualTo("Functional");
		List<OrganizationHierarchy> hierarchy2 = organizationHierarchySetupServiceImpl.findByExample(example);
		hierarchy.addAll(hierarchy2);
		
		ListModelList<OrganizationHierarchy> listModel = new ListModelList<OrganizationHierarchy>(hierarchy);
		cmbHierarchy.setModel(listModel);
		cmbHierarchy.setItemRenderer(new SerializableListItemRenderer<OrganizationHierarchy>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, OrganizationHierarchy data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getOrgHierName()));
			}
		});
		cmbHierarchy.renderAll();
		listModel.addToSelection(orgSelect);	
	}
	
	private void getScope() {
		cmbScope.setMold("select");
		List<Company> companies = new ArrayList<Company>();
		Company common = new Company();
		common.setCompanyId(ScopeType.COMMON.getValue());
		common.setCompanyName(ScopeType.COMMON.getDesc());
		
		Company select = new Company();
		select.setCompanyId(ScopeType.SELECT.getValue());
		select.setCompanyName(ScopeType.SELECT.getDesc());
		
		CompanyExample example = new CompanyExample();
		example.createCriteria().andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		example.setOrderByClause("UPPER(COMPANY_NAME) ASC");
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
		
		companies.add(select);
		companies.add(common);
		companies.addAll(listCompany);
		
		ListModelList<Company> listModelCompany = new ListModelList<Company>(companies);
		cmbScope.setModel(listModelCompany);
		cmbScope.setItemRenderer(new SerializableListItemRenderer<Company>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, Company data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getCompanyName()));
			}
		});
		cmbScope.renderAll();
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
	
	@Listen("onClick = button#btnFind")
	public void doFind() {
		if (cmbScope.getSelectedIndex() == 0 && cmbHierarchy.getSelectedIndex() == 0 && cmbTrxType.getSelectedIndex() == 0
				&& dtbEffectiveOnDate.getValue() == null) {
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
		try {
			Long scopeId = cmbScope.getSelectedIndex() != -1 ? ((Company) cmbScope.getSelectedItem().getValue()).getCompanyId() : null;
			TrxType trxType = cmbTrxType.getSelectedIndex() != -1 ? (TrxType) cmbTrxType.getSelectedItem().getValue() : null;
			Long orgHierId = cmbHierarchy.getSelectedIndex() != -1 ? ((OrganizationHierarchy) cmbHierarchy.getSelectedItem().getValue()).getId() : null;
			Date effectiveOnDate = dtbEffectiveOnDate.getValue();
			List<ApprovalModelMappingHeaderDTO> listApprovalMappingHeader = approvalModelMappingServiceImpl.getApprovalModelMappingHeaderByCriteria(scopeId, trxType, orgHierId, effectiveOnDate);
			ListModelList<ApprovalModelMappingHeaderDTO> listModelApprovalModelMappingHeaders = new ListModelList<ApprovalModelMappingHeaderDTO>(listApprovalMappingHeader);
			lbxApprovalModelMapping.setModel(listModelApprovalModelMappingHeaders);
			lbxApprovalModelMapping.setItemRenderer(getItemRenderer());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	} 
	
	public ListitemRenderer<ApprovalModelMappingHeaderDTO> getItemRenderer() {
		ListitemRenderer<ApprovalModelMappingHeaderDTO> listitemRenderer = new SerializableListItemRenderer<ApprovalModelMappingHeaderDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final ApprovalModelMappingHeaderDTO data, final int index)
					throws Exception {
				String orgHierName = "";
				String companyName = "";
				if (data.getScope() == ScopeType.COMMON.getValue()) {
					companyName = "Common";
				} else {
					CompanyExample example = new CompanyExample();
					example.createCriteria().andCompanyIdEqualTo(data.getScope())
						.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
						.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
					List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
					if (listCompany.size() > 0) {
						companyName = listCompany.get(0).getCompanyName();
					}
				}
				data.setCompanyName(companyName);
				if (data.getOrgHierId() == 0) {
					orgHierName = "Default";
				} else {
					OrganizationHierarchyExample example = new OrganizationHierarchyExample();
					example.createCriteria().andIdEqualTo(data.getOrgHierId());
					List<OrganizationHierarchy> org = organizationHierarchySetupServiceImpl.findByExample(example);
					if (org.size() > 0) {
						orgHierName = org.get(0).getOrgHierName();
					}
				}
				data.setOrgHierName(orgHierName);
				new Listcell(companyName).setParent(item);
				data.setTransactionType(TrxType.fromCode(data.getTransactionId()).getMessage());
				new Listcell(data.getTransactionType()).setParent(item);
				new Listcell(orgHierName).setParent(item);
				new Listcell(FormatDateUI.formatDate(data.getEffectiveStartDate())).setParent(item);
				new Listcell(FormatDateUI.formatDate(data.getEffectiveEndDate())).setParent(item);
				new Listcell(data.getUserName()).setParent(item);
				new Listcell(FormatDateUI.formatDateTime(data.getLastUpdatedDate())).setParent(item);
												
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
//				link.setDisabled(disabledHyperlinkDetail);
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("modelMappingHeader", data);
						Executions.createComponents("~./hcms/workflow/workflow_approval_model_mapping.zul", getSelf().getParent(), params);
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
		Executions.createComponents("~./hcms/workflow/workflow_approval_model_mapping.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
}

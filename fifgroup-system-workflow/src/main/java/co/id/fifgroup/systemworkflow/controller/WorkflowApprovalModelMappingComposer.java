package co.id.fifgroup.systemworkflow.controller;

import java.text.DecimalFormat;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.systemworkflow.constants.TrxType;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMappingHeaderDTO;
import co.id.fifgroup.systemworkflow.dto.ApprovalModelMessageMappingDTO;
import co.id.fifgroup.systemworkflow.service.ApprovalModelMappingService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.systemworkflow.service.RuleLookupService;
import co.id.fifgroup.systemworkflow.validation.ApprovalModelMappingValidator;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchy;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import co.id.fifgroup.workstructure.service.OrganizationHierarchySetupService;

import co.id.fifgroup.avm.constants.ConjunctionType;
import co.id.fifgroup.avm.domain.AVM;
import co.id.fifgroup.avm.domain.AVMRuleExpression;
import co.id.fifgroup.avm.domain.AVMRuleLookupDetail;
import co.id.fifgroup.avm.domain.AVMRuleMapping;
import co.id.fifgroup.avm.domain.AVMRuleStatement;
import co.id.fifgroup.avm.manager.AVMRuleEvaluationManager;
import co.id.fifgroup.avm.manager.AVMSetupManager;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkflowApprovalModelMappingComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(WorkflowApprovalModelMappingComposer.class);

	private WorkflowApprovalModelMappingComposer thisComposer = this;
	private Map<String, Object> params = new HashMap<String, Object>();
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient ApprovalModelMappingService approvalModelMappingServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationHierarchySetupService organizationHierarchySetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient RuleLookupService ruleLookupServiceImpl;
	
	@Wire
	private Window winWorkflowApprovalModelMapping;
	@Wire
	private Listbox cmbScope;
	@Wire
	private Listbox cmbTrxType;
	@Wire
	private Listbox cmbHierarchy;
	@Wire
	private Datebox dtbEffectiveFrom;
	@Wire
	private Datebox dtbEffectiveTo;
	@Wire
	private Div errListApprovalModelMappingDetail;
	@Wire
	private Listbox lbxApprovalModelMappingDetail;
	@Wire
	private Button btnAddMapping;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnTop;
	@Wire
	private Button btnUp;
	@Wire
	private Button btnDown;
	@Wire
	private Button btnBottom;
	@Wire
	private Button btnDeleteData;
	
	private ApprovalModelMappingHeaderDTO approvalModelMappingHeader;
	private ApprovalModelMappingDTO approvalModelMappingDto = new ApprovalModelMappingDTO();
	
	private List<ApprovalModelMessageMappingDTO> listApprovalMessageMapping;
	private ListModelList<ApprovalModelMessageMappingDTO> listModelApprovalMessageMapping;
	private ListModelList<TrxType> listModelTrxType; 
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initComponent();
		populateData();
	}
	
	public void initComponent() {
		dtbEffectiveFrom.setFormat(FormatDateUI.getDateFormat());
		dtbEffectiveTo.setFormat(FormatDateUI.getDateFormat());
		
		getTransactionType();
	}
	
	public void disabledComponent(boolean disabled) {
		cmbTrxType.setDisabled(disabled);
		dtbEffectiveFrom.setDisabled(disabled);
		btnAddMapping.setDisabled(disabled);
		btnDelete.setDisabled(disabled);
		btnTop.setDisabled(disabled);
		btnUp.setDisabled(disabled);
		btnDown.setDisabled(disabled);
		btnBottom.setDisabled(disabled);
	}
	
	public void populateData() {
		approvalModelMappingHeader = (ApprovalModelMappingHeaderDTO) arg.get("modelMappingHeader");
		if (approvalModelMappingHeader != null) {
			approvalModelMappingDto.setModelMappingHeader(approvalModelMappingHeader);
			getScope(approvalModelMappingDto.getModelMappingHeader().getScope(), true);
			for (TrxType trxType : listModelTrxType) {
				if (trxType.getCode() != null) {
					if (trxType.getCode().equals(approvalModelMappingDto.getModelMappingHeader().getTransactionId())) {
						listModelTrxType.addToSelection(trxType);
						break;
					}					
				}
			}
			getHirearchy(approvalModelMappingDto.getModelMappingHeader().getOrgHierId(), approvalModelMappingDto.getModelMappingHeader().getScope());
			cmbHierarchy.setDisabled(true);
			dtbEffectiveFrom.setValue(approvalModelMappingDto.getModelMappingHeader().getEffectiveStartDate());
			dtbEffectiveTo.setValue(approvalModelMappingDto.getModelMappingHeader().getEffectiveEndDate());
			listApprovalMessageMapping = approvalModelMappingServiceImpl.getApprovalModelMessageMappingDtoByHeaderId(approvalModelMappingDto.getModelMappingHeader().getModelMappingId());
			if(approvalModelMappingDto.getModelMappingHeader().getEffectiveStartDate().after(DateUtil.truncate(new Date()))) {
				btnDeleteData.setDisabled(false);
			}
			disabledComponent(true);
		} else {
			approvalModelMappingHeader = new ApprovalModelMappingHeaderDTO();
			approvalModelMappingDto.setModelMappingHeader(approvalModelMappingHeader);
			getScope(null, false);
			getHirearchy((long) 0, ScopeType.COMMON.getValue());
			listApprovalMessageMapping = new ArrayList<ApprovalModelMessageMappingDTO>();
			disabledComponent(false);
		}
		listModelApprovalMessageMapping = new ListModelList<ApprovalModelMessageMappingDTO>(listApprovalMessageMapping);
		listModelApprovalMessageMapping.setMultiple(true);
		lbxApprovalModelMappingDetail.setModel(listModelApprovalMessageMapping);
		lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());
	}
	
	private void getHirearchy(Long orgHierId, Long companyId) {
		cmbHierarchy.setMold("select");
		List<OrganizationHierarchy> hierarchy = new ArrayList<OrganizationHierarchy>();
				
		OrganizationHierarchy orgDefault = new OrganizationHierarchy();
		orgDefault.setId((long) 0);
		orgDefault.setOrgHierName("Default");
		
		hierarchy.add(orgDefault);

		OrganizationHierarchyExample example = new OrganizationHierarchyExample();
		example.createCriteria().andOrgHierTypeEqualTo("Functional").andCompanyIdEqualTo(companyId);
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
		listModel.addToSelection(orgDefault);	
	}
	
	private void getScope(Long companyScope, boolean disabled) {
		cmbScope.setMold("select");
		List<Company> companies = new ArrayList<Company>();
		Company common = new Company();
		common.setCompanyId(ScopeType.COMMON.getValue());
		common.setCompanyName(ScopeType.COMMON.getDesc());
		
		CompanyExample example = new CompanyExample();
		example.createCriteria().andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date()))
			.andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		example.setOrderByClause("UPPER(COMPANY_NAME) ASC");
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(example);
		
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
		listModelCompany.addToSelection(common);
		for(Company selectedCompanyScope : listModelCompany) {
			if(selectedCompanyScope.getCompanyId() == companyScope) {
				listModelCompany.addToSelection(selectedCompanyScope);
				break;
			}
		}
		cmbScope.setDisabled(disabled);
	}
		
	private void getTransactionType() {
		cmbTrxType.setMold("select");
		listModelTrxType = new ListModelList<TrxType>(TrxType.values());
		listModelTrxType.remove(TrxType.DEFAULT);
		cmbTrxType.setModel(listModelTrxType);
		cmbTrxType.setItemRenderer(new SerializableListItemRenderer<TrxType>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, TrxType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getMessage()));
			}
		});
		listModelTrxType.addToSelection(TrxType.SELECT);
		cmbTrxType.renderAll();	
	}
	
	@Listen("onSelect = listbox#cmbTrxType")
	public void clearData() {
		approvalModelMappingHeader = new ApprovalModelMappingHeaderDTO();
		approvalModelMappingDto.setModelMappingHeader(approvalModelMappingHeader);
		listApprovalMessageMapping = new ArrayList<ApprovalModelMessageMappingDTO>();
		disabledComponent(false);
		
		listModelApprovalMessageMapping = new ListModelList<ApprovalModelMessageMappingDTO>(listApprovalMessageMapping);
		lbxApprovalModelMappingDetail.setModel(listModelApprovalMessageMapping);
		listModelApprovalMessageMapping.setMultiple(true);
		lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());
	}
	
	@Listen("onSelect = listbox#cmbScope")
	public void onSelectScope() {
		Company company = (Company) cmbScope.getSelectedItem().getValue();
		if (company.getCompanyId().equals(ScopeType.COMMON.getValue())) {
			cmbHierarchy.setDisabled(true);
		} else {
			cmbHierarchy.setDisabled(false);
		}
		getHirearchy((long) 0, ((Company) cmbScope.getSelectedItem().getValue()).getCompanyId());
	}
	
	@Listen("onClick = button#btnAddMapping")
	public void addMapping() {
		TrxType trxTpe = (TrxType) cmbTrxType.getSelectedItem().getValue();
		if (!trxTpe.equals(TrxType.SELECT)) {
			params = new HashMap<String, Object>();
			params.put("parent", thisComposer);
			params.put("trxType", trxTpe);
			Window window = (Window) Executions.createComponents("~./hcms/workflow/workflow_approval_model_mapping_add.zul", getSelf().getParent(), params);
			window.setClosable(true);
			window.setWidth("80%");
			window.doModal();			
		} else {
			Messagebox.show(Labels.getLabel("swf.err.pleaseSelectTrxType"));
		}
	}
	
	@Listen("onClick = button#btnDelete")
	public void deleteMapping() {
		Set<ApprovalModelMessageMappingDTO> selected = listModelApprovalMessageMapping.getSelection();
		if (selected.isEmpty()) {
			return;
		}
		while (selected.iterator().hasNext()) {
			ApprovalModelMessageMappingDTO selectedItem = selected.iterator().next();
			listModelApprovalMessageMapping.remove(selectedItem);			
		}
		
		lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());
	}
	
	@Listen("onClick = button#btnTop")
	public void top() {
		if (listModelApprovalMessageMapping.getSelection().size() == 1) {
		int i = 0;
			Iterator<ApprovalModelMessageMappingDTO> iterator = new LinkedHashSet<ApprovalModelMessageMappingDTO>(listModelApprovalMessageMapping.getSelection()).iterator();
			while (iterator.hasNext()) {
				ApprovalModelMessageMappingDTO selectedItem = iterator.next();
				listModelApprovalMessageMapping.remove(selectedItem);
				listModelApprovalMessageMapping.add(i++, selectedItem);
				listModelApprovalMessageMapping.addToSelection(selectedItem);
			}
			lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());
		}
	}
	
	@Listen("onClick = button#btnUp")
	public void up() {
		Set<ApprovalModelMessageMappingDTO> selected = listModelApprovalMessageMapping.getSelection();
		if (selected.isEmpty()) {
			return;
		}
		if (selected.size() == 1) {
			int index = listModelApprovalMessageMapping.indexOf(selected.iterator().next());
			if (index == 0 || index < 0) {
				return;
			}
			ApprovalModelMessageMappingDTO selectedItem = listModelApprovalMessageMapping.get(index);
			listModelApprovalMessageMapping.remove(selectedItem);
			listModelApprovalMessageMapping.add(--index, selectedItem);
			listModelApprovalMessageMapping.addToSelection(selectedItem);
			lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());			
		}
	}
	
	@Listen("onClick = button#btnDown")
	public void down() {
		Set<ApprovalModelMessageMappingDTO> selected = listModelApprovalMessageMapping.getSelection();
		if (selected.isEmpty()) {
			return;
		}
		if (selected.size() == 1) {
			int index = listModelApprovalMessageMapping.indexOf(selected.iterator().next());
			if (index == listModelApprovalMessageMapping.size() - 1 || index < 0) {
				return;
			}
			ApprovalModelMessageMappingDTO selectedItem = listModelApprovalMessageMapping.get(index);
			listModelApprovalMessageMapping.remove(selectedItem);
			listModelApprovalMessageMapping.add(++index, selectedItem);
			listModelApprovalMessageMapping.addToSelection(selectedItem);
			lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());			
		}
	}
	
	@Listen("onClick = button#btnBottom")
	public void bottom() {
		if (listModelApprovalMessageMapping.getSelection().size() == 1) {
			Iterator<ApprovalModelMessageMappingDTO> iterator = new LinkedHashSet<ApprovalModelMessageMappingDTO>(listModelApprovalMessageMapping.getSelection()).iterator();
			while (iterator.hasNext()) {
				ApprovalModelMessageMappingDTO selectedItem = iterator.next();
				listModelApprovalMessageMapping.remove(selectedItem);
				listModelApprovalMessageMapping.add(selectedItem);
				listModelApprovalMessageMapping.addToSelection(selectedItem);
			}
			lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());			
		}
	}
	
	public ListitemRenderer<ApprovalModelMessageMappingDTO> getItemRenderer() {
		ListitemRenderer<ApprovalModelMessageMappingDTO> listitemRenderer = new SerializableListItemRenderer<ApprovalModelMessageMappingDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final ApprovalModelMessageMappingDTO modelMessageMapping, final int index)
					throws Exception {
				final AVMRuleExpression completeRuleStatement = new AVMRuleExpression();
				new Listcell("").setParent(item);
				new Listcell((index+1) +"").setParent(item);
				String ruleExpression = "";
				List<String> parsedRule = AVMRuleEvaluationManager.unwrapSingleQouteMark(modelMessageMapping.getModelMappingDetail().getConditionRule());
				for (int i = 0; i < parsedRule.size(); i++) {
					int j = i;
					AVMRuleStatement ruleStatement = new AVMRuleStatement();
					ruleStatement.setAvmRuleStatementId(UUID.randomUUID());
					AVMRuleMapping avmRuleMapping = null;
					AVMSetupManager avmSetupManager = (AVMSetupManager) SpringUtil.getBean("avmSetupManager");
					
					String conjunction = "";
					if (parsedRule.size() > 3 && i < parsedRule.size() - 1) {
						if (j > 0) {
							ConjunctionType conjunctionType = ConjunctionType.getConjunctionTypeFromString(parsedRule.get((++i % 4) + j));
							conjunction = conjunctionType.toString();
							ruleStatement.setConjunctionType(conjunctionType);
							j = i;
						}						
					}
					
					try {
						avmRuleMapping = avmSetupManager.getAVMRuleMapping(parsedRule.get((i % 4) + j));
						ruleStatement.setAvmRuleMapping(avmRuleMapping);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					String lookupHeaderId = avmRuleMapping.getLookupHeaderId();
					short withValue = avmRuleMapping.getWithValue();
					String lookupValue = "";
					String operator = "";
					String value = "";
					if (withValue == 1) {
						lookupValue = "\'" + parsedRule.get((++i % 4) + j) + "\'";
						if (avmRuleMapping.getLookupHeaderId().equalsIgnoreCase("Number")) {
							DecimalFormat format = new DecimalFormat("#,##0.00");
							double doubleValue = Double.parseDouble(parsedRule.get((++i % 4) + j));
							value = format.format(doubleValue);
							ruleStatement.setValue(doubleValue);
						} else {
							String lookupName = (avmRuleMapping.getAttributeLabel().replaceAll(" ", "_")).toUpperCase();
							value = parsedRule.get((++i % 4) + j);
							KeyValue keyValue = ruleLookupServiceImpl.getLookupValue(lookupName, value);
							ruleStatement.setValue(value);
							value = (String) keyValue.getDescription();
						}
					} else {
						lookupValue = "\'" + parsedRule.get((++i % 4) + j) + "\'" +
								"\'" + parsedRule.get((++i % 4) + j) + "\'";
					}
					AVMRuleLookupDetail lookupDetail = null;
					operator = lookupValue;
					
					lookupDetail = avmSetupManager.getAVMRuleLookupDetail(lookupHeaderId, lookupValue);
					ruleStatement.setAvmRuleLookupDetail(lookupDetail);
					if (lookupDetail != null) {
						operator = lookupDetail.getDescription();
					}
										
					ruleExpression += conjunction + " " + avmRuleMapping.getAttributeLabel() + " "
								+ operator + " " 
								+ value + " ";
					
					completeRuleStatement.getAvmRuleStatementList().add(ruleStatement);
		 		}
				new Listcell(ruleExpression).setParent(item);
				
				AVM avm = avmAdapterServiceImpl.getAVMById(modelMessageMapping.getModelMappingDetail().getApprovalModel());
				if (avm != null)
					new Listcell(avm.getAvmName()).setParent(item);
				else
					new Listcell("No Approval Model").setParent(item);
				
				A link = new A(Labels.getLabel("common.detail"));
				link.setStyle("color:blue");
				link.addEventListener("onClick", new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event arg0) throws Exception {
						params = new HashMap<String, Object>();
						params.put("parent", thisComposer);
						params.put("modelMapping", modelMessageMapping);
						params.put("completeRuleStatement", completeRuleStatement);
						params.put("index", index);
						params.put("trxType", cmbTrxType.getSelectedItem().getValue());
						
						Window window = (Window)Executions.createComponents(
								"~./hcms/workflow/workflow_approval_model_mapping_add.zul", winWorkflowApprovalModelMapping, params);
						window.setClosable(true);
						window.setWidth("80%");
						window.doModal();
					}
				});
				Listcell lc = new Listcell();
				link.setParent(lc);
				lc.setParent(item);				
			}
		};
		
		return listitemRenderer;
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(cmbScope);
		ErrorMessageUtil.clearErrorMessage(dtbEffectiveFrom);
		ErrorMessageUtil.clearErrorMessage(dtbEffectiveTo);
		ErrorMessageUtil.clearErrorMessage(cmbTrxType);
		ErrorMessageUtil.clearErrorMessage(errListApprovalModelMappingDetail);
	}
	
	@Listen("onClick = button#btnSave")
	public void onSave() {
		String confirmation = approvalModelMappingHeader.getModelMappingId() == null ? Labels.getLabel("common.confirmationSave") : Labels.getLabel("common.confirmationSaveVersion");
		Messagebox.show(confirmation, Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					TrxType trxType =  (TrxType) cmbTrxType.getSelectedItem().getValue();
					approvalModelMappingHeader.setScope(((Company) cmbScope.getSelectedItem().getValue()).getCompanyId());
					approvalModelMappingHeader.setTransactionId(trxType.getCode());
					approvalModelMappingHeader.setOrgHierId(((OrganizationHierarchy) cmbHierarchy.getSelectedItem().getValue()).getId());
					approvalModelMappingHeader.setEffectiveStartDate(dtbEffectiveFrom.getValue() != null ? DateUtil.truncate(dtbEffectiveFrom.getValue()) : null);
					approvalModelMappingHeader.setEffectiveEndDate(DateUtil.truncate(dtbEffectiveTo.getValue()));
					approvalModelMappingDto.setModelMappingHeader(approvalModelMappingHeader);
					approvalModelMappingDto.setListModelMessageMapping(listModelApprovalMessageMapping);
					try {
						clearErrorMessage();
						approvalModelMappingServiceImpl.saveApprovalModelMapping(approvalModelMappingDto);
						Messagebox.show(Labels.getLabel("common.saveSuccessfully"));
						Executions.createComponents("~./hcms/workflow/workflow_approval_model_mapping_inquiry.zul", getSelf().getParent(), null);
						getSelf().detach();
					} catch (ValidationException e) {
						showErrorMessage(e.getConstraintViolations());
					}
				}
			}
		});		
		
	}
	
	private void showErrorMessage(Map<String, String> maps) {
		
		if(maps.get(ApprovalModelMappingValidator.SCOPE_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(cmbScope, 
				maps.get(ApprovalModelMappingValidator.SCOPE_VALIDATE));
		}
		if(maps.get(ApprovalModelMappingValidator.EFFECTIVE_DATE_FROM_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(dtbEffectiveFrom, 
				maps.get(ApprovalModelMappingValidator.EFFECTIVE_DATE_FROM_VALIDATE));
		}
		if(maps.get(ApprovalModelMappingValidator.EFFECTIVE_DATE_TO_VALIDATE) != null) {			
			ErrorMessageUtil.setErrorMessage(dtbEffectiveTo, 
				maps.get(ApprovalModelMappingValidator.EFFECTIVE_DATE_TO_VALIDATE));
		}		
		if(maps.get(ApprovalModelMappingValidator.TRANSACTION_TYPE_VALIDATE) != null) {
			ErrorMessageUtil.setErrorMessage(cmbTrxType, 
				maps.get(ApprovalModelMappingValidator.TRANSACTION_TYPE_VALIDATE));
		}
		if(maps.get(ApprovalModelMappingValidator.LIST_APPROVAL_MODEL_MAPPING_DETAIL_VALIDATE) != null) {
			ErrorMessageUtil.setErrorMessage(errListApprovalModelMappingDetail, 
				maps.get(ApprovalModelMappingValidator.LIST_APPROVAL_MODEL_MAPPING_DETAIL_VALIDATE));
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
					Executions.createComponents("~./hcms/workflow/workflow_approval_model_mapping_inquiry.zul", getSelf().getParent(), null);
					getSelf().detach();
				}
			}
		});	
	}
	
	public void doAfterAddApprovalMapping(ApprovalModelMessageMappingDTO insertedMapping, int index) {
		if (index >= 0) {
			listModelApprovalMessageMapping.remove(index);
			listModelApprovalMessageMapping.add(index, insertedMapping);
		} else {
			listModelApprovalMessageMapping.add(insertedMapping);
		}
		listModelApprovalMessageMapping.setMultiple(true);
		lbxApprovalModelMappingDetail.setModel(listModelApprovalMessageMapping);
		lbxApprovalModelMappingDetail.setItemRenderer(getItemRenderer());
	}
	
	@Listen("onClick = #btnDeleteData")
	public void doDelete() {
		Messagebox.show(Labels.getLabel("common.confirmationDelete"), Labels.getLabel("title.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new SerializableEventListener<Event>() {				
			
			private static final long serialVersionUID = 8478465328123300993L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals("onYes")) {
					approvalModelMappingDto.setListModelMessageMapping(listModelApprovalMessageMapping);
					approvalModelMappingServiceImpl.deleteApprovalModelMapping(approvalModelMappingDto);
					Messagebox.show(Labels.getLabel("common.dataHasBeenDeleted"));
					Executions.createComponents("~./hcms/workflow/workflow_approval_model_mapping_inquiry.zul", getSelf().getParent(), null);
					getSelf().detach();
					
				}
			}
		});		
	}
}

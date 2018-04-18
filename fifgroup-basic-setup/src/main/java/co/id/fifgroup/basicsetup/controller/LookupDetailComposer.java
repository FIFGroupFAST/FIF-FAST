package co.id.fifgroup.basicsetup.controller;

import static co.id.fifgroup.core.validation.ValidationRule.isNumeric;
import static co.id.fifgroup.core.validation.ValidationRule.isValidDateForLookupSetup;
import static co.id.fifgroup.core.validation.ValidationRule.isWord;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.common.LookupCriteriaBasicSetup;
import co.id.fifgroup.basicsetup.common.LookupQuerySet;
import co.id.fifgroup.basicsetup.common.LookupWindowBasicSetup;
import co.id.fifgroup.basicsetup.constant.LookupDataType;
import co.id.fifgroup.basicsetup.constant.LookupParentType;
import co.id.fifgroup.basicsetup.constant.LookupType;
import co.id.fifgroup.basicsetup.constant.QuerySet;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.domain.LookupDependent;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.basicsetup.dto.LookupDependentDetailDTO;
import co.id.fifgroup.basicsetup.dto.LookupDynamicDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.validation.LookupValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class LookupDetailComposer extends SelectorComposer<Window> implements LookupQuerySet {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = LoggerFactory.getLogger(LookupDetailComposer.class);
	
	@WireVariable
	private Map<String, Object> arg = new HashMap<String, Object>();
	
	@Wire
	private Textbox txtLookupName;
	@Wire
	private Textbox txtDesc;
	@Wire
	private Listbox lstType;
	@Wire
	private Listbox lstParent;
	@Wire
	private Listbox lstDataType;
	@Wire
	private Row rowParent;
	@Wire
	private Row rowDataType;
	@Wire
	private Groupbox grbLookupDependent;
	@Wire
	private Groupbox grbLookupDynamic;
	@Wire
	private TabularEntry<LookupDependentDetailDTO> lstLookupDependent;
	@Wire
	private TabularEntry<LookupDynamicDTO> lstLookupDynamic;
	@Wire
	private Div divEmptyLookupDependent;
	@Wire
	private Div divEmptyLookupDynamic;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRowLookupDependent;
	@Wire
	private Button btnDeleteRowLookupDependent;
	@Wire
	private Button btnAddRowLookupDynamic;
	@Wire
	private Button btnDeleteRowLookupDynamic;
	
	private ListModelList<LookupHeader> listModelLookupParentType;
	private ListModelList<LookupType> listModelLookupType;
	private ListModelList<LookupDataType> listModelLookupDataType;
	private ListModelList<LookupDependentDetailDTO> listModelLookupDependent;
	private ListModelList<LookupDynamicDTO> listModelLookupDynamic;
	
	private List<LookupDependentDetailDTO> lookupDependentDetailDtos;
	private List<LookupDynamicDTO> lookupDynamicDtos;
	
	private LookupDTO lookupDto;
	
	private List<LookupHeader> lookupHeaderDependent;
	
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		loadData();
		if(arg.containsKey(LookupDTO.class.getName())) {
			LookupDTO lookupHeaderDto = (LookupDTO) arg.get(LookupDTO.class.getName());
			lookupDto = lookupServiceImpl.getLookupByLookupId(lookupHeaderDto.getLookupId());
			txtLookupName.setValue(lookupDto.getName());
			txtDesc.setValue(lookupDto.getDescription());
			txtLookupName.setReadonly(true);
			for(LookupType lookupType : listModelLookupType) {
				if(lookupDto.getLookupType().equals(lookupType.toString())) {
					listModelLookupType.addToSelection(lookupType);
					break;
				}
			}
			lstType.setDisabled(true);
			if(lookupHeaderDto.getLookupType().equals(LookupType.DEPENDENT.toString())) {
				for(LookupHeader lookupHeader : listModelLookupParentType) {
					if(lookupDto.getParentId() != null && lookupDto.getParentId().equals(lookupHeader.getLookupId())) {
						listModelLookupParentType.addToSelection(lookupHeader);
						break;
					} else if(lookupDto.getParentId() == LookupParentType.NONE.getValue() && lookupDto.getParentId() == lookupHeader.getLookupId()) {
						listModelLookupParentType.addToSelection(lookupHeader);
					}
				}
				for(LookupDataType lookupDataType : listModelLookupDataType) {
					if(lookupDto.getDataType().equals(lookupDataType.toString())) {
						listModelLookupDataType.addToSelection(lookupDataType);
						break;
					}
				}
				lookupDependentDetailDtos = lookupDto.getLookupDependentDetailDtos();
				Iterator<LookupDependentDetailDTO> itrLookupDependentDetailDto = lookupDependentDetailDtos.iterator();
				while(itrLookupDependentDetailDto.hasNext()) {
					LookupDependentDetailDTO lookupDependentDetailDto = itrLookupDependentDetailDto.next();
					if(lookupDependentDetailDto.getDetailCode() == null) {
						itrLookupDependentDetailDto.remove();
					}
				}
				listModelLookupDependent.clear();
				listModelLookupDependent.addAll(lookupDependentDetailDtos);
				grbLookupDependent.setVisible(true);
				rowParent.setVisible(true);
				rowDataType.setVisible(true);
				lstDataType.setDisabled(true);
				lstParent.setDisabled(true);
			} else if(lookupHeaderDto.getLookupType().equals(LookupType.DYNAMIC.toString())) {
				lookupDynamicDtos = lookupDto.getLookupDynamicDtos();
				listModelLookupDynamic.clear();
				listModelLookupDynamic.addAll(lookupDynamicDtos);
				grbLookupDynamic.setVisible(true);
			}
		}

		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			txtLookupName.setDisabled(true);
			txtDesc.setDisabled(true);
			lstType.setDisabled(true);
			lstParent.setDisabled(true);
			lstDataType.setDisabled(true);
			btnAddRowLookupDependent.setDisabled(true);
			btnDeleteRowLookupDependent.setDisabled(true);
			btnAddRowLookupDynamic.setDisabled(true);
			btnDeleteRowLookupDynamic.setDisabled(true);
		}
			
	}
	
	private void init() {
		initListModelLookupParentType();
		initListModelLookupDataType();
		initListModelLookupType();
		buildLookupDependent();
		buildLookupDynamic();
	}
	
	private void loadData() {
		loadDataLookupParentType();
	}
	
	private void initListModelLookupParentType() {
		listModelLookupParentType = new ListModelList<LookupHeader>();
		lstParent.setModel(listModelLookupParentType);
	}
	
	private void loadDataLookupParentType() {
		lookupHeaderDependent = new ArrayList<LookupHeader>();
		
		LookupHeader lookupHeaderSelect = new LookupHeader();
		lookupHeaderSelect.setLookupId(LookupParentType.SELECT.getValue());
		lookupHeaderSelect.setName(LookupParentType.SELECT.getDesc());
		
		LookupHeader lookupHeaderNone = new LookupHeader();
		lookupHeaderNone.setLookupId(LookupParentType.NONE.getValue());
		lookupHeaderNone.setName(LookupParentType.NONE.getDesc());
		
		lookupHeaderDependent.add(lookupHeaderSelect);
		lookupHeaderDependent.add(lookupHeaderNone);		
		lookupHeaderDependent.addAll(lookupServiceImpl.getLookupHeaderDependent());
		
		listModelLookupParentType.clear();
		listModelLookupParentType.addAll(lookupHeaderDependent);
		
		lstParent.renderAll();
		
		listModelLookupParentType.addToSelection(lookupHeaderSelect);
	}
	
	private void initListModelLookupType() {
		listModelLookupType = new ListModelList<LookupType>(LookupType.values());
		lstType.setModel(listModelLookupType);
		lstType.renderAll();
		listModelLookupType.addToSelection(LookupType.SELECT);
	}
	
	private void initListModelLookupDataType() {
		listModelLookupDataType = new ListModelList<LookupDataType>(LookupDataType.values());
		lstDataType.setModel(listModelLookupDataType);
		lstDataType.renderAll();
		listModelLookupDataType.addToSelection(LookupDataType.SELECT);
	}
	
	private void buildLookupDependent() {
		lstLookupDependent.setDataType(LookupDependentDetailDTO.class);
		lstLookupDependent.setModel(getLookupDependentModel());
		lstLookupDependent.setItemRenderer(getListItemRendererLookupDependentDetailDto());
		lstLookupDependent.setValidationRule(getTabularValidationRuleLookupDependentDetailDto());
	}
	
	private void buildLookupDynamic() {
		lstLookupDynamic.setDataType(LookupDynamicDTO.class);
		lstLookupDynamic.setModel(getLookupDynamicModel());
		lstLookupDynamic.setItemRenderer(getListItemRendererLookupDynamicDto());
	}
	
	private ListModelList<LookupDependentDetailDTO> getLookupDependentModel() {
		if(lookupDependentDetailDtos == null) {
			lookupDependentDetailDtos = new ArrayList<LookupDependentDetailDTO>();
		}
		listModelLookupDependent = new ListModelList<LookupDependentDetailDTO>(lookupDependentDetailDtos);
		listModelLookupDependent.setMultiple(true);
		return listModelLookupDependent;
	}
	
	private ListModelList<LookupDynamicDTO> getLookupDynamicModel() {
		if(lookupDynamicDtos == null) {
			lookupDynamicDtos = new ArrayList<LookupDynamicDTO>();
		}
		listModelLookupDynamic = new ListModelList<LookupDynamicDTO>();
		listModelLookupDynamic.setMultiple(true);
		return listModelLookupDynamic;
	}
	
	private SerializableListItemRenderer<LookupDependentDetailDTO> getListItemRendererLookupDependentDetailDto() {
		return new SerializableListItemRenderer<LookupDependentDetailDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final LookupDependentDetailDTO data,
					int index) throws Exception {
				if(data != null) {
					if(data.getParentDetail() == null) {
						data.setParentDetail(new LookupDependent());
					}
					new Listcell().setParent(item);
					
					TextboxListcell<LookupDependentDetailDTO> txtDetailCode = new TextboxListcell<LookupDependentDetailDTO>(data, data.getDetailCode(), "detailCode", data.getDetailCode() != null);
					txtDetailCode.getComponent().setMaxlength(30);
					txtDetailCode.setParent(item);
					
					TextboxListcell<LookupDependentDetailDTO> txtMeaning = new TextboxListcell<LookupDependentDetailDTO>(data, data.getMeaning(), "meaning", false);
					txtMeaning.getComponent().setMaxlength(50);
					txtMeaning.setParent(item);
					
					TextboxListcell<LookupDependentDetailDTO> txtDescription = new TextboxListcell<LookupDependentDetailDTO>(data, data.getDescription(), "description", false);
					txtDescription.getComponent().setMaxlength(255);
					txtDescription.setParent(item);
					final Listbox listboxScope = getComponentScope(data.getCompanyScope(), data.getDetailCode() != null);
					final LookupWindowBasicSetup bnbParentDetailCode = getComponentParentDetailCode(listboxScope, data.getParentDetailCode(), data.getDetailCode() != null);
					
					Listcell listcellScope = new Listcell();
					listboxScope.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 3947651076929167350L;

						@Override
						public void onEvent(Event event) throws Exception {
							Company company = (Company) listboxScope.getSelectedItem().getValue();
							data.setCompanyScope(company.getCompanyId());
							bnbParentDetailCode.setRawValue(null);
							data.setParentDetailCode(null);
						}
					});
					listcellScope.appendChild(listboxScope);
					listcellScope.setParent(item);
					
					Listcell listcellParentDetailCode = new Listcell();
					bnbParentDetailCode.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -5788144121529892249L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue == null) {
								data.setParentDetailCode(null);
							} else {
								data.setParentDetailCode(keyValue.getKey().toString());
							}
						}
					});
					listcellParentDetailCode.appendChild(bnbParentDetailCode);
					listcellParentDetailCode.setParent(item);
					
					if(data.getDateFrom() == null) data.setDateFrom(new Date());
					DateboxListcell<LookupDependentDetailDTO> dtbFrom= new DateboxListcell<LookupDependentDetailDTO>(data, data.getDateFrom(), "dateFrom", false);
					dtbFrom.getComponent().setWidth("100px");
					dtbFrom.setParent(item);
					DateboxListcell<LookupDependentDetailDTO> dtbTo= new DateboxListcell<LookupDependentDetailDTO>(data, data.getDateTo(), "dateTo", false);
					dtbTo.getComponent().setWidth("100px");
					dtbTo.setParent(item);
					
					LookupHeader lookupParent = (LookupHeader) lstParent.getSelectedItem().getValue();
					if((lookupParent.getLookupId() != null && lookupParent.getLookupId().equals(LookupParentType.SELECT.getValue())) || lookupParent.getLookupId() == LookupParentType.NONE.getValue()) {
						bnbParentDetailCode.setDisabled(true);
					}
					
				}
			}
			
		};
	}
	
	private SerializableListItemRenderer<LookupDynamicDTO> getListItemRendererLookupDynamicDto() {
		return new SerializableListItemRenderer<LookupDynamicDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final LookupDynamicDTO data, int index)
					throws Exception {
				if(data != null) {
					if(data.getCompany() == null) {
						data.setCompany(new Company());
					}
					new Listcell().setParent(item);
					
					Listcell listcellScope = new Listcell();
					Label lblScope = new Label(data.getCompany().getCompanyName() == null ? "" : data.getCompany().getCompanyId().equals(-1L) ? ScopeType.COMMON.toString() : data.getCompany().getCompanyName());
					listcellScope.appendChild(lblScope);
					listcellScope.setParent(item);
					
					Listcell listcellAction = new Listcell();
					A linkEdit = new A(Labels.getLabel("common.edit"));
					linkEdit.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -7383644255213305517L;

						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put(LookupDynamicDTO.class.getName(), data);
							params.put(QuerySet.class.getName(), QuerySet.EDIT);
							params.put(LookupQuerySet.class.getName(), LookupDetailComposer.this);
							params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Window winLookupDynamicSetupQuery = (Window) Executions.createComponents("~./hcms/basic-setup/lookup_detail_set_query.zul", null, params);
							winLookupDynamicSetupQuery.doModal();
						}
					
					});
					listcellAction.appendChild(linkEdit);
					listcellAction.setParent(item);
					
				}
			}
		};
	}
	
	private TabularValidationRule<LookupDependentDetailDTO> getTabularValidationRuleLookupDependentDetailDto() {
		return new TabularValidationRule<LookupDependentDetailDTO>() {

			@Override
			public boolean validate(LookupDependentDetailDTO data, List<String> errorRow) {
				if(isNullOrEmpty(data.getDetailCode())) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.detailCode") }));
				} else if(!isWord(data.getDetailCode())) {
					errorRow.add(Labels.getLabel("bse.err.lookupDetailCodeStartWithUnderscodeOrAlphabet"));
				} else if(!isNullOrEmpty(data.getDetailCode())) {
					for(LookupDependentDetailDTO lookupDependentDetailDto : lstLookupDependent.getValue()) {
						if(data.getDetailCode().equals(lookupDependentDetailDto.getDetailCode()) && data != lookupDependentDetailDto) {
							errorRow.add(Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("bse.detailCode") }));
							break;
						}
					}
				}
				if(isNullOrEmpty(data.getMeaning())) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.meaning") }));
				} else {
					LookupDataType selectedLookupDataType = (LookupDataType) lstDataType.getSelectedItem().getValue();
					if(selectedLookupDataType.equals(LookupDataType.SELECT)) {
						errorRow.add(Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.dataType") }));
					} else if(selectedLookupDataType.equals(LookupDataType.NUMERIC)) {
						if(!isNumeric(data.getMeaning())) {
							errorRow.add(Labels.getLabel("common.mustBeNumeric", new Object[] { Labels.getLabel("bse.meaning") }));
						}
					} else if(selectedLookupDataType.equals(LookupDataType.DATE)) {
						if(!isValidDateForLookupSetup(data.getMeaning())) {
							errorRow.add(Labels.getLabel("bse.err.lookupDetailMeaningMustBeValidFormatDate"));
						}
					}
				}
				if(isNullOrEmpty(data.getDescription())) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.description") }));
				}
				if(data.getCompanyScope() == null) {
					errorRow.add(Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.scope") }));
				}
				LookupHeader selectedLookupHeader = (LookupHeader) lstParent.getSelectedItem().getValue();
				if((selectedLookupHeader.getLookupId() != LookupParentType.NONE.getValue() && !selectedLookupHeader.getLookupId().equals(LookupParentType.SELECT.getValue())) && isNullOrEmpty(data.getParentDetailCode())) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.parentDetailCode") }));
				}
				
				if(data.getDateFrom() == null){
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("common.effectiveDateFrom") }));
				}
				
				if(data.getDateTo() != null) {
					if(DateUtil.before(data.getDateTo(), data.getDateFrom())) {
						errorRow.add(Labels.getLabel("bse.err.lookupDetailDateToMustBeGreaterThanOrEqualToDateFrom"));
					}
				}
				if(errorRow.size() > 0) {
					return false;
				}
				return true;
			}
		};
	}
	
	@Listen("onClick = #btnAddRowLookupDependent")
	public void onAddRowLookupDependent() {
		lstLookupDependent.addRow();
	}
	
	@Listen("onClick = #btnDeleteRowLookupDependent")
	public void onDeleteRowLookupDependent() {
		List<LookupDependentDetailDTO> removeableSelection = new ArrayList<LookupDependentDetailDTO>();
		Set<LookupDependentDetailDTO> listLookupDependentDetailDto = listModelLookupDependent.getSelection();
		for(LookupDependentDetailDTO lookupDependentDetailDto : listLookupDependentDetailDto) {
			if(lookupDependentDetailDto.getLookupId() != null) {
				removeableSelection.add(lookupDependentDetailDto);
			}
		}
		if(removeableSelection.size() != 0){
			Messagebox.show(Labels.getLabel("bse.err.existingDataCannotBeDeleted"));
		}else{
			for(LookupDependentDetailDTO lookupDependentDetailDto : removeableSelection) {
				listModelLookupDependent.removeFromSelection(lookupDependentDetailDto);
			}
			lstLookupDependent.deleteRow();
		}
	}
	
	@Listen("onClick = #btnAddRowLookupDynamic")
	public void onAddRowLookupDynamic() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(LookupQuerySet.class.getName(), this);
		params.put(QuerySet.class.getName(), QuerySet.ADD);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Window winLookupDynamicSetupQuery = (Window) Executions.createComponents("~./hcms/basic-setup/lookup_detail_set_query.zul", null, params);
		winLookupDynamicSetupQuery.doModal();
	}
	
	@Listen("onClick = #btnDeleteRowLookupDynamic")
	public void onDeleteRowLookupDynamic() {
		List<LookupDynamicDTO> removeableSelection = new ArrayList<LookupDynamicDTO>();
		Set<LookupDynamicDTO> listLookupDynamicDto = listModelLookupDynamic.getSelection();
		for(LookupDynamicDTO lookupDynamicDto : listLookupDynamicDto) {
			if(lookupDynamicDto.getLookupId() != null) {
				removeableSelection.add(lookupDynamicDto);
			}
		}
		if(removeableSelection.size() != 0){
			Messagebox.show(Labels.getLabel("bse.err.existingDataCannotBeDeleted"));
		}else{
			for(LookupDynamicDTO lookupDynamicDto : removeableSelection) {
				listModelLookupDynamic.removeFromSelection(lookupDynamicDto);
				
			}
			lstLookupDynamic.deleteRow();
		}
	}
	
	private Listbox getComponentScope(Long companyScope, boolean disable) {
		Listbox scope = new Listbox();
		scope.setMold("select");
		List<Company> companies = new ArrayList<Company>();
		Company select = new Company();
		select.setCompanyId(ScopeType.SELECT.getValue());
		select.setCompanyName(ScopeType.SELECT.getDesc());
		Company common = new Company();
		common.setCompanyId(ScopeType.COMMON.getValue());
		common.setCompanyName(ScopeType.COMMON.getDesc());
		
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andGroupIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId())
		.andEffectiveStartDateLessThanOrEqualTo(DateUtils.truncate(new Date(), Calendar.DATE))
		.andEffectiveEndDateGreaterThanOrEqualTo(DateUtils.truncate(new Date(), Calendar.DATE));
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(companyExample);
		
		companies.add(select);
		companies.add(common);
		companies.addAll(listCompany);
		
		ListModelList<Company> listModelCompany = new ListModelList<Company>(companies);
		scope.setModel(listModelCompany);
		scope.setItemRenderer(new SerializableListItemRenderer<Company>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, Company data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getCompanyName()));
			}
		});
		scope.renderAll();
		listModelCompany.addToSelection(select);
		for(Company selectedCompanyScope : listModelCompany) {
			if(selectedCompanyScope.getCompanyId() != null && selectedCompanyScope.getCompanyId().equals(companyScope)) {
				listModelCompany.addToSelection(selectedCompanyScope);
				break;
			}
		}
		scope.setDisabled(disable);
		return scope;
	}
	
	private LookupWindowBasicSetup getComponentParentDetailCode(final Listbox listboxScope, String detailCode, boolean disable) {
		LookupWindowBasicSetup parentDetailCode = new LookupWindowBasicSetup();
		parentDetailCode.setReadonly(true);
		parentDetailCode.setLookupCriteria(new LookupCriteriaBasicSetup() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7561077987046022806L;
			
			@Override
			public Long getScope() {
				Company scope = (Company) listboxScope.getSelectedItem().getValue();
				if(scope.getCompanyId() == ScopeType.SELECT.getValue()) return null;
				return scope.getCompanyId();
			}
			
			@Override
			public String getParentDetailCode() {
				return null;
			}
			
			@Override
			public String getLookupName() {
				LookupHeader selectedLookupHeader = (LookupHeader) lstParent.getSelectedItem().getValue();
				if(selectedLookupHeader.getName().equals(LookupParentType.SELECT.getDesc())) return null;
				if(selectedLookupHeader.getName().equals(LookupParentType.NONE.getDesc())) return "";
				return selectedLookupHeader.getName();
			}

			@Override
			public Long getBusinessGroupId() {
				return securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId();
			}

		});
		if(disable) {
			LookupHeader lookupHeaderParent = (LookupHeader) lstParent.getSelectedItem().getValue();
			if(lookupHeaderParent.getLookupId() != LookupParentType.NONE.getValue() && !lookupHeaderParent.getLookupId().equals(LookupParentType.SELECT.getValue())) {
				try {
					parentDetailCode.setValueByCode(lookupHeaderParent.getName(), securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), detailCode, securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				} catch (Exception e) {
					Messagebox.show(e.getMessage());
				}
			}
		}
		parentDetailCode.setDisabled(disable);
		return parentDetailCode;
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 8495380273415042409L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					clearErrorMessage();
					if(lstLookupDependent.validate()){
						try {
							setLookup();
							if(lookupDto.getLookupType() != null && lookupDto.getLookupType().equals(LookupType.DEPENDENT.toString())) {
								lookupDto.setValidLookupDependent(lstLookupDependent.validate());
							}
							lookupServiceImpl.save(lookupDto);
							Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Executions.createComponents("~./hcms/basic-setup/lookup_inquiry.zul", getSelf().getParent(), param);
							getSelf().detach();
						} catch (ValidationException e) {
							showErrorMessage(e);
							log.error(e.getMessage());
						} catch (Exception e) {
							Messagebox.show("System Error");
							log.error(e.getMessage(), e);
						}
					}
				}
			}
			
		});
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtLookupName);
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(lstType);
		ErrorMessageUtil.clearErrorMessage(lstParent);
		ErrorMessageUtil.clearErrorMessage(lstDataType);
		ErrorMessageUtil.clearErrorMessage(divEmptyLookupDependent);
		ErrorMessageUtil.clearErrorMessage(divEmptyLookupDynamic);
	}
	
	private void showErrorMessage(ValidationException e) {
		Map<String, String> errors = e.getConstraintViolations();
		if(errors.containsKey(LookupValidator.LOOKUP_NAME)) {
			ErrorMessageUtil.setErrorMessage(txtLookupName, e.getMessage(LookupValidator.LOOKUP_NAME));
		}
		if(errors.containsKey(LookupValidator.DESCRIPTION)) {
			ErrorMessageUtil.setErrorMessage(txtDesc, e.getMessage(LookupValidator.DESCRIPTION));
		}
		if(errors.containsKey(LookupValidator.TYPE)) {
			ErrorMessageUtil.setErrorMessage(lstType, e.getMessage(LookupValidator.TYPE));
		}
		if(errors.containsKey(LookupValidator.PARENT)) {
			ErrorMessageUtil.setErrorMessage(lstParent, e.getMessage(LookupValidator.PARENT));
		}
		if(errors.containsKey(LookupValidator.DATA_TYPE)) {
			ErrorMessageUtil.setErrorMessage(lstDataType, e.getMessage(LookupValidator.DATA_TYPE));
		}
		if(errors.containsKey(LookupValidator.LOOKUP_DEPENDENT)) {
			ErrorMessageUtil.setErrorMessage(divEmptyLookupDependent, e.getMessage(LookupValidator.LOOKUP_DEPENDENT));
		}
		if(errors.containsKey(LookupValidator.LOOKUP_DYNAMIC)) {
			ErrorMessageUtil.setErrorMessage(divEmptyLookupDynamic, e.getMessage(LookupValidator.LOOKUP_DYNAMIC));
		}
	}
	
	@Listen ("onClick = button#btnCancel")
	public void onCancel() {
		Messagebox.show(Labels.getLabel("common.confirmationCancel"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5799520384125775408L;

			@Override
			public void onEvent(Event event) throws Exception {
				int resultButton = (int) event.getData();
				if(resultButton == Messagebox.YES) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/basic-setup/lookup_inquiry.zul", getSelf().getParent(), param);
					getSelf().detach();
				}
			}
		});
	}

	@Listen("onSelect = #lstType")
	public void onSelectLookupType() {
		lstParent.setSelectedIndex(0);
		listModelLookupDataType.addToSelection(LookupDataType.SELECT);
		lstLookupDependent.getValue().clear();
		lstLookupDependent.getItems().clear();
		lstLookupDynamic.getValue().clear();
		lstLookupDynamic.getItems().clear();
		clearErrorMessage();
		if(lstType.getSelectedItem().getValue().equals(LookupType.DEPENDENT)) {
			rowParent.setVisible(true);
			rowDataType.setVisible(true);
			grbLookupDependent.setVisible(true);
			grbLookupDynamic.setVisible(false);
		} else if(lstType.getSelectedItem().getValue().equals(LookupType.DYNAMIC)) {
			rowParent.setVisible(false);
			rowDataType.setVisible(false);
			grbLookupDependent.setVisible(false);
			grbLookupDynamic.setVisible(true);
		} else {
			rowParent.setVisible(false);
			rowDataType.setVisible(false);
			grbLookupDependent.setVisible(false);
			grbLookupDynamic.setVisible(false);
		}
	}
	
	@Listen("onSelect = #lstParent")
	public void onSelectParent() {
		List<Listitem> items = lstLookupDependent.getItems();
		for(Listitem item : items) {
			Listbox listboxScope = (Listbox) (item.getChildren().get(4).getChildren().get(0));
			Bandbox bnbParentDetailCode = (Bandbox) (item.getChildren().get(5).getChildren().get(0));
			listboxScope.setSelectedIndex(0);
			bnbParentDetailCode.setRawValue(null);
			LookupHeader lookupParent = (LookupHeader) lstParent.getSelectedItem().getValue();
			Events.postEvent(Events.ON_SELECT, listboxScope, null);
			if(lookupParent.getLookupId() == LookupParentType.NONE.getValue() || (lookupParent.getLookupId() != null && lookupParent.getLookupId().equals(LookupParentType.SELECT.getValue()))) {
				bnbParentDetailCode.setDisabled(true);
			} else {
				bnbParentDetailCode.setDisabled(false);
			}
		}
	}
	
	private void setLookup() {
		if(lookupDto == null) {
			lookupDto = new LookupDTO();
		}
		lookupDto.setName(txtLookupName.getValue().toUpperCase());
		lookupDto.setDescription(txtDesc.getValue());
		lookupDto.setLookupType(((LookupType) lstType.getSelectedItem().getValue()).equals(LookupType.SELECT) ? null : ((LookupType) lstType.getSelectedItem().getValue()).toString());
		lookupDto.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
		if(lookupDto.getLookupId() == null) {
			lookupDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			lookupDto.setCreationDate(new Date());
		}
		lookupDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		lookupDto.setLastUpdateDate(new Date());
		if(((LookupType) lstType.getSelectedItem().getValue()).equals(LookupType.DEPENDENT)) {
			setLookupDependentDto();
		} else if(((LookupType) lstType.getSelectedItem().getValue()).equals(LookupType.DYNAMIC)) {
			setLookupDynamicDto();
		}
	}
	
	private void setLookupDependentDto() {
		if(lookupDto.getLookupDependentDetailDtos() == null) {
			lookupDto.setLookupDependentDetailDtos(new ArrayList<LookupDependentDetailDTO>());
		}
		lookupDto.setParentId(((LookupHeader) lstParent.getSelectedItem().getValue()).getLookupId());
		lookupDto.setDataType(((LookupDataType) lstDataType.getSelectedItem().getValue()).equals(LookupDataType.SELECT) ? null : ((LookupDataType) lstDataType.getSelectedItem().getValue()).toString());
		lookupDto.setParentHeader((LookupHeader) lstParent.getSelectedItem().getValue());
		List<LookupDependentDetailDTO> listValuesTabularEntry = lstLookupDependent.getValue();
		List<LookupDependentDetailDTO> listLookupDetailDependentDto = new ArrayList<LookupDependentDetailDTO>();
		
		for(LookupDependentDetailDTO lookupDetailDependentDto : listValuesTabularEntry) {
			if(lookupDetailDependentDto.getDateTo() == null) lookupDetailDependentDto.setDateTo(DateUtil.MAX_DATE);
			LookupHeader parentLookupHeader = (LookupHeader) lstParent.getSelectedItem().getValue();
			if(lookupDetailDependentDto.getLookupId() == null) {
				lookupDetailDependentDto.setParentHdrId(parentLookupHeader.getLookupId());
				lookupDetailDependentDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				lookupDetailDependentDto.setCreationDate(new Date());
			}
			lookupDetailDependentDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			lookupDetailDependentDto.setLastUpdateDate(new Date());
			lookupDetailDependentDto.setDateFrom(DateUtils.truncate(lookupDetailDependentDto.getDateFrom(), Calendar.DATE));
			lookupDetailDependentDto.setDateTo(DateUtils.truncate(lookupDetailDependentDto.getDateTo(), Calendar.DATE));
			listLookupDetailDependentDto.add(lookupDetailDependentDto);
		}
		
		lookupDto.setLookupDependentDetailDtos(listLookupDetailDependentDto);
	}
	
	private void setLookupDynamicDto() {
		List<LookupDynamicDTO> listLookupDynamicDto = new ArrayList<LookupDynamicDTO>(lstLookupDynamic.getValue());
		for(LookupDynamicDTO lookupDynamicDto : listLookupDynamicDto) {
			if(lookupDynamicDto.getLookupId() == null) {
				lookupDynamicDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				lookupDynamicDto.setCreationDate(new Date());
			}
			lookupDynamicDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			lookupDynamicDto.setLastUpdateDate(new Date());
		}
		lookupDto.setLookupDynamicDtos(listLookupDynamicDto);
	}
	
	@Override
	public void setLookupQuerySet(LookupDynamicDTO lookupDynamicDto, QuerySet querySet) throws Exception {
		if(querySet.equals(QuerySet.ADD)) {
			lookupDynamicDtos.add(lookupDynamicDto);
		}
		listModelLookupDynamic.clear();
		listModelLookupDynamic.addAll(lookupDynamicDtos);
	}

	@Override
	public void checkCompanyScope(Long companyScope, boolean scopeIsChange) throws Exception {
		if(scopeIsChange) {
			for(LookupDynamicDTO lookupDynamicSet : lookupDynamicDtos) {
				if(lookupDynamicSet.getCompanyScope().equals(companyScope)) {
					throw new Exception(Labels.getLabel("bse.err.scopelookupdynamicalreadyexist"));
				}
			}			
		}
	}
	
}

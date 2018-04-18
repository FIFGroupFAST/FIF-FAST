package co.id.fifgroup.basicsetup.controller;

import static co.id.fifgroup.core.validation.ValidationRule.isComponentId;
import static co.id.fifgroup.core.validation.ValidationRule.isIntegerUnsigned;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.constant.OtherInfoDataType;
import co.id.fifgroup.basicsetup.constant.OtherInfoFormName;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.LookupHeaderExample;
import co.id.fifgroup.basicsetup.dto.LookupDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDTO;
import co.id.fifgroup.basicsetup.dto.OtherInfoDetailDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.OtherInfoService;
import co.id.fifgroup.basicsetup.validation.OtherInfoValidator;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.tabularentry.IntboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.core.validation.ValidationException;

@VariableResolver(DelegatingVariableResolver.class)
public class OtherInfoSetupComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4373064548488803096L;

	private static Logger log = LoggerFactory.getLogger(OtherInfoSetupComposer.class);
	
	@Wire
	private Listbox lstFormName;
	@Wire
	private TabularEntry<OtherInfoDetailDTO> lstOtherInfo;
	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OtherInfoService otherInfoServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnAddRowOtherInfo;
	@Wire
	private Button btnDeleteRowOtherInfo;
	@Wire
	private Label lblErrOtherInfo;
	
	private ListModelList<KeyValue> listModelFormName;
	private ListModelList<OtherInfoDetailDTO> listModelOtherInfo;
	
	private OtherInfoDTO otherInfoDto;
	private List<OtherInfoDetailDTO> listOtherInfoDetailDto;
	
	private String lookupName = "MST_FORM_NAME";
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		loadData();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void init() {
		initListModelFormName();
		buildOtherInfo();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnSave.setDisabled(true);
		
		if(!functionPermission.isEditable()){
			btnSave.setDisabled(true);
			lstFormName.setDisabled(true);
			btnAddRowOtherInfo.setDisabled(true);
			btnDeleteRowOtherInfo.setDisabled(true);
		}
	}
	
	private void initListModelFormName() {
		listModelFormName = new ListModelList<KeyValue>();
		lstFormName.setModel(listModelFormName);
	}
	
	private void loadData() {
		loadDataFormName();
	}
	

	@Listen("onDownloadUserManual = #winOtherInfoSetup")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void loadDataFormName() {
		try {
			List<KeyValue> listFormName = new ArrayList<KeyValue>();
			KeyValue keyValue = new KeyValue(null, OtherInfoFormName.SELECT.getDesc());
			listFormName.add(keyValue);
			listFormName.addAll(lookupServiceImpl.getLookups(lookupName, securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId()));
			listModelFormName.clear();
			listModelFormName.addAll(listFormName);
			lstFormName.renderAll();
			listModelFormName.addToSelection(keyValue);
		} catch (Exception e) {
			ErrorMessageUtil.setErrorMessage(lstFormName, e.getMessage() + " : " + lookupName);
			log.error(e.getMessage(), e);
		}
	}
	
	private void buildOtherInfo() {
		lstOtherInfo.setDataType(OtherInfoDetailDTO.class);
		lstOtherInfo.setModel(getOtherInfoModel());
		lstOtherInfo.setItemRenderer(getListItemRendererOtherInfo());
		lstOtherInfo.setValidationRule(getTabularValidationRuleOtherInfo());
	}

	private ListModelList<OtherInfoDetailDTO> getOtherInfoModel() {
		if(listOtherInfoDetailDto == null) {
			listOtherInfoDetailDto = new ArrayList<OtherInfoDetailDTO>();			
		}
		listModelOtherInfo = new ListModelList<OtherInfoDetailDTO>(listOtherInfoDetailDto);
		return listModelOtherInfo;
	}
	
	private SerializableListItemRenderer<OtherInfoDetailDTO> getListItemRendererOtherInfo() {
		return new SerializableListItemRenderer<OtherInfoDetailDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, final OtherInfoDetailDTO data, int index)
					throws Exception {
				if(data != null) {
					
					if(data.getLookupHeader() == null) {
						data.setLookupHeader(new LookupHeader());
					}
					
					new Listcell().setParent(item);
					
					TextboxListcell<OtherInfoDetailDTO> code = new TextboxListcell<OtherInfoDetailDTO>(data, data.getDetailCode(), "detailCode", data.getOtherInfoDtlId() != null);
					code.getComponent().setMaxlength(30);
					code.setParent(item);
					code.getComponent().setWidth("180px");
					
					TextboxListcell<OtherInfoDetailDTO> promptName = new TextboxListcell<OtherInfoDetailDTO>(data, data.getPromptName(), "promptName", data.getOtherInfoDtlId() != null);
					promptName.getComponent().setMaxlength(50);
					promptName.setParent(item);
					promptName.getComponent().setWidth("280px");
					
					if(data.getLookupHeader() != null) {
						data.getLookupHeader().setLookupId(data.getLookupId());
					}
					
					final CommonPopupBandbox bnbLookupName = getComponentLookup(data.getLookupHeader());
					bnbLookupName.setWidth("280px");
					final IntboxListcell<OtherInfoDetailDTO> maxLength = new IntboxListcell<OtherInfoDetailDTO>(data, data.getMaxLength(), "maxLength", data.getOtherInfoDtlId() != null);
					maxLength.getComponent().setWidth("80px");
					final Checkbox chkDisplay = new Checkbox();
					chkDisplay.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -303535789635759667L;

						@Override
						public void onEvent(Event event) throws Exception {
							data.setIsDisplayed(chkDisplay.isChecked());
						}
					});
					
					Listcell listcellDataType = new Listcell();
					final Listbox dataType =getComponentDataType(data.getDataType() == null ? null : OtherInfoDataType.valueOf(data.getDataType()), data.getOtherInfoDtlId() != null);
					dataType.setWidth("80px");
					bnbLookupName.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -303535789635759667L;

						@Override
						public void onEvent(Event event) throws Exception {
							KeyValue keyValue = (KeyValue) event.getData();
							if(keyValue == null) {
								data.setLookupHeader(null);
								data.setLookupId(null);
							} else {
								LookupHeader lookupHeader = (LookupHeader) keyValue.getValue();
								data.setLookupHeader(lookupHeader);
								data.setLookupId(lookupHeader.getLookupId());
							}
						}
					});
					dataType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -303535789635759667L;

						@Override
						public void onEvent(Event event) throws Exception {
							OtherInfoDataType otherInfoDataType = (OtherInfoDataType) dataType.getSelectedItem().getValue();
							data.setDataType(otherInfoDataType.toString());
							data.setLookupHeader(null);
							data.setMaxLength(null);
							
							if(otherInfoDataType.equals(OtherInfoDataType.LOOKUP)) {
								bnbLookupName.setRawValue(null);
								bnbLookupName.setDisabled(false);
							} else {
								bnbLookupName.setRawValue(null);
								bnbLookupName.setDisabled(true);
							}
							if(otherInfoDataType.equals(OtherInfoDataType.TEXT) || otherInfoDataType.equals(OtherInfoDataType.NUMERIC)){
								maxLength.getComponent().setValue(null);
								maxLength.getComponent().setDisabled(false);
							} else {
								maxLength.getComponent().setValue(null);
								maxLength.getComponent().setDisabled(true);
							}
						}
					});
					listcellDataType.appendChild(dataType);
					listcellDataType.setParent(item);
					
					Listcell listcellMandatory = new Listcell();
					final Checkbox chkMandatory = new Checkbox();
					chkMandatory.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -162941781630148566L;

						@Override
						public void onEvent(Event event) throws Exception {
							if(chkMandatory.isChecked()) {
								data.setIsMandatory(true);
								chkDisplay.setChecked(true);
								chkDisplay.setDisabled(true);
								data.setIsDisplayed(true);
							} else {
								data.setIsMandatory(false);
								chkDisplay.setChecked(false);
								chkDisplay.setDisabled(false);
								data.setIsDisplayed(false);
							}
						}
					});
					listcellMandatory.appendChild(chkMandatory);
					listcellMandatory.setParent(item);
					
					Listcell listcellLookup = new Listcell();
					listcellLookup.appendChild(bnbLookupName);
					listcellLookup.setParent(item);
					
					Listcell listcellEnabled = new Listcell();
					final Checkbox chkEnabled = new Checkbox();
					chkEnabled.addEventListener(Events.ON_CHECK, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 7671600502237508355L;

						@Override
						public void onEvent(Event event) throws Exception {
							data.setIsEnabled(chkEnabled.isChecked());
						}
					});
					listcellEnabled.appendChild(chkEnabled);
					listcellEnabled.setParent(item);
					
					Listcell listcellDisplay = new Listcell();
					listcellDisplay.appendChild(chkDisplay);
					listcellDisplay.setParent(item);
					
					OtherInfoDataType otherInfoDataType = (OtherInfoDataType) dataType.getSelectedItem().getValue();
					if(otherInfoDataType.equals(OtherInfoDataType.SELECT)) {
						maxLength.getComponent().setValue(null);
						maxLength.getComponent().setDisabled(true);
					}
					maxLength.getComponent().setMaxlength(3);
					maxLength.setParent(item);
					
					Listcell listcellScope = new Listcell();
					final Listbox listboxScope = getComponentScope(data.getCompanyScope(), data.getOtherInfoDtlId() != null);
					listboxScope.setWidth("280px");
					listboxScope.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

						/**
						 * 
						 */
						private static final long serialVersionUID = -3978704984160713031L;

						@Override
						public void onEvent(Event event) throws Exception {
							Company company = (Company) listboxScope.getSelectedItem().getValue();
							data.setCompanyScope(company.getCompanyId());
						}
					});
					listcellScope.appendChild(listboxScope);
					listcellScope.setParent(item);
					
					if(data.getIsMandatory() == null) {
						data.setIsMandatory(false);
					} else {
						data.setIsMandatory(data.getIsMandatory());
						chkMandatory.setChecked(data.getIsMandatory());
					}
					
					if(data.getIsEnabled() == null) {
						data.setIsEnabled(false);
					} else {
						data.setIsEnabled(data.getIsEnabled());
						chkEnabled.setChecked(data.getIsEnabled());
					}
					
					if(data.getIsDisplayed() == null) {
						data.setIsDisplayed(false);
					} else {
						data.setIsDisplayed(data.getIsDisplayed());
						if(data.getIsDisplayed()) {
							chkDisplay.setChecked(true);
						}
						if(data.getIsMandatory()) {
							chkDisplay.setDisabled(true);
						}
					}
				}
			}
		};
	}
	
	private TabularValidationRule<OtherInfoDetailDTO> getTabularValidationRuleOtherInfo() {
		return new TabularValidationRule<OtherInfoDetailDTO>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3697930972158327545L;

			@Override
			public boolean validate(OtherInfoDetailDTO data, List<String> errorRow) {
				
				if(isNullOrEmpty(data.getDetailCode())) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.code") }));
				} else if(!isComponentId(data.getDetailCode())) {
					errorRow.add(Labels.getLabel("bse.err.otherInfoCodeNotValid"));
				} else if(!isNullOrEmpty(data.getDetailCode())) {
					for(OtherInfoDetailDTO otherInfoDetailDto : lstOtherInfo.getValue()) {
						if(data.getDetailCode().equals(otherInfoDetailDto.getDetailCode()) && data != otherInfoDetailDto) {
							errorRow.add(Labels.getLabel("common.mustBeUnique", new Object[] { Labels.getLabel("bse.code") }));
							break;
						}
					}
				}
				if(isNullOrEmpty(data.getPromptName())) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.promptName") }));
				}
				if(data.getDataType() == null) {
					errorRow.add(Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.dataType") }));
				} else if(data.getDataType().equals(OtherInfoDataType.SELECT.toString())) {
					errorRow.add(Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.dataType") }));
				} else if(data.getDataType().equals(OtherInfoDataType.LOOKUP.toString()) && data.getLookupHeader() == null) {
					errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.lookup") }));
				}
				if(OtherInfoDataType.TEXT.toString().equals(data.getDataType()) || OtherInfoDataType.NUMERIC.toString().equals(data.getDataType())) {
					if(data.getMaxLength() == null) {
						errorRow.add(Labels.getLabel("common.mustBeFill", new Object[] { Labels.getLabel("bse.maxLength") }));
					} else if(data.getMaxLength() > 255) {
						errorRow.add(Labels.getLabel("bse.err.otherInfoMaxlengthCannotGreaterThan255"));
					} else if(data.getMaxLength() < 0) {
						errorRow.add(Labels.getLabel("bse.err.otherInfoMaxlengthCannotLessThanZero"));
					} else if(!isIntegerUnsigned(data.getMaxLength().toString())) {
						errorRow.add(Labels.getLabel("bse.err.otherInfoMaxlengthNotValid"));
					}
				}
				if(data.getCompanyScope() == null) {
					errorRow.add(Labels.getLabel("common.mustBeSelected", new Object[] { Labels.getLabel("bse.scope") }));
				}
				if(errorRow.size() > 0) {
					return false;
				}
				return true;
			}
		};
	}
	
	private Listbox getComponentDataType(OtherInfoDataType otherInfoDataType, boolean disable) {
		Listbox dataType = new Listbox();
		dataType.setMold("select");
		ListModelList<OtherInfoDataType> listModelDataType = new ListModelList<OtherInfoDataType>(OtherInfoDataType.values());
		dataType.setModel(listModelDataType);
		dataType.setItemRenderer(new SerializableListItemRenderer<OtherInfoDataType>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, OtherInfoDataType data, int index)
					throws Exception {
				item.setValue(data);
				item.appendChild(new Listcell(data.getDesc()));
			}
		});
		listModelDataType.addToSelection(OtherInfoDataType.SELECT);
		dataType.renderAll();
		dataType.setDisabled(disable);
		if(otherInfoDataType != null && !otherInfoDataType.equals("")) {
			listModelDataType.addToSelection(otherInfoDataType);
		}
		return dataType;
	}
	
	private CommonPopupBandbox getComponentLookup(LookupHeader lookupHeader) {
		CommonPopupBandbox lookup = new CommonPopupBandbox();
		lookup.setReadonly(true);
		lookup.setDisabled(true);
		lookup.setSearchDelegate(new SerializableSearchDelegate<LookupHeader>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5097573899239880480L;

			@Override
			public List<LookupHeader> findBySearchCriteria(
					String searchCriteria, int limit, int offset) {
				LookupHeaderExample lookupHeaderExample = new LookupHeaderExample();
				LookupHeaderExample.Criteria criteriaLookupHeaderExample = lookupHeaderExample.createCriteria();
				if(searchCriteria != null) {
					criteriaLookupHeaderExample.andDescriptionLikeInsensitive(searchCriteria);
				}
				return lookupServiceImpl.getLookupHeaderByExample(lookupHeaderExample, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				LookupHeaderExample lookupHeaderExample = new LookupHeaderExample();
				LookupHeaderExample.Criteria criteriaLookupHeaderExample = lookupHeaderExample.createCriteria();
				if(searchCriteria != null) {
					criteriaLookupHeaderExample.andDescriptionLikeInsensitive(searchCriteria);
				}
				return lookupServiceImpl.getCountLookupHeaderByExample(lookupHeaderExample);
			}

			@Override
			public void mapper(KeyValue keyValue, LookupHeader data) {
				keyValue.setKey(data.getLookupId());
				keyValue.setValue(data);
				keyValue.setDescription(data.getName());
			}
		});
		if(lookupHeader != null && lookupHeader.getLookupId() != null) {
			LookupDTO lookupDto = lookupServiceImpl.getLookupByLookupId(lookupHeader.getLookupId());
			KeyValue keyValueLookup = new KeyValue(lookupDto.getLookupId(), lookupDto.getName(), lookupDto.getName());
			lookup.setRawValue(keyValueLookup);
		}
		return lookup;
	}
	
	private Listbox getComponentScope(final Long companyScopeId, boolean disable) {
		final Listbox scope = new Listbox();
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
				if(companyScopeId != null && companyScopeId.equals(data.getCompanyId())) {
					scope.setSelectedItem(item);
				}
			}
		});
		if(companyScopeId == null) {
			listModelCompany.addToSelection(select);			
		}
		scope.renderAll();
		scope.setDisabled(disable);
		return scope;
	}
	
	@Listen("onClick = #btnAddRowOtherInfo")
	public void onAddRowOtherInfo() {
		lstOtherInfo.addRow();
	}
	
	@Listen("onClick = #btnDeleteRowOtherInfo")
	public void onDeleteRowOtherInfo() {
		List<OtherInfoDetailDTO> removeableSelection = new ArrayList<OtherInfoDetailDTO>();
		for(OtherInfoDetailDTO otherInfoDetailDto : listModelOtherInfo.getSelection()) {
			if(otherInfoDetailDto.getOtherInfoHdrId() != null) {
				removeableSelection.add(otherInfoDetailDto);
			}
		}
		lblErrOtherInfo.setValue("");
		if (removeableSelection.size() > 0) {
			lblErrOtherInfo.setValue(Labels.getLabel("sam.existingDataCannotBeDeletd"));
		}
		for(OtherInfoDetailDTO otherInfoDetailDto : removeableSelection) {
			listModelOtherInfo.removeFromSelection(otherInfoDetailDto);
		}
		lstOtherInfo.deleteRow();
	}
	
	@Listen("onSelect = #lstFormName")
	public void onSelectFormName(Event evt) {
		listOtherInfoDetailDto.clear();
		listModelOtherInfo.clear();
		KeyValue keyValue = (KeyValue) lstFormName.getSelectedItem().getValue();
		if(keyValue.getKey() != null) {
			otherInfoDto = otherInfoServiceImpl.getOtherInfoDtoByFormNameAndGroupId(keyValue.getKey().toString(), securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());			
			if(otherInfoDto != null) {
				Iterator<OtherInfoDetailDTO> itrOtherInfoDetailDto = otherInfoDto.getOtherInfoDetail().iterator();
				while(itrOtherInfoDetailDto.hasNext()) {
					OtherInfoDetailDTO otherInfoDetailDto = itrOtherInfoDetailDto.next();
					if(otherInfoDetailDto.getOtherInfoDtlId() == null) {
						itrOtherInfoDetailDto.remove();
					}
				}
				listModelOtherInfo.addAll(otherInfoDto.getOtherInfoDetail());			
			}			
		}
	}
	
	@Listen("onClick = #btnSave")
	public void onSave() {
		Messagebox.show(Labels.getLabel("common.confirmationMessage"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES|Messagebox.NO,null, new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				int ok = (int) event.getData();
				if(ok == Messagebox.YES){
					try {
						clearErrorMessage();
						setOtherInfoHeader();
						otherInfoDto.setValidOtherInfo(lstOtherInfo.validate());
						otherInfoServiceImpl.save(otherInfoDto);
						Messagebox.show(Labels.getLabel("common.dataHasBeenSaved"));
						onCancel();
					} catch (ValidationException e) {
						showErrorMessage(e);
						log.error(e.getMessage());
					} catch (Exception e) {
						Messagebox.show("System Error");
						log.error(e.getMessage(), e);
					}
				}
			}
		});
	}
	
	@Listen("onClick = #btnCancel")
	public void onCancel() {
		lstFormName.setSelectedIndex(0);
		listOtherInfoDetailDto.clear();
		listModelOtherInfo.clear();
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lstFormName);
	}
	
	private void showErrorMessage(ValidationException e) {
		Map<String, String> errors = e.getConstraintViolations();
		if(errors.containsKey(OtherInfoValidator.FORM_NAME)) {
			ErrorMessageUtil.setErrorMessage(lstFormName, e.getMessage(OtherInfoValidator.FORM_NAME));
		}
	}
	
	private void setOtherInfoHeader() {
		if(otherInfoDto == null) {
			otherInfoDto = new OtherInfoDTO();
		}
		KeyValue keyValue = (KeyValue) lstFormName.getSelectedItem().getValue();
		otherInfoDto.setFormName(keyValue.getKey() == null ? null : keyValue.getKey().toString());
		otherInfoDto.setGroupId(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId());
		if(otherInfoDto.getOtherInfoHdrId() == null) {
			otherInfoDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			otherInfoDto.setCreationDate(new Date());
		}
		otherInfoDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		otherInfoDto.setLastUpdateDate(new Date());
		setOtherInfoDetail();
	}
	
	private void setOtherInfoDetail() {
		List<OtherInfoDetailDTO> listOtherInfoDetailDto = lstOtherInfo.getValue();
		for(OtherInfoDetailDTO otherInfoDetailDto : listOtherInfoDetailDto) {
			if(otherInfoDetailDto.getOtherInfoDtlId() == null) {
				otherInfoDetailDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				otherInfoDetailDto.setCreationDate(new Date());
			}
			otherInfoDetailDto.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			otherInfoDetailDto.setLastUpdateDate(new Date());
		}
		otherInfoDto.setOtherInfoDetail(listOtherInfoDetailDto);
	}
}

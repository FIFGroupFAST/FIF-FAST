package co.id.fifgroup.basicsetup.controller;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.common.LookupQuerySet;
import co.id.fifgroup.basicsetup.constant.AliasLookupDynamic;
import co.id.fifgroup.basicsetup.constant.QuerySet;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.domain.CompanyExample;
import co.id.fifgroup.basicsetup.dto.LookupDynamicDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class LookupDetailSetQueryComposer extends SelectorComposer<Window> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -297395828602500709L;
	
	private static final Logger logger = LoggerFactory.getLogger(LookupDetailSetQueryComposer.class);

	@WireVariable
	private Map<String, Object> arg;
	@Wire
	private Listbox lstScope;
	@Wire
	private Textbox txtSelect;
	@Wire
	private Textbox txtFromTable;
	@Wire
	private Textbox txtWhereCondition;
	@Wire
	private Textbox txtOrderBy;
	@Wire
	private Button btnOk;
	@Wire
	private Button btnValidate;
	
	private ListModelList<Company> listModelScope;
	
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LookupService lookupServiceImpl;
	
	private LookupQuerySet lookupQuerySet;
	
	private LookupDynamicDTO lookupDynamicDto;
	
	private boolean scopeIsChange = true;
	private QuerySet querySet;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		loadData();
		lookupQuerySet = (LookupQuerySet) arg.get(LookupQuerySet.class.getName());
		querySet = (QuerySet) arg.get(QuerySet.class.getName());
		if(arg.containsKey(LookupDynamicDTO.class.getName())) {
			lookupDynamicDto = (LookupDynamicDTO) arg.get(LookupDynamicDTO.class.getName());
			for(Company company : listModelScope) {
				if(company.getCompanyId() != null && company.getCompanyId().equals(lookupDynamicDto.getCompanyScope())) {
					listModelScope.addToSelection(company);
					break;
				}
			}
			txtSelect.setValue(lookupDynamicDto.getSelectStatement());
			txtFromTable.setValue(lookupDynamicDto.getFromStatement());
			txtWhereCondition.setValue(lookupDynamicDto.getWhereStatement());
			txtOrderBy.setValue(lookupDynamicDto.getOrderBy());
			if(lookupDynamicDto.getLookupId() != null) {
				lstScope.setDisabled(true);
				txtSelect.setReadonly(true);
				txtFromTable.setReadonly(true);
				txtWhereCondition.setReadonly(true);
			}
		}
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isEditable()){
			btnOk.setDisabled(true);
			btnValidate.setDisabled(true);
			lstScope.setDisabled(true);
			txtSelect.setDisabled(true);
			txtFromTable.setDisabled(true);
			txtWhereCondition.setDisabled(true);
			txtOrderBy.setDisabled(true);
		}
	}
	
	private void init() {
		initListModelScope();
	}
	
	private void loadData() {
		loadDataScope();
	}
	
	private void initListModelScope() {
		listModelScope = new ListModelList<Company>();
		lstScope.setModel(listModelScope);
	}
	
	private void loadDataScope() {
		List<Company> companies = new ArrayList<Company>();
		Company select = new Company();
		select.setCompanyId(ScopeType.SELECT.getValue());
		select.setCompanyName(ScopeType.SELECT.getDesc());
		Company common = new Company();
		common.setCompanyId(ScopeType.COMMON.getValue());
		common.setCompanyName(ScopeType.COMMON.getDesc());
		
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria()
		.andGroupIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceBusinessGroupId())
		.andEffectiveStartDateLessThanOrEqualTo(DateUtil.truncate(new Date())).andEffectiveEndDateGreaterThanOrEqualTo(DateUtil.truncate(new Date()));
		List<Company> listCompany = companyServiceImpl.getCompanyByExample(companyExample);
		
		companies.add(select);
		companies.add(common);
		companies.addAll(listCompany);
		
		listModelScope.clear();
		listModelScope.addAll(companies);
		lstScope.renderAll();
		listModelScope.addToSelection(select);
	}
	
	private boolean isValidate() {
		boolean validate = true;
		ErrorMessageUtil.clearErrorMessage(lstScope);
		ErrorMessageUtil.clearErrorMessage(txtSelect);
		ErrorMessageUtil.clearErrorMessage(txtFromTable);
		
		Company scope = (Company) lstScope.getSelectedItem().getValue();
		if(scope.getCompanyId() == null) {
			ErrorMessageUtil.setErrorMessage(lstScope, Labels.getLabel("bse.err.scopeMustBeSelected"));
			validate &= false;
		}
		
		String selectStatemet = txtSelect.getValue().trim();
		if(selectStatemet.equals("")) {
			ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("common.pleaseFill", new Object[]{Labels.getLabel("bse.selectQuery")}));
			validate &= false;
		} else {
			String fields[] = selectStatemet.split(",");
			if(fields.length != 2) {
				ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.numberOfFieldsShouldBeTwo"));
				validate &= false;
			} else {
				String segment1 = fields[0].trim().replaceAll("\\s+", " ");
				String segment2 = fields[1].trim().replaceAll("\\s+", " ");
				String segments1[] = segment1.split(" ");
				String segments2[] = segment2.split(" ");
				if(segment1.trim().equals("") || segment2.trim().equals("")) {
					ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.numberOfFieldsShouldBeTwo"));
					validate &= false;
				} else if(segments1.length > 3 || segments2.length > 3) {
					ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementNotValid"));
					validate &= false;
				} else if(segments1.length < 2 || segments2.length < 2) {
					ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
					validate &= false;
				} else {
					if(segments1.length == 2) {
						if(segments1[1].toLowerCase().equals(AliasLookupDynamic.KEY.toString().toLowerCase())) {
							if(segments2.length == 2) {
								if(!segments2[1].toLowerCase().equals(AliasLookupDynamic.VALUE.toString().toLowerCase())) {
									ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
									validate &= false;
								}
							} else if(segments2.length == 3) {
								if(segments2[1].toLowerCase().equals(AliasLookupDynamic.AS.toString().toLowerCase())) {
									if(!segments2[2].toLowerCase().equals(AliasLookupDynamic.VALUE.toString().toLowerCase())) {
										ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
										validate &= false;
									}									
								} else {
									ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementWithASOrWithoutAS"));
									validate &= false;
								}
							}
						} else if(segments1[1].toLowerCase().equals(AliasLookupDynamic.VALUE.toString().toLowerCase())) {
							if(segments2.length == 2) {
								if(!segments2[1].toLowerCase().equals(AliasLookupDynamic.KEY.toString().toLowerCase())) {
									ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
									validate &= false;
								}
							} else if(segments2.length == 3) {
								if(segments2[1].toLowerCase().equals(AliasLookupDynamic.AS.toString().toLowerCase())) {
									if(!segments2[2].toLowerCase().equals(AliasLookupDynamic.KEY.toString().toLowerCase())) {
										ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
										validate &= false;
									}									
								} else {
									ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementWithASOrWithoutAS"));
									validate &= false;
								}
							}
						} else {
							ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
							validate &= false;
						}
					} else if(segments1.length == 3) {
						if(segments1[1].toLowerCase().equals(AliasLookupDynamic.AS.toString().toLowerCase())) {
							if(segments1[2].toLowerCase().equals(AliasLookupDynamic.KEY.toString().toLowerCase())) {
								if(segments2.length == 2) {
									if(!segments2[1].toLowerCase().equals(AliasLookupDynamic.VALUE.toString().toLowerCase())) {
										ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
										validate &= false;
									}
								} else if(segments2.length == 3) {
									if(segments2[1].toLowerCase().equals(AliasLookupDynamic.AS.toString().toLowerCase())) {
										if(!segments2[2].toLowerCase().equals(AliasLookupDynamic.VALUE.toString().toLowerCase())) {
											ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
											validate &= false;
										}									
									} else {
										ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementWithASOrWithoutAS"));
										validate &= false;
									}
								}
							} else if(segments1[2].toLowerCase().equals(AliasLookupDynamic.VALUE.toString().toLowerCase())) {
								if(segments2.length == 2) {
									if(!segments2[1].toLowerCase().equals(AliasLookupDynamic.KEY.toString().toLowerCase())) {
										ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
										validate &= false;
									}
								} else if(segments2.length == 3) {
									if(segments2[1].toLowerCase().equals(AliasLookupDynamic.AS.toString().toLowerCase())) {
										if(!segments2[2].toLowerCase().equals(AliasLookupDynamic.KEY.toString().toLowerCase())) {
											ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
											validate &= false;
										}									
									} else {
										ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementWithASOrWithoutAS"));
										validate &= false;
									}
								}
							} else {
								ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementShouldBeAliasesToTheKeyAndValue"));
								validate &= false;
							}
						} else {
							ErrorMessageUtil.setErrorMessage(txtSelect, Labels.getLabel("bse.err.selectStatementWithASOrWithoutAS"));
							validate &= false;
						}
					}
				}
			}
		}
		
		if(txtFromTable.getValue().trim().equals("")) {
			ErrorMessageUtil.setErrorMessage(txtFromTable, Labels.getLabel("common.pleaseFill", new Object[]{Labels.getLabel("bse.from")}));
			validate &= false;
		}
		
		return validate;
	}
	
	private void validateSql() throws SQLSyntaxErrorException {
		lookupServiceImpl.validateDynamicSql(lookupDynamicDto);
	}
	
	@Listen("onClick = #btnValidate")
	public void onValidate() {
		try {
			if(isValidate()){
				setLookupDynamicDto();
				validateSql();
				Messagebox.show(Labels.getLabel("bse.successValidation"));
			}
		} catch (SQLSyntaxErrorException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage());
		}
	}
	
	@Listen("onClick = #btnOk")
	public void onOk() {
		try {
			if(isValidate()) {
				setLookupDynamicDto();
				validateSql();
				checkCompanyScope();
				lookupQuerySet.setLookupQuerySet(lookupDynamicDto, querySet);
				getSelf().detach();
			}
		} catch (SQLSyntaxErrorException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage());
		}
	}
	
	@Listen("onClick = #btnCancel")
	public void onCancel() {
		getSelf().detach();
	}
	
	private void checkCompanyScope() throws Exception {
		Company scope = (Company) lstScope.getSelectedItem().getValue();
		if(querySet.equals(QuerySet.EDIT)) {
			if(lookupDynamicDto.getCompanyScope().equals(scope.getCompanyId())) {
				scopeIsChange = false;
			}
		}
		lookupQuerySet.checkCompanyScope(scope.getCompanyId(), scopeIsChange);
	}
	
	private void setLookupDynamicDto() {
		if(lookupDynamicDto == null) {
			lookupDynamicDto = new LookupDynamicDTO();
		}
		Company scope = (Company) lstScope.getSelectedItem().getValue();
		lookupDynamicDto.setCompany(scope);
		lookupDynamicDto.setCompanyScope(scope.getCompanyId());
		lookupDynamicDto.setSelectStatement(txtSelect.getValue());
		lookupDynamicDto.setFromStatement(txtFromTable.getValue());
		lookupDynamicDto.setWhereStatement(txtWhereCondition.getValue());
		lookupDynamicDto.setOrderBy(txtOrderBy.getValue());
	}
	
}

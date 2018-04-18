package co.id.fifgroup.systemadmin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.CommonDataSource;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.groovy.util.TripleKeyHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.personneladmin.dto.PersonCriteriaDTO;
import co.id.fifgroup.personneladmin.dto.PersonDTO;
import co.id.fifgroup.personneladmin.service.PersonService;
import co.id.fifgroup.workstructure.domain.Job;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.service.JobSetupService;
import co.id.fifgroup.workstructure.service.LocationSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import co.id.fifgroup.workstructure.ui.CommonBranchBandbox;
import co.id.fifgroup.workstructure.ui.CommonEmployeeBandboxAdapter;
import co.id.fifgroup.workstructure.ui.CommonOrganizationBandbox;

import co.id.fifgroup.basicsetup.domain.Company;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.core.constant.SecurityType;
import co.id.fifgroup.core.constant.StaticParameterKey;
import co.id.fifgroup.core.service.SecurityService;
/*import co.id.fifgroup.core.service.TraDevelopmentServiceAdapter;
import co.id.fifgroup.core.ui.CommonDevelopmentBandboxAdapter;*/
import co.id.fifgroup.core.ui.FormLabel;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.CommonPopupTripleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.lookup.TripleBandKeyValue;
import co.id.fifgroup.personneladmin.ui.CommonEmployeeBandbox;
import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.domain.TaskParameter;
import co.id.fifgroup.systemadmin.domain.TaskParameterValue;
import co.id.fifgroup.systemadmin.domain.TaskRunner;
import co.id.fifgroup.systemadmin.domain.TaskRunnerDetail;
import co.id.fifgroup.systemadmin.dto.TaskParameterDTO;
import co.id.fifgroup.systemadmin.service.ReportSecurityService;
import co.id.fifgroup.systemadmin.service.TaskRequestService;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class EntryTaskParameterComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(EntryTaskParameterComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient TaskRequestService taskRequestService;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient LookupService lookupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient PersonService personService;
	@WireVariable(rewireOnActivate=true)
	private transient ReportSecurityService reportSecurityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient CompanyService companyServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient JobSetupService jobSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;
	// start added for Phase 2 Training Admin by WLY
	/*@WireVariable(rewireOnActivate=true)
	private transient TraDevelopmentServiceAdapter developmentAdministartionBankServiceImpl;*/
	// end added for Phase 2 Training Admin by WLY
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	private TaskRunner selectedTaskrunner;
	@Wire
	private Vlayout dynamicParams;
	
	private Grid grid;
	private Groupbox groupBox;
	private Columns columns;
	private Rows rows;
	
	private TaskRequestDetailComposer composer;
	
	private boolean refreshList;
	private boolean editTaskParameters;
	private List<TaskParameter> taskParameters = new ArrayList<>(); 
	private List<Task> tasks = new ArrayList<>();
	private List<TaskParameterValue> taskParameterValues;
	private List<String> taskParameterDescription;
	private List<TaskRunnerDetail> taskRunnerDetails;
	private boolean setDisabled = false;
	private CommonBranchBandbox branchBandbox;
	private CommonPopupDoubleBandbox branchCommonBandBox;
	private CommonOrganizationBandbox organizationBandbox;
	private CommonEmployeeBandbox employeeBandBox;
	private CommonPopupDoubleBandbox employeeCommonBandBox;
	
	private CommonPopupBandbox bnbDependentCompany = new CommonPopupBandbox();
	private CommonPopupDoubleBandbox bnbDependentBranch = new CommonPopupDoubleBandbox();
	private CommonPopupDoubleBandbox bnbDependentLocation = new CommonPopupDoubleBandbox();
	private CommonPopupDoubleBandbox bnbDependentOrganization = new CommonPopupDoubleBandbox();
	private CommonPopupDoubleBandbox bnbDependentEmployee = new CommonPopupDoubleBandbox();
	private CommonPopupDoubleBandbox bnbDependentJob = new CommonPopupDoubleBandbox();
	
	// start added for Phase 2 Training Admin by WLY
	/*private CommonDevelopmentBandboxAdapter bnbDevelopment;*/
	// end added for Phase 2 Training Admin by WLY
	
	private Long responsibilityId;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		responsibilityId = (Long)arg.get("responsibilityId");
		// start add by YNT, ITSM 15102208595234
		logger.info("Check data argument --> '"+securityServiceImpl.getSecurityProfile().getPersonId()+"','"+responsibilityId+"'");
		// end add by YNT, ITSM 15102208595234
		
		loadParameters();
		buildParameter();
	}
	
	
	@SuppressWarnings("unchecked")
	private void loadParameters() {
		composer = (TaskRequestDetailComposer) arg.get("composer");
		taskParameterValues = (List<TaskParameterValue>) arg.get("taskParameterValues");
		selectedTaskrunner = (TaskRunner) arg.get("taskRunner");
		refreshList =(boolean) arg.get("refreshList");
		if(arg.containsKey("setDisabled")){
			setDisabled = (boolean) arg.get("setDisabled");
			taskParameterValues = taskRequestService.getTaskParameterValuesByTaskRunnerId((Long) arg.get("requestId"));
		} else {
			if(taskParameterValues == null || refreshList)
				taskParameterValues = new ArrayList<TaskParameterValue>();
		}
		taskParameterDescription = new ArrayList<>();
//		taskParameters = taskRequestService.getTaskParameterByTaskRunnerId(selectedTaskrunner.getId());
//		tasks = taskRequestService.getTaskByTaskRunnerId(selectedTaskrunner.getId());
		taskRunnerDetails = taskRequestService.getTaskRunnerDetailByTaskRunnerId(selectedTaskrunner.getId());
		for(TaskRunnerDetail trd : taskRunnerDetails) {
			Task task = new Task();
			task = taskRequestService.getTaskByTaskId(trd.getTaskId());
			tasks.add(task);
		}
	}
	
	private void buildParameter() {
		for (Task task : tasks) {
			buildHeader(task.getTaskName());
			List<TaskParameterDTO> listOfParams = new ArrayList<>();
			listOfParams = taskRequestService.getTaskParameterByTaskId(task.getId());
			@SuppressWarnings("unused")
			int taskParamCount = 0;
			for (TaskParameterDTO taskParameter : listOfParams) {
				taskParameters.add(taskParameter);
				if(taskParameter.getTaskId().equals(task.getId())) { 
					buildComponent(taskParameter);
					taskParamCount++;
				}
			}
			if (organizationBandbox != null)
				organizationBandbox.setBnbBranch(branchBandbox);
			if (employeeBandBox != null) {
				employeeBandBox.setBnbBranch(branchBandbox);
				employeeBandBox.setBnbOrganization(organizationBandbox);
			}
			setParents();
			if(taskParameters.size() ==0){
				groupBox.detach();
			}
			taskParamCount = 0;
		}
	}
	
	private void setParents() {
		rows.setParent(grid);
		columns.setParent(grid);
		grid.setParent(groupBox);
		groupBox.setParent(dynamicParams);
	}
	
	private void buildHeader(String captionLbl) {
		groupBox = new Groupbox();
		Caption caption = new Caption(captionLbl);
		caption.setParent(groupBox);
		grid = new Grid();
		columns = new Columns();
		Column head1 = new Column();
		head1.setWidth("150px");
		head1.setAlign("right");
		Column head2 = new Column();
		head1.setParent(columns);
		head2.setParent(columns);
		rows = new Rows();
	}
	
	private int getComponentIndex = 0;
	
	private Object getComponentValue(TaskParameter taskParameter) {
		/*Object value = null;
		if(taskParameterValues.size() == 0){
			return value;
		} else {
			value = taskParameterValues.get(getComponentIndex).getValue();
			getComponentIndex++;
		}
		return value;*/
		for (TaskParameterValue taskParameterValue : taskParameterValues) {
			if(taskParameterValue.getTaskParameterId().equals(taskParameter.getId())){
				Object value = taskParameterValue.getValue();
				if(value!=null){
					return value.toString();
				}else{
					return null;
				}
			}
		}
		return null;
	}
	
	private void buildComponent(final TaskParameterDTO taskParameter) {
		Row row = new Row();
		FormLabel formLabel = new FormLabel();
		formLabel.setValue(taskParameter.getDisplayName());
		formLabel.setRequired(taskParameter.isMandatory());
		formLabel.setParent(row);
		
		if(taskParameter.getDataType().trim().equals(TaskDataType.NUMERIC.toString())) {
			Intbox intbox = new Intbox();
			intbox.setParent(row);
			intbox.setId(getComponentId(taskParameter));
			intbox.setDisabled(setDisabled);
			Object value = getComponentValue(taskParameter);
			if(value != null){
				if(!value.equals(""))
					intbox.setValue(Integer.valueOf(value.toString()));
			}
				
		} else if(taskParameter.getDataType().trim().equals(TaskDataType.TEXT.toString())) {
			Textbox textbox = new Textbox();
			textbox.setParent(row);
			textbox.setId(getComponentId(taskParameter));
			textbox.setDisabled(setDisabled);
			Object value = getComponentValue(taskParameter);
			if(value != null ){
				if(!value.equals(""))
					textbox.setValue((String)value);
			}
		} 
		else if(taskParameter.getDataType().trim().equals(TaskDataType.DATE.toString())) {
			Datebox datebox = new Datebox();
			datebox.setFormat(GlobalVariable.getDateFormat());
			datebox.setParent(row);
//			datebox.setReadonly(true);
			datebox.setId(getComponentId(taskParameter));
			datebox.setDisabled(setDisabled);
			Object value = getComponentValue(taskParameter);
			if(value != null) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					datebox.setValue(sdf.parse((String) value));
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}else if(taskParameter.getDataType().trim().equals(TaskDataType.LOOKUP.toString())) {
			if(!taskParameter.isStaticParam()){
				LookupWindow lookupWindow = new LookupWindow();
				lookupWindow.setTitle(taskParameter.getDisplayName());
				lookupWindow.setSearchText(taskParameter.getDisplayName());
				lookupWindow.setHeaderLabel(taskParameter.getDisplayName());
				lookupWindow.setParent(row);
				lookupWindow.setReadonly(true);
				lookupWindow.setId(getComponentId(taskParameter));
				lookupWindow.setDisabled(setDisabled);
				lookupWindow.setLookupCriteria(new LookupCriteria() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public String getParentDetailCode() {
						return null;
					}
					
					@Override
					public String getLookupName() {
						return lookupServiceImpl.getLookupByLookupId(taskParameter.getLookupId()).getName();
					}
				});
				
				Object value = getComponentValue(taskParameter);
				if(value != null) {
					lookupWindow.setValue(value.toString());
				}
			}else{
				if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH.toString())){
					
					branchBandbox = new CommonBranchBandbox();
					branchBandbox.setParent(row);
					branchBandbox.setId(getComponentId(taskParameter));
					branchBandbox.setDisabled(setDisabled || branchBandbox.isDisabled());
										
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
							if (org == null && value.toString().equalsIgnoreCase("-1")) {
								org = organizationSetupServiceImpl.getHeadOffice();
							}
							KeyValue keyValue = new KeyValue(org.getId(), org.getName());
							keyValue.setDescription(org.getName());
							branchBandbox.setRawValue(keyValue);
						}
					}
					
				}else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString())){
					
					organizationBandbox = new CommonOrganizationBandbox();

					organizationBandbox.setParent(row);
					organizationBandbox.setId(getComponentId(taskParameter));
					organizationBandbox.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()));
							KeyValue keyValue = new KeyValue(org.getId(), org.getName());
							keyValue.setDescription(org.getName());
							organizationBandbox.setRawValue(keyValue);
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_EMPLOYEE.toString())){
					
					employeeBandBox = new CommonEmployeeBandbox();
					
					employeeBandBox.setVisible(true);
					employeeBandBox.setReadonly(true);
//					employeeBandBox.setConcatValueDescription(true);
					employeeBandBox.setParent(row);
					employeeBandBox.setId(getComponentId(taskParameter));
					employeeBandBox.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());
							criteria.setEmployeeNumber(value.toString());
//							criteria.setPeopleType(PeopleType.EMPLOYEE.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompany(criteria, securityServiceImpl.getSecurityProfile().getCompanyId());
							if(!persons.isEmpty()){
								KeyValue keyValue = new KeyValue(persons.get(0).getPersonId(), persons.get(0).getFullName());
								keyValue.setDescription( persons.get(0).getFullName());
								employeeBandBox.setRawValue(keyValue);
							}
						}
					}
				}
				/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_EMPLOYEE_COMMON.toString())){
					
					employeeCommonBandBox = new CommonPopupDoubleBandbox();
					employeeCommonBandBox.setSearchText1("Employee Number");
					employeeCommonBandBox.setSearchText2("Employee Name");
					employeeCommonBandBox.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<PersonDTO>() {

						private static final long serialVersionUID = -3538530346194173652L;

						@Override
						public List<PersonDTO> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							if (!securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName().toString().toUpperCase().contains("GLOBAL")){
								criteria.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());	
							}
							criteria.setEmployeeNumber(searchCriteria1);
							criteria.setFullName(searchCriteria2);
						
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.getPersonByBusinessGroup(criteria, securityServiceImpl.getSecurityProfile().getBusinessGroupId(), offset, limit);
							return persons;
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setEmployeeNumber(searchCriteria1);
							criteria.setFullName(searchCriteria2);
							if (!securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName().toString().toUpperCase().contains("GLOBAL")){
								criteria.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());	
							}
							criteria.setEffectiveOnDate(new Date());
							return personService.countPersonByBusinessGroup(criteria, securityServiceImpl.getSecurityProfile().getBusinessGroupId());
							
						}

						@Override
						public void mapper(KeyValue keyValue, PersonDTO data) {
							keyValue.setKey(data.getPersonId());
							keyValue.setValue(data.getEmployeeNumber());
							keyValue.setDescription(data.getFullName());
						}
					});
					
					employeeCommonBandBox.setVisible(true);
					employeeCommonBandBox.setReadonly(true);
					employeeCommonBandBox.setParent(row);
					employeeCommonBandBox.setId(getComponentId(taskParameter));
					employeeCommonBandBox.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());
							criteria.setEmployeeNumber(value.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompanyInquiry(criteria);
							if(!persons.isEmpty()){
								KeyValue keyValue = new KeyValue(persons.get(0).getPersonId(), persons.get(0).getFullName());
								keyValue.setDescription( persons.get(0).getFullName());
								employeeCommonBandBox.setRawValue(keyValue);
							}
						}
					}
				}
				/*End add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				
				/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH_COMMON.toString())){
					
					if (!securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName().toString().toUpperCase().contains("GLOBAL")){
						branchBandbox = new CommonBranchBandbox();
						branchBandbox.setParent(row);
						branchBandbox.setId(getComponentId(taskParameter));
						branchBandbox.setDisabled(setDisabled || branchBandbox.isDisabled());
											
						Object value = getComponentValue(taskParameter);
						if(value != null){
							if(!value.equals("")){
								OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
								if (org == null && value.toString().equalsIgnoreCase("-1")) {
									org = organizationSetupServiceImpl.getHeadOffice();
								}
								KeyValue keyValue = new KeyValue(org.getId(), org.getName());
								keyValue.setDescription(org.getName());
								branchBandbox.setRawValue(keyValue);
							}
						}
					}
					else
					{
						branchCommonBandBox = new CommonPopupDoubleBandbox();
						branchCommonBandBox.setSearchText1("Branch Code");
						branchCommonBandBox.setSearchText2("Branch Name");
						branchCommonBandBox.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Organization>() {

							private static final long serialVersionUID = 7106293492850530672L;

							@Override
							public List<Organization> findBySearchCriteria(
									String searchCriteria1, String searchCriteria2,
									int limit, int offset) {
								

								return organizationSetupServiceImpl.findBranchesCommon(
										searchCriteria1, searchCriteria2, limit, offset, DateUtils.truncate(new Date(), Calendar.DATE));
							}

							@Override
							public int countBySearchCriteria(String searchCriteria1,
									String searchCriteria2) {
								return organizationSetupServiceImpl.countBranchesCommon(searchCriteria1, searchCriteria2,DateUtils.truncate(new Date(), Calendar.DATE));
							}


							@Override
							public void mapper(KeyValue keyValue,
									Organization data) {
								keyValue.setKey(data.getId());
								keyValue.setValue(data.getOrganizationCode());
								keyValue.setDescription(data.getOrganizationName());
								
							}
						});
						
						branchCommonBandBox.setVisible(true);
						branchCommonBandBox.setReadonly(true);
						branchCommonBandBox.setParent(row);
						branchCommonBandBox.setId(getComponentId(taskParameter));
						branchCommonBandBox.setDisabled(setDisabled);
						
																	
						Object value = getComponentValue(taskParameter);
						if(value != null){
							if(!value.equals("")){
								OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
								if (org == null && value.toString().equalsIgnoreCase("-1")) {
									org = organizationSetupServiceImpl.getHeadOffice();
								}
								KeyValue keyValue = new KeyValue(org.getId(), org.getName());
								keyValue.setDescription(org.getName());
								branchCommonBandBox.setRawValue(keyValue);
							}
						}
					}
					
					
					
				}
				/*End add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				
				/*Add new behaviour predefined parameter */
				else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString())){
					bnbDependentCompany = new CommonPopupBandbox();
					bnbDependentCompany.setTitle("Company");
					bnbDependentCompany.setSearchText("Company Name");
					bnbDependentCompany.setHeaderLabel("Company Name");
					bnbDependentCompany.setSearchDelegate(new SerializableSearchDelegate<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria, int limit, int offset) {
							return reportSecurityServiceImpl.getCompany(responsibilityId, securityServiceImpl.getSecurityProfile().getCompanyId(), searchCriteria, limit, offset);
						}

						@Override
						public int countBySearchCriteria(String searchCriteria) {
							return reportSecurityServiceImpl.countCompany(responsibilityId, securityServiceImpl.getSecurityProfile().getCompanyId(), searchCriteria);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getValue());
							
						}
					});
					bnbDependentCompany.setVisible(true);
					bnbDependentCompany.setReadonly(true);
					bnbDependentCompany.setParent(row);
					bnbDependentCompany.setId(getComponentId(taskParameter));
					bnbDependentCompany.setDisabled(setDisabled);
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							Company company = companyServiceImpl.getCompanyByIdAndEffectiveDate(Long.valueOf(value.toString()), DateUtil.truncate(new Date()));
							if (company != null) {
								KeyValue keyValue = new KeyValue(company.getCompanyId(), company.getCompanyName());
								keyValue.setDescription(company.getCompanyName());
								bnbDependentCompany.setRawValue(keyValue);
							}
							
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString())){
					bnbDependentBranch = new CommonPopupDoubleBandbox();
					
//					bnbDependentBranch.setTitle("List Of Branch");
//					bnbDependentBranch.setSearchText1("Branch Code");
//					bnbDependentBranch.setSearchText2("Branch Name");
//					bnbDependentBranch.setHeaderLabel1("Branch Code");
//					bnbDependentBranch.setHeaderLabel2("Branch Name");
					
					// start rename bandbox title and label by Arf || 08-12-2015
					bnbDependentBranch.setTitle(Labels.getLabel("common.listOfBranch"));
					bnbDependentBranch.setSearchText1(Labels.getLabel("common.branchCode"));
					bnbDependentBranch.setSearchText2(Labels.getLabel("common.branchName"));
					bnbDependentBranch.setHeaderLabel1(Labels.getLabel("common.branchCode"));
					bnbDependentBranch.setHeaderLabel2(Labels.getLabel("common.branchName"));
					// end rename bandbox title and label by Arf || 08-12-2015
					
					if (securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType() != null
							&& (SecurityType.VIEW_BRANCH.getValue().equals(
									securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType()) || SecurityType.VIEW_HIERARCHY
									.getValue().equals(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType()))) {
						bnbDependentBranch.setDisabled(true);
						Long branchId = null;
						if (SecurityType.VIEW_BRANCH.getValue().equals(securityServiceImpl.getSecurityProfile().getSecurity().getSecurityType()))
							branchId = securityServiceImpl.getSecurityProfile().getBranchId();
						bnbDependentBranch.setRawValue(new KeyValue(branchId, securityServiceImpl.getSecurityProfile()
								.getBranchName(), securityServiceImpl.getSecurityProfile().getBranchName()));
					}
					bnbDependentBranch.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							return reportSecurityServiceImpl.getBranch(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, responsibilityId, searchCriteria1, searchCriteria2, limit, offset);
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							// start add by YNT, ITSM 15102208595234
							logger.info("Check data countBranch --> '"+securityServiceImpl.getSecurityProfile().getPersonId()+"','"+responsibilityId+"','"+searchCriteria1+"','"+searchCriteria2+"'");
							// end add by YNT, ITSM 15102208595234
							return reportSecurityServiceImpl.countBranch(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, responsibilityId, searchCriteria1, searchCriteria2);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getDescription());
						}
					});
					bnbDependentBranch.setVisible(true);
					bnbDependentBranch.setReadonly(true);
					bnbDependentBranch.setParent(row);
					bnbDependentBranch.setId(getComponentId(taskParameter));
					bnbDependentBranch.setDisabled(setDisabled || bnbDependentBranch.isDisabled());
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()) );
							if (org == null && value.toString().equalsIgnoreCase("-1")) {
								org = organizationSetupServiceImpl.getHeadOffice();
							}
							KeyValue keyValue = new KeyValue(org.getId(), org.getName());
							keyValue.setDescription(org.getName());
							bnbDependentBranch.setRawValue(keyValue);
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString())){
					bnbDependentLocation = new CommonPopupDoubleBandbox();
					bnbDependentLocation.setTitle("Location");
					bnbDependentLocation.setSearchText1("Location Code");
					bnbDependentLocation.setSearchText2("Location Name");
					bnbDependentLocation.setHeaderLabel1("Code");
					bnbDependentLocation.setHeaderLabel2("Name");
					bnbDependentLocation.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							return reportSecurityServiceImpl.getLocation(responsibilityId, 
									selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, 
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, searchCriteria1, searchCriteria2, limit, offset);
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							return reportSecurityServiceImpl.countLocation(responsibilityId, 
									selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, 
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, searchCriteria1, searchCriteria2);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getDescription());
						}
					});
					bnbDependentLocation.setVisible(true);
					bnbDependentLocation.setReadonly(true);
					bnbDependentLocation.setParent(row);
					bnbDependentLocation.setId(getComponentId(taskParameter));
					bnbDependentLocation.setDisabled(setDisabled);
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							Location location = locationSetupServiceImpl.findByPrimaryKey(Long.valueOf(value.toString()));
							if(location != null){
								KeyValue keyValue = new KeyValue(location.getId(), location.getLocationName());
								keyValue.setDescription(location.getLocationName());
								bnbDependentLocation.setRawValue(keyValue);
							}
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString())){
					bnbDependentOrganization = new CommonPopupDoubleBandbox();
					bnbDependentOrganization.setTitle("Organization");
					bnbDependentOrganization.setSearchText1("Organization Code");
					bnbDependentOrganization.setSearchText2("Organization Name");
					bnbDependentOrganization.setHeaderLabel1("Code");
					bnbDependentOrganization.setHeaderLabel2("Name");
					bnbDependentOrganization.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							KeyValue selectedLocation = bnbDependentLocation.getKeyValue();
							return reportSecurityServiceImpl.getOrganization(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null,
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, 
									selectedLocation != null ? Long.valueOf(selectedLocation.getKey().toString()) : null, searchCriteria1, searchCriteria2, limit, offset);
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							KeyValue selectedLocation = bnbDependentLocation.getKeyValue();
							return reportSecurityServiceImpl.countOrganization(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null,
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, 
									selectedLocation != null ? Long.valueOf(selectedLocation.getKey().toString()) : null, searchCriteria1, searchCriteria2);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getDescription());
						}
					});
					bnbDependentOrganization.setVisible(true);
					bnbDependentOrganization.setReadonly(true);
					bnbDependentOrganization.setParent(row);
					bnbDependentOrganization.setId(getComponentId(taskParameter));
					bnbDependentOrganization.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							OrganizationDTO org = organizationSetupServiceImpl.findById(Long.valueOf(value.toString()));
							KeyValue keyValue = new KeyValue(org.getId(), org.getName());
							keyValue.setDescription(org.getName());
							bnbDependentOrganization.setRawValue(keyValue);
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString())){
					bnbDependentEmployee = new CommonPopupDoubleBandbox();
					
//					bnbDependentEmployee.setTitle("Employee");
//					bnbDependentEmployee.setSearchText1("Number");
//					bnbDependentEmployee.setSearchText2("Name");
//					bnbDependentEmployee.setHeaderLabel1("Number");
//					bnbDependentEmployee.setHeaderLabel2("Name");
					
					// start rename bandbox title and label by Arf || 08-12-2015
					bnbDependentEmployee.setTitle(Labels.getLabel("common.listOfEmployee"));
					bnbDependentEmployee.setSearchText1(Labels.getLabel("common.lovEmployeeNumber"));
					bnbDependentEmployee.setSearchText2(Labels.getLabel("common.lovEmployeeName"));
					bnbDependentEmployee.setHeaderLabel1(Labels.getLabel("common.lovEmployeeNumber"));
					bnbDependentEmployee.setHeaderLabel2(Labels.getLabel("common.lovEmployeeName"));
					// end rename bandbox title and label by Arf || 08-12-2015
					
					bnbDependentEmployee.setConcatValueDescription(true);
					bnbDependentEmployee.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							KeyValue selectedLocation = bnbDependentLocation.getKeyValue();
							KeyValue selectedOrganization = bnbDependentOrganization.getKeyValue();
							
							return reportSecurityServiceImpl.getEmployee(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, 
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, 
									selectedLocation != null ? Long.valueOf(selectedLocation.getKey().toString()) : null,
									selectedOrganization != null ? Long.valueOf(selectedOrganization.getKey().toString()) : null, 
									searchCriteria1, searchCriteria2, limit, offset);
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							KeyValue selectedLocation = bnbDependentLocation.getKeyValue();
							KeyValue selectedOrganization = bnbDependentOrganization.getKeyValue();
							return reportSecurityServiceImpl.countEmployee(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, 
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, 
									selectedLocation != null ? Long.valueOf(selectedLocation.getKey().toString()) : null,
									selectedOrganization != null ? Long.valueOf(selectedOrganization.getKey().toString()) : null, 
									searchCriteria1, searchCriteria2);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getDescription());
						}
					});
					bnbDependentEmployee.setVisible(true);
					bnbDependentEmployee.setReadonly(true);
					bnbDependentEmployee.setParent(row);
					bnbDependentEmployee.setId(getComponentId(taskParameter));
					bnbDependentEmployee.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());
							criteria.setEmployeeNumber(value.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompanyInquiry(criteria);
							if(!persons.isEmpty()){
								KeyValue keyValue = new KeyValue(persons.get(0).getPersonId(), persons.get(0).getFullName());
								keyValue.setDescription( persons.get(0).getFullName());
								bnbDependentEmployee.setRawValue(keyValue);
							}
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE_CWK.toString())){
					bnbDependentEmployee = new CommonPopupDoubleBandbox();
					bnbDependentEmployee.setTitle("Employee");
					bnbDependentEmployee.setSearchText1("Number");
					bnbDependentEmployee.setSearchText2("Name");
					bnbDependentEmployee.setHeaderLabel1("Number");
					bnbDependentEmployee.setHeaderLabel2("Name");
					bnbDependentEmployee.setConcatValueDescription(true);
					bnbDependentEmployee.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							KeyValue selectedLocation = bnbDependentLocation.getKeyValue();
							KeyValue selectedOrganization = bnbDependentOrganization.getKeyValue();
							
							return reportSecurityServiceImpl.getEmployeeCWK(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, 
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, 
									selectedLocation != null ? Long.valueOf(selectedLocation.getKey().toString()) : null,
									selectedOrganization != null ? Long.valueOf(selectedOrganization.getKey().toString()) : null, 
									searchCriteria1, searchCriteria2, limit, offset);
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							KeyValue selectedBranch = bnbDependentBranch.getKeyValue();
							KeyValue selectedLocation = bnbDependentLocation.getKeyValue();
							KeyValue selectedOrganization = bnbDependentOrganization.getKeyValue();
							return reportSecurityServiceImpl.countEmployeeCWK(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, 
									selectedBranch != null ? Long.valueOf(selectedBranch.getKey().toString()) : null, 
									selectedLocation != null ? Long.valueOf(selectedLocation.getKey().toString()) : null,
									selectedOrganization != null ? Long.valueOf(selectedOrganization.getKey().toString()) : null, 
									searchCriteria1, searchCriteria2);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getDescription());
						}
					});
					bnbDependentEmployee.setVisible(true);
					bnbDependentEmployee.setReadonly(true);
					bnbDependentEmployee.setParent(row);
					bnbDependentEmployee.setId(getComponentId(taskParameter));
					bnbDependentEmployee.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							PersonCriteriaDTO criteria = new PersonCriteriaDTO();
							criteria.setCompanyId(securityServiceImpl.getSecurityProfile().getCompanyId());
							criteria.setEmployeeNumber(value.toString());
							criteria.setEffectiveOnDate(new Date());
							List<PersonDTO> persons = personService.selectPersonByCompanyInquiry(criteria);
							if(!persons.isEmpty()){
								KeyValue keyValue = new KeyValue(persons.get(0).getPersonId(), persons.get(0).getFullName());
								keyValue.setDescription( persons.get(0).getFullName());
								bnbDependentEmployee.setRawValue(keyValue);
							}
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_JOB.toString())){
					bnbDependentJob = new CommonPopupDoubleBandbox();
//					bnbDependentJob.setTitle("Job");
//					bnbDependentJob.setSearchText1("Job Code");
//					bnbDependentJob.setSearchText2("Job Name");
//					bnbDependentJob.setHeaderLabel1("Code");
//					bnbDependentJob.setHeaderLabel2("Name");
					
					// start rename bandbox title and label by Arf || 08-12-2015
					bnbDependentJob.setTitle(Labels.getLabel("common.listOfJob"));
					bnbDependentJob.setSearchText1(Labels.getLabel("common.jobCode"));
					bnbDependentJob.setSearchText2(Labels.getLabel("common.jobName"));
					bnbDependentJob.setHeaderLabel1(Labels.getLabel("common.jobCode"));
					bnbDependentJob.setHeaderLabel2(Labels.getLabel("common.jobName"));
					// end rename bandbox title and label by Arf || 08-12-2015
					
					bnbDependentJob.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<KeyValue>() {

						private static final long serialVersionUID = 1L;

						@Override
						public List<KeyValue> findBySearchCriteria(
								String searchCriteria1, String searchCriteria2,
								int limit, int offset) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							return reportSecurityServiceImpl.getJob(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, responsibilityId, searchCriteria1, searchCriteria2, limit, offset);
						}

						@Override
						public int countBySearchCriteria(
								String searchCriteria1, String searchCriteria2) {
							KeyValue selectedCompany = bnbDependentCompany.getKeyValue();
							return reportSecurityServiceImpl.countJob(selectedCompany != null ? Long.valueOf(selectedCompany.getKey().toString()) : null, responsibilityId, searchCriteria1, searchCriteria2);
						}

						@Override
						public void mapper(KeyValue keyValue, KeyValue data) {
							keyValue.setKey(data.getKey());
							keyValue.setValue(data.getValue());
							keyValue.setDescription(data.getDescription());
						}
					});
					bnbDependentJob.setVisible(true);
					bnbDependentJob.setReadonly(true);
					bnbDependentJob.setParent(row);
					bnbDependentJob.setId(getComponentId(taskParameter));
					bnbDependentJob.setDisabled(setDisabled);
					
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							Job job = jobSetupServiceImpl.findByPrimaryKey(Long.valueOf(value.toString()));
							if(job != null){
								KeyValue keyValue = new KeyValue(job.getId(), job.getJobName());
								keyValue.setDescription(job.getJobName());
								bnbDependentJob.setRawValue(keyValue);
							}
						}
					}
				}
				
				// start added for Phase 2 Training Admin by WLY
				/*else if (taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_DEVELOPMENT.toString())){
					bnbDevelopment = developmentAdministartionBankServiceImpl.getCommonDevelopmentNameBandbox();
					bnbDevelopment.setDisabled(setDisabled);
					bnbDevelopment.setParent(row);
					bnbDevelopment.setId(getComponentId(taskParameter));
					Object value = getComponentValue(taskParameter);
					if(value != null){
						if(!value.equals("")){
							bnbDevelopment.setRawValue(Long.valueOf(value.toString()));
						}
					}
				}*/
				// end added for Phase 2 Training Admin by WLY
			}
			
		}else if(taskParameter.getDataType().trim().equals(TaskDataType.BOOLEAN.toString())) {
			Checkbox checkbox = new Checkbox();
			checkbox.setParent(row);
			checkbox.setId(getComponentId(taskParameter));
			checkbox.setDisabled(setDisabled);
			
			Object value = getComponentValue(taskParameter);
			if(value != null)
				checkbox.setChecked(value.toString().equalsIgnoreCase("1") ? true : value.toString().equalsIgnoreCase("true") ? true : false);
		}
		row.setParent(rows);
	}
	
	@Listen("onClick=button#btnOk")
	public void onOk() {
		try{
			if(setDisabled) {
				getSelf().detach();
			} else {
				getComponentCounter = 1;
				if(validate()){
					composer.setTaskParameterValues(taskParameterValues);
					composer.setTaskParameterDescription(taskParameterDescription);
					getSelf().detach();
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private boolean validate() {
		boolean valid = true;
		taskParameterValues.clear();
		taskParameterDescription.clear();
		String description = "";
		for (TaskParameter taskParameter : taskParameters) {
			TaskParameterValue taskParameterValue = new TaskParameterValue();
			taskParameterValue.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			taskParameterValue.setCreationDate(DateUtil.truncate(new Date()));
			taskParameterValue.setLastUpdatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			taskParameterValue.setLastUpdateDate(DateUtil.truncate(new Date()));
			
			taskParameterValue.setTaskParameterId(taskParameter.getId());
			Component comp = dynamicParams.getFellow(getComponentId(taskParameter));
			Textbox textParam = new Textbox();
			Datebox dateParam = new Datebox();
			Intbox intParam = new Intbox();
			Checkbox chkParam = new Checkbox();
			LookupWindow lookupParam= new LookupWindow();
			if(comp instanceof Textbox) {
				if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString()))){
					if(taskParameter.isMandatory() && bnbDependentCompany.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentCompany, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentCompany.getKeyValue() != null){
							KeyValue keyValue = bnbDependentCompany.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else{
					textParam = (Textbox) comp;
					ErrorMessageUtil.clearErrorMessage(textParam);
					if(taskParameter.isMandatory() && (textParam.getValue() == null || (textParam.getValue() != null && textParam.getValue().equals("")))) {
						ErrorMessageUtil.setErrorMessage(textParam, "This field cannot be empty");
						valid = false;
					} else {
						taskParameterValue.setValue(textParam.getValue());
						description = textParam.getValue() == null ? "" : textParam.getValue();
					}
				}
			}
			if(comp instanceof Datebox) {
				dateParam = (Datebox) comp;
				ErrorMessageUtil.clearErrorMessage(dateParam);
				if(taskParameter.isMandatory() && (dateParam.getValue() == null || (dateParam.getValue() != null && dateParam.getValue().equals("")))) {
					ErrorMessageUtil.setErrorMessage(dateParam, "This field cannot be empty");
					valid = false;
				} else {
//					LocalDate ld = new LocalDate(dateParam.getValue());
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
					taskParameterValue.setValue(dateParam.getValue() != null ? sdf.format(dateParam.getValue()) : null);
					description = dateParam.getValue() != null ? sdf.format(dateParam.getValue()).toString() : null;
//					taskParameterValue.setValue(DateFormats.format(ld.toDate(), true));
				}
			}
			if(comp instanceof CommonPopupDoubleBandbox){
				if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH.toString())){
					
					if(taskParameter.isMandatory() && branchBandbox.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(branchBandbox, "This field cannot be empty");
						valid = false;
					}else{
						if(branchBandbox.getKeyValue() != null){
							KeyValue keyValue = branchBandbox.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
					
				}else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_ORGANIZATION.toString())){
				
					if(taskParameter.isMandatory() && organizationBandbox.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(organizationBandbox, "This field cannot be empty");
						valid = false;
					}else{
						if(organizationBandbox.getKeyValue() != null){
							KeyValue keyValue = organizationBandbox.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_EMPLOYEE.toString())){
				
					if(taskParameter.isMandatory() && employeeBandBox.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(employeeBandBox, "This field cannot be empty");
						valid = false;
					}else{
						if(employeeBandBox.getKeyValue() != null){
							KeyValue keyValue = employeeBandBox.getKeyValue();
							taskParameterValue.setValue(keyValue.getValue());
							description= keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}
				/*Add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_EMPLOYEE_COMMON.toString())){
				
					if(taskParameter.isMandatory() && employeeCommonBandBox.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(employeeCommonBandBox, "This field cannot be empty");
						valid = false;
					}else{
						if(employeeCommonBandBox.getKeyValue() != null){
							KeyValue keyValue = employeeCommonBandBox.getKeyValue();
							taskParameterValue.setValue(keyValue.getValue());
							description= keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}
				else if(taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_BRANCH_COMMON.toString())){
					
					
					if (!securityServiceImpl.getSecurityProfile().getSecurity().getResponsibilityName().toString().toUpperCase().contains("GLOBAL")){
						if(taskParameter.isMandatory() && branchBandbox.getKeyValue() == null){
							ErrorMessageUtil.setErrorMessage(branchBandbox, "This field cannot be empty");
							valid = false;
						}else{
							if(branchBandbox.getKeyValue() != null){
								KeyValue keyValue = branchBandbox.getKeyValue();
								taskParameterValue.setValue(keyValue.getKey());
								description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
							}
						}
					}
					else
					{
						if(taskParameter.isMandatory() && branchCommonBandBox.getKeyValue() == null){
							ErrorMessageUtil.setErrorMessage(branchCommonBandBox, "This field cannot be empty");
							valid = false;
						}else{
							if(branchCommonBandBox.getKeyValue() != null){
								KeyValue keyValue = branchCommonBandBox.getKeyValue();
								taskParameterValue.setValue(keyValue.getKey());
								description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
							}
						}
					}
					
					
					
				}
				/*End add BY Rim --> Ticket : 14060510275024_Perbaikan Report HCMS Fase 1 - Time Service*/
				else if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_COMPANY.toString()))){
					if(taskParameter.isMandatory() && bnbDependentCompany.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentCompany, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentCompany.getKeyValue() != null){
							KeyValue keyValue = bnbDependentCompany.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_BRANCH.toString()))){
					if(taskParameter.isMandatory() && bnbDependentBranch.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentBranch, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentBranch.getKeyValue() != null){
							KeyValue keyValue = bnbDependentBranch.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_LOCATION.toString()))){
					if(taskParameter.isMandatory() && bnbDependentLocation.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentLocation, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentLocation.getKeyValue() != null){
							KeyValue keyValue = bnbDependentLocation.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_ORGANIZATION.toString()))){
					if(taskParameter.isMandatory() && bnbDependentOrganization.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentOrganization, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentOrganization.getKeyValue() != null){
							KeyValue keyValue = bnbDependentOrganization.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_EMPLOYEE.toString()))){
					if(taskParameter.isMandatory() && bnbDependentEmployee.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentEmployee, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentEmployee.getKeyValue() != null){
							KeyValue keyValue = bnbDependentEmployee.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}else if((taskParameter.getKey().equals(StaticParameterKey.DEPENDENT_PARAM_JOB.toString()))){
					if(taskParameter.isMandatory() && bnbDependentJob.getKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDependentJob, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDependentJob.getKeyValue() != null){
							KeyValue keyValue = bnbDependentJob.getKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}
				
			}else
			if(comp instanceof LookupWindow) {
					
				lookupParam = (LookupWindow) comp;
				ErrorMessageUtil.clearErrorMessage(lookupParam);
				if(taskParameter.isMandatory() && (lookupParam.getValue() == null || (lookupParam.getValue() != null && lookupParam.getValue().equals("")))) {
					ErrorMessageUtil.setErrorMessage(lookupParam, "This field cannot be empty");
					valid = false;
				} else {
					if(lookupParam.getKeyValue() != null){
						KeyValue keyValue = lookupParam.getKeyValue();
						taskParameterValue.setValue(keyValue.getKey().toString());
						description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
					}
						
				}
					
			}
			if(comp instanceof Intbox) {
				intParam = (Intbox) comp;
				ErrorMessageUtil.clearErrorMessage(intParam);
				if(taskParameter.isMandatory() && (intParam.getValue() == null || (intParam.getValue() != null && intParam.getValue().equals("")))) {
					ErrorMessageUtil.setErrorMessage(intParam, "This field cannot be empty");
					valid = false;
				} else {
					taskParameterValue.setValue(intParam.getValue());
					description = intParam.getValue() == null ? "" : intParam.getValue().toString();
				}
			}
			if(comp instanceof Checkbox) {
				chkParam = (Checkbox) comp;
				if(chkParam.isChecked()){
					taskParameterValue.setValue(true);
					description = "true";
				}
				else{
					taskParameterValue.setValue(false);
					description = "false";					
				}
			}
			
			// start added for Phase 2 Training Admin by WLY
			/*if(comp instanceof CommonPopupTripleBandbox) {
				if (taskParameter.getKey().equals(StaticParameterKey.STATIC_PARAM_DEVELOPMENT.toString())){
					if(taskParameter.isMandatory() && bnbDevelopment.getTripleKeyValue() == null){
						ErrorMessageUtil.setErrorMessage(bnbDevelopment, "This field cannot be empty");
						valid = false;
					}else{
						if(bnbDevelopment.getTripleKeyValue() != null){
							TripleBandKeyValue keyValue = bnbDevelopment.getTripleKeyValue();
							taskParameterValue.setValue(keyValue.getKey());
							description = keyValue.getDescription() == null ? "" : keyValue.getDescription().toString();
						}
					}
				}
			}*/
			// end added for Phase 2 Training Admin by WLY
			
			
			if(!editTaskParameters) {
				taskParameterValues.add(taskParameterValue);
				taskParameterDescription.add(description);
			}
		}
		return valid;
	}
	
	private int getComponentCounter = 1;
	private String getComponentId(TaskParameter taskParameter) {
		String returnValue = taskParameter.getId().toString() + "." + getComponentCounter;
		getComponentCounter++;
		return returnValue;
	}
}

package co.id.fifgroup.workstructure.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.domain.LocationExample;
import co.id.fifgroup.workstructure.domain.Organization;
import co.id.fifgroup.workstructure.domain.OrganizationExample;
import co.id.fifgroup.workstructure.domain.OrganizationLevel;
import co.id.fifgroup.workstructure.domain.OrganizationLevelExample;
import co.id.fifgroup.workstructure.service.LocationSetupService;
import co.id.fifgroup.workstructure.service.OrganizationLevelSetupService;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;

@VariableResolver(DelegatingVariableResolver.class)
public class OrganizationSetupInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(OrganizationSetupInquiryComposer.class);
	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient OrganizationLevelSetupService organizationLevelSetupServiceImpl;
	
	@Wire
	private co.id.fifgroup.core.ui.lookup.CommonPopupDoubleBandbox bnbOrgName;
	@Wire
	private CommonPopupDoubleBandbox bnbOrgLevel;
	@Wire
	private CommonPopupDoubleBandbox bnbLocation;
	@Wire
	private Datebox dtbEffectiveOnDate;
	@Wire
	private LookupWindow bnbKpp;
	@Wire
	private Listbox lstOrg;
	@Wire
	private Button btnNew;
	@Wire
	private Button btnUpload;
	
	private final String kppLookup = "MST_KPP";
	private OrganizationDTO selectedOrg;
	private final String organization = "organization";
	FunctionPermission functionPermission;
	private final String bandboxWidth = "400px";
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		bnbLocation.setWidth(bandboxWidth);
		bnbOrgLevel.setWidth(bandboxWidth);
		bnbOrgName.setWidth(bandboxWidth);
		bnbKpp.setWidth(bandboxWidth);
		lstOrg.setMold("paging");
		lstOrg.setPageSize(GlobalVariable.getMaxRowPerPage());
		dtbEffectiveOnDate.setValue(new Date());
		populateOrgName();
		populateLocation();
		populateOrgLevel();
		populateKpp();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable()){
			btnNew.setDisabled(true);
			btnUpload.setDisabled(true);
		}
	}
	
	@Listen("onDownloadUserManual = #winOrganizationSetupInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	private void populateKpp() {
		bnbKpp.setLookupCriteria(new LookupCriteria() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getParentDetailCode() {
				return null;
			}
			
			@Override
			public String getLookupName() {
				return kppLookup;
			}
		});
	}
	
	private void populateOrgName() {
		bnbOrgName.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Organization>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Organization> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				OrganizationExample example = new OrganizationExample();
				example.createCriteria().andOrganizationCodeLikeInsensitive(searchCriteria1)
					.andOrganizationNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				OrganizationExample example = new OrganizationExample();
				example.createCriteria().andOrganizationCodeLikeInsensitive(searchCriteria1)
					.andOrganizationNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationSetupServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Organization data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getOrganizationCode());
				keyValue.setDescription(data.getOrganizationName());
			}
		});
	}
	
	private void populateOrgLevel() {
		bnbOrgLevel.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<OrganizationLevel>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<OrganizationLevel> findBySearchCriteria(
					String searchCriteria1, String searchCriteria2, int limit,
					int offset) {
				OrganizationLevelExample example = new OrganizationLevelExample();
				example.createCriteria().andLevelCodeLikeInsensitive(searchCriteria1)
					.andLevelNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setOrderByClause("LEVEL_CODE ASC");
				return organizationLevelSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				OrganizationLevelExample example = new OrganizationLevelExample();
				example.createCriteria().andLevelCodeLikeInsensitive(searchCriteria1)
					.andLevelNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return organizationLevelSetupServiceImpl.countByExample(example);
			}
			
			@Override
			public void mapper(KeyValue keyValue, OrganizationLevel data) {
				keyValue.setKey(data.getLevelId());
				keyValue.setValue(data.getLevelCode());
				keyValue.setDescription(data.getLevelName());
			}
		});
	}
	
	private void populateLocation() {
		bnbLocation.setSearchDelegate(new SerializableSearchDelegateDoubleCriteria<Location>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Location> findBySearchCriteria(String searchCriteria1,
					String searchCriteria2, int limit, int offset) {
				LocationExample example = new LocationExample();
				example.createCriteria().andLocationCodeLikeInsensitive(searchCriteria1)
					.andLocationNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return locationSetupServiceImpl.findByExample(example, limit, offset);
			}

			@Override
			public int countBySearchCriteria(String searchCriteria1,
					String searchCriteria2) {
				LocationExample example = new LocationExample();
				example.createCriteria().andLocationCodeLikeInsensitive(searchCriteria1)
					.andLocationNameLikeInsensitive(searchCriteria2)
					.andCompanyIdEqualTo(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				return locationSetupServiceImpl.countByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Location data) {
				keyValue.setKey(data.getId());
				keyValue.setValue(data.getLocationCode());
				keyValue.setDescription(data.getLocationName());
			}		
		});
	}
	
	@Listen("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnFind")
	public void onFind() {
		populateParameter();
		if(selectedOrg.getName() == null 
				&& selectedOrg.getLevelCode() == null
				&& selectedOrg.getKppCode() == null
				&& selectedOrg.getLocation() == null
				&& selectedOrg.getEffectiveOnDate() == null ) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int resultButton = (int) event.getData();
					if(resultButton == Messagebox.YES) {
						search();
					}
				}				
			});
		} else {
			search();
		}		
	}
	
	private void populateParameter() {
		selectedOrg = new OrganizationDTO();
		selectedOrg.setName(bnbOrgName.getKeyValue() != null ? (String)bnbOrgName.getKeyValue().getDescription() : null);
		selectedOrg.setLevelCode(bnbOrgLevel.getKeyValue() != null ? (String)bnbOrgLevel.getKeyValue().getValue() : null);
		selectedOrg.setLevelName(bnbOrgLevel.getKeyValue() != null ? (String)bnbOrgLevel.getKeyValue().getDescription() : null);
		selectedOrg.setKppCode(bnbKpp.getKeyValue() != null ? (String)bnbKpp.getKeyValue().getKey() : null);
		selectedOrg.setKppName(bnbKpp.getKeyValue() != null ? (String)bnbKpp.getKeyValue().getDescription() : null);
		selectedOrg.setEffectiveOnDate(dtbEffectiveOnDate.getValue() != null ? DateUtils.truncate(dtbEffectiveOnDate.getValue(), Calendar.DATE) : DateUtils.truncate(new Date(), Calendar.DATE));
		selectedOrg.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(bnbLocation.getKeyValue() != null) {
			LocationDTO location = new LocationDTO();
			location.setId((Long) bnbLocation.getKeyValue().getKey());
			location.setLocationName((String) bnbLocation.getKeyValue().getDescription());
			selectedOrg.setLocation(location);
		}
	}
	
	private void search() {		
		List<OrganizationDTO> orgs = organizationSetupServiceImpl.findByInquiry(selectedOrg);
		lstOrg.setModel(new ListModelList<OrganizationDTO>(orgs));
	}
	
	@Listen("onDetail= #winOrganizationSetupInquiry")
	public void onDetail(ForwardEvent event){
		selectedOrg = (OrganizationDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(OrganizationDTO.class.getName(), selectedOrg);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/organization_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onBranchSize= #winOrganizationSetupInquiry")
	public void onBranchSize(ForwardEvent event){
		selectedOrg = (OrganizationDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(OrganizationDTO.class.getName(), selectedOrg);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/branch_size_setup_inquiry.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onClick = button#btnUpload")
	public void onUpload() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(organization, true);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/workstructure/upload_organization.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
}

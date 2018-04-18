package co.id.fifgroup.main.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Li;
import org.zkoss.zhtml.Ul;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.constant.FunctionAccessType;
import co.id.fifgroup.core.constant.LoginStatus;
import co.id.fifgroup.core.constant.MenuItemType;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.security.Security;
import co.id.fifgroup.core.security.SecurityProfile;
import co.id.fifgroup.core.security.SimpleUserDetail;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.AuthorizedMenuDTO;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.NavigationTransactionForm;
import co.id.fifgroup.core.ui.TreeInfo;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.ApplicationContextUtil;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.systemadmin.domain.User;
import co.id.fifgroup.systemadmin.domain.UserAccessRecord;
import co.id.fifgroup.systemadmin.domain.UserExample;
import co.id.fifgroup.systemadmin.service.UserAccessRecordService;
import co.id.fifgroup.systemadmin.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class WorkspaceMainComposer extends SelectorComposer<Window>
	implements TreeitemRenderer<DefaultTreeNode<TreeInfo>>, NavigationTransactionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	
	@WireVariable(rewireOnActivate = true)
	private transient CompanyService companyServiceImpl;
	
	@WireVariable(rewireOnActivate=true)
	private transient UserService userService;
	
	private static Logger log = LoggerFactory.getLogger(WorkspaceMainComposer.class);
	
	private WorkspaceMainComposer thisComposer = this;
	
	private SimpleUserDetail userDetail;
	
	@Wire
	private Window mainWindow;
	
	@Wire
	private Listbox cbCompany;
	
	@Wire
	private Tree tree;
	
	@Wire
	private Tabpanels tabPanels;
	
	@Wire
	private Tabs tabs;
	
	@Wire
	private Tab tbMenu;
	
	@Wire
	private Tab tbResp;
	
	@Wire
	private Label lblUsername;
	
	@Wire
	private Caption lblCaption;
	
	@Wire
	private Label lblLastLogin;
	
	@Wire 
	private Tabbox tabBox;
	
	@Wire
	private Label lblScope;
	
	private int prevSelectedCompany;
	
	private SecurityProfile securityProfile;
	
	@Wire
	private Div divNavigation;
	@Wire
	private Div divNavigationm;
	
	private UsernamePasswordAuthenticationToken auth =  (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
	
	@WireVariable(rewireOnActivate = true)
	private transient UserAccessRecordService userAccessRecordProxyService;
	
	private Long responsibilityId;
	
	private String titleResponsibility;
	
	org.zkoss.zhtml.Div divhNavigation = new org.zkoss.zhtml.Div();
	
	@Wire
	private Button btnShowSO;
	@Wire
	private Button btnShowAT;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if (Executions.getCurrent().getBrowser("mobile") != null)
			tabBox.setHeight("100%");
		if (!SLF4JBridgeHandler.isInstalled())
			SLF4JBridgeHandler.install();
		Labels.reset();
		userDetail = (SimpleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserExample example = new UserExample();
		example.createCriteria().andUserNameEqualTo(userDetail.getUsername());
		Date lastLoggedIn = userService.getUserByExample(example).get(0).getLastLoggedIn();
		lblLastLogin.setValue("Last login : "+DateUtil.format(lastLoggedIn, "dd-MMM-yyyy kk:mm:ss"));
		lblUsername.setValue(userDetail.getUsername());
		User user = new User();
		user.setLastLoggedIn(new Date());
		userService.updateByExample(user, example);
		log.debug("User Login :"+userDetail.getUsername());
		
		divhNavigation.setParent(divNavigation);
		divhNavigation.setId("divhNavigation");
		
		if(userDetail.getLoginStatus().equals(LoginStatus.BUSINESS_GROUP_ADMIN)){
			lblScope.setValue("Business Group : ");
		}else
			lblScope.setValue("Company : ");


	}
	
	@Listen("onClick=button#btnShowSO")
	public void showSO() {
		List data = getStockOpnameService().getResponsibilityIdSO();
		if (data.size() > 0) {
			StockOpnameDTO so = (StockOpnameDTO) data.get(0);
			getResponsibilityDetail(so.getResponsibilityName(), so.getResponsibilityId());
			showFormSO(so.getFunctionId(), so.getModuleName(), so.getActionUrl());
		}
	}
	
	@Listen("onClick=button#btnShowAT")
	public void showAT() {
		List data = getStockOpnameService().getResponsibilityIdAT();
		if (data.size() > 0) {
			StockOpnameDTO so = (StockOpnameDTO) data.get(0);
			getResponsibilityDetail(so.getResponsibilityName(), so.getResponsibilityId());
			showFormSO(so.getFunctionId(), so.getModuleName(), so.getActionUrl());
		}
	}
	
	public void showFormSO(Long functionId,String moduleName,String actionUrl){
		//FunctionPermission functionPermission = getFunctionPermissions(new Long(589));
		showForm(functionId,
				actionUrl,
				moduleName,
				null,
				securityServiceImpl.getFunctionPermissionByFunctionId(functionId), null);
	}

	@Listen("onCreate=#mainWindow")
	public void createMainWindow(Event event) {
		log.debug("Loading responsibilities..");
		userDetail = (SimpleUserDetail) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		divhNavigation.getChildren().clear();
		Ul ul = new Ul();
		ul.setSclass("c-menu__items");
		divhNavigation.appendChild(ul);

		if (!userDetail.getLoginStatus().equals(
				LoginStatus.BUSINESS_GROUP_ADMIN)) {
			List<KeyValue> responsibilities = securityServiceImpl
					.getResponsibilityByCompany(userDetail.getUsername(),
							(Long) cbCompany.getSelectedItem().getValue(),
							userDetail.getLoginStatus());
			for (KeyValue keyValue : responsibilities) {
				Li li = new Li();
				li.setSclass("c-menu__item");
				li.setParent(ul);
				A a = new A(keyValue.getValue().toString());
				a.setAttribute("respId", keyValue.getKey());
				a.setSclass("c-menu__link");
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						A a = (A) event.getTarget();
						String respLabel = a.getLabel();
						titleResponsibility = a.getLabel();
						Long respId = (Long) a.getAttribute("respId");
						selectResponsibility(respLabel, respId);
					}
				});
				a.setParent(li);
			}
		} else {
			List<KeyValue> responsibilities = securityServiceImpl
					.getSuperUserResponsibility();
			for (KeyValue keyValue : responsibilities) {
				Li li = new Li();
				li.setSclass("c-menu__item");
				li.setParent(ul);
				A a = new A(keyValue.getValue().toString());
				a.setAttribute("respId", keyValue.getKey());
				a.setSclass("c-menu__link");
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						A a = (A) event.getTarget();
						String respLabel = a.getLabel();
						Long respId = (Long) a.getAttribute("respId");
						selectResponsibility(respLabel, respId);
					}
				});
				a.setParent(li);
			}
		}
	}
	
	@Listen("onCreate=#cbCompany")
	public void loadCompanyList(){
		userDetail = (SimpleUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
			List<KeyValue> autorizedCompanies = userDetail.getAuthorizedCompanies();
			ListModelList<KeyValue> model = new ListModelList<KeyValue>(autorizedCompanies);
			cbCompany.setModel(model);
			cbCompany.renderAll();
			cbCompany.setSelectedIndex(0);
			prevSelectedCompany = cbCompany.getSelectedIndex();
		
		if(!userDetail.getLoginStatus().equals(LoginStatus.BUSINESS_GROUP_ADMIN)){	
			getSecurityProfile();
			buildDashboard();
		}else{
			securityProfile = new SecurityProfile();
			securityProfile.setUserName(userDetail.getUsername());
			securityProfile.setUserId(securityServiceImpl.getUserIdByUsername(userDetail.getUsername()));
			securityProfile.setWorkspaceBusinessGroupId((Long) cbCompany.getSelectedItem().getValue());
			auth.setDetails(securityProfile);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		if (securityProfile != null) {
			String userInfo = String.format("%s - %s", 
					securityProfile.getEmployeeNumber() == null ? securityProfile.getUserName() : securityProfile.getEmployeeNumber(), 
					securityProfile.getFullName() == null ? "" : securityProfile.getFullName());
			lblUsername.setValue(userInfo);
		}		
		
	}
	
	@Listen("onSelect=#cbCompany")
	public void changeCompany(){
		Messagebox
		.show("Are you sure want to change the company workspace? \n It will close all of opened tabs and change the permissions related with company security",
				"Warning", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION,
				new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == Messagebox.YES) {
							if (userDetail.getLoginStatus().equals(
									LoginStatus.BUSINESS_GROUP_ADMIN)) {
								securityProfile
										.setWorkspaceBusinessGroupId((Long) cbCompany
												.getSelectedItem()
												.getValue());
								auth.setDetails(securityProfile);
								SecurityContextHolder.getContext()
										.setAuthentication(auth);
								tabs.getChildren().clear();
								tabPanels.getChildren().clear();
							} else {
								getSecurityProfile();
								refreshWorkspace();
								createMainWindow(event);
								divNavigationm.getChildren().clear();
								//tbResp.setSelected(true);
							}
							prevSelectedCompany = cbCompany
									.getSelectedIndex();
						} else {
							cbCompany
									.setSelectedIndex(prevSelectedCompany);
							return;
						}
					}
				});
		
	}
	
	private void refreshWorkspace(){
		tabs.getChildren().clear();
		tabPanels.getChildren().clear();
		buildDashboard();
	}
	
	public void selectResponsibility(String respLabel, Long respId) {
		this.responsibilityId = respId;
		log.info("Accessing Responsibility " + respLabel + "(" + respId + ")");
		if (userDetail.getLoginStatus()
				.equals(LoginStatus.BUSINESS_GROUP_ADMIN)) {
			getResponsibilityDetail(respLabel, respId);
			loadMenu(securityProfile.getSecurity().getMenuId());
		} else {
			getResponsibilityDetail(respLabel, respId);
			loadMenu(securityProfile.getSecurity().getMenuId());
			refreshWorkspace();
		}
	}
	
	private void getSecurityProfile(){
		log.debug("Getting security profile");
		securityProfile = securityServiceImpl.getSecurityProfile(userDetail.getUsername(), (Long)cbCompany.getSelectedItem().getValue()); 
		securityProfile.setWorkspaceCompanyId((Long) cbCompany.getSelectedItem().getValue());
		securityProfile.setWorkspaceBusinessGroupId(companyServiceImpl.getBusinessGroupIdByCompany((Long) cbCompany.getSelectedItem().getValue()));
		log.info("Security profile : "+securityProfile);
		auth.setDetails(securityProfile);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	private void getResponsibilityDetail(String respLabel, Long respId){
		log.debug("Getting Responsibility detail");
		boolean isAssignment = false;
		
		if(userDetail.getLoginStatus().equals(LoginStatus.BUSINESS_GROUP_ADMIN)){
			Security security = securityServiceImpl.getSecurityByResponsibilityId(respId);
			securityProfile.setSecurity(security);
			log.debug("Security Detail "+securityProfile.getSecurity());
			auth.setDetails(securityProfile);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}else{
			if(securityProfile.getCompanyId() != null && securityProfile.getCompanyId().equals((Long)cbCompany.getSelectedItem().getValue())){
				isAssignment = true;
			}
			Security security = securityServiceImpl.getSecurityByResponsibilityId(respId, (Long)cbCompany.getSelectedItem().getValue(), isAssignment);
			securityProfile.setSecurity(security);
			log.debug("Security Detail "+securityProfile.getSecurity());
			auth.setDetails(securityProfile);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}
	
	private void loadMenu(Long rootMenuId){
		divNavigationm.getChildren().clear();
		DefaultTreeModel<TreeInfo> model = securityServiceImpl
				.getAuthorizedTreeModelMenuByRootMenuId(rootMenuId,
						securityProfile.getSecurity().getMenuItemExclusions(),
						securityProfile.getSecurity()
								.getFunctionItemExclusions());
		
		String s1 = "x = new Boolean(true);";
		Clients.evalJavaScript(s1);
		
		org.zkoss.zhtml.Div div = new org.zkoss.zhtml.Div();
		div.setParent(divNavigationm);
		div.setId("divhNavigationm");
		div.setSclass("c-menu c-menu--push-left-menu is-active");
		Ul ul = new Ul();
		ul.setSclass("c-menu__items");
		ul.setParent(div);
		
		Li liResponsibility = new Li();
		liResponsibility.setSclass("c-menu__item");
		liResponsibility.setParent(ul);
		Label label = new Label(titleResponsibility);
		label.setSclass("c-menu__close");
		label.setParent(liResponsibility);
		
		Li liback = new Li();
		liback.setSclass("c-menu__item");
		liback.setParent(ul);
		A aback = new A("Back");
		aback.setSclass("c-menu__close");
		aback.setParent(liback);
		aback.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				String s1 = "x = false;";
				Clients.evalJavaScript(s1);
				String s2 = "jQuery('#divhNavigationm').removeClass('is-active');";
				Clients.evalJavaScript(s2);
			}
		});
		
		A aLink = new A();
		aLink.setStyle("display:none;");
		aLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				String s1 = "jQuery('#wrapper').removeClass('has-push-left');"
						+ "jQuery('#divhNavigation').removeClass('is-active');"
						+ "jQuery('#c-maskDiv').removeClass('is-active');"
						+ "jQuery('#divhNavigationm').removeClass('is-active');";
				Clients.evalJavaScript(s1);
			}
		});

		List<TreeNode<TreeInfo>> treeNodes = model.getRoot().getChildren();
		for (TreeNode<TreeInfo> treeNode : treeNodes) {
			final TreeInfo fi = treeNode.getData();
			final AuthorizedMenuDTO authorizedMenuDTO = (AuthorizedMenuDTO) fi
					.getValue();
			Li li = new Li();
			li.setSclass("c-menu__item");
			li.setParent(ul);

			if (fi.getType().equals(MenuItemType.FUNCTION.toString())) {
				A a = new A(fi.getPrompt());
				a.setStyle("color: #2C28DC; "
						+ "font-family: 'Open Sans', Arial, Sans-serif;");
				a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						AuthorizedMenuDTO function = (AuthorizedMenuDTO) fi
								.getValue();
						FunctionPermission functionPermission = getFunctionPermissions(function
								.getActionId());
						showForm(function.getActionId(),
								authorizedMenuDTO.getActionUrl(),
								authorizedMenuDTO.getModuleName(),
								authorizedMenuDTO.getUserManual(),
								functionPermission, null);
						log.info(String.format(
								"User Accessing %s in module %s",
								authorizedMenuDTO.getItemName(),
								authorizedMenuDTO.getModuleName()));
						recordUserAccess(authorizedMenuDTO.getItemName(),
								authorizedMenuDTO.getModuleName());
						String s1 = "jQuery('#wrapper').removeClass('has-push-left');"
								+ "jQuery('#divhNavigation').removeClass('is-active');"
								+ "jQuery('#c-maskDiv').removeClass('is-active');"
								+ "jQuery('#divhNavigationm').removeClass('is-active');";
						Clients.evalJavaScript(s1);
					}
				});
				a.setParent(li);
			} else {
				String name = fi.getPrompt();
				name = name.replace("&", "and");
				String cmsContent = "<input type='checkbox' class='menu-trigger' id='" 
						+ treeNodes.indexOf(treeNode) 
						+ "' /> "
						+ " <label class='sb-toggle-submenu' style='color: #1C1D54;' for='"
						+ treeNodes.indexOf(treeNode) 
						+ "'>" + 
						name 
						+ "</label>"
						;
				String htmlContent = "<html xmlns='native'>" + cmsContent
						+ "</html>";
				Executions.createComponentsDirectly(htmlContent, "zul", li, null);
				
				List<TreeNode<TreeInfo>> treeNodes2 = treeNode.getChildren();
				Ul ul2 = new Ul();
				ul2.setSclass("c-menu__items");
				if (treeNodes2.size() > 0) {
					ul2.setParent(li);
				}
				for (TreeNode<TreeInfo> treeNode2 : treeNodes2) {
					final TreeInfo fi2 = treeNode2.getData();
					final AuthorizedMenuDTO authorizedMenuDTO2 = (AuthorizedMenuDTO) fi2
							.getValue();
					Li li2 = new Li();
					li2.setParent(ul2);
					A a = new A(fi2.getPrompt());
					a.setStyle("color: #2C28DC; "
							+ "font-family: 'Open Sans', Arial, Sans-serif;");
					a.addEventListener(Events.ON_CLICK,
							new EventListener<Event>() {
								@Override
								public void onEvent(Event event)
										throws Exception {
									AuthorizedMenuDTO function = (AuthorizedMenuDTO) fi2
											.getValue();
									FunctionPermission functionPermission = getFunctionPermissions(function
											.getActionId());
									showForm(function.getActionId(),
											authorizedMenuDTO2.getActionUrl(),
											authorizedMenuDTO2.getModuleName(),
											authorizedMenuDTO2.getUserManual(),
											functionPermission, null);
									log.info(String.format(
											"User Accessing %s in module %s",
											authorizedMenuDTO2.getItemName(),
											authorizedMenuDTO2.getModuleName()));
									recordUserAccess(
											authorizedMenuDTO2.getItemName(),
											authorizedMenuDTO2.getModuleName());
									String s1 = "jQuery('#wrapper').removeClass('has-push-left');"
											+ "jQuery('#divhNavigation').removeClass('is-active');"
											+ "jQuery('#c-maskDiv').removeClass('is-active');"
											+ "jQuery('#divhNavigationm').removeClass('is-active');";
									Clients.evalJavaScript(s1);
								}
							});
					a.setParent(li2);
				}
			}
		}
	}
	
	private void recordUserAccess(String functionName, String moduleName){
		UserAccessRecord userAccessRecord = new UserAccessRecord();
		userAccessRecord.setAccessDate(new Date());
		userAccessRecord.setFunctionName(functionName);
		userAccessRecord.setModuleName(moduleName);
		userAccessRecord.setUsername(userDetail.getUsername());
		if(userAccessRecordProxyService != null)
			userAccessRecordProxyService.recordFunctionAccess(userAccessRecord);
		
	}
	
	private void showForm(Long actionId, String path, String moduleName, String userManualPath, FunctionPermission functionPermission, Map<String, Object> parameterMap){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(GlobalVariable.RESPONSIBILITY_NAME, securityProfile.getSecurity().getResponsibilityName());
		params.put(GlobalVariable.USER_MANUAL_KEY, userManualPath);
		params.put(GlobalVariable.ACTION_ID, actionId);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		params.put("workspaceMain", thisComposer);
		params.put("Tabbox",tabBox);
		params.put("responsibilityId", this.responsibilityId);
		
		log.info(String.format("Opening %s on Module %s", path, moduleName));
		
		if (parameterMap != null) {
			params.put("parameterMap", parameterMap);			
		}
		
		try {
			Tab tab = (Tab) tabs.getFellow("tab_"+moduleName.replaceAll("\\s+", ""));
			tab.setSelected(true);
			
		} catch (Exception e) {
			final Tab tab = new Tab(moduleName);
			tab.setId("tab_"+moduleName.replaceAll("\\s+", ""));
			tab.setClosable(true);
			tab.setParent(tabs);
			tab.setSelected(true);
			tab.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					event.stopPropagation();
					Messagebox.show("Are you sure want to close this tab ?", "Warning", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public void onEvent(Event event) throws Exception {	
							int status = (int) event.getData();
							if(status == Messagebox.YES)
								tab.close();
							else
								return;
						}
					});
					
				}
			});
			
		}
		
		try {
			Tabpanel tabpanel = (Tabpanel) tabPanels.getFellow("panel_"+moduleName.replaceAll("\\s+", ""));
			tabpanel.getFirstChild().detach();
			Executions.createComponents(path, tabpanel, params);
		} catch (Exception e) {
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setId("panel_"+moduleName.replaceAll("\\s+", ""));
			tabpanel.setStyle("overflow: auto");
			Executions.createComponents(path, tabpanel, params);
			tabpanel.setParent(tabPanels);
		}
		
	}
	
	private void buildDashboard(){
		log.debug("Building Dashboard");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workspaceMain", thisComposer);
		Tab tab = new Tab("Dashboard");
		tab.setClosable(false);
		tab.setParent(tabs);
		tab.setSelected(true);
		
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.setStyle("overflow: auto");
		Executions.createComponents("~./hcms/workflow/workflow_dashboard.zul", tabpanel, params);
		tabpanel.setParent(tabPanels);
	}
	
	private FunctionPermission getFunctionPermissions(Long functionId){
		FunctionPermission functionPermission = null;
		if(securityProfile.getSecurity().getFunctionDefaultAccess().equals(FunctionAccessType.FULL.toString()))
			functionPermission = new FunctionPermission(true, true, true);
		else if (securityProfile.getSecurity().getFunctionDefaultAccess().equals(FunctionAccessType.READ.toString()))
			functionPermission = new FunctionPermission(false, false, false);
		else{
			functionPermission = securityServiceImpl.getFunctionPermissionByFunctionId(functionId); 
		}
		return functionPermission;
	}

	@Override
	public void render(Treeitem item, DefaultTreeNode<TreeInfo> data, int index)
			throws Exception {
		final TreeInfo fi = data.getData();
		if(fi.getType().equals(MenuItemType.FUNCTION.toString())){
			final AuthorizedMenuDTO authorizedMenuDTO = (AuthorizedMenuDTO) fi.getValue();
			item.setImage(Labels.getLabel("image.link.window"));
			
			item.addEventListener(Events.ON_CLICK, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = -937092478271882623L;

				@Override
				public void onEvent(Event event) throws Exception {
					AuthorizedMenuDTO function = (AuthorizedMenuDTO) fi.getValue();
					FunctionPermission functionPermission = getFunctionPermissions(function.getActionId());
					showForm(function.getActionId(),authorizedMenuDTO.getActionUrl(), authorizedMenuDTO.getModuleName(), authorizedMenuDTO.getUserManual(), functionPermission, null);
					log.info(String.format("User Accessing %s in module %s", authorizedMenuDTO.getItemName(), authorizedMenuDTO.getModuleName()));
					recordUserAccess(authorizedMenuDTO.getItemName(), authorizedMenuDTO.getModuleName());
				}
			});

		}
			
        item.setLabel(fi.getPrompt());
        item.setValue(fi);
	}

	@Override
	public void navigateForm(String path, String moduleName, Map<String, Object> parameterMap) {
		showForm(null, path, moduleName, null, null, parameterMap);
	}

	public Long getResponsibilityId() {
		return responsibilityId;
	}

	public void setResponsibilityId(Long responsibilityId) {
		this.responsibilityId = responsibilityId;
	}
	
	public static StockOpnameService getStockOpnameService() {
		return (StockOpnameService) ApplicationContextUtil.getApplicationContext().getBean("stockOpnameService");
	}
}
package co.id.fifgroup.systemadmin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.Searchtextbox;
import co.id.fifgroup.systemadmin.domain.Menu;
import co.id.fifgroup.systemadmin.domain.MenuExample;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.MenuService;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;

@VariableResolver(DelegatingVariableResolver.class)
public class ResponsibiltyInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(ResponsibiltyInquiryComposer.class);
	
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate = true)
	private transient ResponsibilityService responsibilityService;
	@WireVariable(rewireOnActivate = true)
	private transient MenuService menuService;
	
	@Wire
	private Searchtextbox txtResponsibility;
	@Wire
	private Listbox lstMenu;
	@Wire
	private Listbox lstResponsibility;
	@Wire
	private Paging pgResponsibility;
	@Wire
	private Button btnNew;
	
	private ResponsibilityDTO responsibilityDto;
	private Menu menu;
	private ListModelList<ResponsibilityDTO> listModelResponsibility;
	private List<ResponsibilityDTO> listResponsibilities;
	private int totalSize;
	private String responsibilityName;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		populateMenu();
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		listModelResponsibility = new ListModelList<ResponsibilityDTO>();
		lstResponsibility.setModel(listModelResponsibility);
	}
		
	private void populateMenu() {
		MenuExample example = new MenuExample();
		example.setOrderByClause("MENU_NAME ASC");
		List<Menu> menus = menuService.getMenuByExample(example);
		Menu menu = new Menu();
		menu.setMenuName("-Select-");
		menus.add(0, menu);
		ListModelList<Menu> model = new ListModelList<Menu>(menus);
		lstMenu.setModel(model);
		lstMenu.renderAll();
		lstMenu.setSelectedIndex(0);
	}
	
	@Listen("onDownloadUserManual = #winResponsibilityInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = button#btnFind")
	public void find() {
		responsibilityName = txtResponsibility.getValue();
		if(responsibilityName.equals("%%") && lstMenu.getSelectedIndex() == 0) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = -8756250972566999901L;

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
	
	private void search() {
		try {
			Menu menuIdSelected = new Menu();
			if(lstMenu.getSelectedIndex() > -1) {
				menu = lstMenu.getSelectedItem().getValue();
				menuIdSelected.setId(menu.getId());
			}
			
			responsibilityDto = new ResponsibilityDTO();
			responsibilityDto.setResponsibilityName(txtResponsibility.getValue());
			responsibilityDto.setMenuId(menuIdSelected.getId());
						
			/*totalSize = responsibilityService.countResponsibilityAndMenuByParameter(responsibilityDto);
			pgResponsibility.setTotalSize(totalSize);
			pgResponsibility.setPageSize(GlobalVariable.getMaxRowPerPage());
			pgResponsibility.setActivePage(0);*/
			generateDataAndPaging();
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/responsibility_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen("onDetail= #winResponsibilityInquiry")
	public void onDetail(ForwardEvent event) {
		responsibilityDto = (ResponsibilityDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("responsibilityInquiry", responsibilityDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/responsibility_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onPaging = #winResponsibilityInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging();
	}
	
	private void generateDataAndPaging() {		
		Menu menuIdSelected = new Menu();
		if(lstMenu.getSelectedIndex() > -1) {
			menu = lstMenu.getSelectedItem().getValue();
			menuIdSelected.setId(menu.getId());
		}
		
		responsibilityDto = new ResponsibilityDTO();
		responsibilityDto.setResponsibilityName(txtResponsibility.getValue());
		responsibilityDto.setMenuId(menuIdSelected.getId());
					
		//listResponsibilities = responsibilityService.selectResponsibilityAndMenuByParameter(responsibilityDto);
		//listResponsibilities = responsibilityService.selectResponsibilityAndMenuByParameterWithRowbounds(responsibilityDto, pgResponsibility.getPageSize(), pgResponsibility.getActivePage() * pgResponsibility.getPageSize());
		listResponsibilities = responsibilityService.selectResponsibilityAndMenuByParameter(responsibilityDto);
		listModelResponsibility.clear();
		listModelResponsibility.addAll(listResponsibilities);
	}
}

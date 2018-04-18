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
import co.id.fifgroup.systemadmin.dto.MenuDto;
import co.id.fifgroup.systemadmin.service.MenuService;

@VariableResolver(DelegatingVariableResolver.class)
public class MenuInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(MenuInquiryComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient MenuService menuService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Searchtextbox txtMenuName;
	@Wire
	private Searchtextbox txtDescription;
	@Wire
	private Listbox lstMenu;
	@Wire
	private Paging pgMenu;
	@Wire
	private Button btnNew;
	
	private MenuDto menuDto;
	private ListModelList<MenuDto> listModelMenu;
	private List<MenuDto> listMenu;
	private int totalSize;
	private String menuName;
	private String description;
	private FunctionPermission functionPermission;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void initFunctionSecurity(){
		if(!functionPermission.isCreateable())
			btnNew.setDisabled(true);
	}
	
	private void init() {
		listModelMenu = new ListModelList<MenuDto>();
		lstMenu.setModel(listModelMenu);
	}
	
	@Listen("onDownloadUserManual = #winMenuInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}
	
	@Listen ("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/menu_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}

	@Listen ("onClick = button#btnFind; onOK=#txtFunctionName,#txtDescription")
	public void find() {
		menuName = txtMenuName.getValue();
		description = txtDescription.getValue();
		if(menuName.equals("%%") && description.equals("%%")) {
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
			MenuExample example = new MenuExample();
			example.createCriteria().andMenuNameLikeInsensitive(txtMenuName.getValue())
				.andDescriptionLikeInsensitive(txtDescription.getValue());
			
			/*totalSize = menuService.countByExample(example);
			pgMenu.setTotalSize(totalSize);
			pgMenu.setPageSize(GlobalVariable.getMaxRowPerPage());
			pgMenu.setActivePage(0);*/
			generateDataAndPaging();
			
		} catch (Exception e) {
			log.error("error", e);
		}
	}
	
	@Listen("onDetail= #winMenuInquiry")
	public void onDetail(ForwardEvent event) {
		menuDto = (MenuDto) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuDto", menuDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/systemadmin/menu_setup.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	
	@Listen("onPaging = #winMenuInquiry")
	public void onPaging(ForwardEvent evt) {
		generateDataAndPaging();
	}
	
	private void generateDataAndPaging() {
		Menu menu = new Menu();
		menu.setMenuName(txtMenuName.getValue());
		menu.setDescription(txtDescription.getValue());
		listMenu = menuService.selectMenuByMenu(menu);
		listModelMenu.clear();
		listModelMenu.addAll(listMenu);
	}
}

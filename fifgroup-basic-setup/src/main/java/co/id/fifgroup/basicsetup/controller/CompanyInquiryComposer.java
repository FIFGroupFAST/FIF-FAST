package co.id.fifgroup.basicsetup.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.CompanyDTO;
import co.id.fifgroup.basicsetup.service.CompanyService;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.core.util.UserManualDownloadUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class CompanyInquiryComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;

	@WireVariable("arg")
	private Map<String, Object> arg;

	@Wire
	private Datebox dtbEffectiveDate;
	@Wire
	private Textbox txtCompanyName;
	@Wire
	private Listbox lstCompany;
	@Wire
	private Button btnNew;

	private List<CompanyDTO> companyDtos;

	private ListModelList<CompanyDTO> listModelCompany;

	@WireVariable
	private CompanyService companyServiceImpl;

	private Date effectiveOnDate;
	private String companyName;
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
		initListModelCompany();
		dtbEffectiveDate.setFormat(GlobalVariable.getDateFormat());
	}

	@Listen("onDownloadUserManual = #winCompanyInquiry")
	public void downloadUserManual() {
		Long functionId  = (Long) arg.get(GlobalVariable.ACTION_ID);
		UserManualDownloadUtil userManualDownloadUtil = (UserManualDownloadUtil) SpringUtil.getBean("userManualDownloadUtilImpl");
		DownloadUtil.downloadFile(userManualDownloadUtil.getManualFilePath(functionId), "application/file");
	}

	private void initListModelCompany() {
		listModelCompany = new ListModelList<CompanyDTO>();
		lstCompany.setPageSize(GlobalVariable.getMaxRowPerPage());
		lstCompany.setModel(listModelCompany);
	}

	@Listen("onClick = button#btnNew")
	public void addNew() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/company_setup.zul",
				getSelf().getParent(), params);
		getSelf().detach();
	}

	@Listen("onClick = button#btnFind; onOK = #txtCompanyName")
	public void onFind() {
		effectiveOnDate = dtbEffectiveDate.getValue();
		companyName = txtCompanyName.getValue();
		if (effectiveOnDate == null && companyName.equals("%%")) {
			Messagebox.show(Labels.getLabel("common.confirmationInquiry"),
					Labels.getLabel("common.confirmationTitle"), Messagebox.YES
							| Messagebox.NO, null,
					new SerializableEventListener<Event>() {

						private static final long serialVersionUID = -8756250972566999901L;

						@Override
						public void onEvent(Event event) throws Exception {
							int resultButton = (int) event.getData();
							if (resultButton == Messagebox.YES) {
								search();
							}
						}

					});
		} else {
			search();
		}
	}

	private void search() {
		companyDtos = companyServiceImpl.getCompaniesByEffectiveOnDateAndCompanyName(effectiveOnDate, companyName);
		listModelCompany.clear();
		listModelCompany.addAll(companyDtos);
	}

	@Listen("onDetail = #winCompanyInquiry")
	public void onDetail(ForwardEvent evt) {
		CompanyDTO selectedData = (CompanyDTO) evt.getData();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(CompanyDTO.class.getName(), selectedData);
		params.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Executions.createComponents("~./hcms/basic-setup/company_setup.zul",
				getSelf().getParent(), params);
		getSelf().detach();
	}

}

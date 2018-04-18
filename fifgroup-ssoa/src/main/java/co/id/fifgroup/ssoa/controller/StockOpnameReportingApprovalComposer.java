package co.id.fifgroup.ssoa.controller;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
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
import org.zkoss.zul.A;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.StockOpname;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;


@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnameReportingApprovalComposer extends  SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(StockOpnameReportingApprovalComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private StockOpnameDTO selected;
	private StockOpnameDTO stockOpnameDto;
	private StockOpname stockOpname;
	
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@Wire
    private Textbox txtPeriodName;
	@Wire
    private Textbox txtPeriodStartDate;
	@Wire
    private Textbox txtBranch;
	@Wire
    private Textbox txtPeriodEndDate;
	@Wire
    private Textbox txtNote;
	@Wire
    private Label linkNumberOfAssetReg;
	@Wire
    private Label linkNumberOfProcessAssetReg;
	@Wire
    private Label linkNumberOfUnProcessAssetReg;
	@Wire
    private Label linkNumberOfAssetUnReg;
	@Wire
    private Label linkNotRecordedAssets;
	@Wire
    private Label linkAssetsStatusA;
	@Wire
    private Label linkAssetsStatusB;
	@Wire
    private Label linkAssetsStatusC;
	@Wire
    private Label linkAssetsStatusD;
	@Wire
    private Label lblDtlProcess;
	@Wire
	private Listbox lbxRegisteredDetailReport;
	
	private FunctionPermission functionPermission;
	
	private OrganizationDTO organizationDTO;
	
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationSetupService organizationSetupServiceImpl;

	@Wire
	private Window winStockOpnameReportingApproval;
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		loadStockOpnames();
	}
	
	private void loadStockOpnames() {
		//System.out.println("avmTrxId=="+arg.get("applicationData"));
		StockOpnameDTO sod = arg.get("applicationData")!=null?(StockOpnameDTO)arg.get("applicationData"):null;
		selected = stockOpnameService.getStockOpnameByAvmTrxId(sod.getAvmTrxId().toString());
		
		if(selected != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			stockOpname = stockOpnameService.getStockOpnameById(selected.getId());
			stockOpnameDto = modelMapper.map(stockOpname, StockOpnameDTO.class);
			txtPeriodName.setValue(stockOpname.getDescription());
			txtPeriodStartDate.setValue(stockOpname.getStartDate()!=null?sdf.format(stockOpname.getStartDate()):null);
			txtPeriodEndDate.setValue(stockOpname.getEndDate()!=null?sdf.format(stockOpname.getEndDate()):null);
			Branch branch = stockOpnameService.getBranchById(stockOpname.getBranchId(),stockOpname.getCompanyId());
			txtBranch.setValue(branch.getBranchName());
			linkNumberOfAssetReg.setValue(selected.getNumberOfAssetReg().toString());
			linkNumberOfProcessAssetReg.setValue(selected.getNumberOfProcessAssetReg().toString());
			linkNumberOfUnProcessAssetReg.setValue(selected.getNumberOfUnProcessAssetReg().toString());
			linkNumberOfAssetUnReg.setValue(selected.getNumberOfAssetUnReg().toString());
			linkNotRecordedAssets.setValue(selected.getNumberOfAssetUnReg().toString());
			linkAssetsStatusA.setValue(selected.getSoResultFoundGoodUsed().toString());
			linkAssetsStatusB.setValue(selected.getSoResultFoundGoodNotUsed().toString());
			linkAssetsStatusC.setValue(selected.getSoResultFoundBroken().toString());
			linkAssetsStatusD.setValue(selected.getSoResultNotFound().toString());
			txtNote.setValue(selected.getRemark());
		}
	}
	
	@Listen ("onClick = button#btnCancelReport")
	public void cancel() {
		Messagebox.show(Labels.getLabel("sam.cancelMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					//param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/stock_opname.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	@Listen("onClick = button#btnSubmit")
	public void onSubmit() {
		// if(lstStockOpnameDetail.validate()) {
		Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {

								StockOpnameDTO stockOpnameDTO = new StockOpnameDTO();
								stockOpnameDTO.setId(selected.getId());
								stockOpnameDTO.setBranchId(securityServiceImpl.getSecurityProfile().getBranchId());
								stockOpnameDTO.setBusinessGroupId(
										securityServiceImpl.getSecurityProfile().getBusinessGroupId());
								//stockOpnameDTO.setBranchId(securityServiceImpl.getSecurityProfile().getBranchName());
								stockOpnameDTO
										.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
								stockOpnameDTO.setJobId(securityServiceImpl.getSecurityProfile().getJobId());

								organizationDTO = organizationSetupServiceImpl
										.findById(securityServiceImpl.getSecurityProfile().getBranchId());

								if (organizationDTO == null
										&& securityServiceImpl.getSecurityProfile().getBranchId().equals(-1L)) {
									organizationDTO = organizationSetupServiceImpl.getHeadOffice();
								}

								stockOpnameDTO.setOrganizationId(organizationDTO.getId());

								/*stockOpnameService.submit(stockOpnameDTO,
										securityServiceImpl.getSecurityProfile().getPersonUUID());*/

								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
										Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/stock_opname.zul", getSelf().getParent(),
										param);
								getSelf().detach();

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							return;
						}
					}
				});
	}
	
	
	@Listen("onNumberOfAsset= #winStockOpnameReportingApproval")
	public void onNumberOfAsset(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isUnReg", new Boolean(false));
		param.put("isReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(true));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(false));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onNumberOfProcessAsset= #winStockOpnameReportingApproval")
	public void onNumberOfProcessAsset(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isUnReg", new Boolean(false));
		param.put("isReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(true));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(false));

		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onNumberOfUnProcessAsset= #winStockOpnameReportingApproval")
	public void onNumberOfUnProcessAsset(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isUnReg", new Boolean(false));
		param.put("isReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(true));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(false));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onAssetsStatusA= #winStockOpnameReportingApproval")
	public void onAssetsStatusA(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isUnReg", new Boolean(false));
		param.put("isReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(true));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(false));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onAssetsStatusB= #winStockOpnameReportingApproval")
	public void onAssetsStatusB(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isUnReg", new Boolean(false));
		param.put("isReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(true));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(false));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onAssetsStatusC= #winStockOpnameReportingApproval")
	public void onAssetsStatusC(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isUnReg", new Boolean(false));
		param.put("isReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(true));
		param.put("isAssetStatusD", new Boolean(false));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onAssetsStatusD= #winStockOpnameReportingApproval")
	public void onAssetsStatusD(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isReg", new Boolean(true));
		param.put("isUnReg", new Boolean(false));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(true));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	
	@Listen("onNumberOfAssetUnReg= #winStockOpnameReportingApproval")
	public void onNumberOfAssetUnReg(ForwardEvent event){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stockOpnameDTO", stockOpnameDto);
		param.put("isReg", new Boolean(false));
		param.put("isUnReg", new Boolean(true));
		param.put("isNumberOfAssetReg", new Boolean(false));
		param.put("isNumberOfProcessAssetReg", new Boolean(false));
		param.put("isNumberOfUnProcessAssetReg", new Boolean(false));
		param.put("isAssetStatusA", new Boolean(false));
		param.put("isAssetStatusB", new Boolean(false));
		param.put("isAssetStatusC", new Boolean(false));
		param.put("isAssetStatusD", new Boolean(false));
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_assets.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onClick = #btnClose")
	public void close(Event e) {
		winStockOpnameReportingApproval.detach();
	}
	
	@Listen("onViewDetail= #winStockOpnameReportingApproval")
	public void onViewDetail(ForwardEvent event){
		lblDtlProcess.setVisible(true);
		lbxRegisteredDetailReport.setVisible(true);
	}
}

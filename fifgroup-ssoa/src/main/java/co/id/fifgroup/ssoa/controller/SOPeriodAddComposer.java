package co.id.fifgroup.ssoa.controller;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.DateBoxMax;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.tabularentry.DateboxListcell;
import co.id.fifgroup.core.ui.tabularentry.IntboxListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.util.FormatDateUI;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.ParameterDetail;
import co.id.fifgroup.ssoa.domain.SOPeriod;
import co.id.fifgroup.ssoa.domain.SOPeriodDetail;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.ParameterDetailDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDTO;
import co.id.fifgroup.ssoa.dto.SOPeriodDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.SOPeriodService;
import co.id.fifgroup.ssoa.service.SSOACommonService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class SOPeriodAddComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(SOPeriodAddComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient SOPeriodService soPeriodService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable(rewireOnActivate = true)
	private transient SSOACommonService ssoaCommonService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private SOPeriodAddComposer composer = this;
	
	private ListModelList<Branch> availableModel;
    private ListModelList<Branch> selectedDataModel;
	
	
	@Wire
	private TabularEntry<SOPeriodDetailDTO> selectedBranchListbox;
	@Wire
	private Listbox selectedBranch;
    @Wire
    private Listbox availableBranchListbox;
 
	@Wire
	private Textbox txtDesc;
	@Wire
	private Textbox txtSearchBranchName;
	@Wire
	private Textbox txtSearchBranchCode;
	@Wire
	private Datebox dbStart;
	@Wire
	private Datebox dbEnd;
	@Wire
	private Label lblErrorSOPeriod;
	@Wire
	private Listcell branchCode;
	@Wire
	private Button btnSave;	
	@Wire
	private Panel panelBranch;
	@Wire
	private Panel panelDetailSOPeriod;	
	@Wire
	private Label errMsgDtl;

	
	private SOPeriodDTO soPeriodDto;
	private SOPeriodDTO selected;
	private SOPeriod soPeriod;
	private List<SOPeriodDetailDTO> soPeriodDetailList;
	private FunctionPermission functionPermission;
	private boolean isDetail = false;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		//listBranch();
		if(arg.containsKey("detail")){
			isDetail = true;
		}
		loadSOPeriods();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
		
		selectedBranch.setModel(selectedDataModel = new ListModelList<Branch>());
        selectedDataModel.setMultiple(true);
        getListBranch();
	
	}
	
	
		public void listBranch() {
			if(selected == null)
			{
				Branch example = new Branch();
				example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				example.setBranchCode(txtSearchBranchCode.getValue());
				example.setBranchName(txtSearchBranchName.getValue());
				
				List<Branch> listBranch = soPeriodService.getBranchAll(example);
				availableModel =new ListModelList<Branch>(listBranch);
				ListModel<Branch> selected= selectedBranch.getModel();
				ListModelList<Branch> selectedList = (ListModelList)selected;
				Map<Long, Branch> branchSelected = new HashMap<Long, Branch>();
				for(int i=0;i<selectedList.size();i++){
					Branch branch = (Branch)selectedList.get(i);
					branchSelected.put(branch.getBranchId(), branch);
				}
				List<Branch> availableList = new ArrayList<Branch>();
				for(int x=0;x<availableModel.getSize();x++){
					Branch branch = (Branch)availableModel.get(x);
					if(branchSelected.get(branch.getBranchId())==null){
						availableList.add(branch);
					}
				}
				availableModel =new ListModelList<Branch>(availableList);
				
				availableBranchListbox.setModel(availableModel);
				this.availableModel.setMultiple(true);
			}
		}
	
		@Listen ("onClick = button#btnFind")
		public void find() {
			
			
			String branchName = txtSearchBranchName.getValue();

			if(branchName.equals("%%")) {
				Messagebox.show(Labels.getLabel("common.confirmationInquiry"), Labels.getLabel("common.confirmationTitle"), Messagebox.YES | Messagebox.NO, null, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = -8756250972566999901L;

					@Override
					public void onEvent(Event event) throws Exception {
						int resultButton = (int) event.getData();
						if(resultButton == Messagebox.YES) {
							listBranch();
						}
					}
				});
			}
			else {
				listBranch();
			}
		}
		public void getListBranch(){
	
		List<SOPeriodDetailDTO> data = new ArrayList<SOPeriodDetailDTO>();
		for(int i=0; i <selectedDataModel.getSize();i++){
			Branch branch = selectedDataModel.get(i);
			SOPeriodDetailDTO sOPeriodDetailDTO = new SOPeriodDetailDTO();
			sOPeriodDetailDTO.setBranchId(branch.getBranchId());
			sOPeriodDetailDTO.setBranchCode(branch.getBranchCode());
			sOPeriodDetailDTO.setBranch(branch);
			//sOPeriodDetailDTO.setSoPeriodId(soPeriodDto.getSoPeriodId());
			data.add(sOPeriodDetailDTO);
		}
		
		selectedBranchListbox.setModel(new ListModelList<SOPeriodDetailDTO>(data));	
	}
	
	
	private void initFunctionSecurity(){
		if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}
		
		if(isDetail){
			if(functionPermission.isEditable())
				disableComponent(false);
			else
				disableComponent(true);
		}
	}

	private void disableComponent(boolean disabled){}

	private void loadSOPeriods() {
		selected = (SOPeriodDTO) arg.get("soPeriodDto");
		if(selected != null) {
			soPeriod = soPeriodService.getSOPeriodById(selected.getSoPeriodId());
			soPeriodDto = modelMapper.map(soPeriod, SOPeriodDTO.class);
			soPeriodDetailList = soPeriodDto.getSOPeriodDetailDto();
			soPeriodDetailList = soPeriodService.getSOPeriodDetailDtoBySOPeriodId(soPeriod.getSoPeriodId());
			txtDesc.setValue(soPeriod.getDescription());
			txtDesc.setDisabled(true);
			dbStart.setValue(soPeriod.getStartDate());
			dbStart.setDisabled(true);
			dbEnd.setValue(soPeriod.getEndDate());
			dbEnd.setDisabled(true);
			//panelBranch.setVisible(false);
			panelDetailSOPeriod.setVisible(true);
			
		}
	}
	
	private ListModelList<SOPeriodDetailDTO> getSOPeriodDetailModel() {
		if(soPeriodDetailList == null || soPeriodDetailList.size() < 1) {
			soPeriodDetailList = new ArrayList<SOPeriodDetailDTO>();
			soPeriodDetailList.add(new SOPeriodDetailDTO());
		}
		ListModelList<SOPeriodDetailDTO> model = new ListModelList<SOPeriodDetailDTO>(soPeriodDetailList);
		model.setMultiple(true);
		return model;
	}
	
	private SerializableListItemRenderer<SOPeriodDetailDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<SOPeriodDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final SOPeriodDetailDTO data, int index)
					throws Exception {
				
				System.out.println("msk render");
				if(data == null){
					data.setMainTask(new SOPeriodDTO());
					data.setErrorTask(new SOPeriodDTO());
					data.setSuccessTask(new SOPeriodDTO());
				}
				
				//SOPeriodDetail selectedBranchListbox = data;
				 //new Listcell(data.getBranchId()).setParent(item);			
								
			}
		};
	}

	private TabularValidationRule<SOPeriodDetailDTO> getTabularValidationRule() {
		return new TabularValidationRule<SOPeriodDetailDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(SOPeriodDetailDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<SOPeriodDetailDTO> model = new ListModelList<SOPeriodDetailDTO>(selectedBranchListbox.getValue());
		model.setMultiple(true);
		selectedBranchListbox.setModel(model);
	}

	private Boolean validate(){
		Boolean flag = false;
	
		if(dbStart.getValue()== null) {
			ErrorMessageUtil.setErrorMessage(dbStart, "Period Start Date must be filled");
			flag = true;
		}
		
		
		if(dbEnd.getValue()== null) {
			ErrorMessageUtil.setErrorMessage(dbEnd, "Period End Date Date must be filled");
			flag = true;
		}
		
		Date date= new Date();
		long currentDate= (long)date.getTime()-1;
		System.out.println(date);
		System.out.println(currentDate);
		
		if(dbStart.getValue() != null)
		{
			if(dbStart.getValue().getDate() < date.getDate() ) {
				ErrorMessageUtil.setErrorMessage(dbStart, "Period Start Date should not be less then current date");
				flag = true;
			}
		}
		if(dbEnd.getValue()!= null && dbStart.getValue() != null)
		{
			if(dbStart.getValue().getTime() > dbEnd.getValue().getTime()) {
				ErrorMessageUtil.setErrorMessage(dbStart, "Period Start Date should be less than Period End Date");
				flag = true;
			}
			
			if(dbEnd.getValue().getTime() < dbStart.getValue().getTime()) {
				ErrorMessageUtil.setErrorMessage(dbEnd, "Period End Date should be greater than Period Start Date");
				flag = true;
			}
		}
		if(txtDesc.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(txtDesc, "Period Name must be filled");
			flag = true;
		}
		
		if(selectedBranchListbox.getValue().size() == 0){
			ErrorMessageUtil.setErrorMessage(errMsgDtl, "Please fill participant of stock opname period");
			flag = true;
		}
		
		return flag;
	}
	
	@Listen ("onClick = button#btnSave")
	public void onSave() {
		clearErrorMessage();
		if(!validate()){
		
			if(soPeriodDto == null){
				soPeriodDto = new SOPeriodDTO();
				soPeriod = new SOPeriod();
			}
	
			soPeriodDto.setDescription(txtDesc.getValue());
			soPeriodDto.setStartDate(dbStart.getValue());
			soPeriodDto.setEndDate(dbEnd.getValue());
			soPeriodDto.setLastNotificationDate(new Date());
			soPeriodDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			if(selected == null) {
				soPeriodDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				soPeriodDto.setCreationDate(new Date());
			} else {
				soPeriodDto.setCreatedBy(selected.getCreatedBy());
				soPeriodDto.setCreationDate(selected.getCreationDate());
			}
			soPeriodDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			soPeriodDto.setLastUpdateDate(new Date());
			
			soPeriodDto.setSOPeriodDetailDto(selectedBranchListbox.getValue());
			
			soPeriodDto.setObjectEmployeeUUID(securityServiceImpl.getSecurityProfile().getPersonUUID());
			
			
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new SerializableEventListener<Event>() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {	
					int status = (int) event.getData();
					if(status == 16) {
						
						Thread thread = new Thread() {
							public void run() {
								try {
									soPeriodService.save(soPeriodDto);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						thread.start();
						
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Messagebox.show("Data aset stock opname period sedang disimpan, proses ini memerlukan waktu agak lama, silahkan lakukan pencarian beberapa kali untuk memeriksa apakah data sudah selesai disimpan.", "Information", Messagebox.YES,
									Messagebox.INFORMATION);
							Executions.createComponents("~./hcms/ssoa/so_period.zul", getSelf().getParent(), param);
							getSelf().detach();
					} else {
						return;
					}
				}
			});
		}
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(txtDesc);
		ErrorMessageUtil.clearErrorMessage(dbStart);
		ErrorMessageUtil.clearErrorMessage(dbEnd);
		ErrorMessageUtil.clearErrorMessage(errMsgDtl);
	}

	
	@Listen ("onClick = button#btnCancel")
	public void cancel() {
		Messagebox.show("Are you sure want to cancel?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/so_period.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
    @Listen("onClick = #chooseBtn")
    public void chooseItem() {
    	 chooseOne();
    }
 
    @Listen("onClick = #removeBtn")
    public void unchooseItem() {
        unchooseOne();
    }
 
    @Listen("onClick = #chooseAllBtn")
    public void chooseAllItem() {
        selectedDataModel.addAll(availableModel);
        availableModel.clear();
        getListBranch();
   
    }
 
    @Listen("onClick = #removeAllBtn")
    public void unchooseAll() {
        availableModel.addAll(selectedDataModel);
        selectedDataModel.clear();
        getListBranch();
    }
 
    @Listen("onClick = #topBtn")
    public void top() {
        Set<Branch> selection = new LinkedHashSet<Branch>(selectedDataModel.getSelection());
        selectedDataModel.removeAll(selection);
        selectedDataModel.addAll(0, selection);
        selectedDataModel.setSelection(selection);
    }
 
    @Listen("onClick = #upBtn")
    public void up() {
        Set<Branch> selection = new LinkedHashSet<Branch>(selectedDataModel.getSelection());
        if (selection.isEmpty())
            return;
        int index = selectedDataModel.indexOf(selection.iterator().next());
        if (index == 0 || index < 0)
            return;
        selectedDataModel.removeAll(selection);
        selectedDataModel.addAll(--index, selection);
        selectedDataModel.setSelection(selection);
 
    }
 
    @Listen("onClick = #downBtn")
    public void down() {
        Set<Branch> selection = new LinkedHashSet<Branch>(selectedDataModel.getSelection());
        if (selection.isEmpty())
            return;
        int index = selectedDataModel.indexOf(selection.iterator().next());
        if (index == selectedDataModel.size() - 1 || index < 0)
            return;
        selectedDataModel.removeAll(selection);
        selectedDataModel.addAll(++index, selection);
        selectedDataModel.setSelection(selection);
    }
 
    @Listen("onClick = #bottomBtn")
    public void bottom() {
        Set<Branch> selection = new LinkedHashSet<Branch>(selectedDataModel.getSelection());
        selectedDataModel.removeAll(selection);
        selectedDataModel.addAll(selection);
        selectedDataModel.setSelection(selection);
    }
 
    /**
     * Set new candidate ListModelList.
     * 
     * @param candidate
     *            is the data of candidate list model
     */
    public void setModel(List<Branch> available) {
    	availableBranchListbox.setModel(this.availableModel = new ListModelList<Branch>(available));
        this.availableModel.setMultiple(true);
        selectedDataModel.clear();
    	
        this.availableModel.setMultiple(true);
        selectedDataModel.clear();
        getListBranch();
    }
 
    /**
     * @return current chosen data list
     */
    public List<Branch> getChosenDataList() {
        return new ArrayList<Branch>(selectedDataModel);
    }
 
    private Set<Branch> chooseOne() {
        Set<Branch> set = availableModel.getSelection();
        selectedDataModel.addAll(set);
        availableModel.removeAll(set);
        getListBranch();
        return set;
    }
 
    private Set<Branch> unchooseOne() {
        Set<Branch> set = selectedDataModel.getSelection();
        availableModel.addAll(set);
        selectedDataModel.removeAll(set);
        getListBranch();
        return set;
    }
 
   
}
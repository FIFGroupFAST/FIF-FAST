package co.id.fifgroup.ssoa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonEmployeeNumberBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.domain.AssetLabeling;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetail;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample.Criteria;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.service.AssetLabelingService;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;



@VariableResolver(DelegatingVariableResolver.class)
public class AssetLabelingAddComposer extends SelectorComposer<Window> {
	private static Logger log = LoggerFactory.getLogger(AssetLabelingAddComposer.class);

	private static final long serialVersionUID = 1L;
	
	@Wire
	private Listitem lstAsset1;
	@Wire
	private Listitem lstAsset2;
	@Wire
	private Listitem lstAsset3;
	@Wire
	private Listbox cbReprintingReason;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnSearch;
	@Wire
	private Button btnAddAsset;
	@Wire
    private Textbox txtBranchName;
	@Wire
    private Textbox txtBranchId;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@Wire
	private Combobox cmbBranch;
	@Wire
	private Datebox startDate;
	@Wire
	private Datebox endDate;
	@Wire
	private Datebox labelingDate;
	@Wire
	private Textbox branchOwner;
	@Wire
	private Textbox description;
	@Wire
	private Textbox branchOwnerAdd;
	@Wire
	private Listcell assetNumber;
	@Wire
	private Datebox dtDateStart;
	@Wire
	private Datebox dtDateEnd;
	@Wire
	private Groupbox gbAssetLabeling;
	@Wire
	private Groupbox gbAssetLabelingAdd;
	@Wire
	private Groupbox gbAssetLabelingDetail;
	@Wire
	private Label lbldelete;
	@Wire
	private Listbox lstAssetLabeling;
	@Wire
	private TabularEntry<AssetLabelingDetailDTO> lstAssetAdd;
	@Wire
	private Listbox lbxAssetSearch;
	@Wire
	private Window winGenericDoubleLov;
	@Wire
	private Window winAssetLabeling;
	@Wire
	private CommonEmployeeNumberBandbox bnbApprover;
	@Wire
	private TabularEntry<AssetLabelingDetailDTO> assetLabelingDetail;
	@Wire
	private Paging pgListOfValue;
	@Wire
	private Label errMsgDtl;
	@Wire
	private Iframe iFramePdf;

	private List<AssetLabelingDetailDTO> assetLabelingDetailList;
	
	private List<AssetLabelingDetailDTO> assetsList;

	private AssetLabelingDTO assetLabelingDto;
	private AssetLabelingDTO selected;
	private AssetLabelingDetailDTO assetTemp;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private FunctionPermission functionPermission;
	private boolean disabled = false;
	private AssetLabeling assetLabeling;
	private OrganizationDTO organizationDTO;
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient AssetLabelingService assetLabelingService;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private Map<Long, AssetLabelingDetailDTO> editAssetLabelingDtl = new HashMap<Long, AssetLabelingDetailDTO>();

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		System.out.println("msk doAfterCompose");
		init();
		//initFunctionSecurity();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);

		
			
		 lstAssetAdd.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
				private static final long serialVersionUID = -91355005227901153L;

			
				public void onEvent(Event event) throws Exception {
					
					if(event.getData()!=null){
						if(assetsList == null){ 
							assetsList = new ArrayList<AssetLabelingDetailDTO>(); 
							assetsList.addAll((List<AssetLabelingDetailDTO>)event.getData());
						}else{
								for (int i = 0; i < ((List<AssetLabelingDetailDTO>) event.getData()).size(); i++) {
									AssetLabelingDetailDTO asset = (AssetLabelingDetailDTO) ((List<AssetLabelingDetailDTO>) event.getData()).get(i);
									//AssetLabelingDetailDTO lstAssetLabelingDetailDTO = (AssetLabelingDetailDTO)assetsList.get(i);
									boolean flag = false;
									for(int x=0;x<assetsList.size();x++){
										assetTemp = assetsList.get(x);
										System.out.println(asset.getAssetNumber()+"=="+assetTemp.getAssetNumber());
										if(asset.getAssetNumber().trim().equals(assetTemp.getAssetNumber().trim())){
											flag = true;
										}
										
										editAssetLabelingDtl.put(asset.getLabelingDetailId(), asset);
										
										int row =assetLabelingDetailList.lastIndexOf(asset);
									}
									
									if(!flag){
										assetsList.add(asset);
									}
									
									
								}
						}
						
						buildAssetLabelingDetail();
						ListModelList<AssetLabelingDetail> list = new ListModelList<AssetLabelingDetail>(assetsList);
						lstAssetAdd.setModel(list);
					}
				}
				});
			

	}
	


	private void init() {
		String action = (String) arg.get("Branch");
		branchOwnerAdd.setValue(action);
	}
	
	private void initFunctionSecurity(){
		if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}
	}
				
	private void disableComponent(boolean disabled){
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
		all.setKey("");
		all.setValue("ALL");
		all.setDescription("--Select--");
		data.add(all);
		data.addAll(assetLabelingService.getLabelingPrintingType());
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbReprintingReason.setModel(model);
		cbReprintingReason.renderAll();
		cbReprintingReason.setSelectedIndex(0);
	}
	
	private void buildAssetLabelingDetail() {
		lstAssetAdd.setModel(getAssetLabelingDetailModel());
		lstAssetAdd.setItemRenderer(getListitemRenderer());
		lstAssetAdd.setValidationRule(getTabularValidationRule());
		lstAssetAdd.renderAll();
	}

	
	@Listen("onClick = #btnCancel")
	public void cancel() {
		Messagebox.show("Are you sure want to cancel ?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {	
				int status = (int) event.getData();
				if(status == 16) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
					Executions.createComponents("~./hcms/ssoa/asset_labeling.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
		
	}
	
	
	private ListModelList<AssetLabelingDetailDTO> getAssetLabelingDetailModel() {
		if(assetLabelingDetailList == null || assetLabelingDetailList.size() < 1) {
			assetLabelingDetailList = new ArrayList<AssetLabelingDetailDTO>();
			assetLabelingDetailList.add(new AssetLabelingDetailDTO());
		}
		
		ListModelList<AssetLabelingDetailDTO> model = new ListModelList<AssetLabelingDetailDTO>(assetLabelingDetailList);
		model.setMultiple(true);
		return model;
	}

	
	private TabularValidationRule<AssetLabelingDetailDTO> getTabularValidationRule() {
		return new TabularValidationRule<AssetLabelingDetailDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(AssetLabelingDetailDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	
	private SerializableListItemRenderer<AssetLabelingDetailDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<AssetLabelingDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final AssetLabelingDetailDTO data, int index)
					throws Exception {
				
				
				
				AssetLabelingDetail lstStockOpnameDetail = data;
				
				new Listcell().setParent(item);
				/*A result = new A();
				createLinkResult(item,data,result);
				*/
				Label assetNumber = new Label();
				createAssetNumber(item,data,assetNumber);
				Label printedCount = new Label();
				createPrintedCount(item,data,printedCount);
				
				Listbox reprintingReason = new Listbox();
				List<KeyValue> data2 = new ArrayList<KeyValue>();
				data2.addAll(assetLabelingService.getLabelingPrintingType());
				ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
				reprintingReason.setMold("select");
				reprintingReason.setWidth("150px");
				KeyValue all = new KeyValue();
				all.setKey("0");
				all.setValue("ALL");
				all.setDescription("--Select--");
				reprintingReason.appendItem((String)all.getDescription(), (String)all.getValue());
				for(int i=0;i<model2.getSize();i++){
					KeyValue key = (KeyValue)model2.get(i);
					reprintingReason.appendItem((String)key.getDescription(), (String)key.getValue());
				}
				
				
				reprintingReason.renderAll();
				reprintingReason.setSelectedIndex(0);
				
				createReprintingReason(item,data,reprintingReason,index);
				
				
				Label assetTypeName = new Label();
				createAssetTypeName(item,data,assetTypeName);
				Label branchcode = new Label();
				createBranchCode(item,data,branchcode);
				Label branchName = new Label();
				createBranchName(item,data,branchName);
				Label locationCode = new Label();
				createLocationCode(item,data,locationCode);
				Label locationName = new Label();
				createLocationName(item,data,locationName);
				Label categoryName = new Label();
				createAssetCategory(item,data,categoryName);
				Label datePlacedInService = new Label();
				createDateOfService(item,data,datePlacedInService);
				Label desc = new Label();
				createDescription(item,data,desc);
				Label serialNumber = new Label();
				createSerialNumber(item,data,serialNumber);
			}
		};
	}
	
	
	private void createAssetNumber(Listitem item, final AssetLabelingDetailDTO data, Label assetNumber) {
		Listcell cell = new Listcell();
		assetNumber.setValue(data.getAssetNumber());
		assetNumber.setParent(cell);
		cell.setParent(item);
	}
	
	private void createPrintedCount(Listitem item, final AssetLabelingDetailDTO data, Label printedCount) {
		Listcell cell = new Listcell();
		printedCount.setValue(data.getPrintedCount()!=null?data.getPrintedCount().toString():null);
		printedCount.setParent(cell);
		cell.setParent(item);
	}
	
	private void createDescription(Listitem item, final AssetLabelingDetailDTO data, Label desc) {
		Listcell cell = new Listcell();
		desc.setValue(data.getDescription());
		desc.setParent(cell);
		cell.setParent(item);
	}
	
	private void createReprintingReason(Listitem item, final AssetLabelingDetailDTO data, final Listbox ReprintingReason, final int index) {
		Listcell cell = new Listcell();
		
		for(int i=0;i<ReprintingReason.getItemCount();i++){
			Listitem val = (Listitem)ReprintingReason.getItems().get(i);
			if(val.getValue().toString().equals(data.getReprintingReasonCode())){
				ReprintingReason.setSelectedIndex(i);
			}
		}
		ReprintingReason.addEventListener("onSelect", new SerializableEventListener<Event>() {
			//final KeyValue keyResult = (KeyValue)HOrecommendation.getModel().getElementAt(HOrecommendation.getSelectedIndex());
			private static final long serialVersionUID = 1L;
            //Listitem item = HOrecommendation.getSelectedItem().getLabel();
            
			@Override
			public void onEvent(Event arg0) throws Exception {
				System.out.println("HOrecommendation.getSelectedItem().getValue()=="+ReprintingReason.getSelectedItem().getValue());
				data.setReprintingReasonCode((String)ReprintingReason.getSelectedItem().getValue());
				
				editAssetLabelingDtl.put(data.getLabelingDetailId(), data);
				
			}
		});
		
		ReprintingReason.addEventListener("onFocus", new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;
            
			@Override
			public void onEvent(Event arg0) throws Exception {
				lstAssetAdd.setSelectedIndex(index);
				
				
			}
		});
		ReprintingReason.setParent(cell);
		cell.setParent(item);
	}
	
	
	private void createAssetTypeName(Listitem item, final AssetLabelingDetailDTO data, Label assetTypeName) {
		Listcell cell = new Listcell();
		assetTypeName.setValue(data.getAssetTypeName());
		assetTypeName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBranchCode(Listitem item, final AssetLabelingDetailDTO data, Label branchCode) {
		Listcell cell = new Listcell();
		branchCode.setValue(data.getBranchCode()!=null?data.getBranchCode():null);
		branchCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createBranchName(Listitem item, final AssetLabelingDetailDTO data, Label branchName) {
		Listcell cell = new Listcell();
		branchName.setValue(data.getBranchName());
		branchName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLocationCode(Listitem item, final AssetLabelingDetailDTO data, Label locationCode) {
		Listcell cell = new Listcell();
		locationCode.setValue(data.getLocationCode());
		locationCode.setParent(cell);
		cell.setParent(item);
	}
	
	private void createLocationName(Listitem item, final AssetLabelingDetailDTO data, Label locationName) {
		Listcell cell = new Listcell();
		locationName.setValue(data.getLocationName());
		locationName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createAssetCategory(Listitem item, final AssetLabelingDetailDTO data, Label categoryName) {
		Listcell cell = new Listcell();
		categoryName.setValue(data.getCategoryName());
		categoryName.setParent(cell);
		cell.setParent(item);
	}
	
	private void createDateOfService(Listitem item, final AssetLabelingDetailDTO data, Label datePlacedInService) {
		Listcell cell = new Listcell();
		datePlacedInService.setValue(data.getDatePlacedInService()!=null?sdf.format(data.getDatePlacedInService()):null);
		datePlacedInService.setParent(cell);
		cell.setParent(item);
	}
	
	private void createSerialNumber(Listitem item, final AssetLabelingDetailDTO data, Label serialNumber) {
		Listcell cell = new Listcell();
		serialNumber.setValue(data.getSerialNumber());
		serialNumber.setParent(cell);
		cell.setParent(item);
	}
	
	
	private Boolean validate(){
		Boolean flag = false;
		
		if(labelingDate.getValue()== null) {
			ErrorMessageUtil.setErrorMessage(labelingDate, "Labeling Date must be filled");
			flag = true;
		}
		
		if(lstAssetAdd == null || lstAssetAdd.getModel() == null || lstAssetAdd.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(errMsgDtl, "Please fill asset to be labeling");
			flag = true;
		}
		
		else if(lstAssetAdd != null || lstAssetAdd.getModel() != null || lstAssetAdd.getModel().getSize() > 0){
			int x=0;
			boolean f = false;
			for(int i=0;i<lstAssetAdd.getModel().getSize();i++){
				x=i+1;
				AssetLabelingDetailDTO assetLabelingDetailDTO = (AssetLabelingDetailDTO)lstAssetAdd.getModel().getElementAt(i);
				if(assetLabelingDetailDTO.getReprintingReasonCode() == null || assetLabelingDetailDTO.getReprintingReasonCode().isEmpty() || assetLabelingDetailDTO.getReprintingReasonCode().equals("ALL")){
					f= true;
				}
				
			}
			if(f){
				ErrorMessageUtil.setErrorMessage(errMsgDtl, "Please fill Reprinting Reason");
				flag = true;
			}
			
		}	
		 
		return flag;
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(labelingDate);
		ErrorMessageUtil.clearErrorMessage(errMsgDtl);
	}
	
	@Listen("onClick = #btnSave")
	public void save() {
		clearErrorMessage();
		if(!validate()){
			if (assetLabelingDto == null) {
				assetLabelingDto = new AssetLabelingDTO();
				assetLabeling = new AssetLabeling();
			}
	
			assetLabelingDto.setLabelingDate(labelingDate.getValue());
			assetLabelingDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			String branchId = (String) arg.get("BranchId");
			assetLabelingDto.setBranchId(Long.parseLong(branchId));
			assetLabelingDto.setDescription(description.getValue());
	
			assetLabelingDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetLabelingDto.setCreationDate(new Date());
	
			assetLabelingDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetLabelingDto.setLastUpdateDate(new Date());
	
			assetLabelingDto.setAssetLabelingDto((List<AssetLabelingDetailDTO>) lstAssetAdd.getListModel());
			
			if (organizationDTO == null
					&& securityServiceImpl.getSecurityProfile().getBranchId().equals(-1L)) {
				organizationDTO = organizationSetupServiceImpl.getHeadOffice();
			}
	
			//assetLabelingDto.setOrganizationId(organizationDTO.getId());
	
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {
								AMedia aMedia= (AMedia)assetLabelingService.save(assetLabelingDto);
								iFramePdf.setContent(aMedia);
								String s1="i=1;";
								Clients.evalJavaScript(s1);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
										Messagebox.INFORMATION, new SerializableEventListener<Event>() {
									private static final long serialVersionUID = 1L;

									@Override
									public void onEvent(Event event) throws Exception {	
										int status = (int) event.getData();
										if (status == 16) {
											Executions.createComponents("~./hcms/ssoa/asset_labeling.zul", getSelf().getParent(), null);
											getSelf().detach();
										}
									}
								});
								
								//Map<String, AMedia> map = new HashMap<String, AMedia>();
								//map.put("aMedia", aMedia);
								//Executions.createComponents("~./hcms/ssoa/asset_labeling.zul", getSelf().getParent(), map);
								//getSelf().detach();
							} catch (ValidationException e) {
								
							}
							
						} 
					}
				});
		 }

	}

	
	@Listen("onDetail= #winAssetLabeling")
	public void onDetail(ForwardEvent event){
		AssetLabelingDTO assetLabelingDto = (AssetLabelingDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetLabelingDto", assetLabelingDto);
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		param.put("edit", true);
		Executions.createComponents("~./hcms/ssoa/asset_labeling_detail.zul", getSelf().getParent(), param);
		getSelf().detach();
	}
	

	
	@Listen("onClick = #btnAddAsset")
	public void showPopup(Event e) {
		Map<String, Object> params = new HashMap<String, Object>();
		String branchId = (String) arg.get("BranchId");
		AssetLabelingDTO assetLabelingDTO = new AssetLabelingDTO();
		assetLabelingDTO.setBranchId(Long.parseLong(branchId));
		params.put("source", lstAssetAdd);
		params.put("branchId", assetLabelingDTO);
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_labeling_popup.zul", null, params);
		win.doModal();
	}

	public void addAsset(Event event) {
		List<AssetLabeling> asset = assetLabelingService.findAll();
		lstAssetAdd.setModel(new ListModelList<AssetLabeling>(asset));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void msgOkCancel(String question, String title) {
		Messagebox.show(question, title, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
			public void onEvent(Event e) {
				if (Messagebox.ON_OK.equals(e.getName())) {
					// alert("Click Ok");
				} else if (Messagebox.ON_CANCEL.equals(e.getName())) {
					// alert("Click Cancel");
				}
			}
		});
	}
	
	
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
	
		ListModel listData = (ListModel)lstAssetAdd.getModel();
		ListModelList data =(ListModelList)listData;
		if(data!=null && data.getSelection().size()>0){
			
		final Set<AssetLabelingDetailDTO> selection = data.getSelection();
		
		Messagebox.show("Are you sure want to delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							int i = 0;
							for (Iterator<AssetLabelingDetailDTO> iterator = selection.iterator(); selection.iterator().hasNext();) {
								AssetLabelingDetailDTO asset = iterator.next();
								assetsList.remove(asset);
								editAssetLabelingDtl.remove(asset.getAssetId());
								i++;
								if (i == selection.size()) {
									break;
								}
							}
							ListModelList<AssetLabelingDetailDTO> list = new ListModelList<AssetLabelingDetailDTO>(assetsList);
							lstAssetAdd.setModel(list);
							/*if(list.size() == 0){
								assetType = null;
							}*/
						}
					}
				});
		}
	}
	
}
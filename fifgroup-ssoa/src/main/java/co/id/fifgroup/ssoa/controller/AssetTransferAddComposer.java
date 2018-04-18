package co.id.fifgroup.ssoa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
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
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.constants.AssetTransferApprovalStatus;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.controller.StockOpnameComposer.DelegateSearch;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetail;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.AssetTransferBast;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.StockOpnameUnregAssets;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.AssetTransferService;
import co.id.fifgroup.ssoa.service.SSOACommonService;
import co.id.fifgroup.systemworkflow.service.AvmAdapterService;
import co.id.fifgroup.workstructure.dto.OrganizationDTO;
import co.id.fifgroup.workstructure.service.OrganizationSetupService;
import java.util.Iterator;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetTransferAddComposer extends  SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;
	
	private static Logger log = LoggerFactory.getLogger(AssetTransferAddComposer.class);
	
	@WireVariable(rewireOnActivate = true)
	private transient AssetTransferService assetTransferService;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient AvmAdapterService avmAdapterServiceImpl;

	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient SSOACommonService ssoaCommonService;
	@Wire
	private TabularEntry<AssetTransferDetailDTO> assetTransferDetail;
	@Wire
	private TabularEntry<AssetTransferDetailDTO> lbxAssetAdd;
	@Wire
	private Label lblErrBASTInfo;
	@Wire
	private Textbox txtDocumentNo;
	@Wire
    private Textbox txtBastDocNoDetail;
	@Wire
    private Textbox txtBastDocNoAdd;
	@Wire
	private Textbox txtBastDocName;
	@Wire
	private Textbox txtBranchOrigin;
	@Wire
	private Textbox txtBranchNameOrigin;
	@Wire
	private Textbox txtBranchDestination;
	@Wire
	private Datebox dbRequestDate;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDeleteRow;
	@Wire
	private Textbox txtReason;
	@Wire
	private Textbox txtBastDocNo;
	@Wire
	private Textbox txtBastDocImage;
	@Wire
	private Textbox txtBranchNameDestination;
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
	@Wire
	private SSOACommonPopupBandbox bnbBranchDestination;
	
	@Wire
	private Label lblErrorAssetTransfer;
	@Wire
	private Label lblErrorBAST;
	@Wire
	private Button btnSave;
	@Wire
	private Button btnSubmit;
	@Wire
	private Button btnCancel;
	@Wire
	private Button btnAddAsset;
	@Wire
	private Button btnSubmitBAST;
	@Wire
	private Button btnBack;
	@Wire
	private Button btnDelete;
	@Wire
	private Listbox lbxDetailAsset;
	@Wire
	private TabularEntry<AssetTransferBastDTO> lbxBASTAdd;
	@Wire
	private Listbox lbxBASTDetail;
	
	@Wire
	private Listbox lbxAsset;
	@Wire
	private Groupbox gbAddListAsset;
	
	@Wire
	private Groupbox gbBASTDetail;
	@Wire
	private Label errMsgDtl;
	@Wire
	private Groupbox gbListAssetDetail;
	
	private List<AssetTransferDetailDTO> assetsList;
	
	private List<AssetTransferBastDTO> assetTransferBastList;
	private ListModelList<AssetTransferDetailDTO> listModelAsset;
	private List<AssetTransferDetailDTO> listAddAsset;
	private ListModelList<AssetTransferBast> listModelBast;
	private List<AssetTransferDetailDTO> listDetailAssets;
	private List<AssetTransferBast> listDetailBast;
	private AssetTransferDTO assetTransferDto;
	private AssetTransferDTO selected;
	private AssetTransferBast assetTransferBast;
	private AssetTransferBastDTO assetTransferBastDTO;
	private AssetTransfer assetTransfer;
	private FunctionPermission functionPermission;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
	private boolean isEdit = false;
	private OrganizationDTO organizationDTO;
	@WireVariable(rewireOnActivate = true)
	private transient OrganizationSetupService organizationSetupServiceImpl;
	
	private Map<Long, AssetTransferDetailDTO> assets = new HashMap<Long, AssetTransferDetailDTO>();
	
	private static String tmpFile ="E:\\Temp\\";
	private static final String imageCode ="IMAGE_PATH";
	private static String maxSize ="1000000";
	
	String action;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
			super.doAfterCompose(comp);
			setTmpFilePath();
			buildData();
			if(arg.containsKey("edit")){
				isEdit = true;
			}
			selected = (AssetTransferDTO) arg.get("assetTransferDTO");
			if(selected != null) {
				loadAssetTransfer();
			}
			else{
				init();
			}
			functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
			if(arg.get("action")!=null){
				String action = (String) arg.get("action");
			    if(action.equals("addNew")){
					//KeyValue value = (KeyValue) arg.get("Branch");
					//bnbBranch.setRawValue(value);
				    	Branch branch = (Branch)assetTransferService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					    KeyValue value = new KeyValue();
					    value.setKey(branch.getBranchId());
					    value.setValue(branch.getBranchName());
					    value.setDescription(branch.getBranchCode());
					    bnbBranch.setRawValue(value);
					    //txtBranchNameOrigin.setValue(branch.getBranchName());
					    /*
					    if(securityServiceImpl.getSecurityProfile().getBranchId()!=null && securityServiceImpl.getSecurityProfile().getBranchId().longValue() == -1){
					    	bnbBranch.setDisabled(false);
					    }else{
					    	bnbBranch.setDisabled(true);
					    }
					    */
					    bnbBranch.setDisabled(true);
					    gbListAssetDetail.setVisible(false);
					    btnSubmitBAST.setVisible(false);
					    btnBack.setVisible(false);
					    gbBASTDetail.setVisible(false);
					    
				    }else{
				    Branch branch = (Branch)assetTransferService.getBranchById(assetTransferDto.getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
				    KeyValue value = new KeyValue();
				    value.setKey(branch.getBranchId());
				    value.setValue(branch.getBranchName());
				    value.setDescription(branch.getBranchCode());
				    bnbBranch.setRawValue(value);
				    }
				
					if(action.equals("detail")){
						selected = (AssetTransferDTO) arg.get("assetTransferDTO");
						btnSubmit.setVisible(false);
						btnSubmitBAST.setVisible(false);
						btnCancel.setVisible(false);
						bnbBranch.setValue(selected.getBranchNameOrigin());
						gbAddListAsset.setVisible(false);
						gbListAssetDetail.setVisible(true);
						btnAddRow.setVisible(false);
						btnDeleteRow.setVisible(false);
						
						if (selected.getStatusCode().equals(AssetTransferApprovalStatus.COMPLETED.getCode()))
						{
							lbxBASTDetail.setVisible(true);
							lbxBASTAdd.setVisible(false);
							btnAddRow.setVisible(false);
							btnDeleteRow.setVisible(false);
						}
						else
						{
							gbBASTDetail.setVisible(false);
						}
						
						listModelBast = new ListModelList<AssetTransferBast>();
						AssetTransferBast assetTransferBast = new AssetTransferBast();
						assetTransferBast.setTransferId(selected.getTransferId());
						listDetailBast = assetTransferService.getDetailBastByPrimaryKey(assetTransferBast.getTransferId());
						listModelBast.clear();
						listModelBast.addAll(listDetailBast);
						lbxBASTDetail.setModel(listModelBast);
						}
					else if(action.equals("BAST")){
						btnSubmit.setVisible(false);
						btnBack.setVisible(true);
						gbBASTDetail.setVisible(true);
						btnCancel.setVisible(false);
						lbxBASTDetail.setVisible(false);
						gbAddListAsset.setVisible(false);
						lbxBASTAdd.setVisible(true);	
						btnSubmitBAST.setVisible(true);
						btnAddRow.setVisible(true);
						btnDeleteRow.setVisible(true);
					}
			}
		
			initFunctionSecurity();
			loadBranchDestination(bnbBranchDestination);	
			
			try{
			bnbBranchDestination.addEventListener(Events.ON_FOCUS, new SerializableEventListener<Event>() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void onEvent(Event event) throws Exception {
					if(!bnbBranchDestination.getValue().isEmpty()){
						KeyValue keyValue = (KeyValue)bnbBranchDestination.getKeyValue();
						txtBranchNameDestination.setValue((String)keyValue.getDescription());
						txtBranchDestination.setValue((keyValue.getKey().toString()));
						}
					else
					{
						txtBranchNameDestination.setValue("");
						txtBranchDestination.setValue("");
					
					}
				}
			});
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			loadBranch(bnbBranch);	
			
			
			lbxAssetAdd.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
				private static final long serialVersionUID = -91355005227901153L;

			
				public void onEvent(Event event) throws Exception {
					
					if(event.getData()!=null){
						if(assetsList == null){ 
							assetsList = new ArrayList<AssetTransferDetailDTO>(); 
							assetsList.addAll((List<AssetTransferDetailDTO>)event.getData());
						}else{
								for (int i = 0; i < ((List<AssetTransferDetailDTO>) event.getData()).size(); i++) {
									AssetTransferDetailDTO asset = (AssetTransferDetailDTO) ((List<AssetTransferDetailDTO>) event.getData()).get(i);
									
									boolean flag = false;
									for(int x=0;x<assetsList.size();x++){
										AssetTransferDetailDTO assetTemp = assetsList.get(x);
										System.out.println(asset.getAssetNumber()+"=="+assetTemp.getAssetNumber());
										if(asset.getAssetNumber().trim().equals(assetTemp.getAssetNumber().trim())){
											flag = true;
										}
									}
									
									if(!flag){
										assetsList.add(asset);
									}
								}
						}
						
						ListModelList<AssetTransferDetailDTO> list = new ListModelList<AssetTransferDetailDTO>(assetsList);
						lbxAssetAdd.setModel(list);
					}
				}
				});
			
	}
	
	private void loadBranch(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearch());
	}

	class DelegateSearch implements SerializableSearchDelegateDoubleCriteria<Branch> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Branch> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			
			List<Branch> listBranch = new ArrayList<Branch>();
			listBranch = assetTransferService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return assetTransferService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}

	
	private void setTmpFilePath(){
		List<GlobalSettingDTO> dataImgPath = assetTransferService.getGlobalsettingByCode(SSOAConstants.imagePathTmp);
		if(dataImgPath!=null){
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
		List<GlobalSettingDTO> dataMaxSizeUpload = assetTransferService.getGlobalsettingByCode(SSOAConstants.maxSizeUploadFileTransfer);
		if(dataMaxSizeUpload!=null){
			maxSize = dataMaxSizeUpload.get(0).getGlobalSetting().getValue().toString();
		}
	}
	
	private void init() {
		
	}
	private void loadBranchDestination(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearch1());
	}

	class DelegateSearch1 implements SerializableSearchDelegateDoubleCriteria<Branch> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Branch> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			
			List<Branch> listBranch = new ArrayList<Branch>();
			listBranch = assetTransferService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return assetTransferService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}
	
	 @Listen("onDetailBast= #winAssetTransferAdd")
		public void onDetail(ForwardEvent event){
		 	AssetTransferBast assetTransferBast = (AssetTransferBast) event.getData();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("images", assetTransferBast.getBastDocPath());
			Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_transfer_bast_popup_image.zul",
					getSelf().getParent(), param);
			win.doModal();
		}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		//lbxAssetAdd.deleteRow();
		ListModel listData = (ListModel)lbxAssetAdd.getModel();
		ListModelList data =(ListModelList)listData;
		if(data!=null && data.getSelection().size()>0){
			
		final Set<AssetTransferDetailDTO> selection = data.getSelection();
		
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
							for (Iterator<AssetTransferDetailDTO> iterator = selection.iterator(); selection.iterator().hasNext();) {
								AssetTransferDetailDTO asset = iterator.next();
								assetsList.remove(asset);
								assets.remove(asset.getAssetId());
								i++;
								if (i == selection.size()) {
									break;
								}
							}
							ListModelList<AssetTransferDetailDTO> list = new ListModelList<AssetTransferDetailDTO>(assetsList);
							lbxAssetAdd.setModel(list);
							/*if(list.size() == 0){
								assetType = null;
							}*/
						}
					}
				});
			}
	}
	
	
	private void loadAssetTransfer() {
		selected = (AssetTransferDTO) arg.get("assetTransferDTO");
		//String action = (String) arg.get("action");
		if(selected != null) {
			assetTransfer = assetTransferService.getAssetTransferById(selected.getTransferId());
			assetTransferDto = modelMapper.map(assetTransfer, AssetTransferDTO.class);
			listAddAsset = assetTransferDto.getAssetTransferAdd();
		
			
			txtDocumentNo.setValue(selected.getRequestNo());
			txtDocumentNo.setReadonly(true);
			
			txtBranchNameOrigin.setValue(assetTransferDto.getBranchNameOrigin());
			txtBranchNameOrigin.setReadonly(true);
			
			txtReason.setValue(selected.getReason());
			txtReason.setReadonly(true);
			
			dbRequestDate.setValue(selected.getRequestDate());
			dbRequestDate.setDisabled(true);
			
			bnbBranch.setValue(selected.getBranchNameOrigin());
			bnbBranch.setDisabled(true);
			bnbBranch.setStyle("background:white");
			
			bnbBranchDestination.setValue(selected.getBranchNameDestination());
			bnbBranchDestination.setDisabled(true);
			bnbBranchDestination.setStyle("background:white");

			System.out.println("assetTransferDto.getBastDocNo()--" +assetTransferDto.getBastDocNo());
			txtBastDocNoAdd.setValue(assetTransferDto.getBastDocNo());
			txtBastDocNoAdd.setDisabled(true);
			
			listModelAsset = new ListModelList<AssetTransferDetailDTO>();
			AssetTransferDetailDTO assets = new AssetTransferDetailDTO();
			assets.setAssetId(selected.getTransferId());
			listDetailAssets = assetTransferService.getDetailAssetsByPrimaryKey(assets.getAssetId());
			listModelAsset.clear();
			listModelAsset.addAll(listDetailAssets);
			lbxDetailAsset.setModel(listModelAsset);
			
		}
	}
	
	private void initFunctionSecurity(){
		
	}
	
	private Boolean validate(){
		Boolean flag = false;
		
		if(dbRequestDate.getValue()== null) {
			ErrorMessageUtil.setErrorMessage(dbRequestDate, "Request Date must be filled");
			flag = true;
		}
		if(txtBranchNameDestination.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(txtBranchNameDestination, "Branch Name Destination must be filled");
			flag = true;
		}
		if(txtBranchDestination.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(bnbBranchDestination, "Branch Destination must be filled");
			flag = true;
		}
		if(bnbBranch.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(bnbBranch, "Branch Origin must be filled");
			flag = true;
		}
		if(txtBranchDestination.getValue() != null) {
			if(bnbBranchDestination.getValue().equals(bnbBranch.getValue())) {
				ErrorMessageUtil.setErrorMessage(bnbBranchDestination, "Branch Destination must be different with Branch Origin");
				flag = true;
			}
		}
		if(lbxAssetAdd == null || lbxAssetAdd.getModel() == null || lbxAssetAdd.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(errMsgDtl, "Please fill asset to be transfer");
			flag = true;
		}
		
		return flag;
	}
	
	@Listen("onClick = button#btnSubmit")
	public void onSubmit() {
		clearErrorMessage();
		if(!validate()){
			if(assetTransferDto == null){
				assetTransferDto = new AssetTransferDTO();
				assetTransfer = new AssetTransfer();
			}
			assetTransferDto.setRequestNo(txtDocumentNo.getValue());
			assetTransferDto.setEbsFlag("Y");
			assetTransferDto.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			assetTransferDto.setRequestDate(dbRequestDate.getValue());
			assetTransferDto.setBranchOrigin((Long)bnbBranch.getKeyValue().getKey());
			assetTransferDto.setBranchDestination(Long.parseLong(txtBranchDestination.getValue()));
			assetTransferDto.setBranchNameOrigin(bnbBranch.getValue());
			assetTransferDto.setReason(txtReason.getValue());
			assetTransferDto.setBranchId((Long)bnbBranch.getKeyValue().getKey());
			assetTransferDto.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
			assetTransferDto.setLastUpdateDate(new Date());
			
			if(selected == null) {
				assetTransferDto.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
				assetTransferDto.setCreationDate(new Date());
			} else {
				assetTransferDto.setCreatedBy(selected.getCreatedBy());
				assetTransferDto.setCreationDate(selected.getCreationDate());
			}
	
			if (organizationDTO == null
					&& securityServiceImpl.getSecurityProfile().getBranchId().equals(-1L)) {
				organizationDTO = organizationSetupServiceImpl.getHeadOffice();
				assetTransferDto.setAtBranchType("AT_BRANCH_TYPE_HO");
			}else{
				assetTransferDto.setAtBranchType("AT_BRANCH_TYPE_NON_HO");
			}
			
			if(assetTransferDto.getBranchDestination()!= null && assetTransferDto.getBranchDestination().intValue() == -1){
				assetTransferDto.setAtBranchDestinationType("AT_BRANCH_DEST_TYPE_HO");
			}else{
				assetTransferDto.setAtBranchDestinationType("AT_BRANCH_DEST_TYPE_NON_HO");
			}
			
			
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {
								UUID personUUID = null;
								if(securityServiceImpl.getSecurityProfile().getBranchId().intValue() == -1 && assetTransferDto.getBranchId().intValue()!= -1){
									//personUUID = UUID.fromString(stockOpnameService.getOnePersonByBranchId(selected.getBranchId()));
									StockOpnameDTO sod = ssoaCommonService.getOnePersonByBranchId(assetTransferDto.getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
									personUUID = sod.getPersonUUID();
									//personUUID = rd.getPersonUUID();
								
								}else{
									personUUID = securityServiceImpl.getSecurityProfile().getPersonUUID();
								}
								assetTransferService.submit(assetTransferDto,personUUID, lbxAssetAdd.getValue());
								
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
										Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/asset_transfer.zul", getSelf().getParent(),
										param);
								getSelf().detach();
								
							} catch (FifException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Messagebox.show(e.getMessage() , "Information", Messagebox.YES,
										Messagebox.ERROR);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Messagebox.show(e.getMessage() , "Information", Messagebox.YES,
										Messagebox.ERROR);
							}
						}
					}  
			}); 
		}
	}
	
	private void clearErrorMessage() {
		//ErrorMessageUtil.clearErrorMessage(txtReason);
		ErrorMessageUtil.clearErrorMessage(dbRequestDate);
		ErrorMessageUtil.clearErrorMessage(bnbBranchDestination);
		ErrorMessageUtil.clearErrorMessage(txtBranchNameDestination);
		ErrorMessageUtil.clearErrorMessage(bnbBranch);
		ErrorMessageUtil.clearErrorMessage(errMsgDtl);
	}
	
	
	@Listen ("onClick = button#btnCancel")
	public void cancel() {
		Messagebox.show("Are you sure want to cancel?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
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
					Executions.createComponents("~./hcms/ssoa/asset_transfer.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	@Listen ("onClick = button#btnAddAsset")
	public void onshowInput() {
		Map<String, Object> param = new HashMap<String, Object>();
		AssetTransferDTO assetTransferDto = new AssetTransferDTO();

		String branchId= bnbBranch.getKeyValue().getKey().toString();
		assetTransferDto.setBranchOrigin(Long.parseLong(branchId));
		System.out.println("branchId......" + branchId);
		param.put("source", lbxAssetAdd );
		param.put("branchId", assetTransferDto);
		
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_transfer_popup.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	
	@Listen ("onClick = button#btnBack")
	public void back() {
		Messagebox.show("Are you sure want to back?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
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
					Executions.createComponents("~./hcms/ssoa/asset_transfer.zul", getSelf().getParent(), param);
					getSelf().detach();
				} else {
					return;
				}
			}
		});
	}
	
	
	
	/*
	 * ASSETS TRANSFER --ADD / DELETE BAST
	 */
	
	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		lbxBASTAdd.setDataType(AssetTransferBastDTO.class);
		lbxBASTAdd.addRow();
	}
	
	@Listen("onClick=button#btnDeleteRow")
	public void deleteRowBast() {
		
		ListModel listData = (ListModel)lbxBASTAdd.getModel();
		ListModelList data =(ListModelList)listData;
		Set<AssetTransferBastDTO> selection = data.getSelection();
		for(int i=0; i<selection.size();i++){
			AssetTransferBastDTO assetTransferBastDTO = selection.iterator().next();
			if(assetTransferBastDTO.getBastDocPath() != null){
				File file = new File(assetTransferBastDTO.getBastDocPath());
					file.delete();
			}
		}
		
		lbxBASTAdd.deleteRow();
		rebuildBASTTabularEntryOnChangeTaskCollection();
	}
	
	public void rebuildBASTTabularEntryOnChangeTaskCollection() {
		ListModelList<AssetTransferBastDTO> model = new ListModelList<AssetTransferBastDTO>(lbxBASTAdd.getValue());
		model.setMultiple(true);
		lbxBASTAdd.setModel(model);
	}
	
	private void clearErrorMessageBAST() {
		ErrorMessageUtil.clearErrorMessage(lblErrorBAST);
	}
	private Boolean validateBAST(){
		Boolean flag = false;
		
		if(lbxBASTAdd == null || lbxBASTAdd.getModel() == null || lbxBASTAdd.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(lblErrorBAST, "Document Image must be filled");
			flag = true;
		}
		else if(lbxBASTAdd != null || lbxBASTAdd.getModel() != null || lbxBASTAdd.getModel().getSize() > 0){
			int x=0;
			boolean f = false;
			for(int i=0;i<lbxBASTAdd.getModel().getSize();i++){
				x=i+1;
				AssetTransferBastDTO assetTransferBastDTO = (AssetTransferBastDTO)lbxBASTAdd.getModel().getElementAt(i);
				if(assetTransferBastDTO.getBastDocPath() == null || assetTransferBastDTO.getBastDocPath().isEmpty()){
					f= true;
				}
				
			}
			if(f){
				ErrorMessageUtil.setErrorMessage(lblErrorBAST, "Document Image must be filled");
				flag = true;
			}
			
		}	
		return flag;
	}
	
	@Listen("onClick = #btnSubmitBAST")
	public void saveBAST() {
		clearErrorMessageBAST();
		if(!validateBAST())
		{		
			if(assetTransferBastDTO == null){
				assetTransferBastDTO = new AssetTransferBastDTO();
				assetTransferBast = new AssetTransferBast();
			}
			assetTransferBastDTO.setTransferId(selected.getTransferId());
			assetTransferBastDTO.setAssetTransferBastDetail(lbxBASTAdd.getValue());
			
			// if(stockOpnameDetail.validate()) {
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new SerializableEventListener<Event>() {
	
						
						private static final long serialVersionUID = 1L;
	
						@Override
						public void onEvent(Event event) throws Exception {
							int status = (int) event.getData();
							if (status == 16) {
								try {
									
								assetTransferService.submitBast(assetTransferBastDTO);
								Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES, Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/asset_transfer.zul", getSelf().getParent(), param);
								getSelf().detach();
							} catch (FifException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Messagebox.show(e.getMessage() , "Information", Messagebox.YES,
										Messagebox.ERROR);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Messagebox.show(e.getMessage() , "Information", Messagebox.YES,
										Messagebox.ERROR);
							}
						} else {
								return;
							}
						}
					});
		}
	}

	
	private void buildAssetAdd() {
		lbxAssetAdd.setModel(getAssetModel());
		lbxAssetAdd.setItemRenderer(getListitemRendererAsset());
		lbxAssetAdd.setValidationRule(getTabularValidationRuleAsset());
		lbxAssetAdd.renderAll();
	}
	
	private ListModelList<AssetTransferDetailDTO> getAssetModel() {
		if(listAddAsset == null || listAddAsset.size() < 1) {
			listAddAsset = new ArrayList<AssetTransferDetailDTO>();
			listAddAsset.add(new AssetTransferDetailDTO());
		}
		ListModelList<AssetTransferDetailDTO> model = new ListModelList<AssetTransferDetailDTO>(listAddAsset);
		model.setMultiple(true);
		model.setSelection(listAddAsset);
		return model;
	}
	
	private SerializableListItemRenderer<AssetTransferDetailDTO> getListitemRendererAsset() {
		return new SerializableListItemRenderer<AssetTransferDetailDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final AssetTransferDetailDTO data, int index)
					throws Exception {
				
				AssetTransferDetailDTO lstAsset = data;
				
			
			}
		};
	}
	
	
	private TabularValidationRule<AssetTransferDetailDTO> getTabularValidationRuleAsset() {
		return new TabularValidationRule<AssetTransferDetailDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(AssetTransferDetailDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	
	private void buildData() {
		lbxBASTAdd.setModel(getDataModel());
		lbxBASTAdd.setItemRenderer(getListitemRenderer());
		lbxBASTAdd.setValidationRule(getTabularValidationRule());
		lbxBASTAdd.renderAll();
	}

	private ListModelList<AssetTransferBastDTO> getDataModel() {
		if (assetTransferBastList == null || assetTransferBastList.size() < 1) {
			assetTransferBastList = new ArrayList<AssetTransferBastDTO>();
			assetTransferBastList.add(new AssetTransferBastDTO());
		}
		ListModelList<AssetTransferBastDTO> model = new ListModelList<AssetTransferBastDTO>(assetTransferBastList);
		model.setMultiple(true);
		return model;
	}

	private TabularValidationRule<AssetTransferBastDTO> getTabularValidationRule() {
		return new TabularValidationRule<AssetTransferBastDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(AssetTransferBastDTO data, List<String> errorRow) {

				return true;
			}
		};
	}

	private SerializableListItemRenderer<AssetTransferBastDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<AssetTransferBastDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final AssetTransferBastDTO data, final int index) throws Exception {

				AssetTransferBastDTO assetTransferBastDTO = data;

				new Listcell().setParent(item);
				A imageName = new A();
				Button btnImage = new Button();
				btnImage.setUpload("true,maxsize="+maxSize);
				btnImage.addEventListener(Events.ON_UPLOAD, new SerializableEventListener<UploadEvent>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 9122433436647795127L;

					@Override
					public void onEvent(UploadEvent event) throws Exception {
						String fileName = null;
						if (event.getMedias() != null) {
							StringBuilder sb = new StringBuilder("");

							for (Media m : event.getMedias()) {
								sb.append(m.getName());
							}
							fileName = sb.toString();
							// Messagebox.show(sb.toString());
						}
						boolean flag=false;
						if(fileName!=null && (fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("jpeg") || fileName.toLowerCase().endsWith("gif") || fileName.toLowerCase().endsWith("png") || fileName.toLowerCase().endsWith("bmp"))  ){
							flag = true;
						}
						if(!flag){
							Messagebox.show("File Upload must be image" , "Information", Messagebox.YES,
									Messagebox.ERROR);
						}else{
						ListModel model = lbxBASTAdd.getModel();
						AssetTransferBastDTO data = (AssetTransferBastDTO) model.getElementAt(index);
						data.setBastDocName(fileName);
						data.setBastDocFile(event.getMedia().getByteData());
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setBastDocPath(tmpFile+uuid+"_"+fileName);
					
						rebuildBASTTabularEntryOnChangeTaskCollection();
						}
					}

				});
				createImageData(item, data, btnImage, imageName);

			}
		};
	}

	private void createImageData(Listitem item, final AssetTransferBastDTO data, Button btnImage, A imageName) {
		Listcell cell = new Listcell();
		btnImage.setLabel("Browse");
		btnImage.setParent(cell);
		Space space = new Space();

		space.setParent(cell);
		imageName.setLabel(data.getBastDocName());		
		System.out.println("image Name" + data.getBastDocName());
		imageName.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				Map<String, Object> param = new HashMap<String, Object>();
				//param.put("images", data.getImageFile());
				param.put("images", data.getBastDocPath());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_transfer_bast_popup_image.zul",
						getSelf().getParent(), param);
				win.doModal();
			}
		});
		imageName.setParent(cell);
		cell.setParent(item);
	}

}

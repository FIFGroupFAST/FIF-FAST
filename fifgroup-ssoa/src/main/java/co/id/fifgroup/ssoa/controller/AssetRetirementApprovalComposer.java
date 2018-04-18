package co.id.fifgroup.ssoa.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
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
import co.id.fifgroup.core.ui.tabularentry.RadioButtonListcell;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.ssoa.constants.RetirementType;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.RetirementImg;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementImgDTO;
import co.id.fifgroup.ssoa.dto.RetirementQuotationDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.RetirementBastService;
import co.id.fifgroup.ssoa.service.RetirementService;



@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementApprovalComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;
	
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Datebox dtRequestDate;
	@Wire
	private Textbox txtDocNo;
	@Wire
	private Textbox txtNotes;
	@Wire
	private Listbox cbRetirementType;
	@Wire
	private Radiogroup rdgroup;
	@Wire
	private Listbox lstdetail;
	@Wire
	private Listbox lstBASTImg;
	
	
	
	@Wire
	private Groupbox gbQuotationsDetail;
	@Wire
	private Groupbox gbBast;
	@Wire
	private TabularEntry<RetirementQuotationDTO> lstRetirementQuotation;
	@Wire
	private TabularEntry<RetirementImgDTO> lstRetirementImg;
	@Wire
	private TabularEntry<RetirementBastDTO> lstRetirementBast;
	
	private List<RetirementQuotationDTO> listRetirementQuotationDTO;
	private List<RetirementBastDTO> listRetirementBastDTO;
	
	@WireVariable(rewireOnActivate = true)
	private transient RetirementService retirementService;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementBastService retirementBastService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Window winAssetRetirementApproval;
	private List<RetirementDetailDTO> assetsList;
	private RetirementDTO retirementDTO;
	private FunctionPermission functionPermission;
	private static String tmpFile ="E:\\Temp\\";
	private static final String imageCode ="IMAGE_PATH";
	
    
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		buildData();
		buildDataBast();
		action();
		setTmpFilePath();
		
		
	}
	
	private void setTmpFilePath(){
		List<GlobalSettingDTO> dataImgPath = retirementService.getGlobalsettingByCode(imageCode);
		if(dataImgPath!=null){
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
	}
	
	private void action(){
		cbRetirementType.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {
			private static final long serialVersionUID = -91355005227901153L;

			public void onEvent(Event event) throws Exception {
				KeyValue keyType = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
				//System.out.println("value=="+keyType.getValue());
				if(keyType.getValue().equals(RetirementType.SELL.getCode())){
					gbQuotationsDetail.setVisible(true);
					gbBast.setVisible(false);
				}else if(keyType.getValue().equals(RetirementType.GRANT.getCode())){
					gbQuotationsDetail.setVisible(false);
					gbBast.setVisible(false);
				}else{
					gbBast.setVisible(false);
					gbQuotationsDetail.setVisible(false);
				}
			}
			});
		
		
		lstRetirementImg.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
			private static final long serialVersionUID = -91355005227901153L;

		
			public void onEvent(Event event) throws Exception {
				
				if(event.getData()!=null){
					List<RetirementImgDTO> listRetirementImgDTO =  (List<RetirementImgDTO>)event.getData();
					if(listRetirementImgDTO.size()>0){
						String assetNumber = (String)listRetirementImgDTO.get(0).getRetirementDetailDTO().getAssetNumber();
						for(int i=0;i<assetsList.size();i++){
							RetirementDetailDTO retirementDetail =(RetirementDetailDTO)assetsList.get(i);
							if(retirementDetail.getAssetNumber().equals(assetNumber)){
								retirementDetail.setRetirementImg(listRetirementImgDTO);
								break;
							}
						}
					}
					
					ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(assetsList);
					lstdetail.setModel(list);
				}
			}
			});
		
		lstdetail.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
		private static final long serialVersionUID = -91355005227901153L;

	
		public void onEvent(Event event) throws Exception {
			
			if(event.getData()!=null){
				if(assetsList == null){ 
					assetsList = new ArrayList<RetirementDetailDTO>(); 
					assetsList.addAll((List<RetirementDetailDTO>)event.getData());
				}else{
						for (int i = 0; i < ((List<RetirementDetailDTO>) event.getData()).size(); i++) {
							RetirementDetailDTO asset = (RetirementDetailDTO) ((List<RetirementDetailDTO>) event.getData()).get(i);
							
							boolean flag = false;
							for(int x=0;x<assetsList.size();x++){
								RetirementDetailDTO assetTemp = assetsList.get(x);
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
				
				ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(assetsList);
				lstdetail.setModel(list);
			}
		}
		});
		
		/*btnUpload.setLabel("Browse");
		btnUpload.setUpload("true,maxsize=1000000");
		btnUpload.addEventListener(Events.ON_UPLOAD, new SerializableEventListener<UploadEvent>() {
			
			private static final long serialVersionUID = 9122433436647795127L;

			@Override
			public void onEvent(UploadEvent event) throws Exception {
				String fileName= null;
				if (event.getMedias() != null) {
					StringBuilder sb = new StringBuilder("");

					for (Media m : event.getMedias()) {
						sb.append(m.getName());
					}
					fileName = sb.toString();
	            }
				
				
				linkImageName.setLabel(fileName);
				retirementDTO.setBastFileName(fileName);
				String uuid = UUID.randomUUID().toString();
				FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
				retirementDTO.setBastFilePath(tmpFile+uuid+"_"+fileName);
				
			}

			
		});*/
		
		/*linkImageName.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				Map<String, Object> param = new HashMap<String, Object>();
				//param.put("images", data.getImageFile());
				param.put("images", retirementDTO.getBastFilePath());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", getSelf().getParent(), param);
				win.doModal();
			}
		});*/
	}
	
	private void buildData() {
		lstRetirementQuotation.setModel(getDataModel());
		lstRetirementQuotation.setItemRenderer(getListitemRenderer());
		lstRetirementQuotation.setValidationRule(getTabularValidationRule());
		lstRetirementQuotation.renderAll();
	}
	
	private ListModelList<RetirementQuotationDTO> getDataModel() {
		if(listRetirementQuotationDTO == null || listRetirementQuotationDTO.size() < 1) {
			listRetirementQuotationDTO = new ArrayList<RetirementQuotationDTO>();
			listRetirementQuotationDTO.add(new RetirementQuotationDTO());
		}
		ListModelList<RetirementQuotationDTO> model = new ListModelList<RetirementQuotationDTO>(listRetirementQuotationDTO);
		model.setMultiple(true);
		return model;
	}
	
	private TabularValidationRule<RetirementQuotationDTO> getTabularValidationRule() {
		return new TabularValidationRule<RetirementQuotationDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(RetirementQuotationDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<RetirementQuotationDTO> model = new ListModelList<RetirementQuotationDTO>(lstRetirementQuotation.getValue());
		model.setMultiple(true);
		lstRetirementQuotation.setModel(model);
	}
	
	@Listen("onClick=button#btnAddQuotation")
	public void addRow() {
		lstRetirementQuotation.setDataType(RetirementQuotationDTO.class);
		lstRetirementQuotation.addRow();
	}
	
	@Listen("onClick=button#btnDeleteQuotation")
	public void deleteRowQuotation() {
		ListModel listData = (ListModel)lstRetirementQuotation.getModel();
		ListModelList data =(ListModelList)listData;
		Set<RetirementQuotationDTO> selection = data.getSelection();
		if(selection.size()>0){
		int i=0;
		for(Iterator iter=(Iterator)selection.iterator();selection.iterator().hasNext();){
			RetirementQuotationDTO retirementQuotationDTO = (RetirementQuotationDTO)iter.next();
			if(retirementQuotationDTO!=null && retirementQuotationDTO.getDocFilePath()!=null){
			File file = new File(retirementQuotationDTO.getDocFilePath());
			file.delete();
			}
			i++;
			if(i==selection.size()){
				break;
			}
		}
		}
		lstRetirementQuotation.deleteRow();
		
		rebuildTabularEntryOnChangeTaskCollection();
	}
	
	private SerializableListItemRenderer<RetirementQuotationDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<RetirementQuotationDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final RetirementQuotationDTO data, final int index)
					throws Exception {
				
				RetirementQuotationDTO retirementQuotation = data;
				
				new Listcell().setParent(item);
				A imageName= new A();
				//createImageName(item,data,imageName);
				Button btnImage= new Button();
				btnImage.setDisabled(true);
				btnImage.setUpload("true,maxsize=1000000");
				btnImage.addEventListener(Events.ON_UPLOAD, new SerializableEventListener<UploadEvent>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 9122433436647795127L;

					@Override
					public void onEvent(UploadEvent event) throws Exception {
						String fileName= null;
						if (event.getMedias() != null) {
							StringBuilder sb = new StringBuilder("");

							for (Media m : event.getMedias()) {
								sb.append(m.getName());
							}
							fileName = sb.toString();
			            }
						ListModel model = lstRetirementQuotation.getModel();
						RetirementQuotationDTO data= (RetirementQuotationDTO)model.getElementAt(index);
						data.setDocFileName(fileName);
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setDocFilePath(tmpFile+uuid+"_"+fileName);
					
						rebuildTabularEntryOnChangeTaskCollection();
						
					}

				});
				
				TextboxListcell<RetirementQuotationDTO> textBoxVendor = new TextboxListcell<RetirementQuotationDTO>(data, data.getVendorName(),"vendorName", false);
				textBoxVendor.setParent(item);
				TextboxListcell<RetirementQuotationDTO> textBoxPrice = new TextboxListcell<RetirementQuotationDTO>(data, data.getQuotationPrice()!=null?data.getQuotationPrice().toString():null,"quotationPrice", false);
				textBoxPrice.getComponent().setStyle("float:right;text-align:right");
				textBoxPrice.setParent(item);
				createImageData(item,data,btnImage,imageName);
				RadioButtonListcell<RetirementQuotationDTO> radio = new RadioButtonListcell<RetirementQuotationDTO>(data, data.getWinnerFlag(), "winnerFlag");
				radio.getComponent().setRadiogroup(rdgroup);
				radio.setParent(item);
			}
		};
	}
	
	private void createWinner(Listitem item, final RetirementQuotationDTO data, final int index){
		Listcell cell = new Listcell();
		Radio radioWinn = new Radio();
		radioWinn.setValue(data.getWinnerFlag());
		radioWinn.setChecked(data.getWinnerFlag()!=null?data.getWinnerFlag():false);
		radioWinn.setRadiogroup(rdgroup);
		radioWinn.setParent(cell);
		cell.setParent(item);
	}
	
	private void createVendor(Listitem item, final RetirementQuotationDTO data, Textbox txtVendor){
		Listcell cell = new Listcell();
		txtVendor.setValue(data.getVendorName());
		txtVendor.setParent(cell);
		cell.setParent(item);
	}
	
	private void createPrice(Listitem item, final RetirementQuotationDTO data, Textbox txtPrice){
		Listcell cell = new Listcell();
		txtPrice.setValue(data.getVendorName());
		txtPrice.setStyle("float:right;text-align:right");
		txtPrice.setParent(cell);
		cell.setParent(item);
	}
	
	
	private void createImageData(Listitem item, final RetirementQuotationDTO data, Button btnImage, A imageName) {
		Listcell cell = new Listcell();
		btnImage.setLabel("Browse");
		btnImage.setParent(cell);
		Space space = new Space();
		
		space.setParent(cell);
		imageName.setLabel(data.getDocFileName());
		imageName.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				Map<String, Object> param = new HashMap<String, Object>();
				//param.put("images", data.getImageFile());
				param.put("images", data.getDocFilePath());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", getSelf().getParent(), param);
				win.doModal();
			}
		});
		imageName.setParent(cell);
		cell.setParent(item);
	}
	
	private void init(){
		setComboBoxList();
		//String avmTrxId = arg.get("avmTrxId")!=null?(String)arg.get("avmTrxId"):null;
		RetirementDTO rd = arg.get("applicationData")!=null?(RetirementDTO)arg.get("applicationData"):null;
		String action = "detail";
		
		if(rd.getAvmTrxIdBast()!=null){
		    retirementDTO = retirementService.getRetirementByAvmTrxIdBast(rd.getAvmTrxIdBast().toString());
		}else{
			retirementDTO = retirementService.getRetirementByAvmTrxId(rd.getAvmTrxId().toString());
		}
		
	
			retirementDTO = retirementService.getRetirementById(retirementDTO.getId());
			assetsList = new ArrayList<RetirementDetailDTO>();
			for(int i=0;i<retirementDTO.getRetirementDetail().size();i++){
				RetirementDetailExample retirementDetailExample = new RetirementDetailExample();
				retirementDetailExample.createCriteria().andIdEqualTo(retirementDTO.getRetirementDetail().get(i).getAssetId());
				List<RetirementDetailDTO> listAsset = retirementService.getAssetSODtlByCriteria(retirementDetailExample, 10, 0,null);
				if(listAsset.size()>0){
					listAsset.get(0).setRetirementImg(retirementDTO.getRetirementDetail().get(i).getRetirementImg());
					assetsList.add(listAsset.get(0));
				}
			}
			
			for(int i=0;i<assetsList.size();i++){
				RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)assetsList.get(i);
				RetirementDetailDTO retirementDetailDTOTmp = retirementService.getSOResultByAssetId(retirementDetailDTO.getAssetId());
				if(retirementDetailDTOTmp!=null){
				retirementDetailDTO.setStockOpnameSugest(retirementDetailDTOTmp.getStockOpnameSugest());
				retirementDetailDTO.setStockOpnameResult(retirementDetailDTOTmp.getStockOpnameResult());
				retirementDetailDTO.setStockOpnameHoSugest(retirementDetailDTOTmp.getStockOpnameHoSugest());
				retirementDetailDTO.setStockOpnameDtlId(retirementDetailDTOTmp.getStockOpnameDtlId());
				}
			}
			
			ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(assetsList);
			lstdetail.setModel(list);
			
			listRetirementQuotationDTO = new ArrayList<RetirementQuotationDTO>();
			listRetirementQuotationDTO.addAll(retirementDTO.getRetirementQuotation());
			
			listRetirementBastDTO = new ArrayList<RetirementBastDTO>();
			listRetirementBastDTO.addAll(retirementDTO.getRetirementBast());
			
			txtDocNo.setValue(retirementDTO.getRequestNo());
			txtNotes.setValue(retirementDTO.getNotes());
			dtRequestDate.setValue(retirementDTO.getRequestDate());
			for(int i=0;i<cbRetirementType.getModel().getSize();i++){
				KeyValue val = (KeyValue)cbRetirementType.getModel().getElementAt(i);
				if(val.getValue().toString().equals(retirementDTO.getRetirementTypeCode().toString())){
					cbRetirementType.setSelectedIndex(i);
				}
			}
			//linkImageName.setLabel(retirementDTO.getBastFileName());
			
			if(retirementDTO.getRetirementTypeCode().equals(RetirementType.SELL.getCode())){
				gbQuotationsDetail.setVisible(true);
				gbBast.setVisible(false);
			}else if(retirementDTO.getRetirementTypeCode().equals(RetirementType.GRANT.getCode())){
				gbBast.setVisible(false);
				gbQuotationsDetail.setVisible(false);
			}else{
				gbBast.setVisible(false);
				gbQuotationsDetail.setVisible(false);
			}
		
		
		if(action.equals("detail")){
			
			if(retirementDTO.getBastStatusCode()!=null){
				gbBast.setVisible(true);
			}else{
				gbBast.setVisible(false);
			}
		}
		else if(action.equals("addNew")){
			
		}else if(action.equals("BAST")){
			gbBast.setVisible(true);
			
		}
		
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue key = new KeyValue();
		key.setKey("%");
		key.setValue("%");
		key.setDescription("--Pilih--");
		data.add(key);
		data.addAll(retirementService.getLookupKeyValues(RetirementType.CODE.getCode(), null));
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbRetirementType.setModel(model);
		cbRetirementType.renderAll();
		cbRetirementType.setSelectedIndex(0);
	}
	
	@Listen ("onClick = button#btnAddRow")
	public void add() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("source", lstdetail);
		param.put("data", assetsList);
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_retirement_popup.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		ListModel listData = (ListModel)lstdetail.getModel();
		ListModelList data =(ListModelList)listData;
		Set<Assets> selection = data.getSelection();
		int i=0;
		for(Iterator<Assets> iterator = selection.iterator(); selection.iterator().hasNext();){
			Assets asset = iterator.next();
			assetsList.remove(asset);
			i++;
			if(i==selection.size()){
				break;
			}
		}
		ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(assetsList);
		lstdetail.setModel(list);
		
	}
	
	@Listen("onClick=button#btnSubmitBAST")
	public void submitBAST() {
		retirementDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		retirementDTO.setNotes(txtNotes.getValue());
		retirementDTO.setRequestNo(txtDocNo.getValue());
		retirementDTO.setRequestDate(dtRequestDate.getValue());
		KeyValue keyType = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
		retirementDTO.setRetirementTypeId(((BigDecimal)keyType.getKey()).longValue());
		retirementDTO.setRetirementTypeCode((String)keyType.getValue());
		retirementDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		retirementDTO.setCreationDate(new Date());
		retirementDTO.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
		retirementDTO.setLastUpdateDate(new Date());
		retirementDTO.setRetirementDetail(null);
		retirementDTO.setRetirementQuotation(null);
		
		try {
			 retirementBastService.submitBAST(retirementDTO, securityServiceImpl.getSecurityProfile().getPersonUUID(),lstRetirementBast.getValue());
			} catch (FifException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
				Messagebox.INFORMATION);
		Executions.createComponents("~./hcms/ssoa/asset_retirement.zul", getSelf().getParent(),
				param);
		getSelf().detach();
		
	}
	
	@Listen("onClick=button#btnSubmit")
	public void submit() {
		if(retirementDTO == null){
			retirementDTO = new RetirementDTO();
		}
		retirementDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		retirementDTO.setNotes(txtNotes.getValue());
		retirementDTO.setRequestNo(txtDocNo.getValue());
		retirementDTO.setRequestDate(dtRequestDate.getValue());
		KeyValue keyType = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
		retirementDTO.setRetirementTypeId(((BigDecimal)keyType.getKey()).longValue());
		retirementDTO.setRetirementTypeCode((String)keyType.getValue());
		retirementDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
		retirementDTO.setCreationDate(new Date());
		retirementDTO.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
		retirementDTO.setLastUpdateDate(new Date());
		
		
		List<RetirementDetailDTO> listDetail = new ArrayList<RetirementDetailDTO>();
		for(int i=0;i<assetsList.size();i++){
			RetirementDetailDTO asset =(RetirementDetailDTO)assetsList.get(i);
			RetirementDetailDTO retirementDetailDTO = new RetirementDetailDTO();
			retirementDetailDTO.setAssetId(asset.getAssetId());
			retirementDetailDTO.setRetirementImg(asset.getRetirementImg());
			//retirementDetailDTO.setRetirementId(retirementDTO.getId());
			retirementDetailDTO.setStockOpnameDtlId(asset.getStockOpnameDtlId());
			listDetail.add(retirementDetailDTO);
		}
		//retirementDTO.setRetirementDetail(listDetail);
		System.out.println("uuid=="+securityServiceImpl.getSecurityProfile().getPersonUUID());
		try {
		 retirementService.save(retirementDTO, securityServiceImpl.getSecurityProfile().getPersonUUID(),listDetail,lstRetirementQuotation.getValue());
		} catch (FifException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Thread thread = new Thread(){
		    public void run(){
		      System.out.println("Thread Running");
		      try {
					retirementService.save(retirementDTO, securityServiceImpl.getSecurityProfile().getPersonUUID());
				} catch (FifException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		  };
		 thread.start();*/
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
		Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
				Messagebox.INFORMATION);
		Executions.createComponents("~./hcms/ssoa/asset_retirement.zul", getSelf().getParent(),
				param);
		getSelf().detach();
		
	}
	
	@Listen ("onClick = button#btnClose")
	public void back() {
					
		winAssetRetirementApproval.detach();
	}
	
	
	@Listen("onViewImage= #winAssetRetirementApproval")
	public void onViewImage(ForwardEvent event){
		RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("retirementDetailDTO", retirementDetailDTO);
		param.put("source", lstRetirementImg);
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_retirement_add_image.zul", getSelf().getParent(), param);
		win.doModal();
		
	}
	
	private void buildDataBast() {
		lstRetirementBast.setModel(getDataModelBast());
		lstRetirementBast.setItemRenderer(getListitemRendererBast());
		lstRetirementBast.setValidationRule(getTabularValidationRuleBast());
		lstRetirementBast.renderAll();
	}
	
	private ListModelList<RetirementBastDTO> getDataModelBast() {
		if(listRetirementBastDTO == null || listRetirementBastDTO.size() < 1) {
			listRetirementBastDTO = new ArrayList<RetirementBastDTO>();
			listRetirementBastDTO.add(new RetirementBastDTO());
		}
		ListModelList<RetirementBastDTO> model = new ListModelList<RetirementBastDTO>(listRetirementBastDTO);
		model.setMultiple(true);
		return model;
	}
	
	private TabularValidationRule<RetirementBastDTO> getTabularValidationRuleBast() {
		return new TabularValidationRule<RetirementBastDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(RetirementBastDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	private SerializableListItemRenderer<RetirementBastDTO> getListitemRendererBast() {
		return new SerializableListItemRenderer<RetirementBastDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final RetirementBastDTO data, final int index)
					throws Exception {
				
				RetirementBastDTO retirementBast = data;
				
				new Listcell().setParent(item);
				A imageName= new A();
				//createImageName(item,data,imageName);
				Button btnImage= new Button();
				btnImage.setUpload("true,maxsize=1000000");
				btnImage.addEventListener(Events.ON_UPLOAD, new SerializableEventListener<UploadEvent>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 9122433436647795127L;

					@Override
					public void onEvent(UploadEvent event) throws Exception {
						String fileName= null;
						if (event.getMedias() != null) {
							StringBuilder sb = new StringBuilder("");

							for (Media m : event.getMedias()) {
								sb.append(m.getName());
							}
							fileName = sb.toString();
			            }
						ListModel model = lstRetirementBast.getModel();
						RetirementBastDTO data= (RetirementBastDTO)model.getElementAt(index);
						data.setBastDocName(fileName);
						data.setImageFile(event.getMedia().getByteData());
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setBastDocPath(tmpFile+uuid+"_"+fileName);
						
						
						//System.out.println("msk upload"+data.getImageFileName());
						rebuildTabularEntryOnChangeTaskCollectionBast();
						
					}

					
				});
				createImageData(item,data,btnImage,imageName);
				
						
			}
		};
	}
	
	
	private void createImageData(Listitem item, final RetirementBastDTO data, Button btnImage, A imageName) {
		Listcell cell = new Listcell();
		btnImage.setLabel("Browse");
		btnImage.setParent(cell);
		Space space = new Space();
		
		space.setParent(cell);
		imageName.setLabel(data.getBastDocName());
		imageName.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				Map<String, Object> param = new HashMap<String, Object>();
				//param.put("images", data.getImageFile());
				param.put("images", data.getBastDocPath());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", getSelf().getParent(), param);
				win.doModal();
			}
		});
		imageName.setParent(cell);
		cell.setParent(item);
	}
	
	@Listen("onClick=button#btnAddRowBast")
	public void addRowBast() {
		lstRetirementBast.setDataType(RetirementBastDTO.class);
		lstRetirementBast.addRow();
	}
	
	@Listen("onClick=button#btnDeleteBast")
	public void deleteBast() {
		ListModel listData = (ListModel)lstRetirementQuotation.getModel();
		ListModelList data =(ListModelList)listData;
		Set<RetirementBastDTO> selection = data.getSelection();
		if(selection.size()>0){
		int i=0;
		for(Iterator iter=(Iterator)selection.iterator();selection.iterator().hasNext();){
			RetirementBastDTO retirementBastDTO = (RetirementBastDTO)iter.next();
			if(retirementBastDTO!=null && retirementBastDTO.getBastDocPath()!=null){
			File file = new File(retirementBastDTO.getBastDocPath());
			file.delete();
			}
			i++;
			if(i==selection.size()){
				break;
			}
		}
		}
		lstRetirementBast.deleteRow();
		
		rebuildTabularEntryOnChangeTaskCollectionBast();
	}

	public void rebuildTabularEntryOnChangeTaskCollectionBast() {
		ListModelList<RetirementBastDTO> model = new ListModelList<RetirementBastDTO>(lstRetirementBast.getValue());
		model.setMultiple(true);
		lstRetirementBast.setModel(model);
	}
	
	
}
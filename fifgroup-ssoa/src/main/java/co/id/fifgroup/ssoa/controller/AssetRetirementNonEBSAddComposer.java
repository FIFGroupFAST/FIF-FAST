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
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
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
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.ssoa.constants.RetirementType;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.RV;
import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementImgDTO;
import co.id.fifgroup.ssoa.dto.RetirementQuotationDTO;
import co.id.fifgroup.ssoa.dto.RetirementRVDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.service.RetirementNonEBSBastService;
import co.id.fifgroup.ssoa.service.RetirementNonEBSService;
import co.id.fifgroup.ssoa.service.SSOACommonService;



@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementNonEBSAddComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;
	
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Datebox dtRequestDate;
	@Wire 
	private Label lblErrorBAST;
	@Wire
	private Textbox txtDocNo;
	@Wire
	private Textbox txtNotes;
	@Wire
	private Textbox txtNotes2;
	@Wire
	private Textbox txtBastDocNo;
	@Wire
	private Textbox txtPenerima;
	@Wire
	private Listbox cbRetirementType;
	@Wire
	private Radiogroup rdgroup;
	@Wire
	private ListModelList<RetirementBast> listModelBast;
	@Wire
	private Listbox lstdetail;	
	@Wire
	private Listbox lstBASTImg;
	@Wire
	private Button btnSubmit;
	@Wire
	private Button btnSaveRV;
	@Wire
	private Button btnDeleteBast;
	@Wire
	private Button btnAddRowBast;
	@Wire
	private Button btnAddBast;
	@Wire
	private Button btnCancel;
	@Wire
	private Button btnBack;
	@Wire
	private Button btnSubmitBAST;
	@Wire
	private Button btnAddRow;
	@Wire
	private Button btnDelete;
	@Wire
	private Button btnAddQuotation;
	@Wire
	private Button btnDeleteQuotation;
	@Wire
	private Label errMsgQuotationDtl;
	@Wire
	private Label errMsgRVDtl;
	@Wire
	private Button btnAddRV;
	@Wire
	private Button btnDeleteRV;
	@Wire
	private Label errMsgDtl;
	@Wire
	private Label lblPenerima;
	@Wire
	private Label lblNotes;
	@Wire
	private Label lblNotes2;
	@Wire
	private Listbox lbxBASTDetail;
	
	@Wire
	private Groupbox gbQuotationsDetail;
	@Wire
	private Groupbox gbRVDetail;
	@Wire
	private Groupbox gbBast;
	
	@Wire
	private TabularEntry<RetirementQuotationDTO> lstRetirementQuotation;
	@Wire
	private TabularEntry<RetirementRVDTO> lstRV;
	@Wire
	private TabularEntry<RetirementImgDTO> lstRetirementImg;
	@Wire
	private TabularEntry<RetirementBastDTO> lstRetirementBast;
	
	private List<RetirementRVDTO> listRetirementRVDTO;
	private List<RetirementQuotationDTO> listRetirementQuotationDTO;
	private List<RetirementBastDTO> listRetirementBastDTO;
	private List<RetirementBast> listDetailBast;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementNonEBSService retirementNonEBSService;
	@WireVariable(rewireOnActivate = true)
	private transient SSOACommonService ssoaCommonService;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementNonEBSBastService retirementNonEBSBastService;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private List<RetirementDetailDTO> assetsList;
	private List<RetirementRVDTO> rvList;
	private RetirementDTO retirementDTO;
	private FunctionPermission functionPermission;
	private static String tmpFile ="E:\\Temp\\";
	private static String maxSize ="1000000";
	private static String maxOriginalCostForLVA = "5000000"; 
	//private static String assetType=null;
	private Map<Long, RetirementDetailDTO> assets = new HashMap<Long, RetirementDetailDTO>();
	private Map<String, RetirementRVDTO> rvs = new HashMap<String, RetirementRVDTO>();

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
		List<GlobalSettingDTO> dataImgPath = retirementNonEBSService.getGlobalsettingByCode(SSOAConstants.imagePathTmp);
		if(dataImgPath!=null){
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
		List<GlobalSettingDTO> dataMaxSizeUpload = retirementNonEBSService.getGlobalsettingByCode(SSOAConstants.maxSizeUploadFileRetirement);
		if(dataMaxSizeUpload!=null){
			maxSize = dataMaxSizeUpload.get(0).getGlobalSetting().getValue().toString();
		}
		List<GlobalSettingDTO> dataMaxOriginalCostForLVA = retirementNonEBSService.getGlobalsettingByCode(SSOAConstants.maxOriginalCostForLVA);
		if(dataMaxOriginalCostForLVA!=null){
			maxOriginalCostForLVA = dataMaxOriginalCostForLVA.get(0).getGlobalSetting().getValue();
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
					lblPenerima.setVisible(false);
					txtPenerima.setVisible(false);
					lblNotes.setVisible(true);
					txtNotes.setVisible(true);
					lblNotes2.setVisible(false);
					txtNotes2.setVisible(false);
				}else if(keyType.getValue().equals(RetirementType.GRANT.getCode())){
					gbQuotationsDetail.setVisible(false);
					gbBast.setVisible(false);
					lblPenerima.setVisible(true);
					txtPenerima.setVisible(true);
					lblNotes.setVisible(false);
					txtNotes.setVisible(false);
					lblNotes2.setVisible(true);
					txtNotes2.setVisible(true);
				}else{
					gbBast.setVisible(false);
					gbQuotationsDetail.setVisible(false);
					lblPenerima.setVisible(false);
					txtPenerima.setVisible(false);
					lblNotes.setVisible(true);
					txtNotes.setVisible(true);
					lblNotes2.setVisible(false);
					txtNotes2.setVisible(false);
				}
			}
			});
		
		
		lstRetirementImg.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
			private static final long serialVersionUID = -91355005227901153L;

		
			public void onEvent(Event event) throws Exception {
				
				if(event.getData()!=null){
					List<RetirementImgDTO> listRetirementImgDTO =  (List<RetirementImgDTO>)event.getData();
					if(listRetirementImgDTO.size()>0){
						Long assetId = (Long)listRetirementImgDTO.get(0).getRetirementDetailDTO().getAssetId();
						for(int i=0;i<assetsList.size();i++){
							RetirementDetailDTO retirementDetail =(RetirementDetailDTO)assetsList.get(i);
							if(retirementDetail.getAssetId().equals(assetId)){
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
					
					String assetTypeTemp = null;
					for(int i=0; i<((List<RetirementDetailDTO>) event.getData()).size();i++){
						boolean flag = false; 
						RetirementDetailDTO asset = (RetirementDetailDTO) ((List<RetirementDetailDTO>) event.getData()).get(i);
						/*if(i==0){
							if(assetType == null && asset.getOriginalCost()!=null && asset.getOriginalCost().longValue() < new Long(maxOriginalCostForLVA)){
								assetType = SSOAConstants.assetTypeLVA;
							}
							if(assetType == null && asset.getOriginalCost()!=null && asset.getOriginalCost().longValue() >= new Long(maxOriginalCostForLVA)){
								assetType = SSOAConstants.assetTypeAST;
							}
						}else{
							String assetTypeTemp = null;
							if(asset.getOriginalCost()!=null && asset.getOriginalCost().longValue() < new Long(maxOriginalCostForLVA)){
								assetTypeTemp = SSOAConstants.assetTypeLVA;
							}
							if(asset.getOriginalCost()!=null && asset.getOriginalCost().longValue() >= new Long(maxOriginalCostForLVA)){
								assetTypeTemp = SSOAConstants.assetTypeAST;
							}
							if(asset.getOriginalCost()==null){
								assetTypeTemp = SSOAConstants.assetTypeLVA;
							}
							if(!assetType.equals(assetTypeTemp)){
								Messagebox.show("Asset Type must be same" , "Information", Messagebox.YES,
										Messagebox.ERROR);
								flag = true;
							}
						}*/
						
						if(i==0){
							assetTypeTemp = asset.getAssetType();
						}
						
						if(assetTypeTemp != null && asset.getAssetType()!=null && !assetTypeTemp.equals(asset.getAssetType())){
							Messagebox.show("Tipe Asset harus sama" , "Information", Messagebox.YES,
									Messagebox.ERROR);
							flag = true;
						}
						
						if(!flag){
							if(assets.get(asset.getAssetId())!=null){
								
							}else{
							assets.put(asset.getAssetId(), asset);
							assetsList.add(asset);
							}
						}
					}
					
				}else{
					    String assetTypeTemp = null;
					    if(assetsList.size()>0){
					    assetTypeTemp = assetsList.get(0).getAssetType();
					    }
						for (int i = 0; i < ((List<RetirementDetailDTO>) event.getData()).size(); i++) {
							RetirementDetailDTO asset = (RetirementDetailDTO) ((List<RetirementDetailDTO>) event.getData()).get(i);
							
							if(assetTypeTemp == null && i==0){
								assetTypeTemp = asset.getAssetType();
							}
							
							boolean flag = false;
							for(int x=0;x<assetsList.size();x++){
								RetirementDetailDTO assetTemp = assetsList.get(x);
								if(asset.getAssetId().equals(assetTemp.getAssetId())){
									flag = true;
								}
							}
							
							if(!flag){
								
								
								/*String assetTypeTemp = null;
								if(asset.getOriginalCost()!=null && asset.getOriginalCost().longValue() < new Long(maxOriginalCostForLVA)){
									assetTypeTemp = SSOAConstants.assetTypeLVA;
								}
								if(asset.getOriginalCost()!=null && asset.getOriginalCost().longValue() >= new Long(maxOriginalCostForLVA)){
									assetTypeTemp = SSOAConstants.assetTypeAST;
								}
								if(asset.getOriginalCost()==null){
									assetTypeTemp = SSOAConstants.assetTypeLVA;
								}*/
								
								if(asset.getAssetType()!=null && assetTypeTemp !=null && !asset.getAssetType().equals(assetTypeTemp)){
									Messagebox.show("Tipe Asset harus "+assetTypeTemp , "Information", Messagebox.YES,
											Messagebox.ERROR);
								}else{
									 assets.put(asset.getAssetId(), asset);
								     assetsList.add(asset);
								}
							}
								
							

						}
				}
				
				ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(assetsList);
				lstdetail.setModel(list);
			}
		}
		});
		
		lstRV.addEventListener(Events.ON_CLOSE, new SerializableEventListener<Event>() {
			private static final long serialVersionUID = -91355005227901153L;

		
			public void onEvent(Event event) throws Exception {
				
				if(event.getData()!=null){
					if(rvList == null){ 
						rvList = new ArrayList<RetirementRVDTO>(); 
						
						String assetTypeTemp = null;
						for(int i=0; i<((List<RetirementRVDTO>) event.getData()).size();i++){
							boolean flag = false; 
							RetirementRVDTO rv = (RetirementRVDTO) ((List<RetirementRVDTO>) event.getData()).get(i);
							
								if(rvs.get(rv.getRvNo())!=null){
									
								}else{
								rvs.put(rv.getRvNo(), rv);
								rvList.add(rv);
								}
							
						}
						
					}else{
						    
							for (int i = 0; i < ((List<RetirementRVDTO>) event.getData()).size(); i++) {
								RetirementRVDTO rv = (RetirementRVDTO) ((List<RetirementRVDTO>) event.getData()).get(i);
								
								
								
								boolean flag = false;
								for(int x=0;x<rvList.size();x++){
									RetirementRVDTO assetTemp = rvList.get(x);
									if(rv.getRvNo().trim().equals(assetTemp.getRvNo().trim())){
										flag = true;
									}
								}
								
								if(!flag){
									
									
									
										 rvs.put(rv.getRvNo(), rv);
									     rvList.add(rv);
									
								}
									
								

							}
					}
					
					ListModelList<RetirementRVDTO> list = new ListModelList<RetirementRVDTO>(rvList);
					lstRV.setModel(list);
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
		ListModel listData = (ListModel) lstRetirementQuotation.getModel();
		ListModelList data = (ListModelList) listData;
		if(data!=null && data.getSelection().size() >0){
		final Set<RetirementQuotationDTO> selection = data.getSelection();
		Messagebox.show("Are you sure delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
					
							if (selection.size() > 0) {
								int i = 0;
								for (Iterator iter = (Iterator) selection.iterator(); selection.iterator().hasNext();) {
									RetirementQuotationDTO retirementQuotationDTO = (RetirementQuotationDTO) iter
											.next();
									if (retirementQuotationDTO != null
											&& retirementQuotationDTO.getDocFilePath() != null) {
										File file = new File(retirementQuotationDTO.getDocFilePath());
										file.delete();
									}
									i++;
									if (i == selection.size()) {
										break;
									}
								}
							}
							lstRetirementQuotation.deleteRow();

							rebuildTabularEntryOnChangeTaskCollection();
						}
					}
				});
		}
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
				btnImage.setUpload("true,maxsize="+maxSize);
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
						
						boolean flag=false;
						if(fileName!=null && (fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("jpeg") || fileName.toLowerCase().endsWith("gif") || fileName.toLowerCase().endsWith("png") || fileName.toLowerCase().endsWith("bmp"))  ){
							flag = true;
						}
						if(!flag){
							Messagebox.show("File Upload must be image" , "Information", Messagebox.YES,
									Messagebox.ERROR);
						}else{
						
						ListModel model = lstRetirementQuotation.getModel();
						RetirementQuotationDTO data= (RetirementQuotationDTO)model.getElementAt(index);
						data.setDocFileName(fileName);
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setDocFilePath(tmpFile+uuid+"_"+fileName);
					
						rebuildTabularEntryOnChangeTaskCollection();
						}
						
					}

				});
				
				TextboxListcell<RetirementQuotationDTO> textBoxVendor = new TextboxListcell<RetirementQuotationDTO>(data, data.getVendorName(),"vendorName", false);
				textBoxVendor.setParent(item);
				//TextboxListcell<RetirementQuotationDTO> textBoxPrice = new TextboxListcell<RetirementQuotationDTO>(data, data.getQuotationPrice(),"quotationPrice", false);
				//textBoxPrice.getComponent().setStyle("float:right;text-align:right");
				//textBoxPrice.setParent(item);
				Doublebox db = new Doublebox();
				createPrice(item, data, db);
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
	
	private void createPrice(Listitem item, final RetirementQuotationDTO data, final Doublebox txtPrice){
		Listcell cell = new Listcell();
		txtPrice.setFormat("###,###.###");
		txtPrice.setValue(data.getQuotationPrice()==null?new Double(0):Double.parseDouble(data.getQuotationPrice()));
		txtPrice.setStyle("float:right;text-align:right");
		txtPrice.addEventListener("onChange", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				data.setQuotationPrice(txtPrice.getValue().toString());
			}
		});
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
		retirementDTO = (RetirementDTO) arg.get("retirementDTO");
		KeyValue key = (KeyValue) arg.get("Branch");
		String action = (String) arg.get("action");
		if(retirementDTO == null){
			retirementDTO = new RetirementDTO();
			//retirementDTO.setBranchId((Long)key.getKey());
			//retirementDTO.setBranchName((String)key.getDescription());
			//retirementDTO.setBranchCode((String)key.getValue());
			lblPenerima.setVisible(false);
			txtPenerima.setVisible(false);
			lblNotes.setVisible(true);
			txtNotes.setVisible(true);
			lblNotes2.setVisible(false);
			txtNotes2.setVisible(false);
		}else{
			retirementDTO = retirementNonEBSService.getRetirementById(retirementDTO.getId());
			assetsList = new ArrayList<RetirementDetailDTO>();
			for(int i=0;i<retirementDTO.getRetirementDetail().size();i++){
				RetirementDetailExample retirementDetailExample = new RetirementDetailExample();
				retirementDetailExample.createCriteria().andIdEqualTo(retirementDTO.getRetirementDetail().get(i).getAssetId());
				List<RetirementDetailDTO> listAsset = retirementNonEBSService.getAssetSODtlByCriteria(retirementDetailExample, 10, 0,null);
				for(int x=0;x<listAsset.size();x++){
					RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO)listAsset.get(x);
					RetirementDetailDTO retirementDetailDTOTmp = retirementNonEBSService.getSOResultByAssetId(retirementDetailDTO.getAssetId());
					if(retirementDetailDTOTmp!=null){
					retirementDetailDTO.setStockOpnameSugest(retirementDetailDTOTmp.getStockOpnameSugest());
					retirementDetailDTO.setStockOpnameResult(retirementDetailDTOTmp.getStockOpnameResult());
					retirementDetailDTO.setStockOpnameHoSugest(retirementDetailDTOTmp.getStockOpnameHoSugest());
					retirementDetailDTO.setStockOpnameDtlId(retirementDetailDTOTmp.getStockOpnameDtlId());
					}
				}
				if(listAsset.size()>0){
					listAsset.get(0).setRetirementImg(retirementDTO.getRetirementDetail().get(i).getRetirementImg());
					
					assets.put(listAsset.get(0).getAssetId(), listAsset.get(0));
					assetsList.add(listAsset.get(0));
				}
			}
			ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(assetsList);
			lstdetail.setModel(list); 
			/*
			ListModelList<RetirementRVDTO> listRV = new ListModelList<RetirementRVDTO>(retirementDTO.getRetirementRV());
			lstRV.setModel(listRV); 
			*/
			
			
			listRetirementQuotationDTO = new ArrayList<RetirementQuotationDTO>();
			listRetirementQuotationDTO.addAll(retirementDTO.getRetirementQuotation());
			
			listRetirementBastDTO = new ArrayList<RetirementBastDTO>();
			listRetirementBastDTO.addAll(retirementDTO.getRetirementBast());
			
			txtDocNo.setValue(retirementDTO.getRequestNo());
			//txtNotes.setValue(retirementDTO.getNotes());
			if(retirementDTO.getRetirementTypeCode().equals(RetirementType.GRANT.getCode())){
				txtNotes2.setValue(retirementDTO.getNotes());
			}else{
				txtNotes.setValue(retirementDTO.getNotes());
			}
			txtNotes2.setDisabled(true);
			txtNotes.setDisabled(true);
			
			txtBastDocNo.setValue(retirementDTO.getBastDocNo());
			
			dtRequestDate.setValue(retirementDTO.getRequestDate());
			dtRequestDate.setDisabled(true);
			txtPenerima.setText(retirementDTO.getRecipient());
			txtPenerima.setDisabled(true);
			for(int i=0;i<cbRetirementType.getModel().getSize();i++){
				KeyValue val = (KeyValue)cbRetirementType.getModel().getElementAt(i);
				if(val.getValue().toString().equals(retirementDTO.getRetirementTypeCode().toString())){
					cbRetirementType.setSelectedIndex(i);
				}
			}
			cbRetirementType.setStyle("background-color:#f2f2f2;");
			cbRetirementType.setDisabled(true);
		
			
			if(retirementDTO.getRetirementTypeCode().equals(RetirementType.SELL.getCode())){
				gbQuotationsDetail.setVisible(true);
				gbRVDetail.setVisible(false);
				gbBast.setVisible(false);
				lblPenerima.setVisible(false);
				txtPenerima.setVisible(false);
				lblNotes.setVisible(true);
				txtNotes.setVisible(true);
				lblNotes2.setVisible(false);
				txtNotes2.setVisible(false);
			}else if(retirementDTO.getRetirementTypeCode().equals(RetirementType.GRANT.getCode())){
				gbBast.setVisible(false);
				gbQuotationsDetail.setVisible(false);
				gbRVDetail.setVisible(false);
				lblPenerima.setVisible(true);
				txtPenerima.setVisible(true);
				lblNotes.setVisible(false);
				txtNotes.setVisible(false);
				lblNotes2.setVisible(true);
				txtNotes2.setVisible(true);
			}else{
				gbBast.setVisible(false);
				gbQuotationsDetail.setVisible(false);
				gbRVDetail.setVisible(false);
				lblPenerima.setVisible(false);
				txtPenerima.setVisible(false);
				lblNotes.setVisible(true);
				txtNotes.setVisible(true);
				lblNotes2.setVisible(false);
				txtNotes2.setVisible(false);
			}
		}
		
		if(action.equals("detail")){
			btnAddRV.setVisible(false);
			btnDeleteRV.setVisible(false);
			btnSaveRV.setVisible(false);
			btnSubmitBAST.setVisible(false);
			btnSubmit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnAddRow.setVisible(false);
			btnDelete.setVisible(false);
			btnAddRowBast.setVisible(false);
			btnDeleteBast.setVisible(false);
			btnAddQuotation.setVisible(false);
			btnDeleteQuotation.setVisible(false);
			if(retirementDTO.getBastStatusCode()!=null){
				gbBast.setVisible(true);
				lstRetirementBast.setVisible(false);
				
			}else{
				gbBast.setVisible(false);
			}
			
			if(retirementDTO.getRvNumber()!=null){
				gbRVDetail.setVisible(true);
			}else{
				gbRVDetail.setVisible(false);
			}
			
			listModelBast = new ListModelList<RetirementBast>();
			RetirementBast retirementBast = new RetirementBast();
			retirementBast.setId(retirementDTO.getId());
			listDetailBast = retirementNonEBSService.getDetailBastByPrimaryKey(retirementBast.getId());
			listModelBast.clear();
			listModelBast.addAll(listDetailBast);
			lbxBASTDetail.setModel(listModelBast);
			
			
		}
		else if(action.equals("addNew")){
			btnSaveRV.setVisible(false);
			btnSubmit.setVisible(true);
			btnSubmitBAST.setVisible(false);
			btnAddRow.setVisible(true);
			btnDelete.setVisible(true);
			btnBack.setVisible(false);
			btnCancel.setVisible(true);
		}else if(action.equals("BAST")){
			btnSaveRV.setVisible(false);
			gbBast.setVisible(true);
			lbxBASTDetail.setVisible(false);
			btnSubmitBAST.setVisible(true);
			btnSubmit.setVisible(false);
			btnBack.setVisible(false);
			btnCancel.setVisible(true);
			btnAddRow.setVisible(false);
			btnDelete.setVisible(false);
			if(retirementDTO.getRvNumber()!=null){
				gbRVDetail.setVisible(true);
			}else{
				gbRVDetail.setVisible(false);
			}
			btnAddRow.setVisible(false);
			btnDelete.setVisible(false);
			btnAddQuotation.setVisible(false);
			btnDeleteQuotation.setVisible(false);
			btnAddRV.setVisible(false);
			btnDeleteRV.setVisible(false);
		}
		else if(action.equals("RV")){
			gbRVDetail.setVisible(true);
			btnSaveRV.setVisible(true);
			btnSubmit.setVisible(false);
			btnSubmitBAST.setVisible(false);
			btnBack.setVisible(false);
			btnCancel.setVisible(true);
			btnAddRow.setVisible(false);
			btnDelete.setVisible(false);
			btnAddQuotation.setVisible(false);
			btnDeleteQuotation.setVisible(false);
			
		}
		
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue key = new KeyValue();
		key.setKey("%");
		key.setValue("%");
		key.setDescription("--Pilih--");
		data.add(key);
		data.addAll(retirementNonEBSService.getLookupKeyValues(RetirementType.CODE.getCode(), null));
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbRetirementType.setModel(model);
		cbRetirementType.renderAll();
		cbRetirementType.setSelectedIndex(0);
	}
	
	@Listen ("onClick = button#btnAddRow")
	public void add() {
		Branch branch = (Branch)retirementNonEBSService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		RetirementDTO retirementDto = new RetirementDTO();
		retirementDto.setBranchId(branch.getBranchId());

		Map<String, Object> param = new HashMap<String, Object>();
		//KeyValue key = (KeyValue) arg.get("Branch");
		String action = (String) arg.get("action");
		param.put("source", lstdetail);
		param.put("data", assetsList);
		param.put("Branch", retirementDto);
		param.put("action", action);
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs_popup.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		ListModel listData = (ListModel) lstdetail.getModel();
		ListModelList data = (ListModelList) listData;
		if(data!=null && data.getSelection().size()>0){
		final Set<RetirementDetailDTO> selection = data.getSelection();
		
		Messagebox.show("Are you sure delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
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
							for (Iterator<RetirementDetailDTO> iterator = selection.iterator(); selection.iterator().hasNext();) {
								RetirementDetailDTO asset = iterator.next();
								assetsList.remove(asset);
								assets.remove(asset.getAssetId());
								i++;
								if (i == selection.size()) {
									break;
								}
							}
							ListModelList<RetirementDetailDTO> list = new ListModelList<RetirementDetailDTO>(
									assetsList);
							lstdetail.setModel(list);
							/*if(list.size() == 0){
								assetType = null;
							}*/
						}
					}
				});
		}

	}
	
	/*
	@Listen ("onClick = button#btnAddRV")
	public void addRV() {
		Branch branch = (Branch)retirementNonEBSService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		RetirementDTO retirementDto = new RetirementDTO();
		retirementDto.setBranchId(branch.getBranchId());

		Map<String, Object> param = new HashMap<String, Object>();
		//KeyValue key = (KeyValue) arg.get("Branch");
		String action = (String) arg.get("action");
		param.put("source", lstRV);
		param.put("data", rvList);
		param.put("Branch", retirementDto);
		param.put("action", action);
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_retirement_rv_popup.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	@Listen("onClick=button#btnDeleteRV")
	public void deleteRV() {
		ListModel listData = (ListModel) lstRV.getModel();
		ListModelList data = (ListModelList) listData;
		if(data!=null && data.getSelection().size()>0){
		final Set<RetirementRVDTO> selection = data.getSelection();
		
		Messagebox.show("Are you sure delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							int i = 0;
							for (Iterator<RetirementRVDTO> iterator = selection.iterator(); selection.iterator().hasNext();) {
								RetirementRVDTO asset = iterator.next();
								rvList.remove(asset);
								rvs.remove(asset.getRvNo());
								i++;
								if (i == selection.size()) {
									break;
								}
							}
							ListModelList<RetirementRVDTO> list = new ListModelList<RetirementRVDTO>(
									rvList);
							lstRV.setModel(list);
							
						}
					}
				});
		}

	}
	*/
	
	private Boolean validateBAST(){
		Boolean flag = false;
		
		if(lstRetirementBast == null || lstRetirementBast.getModel() == null || lstRetirementBast.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(lblErrorBAST, "Document Image must be filled");
			flag = true;
		}
		else if(lstRetirementBast != null || lstRetirementBast.getModel() != null || lstRetirementBast.getModel().getSize() > 0){
			int x=0;
			boolean f = false;
			for(int i=0;i<lstRetirementBast.getModel().getSize();i++){
				x=i+1;
				RetirementBastDTO retirementBastDTO = (RetirementBastDTO)lstRetirementBast.getModel().getElementAt(i);
				if(retirementBastDTO.getBastDocPath() == null || retirementBastDTO.getBastDocPath().isEmpty()){
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
	
	private void clearErrorMessageBAST() {
		ErrorMessageUtil.clearErrorMessage(lblErrorBAST);
	
	}
	@Listen("onClick=button#btnSubmitBAST")
	public void submitBAST() {
		clearErrorMessageBAST();
		if(!validateBAST()){
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
		retirementDTO.setRetirementRV(null);
		
		if (securityServiceImpl.getSecurityProfile().getBranchId().equals(-1L)) {
			retirementDTO.setArBranchType("AR_BRANCH_TYPE_HO");
		}else{
			retirementDTO.setArBranchType("AR_BRANCH_TYPE_NON_HO");
		}
		
		Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							try {
							    retirementNonEBSBastService.submitBAST(retirementDTO, securityServiceImpl.getSecurityProfile().getPersonUUID(),lstRetirementBast.getValue());
							
							    Map<String, Object> param = new HashMap<String, Object>();
								param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
								Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
										Messagebox.INFORMATION);
								Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs.zul", getSelf().getParent(),
										param);
								getSelf().detach();	
							} catch (FifException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		
						
						}
					}
				});
		}
		
	}
	/*
	@Listen("onClick=button#btnSaveRV")
	public void saveRV() {
		clearErrorMessage();
		if(!validateRV()){
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new SerializableEventListener<Event>() {

						
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if (status == 16) {
						retirementDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						retirementDTO.setNotes(txtNotes.getValue());
						retirementDTO.setRequestNo(txtDocNo.getValue());
						retirementDTO.setRequestDate(dtRequestDate.getValue());
						try {
						    retirementService.saveRV(retirementDTO,lstRV.getValue());
						 
						    Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
									Messagebox.INFORMATION);
							Executions.createComponents("~./hcms/ssoa/asset_retirement.zul", getSelf().getParent(),
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
	*/
	
	@Listen("onClick=button#btnSubmit")
	public void submit() {
		clearErrorMessage();
		if(!validate()){
			if(retirementDTO == null){
				retirementDTO = new RetirementDTO();
			}
			Messagebox.show(Labels.getLabel("sam.submitMessage"), "Information", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new SerializableEventListener<Event>() {

						
				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					int status = (int) event.getData();
					if (status == 16) {
					Branch branch = (Branch)retirementNonEBSService.getBranchById(securityServiceImpl.getSecurityProfile().getBranchId(), securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					RetirementDTO retirementDTO = new RetirementDTO();
					
					retirementDTO.setBranchId(branch.getBranchId());
					System.out.println(branch.getBranchId());
					
					if (securityServiceImpl.getSecurityProfile().getBranchId().equals(-1L)) {
						retirementDTO.setArBranchType("AR_BRANCH_TYPE_HO");
					}else{
						retirementDTO.setArBranchType("AR_BRANCH_TYPE_NON_HO");
					}
					
					retirementDTO.setBranchName(branch.getBranchName());
					retirementDTO.setBranchId(branch.getBranchId());
					retirementDTO.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
					
					retirementDTO.setRequestNo(txtDocNo.getValue());
					retirementDTO.setRequestDate(dtRequestDate.getValue());
					KeyValue keyType = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
					retirementDTO.setRetirementTypeId(((BigDecimal)keyType.getKey()).longValue());
					retirementDTO.setRetirementTypeCode((String)keyType.getValue());
					retirementDTO.setRetirementType((String)keyType.getValue());
					if(keyType.getValue().equals(RetirementType.GRANT.getCode())){
						retirementDTO.setNotes(txtNotes2.getValue());
					}else{
						retirementDTO.setNotes(txtNotes.getValue());
					}
					retirementDTO.setRecipient(txtPenerima.getText());
					retirementDTO.setCreatedBy(securityServiceImpl.getSecurityProfile().getUserId());
					retirementDTO.setCreationDate(new Date());
					retirementDTO.setLastUpdateBy(securityServiceImpl.getSecurityProfile().getUserId());
					retirementDTO.setLastUpdateDate(new Date());
					
					boolean flag = false;
					String assetType = null;
					final List<RetirementDetailDTO> listDetail = new ArrayList<RetirementDetailDTO>();
					for(int i=0;i<assetsList.size();i++){
						RetirementDetailDTO asset =(RetirementDetailDTO)assetsList.get(i);
						if(i==0){assetType = asset.getAssetType();}
						RetirementDetailDTO retirementDetailDTO = new RetirementDetailDTO();
						retirementDetailDTO.setAssetId(asset.getAssetId());
						retirementDetailDTO.setRetirementImg(asset.getRetirementImg());
						//retirementDetailDTO.setRetirementId(retirementDTO.getId());
						retirementDetailDTO.setStockOpnameDtlId(asset.getStockOpnameDtlId());
						listDetail.add(retirementDetailDTO);
					}
					retirementDTO.setAssetType(assetType);
					
					
					UUID personUUID = null;
					if(securityServiceImpl.getSecurityProfile().getBranchId().intValue() == -1 && retirementDTO.getBranchId().intValue()!= -1){
						//personUUID = UUID.fromString(stockOpnameService.getOnePersonByBranchId(selected.getBranchId()));
						StockOpnameDTO sod = ssoaCommonService.getOnePersonByBranchId(retirementDTO.getBranchId(),securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
						personUUID = sod.getPersonUUID();
						//personUUID = rd.getPersonUUID();
					
					}else{
						personUUID = securityServiceImpl.getSecurityProfile().getPersonUUID();
					}
						try {
						    retirementNonEBSService.save(retirementDTO, personUUID,listDetail,lstRetirementQuotation.getValue());
						 
						    Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Messagebox.show(Labels.getLabel("sam.dataSave"), "Information", Messagebox.YES,
									Messagebox.INFORMATION);
							Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs.zul", getSelf().getParent(),
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
	
	@Listen ("onClick = button#btnBack")
	public void back() {
		Messagebox.show("Are you sure want to back ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs.zul", getSelf().getParent(), param);
							getSelf().detach();
						}
					}
				});
					
	}
	
	@Listen ("onClick = button#btnCancel")
	public void cancel() {
		Messagebox.show("Are you sure want to cancel ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if (status == 16) {
							Map<String, Object> param = new HashMap<String, Object>();
							param.put(GlobalVariable.FUNCTION_PERMISSION_KEY, functionPermission);
							Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs.zul", getSelf().getParent(), param);
							getSelf().detach();
						}
					}
				});
					
	}
	
	@Listen("onViewImage= #winAssetRetirementAdd")
	public void onViewImage(ForwardEvent event){
		String action = (String) arg.get("action");
		RetirementDetailDTO retirementDetailDTO = (RetirementDetailDTO) event.getData();
		retirementDTO = (RetirementDTO) arg.get("retirementDTO");
		
		  if(action.equals("detail")){
			Long id= retirementDTO.getId();
			Long assetId = retirementDetailDTO.getAssetId();
			
			RetirementDetailDTO retirementDetail = retirementNonEBSService.getDetailIdForImages(id, assetId);
			System.out.println("retirementDetail.getId()" + retirementDetail.getId());
			System.out.println("retirementDetailDTO.getAssetId()" + retirementDetailDTO.getAssetId());
			System.out.println("retirementDTO.getId()" + retirementDTO.getId());
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("retirementDetailDTO", retirementDetail.getId());
			Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs_detail_image.zul", getSelf().getParent(), param);
			win.doModal();
		  }
		  
	  else{
		  Map<String, Object> param = new HashMap<String, Object>();
		  param.put("retirementDetailDTO", retirementDetailDTO);
		  param.put("source", lstRetirementImg);
		  Window win = (Window) Executions.createComponents("~./hcms/ssoa/asset_retirement_non_ebs_add_image.zul", getSelf().getParent(), param);
		  win.doModal();
	  	}

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
				btnImage.setUpload("true,maxsize="+maxSize);
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
						
						boolean flag=false;
						if(fileName!=null && (fileName.toLowerCase().endsWith("jpg") || fileName.toLowerCase().endsWith("jpeg") || fileName.toLowerCase().endsWith("gif") || fileName.toLowerCase().endsWith("png") || fileName.toLowerCase().endsWith("bmp"))  ){
							flag = true;
						}
						if(!flag){
							Messagebox.show("File Upload must be image" , "Information", Messagebox.YES,
									Messagebox.ERROR);
						}else{
						
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
	
	private Boolean validateRV(){
		Boolean flag = false;
		if(lstRV!=null && lstRV.getModel()!=null && lstRV.getModel().getSize()>0){
			for(int i=0;i<lstRV.getModel().getSize();i++){
				RetirementRVDTO retirementRVDTO = (RetirementRVDTO)lstRV.getModel().getElementAt(i);
				if(retirementRVDTO.getRvNo() == null || retirementRVDTO.getRvNo().isEmpty()){
					flag = true;
					ErrorMessageUtil.setErrorMessage(errMsgRVDtl, "RV Number be filled, ");
				}
			}
		}else{
			ErrorMessageUtil.setErrorMessage(errMsgRVDtl, "RV Number must be filled, ");
			flag = true;
		}
		return flag;
	}
	private Boolean validate(){
		Boolean flag = false;
		if(cbRetirementType.getSelectedIndex() == 0) {
			ErrorMessageUtil.setErrorMessage(cbRetirementType, "Retirement Type must be selected");
			flag = true;
		}
		if(dtRequestDate.getValue()== null) {
			ErrorMessageUtil.setErrorMessage(dtRequestDate, "Request Date must be filled");
			flag = true;
		}

		/*if(txtNotes.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(txtNotes, "Remark must be filled");
			flag = true;
		}*/
		KeyValue keyType = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
		String retirementType = keyType.getValue().toString();
		if(retirementType.equals(RetirementType.GRANT.getCode())){
			if(txtPenerima.getValue()== null || txtPenerima.getValue().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(txtPenerima, "Penerima must be filled");
				flag = true;
			}
		}
		if(lstdetail == null || lstdetail.getModel() == null || lstdetail.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(errMsgDtl, "Asset must be filled, ");
			flag = true;
		}else{
			for(int i=0;i<lstdetail.getModel().getSize();i++){
				RetirementDetailDTO retirementDetailDTO =(RetirementDetailDTO)lstdetail.getModel().getElementAt(i);
				if(retirementDetailDTO.getRetirementImg() == null || retirementDetailDTO.getRetirementImg().size()==0){
					ErrorMessageUtil.setErrorMessage(errMsgDtl, "Image for Asset ID "+retirementDetailDTO.getAssetId()+" must be filled, ");
					flag = true;
				}
			}
		}
		if(lstRetirementQuotation!=null && lstRetirementQuotation.getModel()!=null && lstRetirementQuotation.getModel().getSize()>0){
			boolean f = false;
			boolean g = false;
			boolean h = false;
			boolean j = false;
			boolean k = false;
			//KeyValue keyType = (KeyValue)cbRetirementType.getModel().getElementAt(cbRetirementType.getSelectedIndex());
			retirementType = keyType.getValue().toString();
			if(retirementType.equals(RetirementType.SELL.getCode())){
				for(int i=0;i<lstRetirementQuotation.getModel().getSize();i++){
					RetirementQuotationDTO retirementQuotationDTO = (RetirementQuotationDTO)lstRetirementQuotation.getModel().getElementAt(i);
					if(retirementQuotationDTO.getVendorName() == null || retirementQuotationDTO.getVendorName().isEmpty()){
						f = true;
					}
					if(retirementQuotationDTO.getQuotationPrice() == null || retirementQuotationDTO.getQuotationPrice().isEmpty()){
						g = true;
					}
					if(retirementQuotationDTO.getDocFileName() == null || retirementQuotationDTO.getDocFileName().isEmpty()){
						k = true;
					}
					if(retirementQuotationDTO.getWinnerFlag()){
						h = true;
					}
					if(retirementQuotationDTO.getQuotationPrice() != null){
						try{
							double price = Double.parseDouble(retirementQuotationDTO.getQuotationPrice());
						}catch(Exception e){
							j = true;
						}
						
					}
				}
				if(lstRetirementQuotation.getModel().getSize()==0){
					ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Quotations must be filled, ");
					flag = true;
				}
				if(f){
					ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Vendor must be filled, ");
					flag = true;
				}
				if(g){
					ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Price must be filled, ");
					flag = true;
				}
				if(k){
					ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Document must be filled, ");
					flag = true;
				}
				if(j){
					ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Price must be number, ");
					flag = true;
				}
				if(!h){
					ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Please select a Winner, ");
					flag = true;
				}
			}
		}else{
			ErrorMessageUtil.setErrorMessage(errMsgQuotationDtl, "Quotations must be filled, ");
			flag = true;
		}

		return flag;
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(cbRetirementType);
		ErrorMessageUtil.clearErrorMessage(dtRequestDate);
		ErrorMessageUtil.clearErrorMessage(txtNotes);
		ErrorMessageUtil.clearErrorMessage(txtPenerima);
		ErrorMessageUtil.clearErrorMessage(errMsgQuotationDtl);
		ErrorMessageUtil.clearErrorMessage(errMsgDtl);
		ErrorMessageUtil.clearErrorMessage(errMsgRVDtl);

	}
	
	@Listen("onDetailBast= #winAssetRetirementAdd")
	public void onDetail(ForwardEvent event){
	 	RetirementBast retirementBast = (RetirementBast) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("images", retirementBast.getBastDocPath());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", getSelf().getParent(), param);
		win.doModal();
	}
	
	
}
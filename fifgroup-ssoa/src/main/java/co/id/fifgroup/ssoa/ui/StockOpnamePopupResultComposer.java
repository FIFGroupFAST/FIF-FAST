package co.id.fifgroup.ssoa.ui;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.ssoa.constants.SOResult;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.StockOpnameImg;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;

@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnamePopupResultComposer extends SelectorComposer<Window> {
	
	@Wire
	private Textbox txtAssetNo;
	@Wire
	private Textbox txtItemCategory;
	@Wire
	private Textbox txtDescription;
	@Wire
	private Textbox txtOwnType;
	@Wire
	private Textbox txtBranch;
	@Wire
	private Textbox txtPosition;
	@Wire
	private Textbox txtDateOfService;
	@Wire
	private Textbox txtLocation;
	@Wire
	private Textbox txtSerialNo;
	@Wire
	private Listbox cbResult;
	@Wire
	private Listbox cbRecommendation;
	@Wire
	private Label lblErrStockOpnameInfo;
	@Wire
	private TabularEntry<StockOpnameImgDTO> lstStockOpnameInfo;
	@Wire
	private Listbox stockOpnameImgListbox;
	@Wire
	private Label lblResult;
	@Wire
	private Label lblRecommendation;
	@Wire
	private Label lblPosition;
	
	
	private List<StockOpnameImgDTO> stockOpnameImgList;
	@Wire
	private Window winResultPopup;
	private StockOpnameDetailDTO selected;
	@WireVariable("arg")
	private Map<String, Object> arg;
	private Listbox source;

	private static final long serialVersionUID = 1L;
	
	
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	private static String tmpFile ="E:\\Temp\\";
	private static String maxSize ="1000000";
	private static String maxOriginalCostForLVA = "5000000"; 
	
	

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
			source = arg.get("source") == null ? null : (Listbox) arg.get("source");
			setComboBoxList();
			buildData();
			loadData();
			action();
			List<GlobalSettingDTO> dataImgPath = stockOpnameService.getGlobalsettingByCode(SSOAConstants.imagePathTmp);
			if(dataImgPath!=null){
				tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
			}
			List<GlobalSettingDTO> dataMaxSizeUpload = stockOpnameService.getGlobalsettingByCode(SSOAConstants.maxSizeUploadFileStockOpname);
			if(dataMaxSizeUpload!=null){
				maxSize = dataMaxSizeUpload.get(0).getGlobalSetting().getValue().toString();
			}
			List<GlobalSettingDTO> dataMaxOriginalCostForLVA = stockOpnameService.getGlobalsettingByCode(SSOAConstants.maxOriginalCostForLVA);
			if(dataMaxOriginalCostForLVA!=null){
				maxOriginalCostForLVA = dataMaxOriginalCostForLVA.get(0).getGlobalSetting().getValue();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void action(){
		cbResult.addEventListener(Events.ON_SELECT, new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				
				KeyValue keyType = (KeyValue)cbResult.getModel().getElementAt(cbResult.getSelectedIndex());
					if(keyType.getValue().equals(SOResult.GOOD_USED.getCode())){
						cbRecommendation.setDisabled(true);
						cbRecommendation.setSelectedIndex(0);
					}else{
						cbRecommendation.setDisabled(false);
					}
				
			}
		});
	}
	
	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue keyValue1 = new KeyValue(null,null,"--Select--");
		data.add(keyValue1);
		//data.addAll(stockOpnameService.getSOResultList());
		List<KeyValue> dataFromDb = new ArrayList<KeyValue>();
		for(int i=0;i<stockOpnameService.getSOResultList().size();i++){
			KeyValue keyVal = (KeyValue)stockOpnameService.getSOResultList().get(i);
			if(keyVal.getValue()!=null && !keyVal.getValue().equals(SOResult.NOT_RECORDED.getCode())){
				dataFromDb.add(keyVal);
			}
		}
		data.addAll(dataFromDb);
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);		
		cbResult.setModel(model);
		cbResult.renderAll();
		cbResult.setSelectedIndex(0);
		
		List<KeyValue> data2 = new ArrayList<KeyValue>();
		KeyValue keyValue = new KeyValue(null,null,"--Select--");
		data2.add(keyValue);
		data2.addAll(stockOpnameService.getSORecommendList());
		ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
		cbRecommendation.setModel(model2);
		cbRecommendation.renderAll();
		cbRecommendation.setSelectedIndex(0);
	}
	
	private void buildData() {
		lstStockOpnameInfo.setModel(getDataModel());
		lstStockOpnameInfo.setItemRenderer(getListitemRenderer());
		lstStockOpnameInfo.setValidationRule(getTabularValidationRule());
		lstStockOpnameInfo.renderAll();
	}
	
	private ListModelList<StockOpnameImgDTO> getDataModel() {
		if(stockOpnameImgList == null || stockOpnameImgList.size() < 1) {
			stockOpnameImgList = new ArrayList<StockOpnameImgDTO>();
			stockOpnameImgList.add(new StockOpnameImgDTO());
		}
		ListModelList<StockOpnameImgDTO> model = new ListModelList<StockOpnameImgDTO>(stockOpnameImgList);
		model.setMultiple(true);
		return model;
	}
	
	private TabularValidationRule<StockOpnameImgDTO> getTabularValidationRule() {
		return new TabularValidationRule<StockOpnameImgDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(StockOpnameImgDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	private SerializableListItemRenderer<StockOpnameImg> getListitemRenderer() {
		return new SerializableListItemRenderer<StockOpnameImg>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final StockOpnameImg data, final int index)
					throws Exception {
				
				StockOpnameImg stockOpnameImg = data;
				
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
						ListModel model = lstStockOpnameInfo.getModel();
						StockOpnameImg data= (StockOpnameImg)model.getElementAt(index);
						data.setImageFileName(fileName);
						data.setImageFile(event.getMedia().getByteData());
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setImageFilePath(tmpFile+uuid+"_"+fileName);
						
						
						//System.out.println("msk upload"+data.getImageFileName());
						rebuildTabularEntryOnChangeTaskCollection();
						
					}

					
				});
				createImageData(item,data,btnImage,imageName);
				
						
			}
		};
	}
	
	
	private void createImageData(Listitem item, final StockOpnameImg data, Button btnImage, A imageName) {
		Listcell cell = new Listcell();
		btnImage.setLabel("Browse");
		btnImage.setParent(cell);
		Space space = new Space();
		
		space.setParent(cell);
		imageName.setLabel(data.getImageFileName());
		imageName.addEventListener("onClick", new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event arg0) throws Exception {
				Map<String, Object> param = new HashMap<String, Object>();
				//param.put("images", data.getImageFile());
				param.put("images", data.getImageFilePath());
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", getSelf().getParent(), param);
				win.doModal();
			}
		});
		imageName.setParent(cell);
		cell.setParent(item);
	}
	
	private void loadData() {
		selected = (StockOpnameDetailDTO) arg.get("stockOpnameDetail");
		if(selected != null) {
			Assets assets= (Assets)stockOpnameService.getAssetsById(selected.getAssetId());
			txtAssetNo.setValue(assets.getAssetNumber());
			//txtOwnType.setValue(selected.getOwnType());
			txtItemCategory.setValue(selected.getCategoryName());
			txtDescription.setValue(selected.getDescription());
			txtBranch.setValue(selected.getBranchName());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			txtDateOfService.setValue(selected.getDateOfService()!=null?sdf.format(selected.getDateOfService()):null);
			txtLocation.setValue(selected.getLocationName());
			txtSerialNo.setValue(selected.getSerialNumber());
			txtPosition.setValue(selected.getPosition());
			if(assets.getAssetTypeCode()!=null){
				txtOwnType.setValue(assets.getAssetType());
			}else{
				if(assets.getOriginalCost()!=null && Double.parseDouble(assets.getOriginalCost().toString())< Double.parseDouble(maxOriginalCostForLVA)){
					txtOwnType.setValue(SSOAConstants.assetTypeLVA);
				}else{
					txtOwnType.setValue(SSOAConstants.assetTypeAST);
				}
					
			}
			//cbResult.setValue(selected.getOpnameResultId());
			//cbRecommendation.setValue(selected.getOpnameSugest());
			if(selected.getOpnameResultCode()!=null){
				for(int i=0;i<cbResult.getModel().getSize();i++){
					KeyValue val = (KeyValue)cbResult.getModel().getElementAt(i);
					if(val!=null && val.getValue()!=null && val.getValue().toString().equals(selected.getOpnameResultCode().toString())){
						cbResult.setSelectedIndex(i);
					}
				}
			}
			
			if(selected.getOpnameSugestCode()!=null){
				for(int i=0;i<cbRecommendation.getModel().getSize();i++){
					KeyValue val = (KeyValue)cbRecommendation.getModel().getElementAt(i);
					if(val.getValue()!=null && val.getValue().toString().equals(selected.getOpnameSugestCode().toString())){
						cbRecommendation.setSelectedIndex(i);
					}
				}
			}
			
			//List<StockOpnameImg> listStockOpnameImg = (List<StockOpnameImg>)stockOpnameService.getStockOpnameImgByStockOpnameDtlId(selected.getId());
			List<StockOpnameImgDTO> listStockOpnameImg = selected.getStockOpnameImg();
			if(listStockOpnameImg!=null && listStockOpnameImg.size()>0){
			ListModelList<StockOpnameImg> model = new ListModelList<StockOpnameImg>(listStockOpnameImg);
			model.setMultiple(true);
			lstStockOpnameInfo.setModel(model);
			//ListModel model2 = lstStockOpnameInfo.getModel();
			
			}else{
				listStockOpnameImg = (List<StockOpnameImgDTO>)stockOpnameService.getStockOpnameImgByStockOpnameDtlId(selected.getId());
				ListModelList<StockOpnameImg> model = new ListModelList<StockOpnameImg>(listStockOpnameImg);
				model.setMultiple(true);
				lstStockOpnameInfo.setModel(model);
			}
			
			KeyValue keyType = (KeyValue)cbResult.getModel().getElementAt(cbResult.getSelectedIndex());
			if(keyType!=null && keyType.getValue()!=null &&  keyType.getValue().equals(SOResult.GOOD_USED.getCode())){
				cbRecommendation.setDisabled(true);
				cbRecommendation.setSelectedIndex(0);
			}else{
				cbRecommendation.setDisabled(false);
			}
		}
	}
	
	
	
	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		lstStockOpnameInfo.setDataType(StockOpnameImgDTO.class);
		lstStockOpnameInfo.addRow();
	}
	
	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		Messagebox.show("Are you sure delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new SerializableEventListener<Event>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						int status = (int) event.getData();
						if(status == 16) {
						ListModel listData = (ListModel) lstStockOpnameInfo.getModel();
						ListModelList data = (ListModelList) listData;
						Set<StockOpnameImgDTO> selection = data.getSelection();
						int i = 0;
						for (Iterator iter = selection.iterator(); selection.iterator().hasNext();) {
							StockOpnameImgDTO stockOpnameImgDTO = selection.iterator().next();
							if (stockOpnameImgDTO.getImageFilePath()!=null &&!stockOpnameImgDTO.getImageFilePath().isEmpty()) {
								File file = new File(stockOpnameImgDTO.getImageFilePath());
								file.delete();
							}
							i++;
							if (i == selection.size()) {
								break;
							}
						}
						lstStockOpnameInfo.deleteRow();

						rebuildTabularEntryOnChangeTaskCollection();
						}
					}
				});
	}
	
	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<StockOpnameImg> model = new ListModelList<StockOpnameImg>(lstStockOpnameInfo.getValue());
		model.setMultiple(true);
		lstStockOpnameInfo.setModel(model);
	}
	
	public Boolean validate(){
		Boolean flag = false;
		
		 KeyValue keyType = (KeyValue)cbResult.getModel().getElementAt(cbResult.getSelectedIndex());
		 if(keyType!=null && keyType.getValue()!=null && !keyType.getValue().equals(SOResult.GOOD_USED.getCode()) && cbRecommendation.getSelectedIndex() == 0){
			 ErrorMessageUtil.setErrorMessage(lblRecommendation, "Recommendation must be selected");
			 flag = true;
		 }

		if(cbResult.getSelectedIndex() == 0){
			ErrorMessageUtil.setErrorMessage(lblResult, "Result must be selected");
			flag = true;
		}
		if(txtPosition.getValue() == null || txtPosition.getValue().isEmpty()){
			ErrorMessageUtil.setErrorMessage(lblPosition, "Position must be filled");
			flag = true;
		}
		
		if(lstStockOpnameInfo == null || lstStockOpnameInfo.getModel() == null || lstStockOpnameInfo.getModel().getSize() == 0){
			ErrorMessageUtil.setErrorMessage(lblErrStockOpnameInfo, "Document Image must be filled");
			flag = true;
		}
		else if(lstStockOpnameInfo != null || lstStockOpnameInfo.getModel() != null || lstStockOpnameInfo.getModel().getSize() > 0){
			int x=0;
			boolean f = false;
			for(int i=0;i<lstStockOpnameInfo.getModel().getSize();i++){
				x=i+1;
				StockOpnameImgDTO stockOpnameImgDTO = (StockOpnameImgDTO)lstStockOpnameInfo.getModel().getElementAt(i);
				if(stockOpnameImgDTO.getImageFilePath() == null || stockOpnameImgDTO.getImageFilePath().isEmpty()){
					f= true;
				}
				
			}
			if(f){
				ErrorMessageUtil.setErrorMessage(lblErrStockOpnameInfo, "Document Image must be filled");
				flag = true;
			}
			
		}	
		
		return flag;
	}

	@Listen("onClick = #btnSave")
	public void save(Event e) {
		//winResultPopup.detach();
		clearErrorMessage();
		if(!validate()){
		selected.setStockOpnameImg(lstStockOpnameInfo.getValue());
		KeyValue keyResult = (KeyValue)cbResult.getModel().getElementAt(cbResult.getSelectedIndex());
		selected.setOpnameResultId(((BigDecimal)keyResult.getKey()).longValue());
		selected.setOpnameResultCode((String)keyResult.getValue());
		selected.setOpnameResult((String)keyResult.getDescription());
		KeyValue keySugest = (KeyValue)cbRecommendation.getModel().getElementAt(cbRecommendation.getSelectedIndex());
		if(keySugest.getValue()!=null){
		selected.setOpnameSugestId(((BigDecimal)keySugest.getKey()).longValue());
		selected.setOpnameSugestCode((String)keySugest.getValue());
		selected.setOpnameSugest((String)keySugest.getDescription());
		}else{
			selected.setOpnameSugestId(null);
			selected.setOpnameSugestCode(null);
			selected.setOpnameSugest(null);	
		}
		selected.setAssetType(txtOwnType.getValue());
		selected.setPosition(txtPosition.getValue());
		selected.setStockOpnameImg(lstStockOpnameInfo.getValue());
		List<StockOpnameDetail> list  = new ArrayList<StockOpnameDetail>();
		list.add(selected);
		ListModelList<StockOpnameDetail> model = new ListModelList<StockOpnameDetail>(list);
		Events.postEvent(new Event(Events.ON_CLOSE,source,model));
		getSelf().detach();
		}
	}
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lblResult);
		ErrorMessageUtil.clearErrorMessage(lblRecommendation);
		ErrorMessageUtil.clearErrorMessage(lblPosition);
		ErrorMessageUtil.clearErrorMessage(lblErrStockOpnameInfo);
	}
	
	

	@Listen("onClick = #btnCancel")
	public void modalResult(Event e) {
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
							winResultPopup.detach();
						}
					}
				});
		
	}
	
	
}
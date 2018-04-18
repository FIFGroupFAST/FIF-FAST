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
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;

@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnamePopupResultImageComposer extends SelectorComposer<Window> {
	
	
	@Wire
	private Label lblErrStockOpnameInfo;
	@Wire
	private Listbox lstStockOpnameInfo;
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
			buildData();
			loadData();
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
	
	

	private void buildData() {
		lstStockOpnameInfo.setModel(getDataModel());
		lstStockOpnameInfo.setItemRenderer(getListitemRenderer());
		//lstStockOpnameInfo.setValidationRule(getTabularValidationRule());
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
				btnImage.setVisible(false);
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
						//rebuildTabularEntryOnChangeTaskCollection();
						
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
		}
	}
	
	/*
	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<StockOpnameImg> model = new ListModelList<StockOpnameImg>(lstStockOpnameInfo.getValue());
		model.setMultiple(true);
		lstStockOpnameInfo.setModel(model);
	}
	*/

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
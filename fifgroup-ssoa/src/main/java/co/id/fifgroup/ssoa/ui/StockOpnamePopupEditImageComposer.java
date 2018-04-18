package co.id.fifgroup.ssoa.ui;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.zkoss.util.media.Media;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
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
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonPopupBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.constants.AssetType;
import co.id.fifgroup.ssoa.constants.SOResult;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.AssetCategory;
import co.id.fifgroup.ssoa.domain.AssetCategoryExample;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.AssetsExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.workstructure.service.LocationSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnamePopupEditImageComposer extends SelectorComposer<Window> {

	@Wire
	private Label lblErrStockOpnameInfo;
	@Wire
	private Listbox lstStockOpnameInfo;
	@Wire
	private Window winEditPopup;

	private static final long serialVersionUID = 1L;
	@WireVariable(rewireOnActivate = true)
	private transient StockOpnameService stockOpnameService;
	@WireVariable(rewireOnActivate = true)
	private transient LocationSetupService locationSetupService;

	private List<StockOpnameUnregAssetImgDTO> stockOpnameImgList;
	@Wire
	private SSOACommonPopupBandbox bnbLocation;
	
	/*@Wire
	private SSOACommonPopupBandbox bnbDescription;*/
	@Wire
	
	private List<StockOpnameDTO> listLocation = new ArrayList<StockOpnameDTO>();
	private List<Assets> listAssets = new ArrayList<Assets>();
	private Listbox source;
	private StockOpnameUnregAssetsDTO selected;
	@WireVariable("arg")
	private Map<String, Object> arg;
	
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate=true)
	private transient LocationSetupService locationSetupServiceImpl;
	
	private static String tmpFile ="E:\\Temp\\";
	private static String maxSize ="1000000";
	private static String maxOriginalCostForLVA = "5000000"; 
	

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
			buildData();

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
		
			source = arg.get("source") == null ? null : (Listbox) arg.get("source");
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadData() {
		selected = (StockOpnameUnregAssetsDTO) arg.get("stockOpnameUnregAssets");
		if (selected != null) {
			selected.setAction("EDIT");
			//bnbAsset.setValue(selected.getAssetNo());
			
			Assets assets = (Assets) stockOpnameService.getAssetsById(selected.getAssetId());
			if(assets!=null && assets.getId()!=null){
				KeyValue key = new KeyValue();
				key.setKey(assets.getId());
				key.setValue(assets.getAssetNumber());
				key.setDescription(assets.getDescription());
				
			}else{
			}
		
			List<StockOpnameUnregAssetImgDTO> listStockOpnameUnregAssetImg = selected.getStockOpnameUnregAssetImg();
			if (listStockOpnameUnregAssetImg != null && listStockOpnameUnregAssetImg.size() > 0) {
				ListModelList<StockOpnameUnregAssetImgDTO> model = new ListModelList<StockOpnameUnregAssetImgDTO>(listStockOpnameUnregAssetImg);
				model.setMultiple(true);
				lstStockOpnameInfo.setModel(model);
				// ListModel model2 = lstStockOpnameInfo.getModel();

			} else {
				listStockOpnameUnregAssetImg = (List<StockOpnameUnregAssetImgDTO>) stockOpnameService
						.getStockOpnameUnregAssetImgBySOUnregAssetId(selected.getId());
				ListModelList<StockOpnameUnregAssetImgDTO> model = new ListModelList<StockOpnameUnregAssetImgDTO>(listStockOpnameUnregAssetImg);
				model.setMultiple(true);
				lstStockOpnameInfo.setModel(model);
			}
		}else{
			
		}
	}

	private void buildData() {
		lstStockOpnameInfo.setModel(getDataModel());
		lstStockOpnameInfo.setItemRenderer(getListitemRenderer());
		//lstStockOpnameInfo.setValidationRule(getTabularValidationRule());
		lstStockOpnameInfo.renderAll();
	}

	private ListModelList<StockOpnameUnregAssetImgDTO> getDataModel() {
		if (stockOpnameImgList == null || stockOpnameImgList.size() < 1) {
			stockOpnameImgList = new ArrayList<StockOpnameUnregAssetImgDTO>();
			stockOpnameImgList.add(new StockOpnameUnregAssetImgDTO());
		}
		ListModelList<StockOpnameUnregAssetImgDTO> model = new ListModelList<StockOpnameUnregAssetImgDTO>(stockOpnameImgList);
		model.setMultiple(true);
		return model;
	}

	private TabularValidationRule<StockOpnameUnregAssetImgDTO> getTabularValidationRule() {
		return new TabularValidationRule<StockOpnameUnregAssetImgDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(StockOpnameUnregAssetImgDTO data, List<String> errorRow) {

				return true;
			}
		};
	}

	private SerializableListItemRenderer<StockOpnameUnregAssetImgDTO> getListitemRenderer() {
		return new SerializableListItemRenderer<StockOpnameUnregAssetImgDTO>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final StockOpnameUnregAssetImgDTO data, final int index) throws Exception {

				StockOpnameUnregAssetImgDTO stockOpnameImg = data;

				new Listcell().setParent(item);
				A imageName = new A();
				// createImageName(item,data,imageName);
				Button btnImage = new Button();
				btnImage.setVisible(false);
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
						ListModel model = lstStockOpnameInfo.getModel();
						StockOpnameUnregAssetImgDTO data = (StockOpnameUnregAssetImgDTO) model.getElementAt(index);
						data.setImageFileName(fileName);
						data.setImageFile(event.getMedia().getByteData());
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setImageFilePath(tmpFile+uuid+"_"+fileName);
						//System.out.println("msk upload" + data.getImageFileName());
						//rebuildTabularEntryOnChangeTaskCollection();

					}

				});
				createImageData(item, data, btnImage, imageName);

			}
		};
	}

	private void createImageData(Listitem item, final StockOpnameUnregAssetImgDTO data, Button btnImage, A imageName) {
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
				Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul",
						getSelf().getParent(), param);
				win.doModal();
			}
		});
		imageName.setParent(cell);
		cell.setParent(item);
	}

	/*
		public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<StockOpnameUnregAssetImgDTO> model = new ListModelList<StockOpnameUnregAssetImgDTO>(lstStockOpnameInfo.getValue());
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
							winEditPopup.detach();
						}
					}
				});
		
	}
	
}
package co.id.fifgroup.ssoa.ui;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetImgDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameUnregAssetsDTO;
import co.id.fifgroup.ssoa.service.StockOpnameService;
import co.id.fifgroup.workstructure.service.LocationSetupService;

@VariableResolver(DelegatingVariableResolver.class)
public class StockOpnamePopupEditComposer extends SelectorComposer<Window> {

	@Wire
	private Label lblErrStockOpnameInfo;
	@Wire
	private TabularEntry<StockOpnameUnregAssetImgDTO> lstStockOpnameInfo;
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
	private CommonPopupBandbox bnbDescription;
	@Wire
	private SSOACommonPopupBandbox bnbAssetCategory;
	@Wire
	private SSOACommonPopupBandbox bnbAsset;
	private List<StockOpnameDTO> listLocation = new ArrayList<StockOpnameDTO>();
	private List<Assets> listAssets = new ArrayList<Assets>();
	private Listbox source;
	private StockOpnameUnregAssetsDTO selected;
	@WireVariable("arg")
	private Map<String, Object> arg;
	/*@Wire
	private Textbox txtBookTypeCode;*/
	
	@Wire
	private Textbox txtSerialNumber;
	@Wire 
	private Label lblAssetNo;
	@Wire
	private Textbox txtUmurAsset;
	@Wire
	private Textbox txtPosition;
	
	@Wire
	private Textbox txtGroupAsset;
	@Wire
	private Datebox dDateOfService;
	
	@Wire
	private Listbox cbRecommendation;
	@Wire
	private Listbox cbAssetType;
	@Wire
	private Label lblSerialNo;
	@Wire
	private Label lblAssetType;
	@Wire
	private Label lblBranch;
	@Wire
	private Label lblDateOfService;
	@Wire
	private Label lblLocation;
	@Wire
	private Label lblUmurAsset;
	@Wire
	private Label lblDescription;
	@Wire
	private Label lblAssetCategory;
	/*@Wire
	private CommonBranchBandbox bnbBranch;*/
	@Wire
	private SSOACommonPopupBandbox bnbBranch;
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
			setComboBoxList();
			buildData();
			loadBranch(bnbBranch);
			loadLocation(bnbLocation);
			buildDescriptionPopup();
			loadAssetCategory(bnbAssetCategory);
			//buildAssetCategoryPopup();
			//loadDescription(bnbDescription);
			
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
			loadAsset(bnbAsset);
			source = arg.get("source") == null ? null : (Listbox) arg.get("source");
			loadData();
			
			
			dDateOfService.addEventListener(Events.ON_CHANGE, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event arg0) throws Exception {
					Date date= new Date();
					Calendar currentCalendar = new GregorianCalendar();
					currentCalendar.setTime(date);
					Calendar lifeInMonthCalendar = new GregorianCalendar();
					lifeInMonthCalendar.setTime(dDateOfService.getValue());
					int diffYear = currentCalendar.get(Calendar.YEAR) - lifeInMonthCalendar.get(Calendar.YEAR);
					int diffMonth = diffYear*12 + currentCalendar.get(Calendar.MONTH) - lifeInMonthCalendar.get(Calendar.MONTH);
							
					System.out.println(diffMonth);
					txtUmurAsset.setValue(Long.toString(diffMonth));
					System.out.println("masuk2");
					
				}
			});
			
			
			
			bnbAsset.addEventListener(Events.ON_FOCUS, new SerializableEventListener<Event>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void onEvent(Event event) throws Exception {
					
					//System.out.println("msk onFocus"+bnbAsset.getValue());
					
					if(!bnbAsset.getValue().isEmpty()){
					KeyValue keyValue = (KeyValue)bnbAsset.getKeyValue();
					//System.out.println("msk onFocus 2 == "+keyValue.getKey());
					Assets data = (Assets)stockOpnameService.getAssetsById((Long)keyValue.getKey());
					if(data!=null){
						
						//txtAssetCategoery.setValue(data.getCategoryId()!=null?data.getCategoryId().toString():null);
						AssetCategory assetCategory = (AssetCategory) stockOpnameService.getAssetCategoryById(data.getCategoryId());
						if(assetCategory!=null && assetCategory.getCategoryId()!=null){
							KeyValue key = new KeyValue();
							key.setKey(assetCategory.getCategoryId());
							key.setValue(assetCategory.getCategoryName());
							key.setDescription(assetCategory.getCategoryId());
							bnbAssetCategory.setRawValue(key);
							
						}
						
						bnbAssetCategory.setDisabled(true);
						
						/*txtBookTypeCode.setValue(data.getBookTypeCode());
						txtBookTypeCode.setDisabled(true);*/
						cbAssetType.setDisabled(true);
						if(data.getAssetTypeCode()!=null){
							for(int i=0;i<cbAssetType.getModel().getSize();i++){
								KeyValue val = (KeyValue)cbAssetType.getModel().getElementAt(i);
								if(val.getValue()!=null && val.getValue().toString().equals(data.getAssetTypeCode().toString())){
									cbAssetType.setSelectedIndex(i);
								}
							}
						}else{
							if(data.getOriginalCost()!=null && Double.parseDouble(data.getOriginalCost().toString())< Double.parseDouble(maxOriginalCostForLVA)){
								for(int i=0;i<cbAssetType.getModel().getSize();i++){
									KeyValue val = (KeyValue)cbAssetType.getModel().getElementAt(i);
									if(val.getValue()!=null && val.getValue().toString().equals(AssetType.ASSET_TYPE_LVA.getCode())){
										cbAssetType.setSelectedIndex(i);
									}
								}
							}else{
								for(int i=0;i<cbAssetType.getModel().getSize();i++){
									KeyValue val = (KeyValue)cbAssetType.getModel().getElementAt(i);
									if(val.getValue()!=null && val.getValue()!=null && val.getValue().toString().equals(AssetType.ASSET_TYPE_AST.getCode())){
										cbAssetType.setSelectedIndex(i);
									}
								}
							}
						}
						
						dDateOfService.setValue(data.getDatePlacedInService());
						//dDateOfService.setDisabled(true);
						Branch branch = (Branch) stockOpnameService.getBranchById(data.getBranchId(),data.getCompanyId());
						if(branch!=null && branch.getBranchCode()!=null){
							KeyValue key = new KeyValue();
							key.setKey(branch.getBranchId());
							key.setValue(branch.getBranchName());
							key.setDescription(branch.getBranchCode());
							bnbBranch.setRawValue(key);
							
						}
						//bnbBranch.setRowValue(data.getBranchId());
						//bnbBranch.setDisabled(true);
						txtUmurAsset.setText(data.getLifeInMonths()!=null?data.getLifeInMonths().toString():null);
						txtUmurAsset.setDisabled(true);
						/*if(data.getLocationId()!=null){
						bnbLocation.setValue(data.getLocationId().toString());
						}*/
						SSOALocation location = (SSOALocation) stockOpnameService.getLocationById(data.getLocationId());
						if(location!=null && location.getLocationId()!=null){
							KeyValue key = new KeyValue();
							key.setKey(location.getLocationId());
							key.setValue(location.getLocationName());
							key.setDescription(location.getLocationCode());
							bnbLocation.setRawValue(key);
							
						}
						
						bnbDescription.setValue(data.getDescription());
						//bnbLocation.setDisabled(true);
						
						//txtDescription.setValue(data.getDescription());
						//txtDescription.setDisabled(true);
						//bnbDescription.setDisabled(true);
						txtSerialNumber.setValue(data.getSerialNumber());
						txtSerialNumber.setDisabled(true);
						
						
						bnbBranch.setDisabled(true);
						bnbDescription.setDisabled(true);
						bnbLocation.setDisabled(true);
						cbRecommendation.setDisabled(false);
					}
					}else{
						bnbBranch.setDisabled(false);
						bnbDescription.setDisabled(false);
						bnbLocation.setDisabled(false);
						txtSerialNumber.setDisabled(false);
						
						txtUmurAsset.setDisabled(true);
						dDateOfService.setDisabled(false);
						cbAssetType.setDisabled(false);
						//txtBookTypeCode.setDisabled(false);
						bnbAssetCategory.setDisabled(false);
						
						cbRecommendation.setDisabled(true);
						cbRecommendation.setSelectedIndex(0);
					}
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setComboBoxList(){
		List<KeyValue> data = new ArrayList<KeyValue>();
		KeyValue all = new KeyValue();
		all.setKey(null);
		all.setValue(null);
		all.setDescription("--Select--");
		data.add(all);
		data.addAll(stockOpnameService.getSORecommendList());
		ListModelList<KeyValue> model = new ListModelList<KeyValue>(data);
		cbRecommendation.setModel(model);
		cbRecommendation.renderAll();
		cbRecommendation.setSelectedIndex(0);
		
		List<KeyValue> data2 = new ArrayList<KeyValue>();
		KeyValue all2 = new KeyValue();
		all2.setKey(null);
		all2.setValue(null);
		all2.setDescription("--Select--");
		data2.add(all2);
		data2.addAll(stockOpnameService.getLookupKeyValues(AssetType.CODE.getCode(), null));
		ListModelList<KeyValue> model2 = new ListModelList<KeyValue>(data2);
		cbAssetType.setModel(model2);
		cbAssetType.renderAll();
		cbAssetType.setSelectedIndex(0);
		
		
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
				bnbAsset.setRawValue(key);
				
				bnbBranch.setDisabled(true);
				bnbDescription.setDisabled(true);
				bnbLocation.setDisabled(true);
				cbRecommendation.setDisabled(false);
			}else{
				bnbBranch.setDisabled(false);
				bnbDescription.setDisabled(false);
				bnbLocation.setDisabled(false);
				
				cbRecommendation.setDisabled(true);
				cbRecommendation.setSelectedIndex(0);
			}
			
			
			
			//txtBookTypeCode.setValue(selected.getBookTypeCode());
			txtSerialNumber.setValue(selected.getSerialNumber());
			
			Branch branch = (Branch) stockOpnameService.getBranchById(selected.getBranchId(),selected.getCompanyId());
			if(branch!=null && branch.getBranchCode()!=null){
				KeyValue key = new KeyValue();
				key.setKey(branch.getBranchId());
				key.setValue(branch.getBranchName());
				key.setDescription(branch.getBranchCode());
				bnbBranch.setRawValue(key);
				
			}
			
			SSOALocation location = (SSOALocation) stockOpnameService.getLocationById(selected.getLocationId());
			if(location!=null && location.getLocationId()!=null){
				KeyValue key = new KeyValue();
				key.setKey(location.getLocationId());
				key.setValue(location.getLocationName());
				key.setDescription(location.getLocationCode());
				bnbLocation.setRawValue(key);
				
			}
			txtUmurAsset.setText(selected.getLifeInMonths()!=null?selected.getLifeInMonths().toString():null);
			dDateOfService.setValue(selected.getDatePlacedInService());
			
			//txtAssetCategoery.setValue(selected.getCategoryId()!=null?""+selected.getCategoryId().toString():null);
			AssetCategory assetCategory = (AssetCategory) stockOpnameService.getAssetCategoryById(selected.getCategoryId());
			if(assetCategory!=null && assetCategory.getCategoryId()!=null){
				KeyValue key = new KeyValue();
				key.setKey(assetCategory.getCategoryId());
				key.setValue(assetCategory.getCategoryName());
				key.setDescription(assetCategory.getCategoryId());
				bnbAssetCategory.setRawValue(key);
				
			}
			//txtDescription.setValue(selected.getDescription());
			bnbDescription.setValue(selected.getDescription());
			
			for(int i=0;i<cbRecommendation.getModel().getSize();i++){
				KeyValue val = (KeyValue)cbRecommendation.getModel().getElementAt(i);
				if(val.getValue()!=null && selected.getOpnameSugestCode()!=null && val.getValue().toString().equals(selected.getOpnameSugestCode().toString())){
					cbRecommendation.setSelectedIndex(i);
				}
			}
			
			for(int i=0;i<cbAssetType.getModel().getSize();i++){
				KeyValue val = (KeyValue)cbAssetType.getModel().getElementAt(i);
				if(val.getValue()!=null && selected.getAssetTypeCode()!=null && val.getValue().toString().equals(selected.getAssetTypeCode().toString())){
					cbAssetType.setSelectedIndex(i);
				}
			}
			txtPosition.setValue(selected.getPosition());
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
			bnbBranch.setDisabled(false);
		}
	}
	
	private void loadBranch(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearch());
	}

	class DelegateSearch implements SerializableSearchDelegateDoubleCriteria<Branch> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Branch> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			KeyValue key =(KeyValue) arg.get("Branch");
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			
			List<Branch> listBranch = new ArrayList<Branch>();
			listBranch = stockOpnameService.getBranchByExample(example, limit, offset);

			return listBranch;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			KeyValue key =(KeyValue) arg.get("Branch");
			//KeyValue key = bnbBranch.getKeyValue();
			BranchExample example = new BranchExample();
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andBranchNameLike(searchCriteriaName).andBranchCodeLike(searchCriteriaCode);
			return stockOpnameService.countBranchByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Branch data) {
			keyValue.setKey(data.getBranchId());
			keyValue.setValue(data.getBranchName());
			keyValue.setDescription(data.getBranchCode());
		}
	}

	
	
	
	private void loadLocation(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearchLocation());
	}
	
	class DelegateSearchLocation implements SerializableSearchDelegateDoubleCriteria<SSOALocation> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<SSOALocation> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			SSOALocationExample example = new SSOALocationExample();
			if (!bnbBranch.getValue().isEmpty())
			{
			example.setBranchId((Long)bnbBranch.getKeyValue().getKey());
			}
			else
			{
				Messagebox.show("Silahkan pilih branch terlebih dahulu "+""+"" , "Information", Messagebox.YES,
						Messagebox.ERROR);
			}
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andLocationNameLike(searchCriteriaName).andLocationCodeLike(searchCriteriaCode);
			
			List<SSOALocation> listLocation = new ArrayList<SSOALocation>();
			listLocation = stockOpnameService.getLocationByExample(example, limit, offset);
			return listLocation;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			SSOALocationExample example = new SSOALocationExample();
			if (!bnbBranch.getValue().isEmpty())
			{
			example.setBranchId((Long)bnbBranch.getKeyValue().getKey());
			}
			
			example.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			example.createCriteria().andLocationNameLike(searchCriteriaName).andLocationCodeLike(searchCriteriaCode);
			return stockOpnameService.countLocationByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, SSOALocation data) {
			keyValue.setKey(data.getLocationId());
			keyValue.setValue(data.getLocationName());
			keyValue.setDescription(data.getLocationCode());
		}
	}
	
    private void buildDescriptionPopup() {
		
		SerializableSearchDelegate<Assets> descriptionSearchDelegate = new SerializableSearchDelegate<Assets>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Assets> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				AssetsExample example = new AssetsExample();
				example.createCriteria().andDescriptionLike(searchCriteria);
				
				List<Assets> listDesc = new ArrayList<Assets>();
				listDesc = stockOpnameService.getDescriptionByExample(example, limit, offset);
				return listDesc;
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				AssetsExample example = new AssetsExample();
				example.createCriteria().andDescriptionLike(searchCriteria);
				return stockOpnameService.countDecsriptionByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, Assets data) {
				keyValue.setKey(data.getDescription());
				keyValue.setValue(data.getDescription());
				keyValue.setDescription(data.getDescription());
			}
		};
		bnbDescription.setTitle("Description");
		bnbDescription.setSearchText("Description");
		bnbDescription.setHeaderLabel("Description");
		bnbDescription.setSearchDelegate(descriptionSearchDelegate);
	}
    
    private void buildAssetCategoryPopup() {
		
		SerializableSearchDelegate<AssetCategory> descriptionSearchDelegate = new SerializableSearchDelegate<AssetCategory>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<AssetCategory> findBySearchCriteria(String searchCriteria, int limit, int offset) {
				AssetCategoryExample example = new AssetCategoryExample();
				example.createCriteria().andCategoryNameLike(searchCriteria);
				
				List<AssetCategory> listDesc = new ArrayList<AssetCategory>();
				listDesc = stockOpnameService.getAssetCategoryByExample(example, limit, offset);
				return listDesc;
			}

			@Override
			public int countBySearchCriteria(String searchCriteria) {
				AssetCategoryExample example = new AssetCategoryExample();
				example.createCriteria().andCategoryNameLike(searchCriteria);
				return stockOpnameService.countAssetCategoryByExample(example);
			}

			@Override
			public void mapper(KeyValue keyValue, AssetCategory data) {
				keyValue.setKey(data.getCategoryId());
				keyValue.setValue(data.getCategoryName());
				keyValue.setDescription(data.getCategoryName());
			}
		};
	}
	
	private void loadAssetCategory(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearchDescription());
	}
	
	class DelegateSearchDescription implements SerializableSearchDelegateDoubleCriteria<AssetCategory> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<AssetCategory> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			AssetCategoryExample example = new AssetCategoryExample();
			example.createCriteria().andCategoryIdLike(searchCriteriaCode).andCategoryNameLike(searchCriteriaName);
			
			List<AssetCategory> listDesc = new ArrayList<AssetCategory>();
			listDesc = stockOpnameService.getAssetCategoryByExample(example, limit, offset);
			return listDesc;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			AssetCategoryExample example = new AssetCategoryExample();
			example.createCriteria().andCategoryIdLike(searchCriteriaCode).andCategoryNameLike(searchCriteriaName);
			return stockOpnameService.countAssetCategoryByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, AssetCategory data) {
			keyValue.setKey(data.getCategoryId());
			keyValue.setValue(data.getCategoryName());
			keyValue.setDescription(data.getCategoryId());
		}
	}
	
	
	private void loadAsset(SSOACommonPopupBandbox bandbox) {
		bandbox.setSearchDelegate(new DelegateSearchAsset());
	}

	class DelegateSearchAsset implements SerializableSearchDelegateDoubleCriteria<Assets> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<Assets> findBySearchCriteria(String searchCriteriaCode,String searchCriteriaName, int limit, int offset) {
			KeyValue key =(KeyValue) arg.get("Branch");
			AssetsExample example = new AssetsExample();
			example.createCriteria().andDescriptionLikeInsensitive(searchCriteriaName).andAssetNumberLike(searchCriteriaCode).andBranchIdNotEqual((Long)key.getKey()).andRetiredFlagLike("N").andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			
			listAssets = stockOpnameService.getAssetsByExample(example, limit, offset);

			return listAssets;
		}

		@Override
		public int countBySearchCriteria(String searchCriteriaCode,String searchCriteriaName) {
			KeyValue key =(KeyValue) arg.get("Branch");
			AssetsExample example = new AssetsExample();
			example.createCriteria().andDescriptionLikeInsensitive(searchCriteriaName).andAssetNumberLike(searchCriteriaCode).andBranchIdNotEqual((Long)key.getKey()).andRetiredFlagLike("N").andCompanyIdLike(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
			return stockOpnameService.countAssetsByExample(example);
		}

		@Override
		public void mapper(KeyValue keyValue, Assets data) {
			keyValue.setKey(data.getId());
			keyValue.setValue(data.getAssetNumber());
			keyValue.setDescription(data.getDescription());
			
		}
	}

	private void buildData() {
		lstStockOpnameInfo.setModel(getDataModel());
		lstStockOpnameInfo.setItemRenderer(getListitemRenderer());
		lstStockOpnameInfo.setValidationRule(getTabularValidationRule());
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
						rebuildTabularEntryOnChangeTaskCollection();

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

	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		lstStockOpnameInfo.setDataType(StockOpnameUnregAssetImgDTO.class);
		lstStockOpnameInfo.addRow();
	}

	@Listen("onClick=button#btnDelete")
	public void deleteRow() {
		Messagebox.show("Are you sure want to delete this data ?", "Information", Messagebox.YES | Messagebox.NO,
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
						Set<StockOpnameUnregAssetImgDTO> selection = data.getSelection();
						for (int i = 0; i < selection.size(); i++) {
							StockOpnameUnregAssetImgDTO stockOpnameUnregAssetImgDTO = selection.iterator().next();
							if (stockOpnameUnregAssetImgDTO.getImageFilePath()!=null && !stockOpnameUnregAssetImgDTO.getImageFilePath().isEmpty()) {
								File file = new File(stockOpnameUnregAssetImgDTO.getImageFilePath());
								file.delete();
							}
						}

						lstStockOpnameInfo.deleteRow();
						// }
						rebuildTabularEntryOnChangeTaskCollection();
						}
					}
				});
	}

	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<StockOpnameUnregAssetImgDTO> model = new ListModelList<StockOpnameUnregAssetImgDTO>(lstStockOpnameInfo.getValue());
		model.setMultiple(true);
		lstStockOpnameInfo.setModel(model);
	}

	@Listen("onClick = #btnSave")
	public void save(Event e) {
		clearErrorMessage();
		if(!validate()){
		if(selected == null){ selected = new StockOpnameUnregAssetsDTO();}
		//selected.setBookTypeCode(txtBookTypeCode.getValue());
		
		selected.setSerialNumber(txtSerialNumber.getValue());
		if(!bnbBranch.getValue().isEmpty()){
		selected.setBranchId((Long)bnbBranch.getKeyValue().getKey());
		selected.setBranchCode((String)bnbBranch.getKeyValue().getDescription());
		selected.setBranchName((String)bnbBranch.getKeyValue().getValue());
		}
		if(!txtUmurAsset.getText().isEmpty()){
		selected.setLifeInMonths(txtUmurAsset.getValue()!=null?new Long(txtUmurAsset.getValue()):0);
		}
		selected.setDatePlacedInService(dDateOfService.getValue());
		selected.setCompanyId(securityServiceImpl.getSecurityProfile().getWorkspaceCompanyId());
		if(!bnbAssetCategory.getValue().isEmpty()){
			selected.setCategoryId((Long)bnbAssetCategory.getKeyValue().getKey());
			selected.setCategoryName((String)bnbAssetCategory.getKeyValue().getValue());
		}
		
		selected.setStockOpnameUnregAssetImg(lstStockOpnameInfo.getValue());
		//selected.setOpnameResult("Tidak Tercatat");
		if(!bnbLocation.getValue().isEmpty()){
	    KeyValue keyValue = (KeyValue)bnbLocation.getKeyValue();
		selected.setLocationId((Long)keyValue.getKey());
		selected.setLocationName((String)keyValue.getValue());
		selected.setLocationCode((String)keyValue.getDescription());
		}
		
		if(!bnbAsset.getValue().isEmpty()){
		KeyValue keyValue = (KeyValue)bnbAsset.getKeyValue();
		selected.setAssetId((Long)keyValue.getKey());
		selected.setAssetNo((String)keyValue.getValue());
		selected.setDescription((String)keyValue.getDescription());
		}else{
			selected.setAssetId(null);
			selected.setAssetNo(null);
			selected.setDescription(null);
		}
		
		selected.setDescription(bnbDescription.getValue());
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
		
		List<KeyValue> data1 = stockOpnameService.getLookupKeyValues(SOResult.CODE.getCode().trim(), SOResult.NOT_RECORDED.getCode().trim());
		if(data1.size()>0){
			selected.setOpnameResultId(((BigDecimal)data1.get(0).getKey()).longValue());
			selected.setOpnameResultCode((String)data1.get(0).getValue());
			selected.setOpnameResult((String)data1.get(0).getDescription());
		}
		
		KeyValue keyType = (KeyValue)cbAssetType.getModel().getElementAt(cbAssetType.getSelectedIndex());
		if(keyType.getValue()!=null){
		selected.setAssetTypeId(((BigDecimal)keyType.getKey()).longValue());
		selected.setAssetTypeCode((String)keyType.getValue());
		selected.setAssetType((String)keyType.getDescription());
		}else{
			selected.setAssetTypeId(null);
			selected.setAssetType(null);
			selected.setAssetTypeCode(null);
		}
		//selected.setOpnameSugest((String)keyType.getDescription());
		selected.setPosition(txtPosition.getValue());
		List<StockOpnameUnregAssetsDTO> list  = new ArrayList<StockOpnameUnregAssetsDTO>();
		list.add(selected);
		ListModelList<StockOpnameUnregAssetsDTO> model = new ListModelList<StockOpnameUnregAssetsDTO>(list);
		Events.postEvent(new Event(Events.ON_CLOSE,source,model));
		winEditPopup.detach();
		}
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
							winEditPopup.detach();
						}
					}
				});
		
	}
	
	private Boolean validate(){
		Boolean flag = false;
		if(bnbAsset.getValue() == null || bnbAsset.getValue().isEmpty()){
			if(cbAssetType.getSelectedIndex() == 0) {
				ErrorMessageUtil.setErrorMessage(lblAssetType, "Asset Type must be filled");
				flag = true;
			}
			if(bnbAssetCategory.getValue().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(lblAssetCategory, "Asset Category must be filled");
				flag = true;
			}
			if(dDateOfService.getValue()== null) {
				ErrorMessageUtil.setErrorMessage(lblDateOfService, "Tanggal Perolehan must be filled");
				flag = true;
			}
			if(bnbBranch.getValue().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(lblBranch, "Branch must be filled");
				flag = true;
			}
			if(txtUmurAsset.getText().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(lblUmurAsset, "Life in Months must be filled");
				flag = true;
			}
			
			if(bnbLocation.getValue().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(lblLocation, "Outlet must be filled");
				flag = true;
			}
			
			if(bnbDescription.getValue().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(lblDescription, "Description must be filled");
				flag = true;
			}
			
			if(txtSerialNumber.getValue().isEmpty()) {
				ErrorMessageUtil.setErrorMessage(lblSerialNo, "Serial Number must be filled");
				flag = true;
			}
			
		}
		if(txtPosition.getValue().isEmpty()) {
			ErrorMessageUtil.setErrorMessage(txtPosition, "Position must be filled");
			flag = true;
		}
		if(!bnbAsset.getValue().isEmpty()){
			if(cbRecommendation.getSelectedIndex() == 0) {
				ErrorMessageUtil.setErrorMessage(cbRecommendation, "Recommendation must be filled");
				flag = true;
			}
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
				StockOpnameUnregAssetImgDTO stockOpnameUnregAssetImgDTO = (StockOpnameUnregAssetImgDTO)lstStockOpnameInfo.getModel().getElementAt(i);
				if(stockOpnameUnregAssetImgDTO.getImageFilePath() == null || stockOpnameUnregAssetImgDTO.getImageFilePath().isEmpty()){
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
	
	private void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lblAssetNo);
		ErrorMessageUtil.clearErrorMessage(lblAssetCategory);
		ErrorMessageUtil.clearErrorMessage(lblSerialNo);
		ErrorMessageUtil.clearErrorMessage(lblDescription);
		ErrorMessageUtil.clearErrorMessage(lblLocation);
		ErrorMessageUtil.clearErrorMessage(lblUmurAsset);
		ErrorMessageUtil.clearErrorMessage(lblBranch);
		ErrorMessageUtil.clearErrorMessage(dDateOfService);
		ErrorMessageUtil.clearErrorMessage(lblDateOfService);
		ErrorMessageUtil.clearErrorMessage(lblAssetType);
		ErrorMessageUtil.clearErrorMessage(txtPosition);
		ErrorMessageUtil.clearErrorMessage(cbRecommendation);
		ErrorMessageUtil.clearErrorMessage(lblErrStockOpnameInfo);
	}
	
	private void lifeInMonths(final Datebox datebox, final Textbox textbox) { 
		
		
		
	}
	
}
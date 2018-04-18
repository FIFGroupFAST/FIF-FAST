package co.id.fifgroup.ssoa.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.ssoa.constants.SSOAConstants;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.RetirementImg;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample.Criteria;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementImgDTO;
import co.id.fifgroup.ssoa.service.RetirementNonEBSService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementNonEBSAddImageComposer extends SelectorComposer<Window> {

	@Wire
	private Button btnSavePopup;
	@Wire
	private Textbox txtAssetNumber;
	@Wire
	private Textbox txtDescription;
	@Wire
	private Datebox dtDateStart;
	@Wire
	private Datebox dtDateEnd;
	@Wire
	private Listbox lstAssetPopup;
	@Wire
	private Window winPopupImage;
	@Wire
	private TabularEntry<RetirementImgDTO> lstRetirementImg;
	
	private Listbox source;

	@WireVariable
	private Map<String, Object> arg;
	@WireVariable(rewireOnActivate = true)
	private transient RetirementNonEBSService retirementNonEBSService;
	@Wire
	private Paging pgListOfValue;
	@WireVariable(rewireOnActivate=true)
	private transient SecurityService securityServiceImpl;
	
	private RetirementDetailDTO retirementDetailDTO;
	private List<RetirementImgDTO> retirementImgList;
	private static final long serialVersionUID = 1L;
	private static String tmpFile ="E:\\Temp\\";
	private static String maxSize ="1000000";
	//private static final String imagePathTmp ="IMAGE_PATH_TEMP";
	//private static final String maxSizeUploadFile ="MAX_SIZE_UPLOAD_RETIREMENT";
	

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		try {
			super.doAfterCompose(comp);
		}catch (Exception e) {
			e.printStackTrace();
		}

		init();
		
	}

	private void init() {
		source = arg.get("source") == null ? null : (Listbox) arg.get("source");
		retirementDetailDTO = (RetirementDetailDTO)arg.get("retirementDetailDTO");
		if(retirementDetailDTO!=null && retirementDetailDTO.getRetirementImg()!=null){
			if(retirementImgList!=null){
			retirementImgList.clear();
			}else{
				retirementImgList = new ArrayList<>();	
			}
			retirementImgList.addAll(retirementDetailDTO.getRetirementImg());
		}
		List<GlobalSettingDTO> dataImgPath = retirementNonEBSService.getGlobalsettingByCode(SSOAConstants.imagePathTmp);
		if(dataImgPath!=null){
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
		List<GlobalSettingDTO> dataMaxSizeUpload = retirementNonEBSService.getGlobalsettingByCode(SSOAConstants.maxSizeUploadFileRetirement);
		if(dataMaxSizeUpload!=null){
			maxSize = dataMaxSizeUpload.get(0).getGlobalSetting().getValue().toString();
		}
		buildData();
	}

	private void buildData() {
		lstRetirementImg.setModel(getDataModel());
		lstRetirementImg.setItemRenderer(getListitemRenderer());
		lstRetirementImg.setValidationRule(getTabularValidationRule());
		lstRetirementImg.renderAll();
	}
	
	private ListModelList<RetirementImgDTO> getDataModel() {
		if(retirementImgList == null || retirementImgList.size() < 1) {
			retirementImgList = new ArrayList<RetirementImgDTO>();
			retirementImgList.add(new RetirementImgDTO());
		}
		ListModelList<RetirementImgDTO> model = new ListModelList<RetirementImgDTO>(retirementImgList);
		model.setMultiple(true);
		return model;
	}
	
	private TabularValidationRule<RetirementImgDTO> getTabularValidationRule() {
		return new TabularValidationRule<RetirementImgDTO>() {

			private static final long serialVersionUID = -5668232788580509231L;

			@Override
			public boolean validate(RetirementImgDTO data, List<String> errorRow) {
				
				return true;
			}
		};
	}
	
	private SerializableListItemRenderer<RetirementImg> getListitemRenderer() {
		return new SerializableListItemRenderer<RetirementImg>() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("null")
			@Override
			public void render(Listitem item, final RetirementImg data, final int index)
					throws Exception {
				
				RetirementImg stockOpnameImg = data;
				
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
						ListModel model = lstRetirementImg.getModel();
						RetirementImg data= (RetirementImg)model.getElementAt(index);
						data.setImageFileName(fileName);
						data.setImageFile(event.getMedia().getByteData());
						String uuid = UUID.randomUUID().toString();
						FileUtils.writeByteArrayToFile(new File(tmpFile+uuid+"_"+fileName), event.getMedia().getByteData());
						data.setImageFilePath(tmpFile+uuid+"_"+fileName);
						
						
						//System.out.println("msk upload"+data.getImageFileName());
						rebuildTabularEntryOnChangeTaskCollection();
						}
						
					}

					
				});
				createImageData(item,data,btnImage,imageName);
				
						
			}
		};
	}
	
	
	private void createImageData(Listitem item, final RetirementImg data, Button btnImage, A imageName) {
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

	public void rebuildTabularEntryOnChangeTaskCollection() {
		ListModelList<RetirementImg> model = new ListModelList<RetirementImg>(lstRetirementImg.getValue());
		model.setMultiple(true);
		lstRetirementImg.setModel(model);
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
					winPopupImage.detach();
				} else {
					return;
				}
			}
		});
	}	
		
	
	@Listen("onClick=button#btnAddRow")
	public void addRow() {
		lstRetirementImg.setDataType(RetirementImgDTO.class);
		lstRetirementImg.addRow();
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
						if (status == 16) {
							ListModel listData = (ListModel) lstRetirementImg.getModel();
							ListModelList data = (ListModelList) listData;
							Set<RetirementImgDTO> selection = data.getSelection();
							int i = 0;
							for (Iterator iter = selection.iterator(); selection.iterator().hasNext();) {
								RetirementImgDTO retirementImgDTO = selection.iterator().next();
								if (retirementImgDTO.getImageFilePath() != null) {
									File file = new File(retirementImgDTO.getImageFilePath());
									file.delete();
								}
								i++;
								if (i == selection.size()) {
									break;
								}
							}
							lstRetirementImg.deleteRow();

							rebuildTabularEntryOnChangeTaskCollection();
						}
					}
				});
	}
	
	@Listen("onClick = #btnOK")
	public void save(Event e) {
		winPopupImage.detach();
		List<RetirementImgDTO> list = lstRetirementImg.getValue();
		for(int i=0;i<list.size();i++){
			RetirementImgDTO retirementImgDTO = (RetirementImgDTO)list.get(i);
			retirementImgDTO.setRetirementDtlId(retirementDetailDTO.getId());
			retirementImgDTO.setRetirementDetailDTO(retirementDetailDTO);
		}
		Events.postEvent(new Event(Events.ON_CLOSE,source,list));
		getSelf().detach();
	}
}
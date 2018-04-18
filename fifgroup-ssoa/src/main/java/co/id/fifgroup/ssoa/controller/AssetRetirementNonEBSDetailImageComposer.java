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
import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample;
import co.id.fifgroup.ssoa.domain.RetirementImg;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.domain.RetirementDetailExample.Criteria;
import co.id.fifgroup.ssoa.dto.RetirementDetailDTO;
import co.id.fifgroup.ssoa.dto.RetirementImgDTO;
import co.id.fifgroup.ssoa.service.RetirementNonEBSService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRetirementNonEBSDetailImageComposer extends SelectorComposer<Window> {

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
	private ListModelList<RetirementImg> listModelImage;
	@Wire
	private Listbox lstRetirementImg;
	private List<RetirementImg> listDetailImage;
	
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
		Long id = (Long)arg.get("retirementDetailDTO");
		
		listModelImage = new ListModelList<RetirementImg>();
		RetirementImg retirementBast = new RetirementImg();
		retirementBast.setId(id);
		listDetailImage = retirementNonEBSService.getDetailImageByPrimaryKey(retirementBast.getId());
		listModelImage.clear();
		listModelImage.addAll(listDetailImage);
		lstRetirementImg.setModel(listModelImage);
	
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
		
	@Listen("onDetailImage= #winPopupImage")
	public void onDetail(ForwardEvent event){
	 	RetirementImg retirementImg = (RetirementImg) event.getData();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("images", retirementImg.getImageFilePath());
		Window win = (Window) Executions.createComponents("~./hcms/ssoa/stock_opname_popup_image.zul", getSelf().getParent(), param);
		win.doModal();
	}	
	
}
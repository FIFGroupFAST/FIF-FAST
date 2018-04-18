package co.id.fifgroup.ssoa.controller;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
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
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.core.ui.lookup.CommonEmployeeNumberBandbox;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegate;
import co.id.fifgroup.core.ui.lookup.SerializableSearchDelegateDoubleCriteria;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.util.ErrorMessageUtil;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.controller.AssetTransferComposer.DelegateSearch;
import co.id.fifgroup.ssoa.domain.AssetLabeling;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetail;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.StockOpnameDetail;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDetailDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameImgDTO;
import co.id.fifgroup.ssoa.service.AssetLabelingService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;


@VariableResolver(DelegatingVariableResolver.class)
public class AssetLabelingDetailComposer extends SelectorComposer<Window> {

	@Wire
	private Listbox lstAssetdetail;	
	@Wire
	private Textbox txtBranchName;
	@Wire
	private Textbox branchOwnerDetail;
	@Wire
    private Textbox txtBranchId;
	
	@Wire
	private Datebox labelingDateDetail;

	@Wire
	private Textbox descriptionDetail;
	
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
	
	private List<AssetLabelingDetailDTO> assetLabelingDetailList;
	private ListModelList<AssetLabelingDetailDTO> listModelAsset;
	private AssetLabelingDTO selected;
	private FunctionPermission functionPermission;
	private AssetLabeling assetLabeling;
	private AssetLabelingDTO assetLabelingDto;
	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@WireVariable(rewireOnActivate = true)
	private transient AssetLabelingService assetLabelingService;
	@WireVariable(rewireOnActivate=true)
	private transient ModelMapper modelMapper;
	@WireVariable("arg")
	private Map<String, Object> arg;

	private static Logger log = LoggerFactory.getLogger(AssetLabelingComposer.class);
	/* private EventQueue eq; */
	private static final long serialVersionUID = 1L;
	//private AssetLabelingServiceImpl assetModelingInterface = new AssetLabelingService();
	
	
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		System.out.println("msk doAfterCompose");
		
		loadParameters();
		functionPermission = (FunctionPermission) arg.get(GlobalVariable.FUNCTION_PERMISSION_KEY);
		initFunctionSecurity();
	}
	
	private void disableComponent(boolean disabled){
	}
	
	
	private void initFunctionSecurity(){
		/*if (functionPermission.isCreateable()) {
			disableComponent(false);
		}else if (functionPermission.isEditable()) {
			disableComponent(false);
		}else{
			disableComponent(true);
		}*/
	}
	

	private void loadParameters() {
		selected = (AssetLabelingDTO) arg.get("assetLabelingDto");
		if(selected != null) {
			AssetLabelingDetail assetLabelingDetail = new AssetLabelingDetail();
			assetLabelingDetail = assetLabelingService.getDetailByPrimaryKey(selected.getLabelingId());
		
			labelingDateDetail.setValue(selected.getLabelingDate());
			branchOwnerDetail.setValue (assetLabelingDetail.getBranchName());
			descriptionDetail.setValue(selected.getDescription());
			
			listModelAsset = new ListModelList<AssetLabelingDetailDTO>();
			AssetLabelingDetailDTO assets = new AssetLabelingDetailDTO();
			assets.setAssetId(selected.getLabelingId());
			assetLabelingDetailList = assetLabelingService.getDetailAssetsByPrimaryKey(selected.getLabelingId());
			listModelAsset.clear();
			listModelAsset.addAll(assetLabelingDetailList);
			lstAssetdetail.setModel(listModelAsset);
			
		}
	}
	
	@Listen ("onClick = button#btnBack")
	public void back() {
		Messagebox.show("Are you sure want to back ?", "Information", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION, new SerializableEventListener<Event>() {
			
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
	
	
}
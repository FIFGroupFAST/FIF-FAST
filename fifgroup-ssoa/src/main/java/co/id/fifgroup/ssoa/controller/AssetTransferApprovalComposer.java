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
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.core.ui.tabularentry.TabularValidationRule;
import co.id.fifgroup.core.ui.tabularentry.TextboxListcell;
import co.id.fifgroup.ssoa.domain.Assets;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.service.AssetTransferService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetTransferApprovalComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;

	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Textbox txtDocumentNo;
	@Wire
	private Textbox txtBastDocName;
	@Wire
	private Textbox txtBranchOrigin;
	@Wire
	private Textbox txtBranch;
	@Wire
	private Textbox txtBranchDestination;
	@Wire
	private Datebox dbRequestDate;
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
	private Listbox lbxDetailAsset;
	@Wire
	private TabularEntry<AssetTransferBastDTO> lbxBASTDetail;

	private ListModelList<AssetTransferDetailDTO> listModelAsset;
	private List<AssetTransferDetailDTO> listDetailAssets;

	@Wire
	private Groupbox gbDetail;
	@Wire
	private Groupbox gbBASTDetail;

	@Wire
	private Groupbox gbListAssetDetail;

	private List<AssetTransferBastDTO> listAssetTransferBastDTO;

	@WireVariable(rewireOnActivate = true)
	private transient AssetTransferService assetTransferService;

	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Window winAssetTransferApproval;
	private List<AssetTransferDetailDTO> assetsList;
	private AssetTransferDTO assetTransferDTO;
	private FunctionPermission functionPermission;
	private static String tmpFile = "E:\\Temp\\";
	private static final String imageCode = "IMAGE_PATH";

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		// buildData();
		// buildDataBast();
		action();
		setTmpFilePath();
	}

	private void setTmpFilePath() {
		List<GlobalSettingDTO> dataImgPath = assetTransferService.getGlobalsettingByCode(imageCode);
		if (dataImgPath != null) {
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
	}

	private void action() {

	}

	private void init() {
		AssetTransferDTO avmTrxId = arg.get("applicationData") != null ? (AssetTransferDTO) arg.get("applicationData") : null;
		String action = "detail";
		assetTransferDTO = assetTransferService.getAssetTransferByAvmTrxId(avmTrxId.getAvmTrxId().toString());
		/*if (assetTransferDTO == null) {
			assetTransferDTO = assetTransferService.getAssetTransferByAvmTrxId(avmTrxId);
		}*/
		assetTransferDTO = assetTransferService.getAssetTransferById(assetTransferDTO.getTransferId());
		assetsList = new ArrayList<AssetTransferDetailDTO>();
		// listAddAsset = assetTransferDto.getAssetTransferAdd();

		txtDocumentNo.setValue(assetTransferDTO.getRequestNo());
		txtDocumentNo.setDisabled(true);

		txtBranch.setValue(assetTransferDTO.getBranchNameOrigin());
		txtBranch.setDisabled(true);

		txtReason.setValue(assetTransferDTO.getReason());
		txtReason.setDisabled(true);

		dbRequestDate.setValue(assetTransferDTO.getRequestDate());
		dbRequestDate.setDisabled(true);

		txtBranchDestination.setValue(assetTransferDTO.getBranchNameDestination());
		txtBranchDestination.setDisabled(true);

		listModelAsset = new ListModelList<AssetTransferDetailDTO>();
		AssetTransferDetailDTO assets = new AssetTransferDetailDTO();
		assets.setAssetId(assetTransferDTO.getTransferId());
		listDetailAssets = assetTransferService.getDetailAssetsByPrimaryKey(assets.getAssetId());
		listModelAsset.clear();
		listModelAsset.addAll(listDetailAssets);
		lbxDetailAsset.setModel(listModelAsset);

	}

	@Listen("onClick = button#btnClose")
	public void back() {
		winAssetTransferApproval.detach();
	}

	/*
	 * @Listen("onViewImage= #winAssetTransferApproval") public void
	 * onViewImage(ForwardEvent event){ AssetTransferDetailDTO
	 * retirementDetailDTO = (AssetTransferDetailDTO) event.getData();
	 * Map<String, Object> param = new HashMap<String, Object>();
	 * param.put("retirementDetailDTO", retirementDetailDTO);
	 * param.put("source", lstRetirementImg); Window win = (Window)
	 * Executions.createComponents(
	 * "~./hcms/ssoa/asset_retirement_add_image.zul", getSelf().getParent(),
	 * param); win.doModal();
	 * 
	 * }
	 */

	/*
	 * public void rebuildTabularEntryOnChangeTaskCollectionBast() {
	 * ListModelList<AssetTransferBastDTO> model = new
	 * ListModelList<AssetTransferBastDTO>(lbxBASTDetail.getValue());
	 * model.setMultiple(true); lstRetirementBast.setModel(model); }
	 */

}
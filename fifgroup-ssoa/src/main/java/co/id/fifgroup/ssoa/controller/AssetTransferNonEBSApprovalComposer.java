package co.id.fifgroup.ssoa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.basicsetup.dto.GlobalSettingDTO;
import co.id.fifgroup.core.security.FunctionPermission;
import co.id.fifgroup.core.service.SecurityService;
import co.id.fifgroup.core.ui.tabularentry.TabularEntry;
import co.id.fifgroup.ssoa.common.SSOACommonPopupBandbox;
import co.id.fifgroup.ssoa.dto.AssetTransferBastDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.service.AssetTransferNonEBSService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetTransferNonEBSApprovalComposer extends SelectorComposer<Window> {

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

	@WireVariable(rewireOnActivate = true)
	private transient AssetTransferNonEBSService assetTransferNonEBSService;

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
		action();
		setTmpFilePath();
	}

	private void setTmpFilePath() {
		List<GlobalSettingDTO> dataImgPath = assetTransferNonEBSService.getGlobalsettingByCode(imageCode);
		if (dataImgPath != null) {
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
	}

	private void action() {

	}

	private void init() {
		AssetTransferDTO avmTrxId = arg.get("applicationData") != null ? (AssetTransferDTO) arg.get("applicationData") : null;
		assetTransferDTO = assetTransferNonEBSService.getAssetTransferByAvmTrxId(avmTrxId.getAvmTrxId().toString());
		assetTransferDTO = assetTransferNonEBSService.getAssetTransferById(assetTransferDTO.getTransferId());
		assetsList = new ArrayList<AssetTransferDetailDTO>();

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
		listDetailAssets = assetTransferNonEBSService.getDetailAssetsByPrimaryKey(assets.getAssetId());
		listModelAsset.clear();
		listModelAsset.addAll(listDetailAssets);
		lbxDetailAsset.setModel(listModelAsset);

	}

	@Listen("onClick = button#btnClose")
	public void back() {
		winAssetTransferApproval.detach();
	}

}
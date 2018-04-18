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
import co.id.fifgroup.ssoa.domain.AssetRegistrationDetailExample;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDTO;
import co.id.fifgroup.ssoa.dto.AssetRegistrationDetailDTO;
import co.id.fifgroup.ssoa.service.AssetRegistrationService;

@VariableResolver(DelegatingVariableResolver.class)
public class AssetRegistrationApprovalComposer extends SelectorComposer<Window> {

	private static final long serialVersionUID = 4373064548488803096L;

	@WireVariable(rewireOnActivate = true)
	private transient SecurityService securityServiceImpl;
	@Wire
	private Textbox txtRegistrationNo;
	@Wire
	private Datebox dbRegistrationDate;
	@Wire
	private Textbox txtRemarks;

	@Wire
	private Listbox lbxDetailAsset;
	private ListModelList<AssetRegistrationDetailDTO> listModelAsset;
	private List<AssetRegistrationDetailDTO> listDetailAssets;

	@Wire
	private Groupbox gbDetail;

	@Wire
	private Groupbox gbListAssetDetail;

	@WireVariable(rewireOnActivate = true)
	private transient AssetRegistrationService assetRegistrationService;

	@WireVariable("arg")
	private Map<String, Object> arg;
	@Wire
	private Window winAssetRegistrationApproval;
	private List<AssetRegistrationDetailDTO> assetsList;
	private AssetRegistrationDTO assetRegistrationDTO;
	private FunctionPermission functionPermission;
	private static String tmpFile = "E:\\Temp\\";
	private static final String imageCode = "IMAGE_PATH";

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		init();
		setTmpFilePath();
	}

	private void setTmpFilePath() {
		List<GlobalSettingDTO> dataImgPath = assetRegistrationService.getGlobalsettingByCode(imageCode);
		if (dataImgPath != null) {
			tmpFile = dataImgPath.get(0).getGlobalSetting().getValue().toString();
		}
	}

	private void init() {
		AssetRegistrationDTO avmTrxId = arg.get("applicationData") != null ? (AssetRegistrationDTO) arg.get("applicationData") : null;
		String action = "detail";
		assetRegistrationDTO = assetRegistrationService.getAssetRegistrationByAvmTrxId(avmTrxId.getAvmTrxId().toString());
		
		assetRegistrationDTO = assetRegistrationService.getAssetRegistrationById(assetRegistrationDTO.getRegistrationId());
		assetsList = new ArrayList<AssetRegistrationDetailDTO>();
		// listAddAsset = assetTransferDto.getAssetTransferAdd();

		txtRegistrationNo.setValue(assetRegistrationDTO.getRegistrationNo());
		txtRegistrationNo.setDisabled(true);

		txtRemarks.setValue(assetRegistrationDTO.getRemarks());
		txtRemarks.setDisabled(true);

		dbRegistrationDate.setValue(assetRegistrationDTO.getRegistrationDate());
		dbRegistrationDate.setDisabled(true);

		listModelAsset = new ListModelList<AssetRegistrationDetailDTO>();
		AssetRegistrationDetailDTO assets = new AssetRegistrationDetailDTO();
		assets.setAssetId(assetRegistrationDTO.getRegistrationId());
		listDetailAssets = assetRegistrationService.getDetailAssetsByPrimaryKey(assets.getAssetId());
		listModelAsset.clear();
		listModelAsset.addAll(listDetailAssets);
		lbxDetailAsset.setModel(listModelAsset);

	}

	@Listen("onClick = button#btnClose")
	public void back() {
		winAssetRegistrationApproval.detach();
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
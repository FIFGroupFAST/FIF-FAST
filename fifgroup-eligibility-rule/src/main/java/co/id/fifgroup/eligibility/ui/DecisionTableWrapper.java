package co.id.fifgroup.eligibility.ui;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Span;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

import co.id.fifgroup.core.util.ErrorMessageUtil;

import co.id.fifgroup.core.ui.SerializableListItemRenderer;
import co.id.fifgroup.eligibility.dto.DecisionTableDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableModelDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableRowDTO;
import co.id.fifgroup.eligibility.service.DecisionTableModelSetupService;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;
import co.id.fifgroup.eligibility.service.impl.DecisionTableUploadServiceImpl;

public class DecisionTableWrapper extends Grid {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory
			.getLogger(DecisionTableWrapper.class);

	private Long decisionTableId;

	private final Listbox lstModel;
	private final Listbox lstModelVersion;
	private final Decisiontable decisionTable;
	private final Button btnAdd;
	private final Button btnRemove;
	private final Button btnDownload;
	private final Button btnUpload;
	private UploadDownloadDelegate uploadDownloadDelagate;
	private final Label lblErrorDownload;
	private final Label lblNoData;
	public DecisionTableWrapper() {
		super();
		btnDownload = new Button(Labels.getLabel("common.download"));
		btnUpload = new Button(Labels.getLabel("common.upload"));
		lblNoData=new Label(Labels.getLabel("hou.labelNoData"));
		lblNoData.setVisible(false);
		lblNoData.setStyle("color:red;");
		lblErrorDownload=new Label(Labels.getLabel("hou.labelErrorDownload"));
		lblErrorDownload.setVisible(false);
		lblErrorDownload.setStyle("color:red;");
		setStyle("border:0px;");
		decisionTable = new Decisiontable();
		Rows rows = new Rows();
		rows.setParent(this);
		lstModel = new Listbox();
		lstModel.setId("lstModel" + java.util.UUID.randomUUID());

		lstModel.setItemRenderer(new SerializableListItemRenderer<DecisionTableModelDTO>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void render(Listitem item, DecisionTableModelDTO data,
					int index) throws Exception {
				item.setLabel(null == data ? Labels
						.getLabel("common.pleaseSelect") : data.getName());
				item.setValue(data);
			}
		});

		lstModel.addEventListener(Events.ON_SELECT, getModelListener());
		
		lstModel.setMold("select");
		ListModelList<DecisionTableModelDTO> listModelList = new ListModelList<>(
				getService().findByExample(null));
		listModelList.add(0, null);
		lstModel.setModel(listModelList);
		lstModel.renderAll();
		Row modelRow = new Row();

		new Label("Decision Table Model").setParent(modelRow);
		lstModel.setParent(modelRow);
		// btnUpload.setParent(modelRow);
		modelRow.setParent(rows);

		lstModelVersion = new Listbox();
		lstModelVersion.setId("lstModelVersion" + java.util.UUID.randomUUID());
		lstModelVersion.setMold("select");
		lstModelVersion
				.setItemRenderer(new SerializableListItemRenderer<Integer>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void render(Listitem item, Integer data, int index)
							throws Exception {
						item.setLabel(String.valueOf(!data.equals(0) ? data
								: Labels.getLabel("common.pleaseSelect")));
						item.setValue(data);
					}
				});
		lstModelVersion
				.addEventListener(Events.ON_SELECT, getVersionListener());

		Row versionRow = new Row();
		new Label("Version").setParent(versionRow);
		lstModelVersion.setParent(versionRow);
		versionRow.setParent(rows);

		Row dtRow = new Row();
		Cell dtCell = new Cell();
		dtCell.setColspan(2);
		dtCell.setParent(dtRow);
		decisionTable.setParent(dtCell);
		dtRow.setParent(rows);

		Row btnRow = new Row();
		Cell btnCell = new Cell();
		btnCell.setAlign("left");
		btnCell.setWidth("800px");
		btnAdd = new Button(Labels.getLabel("common.addRow"));
		btnAdd.setParent(btnCell);
		btnRemove = new Button(Labels.getLabel("common.delete"));
		btnRemove.setParent(btnCell);
		Cell cell = new Cell();
		cell.setAlign("right");

		btnUpload.setParent(cell);
		btnDownload.setParent(cell);
		lblErrorDownload.setParent(cell);
		lblNoData.setParent(cell);
		btnCell.setParent(btnRow);
		cell.setParent(btnRow);

		btnRow.setParent(rows);

		btnAdd.addEventListener(Events.ON_CLICK,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if (decisionTable.getData() != null)
							decisionTable.addRow(new DecisionTableRowDTO());

					}
				});

		btnRemove.addEventListener(Events.ON_CLICK,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						if (decisionTable != null) {
							decisionTable.removeSelectedRows();
						}
					}
				});

		lstModelVersion.setDisabled(true);

		btnUpload.addEventListener(Events.ON_CLICK,
				new SerializableEventListener<Event>() {

					/**
			 * 
			 */
					private static final long serialVersionUID = 6433132920053635915L;

					@Override
					public void onEvent(Event event) throws Exception {
						if (decisionTable != null
								&& uploadDownloadDelagate != null)
							uploadDownloadDelagate.doUpload(getDecisionTable());
					}
				});

		btnDownload.addEventListener(Events.ON_CLICK,
				new SerializableEventListener<Event>() {

					/**
			 * 
			 */
					private static final long serialVersionUID = -8532301367579964785L;

					@Override
					public void onEvent(Event event) throws Exception {
//						if(lstModel.getSelectedItem().getValue()==null
//								&& lstModelVersion.getSelectedItem().getValue()==null && decisionTable.getData()==null || lstModel.getSelectedItem().getValue()!=null
//								&& lstModelVersion.getSelectedItem().getValue()==null) {
//						lblErrorDownload.setVisible(true);
//						lblNoData.setVisible(false);
//						}	
						if((lstModel.getSelectedItem()==null || lstModel.getSelectedItem().getValue()==null)
								|| (lstModelVersion.getSelectedItem()==null || lstModelVersion.getSelectedItem().getValue()==null)) {
						lblErrorDownload.setVisible(true);
						lblNoData.setVisible(false);
						}
						else if(decisionTable != null
								&& uploadDownloadDelagate != null
								&& lstModel.getSelectedItem().getValue()!=null
								&& lstModelVersion.getSelectedItem().getValue()!=null && (decisionTable.getData()==null 
								|| decisionTable.getData().getRows().isEmpty()
								|| decisionTable.getData().getRows().size()==0
								|| decisionTable.getDecisionTableModel().getId()==null)){
							lblNoData.setVisible(true);
							lblErrorDownload.setVisible(false);
						} 
						else if (decisionTable != null
								&& uploadDownloadDelagate != null
								&& lstModel.getSelectedItem().getValue()!=null
								&& lstModelVersion.getSelectedItem().getValue()!=null 
								&& decisionTable.getData()!=null
								&& !decisionTable.getData().getRows().isEmpty()
								&& decisionTable.getData().getRows().size()>0) {
							lblErrorDownload.setVisible(false);
							lblNoData.setVisible(false);
							uploadDownloadDelagate.doDownload(
									getDecisionTable(),
									getDecisionTableUploadService());
						}
					}
				});
	}

	public void setDecisionTable(DecisionTableDTO decisionTableDTO) {
		if (decisionTableDTO != null) {
			this.decisionTableId = decisionTableDTO.getId();
			for (Listitem item : lstModel.getItems()) {
				DecisionTableModelDTO model = item.getValue();
				if (model != null
						&& model.getId().equals(
								decisionTableDTO.getModel().getId())) {
					item.setSelected(true);
					ListModelList<Integer> versionModel = new ListModelList<>(
							getService().getAvailableVersions(model.getId()));
					versionModel.add(0, 0);
					lstModelVersion.setModel(versionModel);
					lstModelVersion.renderAll();
				}
			}

			for (Listitem item : lstModelVersion.getItems()) {
				if (item.getValue() != null
						&& item.getValue().equals(
								decisionTableDTO.getModel().getVersionNumber())) {
					item.setSelected(true);
				}
			}

		} else {
			lstModel.setSelectedIndex(0);
			lstModelVersion.setSelectedIndex(-1);
		}

		decisionTable.setData(decisionTableDTO);
	}

	public DecisionTableDTO getDecisionTable() {
		if (null != decisionTable.getData())
			decisionTable.getData().setId(decisionTableId);
		return decisionTable.getData();
	}

	private SerializableEventListener<Event> getModelListener() {
		return new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				if (null != lstModel.getSelectedItem()) {
					DecisionTableModelDTO model = lstModel.getSelectedItem()
							.getValue();
					decisionTable.setData(null);
					lstModelVersion.setDisabled(true);
					if (null != model) {
						lstModelVersion.setDisabled(false);
						ListModelList<Integer> listModel = new ListModelList<>(
								getService()
										.getAvailableVersions(model.getId()));
						listModel.add(0, 0);
						lstModelVersion.setModel(listModel);
						listModel.setSelection(Arrays.asList(0));
						lstModelVersion.renderAll();
						onVersionSelect();
					}
				}
			}
		};
	}

	private SerializableEventListener<Event> getVersionListener() {
		return new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onEvent(Event event) throws Exception {
				onVersionSelect();
			}
		};
	}

	private void onVersionSelect() {
		logger.debug("ON SELECT EVENT FIRED");
		logger.debug("selection empty: "
				+ ((ListModelList<?>) lstModelVersion.getModel())
						.isSelectionEmpty());
		if (lstModelVersion.getSelectedItem() != null
				&& lstModel.getSelectedItem() != null
				&& lstModelVersion.getSelectedItem().getValue() != null
				&& !lstModelVersion.getSelectedItem().getValue().equals(0)) {
			Integer version = lstModelVersion.getSelectedItem().getValue();
			DecisionTableDTO dt = new DecisionTableDTO();
			DecisionTableModelDTO dtModel = (DecisionTableModelDTO) lstModel
					.getSelectedItem().getValue();
			if (dtModel != null) {
				dt.setModel(getService().findByIdAndVersionNumber(
						dtModel.getId(), version));
				decisionTable.setData(dt);
			}
		} else {
			decisionTable.setData(null);
		}
	}

	private DecisionTableModelSetupService getService() {
		return (DecisionTableModelSetupService) SpringUtil
				.getBean("decisionTableModelSetupServiceImpl");
	}

	private DecisionTableUploadService getDecisionTableUploadService() {
		return (DecisionTableUploadService) SpringUtil
				.getBean("decisionTableUploadServiceImpl");
	}

	public void setDisabled(boolean disabled) {
		lstModel.setDisabled(disabled);
		btnAdd.setDisabled(disabled);
		btnRemove.setDisabled(disabled);
		lstModelVersion.setDisabled(disabled);
		decisionTable.setDisabled(disabled);
		btnDownload.setDisabled(disabled);
		btnUpload.setDisabled(disabled);
	}

	public boolean validate() {
		ErrorMessageUtil.clearErrorMessage(lstModel);
		ErrorMessageUtil.clearErrorMessage(lstModelVersion);

		if (null == lstModel.getSelectedItem()
				|| null == lstModel.getSelectedItem().getValue())
			ErrorMessageUtil.setErrorMessage(lstModel,
					Labels.getLabel("elr.pleaseFillModel"));
		if (!(lstModelVersion.getSelectedItem() != null
				&& lstModel.getSelectedItem() != null
				&& lstModelVersion.getSelectedItem().getValue() != null && !lstModelVersion
				.getSelectedItem().getValue().equals(0)))
			ErrorMessageUtil.setErrorMessage(lstModelVersion,
					Labels.getLabel("elr.pleaseFillVersion"));

		return (lstModelVersion.getSelectedItem() != null
				&& lstModel.getSelectedItem() != null
				&& lstModelVersion.getSelectedItem().getValue() != null && !lstModelVersion
				.getSelectedItem().getValue().equals(0));
	}

	public void clearErrorMessage() {
		ErrorMessageUtil.clearErrorMessage(lstModel);
		ErrorMessageUtil.clearErrorMessage(lstModelVersion);
	}

	public UploadDownloadDelegate getUploadDownloadDelagate() {
		return uploadDownloadDelagate;
	}

	public void setUploadDownloadDelagate(
			UploadDownloadDelegate uploadDownloadDelagate) {
		this.uploadDownloadDelagate = uploadDownloadDelagate;
	}

	public Button getButtonUpload() {
		return this.btnUpload;
	}
	
// added by jatis for CAM
	public Button getButtonDownload() {
		return this.btnDownload;
	}
// added by jatis for CAM
	
	public Listbox getVersionDecisionTable(){
		return this.lstModelVersion;
	}
	
	public Listbox getModelDecisionTable(){
		return this.lstModel;
	}
	
	public void setReadOnly(boolean readOnly) {
		for(Listitem item : decisionTable.getItems()) {
			for(Component c : item.getChildren()){
				for (Component child : c.getChildren()){
					if(child instanceof Bandbox){
						((Bandbox) child).setReadonly(readOnly);
					}else if (child instanceof Textbox){
						((Textbox) child).setReadonly(readOnly);
					}else if (child instanceof InputElement ) {
						((InputElement) child).setReadonly(readOnly);
					}
				}
			}
		}
	}

}

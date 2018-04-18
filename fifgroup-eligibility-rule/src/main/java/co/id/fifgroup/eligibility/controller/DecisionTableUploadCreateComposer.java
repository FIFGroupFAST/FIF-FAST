package co.id.fifgroup.eligibility.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.id.fifgroup.core.domain.BatchUpload;
import co.id.fifgroup.core.domain.UploadError;
import co.id.fifgroup.core.dto.UploadElementDTO;
import co.id.fifgroup.eligibility.dto.DecisionTableUploadDTO;
import co.id.fifgroup.eligibility.service.DecisionTableUploadService;
import co.id.fifgroup.eligibility.upload.Argument;
import co.id.fifgroup.eligibility.upload.Argument.Type;

@VariableResolver(DelegatingVariableResolver.class)
public class DecisionTableUploadCreateComposer extends SelectorComposer<Window>{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(DecisionTableUploadCreateComposer.class);
	
	private BatchUpload openBatch;
	private UploadElementDTO uploadElementDTO;
	private Media media;
	
	@Wire private Longbox txtDecisionTableId;
	@Wire private Label lblBatchNumber;
	@Wire private Textbox txtFile;
	@Wire private Label lblTotalRecord;
	@Wire private Label lblTotalSucceed;
	@Wire private Label lblTotalFailed;
	@Wire private Listbox lstFailedRow;
	@Wire private Button btnDownloadFailed;
	@Wire private Button btnCloseBatch;
	
	
	@WireVariable(rewireOnActivate = true)
	private transient DecisionTableUploadService decisionTableUploadServiceImpl;
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		logger.debug("Opening Create Decision Table Upload UI...");
		DecisionTableUploadDTO uploadDTO = new DecisionTableUploadDTO();
		
		Long id = null == txtDecisionTableId.getValue() ? 0L : txtDecisionTableId.getValue();
		
		uploadDTO.setFormatReferenceId(id);
		
		openBatch = decisionTableUploadServiceImpl.getOpenStatusObject(uploadDTO);
		if (null != openBatch) {
			lblBatchNumber.setValue(openBatch.getBatchNumber());
		} else {
			openBatch = new BatchUpload();
		}
	}
	
	@Listen("onClick = #btnCheck") 
	public void onCheck() {
		
		Long id = null == txtDecisionTableId.getValue() ? 0L : txtDecisionTableId.getValue();
		DecisionTableUploadDTO uploadDTO = new DecisionTableUploadDTO();
		uploadDTO.setFormatReferenceId(id);
		
		openBatch = decisionTableUploadServiceImpl.getOpenStatusObject(uploadDTO);
		if (null == openBatch) {
			openBatch = new BatchUpload();
		}
		lblBatchNumber.setValue(openBatch.getBatchNumber());
	}
	
	@Listen("onClick = #btnDownloadTemplate")
	public void onDownloadTemplate() throws Exception{
		decisionTableUploadServiceImpl.downloadTemplate(
				new Argument(Type.ID, String.valueOf(txtDecisionTableId.getValue())),
				new Argument(Type.MODULE, "ELR"));
	}
	
	@Listen("onUpload = #btnBrowse")
	public void onBrowse(UploadEvent event) {
		media = event.getMedia();
		txtFile.setValue(media.getName());
	}
	
	@Listen("onClick = #btnProcess")
	public void onProcess() {
		try {
			DecisionTableUploadDTO upload = new DecisionTableUploadDTO();
			upload.setBatchNumber(openBatch.getBatchNumber());
			Long decisionTableId = openBatch.getFormatReferenceId();
			upload.setFormatReferenceId(null == decisionTableId ? txtDecisionTableId.getValue() : decisionTableId);
			uploadElementDTO = decisionTableUploadServiceImpl.process(media, true, upload);
			
			lblTotalRecord.setValue(String.valueOf(uploadElementDTO.getSuccessRows() + uploadElementDTO.getFailedRows()));
			lblTotalSucceed.setValue(String.valueOf(uploadElementDTO.getSuccessRows()));
			lblTotalFailed.setValue(String.valueOf(uploadElementDTO.getFailedRows()));
			lblBatchNumber.setValue(String.valueOf(uploadElementDTO.getBatchNumber()));
			List<UploadError> uploadErrors = decisionTableUploadServiceImpl.getFailedRows(uploadElementDTO.getUploadId());
			lstFailedRow.setModel(new ListModelList<>(uploadErrors));
			if(uploadErrors.size() > 0) {
				btnDownloadFailed.setDisabled(false);
			}
			btnCloseBatch.setDisabled(false);
		} catch (Exception ex) {
			Messagebox.show(ex.getMessage());
			logger.error(ex.getMessage(), ex);
		}
	}
	
	@Listen("onClick = #btnDownloadFailed")
	public void onDownloadFailed() {
		decisionTableUploadServiceImpl.downloadFailedRecords(uploadElementDTO.getUploadId());
	}
	
	@Listen("onClick = #btnCloseBatch")
	public void onCloseBatch() {
		decisionTableUploadServiceImpl.closeBatch(uploadElementDTO.getBatchUpload());
	}

}

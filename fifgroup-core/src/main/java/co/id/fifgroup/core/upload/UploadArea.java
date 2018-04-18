package co.id.fifgroup.core.upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class UploadArea extends Hbox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String upload;
	private Button uploadBtn;
	private A linkLabel;
	private int count = 0;
	private String fileInfo;
	private List<Media> selectedMedias = new ArrayList<Media>();
	private static String FILE = " file(s)";
	private UploadArea thisComposer = this;
	private List<String> listFormatFiles;
	private int maxSize = 0;
	private boolean disabledRemove = false;
	private boolean isPhoto = false;

	public UploadArea() {
		uploadBtn = new Button(Labels.getLabel("common.browse"));
		fileInfo = count + FILE;
		linkLabel = new A();
		linkLabel.setLabel(fileInfo);
		linkLabel.setStyle("color:blue");

		linkLabel.addEventListener(Events.ON_CLICK,
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("composer", thisComposer);
						param.put("disabledRemove", disabledRemove);
						Window win = (Window) Executions.createComponents(
								"~./common/popup_file_upload.zul", null, param);
						win.doModal();
					}
				});

		appendChild(uploadBtn);
		appendChild(linkLabel);
	}

	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
		uploadBtn.setUpload(upload);

		uploadBtn.addEventListener(Events.ON_UPLOAD,
				new SerializableEventListener<UploadEvent>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(UploadEvent event) throws Exception {
						Media[] medias = event.getMedias();
						List<Media> validMedia = new ArrayList<Media>();
						for (Media media : medias) {
							if (doValidateDocumentSize(media)) {
								if (doValidateDocumentFile(media)) {
									validMedia.add(media);
								}							
							}
						}
						selectedMedias.addAll(validMedia);
						count = selectedMedias.size();
						fileInfo = count + FILE;
						linkLabel.setLabel(fileInfo);
						Events.postEvent("onUploadMedia", uploadBtn,
								selectedMedias);
					}
				});

		uploadBtn.addEventListener("onSetCount",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						fileInfo = count + FILE;
						linkLabel.setLabel(fileInfo);
					}
				});
	}
	
	// start added by WLY, ITSM 15062214124936, 27/07/2015
	public void setUploadWithMaxTotalSize(String upload) {
		this.upload = upload;
		uploadBtn.setUpload(upload);

		uploadBtn.addEventListener(Events.ON_UPLOAD,
				new SerializableEventListener<UploadEvent>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(UploadEvent event) throws Exception {
						Media[] medias = event.getMedias();
						List<Media> validMedia = new ArrayList<Media>();
						for (Media media : medias) {
							if (doValidateTotalDocumentSize(media)) {
								if (doValidateDocumentFile(media)) {
									validMedia.add(media);
								}							
							}
						}
						selectedMedias.addAll(validMedia);
						count = selectedMedias.size();
						fileInfo = count + FILE;
						linkLabel.setLabel(fileInfo);
						Events.postEvent("onUploadMedia", uploadBtn,
								selectedMedias);
					}
				});

		uploadBtn.addEventListener("onSetCount",
				new SerializableEventListener<Event>() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onEvent(Event event) throws Exception {
						fileInfo = count + FILE;
						linkLabel.setLabel(fileInfo);
					}
				});
	}
	
	private boolean doValidateTotalDocumentSize(Media media) {
		int totalSize = 0;
		for (Media selectedMedia : selectedMedias){
			totalSize += selectedMedia.getByteData().length;
		}
		totalSize += media.getByteData().length;
		
		if (maxSize > 0) {
			if (totalSize < (maxSize * 1024)) {
				return true;
			}
			Messagebox.show(Labels.getLabel("common.err.maximumFileSize", new Object[] {maxSize}));
			return false;
		}
		return true;
	}
	// end added by WLY, ITSM 15062214124936, 27/07/2015
	
	public void removeMedia(Media media) {
		selectedMedias.remove(media);
		count = selectedMedias.size();
		fileInfo = count + FILE;
		linkLabel.setLabel(fileInfo);

		Events.postEvent("onUploadMedia", uploadBtn, selectedMedias);
	}

	public List<Media> getMedias() {
		return selectedMedias;
	}

	public void setDisabled(Boolean disabled) {
		uploadBtn.setDisabled(disabled);
		disabledRemove = disabled;
//		linkLabel.setDisabled(disabled);
	}

	public Button getButtonUpload() {
		return uploadBtn;
	}

	public void setMedias(List<Media> medias) {
		this.selectedMedias = medias;
		count = selectedMedias.size();
		fileInfo = count + FILE;
		linkLabel.setLabel(fileInfo);
	}

	public A getLinkLabel() {
		return linkLabel;
	}

	public List<String> getListFormatFiles() {
		return listFormatFiles;
	}

	public void setListFormatFiles(List<String> listFormatFiles) {
		this.listFormatFiles = listFormatFiles;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	private boolean doValidateDocumentFile(Media media) {
		String extension = media.getName().substring(media.getName().indexOf(".") + 1, media.getName().length());
		boolean valid = false;
		if(isPhoto){
			
			if (extension.toUpperCase().equals("JPEG")||extension.toUpperCase().equals("JPG")) {
				valid = true;
			}else{
				Messagebox.show(Labels.getLabel("common.err.formatFileInvalid"));
				return false;
			}
			
		}else{
			if (listFormatFiles != null && listFormatFiles.size() > 0) {
				for (String formatFiles : listFormatFiles) {
					if (extension.toLowerCase().equals(formatFiles.toLowerCase())) {
						return true;
					}
				}
				Messagebox.show(Labels.getLabel("common.err.formatFileInvalid"));
				return false;
			}
			valid = true;
		}
		return valid;
		
	}

	private boolean doValidateDocumentSize(Media media) {
		if (maxSize > 0) {
			if (media.getByteData().length < (maxSize * 1024)) {
				return true;
			}
			Messagebox.show(Labels.getLabel("common.err.maximumFileSize", new Object[] {maxSize}));
			return false;
		}
		return true;
	}
	
	public void setPhoto(boolean isPhoto){
		this.isPhoto = isPhoto;
	}
}

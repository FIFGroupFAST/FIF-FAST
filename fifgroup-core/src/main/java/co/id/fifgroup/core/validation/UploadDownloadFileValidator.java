package co.id.fifgroup.core.validation;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;

public class UploadDownloadFileValidator implements Validator<Media> {

	public static final String NUMBER_OF_COLUMN = "uploadDownload.numberOfColumn";
	public static final String EXTENSION_FILE = "uploadDownload.extensionFile";
	public static final String UPLOAD_FILE_EMPTY = "uploadDownload.uploadFileEmpty";
	public static final String HEADER_TEMPLATE = "uploadDownload.templateFile";
	
	private String extension = "";
	
	@Override
	public void validate(Media subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject) {
			violations.put(UPLOAD_FILE_EMPTY, Labels.getLabel("common.mustBeFill", new Object[] {Labels.getLabel("common.file")}));
			throw new ValidationException(violations);
		}
		if (subject.getByteData().length==2) {
			violations.put(UPLOAD_FILE_EMPTY, Labels.getLabel("common.mustbefillonerow"));
			throw new ValidationException(violations);
		}
		String formatSubject = subject.getName().substring(subject.getName().indexOf(".") + 1, subject.getName().length());
		if(!formatSubject.equalsIgnoreCase(extension)) {
			violations.put(EXTENSION_FILE, Labels.getLabel("common.fileFormatIsInvalid"));
		}
		if(violations.size() > 0) throw new ValidationException(violations);
	}
	
	public void validate2(Media subject) throws ValidationException {
		Map<String, String> violations = new HashMap<String, String>();
		if (null == subject) {
			violations.put(UPLOAD_FILE_EMPTY, Labels.getLabel("common.mustBeFill", new Object[] {Labels.getLabel("common.file")}));
			throw new ValidationException(violations);
		}
		
		String formatSubject = subject.getName().substring(subject.getName().indexOf(".") + 1, subject.getName().length());
		if(!formatSubject.equalsIgnoreCase(extension)) {
			violations.put(EXTENSION_FILE, Labels.getLabel("common.fileFormatIsInvalid"));
			throw new ValidationException(violations);
		}
		
		if (subject.getByteData().length==0) {
			violations.put(UPLOAD_FILE_EMPTY, Labels.getLabel("common.mustbefillonerow"));
			throw new ValidationException(violations);
		}
		
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}

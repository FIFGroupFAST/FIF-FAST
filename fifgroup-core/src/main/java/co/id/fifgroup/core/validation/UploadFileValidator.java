package co.id.fifgroup.core.validation;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;

import co.id.fifgroup.core.upload.UploadUtil;

public abstract class UploadFileValidator implements Validator<Media>{
	
	private Logger logger = LoggerFactory.getLogger(UploadFileValidator.class);
	
	public static final String FORMAT_VALIDATE = "format";
	public static final String TEMPLATE_VALIDATE = "template";
	public static final String FILE_SIZE_VALIDATE = "fileSize";
	
	@Override
	public void validate(Media media) throws ValidationException {
		Map<String, String> mapValidate = new HashMap<String, String>();
		int columnLength = getColumns();
		
		try {
		/*	if(!(media.getFormat().equals("xls")) {
				mapValidate.put(FORMAT_VALIDATE, "File format is invalid");
			}*/
			
			File file = new File(media.getName());
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(media.getByteData());
			fos.flush();
			fos.close();
			
			String[] headers = UploadUtil.getHeader(file);
			if(headers.length != columnLength) {
				mapValidate.put(TEMPLATE_VALIDATE, "Template is invalid");
			}
			
			if(file.length() > 10000) {
				mapValidate.put(FILE_SIZE_VALIDATE, "Exceed file size");
			}
		} catch (Exception e) {
			logger.error(null, e);
		} finally {
			if(mapValidate.size() > 0) {
				throw new ValidationException(mapValidate);
			}
		}
	}
	
	public abstract int getColumns();

}

package co.id.fifgroup.personneladmin.util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.personneladmin.dto.MediaDTO;

import co.id.fifgroup.basicsetup.constant.UploadExtension;
import co.id.fifgroup.core.ui.GlobalVariable;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static String FOLDER_PEA = "PERSONNEL_ADMINISTRATION";
	
	
	public static void downloadFile(String path, String contentType) {
		try {
			if (path != null) {
				File file = new File(GlobalVariable.getAppsRootFolder() + path);
				FileInputStream inputStream = new FileInputStream(file);
				System.out.println("file.getName()"+file.getName());
				Filedownload.save(inputStream, contentType, file.getName());
			} else {
				Messagebox.show(Labels.getLabel("pea.err.fileNotFound"), "Information", Messagebox.YES, Messagebox.INFORMATION);
			}
		} catch (FileNotFoundException e) {
			Messagebox.show(Labels.getLabel("pea.err.fileNotFound"), "Information", Messagebox.YES, Messagebox.INFORMATION);
			logger.error(e.getMessage(), e);
		}
	}
	
	public static boolean doValidateImageFile(Media media) {
		String extension = media.getName().substring(media.getName().indexOf(".") + 1, media.getName().length());
		if(!extension.toLowerCase().equals(UploadExtension.PNG.toString().toLowerCase()) 
				&& !extension.toLowerCase().equals(UploadExtension.JPEG.toString().toLowerCase()) 
				&& !extension.toLowerCase().equals(UploadExtension.JPG.toString().toLowerCase())) {
			Messagebox.show(Labels.getLabel("pea.err.formatFileImage"));
			return false;
		} 
		return true;
	}
	
	public static boolean doValidateDocumentFile(Media media) {
		String extension = media.getName().substring(media.getName().indexOf(".") + 1, media.getName().length());
		if(!extension.toLowerCase().equals(UploadExtension.PNG.toString().toLowerCase()) 
				&& !extension.toLowerCase().equals(UploadExtension.JPEG.toString().toLowerCase()) 
				&& !extension.toLowerCase().equals(UploadExtension.JPG.toString().toLowerCase())
				&& !extension.toLowerCase().equals(UploadExtension.PDF.toString().toLowerCase())) {
			Messagebox.show(Labels.getLabel("common.err.formatFileInvalid"));
			return false;
		} 
		return true;
	}
	
	public static boolean doValidateDocumentSize(Media media, int maxSize) {
		try {
			if (media.getByteData().length > (maxSize * 1024)) {
				Messagebox.show(Labels.getLabel("common.err.maximumFileSize", new Object[] {maxSize}));
				return false;
			}
		} catch (IllegalStateException ex) {
			if (media.getStringData().getBytes().length > (maxSize * 1024)) {
				Messagebox.show(Labels.getLabel("common.err.maximumFileSize", new Object[] {maxSize}));
				return false;
			}
		} catch (Exception ex) {
			logger.warn(ex.getMessage(), ex);
			return false;
		}
		
		return true;
	}
	
	private static String createRootPath(String documentType, String folder) {

		File root = new File(GlobalVariable.getAppsRootFolder());

		if (!root.isDirectory()) {
			root.mkdir();
		}
		
		File peaFolder = new File(root.getPath() + File.separator + folder);
		if (!peaFolder.isDirectory()) {
			peaFolder.mkdir();
		}
		
		File documentTypePath = new File(peaFolder.getPath()  + File.separator + documentType);
		if (!documentTypePath.isDirectory()) {
			documentTypePath.mkdir();
		}
		return documentTypePath.getPath();		
	}
	
	public static String doUpload(MediaDTO media) {
		return doUpload(media, FOLDER_PEA);
	}
	
	public static String doUpload(MediaDTO media, String folder) {
		String fullPath = null;
		String filePath = null;
		String fileName = null;
		if(media.getMedia() != null) {
				
			InputStream inputMedia = media.getMedia().getStreamData();
			
			DataOutputStream out = null;
			try {
				SimpleDateFormat sdfLogo = new SimpleDateFormat("ddMMyyyyhhmmS");
				fileName = media.getDocumentType() + "_" +  media.getEmployeeNumber()
						+ "_" + sdfLogo.format(new Date()) 
						+ media.getMedia().getName().substring(media.getMedia().getName().indexOf("."), media.getMedia().getName().length());
				
				fullPath = createRootPath(media.getDocumentType(), folder) + File.separator + (fileName);
				File file = new File(fullPath);
				out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
				int ch = 0;
				while((ch = inputMedia.read()) != -1) {
					out.write(ch);
				}
				out.flush();
				filePath = File.separator + folder + File.separator + media.getDocumentType() + File.separator + fileName;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} finally {
				if(out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} 
		media.setFileName(fileName);
		return filePath;
	}
	
	public static void deleteFile(String filePathName) {
		try {
			File file = new File(GlobalVariable.getAppsRootFolder() + filePathName);
			file.delete();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static String copyDataUpload(MediaDTO media, String fullPath) {
		return copyDataUpload(media, fullPath, FOLDER_PEA);
	}
	
	public static String copyDataUpload(MediaDTO media, String fullPath, String folder) {
		String filePath = null;
		String fileName = null;
		DataOutputStream out = null;
		try {
			File dataFile = new File(GlobalVariable.getAppsRootFolder() + fullPath);
			InputStream inputMedia = new FileInputStream(dataFile);
			SimpleDateFormat sdfLogo = new SimpleDateFormat("yyyyMMddhhmmS");
			fileName = media.getDocumentType() + "_" + media.getEmployeeNumber()
					+ "_" + sdfLogo.format(new Date()) 
					+ fullPath.substring(fullPath.indexOf("."), fullPath.length());
			
			fullPath = createRootPath(media.getDocumentType(), folder) + File.separator + (fileName);
			File file = new File(fullPath);
			out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			int ch = 0;
			while((ch = inputMedia.read()) != -1) {
				out.write(ch);
			}
			out.flush();
			filePath = File.separator + FOLDER_PEA + File.separator + media.getDocumentType() + File.separator + fileName;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		media.setFileName(fileName);
		return filePath;
	}
	
	public static List<String> unwrapAndMark(String string2bTokenized) {
		String separator = ",";
		List<String> listString = new ArrayList<String>();
		if (string2bTokenized != null && !string2bTokenized.isEmpty()) {
			StringTokenizer tokens = new StringTokenizer(string2bTokenized,
					separator);
			int count = tokens.countTokens();
			for (int i = 0; i < count; i++) {
				String temp = tokens.nextToken().trim();
				listString.add(temp);
			}			
		}	
		
		return listString;
	}
}

package co.id.fifgroup.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

public class DownloadUtil {
	
	/**
	 * 
	 * @param path
	 * @param contentType
	 */
	public static void downloadFile(String path, String contentType) {
		try {
			if(path != null) {
				File file = new File(path);
				FileInputStream inputStream = new FileInputStream(file);
				System.out.println("file.getName()"+file.getName());
				Filedownload.save(inputStream, contentType, file.getName());
			} else {
				Messagebox.show(Labels.getLabel("sam.fileNotFound"), "Information", Messagebox.YES, Messagebox.INFORMATION);
			}
		} catch (FileNotFoundException e) {
			Messagebox.show(Labels.getLabel("sam.fileNotFound"), "Information", Messagebox.YES, Messagebox.INFORMATION);
		}
	}
}

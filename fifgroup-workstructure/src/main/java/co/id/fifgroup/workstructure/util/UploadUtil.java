package co.id.fifgroup.workstructure.util;

public class UploadUtil {
	public static boolean isNumeric(String inputData) {
		return inputData.matches("[-+]?\\d+(\\.\\d+)?");		
	}
}
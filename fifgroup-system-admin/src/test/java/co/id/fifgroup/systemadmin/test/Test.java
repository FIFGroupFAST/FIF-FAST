package co.id.fifgroup.systemadmin.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] xxx) {
		String path = "~./hcms/basic-setup/formula_usage.zul";
		File file = new File(path);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getParent());
		System.out.println(file.getAbsoluteFile());
		System.out.println(file.getName());
	}
	
}

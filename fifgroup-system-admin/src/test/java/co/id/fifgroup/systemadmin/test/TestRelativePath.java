package co.id.fifgroup.systemadmin.test;

import java.io.File;

public class TestRelativePath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File[] files = File.listRoots();
		for (File file : files) {
			System.out.println(file.getPath());
		}
		
	}

}

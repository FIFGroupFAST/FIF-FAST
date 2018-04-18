package test.co.id.fifgroup.core;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Test;

public class ZipTest {

	@Test
	public void testZip() throws Exception{
		ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(new File("test.zip"));
		ZipArchiveEntry entry = new ZipArchiveEntry(new File("pom.xml"), "pom.xml");
		zipOutput.putArchiveEntry(entry);
		IOUtils.copy(new FileInputStream(new File("pom.xml")), zipOutput);
		zipOutput.closeArchiveEntry();
		zipOutput.finish();
		zipOutput.close();
	}
}

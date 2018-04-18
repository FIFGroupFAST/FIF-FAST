package co.id.fifgroup.systemadmin.test;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import co.id.fifgroup.core.validation.TaskExecutionException;

import co.id.fifgroup.core.task.ExecutableTask;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class JasperTester extends ExecutableTask{
	
	Map<String, Object> param = new HashMap<String, Object>();
	

	String jasperFileName = "D:\\Jaspers\\jasperReport1.jasper";
	File file = new File(jasperFileName);
	String fileName = file.getName().substring(0, file.getName().length()-7);

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
	String date = sdf.format(new Date());

	String jasperXmlFileName = "D:\\Jaspers\\jasperReport1.jrxml";

	String outputFormat = "PDF";

	String outputFileName = "D:\\HCMS\\REPORTS\\" + fileName + "_" + date + "."+ outputFormat;
//	JasperFillManager

	DataSource ds = (DataSource) getApplicationContext().getBean("dataSource");
	Connection conn = DataSourceUtils.getConnection(ds);

	
//	String jPrint = JasperFillManager.fillReportToFile(jasperFileName, param, conn);
//	JasperExportManager.exportReportToPdfFile(jPrint,outputFileName);
		
	@Override
	public void execute() throws TaskExecutionException {
//		System.out.println(this.jPrint);
		
	}
	@Override
	public void setParameter(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}
		
	
}

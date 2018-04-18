package co.id.fifgroup.systemadmin.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zkoss.util.resource.Labels;

import co.id.fifgroup.systemadmin.constant.TaskDataType;
import co.id.fifgroup.systemadmin.domain.Responsibility;
import co.id.fifgroup.systemadmin.domain.ResponsibilityExample;
import co.id.fifgroup.systemadmin.domain.Task;
import co.id.fifgroup.systemadmin.dto.ExecutableTaskDTO;
import co.id.fifgroup.systemadmin.dto.ResponsibilityDTO;
import co.id.fifgroup.systemadmin.service.FunctionService;
import co.id.fifgroup.systemadmin.service.MenuService;
import co.id.fifgroup.systemadmin.service.ResponsibilityService;
import co.id.fifgroup.systemadmin.service.TaskRequestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class SystemAdminTest {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ResponsibilityService responsibilityService;
	
	@Autowired
	private TaskRequestService taskRequestServiceImpl;

	private static Logger log = LoggerFactory.getLogger(SystemAdminTest.class);

	public static void objectTest() {
		Task mainTask = new Task();
		mainTask.setId(-1L);
		System.out.println(mainTask == null);
		System.out.println(mainTask.getId());
	}

	@Test
	public void testReport() {
		taskRequestServiceImpl.taskParameterBuilder(TaskDataType.TEXT.toString(), "lalala", "lalala");
//		Date subject = new Date(1367952300000L);
//		int hours = subject.getHours();
//		int minute = subject.getMinutes();
//		System.out.println(hours + ":" + minute);
//		FilenameFilter textFilter = 
//		File txtFile = new File("D:\\HCMS\\LOGS\\1190_30-Apr-2013.txt");
//		try {
//			Scanner scanner = new Scanner(txtFile);
//			while (scanner.hasNextLine()) {
//				String line = scanner.nextLine();
//				System.out.println(line);
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// try {
		// PrintWriter log = new PrintWriter(file);
		// for (int i = 0; i < 4; i++) {
		//
		// log.println("lalala");
		// }
		// log.close();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// String jasperFileName = "D:\\Jaspers\\jasperReport1.jasper";
		// String pdfFileName = "D:\\HCMS\\REPORTS\\jasperReport1.pdf";
		// String xmlFileName = "D:\\HCMS\\REPORTS\\jasperReport1.xml";
		//
		// try {
		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("nama", "Fajar");
		//
		// DataSource ds = dataSource;
		// Connection conn = DataSourceUtils.getConnection(ds);
		// JasperPrint jPrint = new JasperPrint();
		// jPrint = (JasperPrint) JasperFillManager.fillReport(jasperFileName,
		// param, conn);
		// JasperExportManager.exportReportToPdfFile(jPrint, pdfFileName);
		// JasperExportManager.exportReportToXmlFile(jPrint, xmlFileName,false);
		// System.out.println("done");
		// } catch (JRException e) {
		// System.out.println(e.getMessage());
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) {

		// File newFile = new File("D:\\HCMS\\REPORTS\\JASPERX.jrxml");
		// if(!newFile.exists()){
		// try {
		// newFile.createNewFile();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// String x = "apapuniniisinya.jasper";
		// String y = x.substring(0, x.length()-7);
		// System.out.println(y);

		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		// String date = sdf.format(new Date());
		// System.out.println(date);
	}

	public void responsibilityTEST() {
		ResponsibilityDTO respDto = new ResponsibilityDTO();
		Responsibility resp = new Responsibility();
		resp.setResponsibilityName("Test");
		resp.setDateFrom(new Date());
		try {
			responsibilityService
					.countResponsibilityByExample(new ResponsibilityExample());
		} catch (Exception e) {
			log.error("error", e);
		}

	}

	/*
	 * public void functionCRUD(){
	 * 
	 * // Function function = new Function(); // function.setModule(new
	 * Module((long) 1)); // function.setItemName("Function A"); //
	 * function.setDescription("Function Desc"); //
	 * function.setActionUrl("test/url.zul"); // function.setAuditInfo(new
	 * AuditInfo((long)1, new Date(), (long)1, new Date())); // // try { //
	 * functionService.save(function); // } catch (Exception e) { //
	 * log.error("Error Access Data", e); // } // // Function example = new
	 * Function(); // example.setModule(new Module((long) 1)); //
	 * example.setItemName("%Function A%"); //
	 * example.setDescription("%Function Desc%"); // // List<Function> functions
	 * = functionService.getFunctionByExample(example); // for (Function func :
	 * functions) { // System.out.println(func); // try { //
	 * functionService.save(func); // } catch (Exception e) { //
	 * log.error("Error Update",e); // } // } // // Function func =
	 * functionService.getFunctionById((long) 3); // System.out.println(func); }
	 * 
	 * @Test public void menuCRUD(){ Function function1 = new Function();
	 * function1.setItemId((long) 3);
	 * 
	 * Menu menu = new Menu(); menu.setItemId((long) 4);
	 * 
	 * Menu menu1 = new Menu(); menu1.setItemName("Menu 2");
	 * menu1.setDescription("Description"); menu1.setDateFrom(new Date());
	 * menu1.setDateTo(DateUtil.MAX_DATE); menu1.setAuditInfo(new
	 * AuditInfo((long)1, new Date(), (long)1, new Date()));
	 * 
	 * List<MenuDetail> menuDetails = new ArrayList<MenuDetail>();
	 * menuDetails.add(new MenuDetail(1, "Func1", function1, new Date(),
	 * DateUtil.MAX_DATE)); menuDetails.add(new MenuDetail(2, "menu1", menu, new
	 * Date(), DateUtil.MAX_DATE));
	 * 
	 * menu1.setMenuDetails(menuDetails);
	 * 
	 * 
	 * try { menuService.save(menu1); } catch (Exception e) {
	 * log.error("Error Menu", e); } }
	 */

}

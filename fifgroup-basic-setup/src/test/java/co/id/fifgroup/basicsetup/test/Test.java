package co.id.fifgroup.basicsetup.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Duration;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zkplus.spring.SpringUtil;

import co.id.fifgroup.basicsetup.common.BoyerMoore;
import co.id.fifgroup.basicsetup.domain.Printer;
import co.id.fifgroup.basicsetup.service.PrinterUtilService;
import co.id.fifgroup.basicsetup.service.impl.PrinterUtilServiceImpl;
import co.id.fifgroup.core.constant.BooleanType;
import co.id.fifgroup.core.task.ExecutableTask;
import co.id.fifgroup.core.validation.TaskExecutionException;
import co.id.fifgroup.core.validation.ValidationRule;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import groovy.lang.BenchmarkInterceptor;
import groovy.lang.Newify;


public class Test {

	private Integer a;
	
	public static void main(String[] args) {
//		System.out.println(String.valueOf((char) 48));
//		DataType dataType = DataType.getType(DataType.NUMERIC.toString());
//		System.out.println(true & false);
//		Combobox cmb = new Combobox();
//		cmb.appendChild(new Comboitem());
//		cmb.appendChild(new Comboitem());
//		System.out.println(cmb.getSelectedIndex());
//		ValidationUtil.isEmpty(cmb, "Error");
//		new Test().play();
//		BigDecimal bigDecimal = new BigDecimal("1234");
//		BigDecimal bigDecimal1 = new BigDecimal("123.25");
//		System.out.println(bigDecimal1.add(bigDecimal));
//		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//		df.setLenient(false);
//		try {
//			System.out.println(df.parse("01-01-20078"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		BigDecimal bigDecimal = new BigDecimal("0154.00");
//		System.out.println(bigDecimal);
//		System.out.println("123".split("\\.")[0]);
//		play();
		
//		try {
//			Map<String, String> model = new HashMap<String, String>();
//			model.put("printerName", "test printer");
//			model.put("printerName1", "test printer");
//			Template template = new Template("printer", new StringReader("lpr ${printerName}"), new Configuration());
//			Writer output = new StringWriter();
//			template.process(model, output);
//			System.out.println(output.toString());
//		} catch (IOException | TemplateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		BoyerMoore boyerMoore = new BoyerMoore();
//		System.out.println(boyerMoore.isPatternInText("MATCH_TEXT", "TEST MESSAGE TEMPLATE MATCH_TEXT"));
//		String str = "D:\\user\\picture\\icon.png";
//		String[] splits = str.split("\\\\");
//		System.out.println(splits.length);
		
//		List<String> a = new ArrayList<String>();
//		List<String> b = new ArrayList<String>();
//		
//		a.add("a");
//		a.add("b");
//		b.add("c");
//		b.add("d");
//		
//		for(String str : a) {
//			for(String str1 : b) {
//				System.out.println(str);
//				break;
//			}
//		}
//		System.out.println(StringUtils.collectionToDelimitedString(a, "|"));
//		System.out.println(StringUtils.delimitedListToStringArray("a", "|")[0]);
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:**/applicationContext.xml");
//		try {
//			Selectors.wi
//			ExecutableTask ext = (ExecutableTask) Class.forName("co.id.fifgroup.basicsetup.common.ResetSequenceGenerator").newInstance();
//			ext.setApplicationContext(ctx);
//			ext.execute();
//		} catch (InstantiationException | IllegalAccessException
//				| ClassNotFoundException | TaskExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			test();
//		} catch (TestException e) {
//			System.out.println("TestException");
//		} catch (Exception e) {
//			System.out.println("Exception");
//		}
//		Calendar calStartEffectiveLeave = Calendar.getInstance();
//		Calendar calEndEffectiveLeave = Calendar.getInstance();
//		calStartEffectiveLeave.setTime(DateUtils.truncate(new Date(), Calendar.DATE));
//		calEndEffectiveLeave.setTime(DateUtils.truncate(calStartEffectiveLeave.getTime(), Calendar.DATE));
//		calEndEffectiveLeave.add(Calendar.DAY_OF_MONTH, 0);
//		System.out.println((calEndEffectiveLeave.getTimeInMillis() - calStartEffectiveLeave.getTimeInMillis()) / (24 * 60 * 60 * 1000));
//		System.out.println((int) (3/2));
//		ArrayList<String> arrayList = new ArrayList<String>();
//		arrayList.add("test");
//		List<String> a = (List<String>) arrayList;
//		ArrayList<String> b = (ArrayList<String>) a;
//		System.out.println(b);
//		System.out.println(Test.class.getName());
//		Media media;
//		
//		String[] fieldMapping = new String[] {
//				"dateFrom",
//				"code",
//				"name",
//				"description",
//				"levelCode",
//				"npwp",
//				"kppCode",
//				"locationDto.locationCode",
//				"organizationHeadCode",
//				"branchCode",
//				"costCenterCode"
//		};
////		
//		CellProcessor[] processors = new CellProcessor[] {
//			new ParseDate("dd-MMM-YYYY"),
//			new UniqueHashCode(),
//			new UniqueHashCode(),
//			new Optional(),
//			new Optional(),
//			new Optional(),
//			new Optional(),
//			new Optional(),
//			new Optional(),
//			new Optional(),
//			new Optional()
//		};
//		
//		ICsvDozerBeanReader beanReader = null;
//		try {
//			beanReader = new CsvDozerBeanReader(new FileReader("D://organization.csv"), CsvPreference.STANDARD_PREFERENCE);
//			String[] header = beanReader.getHeader(false);
//			for(int i = 0; i < header.length; i++) {
////				System.out.println(header[i]);
//			}
//			beanReader.configureBeanMapping(OrganizationDTO.class, fieldMapping);
//			OrganizationDTO organizationDto = null;
//			boolean isRead = true;
//			while(isRead) {
//				try {
//					while((organizationDto = beanReader.read(OrganizationDTO.class, processors)) != null) {
//						System.out.println(String.format("lineNo=%s, rowNo=%s, organizationDto=%s", beanReader.getLineNumber(), beanReader.getRowNumber(), organizationDto));
//					}
//					isRead = false;
//				} catch (SuperCsvException e) {
//					System.out.println(String.format("Error, lineNo=%s, rowNo=%s, organizationDto=%s", beanReader.getLineNumber(), beanReader.getRowNumber(), beanReader.getUntokenizedRow()));
//				}				
//			}
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Calendar calendar = Calendar.getInstance();
//		calendar.clear(Calendar.YEAR);
//		calendar.clear(Calendar.MONTH);
//		calendar.clear(Calendar.DATE);
//		System.out.println(calendar.getTime());
//		TestClone testClone = new TestClone();
//		testClone.setName("Name 1");
//		
//		TestClone testClone1 = modelMapper.map(testClone, TestClone.class);
//		System.out.println(testClone == testClone1);
		
//		DateTimeComparator timeComparator = DateTimeComparator.getTimeOnlyInstance();
//		Calendar calendarStart = Calendar.getInstance();
//		Calendar calendarEnd = Calendar.getInstance();
//		calendarEnd.clear(Calendar.YEAR);
//		calendarEnd.clear(Calendar.MONTH);
//		calendarEnd.clear(Calendar.DATE);
//		calendarEnd.add(Calendar.MINUTE, 10);
//		System.out.println(calendarStart.getTime() + " - " + calendarEnd.getTime());
//		
//		System.out.println(timeComparator.compare(calendarStart, calendarEnd));
		
//		Calendar calendarStart = Calendar.getInstance();
//		calendarStart.add(Calendar.YEAR, 10);
//		Calendar calendarEnd = Calendar.getInstance();
//		calendarEnd.add(Calendar.HOUR, 1);
//		
//		Duration duration = new Duration(calendarStart.getTimeInMillis(), calendarEnd.getTimeInMillis());
//		System.out.println(duration.getStandardHours());
//		System.out.println(duration.getStandardMinutes());
		
//		Test t = new Test();
//		System.out.println(t.getType("a", String.class));
		System.out.println(ValidationRule.isValidDateTime("13-FEB-13 24:60:12"));
//		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy kk:mm:ss");
//		df.setLenient(false);
//		try {
//			System.out.println(df.format(df.parse("13-FEB-2013 24:70:12")));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void play() {
		int i = 100;
		int j = 5;
		
		while(i > 0) {
			try {
				System.out.println(i);
				System.out.println(i-- / j--);
			} catch (Exception e) {
				e.printStackTrace();
				j = 5;
			}
		}
	}
	
	public static void test() throws TestException {
		throw new TestException();
	}
	
	public <T> T getType(Class<T> clazz) {
		Object a = new String();
		a = "abc";
		
		return (T) a;
	}
	
}

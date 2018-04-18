package co.id.fifgroup.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.core.service.GlobalSettingServiceAdapter;
import co.id.fifgroup.core.ui.GlobalVariable;

public class FormatDateUI {
	
//	private static SimpleDateFormat sdf;
	
	private static final Logger logger = LoggerFactory.getLogger(FormatDateUI.class);
	
	public static String formatDate(Date date) {
		GlobalSettingServiceAdapter globalSettingService = (GlobalSettingServiceAdapter) ApplicationContextUtil.getApplicationContext().getBean("globalSettingServiceImpl");;
		return formatDate(date, globalSettingService.getGlobalSettingValueByCode("DATE_FORMAT"));
	}
	
	public static String formatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		if((sdf == null) || (date == null)) return null;
		return sdf.format(date);
	}
	
	public static String getDateFormat() {
		GlobalSettingServiceAdapter globalSettingService = (GlobalSettingServiceAdapter) ApplicationContextUtil.getApplicationContext().getBean("globalSettingServiceImpl");
		return globalSettingService.getGlobalSettingValueByCode("DATE_FORMAT");
	}
	
	public static String formatDateTime(Date date) {
		return formatDate(date, GlobalVariable.getDateTimeFormat());
	}
}

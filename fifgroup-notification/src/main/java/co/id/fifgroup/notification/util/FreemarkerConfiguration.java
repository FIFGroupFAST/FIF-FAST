package co.id.fifgroup.notification.util;

import java.util.Locale;

import freemarker.template.Configuration;

public class FreemarkerConfiguration {
	
	public static Configuration getConfiguration() {
		Configuration config = new Configuration();
		Locale locale = new Locale("INDONESIA", "id", "ID");
		config.setLocale(locale);
		config.setDateFormat("dd-MMM-yyyy");
		config.setNumberFormat(",##0.00");
		config.setTimeFormat("kk:mm");
		return config;
	}

}
